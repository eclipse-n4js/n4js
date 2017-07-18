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
package org.eclipse.n4js.tests.parser

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.BooleanLiteral
import org.eclipse.n4js.n4JS.MultiplicativeExpression
import org.eclipse.n4js.n4JS.NullLiteral
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.RegularExpressionLiteral
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.NumericLiteral

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(N4JSInjectorProvider))
class ES_07_08_LiteralsTest {

	@Inject
	extension ParseHelper<Script>

	@Test
	def void testLiterals() {
		val program = '''
			var string = "Hello";
			var num = 1;
			var bool = true;
			var nullL = null;
			var regexp = /someregex/;
			var division = 12 / 5;
		'''.parse

		assertTrue(program.eResource.errors.toString, program.eResource.errors.empty)

		val varDecls = program.eAllContents.filter(VariableDeclaration)
		assertType(StringLiteral, varDecls.findFirst[name=="string"].expression)
		assertType(NumericLiteral, varDecls.findFirst[name=="num"].expression)
		assertType(BooleanLiteral, varDecls.findFirst[name=="bool"].expression)
		assertType(NullLiteral, varDecls.findFirst[name=="nullL"].expression)
		assertType(RegularExpressionLiteral, varDecls.findFirst[name=="regexp"].expression)
		assertType(MultiplicativeExpression, varDecls.findFirst[name=="division"].expression)
	}

	def assertType(Class<?> type, Expression expression) {
		assertNotNull("Expected type " + type.name + ", was null", expression)
		assertTrue("Expected type " + type.name + ", was " + expression.eClass.name,
			type.isInstance(expression)
		)
	}

}
