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
public class ImplementedInterfacesIterableTest {

	@Test
	public void testNoInterfacesOrRoles() {
		TClass A = clazz("A");
		SuperInterfacesIterable iter = new SuperInterfacesIterable(A);
		assertFalse(iter.iterator().hasNext());
	}

	@Test
	public void testDirectlyImplementedInterface() {
		TInterface I = interf("I");
		TClass A = impl(clazz("A"), I);
		SuperInterfacesIterable iter = new SuperInterfacesIterable(A);
		assertTrue(iter.iterator().hasNext());
		assertEquals(size(iter), 1);
		assertEquals(I, iter.iterator().next());
	}

	@Test
	public void testIndirectlyImplementedInterface() {
		TInterface I = interf("I");
		TClass B = impl(clazz("B"), I);
		TClass A = ext(clazz("A"), B);
		SuperInterfacesIterable iter = new SuperInterfacesIterable(A);

		assertTrue(iter.iterator().hasNext());
		assertEquals(size(iter), 1);
		assertEquals(I, iter.iterator().next());
	}

	@Test
	public void recursiveInterfacesTest() {
		TInterface I2 = interf("I2");
		TInterface I1 = ext(interf("I1"), I2);
		ext(I2, I1);

		TClass A = impl(clazz("A"), I1);
		SuperInterfacesIterable iter = new SuperInterfacesIterable(A);

		assertTrue(iter.iterator().hasNext());
		assertEquals(size(iter), 2);
	}

}
