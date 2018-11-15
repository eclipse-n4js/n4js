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
package org.eclipse.n4js.typesystem

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/*
 * Tests for {@link TypeSystemHelper#join(RuleEnvironment, TypeRef...)} method with function types.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class JoinComputer_FunctionTypesTest extends AbstractTypeSystemHelperTests {

	@Before
	def void prepareTypeDefs() {
		setDefaultTypeDefinitions()
	}

	/*
	 * Test some assumptions.
	 */
	@Test
	def void testJoinAssumptions() {
		assertJoin("G<? extends A>", "G<A>", "G<B>");
		assertJoin("A", "A", "B");
		assertMeet("B", "A", "B");
		assertJoin("N4Object", "A", "D");

		// G is instanceof of N4OBject ;-)
		assertJoin("N4Object", "G<A>", "A");
	}

	@Test
	def void testJoinEmptyVoidFunction() {
		assertJoin("{function()}", "{function()}", "{function()}");
	}


	@Test
	def void testJoinFunction() {
		assertJoin("{function(A)}", "{function(A)}", "{function(A)}");
		assertJoin("{function(A)}", "{function()}", "{function(A)}");
		assertJoin("{function(A)}", "{function(A)}", "{function()}");
		assertJoin("{function(B)}", "{function()}", "{function(B)}");
		assertJoin("{function(B)}", "{function(A)}", "{function(B)}");
		assertJoin("{function(B)}", "{function(B)}", "{function(A)}");

		assertJoin("{function():A}", "{function():A}", "{function():A}");
		assertJoin("{function()}", "{function()}", "{function():A}");
		assertJoin("{function()}", "{function():A}", "{function()}");
		assertJoin("{function()}", "{function()}", "{function():B}");
		assertJoin("{function():A}", "{function():A}", "{function():B}");
		assertJoin("{function():A}", "{function():B}", "{function():A}");

		assertJoin("{function(B):A}", "{function(B):A}", "{function(B):B}");
		assertJoin("{function(B):A}", "{function(B):A}", "{function(A):A}");
		assertJoin("{function(B):A}", "{function(A):A}", "{function(B):A}");
		assertJoin("{function(B):A}", "{function(A):A}", "{function(B):B}");
		assertJoin("{function(B):A}", "{function(A):B}", "{function(B):A}");

	}


	@Test
	def void testJoinFunctionDebug() {
		assertJoin("{function(B):A}", "{function(A):A}", "{function(B):B}");
	}

}
