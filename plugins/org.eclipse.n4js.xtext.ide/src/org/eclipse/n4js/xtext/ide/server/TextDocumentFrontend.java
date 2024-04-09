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
package org.eclipse.n4js.xtext.ide.server;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.CallHierarchyIncomingCall;
import org.eclipse.lsp4j.CallHierarchyIncomingCallsParams;
import org.eclipse.lsp4j.CallHierarchyItem;
import org.eclipse.lsp4j.CallHierarchyOutgoingCall;
import org.eclipse.lsp4j.CallHierarchyOutgoingCallsParams;
import org.eclipse.lsp4j.CallHierarchyPrepareParams;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightParams;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverParams;
import org.eclipse.lsp4j.ImplementationParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.PrepareRenameDefaultBehavior;
import org.eclipse.lsp4j.PrepareRenameParams;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureHelpParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.TypeHierarchyItem;
import org.eclipse.lsp4j.TypeHierarchyPrepareParams;
import org.eclipse.lsp4j.TypeHierarchySubtypesParams;
import org.eclipse.lsp4j.TypeHierarchySupertypesParams;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.n4js.xtext.ide.server.build.ConcurrentIndex;
import org.eclipse.n4js.xtext.ide.server.build.ConcurrentIndex.IIndexListener;
import org.eclipse.n4js.xtext.ide.server.contentassist.XContentAssistService;
import org.eclipse.n4js.xtext.ide.server.findReferences.XWorkspaceResourceAccess;
import org.eclipse.n4js.xtext.ide.server.rename.XIRenameService;
import org.eclipse.n4js.xtext.ide.server.util.ParamHelper;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.findReferences.IReferenceFinder.IResourceAccess;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.codelens.ICodeLensResolver;
import org.eclipse.xtext.ide.server.codelens.ICodeLensService;
import org.eclipse.xtext.ide.server.formatting.FormattingService;
import org.eclipse.xtext.ide.server.hover.IHoverService;
import org.eclipse.xtext.ide.server.occurrences.IDocumentHighlightService;
import org.eclipse.xtext.ide.server.rename.IRenameService2;
import org.eclipse.xtext.ide.server.signatureHelp.ISignatureHelpService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.ide.server.symbol.IDocumentSymbolService;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@SuppressWarnings({ "restriction", "deprecation" })
@Singleton
public class TextDocumentFrontend implements TextDocumentService, IIndexListener {

	/***/
	@Inject
	protected ResourceTaskManager resourceTaskManager;

	/***/
	@Inject
	protected ParamHelper paramHelper;

	/***/
	@Inject
	protected IResourceServiceProvider.Registry languagesRegistry;

	/***/
	@Inject
	protected ConcurrentIndex concurrentIndex;

	/***/
	private boolean hierarchicalSymbols;

	/***/
	protected IResourceAccess resourceAccess;

	/***/
	protected ILanguageServerAccess langServerAccess;

	/** Sets connection to client */
	public void connect(@SuppressWarnings("unused") LanguageClient client) {
		// nothing to do
	}

	/** Sets non-injectable fields */
	public void initialize(InitializeParams initializeParams, ILanguageServerAccess access) {
		this.hierarchicalSymbols = isHierarchicalDocumentSymbolSupport(initializeParams);
		this.resourceAccess = new XWorkspaceResourceAccess(resourceTaskManager);
		this.langServerAccess = access;
		concurrentIndex.addListener(this);
	}

	/** Resets non-injectable fields */
	public void disconnect() {
		this.resourceAccess = null;
		this.langServerAccess = null;
		concurrentIndex.removeListener(this);
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		URI uri = paramHelper.getURI(params);
		TextDocumentItem textDocument = params.getTextDocument();
		resourceTaskManager.createContext(uri, textDocument.getVersion(), textDocument.getText());
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		URI uri = paramHelper.getURI(params);
		VersionedTextDocumentIdentifier textDocument = params.getTextDocument();
		resourceTaskManager.changeSourceTextOfExistingContext(uri, textDocument.getVersion(),
				params.getContentChanges());
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		URI uri = paramHelper.getURI(params);
		resourceTaskManager.disposeContext(uri);
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
		URI uri = paramHelper.getURI(params);
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
			DefinitionParams params) {
		URI uri = paramHelper.getURI(params);
		// LSP clients will usually use this request for open files only, but that is not strictly required by the LSP
		// specification, so we use "runInExistingOrTemporary..." here:
		return resourceTaskManager.runInExistingOrTemporaryContext(uri, "definition", (rtc, ci) -> {
			return definition(rtc, params, ci);
		});
	}

