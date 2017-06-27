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
package org.eclipse.n4js.jsdoc;

import java.util.List;

import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;

/**
 * Tag definition
 *
 * @see <a href="http://usejsdoc.org/about-plugins.html#tag-definitions">http://usejsdoc.org/about-plugins.html#tag-
 *      definitions</a>
 */
public interface ITagDefinition {

	/**
	 * Returns canonical title, this is the title which is later used to add a concrete tag to the JSDoc DOM.
	 */
	String getTitle();

	/**
	 * Returns all titles of this tag. The first title is the canonical title, the following titles are synonyms.
	 *
	 * @return titles, size is at least one
	 */
	List<String> getAllTitles();

	// /**
	// * Parses the given tag. The title is already parsed, the scanner is
	// * configured accordingly.
	// */
	// Tag parse(TagTitle title, JSDocCharScanner scanner);

	/**
	 * Parses the given tag. The title is already parsed, the scanner is configured accordingly. Additionally expects
	 * specialized parser for free-text descriptions.
	 *
	 * @param descriptionParser
	 *            the parser used to parse description with inline tags, maybe ignored if line tag has no description.
	 */
	Tag parse(TagTitle title, JSDocCharScanner scanner, DescriptionParser descriptionParser);

	/**
	 * Validates the tag and resolve types etc.
	 */
	void validate();

	/**
	 * Returns true if tag can be repeated in current context, that is in current comment or (in case of inline tags)
	 * line tag. Default implementation returns true.
	 */
	default boolean repeatable() {
		return true;
	}

	/**
	 * Returns the completion hint at a given position.
	 *
	 * @param scanner
	 *            scanner with offset at current cursor location. The scanner is used only for this one call, this,
	 *            {@link JSDocCharScanner#setNextOffset(int)} may be called.
	 */
	default JSDocCompletionHint completionHint(JSDocCharScanner scanner) {
		return JSDocCompletionHint.NO_COMPLETION;
	}
}
