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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.CallHierarchyIncomingCall;
import org.eclipse.lsp4j.CallHierarchyIncomingCallsParams;
import org.eclipse.lsp4j.CallHierarchyItem;
import org.eclipse.lsp4j.CallHierarchyOutgoingCall;
import org.eclipse.lsp4j.CallHierarchyOutgoingCallsParams;
import org.eclipse.lsp4j.CallHierarchyPrepareParams;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.ColorInformation;
import org.eclipse.lsp4j.ColorPresentation;
import org.eclipse.lsp4j.ColorPresentationParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DeclarationParams;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentColorParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightParams;
import org.eclipse.lsp4j.DocumentLink;
import org.eclipse.lsp4j.DocumentLinkParams;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.FoldingRange;
import org.eclipse.lsp4j.FoldingRangeRequestParams;
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
import org.eclipse.lsp4j.SelectionRange;
import org.eclipse.lsp4j.SelectionRangeParams;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureHelpParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.TypeDefinitionParams;
import org.eclipse.lsp4j.TypeHierarchyItem;
import org.eclipse.lsp4j.TypeHierarchyPrepareParams;
import org.eclipse.lsp4j.TypeHierarchySubtypesParams;
import org.eclipse.lsp4j.TypeHierarchySupertypesParams;
import org.eclipse.lsp4j.WillSaveTextDocumentParams;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.WorkspaceSymbol;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.eclipse.n4js.xtext.ide.server.build.BuilderFrontend;
import org.eclipse.n4js.xtext.ide.server.util.LspLogger;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@SuppressWarnings("deprecation")
@Singleton
public class LanguageServerFrontend implements TextDocumentService, WorkspaceService {

	@Inject
	private BuilderFrontend builderFrontend;

	@Inject
	private TextDocumentFrontend textDocumentFrontend;

	@Inject
	private WorkspaceFrontend workspaceFrontend;

	@Inject
	private LspLogger lspLogger;

	/**
	 * Initialize this front-end according to the given arguments.
	 */
	public void initialize(InitializeParams params, ILanguageServerAccess access) {
		logWelcomeMessage();
		workspaceFrontend.initialize(access);
		textDocumentFrontend.initialize(params, access);
	}

	/**
	 * Sends a welcome message to the client when initialization begins.
	 */
	protected void logWelcomeMessage() {
		lspLogger.log("Connected to LSP server");
	}

	/**
	 * Notifies the front-end that the initialization was completed.
	 */
	public void initialized(URI baseDir) {
		builderFrontend.initialize(baseDir);
	}

	/**
	 * Blocks until all work is done.
	 */
	public void join() {
		builderFrontend.join();
	}

	/**
	 * Orderly shutdown of this front-end.
	 */
	public CompletableFuture<? extends Object> shutdown() {
		return CompletableFuture.runAsync(builderFrontend::shutdown);
	}

	/** Connect this front-end to the given client. */
	public void connect(LanguageClient client) {
		textDocumentFrontend.connect(client);
	}

	/** Disconnect this front-end from the currently connected client. */
	public void disconnect() {
		textDocumentFrontend.disconnect();
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		textDocumentFrontend.didOpen(params);
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		textDocumentFrontend.didChange(params);
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		textDocumentFrontend.didClose(params);
	}

	@Override
	public void willSave(WillSaveTextDocumentParams params) {
		textDocumentFrontend.willSave(params);
	}

