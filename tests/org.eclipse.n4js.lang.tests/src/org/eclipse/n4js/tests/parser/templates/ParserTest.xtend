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
package org.eclipse.n4js.tests.parser.templates

import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.TaggedTemplateString
import org.eclipse.n4js.n4JS.TemplateLiteral
import org.eclipse.n4js.n4JS.TemplateSegment
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.eclipse.xtext.diagnostics.AbstractDiagnostic
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic
import org.junit.Test

class ParserTest extends AbstractParserTest {

	@Test
	def void testNoSubst() {
		val script = '`noSubst`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('`noSubst`', segment.rawValue)
		assertEquals('noSubst', segment.value)
	}

	@Test
	def void testSubstBackslash() {
		val script = '`\\\\`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals(segment.value, 1, segment.value.length)
		assertEquals('`\\\\`', segment.rawValue)
		assertEquals('\\', segment.value)
	}

	@Test
	def void testSubstQuote() {
		val script = '`\\\'`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals(segment.value, 1, segment.value.length)
		assertEquals('`\\\'`', segment.rawValue)
		assertEquals('\'', segment.value)
	}

	@Test
	def void testSubstBackslashR() {
		val script = '`\\r`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals(segment.value, 1, segment.value.length)
		assertEquals('`\\r`', segment.rawValue)
		assertEquals('\r', segment.value)
	}

	@Test
	def void testNoSubstSingleDollar() {
		val script = '`$`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('`$`', segment.rawValue)
		assertEquals('$', segment.value)
	}

	@Test
	def void testNoSubstDollars() {
		val script = '`$$ $$`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('`$$ $$`', segment.rawValue)
		assertEquals('$$ $$', segment.value)
	}

	@Test
	def void testNoSubstWithLineBreak() {
		val script = '''
			`no
			 Subst`
		'''.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('`no\n Subst`', segment.rawValue)
		assertEquals('no\n Subst', segment.value)
	}

	@Test
	def void testEmptyExpression() {
		val script = '`${}`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(2, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('`${', first.rawValue)
		assertEquals('', first.value)
		val second = template.segments.last as TemplateSegment
		assertEquals('}`', second.rawValue)
		assertEquals('', second.value)
	}

	@Test
	def void testEmptyExpressionWithText() {
		val script = '`a${}b`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(2, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('`a${', first.rawValue)
		assertEquals('a', first.value)
		val second = template.segments.last as TemplateSegment
		assertEquals('}b`', second.rawValue)
		assertEquals('b', second.value)
	}

	@Test
	def void testEmptyExpressionsWithTextAndSpaces() {
		val script = '` a ${} b ${} c `'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('` a ${', first.rawValue)
		assertEquals(' a ', first.value)
		val second = template.segments.get(1) as TemplateSegment
		assertEquals('} b ${', second.rawValue)
		assertEquals(' b ', second.value)
		val third = template.segments.last as TemplateSegment
		assertEquals('} c `', third.rawValue)
		assertEquals(' c ', third.value)
	}

	@Test
	def void testSubstDollars() {
		val script = '`$${}`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(2, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('`$${', segment.rawValue)
		assertEquals('$', segment.value)
	}

	/* line delimiters are normalized \n */
	@Test
	def void testLineDelimiters() {
		val script = '` \r ${} \r\n ${} \n `'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('` \r ${', first.rawValue)
		assertEquals(' \n ', first.value)
		val second = template.segments.get(1) as TemplateSegment
		assertEquals('} \r\n ${', second.rawValue)
		assertEquals(' \n ', second.value)
		val third = template.segments.last as TemplateSegment
		assertEquals('} \n `', third.rawValue)
		assertEquals(' \n ', third.value)
	}

	@Test
	def void testExpression() {
		val script = '`${true}`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('`${', first.rawValue)
		assertEquals('', first.value)
		val third = template.segments.last as TemplateSegment
		assertEquals('}`', third.rawValue)
		assertEquals('', third.value)
	}

	@Test
	def void testNestedTemplates() {
		val script = '`${`${`a`}`}`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('`${', first.rawValue)
		assertEquals('', first.value)
		val third = template.segments.last as TemplateSegment
		assertEquals('}`', third.rawValue)
		assertEquals('', third.value)

		// nesting level 1
		val template1 = template.segments.get(1) as TemplateLiteral
		assertEquals(3, template1.segments.size)
		val template1_first = template1.segments.head as TemplateSegment
		assertEquals('`${', template1_first.rawValue)
		assertEquals('', template1_first.value)
		val template1_third = template1.segments.last as TemplateSegment
		assertEquals('}`', template1_third.rawValue)
		assertEquals('', template1_third.value)

		// nesting level 2
		val template2 = template1.segments.get(1) as TemplateLiteral
		assertEquals(1, template2.segments.size)
		val template2_first = template2.segments.head as TemplateSegment
		assertEquals('`a`', template2_first.rawValue)
		assertEquals('a', template2_first.value)
	}

	@Test
	def void testObjectLiteral() {
		val script = '`a${{}}b`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('`a${', first.rawValue)
		assertEquals('a', first.value)
		val third = template.segments.last as TemplateSegment
		assertEquals('}b`', third.rawValue)
		assertEquals('b', third.value)
	}

	@Test
	def void testTaggedTemplate_01() {
		val script = 'tag `noSubst`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val tagged = statement.expression as TaggedTemplateString
		val template = tagged.template
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('`noSubst`', segment.rawValue)
		assertEquals('noSubst', segment.value)
	}

	@Test
	def void testSyntaxProblem_01() {
		val script = '`$'.parseN4jsWithError
		val errors = script.eResource.errors
		assertEquals(1, errors.size)
		assertEquals('Template literal is not properly closed by a backtick.', errors.head.message)
	}

	@Test
	def void testSyntaxProblem_02() {
		val script = '`'.parseN4jsWithError
		val errors = script.eResource.errors
		assertEquals(1, errors.size)
		val error =  errors.head as XtextSyntaxDiagnostic
		assertEquals('Template literal is not properly closed by a backtick.', error.message)
		assertEquals(1, error.length)
		assertEquals(1, error.line)
		assertEquals(0, error.offset)
	}

	@Test
	def void testSyntaxProblem_03() {
		val script = '`${
             }'.parseN4jsWithError
		val errors = script.eResource.errors
		assertEquals(1, errors.size)
		val error =  errors.head as XtextSyntaxDiagnostic
		assertEquals('Template literal is not properly closed by a backtick.', error.message)
		assertEquals(1, error.length)
		assertEquals(2, error.line)
	}

