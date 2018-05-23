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
package org.eclipse.n4js.ts.types.util

import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import static extension org.eclipse.n4js.ts.types.util.TypesTestUtils.*
import org.eclipse.n4js.ts.types.util.SuperInterfacesIterable

/**
 */
@RunWith(XtextRunner)
class SuperInterfacesIteratorTest {

	@Test
	def void testNoRoles() {
		val A = clazz("A");
		val cri = new SuperInterfacesIterable(A);
		assertFalse(cri.iterator().hasNext())
	}

	@Test
	def void testDirectlyConsumedRole() {
		val R = interf("R");
		val A = clazz("A").impl(R);

		val cri = new SuperInterfacesIterable(A);
		assertTrue(cri.iterator().hasNext())
		assertEquals(cri.size, 1)
		assertEquals(A, cri.findClassImplementingInterface(R));
	}

	@Test
	def void testIndirectlyConsumedRole() {
		val R = interf("R");
		val B = clazz("B").impl(R);
		val A = clazz("A").ext(B);
		val cri = new SuperInterfacesIterable(A);

		assertTrue(cri.iterator().hasNext())
		assertEquals(cri.size, 1)
		assertEquals(B, cri.findClassImplementingInterface(R));
	}

	@Test
	def void testManyRoles() {
		val R1 = interf("R1");
		val R2 = interf("R2");
		val R3 = interf("R3");
		val R7 = interf("R7");
		val R8 = interf("R8");
		val R9 = interf("R9");
		val RA = interf("RA");
		val R5 = interf("R5").ext(R7,R8);
		val R6 = interf("R6");
		val R4 = interf("R4").ext(R5,R6);
		val D = clazz("D");
		val C = clazz("C").impl(R1,R2).ext(D)
		val B = clazz("B").ext(C).impl(R3,R4)
		val A = clazz("A").ext(B).impl(R9,RA)

		val cri = new SuperInterfacesIterable(A);
		assertTrue(cri.iterator().hasNext())
		assertEquals(10, cri.size)
		assertEquals(C, cri.findClassImplementingInterface(R1));
		assertEquals(C, cri.findClassImplementingInterface(R2));
		assertEquals(B, cri.findClassImplementingInterface(R3));
		assertEquals(B, cri.findClassImplementingInterface(R4));
		assertEquals(B, cri.findClassImplementingInterface(R5));
		assertEquals(B, cri.findClassImplementingInterface(R6));
		assertEquals(B, cri.findClassImplementingInterface(R7));
		assertEquals(B, cri.findClassImplementingInterface(R8));
		assertEquals(A, cri.findClassImplementingInterface(R9));
		assertEquals(A, cri.findClassImplementingInterface(RA));
	}

	@Test
	def void recursiveRolesTest() {
		val R2 = interf("R2");
		val R1 = interf("R1").ext(R2);
		R2.ext(R1);

		val B = clazz("B").impl(R1);
		val A = clazz("A").ext(B);
		val cri = new SuperInterfacesIterable(A);

		assertTrue(cri.iterator().hasNext())
		assertEquals(cri.size, 2)
		assertEquals(B, cri.findClassImplementingInterface(R1));
		assertEquals(B, cri.findClassImplementingInterface(R2));
	}

	@Test
	def void recursiveClassesTest() {
		val R2 = interf("R2");
		val R1 = interf("R1").ext(R2);

		val C = clazz("C");
		val B = clazz("B").ext(C).impl(R1);
		val A = clazz("A").ext(B);
		C.ext(B);
		val cri = new SuperInterfacesIterable(A);

		assertTrue(cri.iterator().hasNext())
		assertEquals(cri.size, 2)
		assertEquals(B, cri.findClassImplementingInterface(R1));
		assertEquals(B, cri.findClassImplementingInterface(R2));
	}



}
