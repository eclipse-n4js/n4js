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
package org.eclipse.n4js.utils.collections;

import static org.eclipse.n4js.utils.collections.Iterables2.chainOf;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * Tests for {@link Iterables2}.
 */
public class Iterables2Test {

	@Test(expected = NullPointerException.class)
	public void testNullElementsExpectNPE() {
		chainOf(null, new Object());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotContainedRefExpectIAE() {
		chainOf(List.of(), new Object());
	}

	@Test(expected = NullPointerException.class)
	public void testNullRefAsNotContainedRefExpectIAE() {
		chainOf(List.of(1, 2, 3, 4), null);
	}

	@Test
	public void testChainOfWithSingleContainment() {
		assertEquals(chainOf(List.of(1, 2, 3, 4, 5), 3), List.of(3, 4, 5, 1, 2, 3));
	}

	@Test
	public void testChainOfWithMultipleContainment() {
		assertEquals(chainOf(List.of(1, -1, 3, -1, 4, 5), -1), List.of(-1, 3, -1, 4, 5, 1, -1));
	}

}
