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
package org.eclipse.n4js.parser.conversion;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.Strings;

/**
 * A value converter that properly converts octal JS numbers to {@link BigDecimal}.
 */
public class LegacyOctalIntValueConverter extends AbstractLexerBasedConverter<BigDecimal> {

	/**
	 * An issue code for a number with leading zeros, which is discouraged.
	 */
	public static final String ISSUE_CODE = "N4JSStringValueConverter.bad.escapement.warn";

	/**
	 * A value converter exception that indicates a discouraged concrete syntax.
	 */
	public static class LeadingZerosException extends N4JSValueConverterWithValueException {

		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new exception for the given node that carries the value and message.
		 */
		public LeadingZerosException(String message, String issueCode, INode node, BigDecimal value) {
			super(message, issueCode, node, value, null);
		}

	}

	@Override
	protected String toEscapedString(BigDecimal value) {
		return "0" + value.toBigInteger().toString(8);
	}

	@Override
	protected void assertValidValue(BigDecimal value) {
		super.assertValidValue(value);
		if (value.signum() == -1)
			throw new N4JSValueConverterException(IssueCodes.VCO_OCTALINT_NEGATIVE.getMessage(getRuleName(), value),
					IssueCodes.VCO_OCTALINT_NEGATIVE.name(), null, null);
	}

	@Override
	public BigDecimal toValue(String string, INode node) {
		if (Strings.isEmpty(string))
			throw new N4JSValueConverterException(IssueCodes.VCO_OCTALINT_CONVERT_EMPTY_STR.getMessage(),
					IssueCodes.VCO_OCTALINT_CONVERT_EMPTY_STR.name(), node, null);
		if (string.length() <= 1) {
			throw new N4JSValueConverterWithValueException(
					IssueCodes.VCO_OCTALINT_CONVERT_TOO_SHORT.getMessage(string),
					IssueCodes.VCO_OCTALINT_CONVERT_TOO_SHORT.name(), node,
					BigDecimal.ZERO, null);
		}
		try {
			return new BigDecimal(new BigInteger(string, 8));
		} catch (NumberFormatException e) {
			try {
				BigDecimal result = new BigDecimal(new BigInteger(string, 10));
				throw new LeadingZerosException(IssueCodes.VCO_OCTALINT_LEADING_ZEROS.getMessage(string),
						IssueCodes.VCO_OCTALINT_LEADING_ZEROS.name(), node, result);
			} catch (NumberFormatException again) {
				throw new N4JSValueConverterException(IssueCodes.VCO_OCTALINT_CONVERT_STR.getMessage(string),
						IssueCodes.VCO_OCTALINT_CONVERT_STR.name(), node, null);
			}
		}
	}

}
