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
package org.eclipse.n4js.ide.tests.helper.server;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.ResourceChange;
import org.eclipse.lsp4j.ResourceOperation;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentEdit;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.cli.N4jscFactory;
import org.eclipse.n4js.cli.N4jscTestFactory;
import org.eclipse.n4js.cli.helper.SystemOutRedirecter;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.ide.tests.helper.client.IdeTestLanguageClient;
import org.eclipse.n4js.ide.tests.helper.client.IdeTestLanguageClient.IIdeTestLanguageClientListener;
import org.eclipse.n4js.ide.xtext.server.ProjectBuildOrderInfo;
import org.eclipse.n4js.ide.xtext.server.ProjectBuildOrderInfo.ProjectBuildOrderIterator;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersisterConfig;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.build.BuilderFrontend;
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIndex;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.LanguageInfo;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.testing.GlobalRegistries;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Abstract base class for LSP-based IDE tests.
 */
@SuppressWarnings("deprecation")
abstract public class AbstractIdeTest implements IIdeTestLanguageClientListener {

	/** Wildcard string that may be used at the start or end of a file content expectation. */
	protected static final String FILE_CONTENT_ASSERTION_WILDCARD = "[...]";

	private static final SystemOutRedirecter SYSTEM_OUT_REDIRECTER = new SystemOutRedirecter();

	// copy some constants to avoid having to deal with fragile static imports in subclasses:
	/** @see TestWorkspaceManager#DEFAULT_PROJECT_NAME */
	public static final String DEFAULT_PROJECT_NAME = TestWorkspaceManager.DEFAULT_PROJECT_NAME;
	/** @see TestWorkspaceManager#DEFAULT_MODULE_NAME */
	public static final String DEFAULT_MODULE_NAME = TestWorkspaceManager.DEFAULT_MODULE_NAME;
	/** @see TestWorkspaceManager#DEFAULT_SOURCE_FOLDER */
	public static final String DEFAULT_SOURCE_FOLDER = TestWorkspaceManager.DEFAULT_SOURCE_FOLDER;
	/** @see TestWorkspaceManager#CFG_DEPENDENCIES */
	public static final String CFG_DEPENDENCIES = TestWorkspaceManager.CFG_DEPENDENCIES;
	/** @see TestWorkspaceManager#CFG_MAIN_MODULE */
	public static final String CFG_MAIN_MODULE = TestWorkspaceManager.CFG_MAIN_MODULE;
	/** @see TestWorkspaceManager#CFG_SOURCE_FOLDER */
	public static final String CFG_SOURCE_FOLDER = TestWorkspaceManager.CFG_SOURCE_FOLDER;
	/** @see TestWorkspaceManager#CFG_NODE_MODULES */
	public static final String CFG_NODE_MODULES = TestWorkspaceManager.CFG_NODE_MODULES;
	/** @see TestWorkspaceManager#CFG_SRC */
	public static final String CFG_SRC = TestWorkspaceManager.CFG_SRC;
	/** Name of the package.json file. */
	protected static final String PACKAGE_JSON = N4JSGlobals.PACKAGE_JSON;
	/** Name of n4js library 'n4js-runtime'. */
	protected static final String N4JS_RUNTIME = N4JSGlobals.N4JS_RUNTIME.getRawName();
	/** Name of the npm node_modules folder. */
	protected static final String NODE_MODULES = N4JSGlobals.NODE_MODULES;

	/** Clear global state to ensure IDE tests run on a clean slate. */
	@BeforeClass
	static final public void clearGlobalRegistries() {
		// Due to problems in {@link InProcessExecuter}, invalid global state was leaking from tests that use the
		// {@link CliTools} (e.g. singletons from an earlier test were used in later tests).
		// Should be fixed as of GH-1915, so clearing the global state should no longer be necessary, here. But because
		// this is hard to notice and debug, we still clear the global state to make the IDE tests more robust against
		// similar problems in the future.
		GlobalRegistries.clearGlobalRegistries();
	}

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
	protected BuilderFrontend lspBuilder;
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
	protected ConcurrentIndex concurrentIndex;
	/** */
	@Inject
	protected IdeTestLanguageClient languageClient;
	/** */
	@Inject
	protected LanguageInfo languageInfo;
	/** */
	@Inject
	protected ProjectBuildOrderInfo.Provider projectBuildOrderInfoProvider;

	/** Utility to create/delete the test workspace on disk */
	protected final TestWorkspaceManager testWorkspaceManager = new TestWorkspaceManager(getProjectType());

	/** Tracks open files, their version and their, possibly unsaved, content. */
	private final Map<FileURI, OpenFileInfo> openFiles = new HashMap<>();

	private static final class OpenFileInfo {
		/** Current version of the open file. */
		int version;
		/** In-memory content of the open file. Might be unsaved. */
		String content;

		OpenFileInfo(String content) {
			this.version = 1;
			this.content = content;
		}
	}

	/** Deletes the test data folder if it exists (to ensure tests are not affected by old test data). */
	@Before
	public void cleanupTestDataFolder() throws IOException {
		File root = testWorkspaceManager.getRoot();
		if (root.exists()) {
			FileUtils.delete(root);
		}
	}

	/** Deletes the test project in case it exists. */
	@After
	final public void deleteTestProject() throws IOException {
		try {
			if (languageClient != null) { // only if LSP server was started
				assertNoErrorsInLog();
			}
			assertNoErrorsInOutput();
		} finally {
			if (languageServer != null) { // only if LSP server was started
				shutdownLspServer();
			}
			SYSTEM_OUT_REDIRECTER.clearSystemOut();
			SYSTEM_OUT_REDIRECTER.clearSystemErr();
			// clear the state related to the test
			testWorkspaceManager.deleteTestFromDiskIfCreated();
		}
	}

