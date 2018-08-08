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

import org.eclipse.n4js.tester.ui.TesterUiActivator;
import org.eclipse.n4js.utils.N4ExecutableExtensionFactory;

import com.google.inject.Injector;

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
		return TesterUiActivator.getInjector();
	}

	@Override
	protected String getBundleId() {
		return NodejsTesterUiActivator.PLUGIN_ID;
	}

}
