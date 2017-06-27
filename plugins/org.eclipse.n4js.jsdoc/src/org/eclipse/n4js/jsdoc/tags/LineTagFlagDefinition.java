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
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;

/**
 * Simple line tag which serves as a flag. It has no description or other content.
 */
public class LineTagFlagDefinition extends AbstractLineTagDefinition {

	/**
	 * Creates a line tag without description.
	 */
	public LineTagFlagDefinition(String title) {
		setTitles(title);
	}

	/**
	 */
	@Override
	public Tag parse(TagTitle title, JSDocCharScanner scanner, DescriptionParser descriptionParser) {
		Tag tag = createLineTag(title);
		descriptionParser.parse(scanner, TagDictionary.emptyDict());
		return tag;
	}
}
