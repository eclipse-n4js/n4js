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

import org.eclipse.n4js.jsdoc.JSDocCharScanner.ScannerState;
import org.eclipse.n4js.jsdoc.dom.ComposedContent;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.DomFactory;
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.tags.AbstractInlineTagDefinition;
import org.eclipse.n4js.jsdoc.tokenizers.InlineTagTokenizer;
import org.eclipse.n4js.jsdoc.tokenizers.TagTitleTokenizer;

/**
 * Parserfor free text that makes description. Can be used with {@link TagDictionary} of
 * {@link AbstractInlineTagDefinition}s that will be included in parsed description text. Without this initialization
 * any {@link InlineTag}s encountered will be parsed as plain text.
 */
public class DescriptionParser extends AbstractJSDocParser {

	/**
	 * Method used to parse Region encountered in text. Expects {@link JSDocCharScanner#nextNonWS()} to be beginning of
	 * region. It will use provided {@link TagDictionary} to recognize parse region. If no tags will mach parsed region,
	 * null is returned. If region is maching one of {@link InlineTag} definitions it will return result of parsing
	 * region with given tag.
	 *
	 * @param scanner
	 *            JSDocCharScanner with offset_ before region
	 * @param inlineTagsDictinary
	 *            Dictionary of tags used to parse given region
	 * @return Returns instance of Tag if parsed successfully, null otherwise.
	 */
	Tag parseRegion(JSDocCharScanner scanner, TagDictionary<AbstractInlineTagDefinition> inlineTagsDictinary) {
		ScannerState stateBeforeRegion = scanner.saveState();
		char _char = scanner.nextNonWS();
		// unsafe if region is marked by more than one char
		if (!regionStart(_char)) {
			return null;
		}
		scanner.skipWS();
		JSDocToken tokenTitle = TagTitleTokenizer.INSTANCE.nextToken(scanner);
		if (tokenTitle != null) {
			ITagDefinition iTagDefinition = inlineTagsDictinary.getDefinition(tokenTitle.token);
			if (iTagDefinition != null) {
				TagTitle tagTitle = createTagTitle(tokenTitle, iTagDefinition);
				AbstractInlineTagDefinition tagDefinition = (AbstractInlineTagDefinition) iTagDefinition;
				// although start of the region will be parsed twice (e.g. tag
				// title) it allows given tag implementation to decide what to
				// do with whole region, also might be important for nested
				// regions detection.
				scanner.restoreState(stateBeforeRegion);
				InlineTag tag = (InlineTag) tagDefinition.parse(tagTitle, scanner);
				tag.setRange(tokenTitle.start, scanner.offset());
				return tag;

			} else {
				System.err.println("silent ignore of unrecognized InlineTag {" + tokenTitle.token + "}");
				return null;
			}
		}
		return null;
	}

	private boolean regionStart(char _char) {
		// TODO unsafe region recognition
		// region can have multichar start/end markers
		return String.valueOf(_char).equals(InlineTagTokenizer.INSTANCE.startMarker);
	}

	/**
	 * Returns Text if descriptions contains only text. If it contains any inline Tags, than returned composedContent
	 * will contain multiple nodes, one for each inline tag and one for each text before/after/in between inline tags.
	 * Order of data is preserved. Stops scanning for text when reaches end of comment or LineTag.
	 *
	 * InlineTags are identified based on dictionary provided with method call.
	 *
	 */
	public ContentNode parse(JSDocCharScanner scanner, TagDictionary<AbstractInlineTagDefinition> inlineTagsDictinary) {
		ComposedContent description = DomFactory.eINSTANCE.createComposedContent();

		if (!scanner.hasNext()) {
			return null;
		}

		if (nextIsTagTitle(scanner)) {
			return null;
		}

		int start = scanner.nextOffset();
		int end = start;

		StringBuilder strb = new StringBuilder();
		while (scanner.hasNext()) {
			char c = scanner.peek();
			if (regionStart(c)) {
				ScannerState st = scanner.saveState();
				InlineTag tag = (InlineTag) parseRegion(scanner, inlineTagsDictinary);
				if (tag != null) {
					saveTextTokens(description, start, end, strb);
					strb = new StringBuilder();
					start = end;
					description.getContents().add(tag);
					continue;
				} else {
					scanner.restoreState(st);
					if (start == end) {
						assert false;
					}
					/*
					 * On unkown region just continue with text parsing
					 */
					// saveTextTokens(description, start, end, strb);
					// break;
				}
			}
			scanner.next(); // consume c
			if (JSDocCharScanner.isNL(c)) {
				if (scanner.hasNext() && !nextIsTagTitle(scanner)) {
					end = scanner.offset();
				} else {
					break;
				}
			}
			strb.append(c);
			end = scanner.offset();
		}

		String pendingData = strb.toString();
		if (pendingData.isEmpty() == false) {
			saveTextTokens(description, start, end, strb);
		}

		switch (description.getContents().size()) {
		case 0:
			return null;
		case 1:
			return description.getContents().get(0);
		default:
			return description;
		}
	}

	private void saveTextTokens(ComposedContent description, int start, int end, StringBuilder strb) {
		Text text = DomFactory.eINSTANCE.createText();
		JSDocToken token = new JSDocToken(strb.toString(), start, end);
		text.setText(token.token);
		text.setBegin(token.start);
		text.setEnd(token.end);
		description.getContents().add(text);
	}

	/**
	 * Returns true, if a tag title (including leading '@' character) is found next, omitting leading whitespaces.
	 *
	 * @param scanner
	 *            scanner with current offset_, scanner's state is not changed
	 */
	boolean nextIsTagTitle(JSDocCharScanner scanner) {
		ScannerState state = scanner.saveState();
		try {
			scanner.skipWS();
			if (scanner.hasNext() && scanner.peek() == JSDocCharScanner.TAG_START) {
				return TagTitleTokenizer.INSTANCE.nextToken(scanner.copy()) != null;
			}
		} finally {
			scanner.restoreState(state);
		}
		return false;
	}
}
