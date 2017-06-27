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
package org.eclipse.n4js.ui.organize.imports;

/**
 * Exception in case the user canceled, or an automatic resolution was not possible. For active abortion by the user,
 * use {@link UserCanceledBreakException}
 */
public class BreakException extends Exception {

	/**
	 *
	 */
	public BreakException() {
	}

	/**
	 */
	public BreakException(String message) {
		super(message);
	}

	/**
	 */
	public BreakException(Throwable cause) {
		super(cause);
	}

	/**
	 */
	public BreakException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 */
	public BreakException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/** Special case for UI-interaction */
	public static class UserCanceledBreakException extends BreakException {
		/**	 */
		public UserCanceledBreakException(String msg) {
			super(msg);
		}
	}

}
