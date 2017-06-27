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
package org.eclipse.n4js.ui.utils;

import static org.eclipse.n4js.ui.internal.N4JSActivator.ORG_ECLIPSE_N4JS_N4JS;

import com.google.inject.Injector;

import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.utils.injector.InjectorSupplier;

/**
 * Supplies the injector for the N4JS language.
 */
public class N4JSInjectorSupplier implements InjectorSupplier {

	@Override
	public Injector get() {
		return N4JSActivator.getInstance().getInjector(getInjectorId());
	}

	@Override
	public String getInjectorId() {
		return ORG_ECLIPSE_N4JS_N4JS;
	}

}
