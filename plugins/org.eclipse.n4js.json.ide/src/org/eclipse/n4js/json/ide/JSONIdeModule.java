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

import org.eclipse.n4js.json.ide.codeActions.JSONCodeActionService;
import org.eclipse.n4js.json.ide.contentassist.JSONIdeContentProposalProvider;
import org.eclipse.n4js.json.ide.contentassist.PatchedContentAssistService;
import org.eclipse.n4js.json.ide.symbol.JSONDocumentSymbolKindProvider;
import org.eclipse.n4js.json.ide.symbol.JSONDocumentSymbolNameProvider;
import org.eclipse.n4js.json.ide.symbol.JSONHierarchicalSymbolService;
import org.eclipse.n4js.xtext.server.EmfDiagnosticToLSPIssueConverter;
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.ide.server.contentassist.ContentAssistService;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolKindProvider;
import org.eclipse.xtext.ide.server.symbol.DocumentSymbolMapper.DocumentSymbolNameProvider;
import org.eclipse.xtext.ide.server.symbol.HierarchicalDocumentSymbolService;
import org.eclipse.xtext.validation.IDiagnosticConverter;

/**
 * Use this class to register ide components.
 */
@SuppressWarnings({ "javadoc", "restriction" })
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

	public Class<? extends IdeContentProposalProvider> bindIdeContentProposalProvider() {
		return JSONIdeContentProposalProvider.class;
	}

	public Class<? extends IDiagnosticConverter> bindIDiagnosticConverter() {
		return EmfDiagnosticToLSPIssueConverter.class;
	}

	public Class<? extends ContentAssistService> bindContentAssistService() {
		return PatchedContentAssistService.class;
	}

	public Class<? extends ICodeActionService2> bindCodeActionService() {
		return JSONCodeActionService.class;
	}
}
