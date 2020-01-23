/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.io;

import static org.eclipse.n4js.utils.io.OutputStreamPrinterThread.OutputStreamType.STD_ERR;
import static org.eclipse.n4js.utils.io.OutputStreamPrinterThread.OutputStreamType.STD_OUT;

import java.io.OutputStream;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Provides a {@link OutputStreamPrinterThread output stream printer thread} for the given {@link Process process}. The
 * provided threads are wrapping input streams associated with the spawned {@link Process process}. It is the clients
 * responsibility to properly release the resource. Since the provided threads are {@link AutoCloseable auto-closeable}
 * instances one can make sure to close the resources by simply using the
 * <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">try-with-resources</a>
 * pattern. Also if clients must make sure all the content from the input streams are read properly one can join to the
 * provided threads.<br>
 * Example usage:
 *
 * <pre>
 * Process process = getSomeProcess();
 *
 * // Make sure underlying input streams are closed.
 * try (OutputStreamPrinterThread stdOutThread = getStdOutPrinterThread(process);
 * 		OutputStreamPrinterThread stdErrThread = getStdErrPrinterThread(process)) {
 *
 * 	process.waitFor();
 *
 * 	// Make sure everything is read.
 * 	stdOutThread.join(5L);
 * 	stdErrThread.join(5L);
 *
 * 	// Some logic for the process.
 *
 * }
 * </pre>
 */
@Singleton
public class OutputStreamPrinterThreadProvider {

	@Inject
	private OutputStreamProvider osProvider;

	/**
	 * Returns with a thread that reads the {@link Process#getInputStream() standard input} of the process. The
	 * {@link Thread#start()} is already called on the returning thread. Its the clients responsibility to release the
	 * underlying resource by calling {@link OutputStreamPrinterThread#close() close} on the provided instance.
	 *
	 * @param process
	 *            the process to read its standard input.
	 * @param redirect
	 *            if {@link OutputRedirection#SUPPRESS} the captured out stream of the process will be swallowed at all.
	 *            If {@link OutputRedirection#REDIRECT} then the output stream of the process will be redirected to
	 *            another output stream as user notification purposes.
	 * @return a thread that reads the input stream of the process and writes it to the process output stream.
	 */
	@SuppressWarnings("resource")
	public OutputStreamPrinterThread getPrinterThreadForStdOut(Process process, OutputRedirection redirect) {
		final OutputStream os = osProvider.getOutputStream(STD_OUT, redirect);
		final OutputStreamPrinterThread thread = new OutputStreamPrinterThread(process.getInputStream(), os, redirect);
		thread.start();
		return thread;
	}

	/**
	 * Returns with a thread that reads the {@link Process#getErrorStream() error stream} of the process. The
	 * {@link Thread#start()} is already called on the returning thread. Its the clients responsibility to release the
	 * underlying resource by calling {@link OutputStreamPrinterThread#close() close} on the provided instance.
	 *
	 * @param process
	 *            the process to read its error stream.
	 * @param redirect
	 *            if {@link OutputRedirection#SUPPRESS} the captured out stream of the process will be swallowed at all.
	 *            If {@link OutputRedirection#REDIRECT} then the output stream of the process will be redirected to
	 *            another output stream.
	 * @return a thread that reads the input stream of the process and writes it to the process output stream.
	 */
	@SuppressWarnings("resource")
	public OutputStreamPrinterThread getPrinterThreadForStdErr(Process process, OutputRedirection redirect) {
		final OutputStream os = osProvider.getOutputStream(STD_ERR, redirect);
		final OutputStreamPrinterThread thread = new OutputStreamPrinterThread(process.getErrorStream(), os, redirect);
		thread.start();
		return thread;
	}

}
