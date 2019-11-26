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
package org.eclipse.n4js.cli.compiler;

import java.security.Permission;

/**
 * Catches calls to System.exit() and throws an {@link SystemExitException} instead.
 */
public class SystemExitRedirecter {

	static boolean setByMe = false;

	/** Enables redirection */
	static public void set() {
		String name = null;
		if (System.getSecurityManager() != null) {
			name = System.getSecurityManager().getClass().getName();
		}
		if (name == null || !name.contains("NoExitSecurityManager")) {
			System.setSecurityManager(new NoExitSecurityManager());
			setByMe = true;
		}
	}

	/** Disables redirection */
	static public void unset() {
		if (setByMe) {
			System.setSecurityManager(null);
		}
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
