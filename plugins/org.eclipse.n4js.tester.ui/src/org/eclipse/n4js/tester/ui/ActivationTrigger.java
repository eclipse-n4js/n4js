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

/**
 * (copied from org.eclipse.n4js.runner.ui.ActivationTrigger)
 *
 * Dummy hook into Eclipse UI activation. Ensures that ui elements are loaded, otherwise things like
 * {@link IsTestableLocationPropertyTester} could be NOT called. This could happen when no hard dependencies (direct code
 * dependencies) are present, only weak dependency like reference to extension point in plugin.xml, thus laziness of the
 * platform could skip loading UI contributed code.
 *
 * @see <a
 *      href="http://www.robertwloch.net/2011/01/eclipse-tips-tricks-property-testers-with-command-core-expressions/">Early
 *      Startup Trigger</a>
 */
public class ActivationTrigger implements IStartup {

	@Override
	public void earlyStartup() {
		// NOOP
	}

}
