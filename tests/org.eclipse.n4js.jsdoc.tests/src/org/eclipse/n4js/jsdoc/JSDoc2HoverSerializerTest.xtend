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
package org.eclipse.n4js.jsdoc

import org.eclipse.n4js.jsdoc.JSDoc2HoverSerializer
import org.eclipse.n4js.jsdoc.N4JSDocletParser
import org.junit.Assert
import org.junit.Test

class JSDoc2HoverSerializerTest {
	
	
	def assertHover(CharSequence jsdocString, CharSequence expectedHover) {
		val docletParser = new N4JSDocletParser();
		val doclet = docletParser.parse(jsdocString.toString);
		val out = JSDoc2HoverSerializer.toJSDocString(doclet);
		Assert.assertEquals(expectedHover.toString, out);
	}
	
	
	@Test
	def testEmpty() {
		assertHover("", "");	
	}
	
	@Test
	def testSingleLine() {
		assertHover("Single line.", "Single line.");	
	}
	
	@Test
	def testTwoLines() {
		assertHover(
			'''
			First line.
			Second line.
			''',
			'''
			First line.
			Second line.'''
			
		);	
	}
	
	@Test
	def testTwoShortParagraphs() {
		assertHover(
			'''
			/**
			 * 1
			 *
			 * 2
			 */
			''',
			'''
			1
			<p>2'''
		);	
	}
	

	@Test
	def testTwoParagraphs() {
		assertHover(
			'''
			/**
			 * First par.
			 * 
			 * Second par.
			 */
			''',
			'''
			First par.
			<p>Second par.'''
		);	
	}
	
	@Test
	def testInlineCode() {
		assertHover(
			'''
			/**
			 * Some {@code inline}.
			 */
			''',
			'''
			Some <code> inline</code>.'''
		);
	}

	@Test
	def testListWithInlineCode() {
		assertHover(
			'''
			/**
			 * - item {@code inline}.
			 */
			''',
			'''
			<ul>
			<li>item <code> inline</code>.</ul>
			'''
		);
	}
	
	@Test
	def testRealWorld() {
		assertHover(
			'''
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
			''',
			'''
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
			</dl>'''
		);	
	}
	
}
