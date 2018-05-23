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
package org.eclipse.n4js.utils.io;

import java.nio.file.Path;

import com.google.common.base.Predicate;

/**
 * Enumeration of file matching modes.
 */
public enum FileMatchingMode implements Predicate<Path> {

	/** Considers and accepts only file resources. */
	FILES {
		@Override
		public boolean apply(final Path path) {
			return null != path && path.toFile().isFile();
		}
	},

	/** Considers and accepts only directory resources. */
	DIRECTORIES {
		@Override
		public boolean apply(final Path path) {
			return null != path && path.toFile().isDirectory();
		}
	}

}
