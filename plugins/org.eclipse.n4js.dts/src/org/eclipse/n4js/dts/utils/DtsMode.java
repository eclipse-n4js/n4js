/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts.utils;

/**
 * Enumeration for specifying whether a .d.ts file is a {@link #SCRIPT} or {@link #MODULE}.
 * <p>
 * From the TypeScript handbook: "In TypeScript, any file containing a top-level import or export is considered a
 * module. Conversely, a file without any top-level import or export declarations is treated as a script whose contents
 * are available in the global scope (and therefore to modules as well)".
 */
public enum DtsMode {

	/** Script mode for .d.ts files. See {@link DtsMode}. */
	SCRIPT,

	/** Module mode for .d.ts files. See {@link DtsMode}. */
	MODULE
}
