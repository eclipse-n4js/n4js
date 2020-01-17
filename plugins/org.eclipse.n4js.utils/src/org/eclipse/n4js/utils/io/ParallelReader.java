/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.google.common.collect.Lists;

/**
 * Utility class for simultaneously reading from multiple {@link Reader}s, storing the characters read and optionally
 * forwarding to a given writer (per reader). Once exhausted, readers will be {@link Reader#close() closed} by this
 * class, but not the writers.
 * <p>
 * Given readers and writers will be wrapped in {@link BufferedReader} / {@link BufferedWriter}, if they are not already
 * of those types.
 * <p>
 * While using threads internally, this class is not thread-safe as far as the client is concerned, i.e. the client must
 * invoke all methods of this class from a single thread.
 */
public class ParallelReader {

	private final ExecutorService customExecutorService;

	private ExecutorService actualExecutorService = null; // will be set in #start()
	private boolean completed = false; // will be set in #waitFor()

	private final List<Exhauster> exhausters = Lists.newArrayList();
	private final StringBuffer outputMerged = new StringBuffer(); // n.b.: StringBuffer is thread-safe

	private final class Exhauster implements Runnable {

		final BufferedReader r;
		final BufferedWriter forwardTo;
		final boolean flush;

		final StringBuilder output = new StringBuilder();

		public Exhauster(BufferedReader r, BufferedWriter forwardTo, boolean flush) {
			this.r = r;
			this.forwardTo = forwardTo;
			this.flush = flush;
		}

		@Override
		public void run() {
			try {
				try (r) {
					int len;
					char[] buff = new char[1024];
					while ((len = r.read(buff)) >= 0) {
						output.append(buff, 0, len);
						outputMerged.append(buff, 0, len);
						if (forwardTo != null) {
							forwardTo.write(buff, 0, len);
							if (flush && !r.ready()) {
								forwardTo.flush();
							}
						}
					}
				}
			} catch (IOException e) {
				// ignore
			}
		}
	}

	/**
	 * Creates an instance. Afterwards, continue with adding readers to this instance via the <code>#add()</code>
	 * methods.
	 */
	public ParallelReader() {
		this(null);
	}

	/**
	 * Creates an instance with a custom {@link ExecutorService}. Afterwards, continue with adding readers to this
	 * instance via the <code>#add()</code> methods.
	 * <p>
	 * NOTE: if the given executor service will use fewer threads than readers added via the <code>#add()</code> methods
	 * of this class, then the order of forwarding and merging of the output will be messed up.
	 */
	public ParallelReader(ExecutorService executorService) {
		this.customExecutorService = executorService;
	}

	/** Same as {@link #add(Reader, Writer, boolean)}, but accepts an {@link InputStream}. */
	public ParallelReader add(InputStream in, Charset charset) {
		return add(in, null, false, charset);
	}

	/** Same as {@link #add(Reader, Writer, boolean)}, but accepts {@link InputStream}s. */
	public ParallelReader add(InputStream in, OutputStream forwardTo, boolean flush, Charset charset) {
		return add(
				new InputStreamReader(in, charset),
				forwardTo != null ? new OutputStreamWriter(forwardTo, charset) : null,
				flush);
	}

	/** Same as {@link #add(Reader, Writer, boolean)}, but without forwarding. */
	public ParallelReader add(Reader r) {
		return add(r, null, false);
	}

	/**
	 * Add another reader to this instance. See {@link ParallelReader} for details.
	 * <p>
	 * Won't actually start reading until {@link #start()} is invoked.
	 *
	 * @param r
	 *            the reader to read from.
	 * @param forwardTo
	 *            the optional writer to forward to. May be <code>null</code>.
	 * @param flush
	 *            whether the writer should be flushed. Ignored if <code>forwardTo</code> is <code>null</code>.
	 */
	public ParallelReader add(Reader r, Writer forwardTo, boolean flush) {
		BufferedReader rBuff = r instanceof BufferedReader
				? (BufferedReader) r
				: new BufferedReader(r);
		BufferedWriter forwardToBuff = forwardTo instanceof BufferedWriter
				? (BufferedWriter) forwardTo
				: (forwardTo != null ? new BufferedWriter(forwardTo) : null);
		Exhauster exhauster = new Exhauster(rBuff, forwardToBuff, flush);
		exhausters.add(exhauster);
		return this;
	}

