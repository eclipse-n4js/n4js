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
package org.eclipse.n4js.utils.injector;

import com.google.common.base.Supplier;
import com.google.inject.Injector;

/**
 * Representation of an injector supplier.
 */
public interface InjectorSupplier extends Supplier<Injector> {

	/**
	 * The unique extension point identifier that is used to register the concrete injector supplier implementations.
	 */
	String EXTENSION_POINT_ID = "org.eclipse.n4js.utils.injectorSupplier";

	/**
	 * Returns with the unique identifier of the provided injector.
	 *
	 * @return the unique ID of the provided injector.
	 */
	String getInjectorId();

}
