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
package org.eclipse.n4js.ide.tests.lsp;

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

import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.cli.helper.N4jscTestLanguageClient;
import org.eclipse.n4js.cli.helper.SystemOutRedirecter;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Injector;

/**
 * Tests the client command 'n4js.rebuild'
 */
public class CommandRebuildTest {
	static final String PROBANDS_NAME = "probands";
	static final String PROJECT_NAME = "lsp-command-rebuild";
	static final String PROJECT_STATE_NAME = ".n4js.projectstate";
	static final String GEN_FILE_NAME = "src-gen/Module.js";

	static final long FILE_TIME_MILLISECONDS = 8472000;

	static final SystemOutRedirecter SYSTEM_OUT_REDIRECTER = new SystemOutRedirecter();

	/** Catch outputs on console to an internal buffer */
	@BeforeClass
	static public void redirectPrintStreams() {
		SYSTEM_OUT_REDIRECTER.set(false);
	}

	/** Reset redirection */
	@AfterClass
	static public void resetPrintStreams() {
		SYSTEM_OUT_REDIRECTER.unset();
	}

	final Path prjPath = Path.of(PROBANDS_NAME, PROJECT_NAME).toAbsolutePath();
	final Path prjStatePath = Path.of(PROBANDS_NAME, PROJECT_NAME, PROJECT_STATE_NAME).toAbsolutePath();
	final Path genFileStatePath = Path.of(PROBANDS_NAME, PROJECT_NAME, GEN_FILE_NAME).toAbsolutePath();
	final FileURI prjURI = new FileURI(prjPath.toFile());

	private N4jscTestLanguageClient client;
	private XLanguageServerImpl languageServer;

	/** Sets up the language server and client */
	@Before
	public void init() throws IOException {
		// init
		N4jscTestFactory.set(true);
		languageServer = N4jscFactory.getLanguageServer();
		client = (N4jscTestLanguageClient) N4jscFactory.getLanguageClient();
		Injector injector = N4jscFactory.getOrCreateInjector();
		ProjectStatePersisterConfig persisterConfig = injector.getInstance(ProjectStatePersisterConfig.class);
		persisterConfig.setDeleteState(false);
		persisterConfig.setWriteToDisk(true);

		ClientCapabilities capabilities = new ClientCapabilities();
		WorkspaceClientCapabilities wcc = new WorkspaceClientCapabilities();
		wcc.setExecuteCommand(new ExecuteCommandCapabilities());
		capabilities.setWorkspace(wcc);
		InitializeParams initParams = new InitializeParams();
		initParams.setCapabilities(capabilities);
		initParams.setRootUri(prjURI.toString());

		languageServer.connect(client);
		languageServer.initialize(initParams);
		languageServer.initialized(null);
		languageServer.joinInitBuildFinished();

		// check pre-state
		assertEquals(0, client.getErrorsCount());
		setFileCreationDate(prjStatePath);
		setFileCreationDate(genFileStatePath);

		// wait two seconds because file time does not consider milliseconds
		client.resetCounters();
	}

	/** remove generated files */
	@After
	public void cleanup() {
		languageServer.clean();

		// wait for previous command to finish
		waitForRequestsDone();
	}

	/** Expectation is that files '.n4js.projectstate' and 'src-gen/Module.js' are changed due to rebuild action. */
	@Test
	public void testCommandRebuild() throws IOException {

		// send command under test
		ExecuteCommandParams cmdCleanParams = new ExecuteCommandParams("n4js.rebuild", Collections.emptyList());
		CompletableFuture<Object> future = languageServer.executeCommand(cmdCleanParams);
		future.join();

		// wait for previous command to finish
		waitForRequestsDone();

		// evaluate
		assertEquals(0, client.getErrorsCount());
		FileTime prjStateTime = Files.readAttributes(prjStatePath, BasicFileAttributes.class).creationTime();
		FileTime genFileTime = Files.readAttributes(genFileStatePath, BasicFileAttributes.class).creationTime();
		assertNotEquals(FILE_TIME_MILLISECONDS, prjStateTime.toMillis());
		assertNotEquals(FILE_TIME_MILLISECONDS, genFileTime.toMillis());
	}

	/** Expectation is that files '.n4js.projectstate' and 'src-gen/Module.js' are NOT changed. */
	@Test
	public void testRebuildWithoutClean() throws IOException {

		// send command under test
		languageServer.reinitWorkspace();

		// wait for previous command to finish
		waitForRequestsDone();

		// evaluate
		assertEquals(0, client.getErrorsCount());
		FileTime prjStateTime = Files.readAttributes(prjStatePath, BasicFileAttributes.class).creationTime();
		FileTime genFileTime = Files.readAttributes(genFileStatePath, BasicFileAttributes.class).creationTime();
		assertEquals(FILE_TIME_MILLISECONDS, prjStateTime.toMillis());
		assertEquals(FILE_TIME_MILLISECONDS, genFileTime.toMillis());
	}

	private void setFileCreationDate(Path filePath) throws IOException {
		BasicFileAttributeView attributes = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
		FileTime time = FileTime.fromMillis(FILE_TIME_MILLISECONDS);
		attributes.setTimes(time, time, time);
	}

	private void waitForRequestsDone() {
		ExecuteCommandParams cmdUnknownParams = new ExecuteCommandParams("unknown.command", Collections.emptyList());
		languageServer.executeCommand(cmdUnknownParams).join();
	}
}
