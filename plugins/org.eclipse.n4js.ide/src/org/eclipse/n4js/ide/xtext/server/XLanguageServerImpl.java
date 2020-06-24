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

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionKind;
import org.eclipse.lsp4j.CodeActionOptions;
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
import org.eclipse.n4js.ide.server.LspLogger;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentChunkedIndex.IChunkedIndexListener;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIssueRegistry;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIssueRegistry.IIssueRegistryListener;
import org.eclipse.n4js.ide.xtext.server.concurrent.ConcurrentIssueRegistry.IssueRegistryChangeEvent;
import org.eclipse.n4js.ide.xtext.server.concurrent.LSPExecutorService;
import org.eclipse.n4js.ide.xtext.server.contentassist.XContentAssistService;
import org.eclipse.n4js.ide.xtext.server.findReferences.XWorkspaceResourceAccess;
import org.eclipse.n4js.ide.xtext.server.openfiles.OpenFileContext;
import org.eclipse.n4js.ide.xtext.server.openfiles.OpenFilesManager;
import org.eclipse.n4js.ide.xtext.server.openfiles.OpenFilesManager.IOpenFilesListener;
import org.eclipse.n4js.ide.xtext.server.rename.XIRenameService;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.ide.server.ICapabilitiesContributor;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.ILanguageServerExtension;
import org.eclipse.xtext.ide.server.ILanguageServerShutdownAndExitHandler;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.codelens.ICodeLensResolver;
import org.eclipse.xtext.ide.server.codelens.ICodeLensService;
import org.eclipse.xtext.ide.server.coloring.IColoringService;
import org.eclipse.xtext.ide.server.commands.ExecutableCommandRegistry;
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
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @since 2.11
 */
