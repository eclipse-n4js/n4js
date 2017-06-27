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
package org.eclipse.n4js.utils.collections

import org.junit.Test
import static extension org.junit.Assert.*

import static extension org.eclipse.n4js.utils.collections.Collections2.*

/**
 *
 */
class Collections2Test {
	@Test(expected = NullPointerException)
	def void testNullLHSExpectNPE() {
		null.concatUnique(newLinkedList());
	}

	@Test(expected = NullPointerException)
	def void testNullRHSExpectNPE() {
		newLinkedList().concatUnique(null);
	}

	@Test
	def void testEmptyLHS() {
		val lhs = newLinkedList();
		val rhs = newLinkedList("a", "c", "b");
		lhs.concatUnique(rhs).assertEquals(rhs);
	}

	@Test
	def void testEmptyRHS() {
		val lhs = newLinkedList("a", "c", "b");
		val rhs = newLinkedList();
		lhs.concatUnique(rhs).assertEquals(lhs);
	}

	@Test
	def void testNonEmptyListsWithoutDuplicates() {
		val lhs = newLinkedList("a", "c", "b");
		val rhs = newLinkedList("g", "f", "e");
		val res = newLinkedList("a", "c", "b", "g", "f", "e")
		lhs.concatUnique(rhs).assertEquals(res);
	}

	@Test
	def void testNonEmptyListsWithDuplicatesInLHS() {
		val lhs = newLinkedList("a", "b", "b");
		val rhs = newLinkedList("g", "f", "e");
		val res = newLinkedList("a", "b", "g", "f", "e")
		lhs.concatUnique(rhs).assertEquals(res);
	}

	@Test
	def void testNonEmptyListsWithDuplicatesInRHS() {
		val lhs = newLinkedList("a", "c", "b");
		val rhs = newLinkedList("g", "f", "g");
		val res = newLinkedList("a", "c", "b", "g", "f")
		lhs.concatUnique(rhs).assertEquals(res);
	}

	@Test
	def void testNonEmptyListsWithDuplicatesInLHSAndRHS() {
		val lhs = newLinkedList("a", "b", "b");
		val rhs = newLinkedList("g", "f", "g");
		val res = newLinkedList("a", "b", "g", "f")
		lhs.concatUnique(rhs).assertEquals(res);
	}

	@Test
	def void testNonEmptyListsWithDuplicatesInLHSAndAcrossRHS() {
		val lhs = newLinkedList("g", "b", "b");
		val rhs = newLinkedList("g", "f", "g");
		val res = newLinkedList("g", "b", "f")
		lhs.concatUnique(rhs).assertEquals(res);
	}
}
