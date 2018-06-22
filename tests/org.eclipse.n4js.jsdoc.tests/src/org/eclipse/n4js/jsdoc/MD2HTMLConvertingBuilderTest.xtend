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

import org.junit.Assert
import org.junit.Test

class MD2HTMLConvertingBuilderTest {
	
	
	def assertConvert(CharSequence markdown, CharSequence expectedHTML) {
		val html = MD2HTMLConvertingBuilder.convert(markdown.toString);
		Assert.assertEquals(expectedHTML.toString, html);
	}
	
	
	@Test
	def testEmpty() {
		assertConvert("", "");	
	}
	
	@Test
	def testSingleLine() {
		assertConvert("Single line.", "Single line.");	
	}
	
	@Test
	def testTwoLines() {
		assertConvert(
			'''
			First line.
			Second line.
			''',
			'''
			First line.
			Second line.
			''');	
	}

	@Test
	def testTwoParagraphs() {
		assertConvert(
			'''
			First par.
			
			Second par.
			''',
			'''
			First par.
			<p>Second par.
			''');	
	}
	
	@Test
	def testList() {
		assertConvert(
			'''
			This is a list:
			- hello
			- world
			''',
			'''
			This is a list:
			<ul>
			<li>hello
			<li>world
			</ul>
			''');	
	}
	
	@Test
	def testMultiLineList() {
		assertConvert(
			'''
			This is a list:
			- hello
			  hallo
			- world
			''',
			'''
			This is a list:
			<ul>
			<li>hello
			hallo
			<li>world
			</ul>
			''');	
	}
	
	@Test
	def testListCont() {
		assertConvert(
			'''
			This is a list:
			- hello
			- world
			cont
			
			More text
			''',
			'''
			This is a list:
			<ul>
			<li>hello
			<li>world
			cont
			</ul>
			<p>More text
			''');	
	}


	@Test
	def testNestedListCont() {
		assertConvert(
			'''
			This is a list:
			- hello
				- this
				- is
			- world
				- some text
				
			More text
			''',
			'''
			This is a list:
			<ul>
			<li>hello
			<ul>
			<li>this
			<li>is
			</ul>
			<li>world
			<ul>
			<li>some text
			</ul>
			</ul>
			<p>More text
			''');	
	}
	
}
