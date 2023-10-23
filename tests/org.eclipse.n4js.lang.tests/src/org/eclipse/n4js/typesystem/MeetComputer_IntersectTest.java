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
package org.eclipse.n4js.typesystem;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.MeetComputer;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for {@link MeetComputer#intersectRelaxed(RuleEnvironment, TypeRef...)} method.
 */
// TODO to be enhanced, see IDE-142/IDE-385
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class MeetComputer_IntersectTest extends AbstractTypeSystemHelperTests {

	@Inject
	MeetComputer meetComputer;

	@Before
	public void prepareTypeDefs() {
		setDefaultTypeDefinitions();
	}

	/**
	 * Asserts that intersection of given type expressions equals a given expected type, for comparison
	 * {@link TypeRef#getTypeRefAsString()} is used.
	 */
	public void assertIntersectRelaxed(String expectedType, String... typeExpressionsToBeIntersected) {
		RuleEnvironment G = assembler.prepareScriptAndCreateRuleEnvironment(typeExpressionsToBeIntersected);
		Iterable<TypeRef> typeRefs = map(Arrays.asList(typeExpressionsToBeIntersected),
				s -> TypeUtils.copy(assembler.getTypeRef(s)));
		TypeRef intersection = TypeUtils
				.copy(meetComputer.intersectRelaxed(G, toList(typeRefs).toArray(new TypeRef[0])));
		sorted(intersection);
		assertEquals(expectedType, intersection.getTypeRefAsString());
	}

	@Test
	public void testIntersectReleaxed() {
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
	public void testIntersectReleaxedWithIntersections() {
		assertIntersectRelaxed("intersection{I1,R1}", "intersection{I1,R1}", "intersection{I1,R1}");
		assertIntersectRelaxed("intersection{I1,R1}", "intersection{I1,R1}", "I1");
		assertIntersectRelaxed("intersection{I1,R1}", "intersection{I1,R1}", "R1");
		assertIntersectRelaxed("intersection{B,R1}", "intersection{A,R1}", "B");
		assertIntersectRelaxed("intersection{B,R1}", "intersection{B,R1}", "A");
	}

}
