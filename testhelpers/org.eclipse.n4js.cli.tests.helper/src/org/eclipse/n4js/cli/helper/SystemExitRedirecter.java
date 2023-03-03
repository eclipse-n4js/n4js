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
package org.eclipse.n4js.cli.helper;

import java.security.Permission;

/**
 * Catches calls to System.exit() and throws an {@link SystemExitException} instead.
 *
 * @implNote The Java {@link SecurityManager} is deprecated. Here it is used to throw an exception instead of exiting
 *           the application. This cannot be done with a shutdown hook. Since work is still in progress regarding
 *           replacements (see here: https://openjdk.org/jeps/411 and here: https://bugs.openjdk.org/browse/JDK-8199704)
 *           we will ignore the warning at the moment and revisit this issue once we need to update Java.
 */
@SuppressWarnings({ "removal", "deprecation" })
public class SystemExitRedirecter {

	/** Enables redirection */
	public void set() {
		System.setSecurityManager(new NoExitSecurityManager());
	}

	/** Disables redirection */
	public void unset() {
		System.setSecurityManager(null);
	}

	static class SystemExitException extends SecurityException {
		public final int status;

		public SystemExitException(int status) {
			this.status = status;
		}
	}

	private static class NoExitSecurityManager extends SecurityManager {
		private final SecurityManager securityManager;

		NoExitSecurityManager() {
			this.securityManager = System.getSecurityManager();
		}

		@Override
		public void checkPermission(Permission perm) {
			if (securityManager != null) {
				securityManager.checkPermission(perm);
			}
		}

		@Override
		public void checkPermission(Permission perm, Object context) {
			if (securityManager != null) {
				securityManager.checkPermission(perm, context);
			}
		}

		@Override
		public void checkExit(int status) {
			super.checkExit(status);
			throw new SystemExitException(status);
		}

	}
}
