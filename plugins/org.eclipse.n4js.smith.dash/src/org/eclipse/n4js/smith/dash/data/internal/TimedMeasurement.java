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

import java.util.function.Consumer;

import org.eclipse.n4js.smith.dash.data.Measurement;

import com.google.common.base.Stopwatch;

/**
 *
 */
public class TimedMeasurement implements Measurement {
	public final String name;
	final Stopwatch sw;
	Consumer<TimedMeasurement> stopHandler;

	TimedMeasurement(final String name, Consumer<TimedMeasurement> stopHandler) {
		this.name = name;
		this.sw = Stopwatch.createStarted();
		this.stopHandler = stopHandler;
	}

	@Override
	public void end() {
		if (this.sw.isRunning())
			this.sw.stop();
		stopHandler.accept(this);
		stopHandler = TimedMeasurement::noop;
	}

	@SuppressWarnings("unused")
	static void noop(TimedMeasurement measurement) {
		// NOOP
	}

}
