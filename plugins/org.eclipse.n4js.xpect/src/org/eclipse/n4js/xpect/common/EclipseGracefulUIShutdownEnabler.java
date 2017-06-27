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
package org.eclipse.n4js.xpect.common;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Helper to enable logging for UI-Tests. {@link #enableOnce() should be called early while setting up the test}
 */
public class EclipseGracefulUIShutdownEnabler {

	private static boolean logged = false;

	/**
	 * Called to eagerly configure the logging-mechanism.
	 */
	public static void enableOnce() {
		if (logged)
			return;
		// workaround for shutdown-stack-traces due to non-initialized loggers.
		// @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=460863
		ResourcesPlugin.getPlugin().getLog()
				.log(new Status(IStatus.OK, ResourcesPlugin.PI_RESOURCES,
						"Place holder to init log-system. Loaded by " + EclipseGracefulUIShutdownEnabler.class.getName()
								+ " @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=460863 "));

		// without actual logging the following line is enough (but restricted):
		// StatusHandlerRegistry.getDefault().getDefaultHandlerDescriptor();

		logged = true;
	}
}
