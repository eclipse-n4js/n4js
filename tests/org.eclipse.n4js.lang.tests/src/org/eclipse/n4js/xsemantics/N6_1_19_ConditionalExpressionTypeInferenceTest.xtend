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
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

/*
 * Tests for judgment type, see n4js.xsemantics for judgment, axiom and rules.
 * Tests and rules are ordered according to N4JS spec
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_19_ConditionalExpressionTypeInferenceTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

	/*
	 * @see [N4JS] 6.1.19 Conditional Expression
	 */
	@Test
	def void testConditional() {
		"a : b".assertTypeNameOfConditional("A")
		"a : c".assertTypeNameOfConditional("union{A,C}")
		"b : d".assertTypeNameOfConditional("union{B,D}")
	}

	@Test
	def void testConditionalLinearizationWithSingleType() {
		"a : a".assertTypeNameOfConditional("A")
	}

	@Test
	def void testConditionalLinearizationWithNestedUnionTypes() {
		"(true ? a : b) : (true ? c : d)".assertTypeNameOfConditional("union{A,C,D}")
	}

	@Test
	def void testConditionalLinearizationWithNestedUnionTypesAndSimplification() {
		"(true ? a : b) : (true ? b : d)".assertTypeNameOfConditional("union{A,B,D}")
	}

	@Test
	def void testConditionalWithGenerics() {
		"ga : gb".assertTypeNameOfConditional("union{G<A>,G<B>}")
	}

	def private void assertTypeNameOfConditional(String conditionalExpression, String expectedTypeName) {
		val script = '''
			class A{}
			class B extends A{}
			class C{}
			class D extends A{}

			class G<T> {
				field: T;
			}

			var ga: G<A> = null;
			var gb: G<B> = null;

			var a: A;
			var b: B;
			var c: C;
			var d: D;

			var x = true ? «conditionalExpression»;

			'''.parse()

		assertTypeName(expectedTypeName, script.variableDeclarations.last.expression)
	}
}
