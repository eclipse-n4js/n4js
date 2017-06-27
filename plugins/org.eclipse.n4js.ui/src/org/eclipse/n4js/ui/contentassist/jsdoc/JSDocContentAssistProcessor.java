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
package org.eclipse.n4js.ui.contentassist.jsdoc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.ui.editor.XtextSourceViewer;
import org.eclipse.xtext.ui.editor.contentassist.antlr.DelegatingContentAssistContextFactory;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import com.google.inject.Inject;

import org.eclipse.n4js.jsdoc.N4JSDocletParser;

/**
 *
 * <p>
 * {@code XtendJavaDocContentAssistProcessor} was used as a blueprint.
 */
public class JSDocContentAssistProcessor implements IContentAssistProcessor {

	@Inject
	DelegatingContentAssistContextFactory.StatefulFactory contentAssistContextFactory;

	@Inject
	N4JSDocletParser docletParser;

	@Inject
	private IScopeProvider scopeProvider;

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		if (viewer instanceof XtextSourceViewer) {
			IXtextDocument document = (IXtextDocument) viewer.getDocument();
			return document.priorityReadOnly(createCompletionProposalComputer(viewer, offset));
		}

		return new ICompletionProposal[0];
	}

	private JSDocCompletionProposalComputer createCompletionProposalComputer(ITextViewer viewer,
			int offset) {
		return new JSDocCompletionProposalComputer(this, viewer, offset);
	}

	/**
	 * Returns the context factory setting an execution pool. Actually this is a dirty hack...
	 */
	public DelegatingContentAssistContextFactory.StatefulFactory getContextFactory() {

		// TODO IDE-2446: Suggested improvement of the following situation
		// kept the original comment for easy spotting

		// TODO hack. I have no clue why this is needed and how pool should be set.
		ExecutorService pool = Executors.newFixedThreadPool(3);
		contentAssistContextFactory.setPool(pool);

		return contentAssistContextFactory;
	}

	/**
	 * Returns the doclet parser, needs to be made more dynamic in the future?
	 */
	public N4JSDocletParser getDocletParser() {
		return docletParser;
	}

	/**
	 * Returns the scope provider which is injected here but is used by the computer which does not support DI.
	 */
	public IScopeProvider getScopeProvider() {
		return scopeProvider;
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

}
