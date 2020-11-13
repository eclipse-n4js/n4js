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

import org.junit.Assert
import org.junit.Test
import org.eclipse.n4js.xtext.workspace.ProjectBuildOrderInfo

/**
 * Tests for the generic dependency traverser.
 *
 * Note that dependency cycles are detected using {@link ProjectBuildOrderInfo} (see GH-1964)
 */
class DependencyTraverserTest {

	@Test
	def void testNoCycle() {
		val graph = #{
			1 -> #[2],
			2 -> #[3]
		};
		new DependencyTraverser(1, [graph.get(it)], false).findCycle.assertEquals('');
	}

	@Test
	def void testSelfCycle() {
		val graph = #{
			1 -> #[1]
		};
		new DependencyTraverser(1, [graph.get(it)], false).findCycle.assertEquals('''[1] -> [1]''');
	}

	@Test
	def void testSimpleCycle() {
		val graph = #{
			1 -> #[2],
			2 -> #[1]
		};
		new DependencyTraverser(1, [graph.get(it)], false).findCycle.assertEquals('''[1] -> 2 -> [1]''');
	}

	@Test
	def void testTransitiveCycle() {
		val graph = #{
			1 -> #[2],
			2 -> #[3],
			3 -> #[1]
		};
		new DependencyTraverser(1, [graph.get(it)], false).findCycle.assertEquals('''[1] -> 2 -> 3 -> [1]''');
	}

	@Test
	def void testHook() {
		val visitedNodes = newArrayList;
		val graph = #{
			1 -> #[2],
			2 -> #[3],
			3 -> #[4],
			4 -> #[3]
		};
		new DependencyTraverser(1, [visitedNodes.add(it)], [graph.get(it)], false).findCycle.assertEquals('''1 -> 2 -> [3] -> 4 -> [3]''');
		Assert.assertEquals("Not all nodes were visited.", "[1, 2, 3, 4]", visitedNodes.toString)
	}
	
	@Test
	def void testNoCycleIgnoreCycles() {
		val visitedNodes = newArrayList;
		val graph = #{
			1 -> #[2],
			2 -> #[3]
		};
		new DependencyTraverser(1, [visitedNodes.add(it)], [graph.get(it)], true).findCycle.assertEquals('');
		Assert.assertEquals("All nodes were visited.", "[1, 2, 3]", visitedNodes.toString)
	}
	
	@Test
	def void testSelfCycleIgnoreCycles() {
		val visitedNodes = newArrayList;
		val graph = #{
			1 -> #[1, 2]
		};
		new DependencyTraverser(1, [visitedNodes.add(it)], [graph.get(it)], true).findCycle.assertEquals('''[1] -> [1]''');
		Assert.assertEquals("All nodes were visited.", "[1, 2]", visitedNodes.toString)
	}
	
	@Test
	def void testHookIgnoreCycles() {
		val visitedNodes = newArrayList;
		val graph = #{
			1 -> #[2],
			2 -> #[3],
			3 -> #[4],
			4 -> #[3, 5]
		};
		new DependencyTraverser(1, [visitedNodes.add(it)], [graph.get(it)], true).findCycle.assertEquals('''1 -> 2 -> [3] -> 4 -> [3]''');
		
		Assert.assertEquals("All nodes were visited", "[1, 2, 3, 4, 5]", visitedNodes.toString)
	}

	def private assertEquals(DependencyCycle<?> actual, String expected) {
		val actualToString = actual.prettyPrint;
		val msg = '''Expected: «expected», but got «actualToString» instead.''';
		Assert.assertEquals(msg, expected, actualToString);
	}

}
