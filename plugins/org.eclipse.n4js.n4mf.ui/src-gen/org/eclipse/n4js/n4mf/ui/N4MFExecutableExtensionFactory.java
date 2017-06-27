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
package org.eclipse.n4js.n4mf.ui;

import com.google.inject.Injector;
import org.eclipse.n4js.n4mf.ui.internal.N4MFActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class N4MFExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return N4MFActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return N4MFActivator.getInstance().getInjector(N4MFActivator.ORG_ECLIPSE_N4JS_N4MF_N4MF);
	}
	
}
