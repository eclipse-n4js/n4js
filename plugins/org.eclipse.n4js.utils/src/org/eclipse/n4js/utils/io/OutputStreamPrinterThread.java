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

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;

/**
 * Thread for reading the content of an {@link InputStream input stream} and writing the read content to an
 * {@link OutputStream output stream}.
 */
public class OutputStreamPrinterThread extends Thread implements AutoCloseable {

	private static final Logger LOGGER = Logger.getLogger(OutputStreamPrinterThread.class);

	private final InputStream is;
	private final OutputStream os;
	private final ByteArrayOutputStream baos;
	private final OutputRedirection redirect;

	/* default */ OutputStreamPrinterThread(final InputStream is, final OutputStream os, OutputRedirection redirect) {
		this.is = checkNotNull(is, "is");
		this.os = checkNotNull(os, "os");
		setName(this.getClass().getSimpleName());
		this.baos = new ByteArrayOutputStream();
		this.redirect = redirect;
	}

	@Override
	public void run() {
		try {
			final byte[] buffer = new byte[1024];
			int numberOfReadBytes;
			final BufferedInputStream bis = new BufferedInputStream(is);
			while ((numberOfReadBytes = bis.read(buffer)) != -1) {
				final byte[] clearedBuffer = new byte[numberOfReadBytes];
				System.arraycopy(buffer, 0, clearedBuffer, 0, numberOfReadBytes);
				baos.write(clearedBuffer);
				if (redirect == OutputRedirection.REDIRECT) {
					os.write(clearedBuffer);
				}
			}
		} catch (final Exception e) {
			final String message = "Error reading output of running process.";
			LOGGER.error(message, e);
			throw new RuntimeException(message, e);
		}
	}

	@Override
	public void close() {
		if (null != is) {
			try {
				is.close();
			} catch (final IOException e) {
				LOGGER.error("Error while closing input stream for printer thread.", e);
				try {
					is.close();
				} catch (final IOException ignoredExc) {
					// This is intentionally swallowed, otherwise we would have no idea what was the original cause.
				}
			}
		}
	}

	@Override
	public String toString() {
		try {
			return baos.toString(Charsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			return Throwables.getStackTraceAsString(e);
		}
	}

	/**
	 * Enumeration of output stream types.
	 */
	public static enum OutputStreamType {

		/**
		 * Type for the standard output stream.
		 */
		STD_OUT,

		/**
		 * Type for the standard error stream.
		 */
		STD_ERR;

	}

}
