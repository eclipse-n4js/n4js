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
package org.eclipse.n4js.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * The class {@link InjectorCollector} is only instantiated in contributing modules to identify the shared
 * injector.Later, the method {@link #getSharedInjectors()} can be used to retrieve all shared injectors that were used
 * to instantiate this class.
 */
@Singleton
public class InjectorCollector {
	private static Set<InjectorCollector> INSTANCES = new HashSet<>();

	@Inject
	private Injector injector;

	/** Constructor. Collects all instances of this class. */
	public InjectorCollector() {
		INSTANCES.add(this);
	}

	/** @return a map of all -- usually one -- injectors that were used to instantiate this class. */
	static public Map<Injector, String> getSharedInjectors() {
		Map<Injector, String> injectors = new HashMap<>();

		int i = 0;
		for (InjectorCollector ic : INSTANCES) {
			injectors.put(ic.injector, "Shared-Injector_" + i++);
		}

		return injectors;
	}
}
