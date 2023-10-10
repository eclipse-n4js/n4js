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
package org.eclipse.n4js.jsdoc.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.eclipse.n4js.jsdoc.DocletParser;
import org.eclipse.n4js.jsdoc.JSDocSerializer;
import org.eclipse.n4js.jsdoc.N4JSDocletParser;
import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.eclipse.n4js.jsdoc.dom.Text;

/**
 * Simple JSDoc specific tests only comparing serialized doclet with original string.
 */
public class SimpleN4JSDocTest {

	private void assertSameNumberOfLineTags(int expectedNumberOfTags, String jsdocString) {
		DocletParser docletParser = new N4JSDocletParser();
		Doclet doclet = docletParser.parse(jsdocString);
		assertEquals("Line tag number differs", expectedNumberOfTags, doclet.getLineTags().size());
	}

	private void assertSameNumberOfLineTags(int expectedNumberOfTags, Doclet doclet) {
		assertEquals("Line tag number differs", expectedNumberOfTags, doclet.getLineTags().size());
	}

	/**
	 * @param jsdocString
	 *            jsDoc string
	 */
	private void assertSameSerializationForm(String jsdocString) {
		DocletParser docletParser = new N4JSDocletParser();
		Doclet doclet = docletParser.parse(jsdocString);
		String out = JSDocSerializer.toJSDocString(doclet);
		assertEquals(jsdocString, out);
	}

	
	@Test
	public void testTaskWithOutContent() {
		String in = "/**"
				+ "\n * @task" // test tag without content
				+ "\n */";
		assertSameNumberOfLineTags(1, in);
		assertSameSerializationForm(in);
	}

	
	@Test
	public void testTaskWithFollowingLine() {
		String in = "/**"
				+ "\n * @task" // test tag without content
				+ "\n * @" + "author withValue" // test tag without content
				+ "\n */";

		assertSameNumberOfLineTags(2, in);
		assertSameSerializationForm(in);
	}

	
	@Test
	public void testAPIRelatedTags() {
		String in = "/**"
				+ "\n * @spec This is the spec"
				+ "\n * @apiNote Well done"
				+ "\n * @apiState stable"
				+ "\n * @" + "author Me"
				+ "\n */";

		assertSameNumberOfLineTags(4, in);
		assertSameSerializationForm(in);
	}

	
	@Test
	public void testTestRelatedTags() {
		String in = "/**"
				+ "\n * @testee some/package/module.Type.Member"
				+ "\n * @testee some/package/module.Type"
				+ "\n * @" + "author Me"
				+ "\n */";

		assertSameNumberOfLineTags(3, in);
		assertSameSerializationForm(in);
	}

	
	@Test
	public void testSpecAndNoNewline() {
		String in = "/**"
				+ "\n * @return some text"
				+ "\n * @spec comment"
				+ "\n */";

		assertSameNumberOfLineTags(2, in);
		assertSameSerializationForm(in);
	}

	
	@Test
	public void testSpecAndNewline() {
		String in = "/**"
				+ "\n * @return some text"
				+ "\n *"
				+ "\n * @spec comment"
				+ "\n */";

		assertSameNumberOfLineTags(2, in);
	}

	
	@Test
	public void testDescriptionWithInlineAndSeveralTags() {
		String in = "/**"
				+ "\n * Sample description"
				+ "\n * yet another line {@code sample} with code."
				+ "\n * @reqid 1234"
				+ "\n * @task" // test tag without content
				+ "\n * @task IDE-1356"
				+ "\n * @spec This is internal. The following things {@link my/class/B} are to be done:"
				+ "\n * 		<ol>"
				+ "\n *  			<li>world"
				+ "\n * 		</ol>"
				+ "\n * and maybe more"
				+ "\n * @task IDE-1357"
				+ "\n */";

		assertSameNumberOfLineTags(5, in);
		assertSameSerializationForm(in);
	}

	
	@Test
	public void testWhitespaces_IDEBUG_571_a() {
		String in = "/**\n" +
				" * X\n" +
				" *\n" +
				" * @testeeType Y" +
				" */";

		DocletParser docletParser = new N4JSDocletParser();
		Doclet doclet = docletParser.parse(in);
		String descr = ((Text) doclet.getContents().get(0)).getText();
		assertEquals("X", descr);

		assertSameNumberOfLineTags(1, doclet);
	}

	
	@Test
	public void testWhitespaces_IDEBUG_571_b() {
		String in = "/**\n" +
				" * X\n" +
				" * \n" +
				" * @testeeType Y\n" +
				" */";
		DocletParser docletParser = new N4JSDocletParser();
		Doclet doclet = docletParser.parse(in);
		String descr = ((Text) doclet.getContents().get(0)).getText();
		assertEquals("X", descr);

		assertSameNumberOfLineTags(1, doclet);

	}

}
