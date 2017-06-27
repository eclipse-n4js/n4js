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
package org.eclipse.n4js.naming;

import org.eclipse.xtext.naming.IQualifiedNameConverter;

import com.google.inject.Singleton;

/**
 * Converter separating segments of a qualified name with slashes, as used in module and type specifiers. See N4JS spec,
 * Chapter 3.4. Qualified Names
 */
@Singleton
public class SpecifierConverter extends IQualifiedNameConverter.DefaultImpl {

	@Override
	public String getDelimiter() {
		return "/";
	}
}
