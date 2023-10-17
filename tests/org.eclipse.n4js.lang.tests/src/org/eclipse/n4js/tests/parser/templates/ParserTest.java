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
package org.eclipse.n4js.tests.parser.templates;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.TemplateSegment;
import org.eclipse.n4js.tests.parser.AbstractParserTest;
import org.eclipse.xtext.diagnostics.AbstractDiagnostic;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.junit.Test;

public class ParserTest extends AbstractParserTest {

	@Test
	public void testNoSubst() {
		Script script = parseJSSuccessfully("`noSubst`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(1, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`noSubst`", segment.getRawValue());
		assertEquals("noSubst", segment.getValue());
	}

	@Test
	public void testSubstBackslash() {
		Script script = parseJSSuccessfully("`\\\\`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(1, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals(segment.getValue(), 1, segment.getValue().length());
		assertEquals("`\\\\`", segment.getRawValue());
		assertEquals("\\", segment.getValue());
	}

	@Test
	public void testSubstQuote() {
		Script script = parseJSSuccessfully("`\\\'`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(1, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals(segment.getValue(), 1, segment.getValue().length());
		assertEquals("`\\\'`", segment.getRawValue());
		assertEquals("\'", segment.getValue());
	}

	@Test
	public void testSubstBackslashR() {
		Script script = parseJSSuccessfully("`\\r`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(1, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals(segment.getValue(), 1, segment.getValue().length());
		assertEquals("`\\r`", segment.getRawValue());
		assertEquals("\r", segment.getValue());
	}

	@Test
	public void testNoSubstSingleDollar() {
		Script script = parseJSSuccessfully("`$`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(1, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`$`", segment.getRawValue());
		assertEquals("$", segment.getValue());
	}

	@Test
	public void testNoSubstDollars() {
		Script script = parseJSSuccessfully("`$$ $$`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(1, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`$$ $$`", segment.getRawValue());
		assertEquals("$$ $$", segment.getValue());
	}

	@Test
	public void testNoSubstWithLineBreak() {
		Script script = parseJSSuccessfully("""
				`no
				 Subst`
				""");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(1, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`no\n Subst`", segment.getRawValue());
		assertEquals("no\n Subst", segment.getValue());
	}

	@Test
	public void testEmptyExpression() {
		Script script = parseJSSuccessfully("`${}`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(2, template.getSegments().size());
		TemplateSegment first = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`${", first.getRawValue());
		assertEquals("", first.getValue());
		TemplateSegment second = (TemplateSegment) last(template.getSegments());
		assertEquals("}`", second.getRawValue());
		assertEquals("", second.getValue());
	}

	@Test
	public void testEmptyExpressionWithText() {
		Script script = parseJSSuccessfully("`a${}b`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(2, template.getSegments().size());
		TemplateSegment first = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`a${", first.getRawValue());
		assertEquals("a", first.getValue());
		TemplateSegment second = (TemplateSegment) last(template.getSegments());
		assertEquals("}b`", second.getRawValue());
		assertEquals("b", second.getValue());
	}

	@Test
	public void testEmptyExpressionsWithTextAndSpaces() {
		Script script = parseJSSuccessfully("` a ${} b ${} c `");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(3, template.getSegments().size());
		TemplateSegment first = (TemplateSegment) template.getSegments().get(0);
		assertEquals("` a ${", first.getRawValue());
		assertEquals(" a ", first.getValue());
		TemplateSegment second = (TemplateSegment) template.getSegments().get(1);
		assertEquals("} b ${", second.getRawValue());
		assertEquals(" b ", second.getValue());
		TemplateSegment third = (TemplateSegment) last(template.getSegments());
		assertEquals("} c `", third.getRawValue());
		assertEquals(" c ", third.getValue());
	}

	@Test
	public void testSubstDollars() {
		Script script = parseJSSuccessfully("`$${}`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(2, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`$${", segment.getRawValue());
		assertEquals("$", segment.getValue());
	}

	/* line delimiters are normalized \n */
	@Test
	public void testLineDelimiters() {
		Script script = parseJSSuccessfully("` \r ${} \r\n ${} \n `");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(3, template.getSegments().size());
		TemplateSegment first = (TemplateSegment) template.getSegments().get(0);
		assertEquals("` \r ${", first.getRawValue());
		assertEquals(" \n ", first.getValue());
		TemplateSegment second = (TemplateSegment) template.getSegments().get(1);
		assertEquals("} \r\n ${", second.getRawValue());
		assertEquals(" \n ", second.getValue());
		TemplateSegment third = (TemplateSegment) last(template.getSegments());
		assertEquals("} \n `", third.getRawValue());
		assertEquals(" \n ", third.getValue());
	}

	@Test
	public void testExpression() {
		Script script = parseJSSuccessfully("`${true}`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(3, template.getSegments().size());
		TemplateSegment first = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`${", first.getRawValue());
		assertEquals("", first.getValue());
		TemplateSegment third = (TemplateSegment) last(template.getSegments());
		assertEquals("}`", third.getRawValue());
		assertEquals("", third.getValue());
	}

	@Test
	public void testNestedTemplates() {
		Script script = parseJSSuccessfully("`${`${`a`}`}`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(3, template.getSegments().size());
		TemplateSegment first = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`${", first.getRawValue());
		assertEquals("", first.getValue());
		TemplateSegment third = (TemplateSegment) last(template.getSegments());
		assertEquals("}`", third.getRawValue());
		assertEquals("", third.getValue());

		// nesting level 1
		TemplateLiteral template1 = (TemplateLiteral) template.getSegments().get(1);
		assertEquals(3, template1.getSegments().size());
		TemplateSegment template1_first = (TemplateSegment) template1.getSegments().get(0);
		assertEquals("`${", template1_first.getRawValue());
		assertEquals("", template1_first.getValue());
		TemplateSegment template1_third = (TemplateSegment) last(template1.getSegments());
		assertEquals("}`", template1_third.getRawValue());
		assertEquals("", template1_third.getValue());

		// nesting level 2
		TemplateLiteral template2 = (TemplateLiteral) template1.getSegments().get(1);
		assertEquals(1, template2.getSegments().size());
		TemplateSegment template2_first = (TemplateSegment) template2.getSegments().get(0);
		assertEquals("`a`", template2_first.getRawValue());
		assertEquals("a", template2_first.getValue());
	}

	@Test
	public void testObjectLiteral() {
		Script script = parseJSSuccessfully("`a${{}}b`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(3, template.getSegments().size());
		TemplateSegment first = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`a${", first.getRawValue());
		assertEquals("a", first.getValue());
		TemplateSegment third = (TemplateSegment) last(template.getSegments());
		assertEquals("}b`", third.getRawValue());
		assertEquals("b", third.getValue());
	}

	@Test
	public void testTaggedTemplate_01() {
		Script script = parseJSSuccessfully("tag `noSubst`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TaggedTemplateString tagged = (TaggedTemplateString) statement.getExpression();
		TemplateLiteral template = tagged.getTemplate();
		assertEquals(1, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`noSubst`", segment.getRawValue());
		assertEquals("noSubst", segment.getValue());
	}

	@Test
	public void testSyntaxProblem_01() {
		Script script = parseN4jsWithError("`$");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		assertEquals("Template literal is not properly closed by a backtick.", errors.get(0).getMessage());
	}

	@Test
	public void testSyntaxProblem_02() {
		Script script = parseN4jsWithError("`");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) errors.get(0);
		assertEquals("Template literal is not properly closed by a backtick.", error.getMessage());
		assertEquals(1, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(0, error.getOffset());
	}

	@Test
	public void testSyntaxProblem_03() {
		Script script = parseN4jsWithError("""
				`${
				   }""");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) errors.get(0);
		assertEquals("Template literal is not properly closed by a backtick.", error.getMessage());
		assertEquals(1, error.getLength());
		assertEquals(2, error.getLine());
	}

	@Test
	public void testSyntaxProblem_04() {
		Script script = parseN4jsWithError("`${} ");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) errors.get(0);
		assertEquals("Template literal is not properly closed by a backtick.", error.getMessage());
		assertEquals(2, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(3, error.getOffset());
	}

	@Test
	public void testSyntaxProblem_05() {
		Script script = parseN4jsWithError("`\\");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) errors.get(0);
		assertEquals(2, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(0, error.getOffset());
	}

	@Test
	public void testSyntaxProblem_06() {
		Script script = parseN4jsWithError("`\\u123`");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) errors.get(0);
		assertEquals("Bad escapement", error.getMessage());
		assertEquals(7, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(0, error.getOffset());
	}

	@Test
	public void testContinuation_01() {
		Script script = parseJSSuccessfully("`\\\r\n`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(1, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) template.getSegments().get(0);
		assertEquals("`\\\r\n`", segment.getRawValue());
		assertEquals("", segment.getValue());
	}

	@Test
	public void testContinuation_02() {
		Script script = parseJSSuccessfully("`${}\\\n`");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		TemplateLiteral template = (TemplateLiteral) statement.getExpression();
		assertEquals(2, template.getSegments().size());
		TemplateSegment segment = (TemplateSegment) last(template.getSegments());
		assertEquals("}\\\n`", segment.getRawValue());
		assertEquals("", segment.getValue());
	}

	@Test
	public void testOctalEscapeSequenceInStringLiteral_plainJS_nonStrict() {
		// this is the only case in which octal escape sequences are allowed:
		Script script = parseJSSuccessfully("\"hello\\101world\"");
		ExpressionStatement statement = (ExpressionStatement) script.getScriptElements().get(0);
		StringLiteral literal = (StringLiteral) statement.getExpression();
		assertEquals("\"hello\\101world\"", literal.getRawValue());
		assertEquals("helloAworld", literal.getValue());
	}

	@Test
	public void testOctalEscapeSequenceInStringLiteral_plainJS_strict() {
		Script script = parseJSWithError("\"use strict\"; \"hello\\101world\"");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		AbstractDiagnostic error = (AbstractDiagnostic) errors.get(0);
		assertEquals("octal literals and octal escape sequences are not allowed in strict mode.", error.getMessage());
		assertEquals(16, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(14, error.getOffset());
	}

	@Test
	public void testOctalEscapeSequenceInStringLiteral_n4js() {
		Script script = parseN4jsWithError("\"hello\\101world\"");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		AbstractDiagnostic error = (AbstractDiagnostic) errors.get(0);
		assertEquals("octal literals and octal escape sequences are not allowed in strict mode.", error.getMessage());
		assertEquals(16, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(0, error.getOffset());
	}

	@Test
	public void testOctalEscapeSequenceInTemplateLiteral() {
		Script script = parseN4jsWithError("`hello\\101world`");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		AbstractDiagnostic error = (AbstractDiagnostic) errors.get(0);
		assertEquals("octal literals and octal escape sequences are not allowed in strict mode.", error.getMessage());
		assertEquals(16, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(0, error.getOffset());
	}
}
