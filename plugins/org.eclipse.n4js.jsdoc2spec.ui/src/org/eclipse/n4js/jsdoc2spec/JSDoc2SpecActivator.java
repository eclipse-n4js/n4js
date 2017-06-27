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
package org.eclipse.n4js.jsdoc2spec;

import org.eclipse.n4js.ui.utils.N4JSGuiceUIPlugin;

/**
 * Activator for JSDoc2Spec in order to set up injector correctly.
 */
public class JSDoc2SpecActivator extends N4JSGuiceUIPlugin {

	/**
	 * Returns with the shared activator singleton.
	 *
	 * @return the shared activator instance.
	 */
	public static JSDoc2SpecActivator getInstance() {
		return (JSDoc2SpecActivator) INSTANCE;
	}

}
