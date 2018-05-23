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
package org.eclipse.n4js.ui.proposals.linkedEditing;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.link.LinkedModeUI.IExitPolicy;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;

/**
 * Completion proposal that supports advanced linked editing capabilities.
 */
public class N4JSCompletionProposal extends ConfigurableCompletionProposal {

	/**
	 * The builder for the linked editing mode. If the proposal is configured with such a builder, it is used after the
	 * document was changed.
	 */
	public interface LinkedModeBuilder {

		/**
		 * Configure the linked model of the document.
		 *
		 * @see ConfigurableCompletionProposal#setUpLinkedMode for inspiration.
		 */
		void configureLinkedMode(ConfigurableCompletionProposal proposal, IDocument document);

	}

	private LinkedModeBuilder linkedModeBuilder;

	/**
	 * Creates a new proposal.
	 */
	public N4JSCompletionProposal(String replacementString, int replacementOffset, int replacementLength,
			int cursorPosition) {
		super(replacementString, replacementOffset, replacementLength, cursorPosition);
	}

	/**
	 * Creates a new proposal.
	 */
	public N4JSCompletionProposal(String replacementString, int replacementOffset, int replacementLength,
			int cursorPosition, Image image, StyledString displayString, IContextInformation contextInformation,
			String additionalProposalInfo) {
		super(replacementString, replacementOffset, replacementLength, cursorPosition, image, displayString,
				contextInformation, additionalProposalInfo);
	}

	/**
	 * Configure the linked mode builder for this proposal.
	 */
	public void setLinkedModeBuilder(LinkedModeBuilder linkedModeBuilder) {
		if (linkedModeBuilder != null) {
			setSimpleLinkedMode(null, null);
		}
		this.linkedModeBuilder = linkedModeBuilder;
	}

	/**
	 * Obtain the linked mode builder for this proposal.
	 */
	public LinkedModeBuilder getLinkedModeBuilder() {
		return linkedModeBuilder;
	}

	@Override
	protected void setUpLinkedMode(IDocument document) {
		if (linkedModeBuilder != null) {
			linkedModeBuilder.configureLinkedMode(this, document);
		} else {
			super.setUpLinkedMode(document);
		}
	}

	/**
	 * Return a default exit policy for the linked mode that exists on a given character.
	 */
	public IExitPolicy createExitPolicy(char... exitChars) {
		return new ExitPolicy(exitChars);
	}

}
