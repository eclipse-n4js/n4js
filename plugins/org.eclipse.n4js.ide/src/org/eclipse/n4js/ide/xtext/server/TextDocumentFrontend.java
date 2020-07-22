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
package org.eclipse.n4js.ide.xtext.server;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.ColoringInformation;
import org.eclipse.lsp4j.ColoringParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
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
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientExtensions;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.n4js.ide.xtext.server.ResourceTaskManager.IResourceTaskListener;
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIndex;
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIndex.IIndexListener;
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIssueRegistry;
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIssueRegistry.IIssueRegistryListener;
import org.eclipse.n4js.ide.xtext.server.build.ConcurrentIssueRegistry.IssueRegistryChangeEvent;
import org.eclipse.n4js.ide.xtext.server.contentassist.XContentAssistService;
import org.eclipse.n4js.ide.xtext.server.rename.XIRenameService;
import org.eclipse.n4js.xtext.workspace.IProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.IWorkspaceConfigSnapshot;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.codelens.ICodeLensResolver;
import org.eclipse.xtext.ide.server.codelens.ICodeLensService;
import org.eclipse.xtext.ide.server.coloring.IColoringService;
import org.eclipse.xtext.ide.server.formatting.FormattingService;
import org.eclipse.xtext.ide.server.hover.IHoverService;
import org.eclipse.xtext.ide.server.occurrences.IDocumentHighlightService;
import org.eclipse.xtext.ide.server.rename.IRenameService2;
import org.eclipse.xtext.ide.server.semanticHighlight.SemanticHighlightingRegistry;
import org.eclipse.xtext.ide.server.signatureHelp.ISignatureHelpService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.IDocumentSymbolService;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
@Singleton
public class TextDocumentFrontend implements TextDocumentService, IIndexListener, IIssueRegistryListener,
		IResourceTaskListener {

	@Inject
	private ResourceTaskManager resourceTaskManager;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private IResourceServiceProvider.Registry languagesRegistry;

	@Inject
	private IssueAcceptor issueAcceptor;

	@Inject
	private ConcurrentIndex index;

	@Inject
	private ConcurrentIssueRegistry issueRegistry;

	@Inject
	private SemanticHighlightingRegistry semanticHighlightingRegistry;

	private LanguageClient client;

	private boolean hierarchicalSymbols;

	private ILanguageServerAccess access;

	/** Sets connection to client */
	public void connect(LanguageClient _client) {
		this.client = _client;
	}

	/** Sets non-injectable fields */
	public void initialize(InitializeParams _initializeParams, ILanguageServerAccess _access) {
		this.hierarchicalSymbols = isHierarchicalDocumentSymbolSupport(_initializeParams);
		this.access = _access;
		index.addListener(this);
		issueRegistry.addListener(this);
	}

	/** Resets non-injectable fields */
	public void disconnect() {
		this.client = null;
		this.access = null;
		index.removeListener(this);
		issueRegistry.removeListener(this);
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		TextDocumentItem textDocument = params.getTextDocument();
		URI uri = getURI(textDocument);
		resourceTaskManager.createContext(uri, textDocument.getVersion(), textDocument.getText());
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		VersionedTextDocumentIdentifier textDocument = params.getTextDocument();
		URI uri = getURI(textDocument);
		resourceTaskManager.changeSourceTextOfExistingContext(uri, textDocument.getVersion(),
				params.getContentChanges());
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		TextDocumentIdentifier textDocument = params.getTextDocument();
		URI uri = getURI(textDocument);
		resourceTaskManager.closeContext(uri);
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
		URI uri = getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "completion", (rtc, ci) -> {
			return completion(rtc, params, ci);
		});
	}

	/** Compute the completion items. */
	protected Either<List<CompletionItem>, CompletionList> completion(ResourceTaskContext rtc, CompletionParams params,
			CancelIndicator originalCancelIndicator) {
		URI uri = rtc.getURI();
		XContentAssistService contentAssistService = getService(uri, XContentAssistService.class);
		if (contentAssistService == null) {
			return Either.forRight(new CompletionList());
		}
		BufferedCancelIndicator cancelIndicator = new BufferedCancelIndicator(
				originalCancelIndicator,
				Duration.ofMillis(750));
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return Either.forRight(contentAssistService.createCompletionList(doc, res, params, cancelIndicator));
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			TextDocumentPositionParams params) {
		URI uri = getURI(params);
		// LSP clients will usually use this request for open files only, but that is not strictly required by the LSP
		// specification, so we use "runInExistingOrTemporary..." here:
		return resourceTaskManager.runInExistingOrTemporaryContext(uri, "definition", (rtc, ci) -> {
			return definition(rtc, params, ci);
		});
	}

	/** Compute the definition. Executed in a read request. */
	protected Either<List<? extends Location>, List<? extends LocationLink>> definition(ResourceTaskContext rtc,
			TextDocumentPositionParams params, CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		DocumentSymbolService documentSymbolService = getService(uri, DocumentSymbolService.class);
		if (documentSymbolService == null) {
			return Either.forLeft(Collections.emptyList());
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return Either.forLeft(documentSymbolService.getDefinitions(doc, res, params, resourceTaskManager, cancelIndicator));
	}

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		URI uri = getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "references", (rtc, ci) -> {
			return references(rtc, params, ci);
		});
	}

	/** Compute the references. Executed in read request. */
	protected List<? extends Location> references(ResourceTaskContext rtc, ReferenceParams params,
			CancelIndicator cancelIndicator) {

		URI uri = rtc.getURI();
		DocumentSymbolService documentSymbolService = getService(uri, DocumentSymbolService.class);
		if ((documentSymbolService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return documentSymbolService.getReferences(doc, res, params, resourceTaskManager,
				resourceTaskManager.createLiveScopeIndex(), cancelIndicator);
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {

		URI uri = getURI(params.getTextDocument());
		return resourceTaskManager.runInExistingContext(uri, "documentSymbol", (rtc, ci) -> {
			return documentSymbol(rtc, params, ci);
		});
	}

	/** Compute the symbol information. Executed in a read request. */
	protected List<Either<SymbolInformation, DocumentSymbol>> documentSymbol(ResourceTaskContext rtc,
			DocumentSymbolParams params, CancelIndicator cancelIndicator) {

		URI uri = rtc.getURI();
		IDocumentSymbolService documentSymbolService = getIDocumentSymbolService(getResourceServiceProvider(uri));
		if ((documentSymbolService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return documentSymbolService.getSymbols(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<Hover> hover(TextDocumentPositionParams params) {
		URI uri = getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "hover", (rtc, ci) -> {
			return hover(rtc, params, ci);
		});
	}

	/** Compute the hover. Executed in a read request. */
	protected Hover hover(ResourceTaskContext rtc, TextDocumentPositionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		IHoverService hoverService = getService(uri, IHoverService.class);
		if (hoverService == null) {
			return IHoverService.EMPTY_HOVER;
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return hoverService.hover(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		return CompletableFuture.<CompletionItem> completedFuture(unresolved);
	}

	@Override
	public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams params) {
		URI uri = getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "signatureHelp", (rtc, ci) -> {
			return signatureHelp(rtc, params, ci);
		});
	}

	/** Compute the signature help. Executed in a read request. */
	protected SignatureHelp signatureHelp(ResourceTaskContext rtc, TextDocumentPositionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		ISignatureHelpService helper = getService(uri, ISignatureHelpService.class);
		if (helper == null) {
			return ISignatureHelpService.EMPTY;
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return helper.getSignatureHelp(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams params) {
		URI uri = getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "documentHighlight", (rtc, ci) -> {
			return documentHighlight(rtc, params, ci);
		});
	}

	/** Compute the document highlights. Executed in a read request. */
	protected List<? extends DocumentHighlight> documentHighlight(ResourceTaskContext rtc,
			TextDocumentPositionParams params, CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		IDocumentHighlightService service = getService(uri, IDocumentHighlightService.class);
		if (service == null) {
			return Collections.emptyList();
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return service.getDocumentHighlights(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
		URI uri = getURI(params.getTextDocument());
		return resourceTaskManager.runInExistingContext(uri, "codeAction", (rtc, ci) -> {
			return codeAction(rtc, params, ci);
		});
	}

	/** Compute the code action commands. Executed in a read request. */
	protected List<Either<Command, CodeAction>> codeAction(ResourceTaskContext rtc, CodeActionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		IResourceServiceProvider serviceProvider = getResourceServiceProvider(uri);
		ICodeActionService service = getService(serviceProvider, ICodeActionService.class);
		ICodeActionService2 service2 = getService(serviceProvider, ICodeActionService2.class);
		if (service == null && service2 == null) {
			return Collections.emptyList();
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();

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

	@Override
	public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
		URI uri = getURI(params.getTextDocument());
		return resourceTaskManager.runInExistingContext(uri, "codeLens", (rtc, ci) -> {
			return codeLens(rtc, params, ci);
		});
	}

	/** Compute the code lenses. */
	protected List<? extends CodeLens> codeLens(ResourceTaskContext rtc, CodeLensParams params,
			CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		ICodeLensService codeLensService = getService(uri, ICodeLensService.class);
		if ((codeLensService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
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
		return resourceTaskManager.runInExistingOrTemporaryContext(uri, "resolveCodeLens", (rtc, ci) -> {
			return resolveCodeLens(rtc, unresolved, ci);
		});
	}

	/** Resolve the given code lens. */
	protected CodeLens resolveCodeLens(ResourceTaskContext rtc, CodeLens unresolved, CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		ICodeLensResolver resolver = getService(uri, ICodeLensResolver.class);
		if (resolver == null) {
			return unresolved;
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return resolver.resolveCodeLens(doc, res, unresolved, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
		URI uri = getURI(params.getTextDocument());
		return resourceTaskManager.runInExistingContext(uri, "formatting", (rtc, ci) -> {
			return formatting(rtc, params, ci);
		});
	}

	/** Create the text edits for the formatter. Executed in a read request. */
	protected List<? extends TextEdit> formatting(ResourceTaskContext rtc, DocumentFormattingParams params,
			CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		FormattingService formatterService = getService(uri, FormattingService.class);
		if ((formatterService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return formatterService.format(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
		URI uri = getURI(params.getTextDocument());
		return resourceTaskManager.runInExistingContext(uri, "rangeFormatting", (rtc, ci) -> {
			return rangeFormatting(rtc, params, ci);
		});
	}

	/** Create the text edits for the formatter. Executed in a read request. */
	protected List<? extends TextEdit> rangeFormatting(ResourceTaskContext rtc, DocumentRangeFormattingParams params,
			CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		FormattingService formatterService = getService(uri, FormattingService.class);
		if ((formatterService == null)) {
			return Collections.emptyList();
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return formatterService.format(doc, res, params, cancelIndicator);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}

	@Override
	public CompletableFuture<WorkspaceEdit> rename(RenameParams renameParams) {
		URI uri = getURI(renameParams.getTextDocument());
		return resourceTaskManager.runInExistingContext(uri, "rename", (rtc, ci) -> {
			return rename(rtc, renameParams, ci);
		});
	}

	/** Compute the rename edits. Executed in a read request. */
	protected WorkspaceEdit rename(ResourceTaskContext rtc, RenameParams renameParams,
			CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();

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

	@Override
	public CompletableFuture<Either<Range, PrepareRenameResult>> prepareRename(TextDocumentPositionParams params) {
		URI uri = getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "prepareRename", (rtc, ci) -> {
			return prepareRename(rtc, params, ci);
		});
	}

	/** Prepare the rename operation. Executed in a read request. */
	protected Either<Range, PrepareRenameResult> prepareRename(ResourceTaskContext rtc,
			TextDocumentPositionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
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
	public void didSave(DidSaveTextDocumentParams params) {
		throw new UnsupportedOperationException();
	}

	/** Obtain the URI from the given parameters. */
	public URI getURI(TextDocumentPositionParams params) {
		return getURI(params.getTextDocument());
	}

	/** Obtain the URI from the given identifier. */
	public URI getURI(TextDocumentIdentifier documentIdentifier) {
		return uriExtensions.toUri(documentIdentifier.getUri());
	}

	/** Obtain the URI from the given document item. */
	public URI getURI(TextDocumentItem documentItem) {
		return uriExtensions.toUri(documentItem.getUri());
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
	 * @param uri
	 *            the current URI
	 * @return the resource service provider or null.
	 */
	protected IResourceServiceProvider getResourceServiceProvider(URI uri) {
		return languagesRegistry.getResourceServiceProvider(uri);
	}

	/**
	 * @param <Service>
	 *            the type of the service
	 * @param serviceProvider
	 *            the resource service provider. May be null
	 * @param type
	 *            the type of the service
	 * @return the service instance or null if not available.
	 */
	protected <Service> Service getService(IResourceServiceProvider serviceProvider, Class<Service> type) {
		if (serviceProvider == null) {
			return null;
		}
		return serviceProvider.get(type);
	}

	/**
	 * @param serviceProvider
	 *            the resource service provider. May be null
	 * @return the service instance or null if not available.
	 */
	protected IDocumentSymbolService getIDocumentSymbolService(IResourceServiceProvider serviceProvider) {
		if ((serviceProvider == null)) {
			return null;
		}
		if (hierarchicalSymbols) {
			return serviceProvider.get(HierarchicalDocumentSymbolService.class);
		} else {
			return serviceProvider.get(DocumentSymbolService.class);
		}
	}

	/**
	 * {@code true} if the {@code TextDocumentClientCapabilities} explicitly declares the hierarchical document symbol
	 * support at LS initialization time. Otherwise, false.
	 */
	protected boolean isHierarchicalDocumentSymbolSupport(InitializeParams initializeParams) {
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

	/** Convert the given parameters to an enriched instance of options. */
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

	/** Put the document uri into the data of the given code lenses. */
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

	/** Remove the document uri from the data of the given code lens. */
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
	public void onIndexChanged(
			IWorkspaceConfigSnapshot newWorkspaceConfig,
			Map<String, ? extends ResourceDescriptionsData> changedDescriptions,
			List<? extends IProjectConfigSnapshot> changedProjects,
			Set<String> removedProjects) {

		resourceTaskManager.updatePersistedState(newWorkspaceConfig, changedDescriptions, changedProjects,
				removedProjects);
	}

	@Override
	public void onIssuesChanged(ImmutableList<IssueRegistryChangeEvent> events) {
		for (IssueRegistryChangeEvent event : events) {
			if (event.persistedState && resourceTaskManager.isOpen(event.uri)) {
				// for open files we ignore issue changes sent by builder
				continue;
			}
			Iterable<LSPIssue> issuesToSend = event.dirtyState ? event.dirtyIssuesNew : event.persistedIssuesNew;
			if (event.dirtyState && issuesToSend == null) {
				// dirty state for a resource was entirely removed, so send its persisted state (if any)
				issuesToSend = event.persistedIssuesNew;
			}
			issueAcceptor.publishDiagnostics(event.uri, issuesToSend != null ? issuesToSend : Collections.emptyList());
		}
	}

	@Override
	public void didRefreshContext(ResourceTaskContext rtc, CancelIndicator ci) {
		if (client instanceof LanguageClientExtensions) {

			LanguageClientExtensions clientExtensions = (LanguageClientExtensions) client;
			XtextResource resource = rtc.getResource();
			IResourceServiceProvider resourceServiceProvider = resource.getResourceServiceProvider();
			IColoringService coloringService = resourceServiceProvider.get(IColoringService.class);

			if (coloringService != null) {
				XDocument doc = rtc.getDocument();
				List<? extends ColoringInformation> colInfos = coloringService.getColoring(resource, doc);

				if (!IterableExtensions.isNullOrEmpty(colInfos)) {
					String uri = resource.getURI().toString();
					ColoringParams colParams = new ColoringParams(uri, colInfos);
					clientExtensions.updateColoring(colParams);
				}
			}
		}

		ILanguageServerAccess.Context ctx = new ILanguageServerAccess.Context(rtc.getResource(), rtc.getDocument(),
				true, ci);
		semanticHighlightingRegistry.update(ctx);
	}
}
