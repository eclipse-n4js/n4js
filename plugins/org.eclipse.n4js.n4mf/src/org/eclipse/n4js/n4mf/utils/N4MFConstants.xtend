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
package org.eclipse.n4js.n4mf.utils

/**
 */
abstract class N4MFConstants {

	/**
	 * The extension of the N4JS manifest file.
	 */
	public val static N4MF_FILE_EXTENSION = 'n4mf';

	/**
	 * The (raw) name of the N4JS manifest file.
	 */
	public val static N4MF_FILE_NAME = 'manifest';

	/**
	 * The name (with extension) of the N4JS manifest file.
	 */
	public val static N4MF_MANIFEST = 'manifest.n4mf';

	/**
	 * The name (with extension) of the manifest fragment file.
	 */
	public val static MANIFEST_FRAGMENT = "fragment.n4mf";

	private new() {	}
}
