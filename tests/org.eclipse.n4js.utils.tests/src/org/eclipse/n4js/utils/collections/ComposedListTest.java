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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 */
public class ComposedListTest {

	@SuppressWarnings("javadoc")
	@Test
	public void testNoList() {
		assertComposedList();
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSingleList() {
		List<String> l1 = Lists.newArrayList("A", "B", "C");
		assertComposedList(l1);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSingleListOneElement() {
		List<String> l1 = Lists.newArrayList("A");
		assertComposedList(l1);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSingleEmptyList() {
		List<String> l1 = Lists.newArrayList();
		assertComposedList(l1);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testWithTwoEmptyLists() {
		List<String> l1 = Lists.newArrayList();
		List<String> l2 = Lists.newArrayList();
		assertComposedList(l1, l2);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleCase() {
		List<String> l1 = Lists.newArrayList("A", "B", "C");
		List<String> l2 = Lists.newArrayList("D", "E", "F");
		List<String> l3 = Lists.newArrayList("G", "H", "I");
		assertComposedList(l1, l2, l3);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleCase1() {
		List<String> l1 = Lists.newArrayList("A");
		List<String> l2 = Lists.newArrayList("D", "E", "F");
		List<String> l3 = Lists.newArrayList("G");
		assertComposedList(l1, l2, l3);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleCase2() {
		List<String> l1 = Lists.newArrayList("A", "B", "C");
		List<String> l2 = Lists.newArrayList("D");
		List<String> l3 = Lists.newArrayList("G", "H", "I");
		assertComposedList(l1, l2, l3);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSimpleCase3() {
		List<String> l1 = Lists.newArrayList("A");
		List<String> l2 = Lists.newArrayList("D");
		List<String> l3 = Lists.newArrayList("G");
		assertComposedList(l1, l2, l3);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testWithEmptyListInMiddle() {
		List<String> l1 = Lists.newArrayList("A", "B", "C");
		List<String> l2 = Lists.newArrayList();
		List<String> l3 = Lists.newArrayList("G", "H", "I");
		assertComposedList(l1, l2, l3);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testWithEmptyListAtStart() {
		List<String> l1 = Lists.newArrayList();
		List<String> l2 = Lists.newArrayList("D", "E", "F");
		List<String> l3 = Lists.newArrayList("G", "H", "I");
		assertComposedList(l1, l2, l3);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testWithEmptyListAtEnd() {
		List<String> l1 = Lists.newArrayList("A", "B", "C");
		List<String> l2 = Lists.newArrayList("D", "E", "F");
		List<String> l3 = Lists.newArrayList();
		assertComposedList(l1, l2, l3);
	}

	@SafeVarargs
	private final void assertComposedList(List<String>... lists) {
		List<String> composed = new ComposedList<>(lists);
		List<String> exp = Lists.newArrayList();
		for (List<String> l : lists) {
			exp.addAll(l);
		}

		String itemBefore = "none";

		assertEquals(exp.size(), composed.size());
		ListIterator<String> iterExp = exp.listIterator();
		ListIterator<String> iterComp = composed.listIterator();
		while (iterExp.hasNext()) {
			assertTrue(iterComp.hasNext());
			itemBefore = iterExp.next();
			assertEquals(itemBefore, iterComp.next());
			assertEquals(iterExp.nextIndex(), iterComp.nextIndex());
			assertEquals(iterExp.previousIndex(), iterComp.previousIndex());
		}
		assertFalse(iterComp.hasNext());

		while (iterExp.hasPrevious()) {
			assertTrue(itemBefore, iterComp.hasPrevious());
			itemBefore = iterExp.previous();
			assertEquals(itemBefore, iterComp.previous());
			assertEquals(iterExp.nextIndex(), iterComp.nextIndex());
			assertEquals(iterExp.previousIndex(), iterComp.previousIndex());
		}
		assertFalse(iterComp.hasPrevious());

		while (iterExp.hasNext()) {
			assertTrue(iterComp.hasNext());
			itemBefore = iterExp.next();
			assertEquals(itemBefore, iterComp.next());
			assertEquals(iterExp.nextIndex(), iterComp.nextIndex());
			assertEquals(iterExp.previousIndex(), iterComp.previousIndex());
		}
		int s = exp.size();
		for (int i = 0; i < s; i++) {
			assertEquals("Failed to get item at index " + i, exp.get(i), composed.get(i));
		}

	}

}
