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
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.n4js.ide.tests.server.TestWorkspaceManager
import org.eclipse.n4js.ide.xtext.server.QueuedExecutorService
import org.eclipse.n4js.projectModel.locations.FileURI
import org.eclipse.xtext.service.AbstractGenericModule
import org.junit.Test

import static org.junit.Assert.*

/**
 * Tests certain cases in which the builder must *not* be cancelled (i.e. changes to files
 * not contained in an N4JS source folder).
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
	def void testChangeInFileOutsideSourceFolderDoesNotCancelBuild() {
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
	def void testChangeInOutputFileDoesNotCancelBuild() {
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
	def void testChangeInFileInYarnWorkspaceRootProjectDoesNotCancelBuild() {
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
	def void testChangeInFileOutsideSourceFolderOfYarnWorkspaceMemberProjectDoesNotCancelBuild() {
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
	def void testChangeInOutputFileOfYarnWorkspaceMemberProjectDoesNotCancelBuild() {
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

	@Test
	def void testChangeInFileOfPlainJsProjectDoesNotCancelBuild() {
		testWorkspaceManager.createTestOnDisk(
			TestWorkspaceManager.CFG_NODE_MODULES + "n4js-runtime" -> null,
			"ProjectN4JS" -> #[
				"A" -> '''
					console.log('hello from A');
				''',
				TestWorkspaceManager.CFG_DEPENDENCIES -> "n4js-runtime"
			],
			"ProjectPlain" -> #[
				TestWorkspaceManager.CFG_SOURCE_FOLDER -> ".",
				TestWorkspaceManager.PACKAGE_JSON -> '''
					{
						"name": "some-pkg",
						"version": "0.0.1",
						"main": "MainModule.js"
					}
				'''
			]
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val projectPlain = getProjectRoot("ProjectPlain");
		val n4jsFileInYarnWorkspaceRootProject = new File(projectPlain, "SomeModule.js").toFileURI;
		simulateFileChangeAndAssertNoCancellation(n4jsFileInYarnWorkspaceRootProject);
	}

	/** This test method is only included to ensure that dependency injection is correctly set up. */
	@Test
	def void ensureCorrectSetup() {
		testWorkspaceManager.createTestProjectOnDisk(
			"A" -> '''
				console.log('hello');
			'''
		);
		startAndWaitForLspServer();
		assertNoIssues();

		val outputFileOfModuleA = getFileURIFromModuleName("A");

		cancellationCounter.set(0);
		sendDidChangeWatchedFiles(outputFileOfModuleA);
		joinServerRequests();
		assertTrue(cancellationCounter.get() > 0);
	}

	def private void simulateFileChangeAndAssertNoCancellation(FileURI fileURI) {
		cancellationCounter.set(0);
		sendDidChangeWatchedFiles(fileURI);
		joinServerRequests(); // we don't expect the builder to do anything at this point; but if it incorrectly does something we want to wait for it to finish
		assertNoCancellation();
	}

	def private void assertNoCancellation() {
		val numOfCancellations = cancellationCounter.get();
		assertTrue("expected no cancellation, but " + numOfCancellations + " cancellation occurred", numOfCancellations === 0);
	}
}
