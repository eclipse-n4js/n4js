/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.parser;

import static org.eclipse.n4js.utils.Strings.join;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class ExpressionWithTargetParserTest extends AbstractParserTest {

	@Test
	public void testCallExpression_01() {
		parseJSSuccessfully("""
					a(b);
				""");
	}

	@Test
	public void testCallExpression_02() {
		parseJSSuccessfully("""
					a(b)()
				""");
	}

	@Test
	public void testPropertyAccess_01() {
		parseJSSuccessfully("""
					a.b
				""");
	}

	@Test
	public void testPropertyAccess_02() {
		parseJSSuccessfully("""
					a.b.c
				""");
	}

	@Test
	public void testIndexAccess_01() {
		parseJSSuccessfully("""
					a[0]
				""");
	}

	@Test
	public void testIndexAccess_02() {
		parseJSSuccessfully("""
					a[0][1]
				""");
	}

	@Test
	public void testTemplateLiteral_01() {
		parseJSSuccessfully("""
					a`raw`
				""");
	}

	@Test
	public void testTemplateLiteral_02() {
		parseJSSuccessfully("""
					a`raw``raw`
				""");
	}

	@Test
	public void testOptionalCallExpression_01() {
		parseJSSuccessfully("""
					a?.(b);
				""");
	}

	@Test
	public void testOptionalCallExpression_02() {
		parseJSSuccessfully("""
					a?.(b)?.()
				""");
	}

	@Test
	public void testOptionalPropertyAccess_01() {
		parseJSSuccessfully("""
					a?.b
				""");
	}

	@Test
	public void testOptionalPropertyAccess_02() {
		parseJSSuccessfully("""
					a?.b?.c
				""");
	}

	@Test
	public void testOptionalIndexAccess_01() {
		parseJSSuccessfully("""
					a?.[0]
				""");
	}

	@Test
	public void testOptionalIndexAccess_02() {
		parseJSSuccessfully("""
					a?.[0]?.[1]
				""");
	}

	@Test
	public void testOptionalTemplateLiteral_01() {
		parseJSSuccessfully("""
					a?.`raw`
				""");
	}

	@Test
	public void testOptionalTemplateLiteral_02() {
		parseJSSuccessfully("""
					a?.`raw`?.`raw`
				""");
	}

	@Test
	public void testDisambiguateFromTernary_01() {
		Script parsed = parseJSSuccessfully("""
					a?.1:2
				""");

		ExpressionStatement expressionStmt = (ExpressionStatement) parsed.getScriptElements().get(0);
		assertTrue(expressionStmt.getExpression() instanceof ConditionalExpression);
	}

	@Test
	public void testDisambiguateFromTernary_02() {
		Script parsed = parseJSWithError("""
					a?. 0 : 2
				""");
		ExpressionStatement expressionStmt = (ExpressionStatement) parsed.getScriptElements().get(0);
		assertTrue(expressionStmt.getExpression() instanceof ParameterizedCallExpression);
	}

	@Test
	public void testDisallowOnLHSOfAssignment_01() throws Exception {
		Script script = parseHelper.parse("""
					c?.field = ""
				""");
		String errors = join("\n", Diagnostic::getMessage, script.eResource().getErrors());
		assertEquals("Invalid assignment left-hand side.", errors);
	}

	@Test
	public void testDisallowOnLHSOfAssignment_02() throws Exception {
		Script script = parseHelper.parse("""
					c?.c.field = ""
				""");
		String errors = join("\n", Diagnostic::getMessage, script.eResource().getErrors());
		assertEquals("Invalid assignment left-hand side.", errors);
	}

	@Test
	public void testDisallowOnLHSOfAssignment_03() throws Exception {
		Script script = parseHelper.parse("""
					c?.['field'] = ""
				""");
		String errors = join("\n", Diagnostic::getMessage, script.eResource().getErrors());
		assertEquals("Invalid assignment left-hand side.", errors);
	}

	@Test
	public void testDisallowOnLHSOfAssignment_04() throws Exception {
		Script script = parseHelper.parse("""
					c?.['c']['field'] = ""
				""");
		String errors = join("\n", Diagnostic::getMessage, script.eResource().getErrors());
		assertEquals("Invalid assignment left-hand side.", errors);
	}

	@Test
	public void testDisallowOnLHSOfAssignment_05() {
		// not really useful, but allowed according to the rules of
		// breaking long short-circuiting with parentheses:
		parseJSSuccessfully("""
					(c?.c).field = "okay"
				""");
	}
}
