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
package org.eclipse.n4js.jsdoc.tags;

import org.eclipse.n4js.jsdoc.DescriptionParser;
import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocToken;
import org.eclipse.n4js.jsdoc.TagDictionary;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.tokenizers.InlineTagTokenizer;

/**
 * Stub to verify general functionality without concrete tag in the jsdoc parser.
 */
public class StubLineTagWithRegionDefinition extends AbstractLineTagDefinition {
	
	public static final String REGION = "REGION";
	
	public static final String DESCR = "DESCR";

	/**
	 *
	 */
	public StubLineTagWithRegionDefinition(String title) {
		setTitles(title);
	}

	
	protected Text convertToText(JSDocToken descr) {
		Text text = DOM.createText();
		text.setText(descr.token);
		text.setBegin(descr.start);
		text.setEnd(descr.end);
		return text;
	}

	@Override
	public Tag parse(TagTitle title, JSDocCharScanner scanner, DescriptionParser descriptionParser) {
		Tag paramTag = createLineTag(title);

		scanner.skipWS();
		JSDocToken region = InlineTagTokenizer.INSTANCE.nextToken(scanner);
		if (region != null) {
			addValue(paramTag, REGION, region);
		} // silent ignore no region

		scanner.skipWS();
		// expect no tags in descritpion
		TagDictionary<AbstractInlineTagDefinition> dictionary = new TagDictionary<>();
		ContentNode description = descriptionParser.parse(scanner, dictionary);
		if (description != null) {
			addValue(paramTag, DESCR, description);
		} // silent ignore no description

		return paramTag;
	}

}
