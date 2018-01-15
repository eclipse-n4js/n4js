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
package org.eclipse.n4js.n4jsx.transpiler.utils;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4jsx.ReactHelper;
import org.eclipse.n4js.ts.types.TModule;

import com.google.inject.Inject;

/**
 * Helper for working with JSX backends, e.g. Ract, Preact, etc. Internally it supports only React, but API wise should
 * work for other backends once their support is added. Therefore, choosing between JSX backends is not implemented at
 * this time, either.
 */
public final class JSXBackendHelper {

	private final static String REACT_ELEMENT_FACTORY_FUNCTION_NAME = "createElement";

	@Inject
	private ReactHelper reactHelper;

	/**
	 * Checks if given module is the module of the active JSX backend (currently only React).
	 */
	public boolean isJsxBackendModule(TModule module) {
		return reactHelper.isReactModule(module);
	}

	/**
	 * Returns the module of the active JSX backend (currently only React).
	 */
	public TModule getJsxBackendModule(Resource resource) {
		return reactHelper.lookUpReactTModule(resource);
	}

	/**
	 * Returns the name of the active JSX backend's element factory function. This function is expected to be exported
	 * by the {@link #getJsxBackendModule(Resource) JSX backend module}. Currently, this method always returns the name
	 * of React's element factory function, i.e. "createElement".
	 */
	public String getBackendElementFactoryFunctionName() {
		return REACT_ELEMENT_FACTORY_FUNCTION_NAME;
	}
}
