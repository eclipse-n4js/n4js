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
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.Text;

/**
 * Base class for implementations {@link InlineTag} instances.
 */
public abstract class AbstractInlineTagDefinition extends AbstractBaseTagDefinition {

	/**
	 * Convenience method.
	 */
	protected InlineTag createInlineTag(TagTitle tagTitle) {
		InlineTag tag = DOM.createInlineTag();
		tag.setTagDefinition(this);
		tag.setTitle(tagTitle);
		return tag;
	}

	/**
	 * Convenience method.
	 */
	protected Text convertToText(JSDocToken descr) {
		Text text = DOM.createText();
		text.setText(descr.token);
		text.setBegin(descr.start);
		text.setEnd(descr.end);
		return text;
	}

	/**
	 * Assuming that inline tags do not have nested inline tags (e.g. nested description with inline tags) we can
	 * suppress description parser.
	 */
	@Override
	public final Tag parse(TagTitle title, JSDocCharScanner scanner, DescriptionParser descriptionParser) {
		// supress description parser
		return this.parse(title, scanner);
	}

	/**
	 * parsing method that must be implemented by sub types.
	 */
	public abstract Tag parse(TagTitle title, JSDocCharScanner scanner);

}
