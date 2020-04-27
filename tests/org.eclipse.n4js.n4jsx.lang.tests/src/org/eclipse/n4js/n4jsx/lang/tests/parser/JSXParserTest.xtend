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
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.JSXExpression
import org.eclipse.n4js.n4JS.JSXPropertyAttribute
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.TemplateLiteral
import org.eclipse.n4js.n4JS.TemplateSegment
import org.junit.Test
import org.eclipse.n4js.n4JS.JSXText

class JSXParserTest extends AbstractN4JSXParserTest {

	@Test
	def void testJSXText_01() {
		val script = '''
			<div>some   text</div>
		'''.parseSuccessfully
		assertEquals(1, script.scriptElements.size)
		val JSXElement jsxElement = (script.scriptElements.get(0) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);
		val child = jsxElement.jsxChildren.head as JSXText
		assertEquals("some   text", child.value)
	}
	
	@Test
	def void testJSXText_02() {
		val script = '''
			<div>/*comment*/some<nested/>text/*comment*/</div>
		'''.parseSuccessfully
		assertEquals(1, script.scriptElements.size)
		val JSXElement jsxElement = (script.scriptElements.get(0) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);
		assertEquals("/*comment*/some", (jsxElement.jsxChildren.head as JSXText).value)
		assertEquals("text/*comment*/", (jsxElement.jsxChildren.last as JSXText).value)
	}
	
	@Test
	def void testJSXText_03() {
		val script = '''
			<div>/*comment*/some{1+1}text/*comment*/</div>
		'''.parseSuccessfully
		assertEquals(1, script.scriptElements.size)
		val JSXElement jsxElement = (script.scriptElements.get(0) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);
		assertEquals("/*comment*/some", (jsxElement.jsxChildren.head as JSXText).value)
		assertEquals("text/*comment*/", (jsxElement.jsxChildren.last as JSXText).value)
	}
	
	@Test
	def void testJSXText_04() {
		val script = '''
			<div>/not a regex/{1+1}not an automatic
			semicolon
			// not a comment
			</div>
		'''.parseSuccessfully
		assertEquals(1, script.scriptElements.size)
		val JSXElement jsxElement = (script.scriptElements.get(0) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);
		assertEquals("/not a regex/", (jsxElement.jsxChildren.head as JSXText).value)
		assertEquals('''
			not an automatic
			semicolon
			// not a comment
		'''.toString(), (jsxElement.jsxChildren.last as JSXText).value)
	}
	
	@Test
	def void testJSXText_05() {
		val script = '''
			<div>// not a comment</div>
		'''.parseSuccessfully
		assertEquals(1, script.scriptElements.size)
		val JSXElement jsxElement = (script.scriptElements.get(0) as ExpressionStatement).expression as JSXElement;
		assertTagName("div", jsxElement);
		assertEquals("// not a comment", (jsxElement.jsxChildren.head as JSXText).value)
	}

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
	
	@Test
	def void testAttributeNameWithDash() {
		'''
			<a attr-1="true"></a>
		'''.parseSuccessfully
	}
	
	@Test
	def void testAttributeNameWithTrailingDash_01() {
		'''
			<a attr-></a>
		'''.parseSuccessfully
	}
	
	@Test
	def void testAttributeNameWithTrailingDash_02() {
		'''
			<a attr-="" attr-2></a>
		'''.parseSuccessfully
	}
	
	@Test
	def void testAttributeNameWithTrailingDash_03() {
		'''
			<a attr--="" attr-2></a>
		'''.parseSuccessfully
	}
	
	@Test
	def void testAttributeNameWithTrailingDashes() {
		'''
			<a attr-1--000---></a>
		'''.parseSuccessfully
	}
	
	@Test
	def void testAttributeNumberKinds() {
		'''
			<a attr-1-0x0-0b0-0o0-00a-0e='false'></a>
		'''.parseSuccessfully
	}
	
	@Test
	def void testAttributeNameKeyword() {
		'''
			<a true="undefined"></a>
		'''.parseSuccessfully
	}
	
	@Test
	def void testAttributeValueJsxElement() {
		'''
			(<a attr=<b></b>></a>)
			(<a attr=<b/>></a>)
		'''.parseSuccessfully
	}
	
	@Test
	def void testAttributeValueJsxFragment() {
		'''
			<a attr=<></>></a>
		'''.parseSuccessfully
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
	 * The example can be parsed successfully with babel, too.
	 */
	@Test
	def void testRegExAmbiguityContra_01() {
		'''
			"Hello"

			(<div></div>)
		'''.parseSuccessfully
	}

	/**
	 * The example can be parsed successfully with babel, too.
	 */
	@Test
	def void testRegExAmbiguityContra_02() {
		'''
			<div></div>

			"Hello"
		'''.parseSuccessfully
	}
	
	@Test
	def void testInvalidAttributeNames_01() {
		'''
			<a attr/**/-attr></a>
		'''.parseWithError("JSX attribute names may not contain whitespace or comments. Attribute names ending with a - and directly followed by another attribute name are merged and must not contain whitespace or comments.")
	}
	
	@Test
	def void testInvalidAttributeNames_02() {
		'''
			<a attr -attr></a>
		'''.parseWithError("JSX attribute names may not contain whitespace or comments. Attribute names ending with a - and directly followed by another attribute name are merged and must not contain whitespace or comments.")
	}
	
	@Test
	def void testInvalidAttributeNames_03() {
		'''
			<a attr-- attr></a>
		'''.parseWithError("JSX attribute names may not contain whitespace or comments. Attribute names ending with a - and directly followed by another attribute name are merged and must not contain whitespace or comments.")
	}
	
	@Test
	def void testInvalidAttributeNames_04() {
		'''
			<a abcd\u0065="true"></a>
		'''.parseWithError("Illegal character in identifier 'abcd\\u0065' (\\) at position 4.")
	}
	
	protected def parseWithError(CharSequence js, String message) {
		val script = js.parseN4JSX
		val errors = script.eResource.errors
		assertFalse(errors.toString, errors.empty)
		if (!errors.map[it.message].contains(message)) {
			assertEquals(errors.toString, "")
		}
		return script
	}

}
