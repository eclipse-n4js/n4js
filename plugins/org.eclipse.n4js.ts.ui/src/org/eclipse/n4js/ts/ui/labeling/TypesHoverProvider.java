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
package org.eclipse.n4js.ts.ui.labeling;

import java.net.URL;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.ide.server.hover.CustomHoverLabelUtil;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.validation.TypesKeywordProvider;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;

import com.google.inject.Inject;

/**
 */
public class TypesHoverProvider extends DefaultEObjectHoverProvider {

	@Inject
	private TypesKeywordProvider keywordProvider;

	@Override
	protected String getFirstLine(EObject obj) {
		final String keyword = keywordProvider.keyword(obj);
		final String label = getLabel(obj);
		return composeFirstLine(null, keyword, label);
	}

	/* Note: raised visibility to public to make this reusable from N4JSHoverProvider. */
	@Override
	public String getLabel(EObject obj) {
		String label = CustomHoverLabelUtil.getLabel(obj);
		if (label != null) {
			return label;
		}

		return super.getLabel(obj);
	}

	/* Note: raised visibility to public to make this reusable from N4JSHoverProvider. */
	@Override
	public boolean hasHover(EObject obj) {

		// some objects do not have an FQN, so super call would return false even if there is a hover
		if (obj instanceof TMember || obj instanceof TFunction)
			return true;
		return super.hasHover(obj);
	}

	private static final String CSS_STYLE_KEYWORD = "color:white;"
			+ "background-color:gray;"
			+ "border: 1px solid gray;"
			+ "padding-left:3px;"
			+ "padding-right:3px;"
			+ "border-radius:6px;";

	private static final String HTML_IMAGE_PROPERTIES = "style=\"vertical-align: top;\" width=\"16\" height=\"16\"";

	/**
	 * Combine keyword and label with appropriate HTML formatting.
	 */
	public static final String composeFirstLine(URL imageURL, String keyword, String label) {
		// wrap label in <b> </b> tags (if not containing these tags already!)
		final String htmlKeyword = "<span style=\"" + CSS_STYLE_KEYWORD + "\">" + keyword + "</span>";
		final String htmlLabel = (label == null) ? "" : ((label.contains("<b>")) ? label : "<b>" + label + "</b>");
		final String htmlImage = (imageURL == null) ? null
				: "<image " + HTML_IMAGE_PROPERTIES + " src=\"" + imageURL.toExternalForm() + "\"/>";
		final String line = ((htmlImage == null) ? htmlKeyword : htmlImage) + " " + htmlLabel;

		return line;
	}
}
