/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.regex;

import org.eclipse.xtext.conversion.impl.INTValueConverter;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class RegularExpressionRuntimeModule extends AbstractRegularExpressionRuntimeModule {

	/**
	 * INT is a data type rule thus the specialized binding
	 */
	public Class<? extends INTValueConverter> bindINTValueConverter() {
		return RegExINTValueConverter.class;
	}

}