@SuppressWarnings({ "restriction", "deprecation" })
@Singleton
public class XLanguageServerImpl implements LanguageServer, WorkspaceService, TextDocumentService, LanguageClientAware,
		Endpoint, JsonRpcMethodProvider, IChunkedIndexListener, IIssueRegistryListener, IOpenFilesListener {

	private static final Logger LOG = Logger.getLogger(XLanguageServerImpl.class);

	@Inject
	private OpenFilesManager openFilesManager;

	@Inject
	private LSPBuilder lspBuilder;

	@Inject
	private LSPExecutorService lspExecutorService;

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

	@Inject
	private LspLogger lspLogger;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	private InitializeParams initializeParams;

	private InitializeResult initializeResult;

	private final CompletableFuture<InitializedParams> clientInitialized = new CompletableFuture<>();

	private LanguageClient client;

	private Map<String, JsonRpcMethod> supportedMethods = null;

	private final Multimap<String, Endpoint> extensionProviders = LinkedListMultimap.<String, Endpoint> create();

	private final ConcurrentIssueRegistry issueRegistry = new ConcurrentIssueRegistry();

	/***/
	public XLanguageServerImpl() {
		issueRegistry.addListener(this);
	}

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

		openFilesManager.setIssueRegistry(issueRegistry);
		openFilesManager.addListener(this);
		lspBuilder.getIndexRaw().addListener(this);

		Stopwatch sw = Stopwatch.createStarted();
		LOG.info("Start server initialization in workspace directory " + baseDir);
		lspBuilder.initialize(baseDir, issueRegistry);
		LOG.info("Server initialization done after " + sw);

		initializeResult = new InitializeResult();
		initializeResult.setCapabilities(createServerCapabilities(params));

		return CompletableFuture.completedFuture(initializeResult);
	}

	@Override
	public void onIndexChanged(Map<String, ResourceDescriptionsData> changedContainers, Set<String> removedContainers) {
		openFilesManager.updatePersistedState(changedContainers, removedContainers);
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
		boolean supportsCodeActions = allLanguages.stream()
				.anyMatch((serviceProvider) -> serviceProvider.get(ICodeActionService.class) != null
						|| serviceProvider.get(ICodeActionService2.class) != null);
		if (supportsCodeActions) {
			Optional<List<String>> supportedKinds = getSupportedCodeActionKinds();
			if (supportedKinds.isPresent()) {
				serverCapabilities.setCodeActionProvider(new CodeActionOptions(supportedKinds.get()));
			} else {
				serverCapabilities.setCodeActionProvider(true);
			}
		} else {
			serverCapabilities.setCodeActionProvider(false);
		}

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

	/**
	 * Iff the receiving server makes use of {@link CodeActionKind}s, this method returns the kinds the server is using
	 * and will send to the client under certain circumstances; otherwise returns {@link Optional#absent() absent}.
	 * <p>
	 * The list of kinds may be generic, such as {@link CodeActionKind#Refactor}, or the server may list out every
	 * specific kind they provide.
	 */
	protected Optional<List<String>> getSupportedCodeActionKinds() {
		return Optional.absent();
	}

	@Override
	public void initialized(InitializedParams params) {
		lspBuilder.initialBuild();
		clientInitialized.complete(params);
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
		lspLogger.connect(client);
	}

	/**
	 * Discard all references to the language client.
	 */
	public void disconnect() {
		lspLogger.disconnect();
		issueAcceptor.disconnect();
		this.client = null;
	}

	@Override
	public void exit() {
		LOG.info("Received exit notification");
		joinServerRequests();
		shutdownAndExitHandler.exit();
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		LOG.info("Start shutdown");

		disconnect();
		return openFilesManager.closeAll()
				.thenCompose(none -> lspBuilder.shutdown())
				.thenApply(any -> {
					issueRegistry.clear();
					shutdownAndExitHandler.shutdown();

					LOG.info("Shutdown done");
					return new Object();
				});
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
		TextDocumentItem textDocument = params.getTextDocument();
		openFilesManager.openFile(getURI(textDocument), textDocument.getVersion(), textDocument.getText());
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		VersionedTextDocumentIdentifier textDocument = params.getTextDocument();
		URI uri = getURI(textDocument);
		if (openFilesManager.isOpen(uri)) { // FIXME GH-1774 reconsider
			openFilesManager.changeFile(uri, textDocument.getVersion(), params.getContentChanges());
		}
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		openFilesManager.closeFile(getURI(params.getTextDocument()));
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		lspBuilder.didSave(params);
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		lspBuilder.didChangeWatchedFiles(params);
	}

	/** Deletes all generated files and clears the type index. */
	public CompletableFuture<Void> clean() {
		return lspBuilder.clean();
	}

	/**
	 * Triggers rebuild of the whole workspace
	 */
	public CompletableFuture<Void> reinitWorkspace() {
		return lspBuilder.reinitWorkspace();
	}

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		reinitWorkspace();
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
		URI uri = getURI(params);
		return openFilesManager.runInOpenFileContext(uri, "completion", (ofc, ci) -> {
			return completion(ofc, params, ci);
		});
	}

	/**
	 * Compute the completion items.
	 */
	protected Either<List<CompletionItem>, CompletionList> completion(OpenFileContext ofc, CompletionParams params,
			CancelIndicator originalCancelIndicator) {
		URI uri = ofc.getURI();
		XContentAssistService contentAssistService = getService(uri, XContentAssistService.class);
		if (contentAssistService == null) {
			return Either.forRight(new CompletionList());
		}
		BufferedCancelIndicator cancelIndicator = new BufferedCancelIndicator(
				originalCancelIndicator,
				Duration.ofMillis(750));
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
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
		URI uri = getURI(params);
		// LSP clients will usually use this request for open files only, but that is not strictly required by the LSP
		// specification, so we use "runInOpenOrTemporary..." here:
		return openFilesManager.runInOpenOrTemporaryFileContext(uri, "definition", (ofc, ci) -> {
			return definition(ofc, params, ci);
		});
	}

	/**
	 * Compute the definition. Executed in a read request.
	 */
	protected Either<List<? extends Location>, List<? extends LocationLink>> definition(OpenFileContext ofc,
			TextDocumentPositionParams params, CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		DocumentSymbolService documentSymbolService = getService(uri, DocumentSymbolService.class);
		if (documentSymbolService == null) {
			return Either.forLeft(Collections.emptyList());
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
		return Either.forLeft(documentSymbolService.getDefinitions(doc, res, params, resourceAccess, cancelIndicator));
	}

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		URI uri = getURI(params);
		return openFilesManager.runInOpenFileContext(uri, "references", (ofc, ci) -> {
			return references(ofc, params, ci);
		});
	}

	/**
	 * Compute the references. Executed in read request.
	 */
	protected List<? extends Location> references(OpenFileContext ofc, ReferenceParams params,
			CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		DocumentSymbolService documentSymbolService = getService(uri, DocumentSymbolService.class);
		if ((documentSymbolService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
		return documentSymbolService.getReferences(doc, res, params, resourceAccess,
				openFilesManager.createLiveScopeIndex(), cancelIndicator);
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {
		URI uri = getURI(params.getTextDocument());
		return openFilesManager.runInOpenFileContext(uri, "documentSymbol", (ofc, ci) -> {
			return documentSymbol(ofc, params, ci);
		});
	}

	/**
	 * Compute the symbol information. Executed in a read request.
	 */
	protected List<Either<SymbolInformation, DocumentSymbol>> documentSymbol(OpenFileContext ofc,
			DocumentSymbolParams params, CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		IDocumentSymbolService documentSymbolService = getIDocumentSymbolService(getResourceServiceProvider(uri));
		if ((documentSymbolService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
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
		// FIXME GH-1774 double-check that the workspaceSymbolService may run in parallel to the builder
		return lspExecutorService.submitAndCancelPrevious(WorkspaceSymbolParams.class, "symbol",
				cancelIndicator -> symbol(params, cancelIndicator));
	}

	/**
	 * Compute the symbol information. Executed in a read request.
	 */
	protected List<? extends SymbolInformation> symbol(WorkspaceSymbolParams params, CancelIndicator cancelIndicator) {
		return workspaceSymbolService.getSymbols(params.getQuery(), resourceAccess,
				openFilesManager.createLiveScopeIndex(), cancelIndicator);
	}

	@Override
	public CompletableFuture<Hover> hover(TextDocumentPositionParams params) {
		URI uri = getURI(params);
		return openFilesManager.runInOpenFileContext(uri, "hover", (ofc, ci) -> {
			return hover(ofc, params, ci);
		});
	}

	/**
	 * Compute the hover. Executed in a read request.
	 */
	protected Hover hover(OpenFileContext ofc, TextDocumentPositionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		IHoverService hoverService = getService(uri, IHoverService.class);
		if (hoverService == null) {
			return IHoverService.EMPTY_HOVER;
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
		return hoverService.hover(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		return CompletableFuture.<CompletionItem> completedFuture(unresolved);
	}

	@Override
	public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams params) {
		URI uri = getURI(params);
		return openFilesManager.runInOpenFileContext(uri, "signatureHelp", (ofc, ci) -> {
			return signatureHelp(ofc, params, ci);
		});
	}

	/**
	 * Compute the signature help. Executed in a read request.
	 */
	protected SignatureHelp signatureHelp(OpenFileContext ofc, TextDocumentPositionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		ISignatureHelpService helper = getService(uri, ISignatureHelpService.class);
		if (helper == null) {
			return ISignatureHelpService.EMPTY;
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
		return helper.getSignatureHelp(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams params) {
		URI uri = getURI(params);
		return openFilesManager.runInOpenFileContext(uri, "documentHighlight", (ofc, ci) -> {
			return documentHighlight(ofc, params, ci);
		});
	}

	/**
	 * Compute the document highlights. Executed in a read request.
	 */
	protected List<? extends DocumentHighlight> documentHighlight(OpenFileContext ofc,
			TextDocumentPositionParams params, CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		IDocumentHighlightService service = getService(uri, IDocumentHighlightService.class);
		if (service == null) {
			return Collections.emptyList();
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
		return service.getDocumentHighlights(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
		URI uri = getURI(params.getTextDocument());
		return openFilesManager.runInOpenFileContext(uri, "codeAction", (ofc, ci) -> {
			return codeAction(ofc, params, ci);
		});
	}

	/**
	 * Compute the code action commands. Executed in a read request.
	 */
	protected List<Either<Command, CodeAction>> codeAction(OpenFileContext ofc, CodeActionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		IResourceServiceProvider serviceProvider = getResourceServiceProvider(uri);
		ICodeActionService service = getService(serviceProvider, ICodeActionService.class);
		ICodeActionService2 service2 = getService(serviceProvider, ICodeActionService2.class);
		if (service == null && service2 == null) {
			return Collections.emptyList();
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();

		List<Either<Command, CodeAction>> result = new ArrayList<>();
		if (service != null) {
			List<Either<Command, CodeAction>> actions = service.getCodeActions(doc, res, params,
					cancelIndicator);
			if (actions != null) {
				result.addAll(actions);
			}
		}
		if (service2 != null) {
			ICodeActionService2.Options options = toOptions(params, doc, res, cancelIndicator);
			List<Either<Command, CodeAction>> actions = service2.getCodeActions(options);
			if (actions != null) {
				result.addAll(actions);
			}
		}
		return result;
	}

	/**
	 * Convert the given params to an enriched instance of options.
	 */
	public ICodeActionService2.Options toOptions(CodeActionParams params, XDocument doc, XtextResource res,
			CancelIndicator cancelIndicator) {

		ICodeActionService2.Options options = new ICodeActionService2.Options();
		options.setDocument(doc);
		options.setResource(res);
		options.setLanguageServerAccess(access);
		options.setCodeActionParams(params);
		options.setCancelIndicator(cancelIndicator);
		return options;
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
		URI uri = getURI(params.getTextDocument());
		return openFilesManager.runInOpenFileContext(uri, "codeLens", (ofc, ci) -> {
			return codeLens(ofc, params, ci);
		});
	}

	/**
	 * Compute the code lenses.
	 */
	protected List<? extends CodeLens> codeLens(OpenFileContext ofc, CodeLensParams params,
			CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		ICodeLensService codeLensService = getService(uri, ICodeLensService.class);
		if ((codeLensService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
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
		return openFilesManager.runInOpenOrTemporaryFileContext(uri, "resolveCodeLens", (ofc, ci) -> {
			return resolveCodeLens(ofc, unresolved, ci);
		});
	}

	/**
	 * Resolve the given code lens.
	 */
	protected CodeLens resolveCodeLens(OpenFileContext ofc, CodeLens unresolved, CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		ICodeLensResolver resolver = getService(uri, ICodeLensResolver.class);
		if (resolver == null) {
			return unresolved;
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
		return resolver.resolveCodeLens(doc, res, unresolved, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
		URI uri = getURI(params.getTextDocument());
		return openFilesManager.runInOpenFileContext(uri, "formatting", (ofc, ci) -> {
			return formatting(ofc, params, ci);
		});
	}

	/**
	 * Create the text edits for the formatter. Executed in a read request.
	 */
	protected List<? extends TextEdit> formatting(OpenFileContext ofc, DocumentFormattingParams params,
			CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		FormattingService formatterService = getService(uri, FormattingService.class);
		if ((formatterService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
		return formatterService.format(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
		URI uri = getURI(params.getTextDocument());
		return openFilesManager.runInOpenFileContext(uri, "rangeFormatting", (ofc, ci) -> {
			return rangeFormatting(ofc, params, ci);
		});
	}

	/**
	 * Create the text edits for the formatter. Executed in a read request.
	 */
	protected List<? extends TextEdit> rangeFormatting(OpenFileContext ofc, DocumentRangeFormattingParams params,
			CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
		FormattingService formatterService = getService(uri, FormattingService.class);
		if ((formatterService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = ofc.getResource();
		XDocument doc = ofc.getDocument();
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
		return lspExecutorService.submit("executeCommand", cancelIndicator -> executeCommand(params, cancelIndicator));
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
		URI uri = getURI(renameParams.getTextDocument());
		return openFilesManager.runInOpenFileContext(uri, "rename", (ofc, ci) -> {
			return rename(ofc, renameParams, ci);
		});
	}

	/**
	 * Compute the rename edits. Executed in a read request.
	 */
	protected WorkspaceEdit rename(OpenFileContext ofc, RenameParams renameParams, CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();

		IResourceServiceProvider resourceServiceProvider = getResourceServiceProvider(uri);
		XIRenameService renameServiceOld = getService(resourceServiceProvider, XIRenameService.class);
		if (renameServiceOld != null) {
			// The deprecated version 1 of IRenameService is no longer supported, because it requires an
			// XWorkspaceManager which we do no longer allow to access outside the builder:
			throw new UnsupportedOperationException(XIRenameService.class.getSimpleName() + " is no longer supported");
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
	public CompletableFuture<Either<Range, PrepareRenameResult>> prepareRename(TextDocumentPositionParams params) {
		URI uri = getURI(params);
		return openFilesManager.runInOpenFileContext(uri, "prepareRename", (ofc, ci) -> {
			return prepareRename(ofc, params, ci);
		});
	}

	/**
	 * Prepare the rename operation. Executed in a read request.
	 */
	protected Either<Range, PrepareRenameResult> prepareRename(OpenFileContext ofc, TextDocumentPositionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = ofc.getURI();
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

	private final IResourceAccess resourceAccess = new XWorkspaceResourceAccess(this);

	private final ILanguageServerAccess access = new ILanguageServerAccess() {
		@Override
		public <T> CompletableFuture<T> doRead(String uriStr, Function<ILanguageServerAccess.Context, T> function) {
			URI uri = uriExtensions.toUri(uriStr);
			OpenFileContext currOFC = openFilesManager.currentContext();
			if (currOFC != null) {
				ResourceSet resSet = currOFC.getResourceSet();
				Resource res = resSet.getResource(uri, true);
				if (res instanceof XtextResource) {
					String content = ((XtextResource) res).getParseResult().getRootNode().getText();
					XDocument doc = new XDocument(1, content);
					boolean isOpen = openFilesManager.isOpen(uri);
					T result = function.apply(
							new ILanguageServerAccess.Context(res, doc, isOpen, CancelIndicator.NullImpl));
					return CompletableFuture.completedFuture(result);
				}
			}
			return openFilesManager.runInOpenOrTemporaryFileContext(uri, "doRead", (ofc, ci) -> {
				XtextResource res = ofc.getResource();
				XDocument doc = ofc.getDocument();
				boolean isOpen = !ofc.isTemporary();
				return function.apply(
						new ILanguageServerAccess.Context(res, doc, isOpen, ci));
			});
		}

		@Override
		public void addBuildListener(ILanguageServerAccess.IBuildListener listener) {
			throw new UnsupportedOperationException();
		}

		@Override
		public LanguageClient getLanguageClient() {
			return client;
		}

		@Override
		public ResourceSet newLiveScopeResourceSet(URI uri) {
			XtextResourceSet resourceSet = resourceSetProvider.get();
			ResourceDescriptionsData index = openFilesManager.createLiveScopeIndex();
			ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(resourceSet, index);
			return resourceSet;
		}

		@Override
		public InitializeParams getInitializeParams() {
			return initializeParams;
		}

		@Override
		public <T> CompletableFuture<T> doReadIndex(
				Function<? super ILanguageServerAccess.IndexContext, ? extends T> function) {
			// because access to the index is thread-safe anyway, we do not need to run anything asynchronously, here:
			ILanguageServerAccess.IndexContext indexContext = new ILanguageServerAccess.IndexContext(
					openFilesManager.createLiveScopeIndex(), CancelIndicator.NullImpl);
			T result = function.apply(indexContext);
			return CompletableFuture.completedFuture(result);
		}

		@Override
		public InitializeResult getInitializeResult() {
			return initializeResult;
		}
	};

	@Override
	public void didRefreshOpenFile(OpenFileContext ofc, CancelIndicator ci) {
		if (client instanceof LanguageClientExtensions) {

			LanguageClientExtensions clientExtensions = (LanguageClientExtensions) client;
			XtextResource resource = ofc.getResource();
			IResourceServiceProvider resourceServiceProvider = resource.getResourceServiceProvider();
			IColoringService coloringService = resourceServiceProvider.get(IColoringService.class);

			if (coloringService != null) {
				XDocument doc = ofc.getDocument();
				List<? extends ColoringInformation> colInfos = coloringService.getColoring(resource, doc);

				if (!IterableExtensions.isNullOrEmpty(colInfos)) {
					String uri = resource.getURI().toString();
					ColoringParams colParams = new ColoringParams(uri, colInfos);
					clientExtensions.updateColoring(colParams);
				}
			}
		}

		ILanguageServerAccess.Context ctx = new ILanguageServerAccess.Context(ofc.getResource(), ofc.getDocument(),
				true, ci);
		semanticHighlightingRegistry.update(ctx);
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
	 * TODO add <code>@since</code> tag
	 */
	public OpenFilesManager getOpenFilesManager() {
		return openFilesManager;
	}

	/**
	 * TODO add <code>@since</code> tag
	 */
	public LSPBuilder getBuilder() {
		return lspBuilder;
	}

	/**
	 * TODO add <code>@since</code> tag
	 */
	public ConcurrentIssueRegistry getIssueRegistry() {
		return issueRegistry;
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
	public LSPExecutorService getLSPExecutorService() {
		return lspExecutorService;

	}

	/** Blocks until the lsp client sent the initialized message */
	public void joinClientInitialized() {
		clientInitialized.join();
	}

	/** Blocks until all requests of the language server finished */
	public void joinServerRequests() {
		lspExecutorService.join();
		lspBuilder.joinPersister();
	}

	@Override
	public void onIssuesChanged(ImmutableList<IssueRegistryChangeEvent> events) {
		for (IssueRegistryChangeEvent event : events) {
			if (event.persistedState && openFilesManager.isOpen(event.uri)) {
				continue; // for open files we ignore issue changes sent by builder
			}
			Iterable<LSPIssue> issues = event.issuesNew != null ? event.issuesNew : Collections.emptyList();
			issueAcceptor.publishDiagnostics(event.uri, issues);
		}
	}

}
