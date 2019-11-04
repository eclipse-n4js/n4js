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
package org.eclipse.n4js.product;

import static org.eclipse.core.runtime.IStatus.ERROR;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.common.base.Strings;

/**
 * The activator class controls the plug-in life cycle.
 */
public class N4ProductActivator extends AbstractUIPlugin {

	/**
	 * The unique ID of the N4JS IDE product activator. <br>
	 * {@value}
	 */
	public static final String PLUGIN_ID = "org.eclipse.n4js.product"; //$NON-NLS-1$

	// The shared instance
	private static N4ProductActivator plugin;

	/**
	 * Sole constructor.
	 */
	public N4ProductActivator() {
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance.
	 */
	public static N4ProductActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path.
	 *
	 * @param path
	 *            the path.
	 * @return the image descriptor.
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		return ResourceLocator.imageDescriptorFromBundle(PLUGIN_ID, path).orElse(null);
	}

	/**
	 * Logs the given message and throwable to the platform log.
	 *
	 * @param message
	 *            A high level UI message describing when the problem happened.
	 * @param t
	 *            The throwable from where the problem actually occurred.
	 */
	public static void log(final String message, final Throwable t) {
		getDefault().getLog().log(new Status(ERROR, PLUGIN_ID, Strings.nullToEmpty(message), t));
	}
}
