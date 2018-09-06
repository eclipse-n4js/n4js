package org.eclipse.n4js.json.ui.contentassist;

import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.DocumentTemplateContext;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.n4js.json.services.JSONGrammarAccess;
import org.eclipse.n4js.json.ui.labeling.JSONImageDescriptorCache;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.templates.XtextTemplateContextTypeRegistry;

import com.google.common.base.Strings;
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

		Image image = JSONImageDescriptorCache.ImageRef.JSON_VALUE.asImage().orNull();
		return createProposal(context, name, value, description, NAME_VALUE_TEMPLATE_STRING, image);
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

		Image image = JSONImageDescriptorCache.ImageRef.JSON_ARRAY.asImage().orNull();
		return createProposal(context, name, array, description, NAME_ARRAY_TEMPLATE_STRING, image);
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

		Image image = JSONImageDescriptorCache.ImageRef.JSON_OBJECT.asImage().orNull();
		return createProposal(context, name, object, description, NAME_OBJECT_TEMPLATE_STRING, image);
	}

	private ICompletionProposal createProposal(ContentAssistContext context, String name, String value,
			String description, String rawTemplate, Image image) {

		TemplateContextType contextType = getTemplateContextType();
		IXtextDocument document = context.getDocument();
		TemplateContext tContext = new DocumentTemplateContext(contextType, document, context.getOffset(), 0);
		Region replaceRegion = context.getReplaceRegion();

		// pre-populate ${name} and ${value} with given args
		tContext.setVariable("name", name);
		tContext.setVariable("value", value);

		return new StyledTemplateProposal(context, name, description, rawTemplate, tContext, replaceRegion, image);
	}

	/** Returns the template content type to use for the NameValuePair template. */
	private TemplateContextType getTemplateContextType() {
		return contextTypeRegistry.getContextType(contextTypeRegistry.getId(grammarAccess.getNameValuePairRule()));
	}

	/**
	 * Creates a new {@link StyledString} with a primary and secondary portion.
	 * 
	 * The two parts are separated by a hyphen ({@code -}) character and the
	 * secondary portion is styled in a slightly lighter color.
	 */
	static StyledString createStyledString(String primary, String secondary) {
		final StyledString styledString = new StyledString(primary);
		if (!Strings.isNullOrEmpty(secondary)) {
			styledString.append(" - " + secondary, StyledString.QUALIFIER_STYLER);
		}
		return styledString;
	}
}
