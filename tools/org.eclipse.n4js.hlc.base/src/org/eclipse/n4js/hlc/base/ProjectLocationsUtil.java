/**
 * Copyright (c) 2018 NumberFour AG.
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
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

/**
 * Utility for transforming various inputs into {@link File}s denoting project locations.
 */
public class ProjectLocationsUtil {

	/**
	 * @param dirpaths
	 *            one or more paths separated by {@link File#pathSeparatorChar} OR empty string if no paths given.
	 */
	public static List<File> convertToFiles(String dirpaths) {
		final List<File> retList = new ArrayList<>();
		if (!Strings.isNullOrEmpty(dirpaths)) {
			for (String dirpath : Splitter.on(File.pathSeparatorChar).split(dirpaths)) {
				final File ret = new File(dirpath);
				HlcFileUtils.isExistingWriteableDir(ret);
				retList.add(ret);
			}
		}

		return retList;
	}

}
