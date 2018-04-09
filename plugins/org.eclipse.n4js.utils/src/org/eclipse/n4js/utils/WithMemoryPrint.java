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

/**
 *
 */
public class WithMemoryPrint implements AutoCloseable {
	private final String label;
	private final boolean gcOnClose;

	/**
	 *
	 */
	public WithMemoryPrint(String label) {
		this(label, false, false);
	}

	/**
	 *
	 */
	public WithMemoryPrint(String label, boolean gcStart) {
		this(label, gcStart, false);
	}

	/**
	 *
	 */
	public WithMemoryPrint(String label, boolean gcStart, boolean gcOnClose) {
		this.label = label;
		this.gcOnClose = gcOnClose;

		dumpMem(label + "_init", gcStart);
	}

	@Override
	public void close() throws Exception {
		dumpMem(label + "_close", gcOnClose);
	}

	private static void dumpMem(String label, boolean runGC) {
		System.out.println(label);
		if (runGC) {
			MemoryTracker.printCurrentMemoryUsage("before GC");
			MemoryTracker.runGC();
			MemoryTracker.printCurrentMemoryUsage("after GC");
		} else
			MemoryTracker.printCurrentMemoryUsage("memory");
	}

}
