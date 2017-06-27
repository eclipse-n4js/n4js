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
package org.eclipse.n4js.n4jsx.lang.tests.parser

import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement
import org.eclipse.n4js.n4jsx.tests.helper.parser.AbstractN4JSXParserTest
import org.junit.Test
import org.eclipse.n4js.n4JS.TemplateSegment
import org.eclipse.n4js.n4jsx.n4JSX.JSXExpression
import org.eclipse.n4js.n4JS.TemplateLiteral
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute
import org.eclipse.n4js.n4JS.StringLiteral

class JSXWithOutFreeTextTest extends AbstractN4JSXParserTest {

	@Test
	def void testSelfClosingTag() {
		val script = '''
			class Foo{}
			<div />
			function bar() {}
		'''.parseSuccessfully
		assertEquals(3, script.scriptElements.size)
		assertType(N4ClassDeclaration, script.scriptElements.get(0))
		assertType(FunctionDeclaration, script.scriptElements.get(2))

		val JSXElement jsxElement = (script.scriptElements.get(1) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);
	}

	@Test
	def void testOpenCloseTag() {
		val script = '''
			class Foo{}
			<div></div>
			function bar() {}
		'''.parseSuccessfully
		assertEquals(3, script.scriptElements.size)
		assertType(N4ClassDeclaration, script.scriptElements.get(0))
		assertType(FunctionDeclaration, script.scriptElements.get(2))

		val JSXElement jsxElement = (script.scriptElements.get(1) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);
	}

	@Test
	def void testOpenCloseTagWithNested() {
		val script = '''
			class Foo{}
			<div><Foo></Foo></div>
			function bar() {}
		'''.parseSuccessfully
		assertEquals(3, script.scriptElements.size)
		assertType(N4ClassDeclaration, script.scriptElements.get(0))
		assertType(FunctionDeclaration, script.scriptElements.get(2))

		val JSXElement jsxElement = (script.scriptElements.get(1) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);
		assertTagName("Foo", jsxElement.jsxChildren.get(0));
	}

	@Test
	def void testOpenCloseTagWithNestedExpression() {
		val script = '''
			class Foo{}
			<div>{`Hello «»
			World`}</div>
			function bar() {}
		'''.parseSuccessfully
		assertEquals(3, script.scriptElements.size)
		assertType(N4ClassDeclaration, script.scriptElements.get(0))
		assertType(FunctionDeclaration, script.scriptElements.get(2))

		val JSXElement jsxElement = (script.scriptElements.get(1) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);
		val TemplateSegment seg = ((jsxElement.jsxChildren.get(0) as JSXExpression).expression as TemplateLiteral).segments.head as TemplateSegment;

		assertEquals("Hello \nWorld",seg.valueAsString );
	}

	@Test
	def void testOpenCloseTagWithPropertyAttribute() {
		val script = '''
			class Foo{}
			<div class="Test"></div>
			function bar() {}
		'''.parseSuccessfully
		assertEquals(3, script.scriptElements.size)
		assertType(N4ClassDeclaration, script.scriptElements.get(0))
		assertType(FunctionDeclaration, script.scriptElements.get(2))

		val JSXElement jsxElement = (script.scriptElements.get(1) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);

		val attr = jsxElement.jsxAttributes.head as JSXPropertyAttribute
		assertEquals("Test", (attr.jsxAttributeValue as StringLiteral).value)
	}

	/**
	 * The example produces the same error when using the babel transpiler.
	 */
	@Test
	def void testRegExAmbiguity() {
		'''
			"Hello"

			<div></div>
		'''.parseWithError
	}

	/**
	 * The example produces the same error when using the babel transpiler.
	 */
	@Test
	def void testRegExAmbiguityContra() {
		'''
			<div></div>

			"Hello"
		'''.parseSuccessfully
	}


}
