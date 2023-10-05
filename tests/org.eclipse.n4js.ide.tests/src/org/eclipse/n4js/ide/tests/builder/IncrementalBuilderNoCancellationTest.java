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
package org.eclipse.n4js.ide.tests.builder;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.n4js.ide.tests.helper.server.TestWorkspaceManager;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.n4js.xtext.ide.server.QueuedExecutorService;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.inject.Module;

/**
 * Tests certain cases in which the builder must *not* be cancelled (i.e. changes to files not contained in an N4JS
 * source folder).
 */

public class IncrementalBuilderNoCancellationTest extends AbstractIncrementalBuilderTest {

	private static final AtomicInteger cancellationCounter = new AtomicInteger(0);

	private static final class CancellationTrackingQueuedExecutorService extends QueuedExecutorService {

		@Override
		synchronized public void cancelAll() {
			cancellationCounter.incrementAndGet();
			super.cancelAll();
		}

		@Override
		synchronized public void cancelAll(Object queueId) {
			cancellationCounter.incrementAndGet();
			super.cancelAll(queueId);
		}
	}

	public static final class IncrementalBuilderNoCancellationTestModule extends AbstractGenericModule {
		public Class<? extends QueuedExecutorService> bindQueuedExecutorService() {
			return CancellationTrackingQueuedExecutorService.class;
		}
	}

	@Override
	protected Optional<Class<? extends Module>> getOverridingModule() {
		return Optional.of(IncrementalBuilderNoCancellationTestModule.class);
	}

	@Test
	public void testChangeInFileOutsideSourceFolderDoesNotCancelBuild() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							console.log('hello');
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		File projectRoot = getProjectRoot();
		FileURI n4jsFileOutsideSourceFolder = toFileURI(new File(projectRoot, "SomeModule.n4js"));
		simulateFileChangeAndAssertNoCancellation(n4jsFileOutsideSourceFolder);
	}

	@Test
	public void testChangeInOutputFileDoesNotCancelBuild() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							console.log('hello');
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI outputFileOfModuleA = toFileURI(getOutputFile("A"));
		simulateFileChangeAndAssertNoCancellation(outputFileOfModuleA);
	}

	@Test
	public void testChangeInFileInYarnWorkspaceRootProjectDoesNotCancelBuild() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectA", Map.of(
						"A", """
									console.log('hello from A');
								"""),
				"ProjectB", Map.of(
						"B", """
									console.log('hello from B');
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		File yarnWorkspaceRoot = getProjectRoot(TestWorkspaceManager.YARN_TEST_PROJECT);
		FileURI n4jsFileInYarnWorkspaceRootProject = toFileURI(new File(yarnWorkspaceRoot, "SomeModule.n4js"));
		simulateFileChangeAndAssertNoCancellation(n4jsFileInYarnWorkspaceRootProject);
	}

	@Test
	public void testChangeInFileOutsideSourceFolderOfYarnWorkspaceMemberProjectDoesNotCancelBuild() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectA", Map.of(
						"A", """
									console.log('hello from A');
								"""),
				"ProjectB", Map.of(
						"B", """
									console.log('hello from B');
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		File projectA = getProjectRoot("ProjectA");
		FileURI n4jsFileProjectA = toFileURI(new File(projectA, "SomeModule.n4js"));
		simulateFileChangeAndAssertNoCancellation(n4jsFileProjectA);
	}

	@Test
	public void testChangeInOutputFileOfYarnWorkspaceMemberProjectDoesNotCancelBuild() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectA", Map.of(
						"A", """
									console.log('hello from A');
								"""),
				"ProjectB", Map.of(
						"B", """
									console.log('hello from B');
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI outputFileOfModuleA = toFileURI(getOutputFile("ProjectA", "A"));
		simulateFileChangeAndAssertNoCancellation(outputFileOfModuleA);
	}

	@Test
	public void testChangeInFileOfPlainJsProjectDoesNotCancelBuild() {
		testWorkspaceManager.createTestOnDisk(Map.of(
				"ProjectN4JS", Map.of(
						"A", """
									console.log('hello from A');
								"""),
				"ProjectPlain", Map.of(
						CFG_SOURCE_FOLDER, ".",
						PACKAGE_JSON, """
									{
										"name": "ProjectPlain",
										"version": "0.0.1",
										"main": "MainModule.js"
									}
								""")));
		startAndWaitForLspServer();
		assertNoIssues();

		File projectPlain = getProjectRoot("ProjectPlain");
		FileURI jsFileInProjectPlain = toFileURI(new File(projectPlain, "SomeModule.js"));
		simulateFileChangeAndAssertNoCancellation(jsFileInProjectPlain);
	}

	/** This test method is only included to ensure that dependency injection is correctly set up. */
	@Test
	public void ensureCorrectSetup() {
		testWorkspaceManager.createTestProjectOnDisk(Map.of(
				"A", """
							console.log('hello');
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		FileURI outputFileOfModuleA = getFileURIFromModuleName("A");

		cancellationCounter.set(0);
		sendDidChangeWatchedFiles(outputFileOfModuleA);
		joinServerRequests();
		assertTrue(cancellationCounter.get() > 0);
	}

	private void simulateFileChangeAndAssertNoCancellation(FileURI fileURI) {
		cancellationCounter.set(0);
		sendDidChangeWatchedFiles(fileURI);
		joinServerRequests(); // we don't expect the builder to do anything at this point; but if it incorrectly does
								// something we want to wait for it to finish
		assertNoCancellation();
	}

	private void assertNoCancellation() {
		int numOfCancellations = cancellationCounter.get();
		assertTrue("expected no cancellation, but " + numOfCancellations + " cancellation occurred",
				numOfCancellations == 0);
	}
}
