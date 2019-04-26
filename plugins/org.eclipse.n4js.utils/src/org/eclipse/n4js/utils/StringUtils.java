/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.util.Iterator;
import java.util.function.Function;

/**
 *
 */
public class StringUtils {

	static public <T> String join(CharSequence delimiter, Iterable<T> elements, Function<T, CharSequence> map) {
		StringBuilder joined = new StringBuilder();
		for (Iterator<T> tIter = elements.iterator(); tIter.hasNext();) {
			T t = tIter.next();
			CharSequence charSequence = map.apply(t);
			joined.append(charSequence);
			if (tIter.hasNext()) {
				joined.append(delimiter);
			}
		}
		return joined.toString();
	}

}
