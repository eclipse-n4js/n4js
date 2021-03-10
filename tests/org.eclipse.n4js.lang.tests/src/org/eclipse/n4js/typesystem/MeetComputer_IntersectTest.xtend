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

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.MeetComputer
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/*
 * Tests for {@link TypeSystemHelper#intersect(RuleEnvironment, TypeRef...)} method.
 */
// TODO to be enhanced, see IDE-142/IDE-385
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class MeetComputer_IntersectTest extends AbstractTypeSystemHelperTests {

	@Inject MeetComputer meetComputer

	@Before
	def void prepareTypeDefs() {
		setDefaultTypeDefinitions()
	}

	/**
	 * Asserts that intersection of given type expressions equals a given expected type, for comparison
	 * {@link TypeRef#getTypeRefAsString()} is used.
	 */
	def void assertIntersectRelaxed(String expectedType, String... typeExpressionsToBeIntersected) {
		val G = assembler.prepareScriptAndCreateRuleEnvironment(typeExpressionsToBeIntersected)
		var typeRefs = typeExpressionsToBeIntersected.map[TypeUtils.copy(assembler.getTypeRef(it))]
		val intersection = TypeUtils.copy(meetComputer.intersectRelaxed(G, typeRefs));
		intersection.sorted();
		assertEquals(expectedType, intersection.typeRefAsString);
	}

	@Test
	def void testIntersectReleaxed() {
		assertIntersectRelaxed("A", "A", "A");
		assertIntersectRelaxed("B", "A", "B");
		assertIntersectRelaxed("B", "B", "A");
		assertIntersectRelaxed("intersection{A,D}", "A", "D");
		assertIntersectRelaxed("intersection{A,D}", "D", "A");
		assertIntersectRelaxed("intersection{B,D}", "B", "D");
		assertIntersectRelaxed("intersection{B,D}", "D", "B");
		assertIntersectRelaxed("intersection{B,D}", "A", "B", "D");
 		assertIntersectRelaxed("intersection{B,D}", "A", "D", "B");
 		assertIntersectRelaxed("intersection{B,D}", "B", "A", "D");
 		assertIntersectRelaxed("intersection{B,D}", "B", "D", "A");
 		assertIntersectRelaxed("intersection{B,D}", "D", "A", "B");
 		assertIntersectRelaxed("intersection{B,D}", "D", "B", "A");
	}



	@Test
	def void testIntersectReleaxedWithIntersections() {
		assertIntersectRelaxed("intersection{I1,R1}", "intersection{I1,R1}", "intersection{I1,R1}");
		assertIntersectRelaxed("intersection{I1,R1}", "intersection{I1,R1}", "I1");
		assertIntersectRelaxed("intersection{I1,R1}", "intersection{I1,R1}", "R1");
		assertIntersectRelaxed("intersection{B,R1}", "intersection{A,R1}", "B");
		assertIntersectRelaxed("intersection{B,R1}", "intersection{B,R1}", "A");
	}

}
