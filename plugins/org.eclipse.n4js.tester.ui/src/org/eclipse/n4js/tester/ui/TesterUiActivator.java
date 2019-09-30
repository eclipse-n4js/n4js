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

import static com.google.common.base.Strings.nullToEmpty;
import static org.eclipse.core.runtime.IStatus.ERROR;

import java.io.File;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * The activator class controls the plug-in life cycle.
 */
@SuppressWarnings("javadoc")
public class TesterUiActivator extends AbstractUIPlugin {

	/**
	 * The unique ID of the N4 Tester UI activator. <br>
	 * {@value}
	 */
	public static final String PLUGIN_ID = "org.eclipse.n4js.tester.ui"; //$NON-NLS-1$

	/**
	 * Icon folder.
	 */
	private static final String PATH_TO_ICONS = "icons" + File.separator;

	public static final String ICON_TEST = "test.gif";
	public static final String ICON_TEST_SKIPPED = "testignored.gif";
	public static final String ICON_TEST_SKIPPED_NOT_IMPLEMENTED_YET = "testassumptionfailed.gif";
	public static final String ICON_TEST_PASSED = "testok.gif";
	public static final String ICON_TEST_FAILED = "testfail.gif";
	public static final String ICON_TEST_ERROR = "testerr.gif";
	public static final String ICON_TEST_RUNNING = "testrunning.gif";

	public static final String ICON_SUITE = "tsuite.gif";
	public static final String ICON_SUITE_SKIPPED = "tsuiteignored.gif";
	public static final String ICON_SUITE_PASSED = "tsuiteok.gif";
	public static final String ICON_SUITE_FAILED = "tsuitefail.gif";
	public static final String ICON_SUITE_ERROR = "tsuiteerr.gif";
	public static final String ICON_SUITE_RUNNING = "tsuiterunning.gif";

	public static final String ICON_LOCK = "lock.gif";
	public static final String ICON_LAUNCHCONFIG = "launchconfig.gif";
	public static final String ICON_RELAUNCH = "relaunch.gif";
	public static final String ICON_RELAUNCH_FAILED = "relaunchf.gif";
	public static final String ICON_STOP = "stop.gif";

	public static final String ICON_HISTORY = "history_list.gif";

	public static final String ICON_TH_VERTICAL = "th_vertical.gif";
	public static final String ICON_TH_HORIZONTAL = "th_horizontal.gif";
	public static final String ICON_TH_AUTOMATIC = "th_automatic.gif";

	public static final String ICON_SHOW_FAILURES_ONLY = "failures.gif";
	public static final String ICON_SHOW_SKIPPED_ONLY = "testignored.gif";

	// The shared instance
	private static TesterUiActivator plugin;

	private Injector injector;

	/**
	 * Sole constructor.
	 */
	public TesterUiActivator() {
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		Injector parentInjector = N4JSActivator.getInstance().getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
		TesterModule testerModule = new TesterModule(parentInjector);
		TesterUiModule testerUiModule = new TesterUiModule(parentInjector);
		injector = Guice.createInjector(testerModule, testerUiModule);
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		injector = null;
		plugin = null;
		super.stop(context);
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		reg.put(ICON_TEST, imageDescriptor(ICON_TEST));
		reg.put(ICON_TEST_SKIPPED, imageDescriptor(ICON_TEST_SKIPPED));
		reg.put(ICON_TEST_SKIPPED_NOT_IMPLEMENTED_YET, imageDescriptor(ICON_TEST_SKIPPED_NOT_IMPLEMENTED_YET));
		reg.put(ICON_TEST_PASSED, imageDescriptor(ICON_TEST_PASSED));
		reg.put(ICON_TEST_FAILED, imageDescriptor(ICON_TEST_FAILED));
		reg.put(ICON_TEST_ERROR, imageDescriptor(ICON_TEST_ERROR));
		reg.put(ICON_TEST_RUNNING, imageDescriptor(ICON_TEST_RUNNING));
		reg.put(ICON_SUITE, imageDescriptor(ICON_SUITE));
		reg.put(ICON_SUITE_SKIPPED, imageDescriptor(ICON_SUITE_SKIPPED));
		reg.put(ICON_SUITE_PASSED, imageDescriptor(ICON_SUITE_PASSED));
		reg.put(ICON_SUITE_FAILED, imageDescriptor(ICON_SUITE_FAILED));
		reg.put(ICON_SUITE_ERROR, imageDescriptor(ICON_SUITE_ERROR));
		reg.put(ICON_SUITE_RUNNING, imageDescriptor(ICON_SUITE_RUNNING));
		reg.put(ICON_LOCK, imageDescriptor(ICON_LOCK));
		reg.put(ICON_LAUNCHCONFIG, imageDescriptor(ICON_LAUNCHCONFIG));
		reg.put(ICON_RELAUNCH, imageDescriptor(ICON_RELAUNCH));
		reg.put(ICON_RELAUNCH_FAILED, imageDescriptor(ICON_RELAUNCH_FAILED));
		reg.put(ICON_STOP, imageDescriptor(ICON_STOP));
		reg.put(ICON_HISTORY, imageDescriptor(ICON_HISTORY));
		reg.put(ICON_TH_HORIZONTAL, imageDescriptor(ICON_TH_HORIZONTAL));
		reg.put(ICON_TH_VERTICAL, imageDescriptor(ICON_TH_VERTICAL));
		reg.put(ICON_TH_AUTOMATIC, imageDescriptor(ICON_TH_AUTOMATIC));
		reg.put(ICON_SHOW_FAILURES_ONLY, imageDescriptor(ICON_SHOW_FAILURES_ONLY));
		reg.put(ICON_SHOW_SKIPPED_ONLY, imageDescriptor(ICON_SHOW_SKIPPED_ONLY));
	}

	public ImageDescriptor imageDescriptor(final String path) {
		return ResourceLocator.imageDescriptorFromBundle(PLUGIN_ID, PATH_TO_ICONS + path).orElse(null);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance.
	 */
	public static TesterUiActivator getDefault() {
		return plugin;
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
		getDefault().getLog().log(new Status(ERROR, PLUGIN_ID, nullToEmpty(message), t));
	}

	public static final Image getImage(String key) {
		return getDefault().getImageRegistry().get(key);
	}

	public static final ImageDescriptor getImageDescriptor(String key) {
		return getDefault().getImageRegistry().getDescriptor(key);
	}

	public static Injector getInjector() {
		return getDefault().injector;
	}
}
