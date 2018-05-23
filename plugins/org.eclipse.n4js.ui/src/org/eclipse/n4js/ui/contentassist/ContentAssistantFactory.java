/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.contentassist;

import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.xtext.ui.editor.contentassist.DefaultContentAssistantFactory;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

import com.google.inject.Inject;

import org.eclipse.n4js.ui.contentassist.jsdoc.JSDocContentAssistProcessor;
import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeToPartitionMapper;

/**
 */
public class ContentAssistantFactory extends DefaultContentAssistantFactory {

	@Inject
	private JSDocContentAssistProcessor jsDocContentAssistProcessor;

	@Override
	protected void setContentAssistProcessor(ContentAssistant assistant, SourceViewerConfiguration configuration,
			ISourceViewer sourceViewer) {
		super.setContentAssistProcessor(assistant, configuration, sourceViewer);
		assistant.setContentAssistProcessor(jsDocContentAssistProcessor, TokenTypeToPartitionMapper.JS_DOC_PARTITION);
		assistant.setContentAssistProcessor(null, TokenTypeToPartitionMapper.REG_EX_PARTITION);
		assistant.setContentAssistProcessor(null, TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION);
		assistant.setContentAssistProcessor(null, TerminalsTokenTypeToPartitionMapper.SL_COMMENT_PARTITION);
		assistant.setContentAssistProcessor(null, TerminalsTokenTypeToPartitionMapper.COMMENT_PARTITION);
	}
}
