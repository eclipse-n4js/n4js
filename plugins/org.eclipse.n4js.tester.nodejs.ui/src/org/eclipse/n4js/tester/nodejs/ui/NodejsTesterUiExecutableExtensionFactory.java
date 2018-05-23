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
package org.eclipse.n4js.tester.nodejs.ui;

import static org.eclipse.n4js.tester.ui.N4TesterUiModule.N4_TESTER_UI_MODULE_ID;

import com.google.inject.Injector;

import org.eclipse.n4js.tester.ui.N4TesterUiModule;
import org.eclipse.n4js.utils.N4ExecutableExtensionFactory;

/**
 * Executable extension factory for the Node.js tester UI.
 */
public class NodejsTesterUiExecutableExtensionFactory extends N4ExecutableExtensionFactory {

	@Override
	protected ClassLoader getClassLoader() {
		return NodejsTesterUiActivator.getDefault().getClass().getClassLoader();
	}

	@Override
	protected Injector getInjector() {
		return N4TesterUiModule.getInjector(N4_TESTER_UI_MODULE_ID);
	}

	@Override
	protected String getBunleId() {
		return NodejsTesterUiActivator.PLUGIN_ID;
	}

}
