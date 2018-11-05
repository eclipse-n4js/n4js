package org.eclipse.n4js.json.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

public interface IJSONProposalProvider {

	/**
	 * @return true iff this provider is responsible for the given {@link EObject}
	 */
	public boolean isResponsible(EObject model);

	public void complete_JSONArray(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor);

	/**
	 * Propose an empty string literal {@code ""} to complete rule {@code STRING}.
	 * 
	 * The cursor is set inside the two double quotes.
	 */
	public void complete_STRING(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor);

	/**
	 * Complete keywords only if they are longer than 4 characters.
	 * 
	 * This removes the single characters {@code {}, {@code [} and {@code ,} from
	 * the list of proposals.
	 */
	public void completeKeyword(Keyword keyword, ContentAssistContext context, ICompletionProposalAcceptor acceptor);

	/**
	 * Complete a the rule JSONObject using an empty object literal {@code {}}.
	 */
	public void complete_JSONObject(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor);

	/**
	 * Complete a template-based name-value pair inside of the rule
	 * {@code JSONObject}.
	 */
	public void complete_NameValuePair(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor);

}
