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
import org.eclipse.n4js.conversion.LegacyOctalIntValueConverter;
import org.eclipse.n4js.conversion.N4JSStringValueConverter;
import org.eclipse.n4js.conversion.N4JSValueConverterWithValueException;
import org.eclipse.n4js.conversion.RegExLiteralConverter;
import org.eclipse.n4js.conversion.RegExLiteralConverter.BogusRegExLiteralException;
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
		if (cause instanceof RegExLiteralConverter.BogusRegExLiteralException) {
			RegExLiteralConverter.BogusRegExLiteralException casted = (BogusRegExLiteralException) cause;
			return createRangedSyntaxErrorMessage(context, RegExLiteralConverter.ISSUE_CODE, casted.getOffset(),
					casted.getLength());
		}
		if (cause instanceof N4JSValueConverterWithValueException) {
			N4JSValueConverterWithValueException casted = (N4JSValueConverterWithValueException) cause;
			String issueCode = casted.getIssueCode();
			if (casted.hasRange()) {
				return createRangedSyntaxErrorMessage(context, issueCode, casted.getOffset(), casted.getLength());
			}
			return new SyntaxErrorMessage(context.getDefaultMessage(), issueCode);
		}
		return super.getSyntaxErrorMessage(context);
	}

	private SyntaxErrorMessage createRangedSyntaxErrorMessage(IValueConverterErrorContext context, String issueCode,
			int offset,
			int length) {
		String range = offset + ":" + length;
		return new SyntaxErrorMessage(context.getDefaultMessage(), issueCode, new String[] { range });
	}
}
