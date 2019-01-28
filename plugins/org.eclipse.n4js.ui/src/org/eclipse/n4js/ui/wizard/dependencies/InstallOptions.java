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
package org.eclipse.n4js.ui.wizard.dependencies;

/**
 * Helper value object for passing captured install options between UI and code responsible for installation.
 */
public class InstallOptions {

	/** String representation of the {@code .npmrc} path. Can be empty to indicate no special setting. */
	public String npmrc = "";

}