	/**
	 * Start reading simultaneously from all readers that were before added via the <code>#add()</code> methods.
	 */
	public ParallelReader start() {
		actualExecutorService = customExecutorService != null ? customExecutorService
				: Executors.newFixedThreadPool(exhausters.size());
		for (Exhauster exhauster : exhausters) {
			actualExecutorService.execute(exhauster);
		}
		return this;
	}

	/**
	 * Wait for all readers to be exhausted, i.e. {@link Reader#read()} returning -1 for each of them.
	 *
	 * @throws TimeoutException
	 *             if the timeout expires before all readers are exhausted.
	 * @throws InterruptedException
	 *             if an interrupt signal is received while waiting.
	 */
	public ParallelReader waitFor(long timeout, TimeUnit unit) throws TimeoutException, InterruptedException {
		if (actualExecutorService == null) {
			throw new IllegalStateException("#waitFor() invoked before #start()");
		}
		actualExecutorService.shutdown();
		boolean success = actualExecutorService.awaitTermination(timeout, unit);
		if (!success) {
			throw new TimeoutException("timeout expired while waiting for exhaustion of readers");
		}
		completed = true;
		return this;
	}

	/**
	 * Returns output read from the <code>n</code>'th reader that was added via the <code>#add()</code> methods.
	 */
	public String getOutput(int n) {
		if (!completed) {
			throw new IllegalStateException("#getOutput() invoked before #waitFor()");
		}
		return exhausters.get(n).output.toString();
	}

	/**
	 * Returns the output read from all readers, merged in the order it was read.
	 * <p>
	 * WARNING: the precise order is usually indeterministic and should therefore not be used for test expectations,
	 * etc.
	 */
	public String getOutputMerged() {
		if (!completed) {
			throw new IllegalStateException("#getOutputMerged() invoked before #waitFor()");
		}
		return outputMerged.toString();
	}

	/**
	 * Output from a {@link Process} captured with method
	 * {@link ParallelReader#waitForAndCaptureOutput(Process, boolean, long, TimeUnit)}.
	 */
	public static final class CapturedOutput {
		/** The output received via the standard output stream. */
		public final String stdout;
		/** The output received via the standard error stream. */
		public final String stderr;
		/** The output of both standard output and standard error streams. */
		public final String merged;

		private CapturedOutput(String stdout, String stderr, String merged) {
			this.stdout = stdout;
			this.stderr = stderr;
			this.merged = merged;
		}
	}

	/**
	 * Similarly to {@link Process#waitFor(long, TimeUnit)}, this method waits for the given process to terminate and
	 * captures all its output, optionally forwarding the output to {@link System#out stdout} and {@link System#err
	 * stderr}. The returned {@link CapturedOutput} can then be queried for the output using method
	 * {@link #getOutput(int)} (index 0 being <code>stdout</code> and 1 being <code>stderr</code>).
	 * <p>
	 * Once this method returns, the given process has terminated and therefore it is safe to invoke methods such as
	 * {@link Process#exitValue() #exitValue()} on this process.
	 *
	 * @throws TimeoutException
	 *             if the timeout expires before the process terminates.
	 * @throws InterruptedException
	 *             if an interrupt signal is received while waiting.
	 */
	@SuppressWarnings("resource")
	public static final CapturedOutput waitForAndCaptureOutput(Process process, boolean inheritIO,
			long timeout, TimeUnit unit) throws TimeoutException, InterruptedException {

		OutputStream stdout = inheritIO ? System.out : null;
		OutputStream stderr = inheritIO ? System.err : null;

		ParallelReader reader = new ParallelReader()
				.add(process.getInputStream(), stdout, true, StandardCharsets.UTF_8)
				.add(process.getErrorStream(), stderr, true, StandardCharsets.UTF_8)
				.start();

		boolean success = process.waitFor(timeout, unit);
		if (!success) {
			throw new TimeoutException("process timed out");
		}

		// The process has terminated, so usually I/O streams should be exhausted now.
		// However, there might be a slight delay, so we need to wait with a short timeout:
		reader.waitFor(30, TimeUnit.SECONDS);

		return new CapturedOutput(
				reader.getOutput(0),
				reader.getOutput(1),
				reader.getOutputMerged());
	}
}