	/** @return the workspace root folder as a {@link File}. */
	public File getRoot() {
		return testWorkspaceManager.getRoot();
	}

	/**
	 * Same as {@link #getProjectRoot(String)}, but for the {@link TestWorkspaceManager#DEFAULT_PROJECT_NAME default
	 * project}.
	 */
	public File getProjectRoot() {
		return testWorkspaceManager.getProjectRoot();
	}

	/** Returns the root folder of the project with the given name. */
	public File getProjectRoot(String projectName) {
		return testWorkspaceManager.getProjectRoot(projectName);
	}

	/**
	 * Same as {@link #getPackageJsonFile(String)}, but for the {@link TestWorkspaceManager#DEFAULT_PROJECT_NAME default
	 * project}.
	 */
	protected File getPackageJsonFile() {
		return testWorkspaceManager.getPackageJsonFile();
	}

	/** Returns the package.json file of the project with the given name. */
	protected File getPackageJsonFile(String projectName) {
		return testWorkspaceManager.getPackageJsonFile(projectName);
	}

	/** Overwrite this method to change the project type */
	protected ProjectType getProjectType() {
		return ProjectType.LIBRARY;
	}

	/** Overwrite this method to ignore certain issues in {@link #assertIssues(Map)}. */
	protected Set<String> getIgnoredIssueCodes() {
		return N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS;
	}

	/** @return instance of {@link StringLSP4J}. */
	protected StringLSP4J getStringLSP4J() {
		return new StringLSP4J(getRoot());
	}

	/** Shuts down a running LSP server. Does not clean disk. */
	protected void shutdownLspServer() {
		if (languageServer == null) {
			throw new IllegalStateException("trying to shut down LSP server, but it was never started");
		}
		// clear thread pools
		languageServer.shutdown().join();
		openFiles.clear();
		languageClient.clearLogMessages();
		languageClient.clearIssues();
		N4jscTestFactory.unset();
	}

	/**
	 * Call this method after creating the test project(s) to start the LSP server and perform other initializations
	 * required for testing. Afterwards, actual testing can begin, e.g. checking issues with {@link #assertNoIssues()}
	 * or modifying files and rebuilding.
	 */
	protected void startAndWaitForLspServer() {
		startLspServerWithoutWaiting();
		joinServerRequests();
	}

	/** Same as {@link #startAndWaitForLspServer()}, but without waiting. */
	protected void startLspServerWithoutWaiting() {
		createInjector();
		doStartLspServer(getRoot());
	}

	/** Creates injector for N4JS */
	protected Injector createInjector() {
		N4jscTestFactory.set(true, getOverridingModule());
		Injector injector = N4jscFactory.getOrCreateInjector();
		injector.injectMembers(this);
		if (enableProjectStatePersister()) {
			ProjectStatePersisterConfig persisterConfig = injector.getInstance(ProjectStatePersisterConfig.class);
			persisterConfig.setDeleteState(false);
			persisterConfig.setWriteToDisk(true);
		}
		return injector;
	}

	/**
	 * By default, tests usually run with a disabled project state persister (i.e. project state files will be deleted
	 * upon start up and not written to disk). Override this method to return <code>true</code> in order to activate the
	 * ordinary project state persistence as in the LSP server. The default implementation returns <code>false</code>.
	 */
	protected boolean enableProjectStatePersister() {
		return false;
	}

	/** Returns an optional module overriding default injection bindings for testing purposes. */
	protected Optional<Class<? extends Module>> getOverridingModule() {
		return Optional.absent();
	}

	/** Connects, initializes and waits for the initial build of the test project. */
	protected void doStartLspServer(File root) {
		ClientCapabilities capabilities = new ClientCapabilities();
		WorkspaceClientCapabilities wcc = new WorkspaceClientCapabilities();
		wcc.setExecuteCommand(new ExecuteCommandCapabilities());
		capabilities.setWorkspace(wcc);
		TextDocumentClientCapabilities tdcc = new TextDocumentClientCapabilities();
		tdcc.setRename(new RenameCapabilities(true, false)); // activate 'prepareRename' requests
		capabilities.setTextDocument(tdcc);
		InitializeParams initParams = new InitializeParams();
		initParams.setCapabilities(capabilities);
		initParams.setRootUri(new FileURI(root).toString());

		languageClient.addListener(this);

		languageServer.connect(languageClient);
		languageServer.initialize(initParams)
				.join(); // according to LSP, we must to wait here before sending #initialized():
		languageServer.initialized(null);
	}

	@Override
	public boolean onServerRequest_applyEdit(ApplyWorkspaceEditParams params) {
		changeFilesOnDiskWithoutNotification(params.getEdit());
		return true;
	}

	/** Like {@link #cleanBuildWithoutWait()}, but {@link #joinServerRequests() waits} for LSP server to finish. */
	protected void cleanBuildAndWait() {
		// NOTE: the #join() in the next line is required; the #joinServerRequests() below is not sufficient!
		cleanBuildWithoutWait().join();
		joinServerRequests();
	}

	/** Cleans and rebuilds entire workspace without waiting for LSP server to finish. */
	protected CompletableFuture<Object> cleanBuildWithoutWait() {
		return executeCommand(N4JSCommandService.N4JS_REBUILD);
	}

	/** Executes the command with the given ID and the given arguments. */
	protected CompletableFuture<Object> executeCommand(String commandId, Object... args) {
		Objects.requireNonNull(commandId);
		Objects.requireNonNull(args);
		ExecuteCommandParams params = new ExecuteCommandParams(commandId, Arrays.asList(args));
		return languageServer.executeCommand(params);
	}

	/** Clears system output and error streams. */
	protected void clearOutput() {
		SYSTEM_OUT_REDIRECTER.clearSystemOut();
		SYSTEM_OUT_REDIRECTER.clearSystemErr();
	}

