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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ResourceChange;
import org.eclipse.lsp4j.ResourceOperation;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentEdit;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.cli.helper.SystemOutRedirecter;
import org.eclipse.n4js.ide.tests.client.IdeTestLanguageClient;
import org.eclipse.n4js.ide.tests.client.IdeTestLanguageClient.IIdeTestLanguageClientListener;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Module;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.tests.codegen.Project.SourceFolder;
import org.eclipse.n4js.tests.codegen.YarnWorkspaceProject;
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
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Streams;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Abstract base class for LSP-based IDE tests.
 */
@SuppressWarnings("deprecation")
abstract public class AbstractIdeTest implements IIdeTestLanguageClientListener {

	/** Wildcard string that may be used at the start or end of a file content expectation. */
	static final protected String FILE_CONTENT_ASSERTION_WILDCARD = "[...]";
	/** Folder where test data is created */
	static final protected String TEST_DATA_FOLDER = "/test-data";
	/** Vendor of the created test project */
	static final protected String VENDOR = "VENDOR";
	/** Name of the created test module */
	static final protected String SRC_FOLDER = "src";
	/** Extension of test modules */
	static final protected String FILE_EXTENSION = "n4js";
	/** Default name of the created test project */
	static final protected String DEFAULT_PROJECT_NAME = "test-project";
	/** Default name of the created test module */
	static final protected String DEFAULT_MODULE_NAME = "MyModule";
	/** Reserved string to identify a dependency to another project */
	static final protected String TAG_DEPENDENCY = "#DEPENDENCY";
	/** Reserved string to identify the directory 'node_modules' */
	static final protected String TAG_NODE_MODULES = "#NODE_MODULES:";
	/** Reserved string to identify the src folder of a project */
	static final protected String TAG_SRC = "#SRC:";
	/** Name of n4js library 'n4js-runtime' */
	static final protected String N4JS_RUNTIME_NAME = "n4js-runtime";
	/** Default project object for 'n4js-runtime' */
	static final protected Project N4JS_RUNTIME_FAKE = new Project(N4JS_RUNTIME_NAME, VENDOR, VENDOR + "_name",
			ProjectType.RUNTIME_ENVIRONMENT);

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

	/** Overwrite this method to ignore certain issues in {@link #assertIssues(Map)}. */
	protected Set<String> getIgnoredIssueCodes() {
		return N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS;
	}

	/** @return the workspace root folder as a {@link File}. */
	protected File getRoot() {
		File root = new File(new File("").getAbsoluteFile(), TEST_DATA_FOLDER);
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
		initParams.setRootUri(new FileURI(new File(root, DEFAULT_PROJECT_NAME)).toString());

		languageClient.addListener(this);

		languageServer.connect(languageClient);
		languageServer.initialize(initParams);
		languageServer.initialized(null);
		joinServerRequests();
	}

