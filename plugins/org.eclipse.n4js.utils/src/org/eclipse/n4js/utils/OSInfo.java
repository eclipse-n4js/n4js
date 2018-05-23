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
package org.eclipse.n4js.utils;

import com.google.common.base.StandardSystemProperty;

/**
 * General utilities regarding platform dependent behavior.
 */
public abstract class OSInfo {

	/** Quick access to system property "os.name" */
	public static final String OS_NAME = StandardSystemProperty.OS_NAME.value().toLowerCase();

	/** Quick access to system property "os.arch" */
	public static final String OS_ARCH = StandardSystemProperty.OS_ARCH.value().toLowerCase();

	/**
	 * Not meant to instantiate.
	 */
	private OSInfo() {
	}

	/**
	 * @return true for Windows
	 */
	public static boolean isWindows() {
		return (OS_NAME.indexOf("win") >= 0);
	}

	/**
	 * @return true for Mac
	 */
	public static boolean isMac() {
		return (OS_NAME.indexOf("mac") >= 0);
	}

	/**
	 * @return true for Unix-like (Linux,Aix,*nix,*nux)
	 */
	public static boolean isUnix() {
		return (OS_NAME.indexOf("nix") >= 0 || OS_NAME.indexOf("nux") >= 0 || OS_NAME.indexOf("aix") > 0);
	}

	/**
	 * Checks descriptor based on os.arch property and returns Arch descriptor.
	 *
	 * @returns true for 64bit, false for 32bit
	 */
	public static boolean is64bit() {
		boolean is64bit = false;
		/*
		 * Note that this behaviour is a lie! System property os.arch tells you about architecture of used JVM not of
		 * the underlying os. Consider dropping 64bit binaries and default all to 32bit, all go refer to system based
		 * tricks
		 */
		// http://mark.koli.ch/reliably-checking-os-bitness-32-or-64-bit-on-windows-with-a-tiny-c-app

		if (isWindows()) {
			is64bit = (System.getenv("ProgramFiles(x86)") != null);
		} else {
			is64bit = (OS_ARCH.contains("64"));
		}

		return is64bit;
	}

	/**
	 * Checks descriptor based on os.arch property and returns Arch descriptor.
	 *
	 * @returns "64" or "32"
	 */
	public static String getArchDescriptor() {
		return is64bit() ? "64" : "32";
	}

}
