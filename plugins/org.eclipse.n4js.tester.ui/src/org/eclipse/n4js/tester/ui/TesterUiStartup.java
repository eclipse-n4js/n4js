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

import org.eclipse.n4js.tester.internal.TesterActivator;
import org.eclipse.ui.IStartup;

/**
 * Hack to activate {@code tester.core} module which will trigger automatic embedded HTTP server instance activation.
 */
public class TesterUiStartup implements IStartup {

	@Override
	public void earlyStartup() {
		// this is just a hack to ensure embedded HTTP server is running when starting application.
		TesterActivator.getInstance().startupWithInjector(TesterUiActivator.getInjector());
	}

}
