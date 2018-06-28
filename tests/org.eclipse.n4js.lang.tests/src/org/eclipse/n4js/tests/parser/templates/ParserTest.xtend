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
import org.eclipse.n4js.n4JS.TemplateLiteral
import org.eclipse.n4js.n4JS.TemplateSegment
import org.eclipse.n4js.tests.parser.AbstractParserTest
import org.junit.Test
import org.eclipse.n4js.n4JS.TaggedTemplateString
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic

class ParserTest extends AbstractParserTest {

	@Test
	def void testNoSubst() {
		val script = '`noSubst`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('noSubst', segment.rawValue)
	}

	@Test
	def void testNoSubstBackslash() {
		val script = '`\\\\`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals(segment.rawValue, 2, segment.rawValue.length)
		assertEquals('\\\\', segment.rawValue)
	}

	@Test
	def void testNoSubstQuote() {
		val script = '`\\\'`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals(segment.rawValue, 2, segment.rawValue.length)
		assertEquals('\\\'', segment.rawValue)
	}

	@Test
	def void testNoSubstBackslashR() {
		val script = '`\\r`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals(segment.rawValue, 2, segment.rawValue.length)
		assertEquals('\\r', segment.rawValue)
	}

	@Test
	def void testNoSubstSingleDollar() {
		val script = '`$`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('$', segment.rawValue)
	}

	@Test
	def void testNoSubstDollars() {
		val script = '`$$ $$`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('$$ $$', segment.rawValue)
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
		assertEquals('no\n Subst', segment.rawValue)
	}

	@Test
	def void testEmptyExpression() {
		val script = '`${}`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(2, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('', first.rawValue)
		val second = template.segments.last as TemplateSegment
		assertEquals('', second.rawValue)
	}

	@Test
	def void testEmptyExpressionWithText() {
		val script = '`a${}b`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(2, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('a', first.rawValue)
		val second = template.segments.last as TemplateSegment
		assertEquals('b', second.rawValue)
	}

	@Test
	def void testEmptyExpressionsWithTextAndSpaces() {
		val script = '` a ${} b ${} c `'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals(' a ', first.rawValue)
		val second = template.segments.get(1) as TemplateSegment
		assertEquals(' b ', second.rawValue)
		val third = template.segments.last as TemplateSegment
		assertEquals(' c ', third.rawValue)
	}

	@Test
	def void testSubstDollars() {
		val script = '`$${}`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(2, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('$', segment.rawValue)
	}

	/* line delimiters are normalized \n */
	@Test
	def void testLineDelimiters() {
		val script = '` \r ${} \r\n ${} \n `'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals(' \n ', first.rawValue)
		val second = template.segments.get(1) as TemplateSegment
		assertEquals(' \n ', second.rawValue)
		val third = template.segments.last as TemplateSegment
		assertEquals(' \n ', third.rawValue)
	}

	@Test
	def void testExpression() {
		val script = '`${true}`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('', first.rawValue)
		val third = template.segments.last as TemplateSegment
		assertEquals('', third.rawValue)
	}

	@Test
	def void testNestedTemplates() {
		val script = '`${`${`a`}`}`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('', first.rawValue)
		val third = template.segments.last as TemplateSegment
		assertEquals('', third.rawValue)
	}

	@Test
	def void testObjectLiteral() {
		val script = '`a${{}}b`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(3, template.segments.size)
		val first = template.segments.head as TemplateSegment
		assertEquals('a', first.rawValue)
		val third = template.segments.last as TemplateSegment
		assertEquals('b', third.rawValue)
	}

	@Test
	def void testTaggedTemplate_01() {
		val script = 'tag `noSubst`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val tagged = statement.expression as TaggedTemplateString
		val template = tagged.template
		assertEquals(1, template.segments.size)
		val segment = template.segments.head as TemplateSegment
		assertEquals('noSubst', segment.rawValue)
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
		assertEquals('\\\n', segment.rawValue)
	}

	@Test
	def void testContinuation_02() {
		val script = '`${}\\\n`'.parseJSSuccessfully
		val statement = script.scriptElements.head as ExpressionStatement
		val template = statement.expression as TemplateLiteral
		assertEquals(2, template.segments.size)
		val segment = template.segments.last as TemplateSegment
		assertEquals('\\\n', segment.rawValue)
	}

}
