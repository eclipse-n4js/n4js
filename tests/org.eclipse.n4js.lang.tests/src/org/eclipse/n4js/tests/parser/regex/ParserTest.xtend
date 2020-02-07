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
package org.eclipse.n4js.tests.parser.regex

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.regex.tests.AbstractParserTests
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.runner.RunWith
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.RegularExpressionLiteral
import org.junit.Test
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic
import org.eclipse.n4js.validation.IssueCodes

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ParserTest extends AbstractParserTests {

	@Inject extension ParseHelper<Script>

	override assertValid(CharSequence expression) {
		val parsed = expression.parse
		val errors = parsed.eResource.errors
		assertTrue(errors.toString, errors.isEmpty)
		val expressionStatement = parsed.scriptElements.head as ExpressionStatement
		val regEx = expressionStatement.expression as RegularExpressionLiteral
		assertEquals(expression.toString.trim, regEx.value)
	}
	
	@Test
	def void testRegExCaptureGroupWarning_01() {
		val script = '/(?<abc>)/'.parse
		val errors = script.eResource.warnings
		assertEquals(1, errors.size)
		val error =  errors.head as XtextSyntaxDiagnostic
		assertEquals(IssueCodes.messageForVCO_REGEX_NAMED_GROUP, error.message)
		assertEquals(3, error.length)
		assertEquals(1, error.line)
		assertEquals(4, error.offset)
	}
	
	@Test
	def void testRegExCaptureGroupWarning_02() {
		val script = '/(?<aa>)(?<bb>)/'.parse
		val errors = script.eResource.warnings
		assertEquals(2, errors.size)
		val error =  errors.last as XtextSyntaxDiagnostic
		assertEquals(IssueCodes.messageForVCO_REGEX_NAMED_GROUP, error.message)
		assertEquals(2, error.length)
		assertEquals(1, error.line)
		assertEquals(11, error.offset)
	}
	
	@Test
	def void testRegExCaptureGroupWarning_03() {
		val script = '/abc(def/'.parse
		val errors = script.eResource.warnings
		assertEquals(1, errors.size)
		val error =  errors.head as XtextSyntaxDiagnostic
		assertEquals("missing ')' at '/'", error.message)
		assertEquals(1, error.length)
		assertEquals(1, error.line)
		assertEquals(8, error.offset)
	}
}
