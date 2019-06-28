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
package org.eclipse.n4js.ui.quickfix;

import com.google.inject.Inject
import org.eclipse.n4js.ui.changes.ChangeManager
import org.eclipse.n4js.ts.ui.quickfix.TypesQuickfixProvider
import org.apache.log4j.Logger
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.xtext.ui.editor.model.edit.IModification
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification
import org.eclipse.xtext.ui.editor.quickfix.CompletionProposalBasedModification
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor
import org.eclipse.xtext.validation.Issue

/**
 */
public abstract class AbstractN4JSQuickfixProvider extends TypesQuickfixProvider {

	@Inject
	private ChangeManager changeManager

	/**
	 * Logger for this class
	 */
	protected static val logger = Logger.getLogger(N4JSQuickfixProvider)


	override createLinkingIssueResolutions(Issue issue, IssueResolutionAcceptor issueResolutionAcceptor) {
		try {
			super.createLinkingIssueResolutions(issue, issueResolutionAcceptor)
		} catch(Exception e) {
			logger.error(e)
		}
	}


	/**
	 * Create an issue resolution based on an {@link N4Modification}. This should be used for most N4JS quick fixes.
	 * <p>
	 * Note how this method mimics the API in {@link IssueResolutionAcceptor}.
	 */
	def protected void accept(IssueResolutionAcceptor acceptor, Issue issue, String label, String description, String image, N4Modification modification) {
		acceptor.accept(issue,label,description,image,new N4ModificationWrapper(modification,issue,changeManager));
	}


	/**
	 * This method allows to create quick fixes via the ordinary Xtext quick fix API that are based on an {@link ICompletionProposal}
	 * instead of an {@link IModification}, {@link ISemanticModification} or {@link N4Modification}. It is only intended for work-around
	 * cases, try to use {@link #accept(IssueResolutionAcceptor,Issue,String,String,String,N4Modification} instead.
	 * <p>
	 * Note how this method mimics the API in {@link IssueResolutionAcceptor}.
	 */
	def protected void accept(IssueResolutionAcceptor acceptor, Issue issue, String label, String description, String image, ICompletionProposal proposal) {
		acceptor.accept(issue,label,description,image,new CompletionProposalBasedModification(proposal));
	}
}
