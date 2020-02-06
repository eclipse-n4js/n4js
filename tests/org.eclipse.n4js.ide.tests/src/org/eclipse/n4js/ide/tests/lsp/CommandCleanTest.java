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

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.cli.helper.N4jscTestLanguageClient;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.junit.Test;

/**
 *
 */
public class CommandCleanTest {
	static final String PROBANDS_NAME = "probands";
	static final String PROJECT_NAME = "lsp-command-clean";
	static final String FILE_NAME = "lsp-command-clean/src/Module.n4js";

	@Test
	public void testCommandClean() {

		N4jscTestFactory.set(true);
		XLanguageServerImpl languageServer = N4jscTestFactory.getLanguageServer();
		N4jscTestLanguageClient client = (N4jscTestLanguageClient) N4jscTestFactory.getLanguageClient();

		Path prjPath = Path.of(PROBANDS_NAME, PROJECT_NAME);
		prjPath.toAbsolutePath();
		FileURI prjURI = new FileURI(prjPath.toFile());

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
		Collection<String> errors = client.getErrors(FILE_NAME);
		assertEquals(1, errors.size());
		assertEquals("  ERR 1:7      Test Issue", errors.iterator().next());

		// send command under test
		client.resetCounters();
		ExecuteCommandParams cmdCleanParams = new ExecuteCommandParams("n4js.clean", Collections.emptyList());
		CompletableFuture<Object> future = languageServer.executeCommand(cmdCleanParams);
		future.join();

		// wait for previous command to finish
		ExecuteCommandParams cmdUnknownParams = new ExecuteCommandParams("unknown.command", Collections.emptyList());
		languageServer.executeCommand(cmdUnknownParams).join();

		// evaluate
		Collection<String> errors2 = client.getErrors(FILE_NAME);
		assertEquals(0, errors2.size());
		System.out.println("!");
	}

}
