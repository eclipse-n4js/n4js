/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

/**
 * Utility for finding strongly connected components in a given graph.
 */
public class SCCUtils {

	/**
	 * Searches strongly connected components (SCCs) in the graph defined by the given adjacency function
	 * <code>fnNext</code>, searching at least the vertices supplied by <code>verticesToCheck</code>.
	 *
	 * @param <T>
	 *            type of the graph's vertices.
	 * @param verticesToCheck
	 *            vertices of the graph to be checked for their containing SCC. If all SCCs in the graph are to be
	 *            found, then this iterator must supply all vertices of the graph; if SCCs of only a subset of vertices
	 *            are to be searched, it is sufficient to supply only those vertices.
	 * @param fnNext
	 *            function mapping each vertex to a list of its adjacent vertices. May return vertices not supplied by
	 *            iterator <code>verticesToCheck</code>.
	 * @return list of SCCs, in which each SCC is represented as a list of vertices.
	 */
	public static <T> List<List<T>> findSCCs(Iterator<T> verticesToCheck, Function<T, Iterable<T>> fnNext) {
		return new SCCAnalyzer<>(fnNext).findSCCs(verticesToCheck);
	}

	/**
	 * Searches strongly connected components in a graph. Implements Tarjan's algorithm [1], slightly changed to allow
	 * vertices being represented by instances of an arbitrary type <code>T</code> (instead of just integers). Other
	 * than that, method/variable names, etc. have been kept as close to the original paper as possible, for ease of
	 * reference.
	 * <p>
	 * [1] Robert E. Tarjan, Depth-First Search and Linear Graph Algorithms, SIAM Journal on Computing, Vol. 1, No. 2,
	 * June 1972, pp. 146-160.
	 */
	private static final class SCCAnalyzer<T> {

		final Function<T, Iterable<T>> fnNext;

		int i = 0;
		final Map<T, SCCData> data = new HashMap<>();
		final Stack<T> stack_of_points = new Stack<>();

		final List<List<T>> result = new LinkedList<>();

		private static class SCCData {
			int LOWLINK = -1;
			int NUMBER = -1;
			boolean onStack = false;
		}

		public SCCAnalyzer(Function<T, Iterable<T>> fnNext) {
			this.fnNext = fnNext;
		}

		private void STRONGCONNECT(T v) {
			i++;

			SCCData vData = new SCCData();
			data.put(v, vData);
			vData.LOWLINK = i;
			vData.NUMBER = i;

			stack_of_points.push(v);
			vData.onStack = true;

			for (T w : fnNext.apply(v)) {
				SCCData wData = data.get(w);
				if (wData == null || wData.NUMBER < 0) {
					// (v,w) is a tree arc
					STRONGCONNECT(w);
					wData = data.get(w);
					vData.LOWLINK = Math.min(vData.LOWLINK, wData.LOWLINK);
				} else if (wData.NUMBER < vData.NUMBER) {
					// (v,w) is a frond or cross-link
					if (wData.onStack) {
						vData.LOWLINK = Math.min(vData.LOWLINK, wData.NUMBER);
					}
				}
			}

			if (vData.LOWLINK == vData.NUMBER) {
				// v is the root of a component
				List<T> scc = new LinkedList<>();
				T w = !stack_of_points.isEmpty() ? stack_of_points.peek() : null;
				SCCData wData = w != null ? data.get(w) : null;
				while (wData != null && wData.NUMBER >= vData.NUMBER) {
					wData.onStack = false;
					stack_of_points.pop();

					scc.add(w);

					w = !stack_of_points.isEmpty() ? stack_of_points.peek() : null;
					wData = w != null ? data.get(w) : null;
				}
				result.add(scc);
			}
		}

		private List<List<T>> findSCCs(Iterator<T> vertices) {
			i = 0;
			data.clear();
			stack_of_points.clear();
			result.clear();
			while (vertices.hasNext()) {
				T w = vertices.next();
				SCCData wData = data.get(w);
				if (wData == null || wData.NUMBER < 0) {
					STRONGCONNECT(w);
				}
			}
			return result;
		}
	}
}
