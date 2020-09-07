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
import java.io.File
import java.util.Collections
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.lsp4j.DidChangeWatchedFilesParams
import org.eclipse.lsp4j.FileChangeType
import org.eclipse.lsp4j.FileEvent
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.eclipse.n4js.ide.xtext.server.QueuedExecutorService
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.xtext.service.AbstractGenericModule
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests certain cases in which the builder must *not* be cancelled.
 */
class IncrementalBuilderNoCancellationTest extends AbstractIncrementalBuilderTest {

	private static final AtomicInteger cancellationCounter = new AtomicInteger(0);

	private static final class CancellationTrackingQueuedExecutorService extends QueuedExecutorService {

		override synchronized cancelAll() {
			cancellationCounter.incrementAndGet();
			super.cancelAll()
		}

		override synchronized cancelAll(Object queueId) {
			cancellationCounter.incrementAndGet();
			super.cancelAll(queueId)
		}
	}

	public static final class IncrementalBuilderNoCancellationTestModule extends AbstractGenericModule {

		def Class<? extends QueuedExecutorService> bindQueuedExecutorService() {
			return CancellationTrackingQueuedExecutorService;
		}
	}

	override protected getOverridingModule() {
		return Optional.of(IncrementalBuilderNoCancellationTestModule);
	}

	@Test
	def void testFileOutsideSourceFolderDoesNotCancelBuild() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				console.log('hello');
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val projectRoot = getProjectRoot();
		val n4jsFileOutsideSourceFolder = new File(projectRoot, "SomeModule.n4js").toFileURI;
		simulateFileChangeAndAssertNoCancellation(n4jsFileOutsideSourceFolder);
	}

	@Test
	def void testOutputFileDoesNotCancelBuild() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				console.log('hello');
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFileOfModuleA = getOutputFile("A").toFileURI;
		simulateFileChangeAndAssertNoCancellation(outputFileOfModuleA);
	}

	@Test
	def void testFileInYarnWorkspaceRootProjectDoesNotCancelBuild() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			"ProjectA" -> #[
				"A" -> '''
					console.log('hello from A');
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			],
			"ProjectB" -> #[
				"B" -> '''
					console.log('hello from B');
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val yarnWorkspaceRoot = getProjectRoot(TestWorkspaceManager.YARN_TEST_PROJECT);
		val n4jsFileInYarnWorkspaceRootProject = new File(yarnWorkspaceRoot, "SomeModule.n4js").toFileURI;
		simulateFileChangeAndAssertNoCancellation(n4jsFileInYarnWorkspaceRootProject);
	}

	@Test
	def void testFileOutsideSourceFolderOfYarnWorkspaceMemberProjectDoesNotCancelBuild() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			"ProjectA" -> #[
				"A" -> '''
					console.log('hello from A');
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			],
			"ProjectB" -> #[
				"B" -> '''
					console.log('hello from B');
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val projectA = getProjectRoot("ProjectA");
		val n4jsFileInYarnWorkspaceRootProject = new File(projectA, "SomeModule.n4js").toFileURI;
		simulateFileChangeAndAssertNoCancellation(n4jsFileInYarnWorkspaceRootProject);
	}

	@Test
	def void testOutputFileOfYarnWorkspaceMemberProjectDoesNotCancelBuild() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			"ProjectA" -> #[
				"A" -> '''
					console.log('hello from A');
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			],
			"ProjectB" -> #[
				"B" -> '''
					console.log('hello from B');
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFileOfModuleA = getOutputFile("ProjectA", "A").toFileURI;
		simulateFileChangeAndAssertNoCancellation(outputFileOfModuleA);
	}

	def private void simulateFileChangeAndAssertNoCancellation(FileURI fileURI) {
		cancellationCounter.set(0);
		val fileEvents = Collections.singletonList(new FileEvent(fileURI.toString(), FileChangeType.Changed));
		val params = new DidChangeWatchedFilesParams(fileEvents);
		languageServer.didChangeWatchedFiles(params);
		joinServerRequests();
		assertNoCancellation();
	}

	def private void assertNoCancellation() {
		val numOfCancellations = cancellationCounter.get();
		assertTrue("expected no cancellation, but " + numOfCancellations + " cancellation occurred", numOfCancellations === 0);
	}
}