	/** Compute the definition. Executed in a read request. */
	protected Either<List<? extends Location>, List<? extends LocationLink>> definition(ResourceTaskContext rtc,
			DefinitionParams params, CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		DocumentSymbolService documentSymbolService = getService(uri, DocumentSymbolService.class);
		if (documentSymbolService == null) {
			return Either.forLeft(Collections.emptyList());
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		return Either.forLeft(documentSymbolService.getDefinitions(doc, res, params, resourceAccess, cancelIndicator));
	}

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		URI uri = paramHelper.getURI(params);
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
		return documentSymbolService.getReferences(doc, res, params, resourceAccess,
				resourceTaskManager.createLiveScopeIndex(), cancelIndicator);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> implementation(
			ImplementationParams params) {

		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "implementation", (rtc, ci) -> {
			return implementation(rtc, params, ci);
		});
	}

	/** Compute the implementation locations. */
	@SuppressWarnings("unused")
	protected Either<List<? extends Location>, List<? extends LocationLink>> implementation(ResourceTaskContext rtc,
			ImplementationParams params, CancelIndicator cancelIndicator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {

		URI uri = paramHelper.getURI(params);
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
	public CompletableFuture<Hover> hover(HoverParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "hover", (rtc, ci) -> {
			return hover(rtc, params, ci);
		});
	}

	/** Compute the hover. Executed in a read request. */
	protected Hover hover(ResourceTaskContext rtc, HoverParams params,
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
	public CompletableFuture<SignatureHelp> signatureHelp(SignatureHelpParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "signatureHelp", (rtc, ci) -> {
			return signatureHelp(rtc, params, ci);
		});
	}

	/** Compute the signature help. Executed in a read request. */
	protected SignatureHelp signatureHelp(ResourceTaskContext rtc, SignatureHelpParams params,
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
	public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(DocumentHighlightParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "documentHighlight", (rtc, ci) -> {
			return documentHighlight(rtc, params, ci);
		});
	}

	/** Compute the document highlights. Executed in a read request. */
	protected List<? extends DocumentHighlight> documentHighlight(ResourceTaskContext rtc,
			DocumentHighlightParams params, CancelIndicator cancelIndicator) {

		URI uri = rtc.getURI();
		IDocumentHighlightService service = getService(uri, IDocumentHighlightService.class);
		if (service == null) {
			return Collections.emptyList();
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();
		try {
			return service.getDocumentHighlights(doc, res, params, cancelIndicator);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "codeAction", (rtc, ci) -> {
			return codeAction(rtc, params, ci);
		});
	}

	/** Compute the code action commands. Executed in a read request. */
	protected List<Either<Command, CodeAction>> codeAction(ResourceTaskContext rtc, CodeActionParams params,
			CancelIndicator cancelIndicator) {
		URI uri = rtc.getURI();
		IResourceServiceProvider serviceProvider = getResourceServiceProvider(uri);
		ICodeActionService2 service2 = getService(serviceProvider, ICodeActionService2.class);
		if (service2 == null) {
			return Collections.emptyList();
		}
		XtextResource res = rtc.getResource();
		XDocument doc = rtc.getDocument();

		List<Either<Command, CodeAction>> result = new ArrayList<>();
		ICodeActionService2.Options options = toOptions(params, doc, res, cancelIndicator);
		List<Either<Command, CodeAction>> actions = service2.getCodeActions(options);
		if (actions != null) {
			result.addAll(actions);
		}
		return result;
	}

	@Override
	public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
		URI uri = paramHelper.getURI(params);
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
		URI uri = paramHelper.getURI(params);
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
		URI uri = paramHelper.getURI(params);
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
	public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInTemporaryContext(uri, "rename", false, (rtc, ci) -> {
			return rename(rtc, params, ci);
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
			options.setLanguageServerAccess(langServerAccess);
			options.setRenameParams(renameParams);
			options.setCancelIndicator(cancelIndicator);
			return renameService2.rename(options);
		}
		return new WorkspaceEdit();
	}

	@Override
	public CompletableFuture<Either3<Range, PrepareRenameResult, PrepareRenameDefaultBehavior>> prepareRename(
			PrepareRenameParams params) {

		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "prepareRename", (rtc, ci) -> {
			return prepareRename(rtc, params, ci);
		});
	}

	/** Prepare the rename operation. Executed in a read request. */
	protected Either3<Range, PrepareRenameResult, PrepareRenameDefaultBehavior> prepareRename(ResourceTaskContext rtc,
			PrepareRenameParams params, CancelIndicator cancelIndicator) {

		URI uri = rtc.getURI();
		IRenameService2 renameService = getService(uri, IRenameService2.class);
		if (renameService == null) {
			throw new UnsupportedOperationException();
		}
		IRenameService2.PrepareRenameOptions options = new IRenameService2.PrepareRenameOptions();
		options.setLanguageServerAccess(langServerAccess);
		options.setParams(params);
		options.setCancelIndicator(cancelIndicator);
		return renameService.prepareRename(options);
	}

	@Override
	public CompletableFuture<List<CallHierarchyItem>> prepareCallHierarchy(CallHierarchyPrepareParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "prepareCallHierarchy", (rtc, ci) -> {
			return prepareCallHierarchy(rtc, params, ci);
		});
	}

