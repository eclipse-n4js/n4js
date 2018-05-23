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
package org.eclipse.n4js.jsdoc2spec.adoc;

/**
 * Convenience class for static methods related to counting chars in strings.
 */
public class StringCountUtils {

	/**
	 * Returns the number of occurrences for a given char {@code ch} in the given string {@code text}.
	 */
	static public int countInString(char ch, String text) {
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == ch)
				count++;
		}

		return count;
	}

	/**
	 * Returns the number of packages in the given module name {@code module}.
	 */
	static public int countFolderDepth(String module) {
		int count = countInString('/', module);

		if (module.startsWith("/"))
			count--;
		if (module.endsWith("/"))
			count--;

		return count;
	}

	/**
	 * Returns the number of new lines in the given string {@code text}.
	 */
	static public int countNewLines(String text) {
		int count = countInString('\n', text);
		return count;
	}
}
