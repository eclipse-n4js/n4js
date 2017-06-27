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
package org.eclipse.n4js.ui.wizard.components

import java.util.regex.Pattern
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter

/**
 * Utility class to handle the {@link N4JSQualifiedNameConverter#DELIMITER delimiter} separated specifiers.
 * <p>
 * TODO rename & clean up this class and, if possible, use an injected IQualifiedNameConverter instead
 */
class DotPathUtils {

		/**
		 * Returns all {@link N4JSQualifiedNameConverter#DELIMITER delimiter} separated segments but the last one.
		 *
		 * @param delimiterSeparatedString
		 *            A delimiter separated string
		 * @return The front segments
		 */
		public static def String frontDotSegments(String delimiterSeparatedString) {
			if (delimiterSeparatedString.nullOrEmpty) {
				return "";
			}

			val lastSegment = lastDotSegment(delimiterSeparatedString);
			if (lastSegment.length == delimiterSeparatedString.length) {
				return "";
			}
			return delimiterSeparatedString.substring(0, delimiterSeparatedString.length() - 1 - lastSegment.length());
		}

		/**
		 * Returns the last {@link N4JSQualifiedNameConverter#DELIMITER delimiter} separated segment.
		 *
		 * <p>Note: Returns an empty string if no dot is found in the string.</p>
		 *
		 * @param delimiterSeparatedString
		 *            A delimiter separated string
		 * @return The last segment
		 */
		public static def String lastDotSegment(String delimiterSeparatedString) {
			val segments = delimiterSeparatedString.split(Pattern.quote(N4JSQualifiedNameConverter.DELIMITER));
			if (segments.length > 0) {
				return segments.last;
			}
			return "";
		}

}
