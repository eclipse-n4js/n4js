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
package org.eclipse.n4js.json.ui;

import org.eclipse.n4js.json.ui.editor.autoedit.JSONAutoEditStrategyProvider;
import org.eclipse.n4js.json.ui.editor.hyperlinking.JSONHyperlinkHelperProvider;
import org.eclipse.n4js.json.ui.editor.syntaxcoloring.JSONHighlightingConfiguration;
import org.eclipse.n4js.json.ui.editor.syntaxcoloring.JSONSemanticHighlightingCalculator;
import org.eclipse.n4js.json.ui.editor.syntaxcoloring.JSONTokenToAttributeIdMapper;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.autoedit.AbstractEditStrategyProvider;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
public class JSONUiModule extends AbstractJSONUiModule {

	public JSONUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	@Override
	public Class<? extends AbstractEditStrategyProvider> bindAbstractEditStrategyProvider() {
		return JSONAutoEditStrategyProvider.class;
	}

	/** Bind a JSON specific highlighting configuration */
	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return JSONHighlightingConfiguration.class;
	}

	/**
	 * Bind a JSON-specific ISemanticHighlightingCalculator for syntax highlighting
	 */
	public Class<? extends ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return JSONSemanticHighlightingCalculator.class;
	}

	/**
	 * Bind a JSON-specific AbstractAntlrTokenToAttributeIdMapper for syntax
	 * highlighting.
	 */
	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper() {
		return JSONTokenToAttributeIdMapper.class;
	}

	/**
	 * Provide hyperlinks from extensions.
	 */
	public Class<? extends HyperlinkHelper> bindHyperlinkHelper() {
		return JSONHyperlinkHelperProvider.class;
	}

}
