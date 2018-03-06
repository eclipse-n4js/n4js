/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.versioning

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4idl.TypeDistanceComputer
import org.eclipse.n4js.n4idl.tests.helper.AbstractN4IDLTypeSwitchTest
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit tests with regard to class {@link TypeDistanceComputer}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class TypeDistanceComputerTest extends AbstractN4IDLTypeSwitchTest {
	
	@Inject private TypeDistanceComputer distanceComputer;
	
	@Test
	public def void testClassTypeHeight() {
		val module = '''
		class A#1 {}
		class A1#1 extends A {}
		class A2#1 extends A1 {}
		class A3#1 extends A2 {}
		''';
		
		assertEquals("Class without super-type", 3, makeTypeRef("A#1", module).height);
		assertEquals("Class with 1 level of super-type", 4, makeTypeRef("A1#1", module).height);
		assertEquals("Class with 2 levels of super-types", 5, makeTypeRef("A2#1", module).height);
		assertEquals("Class with 3 levels super-types", 6, makeTypeRef("A3#1", module).height);
	}
	
	@Test
	public def void testInterfaceTypeHeight() {
		val module = '''
		interface I#1 {}
		interface I1#1 extends I {}
		interface I2#1 extends I1 {}
		interface I3#1 extends I2 {}
		
		interface I1And2#1 extends I1, I2 {}
		interface I1And3#1 extends I1, I3 {}
		interface I2And3#1 extends I2, I3 {}
		interface I3And1#1 extends I3, I1 {}
		interface I3And0#1 extends I3, I {}
		''';
		
		assertEquals("Interface without super-type", 3, makeTypeRef("I#1", module).height);
		assertEquals("Interface with 1 level of super-type", 4, makeTypeRef("I1#1", module).height);
		assertEquals("Interface with 2 levels of super-types", 5, makeTypeRef("I2#1", module).height);
		assertEquals("Interface with 3 levels super-types", 6, makeTypeRef("I3#1", module).height);
		
		assertEquals("Multi-super type interface with minimum 2 levels of super-interfaces", 5, 
			makeTypeRef("I1And2#1", module).height)
		assertEquals("Multi-super type interface with minimum 2 levels of super-interfaces", 5, 
			makeTypeRef("I1And3#1", module).height)
		assertEquals("Multi-super type interface with minimum 3 levels of super-interfaces", 6, 
			makeTypeRef("I2And3#1", module).height)
		assertEquals("Multi-super type interface with minimum 2 levels of super-interfaces", 5, 
			makeTypeRef("I3And1#1", module).height)
		assertEquals("Multi-super type interface with minimum 1 level of super-interfaces", 4, 
			makeTypeRef("I3And0#1", module).height)
	}
	
	@Test
	public def void testClassAndInterfaceTypeHeight() {
		val module = '''
		interface I#1 {}
		interface I1#1 extends I {}
		interface I2#1 extends I1 {}
		interface I3#1 extends I2 {}
		
		class A#1 {}
		class A1#1 extends A {}
		class A2#1 extends A1 {}
		class A3#1 extends A2 {}
				
		class A1andI3#1 extends A implements I3, I {}
		class A2andI0#1 extends A1 implements I, I2 {}
		class A3andI0#1 extends A2 implements I, I3 {}
		class A2andI1#1 extends A1 implements I1, I3 {}
		class A2andI2#1 extends A1 implements I2, I1 {}
		''';
		
		assertEquals("Class with super-class and implemented interfaces 0", 3, 
			makeTypeRef("A#1", module).height);
		assertEquals("Class with super-class and implemented interfaces 1", 4, 
			makeTypeRef("A1andI3#1", module).height);
		assertEquals("Class with super-class and implemented interfaces 2", 5, 
			makeTypeRef("A2andI0#1", module).height);
		assertEquals("Class with super-class and implemented interfaces 3", 6, 
			makeTypeRef("A3andI0#1", module).height);
		assertEquals("Class with super-class and implemented interfaces 4", 5, 
			makeTypeRef("A2andI1#1", module).height)
		assertEquals("Class with super-class and implemented interfaces 4", 5, 
			makeTypeRef("A2andI2#1", module).height)
	}
	
	@Test
	public def testTypeHeightVectorComputation() {
		/*
		 * The test modules models the following class hierarchy.
		 * <code>
		 * A		B
		 * |		|
		 * v		v
		 * A1		B1
		 * |		|
		 * v		v
		 * A2		B2
		 * </code>
		 */
		val module = '''
		class A#1 {}
		class A1#1 extends A {}
		class A2#1 extends A1 {}
		
		class B#1 {}
		class B1#1 extends B {}
		class B2#1 extends B1 {}
		''';
		
		val a_b = #["A#1", "B#1"].makeTypeRefs(module);
		val a1_b = #["A1#1", "B#1"].makeTypeRefs(module);
		val a_b1 = #["A#1", "B1#1"].makeTypeRefs(module);
		val a1_b1 = #["A1#1", "B1#1"].makeTypeRefs(module);
		val a_b2 = #["A#1", "B2#1"].makeTypeRefs(module);
		val a2_b = #["A2#1", "B#1"].makeTypeRefs(module);
		val a2_b1 = #["A2#1", "B1#1"].makeTypeRefs(module);
		val a1_b2 = #["A1#1", "B2#1"].makeTypeRefs(module);
		val a2_b2 = #["A2#1", "B2#1"].makeTypeRefs(module);
		
		val allPairs = #[a_b, a1_b, a_b1, a1_b1, a_b2, a2_b, a2_b1, a1_b2, a2_b2].toSet;
		
		assertTrue("a_b more generic than a1_b", a_b.norm < a1_b.norm);
		assertTrue("a_b more generic than a_b1", a_b.norm < a_b1.norm);
		assertTrue("a_b more generic than a1_b1", a_b.norm < a1_b1.norm);
		assertTrue("a_b more generic than a2_b1", a_b.norm < a2_b1.norm);
		
		assertTrue("a1_b equally generic as a_b1", a1_b.norm == a_b1.norm);
		assertTrue("a1_b1 more specific than a_b1", a_b1.norm < a1_b1.norm);
		
		val sortedPairs = allPairs.map[pair | 
			"|(" + pair.map[typeRefAsString].join(", ") + ")|" +
			" = " + pair.norm ]
			.join(",\n");
		
		assertEquals("Order on type pairs is as expected", 
			'''
			|(A#1, B#1)| = 4.242640687119285,
			|(A1#1, B#1)| = 5.0,
			|(A#1, B1#1)| = 5.0,
			|(A1#1, B1#1)| = 5.656854249492381,
			|(A#1, B2#1)| = 5.830951894845301,
			|(A2#1, B#1)| = 5.830951894845301,
			|(A2#1, B1#1)| = 6.4031242374328485,
			|(A1#1, B2#1)| = 6.4031242374328485,
			|(A2#1, B2#1)| = 7.0710678118654755'''.toString, sortedPairs);
		
	}
	
	private def Iterable<TypeRef> makeTypeRefs(Iterable<String> typeExpressions, String preamble) {
		return typeExpressions.map[makeTypeRef(it, preamble)];
	}
	
	private def double norm(Iterable<TypeRef> refs) {
		if (refs.empty) {
			return -1;
		}
		val heightVector = refs.map[it.height];
		return distanceComputer.computeNorm(heightVector);
	}
	
	private def int height(TypeRef ref) {
		return distanceComputer.computeHeight(ref.declaredType);
	}
}