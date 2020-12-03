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
package org.eclipse.n4js.conversion;

import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;

/**
 * Converts a hashbang (a.k.a. shebang) to a string and back. Basically, the prefix '#!' is stripped/prepended.
 */
public class HashbangValueConverter implements IValueConverter<String> {

	@Override
	public String toValue(String string, INode node) throws ValueConverterException {
		if (string != null && string.startsWith(N4IDLGlobals.HASHBANG_PREFIX)) {
			return string.substring(2);
		}
		return string;
	}

	@Override
	public String toString(String value) throws ValueConverterException {
		return N4IDLGlobals.HASHBANG_PREFIX + value;
	}

}
