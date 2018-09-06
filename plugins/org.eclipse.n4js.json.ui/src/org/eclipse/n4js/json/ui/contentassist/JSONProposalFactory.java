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

	private static final String GEN_NAME_VALUE_TEMPLATE_STRING = "\"${name}\": \"${value}\"";
	private static final String GEN_NAME_ARRAY_TEMPLATE_STRING = "\"${name}\": [${value}]";
	private static final String GEN_NAME_OBJECT_TEMPLATE_STRING = "\"${name}\": {${value}}";

	private static final String getNAME_VALUE_TEMPLATE_STRING(String name) {
		return "\"" + name + "\": \"${value}\"";
	}

	private static final String getNAME_ARRAY_TEMPLATE_STRING(String name) {
		return "\"" + name + "\": [${value}]";
	}

	private static final String getNAME_OBJECT_TEMPLATE_STRING(String name) {
		return "\"" + name + "\": {${value}}";
	}

	/**
	 * Creates a name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 */
	public ICompletionProposal createGenericNameValueProposal(ContentAssistContext context) {
		return createNameValueProposal(context, "<value>", "", "Generic name value pair", true);
	}

	/**
	 * Creates a generic name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 */
	public ICompletionProposal createGenericNameArrayProposal(ContentAssistContext context) {
		return createNameArrayProposal(context, "<array>", "", "Generic name array pair", true);
	}

	/**
	 * Creates a generic name-value-pair proposal for the given context.
	 * 
	 * @param context
	 *            The {@link ContentAssistContext} to create the proposal for.
	 */
	public ICompletionProposal createGenericNameObjectProposal(ContentAssistContext context) {
		return createNameObjectProposal(context, "<object>", "", "Generic name object pair", true);
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
		return createNameValueProposal(context, name, value, description, false);
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

		return createNameArrayProposal(context, name, array, description, false);
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

		return createNameObjectProposal(context, name, object, description, false);
	}

	private ICompletionProposal createNameValueProposal(ContentAssistContext context, String name, String value,
			String description, boolean isGenericProposal) {

		Image image = JSONImageDescriptorCache.ImageRef.JSON_VALUE.asImage().orNull();
		String rawTemplate = isGenericProposal ? GEN_NAME_VALUE_TEMPLATE_STRING : getNAME_VALUE_TEMPLATE_STRING(name);
		return createProposal(context, name, value, description, rawTemplate, image, isGenericProposal);
	}

	private ICompletionProposal createNameArrayProposal(ContentAssistContext context, String name, String array,
			String description, boolean isGenericProposal) {

		Image image = JSONImageDescriptorCache.ImageRef.JSON_ARRAY.asImage().orNull();
		String rawTemplate = isGenericProposal ? GEN_NAME_ARRAY_TEMPLATE_STRING : getNAME_ARRAY_TEMPLATE_STRING(name);
		return createProposal(context, name, array, description, rawTemplate, image, isGenericProposal);
	}

	private ICompletionProposal createNameObjectProposal(ContentAssistContext context, String name, String object,
			String description, boolean isGenericProposal) {

		Image image = JSONImageDescriptorCache.ImageRef.JSON_OBJECT.asImage().orNull();
		String rawTemplate = isGenericProposal ? GEN_NAME_OBJECT_TEMPLATE_STRING : getNAME_OBJECT_TEMPLATE_STRING(name);
		return createProposal(context, name, object, description, rawTemplate, image, isGenericProposal);
	}

	private ICompletionProposal createProposal(ContentAssistContext context, String name, String value,
			String description, String rawTemplate, Image image, boolean isGenericProposal) {

		TemplateContextType contextType = getTemplateContextType();
		IXtextDocument document = context.getDocument();
		TemplateContext tContext = new DocumentTemplateContext(contextType, document, context.getOffset(), 0);
		Region replaceRegion = context.getReplaceRegion();

		// pre-populate ${name} and ${value} with given args
		if (isGenericProposal) {
			tContext.setVariable("name", name);
		}
		tContext.setVariable("value", value);

		return new StyledTemplateProposal(context, name, description, rawTemplate, isGenericProposal, tContext,
				replaceRegion, image);
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
		return createStyledString(primary, secondary, null);
	}

	/**
	 * Creates a new {@link StyledString} with a primary and secondary portion.
	 * 
	 * The two parts are separated by a hyphen ({@code -}) character and the
	 * secondary portion is styled in a slightly lighter color.
	 */
	static StyledString createStyledString(String primary, String secondary, StyledString.Styler styler) {
		final StyledString styledString = new StyledString();
		if (styler != null) {
			styledString.append(primary, styler);
		} else {
			styledString.append(primary);
		}
		if (!Strings.isNullOrEmpty(secondary)) {
			styledString.append(" - " + secondary, StyledString.QUALIFIER_STYLER);
		}
		return styledString;
	}
}
