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
package org.eclipse.n4js.utils.collections;

import java.util.Collection;

import org.eclipse.xtext.xbase.typesystem.util.Multimaps2;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 *
 *
 * @see Multimaps
 * @see Multimaps2
 */
@SuppressWarnings("restriction")
public class Multimaps3 {

	/**
	 * Turn this Multimap into a two-dimensional array the first index giving the page, second the choices of this page.
	 *
	 * @param multimap
	 *            name to many options
	 * @return two-dim Array of T
	 */
	public static <T> Object[][] createOptions(Multimap<String, T> multimap) {
		Object[][] result = new Object[multimap.keySet().size()][];

		int page = 0;
		for (String key : multimap.keySet()) {
			Collection<T> values = multimap.get(key);
			result[page] = new Object[values.size()];
			int valueIndex = 0;
			for (T value : values) {
				result[page][valueIndex] = value;
				valueIndex++;
			}
			page++;
		}

		return result;
	}

}
