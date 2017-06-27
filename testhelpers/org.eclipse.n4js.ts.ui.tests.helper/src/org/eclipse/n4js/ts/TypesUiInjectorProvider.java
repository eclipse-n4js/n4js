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
package org.eclipse.n4js.ts;

import org.eclipse.xtext.junit4.IInjectorProvider;

import com.google.inject.Injector;

/***/
public class TypesUiInjectorProvider implements IInjectorProvider {

	@Override
	public Injector getInjector() {
		return org.eclipse.n4js.ts.ui.internal.TypesActivator.getInstance().getInjector("org.eclipse.n4js.ts.model");
	}

}
