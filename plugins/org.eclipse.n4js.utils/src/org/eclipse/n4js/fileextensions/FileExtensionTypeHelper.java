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
package org.eclipse.n4js.fileextensions;

import com.google.inject.Inject;

/**
 * Helper to inspect if a given (derived) file extension is matching concrete {@link FileExtensionType}.
 */
public class FileExtensionTypeHelper {

	@Inject
	private FileExtensionsRegistry fileExtensionsRegistry;

	/**
	 * Checks if a given file extension is of expected type
	 *
	 * @param fileExtension
	 *            without the leading dot e.g. {@code txt} (not {@code .txt})
	 * @param extensionType
	 *            to test against
	 */
	public boolean isOfType(String fileExtension, FileExtensionType extensionType) {
		return fileExtensionsRegistry.getFileExtensions(extensionType).contains(fileExtension);
	}

	/**
	 * Delegates to {@link #isOfType} with {@link FileExtensionType#RUNNABLE_FILE_EXTENSION runnable extension type}
	 */
	public boolean isRunnable(String fileExtension) {
		return isOfType(fileExtension, FileExtensionType.RUNNABLE_FILE_EXTENSION);
	}

	/**
	 * Delegates to {@link #isOfType} with {@link FileExtensionType#TRANSPILABLE_FILE_EXTENSION transpilable extension
	 * type}
	 */
	public boolean isTranspilable(String fileExtension) {
		return isOfType(fileExtension, FileExtensionType.TRANSPILABLE_FILE_EXTENSION);
	}

	/**
	 * Delegates to {@link #isOfType} with {@link FileExtensionType#TESTABLE_FILE_EXTENSION testable extension type}
	 */
	public boolean isTestable(String fileExtension) {
		return isOfType(fileExtension, FileExtensionType.TESTABLE_FILE_EXTENSION);
	}

	/**
	 * Delegates to {@link #isOfType} with {@link FileExtensionType#TYPABLE_FILE_EXTENSION typable extension type}
	 */
	public boolean isTypable(String fileExtension) {
		return isOfType(fileExtension, FileExtensionType.TYPABLE_FILE_EXTENSION);
	}
}
