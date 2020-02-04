/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensOptions;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.ColoringInformation;
import org.eclipse.lsp4j.ColoringParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.ExecuteCommandCapabilities;
import org.eclipse.lsp4j.ExecuteCommandOptions;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.InitializedParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.RenameOptions;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.SemanticHighlightingServerCapabilities;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureHelpOptions;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethodProvider;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageClientExtensions;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.eclipse.n4js.ide.server.HeadlessExtensionRegistrationHelper;
import org.eclipse.n4js.ide.xtext.server.XBuildManager.XBuildable;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
import org.eclipse.n4js.ide.xtext.server.concurrent.XRequestManager;
import org.eclipse.n4js.ide.xtext.server.findReferences.XWorkspaceResourceAccess;
import org.eclipse.n4js.ide.xtext.server.rename.XIRenameService;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.ICapabilitiesContributor;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.ILanguageServerAccess.IBuildListener;
import org.eclipse.xtext.ide.server.ILanguageServerExtension;
import org.eclipse.xtext.ide.server.ILanguageServerShutdownAndExitHandler;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.codelens.ICodeLensResolver;
import org.eclipse.xtext.ide.server.codelens.ICodeLensService;
import org.eclipse.xtext.ide.server.coloring.IColoringService;
import org.eclipse.xtext.ide.server.commands.ExecutableCommandRegistry;
import org.eclipse.xtext.ide.server.contentassist.ContentAssistService;
import org.eclipse.xtext.ide.server.formatting.FormattingService;
import org.eclipse.xtext.ide.server.hover.IHoverService;
import org.eclipse.xtext.ide.server.occurrences.IDocumentHighlightService;
import org.eclipse.xtext.ide.server.rename.IRenameService;
import org.eclipse.xtext.ide.server.rename.IRenameService2;
import org.eclipse.xtext.ide.server.semanticHighlight.SemanticHighlightingRegistry;
import org.eclipse.xtext.ide.server.signatureHelp.ISignatureHelpService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.IDocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.WorkspaceSymbolService;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.workspace.ISourceFolder;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Objects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings({ "restriction", "deprecation" })
public class XLanguageServerImpl implements LanguageServer, WorkspaceService, TextDocumentService, LanguageClientAware,
		Endpoint, JsonRpcMethodProvider, IBuildListener {

	private static final Logger LOG = Logger.getLogger(XLanguageServerImpl.class);

	@Inject
	private XRequestManager requestManager;

	@Inject
	private WorkspaceSymbolService workspaceSymbolService;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private IResourceServiceProvider.Registry languagesRegistry;

	@Inject
	private ExecutableCommandRegistry commandRegistry;

	@Inject
	private SemanticHighlightingRegistry semanticHighlightingRegistry;

	@Inject
	private ILanguageServerShutdownAndExitHandler shutdownAndExitHandler;

	@Inject
	private IssueAcceptor issueAcceptor;

	private XWorkspaceManager workspaceManager;

	private InitializeParams initializeParams;

	private InitializeResult initializeResult;

	private final CompletableFuture<InitializedParams> clientInitialized = new CompletableFuture<>();

	private final CompletableFuture<?> initBuildFinished = new CompletableFuture<>();

	private final CompletableFuture<?> initCleanFinished = new CompletableFuture<>();

	private XWorkspaceResourceAccess resourceAccess;

	private LanguageClient client;

	private Map<String, JsonRpcMethod> supportedMethods = null;

	private final Multimap<String, Endpoint> extensionProviders = LinkedListMultimap.<String, Endpoint> create();

	// TODO we should probably use the DisposableRegistry here
	/**
	 * Called by Guice to initialize the languages. This way it is guaranteed that the registration happens exactly
	 * once.
	 *
	 * @param helper
	 *            the registrar
	 */
	@Inject
	public void registerExtensions(HeadlessExtensionRegistrationHelper helper) {
		helper.unregisterExtensions();
		helper.registerExtensions();
	}

	/**
	 * Setter
	 */
	@Inject
	public void setWorkspaceManager(XWorkspaceManager manager) {
		workspaceManager = manager;
		resourceAccess = new XWorkspaceResourceAccess(workspaceManager);
	}

	private Set<? extends IResourceServiceProvider> getAllLanguages() {
		// provide a stable order
		Map<String, IResourceServiceProvider> sorted = new TreeMap<>();
		for (String ext : languagesRegistry.getExtensionToFactoryMap().keySet()) {
			sorted.put(ext, languagesRegistry.getResourceServiceProvider(URI.createURI("synth:///file." + ext)));
		}
		return ImmutableSet.copyOf(sorted.values());
	}

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		if (initializeParams != null) {
			throw new IllegalStateException("This language server has already been initialized.");
		}
		URI baseDir = getBaseDir(params);
		if (languagesRegistry.getExtensionToFactoryMap().isEmpty()) {
			throw new IllegalStateException(
					"No Xtext languages have been registered. Please make sure you have added the languages\'s setup class in \'/META-INF/services/org.eclipse.xtext.ISetup\'");
		}
		this.initializeParams = params;

		access.addBuildListener(this);

		Stopwatch sw = Stopwatch.createStarted();
		LOG.info("Start server initialization in workspace directory " + baseDir);
		workspaceManager.initialize(baseDir);
		workspaceManager.refreshWorkspaceConfig();
		LOG.info("Server initialization done after " + sw);

		initializeResult = new InitializeResult();
		initializeResult.setCapabilities(createServerCapabilities(params));

		return CompletableFuture.completedFuture(initializeResult);
	}

	/**
	 * Configure the server capabilities for this instance.
	 *
	 * @param params
	 *            the initialization parameters
	 * @return the server capabilities
	 */
	protected ServerCapabilities createServerCapabilities(InitializeParams params) {
		ServerCapabilities serverCapabilities = new ServerCapabilities();
		serverCapabilities.setHoverProvider(true);
		serverCapabilities.setDefinitionProvider(true);
		serverCapabilities.setReferencesProvider(true);
		serverCapabilities.setDocumentSymbolProvider(true);
		serverCapabilities.setWorkspaceSymbolProvider(true);
		Set<? extends IResourceServiceProvider> allLanguages = getAllLanguages();
		if (allLanguages.stream().anyMatch((serviceProvider) -> serviceProvider.get(ICodeLensService.class) != null)) {
			CodeLensOptions codeLensOptions = new CodeLensOptions();
			codeLensOptions.setResolveProvider(allLanguages.stream()
					.anyMatch((serviceProvider) -> serviceProvider.get(ICodeLensResolver.class) != null));
			serverCapabilities.setCodeLensProvider(codeLensOptions);
		}
		serverCapabilities.setCodeActionProvider(allLanguages.stream()
				.anyMatch((serviceProvider) -> serviceProvider.get(ICodeActionService.class) != null
						|| serviceProvider.get(ICodeActionService2.class) != null));

		serverCapabilities.setSignatureHelpProvider(new SignatureHelpOptions(ImmutableList.of("(", ",")));
		serverCapabilities.setTextDocumentSync(TextDocumentSyncKind.Incremental);
		CompletionOptions completionOptions = new CompletionOptions();
		completionOptions.setResolveProvider(false);
		completionOptions.setTriggerCharacters(ImmutableList.of("."));
		serverCapabilities.setCompletionProvider(completionOptions);
		serverCapabilities.setDocumentFormattingProvider(true);
		serverCapabilities.setDocumentRangeFormattingProvider(true);
		serverCapabilities.setDocumentHighlightProvider(true);
		ClientCapabilities clientCapabilities = null;
		if (params != null) {
			clientCapabilities = params.getCapabilities();
		}
		TextDocumentClientCapabilities textDocument = null;
		if (clientCapabilities != null) {
			textDocument = clientCapabilities.getTextDocument();
		}
		RenameCapabilities rename = null;
		if (textDocument != null) {
			rename = textDocument.getRename();
		}
		Boolean prepareSupport = null;
		if (rename != null) {
			prepareSupport = rename.getPrepareSupport();
		}
		boolean clientPrepareSupport = Objects.equal(Boolean.TRUE, prepareSupport);
		if (clientPrepareSupport && allLanguages.stream()
				.anyMatch(serviceProvider -> serviceProvider.get(IRenameService2.class) != null)) {
			RenameOptions renameOptions = new RenameOptions();
			renameOptions.setPrepareProvider(true);
			serverCapabilities.setRenameProvider(Either.<Boolean, RenameOptions> forRight(renameOptions));
		} else {
			serverCapabilities.setRenameProvider(Either.forLeft(allLanguages.stream()
					.anyMatch((serviceProvider) -> serviceProvider.get(IRenameService.class) != null
							|| serviceProvider.get(IRenameService2.class) != null)));
		}
		WorkspaceClientCapabilities workspace = null;
		if (clientCapabilities != null) {
			workspace = clientCapabilities.getWorkspace();
		}
		ExecuteCommandCapabilities executeCommand = null;
		if (workspace != null) {
			executeCommand = workspace.getExecuteCommand();
		}
		if (executeCommand != null) {
			commandRegistry.initialize(allLanguages, clientCapabilities, client);
			ExecuteCommandOptions executeCommandOptions = new ExecuteCommandOptions();
			executeCommandOptions.setCommands(commandRegistry.getCommands());
			serverCapabilities.setExecuteCommandProvider(executeCommandOptions);
		}
		semanticHighlightingRegistry.initialize(allLanguages, clientCapabilities, client);
		serverCapabilities.setSemanticHighlighting(new SemanticHighlightingServerCapabilities(
				semanticHighlightingRegistry.getAllScopes()));

		for (IResourceServiceProvider language : allLanguages) {
			ICapabilitiesContributor capabilitiesContributor = language.get(ICapabilitiesContributor.class);
			if (capabilitiesContributor != null) {
				capabilitiesContributor.contribute(serverCapabilities, params);
			}
		}
		return serverCapabilities;
	}

	@Override
	public void initialized(InitializedParams params) {
		clientInitialized.complete(params);
		clientInitialized.join();

		requestManager.runWrite(
				() -> {
					try {
						Stopwatch sw = Stopwatch.createStarted();
						LOG.info("Start initial build ...");

						workspaceManager.doInitialBuild(CancelIndicator.NullImpl);
						initBuildFinished.complete(null);
						LOG.info("Initial build done after " + sw);
					} catch (Throwable t) {
						t.printStackTrace();
						initBuildFinished.completeExceptionally(t);
					}
					return null;
				},
				(cancelIndicator, it) -> it);
	}

	@Deprecated
	private URI deprecatedToBaseDir(InitializeParams params) {
		String rootPath = params.getRootPath();
		if (rootPath != null) {
			return uriExtensions.toUri(uriExtensions.toUriString(URI.createFileURI(rootPath)));
		}
		return null;
	}

	/**
	 * Compute the base dir.
	 */
	protected URI getBaseDir(InitializeParams params) {
		String rootUri = params.getRootUri();
		if (rootUri != null) {
			return uriExtensions.toUri(rootUri);
		}
		return deprecatedToBaseDir(params);
	}

	@SuppressWarnings("hiding")
	@Override
	public void connect(LanguageClient client) {
		this.client = client;
		issueAcceptor.connect(client);
	}

	/**
	 * Discard all references to the language client.
	 */
	public void disconnect() {
		issueAcceptor.disconnect();
		this.client = null;
	}

	@Override
	public void exit() {
		LOG.info("Received exit notification");
		joinJobs();
		shutdownAndExitHandler.exit();
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		LOG.info("Start shutdown");

		disconnect();
		runBuildable(() -> {
			XBuildable buildable = workspaceManager.closeAll();
			return (cancelIndicator) -> {
				List<Delta> result = buildable.build(cancelIndicator);
				workspaceManager.persistProjectState(CancelIndicator.NullImpl);
				clientInitialized.complete(null);
				initBuildFinished.complete(null);
				shutdownAndExitHandler.shutdown();

				LOG.info("Shutdown done");
				return result;
			};
		});
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public TextDocumentService getTextDocumentService() {
		return this;
	}

	@Override
	public WorkspaceService getWorkspaceService() {
		return this;
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		runBuildable(() -> toBuildable(params));
	}

	/**
	 * Evaluate the params and deduce the respective build command.
	 */
	protected XBuildable toBuildable(DidOpenTextDocumentParams params) {
		TextDocumentItem textDocument = params.getTextDocument();
		return workspaceManager.didOpen(getURI(textDocument),
				textDocument.getVersion(), textDocument.getText());
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		if (isSourceFileOrOpen(uriExtensions.toUri(params.getTextDocument().getUri()))) {
			runBuildable(() -> toBuildable(params));
		}
	}

	private boolean isSourceFileOrOpen(URI uri) {
		if (workspaceManager.isDocumentOpen(uri)) {
			return true;
		}
		return isSourceFile(uri);
	}

	private boolean isSourceFile(URI uri) {
		IProjectConfig projectConfig = workspaceManager.getWorkspaceConfig().findProjectContaining(uri);
		if (projectConfig != null) {
			ISourceFolder sourceFolder = projectConfig.findSourceFolderContaining(uri);
			if (sourceFolder != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Evaluate the params and deduce the respective build command.
	 */
	protected XBuildable toBuildable(DidChangeTextDocumentParams params) {
		VersionedTextDocumentIdentifier textDocument = params.getTextDocument();
		return workspaceManager.didChangeTextDocumentContent(getURI(textDocument),
				textDocument.getVersion(), params.getContentChanges());
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		runBuildable(() -> toBuildable(params));
	}

	/**
	 * Evaluate the params and deduce the respective build command.
	 */
	protected XBuildable toBuildable(DidCloseTextDocumentParams params) {
		return workspaceManager.didClose(getURI(params.getTextDocument()));
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		runBuildable(() -> toBuildable(params));
	}

	/**
	 * Evaluate the params and deduce the respective build command.
	 */
	protected XBuildable toBuildable(DidSaveTextDocumentParams params) {
		return workspaceManager.didSave(getURI(params.getTextDocument()));
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		// TODO: Set watched files to client. Note: Client may have performance issues with lots of folders to watch.
		final List<URI> dirtyFiles = new ArrayList<>();
		final List<URI> deletedFiles = new ArrayList<>();
		for (FileEvent fileEvent : params.getChanges()) {
			URI uri = uriExtensions.toUri(fileEvent.getUri());

			String fileName = uri.lastSegment();
			boolean skipFile = fileName.equals(ProjectStatePersister.FILENAME) || workspaceManager.isDocumentOpen(uri);

			if (!skipFile && isSourceFile(uri)) {
				FileChangeType changeType = fileEvent.getType();

				if (changeType == FileChangeType.Deleted) {
					deletedFiles.add(uri);
				} else {
					dirtyFiles.add(uri);
				}
			}
		}
		if (!dirtyFiles.isEmpty() || !deletedFiles.isEmpty()) {
			runBuildable(() -> workspaceManager.didChangeFiles(dirtyFiles, deletedFiles));
		}
	}

	/** Deletes all generated files and clears the type index. */
	public void clean() {
		requestManager.runWrite(() -> {

			try {
				workspaceManager.clean(CancelIndicator.NullImpl);
				initCleanFinished.complete(null);
			} catch (Throwable t) {
				t.printStackTrace();
				initCleanFinished.completeExceptionally(t);
			}

			return null;
		}, (a, b) -> null);
	}

	/**
	 * Compute a buildable and run the build in a write action
	 *
	 * @param newBuildable
	 *            the factory for the buildable.
	 * @return the result.
	 */
	protected CompletableFuture<List<Delta>> runBuildable(Supplier<? extends XBuildable> newBuildable) {
		return requestManager.runWrite(newBuildable::get,
				(cancelIndicator, buildable) -> buildable.build(cancelIndicator));
	}

	private void joinJobs() {
		// wait for all jobs to finish
		runBuildable(() -> {
			return (cancelIndicator) -> Collections.emptyList();
		});
	}

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		requestManager.runWrite(() -> {
			workspaceManager.refreshWorkspaceConfig();
			workspaceManager.doInitialBuild(CancelIndicator.NullImpl);
			return null;
		}, (a, b) -> null);
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
		return requestManager.runRead((cancelIndicator) -> completion(cancelIndicator, params));
	}

	/**
	 * Compute the completion items.
	 */
	protected Either<List<CompletionItem>, CompletionList> completion(CancelIndicator originalCancelIndicator,
			CompletionParams params) {
		URI uri = getURI(params);
		ContentAssistService contentAssistService = getService(uri, ContentAssistService.class);
		if (contentAssistService == null) {
			return Either.forRight(new CompletionList());
		}
		BufferedCancelIndicator cancelIndicator = new BufferedCancelIndicator(
				originalCancelIndicator,
				Duration.ofMillis(750));
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return Either.forRight(contentAssistService.createCompletionList(doc, res, params, cancelIndicator));
	}

	/**
	 * Obtain the URI from the given parameters.
	 */
	protected URI getURI(TextDocumentPositionParams params) {
		return getURI(params.getTextDocument());
	}

	/**
	 * Obtain the URI from the given identifier.
	 */
	protected URI getURI(TextDocumentIdentifier documentIdentifier) {
		return uriExtensions.toUri(documentIdentifier.getUri());
	}

	/**
	 * Obtain the URI from the given document item.
	 */
	protected URI getURI(TextDocumentItem documentItem) {
		return uriExtensions.toUri(documentItem.getUri());
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			TextDocumentPositionParams params) {
		return requestManager.runRead(cancelIndicator -> definition(params, cancelIndicator));
	}

	/**
	 * Compute the definition. Executed in a read request.
	 */
	protected Either<List<? extends Location>, List<? extends LocationLink>> definition(
			TextDocumentPositionParams params, CancelIndicator cancelIndicator) {
		return Either.forLeft(definition(cancelIndicator, params));
	}

	/**
	 * Compute the definition.
	 */
	protected List<? extends Location> definition(CancelIndicator cancelIndicator,
			TextDocumentPositionParams params) {
		URI uri = getURI(params);
		DocumentSymbolService documentSymbolService = getService(uri, DocumentSymbolService.class);
		if (documentSymbolService == null) {
			return Collections.emptyList();
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return documentSymbolService.getDefinitions(doc, res, params, resourceAccess, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		return requestManager.runRead(cancelIndicator -> references(params, cancelIndicator));
	}

	/**
	 * Compute the references. Executed in read request.
	 */
	protected List<? extends Location> references(ReferenceParams params, CancelIndicator cancelIndicator) {
		URI uri = getURI(params);
		DocumentSymbolService documentSymbolService = getService(uri, DocumentSymbolService.class);
		if ((documentSymbolService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return documentSymbolService.getReferences(doc, res, params, resourceAccess, workspaceManager.getIndex(),
				cancelIndicator);
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {
		return requestManager.runRead((cancelIndicator) -> documentSymbol(params, cancelIndicator));
	}

	/**
	 * Compute the symbol information. Executed in a read request.
	 */
	protected List<Either<SymbolInformation, DocumentSymbol>> documentSymbol(DocumentSymbolParams params,
			CancelIndicator cancelIndicator) {
		URI uri = getURI(params.getTextDocument());
		IDocumentSymbolService documentSymbolService = getIDocumentSymbolService(getResourceServiceProvider(uri));
		if ((documentSymbolService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return documentSymbolService.getSymbols(doc, res, params, cancelIndicator);
	}

	/**
	 * @since 2.16
	 */
	protected IDocumentSymbolService getIDocumentSymbolService(IResourceServiceProvider serviceProvider) {
		if ((serviceProvider == null)) {
			return null;
		}
		if (isHierarchicalDocumentSymbolSupport()) {
			return serviceProvider.get(HierarchicalDocumentSymbolService.class);
		} else {
			return serviceProvider.get(DocumentSymbolService.class);
		}
	}

	/**
	 * {@code true} if the {@code TextDocumentClientCapabilities} explicitly declares the hierarchical document symbol
	 * support at LS initialization time. Otherwise, false.
	 */
	protected boolean isHierarchicalDocumentSymbolSupport() {
		ClientCapabilities capabilities = initializeParams.getCapabilities();
		if (capabilities != null) {
			TextDocumentClientCapabilities textDocument = capabilities.getTextDocument();
			if (textDocument != null) {
				DocumentSymbolCapabilities documentSymbol = textDocument.getDocumentSymbol();
				if (documentSymbol != null) {
					Boolean hierarchicalDocumentSymbolSupport = documentSymbol.getHierarchicalDocumentSymbolSupport();
					if (hierarchicalDocumentSymbolSupport != null) {
						return hierarchicalDocumentSymbolSupport;
					}
				}
			}
		}
		return false;
	}

	@Override
	public CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params) {
		return requestManager.runRead((cancelIndicator) -> symbol(params, cancelIndicator));
	}

	/**
	 * Compute the symbol information. Executed in a read request.
	 */
	protected List<? extends SymbolInformation> symbol(WorkspaceSymbolParams params, CancelIndicator cancelIndicator) {
		return workspaceSymbolService.getSymbols(params.getQuery(),
				resourceAccess, workspaceManager.getIndex(),
				cancelIndicator);
	}

	@Override
	public CompletableFuture<Hover> hover(TextDocumentPositionParams params) {
		return requestManager.runRead((cancelIndicator) -> hover(params, cancelIndicator));
	}

	/**
	 * Compute the hover. Executed in a read request.
	 */
	protected Hover hover(TextDocumentPositionParams params, CancelIndicator cancelIndicator) {
		URI uri = getURI(params);
		IHoverService hoverService = getService(uri, IHoverService.class);
		if (hoverService == null) {
			return IHoverService.EMPTY_HOVER;
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return hoverService.hover(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		return CompletableFuture.<CompletionItem> completedFuture(unresolved);
	}

	@Override
	public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams params) {
		return requestManager.runRead((cancelIndicator) -> signatureHelp(params, cancelIndicator));
	}

	/**
	 * Compute the signature help. Executed in a read request.
	 */
	protected SignatureHelp signatureHelp(TextDocumentPositionParams params, CancelIndicator cancelIndicator) {
		URI uri = getURI(params);
		ISignatureHelpService helper = getService(uri, ISignatureHelpService.class);
		if (helper == null) {
			return ISignatureHelpService.EMPTY;
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return helper.getSignatureHelp(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(
			TextDocumentPositionParams params) {
		return requestManager.runRead((cancelIndicator) -> documentHighlight(params, cancelIndicator));
	}

	/**
	 * Compute the document highlights. Executed in a read request.
	 */
	protected List<? extends DocumentHighlight> documentHighlight(TextDocumentPositionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = getURI(params);
		IDocumentHighlightService service = getService(uri, IDocumentHighlightService.class);
		if (service == null) {
			return Collections.emptyList();
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return service.getDocumentHighlights(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
		return requestManager.runRead((cancelIndicator) -> codeAction(params, cancelIndicator));
	}

	/**
	 * Compute the code action commands. Executed in a read request.
	 */
	protected List<Either<Command, CodeAction>> codeAction(CodeActionParams params, CancelIndicator cancelIndicator) {
		URI uri = getURI(params.getTextDocument());
		IResourceServiceProvider serviceProvider = getResourceServiceProvider(uri);
		ICodeActionService service = getService(serviceProvider, ICodeActionService.class);
		ICodeActionService2 service2 = getService(serviceProvider, ICodeActionService2.class);
		if (service == null && service2 == null) {
			return Collections.emptyList();
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);

		List<Either<Command, CodeAction>> result = new ArrayList<>();
		if (service != null) {
			List<Either<Command, CodeAction>> actions = service.getCodeActions(doc, res, params,
					cancelIndicator);
			if (actions != null) {
				result.addAll(actions);
			}
		}
		if (service2 != null) {
			ICodeActionService2.Options options = new ICodeActionService2.Options();
			options.setDocument(doc);
			options.setResource(res);
			options.setLanguageServerAccess(access);
			options.setCodeActionParams(params);
			options.setCancelIndicator(cancelIndicator);
			List<Either<Command, CodeAction>> actions = service2.getCodeActions(options);
			if (actions != null) {
				result.addAll(actions);
			}
		}
		return result;

	}

	/**
	 * Put the document uri into the data of the given code lenses.
	 */
	protected void installURI(List<? extends CodeLens> codeLenses, String uri) {
		for (CodeLens lens : codeLenses) {
			Object data = lens.getData();
			if (data != null) {
				lens.setData(Arrays.asList(uri, lens.getData()));
			} else {
				lens.setData(uri);
			}
		}
	}

	/**
	 * Remove the document uri from the data of the given code lense.
	 */
	protected URI uninstallURI(CodeLens lens) {
		URI result = null;
		Object data = lens.getData();
		if (data instanceof String) {
			result = URI.createURI(data.toString());
			lens.setData(null);
		} else {
			if (data instanceof List<?>) {
				List<?> l = ((List<?>) data);
				result = URI.createURI(l.get(0).toString());
				lens.setData(l.get(1));
			}
		}
		return result;
	}

	@Override
	public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
		return requestManager.runRead((cancelIndicator) -> codeLens(params, cancelIndicator));
	}

	/**
	 * Compute the code lenses. Executed in a read request.
	 */
	protected List<? extends CodeLens> codeLens(CodeLensParams params, CancelIndicator cancelIndicator) {
		URI uri = getURI(params.getTextDocument());
		ICodeLensService codeLensService = getService(uri, ICodeLensService.class);
		if ((codeLensService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		List<? extends CodeLens> result = codeLensService.computeCodeLenses(doc, res, params, cancelIndicator);
		installURI(result, uri.toString());
		return result;
	}

	@Override
	public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
		URI uri = uninstallURI(unresolved);
		if ((uri == null)) {
			return CompletableFuture.completedFuture(unresolved);
		}
		return requestManager.runRead((cancelIndicator) -> resolveCodeLens(uri, unresolved, cancelIndicator));
	}

	/**
	 * Resolve the given code lens. Executed in a read request.
	 */
	protected CodeLens resolveCodeLens(URI uri, CodeLens unresolved, CancelIndicator cancelIndicator) {
		ICodeLensResolver resolver = getService(uri, ICodeLensResolver.class);
		if (resolver == null) {
			return unresolved;
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return resolver.resolveCodeLens(doc, res, unresolved, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
		return requestManager.runRead((cancelIndicator) -> formatting(params, cancelIndicator));
	}

	/**
	 * Create the text edits for the formatter. Executed in a read request.
	 */
	protected List<? extends TextEdit> formatting(DocumentFormattingParams params, CancelIndicator cancelIndicator) {
		URI uri = getURI(params.getTextDocument());
		FormattingService formatterService = getService(uri, FormattingService.class);
		if ((formatterService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return formatterService.format(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
		return requestManager.runRead((cancelIndicator) -> rangeFormatting(params, cancelIndicator));
	}

	/**
	 * Create the text edits for the formatter. Executed in a read request.
	 */
	protected List<? extends TextEdit> rangeFormatting(DocumentRangeFormattingParams params,
			CancelIndicator cancelIndicator) {
		URI uri = getURI(params.getTextDocument());
		FormattingService formatterService = getService(uri, FormattingService.class);
		if ((formatterService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = workspaceManager.getResource(uri);
		XDocument doc = workspaceManager.getDocument(res);
		return formatterService.format(doc, res, params, cancelIndicator);
	}

	/**
	 * @param uri
	 *            the current URI
	 * @param type
	 *            the type of the service
	 * @return the service instance or null if the language does not exist or if it does not expose the service.
	 */
	protected <Service> Service getService(URI uri, Class<Service> type) {
		return getService(getResourceServiceProvider(uri), type);
	}

	/**
	 * @param <Service>
	 *            the type of the service
	 * @param resourceServiceProvider
	 *            the resource service provider. May be null
	 * @param type
	 *            the type of the service
	 * @return the service instance or null if not available.
	 */
	protected <Service> Service getService(IResourceServiceProvider resourceServiceProvider, Class<Service> type) {
		if (resourceServiceProvider == null) {
			return null;
		}
		return resourceServiceProvider.get(type);
	}

	@Override
	public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
		return requestManager.runRead((cancelIndicator) -> executeCommand(params, cancelIndicator));
	}

	/**
	 * Execute the command. Runs in a read request.
	 */
	protected Object executeCommand(ExecuteCommandParams params, CancelIndicator cancelIndicator) {
		return commandRegistry.executeCommand(params, access, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}

	@Override
	public CompletableFuture<WorkspaceEdit> rename(RenameParams renameParams) {
		return requestManager.runRead(cancelIndicator -> rename(renameParams, cancelIndicator));
	}

	/**
	 * Compute the rename edits. Executed in a read request.
	 */
	protected WorkspaceEdit rename(RenameParams renameParams, CancelIndicator cancelIndicator) {
		URI uri = getURI(renameParams.getTextDocument());

		IResourceServiceProvider resourceServiceProvider = getResourceServiceProvider(uri);
		XIRenameService renameServiceOld = getService(resourceServiceProvider, XIRenameService.class);
		if (renameServiceOld != null) {
			return renameServiceOld.rename(workspaceManager, renameParams, cancelIndicator);
		}
		IRenameService2 renameService2 = getService(resourceServiceProvider, IRenameService2.class);
		if ((renameService2 != null)) {
			IRenameService2.Options options = new IRenameService2.Options();
			options.setLanguageServerAccess(access);
			options.setRenameParams(renameParams);
			options.setCancelIndicator(cancelIndicator);
			return renameService2.rename(options);
		}
		return new WorkspaceEdit();
	}

	/**
	 * @param uri
	 *            the current URI
	 * @return the resource service provider or null.
	 */
	protected IResourceServiceProvider getResourceServiceProvider(URI uri) {
		return languagesRegistry.getResourceServiceProvider(uri);
	}

	/**
	 * @since 2.18
	 */
	@Override
	public CompletableFuture<Either<Range, PrepareRenameResult>> prepareRename(
			TextDocumentPositionParams params) {
		return requestManager.runRead(cancelIndicator -> prepareRename(params, cancelIndicator));
	}

	/**
	 * Prepare the rename operation. Executed in a read request.
	 */
	protected Either<Range, PrepareRenameResult> prepareRename(TextDocumentPositionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = getURI(params);
		IRenameService2 renameService = getService(uri, IRenameService2.class);
		if (renameService == null) {
			throw new UnsupportedOperationException();
		}
		IRenameService2.PrepareRenameOptions options = new IRenameService2.PrepareRenameOptions();
		options.setLanguageServerAccess(access);
		options.setParams(params);
		options.setCancelIndicator(cancelIndicator);
		return renameService.prepareRename(options);
	}

	@Override
	public void notify(String method, Object parameter) {
		for (Endpoint endpoint : extensionProviders.get(method)) {
			try {
				endpoint.notify(method, parameter);
			} catch (UnsupportedOperationException e) {
				if (e != ILanguageServerExtension.NOT_HANDLED_EXCEPTION) {
					throw e;
				}
			}
		}
	}

	@Override
	public CompletableFuture<?> request(String method, Object parameter) {
		if (!extensionProviders.containsKey(method)) {
			throw new UnsupportedOperationException("The json request \'" + method + "\' is unknown.");
		}
		for (Endpoint endpoint : extensionProviders.get(method)) {
			try {
				return endpoint.request(method, parameter);
			} catch (UnsupportedOperationException e) {
				if (e != ILanguageServerExtension.NOT_HANDLED_EXCEPTION) {
					throw e;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("hiding")
	@Override
	public Map<String, JsonRpcMethod> supportedMethods() {
		if (supportedMethods != null) {
			return supportedMethods;
		}
		synchronized (extensionProviders) {
			Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
			supportedMethods.putAll(ServiceEndpoints.getSupportedMethods(getClass()));

			Map<String, JsonRpcMethod> extensions = new LinkedHashMap<>();
			for (IResourceServiceProvider resourceServiceProvider : getAllLanguages()) {
				ILanguageServerExtension ext = resourceServiceProvider.get(ILanguageServerExtension.class);
				if (ext != null) {
					ext.initialize(access);
					Map<String, JsonRpcMethod> supportedExtensions = (ext instanceof JsonRpcMethodProvider)
							? ((JsonRpcMethodProvider) ext).supportedMethods()
							: ServiceEndpoints.getSupportedMethods(ext.getClass());
					for (Map.Entry<String, JsonRpcMethod> entry : supportedExtensions.entrySet()) {
						if (supportedMethods.containsKey(entry.getKey())) {
							LOG.error("The json rpc method \'" + entry.getKey()
									+ "\' can not be an extension as it is already defined in the LSP standard.");
						} else {
							JsonRpcMethod existing = extensions.put(entry.getKey(), entry.getValue());
							if (existing != null && !Objects.equal(existing, entry.getValue())) {
								LOG.error("An incompatible LSP extension \'" + entry.getKey()
										+ "\' has already been registered. Using 1 ignoring 2. \n1 : " + existing
										+ " \n2 : " + entry.getValue());
								extensions.put(entry.getKey(), existing);
							} else {
								Endpoint endpoint = ServiceEndpoints.toEndpoint(ext);
								extensionProviders.put(entry.getKey(), endpoint);
								supportedMethods.put(entry.getKey(), entry.getValue());
							}
						}
					}
				}
			}
			this.supportedMethods = supportedMethods;
			return supportedMethods;
		}
	}

	private final ILanguageServerAccess access = new ILanguageServerAccess() {
		@Override
		public <T> CompletableFuture<T> doRead(String uriStr, Function<ILanguageServerAccess.Context, T> function) {
			URI uri = uriExtensions.toUri(uriStr);
			XtextResource res = workspaceManager.getResource(uri);
			XDocument doc = workspaceManager.getDocument(res);
			return requestManager.runRead(cancelIndicator -> function.apply(new ILanguageServerAccess.Context(res, doc,
					workspaceManager.isDocumentOpen(res.getURI()),
					cancelIndicator)));
		}

		@Override
		public void addBuildListener(ILanguageServerAccess.IBuildListener listener) {
			workspaceManager.addBuildListener(listener);
		}

		@Override
		public LanguageClient getLanguageClient() {
			return client;
		}

		@Override
		public ResourceSet newLiveScopeResourceSet(URI uri) {
			XProjectManager projectManager = workspaceManager.getProjectManager(uri);
			XIndexState indexState = projectManager.getProjectStateHolder().getIndexState();
			XtextResourceSet resourceSet = projectManager.createNewResourceSet(indexState.getResourceDescriptions());
			resourceSet.getLoadOptions().put(ResourceDescriptionsProvider.LIVE_SCOPE, true);
			return resourceSet;
		}

		@Override
		public InitializeParams getInitializeParams() {
			return initializeParams;
		}

		@Override
		public <T> CompletableFuture<T> doReadIndex(
				Function<? super ILanguageServerAccess.IndexContext, ? extends T> function) {
			return requestManager.runRead(cancelIndicator -> function
					.apply(new ILanguageServerAccess.IndexContext(workspaceManager.getIndex(),
							cancelIndicator)));
		}

		@Override
		public InitializeResult getInitializeResult() {
			return initializeResult;
		}
	};

	@Override
	public void afterBuild(List<IResourceDescription.Delta> deltas) {
		for (IResourceDescription.Delta delta : deltas) {
			if (delta.getNew() != null && workspaceManager.isDocumentOpen(delta.getUri())) {
				String uriStr = delta.getUri().toString();

				access.doRead(uriStr, ctx -> {
					if (ctx.isDocumentOpen() && (ctx.getResource() instanceof XtextResource)
							&& client instanceof LanguageClientExtensions) {

						LanguageClientExtensions clientExtensions = (LanguageClientExtensions) client;
						XtextResource resource = ((XtextResource) ctx.getResource());
						IResourceServiceProvider resourceServiceProvider = resource.getResourceServiceProvider();
						IColoringService coloringService = resourceServiceProvider.get(IColoringService.class);

						if (coloringService != null) {
							Document doc = ctx.getDocument();
							List<? extends ColoringInformation> colInfos = coloringService.getColoring(resource, doc);

							if (!IterableExtensions.isNullOrEmpty(colInfos)) {
								String uri = resource.getURI().toString();
								ColoringParams colParams = new ColoringParams(uri, colInfos);
								clientExtensions.updateColoring(colParams);
							}
						}
					}

					semanticHighlightingRegistry.update(ctx);
					return null;
				});
			}
		}
	}

	/**
	 * @since 2.16
	 */
	protected ILanguageServerAccess getLanguageServerAccess() {
		return access;
	}

	/**
	 * @since 2.16
	 * @return instance of {@link LanguageClient} or null iff not available
	 */
	public LanguageClient getLanguageClient() {
		return client;
	}

	/**
	 * @since 2.16
	 */
	protected ExecutableCommandRegistry getCommandRegistry() {
		return commandRegistry;
	}

	/**
	 * @since 2.16
	 */
	protected Multimap<String, Endpoint> getExtensionProviders() {
		return ImmutableMultimap.copyOf(extensionProviders);
	}

	/**
	 * @since 2.16
	 */
	protected Map<String, JsonRpcMethod> getSupportedMethods() {
		return ImmutableMap.copyOf(supportedMethods);
	}

	/**
	 * @since 2.16
	 */
	protected IResourceServiceProvider.Registry getLanguagesRegistry() {
		return languagesRegistry;
	}

	/**
	 * @since 2.16
	 */
	protected IReferenceFinder.IResourceAccess getWorkspaceResourceAccess() {
		return resourceAccess;
	}

	/**
	 * @since 2.16
	 */
	protected XWorkspaceManager getWorkspaceManager() {
		return workspaceManager;
	}

	/**
	 * @since 2.16
	 */
	protected WorkspaceSymbolService getWorkspaceSymbolService() {
		return workspaceSymbolService;
	}

	/**
	 * Getter
	 */
	public XRequestManager getRequestManager() {
		return requestManager;

	}

	/** Blocks until the lsp client sent the initialized message */
	public void joinClientInitialized() {
		clientInitialized.join();
	}

	/** Blocks until the first build (i.e. the initial build) was finished building */
	public void joinInitBuildFinished() {
		initBuildFinished.join();
	}

	/** Blocks until the first clean was finished */
	public void joinCleanFinished() {
		initCleanFinished.join();
	}
}
