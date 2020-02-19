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

import org.eclipse.n4js.conversion.ValueConverterUtils.CharacterValidityChecker;
import org.eclipse.n4js.conversion.ValueConverterUtils.StringConverterResult;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.IDValueConverter;
import org.eclipse.xtext.nodemodel.INode;

/**
 * A value converter that converts N4JSIdentifiers to string values.
 *
 * It is aware of unicode escape sequences.
 */
public class IdentifierValueConverter extends IDValueConverter {

	@Override
	protected String toEscapedString(String value) {
		return ValueConverterUtils.convertToEscapedString(value, false);
	}

	@Override
	public String toValue(String string, INode node) {
		if (string == null)
			return null;
		try {
			String result = convertFromN4JSIdentifier(string, node);
			return result;
		} catch (IllegalArgumentException e) {
			throw new ValueConverterException(e.getMessage(), node, e);
		}
	}

	/**
	 * Made public only for testing.
	 */
	public static String convertFromN4JSIdentifier(String jsString, INode node) {
		CharacterValidityChecker validityChecker = (char ch, int offset) -> {
			if (offset == 0) {
				if (!N4JSLanguageUtils.isValidIdentifierStart(ch)) {
					return false;
				}
			} else {
				if (!N4JSLanguageUtils.isValidIdentifierPart(ch)) {
					return false;
				}
			}
			return true;
		};
		StringConverterResult result = ValueConverterUtils.convertFromEscapedString(jsString, false, false, true,
				validityChecker);
		if (result.hasError()) {
			throw new N4JSValueConverterWithValueException(
					IssueCodes.getMessageForVCO_IDENT_ESCAPE_SEQ(jsString, result.getErrorOffset()),
					IssueCodes.VCO_IDENT_ESCAPE_SEQ,
					node, result.getValue(), null);
		}
		if (result.hasInvalidChar()) {
			if (result.getValue().length() != 0)
				throw new N4JSValueConverterWithValueException(
						IssueCodes.getMessageForVCO_IDENT_ILLEGAL_CHAR_WITH_RESULT(result.getValue(), jsString,
								result.getInvalidCharOffset()),
						IssueCodes.VCO_IDENT_ILLEGAL_CHAR_WITH_RESULT,
						node, result.getValue(), null);
			else
				throw new N4JSValueConverterException(IssueCodes.getMessageForVCO_IDENT_ILLEGAL_CHAR(
						jsString, result.getInvalidCharOffset()),
						IssueCodes.VCO_IDENT_ILLEGAL_CHAR,
						node, null);
		}
		return result.getValue();
	}
}
