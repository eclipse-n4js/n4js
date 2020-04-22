/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.smith;

import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.xtext.scoping.IScope;

/**
 * A scope that can be decorated with a data collector while exploiting internal knowledge of the scope.
 */
public interface MeasurableScope extends IScope {

	/**
	 * Decorate the current scope and return a new scope that will create fine grained measurements if enabled.
	 */
	IScope decorate(DataCollector dataCollector);

	/**
	 * Decorate the given scope with a data collector, if enabled.
	 */
	static IScope decorate(IScope scope, DataCollector collector) {
		if (collector.isPaused()) {
			return scope;
		}
		DynamicDataCollector dynamic = collector instanceof DynamicDataCollector ? (DynamicDataCollector) collector
				: new DynamicDataCollector(collector);
		if (scope instanceof MeasurableScope) {
			scope = ((MeasurableScope) scope).decorate(dynamic);
		}
		return new DataCollectingScope(scope, dynamic);
	}

	/**
	 * Returns a measurement to inject timings into the processing of scopes.
	 */
	static Measurement getMeasurement(IScope scope, String key) {
		if (scope instanceof DataCollectingScope) {
			return ((DataCollectingScope) scope).dataCollector.getMeasurement(key);
		}
		return () -> {
			// no-op
		};
	}

}
