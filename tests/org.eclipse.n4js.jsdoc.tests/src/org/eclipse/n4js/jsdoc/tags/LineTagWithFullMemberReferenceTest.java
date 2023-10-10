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
import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.FullMemberReference;
import org.eclipse.n4js.jsdoc.dom.LineTag;

/**
 */
public class LineTagWithFullMemberReferenceTest {

	
	@Test
	public void test_fullRef_simpleName() {
		String in = "/** foo."
				+ "\n * @testee n4/model/collections/DataSet.DataSet.each"
				+ "\n */";
		AbstractLineTagDefinition tagDef = new LineTagWithFullMemberReference("testee");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tagDef)),
				new TagDictionary<AbstractInlineTagDefinition>());
		Doclet doclet = docletParser.parse(in);

		LineTag lineTag = doclet.getLineTags().get(0);

		EList<ContentNode> contents = lineTag.getValueByKey(LineTagWithFullElementReference.REF)
				.getContents();
		FullMemberReference ref = (FullMemberReference) contents.get(0);

		assertEquals("n4/model/collections/DataSet", ref.getModuleName());
		assertEquals("DataSet", ref.getTypeName());
		assertEquals("each", ref.getMemberName());
	}

	
	@Test
	public void test_fullRef_symbolName() {
		String in = "/** foo."
				+ "\n * @testee n4/model/collections/DataSet.DataSet.#iterator"
				+ "\n */";
		AbstractLineTagDefinition tagDef = new LineTagWithFullMemberReference("testee");
		DocletParser docletParser = new DocletParser(new TagDictionary<>(Arrays.asList(tagDef)),
				new TagDictionary<AbstractInlineTagDefinition>());
		Doclet doclet = docletParser.parse(in);

		LineTag lineTag = doclet.getLineTags().get(0);

		EList<ContentNode> contents = lineTag.getValueByKey(LineTagWithFullElementReference.REF)
				.getContents();
		FullMemberReference ref = (FullMemberReference) contents.get(0);

		assertEquals("n4/model/collections/DataSet", ref.getModuleName());
		assertEquals("DataSet", ref.getTypeName());
		assertEquals("#iterator", ref.getMemberName());
	}
}
