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
package org.eclipse.n4js.ui.internal;

/**
 * Gets thrown when querying an injector while this injector is created.
 */
public class InjectorNotYetAvailableException extends RuntimeException {
	InjectorNotYetAvailableException() {
		super("Creating an injector while creating an injector!");
	}
}
