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
package org.eclipse.n4js.tests.externalPackages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.eclipse.n4js.utils.io.FileCopier;

/**
 * Utilities for external workspace tests.
 */
public class ExternalProjectsTestUtil {

	private static final Logger LOGGER = Logger.getLogger(ExternalProjectsTestUtil.class);

	/**
	 * Convenience method over {@link ExternalProjectsTestUtil#copySingleProjectToLocation(String, Path, Path)}.
	 */
	public static void copyProjectsToLocation(Path sourceLocation, Path targetLocation, String... projectsNames)
			throws Exception {

		for (int i = 0; i < projectsNames.length; i++) {
			String string = projectsNames[i];
			copySingleProjectToLocation(string, sourceLocation, targetLocation);
		}
	}

	/**
	 * Copies given folder structure form resources to the given location in the file system. Symlinks are not followed.
	 *
	 * @param projectName
	 *            folder to copy from resources (probands)
	 * @param target
	 *            location to copy to
	 */
	private static void copySingleProjectToLocation(String projectName, Path source, Path target)
			throws RuntimeException {

		File fromFile = source.resolve(projectName).toFile().getAbsoluteFile();
		if (fromFile.exists() == false || fromFile.isDirectory() == false) {
			throw new RuntimeException("can't obtain proper proband " + projectName);
		}

		Path toPath = target.resolve(projectName);
		File toFile = toPath.toFile();
		if (toFile.exists()) {
			if (!toFile.isDirectory()) {
				throw new RuntimeException("desired location is occupied " + toPath.toAbsolutePath().toString());
			}
			LOGGER.info("re using existing location " + toPath.toAbsolutePath().toString());
		} else {
			if (!toFile.mkdirs()) {
				throw new RuntimeException(
						"can't create directory at desired location " + toPath.toAbsolutePath().toString());
			}
		}

		try {
			FileCopier.copy(fromFile.toPath(), toPath);
		} catch (IOException ieo) {
			throw new RuntimeException("errors copying probands to the external location", ieo);
		}
	}

}
