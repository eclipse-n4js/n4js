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

import static com.google.common.collect.ContiguousSet.create;
import static com.google.common.collect.DiscreteDomain.integers;
import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Range.closed;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.valueOf;
import static org.eclipse.n4js.utils.collections.Arrays2.transform;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

/**
 * Class for testing the {@link Arrays2} utility class.
 */
public class Arrays2Test {

	/***/
	@Test
	public void testNullArrayExpectEmptyList() {
		assertTrue(transform(null, input -> valueOf(input)).isEmpty());
	}

	/***/
	@Test
	public void testEmptyArrayExpectEmptyList() {
		assertTrue(transform(new Object[0], input -> valueOf(input)).isEmpty());
	}

	/***/
	@Test
	public void testToStringTransformation() {
		final Collection<Integer> origin = create(closed(1, 3), integers());
		final Integer[] ints = origin.toArray(new Integer[origin.size()]);
		final Collection<String> actual = newHashSet(transform(ints, input -> input.toString()));
		final Collection<String> expected = newHashSet("1", "2", "3");
		assertEquals(expected, actual);
	}

	/***/
	@Test(expected = NullPointerException.class)
	public void testNullFunctionExpectNPE() {
		transform(new Object[] { "item" }, null);
	}

	/***/
	@Test(expected = UnsupportedOperationException.class)
	public void testReturnIsUnmodifiableExpectUOE() {
		transform(new Object[] { "item" }, input -> valueOf(input)).add("another item");
	}

	/***/
	@Test
	public void testReturnCanBeSet() {
		final List<String> ret = transform(new Object[] { "item" }, input -> valueOf(input));
		assertFalse(ret.isEmpty());
		assertTrue("item".equals(getOnlyElement(ret)));
		ret.set(0, "another item");
		assertTrue("another item".equals(getOnlyElement(ret)));
	}

	/***/
	@Test
	public void testIsEmptyWithNull_ExpectTrue() {
		assertTrue(Arrays2.isEmpty(null));
	}

	/***/
	@Test
	public void testIsEmptyWithEmpty_ExpectTrue() {
		assertTrue(Arrays2.isEmpty(new String[] {}));
	}

	/***/
	@Test
	public void testIsEmptyWithNotEmpty_ExpectFalse() {
		assertTrue(!Arrays2.isEmpty(new String[] { "something" }));
	}

	/***/
	@Test(expected = NullPointerException.class)
	public void testAddToNull_ExpectNPE() {
		Arrays2.add(null, 1, 2, 3);
	}

	/***/
	@Test
	public void testAddNullIsPermited() {
		final Object[] source = new Object[] { 0, 1, 2, 3 };
		final Object[] expected = new Object[] { 0, 1, 2, 3, null, null, null };
		final Object[] actuals = Arrays2.add(source, null, null, null);
		assertArrayEquals(
				"Expected " + Arrays.toString(expected) + " got " + Arrays.toString(actuals) + " instead.",
				expected,
				actuals);
	}

	/***/
	@Test
	public void testAdd() {
		final Object[] source = new Object[] { 0, 1, 2, 3 };
		final Object[] expected = new Object[] { 0, 1, 2, 3, 4, 5, 6 };
		final Object[] actuals = Arrays2.add(source, 4, 5, 6);
		assertArrayEquals(
				"Expected " + Arrays.toString(expected) + " got " + Arrays.toString(actuals) + " instead.",
				expected,
				actuals);
	}

	/***/
	@Test
	public void testAddEmpty() {
		final Object[] source = new Object[] { 0, 1, 2, 3 };
		final Object[] toAdd = new Object[0];
		final Object[] actuals = Arrays2.add(source, toAdd);
		assertArrayEquals(
				"Expected " + Arrays.toString(source) + " got " + Arrays.toString(actuals) + " instead.",
				source,
				actuals);
		assertTrue("Expected same reference for return value as argument", source == actuals);
	}

	/***/
	@Test(expected = NullPointerException.class)
	public void testFilterNullClassExpectNPE() {
		Arrays2.filter(new Object[0], null);
	}

	/***/
	@Test(expected = NullPointerException.class)
	public void testFilterNullArrayExpectNPE() {
		Arrays2.filter(null, Object.class);
	}

	/***/
	@Test
	public void testFilterAlwaysCreatesNewArray() {
		final Object[] input = new Object[0];
		final Object[] result = Arrays2.filter(input, Object.class);
		assertNotSame("Expected non identical argument and result but was the same.", input, result);
	}

	/***/
	@Test
	public void testFilter() {
		final Object[] input = { "A", 1, false };
		final Object[] expected = { "A" };
		final String[] actual = Arrays2.filter(input, String.class);
		assertArrayEquals("Actual arrays differs from expected one. Actual: " + Arrays.toString(actual) + " Expected: "
				+ Arrays.toString(expected), expected, actual);
	}

}
