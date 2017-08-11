/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ListUtils {

	/**
	 *
	 */
	@SafeVarargs
	static public <T> LinkedList<T> filterNulls(T... elems) {
		return filterNulls(Arrays.asList(elems));
	}

	/**
	 *
	 */
	public static <T> LinkedList<T> filterNulls(Iterable<T> elems) {
		LinkedList<T> list = new LinkedList<>();
		for (Iterator<T> it = elems.iterator(); it.hasNext();) {
			T cfe = it.next();
			if (cfe != null)
				list.add(cfe);
		}
		return list;
	}

	/**
	 *
	 */
	@SuppressWarnings("unused")
	@SafeVarargs
	private static <T> List<T> filterNames(T... elems) {
		return filterNames(Arrays.asList(elems));
	}

	private static <T> LinkedList<T> filterNames(Iterable<T> elems) {
		LinkedList<T> list = new LinkedList<>();
		for (Iterator<T> it = elems.iterator(); it.hasNext();) {
			// T cfe = it.next();
			// if (cfe != null && !(cfe instanceof Name))
			// list.add(cfe);
		}
		return list;
	}

}
