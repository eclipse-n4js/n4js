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
package org.eclipse.n4js.ide.tests.server;

import static org.eclipse.n4js.ide.tests.server.TestWorkspaceManager.DEFAULT_PROJECT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.After;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.inject.Injector;

/**
 * Tests the client command 'n4js.rebuild'
 */
public class CommandRebuildTest extends AbstractStructuredIdeTest<Void> {
	static final String PROBANDS_NAME = "probands";
	static final String PROJECT_STATE_NAME = N4JSGlobals.N4JS_PROJECT_STATE;

	static final long FILE_TIME_MILLISECONDS = 8472000;

	Path prjStatePath;
	Path genFileStatePath;

	/** remove generated files */
	@After
	public void cleanup() {
		languageServer.getFrontend().clean();

		// wait for previous command to finish
		joinServerRequests();
	}

	@Override
	protected Injector createInjector() {
		Injector injector = super.createInjector();
		ProjectStatePersisterConfig persisterConfig = injector.getInstance(ProjectStatePersisterConfig.class);
		persisterConfig.setDeleteState(false);
		persisterConfig.setWriteToDisk(true);
		return injector;
	}

	@Override
	protected void performTest(Project project, String moduleName, Void nothing) throws Exception {
		// check pre-state
		assertNoIssues();

		prjStatePath = getFileURIFromModuleName(DEFAULT_PROJECT_NAME + "/" + PROJECT_STATE_NAME).toFileSystemPath();
		genFileStatePath = getFileURIFromModuleName(moduleName + ".js").toFileSystemPath();

		setFileCreationDate(prjStatePath);
		setFileCreationDate(genFileStatePath);
	}

	/** Expectation is that files '.n4js.projectstate' and 'src-gen/Module.js' are changed due to rebuild action. */
	@Test
	public void testCommandRebuild() throws Exception {
		test("class A { foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }");

		// send command under test
		ExecuteCommandParams cmdCleanParams = new ExecuteCommandParams(N4JSCommandService.N4JS_REBUILD,
				Collections.emptyList());
		CompletableFuture<Object> future = languageServer.executeCommand(cmdCleanParams);
		future.join();

		// wait for previous command to finish
		joinServerRequests();

		// evaluate
		assertNoIssues();
		FileTime prjStateTime = Files.readAttributes(prjStatePath, BasicFileAttributes.class).lastModifiedTime();
		FileTime genFileTime = Files.readAttributes(genFileStatePath, BasicFileAttributes.class).lastModifiedTime();
		assertNotEquals(FILE_TIME_MILLISECONDS, prjStateTime.toMillis());
		assertNotEquals(FILE_TIME_MILLISECONDS, genFileTime.toMillis());
	}

	/** Expectation is that files '.n4js.projectstate' and 'src-gen/Module.js' are NOT changed. */
	@Test
	public void testRebuildWithoutClean() throws Exception {
		test("class A { foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }");

		// send command under test
		languageServer.getFrontend().reinitWorkspace();

		// wait for previous command to finish
		joinServerRequests();

		// evaluate
		assertNoIssues();
		FileTime prjStateTime = Files.readAttributes(prjStatePath, BasicFileAttributes.class).lastModifiedTime();
		FileTime genFileTime = Files.readAttributes(genFileStatePath, BasicFileAttributes.class).lastModifiedTime();

		assertEquals(FILE_TIME_MILLISECONDS, prjStateTime.toMillis());
		assertEquals(FILE_TIME_MILLISECONDS, genFileTime.toMillis());
	}

	/**
	 * The build triggered by the rebuild command must send 'publishDiagnostics' events even for those resources that do
	 * not contain any issues, to ensure that any obsolete issues for those resources that might exist on client side
	 * are removed. Normally such obsolete issues should not exist (not at start up time, because LSP clients do not
	 * serialize issues; and not when rebuild is triggered manually, because an incremental build should have removed
	 * those issues); however, in case of bugs in the incremental builder or other special circumstances this may
	 * happen.
	 */
	@Test
	public void testPublishDiagnosticsSentForModuleWithoutIssues() {
		testWorkspaceManager.createTestProjectOnDisk(Pair.of("Main", "let x: string = 42; x;"));
		startAndWaitForLspServer();

		assertIssues(Pair.of("Main", Lists.newArrayList(
				"(Error, [0:16 - 0:18], int is not a subtype of string.)")));

		// fix the error on disk, but don't let the LSP server know (to avoid incremental build)
		changeFileOnDiskWithoutNotification("Main", Pair.of("string", "number"));

		joinServerRequests();
		assertIssues(Pair.of("Main", Lists.newArrayList(
				"(Error, [0:16 - 0:18], int is not a subtype of string.)")));

		// send command under test
		ExecuteCommandParams params = new ExecuteCommandParams(N4JSCommandService.N4JS_REBUILD,
				Collections.emptyList());
		languageServer.executeCommand(params).join();

		// wait for previous command to finish
		joinServerRequests();

		assertNoIssues();
	}

	private void setFileCreationDate(Path filePath) throws IOException {
		BasicFileAttributeView attributes = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
		FileTime time = FileTime.fromMillis(FILE_TIME_MILLISECONDS);
		attributes.setTimes(time, time, time);

		FileTime fileTime = Files.readAttributes(filePath, BasicFileAttributes.class).lastModifiedTime();
		assertEquals(FILE_TIME_MILLISECONDS, fileTime.toMillis());
	}

}
