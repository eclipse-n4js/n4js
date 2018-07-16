package org.eclipse.n4js.json.ui.contentassist;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateContextTypeRegistry;

import com.google.inject.Inject;

/**
 * A factory for creating {@link ICompletionProposal}s that insert new
 * name-value-pairs into a JSON Document.
 * 
 * Example: <code>
 * {
 * 	"a": 1,
 *  <cursor>
 *  "c": 3
 * }
 * 
 * is completed to 
 * 
 * {
 * 	"a": 1,
 * 	"name": "value", // NEW
 * 	"c": 3
 * }
 * 
 * where {@code "name"} and {@code "value"} remain editable as 
 * they do for code templates or refactorings.
 * </code>
 */
public class NameValuePairProposalFactory {
	@Inject
	private XtextTemplateContextTypeRegistry contextTypeRegistry;
	@Inject
	private JSONGrammarAccess grammarAccess;

	private static final String NAME_VALUE_PAIR_PROPOSAL_NAME = "\"<name>\": <value>";
	private static final String NAME_VALUE_TEMPLATE_STRING = "\"${name}\": ${value}";

	/**
	 * Creates a name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 * @param trailingComma
	 *            Specifies whether to include a trailing comma into the proposal
	 *            text.
	 */
	public ICompletionProposal createNameValuePairProposal(ContentAssistContext context, boolean trailingComma) {
		final Template nameValueTemplate = this.createNameValuePairTemplate(context, trailingComma);

		final TemplateContext tContext = new DocumentTemplateContext(getTemplateContextType(), context.getDocument(),
				context.getOffset(), 0);
		// pre-populate ${value} with "value" to avoid an invalid state in terms of
		// syntax
		tContext.setVariable("value", "\"value\"");

		return new TemplateProposal(nameValueTemplate, tContext, context.getReplaceRegion(), null);
	}

	/**
	 * Creates a new name-value-pair template for the given parameters.
	 *
	 * @param context
	 *            The content assist context to create the template for.
	 * @param trailingComma
	 *            Adds a trailing comma to the name-value-pair.
	 */
	private Template createNameValuePairTemplate(ContentAssistContext context, boolean trailingComma) {
		String rawTemplate = NAME_VALUE_TEMPLATE_STRING;

		if (trailingComma) {
			rawTemplate = rawTemplate + ",";
		}

		return new Template(NAME_VALUE_PAIR_PROPOSAL_NAME, "", "", rawTemplate, true);
	}

	/** Returns the template content type to use for the NameValuePair template. */
	private TemplateContextType getTemplateContextType() {
		return contextTypeRegistry.getContextType(contextTypeRegistry.getId(grammarAccess.getNameValuePairRule()));
	}
}
