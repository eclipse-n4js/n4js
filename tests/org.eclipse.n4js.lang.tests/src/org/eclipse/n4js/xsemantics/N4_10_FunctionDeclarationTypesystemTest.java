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
package org.eclipse.n4js.xsemantics;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N4_10_FunctionDeclarationTypesystemTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testFunctionDeclarationEmpty() {
		assertTypeOfFunctionDeclaration("""
					function f() {}
				""", "{function():void}", 0);
	}

	@Test
	public void testFunctionDeclarationWithoutParamType() {
		assertTypeOfFunctionDeclaration("""
					function f(i) { return i; }
				""", "{function(any):any}", 0);
	}

	@Test
	public void testFunctionDeclarationWithoutParams() {
		assertTypeOfFunctionDeclaration("""
					function f() { return 0; }
				""", "{function():any}", 0);
		// assertTypeOfFunctionDeclaration("{function():number}", 0) ;// use this after fixing IDE-1049
	}

	@Test
	public void testFunctionDeclarationRecursiveWithoutTypes() {
		assertTypeOfFunctionDeclaration("""
					function f() { return f(); }
				""", "{function():any}", 0);
	}

	@Test
	public void testFunctionDeclarationWithRecursionInFunctionExpression() {
		assertTypeOfFunctionDeclaration("""
					function f() { // function declaration
					    return function() { // function expression
					        return f();
					    }
					}
				""", "{function():any}", 0);
		// assertTypeOfFunctionDeclaration("{function():{function():any}}", 0) ;// use this after fixing IDE-1049
	}

	@Test
	public void testFunctionDeclarationWithRecursionInFunctionExpression2() {
		assertTypeOfFunctionDeclaration("""
					function f(i) { // function declaration
					    return function() { // function expression
					        return f(i);
					    }
					}
				""", "{function(any):any}", 0);
		// assertTypeOfFunctionDeclaration("{function(any):{function():any}}", 0) ;// use this after fixing IDE-1049
	}

	@Test
	public void testFunctionDeclarationWithRecursionInFunctionExpression3() {
		assertTypeOfFunctionDeclaration("""
					function f(i) { // function declaration
					    return function() { // function expression
					        return function() { // function expression
					        	return f(i);
					    	}
					    }
					}
				""", "{function(any):any}", 0);
		// assertTypeOfFunctionDeclaration("{function(any):{function():any}}", 0) ;// use this after fixing IDE-1049
		// we cannot say anything more about the type of the innermost
		// FunctionExpression
	}

	@Test
	public void testFunctionDeclarationWithRecursionInFunctionExpression4() {
		assertTypeOfFunctionDeclaration("""
					function f(i) { // function declaration
					    return function() : {function():number } { // function expression
					        return function() { // function expression inferred as returning any
					        	return f(i);
					    	}
					    }
					}
				""", "{function(any):any}", 1);
		// assertTypeOfFunctionDeclaration("{function(any):{function():{function():number}}}", 1) ;// use this after
		// fixing IDE-1049
		// any is not a subtype of number
	}

	@Test
	public void testFunctionDeclarationWithRecursionInFunctionExpression5() {
		assertTypeOfFunctionDeclaration("""
					function f(i) { // function declaration
					    return function() : {function():number} { // function expression
					        return f(i);
					    }
					}
				""", "{function(any):any}", 1);
		// assertTypeOfFunctionDeclaration("{function(any):{function():{function():number}}}", 1) ;// use this after
		// fixing IDE-1049
		// f(i) in the body hasn't been inferred yet, thus it is considered to return any
	}

	private void assertTypeOfFunctionDeclaration(CharSequence scriptInput, String expectedTypeName,
			int expectedIssues) {
		Script script = createScript(JavaScriptVariant.n4js, scriptInput.toString());
		FunctionDeclaration funcExpr = last(EcoreUtil2.getAllContentsOfType(
				script,
				FunctionDeclaration.class));

		assertTypeName(expectedTypeName, funcExpr);

		List<Issue> issues = valTestHelper.validate(script);
		assertIssueCount(expectedIssues, issues);
	}
}
