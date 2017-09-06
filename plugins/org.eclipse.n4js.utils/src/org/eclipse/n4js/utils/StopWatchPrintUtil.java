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
package org.eclipse.n4js.utils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

/**
 * Stop watch helper for taking time measurements and printing them to the console.
 */
public class StopWatchPrintUtil {

	private final Stopwatch sw;
	private final String label;
	private final int tabs;
	private final TimeUnit unit;
	private final long threshold;

	/**
	 * Delegates to {@link #StopWatchPrintUtil(String, int)} with {@code -1} as threshold.
	 */
	public StopWatchPrintUtil(String label, int tabs) {
		this(label, tabs, -1);
	}

	/**
	 * Delegates to {@link #StopWatchPrintUtil(String, int, long, TimeUnit)} with {@link TimeUnit#MILLISECONDS} as
	 * threshold unit.
	 */
	public StopWatchPrintUtil(String label, int tabs, long threshold) {
		this(label, tabs, threshold, TimeUnit.MILLISECONDS);
	}

	/**
	 * Creates started instance of the stop watch.
	 *
	 * @param label
	 *            the label used when printing result
	 * @param tabs
	 *            Indentation of the printed label
	 * @param threshold
	 *            the threshold used to decide if measurement should be printed
	 * @param thresholdUnit
	 *            time unit used as threshold
	 */
	public StopWatchPrintUtil(String label, int tabs, long threshold, TimeUnit thresholdUnit) {
		this.sw = Stopwatch.createStarted();
		this.label = label;
		this.tabs = tabs;
		this.threshold = threshold;
		this.unit = thresholdUnit;
	}

	/** Stops this instance and if elapsed time is grater than threshold, prints result to system out. */
	public void stop() {
		sw.stop();

		if (sw.elapsed(unit) > threshold) {
			String indend = times(tabs, "\u2501", "");
			System.out.println(indend + "\u252B " + sw + " " + label);
		}
	}

	private static String times(int n, String s, String j) {
		return String.join(j, Collections.nCopies(n, s));
	}
}