	/** Clear the log messages received from the server via LSP notification 'logMessage'. */
	protected void clearLogMessages() {
		languageClient.clearLogMessages();
	}

	/** Returns the log messages received from the server since the last call to {@link #clearLogMessages()}. */
	protected List<MessageParams> getLogMessages() {
		return languageClient.getLogMessages();
	}

	/** Like {@link #getLogMessages()}, but returns the messages as a string. */
	protected String getLogMessagesAsString() {
		return getLogMessages().stream()
				.map(m -> getMessagePrefix(m) + m.getMessage())
				.collect(Collectors.joining(System.lineSeparator()));
	}

	private String getMessagePrefix(MessageParams message) {
		switch (message.getType()) {
		case Info:
			return "INFO: ";
		case Warning:
			return "WARNING: ";
		case Error:
			return "ERROR: ";
		case Log:
			return ""; // no prefix
		}
		throw new AssertionError("should never reach this point");
	}

	/**
	 * Send a 'didChangeWatchedFiles' notification to the server. The file change type will be
	 * {@link FileChangeType#Changed Changed} for all given URIs.
	 */
	protected void sendDidChangeWatchedFiles(FileURI... changedFileURIs) {
		if (changedFileURIs == null || changedFileURIs.length == 0) {
			Assert.fail("no URIs of changed files given");
		}
		List<FileEvent> fileEvents = Stream.of(changedFileURIs)
				.map(fileURI -> new FileEvent(fileURI.toString(), FileChangeType.Changed))
				.collect(Collectors.toList());
		DidChangeWatchedFilesParams params = new DidChangeWatchedFilesParams(fileEvents);
		languageServer.didChangeWatchedFiles(params);
	}

	/** Same as {@link #isOpen(FileURI)}, but accepts a module name. */
	protected boolean isOpen(String moduleName) {
		FileURI fileURI = getFileURIFromModuleName(moduleName);
		return isOpen(fileURI);
	}

	/** Tells whether the file with the given URI is currently {@link #openFile(String) open}. */
	protected boolean isOpen(FileURI fileURI) {
		return openFiles.containsKey(fileURI);
	}

	/** Returns <code>true</code> iff the file with the given URI is open AND has unsaved changes in memory. */
	protected boolean isDirty(FileURI fileURI) {
		OpenFileInfo info = openFiles.get(fileURI);
		if (info == null) {
			return false;
		}
		String contentInMemory = info.content;
		String contentOnDisk = getContentOfFileOnDisk(fileURI);
		return !contentOnDisk.equals(contentInMemory);
	}

	/** Returns the file URIs of all currently open files. */
	protected Set<FileURI> getOpenFiles() {
		return ImmutableSet.copyOf(openFiles.keySet());
	}

	/** Same as {@link #openFile(FileURI)}, accepting a module name instead of a file URI. */
	protected void openFile(String moduleName) {
		FileURI fileURI = getFileURIFromModuleName(moduleName);
		openFile(fileURI);
	}

	/** Opens the given file in the LSP server and waits for the triggered build to finish. */
	protected void openFile(FileURI fileURI) {
		if (isOpen(fileURI)) {
			Assert.fail("trying to open a file that is already open: " + fileURI);
		}

		Path filePath = fileURI.toJavaIoFile().toPath();
		String content;
		try {
			content = Files.readString(filePath);
		} catch (IOException e) {
			throw new AssertionError("exception while reading file contents from disk", e);
		}

		TextDocumentItem textDocument = new TextDocumentItem();
		textDocument.setLanguageId(languageInfo.getLanguageName());
		textDocument.setUri(fileURI.toString());
		textDocument.setVersion(1);
		textDocument.setText(toUnixLineSeparator(content));

		DidOpenTextDocumentParams dotdp = new DidOpenTextDocumentParams();
		dotdp.setTextDocument(textDocument);

		languageServer.didOpen(dotdp);
		openFiles.put(fileURI, new OpenFileInfo(content));

		joinServerRequests();
	}

	/** Closes all currently open files. */
	protected void closeAllFiles() {
		for (FileURI fileURI : new ArrayList<>(openFiles.keySet())) {
			closeFile(fileURI);
		}
	}

	/** Same as {@link #closeFile(FileURI)}, but accepts a module name instead of a file URI. */
	protected void closeFile(String moduleName) {
		closeFile(getFileURIFromModuleName(moduleName));
	}

	/**
	 * Closes the given file in the LSP server and waits for the server to idle. Throws an exception if the file has
	 * unsaved changes.
	 */
	protected void closeFile(FileURI fileURI) {
		closeFile(fileURI, false, false);
	}

	/**
	 * Same as {@link #closeFileDiscardingChanges(FileURI,boolean)}, but accepts a module name instead of a file URI.
	 *
	 * @param suppressDidChangeNotification
	 *            if <code>true</code>, the usual 'textDocument/didChange' notification sent by many LSP clients (esp.
	 *            VSCode) before closing a dirty file is suppressed. This can be used for testing compatibility with
	 *            such unusual clients.
	 */
	protected void closeFileDiscardingChanges(String moduleName, boolean suppressDidChangeNotification) {
		closeFileDiscardingChanges(getFileURIFromModuleName(moduleName), suppressDidChangeNotification);
	}

	/**
	 * Closes the given file in the LSP server and waits for the server to idle, discarding unsaved in-memory changes.
	 * Throws an exception if the file does not have unsaved changes.
	 */
	protected void closeFileDiscardingChanges(FileURI fileURI, boolean suppressDidChangeNotification) {
		closeFile(fileURI, true, suppressDidChangeNotification);
	}

