/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.symbol;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper;

import com.google.inject.Inject;

/**
 *
 */
public class N4JSDocumentSymbolMapper extends DocumentSymbolMapper {

	@Inject
	LabelCalculationHelper labelHelper;
	@Inject
	SymbolKindCalculationHelper kindCalculationHelper;

	@Override
	public DocumentSymbol toDocumentSymbol(EObject object) {
		SymbolKind symbolKind = kindCalculationHelper.getSymbolKind(object);
		if (symbolKind == null) {
			return null;
		}

		DocumentSymbol documentSymbol = super.toDocumentSymbol(object);
		documentSymbol.setKind(symbolKind);

		String symbolLabel = labelHelper.getSymbolLabel(object);
		if (symbolLabel != null) {
			documentSymbol.setName(symbolLabel);
		}
		return documentSymbol;
	}

}
