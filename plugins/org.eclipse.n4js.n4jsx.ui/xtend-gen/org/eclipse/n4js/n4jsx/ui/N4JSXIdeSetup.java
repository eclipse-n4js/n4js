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
package org.eclipse.n4js.n4jsx.ui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.n4js.n4jsx.N4JSXRuntimeModule;
import org.eclipse.n4js.n4jsx.N4JSXStandaloneSetup;
import org.eclipse.n4js.n4jsx.ui.N4JSXIdeModule;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
@SuppressWarnings("all")
public class N4JSXIdeSetup extends N4JSXStandaloneSetup {
  @Override
  public Injector createInjector() {
    N4JSXRuntimeModule _n4JSXRuntimeModule = new N4JSXRuntimeModule();
    N4JSXIdeModule _n4JSXIdeModule = new N4JSXIdeModule();
    return Guice.createInjector(Modules2.mixin(_n4JSXRuntimeModule, _n4JSXIdeModule));
  }
}
