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
package org.eclipse.n4js.ui;

import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.utils.N4JSInjectorSupplier;

import com.google.inject.Injector;

/**
 * Delegate to {@link N4JSInjectorSupplier} to catch some weird state iff {@link N4JSActivator} is null.
 */
public class GracefulN4JSExecutableExtensionFactory extends N4JSExecutableExtensionFactory {

	@Override
	protected Injector getInjector() {
		return new N4JSInjectorSupplier().get();
	}

}
