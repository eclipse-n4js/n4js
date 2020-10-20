/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextEdit;
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
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.eclipse.n4js.ide.server.HeadlessExtensionRegistrationHelper;
import org.eclipse.n4js.ide.server.LspLogger;
import org.eclipse.n4js.ide.server.util.ServerIncidentLogger;
import org.eclipse.n4js.ide.xtext.server.issues.PublishingIssueAcceptor;
import org.eclipse.n4js.ide.xtext.server.util.ParamHelper;
import org.eclipse.xtext.ide.server.ICapabilitiesContributor;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.ILanguageServerExtension;
import org.eclipse.xtext.ide.server.ILanguageServerShutdownAndExitHandler;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.codelens.ICodeLensResolver;
import org.eclipse.xtext.ide.server.codelens.ICodeLensService;
import org.eclipse.xtext.ide.server.commands.ExecutableCommandRegistry;
import org.eclipse.xtext.ide.server.hover.IHoverService;
import org.eclipse.xtext.ide.server.rename.IRenameService;
import org.eclipse.xtext.ide.server.rename.IRenameService2;
import org.eclipse.xtext.ide.server.semanticHighlight.SemanticHighlightingRegistry;
import org.eclipse.xtext.ide.server.signatureHelp.ISignatureHelpService;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;

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
		Endpoint, JsonRpcMethodProvider, DebugEndpointDefinition {

	private static final Logger LOG = Logger.getLogger(XLanguageServerImpl.class);

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Inject
	private LanguageServerFrontend lsFrontend;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private ParamHelper paramHelper;

	@Inject
	private IResourceServiceProvider.Registry languagesRegistry;

	@Inject
	private ExecutableCommandRegistry commandRegistry;

	@Inject
	private SemanticHighlightingRegistry semanticHighlightingRegistry;

	@Inject
	private ILanguageServerShutdownAndExitHandler shutdownAndExitHandler;

	// TODO only here for connect / disconnect
	@Inject
	private PublishingIssueAcceptor issuePublisher;

	@Inject
	private LspLogger lspLogger;

	@Inject
	private ServerIncidentLogger serverIncidentLogger;

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private DebugService debugService;

	private InitializeParams initializeParams;

	private InitializeResult initializeResult;

	private LanguageClient client;

	private Map<String, JsonRpcMethod> supportedMethods = null;

	private final Multimap<String, Endpoint> extensionProviders = LinkedListMultimap.<String, Endpoint> create();

	/***/
	public XLanguageServerImpl() {
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

	/**
	 * Tells whether the given URI is supported by this server and its registered languages. If this method returns
	 * <code>false</code> the LSP notification/request that contained the given URI will be ignored by this server.
	 * Returns <code>false</code> when given <code>null</code>.
	 * <p>
	 * This method should be used only for an early, very simple sanity check of the URIs sent by the LSP client (e.g.
	 * supported scheme and/or file extension), to avoid exceptions and invalid state deep inside the builder, etc.; it
	 * should not be used for more sophisticated, expensive checks (e.g. whether a URI denotes a valid source file that
	 * actually exists on disk).
	 */
	protected boolean isSupported(URI uri) {
		if (uri == null) {
			return false;
		}
		// TODO consider also delegating to IResourceServiceProvider#canHandle(URI)
		String scheme = uri.scheme();
		if (languagesRegistry.getProtocolToFactoryMap().containsKey(scheme)) {
			return true;
		}
		if ("file".equalsIgnoreCase(scheme)
				&& languagesRegistry.getExtensionToFactoryMap().containsKey(uri.fileExtension())) {
			return true;
		}
		return false;
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

		Stopwatch sw = Stopwatch.createStarted();
		LOG.info("Start server initialization in workspace directory " + baseDir);
		lsFrontend.initialize(initializeParams, baseDir, access);
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
		lsFrontend.initialized();
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
	 * Compute the base directory.
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
		debugService.connect(client);
		issuePublisher.connect(client);
		lspLogger.connect(client);
		lsFrontend.connect(client);
	}

	/**
	 * Discard all references to the language client.
	 */
	public void disconnect() {
		lspLogger.disconnect();
		issuePublisher.disconnect();
		lsFrontend.disconnect();
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
		return resourceTaskManager.closeAll()
				.thenCompose(none -> lsFrontend.shutdown())
				.thenApply(any -> {
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
		if (!isSupported(paramHelper.getURI(params))) {
			return;
		}
		lsFrontend.didOpen(params);
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return;
		}
		lsFrontend.didChange(params);
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return;
		}
		lsFrontend.didClose(params);
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return;
		}
		lsFrontend.didSave(params);
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		DidChangeWatchedFilesParams paramsSupported = removeUnsupportedURIs(params);
		if (paramsSupported.getChanges().isEmpty()) {
			return;
		}
		lsFrontend.didChangeWatchedFiles(paramsSupported);
	}

	/**
	 * Returns a new parameter object including all changes of the given parameter object that have a
	 * {@link #isSupported(URI) supported} URI. Returned parameters may have an empty list of changes.
	 */
	protected DidChangeWatchedFilesParams removeUnsupportedURIs(DidChangeWatchedFilesParams params) {
		return new DidChangeWatchedFilesParams(params.getChanges().stream()
				.filter(change -> isSupported(paramHelper.getURI(change)))
				.collect(Collectors.toList()));
	}

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		lsFrontend.didChangeConfiguration(params);
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Either.forLeft(Collections.emptyList()));
		}
		return lsFrontend.completion(params);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			TextDocumentPositionParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Either.forLeft(Collections.emptyList()));
		}
		return lsFrontend.definition(params);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> typeDefinition(
			TextDocumentPositionParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Either.forLeft(Collections.emptyList()));
		}
		return lsFrontend.typeDefinition(params);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> implementation(
			TextDocumentPositionParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Either.forLeft(Collections.emptyList()));
		}
		return lsFrontend.implementation(params);
	}

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Collections.emptyList());
		}
		return lsFrontend.references(params);
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Collections.emptyList());
		}
		return lsFrontend.documentSymbol(params);
	}

	@Override
	public CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params) {
		return lsFrontend.symbol(params);
	}

	@Override
	public CompletableFuture<Hover> hover(TextDocumentPositionParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(IHoverService.EMPTY_HOVER);
		}
		return lsFrontend.hover(params);
	}

	@Override
	public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		return lsFrontend.resolveCompletionItem(unresolved);
	}

	@Override
	public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(ISignatureHelpService.EMPTY);
		}
		return lsFrontend.signatureHelp(params);
	}

	@Override
	public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Collections.emptyList());
		}
		return lsFrontend.documentHighlight(params);
	}

	@Override
	public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Collections.emptyList());
		}
		return lsFrontend.codeAction(params);
	}

	@Override
	public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Collections.emptyList());
		}
		return lsFrontend.codeLens(params);
	}

	@Override
	public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
		return lsFrontend.resolveCodeLens(unresolved);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Collections.emptyList());
		}
		return lsFrontend.formatting(params);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Collections.emptyList());
		}
		return lsFrontend.formatting(params);
	}

	@Override
	public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
		return lsFrontend.executeCommand(params);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(Collections.emptyList());
		}
		return lsFrontend.onTypeFormatting(params);
	}

	@Override
	public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			return CompletableFuture.completedFuture(new WorkspaceEdit());
		}
		return lsFrontend.rename(params);
	}

	@Override
	public CompletableFuture<Either<Range, PrepareRenameResult>> prepareRename(TextDocumentPositionParams params) {
		if (!isSupported(paramHelper.getURI(params))) {
			// LSP specification: "If null is returned then it is deemed that a ‘textDocument/rename’ request
			// is not valid at the given position."
			return CompletableFuture.completedFuture(Either.forLeft(null));
		}
		return lsFrontend.prepareRename(params);
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
			ResourceTaskContext currOFC = resourceTaskManager.currentContext();
			if (currOFC != null) {
				ResourceSet resSet = currOFC.getResourceSet();
				Resource res = resSet.getResource(uri, true);
				if (res instanceof XtextResource) {
					String content = ((XtextResource) res).getParseResult().getRootNode().getText();
					XDocument doc = new XDocument(1, content);
					boolean isOpen = resourceTaskManager.isOpen(uri);
					T result = function.apply(
							new ILanguageServerAccess.Context(res, doc, isOpen, CancelIndicator.NullImpl));
					return CompletableFuture.completedFuture(result);
				}
			}
			// TODO consider making a current context mandatory by removing the following (see GH-1774):
			return resourceTaskManager.runInExistingOrTemporaryContext(uri, "doRead", (ofc, ci) -> {
				XtextResource res = ofc.getResource();
				XDocument doc = ofc.getDocument();
				boolean isOpen = ofc.isOpen();
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
			ResourceDescriptionsData index = resourceTaskManager.createLiveScopeIndex();
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
					resourceTaskManager.createLiveScopeIndex(), CancelIndicator.NullImpl);
			T result = function.apply(indexContext);
			return CompletableFuture.completedFuture(result);
		}

		@Override
		public InitializeResult getInitializeResult() {
			return initializeResult;
		}
	};

	@Override
	public CompletableFuture<Void> setLogLevel(String level) {
		return debugService.setLogLevel(level);
	}

	@Override
	public CompletableFuture<Void> printDebugInfo() {
		// in some error cases, the debug information might not show up in the output window of the LSP client or might
		// scroll away too fast, so we also report this via the ServerIncidentLogger to obtain a file on disk with the
		// debug information:
		String debugInfo = debugService.getDebugInfo();
		serverIncidentLogger.report("(user request for printing debug information)" + System.lineSeparator()
				+ debugInfo);

		return debugService.printDebugInfo();
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
	public ResourceTaskManager getResourceTaskManager() {
		return resourceTaskManager;
	}

	/**
	 * Getter
	 */
	public LspLogger getLspLogger() {
		return lspLogger;
	}

	/**
	 * Getter
	 */
	public ServerIncidentLogger getServerIncidentLogger() {
		return serverIncidentLogger;
	}

	/**
	 * Returns the front-end to this server instance.
	 */
	public LanguageServerFrontend getFrontend() {
		return lsFrontend;
	}

	/**
	 * Returns the debug service used by this server instance.
	 */
	public DebugService getDebugService() {
		return debugService;
	}

	/** Blocks until all requests of the language server finished */
	public void joinServerRequests() {
		lsFrontend.join();
	}

}
