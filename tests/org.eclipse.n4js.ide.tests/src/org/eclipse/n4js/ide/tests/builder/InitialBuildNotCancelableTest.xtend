/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.builder

import com.google.common.base.Optional
import com.google.common.util.concurrent.Uninterruptibles
import com.google.inject.Inject
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest
import org.eclipse.n4js.ide.xtext.server.BuiltInAwareIncrementalBuilder
import org.eclipse.n4js.ide.xtext.server.QueuedExecutorService
import org.eclipse.n4js.ide.xtext.server.build.XClusteringStorageAwareResourceLoader.LoadResult
import org.eclipse.n4js.ide.xtext.server.build.XIndexer.XIndexResult
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping
import org.eclipse.n4js.ide.xtext.server.build.XStatefulIncrementalBuilder
import org.eclipse.n4js.ide.xtext.server.build.XWorkspaceBuilder
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.service.AbstractGenericModule
import org.eclipse.xtext.service.OperationCanceledManager
import org.junit.Test

import static org.junit.Assert.assertTrue

/**
 * Asserts that
 * <ol>
 * <li>the initial build cannot be canceled, and
 * <li>file change events received during the initial build do not get lost and are properly processed after the initial build.
 * </ol>
 */
class InitialBuildNotCancelableTest extends AbstractIdeTest {

	private static final CountDownLatch didBuildModuleOther = new CountDownLatch(1);
	private static final CountDownLatch didPerformCancellation = new CountDownLatch(1);
	private static final CountDownLatch didCompleteInitialBuild = new CountDownLatch(1);
	private static final CountDownLatch didCheckResultOfInitialBuild = new CountDownLatch(1);

	private static final AtomicBoolean initialBuildCompletedNormally = new AtomicBoolean(false);

	private static final class TestWorkspaceBuilder extends XWorkspaceBuilder {
		override protected void onBuildDone(boolean wasInitialBuild, boolean wasCanceled, boolean hasDependencyCycle, Optional<Throwable> throwable) {
			super.onBuildDone(wasInitialBuild, wasCanceled, hasDependencyCycle, throwable);
			if (wasInitialBuild) {
				initialBuildCompletedNormally.set(!wasCanceled && !throwable.present); // normally = no cancellation, no exceptions
				didCompleteInitialBuild.countDown();
				Uninterruptibles.awaitUninterruptibly(didCheckResultOfInitialBuild);
			}
		}
	}

	private static final class TestStatefulIncrementalBuilder extends BuiltInAwareIncrementalBuilder {

		@Inject
		private OperationCanceledManager operationCanceledManager;

		override protected IResourceDescription.Delta buildClustered(LoadResult loadResult, XSource2GeneratedMapping newSource2GeneratedMapping, XIndexResult result) {
			val retval = super.buildClustered(loadResult, newSource2GeneratedMapping, result);
			val fileBeingBuilt = loadResult.uri.lastSegment();
			if (fileBeingBuilt == "Other.n4js") {
				didBuildModuleOther.countDown();
				Uninterruptibles.awaitUninterruptibly(didPerformCancellation);
				operationCanceledManager.checkCanceled(request.getCancelIndicator());
			}
			return retval;
		}
	}

	private static final class TestQueuedExecutorService extends QueuedExecutorService {

		override synchronized cancelAll() {
			super.cancelAll()
			didPerformCancellation.countDown();
		}

		override synchronized cancelAll(Object queueId) {
			super.cancelAll(queueId)
			didPerformCancellation.countDown();
		}
	}

	public static final class InitialBuildNotCancelableTestModule extends AbstractGenericModule {

		def Class<? extends XWorkspaceBuilder> bindXWorkspaceBuilder() {
			return TestWorkspaceBuilder;
		}

		def Class<? extends XStatefulIncrementalBuilder> bindXStatefulIncrementalBuilder() {
			return TestStatefulIncrementalBuilder;
		}

		def Class<? extends QueuedExecutorService> bindQueuedExecutorService() {
			return TestQueuedExecutorService;
		}
	}

	override protected getOverridingModule() {
		return Optional.of(InitialBuildNotCancelableTestModule);
	}

	@Test(timeout = 10000)
	def void testInitialBuildNotCancelable() throws InterruptedException {
		testWorkspaceManager.createTestOnDisk(
			"OtherProject" -> #[
				"Other" -> '''
					export public class Other {
						public mX() {}
					}
				'''
			],
			"MainProject" -> #[
				"Main" -> '''
					import {Other} from "Other";
					new Other().m();
				''',
				CFG_DEPENDENCIES -> '''
					OtherProject
				'''
			]
		);
		startLspServerWithoutWaiting(); // will trigger initial build

		didBuildModuleOther.await();
		// at this point, file Other.n4js has been built completely but initial build is still in progress

		changeNonOpenedFile("Other", "mX" -> "m"); // fix the error
		didPerformCancellation.await(); // continue initial build

		didCompleteInitialBuild.await();
		assertTrue("expected initial build to complete normally (no cancellation, no exceptions)", initialBuildCompletedNormally.get);
		// at this point, the initial build has completed

		// assert successful completion of initial build by ensuring the already fixed error has still shown up:
		assertIssues("Main" -> #[
			"(Error, [1:12 - 1:13], Couldn't resolve reference to IdentifiableElement 'm'.)"
		]);
		didCheckResultOfInitialBuild.countDown();

		joinServerRequests(); // wait for incremental build to complete
		assertNoIssues(); // assert that the incremental build that was delayed by the initial build successfully removed the error
	}	
}
