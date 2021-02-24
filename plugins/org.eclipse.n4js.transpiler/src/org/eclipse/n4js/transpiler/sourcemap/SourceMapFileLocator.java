/**
 * Copyright (c) 2018 Jens von Pilgrim.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jens von Pilgrim - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.sourcemap;

import java.io.File;
import java.nio.file.Path;

/**
 * Helper class locating source map from generated (JavaScript) or source (n4js) files.
 */
public class SourceMapFileLocator {

	/**
	 * Resolves the source map for a given generated (JavaScript) file.
	 *
	 * @return existing source map file or null, if no such file exists
	 */
	public static File resolveSourceMapFromGen(Path genLocation) {
		Path genParentPath = genLocation.getParent();
		String nameWithoutExtension = extractSimpleFilenameWithoutExtension(genLocation);
		return resolveMapFileInGenPath(nameWithoutExtension, genParentPath);
	}

	private static String extractSimpleFilenameWithoutExtension(Path location) {
		String filename = location.getFileName().toString();
		int i = filename.lastIndexOf('.');
		if (i < 0) {
			return filename;
		}
		return filename.substring(0, i);
	}

	private static File resolveMapFileInGenPath(String nameWithoutExt, Path genParentPath) {
		Path mapFilePath = genParentPath.resolve(nameWithoutExt + ".map");
		File file = mapFilePath.toFile();
		if (!file.exists()) {
			mapFilePath = genParentPath.resolve(nameWithoutExt + ".js.map");
			file = mapFilePath.toFile();
		}
		if (file.exists()) {
			return file;
		}
		return null;
	}

}
