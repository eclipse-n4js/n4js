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
import org.eclipse.n4js.jsdoc.TagDictionary;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;

/**
 * Simple line tag that consists of a title and a description.
 */
public class DefaultLineTagDefinition extends AbstractLineTagDefinition {

	/**
	 * Key for content.
	 */
	public final static String CONTENTS = "CONTENTS";

	/**
	 * Inline tags used for the description, usually the standard tags.
	 */
	protected final TagDictionary<AbstractInlineTagDefinition> inlineTags;

	/**
	 * Creates simple line tag, which consists only of the tag title and a description by default. The description
	 * supports the given inline tags.
	 */
	public DefaultLineTagDefinition(String title, TagDictionary<AbstractInlineTagDefinition> inlineTags) {
		this.inlineTags = inlineTags;
		setTitles(title);
	}

	/**
	 * Parses section content and set it as {@link #CONTENTS}.
	 */
	@Override
	public Tag parse(TagTitle title, JSDocCharScanner scanner, DescriptionParser descriptionParser) {
		ContentNode node = descriptionParser.parse(scanner, inlineTags);
		Tag tag = createLineTag(title);
		if (node != null) {
			addValue(tag, CONTENTS, node);
		}
		return tag;

	}
}
