/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.jsdoc;

import org.eclipse.n4js.jsdoc.dom.Doclet;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class JSDoc2HoverSerializerTest {

	void assertHover(CharSequence jsdocString, CharSequence expectedHover) {
		N4JSDocletParser docletParser = new N4JSDocletParser();
		Doclet doclet = docletParser.parse(jsdocString.toString());
		String out = JSDoc2HoverSerializer.toJSDocString(doclet);
		Assert.assertEquals(expectedHover.toString(), out);
	}

	@Test
	public void testEmpty() {
		assertHover("", "");
	}

	@Test
	public void testSingleLine() {
		assertHover("Single line.", "Single line.");
	}

	@Test
	public void testTwoLines() {
		assertHover(
				"""
						First line.
						Second line.
						""",
				"""
						First line.
						Second line."""

		);
	}

	@Test
	public void testTwoShortParagraphs() {
		assertHover(
				"""
						/**
						 * 1
						 *
						 * 2
						 */
						""",
				"""
						1
						<p>2""");
	}

	@Test
	public void testTwoParagraphs() {
		assertHover(
				"""
						/**
						 * First par.
						 *
						 * Second par.
						 */
						""",
				"""
						First par.
						<p>Second par.""");
	}

	@Test
	public void testInlineCode() {
		assertHover(
				"""
						/**
						 * Some {@code inline}.
						 */
						""",
				"""
						Some <code> inline</code>.""");
	}

	@Test
	public void testListWithInlineCode() {
		assertHover(
				"""
						/**
						 * - item {@code inline}.
						 */
						""",
				"""
						<ul>
						<li>item <code> inline</code>.</ul>
						""");
	}

	@Test
	public void testRealWorld() {
		assertHover(
				"""
						/**
						 * First par.
						 * - some
						 * - details
						 * Lazy par.
						 * @param p string
						 * @author Nobody
						 * @specFromDecscription
						 * @param t int
						 * @author Anybody
						 */
						""",
				"""
						First par.
						<ul>
						<li>some
						<li>details
						Lazy par.</ul>

						<dl>
						<dt>Param:</dt>
						<dd>
						<ul><li>p string</li><li>t int</li></ul></dd>
						<dt>Author:</dt>
						<dd>Nobody<p>Anybody
						</dl>""");
	}

}
