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
package org.eclipse.n4js.ide;

import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.n4js.smith.DataCollector;
import org.eclipse.n4js.smith.DataCollectors;
import org.eclipse.n4js.smith.ForwardingDataCollector;
import org.eclipse.n4js.smith.Measurement;

import com.google.common.base.Supplier;

/**
 * Data collectors for the N4JS language server.
 */
public class N4JSIdeDataCollectors {

	/**
	 * Base collector for all requests
	 */
	public static final DataCollector dcN4JSRequest = new SequentializingDataCollector(create("LSP Request"));

	/**
	 * Generic facility to obtain a data collector for a given request category.
	 */
	public static DataCollector request(String category) {
		return create(category, dcN4JSRequest);
	}

	/**
	 * Generic facility to obtain a data collector for a given request category.
	 */
	public static DataCollector request(String category, String sub) {
		return create(sub, request(category));
	}

	private static DataCollector create(String key) {
		return DataCollectors.INSTANCE.getOrCreateDataCollector(key);
	}

	private static DataCollector create(String key, DataCollector parent) {
		return DataCollectors.INSTANCE.getOrCreateDataCollector(key, parent);
	}

	static class SequentializingDataCollector extends ForwardingDataCollector {

		private final ReentrantLock lock = new ReentrantLock(true);

		SequentializingDataCollector(DataCollector delegate) {
			super(delegate);
		}

		private Measurement getMeasurement(Supplier<Measurement> p) {
			if (!isPaused()) {
				class LockedMeasurement implements Measurement {
					private final Measurement m;

					LockedMeasurement() {
						lock.lock();
						m = p.get();
					}

					@Override
					public void close() {
						try {
							m.close();
						} finally {
							lock.unlock();
						}
					}
				}
				return new LockedMeasurement();
			} else {
				return p.get();
			}
		}

		@Override
		public Measurement getMeasurement() {
			return getMeasurement(() -> super.getMeasurement());
		}

		@Override
		public Measurement getMeasurementIfInactive() {
			return getMeasurement(() -> super.getMeasurementIfInactive());
		}

		@Override
		public Measurement getMeasurementIfInactive(String name) {
			return getMeasurement(() -> super.getMeasurementIfInactive(name));
		}

		@Override
		public Measurement getMeasurement(String name) {
			return getMeasurement(() -> super.getMeasurement(name));
		}
	}
}
