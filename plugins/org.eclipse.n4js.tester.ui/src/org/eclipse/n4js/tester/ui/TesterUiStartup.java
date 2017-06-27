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

import org.eclipse.ui.IStartup;

import org.eclipse.n4js.tester.TesterModule;

/**
 * Hack to activate {@code tester.core} module which will trigger automatic embedded HTTP server instance activation.
 */
public class TesterUiStartup implements IStartup {

	@Override
	public void earlyStartup() {
		if (null != TesterModule.N4_TESTER_MODULE_ID) {
			// this is just a hack to ensure embedded HTTP server is running when starting application.
		}
	}

}