	@Override
	public CompletableFuture<List<TextEdit>> willSaveWaitUntil(WillSaveTextDocumentParams params) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		builderFrontend.didSave(params);
	}

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		rebuildWorkspace();
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		builderFrontend.didChangeWatchedFiles(params);
	}

	@Override
	public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
		// ignore for now
	}

	/**
	 * Triggers the deletion of all generated files and clears the type index in the background. Can be awaited by
	 * {@link #join()}.
	 */
	public void clean() {
		builderFrontend.clean();

	}

	/** Triggers rebuild of the whole workspace in the background. Can be awaited by {@link #join()}. */
	public void rebuildWorkspace() {
		rebuildWorkspace(true);
	}

	/** Triggers rebuild of the whole workspace in the background. Can be awaited by {@link #join()}. */
	public void rebuildWorkspace(boolean recreateWorkspace) {
		builderFrontend.rebuildWorkspace(recreateWorkspace);
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(CompletionParams params) {
		return textDocumentFrontend.completion(params);
	}

	@Override
	public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		return textDocumentFrontend.resolveCompletionItem(unresolved);
	}

	@Override
	public CompletableFuture<Hover> hover(HoverParams params) {
		return textDocumentFrontend.hover(params);
	}

	@Override
	public CompletableFuture<SignatureHelp> signatureHelp(SignatureHelpParams params) {
		return textDocumentFrontend.signatureHelp(params);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> declaration(
			DeclarationParams params) {
		return textDocumentFrontend.declaration(params);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			DefinitionParams params) {
		return textDocumentFrontend.definition(params);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> typeDefinition(
			TypeDefinitionParams params) {
		return textDocumentFrontend.typeDefinition(params);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> implementation(
			ImplementationParams params) {
		return textDocumentFrontend.implementation(params);
	}

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		return textDocumentFrontend.references(params);
	}

	@Override
	public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(DocumentHighlightParams params) {
		return textDocumentFrontend.documentHighlight(params);
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {
		return textDocumentFrontend.documentSymbol(params);
	}

	@Override
	public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
		return textDocumentFrontend.codeAction(params);
	}

	@Override
	public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
		return textDocumentFrontend.codeLens(params);
	}

	@Override
	public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
		return textDocumentFrontend.resolveCodeLens(unresolved);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
		return textDocumentFrontend.formatting(params);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
		return textDocumentFrontend.rangeFormatting(params);
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
		return textDocumentFrontend.onTypeFormatting(params);
	}

	@Override
	public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
		return textDocumentFrontend.rename(params);
	}

	@Override
	public CompletableFuture<List<DocumentLink>> documentLink(DocumentLinkParams params) {
		return textDocumentFrontend.documentLink(params);
	}

	@Override
	public CompletableFuture<DocumentLink> documentLinkResolve(DocumentLink params) {
		return textDocumentFrontend.documentLinkResolve(params);
	}

	@Override
	public CompletableFuture<List<ColorInformation>> documentColor(DocumentColorParams params) {
		return textDocumentFrontend.documentColor(params);
	}

	@Override
	public CompletableFuture<List<ColorPresentation>> colorPresentation(ColorPresentationParams params) {
		return textDocumentFrontend.colorPresentation(params);
	}

	@Override
	public CompletableFuture<List<FoldingRange>> foldingRange(FoldingRangeRequestParams params) {
		return textDocumentFrontend.foldingRange(params);
	}

	@Override
	public CompletableFuture<Either3<Range, PrepareRenameResult, PrepareRenameDefaultBehavior>> prepareRename(
			PrepareRenameParams params) {
		return textDocumentFrontend.prepareRename(params);
	}

	@Override
	public CompletableFuture<List<TypeHierarchyItem>> prepareTypeHierarchy(TypeHierarchyPrepareParams params) {
		return textDocumentFrontend.prepareTypeHierarchy(params);
	}

	@Override
	public CompletableFuture<List<TypeHierarchyItem>> typeHierarchySupertypes(TypeHierarchySupertypesParams params) {
		return textDocumentFrontend.typeHierarchySupertypes(params);
	}

	@Override
	public CompletableFuture<List<TypeHierarchyItem>> typeHierarchySubtypes(TypeHierarchySubtypesParams params) {
		return textDocumentFrontend.typeHierarchySubtypes(params);
	}

	@Override
	public CompletableFuture<List<CallHierarchyItem>> prepareCallHierarchy(CallHierarchyPrepareParams params) {
		return textDocumentFrontend.prepareCallHierarchy(params);
	}

	@Override
	public CompletableFuture<List<CallHierarchyIncomingCall>> callHierarchyIncomingCalls(
			CallHierarchyIncomingCallsParams params) {
		return textDocumentFrontend.callHierarchyIncomingCalls(params);
	}

	@Override
	public CompletableFuture<List<CallHierarchyOutgoingCall>> callHierarchyOutgoingCalls(
			CallHierarchyOutgoingCallsParams params) {
		return textDocumentFrontend.callHierarchyOutgoingCalls(params);
	}

	@Override
	public CompletableFuture<List<SelectionRange>> selectionRange(SelectionRangeParams params) {
		return textDocumentFrontend.selectionRange(params);
	}

	@Override
	public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
		return workspaceFrontend.executeCommand(params);
	}

	@Override
	public CompletableFuture<Either<List<? extends SymbolInformation>, List<? extends WorkspaceSymbol>>> symbol(
			WorkspaceSymbolParams params) {
		return workspaceFrontend.symbol(params);
	}
}