/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.n4js.ide.tests.server;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureInformation;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.cli.compiler.N4jscLanguageClient;
import org.eclipse.n4js.cli.helper.SystemOutRedirecter;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Module;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.tests.codegen.Project.SourceFolder;
import org.eclipse.xtext.LanguageInfo;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.testing.SignatureHelpConfiguration;
import org.eclipse.xtext.testing.TextDocumentConfiguration;
import org.eclipse.xtext.util.Pair;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Signature help test class
 */
public class SignatureHelpTest {
	static final String WORKSPACE_FOLDER = "/test-data";
	static final String PROJECT_NAME = "test-project";
	static final String MODULE_NAME = "MyModule";
	static final String SRC_FOLDER = "src";
	static final String FILE_EXTENSION = "n4js";

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

	@Inject
	IResourceServiceProvider.Registry resourceServerProviderRegistry;
	@Inject
	UriExtensions uriExtensions;
	@Inject
	XLanguageServerImpl languageServer;
	@Inject
	N4jscLanguageClient languageClient;

	List<Pair<String, Object>> notifications = new ArrayList<>();
	LanguageInfo languageInfo;
	File root;
	boolean hierarchicalDocumentSymbolSupport = false;

	private void myTestSignatureHelp(SignatureHelpConfiguration shc)
			throws InterruptedException, ExecutionException {

		root = new File(new File("").getAbsoluteFile(), WORKSPACE_FOLDER);

		if (Strings.isNullOrEmpty(shc.getFilePath())) {
			shc.setFilePath(MODULE_NAME + "." + FILE_EXTENSION);
		}
		Map<String, String> srcFileNameToContents = Collections.singletonMap(shc.getFilePath(), shc.getModel());
		createTmpProjectOnDisk(root, srcFileNameToContents);

		N4jscTestFactory.set(true);
		Injector injector = N4jscFactory.getOrCreateInjector();
		injector.injectMembers(this);

		Object resourceServiceProvider = resourceServerProviderRegistry.getExtensionToFactoryMap().get(FILE_EXTENSION);
		if (resourceServiceProvider instanceof IResourceServiceProvider) {
			languageInfo = ((IResourceServiceProvider) resourceServiceProvider).get(LanguageInfo.class);
		}

		ClientCapabilities capabilities = new ClientCapabilities();
		WorkspaceClientCapabilities wcc = new WorkspaceClientCapabilities();
		wcc.setExecuteCommand(new ExecuteCommandCapabilities());
		capabilities.setWorkspace(wcc);
		InitializeParams initParams = new InitializeParams();
		initParams.setCapabilities(capabilities);
		initParams.setRootUri(new FileURI(new File(root, PROJECT_NAME)).toString());

		languageServer.connect(languageClient);
		languageServer.initialize(initParams);
		languageServer.initialized(null);
		languageServer.joinInitBuildFinished();

		initializeContext(shc);

		TextDocumentPositionParams textDocumentPositionParams = new TextDocumentPositionParams();
		String completeFileUri = getFileUriFromModuleName(shc.getFilePath()).toString();
		textDocumentPositionParams.setTextDocument(new TextDocumentIdentifier(completeFileUri));
		textDocumentPositionParams.setPosition(new Position(shc.getLine(), shc.getColumn()));
		CompletableFuture<SignatureHelp> signatureHelpFuture = languageServer.signatureHelp(textDocumentPositionParams);

		SignatureHelp signatureHelp = signatureHelpFuture.get();
		if (shc.getAssertSignatureHelp() != null) {
			shc.getAssertSignatureHelp().apply(signatureHelp);
		} else {
			String actualSignatureHelp = toExpectation(signatureHelp);
			assertEquals(shc.getExpectedSignatureHelp().trim(), actualSignatureHelp.trim());
		}
	}

	private FileURI getFileUriFromModuleName(String moduleName) {
		Path completeFilePath = Path.of(root.toString(), PROJECT_NAME, SRC_FOLDER, moduleName);
		return new FileURI(completeFilePath.toFile());

	}

	private void createTmpProjectOnDisk(File rootDir, Map<String, String> srcFileNameToContents) {
		List<Module> modules = new ArrayList<>();

		for (Map.Entry<String, String> src : srcFileNameToContents.entrySet()) {
			String fileName = src.getKey();
			String contents = src.getValue();
			modules.add(new Module(fileName).setContents(contents));
		}

		createClientProject(rootDir.toPath(), PROJECT_NAME, SRC_FOLDER, modules);
	}

	private void createClientProject(Path destination, String projectName, String srcFolderName,
			List<Module> clientModules) {

		String vendorId = "VENDOR";
		Project clientProject = new Project(projectName, vendorId, vendorId + "_name", ProjectType.VALIDATION);
		SourceFolder sourceFolder = clientProject.createSourceFolder(srcFolderName);
		for (Module clientModule : clientModules) {
			sourceFolder.addModule(clientModule);
		}
		clientProject.create(destination);
	}

	private void initializeContext(TextDocumentConfiguration tdc) {
		String model = tdc.getModel();
		Assert.assertNotNull(model);
		FileURI fileURI = getFileUriFromModuleName(tdc.getFilePath());
		open(fileURI, languageInfo.getLanguageName(), model);
	}

	private void open(FileURI fileUri, String langaugeId, String model) {
		TextDocumentItem textDocument = new TextDocumentItem();
		textDocument.setLanguageId(langaugeId);
		textDocument.setUri(fileUri.toString());
		textDocument.setVersion(1);
		textDocument.setText(toUnixLineSeparator(model));

		DidOpenTextDocumentParams dotdp = new DidOpenTextDocumentParams();
		dotdp.setTextDocument(textDocument);

		languageServer.didOpen(dotdp);
		waitForRequestsDone();
	}

	static String toUnixLineSeparator(CharSequence cs) {
		return cs.toString().replaceAll("\r?\n", "\n");
	}

	private String toExpectation(SignatureHelp signatureHelp) {
		Integer activeSignature = signatureHelp.getActiveSignature();
		List<SignatureInformation> signatures = signatureHelp.getSignatures();

		if (signatures.size() == 0) {
			Assert.assertNull(
					"Signature index is expected to be null when no signatures are available. Was: " + activeSignature,
					activeSignature);
			return "<empty>";
		}
		Assert.assertNotNull("Active signature index must not be null when signatures are available.", activeSignature);

		Integer activeParameter = signatureHelp.getActiveParameter();
		String param = (activeParameter == null) ? "<empty>"
				: signatures.get(activeSignature).getParameters().get(activeParameter).getLabel().getLeft();

		String allSignatureStr = signatures.stream().map(s -> s.getLabel()).reduce("", (a, b) -> a + " | " + b);
		return allSignatureStr + param;
	}

	/** Example test for signature help */
	@Test
	public void test() throws InterruptedException, ExecutionException {
		SignatureHelpConfiguration shc = new SignatureHelpConfiguration();
		shc.setModel("class A { foo(a: A) { } } class Main { main() { foo(); } }");
		shc.setLine(0);
		shc.setColumn("class A { foo(a: A) { } } class Main { main() { fo".length());
		shc.setExpectedSignatureHelp("<empty>");

		myTestSignatureHelp(shc);
	}

	private void waitForRequestsDone() {
		ExecuteCommandParams cmdUnknownParams = new ExecuteCommandParams("unknown.command", Collections.emptyList());
		languageServer.executeCommand(cmdUnknownParams).join();
	}
}
