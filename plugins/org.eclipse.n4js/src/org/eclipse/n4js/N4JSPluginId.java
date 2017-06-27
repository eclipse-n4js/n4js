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
package org.eclipse.n4js;

/**
 * This class is to have a constant for the plug-in ID, instead of using the string everywhere in the code.
 */
public abstract class N4JSPluginId {

	/**
	 * The unique plug-in identifier for the N4JS core bundle.
	 */
	public static final String N4JS_PLUGIN_ID = "org.eclipse.n4js";

	private N4JSPluginId() {
		//
	}

}
