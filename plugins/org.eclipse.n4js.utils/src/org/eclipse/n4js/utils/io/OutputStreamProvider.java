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

import java.io.OutputStream;

import org.eclipse.n4js.utils.io.OutputStreamPrinterThread.OutputStreamType;

import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;

/**
 * Provides {@link OutputStream output stream} for clients.
 */
@ImplementedBy(value = OutputStreamProvider.Default.class)
public interface OutputStreamProvider {

	/**
	 * Returns with the output stream for the output stream type.
	 *
	 * @param type
	 *            the type of the output stream this instance is providing.
	 * @param redirect
	 *            {@link OutputRedirection#SUPPRESS} if the output stream treated silently. When the {@link OutputRedirection#REDIRECT} argument is set to
	 *            {@link OutputRedirection#SUPPRESS} no consoles will be activated when the workbench is running and nothing will be printed
	 *            to the stream in the headless case. Otherwise {@link OutputRedirection#REDIRECT}.
	 *
	 * @return the output stream for the given type.
	 */
	OutputStream getOutputStream(final OutputStreamType type, final OutputRedirection redirect);

	/**
	 * Default implementation that uses the {@link System#out standard out} and {@link System#err standard error} from
	 * the {@link System system}.
	 */
	@Singleton
	public class Default implements OutputStreamProvider {

		@Override
		public OutputStream getOutputStream(final OutputStreamType type, final OutputRedirection redirect) {
			switch (type) {
			case STD_OUT:
				return System.out;
			case STD_ERR:
				return System.err;
			default:
				throw new IllegalArgumentException("Unexpected output stream type: " + type);
			}
		}

	}

}
