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
package org.eclipse.n4js.smith;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.common.base.Stopwatch;

/**
 * Simple measurement that tracks time between its creation and call to {@link #close()}.
 */
class TimedMeasurement implements Measurement {
	final String name;
	private final Stopwatch sw;
	private boolean consumed = false;
	private Consumer<TimedMeasurement> stopHandler;

	TimedMeasurement(final String name, Consumer<TimedMeasurement> stopHandler) {
		this.name = name;
		this.stopHandler = stopHandler;
		this.sw = Stopwatch.createStarted();
	}

	synchronized long elapsed(TimeUnit timeUnit) {
		return this.sw.elapsed(timeUnit);
	}

	@Override
	public synchronized void close() {
		if (consumed)
			return;

		if (this.sw.isRunning())
			this.sw.stop();
		stopHandler.accept(this);
		consumed = true;
		stopHandler = null;
	}
}
