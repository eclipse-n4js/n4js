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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.tests.codegen.Project;
import org.junit.After;
import org.junit.Test;

import com.google.inject.Injector;

/**
 * Tests the client command 'n4js.rebuild'
 */
public class CommandRebuildTest extends AbstractIdeTest<Void> {
	static final String PROBANDS_NAME = "probands";
	static final String PROJECT_STATE_NAME = ".n4js.projectstate";

	static final long FILE_TIME_MILLISECONDS = 8472000;

	Path prjStatePath;
	Path genFileStatePath;

	@Override
	protected ProjectType getProjectType() {
		return ProjectType.LIBRARY;
	}

	/** remove generated files */
	@After
	public void cleanup() {
		languageServer.clean();

		// wait for previous command to finish
		waitForRequestsDone();
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
	protected void performTest(File root, Project project, Void nothing) throws Exception {
		// check pre-state
		assertEquals(0, languageClient.getErrorsCount());

		String moduleName = project.sourceFolders.get(0).modules.get(0).name;
		Path prjPath = root.toPath().resolve(PROJECT_NAME).toAbsolutePath();
		prjStatePath = prjPath.resolve(PROJECT_STATE_NAME);
		genFileStatePath = prjPath.resolve(project.outputFolder).resolve(moduleName + ".js");

		setFileCreationDate(prjStatePath);
		setFileCreationDate(genFileStatePath);

		languageClient.resetCounters();
	}

	/** Expectation is that files '.n4js.projectstate' and 'src-gen/Module.js' are changed due to rebuild action. */
	@Test
	public void testCommandRebuild() throws Exception {
		test("class A { foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }");

		// send command under test
		ExecuteCommandParams cmdCleanParams = new ExecuteCommandParams("n4js.rebuild", Collections.emptyList());
		CompletableFuture<Object> future = languageServer.executeCommand(cmdCleanParams);
		future.join();

		// wait for previous command to finish
		waitForRequestsDone();

		// evaluate
		assertEquals(0, languageClient.getErrorsCount());
		FileTime prjStateTime = Files.readAttributes(prjStatePath, BasicFileAttributes.class).creationTime();
		FileTime genFileTime = Files.readAttributes(genFileStatePath, BasicFileAttributes.class).creationTime();
		assertNotEquals(FILE_TIME_MILLISECONDS, prjStateTime.toMillis());
		assertNotEquals(FILE_TIME_MILLISECONDS, genFileTime.toMillis());
	}

	/** Expectation is that files '.n4js.projectstate' and 'src-gen/Module.js' are NOT changed. */
	@Test
	public void testRebuildWithoutClean() throws Exception {
		test("class A { foo(a: A) { } } class Main { main(a: A) { a.foo(null); } }");

		// send command under test
		languageServer.reinitWorkspace();

		// wait for previous command to finish
		waitForRequestsDone();

		// evaluate
		assertEquals(0, languageClient.getErrorsCount());
		FileTime prjStateTime = Files.readAttributes(prjStatePath, BasicFileAttributes.class).creationTime();
		FileTime genFileTime = Files.readAttributes(genFileStatePath, BasicFileAttributes.class).creationTime();
		assertEquals(FILE_TIME_MILLISECONDS, prjStateTime.toMillis());
		assertEquals(FILE_TIME_MILLISECONDS, genFileTime.toMillis());
	}

	private void setFileCreationDate(Path filePath) throws IOException {
		BasicFileAttributeView attributes = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
		FileTime time = FileTime.fromMillis(FILE_TIME_MILLISECONDS);
		attributes.setTimes(time, time, time);

		FileTime prjStateTime = Files.readAttributes(filePath, BasicFileAttributes.class).creationTime();
		assertEquals(FILE_TIME_MILLISECONDS, prjStateTime.toMillis());
	}

}
