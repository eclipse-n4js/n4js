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

import org.eclipse.emf.common.util.EList;
import org.junit.Test;

import org.eclipse.n4js.jsdoc.DocletParser;
import org.eclipse.n4js.jsdoc.TagDictionary;
import org.eclipse.n4js.jsdoc.dom.Composite;
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.InlineTag;
import org.eclipse.n4js.jsdoc.dom.TagValue;
import org.eclipse.n4js.jsdoc.dom.Text;

/**
 * General tests for InLineTag. A lot depends on line tag specific parse method. Few tests below are just checking if
 * after parsing jsdoc dom will be populated correctly, if we can querry values, if sub parsers (whan called by tag) are
 * populating dom correctly.
 */
public class InlineTagTest {

	
	@Test
	public void testSimpleLineTag() {
		String in = "/** Some Description {@inline me} some other text. \n */";
		AbstractInlineTagDefinition tag = new StubInlineTagDefinition("inline");
		DocletParser docletParser = new DocletParser(new TagDictionary<AbstractLineTagDefinition>(),
				new TagDictionary<>(Arrays.asList(tag)));

		Doclet doclet = docletParser.parse(in);

		EList<ContentNode> contents = doclet.getContents();
		Composite composite = (Composite) contents.get(0);
		ContentNode node0 = composite.getContents().get(0);
		ContentNode node1 = composite.getContents().get(1);
		ContentNode node2 = composite.getContents().get(2);
		Text descriptionText1 = (Text) node0;
		InlineTag descriptionInlineTag = (InlineTag) node1;
		Text descriptionText2 = (Text) node2;

		assertEquals("Some Description ", descriptionText1.getText());
		assertEquals(" some other text.", descriptionText2.getText());

		TagValue inlineDescription = descriptionInlineTag.getValueByKey(StubInlineTagDefinition.PARAM_VALUE);
		Text tValue = (Text) inlineDescription.getContents().get(0);

		assertEquals(" me", tValue.getText());
	}

}
