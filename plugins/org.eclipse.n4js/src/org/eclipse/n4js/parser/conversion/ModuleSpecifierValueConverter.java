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

import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

import com.google.inject.Inject;

import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.services.N4JSGrammarAccess;

/**
 * Converts the module specifier used in import declarations into qualified module names.
 */
public class ModuleSpecifierValueConverter implements IValueConverter<String> {

	private static final String DELIMITER_MODULE_SPECIFIER = "/";
	private static final String DELIMITER_INTERNAL = N4JSQualifiedNameConverter.DELIMITER;
	private static final boolean IS_CONVERSION_REQUIRED = !DELIMITER_MODULE_SPECIFIER.equals(DELIMITER_INTERNAL);

	@Inject
	private IValueConverterService delegateService;
	@Inject
	private N4JSGrammarAccess grammarAccess;

	/**
	 * Converts from a Javascript module specifier (the string after "from" in import) to an internal qualified name.
	 */
	@Override
	public String toValue(String string, INode node) throws ValueConverterException {
		String withDots = IS_CONVERSION_REQUIRED ? string.replace(DELIMITER_MODULE_SPECIFIER, DELIMITER_INTERNAL)
				: string;
		return (String) delegateService.toValue(withDots, grammarAccess.getSTRINGRule().getName(), node);
	}

	/**
	 * Converts from an internal qualified name to a Javascript module specifier (the string after "from" in import).
	 */
	@Override
	public String toString(String value) throws ValueConverterException {
		String withSlashes = IS_CONVERSION_REQUIRED ? value.replace(DELIMITER_INTERNAL, DELIMITER_MODULE_SPECIFIER)
				: value;
		return delegateService.toString(withSlashes, grammarAccess.getSTRINGRule().getName());
	}
}
