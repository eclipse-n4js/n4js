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
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.diagnostics.Severity
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class N7_3_2_ExportStatementOfVariablesTest extends N7_1_1_VariablesTest {

	@Inject
	extension ValidationTestHelper

	override assertVarDeclarationType(String expectedTypeAsString, String varDeclaration) {
		val scriptSrc = '''export var «varDeclaration»;''';
		val script = createScript(JavaScriptVariant.n4js, scriptSrc);
		val issues = script.validate();
		assertEquals("Bogus test, expected no validation errors in: " + scriptSrc + "\n" + issues.map[message].join("\n"), 0, issues.filter[it.severity!=Severity.WARNING].size)
		val varDecl = ((script.scriptElements.head as ExportDeclaration).exportedElement as VariableStatement).varDecl.head
		assertNotNull("Bogus test, variable declaration not found", varDecl);

		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		var typeOfVariableResult = ts.type(G, varDecl)
		assertNotNull("Cannot compute type of "+varDeclaration+":",typeOfVariableResult)
		assertFalse("Cannot compute type of "+varDeclaration+":"+typeOfVariableResult, typeOfVariableResult instanceof UnknownTypeRef)
		assertEquals(expectedTypeAsString, typeOfVariableResult.typeRefAsString)
	}

	@Test
	def testUseExportedVariable() {
		val scriptSrc = '''
		export var a = "x";
		var b = a;'''
		val script = createScript(JavaScriptVariant.n4js, scriptSrc);
		val issues = script.validate();
		assertEquals("Bogus test, expected no validation errors in: " + scriptSrc + "\n" + issues.map[message].join("\n"), 0, issues.size)
	}

	@Test
	def testUseExportedVariable2() {
		val scriptSrc = '''
		export var a;
		var b = a;'''
		val script = createScript(JavaScriptVariant.n4js, scriptSrc);
		val issues = script.validate();
		assertEquals("Bogus test, expected no validation errors in: " + scriptSrc + "\n" + issues.map[message].join("\n"), 0, issues.size)
	}

	// more tests: see N7_1_1_VariablesTest

}