	private void closeFile(FileURI fileURI, boolean discardChanges, boolean suppressDidChangeNotification) {
		if (!isOpen(fileURI)) {
			Assert.fail("trying to close a file that is not open: " + fileURI);
		}
		boolean dirty = isDirty(fileURI);
		if (dirty && !discardChanges) {
			Assert.fail("trying to close a file with unsaved changes: " + fileURI);
		} else if (!dirty && discardChanges) {
			Assert.fail("no unsaved changes to discard in file: " + fileURI);
		}

		OpenFileInfo info = openFiles.remove(fileURI);
		if (dirty && !suppressDidChangeNotification) {
			// when closing a file with unsaved changes, LSP clients send a 'textDocument/didChange' to bring its
			// content back to the content on disk
			String contentOnDisk = getContentOfFileOnDisk(fileURI);
			DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(
					new VersionedTextDocumentIdentifier(fileURI.toString(), info.version + 1),
					Collections.singletonList(new TextDocumentContentChangeEvent(contentOnDisk)));
			languageServer.didChange(params);
		}
		languageServer.didClose(new DidCloseTextDocumentParams(new TextDocumentIdentifier(fileURI.toString())));
		joinServerRequests();
	}

	/**
	 * Delete a folder and its entire content and notify the LSP server. The folder must not contain open files.
	 * <p>
	 * Which URIs the LSP client will include in the resulting <code>didChangeWatchedFiles</code> notification depends
	 * on the configuration during server start-up and initialization. For example, a VSCode extension might configure a
	 * static glob pattern for this purpose or the server could dynamically register for file events in certain folders.
	 * To simulate various such configurations in tests, this method provides the <code>nameRegEx</code> parameter.
	 *
	 * @param folderURI
	 *            URI of the folder to be deleted. Must not contain open files.
	 * @param nameRegEx
	 *            the URI of a deleted file/folder is included in the <code>didChangeWatchedFiles</code> notification if
	 *            and only if the file/folder's name matches this regular expression.
	 */
	protected void deleteFolderNotContainingOpenFiles(FileURI folderURI, String nameRegEx) {
		// 1) collect all files and folders to be deleted
		List<FileURI> allFoldersAndFiles;
		try (Stream<Path> walker = Files.walk(folderURI.toFile().toPath())) {
			allFoldersAndFiles = walker.map(p -> toFileURI(p.toFile()))
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new WrappedException("exception while walking file tree", e);
		}
		// 2) delete on disk
		for (FileURI currURI : Lists.reverse(allFoldersAndFiles)) {
			deleteFileOnDiskWithoutNotification(currURI);
		}
		Assert.assertFalse("folder was not properly deleted on disk", folderURI.toFile().exists());
		// 3) notify the LSP server
		Pattern pattern = Pattern.compile(nameRegEx);
		List<FileEvent> fileEvents = allFoldersAndFiles.stream()
				.filter(uri -> pattern.matcher(uri.getName()).matches())
				.map(uri -> new FileEvent(uri.toString(), FileChangeType.Deleted))
				.collect(Collectors.toList());
		Assert.assertFalse("at least one deleted file/folder must match the 'nameRegEx'", fileEvents.isEmpty());
		DidChangeWatchedFilesParams params = new DidChangeWatchedFilesParams(fileEvents);
		languageServer.didChangeWatchedFiles(params);
	}

	/** Delete a non-opened file <u>from disk</u> and notify the LSP server. */
	protected void deleteNonOpenedFile(FileURI fileURI) {
		if (isOpen(fileURI)) {
			Assert.fail("trying to delete an open file with method #deleteNonOpenedFile()");
		}
		// 1) delete on disk
		deleteFileOnDiskWithoutNotification(fileURI);
		// 2) notify LSP server
		List<FileEvent> fileEvents = Collections.singletonList(
				new FileEvent(fileURI.toString(), FileChangeType.Deleted));
		DidChangeWatchedFilesParams params = new DidChangeWatchedFilesParams(fileEvents);
		languageServer.didChangeWatchedFiles(params);
	}

	/**
	 * Change a non-opened file <em>on disk</em> and notify the LSP server.
	 * <p>
	 * Use method {@link #changeOpenedFile(String, Pair...)} instead if the file was previously opened with one of the
	 * {@link #openFile(FileURI) #openFile()} methods.
	 *
	 * @param replacements
	 *            for each pair P, the first (and only the first!) occurrence of P's key in the content of the file
	 *            denoted by <code>fileURI</code> will be replaced by P's value.
	 */
	@SafeVarargs
	protected final void changeNonOpenedFile(String moduleName, Pair<String, String>... replacements) {
		changeNonOpenedFile(moduleName, content -> applyReplacements(content, replacements));
	}

	/**
	 * Change a non-opened file <em>on disk</em> and notify the LSP server.
	 * <p>
	 * Use method {@link #changeOpenedFile(String, Pair...)} instead if the file was previously opened with one of the
	 * {@link #openFile(FileURI) #openFile()} methods.
	 *
	 * @param modification
	 *            a function returning the desired new content when given the file's current content on disk.
	 */
	protected void changeNonOpenedFile(String moduleName, Function<String, String> modification) {
		FileURI fileURI = getFileURIFromModuleName(moduleName);
		changeNonOpenedFile(fileURI, modification);
	}

	/**
	 * Change a non-opened file <em>on disk</em> and notify the LSP server.
	 * <p>
	 * Use method {@link #changeOpenedFile(String, Pair...)} instead if the file was previously opened with one of the
	 * {@link #openFile(FileURI) #openFile()} methods.
	 *
	 * @param modification
	 *            a function returning the desired new content when given the file's current content on disk.
	 */
	protected void changeNonOpenedFile(FileURI fileURI, Function<String, String> modification) {
		if (isOpen(fileURI)) {
			Assert.fail("file is open: " + fileURI);
		}
		// 1) change on disk
		changeFileOnDiskWithoutNotification(fileURI, modification);
		// 2) notify LSP server
		sendDidChangeWatchedFiles(fileURI);
	}

