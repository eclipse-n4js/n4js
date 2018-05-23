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

import java.util.List;

import org.eclipse.n4js.jsdoc.DescriptionParser;
import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocSerializer;
import org.eclipse.n4js.jsdoc.TagDictionary;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.DomFactory;
import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;

/**
 * Line tag that contains only a simple text. If the line contains other content, then this content is serialized back
 * to a normal text. The text can be retrieved via key {@link #VALUE}. If no value is given, then it will be null.
 */
public class LineTagWithSimpleTextDefinition extends AbstractLineTagDefinition {

	/**
	 * Key for value.
	 */
	public final static String VALUE = "VALUE";

	/**
	 * Creates simple line tag with given title.
	 */
	public LineTagWithSimpleTextDefinition(String title) {
		setTitles(title);
	}

	/**
	 * Parses section content and set it as {@link #VALUE}.
	 */
	@Override
	public Tag parse(TagTitle title, JSDocCharScanner scanner, DescriptionParser descriptionParser) {
		ContentNode node = descriptionParser.parse(scanner, TagDictionary.emptyDict());
		if (!(node instanceof Text)) {
			// warning: node may now be null! (e.g. in case of a tag without content)
			String asText = node != null ? JSDocSerializer.toJSDocString(node) : "";
			if (asText.isEmpty()) {
				node = null;
			} else {
				Text text = DomFactory.eINSTANCE.createText();
				text.setText(asText);

				if (node == null) {
					text.setRange(scanner.nextOffset(), scanner.nextOffset());
				} else {
					text.setRange(node.getBegin(), node.getEnd());
				}

				node = text;
			}
		} else {
			Text text = (Text) node;
			if (text.getText().isEmpty()) {
				node = null;
			}
		}
		Tag tag = createLineTag(title);
		if (node != null) {
			addValue(tag, VALUE, node);
		}
		return tag;
	}

	/**
	 * Returns the single value of a tag, iff the tag is not null and the title matches the tag definition. Returns
	 * defaultValue otherwise.
	 */
	public String getValue(LineTag tag, String defaultValue) {
		if (tag == null || !getAllTitles().contains(tag.getTitle().getTitle())) {
			return defaultValue;
		}
		TagValue tv = tag.getValueByKey(VALUE);
		if (tv.getContents().isEmpty()) {
			return defaultValue;
		}
		return ((Text) (tv.getContents().get(0))).getText();
	}

	/**
	 * Returns the single value of the (first) tag, or defaultValue if no such tag is found or if it has no value.
	 */
	public String getValue(Doclet doclet, String defaultValue) {
		List<LineTag> tags = doclet.lineTags(getTitle());
		if (tags.isEmpty()) {
			return defaultValue;
		}
		return getValue(tags.get(0), defaultValue);
	}
}
