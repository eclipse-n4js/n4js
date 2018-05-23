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
package org.eclipse.n4js.utils

import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.junit.Assert
import org.junit.Test

/**
 * Class for testing {@link AndFunction1}.
 */
class AndFunction1Test {

	static val Function1<Integer, Boolean> ALWAYS_TRUE = [ true ];
	static val Function1<Integer, Boolean> MOD_2 = [ 0 === it % 2 ];
	static val Function1<Integer, Boolean> MOD_3 = [ 0 === it % 3 ];

	@Test(expected = NullPointerException)
	def void argumentsCannotBeNull_FirstIsNull_ExpectNPE() {
		AndFunction1.conjunctionOf(null, ALWAYS_TRUE);
	}

	@Test(expected = NullPointerException)
	def void argumentsCannotBeNull_OthersIsNull_ExpectNPE() {
		AndFunction1.conjunctionOf(ALWAYS_TRUE, null);
	}

	@Test(expected = NullPointerException)
	def void argumentsCannotBeNull_OthersHasNull_ExpectNPE() {
		AndFunction1.conjunctionOf(ALWAYS_TRUE, MOD_2, null);
	}

	@Test(expected = NullPointerException)
	def void argumentsCannotBeNull_OthersContainsNull_ExpectNPE() {
		AndFunction1.conjunctionOf(ALWAYS_TRUE, #[MOD_2, null]);
	}

	@Test
	def void returnsWithTrueIfAllFunctionsEvaluatesToTrue_1() {
		val expected = #[0, 2, 4, 6, 8];
		val input = #[0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
		val predicate = AndFunction1.conjunctionOf(ALWAYS_TRUE, MOD_2);
		val actual = input.filter(predicate).toList;
		Assert.assertEquals(expected, actual);
	}

	@Test
	def void returnsWithTrueIfAllFunctionsEvaluatesToTrue_2() {
		val expected = #[0, 6];
		val input = #[0, 1, 2, 3, 4, 5, 6, 7, 8, 9];
		val predicate = AndFunction1.conjunctionOf(ALWAYS_TRUE, MOD_2).and(MOD_3);
		val actual = input.filter(predicate).toList;
		Assert.assertEquals(expected, actual);
	}

}
