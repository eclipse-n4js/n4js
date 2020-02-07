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
package org.eclipse.n4js.parser;

import org.eclipse.n4js.conversion.AbstractN4JSStringValueConverter;
import org.eclipse.n4js.conversion.CompositeSyntaxErrorMessages;
import org.eclipse.n4js.conversion.LegacyOctalIntValueConverter;
import org.eclipse.n4js.conversion.N4JSStringValueConverter;
import org.eclipse.n4js.conversion.N4JSValueConverterWithValueException;
import org.eclipse.n4js.conversion.RegExLiteralConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.antlr.SyntaxErrorMessageProvider;

/**
 * <p>
 * A {@link SyntaxErrorMessageProvider} that is aware of special escape sequences in JS strings, regular expressions and
 * numbers that are bogus in the sense of the spec but commonly accepted by JS interpreters.
 * </p>
 *
 * <p>
 * Namely these are octal escapes without a leading zero, e.g. '\123', octal numbers and invalid regular expressions.
 * </p>
 */
public class BadEscapementAwareMessageProvider extends SyntaxErrorMessageProvider {

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage(
			IValueConverterErrorContext context) {
		ValueConverterException cause = context.getValueConverterException();
		if (cause instanceof N4JSStringValueConverter.BadEscapementException) {
			if (((N4JSStringValueConverter.BadEscapementException) cause).isError())
				return new SyntaxErrorMessage(context.getDefaultMessage(),
						AbstractN4JSStringValueConverter.ERROR_ISSUE_CODE);
			return new SyntaxErrorMessage(context.getDefaultMessage(),
					AbstractN4JSStringValueConverter.WARN_ISSUE_CODE);
		}
		if (cause instanceof LegacyOctalIntValueConverter.LeadingZerosException) {
			return new SyntaxErrorMessage(context.getDefaultMessage(), LegacyOctalIntValueConverter.ISSUE_CODE);
		}
		if (cause instanceof N4JSValueConverterWithValueException) {
			return CompositeSyntaxErrorMessages.toSyntaxErrorMessage((N4JSValueConverterWithValueException) cause,
					(vce) -> {
						if (vce instanceof RegExLiteralConverter.BogusRegExLiteralException) {
							return createRangedSyntaxErrorMessage(vce.getMessage(),
									RegExLiteralConverter.ISSUE_CODE,
									vce.getOffset(),
									vce.getLength());
						}
						String issueCode = vce.getIssueCode();
						if (vce.hasRange()) {
							return createRangedSyntaxErrorMessage(vce.getMessage(), issueCode, vce.getOffset(),
									vce.getLength());
						}
						return new SyntaxErrorMessage(vce.getMessage(), issueCode);
					});
		}
		return super.getSyntaxErrorMessage(context);
	}

	private SyntaxErrorMessage createRangedSyntaxErrorMessage(String message, String issueCode,
			int offset,
			int length) {
		String range = offset + ":" + length;
		return new SyntaxErrorMessage(message, issueCode, new String[] { range });
	}
}