	/** Returns a list of outgoing calls */
	@SuppressWarnings("unused")
	protected List<CallHierarchyItem> prepareCallHierarchy(ResourceTaskContext rtc,
			CallHierarchyPrepareParams params, CancelIndicator ci) {
		// enable capability in XLanguageServerImpl and implement this method in subclass
		return Collections.emptyList();
	}

	@Override
	public CompletableFuture<List<CallHierarchyIncomingCall>> callHierarchyIncomingCalls(
			CallHierarchyIncomingCallsParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInTemporaryContext(uri, "callHierarchyIncomingCalls", false, (rtc, ci) -> {
			return callHierarchyIncomingCalls(rtc, params, ci);
		});
	}

	/** Returns a list of incoming calls */
	@SuppressWarnings("unused")
	protected List<CallHierarchyIncomingCall> callHierarchyIncomingCalls(ResourceTaskContext rtc,
			CallHierarchyIncomingCallsParams params, CancelIndicator ci) {
		// enable capability in XLanguageServerImpl and implement this method in subclass
		return Collections.emptyList();
	}

	@Override
	public CompletableFuture<List<CallHierarchyOutgoingCall>> callHierarchyOutgoingCalls(
			CallHierarchyOutgoingCallsParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInTemporaryContext(uri, "callHierarchyOutgoingCalls", false, (rtc, ci) -> {
			return callHierarchyOutgoingCalls(rtc, params, ci);
		});
	}

	/** Returns a list of outgoing calls */
	@SuppressWarnings("unused")
	protected List<CallHierarchyOutgoingCall> callHierarchyOutgoingCalls(ResourceTaskContext rtc,
			CallHierarchyOutgoingCallsParams params, CancelIndicator ci) {
		// enable capability in XLanguageServerImpl and implement this method in subclass
		return Collections.emptyList();
	}

	@Override
	public CompletableFuture<List<TypeHierarchyItem>> prepareTypeHierarchy(TypeHierarchyPrepareParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInExistingContext(uri, "prepareTypeHierarchy", (rtc, ci) -> {
			return prepareTypeHierarchy(rtc, params, ci);
		});
	}

	/** Returns types that the request is performed upon */
	@SuppressWarnings("unused")
	protected List<TypeHierarchyItem> prepareTypeHierarchy(ResourceTaskContext rtc, TypeHierarchyPrepareParams params,
			CancelIndicator ci) {

		// enable capability in XLanguageServerImpl and implement this method in subclass
		return Collections.emptyList();
	}

	@Override
	public CompletableFuture<List<TypeHierarchyItem>> typeHierarchySubtypes(TypeHierarchySubtypesParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInTemporaryContext(uri, "typeHierarchySubtypes", false, (rtc, ci) -> {
			return typeHierarchySubtypes(rtc, params, ci);
		});
	}

	/** Returns all subtypes of the type given in params */
	@SuppressWarnings("unused")
	protected List<TypeHierarchyItem> typeHierarchySubtypes(ResourceTaskContext rtc, TypeHierarchySubtypesParams params,
			CancelIndicator ci) {

		return Collections.emptyList();
	}

	@Override
	public CompletableFuture<List<TypeHierarchyItem>> typeHierarchySupertypes(TypeHierarchySupertypesParams params) {
		URI uri = paramHelper.getURI(params);
		return resourceTaskManager.runInTemporaryContext(uri, "typeHierarchySupertypes", false, (rtc, ci) -> {
			return typeHierarchySupertypes(rtc, params, ci);
		});
	}

	/** Returns all supertypes of the type given in params */
	@SuppressWarnings("unused")
	protected List<TypeHierarchyItem> typeHierarchySupertypes(ResourceTaskContext rtc,
			TypeHierarchySupertypesParams params, CancelIndicator ci) {

		return Collections.emptyList();
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		throw new UnsupportedOperationException();
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
		options.setLanguageServerAccess(langServerAccess);
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
			WorkspaceConfigSnapshot newWorkspaceConfig,
			Map<String, ? extends ResourceDescriptionsData> changedDescriptions,
			List<? extends ProjectConfigSnapshot> changedProjects,
			Set<String> removedProjects) {

		resourceTaskManager.updatePersistedState(newWorkspaceConfig, changedDescriptions, changedProjects,
				removedProjects);
	}

}
