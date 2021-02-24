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
package org.eclipse.n4js.xtext.server.util;

/**
 * This helper provides methods for registering extensions in headless case. This should become obsolete as the
 * extensions are registered by a service loaded in the headless case.
 */
public interface IHeadlessExtensionRegistrationHelper {

	/**
	 * Register extensions manually. This method should become obsolete when extension point fully works in headless
	 * case.
	 */
	public void registerExtensions();

	/**
	 * Unregister all extensions.
	 */
	public void unregisterExtensions();
}
