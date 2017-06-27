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

import org.eclipse.n4js.utils.DependencyTraverser.DependencyCycle
import org.junit.Assert
import org.junit.Test

/**
 * Tests for the generic dependency traverser.
 */
class DependencyTraverserTest {

	@Test
	def void testNoCycle() {
		val graph = #{
			1 -> #[2],
			2 -> #[3]
		};
		new DependencyTraverser(1, [graph.get(it)]).result.assertEquals('');
	}

	@Test
	def void testSelfCycle() {
		val graph = #{
			1 -> #[1]
		};
		new DependencyTraverser(1, [graph.get(it)]).result.assertEquals('''[1] -> [1]''');
	}

	@Test
	def void testSimpleCycle() {
		val graph = #{
			1 -> #[2],
			2 -> #[1]
		};
		new DependencyTraverser(1, [graph.get(it)]).result.assertEquals('''[1] -> 2 -> [1]''');
	}

	@Test
	def void testTransitiveCycle() {
		val graph = #{
			1 -> #[2],
			2 -> #[3],
			3 -> #[1]
		};
		new DependencyTraverser(1, [graph.get(it)]).result.assertEquals('''[1] -> 2 -> 3 -> [1]''');
	}

	@Test
	def void testHook() {
		val graph = #{
			1 -> #[2],
			2 -> #[3],
			3 -> #[4],
			4 -> #[3]
		};
		new DependencyTraverser(1, [graph.get(it)]).result.assertEquals('''1 -> 2 -> [3] -> 4 -> [3]''');
	}

	def private assertEquals(DependencyCycle<?> actual, String expected) {
		val actualToString = actual.prettyPrint;
		val msg = '''Expected: «expected», but got «actualToString» instead.''';
		Assert.assertEquals(msg, expected, actualToString);
	}

}
