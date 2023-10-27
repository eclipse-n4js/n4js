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

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.utils.Log;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * N4JS Spec Test: 6.1.9. Function Calls
 *
 * see IDE-346
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
@Log
public class N6_1_09_CallExpressionTypesystemTest extends AbstractCallExpressionTypesystemTest {

	@Before
	public void prepareScript() {
		assembler.setScriptPrefix(
				"""
						interface A{}
						interface B extends A{}
						interface C extends B{}
						interface D{}
						interface E{}
						""");
	}

	@Test
	public void testNonParameterizedFunctionCallsReturns() {
		assertCall(declare("()"));
		assertCall(declare("(): void"));
		// due to validation for number of parameters, no longer possible
		// assertCall(declare("(A a): void"));
		assertCall(declare("(): A"));
		assertCall(declare("(): B").assignTo("A"));
		assertCall(declare("(): C").assignTo("A"));
		assertCall(declare("(): B").assignTo("union{A,B}"));
	}

	@Test
	public void testNonParameterizedFunctionCallDefaultReturn() {
		assertCall(declare("()").assignTo("any"));
	}

	@Test
	public void testNonParameterizedFunctionCallsReturnsWithError() {
		assertCall(declare("():A").assignTo("B"), 1);
		assertCall(declare("():void").assignTo("A"), 1);
		assertCall(declare("()").assignTo("A"), 1);
	}

	@Test
	public void testNonParameterizedFunctionNoReturn() {
		assertCall(declare("(a: A)").invokeWith("A"));
		assertCall(declare("(a: A)").invokeWith("B"));
		assertCall(declare("(a: A)").invokeWith("C"));
		assertCall(declare("(u: union{A,B})").invokeWith("A"));
		assertCall(declare("(u: union{A,B})").invokeWith("B"));
	}

	@Test
	public void testNonParameterizedFunctionCalls() {
		assertCall(declare("(a: A):A").invokeWith("A").assignTo("A"));
		assertCall(declare("(a: A):B").invokeWith("A").assignTo("A"));
		assertCall(declare("(a: A):B").invokeWith("B").assignTo("A"));
		assertCall(declare("(a: A, u: union{A,B}):B").invokeWith("A", "A").assignTo("A"));
	}

	@Test
	public void testNonParameterizedFunctionCallsWithError() {
		assertCall(declare("(b: B):A").invokeWith("A").assignTo("A"), 1);
		assertCall(declare("(b: B, c: C):A").invokeWith("C", "D").assignTo("A"), 1);
		assertCall(declare("(b: B, c: C):A").invokeWith("A", "D").assignTo("A"), 2);
		assertCall(declare("(b: B):A").invokeWith("A").assignTo("B"), 2);
	}

	@Test
	public void testParameterizedFunctionCallsReturnsDefaultAny() {
		assertCall(declare("(): T").genericWith("T").inferredTo("any"));
	}

	@Test
	public void testParameterizedFunctionCallsReturnsWithTypeArgs() {
		assertCall(declare("(): T").genericWith("T").parameterizedWith("A").inferredTo("A"));
	}

	@Test
	public void testParameterizedFunctionCallsReturnsWithTypeHintFromAssignment() {
		assertCall(declare("(): T").genericWith("T").assignTo("A").inferredTo("A"));
	}

	@Test
	public void testParameterizedFunctionCallWithTypeHintFromArgument() {
		assertCall(declare("(t: T)").genericWith("T").invokeWith("A").inferredTo("A"));
	}

	@Test
	public void testParameterizedFunctionCallsReturnsWithTypeHintFromArgument() {
		assertCall(declare("(t: T): T").genericWith("T").invokeWith("A").inferredTo("A", "A"));
	}

}
