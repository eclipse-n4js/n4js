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
package org.eclipse.n4js;

import java.lang.reflect.Field;

import org.eclipse.n4js.utils.N4JSLanguageUtils;

/**
 * JS support is turned by default. To test that the N4JS parser still supports JS, the support for JS modules can be
 * activated again using this utility class.
 */
public class JSActivationUtil {

	/** Enables JS support. Should only be used for test cases. */
	public static void enableJSSupport() {
		try {
			Field declaredField = N4JSLanguageUtils.class.getDeclaredField("OPAQUE_JS_MODULES");
			declaredField.setAccessible(true);
			declaredField.set(null, false);
			declaredField.setAccessible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
