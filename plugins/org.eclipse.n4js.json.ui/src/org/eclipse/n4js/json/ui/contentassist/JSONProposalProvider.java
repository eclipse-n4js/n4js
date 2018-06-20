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
package org.eclipse.n4js.json.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.inject.Inject;

/**
 * Custom content proposal support for JSON documents.
 */
public class JSONProposalProvider extends AbstractJSONProposalProvider {
	
	@Inject
	private NameValuePairProposalFactory nameValuePairProposalFactory;
	
	/**
	 * Propose an empty array literal to complete rule {@code JSONArray}.
	 * 
	 * The cursor is set between the two squared brackets.
	 */
	@Override
	public void complete_JSONArray(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// only propose if context does not specify a prefix
			if (!context.getPrefix().isEmpty()) {
				return;
			}
		acceptor.accept(new ConfigurableCompletionProposal("[]", context.getOffset(), 0, 1, null, createStyledString("[...]", "Array"), null, ""));
	}
	
	/**
	 * Propose an empty string literal {@code ""} to complete rule {@code STRING}.
	 * 
	 * The cursor is set inside the two double quotes.
	 */
	@Override
	public void complete_STRING(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// do not propose a string literal, if a name-value-pair is expected
		if (ruleCall.eContainer() instanceof Assignment && ((Assignment) ruleCall.eContainer()).getFeature().equals("name")) {
			return;
		}
		
		// only propose if context does not specify a prefix
		if (!context.getPrefix().isEmpty()) {
			return;
		}
		acceptor.accept(new ConfigurableCompletionProposal("\"\"", context.getOffset(), 0, 1, null, createStyledString("\"...\"", "String"), null, ""));
	}
	
	/**
	 * Complete keywords only if they are longer than 4 characters.
	 * 
	 * This removes the single characters {@code {}, {@code [} and {@code ,} from the list of proposals.
	 */
	@Override
	public void completeKeyword(Keyword keyword, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		if (keyword.getValue().length() < 4)
			return; // filter out short keywords
		// otherwise complete keyword using the default behavior
		super.completeKeyword(keyword, context, acceptor);
	}
	
	/**
	 * Complete a the rule JSONObject using an empty object literal {@code {}}.
	 */
	@Override
	public void complete_JSONObject(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// only propose if context does not specify a prefix
		if (!context.getPrefix().isEmpty()) {
			return;
		}
		acceptor.accept(new ConfigurableCompletionProposal("{}", context.getOffset(), 0, 1, null, createStyledString("{...}", "Object"), null, ""));
	}
	
	/**
	 * Complete a template-based name-value pair inside of the rule {@code JSONObject}.
	 */
	@Override
	public void complete_NameValuePair(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// do not add proposal for name-value-pair if a prefix is detected
		if (!context.getPrefix().isEmpty()) {
			return;
		}
		
		boolean trailingComma = false;
		
		// add trailing comma, if the name-value pair is inserted in the middle of a list of existing pairs.
		final INode currentNode = context.getCurrentNode();
		if (currentNode.hasNextSibling()) {
			final INode nextSibling = currentNode.getNextSibling();
			if (nextSibling.getSemanticElement() instanceof NameValuePair) {
				trailingComma = true;
			}
		}
		
		acceptor.accept(nameValuePairProposalFactory.createNameValuePairProposal(context, trailingComma));
	}
	
	/**
	 * Creates a new {@link StyledString} with a primary and secondary portion.
	 * 
	 * The two parts are separated by a hyphen ({@code -}) character and the secondary
	 * portion is styled in a slightly lighter color.
	 */
	private StyledString createStyledString(String primary, String secondary) {
		final StyledString styledString = new StyledString(primary);
		styledString.append(" - " + secondary, StyledString.QUALIFIER_STYLER);
		return styledString;
	}
}
