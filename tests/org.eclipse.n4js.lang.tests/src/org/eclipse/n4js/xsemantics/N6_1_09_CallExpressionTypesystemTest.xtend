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

import org.eclipse.n4js.utils.Log
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 * N4JS Spec Test: 6.1.9. Function Calls
 *
 * @see IDE-346
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
@Log
class N6_1_09_CallExpressionTypesystemTest extends AbstractCallExpressionTypesystemTest {

	@Before
	def prepareScript() {
		assembler.setScriptPrefix(
			'''
				interface A{}
				interface B extends A{}
				interface C extends B{}
				interface D{}
				interface E{}
			''')
	}

	@Test
	def void testNonParameterizedFunctionCallsReturns() {
		declare("()").assertCall();
		declare("(): void").assertCall();
		//due to validation for number of parameters, no longer possible
//		declare("(A a): void").assertCall();
		declare("(): A").assertCall();
		declare("(): B").assignTo("A").assertCall();
		declare("(): C").assignTo("A").assertCall();
		declare("(): B").assignTo("union{A,B}").assertCall();
	}

	@Test
	def void testNonParameterizedFunctionCallDefaultReturn() {
		declare("()").assignTo("any").assertCall();
	}

	@Test
	def void testNonParameterizedFunctionCallsReturnsWithError() {
		declare("():A").assignTo("B").assertCall(1);
		declare("():void").assignTo("A").assertCall(1);
		declare("()").assignTo("A").assertCall(1);
	}

	@Test
	def void testNonParameterizedFunctionNoReturn() {
		declare("(a: A)").invokeWith("A").assertCall();
		declare("(a: A)").invokeWith("B").assertCall();
		declare("(a: A)").invokeWith("C").assertCall();
		declare("(u: union{A,B})").invokeWith("A").assertCall();
		declare("(u: union{A,B})").invokeWith("B").assertCall();
	}

	@Test
	def void testNonParameterizedFunctionCalls() {
		declare("(a: A):A").invokeWith("A").assignTo("A").assertCall();
		declare("(a: A):B").invokeWith("A").assignTo("A").assertCall();
		declare("(a: A):B").invokeWith("B").assignTo("A").assertCall();
		declare("(a: A, u: union{A,B}):B").invokeWith("A", "A").assignTo("A").assertCall();
	}

	@Test
	def void testNonParameterizedFunctionCallsWithError() {
		declare("(b: B):A").invokeWith("A").assignTo("A").assertCall(1);
		declare("(b: B, c: C):A").invokeWith("C", "D").assignTo("A").assertCall(1);
		declare("(b: B, c: C):A").invokeWith("A", "D").assignTo("A").assertCall(2);
		declare("(b: B):A").invokeWith("A").assignTo("B").assertCall(2);
	}

	@Test
	def void testParameterizedFunctionCallsReturnsDefaultAny() {
		declare("(): T").genericWith("T").inferredTo("any").assertCall();
	}

	@Test
	def void testParameterizedFunctionCallsReturnsWithTypeArgs() {
		declare("(): T").genericWith("T").parameterizedWith("A").inferredTo("A").assertCall();
	}

	@Test
	def void testParameterizedFunctionCallsReturnsWithTypeHintFromAssignment() {
		declare("(): T").genericWith("T").assignTo("A").inferredTo("A").assertCall();
	}

	@Test
	def void testParameterizedFunctionCallWithTypeHintFromArgument() {
		declare("(t: T)").genericWith("T").invokeWith("A").inferredTo("A").assertCall();
	}

	@Test
	def void testParameterizedFunctionCallsReturnsWithTypeHintFromArgument() {
		declare("(t: T): T").genericWith("T").invokeWith("A").inferredTo("A", "A").assertCall();
	}

}
