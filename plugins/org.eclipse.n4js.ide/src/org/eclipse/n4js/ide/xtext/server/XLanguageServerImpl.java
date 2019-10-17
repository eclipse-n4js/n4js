/**
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.n4js.ide.xtext.server;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

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
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
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
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.InitializedParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
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
import org.eclipse.n4js.ide.xtext.server.findReferences.XWorkspaceResourceAccess;
import org.eclipse.n4js.ide.xtext.server.rename.XIRenameService;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.ide.server.Document;
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
import org.eclipse.xtext.ide.server.concurrent.RequestManager;
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
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
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
@SuppressWarnings("restriction")
public class XLanguageServerImpl implements LanguageServer, WorkspaceService, TextDocumentService, LanguageClientAware,
		Endpoint, JsonRpcMethodProvider, ILanguageServerAccess.IBuildListener {

	private static Logger LOG = Logger.getLogger(XLanguageServerImpl.class);

	/**
	 * A cancel indicator that will not cancel immediately but only after a second delay to allow short running tasks to
	 * complete despite an attempt to cancel.
	 */
	public static class BufferedCancelIndicator implements CancelIndicator {
		private final CancelIndicator delegate;

		private long canceledSince;

		/**
		 * Constructor
		 */
		public BufferedCancelIndicator(CancelIndicator delegate) {
			this.delegate = delegate;
		}

		@Override
		public boolean isCanceled() {
			if (this.canceledSince == 0 && this.delegate.isCanceled()) {
				this.canceledSince = System.currentTimeMillis();
				return false;
			}
			return this.canceledSince != 0 && System.currentTimeMillis() > this.canceledSince + 1000;
		}
	}

	@Inject
	private RequestManager requestManager;

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

	private XWorkspaceManager workspaceManager;

	private InitializeParams initializeParams;

	private InitializeResult initializeResult;

	private final CompletableFuture<InitializedParams> initialized = new CompletableFuture<>();

	private XWorkspaceResourceAccess resourceAccess;

	private LanguageClient client;

	private Map<String, JsonRpcMethod> supportedMethods = null;

	private final Multimap<String, Endpoint> extensionProviders = LinkedListMultimap.<String, Endpoint> create();

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

	@SuppressWarnings("deprecation")
	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		if (this.initializeParams != null) {
			throw new IllegalStateException("This language server has already been initialized.");
		}
		URI baseDir = getBaseDir(params);
		if (languagesRegistry.getExtensionToFactoryMap().isEmpty()) {
			throw new IllegalStateException(
					"No Xtext languages have been registered. Please make sure you have added the languages\'s setup class in \'/META-INF/services/org.eclipse.xtext.ISetup\'");
		}
		this.initializeParams = params;

		InitializeResult result = new InitializeResult();

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
		result.setCapabilities(serverCapabilities);
		access.addBuildListener(this);
		return requestManager.runWrite(
				() -> {
					workspaceManager.initialize(baseDir, this::publishDiagnostics, CancelIndicator.NullImpl);
					return result;
				}, (cancelIndicator, it) -> it).thenApply(it -> initializeResult = it);
	}

	@Override
	public void initialized(InitializedParams params) {
		initialized.complete(params);
	}

	@SuppressWarnings("deprecation")
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
			return this.uriExtensions.toUri(rootUri);
		}
		return this.deprecatedToBaseDir(params);
	}

	@SuppressWarnings("hiding")
	@Override
	public void connect(LanguageClient client) {
		this.client = client;
	}

	@Override
	public void exit() {
		this.shutdownAndExitHandler.exit();
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		this.shutdownAndExitHandler.shutdown();
		return CompletableFuture.completedFuture(new Object());
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
		requestManager.runWrite(() -> {
			TextDocumentItem textDocument = params.getTextDocument();
			return workspaceManager.didOpen(uriExtensions.toUri(textDocument.getUri()),
					textDocument.getVersion(), textDocument.getText());
		}, (cancelIndicator, buildable) -> buildable.build(cancelIndicator));
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		requestManager.runWrite(() -> {
			VersionedTextDocumentIdentifier textDocument = params.getTextDocument();
			return workspaceManager.didChangeTextDocumentContent(uriExtensions.toUri(textDocument.getUri()),
					textDocument.getVersion(), params.getContentChanges());
		}, (cancelIndicator, buildable) -> buildable.build(cancelIndicator));
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		requestManager.runWrite(() -> workspaceManager.didClose(uriExtensions.toUri(params.getTextDocument().getUri())),
				(cancelIndicator, buildable) -> buildable.build(cancelIndicator));
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		// nothing to do
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		requestManager.runWrite(() -> {
			List<URI> dirtyFiles = new ArrayList<>();
			List<URI> deletedFiles = new ArrayList<>();
			params.getChanges().stream()
					.map((fileEvent) -> Pair.of(uriExtensions.toUri(fileEvent.getUri()), fileEvent.getType()))
					.filter(pair -> workspaceManager.isDocumentOpen(pair.getKey()))
					.forEach(pair -> {
						if (pair.getValue() == FileChangeType.Deleted) {
							deletedFiles.add(pair.getKey());
						} else {
							dirtyFiles.add(pair.getKey());
						}
					});
			return workspaceManager.didChangeFiles(dirtyFiles, deletedFiles);
		}, (cancelIndicator, buildable) -> buildable.build(cancelIndicator));
	}

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		requestManager.runWrite(() -> {
			workspaceManager.refreshWorkspaceConfig(CancelIndicator.NullImpl);
			return null;
		}, (a, b) -> null);
	}

	private void publishDiagnostics(URI uri, Iterable<? extends Issue> issues) {
		this.initialized.thenAccept((initParams) -> {
			PublishDiagnosticsParams publishDiagnosticsParams = new PublishDiagnosticsParams();
			publishDiagnosticsParams.setUri(this.uriExtensions.toUriString(uri));
			publishDiagnosticsParams.setDiagnostics(workspaceManager.doRead(uri,
					(document, resource) -> FluentIterable.from(issues)
							.filter(issue -> issue.getSeverity() != Severity.IGNORE)
							.transform(issue -> toDiagnostic(document, issue)).toList()));
			this.client.publishDiagnostics(publishDiagnosticsParams);
		});
	}

	private Diagnostic toDiagnostic(Document document, Issue issue) {
		Diagnostic result = new Diagnostic();
		result.setCode(issue.getCode());
		Severity severity = issue.getSeverity();
		if (severity != null) {
			switch (severity) {
			case ERROR:
				result.setSeverity(DiagnosticSeverity.Error);
				break;
			case WARNING:
				result.setSeverity(DiagnosticSeverity.Warning);
				break;
			case INFO:
				result.setSeverity(DiagnosticSeverity.Information);
				break;
			default:
				result.setSeverity(DiagnosticSeverity.Hint);
				break;
			}
		} else {
			result.setSeverity(DiagnosticSeverity.Hint);
		}
		result.setMessage(issue.getMessage());
		Position start = document.getPosition(issue.getOffset());
		Position end = document.getPosition(issue.getOffset() + issue.getLength());
		result.setRange(new Range(start, end));
		return result;
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
		return this.requestManager.runRead((cancelIndicator) -> completion(cancelIndicator, params));
	}

	/**
	 * Compute the completion items.
	 */
	protected Either<List<CompletionItem>, CompletionList> completion(CancelIndicator originalCancelIndicator,
			CompletionParams params) {
		URI uri = this.uriExtensions.toUri(params.getTextDocument().getUri());
		IResourceServiceProvider resourceServiceProvider = this.languagesRegistry.getResourceServiceProvider(uri);
		ContentAssistService contentAssistService = resourceServiceProvider != null
				? resourceServiceProvider.get(ContentAssistService.class)
				: null;
		if (contentAssistService == null) {
			return Either.forRight(new CompletionList());
		}
		BufferedCancelIndicator cancelIndicator = new BufferedCancelIndicator(originalCancelIndicator);
		return Either.forRight(this.workspaceManager.doRead(uri,
				(doc, res) -> contentAssistService.createCompletionList(doc, res, params, cancelIndicator)));
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			TextDocumentPositionParams params) {
		return requestManager.runRead(cancelIndicator -> Either.forLeft(definition(cancelIndicator, params)));
	}

	/**
	 * Compute the definition.
	 */
	protected List<? extends Location> definition(CancelIndicator cancelIndicator,
			TextDocumentPositionParams params) {
		URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
		IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(uri);
		DocumentSymbolService documentSymbolService = resourceServiceProvider != null
				? resourceServiceProvider.get(DocumentSymbolService.class)
				: null;
		if ((documentSymbolService == null)) {
			return CollectionLiterals.emptyList();
		}
		return this.workspaceManager.doRead(uri,
				(doc, res) -> documentSymbolService.getDefinitions(doc, res, params, resourceAccess, cancelIndicator));
	}

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		return requestManager.runRead(cancelIndicator -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(uri);
			DocumentSymbolService documentSymbolService = resourceServiceProvider != null
					? resourceServiceProvider.get(DocumentSymbolService.class)
					: null;
			if ((documentSymbolService == null)) {
				return CollectionLiterals.emptyList();
			}
			return this.workspaceManager.doRead(uri,
					(document, resource) -> documentSymbolService.getReferences(document, resource, params,
							resourceAccess,
							workspaceManager.getIndex(), cancelIndicator));
		});
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {
		return this.requestManager.runRead((cancelIndicator) -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IDocumentSymbolService documentSymbolService = getIDocumentSymbolService(
					languagesRegistry.getResourceServiceProvider(uri));
			if ((documentSymbolService == null)) {
				return CollectionLiterals.emptyList();
			}
			return this.workspaceManager.doRead(uri, (document, resource) -> documentSymbolService.getSymbols(document,
					resource, params, cancelIndicator));
		});
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
		return requestManager.runRead((cancelIndicator) -> workspaceSymbolService.getSymbols(params.getQuery(),
				resourceAccess, workspaceManager.getIndex(),
				cancelIndicator));
	}

	@Override
	public CompletableFuture<Hover> hover(TextDocumentPositionParams params) {
		return this.requestManager.runRead((cancelIndicator) -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(uri);
			IHoverService hoverService = resourceServiceProvider != null
					? resourceServiceProvider.get(IHoverService.class)
					: null;
			if (hoverService == null) {
				return IHoverService.EMPTY_HOVER;
			}
			return this.workspaceManager.<Hover> doRead(uri,
					(document, resource) -> hoverService.hover(document, resource, params, cancelIndicator));
		});
	}

	@Override
	public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		return CompletableFuture.<CompletionItem> completedFuture(unresolved);
	}

	@Override
	public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams params) {
		return requestManager.runRead((cancelIndicator) -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(uri);
			ISignatureHelpService helper = resourceServiceProvider != null
					? resourceServiceProvider.get(ISignatureHelpService.class)
					: null;
			if (helper == null) {
				return ISignatureHelpService.EMPTY;
			}
			return workspaceManager.doRead(uri,
					(doc, resource) -> helper.getSignatureHelp(doc, resource, params, cancelIndicator));
		});
	}

	@Override
	public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(
			TextDocumentPositionParams params) {
		return this.requestManager.runRead((cancelIndicator) -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IResourceServiceProvider serviceProvider = languagesRegistry.getResourceServiceProvider(uri);
			IDocumentHighlightService service = serviceProvider != null
					? serviceProvider.get(IDocumentHighlightService.class)
					: null;
			if (service == null) {
				return CollectionLiterals.emptyList();
			}
			return this.workspaceManager.doRead(uri, (doc,
					resource) -> service.getDocumentHighlights(doc, resource, params, cancelIndicator));
		});
	}

	@Override
	public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
		return this.requestManager.runRead((cancelIndicator) -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IResourceServiceProvider serviceProvider = languagesRegistry.getResourceServiceProvider(uri);
			ICodeActionService service = serviceProvider != null ? serviceProvider.get(ICodeActionService.class) : null;
			ICodeActionService2 service2 = serviceProvider != null ? serviceProvider.get(ICodeActionService2.class)
					: null;
			if (service == null && service2 == null) {
				return CollectionLiterals.emptyList();
			}
			return workspaceManager.doRead(uri, (doc, resource) -> {
				List<Either<Command, CodeAction>> result = new ArrayList<>();
				if (service != null) {
					List<Either<Command, CodeAction>> actions = service.getCodeActions(doc, resource, params,
							cancelIndicator);
					if (actions != null) {
						result.addAll(actions);
					}
				}
				if (service2 != null) {
					ICodeActionService2.Options options = new ICodeActionService2.Options();
					options.setDocument(doc);
					options.setResource(resource);
					options.setLanguageServerAccess(this.access);
					options.setCodeActionParams(params);
					options.setCancelIndicator(cancelIndicator);
					List<Either<Command, CodeAction>> actions = service2.getCodeActions(options);
					if (actions != null) {
						result.addAll(actions);
					}
				}
				return result;
			});
		});
	}

	private void installURI(List<? extends CodeLens> codeLenses, String uri) {
		for (CodeLens lens : codeLenses) {
			Object data = lens.getData();
			if (data != null) {
				lens.setData(CollectionLiterals.newArrayList(uri, lens.getData()));
			} else {
				lens.setData(uri);
			}
		}
	}

	private URI uninstallURI(CodeLens lens) {
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
		return requestManager.runRead((cancelIndicator) -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(uri);
			ICodeLensService codeLensService = resourceServiceProvider != null
					? resourceServiceProvider.get(ICodeLensService.class)
					: null;
			if ((codeLensService == null)) {
				return CollectionLiterals.emptyList();
			}
			return workspaceManager.doRead(uri, (document, resource) -> {
				List<? extends CodeLens> result = codeLensService.computeCodeLenses(document, resource, params,
						cancelIndicator);
				installURI(result, uri.toString());
				return result;
			});
		});
	}

	@Override
	public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
		URI uri = uninstallURI(unresolved);
		if ((uri == null)) {
			return CompletableFuture.completedFuture(unresolved);
		}
		return requestManager.runRead((cancelIndicator) -> {
			IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(uri);
			ICodeLensResolver resolver = resourceServiceProvider != null
					? resourceServiceProvider.get(ICodeLensResolver.class)
					: null;
			if ((resolver == null)) {
				return unresolved;
			}
			return this.workspaceManager.doRead(uri,
					(document, resource) -> resolver.resolveCodeLens(document, resource, unresolved, cancelIndicator));
		});
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
		return this.requestManager.<List<? extends TextEdit>> runRead((cancelIndicator) -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(uri);
			FormattingService formatterService = resourceServiceProvider != null
					? resourceServiceProvider.get(FormattingService.class)
					: null;
			if ((formatterService == null)) {
				return CollectionLiterals.emptyList();
			}
			return workspaceManager.doRead(uri, (document,
					resource) -> formatterService.format(document, resource, params, cancelIndicator));
		});
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
		return this.requestManager.runRead((cancelIndicator) -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IResourceServiceProvider resourceServiceProvider = languagesRegistry.getResourceServiceProvider(uri);
			FormattingService formatterService = resourceServiceProvider != null
					? resourceServiceProvider.get(FormattingService.class)
					: null;
			if ((formatterService == null)) {
				return CollectionLiterals.emptyList();
			}
			return workspaceManager.doRead(uri,
					(document, resource) -> formatterService.format(document, resource, params, cancelIndicator));
		});
	}

	@Override
	public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
		return this.requestManager
				.runRead((cancelIndicator) -> commandRegistry.executeCommand(params, this.access, cancelIndicator));
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}

	@Override
	public CompletableFuture<WorkspaceEdit> rename(RenameParams renameParams) {
		return requestManager.runRead(cancelIndicator -> {
			URI uri = uriExtensions.toUri(renameParams.getTextDocument().getUri());
			IResourceServiceProvider resourceServiceProvider = this.languagesRegistry
					.getResourceServiceProvider(uri);
			XIRenameService renameServiceOld = resourceServiceProvider != null
					? resourceServiceProvider.get(XIRenameService.class)
					: null;
			if ((renameServiceOld != null)) {
				return renameServiceOld.rename(this.workspaceManager, renameParams, cancelIndicator);
			}
			IRenameService2 renameService2 = resourceServiceProvider != null
					? resourceServiceProvider.get(IRenameService2.class)
					: null;
			if ((renameService2 != null)) {
				IRenameService2.Options options = new IRenameService2.Options();
				options.setLanguageServerAccess(this.access);
				options.setRenameParams(renameParams);
				options.setCancelIndicator(cancelIndicator);
				return renameService2.rename(options);
			}
			return new WorkspaceEdit();
		});
	}

	/**
	 * @since 2.18
	 */
	@Override
	public CompletableFuture<Either<Range, PrepareRenameResult>> prepareRename(
			TextDocumentPositionParams params) {
		return requestManager.runRead(cancelIndicator -> {
			URI uri = uriExtensions.toUri(params.getTextDocument().getUri());
			IResourceServiceProvider resourceServiceProvider = this.languagesRegistry
					.getResourceServiceProvider(uri);
			IRenameService2 renameService = resourceServiceProvider != null
					? resourceServiceProvider.get(IRenameService2.class)
					: null;
			if (renameService == null) {
				throw new UnsupportedOperationException();
			}
			IRenameService2.PrepareRenameOptions options = new IRenameService2.PrepareRenameOptions();
			options.setLanguageServerAccess(access);
			options.setParams(params);
			options.setCancelIndicator(cancelIndicator);
			return renameService.prepareRename(options);
		});
	}

	@Override
	public void notify(String method, Object parameter) {
		for (Endpoint endpoint : this.extensionProviders.get(method)) {
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
		if (!this.extensionProviders.containsKey(method)) {
			throw new UnsupportedOperationException("The json request \'" + method + "\' is unknown.");
		}
		for (Endpoint endpoint : this.extensionProviders.get(method)) {
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
		synchronized (this.extensionProviders) {
			LinkedHashMap<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
			supportedMethods.putAll(ServiceEndpoints.getSupportedMethods(getClass()));

			Map<String, JsonRpcMethod> extensions = new LinkedHashMap<>();
			for (IResourceServiceProvider resourceServiceProvider : getAllLanguages()) {
				ILanguageServerExtension ext = resourceServiceProvider.get(ILanguageServerExtension.class);
				if (ext != null) {
					ext.initialize(this.access);
					Map<String, JsonRpcMethod> supportedExtensions = (ext instanceof JsonRpcMethodProvider)
							? ((JsonRpcMethodProvider) ext).supportedMethods()
							: ServiceEndpoints.getSupportedMethods(ext.getClass());
					for (Map.Entry<String, JsonRpcMethod> entry : supportedExtensions.entrySet()) {
						if (supportedMethods.containsKey(entry.getKey())) {
							XLanguageServerImpl.LOG.error("The json rpc method \'" + entry.getKey()
									+ "\' can not be an extension as it is already defined in the LSP standard.");
						} else {
							JsonRpcMethod existing = extensions.put(entry.getKey(), entry.getValue());
							if (existing != null && !Objects.equal(existing, entry.getValue())) {
								XLanguageServerImpl.LOG.error("An incompatible LSP extension \'" + entry.getKey()
										+ "\' has already been registered. Using 1 ignoring 2. \n1 : " + existing
										+ " \n2 : " + entry.getValue());
								extensions.put(entry.getKey(), existing);
							} else {
								Endpoint endpoint = ServiceEndpoints.toEndpoint(ext);
								this.extensionProviders.put(entry.getKey(), endpoint);
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
		public <T extends Object> CompletableFuture<T> doRead(String uri,
				Function<ILanguageServerAccess.Context, T> function) {
			return requestManager.runRead(cancelIndicator -> workspaceManager
					.doRead(uriExtensions.toUri(uri), (document,
							resource) -> function.apply(new ILanguageServerAccess.Context(resource, document,
									workspaceManager.isDocumentOpen(resource.getURI()),
									cancelIndicator))));
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
			XtextResourceSet resourceSet = projectManager
					.createNewResourceSet(projectManager.getIndexState().getResourceDescriptions());
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
		FluentIterable.from(deltas).filter((it) -> it.getNew() != null).transform(it -> it.getUri().toString()).forEach(
				it -> {
					access.doRead(it, ctx -> {
						if (ctx.isDocumentOpen()) {
							if (ctx.getResource() instanceof XtextResource) {
								XtextResource resource = ((XtextResource) ctx.getResource());
								IResourceServiceProvider serviceProvider = this.languagesRegistry
										.getResourceServiceProvider(resource.getURI());
								IColoringService coloringService = serviceProvider != null
										? serviceProvider.get(IColoringService.class)
										: null;
								if (coloringService != null) {
									if ((client instanceof LanguageClientExtensions)) {
										Document doc = ctx.getDocument();
										List<? extends ColoringInformation> coloringInfos = coloringService
												.getColoring(resource, doc);
										if ((!IterableExtensions.isNullOrEmpty(coloringInfos))) {
											String uri = resource.getURI().toString();
											((LanguageClientExtensions) this.client)
													.updateColoring(new ColoringParams(uri, coloringInfos));
										}
									}
								}
							}
						}
						this.semanticHighlightingRegistry.update(ctx);
						return null;
					});
				});
	}

	/**
	 * @since 2.16
	 */
	protected ILanguageServerAccess getLanguageServerAccess() {
		return this.access;
	}

	/**
	 * @since 2.16
	 */
	protected LanguageClient getLanguageClient() {
		return this.client;
	}

	/**
	 * @since 2.16
	 */
	protected ExecutableCommandRegistry getCommandRegistry() {
		return this.commandRegistry;
	}

	/**
	 * @since 2.16
	 */
	protected Multimap<String, Endpoint> getExtensionProviders() {
		return ImmutableMultimap.copyOf(this.extensionProviders);
	}

	/**
	 * @since 2.16
	 */
	protected Map<String, JsonRpcMethod> getSupportedMethods() {
		return ImmutableMap.copyOf(this.supportedMethods);
	}

	/**
	 * @since 2.16
	 */
	protected IResourceServiceProvider.Registry getLanguagesRegistry() {
		return this.languagesRegistry;
	}

	/**
	 * @since 2.16
	 */
	protected IReferenceFinder.IResourceAccess getWorkspaceResourceAccess() {
		return this.resourceAccess;
	}

	/**
	 * @since 2.16
	 */
	protected XWorkspaceManager getWorkspaceManager() {
		return this.workspaceManager;
	}

	/**
	 * @since 2.16
	 */
	protected WorkspaceSymbolService getWorkspaceSymbolService() {
		return this.workspaceSymbolService;
	}

	/**
	 * Getter
	 */
	public RequestManager getRequestManager() {
		return this.requestManager;
	}
}
