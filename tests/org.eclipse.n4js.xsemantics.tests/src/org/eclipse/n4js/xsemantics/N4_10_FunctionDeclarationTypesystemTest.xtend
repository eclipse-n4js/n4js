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
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N4_10_FunctionDeclarationTypesystemTest extends AbstractTypesystemTest {

	@Inject extension ValidationTestHelper

	@Test
	def void testFunctionDeclarationEmpty() {
		'''
			function f() {}
		'''.
		assertTypeOfFunctionDeclaration("{function():void}", 0)
	}

	@Test
	def void testFunctionDeclarationWithoutParamType() {
		'''
			function f(i) { return i; }
		'''.
		assertTypeOfFunctionDeclaration("{function(any):any}", 0)
	}

	@Test
	def void testFunctionDeclarationWithoutParams() {
		'''
			function f() { return 0; }
		'''.
		assertTypeOfFunctionDeclaration("{function():any}", 0)
//		assertTypeOfFunctionDeclaration("{function():number}", 0) // use this after fixing IDE-1049
	}

	@Test
	def void testFunctionDeclarationRecursiveWithoutTypes() {
		'''
			function f() { return f(); }
		'''.
		assertTypeOfFunctionDeclaration("{function():any}", 0)
	}

	@Test
	def void testFunctionDeclarationWithRecursionInFunctionExpression() {
		'''
			function f() { // function declaration
			    return function() { // function expression
			        return f();
			    }
			}
		'''.
		assertTypeOfFunctionDeclaration("{function():any}", 0)
//		assertTypeOfFunctionDeclaration("{function():{function():any}}", 0) // use this after fixing IDE-1049
	}

	@Test
	def void testFunctionDeclarationWithRecursionInFunctionExpression2() {
		'''
			function f(i) { // function declaration
			    return function() { // function expression
			        return f(i);
			    }
			}
		'''.
		assertTypeOfFunctionDeclaration("{function(any):any}", 0)
//		assertTypeOfFunctionDeclaration("{function(any):{function():any}}", 0) // use this after fixing IDE-1049
	}

	@Test
	def void testFunctionDeclarationWithRecursionInFunctionExpression3() {
		'''
			function f(i) { // function declaration
			    return function() { // function expression
			        return function() { // function expression
			        	return f(i);
			    	}
			    }
			}
		'''.
		assertTypeOfFunctionDeclaration("{function(any):any}", 0)
//		assertTypeOfFunctionDeclaration("{function(any):{function():any}}", 0) // use this after fixing IDE-1049
		// we cannot say anything more about the type of the innermost
		// FunctionExpression
	}

	@Test
	def void testFunctionDeclarationWithRecursionInFunctionExpression4() {
		'''
			function f(i) { // function declaration
			    return function() : {function():number } { // function expression
			        return function() { // function expression inferred as returning any
			        	return f(i);
			    	}
			    }
			}
		'''.
		assertTypeOfFunctionDeclaration("{function(any):any}", 1)
//		assertTypeOfFunctionDeclaration("{function(any):{function():{function():number}}}", 1) // use this after fixing IDE-1049
		// any is not a subtype of number
	}

	@Test
	def void testFunctionDeclarationWithRecursionInFunctionExpression5() {
		'''
			function f(i) { // function declaration
			    return function() : {function():number} { // function expression
			        return f(i);
			    }
			}
		'''.
		assertTypeOfFunctionDeclaration("{function(any):any}", 1)
//		assertTypeOfFunctionDeclaration("{function(any):{function():{function():number}}}", 1) // use this after fixing IDE-1049
		// f(i) in the body hasn't been inferred yet, thus it is considered to return any
	}

	def private void assertTypeOfFunctionDeclaration(CharSequence scriptInput, String expectedTypeName, int expectedIssues) {
		val script = createScript(JavaScriptVariant.n4js, scriptInput.toString)
		val funcExpr = EcoreUtil2.getAllContentsOfType(
			script,
			FunctionDeclaration
		).last

		assertTypeName(expectedTypeName, funcExpr);

		val issues = script.validate();
		assertIssueCount(expectedIssues, issues);
	}
}
