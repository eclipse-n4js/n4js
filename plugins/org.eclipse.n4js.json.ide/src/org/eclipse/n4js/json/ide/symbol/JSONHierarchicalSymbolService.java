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
package org.eclipse.n4js.json.ide.symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

/**
 * Creates the JSON outline tree for LSP based editors.
 */
public class JSONHierarchicalSymbolService extends HierarchicalDocumentSymbolService {

	@Inject
	private DocumentSymbolMapper symbolMapper;

	@Inject
	private OperationCanceledManager operationCanceledManager;

	@Override
	public List<Either<SymbolInformation, DocumentSymbol>> getSymbols(XtextResource resource,
			CancelIndicator cancelIndicator) {
		List<Either<SymbolInformation, DocumentSymbol>> result = new ArrayList<>();
		for (EObject content : resource.getContents()) {
			if (content instanceof JSONDocument) {
				JSONDocument document = (JSONDocument) content;
				JSONValue rootValue = document.getContent();
				getSymbols(rootValue, symbol -> result.add(Either.forRight(symbol)), cancelIndicator);
			}
		}
		return result;
	}

	private void getSymbols(EObject value, Consumer<? super DocumentSymbol> acceptor, CancelIndicator ci) {
		operationCanceledManager.checkCanceled(ci);
		if (value instanceof NameValuePair) {
			NameValuePair nameValuePair = (NameValuePair) value;
			DocumentSymbol symbol = symbolMapper.toDocumentSymbol(nameValuePair);
			if (isValid(symbol)) {
				acceptor.accept(symbol);
				JSONValue pairValue = ((NameValuePair) value).getValue();
				if (pairValue != null && pairValue.isContainer()) {
					List<DocumentSymbol> children = symbol.getChildren();
					getSymbols(pairValue, children::add, ci);
				}
			}
		} else if (value instanceof JSONValue) {
			JSONValue casted = (JSONValue) value;
			if (casted.isContainer()) {
				for (EObject child : ((JSONValue) value).getChildren()) {
					getSymbols(child, acceptor, ci);
				}
			} else {
				DocumentSymbol symbol = symbolMapper.toDocumentSymbol(casted);
				if (isValid(symbol)) {
					acceptor.accept(symbol);
				}
			}
		}
	}

}
