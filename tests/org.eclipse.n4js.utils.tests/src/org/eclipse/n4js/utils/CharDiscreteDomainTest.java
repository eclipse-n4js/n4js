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
package org.eclipse.n4js.utils;

import static com.google.common.collect.ContiguousSet.create;
import static com.google.common.collect.Range.closed;
import static com.google.common.collect.Range.open;
import static org.eclipse.n4js.utils.CharDiscreteDomain.chars;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for {@link CharDiscreteDomain}.
 */
public class CharDiscreteDomainTest {

	
	@Test
	public void testSizeWithClosed() {
		assertTrue(3 == create(closed('a', 'c'), chars()).size());
	}

	
	@Test
	public void testSizeWithOpened() {
		assertTrue(1 == create(open('a', 'c'), chars()).size());
	}

	
	@Test
	public void testPrevious() {
		assertEquals((Character) 'a', chars().previous('b'));
	}

	
	@Test
	public void testNext() {
		assertEquals((Character) 'c', chars().next('b'));
	}

	
	@Test
	public void testDistance() {
		assertEquals(1, chars().distance('a', 'b'));
		assertEquals(-1, chars().distance('b', 'a'));
		assertEquals(2, chars().distance('a', 'c'));
		assertEquals(-2, chars().distance('c', 'a'));
	}

}
