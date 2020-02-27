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

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.cli.helper.N4jscTestLanguageClient;
import org.eclipse.n4js.cli.helper.SystemOutRedirecter;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Module;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.tests.codegen.Project.SourceFolder;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.LanguageInfo;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Abstract base class for LSP-based IDE tests.
 */
abstract public class AbstractIdeTest<T> {
	static final String WORKSPACE_FOLDER = "/test-data";
	static final String PROJECT_NAME = "test-project";
	static final String MODULE_NAME = "MyModule";
	static final String SRC_FOLDER = "src";
	static final String FILE_EXTENSION = "n4js";

	static final SystemOutRedirecter SYSTEM_OUT_REDIRECTER = new SystemOutRedirecter();

	/** Catch outputs on console to an internal buffer */
	@BeforeClass
	static final public void redirectPrintStreams() {
		SYSTEM_OUT_REDIRECTER.set(false);
	}

	/** Reset redirection */
	@AfterClass
	static final public void resetPrintStreams() {
		SYSTEM_OUT_REDIRECTER.unset();
	}

	/** */
	@Inject
	protected IResourceServiceProvider.Registry resourceServerProviderRegistry;
	/** */
	@Inject
	protected UriExtensions uriExtensions;
	/** */
	@Inject
	protected XLanguageServerImpl languageServer;
	/** */
	@Inject
	protected N4jscTestLanguageClient languageClient;
	/** */
	@Inject
	protected LanguageInfo languageInfo;

	/** Deletes the test project in case it exists. */
	@After
	final public void deleteTestProject() {
		File root = getRoot();
		if (root.exists()) {
			FileUtils.deleteFileOrFolder(root);
		}
	}

	/**
	 * This method gets eventually called after calling one of the {@code test()} methods. Overwrite this method to
	 * implement test logic.
	 *
	 * @param root
	 *            root folder of the project
	 * @param project
	 *            project that was created and is used during the test
	 * @param t
	 *            given argument from the {@code test()} method
	 */
	protected void performTest(File root, Project project, T t) throws Exception {
		// implement me
	}

	/** Overwrite this method to change the project type */
	protected ProjectType getProjectType() {
		return ProjectType.VALIDATION;
	}

	/**
	 * Call this method during a test.
	 *
	 * @param contents
	 *            will be added to a default module and project.
	 */
	protected Project test(String contents) throws Exception {
		return test(MODULE_NAME, contents, null);
	}

	/**
	 * Call this method during a test.
	 *
	 * @param contents
	 *            added to a default module and project.
	 * @param t
	 *            will be passed to {@link #performTest(File, Project, Object)}.
	 */
	protected Project test(String contents, T t) throws Exception {
		return test(MODULE_NAME, contents, t);
	}

	/**
	 * Call this method during a test.
	 *
	 * @param moduleName
	 *            used to create a module in a default project.
	 * @param contents
	 *            contents of the {@code moduleName} module.
	 * @param t
	 *            will be passed to {@link #performTest(File, Project, Object)}.
	 */
	protected Project test(String moduleName, String contents, T t) throws Exception {
		moduleName = getModuleNameOrDefault(moduleName);
		Map<String, String> srcFileNameToContents = Collections.singletonMap(moduleName, contents);
		return test(srcFileNameToContents, moduleName, t);
	}

	/**
	 * Same as {@link #test(Map, String, Object)}, but name and content of the modules can be provided as {@link Pair
	 * pairs}.
	 */
	protected Project test(Iterable<Pair<String, String>> moduleNameToContents, String moduleName, T t)
			throws Exception {
		Map<String, String> moduleNameToContentsAsMap = StreamSupport.stream(moduleNameToContents.spliterator(), false)
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
		return test(moduleNameToContentsAsMap, moduleName, t);
	}

	/**
	 * Call this method during a test.
	 *
	 * @param moduleNameToContents
	 *            map that maps module names to their contents.
	 * @param moduleName
	 *            one moduleName of the {@code moduleNameToContents}. Will be opened during the test.
	 * @param t
	 *            will be passed to {@link #performTest(File, Project, Object)}.
	 */
	protected Project test(Map<String, String> moduleNameToContents, String moduleName, T t) throws Exception {
		File root = getRoot();
		Project project = createTestProjectOnDisk(root, moduleNameToContents);
		createInjector();
		startLspServer(root);
		openFile(root, moduleName, moduleNameToContents.get(moduleName));
		performTest(root, project, t);

		return project;
	}

	/** @return the project root {@link File}. */
	protected File getRoot() {
		File root = new File(new File("").getAbsoluteFile(), WORKSPACE_FOLDER);
		return root;
	}

	/** @return instance of {@link StringLSP4J}. */
	protected StringLSP4J getStringLSP4J() {
		return new StringLSP4J(getRoot());
	}

