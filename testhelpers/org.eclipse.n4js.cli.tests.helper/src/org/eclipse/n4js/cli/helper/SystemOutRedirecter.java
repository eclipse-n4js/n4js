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
	final public void setRedirections() {
		oldSystemOut = System.out;
		oldSystemErr = System.err;

		redirectOut = new ByteArrayOutputStream();
		redirectErr = new ByteArrayOutputStream();

		try {
			PrintStream psOut = new PrintStream(redirectOut, true, "UTF-8");
			PrintStream psErr = new PrintStream(redirectErr, true, "UTF-8");
			System.setOut(psOut);
			System.setErr(psErr);
		} catch (UnsupportedEncodingException e) {
			Assert.fail("Could not redirect output streams");
		}
	}

	/** Restores everything. */
	final public void unsetRedirections() {
		System.out.flush();
		System.err.flush();
		System.setOut(oldSystemOut);
		System.setErr(oldSystemErr);
	}

	/** @return console output */
	public String getSystemOut() {
		try {
			System.out.flush();
			String output = redirectOut.toString("UTF-8");
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
			String output = redirectErr.toString("UTF-8");
			return output;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

}
