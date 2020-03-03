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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.cli.helper.SystemOutRedirecter;
import org.eclipse.n4js.ide.tests.client.IdeTestLanguageClient;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Module;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.tests.codegen.Project.SourceFolder;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.LanguageInfo;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Abstract base class for LSP-based IDE tests.
 */
abstract public class AbstractIdeTest {

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
	protected XWorkspaceManager workspaceManager;
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
	protected IdeTestLanguageClient languageClient;
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
		languageClient.clear();
	}

	/** Overwrite this method to change the project type */
	protected ProjectType getProjectType() {
		return ProjectType.VALIDATION;
	}

	/** @return the workspace root folder as a {@link File}. */
	protected File getRoot() {
		File root = new File(new File("").getAbsoluteFile(), WORKSPACE_FOLDER);
		return root;
	}

	/** @return instance of {@link StringLSP4J}. */
	protected StringLSP4J getStringLSP4J() {
		return new StringLSP4J(getRoot());
	}

	/**
	 * Call this method after creating the test project(s) to start the LSP server and perform other initializations
	 * required for testing. Afterwards, actual testing can begin, e.g. checking issues with {@link #assertNoIssues()}
	 * or modifying files and rebuilding.
	 */
	protected void startAndWaitForLspServer() {
		createInjector();
		startLspServer(getRoot());
		joinServerRequests();
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

	/** Like {@link #cleanBuildWithoutWait()}, but {@link #joinServerRequests() waits} for LSP server to finish. */
	protected void cleanBuildAndWait() {
		cleanBuildWithoutWait();
		joinServerRequests();
	}

	/** Cleans entire workspace without waiting for LSP server to finish. */
	protected void cleanBuildWithoutWait() {
		languageClient.clear();
		languageServer.clean();
		languageServer.reinitWorkspace();
	}

	/**
	 * Same as {@link #createTestProjectOnDisk(Map)}, but name and content of the modules can be provided as
	 * {@link Pair}s.
	 */
	protected Project createTestProjectOnDisk(
			@SuppressWarnings("unchecked") Pair<String, String>... moduleNameToContents) {
		Map<String, String> moduleNameToContentsAsMap = Stream.of(moduleNameToContents)
				.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
		return createTestProjectOnDisk(moduleNameToContentsAsMap);
	}

	/** Creates the default project on file system. */
	protected Project createTestProjectOnDisk(Map<String, String> moduleNameToContents) {
		List<Module> modules = new ArrayList<>();

		for (Map.Entry<String, String> src : moduleNameToContents.entrySet()) {
			String fileName = src.getKey();
			String contents = src.getValue();
			modules.add(new Module(fileName).setContents(contents));
		}

		return createClientProject(getRoot().toPath(), PROJECT_NAME, SRC_FOLDER, modules);
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

	/** Same as {@link #openFile(String, String)}, but content is read from disk. */
	protected void openFile(String moduleName) throws IOException {
		FileURI fileURI = getFileUriFromModuleName(moduleName);
		Path filePath = fileURI.toJavaIoFile().toPath();
		String content = Files.readString(filePath);
		openFile(moduleName, content);
	}

	/** Opens the given file in the LSP server and waits for the triggered build to finish. */
	protected void openFile(String moduleName, String contents) {
		Assert.assertNotNull(contents);
		FileURI fileURI = getFileUriFromModuleName(moduleName);

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

	/**
	 * Change a non-opened file on disk and notify the LSP server.
	 * <p>
	 * Use method {@link #changeOpenedFile(String, Pair...)} instead if the file was previously opened with one of the
	 * {@link #openFile(String, String) #openFile()} methods.
	 */
	protected void changeNonOpenedFile(String moduleName,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) throws IOException {
		FileURI fileURI = getFileUriFromModuleName(moduleName);
		// 1) change on disk
		changeFileOnDiskWithoutNotification(fileURI, replacements);
		// 2) notify LSP server
		List<FileEvent> fileEvents = Collections.singletonList(
				new FileEvent(fileURI.toString(), FileChangeType.Changed));
		DidChangeWatchedFilesParams params = new DidChangeWatchedFilesParams(fileEvents);
		languageServer.didChangeWatchedFiles(params);
	}

	/**
	 * Change an opened file on disk and notify the LSP server.
	 * <p>
	 * Use method {@link #changeNonOpenedFile(String, Pair...)} instead if the file was *not* previously opened with one
	 * of the {@link #openFile(String, String) #openFile()} methods.
	 */
	protected void changeOpenedFile(String moduleName,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) throws IOException {
		FileURI fileURI = getFileUriFromModuleName(moduleName);
		Path filePath = fileURI.toJavaIoFile().toPath();
		String oldContent = Files.readString(filePath);
		// 1) change on disk
		changeFileOnDiskWithoutNotification(fileURI, replacements);
		// 2) notify LSP server
		VersionedTextDocumentIdentifier docId = new VersionedTextDocumentIdentifier(fileURI.toString(), 1);
		List<TextDocumentContentChangeEvent> changes = replacementsToChangeEvents(oldContent, replacements);
		DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(docId, changes);
		languageServer.didChange(params);
	}

	/**
	 * Same as {@link #changeFileOnDiskWithoutNotification(FileURI, Pair...)}, accepting a module name instead of a file
	 * URI.
	 */
	protected void changeFileOnDiskWithoutNotification(String moduleName,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) throws IOException {
		FileURI fileURI = getFileUriFromModuleName(moduleName);
		changeFileOnDiskWithoutNotification(fileURI, replacements);
	}

	/**
	 * Changes a file on disk by applying the given replacements without notifying the LSP server.
	 *
	 * @param fileURI
	 *            URI of the file to change.
	 * @param replacements
	 *            for each pair P, the first (and only the first!) occurrence of P's key in the content of the file
	 *            denoted by <code>fileURI</code> will be replaced by P's value.
	 */
	protected void changeFileOnDiskWithoutNotification(FileURI fileURI,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) throws IOException {
		Path filePath = fileURI.toJavaIoFile().toPath();
		String oldContent = Files.readString(filePath);
		String newContent = oldContent;
		for (Pair<String, String> replacement : replacements) {
			newContent = newContent.replaceFirst(Pattern.quote(replacement.getKey()), replacement.getValue());
		}
		Files.writeString(filePath, newContent, StandardOpenOption.TRUNCATE_EXISTING);
	}

	/**
	 * Asserts that there are no issues in the entire workspace.
	 */
	protected void assertNoIssues() {
		assertIssues(Collections.emptyMap());
	}

	/**
	 * Same as {@link #assertIssues(Map)}, accepting pairs from module name to issue list instead of a map from module
	 * ID to issue list.
	 */
	protected void assertIssues(
			@SuppressWarnings("unchecked") Pair<String, List<String>>... moduleNameToExpectedIssues) {
		assertIssues(convertModuleNamePairsToIdMap(moduleNameToExpectedIssues));
	}

	/**
	 * Same as {@link #assertIssuesInModules(Map)}, but in addition to checking the modules denoted by the given map's
	 * keys, this method will also assert that the remaining modules in the workspace do not contain any issues.
	 */
	protected void assertIssues(Map<FileURI, List<String>> moduleIdToExpectedIssues) {
		// check given expectations
		assertIssuesInModules(moduleIdToExpectedIssues);
		Set<FileURI> checkedModules = moduleIdToExpectedIssues.keySet();

		// check that there are no other issues in the workspace
		Multimap<FileURI, Diagnostic> allIssues = languageClient.getIssues();
		Set<FileURI> modulesWithIssues = allIssues.keySet();
		Set<FileURI> uncheckedModulesWithIssues = new LinkedHashSet<>(modulesWithIssues);
		uncheckedModulesWithIssues.removeAll(checkedModules);
		if (!uncheckedModulesWithIssues.isEmpty()) {
			String msg = moduleIdToExpectedIssues.size() == 0
					? "expected no issues in workspace but found " + allIssues.size() + " issue(s):"
					: "found one or more unexpected issues in workspace:";
			StringBuilder sb = new StringBuilder();
			for (FileURI currModuleURI : uncheckedModulesWithIssues) {
				String currModuleRelPath = getRelativePathFromModuleUri(currModuleURI);
				sb.append(currModuleRelPath);
				sb.append(":\n");
				List<String> currModuleIssuesAsList = allIssues.get(currModuleURI).stream()
						.map(issue -> languageClient.getIssueString(issue))
						.collect(Collectors.toList());
				String currModuleIssuesAsString = issuesToSortedString(currModuleIssuesAsList, "    ");
				sb.append(currModuleIssuesAsString);
			}
			Assert.fail(msg + "\n" + sb.toString());
		}
	}

	/**
	 * Same as {@link #assertIssuesInModules(Map)}, accepting pairs from module name to issue list instead of a map from
	 * module ID to issue list.
	 */
	protected void assertIssuesInModules(
			@SuppressWarnings("unchecked") Pair<String, List<String>>... moduleNameToExpectedIssues) {
		assertIssuesInModules(convertModuleNamePairsToIdMap(moduleNameToExpectedIssues));
	}

	/**
	 * Asserts issues in the modules denoted by the given map's keys. Modules for which the given map does not contain
	 * any IDs are *not* checked to be free of issues! If this is desired use method {@link #assertIssues(Map)} instead.
	 *
	 * @param moduleIdToExpectedIssues
	 *            a map from module IDs to the list of expected issues in each case.
	 */
	protected void assertIssuesInModules(Map<FileURI, List<String>> moduleIdToExpectedIssues) {
		for (Entry<FileURI, List<String>> pair : moduleIdToExpectedIssues.entrySet()) {
			FileURI moduleURI = pair.getKey();
			List<String> expectedIssues = pair.getValue();

			Iterable<String> actualIssues = Iterables.concat(languageClient.getErrors(moduleURI),
					languageClient.getWarnings(moduleURI));
			Set<String> actualIssuesAsSet = IterableExtensions.toSet(
					Iterables.transform(actualIssues, String::trim));
			Set<String> expectedIssuesAsSet = IterableExtensions.toSet(
					Iterables.transform(expectedIssues, String::trim));

			if (!Objects.equals(expectedIssuesAsSet, actualIssuesAsSet)) {
				String indent = "    ";
				String moduleRelPath = getRelativePathFromModuleUri(moduleURI);
				Assert.fail("issues in module " + moduleRelPath + " do not meet expectation\n"
						+ "EXPECTED:\n"
						+ issuesToSortedString(expectedIssuesAsSet, indent) + "\n"
						+ "ACTUAL:\n"
						+ issuesToSortedString(actualIssuesAsSet, indent));
			}
		}
	}

	private String issuesToSortedString(Iterable<String> issues, String indent) {
		if (Iterables.isEmpty(issues)) {
			return indent + "<none>";
		}
		return IterableExtensions.sort(issues).stream()
				.map(issue -> issue.replace("\n", "\n" + indent))
				.collect(Collectors.joining("\n" + indent));
	}

	private List<TextDocumentContentChangeEvent> replacementsToChangeEvents(String content,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) {
		return replacementsToChangeEvents(new XDocument(0, content), replacements);
	}

	private List<TextDocumentContentChangeEvent> replacementsToChangeEvents(XDocument document,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) {
		List<TextDocumentContentChangeEvent> result = new ArrayList<>(replacements.length);
		for (Pair<String, String> replacement : replacements) {
			int offset = document.getContents().indexOf(replacement.getKey());
			if (offset < 0) {
				throw new IllegalArgumentException(
						"string \"" + replacement.getKey() + "\" not found in content of document");
			}
			int len = replacement.getKey().length();
			Range range = new Range(document.getPosition(offset), document.getPosition(offset + len));
			result.add(new TextDocumentContentChangeEvent(range, len, replacement.getValue()));
		}
		return result;
	}

	private <T> Map<FileURI, T> convertModuleNamePairsToIdMap(
			@SuppressWarnings("unchecked") Pair<String, T>... moduleNameToExpectedIssues) {
		return Stream.of(moduleNameToExpectedIssues).collect(Collectors.toMap(
				p -> getFileUriFromModuleName(p.getKey()),
				Pair::getValue));
	}

	/** Translates a given module name to a file URI used in LSP call data. */
	protected FileURI getFileUriFromModuleName(String moduleName) {
		moduleName = getModuleNameOrDefault(moduleName) + "." + FILE_EXTENSION;
		Path completeFilePath = Path.of(getRoot().toString(), PROJECT_NAME, SRC_FOLDER, moduleName);
		return new FileURI(completeFilePath.toFile());
	}

	/**
	 * Returns the module's path and name relative to the test workspace's {@link #getRoot() root folder}. Intended for
	 * use in output presented to the user (e.g. messages of assertion errors).
	 */
	protected String getRelativePathFromModuleUri(FileURI moduleURI) {
		URI relativeUri = workspaceManager.makeWorkspaceRelative(moduleURI.toURI());
		return relativeUri.toFileString();
	}

	/** @return the given name if non-<code>null</code> and non-empty; otherwise {@link #MODULE_NAME}. */
	protected String getModuleNameOrDefault(String moduleName) {
		if (Strings.isNullOrEmpty(moduleName)) {
			return MODULE_NAME;
		}
		return moduleName;
	}

	/** Waits until the LSP server idles. */
	protected void joinServerRequests() {
		languageServer.joinServerRequests();
	}

	/** @see IdeTestLanguageClient#getIssues() */
	protected Multimap<FileURI, Diagnostic> getIssues() {
		return languageClient.getIssues();
	}

	/** @see IdeTestLanguageClient#getIssues(FileURI) */
	protected Collection<Diagnostic> getIssues(FileURI uri) {
		return languageClient.getIssues(uri);
	}

	static String toUnixLineSeparator(CharSequence cs) {
		return cs.toString().replaceAll("\r?\n", "\n");
	}
}
