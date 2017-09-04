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
package org.eclipse.n4js.hlc.tests;

import static com.google.common.base.Preconditions.checkState;
import static org.eclipse.n4js.external.libraries.TargetPlatformModel.TP_FILE_NAME;
import static org.eclipse.n4js.utils.io.FileUtils.createDirectory;
import static org.eclipse.n4js.utils.io.FileUtils.createTempDirectory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.n4js.external.libraries.TargetPlatformModel;

/**
 * Utility for setting up external libraries for HLC tests.
 */
public class ExternalsUtiities {

	/**
	 * Initializes the target platform install location and the target platform file with the desired dependencies.
	 * Performs a sanity check, neither install location, nor the target platform file should exist.
	 */
	public static void setupExternals(File targetPlatformInstallLocation,
			File targetPlatformFile, String tmpPrefix, Map<String, String> getNpmDependencies) throws IOException {
		checkState(null == targetPlatformInstallLocation);
		checkState(null == targetPlatformFile);

		final Path tempRoot = createTempDirectory();
		final String tempFolderName = tmpPrefix + "-time-" + System.currentTimeMillis();
		targetPlatformInstallLocation = createDirectory(tempRoot, tempFolderName).toFile();
		final TargetPlatformModel model = new TargetPlatformModel();
		for (final Entry<String, String> dependencyEntry : getNpmDependencies.entrySet()) {
			model.addNpmDependency(dependencyEntry.getKey(), dependencyEntry.getValue());
		}

		targetPlatformFile = new File(tempRoot.toFile(), TP_FILE_NAME);
		targetPlatformFile.createNewFile();

		try (FileWriter fw = new FileWriter(targetPlatformFile)) {
			fw.write(model.toString());
			fw.flush();
		}
	}

	/**
	 * Cleans up the target platform install location and the actual target platform file.
	 */
	public static void cleanupExternals(File targetPlatformInstallLocation,
			File targetPlatformFile) {
		if (null != targetPlatformFile) {
			targetPlatformFile.delete();
			targetPlatformFile = null;
		}
		if (null != targetPlatformInstallLocation) {
			targetPlatformInstallLocation.delete();
			targetPlatformInstallLocation = null;
		}
	}

}
