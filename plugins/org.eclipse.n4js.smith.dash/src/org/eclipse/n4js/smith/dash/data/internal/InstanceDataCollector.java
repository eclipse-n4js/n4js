/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.smith.dash.data.internal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.eclipse.n4js.smith.dash.data.DataCollector;
import org.eclipse.n4js.smith.dash.data.Measurement;
import org.eclipse.n4js.smith.dash.data.SomeDataPoint;

/**
 *
 */
public class InstanceDataCollector implements DataCollector {

	private static final TimedMeasurement NULL_MEASURMENT = new TimedMeasurement("NOOP", TimedMeasurement::noop);
	private final AtomicLong idGenerator = new AtomicLong();
	private boolean notCollecting = false; // TODO set to true, flip through UI

	// concurrent collection
	private final List<InstanceMeasurement> data = new LinkedList<>();

	public void setCollecting(boolean flag) {
		notCollecting = flag;
	}

	@Override
	public Measurement getMeasurement(String name) {
		if (name == null || name.isEmpty())
			throw new RuntimeException(TimedMeasurement.class.getName() + " needs non empty name.");

		if (notCollecting)
			return NULL_MEASURMENT;

		return new InstanceMeasurement(name, idGenerator.incrementAndGet(), this::consume);
	}

	private void consume(Measurement measurement) {
		if (measurement instanceof InstanceMeasurement) {
			InstanceMeasurement instance = (InstanceMeasurement) measurement;
			// synchronized
			data.add(instance);
		}
	}

	@Override
	public List<SomeDataPoint> getData() {
		return Collections.unmodifiableList(
				data.stream()
						.map(m -> new SomeDataPoint(m.name, m.ID))
						.collect(Collectors.toList()));
	}

}
