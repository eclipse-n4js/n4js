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

import org.eclipse.n4js.runner.nodejs.NodeRunner;
import org.eclipse.n4js.tester.ui.AbstractTesterLaunchConfigurationDelegate;

/**
 */
public class NodejsTesterLaunchConfigurationDelegate extends AbstractTesterLaunchConfigurationDelegate {

	@Override
	public String getRunnerId() {
		return NodeRunner.ID;
	}
}
