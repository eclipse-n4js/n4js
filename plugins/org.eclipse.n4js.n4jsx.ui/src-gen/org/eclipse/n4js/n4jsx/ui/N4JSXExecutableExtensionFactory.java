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

import com.google.inject.Injector;
import org.eclipse.n4js.n4jsx.ui.internal.N4JSXActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class N4JSXExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return N4JSXActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return N4JSXActivator.getInstance().getInjector(N4JSXActivator.ORG_ECLIPSE_N4JS_N4JSX_N4JSX);
	}
	
}
