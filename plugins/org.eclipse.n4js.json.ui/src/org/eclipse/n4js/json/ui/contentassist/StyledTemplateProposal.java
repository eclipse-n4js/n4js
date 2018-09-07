package org.eclipse.n4js.json.ui.contentassist;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;

public class StyledTemplateProposal extends TemplateProposal implements ICompletionProposalExtension6 {
	private final String displayLabel;
	private final String description;
	private final boolean isGenericProposal;

	public StyledTemplateProposal(ContentAssistContext context, String name, String description,
			String rawTemplate, boolean isGenericProposal, TemplateContext tContext, IRegion region, Image image) {
		super(createTemplate(context, name, description, rawTemplate), tContext, region, image);

		this.displayLabel = name;
		this.isGenericProposal = isGenericProposal;
		this.description = description;
	}

	public StyledTemplateProposal(ContentAssistContext context, String name, String description, String rawTemplate,
			TemplateContext tContext, IRegion region, Image image) {
		this(context, name, description, rawTemplate, false, tContext, region, image);
	}

	@Override
	public StyledString getStyledDisplayString() {
		StyledString.Styler styler = null;
		if (isGenericProposal) {
			styler = StyledString.DECORATIONS_STYLER;
		}
		return JSONProposalFactory.createStyledString(displayLabel, description, styler);
	}

	static private Template createTemplate(ContentAssistContext context, String displayLabel, String description,
			String rawTemplate) {

		boolean trailingComma = hasTrailingComma(context);
		if (trailingComma) {
			rawTemplate = rawTemplate + ",";
		}

		return new Template(displayLabel, description, "", rawTemplate, true);
	}

	static private boolean hasTrailingComma(ContentAssistContext context) {
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
}
