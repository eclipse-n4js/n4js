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
package org.eclipse.n4js.utils.io;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Joiner;

/**
 * Used by {@link FileUtils#compareDirectories(File, File, IDirectoryDiffAcceptor)} to report differences between two
 * directory trees.
 */
public interface IDirectoryDiffAcceptor {

	/**
	 * Invoked for files existing in the expected but missing in the actual directory tree.
	 */
	public void missing(Path relPath);

	/**
	 * Invoked for files in the actual directory tree without a corresponding file in the expected tree.
	 */
	public void unexpected(Path relPath);

	/**
	 * Invoked for files in the actual directory tree where the corresponding entry in the expected directory tree is a
	 * directory.
	 */
	public void fileInsteadOfDirectory(Path relPath);

	/**
	 * Invoked for directories in the actual directory tree where the corresponding entry in the expected directory tree
	 * is an ordinary file.
	 */
	public void directoryInsteadOfFile(Path relPath);

	/**
	 * Invoked for files in the actual directory tree with a corresponding file in the expected directory tree that has
	 * a different length.
	 */
	public void differentLength(Path relPath);

	/**
	 * Invoked for files in the actual directory tree with a corresponding file in the expected directory tree that has
	 * the same length but different content.
	 */
	public void differentContent(Path relPath, long pos);

	/**
	 * A default implementation of {@link IDirectoryDiffAcceptor} that collects all differing paths in fields.
	 */
	public static final class CollectingDirectoryDiffAcceptor implements IDirectoryDiffAcceptor {

		/** @see IDirectoryDiffAcceptor#unexpected(Path) */
		public final List<Path> unexpected = new ArrayList<>();
		/** @see IDirectoryDiffAcceptor#missing(Path) */
		public final List<Path> missing = new ArrayList<>();
		/** @see IDirectoryDiffAcceptor#fileInsteadOfDirectory(Path) */
		public final List<Path> fileInsteadOfDir = new ArrayList<>();
		/** @see IDirectoryDiffAcceptor#directoryInsteadOfFile(Path) */
		public final List<Path> dirInsteadOfFile = new ArrayList<>();
		/** @see IDirectoryDiffAcceptor#differentLength(Path) */
		public final List<Path> diffLength = new ArrayList<>();
		/** @see IDirectoryDiffAcceptor#differentContent(Path, long) */
		public final List<Pair<Path, Long>> diffContent = new ArrayList<>();

		/**
		 * Tells if this collector is empty, i.e. no differences were reported so far.
		 */
		public boolean isEmpty() {
			return unexpected.isEmpty() && missing.isEmpty() && fileInsteadOfDir.isEmpty() && dirInsteadOfFile.isEmpty()
					&& diffLength.isEmpty() && diffContent.isEmpty();
		}

		@Override
		public void unexpected(Path relPath) {
			unexpected.add(relPath);
		}

		@Override
		public void missing(Path relPath) {
			missing.add(relPath);
		}

		@Override
		public void fileInsteadOfDirectory(Path relPath) {
			fileInsteadOfDir.add(relPath);
		}

		@Override
		public void directoryInsteadOfFile(Path relPath) {
			dirInsteadOfFile.add(relPath);
		}

		@Override
		public void differentLength(Path relPath) {
			diffLength.add(relPath);
		}

		@Override
		public void differentContent(Path relPath, long pos) {
			diffContent.add(new Pair<>(relPath, pos));
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			if (isEmpty()) {
				sb.append("No differences.");
			} else {
				if (!unexpected.isEmpty()) {
					sb.append("unexpected:\n  ");
					sb.append(Joiner.on("\n  ").join(unexpected));
				}
				if (!missing.isEmpty()) {
					sb.append("missing:");
					sb.append(Joiner.on("\n  ").join(missing));
				}
				if (!fileInsteadOfDir.isEmpty()) {
					sb.append("file instead of directory:");
					sb.append(Joiner.on("\n  ").join(fileInsteadOfDir));
				}
				if (!dirInsteadOfFile.isEmpty()) {
					sb.append("directory instead of file:");
					sb.append(Joiner.on("\n  ").join(dirInsteadOfFile));
				}
				if (!diffLength.isEmpty()) {
					sb.append("differing length:");
					sb.append(Joiner.on("\n  ").join(diffLength));
				}
				if (!diffContent.isEmpty()) {
					sb.append("different content:");
					sb.append(Joiner.on("\n  ").join(diffContent));
				}
			}
			return sb.toString();
		}
	}
}
