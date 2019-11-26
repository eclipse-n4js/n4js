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

import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.DomFactory;
import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.tags.AbstractInlineTagDefinition;
import org.eclipse.n4js.jsdoc.tags.AbstractLineTagDefinition;
import org.eclipse.n4js.jsdoc.tokenizers.TagTitleTokenizer;

/**
 * DocletParser - used to parse JSDoc comment strings. This provides general parsing algorithm
 * {@link DocletParser#parse(String)}. {@link Doclet} structure is described in ecore model.
 */
public class DocletParser extends AbstractJSDocParser {

	/**
	 * Collection of all line tag definitions, including default definitions and plugged-in custom tags.
	 */
	TagDictionary<AbstractLineTagDefinition> lineTagDictionary;

	/**
	 * Collection of all inline tag definitions, including default definitions and plugged-in custom tags.
	 */
	TagDictionary<AbstractInlineTagDefinition> inlineTagDictionary;

	/**
	 * Sub parser for descriptions.
	 */
	DescriptionParser descriptionParser = null;

	/**
	 * @param dict
	 *            Dictionary of line tags used in JSDoc
	 * @param inlineDict
	 *            Dictionary of inline tags used in main description of JSDoc
	 */
	public DocletParser(TagDictionary<AbstractLineTagDefinition> dict,
			TagDictionary<AbstractInlineTagDefinition> inlineDict) {
		lineTagDictionary = dict;
		inlineTagDictionary = inlineDict;
		descriptionParser = new DescriptionParser();
	}

	/**
	 * Returns the dictionary of line tags, basically used by content assist.
	 */
	public TagDictionary<AbstractLineTagDefinition> getLineTagDictionary() {
		return lineTagDictionary;
	}

	/**
	 * Returns the dictionary of default inline tags.
	 */
	public TagDictionary<AbstractInlineTagDefinition> getInlineTagDictionary() {
		return inlineTagDictionary;
	}

	/**
	 * @param commentString
	 *            Input string that will be parsed. Multiline comment expected.
	 * @return Instance of Doclet (not null, but may have no contents).
	 */
	public Doclet parse(String commentString) {

		JSDocCharScanner scanner = new JSDocCharScanner(commentString);

		// parse main description
		Doclet doclet = parseMainDescription(scanner);

		TagTitleTokenizer tagTitleTokenizer = new TagTitleTokenizer();
		JSDocToken token;

		// TODO error handling
		while (null != (token = tagTitleTokenizer.nextToken(scanner))) {
			ITagDefinition tagDefinition = lineTagDictionary.getDefinition(token.token);
			int lineTagEnd = scanner.findLineTagEnd();
			scanner.fence(lineTagEnd + 1); // fence is excluing the upper bound
			if (tagDefinition != null) {
				TagTitle tagTitle = createTagTitle(token, tagDefinition);
				LineTag tag = (LineTag) tagDefinition.parse(tagTitle, scanner, descriptionParser);
				tag.setRange(token.start, scanner.offset());
				doclet.getLineTags().add(tag);
			}
			scanner.unfence();
			scanner.setNextOffset(lineTagEnd);
		}
		doclet.setEnd(scanner.offset());
		return doclet;
	}

	private Doclet parseMainDescription(JSDocCharScanner scanner) {
		Doclet doclet = DomFactory.eINSTANCE.createDoclet();
		doclet.setBegin(scanner.offset());
		ContentNode description = descriptionParser.parse(scanner, inlineTagDictionary);
		if (description != null) {
			doclet.getContents().add(description);
		}
		return doclet;
	}

}
