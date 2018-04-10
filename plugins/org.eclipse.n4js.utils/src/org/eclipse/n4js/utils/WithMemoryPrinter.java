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
 * Helper to be used in try-with resources blocks. Prints memory usage on entry and leave of the try block. Can be
 * configured to trigger GC cycles.
 */
public class WithMemoryPrinter implements AutoCloseable {
	private final String label;
	private final boolean gcOnClose;

	/** Delegates to {@link #WithMemoryPrinter(String, boolean, boolean)} */
	public WithMemoryPrinter(String label) {
		this(label, false, false);
	}

	/** Delegates to {@link #WithMemoryPrinter(String, boolean, boolean)} */
	public WithMemoryPrinter(String label, boolean gcStart) {
		this(label, gcStart, false);
	}

	/**
	 * Creates instance of the printer, that will use provider {@code label} when printing memory info. Provided boolean
	 * flags control if GC will be requested when object is created and closed.
	 *
	 * @param label
	 *            the label used when printing messages
	 * @param gcOnEnter
	 *            controls if GC should be requested when block is entered
	 * @param gcOnClose
	 *            controls if GC should be requested when block is left
	 */
	public WithMemoryPrinter(String label, boolean gcOnEnter, boolean gcOnClose) {
		this.label = label;
		this.gcOnClose = gcOnClose;

		// Unfortunately that is the only way to run code when
		// entering try-with-resources block
		printMem(label + "_enter", gcOnEnter);
	}

	/** Prints memory information. */
	public void printMemory() {
		printMem(label, false);
	}

	/** Prints memory information, can invoke GC if passed {@code true}. */
	public void printMemory(boolean runGC) {
		printMem(label, runGC);
	}

	@Override
	public void close() {
		printMem(label + "_exit", gcOnClose);
	}

	private static void printMem(String label, boolean runGC) {
		System.out.println(label);
		if (runGC) {
			MemoryTracker.printCurrentMemoryUsage("before GC");
			MemoryTracker.runGC();
			MemoryTracker.printCurrentMemoryUsage("after  GC");
		} else
			MemoryTracker.printCurrentMemoryUsage("memory");
	}

}
