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
package org.eclipse.n4js.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.n4js.TypeExpressionsRuntimeModule;
import org.eclipse.n4js.TypeExpressionsStandaloneSetup;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
public class TypeExpressionsIdeSetup extends TypeExpressionsStandaloneSetup {

	@Override
	public Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new TypeExpressionsRuntimeModule(), new TypeExpressionsIdeModule()));
	}
	
}