	@Override
	public boolean onServerRequest_applyEdit(ApplyWorkspaceEditParams params) {
		changeFilesOnDiskWithoutNotification(params.getEdit());
		return true;
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
	 * Same as {@link #createTestProjectOnDisk(Map)}, but name and content of the modules can be provided as one or more
	 * {@link Pair}s.
	 */
	protected Project createTestProjectOnDisk(
			@SuppressWarnings("unchecked") Pair<String, String>... moduleNameToContents) {

		return createTestProjectOnDisk(Arrays.asList(moduleNameToContents));
	}

	/**
	 * Same as {@link #createTestProjectOnDisk(Map)}, but name and content of the modules can be provided as an iterable
	 * of {@link Pair}s.
	 */
	protected Project createTestProjectOnDisk(Iterable<? extends Pair<String, String>> moduleNameToContents) {
		Map<String, String> moduleNameToContentsAsMap = Streams.stream(moduleNameToContents)
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

		return createClientProject(getRoot().toPath(), DEFAULT_PROJECT_NAME, moduleNameToContents);
	}

	private Project createClientProject(Path destination, String projectName,
			Map<String, String> moduleNameToContents) {

		String nestedRuntime = TAG_NODE_MODULES + N4JS_RUNTIME_NAME;

		LinkedHashMap<String, Map<String, String>> projectsModulesContents = new LinkedHashMap<>();
		projectsModulesContents.put(projectName, moduleNameToContents);
		moduleNameToContents.put(nestedRuntime, null);
		moduleNameToContents.put(TAG_DEPENDENCY, nestedRuntime);

		Project project = createTestOnDisk(destination, projectsModulesContents);
		return project;
	}

	/** Creates the default project on file system. */
	protected Project createTestOnDisk(
			LinkedHashMap<String, Map<String, String>> projectsModulesContents) {

		return createTestOnDisk(getRoot().toPath(), projectsModulesContents);
	}

	private Project createTestOnDisk(Path destination,
			LinkedHashMap<String, Map<String, String>> projectsModulesContents) {

		Project project = null;
		if (projectsModulesContents.size() == 1) {
			Entry<String, Map<String, String>> singleProject = projectsModulesContents.entrySet().iterator().next();
			String projectName = singleProject.getKey();
			Map<String, String> moduleContents = singleProject.getValue();
			project = createSimpleProject(projectName, moduleContents, HashMultimap.create());
		} else {
			project = createYarnProject(projectsModulesContents);
		}

		destination.toFile().mkdirs();
		project.create(destination);

		return project;
	}

	private Project createSimpleProject(String projectName, Map<String, String> moduleContents,
			Multimap<String, String> dependencies) {

		if (projectName.equals(N4JS_RUNTIME_NAME) && (moduleContents == null || moduleContents.isEmpty())) {
			return N4JS_RUNTIME_FAKE;
		}

		ProjectType prjType = projectName.equals(N4JS_RUNTIME_NAME) ? ProjectType.RUNTIME_ENVIRONMENT
				: getProjectType();
		Project project = new Project(projectName, VENDOR, VENDOR + "_name", prjType);
		SourceFolder sourceFolder = project.createSourceFolder(SRC_FOLDER);

		for (String moduleName : moduleContents.keySet()) {
			String contents = moduleContents.get(moduleName);
			if (moduleName.equals(TAG_DEPENDENCY)) {
				dependencies.put(projectName, contents);

			} else if (moduleName.startsWith(TAG_NODE_MODULES)) {
				int indexOfSrc = moduleName.indexOf(TAG_SRC);
				if (indexOfSrc == -1) {
					throw new IllegalArgumentException("Missing #SRC: in module location");
				}
				String nmName = moduleName.substring(TAG_NODE_MODULES.length(), indexOfSrc);
				String nmModuleName = moduleName.substring(indexOfSrc + TAG_SRC.length());
				Project nmProject = project.getNodeModuleProject(nmName);
				if (nmProject == null) {
					nmProject = new Project(nmName, VENDOR, VENDOR + "_name", prjType);
					nmProject.createSourceFolder(SRC_FOLDER);
					project.addNodeModuleProject(nmProject);
				}
				SourceFolder nmSourceFolder = nmProject.getSourceFolders().get(0);

				Module module = new Module(nmModuleName);
				module.setContents(contents);
				nmSourceFolder.addModule(module);

			} else {
				Module module = new Module(moduleName);
				module.setContents(contents);
				sourceFolder.addModule(module);
			}
		}

		return project;
	}

	private Project createYarnProject(LinkedHashMap<String, Map<String, String>> projectsModulesContents) {
		YarnWorkspaceProject yarnProject = new YarnWorkspaceProject("yarn-test-project", VENDOR, VENDOR + "_name");
		Multimap<String, String> dependencies = HashMultimap.create();
		for (String projectNameWithSelector : projectsModulesContents.keySet()) {
			Map<String, String> moduleContents = projectsModulesContents.get(projectNameWithSelector);

			String prjName = projectNameWithSelector;

			if (prjName.startsWith(TAG_NODE_MODULES)) {
				prjName = prjName.substring(TAG_NODE_MODULES.length());
				Project project = createSimpleProject(prjName, moduleContents, dependencies);
				yarnProject.addNodeModuleProject(project);
			} else {
				Project project = createSimpleProject(prjName, moduleContents, dependencies);
				yarnProject.addProject(project);
			}
		}

		setDependencies(yarnProject, dependencies);

		return yarnProject;
	}

	private void setDependencies(YarnWorkspaceProject yarnProject, Multimap<String, String> dependencies) {
		for (String projectName : dependencies.keySet()) {
			Collection<String> projectDependencies = dependencies.get(projectName);
			Project project = yarnProject.getProject(projectName);
			for (String projectDependency : projectDependencies) {
				Project dependency = yarnProject.getProject(projectDependency);
				if (dependency == null) {
					dependency = yarnProject.getNodeModuleProject(projectDependency);
				}
				project.addProjectDependency(dependency);
			}
		}
	}

	/** Same as {@link #openFile(String, String)}, but content is read from disk. */
	protected void openFile(String moduleName) throws IOException {
		FileURI fileURI = getFileURIFromModuleName(moduleName);
		Path filePath = fileURI.toJavaIoFile().toPath();
		String content = Files.readString(filePath);
		openFile(moduleName, content);
	}

	/** Same as {@link #openFile(FileURI, String)}, but accepts a module name instead of file URI. */
	protected void openFile(String moduleName, String contents) {
		openFile(getFileURIFromModuleName(moduleName), contents);
	}

	/** Opens the given file in the LSP server and waits for the triggered build to finish. */
	protected void openFile(FileURI fileURI, String contents) {
		Assert.assertNotNull(contents);

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

	/** Same as {@link #closeFile(FileURI)}, but accepts a module name instead of file URI. */
	protected void closeFile(String moduleName) {
		closeFile(getFileURIFromModuleName(moduleName));
	}

	/** Closes the given file in the LSP server and waits for the server to idle. */
	protected void closeFile(FileURI fileURI) {
		languageServer.didClose(new DidCloseTextDocumentParams(new TextDocumentIdentifier(fileURI.toString())));
		joinServerRequests();
	}

	/**
	 * Change a non-opened file on disk and notify the LSP server.
	 * <p>
	 * Use method {@link #changeOpenedFile(String, Pair...)} instead if the file was previously opened with one of the
	 * {@link #openFile(String, String) #openFile()} methods.
	 *
	 * @param replacements
	 *            for each pair P, the first (and only the first!) occurrence of P's key in the content of the file
	 *            denoted by <code>fileURI</code> will be replaced by P's value.
	 */
	protected void changeNonOpenedFile(String moduleName,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) {
		changeNonOpenedFile(moduleName, content -> applyReplacements(content, replacements));
	}

	/**
	 * Change a non-opened file on disk and notify the LSP server.
	 * <p>
	 * Use method {@link #changeOpenedFile(String, Pair...)} instead if the file was previously opened with one of the
	 * {@link #openFile(String, String) #openFile()} methods.
	 *
	 * @param modification
	 *            a function returning the desired new content when given the file's current content on disk.
	 */
	protected void changeNonOpenedFile(String moduleName, Function<String, String> modification) {
		FileURI fileURI = getFileURIFromModuleName(moduleName);
		// 1) change on disk
		changeFileOnDiskWithoutNotification(fileURI, modification);
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
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) {

		FileURI fileURI = getFileURIFromModuleName(moduleName);
		// 1) change on disk
		Pair<String, String> oldToNewContent = changeFileOnDiskWithoutNotification(fileURI, replacements);
		String oldContent = oldToNewContent.getKey();
		// 2) notify LSP server
		VersionedTextDocumentIdentifier docId = new VersionedTextDocumentIdentifier(fileURI.toString(), 1);
		List<TextDocumentContentChangeEvent> changes = replacementsToChangeEvents(oldContent, replacements);
		DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(docId, changes);
		languageServer.didChange(params);
	}

	/**
	 * Same as {@link #changeFileOnDiskWithoutNotification(FileURI, Function)}, but changes one or more files at once,
	 * as defined by the given {@link WorkspaceEdit}.
	 */
	protected List<Pair<FileURI, List<TextEdit>>> changeFilesOnDiskWithoutNotification(WorkspaceEdit workspaceEdit) {
		List<Either<TextDocumentEdit, ResourceOperation>> documentChanges = workspaceEdit.getDocumentChanges();
		if (documentChanges != null && !documentChanges.isEmpty()) {
			List<Pair<FileURI, List<TextEdit>>> appliedChanges = new ArrayList<>();
			for (Either<TextDocumentEdit, ResourceOperation> documentChange : documentChanges) {
				if (documentChange.isRight()) {
					throw new UnsupportedOperationException(
							"resource operations not yet supported by AbstractIdeTest");
				}
				TextDocumentEdit edit = documentChange.getLeft();
				if (edit.getTextDocument().getVersion() != null) {
					throw new UnsupportedOperationException(
							"text document versions not yet supported by AbstractIdeTest");
				}
				FileURI fileURI = getFileURIFromURIString(edit.getTextDocument().getUri());
				changeFileOnDiskWithoutNotification(fileURI, edit.getEdits());
				appliedChanges.add(Pair.of(fileURI, edit.getEdits()));
			}
			return appliedChanges;
		}
		Map<String, List<TextEdit>> changes = workspaceEdit.getChanges();
		if (changes != null && !changes.isEmpty()) {
			List<Pair<FileURI, List<TextEdit>>> appliedChanges = new ArrayList<>();
			for (Entry<String, List<TextEdit>> entry : changes.entrySet()) {
				FileURI fileURI = getFileURIFromURIString(entry.getKey());
				changeFileOnDiskWithoutNotification(fileURI, entry.getValue());
				appliedChanges.add(Pair.of(fileURI, entry.getValue()));
			}
			return appliedChanges;
		}
		List<Either<ResourceChange, TextDocumentEdit>> resourceChanges = workspaceEdit.getResourceChanges();
		if (resourceChanges != null && !resourceChanges.isEmpty()) {
			throw new UnsupportedOperationException(
					"deprecated property 'WorkspaceEdit#resourceChanges' is not supported");
		}
		throw new IllegalArgumentException("workspace edit without changes");
	}

	/** Same as {@link #changeFileOnDiskWithoutNotification(FileURI, Function)}, but accepts {@link TextEdit}s. */
	protected Pair<String, String> changeFileOnDiskWithoutNotification(FileURI fileURI,
			Iterable<? extends TextEdit> textEdits) {

		return changeFileOnDiskWithoutNotification(fileURI, content -> applyTextEdits(content, textEdits));
	}

	/**
	 * Same as {@link #changeFileOnDiskWithoutNotification(FileURI, Pair...)}, accepting a module name instead of a file
	 * URI.
	 */
	protected void changeFileOnDiskWithoutNotification(String moduleName,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) {

		FileURI fileURI = getFileURIFromModuleName(moduleName);
		changeFileOnDiskWithoutNotification(fileURI, replacements);
	}

	/**
	 * Same as {@link #changeFileOnDiskWithoutNotification(FileURI, Function)}, but changes can be provided as string
	 * replacements.
	 *
	 * @param fileURI
	 *            URI of the file to change.
	 * @param replacements
	 *            for each pair P, the first (and only the first!) occurrence of P's key in the content of the file
	 *            denoted by <code>fileURI</code> will be replaced by P's value.
	 * @return a pair with the file's old content as key and its new content as value.
	 */
	protected Pair<String, String> changeFileOnDiskWithoutNotification(FileURI fileURI,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) {

		return changeFileOnDiskWithoutNotification(fileURI, content -> applyReplacements(content, replacements));
	}

	/**
	 * Changes a file on disk by applying the given replacements without notifying the LSP server.
	 *
	 * @param fileURI
	 *            URI of the file to change.
	 * @param modification
	 *            a function returning the desired new content when given the file's current content on disk.
	 * @return a pair with the file's old content as key and its new content as value.
	 */
	protected Pair<String, String> changeFileOnDiskWithoutNotification(FileURI fileURI,
			Function<String, String> modification) {

		try {
			Path filePath = fileURI.toJavaIoFile().toPath();
			String oldContent = Files.readString(filePath);
			String newContent = modification.apply(oldContent);
			Files.writeString(filePath, newContent, StandardOpenOption.TRUNCATE_EXISTING);
			return new Pair<>(oldContent, newContent);
		} catch (IOException e) {
			// wrap in AssertionError to avoid need for test code to declare IOExceptions
			throw new AssertionError("IOException while changing file on disk", e);
		}
	}

	/** @return contents of the file with the given URI as a string. */
	protected String getContentOfFileOnDisk(FileURI fileURI) {
		try {
			Path filePath = fileURI.toJavaIoFile().toPath();
			String content = Files.readString(filePath);
			return content;
		} catch (IOException e) {
			// wrap in AssertionError to avoid need for test code to declare IOExceptions
			throw new AssertionError("IOException while changing file on disk", e);
		}
	}

	/**
	 * Asserts that the file with the given URI has a particular content on disk.
	 * <p>
	 * The expectation string may start or end with the special wildcard string
	 * {@value #FILE_CONTENT_ASSERTION_WILDCARD} to denote that arbitrary file content may precede or succeed the given
	 * expectation string.
	 */
	protected void assertContentOfFileOnDisk(FileURI fileURI, CharSequence expectedContent) {
		String actualContentStr = getContentOfFileOnDisk(fileURI);
		String expectedContentStr = expectedContent.toString();
		// trim
		actualContentStr = actualContentStr.trim();
		expectedContentStr = expectedContentStr.trim();
		// normalize line breaks
		if (!System.lineSeparator().equals("\n")) {
			actualContentStr = actualContentStr.replace(System.lineSeparator(), "\n");
			expectedContentStr = expectedContentStr.replace(System.lineSeparator(), "\n");
		}
		// handle wildcards
		String expectedContentStrBeforeRemovingWildcards = expectedContentStr;
		boolean wildcardAtStart = expectedContentStr.startsWith(FILE_CONTENT_ASSERTION_WILDCARD);
		if (wildcardAtStart) {
			expectedContentStr = expectedContentStr
					.substring(FILE_CONTENT_ASSERTION_WILDCARD.length())
					.trim();
		}
		boolean wildcardAtEnd = expectedContentStr.endsWith(FILE_CONTENT_ASSERTION_WILDCARD);
		if (wildcardAtEnd) {
			expectedContentStr = expectedContentStr
					.substring(0, expectedContentStr.length() - FILE_CONTENT_ASSERTION_WILDCARD.length())
					.trim();
		}
		if (expectedContentStr.contains(FILE_CONTENT_ASSERTION_WILDCARD)) {
			throw new IllegalArgumentException(
					"wildcard string \" + FILE_CONTENT_ASSERTION_WILDCARD + \" may only appear at start or end of expectation");
		}
		// check expectation
		final boolean success;
		if (wildcardAtStart && !wildcardAtEnd) {
			success = actualContentStr.endsWith(expectedContentStr);
		} else if (!wildcardAtStart && wildcardAtEnd) {
			success = actualContentStr.startsWith(expectedContentStr);
		} else if (wildcardAtStart && wildcardAtEnd) {
			success = actualContentStr.contains(expectedContentStr);
		} else {
			success = actualContentStr.equals(expectedContentStr);
		}
		if (!success) {
			Assert.fail("unexpected file content\n"
					+ "EXPECTED:\n"
					+ expectedContentStrBeforeRemovingWildcards + "\n"
					+ "ACTUAL:\n"
					+ actualContentStr);
		}
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
	 * Same as {@link #assertIssues(Map, boolean)}, but with <code>withIgnoredIssues</code> always set to
	 * <code>false</code>.
	 */
	protected void assertIssues(Map<FileURI, List<String>> moduleIdToExpectedIssues) {
		assertIssues(moduleIdToExpectedIssues, false);
	}

	/**
	 * Same as {@link #assertIssuesInModules(Map, boolean)}, but in addition to checking the modules denoted by the
	 * given map's keys, this method will also assert that the remaining modules in the workspace do not contain any
	 * issues. Flag <code>withIgnoredIssues</code> applies to those issues accordingly.
	 */
	protected void assertIssues(Map<FileURI, List<String>> moduleIdToExpectedIssues, boolean withIgnoredIssues) {
		// check given expectations
		assertIssuesInModules(moduleIdToExpectedIssues, withIgnoredIssues);
		Set<FileURI> checkedModules = moduleIdToExpectedIssues.keySet();

		// check that there are no other issues in the workspace
		Multimap<FileURI, Diagnostic> allIssues = languageClient.getIssues();
		Set<FileURI> modulesWithIssues = allIssues.keySet();
		Set<FileURI> uncheckedModulesWithIssues = new LinkedHashSet<>(modulesWithIssues);
		uncheckedModulesWithIssues.removeAll(checkedModules);
		if (!uncheckedModulesWithIssues.isEmpty()) {
			String msg = moduleIdToExpectedIssues.size() == 0
					? "expected no issues in workspace but found one or more issues:"
					: "found one or more unexpected issues in workspace:";
			StringBuilder sb = new StringBuilder();
			for (FileURI currModuleURI : uncheckedModulesWithIssues) {
				List<String> currModuleIssuesAsList = getIssuesInFile(currModuleURI, withIgnoredIssues);
				if (!currModuleIssuesAsList.isEmpty()) { // empty if all issues in current module are ignored
					String currModuleRelPath = getRelativePathFromModuleUri(currModuleURI);
					sb.append(currModuleRelPath);
					sb.append(":\n");
					String currModuleIssuesAsString = issuesToSortedString(currModuleIssuesAsList, "    ");
					sb.append(currModuleIssuesAsString);
				}
			}
			if (sb.length() > 0) { // empty if all remaining issues are ignored
				Assert.fail(msg + "\n" + sb.toString());
			}
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
	 * Same as {@link #assertIssuesInModules(Map, boolean)}, but with <code>withIgnoredIssues</code> always set to
	 * <code>false</code>.
	 */
	protected void assertIssuesInModules(Map<FileURI, List<String>> moduleIdToExpectedIssues) {
		assertIssuesInModules(moduleIdToExpectedIssues, false);
	}

	/**
	 * Asserts issues in the modules denoted by the given map's keys. Modules for which the given map does not contain
	 * any IDs are *not* checked to be free of issues! If this is desired use method {@link #assertIssues(Map, boolean)}
	 * instead.
	 *
	 * @param moduleIdToExpectedIssues
	 *            a map from module IDs to the list of expected issues in each case.
	 * @param withIgnoredIssues
	 *            iff <code>true</code>, even issues with an issue code that is among those returned by method
	 *            {@link #getIgnoredIssueCodes()} will be taken into consideration.
	 */
	protected void assertIssuesInModules(Map<FileURI, List<String>> moduleIdToExpectedIssues,
			boolean withIgnoredIssues) {
		for (Entry<FileURI, List<String>> pair : moduleIdToExpectedIssues.entrySet()) {
			FileURI moduleURI = pair.getKey();
			List<String> expectedIssues = pair.getValue();

			List<String> actualIssues = getIssuesInFile(moduleURI, withIgnoredIssues);
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

	/**
	 * Returns the issues in the file denoted by the given URI. If <code>withIgnoredIssues</code> is set to
	 * <code>true</code>, even issues with an issue code returned by method {@link #getIgnoredIssueCodes()} will be
	 * included in the returned list.
	 */
	protected List<String> getIssuesInFile(FileURI fileURI, boolean withIgnoredIssues) {
		Stream<Diagnostic> issuesInFile = languageClient.getIssues().get(fileURI).stream();
		if (!withIgnoredIssues) {
			issuesInFile = issuesInFile.filter(issue -> !getIgnoredIssueCodes().contains(issue.getCode()));
		}
		return issuesInFile.map(issue -> languageClient.getIssueString(issue)).collect(Collectors.toList());
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
				p -> getFileURIFromModuleName(p.getKey()), Pair::getValue));
	}

	/** Translates a given module name to a file URI used in LSP call data. */
	protected FileURI getFileURIFromModuleName(String moduleName) {
		String moduleNameWithExtension = getModuleNameOrDefault(moduleName) + "." + FILE_EXTENSION;
		try {
			Path firstMatch = Files
					.find(getRoot().toPath(), 99, (path, options) -> path.endsWith(moduleNameWithExtension))
					.findFirst().orElse(null);

			if (firstMatch == null) {
				throw new IllegalStateException("Module not found in test project");
			}
			return new FileURI(firstMatch.toFile());

		} catch (IOException e) {
			throw new IllegalStateException("Error when searching for module " + moduleNameWithExtension, e);
		}
	}

	/** Converts an URI string as received by the LSP server to a {@link FileURI}. */
	protected FileURI getFileURIFromURIString(String uriString) {
		return new FileURI(URI.createURI(uriString));
	}

	/**
	 * Returns the module's path and name relative to the test workspace's {@link #getRoot() root folder}. Intended for
	 * use in output presented to the user (e.g. messages of assertion errors).
	 */
	protected String getRelativePathFromModuleUri(FileURI moduleURI) {
		URI relativeUri = workspaceManager.makeWorkspaceRelative(moduleURI.toURI());
		return relativeUri.toFileString();
	}

	/** @return the given name if non-<code>null</code> and non-empty; otherwise {@link #DEFAULT_MODULE_NAME}. */
	protected String getModuleNameOrDefault(String moduleName) {
		if (Strings.isNullOrEmpty(moduleName)) {
			return DEFAULT_MODULE_NAME;
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

	private static String applyReplacements(CharSequence oldContent,
			@SuppressWarnings("unchecked") Pair<String, String>... replacements) {

		StringBuilder newContent = new StringBuilder(oldContent);
		for (Pair<String, String> replacement : replacements) {
			int offset = newContent.indexOf(replacement.getKey());
			if (offset < 0) {
				throw new IllegalArgumentException(
						"string \"" + replacement.getKey() + "\" not found in content of document");
			}
			int len = replacement.getKey().length();
			newContent.replace(offset, offset + len, replacement.getValue());
		}
		return newContent.toString();
	}

	private static String applyTextEdits(CharSequence oldContent, Iterable<? extends TextEdit> textEdits) {
		XDocument oldDocument = new XDocument(0, oldContent.toString(), true, true);
		XDocument newDocument = oldDocument.applyChanges(textEdits);
		return newDocument.getContents();
	}
}
