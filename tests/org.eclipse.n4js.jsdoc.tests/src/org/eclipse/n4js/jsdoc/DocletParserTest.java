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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.tags.AbstractLineTagDefinition;
import org.junit.Test;

/**
 * General test for DocletParser. Checking how main parsing algorithm works. Description specific content is tested in
 * DocletDescriptionParserTest. LineTags specific content is tested in test package org.eclipse.n4js.jsdoc.tags
 */
public class DocletParserTest {

	/**
	 * Stub to verify general functionality without concrete tag in the jsdoc parser.
	 */
	public class StubLineTagDefinition extends AbstractLineTagDefinition {

		public StubLineTagDefinition(String title) {
			setTitles(title);
		}

		@Override
		public Tag parse(TagTitle title, JSDocCharScanner scanner, DescriptionParser descriptionParser) {
			Tag paramTag = createLineTag(title);
			return paramTag;
		}
	}

	@Test
	public void testJSDocParsing() {
		String in = "/** This is the description.\n * @stubLineTagTitle \n */";
		AbstractLineTagDefinition tag = new StubLineTagDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<>());
		Doclet doclet = docletParser.parse(in);

		Text descr = (Text) doclet.getContents().get(0);
		assertEquals("This is the description.", descr.getText());

		LineTag lineTag = doclet.getLineTags().get(0);
		assertEquals("stubLineTagTitle", lineTag.getTitle().getTitle());
	}

	@Test
	public void testJSDocParsingWithoutMainDescription() {
		String in = "/** \n * @stubLineTagTitle \n */";
		AbstractLineTagDefinition tag = new StubLineTagDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<>());
		Doclet doclet = docletParser.parse(in);

		LineTag lineTag = doclet.getLineTags().get(0);
		assertEquals("stubLineTagTitle", lineTag.getTitle().getTitle());
	}

	@Test
	public void testJSDocParsingWithManyLineTags() {
		String in = "/** \n * @stubLineTagTitle0 \n * @stubLineTagTitle1 \n * @stubLineTagTitle2 \n */";
		AbstractLineTagDefinition tag0 = new StubLineTagDefinition("stubLineTagTitle0");
		AbstractLineTagDefinition tag1 = new StubLineTagDefinition("stubLineTagTitle1");
		AbstractLineTagDefinition tag2 = new StubLineTagDefinition("stubLineTagTitle2");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag0, tag1, tag2)),
				new TagDictionary<>());
		Doclet doclet = docletParser.parse(in);

		EList<LineTag> lineTags = doclet.getLineTags();
		assertEquals(3, lineTags.size());

		// assuming that list is as parsing order
		LineTag lineTag0 = lineTags.get(0);
		assertEquals("stubLineTagTitle0", lineTag0.getTitle().getTitle());
		LineTag lineTag1 = lineTags.get(1);
		assertEquals("stubLineTagTitle1", lineTag1.getTitle().getTitle());
		LineTag lineTag2 = lineTags.get(2);
		assertEquals("stubLineTagTitle2", lineTag2.getTitle().getTitle());
	}

	@Test
	public void testJSDocParsingWithoutLineTags() {
		String in = "/** Just free text.\n */";
		AbstractLineTagDefinition tag = new StubLineTagDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<>());
		Doclet doclet = docletParser.parse(in);

		Text descr = (Text) doclet.getContents().get(0);
		assertEquals("Just free text.", descr.getText());
	}

	@Test
	public void testTrailingDescriptionIsIgnored() {
		String in = "/** This is the description.\n * @stubLineTagTitle \n * Trailing description.\n */";
		AbstractLineTagDefinition tag = new StubLineTagDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<>());
		Doclet doclet = docletParser.parse(in);

		assertEquals(1, doclet.getContents().size());
		assertEquals(1, doclet.getContents().size());
		Text descr = (Text) doclet.getContents().get(0);
		assertEquals("This is the description.", descr.getText());

		LineTag lineTag = doclet.getLineTags().get(0);
		assertEquals("stubLineTagTitle", lineTag.getTitle().getTitle());
	}

	@Test
	public void testTrailingDescriptionAndAllThatFollowsIsIgnored() {
		String in = "/** This is the description." + "\n * @stubLineTagTitle " + "\n * Trailing description."
				+ "\n * @stubLineTagTitle2 " + "\n */";
		AbstractLineTagDefinition tag = new StubLineTagDefinition("stubLineTagTitle");
		AbstractLineTagDefinition tag2 = new StubLineTagDefinition("stubLineTagTitle2");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag, tag2)),
				new TagDictionary<>());
		Doclet doclet = docletParser.parse(in);

		assertEquals(1, doclet.getContents().size());
		Text descr = (Text) doclet.getContents().get(0);
		assertEquals("This is the description.", descr.getText());

		assertEquals(2, doclet.getLineTags().size());
		LineTag lineTag = doclet.getLineTags().get(0);
		assertEquals("stubLineTagTitle", lineTag.getTitle().getTitle());
	}

	@Test
	public void testUnkonwLineTagIsIgnored() {
		String in = "/** \n * @unkonwTagTitle \n */";
		AbstractLineTagDefinition tag = new StubLineTagDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<>());
		Doclet doclet = docletParser.parse(in);

		assertEquals(0, doclet.getLineTags().size());
	}

	@Test
	public void testParserResumesAfterUnkonwLineTag() {
		String in = "/** \n * @unkonwLineTag \n * @stubLineTagTitle1 \n * @stubLineTagTitle2 \n */";
		AbstractLineTagDefinition tag0 = new StubLineTagDefinition("stubLineTagTitle0");
		AbstractLineTagDefinition tag1 = new StubLineTagDefinition("stubLineTagTitle1");
		AbstractLineTagDefinition tag2 = new StubLineTagDefinition("stubLineTagTitle2");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag0, tag1, tag2)),
				new TagDictionary<>());
		Doclet doclet = docletParser.parse(in);

		EList<LineTag> lineTags = doclet.getLineTags();
		assertEquals(2, lineTags.size());

		// assuming that list is as parsing order
		LineTag lineTag1 = lineTags.get(0);
		assertEquals("stubLineTagTitle1", lineTag1.getTitle().getTitle());
		LineTag lineTag2 = lineTags.get(1);
		assertEquals("stubLineTagTitle2", lineTag2.getTitle().getTitle());
	}
}
