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

import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.antlr.SyntaxErrorMessageProvider;

import org.eclipse.n4js.conversion.AbstractN4JSStringValueConverter;
import org.eclipse.n4js.conversion.N4JSStringValueConverter;
import org.eclipse.n4js.conversion.LegacyOctalIntValueConverter;
import org.eclipse.n4js.conversion.RegExLiteralConverter;

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
				return new SyntaxErrorMessage(context.getDefaultMessage(), AbstractN4JSStringValueConverter.ERROR_ISSUE_CODE);
			return new SyntaxErrorMessage(context.getDefaultMessage(), AbstractN4JSStringValueConverter.WARN_ISSUE_CODE);
		}
		if (cause instanceof LegacyOctalIntValueConverter.LeadingZerosException) {
			return new SyntaxErrorMessage(context.getDefaultMessage(), LegacyOctalIntValueConverter.ISSUE_CODE);
		}
		if (cause instanceof RegExLiteralConverter.BogusRegExLiteralException) {
			return new SyntaxErrorMessage(context.getDefaultMessage(), RegExLiteralConverter.ISSUE_CODE);
		}
		return super.getSyntaxErrorMessage(context);
	}
}
