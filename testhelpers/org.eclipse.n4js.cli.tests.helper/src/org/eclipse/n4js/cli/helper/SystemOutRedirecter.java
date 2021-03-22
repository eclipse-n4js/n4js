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
package org.eclipse.n4js.cli.helper;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.OptionHandler;
import org.junit.Assert;

/**
 * Test for checking whether plain JS files have the proper module export.
 */
public class SystemOutRedirecter {

	private PrintStream oldSystemOut = null;
	private PrintStream oldSystemErr = null;
	private ByteArrayOutputStream redirectOut;
	private ByteArrayOutputStream redirectErr;

	/** Sets up the System outputs and Security Manager */
	@SuppressWarnings("resource")
	final public void set(boolean mirrorOnSystemOut) {
		if (oldSystemOut != null && oldSystemErr != null) {
			return; // already set
		}

		oldSystemOut = System.out;
		oldSystemErr = System.err;

		redirectOut = new ByteArrayOutputStream();
		redirectErr = new ByteArrayOutputStream();

		PrintStream mirrorOut = mirrorOnSystemOut ? oldSystemOut : null;
		PrintStream mirrorErr = mirrorOnSystemOut ? oldSystemErr : null;

		try {
			PrintStream psOut = new DualStream(redirectOut, true, "UTF-8", mirrorOut);
			PrintStream psErr = new DualStream(redirectErr, true, "UTF-8", mirrorErr);
			System.setOut(psOut);
			System.setErr(psErr);
		} catch (UnsupportedEncodingException e) {
			Assert.fail("Could not redirect output streams");
		}

		updateLoggers();
	}

	/** Restores everything. */
	final public void unset() {
		if (oldSystemOut == null || oldSystemErr == null) {
			return; // not set yet
		}
		System.out.flush();
		System.err.flush();
		System.out.close();
		System.err.close();
		System.setOut(oldSystemOut);
		System.setErr(oldSystemErr);
		oldSystemOut = null;
		oldSystemErr = null;
		redirectOut = null;
		redirectErr = null;

		updateLoggers();
	}

	/** Clear output from {@link System#out} that was recorded so far. */
	public void clearSystemOut() {
		ByteArrayOutputStream baos = redirectOut;
		if (baos != null) {
			baos.reset();
		}
	}

	/** @return console output */
	public String getSystemOut() {
		try {
			System.out.flush();
			String output = redirectOut == null ? "" : redirectOut.toString("UTF-8");
			return output;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/** Clear output from {@link System#err} that was recorded so far. */
	public void clearSystemErr() {
		ByteArrayOutputStream baos = redirectErr;
		if (baos != null) {
			baos.reset();
		}
	}

	/** @return console output */
	public String getSystemErr() {
		try {
			System.err.flush();
			String output = redirectErr == null ? "" : redirectErr.toString("UTF-8");
			return output;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Assuming that a change of configuration ({@code System.out}, etc.) has just taken place, this method will ensure
	 * that the Log4J logging framework is updated accordingly. This is necessary because Loggers/Appenders cache
	 * certain configuration values (e.g. {@link ConsoleAppender} caches {@code System.out|err}).
	 */
	private void updateLoggers() {
		@SuppressWarnings("unchecked")
		List<?> appenders = Collections.list(Logger.getRootLogger().getAllAppenders());
		for (Object appender : appenders) {
			// note: an alternative would be to invoke ConsoleAppender#setFollow(true), but the following approach is
			// more generic (i.e. should work for all configuration properties) and does not change any global state:
			if (appender instanceof OptionHandler) {
				((OptionHandler) appender).activateOptions();
			}
		}
	}

	static class DualStream extends PrintStream {
		private final PrintStream out2;

		public DualStream(OutputStream out, boolean autoFlush, String encoding, PrintStream out2)
				throws UnsupportedEncodingException {

			super(out, autoFlush, encoding);
			this.out2 = out2;
		}

		@Override
		public void println(String x) {
			super.println(x);
			if (out2 != null) {
				out2.println(x);
			}
		}

		@Override
		public void print(String x) {
			super.print(x);
			if (out2 != null) {
				out2.print(x);
			}
		}
	}
}
