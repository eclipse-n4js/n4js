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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.n4js.jsdoc.DocletParser;
import org.eclipse.n4js.jsdoc.TagDictionary;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.LineTag;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.junit.Test;

/**
 * General tests for LineTag. A lot depends on line tag specific parse method. Few tests below are just checking if
 * after parsing jsdoc dom will be populated correctly, if we can querry values, if sub parsers (whan called by tag) are
 * populating dom correctly.
 */
public class LineTagTest {

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleLineTag() {
		String in = "/** foo.\n * @stubLineTagTitle\n */";
		AbstractLineTagDefinition tag = new StubLineTagWithRegionDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<AbstractInlineTagDefinition>());
		Doclet doclet = docletParser.parse(in);

		LineTag lineTag = doclet.getLineTags().get(0);
		assertEquals("stubLineTagTitle", lineTag.getTitle().getTitle());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testLineTagWithRegion() {
		String in = "/** foo.\n * @stubLineTagTitle {@region value}\n */";
		AbstractLineTagDefinition tag = new StubLineTagWithRegionDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<AbstractInlineTagDefinition>());
		Doclet doclet = docletParser.parse(in);

		LineTag lineTag = doclet.getLineTags().get(0);
		TagValue region = lineTag.getValueByKey(StubLineTagWithRegionDefinition.REGION);
		Text strcturedText = (Text) region.getContents().get(0);
		assertEquals("@region value", strcturedText.getText());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testLineTagDescrWithTrimmedSpace() {
		String in = "/** foo."
				+ "\n * @stubLineTagTitle  tag description. "
				+ "\n */";
		AbstractLineTagDefinition tag = new StubLineTagWithRegionDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<AbstractInlineTagDefinition>());
		Doclet doclet = docletParser.parse(in);

		LineTag lineTag = doclet.getLineTags().get(0);
		TagValue vDescription = lineTag.getValueByKey(StubLineTagWithRegionDefinition.DESCR);
		Text tabDescription = (Text) vDescription.getContents().get(0);
		String deString = tabDescription.getText();
		assertEquals("tag description.", deString);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testLineTagDescrWithOutSpace() {
		String in = "/** foo."
				+ "\n * @stubLineTagTitle  tag description."
				+ "\n */";
		AbstractLineTagDefinition tag = new StubLineTagWithRegionDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<AbstractInlineTagDefinition>());
		Doclet doclet = docletParser.parse(in);

		LineTag lineTag = doclet.getLineTags().get(0);
		TagValue vDescription = lineTag.getValueByKey(StubLineTagWithRegionDefinition.DESCR);
		Text tabDescription = (Text) vDescription.getContents().get(0);
		String deString = tabDescription.getText();
		assertEquals("tag description.", deString);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testLineTagWithRegionAndDescr() {
		String in = "/** foo.\n * @stubLineTagTitle {@region value} tag description.\n */";
		AbstractLineTagDefinition tag = new StubLineTagWithRegionDefinition("stubLineTagTitle");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tag)),
				new TagDictionary<AbstractInlineTagDefinition>());
		Doclet doclet = docletParser.parse(in);

		LineTag lineTag = doclet.getLineTags().get(0);
		TagValue region = lineTag.getValueByKey(StubLineTagWithRegionDefinition.REGION);
		Text strcturedText = (Text) region.getContents().get(0);
		assertEquals("@region value", strcturedText.getText());

		TagValue vDescription = lineTag.getValueByKey(StubLineTagWithRegionDefinition.DESCR);
		Text tabDescription = (Text) vDescription.getContents().get(0);
		String deString = tabDescription.getText();
		assertEquals("tag description.", deString);
	}

}
