/**
 * Copyright (c) 2018 NumberFour AG.
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

import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.ts.services.TypeExpressionsGrammarAccess;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

import com.google.inject.Inject;

/**
 * Converts a VERSION request '#INT' to the corresponding integer value.
 */
public class VersionRequestValueConverter implements IValueConverter<BigDecimal> {

	@Inject
	private IValueConverterService delegateService;
	@Inject
	private TypeExpressionsGrammarAccess grammarAccess;

	@Override
	public BigDecimal toValue(String string, INode node) throws ValueConverterException {
		final String withoutSeparator = string.replaceAll(N4IDLGlobals.VERSION_SEPARATOR, "");
		final BigDecimal delegatedResult = (BigDecimal) delegateService.toValue(withoutSeparator.trim(),
				grammarAccess.getINTRule().getName(), node);
		return delegatedResult;
	}

	@Override
	public String toString(BigDecimal value) throws ValueConverterException {
		return N4IDLGlobals.VERSION_SEPARATOR + value.toString();
	}

}
