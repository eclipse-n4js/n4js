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
package org.eclipse.n4js.xsemantics

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.CommaExpression
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.n4js.ts.utils.TypeCompareHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.validation.IssueCodes

/**
 * Test class for comma expression
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_22_CommaExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

	@Inject extension ValidationTestHelper

	@Inject
	extension TypeCompareHelper

	final static CharSequence scriptPrefix = '''
			class A{}
			class B extends A{}
			var f: boolean;
			var a: A;
			var b: B;
	'''

	def void assertCommaExpressionType(String expectedTypeName, String... expressions) {
		val script = (scriptPrefix + expressions.last+";\n"+expressions.join(",")).parse();

		assertNotNull(script);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val cexpr = (script.scriptElements.reverseView.head as ExpressionStatement).expression;
		val expr = (script.scriptElements.reverseView.tail.head as ExpressionStatement).expression;
		assertTrue(cexpr instanceof CommaExpression)
		val typeExpr = checkedType(G, expr)
		val typePExpr = checkedType(G, cexpr)
		assertEquals(expectedTypeName, typePExpr.typeRefAsString);
		assertEquals("Failed comparing with last expression", typeExpr.typeRefAsString, typePExpr.typeRefAsString);
		assertTrue(typeExpr.typeRefAsString + " is correct, but something went wrong as type is not compared as equivalent", compare(typeExpr, typePExpr)==0);
	}

	@Test
	def void testCommaExpressionType() {
		assertCommaExpressionType("int", '''1''','''2''');
		assertCommaExpressionType("string", '''1''','''2''','''"hello"''');
		assertCommaExpressionType("boolean", '''1''','''"string"''','''2''','''true''');
		assertCommaExpressionType("A", '''1''','''a''');
		assertCommaExpressionType("number", '''1''','''"hello"''','''10-5''');

		assertCommaExpressionType("int", '''"x"''','''2''');
		assertCommaExpressionType("number", '''"x"''','''2.0''');
		assertCommaExpressionType("string", '''"x"''','''"hello"''');
		assertCommaExpressionType("boolean", '''"x"''','''true''');
		assertCommaExpressionType("A", '''"x"''','''a''');
		assertCommaExpressionType("number", '''"x"''','''10-5''');
	}

	@Test
	def void testValidateLoopWithCommas() {
		val script = createScript(JavaScriptVariant.unrestricted,
			'''
			var i,k;
			for (i=0, k=0; i<10; i++, k+=10) {
				var x = i + " * 10 = " + k; // console.log(i + " * 10 = " + k);
			}
			'''
		)
		script.assertNoErrors(N4JSPackage.Literals.N4_MEMBER_DECLARATION, IssueCodes.CLF_DUP_MEMBER);
	}

	@Test
	def void testValidatePlusPlus() {
		val script = createScript(JavaScriptVariant.unrestricted,
		'''
			var i;
			i++
		''')
		script.assertNoErrors(N4JSPackage.Literals.N4_MEMBER_DECLARATION, IssueCodes.CLF_DUP_MEMBER);
	}
}
