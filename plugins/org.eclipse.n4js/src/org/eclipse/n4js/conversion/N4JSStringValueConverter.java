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

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

/**
 * A value converter that converts N4JSString literals to string values.
 *
 * It is aware of unicode escape sequences and octal escapes..
 */
public class N4JSStringValueConverter extends AbstractN4JSStringValueConverter {

	@Override
	protected String getRightDelimiter() {
		return "\"";
	}

	@Override
	protected String getLeftDelimiter() {
		return "\"";
	}

	@Override
	public String toValue(String string, INode node) {
		if (string == null)
			return null;
		try {
			if (string.length() == 1) {
				throw newN4JSValueConverterException(string.charAt(0), node, "");
			}
			char first = string.charAt(0);
			int lastIdx = string.length() - 1;
			if (string.charAt(lastIdx) == first) {
				if (string.length() >= 3) {
					if (string.charAt(lastIdx - 1) == '\\' && string.charAt(lastIdx - 2) != '\\') {
						String value = convertFromN4JSString(string.substring(1), node, false);
						throw newN4JSValueConverterException(first, node, value);
					}
				}
				return convertFromN4JSString(string.substring(1, lastIdx), node, true);
			} else {
				String value = convertFromN4JSString(string.substring(1), node, false);
				throw newN4JSValueConverterException(first, node, value);
			}
		} catch (IllegalArgumentException e) {
			throw new ValueConverterException(e.getMessage(), node, e);
		}
	}

	/**
	 * Creates a new value converter exception.
	 */
	protected N4JSValueConverterWithValueException newN4JSValueConverterException(char c, INode node, String value) {
		if (c == '"')
			return new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_STRING_DOUBLE_QUOTE(),
					IssueCodes.VCO_STRING_DOUBLE_QUOTE, node, value, null);
		else
			return new N4JSValueConverterWithValueException(IssueCodes.getMessageForVCO_STRING_QUOTE(),
					IssueCodes.VCO_STRING_QUOTE, node, value, null);
	}
}
