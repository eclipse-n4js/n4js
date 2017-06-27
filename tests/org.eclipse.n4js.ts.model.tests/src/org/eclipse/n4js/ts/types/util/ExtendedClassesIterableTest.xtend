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

import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.ts.types.util.TypesTestUtils.*
import org.eclipse.n4js.ts.types.util.ExtendedClassesIterable

/**
 */
@RunWith(XtextRunner)
class ExtendedClassesIterableTest {

	@Test
	def void testSupers() {
		val A = clazz("A");
		val cri = new ExtendedClassesIterable(A);
		assertFalse(cri.iterator().hasNext())
	}

	@Test
	def void testTwo() {
		val B = clazz("B");
		val A = clazz("A").ext(B);
		val cri = new ExtendedClassesIterable(A);
		assertTrue(cri.iterator().hasNext())
		assertEquals(cri.size, 1)
		assertEquals(B, cri.iterator().next());
	}

	@Test
	def void testThree() {
		val C = clazz("C");
		val B = clazz("B").ext(C);
		val A = clazz("A").ext(B);
		val cri = new ExtendedClassesIterable(A);
		assertTrue(cri.iterator().hasNext());
		assertEquals(cri.size, 2);
		val iter = cri.iterator;
		assertEquals(B, iter.next());
		assertEquals(C, iter.next());
	}


	@Test
	def void recursiveClassesTest() {

		val C = clazz("C");
		val B = clazz("B").ext(C);
		val A = clazz("A").ext(B);
		C.ext(B);
		val cri = new ExtendedClassesIterable(A);

		assertTrue(cri.iterator().hasNext())
		assertEquals(cri.size, 2)
		val iter = cri.iterator;
		assertEquals(B, iter.next());
		assertEquals(C, iter.next());
	}



}
