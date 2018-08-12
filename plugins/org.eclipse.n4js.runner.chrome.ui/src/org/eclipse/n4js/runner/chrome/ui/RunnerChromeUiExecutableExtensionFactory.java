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
package org.eclipse.n4js.runner.chrome.ui;

import static org.eclipse.n4js.runner.ui.RunnerUiModule.RUNNER_UI_MODULE_ID;

import org.eclipse.n4js.runner.ui.RunnerUiModule;
import org.eclipse.n4js.utils.N4ExecutableExtensionFactory;

import com.google.inject.Injector;

/**
 * Executable extension factory for the runner Chrome UI module.
 */
public class RunnerChromeUiExecutableExtensionFactory extends N4ExecutableExtensionFactory {

	@Override
	protected ClassLoader getClassLoader() {
		return this.getClass().getClassLoader();
	}

	@Override
	protected Injector getInjector() {
		return RunnerUiModule.getInjector(RUNNER_UI_MODULE_ID);
	}

	@Override
	protected String getBundleId() {
		return ChromeRunnerUiActivator.PLUGIN_ID;
	}

}
