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

import org.junit.Assert;

/**
 * Test for checking whether plain JS files have the proper module export.
 */
public class SystemOutRedirecter {
	private PrintStream oldSystemOut;
	private PrintStream oldSystemErr;
	private ByteArrayOutputStream redirectOut;
	private ByteArrayOutputStream redirectErr;

	/** Sets up the System outputs and Security Manager */
	@SuppressWarnings("resource")
	final public void set(boolean mirrorOnSystemOut) {
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
	}

	/** Restores everything. */
	final public void unset() {
		System.out.flush();
		System.err.flush();
		System.out.close();
		System.err.close();
		System.setOut(oldSystemOut);
		System.setErr(oldSystemErr);
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
