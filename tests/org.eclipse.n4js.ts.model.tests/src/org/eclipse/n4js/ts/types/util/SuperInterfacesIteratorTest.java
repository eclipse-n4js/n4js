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
package org.eclipse.n4js.ts.types.util;

import static org.eclipse.n4js.ts.types.util.TypesTestUtils.clazz;
import static org.eclipse.n4js.ts.types.util.TypesTestUtils.ext;
import static org.eclipse.n4js.ts.types.util.TypesTestUtils.impl;
import static org.eclipse.n4js.ts.types.util.TypesTestUtils.interf;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 */
@RunWith(XtextRunner.class)
public class SuperInterfacesIteratorTest {

	@Test
	public void testNoRoles() {
		TClass A = clazz("A");
		SuperInterfacesIterable cri = new SuperInterfacesIterable(A);
		assertFalse(cri.iterator().hasNext());
	}

	@Test
	public void testDirectlyConsumedRole() {
		TInterface R = interf("R");
		TClass A = impl(clazz("A"), R);

		SuperInterfacesIterable cri = new SuperInterfacesIterable(A);
		assertTrue(cri.iterator().hasNext());
		assertEquals(size(cri), 1);
		assertEquals(A, cri.findClassImplementingInterface(R));
	}

	@Test
	public void testIndirectlyConsumedRole() {
		TInterface R = interf("R");
		TClass B = impl(clazz("B"), R);
		TClass A = ext(clazz("A"), B);
		SuperInterfacesIterable cri = new SuperInterfacesIterable(A);

		assertTrue(cri.iterator().hasNext());
		assertEquals(size(cri), 1);
		assertEquals(B, cri.findClassImplementingInterface(R));
	}

	@Test
	public void testManyRoles() {
		TInterface R1 = interf("R1");
		TInterface R2 = interf("R2");
		TInterface R3 = interf("R3");
		TInterface R7 = interf("R7");
		TInterface R8 = interf("R8");
		TInterface R9 = interf("R9");
		TInterface RA = interf("RA");
		TInterface R5 = ext(interf("R5"), R7, R8);
		TInterface R6 = ext(interf("R6"));
		TInterface R4 = ext(interf("R4"), R5, R6);
		TClass D = clazz("D");
		TClass C = ext(impl(clazz("C"), R1, R2), D);
		TClass B = impl(ext(clazz("B"), C), R3, R4);
		TClass A = impl(ext(clazz("A"), B), R9, RA);

		SuperInterfacesIterable cri = new SuperInterfacesIterable(A);
		assertTrue(cri.iterator().hasNext());
		assertEquals(10, size(cri));
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
	public void recursiveRolesTest() {
		TInterface R2 = interf("R2");
		TInterface R1 = ext(interf("R1"), R2);
		ext(R2, R1);

		TClass B = impl(clazz("B"), R1);
		TClass A = ext(clazz("A"), B);
		SuperInterfacesIterable cri = new SuperInterfacesIterable(A);

		assertTrue(cri.iterator().hasNext());
		assertEquals(size(cri), 2);
		assertEquals(B, cri.findClassImplementingInterface(R1));
		assertEquals(B, cri.findClassImplementingInterface(R2));
	}

	@Test
	public void recursiveClassesTest() {
		TInterface R2 = interf("R2");
		TInterface R1 = ext(interf("R1"), R2);

		TClass C = clazz("C");
		TClass B = impl(ext(clazz("B"), C), R1);
		TClass A = ext(clazz("A"), B);
		ext(C, B);
		SuperInterfacesIterable cri = new SuperInterfacesIterable(A);

		assertTrue(cri.iterator().hasNext());
		assertEquals(size(cri), 2);
		assertEquals(B, cri.findClassImplementingInterface(R1));
		assertEquals(B, cri.findClassImplementingInterface(R2));
	}

}
