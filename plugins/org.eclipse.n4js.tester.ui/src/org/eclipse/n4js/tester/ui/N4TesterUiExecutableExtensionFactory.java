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
package org.eclipse.n4js.tester.ui;

import static org.eclipse.n4js.tester.ui.TesterUiActivator.PLUGIN_ID;

import org.eclipse.n4js.utils.N4ExecutableExtensionFactory;

import com.google.inject.Injector;

/**
 * Executable extension factory for the N4 tester UI component.
 */
public class N4TesterUiExecutableExtensionFactory extends N4ExecutableExtensionFactory {

	@Override
	protected ClassLoader getClassLoader() {
		return TesterUiActivator.getDefault().getClass().getClassLoader();
	}

	@Override
	protected Injector getInjector() {
		return TesterUiActivator.getInjector();
	}

	@Override
	protected String getBundleId() {
		return PLUGIN_ID;
	}

}
