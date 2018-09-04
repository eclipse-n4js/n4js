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
public class JSONProposalFactory {
	@Inject
	private XtextTemplateContextTypeRegistry contextTypeRegistry;
	@Inject
	private JSONGrammarAccess grammarAccess;

	private static final String NAME_VALUE_TEMPLATE_STRING = "\"${name}\": \"${value}\"";
	private static final String NAME_ARRAY_TEMPLATE_STRING = "\"${name}\": [${value}]";
	private static final String NAME_OBJECT_TEMPLATE_STRING = "\"${name}\": {${value}}";

	private static final String getTemplateName(String name, String value) {
		return name + ": " + value;
	}

	/**
	 * Creates a name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 */
	public ICompletionProposal createGenericNameValueProposal(ContentAssistContext context) {
		return createNameValueProposal(context, "name", "", "Generic name value pair");
	}

	/**
	 * Creates a generic name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 */
	public ICompletionProposal createGenericNameArrayProposal(ContentAssistContext context) {
		return createNameArrayProposal(context, "name", "", "Generic name array pair");
	}

	/**
	 * Creates a generic name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 */
	public ICompletionProposal createGenericNameObjectProposal(ContentAssistContext context) {
		return createNameObjectProposal(context, "name", "", "Generic name object pair");
	}

	/**
	 * Creates a name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 * @param name
	 *            Specifies the name of the pair.
	 * @param value
	 *            Specifies the value of the pair.
	 * @param description
	 *            Specifies the description of the proposal.
	 */
	public ICompletionProposal createNameValueProposal(ContentAssistContext context, String name, String value,
			String description) {

		Template nameValueTemplate = createNameValueTemplate(context, name, value, description);
		return createProposal(context, name, value, description, nameValueTemplate);
	}

	/**
	 * Creates a name-array-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 * @param name
	 *            Specifies the name of the pair.
	 * @param array
	 *            Specifies the value of the array.
	 * @param description
	 *            Specifies the description of the proposal.
	 */
	public ICompletionProposal createNameArrayProposal(ContentAssistContext context, String name, String array,
			String description) {

		Template nameValueTemplate = createNameArrayTemplate(context, name, array, description);
		return createProposal(context, name, array, description, nameValueTemplate);
	}

	/**
	 * Creates a name-object-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 * @param name
	 *            Specifies the name of the pair.
	 * @param object
	 *            Specifies the value of the object.
	 * @param description
	 *            Specifies the description of the proposal.
	 */
	public ICompletionProposal createNameObjectProposal(ContentAssistContext context, String name, String object,
			String description) {

		Template nameValueTemplate = createNameObjectTemplate(context, name, object, description);
		return createProposal(context, name, object, description, nameValueTemplate);
	}

	private ICompletionProposal createProposal(ContentAssistContext context, String name, String value,
			String description, Template nameValueTemplate) {

		TemplateContextType contextType = getTemplateContextType();
		IXtextDocument document = context.getDocument();
		TemplateContext tContext = new DocumentTemplateContext(contextType, document, context.getOffset(), 0);

		// pre-populate ${name} and ${value} with given args
		tContext.setVariable("name", name);
		tContext.setVariable("value", value);

		return new TemplateProposal(nameValueTemplate, tContext, context.getReplaceRegion(), null);
	}

	private Template createNameValueTemplate(ContentAssistContext context, String name, String value,
			String description) {

		String displayLabel = getTemplateName(name, "\"" + value + "\"");
		return createTemplate(context, displayLabel, description, NAME_VALUE_TEMPLATE_STRING);
	}

	private Template createNameArrayTemplate(ContentAssistContext context, String name, String value,
			String description) {

		String displayLabel = getTemplateName(name, "[" + value + "]");
		return createTemplate(context, displayLabel, description, NAME_ARRAY_TEMPLATE_STRING);
	}

	private Template createNameObjectTemplate(ContentAssistContext context, String name, String value,
			String description) {

		String displayLabel = getTemplateName(name, "{" + value + "}");
		return createTemplate(context, displayLabel, description, NAME_OBJECT_TEMPLATE_STRING);
	}

	private Template createTemplate(ContentAssistContext context, String displayLabel, String description,
			String rawTemplate) {

		boolean trailingComma = hasTrailingComma(context);
		if (trailingComma) {
			rawTemplate = rawTemplate + ",";
		}

		return new Template(displayLabel, description, "", rawTemplate, true);
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

	/** Returns the template content type to use for the NameValuePair template. */
	private TemplateContextType getTemplateContextType() {
		return contextTypeRegistry.getContextType(contextTypeRegistry.getId(grammarAccess.getNameValuePairRule()));
	}
}
