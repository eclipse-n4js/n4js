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
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
public class ExtendedClassesIterableTest {

	@Test
	public void testSupers() {
		TClass A = clazz("A");
		ExtendedClassesIterable cri = new ExtendedClassesIterable(A);
		assertFalse(cri.iterator().hasNext());
	}

	@Test
	public void testTwo() {
		TClass B = clazz("B");
		TClass A = ext(clazz("A"), B);
		ExtendedClassesIterable cri = new ExtendedClassesIterable(A);
		assertTrue(cri.iterator().hasNext());
		assertEquals(size(cri), 1);
		assertEquals(B, cri.iterator().next());
	}

	@Test
	public void testThree() {
		TClass C = clazz("C");
		TClass B = ext(clazz("B"), C);
		TClass A = ext(clazz("A"), B);
		ExtendedClassesIterable cri = new ExtendedClassesIterable(A);
		assertTrue(cri.iterator().hasNext());
		assertEquals(size(cri), 2);
		Iterator<TClass> iter = cri.iterator();
		assertEquals(B, iter.next());
		assertEquals(C, iter.next());
	}

	@Test
	public void recursiveClassesTest() {

		TClass C = clazz("C");
		TClass B = ext(clazz("B"), C);
		TClass A = ext(clazz("A"), B);
		ext(C, B);
		ExtendedClassesIterable cri = new ExtendedClassesIterable(A);

		assertTrue(cri.iterator().hasNext());
		assertEquals(size(cri), 2);
		Iterator<TClass> iter = cri.iterator();
		assertEquals(B, iter.next());
		assertEquals(C, iter.next());
	}

}
