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
package org.eclipse.n4js.conversion;

import org.eclipse.xtext.conversion.impl.STRINGValueConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 */
public abstract class AbstractN4JSStringValueConverter extends STRINGValueConverter {

	/**
	 * The issue code for valid, but discouraged escape sequences in JS.
	 */
	public static final String WARN_ISSUE_CODE = "N4JSStringValueConverter.bad.escapement.warn";
	/**
	 * The issue code for invalid escape sequences in JS.
	 */
	public static final String ERROR_ISSUE_CODE = "N4JSStringValueConverter.bad.escapement.error";

	/**
	 * A {@link BadEscapementException} is thrown to indicate an invalid / discouraged escape sequence in a JS string.
	 */
	public static class BadEscapementException extends N4JSValueConverterWithValueException {

		private static final long serialVersionUID = 1L;
		private final boolean error;

		/**
		 * Creates an exception at the given node.
		 */
		public BadEscapementException(String message, String issueCode, INode node, String value, boolean error) {
			super(message, issueCode, node, value, null);
			this.error = error;
		}

		/**
		 * @return <code>true</code> if it is an error, warnings return <code>false</code>.
		 */
		public boolean isError() {
			return error;
		}

	}

	@Override
	protected String toEscapedString(String value) {
		return getLeftDelimiter() + convertToJSString(value, false) + getRightDelimiter();
	}

	/**
	 * Mostly copied from {@link Strings#convertToJavaString(String, boolean)}
	 */
	public String convertToJSString(String theString, boolean useUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuilder outBuffer = new StringBuilder(bufLen);
		for (int x = 0; x < len; x++) {
			appendToJSString(theString.charAt(x), useUnicode, outBuffer);
		}
		return outBuffer.toString();
	}

	/**
	 * Mostly copied from {@link Strings#convertToJavaString(String, boolean)}
	 */
	protected abstract void appendToJSString(char aChar, boolean useUnicode, StringBuilder result);

	/**
	 * Returns the string that should be used on the right hand side of the concrete syntax.
	 */
	protected abstract String getRightDelimiter();

	/**
	 * Returns the string that should be used on the left hand side of the concrete syntax.
	 */
	protected abstract String getLeftDelimiter();

	/**
	 * Convert the JS string to its value.
	 */
	public abstract String convertFromJSString(String jsString, INode node, boolean validate);

	/**
	 * @param nodeText
	 *            the text in the input document.
	 * @return <code>true</code> if there's an octal escape sequence in the text.
	 */
	public static boolean hasOctalEscapeSequence(String nodeText) {
		if (nodeText.length() <= 2)
			return false;
		char[] charArray = nodeText.toCharArray();
		int last = charArray.length - 1;
		if (charArray[last] == charArray[0]) {
			int idx = 1;
			while (idx < last - 1) {
				char c = charArray[idx];
				if (c == '\\') {
					char next = charArray[idx + 1];
					if (next == '\\') {
						idx++;
					} else if ('0' == next) {
						if (idx + 1 < last - 1) {
							char third = charArray[idx + 2];
							if ('0' <= third && '9' >= third)
								return true;
						}
					} else if ('1' <= next && '9' >= next) {
						return true;
					}
				}
				idx++;
			}
		}
		return false;
	}

}
