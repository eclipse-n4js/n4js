/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui;

import static org.eclipse.n4js.ui.internal.N4JSActivator.ORG_ECLIPSE_N4JS_N4JS;

import org.eclipse.n4js.ui.internal.N4JSActivator;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * This provides methods to delegate dependency injection bindings to other injectors.
 */
public class DependencyDelegator {

	static class DependencyDelegatorN4JS<T> implements Provider<T> {
		final private Class<T> clazzT;

		/** Constructor */
		DependencyDelegatorN4JS(Class<T> clazzT) {
			this.clazzT = clazzT;
		}

		@Override
		public T get() {
			final Injector injector = N4JSActivator.getInstance().getInjector(ORG_ECLIPSE_N4JS_N4JS);
			return injector.getInstance(clazzT);
		}
	}

	/** The given class will be delegated to the injector of N4JS */
	static public void toN4JS(Binder binder, Class<?> clazzT) {
		toN4JS(binder, clazzT, clazzT);
	}

	/** The given class {@code fromClazzS} will be delegated to the class {@code toClazzT} of the injector of N4JS */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static public void toN4JS(Binder binder, Class<?> fromClazzS, Class<?> toClazzT) {
		binder.bind(fromClazzS).toProvider(new DependencyDelegatorN4JS(toClazzT));
	}
}
