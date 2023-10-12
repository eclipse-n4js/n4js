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
package org.eclipse.n4js.n4jsx.lang.tests.parser;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXExpression;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;

public class JSXParserTest extends AbstractN4JSXParserTest {

	@Test
	public void testSelfClosingTag() throws Exception {
		Script script = parseSuccessfully("""
				class Foo{}
				<div />
				function bar() {}
				""");
		assertEquals(3, script.getScriptElements().size());
		assertType(N4ClassDeclaration.class, script.getScriptElements().get(0));
		assertType(FunctionDeclaration.class, script.getScriptElements().get(2));

		JSXElement jsxElement = (JSXElement) ((ExpressionStatement) script.getScriptElements().get(1)).getExpression();
		assertTagName("div", jsxElement);
	}

	@Test
	public void testOpenCloseTag() throws Exception {
		Script script = parseSuccessfully("""
				class Foo{}
				<div></div>
				function bar() {}
				""");
		assertEquals(3, script.getScriptElements().size());
		assertType(N4ClassDeclaration.class, script.getScriptElements().get(0));
		assertType(FunctionDeclaration.class, script.getScriptElements().get(2));

		JSXElement jsxElement = (JSXElement) ((ExpressionStatement) script.getScriptElements().get(1)).getExpression();
		assertTagName("div", jsxElement);
	}

	@Test
	public void testOpenCloseTagWithNested() throws Exception {
		Script script = parseSuccessfully("""
				class Foo{}
				<div><Foo></Foo></div>
				function bar() {}
				""");
		assertEquals(3, script.getScriptElements().size());
		assertType(N4ClassDeclaration.class, script.getScriptElements().get(0));
		assertType(FunctionDeclaration.class, script.getScriptElements().get(2));

		JSXElement jsxElement = (JSXElement) ((ExpressionStatement) script.getScriptElements().get(1)).getExpression();
		assertTagName("div", jsxElement);
		assertTagName("Foo", jsxElement.getJsxChildren().get(0));
	}

	@Test
	public void testOpenCloseTagWithNestedExpression() throws Exception {
		Script script = parseSuccessfully("""
				class Foo{}
				<div>{`Hello\\u0020
				World`}</div>
				function bar() {}
				""");
		assertEquals(3, script.getScriptElements().size());
		assertType(N4ClassDeclaration.class, script.getScriptElements().get(0));
		assertType(FunctionDeclaration.class, script.getScriptElements().get(2));

		JSXElement jsxElement = (JSXElement) ((ExpressionStatement) script.getScriptElements().get(1)).getExpression();
		assertTagName("div", jsxElement);
		TemplateSegment seg = (TemplateSegment) ((TemplateLiteral) ((JSXExpression) jsxElement.getJsxChildren().get(0))
				.getExpression()).getSegments().get(0);

		assertEquals("Hello \nWorld", seg.getValueAsString());
	}

	@Test
	public void testOpenCloseTagWithPropertyAttribute() throws Exception {
		Script script = parseSuccessfully("""
				class Foo{}
				<div class="Test"></div>
				function bar() {}
				""");
		assertEquals(3, script.getScriptElements().size());
		assertType(N4ClassDeclaration.class, script.getScriptElements().get(0));
		assertType(FunctionDeclaration.class, script.getScriptElements().get(2));

		JSXElement jsxElement = (JSXElement) ((ExpressionStatement) script.getScriptElements().get(1)).getExpression();
		assertTagName("div", jsxElement);

		JSXPropertyAttribute attr = (JSXPropertyAttribute) jsxElement.getJsxAttributes().get(0);
		assertEquals("Test", ((StringLiteral) attr.getJsxAttributeValue()).getValue());
	}

	@Test
	public void testAttributeNameWithDash() throws Exception {
		parseSuccessfully("""
				<a attr-1="true"></a>
				""");
	}

	@Test
	public void testAttributeNameWithTrailingDash_01() throws Exception {
		parseSuccessfully("""
				<a attr-></a>
				""");
	}

	@Test
	public void testAttributeNameWithTrailingDash_02() throws Exception {
		parseSuccessfully("""
				<a attr-="" attr-2></a>
				""");
	}

	@Test
	public void testAttributeNameWithTrailingDash_03() throws Exception {
		parseSuccessfully("""
				<a attr--="" attr-2></a>
				""");
	}

	@Test
	public void testAttributeNameWithTrailingDashes() throws Exception {
		parseSuccessfully("""
				<a attr-1--000---></a>
				""");
	}

	@Test
	public void testAttributeNumberKinds() throws Exception {
		parseSuccessfully("""
					<a attr-1-0x0-0b0-0o0-00a-0e='false'></a>
				""");
	}

	@Test
	public void testAttributeNameKeyword() throws Exception {
		parseSuccessfully("""
				<a true="undefined"></a>
				""");
	}

	@Test
	public void testAttributeValueJsxElement() throws Exception {
		parseSuccessfully("""
				(<a attr=<b></b>></a>)
				(<a attr=<b/>></a>)
				""");
	}

	@Test
	public void testAttributeValueJsxFragment() throws Exception {
		parseSuccessfully("""
				<a attr=<></>></a>
				""");
	}

	/**
	 * The example produces the same error when using the babel transpiler.
	 */
	@Test
	public void testRegExAmbiguity() throws Exception {
		parseWithError("""
				"Hello"

				<div></div>
				""");
	}

	/**
	 * The example can be parsed successfully with babel, too.
	 */
	@Test
	public void testRegExAmbiguityContra_01() throws Exception {
		parseSuccessfully("""
				"Hello"

				(<div></div>)
				""");
	}

	/**
	 * The example can be parsed successfully with babel, too.
	 */
	@Test
	public void testRegExAmbiguityContra_02() throws Exception {
		parseSuccessfully("""
				<div></div>

				"Hello"
				""");
	}

	@Test
	public void testInvalidAttributeNames_01() throws Exception {
		parseWithError("""
				<a attr/**/-attr></a>
				""",
				"JSX attribute names may not contain whitespace or comments. Attribute names ending with a - and directly followed by another attribute name are merged and must not contain whitespace or comments.");
	}

	@Test
	public void testInvalidAttributeNames_02() throws Exception {
		parseWithError("""
				<a attr -attr></a>
				""",
				"JSX attribute names may not contain whitespace or comments. Attribute names ending with a - and directly followed by another attribute name are merged and must not contain whitespace or comments.");
	}

	@Test
	public void testInvalidAttributeNames_03() throws Exception {
		parseWithError("""
				<a attr-- attr></a>
				""",
				"JSX attribute names may not contain whitespace or comments. Attribute names ending with a - and directly followed by another attribute name are merged and must not contain whitespace or comments.");
	}

	@Test
	public void testInvalidAttributeNames_04() throws Exception {
		parseWithError("""
				<a abcd\\u0065=\"true\"></a>
				""",
				"Illegal character in identifier 'abcd\\u0065' (\\) at position 4.");
	}

	protected Script parseWithError(CharSequence js, String message) throws Exception {
		Script script = parseHelper.parseN4JSX(js);
		List<Diagnostic> errors = script.eResource().getErrors();
		assertFalse(errors.toString(), errors.isEmpty());
		if (!IterableExtensions.exists(errors, err -> Objects.equals(message, err.getMessage()))) {
			assertEquals(errors.toString(), "");
		}
		return script;
	}

}
