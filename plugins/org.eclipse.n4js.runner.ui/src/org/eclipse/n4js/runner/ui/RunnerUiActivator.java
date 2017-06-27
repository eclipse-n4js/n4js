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
package org.eclipse.n4js.runner.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RunnerUiActivator extends AbstractUIPlugin {

	// The plug-in ID
	@SuppressWarnings("javadoc")
	public static final String PLUGIN_ID = "org.eclipse.n4js.runner.ui"; //$NON-NLS-1$

	// The shared instance
	private static RunnerUiActivator plugin;

	/**
	 * The constructor
	 */
	public RunnerUiActivator() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext )
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RunnerUiActivator getDefault() {
		return plugin;
	}

	/**
	 * Prints given message to eclipse error log
	 *
	 * @param msg
	 *            the message
	 * @param ex
	 *            the exception, may be null
	 */
	public static void logError(String msg, Throwable ex) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, ex));
	}

}
