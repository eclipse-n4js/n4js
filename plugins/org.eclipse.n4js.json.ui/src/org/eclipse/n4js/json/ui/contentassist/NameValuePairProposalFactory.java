package org.eclipse.n4js.json.ui.contentassist;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
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

	private static final String NAME_VALUE_TEMPLATE_STRING = "\"${name}\": ${value}";

	private static final String getTemplateName(String name, String value) {
		return "\"" + name + "\": " + value;
	}

	/**
	 * Creates a name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 * @param defaultName
	 *            Specifies the name of the pair.
	 * @param defaultValue
	 *            Specifies the value of the pair.
	 */
	public ICompletionProposal createNameValuePairProposal(ContentAssistContext context, String name, String value,
			String description) {

		boolean trailingComma = hasTrailingComma(context);
		Template nameValueTemplate = createNameValuePairTemplate(context, name, value, description, trailingComma);
		TemplateContextType contextType = getTemplateContextType();
		IXtextDocument document = context.getDocument();
		TemplateContext tContext = new DocumentTemplateContext(contextType, document, context.getOffset(), 0);

		// pre-populate ${name} and ${value} with given args
		tContext.setVariable("name", name);
		tContext.setVariable("value", value);

		return new TemplateProposal(nameValueTemplate, tContext, context.getReplaceRegion(), null);
	}

	/**
	 * Creates a name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 */
	public ICompletionProposal createNameValuePairProposal(ContentAssistContext context) {
		return createNameValuePairProposal(context, "name", "\"value\"", "Generic name value pair");
	}

	private boolean hasTrailingComma(ContentAssistContext context) {
		boolean trailingComma = false;

		// add trailing comma, if the name-value pair is inserted in the middle of a
		// list of existing pairs.
		final INode currentNode = context.getCurrentNode();
		if (currentNode.hasNextSibling()) {
			final INode nextSibling = currentNode.getNextSibling();
			if (nextSibling.getSemanticElement() instanceof NameValuePair) {
				trailingComma = true;
			}
		}
		return trailingComma;
	}

	/**
	 * Creates a new name-value-pair template for the given parameters.
	 *
	 * @param context
	 *            The content assist context to create the template for.
	 * @param trailingComma
	 *            Adds a trailing comma to the name-value-pair.
	 */
	private Template createNameValuePairTemplate(ContentAssistContext context, String name, String value,
			String description, boolean trailingComma) {

		String rawTemplate = NAME_VALUE_TEMPLATE_STRING;

		if (trailingComma) {
			rawTemplate = rawTemplate + ",";
		}

		return new Template(getTemplateName(name, value), description, "", rawTemplate, true);
	}

	/** Returns the template content type to use for the NameValuePair template. */
	private TemplateContextType getTemplateContextType() {
		return contextTypeRegistry.getContextType(contextTypeRegistry.getId(grammarAccess.getNameValuePairRule()));
	}
}
