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
import org.junit.Test;

import org.eclipse.n4js.jsdoc.dom.Composite;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;
import org.eclipse.n4js.jsdoc.tags.AbstractInlineTagDefinition;
import org.eclipse.n4js.jsdoc.tags.AbstractLineTagDefinition;
import org.eclipse.n4js.jsdoc.tokenizers.InlineTagTokenizer;


public class DocletDescriptionParserTest {

	/**
	 * Stub to verify general functionality without concrete tag in the jsdoc parser.
	 */
	private class StubInlineTagDefinition extends AbstractInlineTagDefinition {
		public static final String PARAM_VALUE = "PARAM_VALUE";

		/**
		 *
		 */
		public StubInlineTagDefinition(String title) {
			setTitles(title);
		}

		@Override
		public Tag parse(TagTitle title, JSDocCharScanner scanner) {
			Tag paramTag = createInlineTag(title);

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

				addValue(paramTag, PARAM_VALUE, text);
			}

			return paramTag;
		}

	}

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

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleDescription() {
		String in = "/** This is the description.\n */";
		DocletParser docletParser = new DocletParser(null, null);
		Doclet doclet = docletParser.parse(in);

		EList<ContentNode> contents = doclet.getContents();
		ContentNode node = contents.get(0);
		Text descriptionText = (Text) node;

		assertEquals("This is the description.", descriptionText.getText());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testDescriptionWithInline() {
		String in = "/** This is the description with {@inline tag description} ..and finish description. \n */";
		AbstractInlineTagDefinition inlineTag = new StubInlineTagDefinition("inline");
		DocletParser docletParser = new DocletParser(null, new TagDictionary<>(Arrays.asList(inlineTag)));

		Doclet doclet = docletParser.parse(in);

		EList<ContentNode> contents = doclet.getContents();
		Composite composite = (Composite) contents.get(0);
		ContentNode node0 = composite.getContents().get(0);
		ContentNode node1 = composite.getContents().get(1);
		ContentNode node2 = composite.getContents().get(2);
		Text descriptionText1 = (Text) node0;
		InlineTag descriptionInlineTag = (InlineTag) node1;
		Text descriptionText2 = (Text) node2;

		assertEquals("This is the description with ", descriptionText1.getText());
		assertEquals(" ..and finish description.", descriptionText2.getText());

		TagValue inlineDescription = descriptionInlineTag.getValueByKey(StubInlineTagDefinition.PARAM_VALUE);
		Text tValue = (Text) inlineDescription.getContents().get(0);

		assertEquals(" tag description", tValue.getText());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testDescriptionWithUnknownInlineTag() {
		String in = "/** This is the description with {@unkonwnInlineTag parsed as text} ..and finish description. \n */";
		AbstractInlineTagDefinition inlineTag = new StubInlineTagDefinition("inline");
		DocletParser docletParser = new DocletParser(null, new TagDictionary<>(Arrays.asList(inlineTag)));

		Doclet doclet = docletParser.parse(in);

		EList<ContentNode> contents = doclet.getContents();
		Text descr = (Text) contents.get(0);
		assertEquals("This is the description with {@unkonwnInlineTag parsed as text} ..and finish description.",
				descr.getText());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testParserResumesAfterUnkownInlineTag() {
		String in = "/** This is the description with {@unkonwnInlineTag parsed as text} ... text in between... {@inline tag description} ..and finish description. \n */";
		AbstractInlineTagDefinition inlineTag = new StubInlineTagDefinition("inline");
		DocletParser docletParser = new DocletParser(null, new TagDictionary<>(Arrays.asList(inlineTag)));

		Doclet doclet = docletParser.parse(in);

		EList<ContentNode> contents = doclet.getContents();
		Composite composite = (Composite) contents.get(0);
		ContentNode node0 = composite.getContents().get(0);
		ContentNode node1 = composite.getContents().get(1);
		ContentNode node2 = composite.getContents().get(2);
		Text descriptionText1 = (Text) node0;
		InlineTag descriptionInlineTag = (InlineTag) node1;
		Text descriptionText2 = (Text) node2;

		assertEquals("This is the description with {@unkonwnInlineTag parsed as text} ... text in between... ",
				descriptionText1.getText());
		assertEquals(" ..and finish description.", descriptionText2.getText());

		TagValue inlineDescription = descriptionInlineTag.getValueByKey(StubInlineTagDefinition.PARAM_VALUE);
		Text tValue = (Text) inlineDescription.getContents().get(0);

		assertEquals(" tag description", tValue.getText());
	}

}