	/** Same as {@link #changeOpenedFile(FileURI, String)}, but accepts a module name. */
	protected void changeOpenedFile(String moduleName, String newContent) {
		changeOpenedFile(getFileURIFromModuleName(moduleName), newContent);
	}

	/**
	 * Same as {@link #changeOpenedFile(FileURI, Function)}, but the desired new content is given as argument instead of
	 * a modification function.
	 */
	protected void changeOpenedFile(FileURI fileURI, String newContent) {
		changeOpenedFile(fileURI, oldContent -> newContent);
	}

	/** Same as {@link #changeFileOnDiskWithoutNotification(FileURI, Function)}, but accepts a module name. */
	protected void changeOpenedFile(String moduleName, Function<String, String> modification) {
		changeOpenedFile(getFileURIFromModuleName(moduleName), modification);
	}

	/**
	 * Similar to {@link #changeOpenedFile(FileURI, Pair...)}, but the file's entire content is replaced in a single
	 * {@link TextDocumentContentChangeEvent} by the string returned from the given modification function.
	 * <p>
	 * NOTE: this method replaces the file's entire content in a single step; if a test wants to simulate manual edits
	 * more closely, then method {@link #changeOpenedFile(FileURI, Pair...)} should be used instead!
	 *
	 * @param modification
	 *            will be invoked with the file's old content as argument and is expected to return the desired new
	 *            content.
	 */
	protected void changeOpenedFile(FileURI fileURI, Function<String, String> modification) {
		changeOpenedFile(fileURI,
				modification,
				(oldContent, newContent) -> singletonList(new TextDocumentContentChangeEvent(newContent)));
	}

	/** Same as {@link #changeOpenedFile(FileURI, Pair...)}, but accepts a module name. */
	@SafeVarargs
	protected final void changeOpenedFile(String moduleName, Pair<String, String>... replacements) {
		changeOpenedFile(getFileURIFromModuleName(moduleName), replacements);
	}

	/**
	 * Change an opened file <em>in memory only</em> and notify the LSP server.
	 * <p>
	 * Use method {@link #changeNonOpenedFile(String, Pair...)} instead if the file was *not* previously opened with one
	 * of the {@link #openFile(FileURI) #openFile()} methods.
	 */
	@SafeVarargs
	protected final void changeOpenedFile(FileURI fileURI, Pair<String, String>... replacements) {
		changeOpenedFile(fileURI,
				oldContent -> applyReplacements(oldContent, replacements),
				(oldContent, newContent) -> replacementsToChangeEvents(oldContent, replacements));
	}

	private void changeOpenedFile(FileURI fileURI, Function<String, String> modification,
			BiFunction<String, String, List<TextDocumentContentChangeEvent>> changeComputer) {
		if (!isOpen(fileURI)) {
			Assert.fail("file is not open: " + fileURI);
		}
		// 1) change in memory (i.e. in map 'openFiles')
		OpenFileInfo info = openFiles.get(fileURI);
		int oldVersion = info.version;
		String oldContent = info.content;
		int newVersion = oldVersion + 1;
		String newContent = modification.apply(oldContent);
		Assert.assertNotNull(newContent);
		info.version = newVersion;
		info.content = newContent;
		// 2) notify LSP server
		VersionedTextDocumentIdentifier docId = new VersionedTextDocumentIdentifier(fileURI.toString(), newVersion);
		List<TextDocumentContentChangeEvent> changes = changeComputer.apply(oldContent, newContent);
		DidChangeTextDocumentParams params = new DidChangeTextDocumentParams(docId, changes);
		languageServer.didChange(params);
	}

	/** Same as {@link #saveOpenedFile(FileURI)}, accepting a module name instead of a file URI. */
	protected void saveOpenedFile(String moduleName) {
		FileURI fileURI = getFileURIFromModuleName(moduleName);
		saveOpenedFile(fileURI);
	}

	/** Save the given, open file's in-memory content to disk. Does *not* close the file. */
	protected void saveOpenedFile(FileURI fileURI) {
		if (!isOpen(fileURI)) {
			Assert.fail("file is not open: " + fileURI);
		}
		OpenFileInfo info = openFiles.get(fileURI);
		// 1) save current content to disk
		changeFileOnDiskWithoutNotification(fileURI, info.content);
		// 2) notify LSP server
		VersionedTextDocumentIdentifier docId = new VersionedTextDocumentIdentifier(fileURI.toString(), info.version);
		DidSaveTextDocumentParams didSaveParams = new DidSaveTextDocumentParams(docId);
		languageServer.didSave(didSaveParams);
		sendDidChangeWatchedFiles(fileURI);
	}

	/**
	 * Same as {@link #createFileOnDiskWithoutNotification(FileURI, CharSequence)}, placing the new file next to an
	 * existing module.
	 */
	protected void createFileOnDiskWithoutNotification(String existingModuleName, String newFileNameIncludingExtension,
			CharSequence content) {
		FileURI existingFileURI = getFileURIFromModuleName(existingModuleName);
		FileURI newFileURI = existingFileURI.getParent().appendSegment(newFileNameIncludingExtension);
		createFileOnDiskWithoutNotification(newFileURI, content);
	}

