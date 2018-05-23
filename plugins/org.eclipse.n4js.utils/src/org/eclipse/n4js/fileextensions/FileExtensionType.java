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

/**
 * The type of file extension, e.g. runnable file extension.
 */
public enum FileExtensionType {
	/**
	 * Runnable file extension, e.g. {@code n4js}. Those files are subjects to the runners in the IDE.
	 */
	RUNNABLE_FILE_EXTENSION,
	/**
	 * Raw file extension, e.g. {@code js}. Those files are excluded from code transformations, e.g. skipped by
	 * {@code Organize Imports}
	 */
	RAW_FILE_EXTENSION,
	/**
	 * Testable file extension, e.g. {@code n4js}. Those files are subjects to the testers in the IDE.
	 */
	TESTABLE_FILE_EXTENSION,
	/**
	 * Transpilable file extension, e.g. {@code n4js}, {@code js}. Those files will be passed through transpilation
	 * pipeline in IDE. Note that some files can be marked as {@link #RAW_FILE_EXTENSION} which excludes them from
	 * <b>original</b> source transformation and still be marked as {@link #TRANSPILABLE_FILE_EXTENSION} which allows
	 * IDE to do certain manipulations of the emitted code. Example of that would be {@code js} for which we don't do
	 * transformations of the original code (e.g. {@code Organize Imports} is skipping those), still when compiling
	 * Emitted code might be subject to transpilation (e.g. adding module wrapping around original source code).
	 */
	TRANSPILABLE_FILE_EXTENSION,
	/**
	 * Files affect type resolution. Note that this might include some surprising entries, e.g. {@code n4jsd} and
	 * {@code js}. In general expect here all file extensions for all supported languages that you can encounter as
	 * files in the N4IDE. This information may be used in various context, e.g. calculating changes in source
	 * containers, organizing imports is source files, etc.
	 *
	 * Some unexpected entries here would include {@code manifest} files, {@code ts} files.
	 */
	TYPABLE_FILE_EXTENSION
}
