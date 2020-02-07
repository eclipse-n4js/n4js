/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.ide;

import org.eclipse.n4js.json.ide.symbol.JSONDocumentSymbolKindProvider;
import org.eclipse.n4js.json.ide.symbol.JSONDocumentSymbolNameProvider;
import org.eclipse.n4js.json.ide.symbol.JSONHierarchicalSymbolService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolKindProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolNameProvider;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;

/**
 * Use this class to register ide components.
 */
@SuppressWarnings("javadoc")
public class JSONIdeModule extends AbstractJSONIdeModule {

	public Class<? extends HierarchicalDocumentSymbolService> bindHierarchicalDocumentSymbolService() {
		return JSONHierarchicalSymbolService.class;
	}

	public Class<? extends DocumentSymbolNameProvider> bindDocumentSymbolNameProvider() {
		return JSONDocumentSymbolNameProvider.class;
	}

	public Class<? extends DocumentSymbolKindProvider> bindDocumentSymbolKindProvider() {
		return JSONDocumentSymbolKindProvider.class;
	}

}
