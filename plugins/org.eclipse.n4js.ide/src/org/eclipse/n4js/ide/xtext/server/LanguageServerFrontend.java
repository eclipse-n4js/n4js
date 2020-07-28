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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.CallHierarchyCall;
import org.eclipse.lsp4j.CallHierarchyParams;
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
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.ResolveTypeHierarchyItemParams;
import org.eclipse.lsp4j.SelectionRange;
import org.eclipse.lsp4j.SelectionRangeParams;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.TypeHierarchyItem;
import org.eclipse.lsp4j.TypeHierarchyParams;
import org.eclipse.lsp4j.WillSaveTextDocumentParams;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.eclipse.n4js.ide.xtext.server.build.BuilderFrontend;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;

import com.google.common.annotations.Beta;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class LanguageServerFrontend implements TextDocumentService, WorkspaceService {

	@Inject
	private BuilderFrontend builderFrontend;

	@Inject
	private TextDocumentFrontend textDocumentFrontend;

	@Inject
	private WorkspaceFrontend workspaceFrontend;

	/**
	 * Initialize this front-end according to the given arguments.
	 */
	public void initialize(InitializeParams params, URI baseDir, ILanguageServerAccess access) {
		workspaceFrontend.initialize(access);
		textDocumentFrontend.initialize(params, access);
		builderFrontend.initialize(baseDir);
	}

	/**
	 * Notifies the front-end that the initialization was completed.
	 */
	public void initialized() {
		builderFrontend.initialBuild();
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
		reinitWorkspace();
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
	public void reinitWorkspace() {
		builderFrontend.reinitWorkspace();
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
	public CompletableFuture<Hover> hover(TextDocumentPositionParams position) {
		return textDocumentFrontend.hover(position);
	}

	@Override
	public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams position) {
		return textDocumentFrontend.signatureHelp(position);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> declaration(
			TextDocumentPositionParams params) {
		return textDocumentFrontend.declaration(params);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			TextDocumentPositionParams position) {
		return textDocumentFrontend.definition(position);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> typeDefinition(
			TextDocumentPositionParams position) {
		return textDocumentFrontend.typeDefinition(position);
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> implementation(
			TextDocumentPositionParams position) {
		return textDocumentFrontend.implementation(position);
	}

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		return textDocumentFrontend.references(params);
	}

	@Override
	public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams position) {
		return textDocumentFrontend.documentHighlight(position);
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
	public CompletableFuture<Either<Range, PrepareRenameResult>> prepareRename(TextDocumentPositionParams params) {
		return textDocumentFrontend.prepareRename(params);
	}

	@Override
	@Beta
	public CompletableFuture<TypeHierarchyItem> typeHierarchy(TypeHierarchyParams params) {
		return textDocumentFrontend.typeHierarchy(params);
	}

	@Override
	@Beta
	public CompletableFuture<TypeHierarchyItem> resolveTypeHierarchy(ResolveTypeHierarchyItemParams params) {
		return textDocumentFrontend.resolveTypeHierarchy(params);
	}

	@Override
	@Beta
	public CompletableFuture<List<CallHierarchyCall>> callHierarchy(CallHierarchyParams params) {
		return textDocumentFrontend.callHierarchy(params);
	}

	@Override
	@Beta
	public CompletableFuture<List<SelectionRange>> selectionRange(SelectionRangeParams params) {
		return textDocumentFrontend.selectionRange(params);
	}

	@Override
	public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
		return workspaceFrontend.executeCommand(params);
	}

	@Override
	public CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params) {
		return workspaceFrontend.symbol(params);
	}
}