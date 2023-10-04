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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class for testing {@link AndFunction1}.
 */
@SuppressWarnings("unchecked")
public class AndFunction1Test {

	static Function1<Integer, Boolean> ALWAYS_TRUE = (i) -> true;
	static Function1<Integer, Boolean> MOD_2 = (i) -> 0 == i % 2;
	static Function1<Integer, Boolean> MOD_3 = (i) -> 0 == i % 3;

	@Test(expected = NullPointerException.class)
	public void argumentsCannotBeNull_FirstIsNull_ExpectNPE() {
		AndFunction1.conjunctionOf(null, ALWAYS_TRUE);
	}

	@Test(expected = NullPointerException.class)
	public void argumentsCannotBeNull_OthersIsNull_ExpectNPE() {
		AndFunction1.conjunctionOf(ALWAYS_TRUE, (Function1<? super Integer, Boolean>[]) null);
	}

	@Test(expected = NullPointerException.class)
	public void argumentsCannotBeNull_OthersHasNull_ExpectNPE() {
		AndFunction1.conjunctionOf(ALWAYS_TRUE, MOD_2, null);
	}

	@Test(expected = NullPointerException.class)
	public void argumentsCannotBeNull_OthersContainsNull_ExpectNPE() {
		AndFunction1.conjunctionOf(ALWAYS_TRUE, new Function1[] { MOD_2, null });
	}

	@Test
	public void returnsWithTrueIfAllFunctionsEvaluatesToTrue_1() {
		List<Integer> expected = List.of(0, 2, 4, 6, 8);
		List<Integer> input = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		Function1<Integer, Boolean> predicate = AndFunction1.conjunctionOf(ALWAYS_TRUE, MOD_2);
		List<Integer> actual = toList(filter(input, predicate));
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void returnsWithTrueIfAllFunctionsEvaluatesToTrue_2() {
		List<Integer> expected = List.of(0, 6);
		List<Integer> input = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		Function1<Integer, Boolean> predicate = AndFunction1.conjunctionOf(ALWAYS_TRUE, MOD_2).and(MOD_3);
		List<Integer> actual = toList(filter(input, predicate));
		Assert.assertEquals(expected, actual);
	}

}
