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
package org.eclipse.n4js.ui.utils;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.google.inject.Injector;
import com.google.inject.Singleton;

import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.utils.injector.AbstractGuiceUIPlugin;

/**
 * N4JS specific Guice aware UI plug-in.
 *
 * <p>
 * This {@code Guice} aware {@link AbstractUIPlugin} implementation provides a convenient way to supply a shared
 * injector instance for the plug-in by creating and caching a new injector instance that is a child of the main
 * {@code N4JS} injector acquired from the {@link N4JSActivator}. The injector (supplied by this class) will inherit all
 * the bindings and singletons from its parent injector. That means any singleton instances (when the declaring class of
 * the instances is annotated with {@link Singleton}) will be shared by the parent and the child injectors as well.
 *
 */
public class N4JSGuiceUIPlugin extends AbstractGuiceUIPlugin {

	@Override
	protected Injector getParentInjector(final String id) {
		return N4JSActivator.getInstance().getInjector(id);
	}

	/**
	 * Returns with the injector for the {@code N4JS} language. This method has the same effect as calling
	 * {@link #getInjector(String)} with the {@link N4JSActivator#ORG_ECLIPSE_N4JS_N4JS} argument.
	 *
	 * @return returns with the shared injector instance for the current plug-in. The returning injector is the child
	 *         injector of the main {@code N4JS} injector acquired from the {@link N4JSActivator} and it inherits all
	 *         the bindings and all the singletons from its parent.
	 */
	public Injector getN4JSChildInjector() {
		return getInjector(N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
	}

}