	@Test
	def void testSyntaxProblem_04() {
		val script = '`${} '.parseN4jsWithError
		val errors = script.eResource.errors
		assertEquals(1, errors.size)
		val error =  errors.head as XtextSyntaxDiagnostic
		assertEquals('Template literal is not properly closed by a backtick.', error.message)
		assertEquals(2, error.length)
		assertEquals(1, error.line)
		assertEquals(3, error.offset)
	}

	@Test
	def void testSyntaxProblem_05() {
		val script = '`\\'.parseN4jsWithError
		val errors = script.eResource.errors
		assertEquals(1, errors.size)
		val error =  errors.head as XtextSyntaxDiagnostic
		assertEquals(2, error.length)
		assertEquals(1, error.line)
		assertEquals(0, error.offset)
	}

	@Test
	def void testSyntaxProblem_06() {
		val script = '`\\u123`'.parseN4jsWithError
		val errors = script.eResource.errors
		assertEquals(1, errors.size)
		val error =  errors.head as XtextSyntaxDiagnostic
		assertEquals('Bad escapement', error.message)
		assertEquals(7, error.length)
		assertEquals(1, error.line)
		assertEquals(0, error.offset)
	}

	@Test
	def void testContinuation_01() {
		val script = '`\\\r\n`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('`\\\r\n`', segment.rawValue)
		assertEquals('', segment.value)
	}

	@Test
	def void testContinuation_02() {
		val script = '`${}\\\n`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(2, template.segments.size)
		val segment = template.segments.last as TemplateSegment
		assertEquals('}\\\n`', segment.rawValue)
		assertEquals('', segment.value)
	}

	@Test
	def void testOctalEscapeSequenceInStringLiteral_plainJS_nonStrict() {
		// this is the only case in which octal escape sequences are allowed:
		val script = '"hello\\101world"'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val literal = statement.expression as StringLiteral
		assertEquals('"hello\\101world"', literal.rawValue);
		assertEquals('helloAworld', literal.value);
	}

	@Test
	def void testOctalEscapeSequenceInStringLiteral_plainJS_strict() {
		val script = '"use strict"; "hello\\101world"'.parseJSWithError
		val errors = script.eResource.errors
		assertEquals(1, errors.size)
		val error =  errors.head as AbstractDiagnostic
		assertEquals('octal literals and octal escape sequences are not allowed in strict mode.', error.message)
		assertEquals(16, error.length)
		assertEquals(1, error.line)
		assertEquals(14, error.offset)
	}

	@Test
	def void testOctalEscapeSequenceInStringLiteral_n4js() {
		val script = '"hello\\101world"'.parseN4jsWithError
		val errors = script.eResource.errors
		assertEquals(1, errors.size)
		val error =  errors.head as AbstractDiagnostic
		assertEquals('octal literals and octal escape sequences are not allowed in strict mode.', error.message)
		assertEquals(16, error.length)
		assertEquals(1, error.line)
		assertEquals(0, error.offset)
	}

	@Test
	def void testOctalEscapeSequenceInTemplateLiteral() {
		val script = '`hello\\101world`'.parseN4jsWithError
		val errors = script.eResource.errors
		assertEquals(1, errors.size)
		val error =  errors.head as AbstractDiagnostic
		assertEquals('octal literals and octal escape sequences are not allowed in strict mode.', error.message)
		assertEquals(16, error.length)
		assertEquals(1, error.line)
		assertEquals(0, error.offset)
	}
}
