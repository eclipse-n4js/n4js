/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.xtext.server

import com.google.common.util.concurrent.Uninterruptibles
import com.google.inject.Inject
import java.util.concurrent.CancellationException
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import org.eclipse.core.runtime.OperationCanceledException
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.ide.tests.bugreports.GH_2130_EditorInvalidAfterFollowingHyperlink
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.utils.ReflectionUtils
import org.eclipse.n4js.xtext.ide.server.QueuedExecutorService
import org.eclipse.n4js.xtext.ide.server.ResourceTaskContext
import org.eclipse.n4js.xtext.ide.server.ResourceTaskManager
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

/**
 * Low-level tests for {@link ResourceTaskManager}.
 */
class ResourceTaskManagerIdeTest extends AbstractIdeTest {

	private val TEST_MODULE_NAME = "TestModule";

	private val TEST_MODULE_CONTENT = "console.log('Hello world!');";

	private val TEST_PROJECT_CONTENT = #[
		TEST_MODULE_NAME -> '''
			«TEST_MODULE_CONTENT»
		'''
	];

	@Inject
	private QueuedExecutorService queuedExecutorService;

	@Inject
	private ResourceTaskManager resourceTaskManager;

	private URI uri;

	@Before
	def void prepareWorkspaceAndServer() {
		testWorkspaceManager.createTestProjectOnDisk(TEST_PROJECT_CONTENT);
		startAndWaitForLspServer();
		assertNoIssues();

		uri = getFileURIFromModuleName(TEST_MODULE_NAME).toURI();
	}

	@Test
	def void testBasic() throws Exception {
		assertFalse(resourceTaskManager.hasContext(uri));
		assertFalse(resourceTaskManager.hasContextOnQueue(uri));
		assertNull(resourceTaskManager.getDocumentOnQueue(uri));

		val rtc = resourceTaskManager.createContext(uri, 0, TEST_MODULE_CONTENT).get();

		assertTrue(resourceTaskManager.hasContext(uri));
		assertTrue(resourceTaskManager.hasContextOnQueue(uri));
		val doc = resourceTaskManager.getDocumentOnQueue(uri);
		assertNotNull(doc);
		assertEquals(TEST_MODULE_CONTENT, doc.contents.trim);

		resourceTaskManager.runInExistingContextVoid(uri, "check RTC", [ rtcLocal, ci |
			assertSame(rtc, rtcLocal);
		]).join();

		resourceTaskManager.disposeContext(uri).join();

		assertFalse(resourceTaskManager.hasContext(uri));
		assertFalse(resourceTaskManager.hasContextOnQueue(uri));
		assertNull(resourceTaskManager.getDocumentOnQueue(uri));
	}

	@Test
	def void testAllowCallingRunInExistingContextBeforeContextCreatedOnQueue() {
		// to simulate some delay during the creation of a ResourceTaskContext on its queue,
		// we block the queue with a dummy task:
		val allowContextCreationToProceed = new CountDownLatch(1);
		val f1 = queuedExecutorService.<Void>submit(getQueueIdForContext(uri), "dummy blocker", [ ci |
			Uninterruptibles.awaitUninterruptibly(allowContextCreationToProceed, 10, TimeUnit.SECONDS);
			return null;
		]);

		assertFalse(resourceTaskManager.hasContext(uri));
		assertFalse(resourceTaskManager.hasContextOnQueue(uri));
		assertNull(resourceTaskManager.getDocumentOnQueue(uri));

		try {
			resourceTaskManager.runInExistingContextVoid(uri, "too early task", []);
			fail("the expected IllegalArgumentException was not thrown");
		} catch(IllegalArgumentException e) {
			assertTrue(e.message.startsWith("no existing context found for given URI"));
		}

		val f2 = resourceTaskManager.createContext(uri, 0, TEST_MODULE_CONTENT); // do not join!

		assertTrue(resourceTaskManager.hasContext(uri));
		assertFalse(resourceTaskManager.hasContextOnQueue(uri));
		assertNull(resourceTaskManager.getDocumentOnQueue(uri));

		val testTaskDidStart = new CountDownLatch(1);
		// this must not throw an exception:
		val f3 = resourceTaskManager.runInExistingContextVoid(uri, "test task", [ rtc, ci |
			testTaskDidStart.countDown();
		]);

		// we're still in the intermediate state of the context being created outside the queue but not on the queue:
		assertTrue(resourceTaskManager.hasContext(uri));
		assertFalse(resourceTaskManager.hasContextOnQueue(uri));
		assertNull(resourceTaskManager.getDocumentOnQueue(uri));

		allowContextCreationToProceed.countDown();
		Uninterruptibles.awaitUninterruptibly(testTaskDidStart, 10, TimeUnit.SECONDS);

		assertTrue(resourceTaskManager.hasContext(uri));
		assertTrue(resourceTaskManager.hasContextOnQueue(uri));
		assertNotNull(resourceTaskManager.getDocumentOnQueue(uri));

		waitForCompletion(f1, f2, f3);
	}

	/**
	 * Tests a sequence of context creation, disposal, and re-creation in short succession while
	 * a long-running task is delaying the actual context disposal.
	 *
	 * @see GH_2130_EditorInvalidAfterFollowingHyperlink
	 */
	@Test
	def void testCreateDisposeRecreate() throws Exception {
		val finishOperation1 = new CountDownLatch(1);
		val rtcOfOperation1 = new AtomicReference<ResourceTaskContext>();
		val rtcOfRecreation = new AtomicReference<ResourceTaskContext>();
		val rtcOfRecreationIsAvailable = new CountDownLatch(1);

		val f1 = resourceTaskManager.createContext(uri, 0, TEST_MODULE_CONTENT);
		val f2 = resourceTaskManager.runInExistingContextVoid(uri, "operation #1", [ rtc, ci |
			rtcOfOperation1.set(rtc);
			Uninterruptibles.awaitUninterruptibly(finishOperation1, 10, TimeUnit.SECONDS);
		]);

		val f3 = resourceTaskManager.disposeContext(uri);

		assertFalse(resourceTaskManager.hasContext(uri));
		assertTrue(resourceTaskManager.hasContextOnQueue(uri)); // disposal on the queue is blocked by operation #1

		// even though disposal of the (first) context is delayed on the queue,
		// we must be able to already re-create a context for 'uri':
		val recreation = resourceTaskManager.createContext(uri, 0, TEST_MODULE_CONTENT);

		assertTrue(resourceTaskManager.hasContext(uri));
		assertTrue(resourceTaskManager.hasContextOnQueue(uri));
		
		// ... and we must be able to schedule new tasks for the re-created context:
		val f4 = resourceTaskManager.runInExistingContextVoid(uri, "operation #2", [ rtc, ci |
			assertNotNull(rtcOfOperation1);
			assertNotSame(rtcOfOperation1, rtc);

			Uninterruptibles.awaitUninterruptibly(rtcOfRecreationIsAvailable, 10, TimeUnit.SECONDS);
			assertSame(rtcOfRecreation.get(), rtc);
		]);

		finishOperation1.countDown();
		rtcOfRecreation.set(recreation.get());
		rtcOfRecreationIsAvailable.countDown();

		waitForCompletion(f1, f2, f3, f4);
	}

	/**
	 * Waits for completion of the given futures. Ignores all return values and cancellation, but exceptions other
	 * than cancellation are propagated. Calling this at the end of a test method for all futures ensures that
	 * exceptions inside the tasks are correctly reported by the test.
	 */
	def private void waitForCompletion(CompletableFuture<?>... futures) {
		for (future : futures) {
			try {
				future.join();
			} catch(CancellationException | OperationCanceledException e) {
				// ignore cancellation
			}
		}
	}

	def private Object getQueueIdForContext(URI uri) {
		return ReflectionUtils.getMethodReturn(ResourceTaskManager, "getQueueIdForContext",
			resourceTaskManager, #[URI, Boolean.TYPE], #[uri, false]);
	}
}
