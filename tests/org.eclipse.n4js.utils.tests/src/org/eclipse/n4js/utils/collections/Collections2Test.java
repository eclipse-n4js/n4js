/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.collections;

import static org.eclipse.n4js.utils.collections.Collections2.concatUnique;
import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 *
 */
public class Collections2Test {
	@Test(expected = NullPointerException.class)
	public void testNullLHSExpectNPE() {
		concatUnique(null, new LinkedList<>());
	}

	@Test(expected = NullPointerException.class)
	public void testNullRHSExpectNPE() {
		concatUnique(new LinkedList<>(), null);
	}

	@Test
	public void testEmptyLHS() {
		List<String> lhs = new LinkedList<>();
		List<String> rhs = List.of("a", "c", "b");
		assertEquals(concatUnique(lhs, rhs), rhs);
	}

	@Test
	public void testEmptyRHS() {
		List<String> lhs = List.of("a", "c", "b");
		List<String> rhs = new LinkedList<>();
		assertEquals(concatUnique(lhs, rhs), lhs);
	}

	@Test
	public void testNonEmptyListsWithoutDuplicates() {
		List<String> lhs = List.of("a", "c", "b");
		List<String> rhs = List.of("g", "f", "e");
		List<String> res = List.of("a", "c", "b", "g", "f", "e");
		assertEquals(concatUnique(lhs, rhs), res);
	}

	@Test
	public void testNonEmptyListsWithDuplicatesInLHS() {
		List<String> lhs = List.of("a", "b", "b");
		List<String> rhs = List.of("g", "f", "e");
		List<String> res = List.of("a", "b", "g", "f", "e");
		assertEquals(concatUnique(lhs, rhs), res);
	}

	@Test
	public void testNonEmptyListsWithDuplicatesInRHS() {
		List<String> lhs = List.of("a", "c", "b");
		List<String> rhs = List.of("g", "f", "g");
		List<String> res = List.of("a", "c", "b", "g", "f");
		assertEquals(concatUnique(lhs, rhs), res);
	}

	@Test
	public void testNonEmptyListsWithDuplicatesInLHSAndRHS() {
		List<String> lhs = List.of("a", "b", "b");
		List<String> rhs = List.of("g", "f", "g");
		List<String> res = List.of("a", "b", "g", "f");
		assertEquals(concatUnique(lhs, rhs), res);
	}

	@Test
	public void testNonEmptyListsWithDuplicatesInLHSAndAcrossRHS() {
		List<String> lhs = List.of("g", "b", "b");
		List<String> rhs = List.of("g", "f", "g");
		List<String> res = List.of("g", "b", "f");
		assertEquals(concatUnique(lhs, rhs), res);
	}
}
