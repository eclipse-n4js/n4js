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
package org.eclipse.n4js.tester;

import java.util.function.Consumer;

/**
 * Allows to register a listener that is invoked when a {@link Process} has terminated.
 */
final class ProcessTerminationListener extends Thread {
	/**
	 * Registers a {@link ProcessTerminationListener} with the given process {@code p}.
	 *
	 * Invokes {@code listener} with the corresponding exit code, once the process has terminated.
	 *
	 * Note that the given listener may not be invoked on the same thread as the registration method.
	 */
	public static void register(Process p, Consumer<Integer> listener) {
		if (p.isAlive()) {
			new ProcessTerminationListener(p, listener).start();
		} else {
			// process was terminated to begin with, invoke listener directly.
			listener.accept(p.exitValue());
		}
	}

	private final Process process;
	private final Consumer<Integer> listener;

	private ProcessTerminationListener(Process process, Consumer<Integer> listener) {
		this.listener = listener;
		this.process = process;
	}

	@Override
	public void run() {
		int exitCode = -1;
		try {
			exitCode = process.waitFor();
		} catch (InterruptedException e) {
			// ignore, we just want to update the UI state
		}
		this.listener.accept(exitCode);
	}
}