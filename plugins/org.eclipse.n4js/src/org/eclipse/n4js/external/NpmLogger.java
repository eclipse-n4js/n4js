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
package org.eclipse.n4js.external;

import static com.google.common.base.Throwables.getStackTraceAsString;
import static java.lang.System.lineSeparator;
import static org.eclipse.n4js.utils.io.OutputStreamPrinterThread.OutputStreamType.STD_ERR;
import static org.eclipse.n4js.utils.io.OutputStreamPrinterThread.OutputStreamType.STD_OUT;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.utils.collections.Arrays2;
import org.eclipse.n4js.utils.io.OutputRedirection;
import org.eclipse.n4js.utils.io.OutputStreamProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper for consistent npm operations logging. It will log all provided data to the standard {@link Logger} as well to
 * the output stream provided by the {@link OutputStreamProvider}.
 */
@Singleton
public class NpmLogger {
	@Inject
	private OutputStreamProvider osProvider;

	private static Logger LOGGER = Logger.getLogger(NpmLogger.class);

	private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<>() {

		private final SimpleDateFormat delegate = new SimpleDateFormat("[dd-MM-yyyy hh:mm:ss] ");

		@Override
		public SimpleDateFormat get() {
			return delegate;
		}

		@Override
		public void set(final SimpleDateFormat value) {
			// Immutable.
		}

	};

	/** Dispatches given message with {@link Logger#info} and to the {@code STD_OUT} of used output stream. */
	public void logInfo(final String message) {
		LOGGER.info(message);
		// Print writer is intentionally not released, its just a wrapper to log a message.
		final PrintWriter pw = new PrintWriter(osProvider.getOutputStream(STD_OUT, OutputRedirection.REDIRECT));
		pw.append(getTimestamp() + message + lineSeparator());
		pw.flush();
	}

	/**
	 * Dispatches given status with {@link Logger#error} and to the {@code STD_ERR} of used output stream. If provided
	 * status {@link IStatus#getChildren() has children} it will log them recursively.
	 */
	public void logError(final IStatus status) {
		logError(status.getMessage(), status.getException());
		final IStatus[] children = status.getChildren();
		if (!Arrays2.isEmpty(children)) {
			for (final IStatus child : children) {
				logError(child);
			}
		}
	}

	/**
	 * Dispatches given message with {@link Logger#error} and to the {@code STD_ERR} of used output stream. Additionally
	 * will print stack trace of the provided throwable.
	 */
	public void logError(final String message, final Throwable t) {
		LOGGER.error(message, t);
		// Print writer is intentionally not released, its just a wrapper to log a message.
		final PrintWriter pw = new PrintWriter(osProvider.getOutputStream(STD_ERR, OutputRedirection.SUPPRESS));
		pw.append(getTimestamp() + message + lineSeparator());
		if (null != t) {
			pw.append(getTimestamp() + getStackTraceAsString(t) + lineSeparator());
		}
		pw.flush();
	}

	private String getTimestamp() {
		return DATE_FORMAT.get().format(new Date());
	}

}
