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

import com.google.common.base.Stopwatch;

/**
 * Utility for making timed calls to the runnable. Extra parameters passed allow to pretty print timed data.
 */
public class WrappedTimedCallUtil {

	/** Delegates to {@link #wrapTimedRun(Runnable, String, int)} with {@code tabs=0} */
	public static void wrapTimedRun(Runnable runnable, String name) {
		wrapTimedRun(runnable, name, 0);
	}

	/**
	 * Executes given runnable, prints start and end of the call. Provided {@code tabs} parameter controls indednation
	 * level which allows for pretty print of the nested Calls.
	 * <p>
	 *
	 * @param runnable
	 *            to run
	 * @param name
	 *            name to use in printed data
	 */
	public static void wrapTimedRun(Runnable runnable, String name, int tabs) {
		String indend = times(tabs, "\u2503", "\t");
		String x = tabs == 0 ? "" : "\t";
		System.out.println(indend + x + "\u250F " + name);
		Stopwatch sw = Stopwatch.createStarted();
		runnable.run();
		sw.stop();
		System.out.println(indend + x + "\u2517 " + name + " took " + sw);
	}

	private static String times(int n, String s, String j) {
		return String.join(j, Collections.nCopies(n, s));
	}

}
