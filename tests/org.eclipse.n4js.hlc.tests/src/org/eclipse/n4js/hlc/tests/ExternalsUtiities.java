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
import org.eclipse.n4js.utils.io.FileUtils;

/**
 * Utility for setting up external libraries for HLC tests.
 */
public class ExternalsUtiities {

	/**
	 * Initializes the target platform install location and the target platform file with the desired dependencies.
	 * Performs a sanity check, neither install location, nor the target platform file should exist.
	 */
	public static void setupExternals(TargetPlatformFiles platformFiles, String tmpPrefix,
			Map<String, String> getNpmDependencies) throws IOException {
		checkState(null == platformFiles.targetPlatformInstallLocation);
		checkState(null == platformFiles.targetPlatformFile);

		final Path tempRoot = createTempDirectory("hlcTest-time-" + System.currentTimeMillis());
		platformFiles.root = tempRoot.toFile();
		platformFiles.targetPlatformInstallLocation = createDirectory(tempRoot, tmpPrefix).toFile();
		final TargetPlatformModel model = new TargetPlatformModel();
		for (final Entry<String, String> dependencyEntry : getNpmDependencies.entrySet()) {
			model.addNpmDependency(dependencyEntry.getKey(), dependencyEntry.getValue());
		}

		platformFiles.targetPlatformFile = new File(tempRoot.toFile(), TP_FILE_NAME);
		platformFiles.targetPlatformFile.createNewFile();

		try (FileWriter fw = new FileWriter(platformFiles.targetPlatformFile)) {
			fw.write(model.toString());
			fw.flush();
		}
	}

	/**
	 * Cleans up the target platform install location and the actual target platform file.
	 */
	public static void cleanupExternals(TargetPlatformFiles platformFiles) {
		if (null != platformFiles.targetPlatformFile) {
			FileUtils.deleteFileOrFolder(platformFiles.targetPlatformFile);
			platformFiles.targetPlatformFile = null;
		}
		if (null != platformFiles.targetPlatformInstallLocation) {
			FileUtils.deleteFileOrFolder(platformFiles.targetPlatformInstallLocation);
			platformFiles.targetPlatformInstallLocation = null;
		}
		if (null != platformFiles.root) {
			FileUtils.deleteFileOrFolder(platformFiles.root);
			platformFiles.root = null;
		}
	}

}
