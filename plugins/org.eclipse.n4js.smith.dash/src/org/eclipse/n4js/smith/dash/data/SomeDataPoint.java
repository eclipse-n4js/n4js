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
package org.eclipse.n4js.smith.dash.data;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import com.google.common.base.Stopwatch;

@SuppressWarnings("javadoc")
public class SomeDataPoint {

	public final Long nanos;
	public final String name;

	public SomeDataPoint(final String name, final Stopwatch sw) {
		if (sw.isRunning())
			throw new RuntimeException("Stop watch is still running.");
		this.name = name;
		this.nanos = sw.elapsed(NANOSECONDS);
	}

	public SomeDataPoint(final String name, final Long nanos) {
		this.name = name;
		this.nanos = nanos;
	}

	public String getHumanDuration() {
		return SimpleTimeFormat.convert(nanos);
	}
}
