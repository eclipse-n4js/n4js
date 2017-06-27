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
package org.eclipse.n4js.generator.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 */
public class GeneratorUiActivator extends AbstractUIPlugin {
	/** identifier */
	public static final String PLUGIN_ID = "org.eclipse.n4js.generator.ui"; //$NON-NLS-1$
	private static GeneratorUiActivator INSTANCE;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * @return instance of the started plug-in
	 */
	public static GeneratorUiActivator getInstance() {
		return INSTANCE;
	}
}