	/** Create a new file on disk without notifying the LSP server about it. */
	protected void createFileOnDiskWithoutNotification(FileURI fileURI, CharSequence content) {
		Path filePath = fileURI.toJavaIoFile().toPath();
		try {
			Files.writeString(filePath, content, StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			throw new RuntimeException("exception while creating file on disk", e);
		}
	}

	/** Same as {@link #deleteFileOnDiskWithoutNotification(FileURI)}, accepting a module name. */
	protected void deleteFileOnDiskWithoutNotification(String moduleName) {
		FileURI fileURI = getFileURIFromModuleName(moduleName);
		deleteFileOnDiskWithoutNotification(fileURI);
	}

	/** Delete an existing file on disk without notifying the LSP server. */
	protected void deleteFileOnDiskWithoutNotification(FileURI fileURI) {
		Path filePath = fileURI.toJavaIoFile().toPath();
		try {
			Files.delete(filePath);
		} catch (IOException e) {
			throw new RuntimeException("exception while deleting file on disk", e);
		}
	}

	/** Same as {@link #renameFileOnDiskWithoutNotification(FileURI, String)}, accepting a module name. */
	protected void renameFileOnDiskWithoutNotification(String moduleName, String newFileNameIncludingExtension) {
		FileURI fileURI = getFileURIFromModuleName(moduleName);
		renameFileOnDiskWithoutNotification(fileURI, newFileNameIncludingExtension);
	}

	/** Rename an existing file on disk without notifying the LSP server about it. */
	protected void renameFileOnDiskWithoutNotification(FileURI fileURI, String newFileNameIncludingExtension) {
		Path filePath = fileURI.toJavaIoFile().toPath();
		Path filePathRenamed = filePath.getParent().resolve(newFileNameIncludingExtension);
		try {
			Files.move(filePath, filePathRenamed);
		} catch (IOException e) {
			throw new RuntimeException("exception while renaming file on disk", e);
		}
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
	@SafeVarargs
	protected final void changeFileOnDiskWithoutNotification(String moduleName, Pair<String, String>... replacements) {
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
	@SafeVarargs
	protected final Pair<String, String> changeFileOnDiskWithoutNotification(FileURI fileURI,
			Pair<String, String>... replacements) {
		return changeFileOnDiskWithoutNotification(fileURI, content -> applyReplacements(content, replacements));
	}

	/**
	 * Changes a file on disk to the given new content without notifying the LSP server.
	 *
	 * @param fileURI
	 *            URI of the file to change.
	 * @param newContent
	 *            the new content to write to the file.
	 * @return a pair with the file's old content as key and its new content as value.
	 */
	protected Pair<String, String> changeFileOnDiskWithoutNotification(FileURI fileURI, String newContent) {
		return changeFileOnDiskWithoutNotification(fileURI, oldContent -> newContent);
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
			throw new AssertionError("IOException while reading file contents from disk", e);
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

	/** Asserts the build order of all projects in the workspace. */
	protected void assertProjectBuildOrder(String expectedProjectBuildOrder) {
		ProjectBuildOrderIterator iter = projectBuildOrderInfoProvider.get().getIterator().visitAll();
		String buildOrderString = org.eclipse.n4js.utils.Strings.toString(pd -> pd.getName(), () -> iter);
		assertEquals("Project build order did not match expectation.", expectedProjectBuildOrder, buildOrderString);
	}

	/** Both {@link #assertNoErrorsInLog()} and {@link #assertNoErrorsInOutput()}. */
	protected void assertNoErrorsInLogOrOutput() {
		assertNoErrorsInLog();
		assertNoErrorsInOutput();
	}

	/** Asserts that there are no ERRORs in the LSP log. */
	protected void assertNoErrorsInLog() {
		for (MessageParams msg : languageClient.getLogMessages()) {
			String message = Strings.nullToEmpty(msg.getMessage());
			assertNotEquals("Unexpected ERROR in log:\n" + message, MessageType.Error, msg.getType());
		}
	}

	/** Asserts that there are no ERRORs in the output streams. */
	static protected void assertNoErrorsInOutput() {
		String syserr = SYSTEM_OUT_REDIRECTER.getSystemErr();
		assertFalse("an error was logged to System.err during the test:" + System.lineSeparator() + syserr,
				syserr.contains("ERROR"));
		String sysout = SYSTEM_OUT_REDIRECTER.getSystemOut();
		assertFalse("an error was logged to System.out during the test:" + System.lineSeparator() + sysout,
				sysout.contains("ERROR"));
	}

	/**
	 * Asserts that there are no issues in the entire workspace and in open editors. See
	 * {@link #assertIssuesInBuilderAndEditors(Iterable, Map)} for details.
	 */
	protected void assertNoIssuesInBuilderAndEditors(Iterable<String> moduleNamesForEditors) {
		assertIssuesInBuilderAndEditors(moduleNamesForEditors, Collections.emptyMap());
	}

	/**
	 * Same as {@link #assertIssuesInBuilderAndEditors(Iterable, Map)}, accepting pairs from module name to issue list
	 * instead of a map from file URI to issue list.
	 */
	@SafeVarargs
	protected final void assertIssuesInBuilderAndEditors(Iterable<String> moduleNamesForEditors,
			Pair<String, List<String>>... moduleNameToExpectedIssues) {

		assertIssuesInBuilderAndEditors(moduleNamesForEditors,
				convertModuleNamePairsToIdMap(moduleNameToExpectedIssues));
	}

	/**
	 * Asserts that there are the given issues in the workspace (as done by {@link #assertIssues(Map)}) <em>and</em>
	 * also opens editors for the modules given in 'moduleNamesForEditors' to assert that the same issues appear in the
	 * validation performed inside the editors.
	 */
	protected void assertIssuesInBuilderAndEditors(Iterable<String> moduleNamesForEditors,
			Map<FileURI, List<String>> fileURIToExpectedIssues) {

		if (IterableExtensions.isEmpty(moduleNamesForEditors)) {
			throw new IllegalArgumentException("no module names for editors given");
		}

		// in builder:
		assertIssues(fileURIToExpectedIssues);

		// in editors:
		for (String moduleName : moduleNamesForEditors) {
			openFile(moduleName);
			joinServerRequests();
			assertIssues(fileURIToExpectedIssues);
			closeFile(moduleName);
			joinServerRequests();
			assertIssues(fileURIToExpectedIssues);
		}
	}

	/** Asserts that there are no issues in the entire workspace. */
	protected void assertNoIssues() {
		assertIssues(Collections.emptyMap());
	}

	/**
	 * Asserts that there are no errors in the entire workspace. Issues of other severity than
	 * {@link DiagnosticSeverity#Error ERROR} are ignored.
	 * <p>
	 * Does not check for errors on stdout and in the log; use {@link #assertNoErrorsInLogOrOutput()} for that.
	 */
	protected void assertNoErrors() {
		Multimap<FileURI, Diagnostic> allErrors = languageClient.getErrors();
		if (!allErrors.isEmpty()) {
			Assert.fail("expected no errors in workspace, but found " + allErrors.size() + " errors:\n"
					+ issuesToString(Multimaps.transformValues(allErrors, languageClient::getIssueString)));
		}
	}

	/**
	 * Same as {@link #assertIssues(Map)}, accepting pairs from module name to issue list instead of a map from file URI
	 * to issue list.
	 */
	@SafeVarargs
	protected final void assertIssues(Pair<String, List<String>>... moduleNameToExpectedIssues) {
		assertIssues(convertModuleNamePairsToIdMap(moduleNameToExpectedIssues));
	}

	/**
	 * Same as {@link #assertIssues(Map, boolean)}, but with <code>withIgnoredIssues</code> always set to
	 * <code>false</code>.
	 */
	protected void assertIssues(Map<FileURI, List<String>> fileURIToExpectedIssues) {
		assertIssues(fileURIToExpectedIssues, false);
	}

	/**
	 * Same as {@link #assertIssuesInFiles(Map, boolean)}, but in addition to checking the files denoted by the given
	 * map's keys, this method will also assert that the remaining files in the workspace do not contain any issues.
	 * Flag <code>withIgnoredIssues</code> applies to those issues accordingly.
	 */
	protected void assertIssues(Map<FileURI, List<String>> fileURIToExpectedIssues, boolean withIgnoredIssues) {
		// check given expectations
		assertIssuesInFiles(fileURIToExpectedIssues, withIgnoredIssues);
		Set<FileURI> checkedModules = fileURIToExpectedIssues.keySet();

		// check that there are no other issues in the workspace
		Multimap<FileURI, Diagnostic> allIssues = languageClient.getIssues();
		Set<FileURI> modulesWithIssues = allIssues.keySet();
		Set<FileURI> uncheckedModulesWithIssues = new LinkedHashSet<>(modulesWithIssues);
		uncheckedModulesWithIssues.removeAll(checkedModules);
		if (!uncheckedModulesWithIssues.isEmpty()) {
			Multimap<FileURI, String> issuesPerUncheckedModule = LinkedHashMultimap.create();
			for (FileURI currModuleURI : uncheckedModulesWithIssues) {
				List<String> currModuleIssuesAsList = getIssuesInFile(currModuleURI, withIgnoredIssues);
				issuesPerUncheckedModule.putAll(currModuleURI, currModuleIssuesAsList);
			}
			if (!issuesPerUncheckedModule.isEmpty()) { // empty if all remaining issues are ignored
				String msg = fileURIToExpectedIssues.size() == 0
						? "expected no issues in workspace but found one or more issues:"
						: "found one or more unexpected issues in workspace:";
				Assert.fail(msg + "\n" + issuesToString(issuesPerUncheckedModule));
			}
		}
	}

	/**
	 * Same as {@link #assertIssuesInFiles(Map)}, accepting pairs from module name to issue list instead of a map from
	 * file URI to issue list.
	 */
	@SafeVarargs
	protected final void assertIssuesInModules(Pair<String, List<String>>... moduleNameToExpectedIssues) {
		assertIssuesInFiles(convertModuleNamePairsToIdMap(moduleNameToExpectedIssues));
	}

	/**
	 * Same as {@link #assertIssuesInFiles(Map, boolean)}, but with <code>withIgnoredIssues</code> always set to
	 * <code>false</code>.
	 */
	protected void assertIssuesInFiles(Map<FileURI, List<String>> fileURIToExpectedIssues) {
		assertIssuesInFiles(fileURIToExpectedIssues, false);
	}

	/**
	 * Asserts issues in the files denoted by the given map's keys. Files for which the given map does not contain any
	 * IDs are *not* checked to be free of issues! If this is desired use method {@link #assertIssues(Map, boolean)}
	 * instead.
	 *
	 * @param fileURIToExpectedIssues
	 *            a map from module IDs to the list of expected issues in each case.
	 * @param withIgnoredIssues
	 *            iff <code>true</code>, even issues with an issue code that is among those returned by method
	 *            {@link #getIgnoredIssueCodes()} will be taken into consideration.
	 */
	protected void assertIssuesInFiles(Map<FileURI, List<String>> fileURIToExpectedIssues, boolean withIgnoredIssues) {
		List<String> failureMessages = new ArrayList<>();
		for (Entry<FileURI, List<String>> pair : fileURIToExpectedIssues.entrySet()) {
			FileURI fileURI = pair.getKey();
			List<String> expectedIssues = pair.getValue();

			List<String> actualIssues = getIssuesInFile(fileURI, withIgnoredIssues);
			Set<String> actualIssuesAsSet = IterableExtensions.toSet(
					Iterables.transform(actualIssues, String::trim));
			Set<String> expectedIssuesAsSet = IterableExtensions.toSet(
					Iterables.transform(expectedIssues, String::trim));

			if (!Objects.equals(expectedIssuesAsSet, actualIssuesAsSet)) {
				String indent = "    ";
				String fileRelPath = getRelativePathFromFileUri(fileURI);
				failureMessages.add("issues in file " + fileRelPath + " do not meet expectation\n"
						+ "EXPECTED:\n"
						+ issuesToSortedString(expectedIssuesAsSet, indent) + "\n"
						+ "ACTUAL:\n"
						+ issuesToSortedString(actualIssuesAsSet, indent));
			}
		}
		if (failureMessages.size() == 1) {
			Assert.fail(failureMessages.get(0));
		} else if (failureMessages.size() > 1) {
			Collections.sort(failureMessages);
			Assert.fail("issues in several files do not meet the expectation:\n"
					+ Joiner.on("\n").join(failureMessages));
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

	/**
	 * Joins the given issues into a single string.
	 * <p>
	 * In case only a map with diagnostics is available, use
	 * {@link Multimaps#transformValues(Multimap, com.google.common.base.Function) transformValues()} as follows:
	 *
	 * <pre>
	 * Multimaps.transformValues(uriToDiagnostic, languageClient::getIssueString)
	 * </pre>
	 */
	protected String issuesToString(Multimap<FileURI, String> issuesPerFile) {
		if (issuesPerFile.isEmpty()) {
			return "<none>";
		}
		StringBuilder sb = new StringBuilder();
		List<FileURI> sortedFileURIs = IterableExtensions.sortWith(issuesPerFile.keySet(),
				Comparator.comparing(FileURI::toString));
		for (FileURI currFileURI : sortedFileURIs) {
			if (sb.length() > 0) {
				sb.append('\n');
			}
			sb.append(getRelativePathFromFileUri(currFileURI));
			sb.append(":\n    ");
			Collection<String> currFileIssues = issuesPerFile.get(currFileURI);
			sb.append(issuesToSortedString(currFileIssues, "    "));
		}
		return sb.toString();
	}

	/** Sorts the given issues and joins them into a single string. */
	protected String issuesToSortedString(Iterable<String> issues, String indent) {
		if (Iterables.isEmpty(issues)) {
			return indent + "<none>";
		}
		return IterableExtensions.sort(issues).stream()
				.map(issue -> issue.replace("\n", "\n" + indent))
				.collect(Collectors.joining("\n" + indent));
	}

	@SafeVarargs
	private List<TextDocumentContentChangeEvent> replacementsToChangeEvents(String content,
			Pair<String, String>... replacements) {

		return replacementsToChangeEvents(new XDocument(0, content), replacements);
	}

	@SafeVarargs
	private List<TextDocumentContentChangeEvent> replacementsToChangeEvents(XDocument document,
			Pair<String, String>... replacements) {

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

	@SafeVarargs
	private <T> Map<FileURI, T> convertModuleNamePairsToIdMap(Pair<String, T>... moduleNameToExpectedIssues) {
		return Stream.of(moduleNameToExpectedIssues).collect(Collectors.toMap(
				p -> getFileURIFromModuleName(p.getKey()), Pair::getValue));
	}

	/** Same as {@link #getResourceDescriptionFromIndex(String, FileURI)}, assuming file belongs to default project. */
	protected IResourceDescription getResourceDescriptionFromIndex(FileURI fileURI) {
		return getResourceDescriptionFromIndex(DEFAULT_PROJECT_NAME, fileURI);
	}

	/** Reads the resource description of the source file with the given URI from the index. */
	protected IResourceDescription getResourceDescriptionFromIndex(String projectName, FileURI fileURI) {
		ResourceDescriptionsData index = concurrentIndex.getProjectIndex(projectName);
		return index.getResourceDescription(fileURI.toURI());
	}

	/**
	 * Translates a given module name to a file URI used in LSP call data. Also supports package.json files. For
	 * details, see {@link TestWorkspaceManager#getFileURIFromModuleName(String) here}.
	 */
	protected FileURI getFileURIFromModuleName(String moduleName) {
		return testWorkspaceManager.getFileURIFromModuleName(moduleName);
	}

	/** Converts an URI string as received by the LSP server to a {@link FileURI}. */
	protected FileURI getFileURIFromURIString(String uriString) {
		return new FileURI(URI.createURI(uriString));
	}

	/**
	 * Returns the files's path and name relative to the test workspace's {@link #getRoot() root folder}. Intended for
	 * use in output presented to the user (e.g. messages of assertion errors).
	 */
	protected String getRelativePathFromFileUri(FileURI fileURI) {
		URI relativeUri = lspBuilder.makeWorkspaceRelative(fileURI.toURI());
		return relativeUri.toFileString();
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

	/** */
	protected static String toUnixLineSeparator(CharSequence cs) {
		return cs.toString().replaceAll("\r?\n", "\n");
	}

	/** */
	protected static FileURI toFileURI(File file) {
		return new FileURI(file);
	}

	/** */
	protected static FileURI toFileURI(Path path) {
		return new FileURI(path.toFile());
	}

	/** Applies the given replacements to the given character sequence and returns the resulting string. */
	@SafeVarargs
	protected static String applyReplacements(CharSequence oldContent, Pair<String, String>... replacements) {
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

	/** Applies the given text edits to the given character sequence and returns the resulting string. */
	protected static String applyTextEdits(CharSequence oldContent, Iterable<? extends TextEdit> textEdits) {
		XDocument oldDocument = new XDocument(0, oldContent.toString(), true, true);
		XDocument newDocument = oldDocument.applyChanges(textEdits);
		return newDocument.getContents();
	}

	/** Sets and asserts all time attributes of the given file to the given time [ms] */
	protected static void setFileCreationDate(Path filePath, long millis) throws IOException {
		BasicFileAttributeView attributes = Files.getFileAttributeView(filePath, BasicFileAttributeView.class);
		FileTime time = FileTime.fromMillis(millis);
		attributes.setTimes(time, time, time);

		FileTime fileTime = Files.readAttributes(filePath, BasicFileAttributes.class).lastModifiedTime();
		assertEquals(millis, fileTime.toMillis());
	}
}
