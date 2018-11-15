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
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/*
 * Tests for {@link TypeSystemHelper#join(RuleEnvironment, TypeRef...)} method with union types.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class JoinComputer_IntersectionTypesTest extends AbstractTypeSystemHelperTests {

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
		assertJoin("N4Object", "A", "D");

		// G is instanceof of N4OBject ;-)
		assertJoin("N4Object", "G<A>", "A");
	}

	@Test
	def void testJoinIntersectionWithElementFromIntersection() {
		assertJoin(#[IssueCodes.INTER_REDUNDANT_SUPERTYPE], "A", "A", "intersection{A,B}");
		assertJoin(#[IssueCodes.INTER_REDUNDANT_SUPERTYPE], "B", "B", "intersection{A,B}");
		assertJoin("R1", "R1", "intersection{A,R1}");
		assertJoin("N4Object", "D", "intersection{A,R1}", "A");
		assertJoin(#[IssueCodes.INTER_REDUNDANT_SUPERTYPE], "A", "B", "intersection{A,B}", "A");
	}

	@Test
	def void testJoinWithIntersections() {
		assertJoin("intersection{I1,R1}", "intersection{I1,R1}", "intersection{I1,R1}");
		assertJoin("intersection{I1,R1}", "intersection{I1,R1,Q1}", "intersection{I1,R1}");
		assertJoin("intersection{A,R1}", "intersection{A,R1}", "intersection{B,R1}");
		assertJoin(#[IssueCodes.INTER_REDUNDANT_SUPERTYPE,IssueCodes.INTER_REDUNDANT_SUPERTYPE], "B", "intersection{A,B}", "intersection{A,B}");

	}

	@Test
	def void testJoinWithIntersectionAndGenerics() {
		assertJoin("G<I1>", "G<I1>", "intersection{G<I1>,I2}");
		assertJoin("intersection{G<I1>,I1}", "intersection{G<I1>,I1}", "intersection{G<I1>,I2}");
		assertJoin("G<I1>", "intersection{G<I1>,R1}", "intersection{G<I1>,I2}");

	}

}
