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

import org.eclipse.n4js.conversion.ValueConverterUtils.StringConverterResult;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.conversion.impl.STRINGValueConverter;
import org.eclipse.xtext.nodemodel.INode;

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
		return getLeftDelimiter() + ValueConverterUtils.convertToEscapedString(value, false) + getRightDelimiter();
	}

	/**
	 * Returns the string that should be used on the right hand side of the concrete syntax.
	 */
	protected abstract String getRightDelimiter();

	/**
	 * Returns the string that should be used on the left hand side of the concrete syntax.
	 */
	protected abstract String getLeftDelimiter();

	/**
	 * Convert the N4JS string literal to its value.
	 *
	 * Made public only for testing.
	 */
	public static String convertFromN4JSString(String n4jsString, INode node, boolean validate) {
		StringConverterResult result = ValueConverterUtils.convertFromEscapedString(n4jsString, true, false, false,
				null);
		if (validate) {
			if (result.hasError()) {
				throw new BadEscapementException(IssueCodes.getMessageForVCO_STRING_BAD_ESCAPE_ERROR(),
						IssueCodes.VCO_STRING_BAD_ESCAPE_ERROR, node, result.getValue(), true);
			} else if (result.hasWarning()) {
				throw new BadEscapementException(IssueCodes.getMessageForVCO_STRING_BAD_ESCAPE_WARN(),
						IssueCodes.VCO_STRING_BAD_ESCAPE_WARN, node, result.getValue(), false);
			}
		}
		return result.getValue();
	}

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
