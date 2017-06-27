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
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.tokenizers.InlineTagTokenizer;

/**
 * Default inline tag conisting of a title and a value ionly.
 */
public class DefaultInlineTagDefinition extends AbstractInlineTagDefinition {

	/**
	 * Key for tag value.
	 */
	public final static String VALUE = "VALUE";

	/**
	 * Creates this inline tag with the given title.
	 */
	public DefaultInlineTagDefinition(String title) {
		setTitles(title);
	}

	@Override
	public Tag parse(TagTitle title, JSDocCharScanner scanner) {
		Tag tag = createInlineTag(title);
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

			addValue(tag, VALUE, text);
		}

		return tag;
	}

	/**
	 * Returns the single value of a tag, iff the tag is not null and the title matches the tag definition. Returns
	 * defaultValue otherwise.
	 */
	public String getValue(InlineTag tag, String defaultValue) {
		if (tag == null || !getTitle().equals(tag.getTitle().getTitle())) {
			return defaultValue;
		}
		TagValue tv = tag.getValueByKey(VALUE);
		if (tv.getContents().isEmpty()) {
			return defaultValue;
		}
		return ((Text) (tv.getContents())).getText();
	}

}
