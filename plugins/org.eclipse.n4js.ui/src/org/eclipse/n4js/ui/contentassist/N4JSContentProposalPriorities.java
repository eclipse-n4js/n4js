/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.contentassist;

import static org.eclipse.n4js.ui.utils.ConfigurableCompletionProposalExtensions.isSecondaryMember;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.n4js.ui.utils.PrefixMatcherHelper;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentProposalPriorities;

import com.google.inject.Inject;

/**
 * Some adjustments to default Xtext content proposal priorities for N4JS.
 */
public class N4JSContentProposalPriorities extends ContentProposalPriorities {

	/**
	 * Priority multiplier for proposals with a prefix that matches in case.
	 */
	protected float sameCaseMultiplier = 1.1f;

	/**
	 * Priority multiplier for proposals representing members of lesser relevance to the user, e.g. members of built-in
	 * type Object.
	 */
	protected float secondaryMemberMultiplier = 0.95f;

	@Inject
	private PrefixMatcherHelper prefixMatcherHelper;

	/***/
	public N4JSContentProposalPriorities() {
		// disable the default 'sameTextMultiplier' (i.e. do not punish perfect matches)
		this.sameTextMultiplier = this.proposalWithPrefixMultiplier;
	}

	@Override
	protected void adjustPriority(ICompletionProposal proposal, String prefix, int priority) {
		// default adjustments
		super.adjustPriority(proposal, prefix, priority);
		// custom adjustments
		if (proposal instanceof ConfigurableCompletionProposal) {
			ConfigurableCompletionProposal proposalCasted = (ConfigurableCompletionProposal) proposal;
			String replacement = proposalCasted.getReplacementString();

			if (!prefix.isEmpty() && isPrefixWithMatchingCase(prefix, replacement)) {
				adjustPriorityByFactor(proposalCasted, sameCaseMultiplier);
			}

			if (isSecondaryMember(proposalCasted)) {
				adjustPriorityByFactor(proposalCasted, secondaryMemberMultiplier);
			}
		}
	}

	private void adjustPriorityByFactor(ConfigurableCompletionProposal proposal, float factor) {
		if (factor == 1.0f) {
			return;
		}
		int newPrio = (int) (proposal.getPriority() * factor);
		proposal.setPriority(newPrio);
	}

	private boolean isPrefixWithMatchingCase(String prefix, String str) {
		return str.startsWith(prefix)
				|| prefixMatcherHelper.camelCaseMatch(str, prefix); // camel case matches are inherently case sensitive
	}
}
