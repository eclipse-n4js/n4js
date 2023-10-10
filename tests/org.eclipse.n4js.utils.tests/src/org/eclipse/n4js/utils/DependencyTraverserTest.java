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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.xtext.workspace.BuildOrderInfo;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the generic dependency traverser.
 *
 * Note that dependency cycles are detected using {@link BuildOrderInfo} (see GH-1964)
 */
public class DependencyTraverserTest {

	@Test
	public void testNoCycle() {
		Map<Integer, List<Integer>> graph = Map.of(
				1, List.of(2),
				2, List.of(3));
		assertEquals(new DependencyTraverser<>(1, idx -> graph.get(idx), false).findCycle(), "");
	}

	@Test
	public void testSelfCycle() {
		Map<Integer, List<Integer>> graph = Map.of(
				1, List.of(1));
		assertEquals(new DependencyTraverser<>(1, idx -> graph.get(idx), false).findCycle(), "[1] -> [1]");
	}

	@Test
	public void testSimpleCycle() {
		Map<Integer, List<Integer>> graph = Map.of(
				1, List.of(2),
				2, List.of(1));

		assertEquals(new DependencyTraverser<>(1, idx -> graph.get(idx), false).findCycle(), "[1] -> 2 -> [1]");
	}

	@Test
	public void testTransitiveCycle() {
		Map<Integer, List<Integer>> graph = Map.of(
				1, List.of(2),
				2, List.of(3),
				3, List.of(1));

		assertEquals(new DependencyTraverser<>(1, idx -> graph.get(idx), false).findCycle(), "[1] -> 2 -> 3 -> [1]");
	}

	@Test
	public void testHook() {
		List<Integer> visitedNodes = new ArrayList<>();
		Map<Integer, List<Integer>> graph = Map.of(
				1, List.of(2),
				2, List.of(3),
				3, List.of(4),
				4, List.of(3));

		assertEquals(
				new DependencyTraverser<>(1, node -> visitedNodes.add(node), idx -> graph.get(idx), false).findCycle(),
				"1 -> 2 -> [3] -> 4 -> [3]");
		Assert.assertEquals("Not all nodes were visited.", "[1, 2, 3, 4]", visitedNodes.toString());
	}

	@Test
	public void testNoCycleIgnoreCycles() {
		List<Integer> visitedNodes = new ArrayList<>();
		Map<Integer, List<Integer>> graph = Map.of(
				1, List.of(2),
				2, List.of(3));

		assertEquals(
				new DependencyTraverser<>(1, node -> visitedNodes.add(node), idx -> graph.get(idx), true).findCycle(),
				"");
		Assert.assertEquals("All nodes were visited.", "[1, 2, 3]", visitedNodes.toString());
	}

	@Test
	public void testSelfCycleIgnoreCycles() {
		List<Integer> visitedNodes = new ArrayList<>();
		Map<Integer, List<Integer>> graph = Map.of(
				1, List.of(1, 2));

		assertEquals(
				new DependencyTraverser<>(1, node -> visitedNodes.add(node), idx -> graph.get(idx), true).findCycle(),
				"[1] -> [1]");
		Assert.assertEquals("All nodes were visited.", "[1, 2]", visitedNodes.toString());
	}

	@Test
	public void testHookIgnoreCycles() {
		List<Integer> visitedNodes = new ArrayList<>();
		Map<Integer, List<Integer>> graph = Map.of(
				1, List.of(2),
				2, List.of(3),
				3, List.of(4),
				4, List.of(3, 5));

		assertEquals(
				new DependencyTraverser<>(1, node -> visitedNodes.add(node), idx -> graph.get(idx), true).findCycle(),
				"1 -> 2 -> [3] -> 4 -> [3]");

		Assert.assertEquals("All nodes were visited", "[1, 2, 3, 4, 5]", visitedNodes.toString());
	}

	private void assertEquals(DependencyCycle<?> actual, String expected) {
		String actualToString = actual.prettyPrint();
		String msg = "Expected: «expected», but got «actualToString» instead.";
		Assert.assertEquals(msg, expected, actualToString);
	}

}
