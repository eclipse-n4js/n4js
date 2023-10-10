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

import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocToken;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.tokenizers.InlineTagTokenizer;

/**
 * Stub to verify general functionality without concrete tag in the jsdoc parser.
 */
public class StubInlineTagDefinition extends AbstractInlineTagDefinition {
	
	public static final String PARAM_VALUE = "PARAM_VALUE";

	/**
	 *
	 */
	public StubInlineTagDefinition(String title) {
		setTitles(title);
	}

	/**
	 * Converts the given token to a DOM text element.
	 */
	@Override
	protected Text convertToText(JSDocToken descr) {
		Text text = DOM.createText();
		text.setText(descr.token);
		text.setBegin(descr.start);
		text.setEnd(descr.end);
		return text;
	}

	@Override
	public Tag parse(TagTitle title, JSDocCharScanner scanner) {
		Tag paramTag = createInlineTag(title);

		scanner.skipWS();
		JSDocToken description = InlineTagTokenizer.INSTANCE.nextToken(scanner);
		if (description != null) {
			String raw = convertToText(description).getText();
			int titleOff = raw.indexOf(title.getTitle());
			int titleLen = title.getTitle().length();
			int fix = titleOff + titleLen;
			raw = raw.substring(fix);

			Text text = DOM.createText();
			text.setText(raw);
			text.setBegin(description.start + fix);
			text.setEnd(description.end);

			addValue(paramTag, PARAM_VALUE, text);
		}

		return paramTag;
	}

}
