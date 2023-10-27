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

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for judgment type, see n4js.xsemantics for judgment, axiom and rules. Tests and rules are ordered according to
 * N4JS spec
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_19_ConditionalExpressionTypeInferenceTest extends AbstractTypesystemTest {

	@Inject
	ParseHelper<Script> parseHelper;

	/**
	 * see [N4JS] 6.1.19 Conditional Expression
	 */
	@Test
	public void testConditional() {
		assertTypeNameOfConditional("a : b", "A");
		assertTypeNameOfConditional("a : c", "union{A,C}");
		assertTypeNameOfConditional("b : d", "union{B,D}");
	}

	@Test
	public void testConditionalLinearizationWithSingleType() {
		assertTypeNameOfConditional("a : a", "A");
	}

	@Test
	public void testConditionalLinearizationWithNestedUnionTypes() {
		assertTypeNameOfConditional("(true ? a : b) : (true ? c : d)", "union{A,C,D}");
	}

	@Test
	public void testConditionalLinearizationWithNestedUnionTypesAndSimplification() {
		assertTypeNameOfConditional("(true ? a : b) : (true ? b : d)", "union{A,B,D}");
	}

	@Test
	public void testConditionalWithGenerics() {
		assertTypeNameOfConditional("ga : gb", "union{G<A>,G<B>}");
	}

	private void assertTypeNameOfConditional(String conditionalExpression, String expectedTypeName) {
		try {

			Script script = parseHelper.parse("""
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

					var x = true ? %s;

					""".formatted(conditionalExpression));

			assertTypeName(expectedTypeName, last(variableDeclarations(script)).getExpression());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
