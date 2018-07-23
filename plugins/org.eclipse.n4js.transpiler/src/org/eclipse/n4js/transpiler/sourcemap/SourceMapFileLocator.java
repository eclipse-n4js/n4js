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

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Helper class locating source map from generated (JavaScript) or source (n4js) files. This class needs to be injected
 * since it needs access to IN4JSCore.
 */
public class SourceMapFileLocator {

	@Inject
	IN4JSCore n4jsCore;

	/**
	 * Resolves the source map for a given source (N4JS) file.
	 *
	 * @return existing source map file or null, if no such file exists
	 */
	public File resolveSourceMapFromSrc(Path srcLocation) throws Exception {
		URI uri = URI.createFileURI(srcLocation.normalize().toAbsolutePath().toString());
		Optional<? extends IN4JSSourceContainer> optSrcContainer = n4jsCore.findN4JSSourceContainer(uri);
		if (optSrcContainer.isPresent()) {
			IN4JSSourceContainer srcContainer = optSrcContainer.get();
			Path projectPath = srcContainer.getProject().getLocationPath();
			Path srcPath = projectPath.resolve(srcContainer.getRelativeLocation());
			Path modulePath = srcPath.relativize(srcLocation).getParent();
			String nameWithoutExt = extractSimpleFilenameWithoutExtension(srcLocation);
			String outPath = srcContainer.getProject().getOutputPath();
			Path genParentPath = projectPath.resolve(outPath);
			if (modulePath != null)
				genParentPath = genParentPath.resolve(modulePath);
			return resolveMapFileInGenPath(nameWithoutExt, genParentPath);
		}
		return null;
	}

	/**
	 * Resolves the source map for a given generated (JavaScript) file.
	 *
	 * @return existing source map file or null, if no such file exists
	 */
	public File resolveSourceMapFromGen(Path genLocation) throws Exception {
		Path genParentPath = genLocation.getParent();
		String nameWithoutExtension = extractSimpleFilenameWithoutExtension(genLocation);
		return resolveMapFileInGenPath(nameWithoutExtension, genParentPath);
	}

	private String extractSimpleFilenameWithoutExtension(Path location) {
		String filename = location.getFileName().toString();
		int i = filename.lastIndexOf('.');
		if (i < 0) {
			return filename;
		}
		return filename.substring(0, i);
	}

	private File resolveMapFileInGenPath(String nameWithoutExt, Path genParentPath) {
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
