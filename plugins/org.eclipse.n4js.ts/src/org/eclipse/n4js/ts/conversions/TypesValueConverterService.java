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
package org.eclipse.n4js.ts.conversions;

import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.impl.AbstractDeclarativeValueConverterService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Value converters for types, such as null and undef modifier converters.
 */
@Singleton
public class TypesValueConverterService extends AbstractDeclarativeValueConverterService {

	@Inject
	private IdentifierDelegateValueConverter identifierDelegateValueConverter;

	@Inject
	private ComputedPropertyNameValueConverter typesComputedPropertyNameValueConverter;

	/**
	 * Registers the value converter for the rule {@code TypesIdentifier}.
	 */
	@ValueConverter(rule = "TypesIdentifier")
	public IValueConverter<String> TypesIdentifier() {
		return identifierDelegateValueConverter;
	}

	/**
	 * Registers the value converter for the rule {@code TypesComputedPropertyName}.
	 */
	@ValueConverter(rule = "TypesComputedPropertyName")
	public IValueConverter<String> TypesComputedPropertyName() {
		return typesComputedPropertyNameValueConverter;
	}
}
