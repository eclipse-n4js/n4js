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
package org.eclipse.n4js.tests.contentAssist

import com.google.inject.Inject
import org.eclipse.jface.text.contentassist.ICompletionProposal
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal
import org.eclipse.xtext.ui.editor.contentassist.IContentProposalPriorities

/**
 * Additional assertions for checking the order of proposals during content assist.
 */
class AbstractN4JSContentAssistWithOrderingPluginUITest extends AbstractN4JSContentAssistPluginUITest {

	@Inject
	private IContentProposalPriorities contentProposalPriorities;

	def protected void assertProposalOrder(CharSequence snippet, String[] expectedProposals) {
		val actualProposals = computeActualProposals(snippet).convertProposalsToStrings;
		assertEquals("incorrect proposals", expectedProposals.sort, actualProposals.sort);
		assertArrayEquals("incorrect order of proposals", expectedProposals, actualProposals);
	}

	/**
	 * In addition to ordering, this assertion checks the proposals' priorities. This method does not check
	 * the exact priority values, as returned by {@link ConfigurableCompletionProposal#getPriority()}; instead,
	 * priority groups are used, as explained in {@link #convertProposalsToPrioGroupsAndStrings(ICompletionProposal[])}.
	 */
	def protected void assertProposalOrder(CharSequence snippet, Pair<Integer, String>[] expectedProposals) {
		// ordinary checks
		assertProposalOrder(snippet, expectedProposals.map[value]);
		// check for correct priority groups
		val actualProposals = computeActualProposals(snippet).convertProposalsToPrioGroupsAndStrings;
		assertArrayEquals("incorrect proposal groups", expectedProposals, actualProposals);
	}

	def protected ICompletionProposal[] computeActualProposals(CharSequence code) {
		return newBuilder().append(code.toString).computeCompletionProposals();
	}

	def protected String[] convertProposalsToStrings(ICompletionProposal[] proposals) {
		val ConfigurableCompletionProposal[] proposalsCasted = castProposals(proposals);
		return proposalsCasted.map[replacementString];
	}

	/**
	 * Converts the given proposals to pairs of priority group index and string.  Priority groups contain
	 * proposal of same priority. The priority group with index 0 contains all proposals with the highest
	 * priority value, priority group with index 1 contains those with the next lower priority, and so on.
	 */
	def protected Pair<Integer, String>[] convertProposalsToPrioGroupsAndStrings(ICompletionProposal[] proposals) {
		val ConfigurableCompletionProposal[] proposalsCasted = castProposals(proposals);
		var cnt = 0;
		val prioToPrioGroup = newLinkedHashMap;
		for (prio : proposalsCasted.map[priority].toSet.sort.reverseView) {
			prioToPrioGroup.put(prio, cnt++);
		}
		return proposalsCasted.map[prioToPrioGroup.get(it.priority) -> it.replacementString];
	}

	def private ConfigurableCompletionProposal[] castProposals(ICompletionProposal[] proposals) {
		val ConfigurableCompletionProposal[] proposalsCasted = proposals.filter(ConfigurableCompletionProposal);
		if (proposalsCasted.length < proposals.length) {
			val badClassNames = proposals.filter[!(it instanceof ConfigurableCompletionProposal)].map[class.name].toSet;
			throw new IllegalStateException(
				"all proposals are expected to be of type " + ConfigurableCompletionProposal.simpleName + " but got: " +
					badClassNames.join(", "));
		}
		return proposalsCasted;
	}
}
