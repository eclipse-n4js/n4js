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

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

/**
 */
public abstract class AbstractTemplateSegmentValueConverter extends AbstractN4JSStringValueConverter {

	/**
	 * Asserts that the given value is a valid raw template string, e.g. it does not contain the '`' character or '${'
	 * sequence.
	 */
	@Override
	protected void assertValidValue(String value) {
		super.assertValidValue(value);
		for (int i = 1; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == '`') {
				throw invalidRawTemplateString(value);
			} else if (c == '$') {
				if (i + 1 < value.length() && value.charAt(i + 1) == '{') {
					throw invalidRawTemplateString(value);
				}
			}
		}
	}

	/**
	 * Creates a new {@link ValueConverterException} because the given value is not a valid raw template string, e.g. it
	 * contains the '`' character or '${' sequence.
	 */
	private ValueConverterException invalidRawTemplateString(String value) {
		return new ValueConverterException("Cannot express the value '" + value + "' as a raw template string.",
				null, null);
	}

	@Override
	public String toValue(String string, INode node) {
		if (string == null)
			return null;
		try {
			int len = string.length();
			int leftLen = getLeftDelimiter().length();
			if (len == leftLen) {
				throw newN4JSValueConverterException(node, "");
			}
			int lastIdx = len - getRightDelimiter().length();
			if (string.startsWith(getLeftDelimiter()) && string.endsWith(getRightDelimiter())) {
				int minLen = leftLen + getRightDelimiter().length();
				if (len > minLen) {
					if (string.charAt(lastIdx - 1) == '\\'
							&& (len == minLen + 1 || string.charAt(lastIdx - 2) != '\\')) {
						String value = convertFromN4JSString(string.substring(leftLen), node, false);
						throw newN4JSValueConverterException(node, value);
					}
				}
				return convertFromN4JSString(string.substring(leftLen, lastIdx), node, true);
			} else {
				String value = convertFromN4JSString(string.substring(leftLen), node, false);
				throw newN4JSValueConverterException(node, value);
			}
		} catch (IllegalArgumentException e) {
			throw new ValueConverterException(e.getMessage(), node, e);
		}
	}

	/**
	 * Creates a new value converter exception.
	 */
	protected abstract N4JSValueConverterWithValueException newN4JSValueConverterException(INode node,
			String value);

}
