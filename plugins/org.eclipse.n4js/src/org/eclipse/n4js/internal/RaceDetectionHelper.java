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
package org.eclipse.n4js.internal;

/**
 * Small utility that helps to detect races between builds. Along with a unit test and the <code>@Repeated</code>
 * annotation the logs can be compared for differences.
 */
public final class RaceDetectionHelper {

	/**
	 * True if logging is enabled.
	 */
	public static final boolean ENABLED = Boolean
			.parseBoolean(System.getProperty(RaceDetectionHelper.class.getName() + ".enabled", "false"));

	private RaceDetectionHelper() {
		throw new AssertionError("Unexpected constructor call");
	}

	/**
	 * Log a message if debug output is enabled.
	 *
	 * @param messageFmt
	 *            the message
	 * @param messageArgs
	 *            the arguments
	 * @see java.util.Formatter
	 */
	public static void log(String messageFmt, Object... messageArgs) {
		if (ENABLED) {
			System.out.printf(messageFmt, messageArgs);
			System.out.println();
		}
	}

	/**
	 * Log a message if debug output is enabled.
	 *
	 * @param messageFmt
	 *            the message
	 * @param messageArg
	 *            the argument
	 * @see java.util.Formatter
	 */
	public static void log(String messageFmt, Object messageArg) {
		if (ENABLED) {
			System.out.printf(messageFmt, messageArg);
			System.out.println();
		}
	}

}
