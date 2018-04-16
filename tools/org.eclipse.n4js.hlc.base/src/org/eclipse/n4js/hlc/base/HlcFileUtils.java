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
package org.eclipse.n4js.hlc.base;

import java.io.File;

import org.eclipse.emf.common.util.URI;

/**
 * Utilities for working with IO.
 */
public class HlcFileUtils {

	/**
	 * Ensure given file is plain, existing and readable file.
	 *
	 * @param file
	 *            to check
	 * @throws RuntimeException
	 *             if any file is not existent or not plain or not readable
	 */
	public static void isExistingWriteableDir(File file) {
		if (!file.exists())
			throw new RuntimeException("File " + file + " doesn't exist.");

		if (!file.isDirectory())
			throw new RuntimeException("File " + file + " is not a directory.");

		if (!file.canWrite())
			throw new RuntimeException("File " + file + " is not writable.");

	}

	/**
	 * Ensure given file is plain, existing and readable file.
	 *
	 * @param file
	 *            to check
	 * @throws RuntimeException
	 *             if any file is not existent or not plain or not readable
	 */
	public static void isExistingReadibleFile(File file) {
		if (!file.exists())
			throw new RuntimeException("File does not exist: " + file.getAbsolutePath());

		if (!file.isFile())
			throw new RuntimeException("Not a file: " + file.getAbsolutePath());

		if (!file.canRead())
			throw new RuntimeException("File is not readable: " + file.getAbsolutePath());
	}

	/** convert file to {@code URI} */
	public static URI fileToURI(File file) {
		return URI.createURI(file.toURI().toString());
	}

}
