/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility methods for moving files and folders.
 */
public class FileMover {

	/** Same as {@link #move(Path, Path)}, but accepting {@link File}s. */
	public static void move(File from, File to) throws IOException {
		move(from.toPath(), to.toPath());
	}

	/**
	 * Moves a single file or recursively an entire folder and its content.
	 * <p>
	 * Use this method in favor of {@link Files#move(Path, Path, java.nio.file.CopyOption...)}, because that method's
	 * behavior for non-empty folders depends on the operating system and the environment (e.g. whether 'from' and 'to'
	 * reside on the same {@link FileStore}), meaning it might succeed locally but still fail on Jenkins, etc. under
	 * certain circumstances.
	 */
	public static void move(Path from, Path to) throws IOException {
		FileCopier.copy(from, to);
		FileDeleter.delete(from);
	}
}