	/**
	 * Same as {@link #createTestProjectOnDisk(Map)}, but name and content of the modules can be provided as {@link Pair
	 * pairs}.
	 */
	protected Project createTestProjectOnDisk(Iterable<Pair<String, String>> moduleNameToContents) {
		Map<String, String> moduleNameToContentsAsMap = StreamSupport.stream(moduleNameToContents.spliterator(), false)
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
		return createTestProjectOnDisk(moduleNameToContentsAsMap);
	}

	/** Same as {@link #createTestProjectOnDisk(File, Map)}, using the {@link #getRoot() default root}. */
	protected Project createTestProjectOnDisk(Map<String, String> moduleNameToContents) {
		return createTestProjectOnDisk(getRoot(), moduleNameToContents);
	}

	/** Creates the default project on file system. */
	protected Project createTestProjectOnDisk(File rootDir, Map<String, String> moduleNameToContents) {
		List<Module> modules = new ArrayList<>();

		for (Map.Entry<String, String> src : moduleNameToContents.entrySet()) {
			String fileName = src.getKey();
			String contents = src.getValue();
			modules.add(new Module(fileName).setContents(contents));
		}

		return createClientProject(rootDir.toPath(), PROJECT_NAME, SRC_FOLDER, modules);
	}

	private Project createClientProject(Path destination, String projectName, String srcFolderName,
			List<Module> clientModules) {

		String vendorId = "VENDOR";
		Project clientProject = new Project(projectName, vendorId, vendorId + "_name", getProjectType());
		Project n4jsRuntimeFake = new Project("n4js-runtime", vendorId, vendorId + "_name",
				ProjectType.RUNTIME_ENVIRONMENT);
		n4jsRuntimeFake.createSourceFolder(srcFolderName);

		clientProject.addProjectDependency(n4jsRuntimeFake);
		SourceFolder sourceFolder = clientProject.createSourceFolder(srcFolderName);
		for (Module clientModule : clientModules) {
			sourceFolder.addModule(clientModule);
		}

		destination.toFile().mkdirs();
		clientProject.create(destination);

		Path nodeModules = destination.resolve(projectName).resolve("node_modules");
		nodeModules.toFile().mkdir();
		n4jsRuntimeFake.create(nodeModules);

		return clientProject;
	}

	/** Creates injector for N4JS */
	protected Injector createInjector() {
		N4jscTestFactory.set(true);
		Injector injector = N4jscFactory.getOrCreateInjector();
		injector.injectMembers(this);
		return injector;
	}

	/** Connects, initializes and waits for the initial build of the test project. */
	protected void startLspServer(File root) {
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
		joinServerRequests();
	}

	/** Opens the given file in the LSP server and waits for the triggered build to finish. */
	protected void openFile(File root, String moduleName, String contents) {
		Assert.assertNotNull(contents);
		FileURI fileURI = getFileUriFromModuleName(root, moduleName);

		TextDocumentItem textDocument = new TextDocumentItem();
		textDocument.setLanguageId(languageInfo.getLanguageName());
		textDocument.setUri(fileURI.toString());
		textDocument.setVersion(1);
		textDocument.setText(toUnixLineSeparator(contents));

		DidOpenTextDocumentParams dotdp = new DidOpenTextDocumentParams();
		dotdp.setTextDocument(textDocument);

		languageServer.didOpen(dotdp);
		joinServerRequests();
	}

	/** Translates a given module name to a file URI used in LSP call data. */
	protected FileURI getFileUriFromModuleName(File root, String moduleName) {
		moduleName = getModuleNameOrDefault(moduleName) + "." + FILE_EXTENSION;
		Path completeFilePath = Path.of(root.toString(), PROJECT_NAME, SRC_FOLDER, moduleName);
		return new FileURI(completeFilePath.toFile());
	}

	private String getModuleNameOrDefault(String moduleName) {
		if (Strings.isNullOrEmpty(moduleName)) {
			return MODULE_NAME;
		}
		return moduleName;
	}

	/** Waits until the LSP server idles. */
	protected void joinServerRequests() {
		languageServer.joinServerRequests();
	}

	/** @see N4jscTestLanguageClient#getAllDiagnostics() */
	protected Collection<Diagnostic> getAllDiagnostics() {
		return languageClient.getAllDiagnostics();
	}

	/** @see N4jscTestLanguageClient#getDiagnostics(FileURI) */
	protected Collection<Diagnostic> getDiagnostics(FileURI uri) {
		return languageClient.getDiagnostics(uri);
	}

	static String toUnixLineSeparator(CharSequence cs) {
		return cs.toString().replaceAll("\r?\n", "\n");
	}
}
