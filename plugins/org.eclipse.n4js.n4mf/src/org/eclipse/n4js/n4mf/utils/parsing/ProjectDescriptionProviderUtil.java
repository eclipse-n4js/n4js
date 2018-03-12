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
package org.eclipse.n4js.n4mf.utils.parsing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.log4j.Logger;
import org.eclipse.n4js.n4mf.ProjectDescription;

import com.google.common.base.Strings;

/**
 * Utility for creating {@link ProjectDescription} based on the given {@code manifest} file.
 */
public class ProjectDescriptionProviderUtil {
	private static final Logger LOGGER = Logger.getLogger(ProjectDescriptionProviderUtil.class);

	/** Creates new instance of {@link ProjectDescription} (or null) based on the provided manifest file. */
	public static ProjectDescription getFromFile(File manifest) {
		ProjectDescription projectDescription = null;

		if (manifest == null) {
			LOGGER.warn("Provided file was null.");
			return projectDescription;
		}

		if (!manifest.isFile()) {
			LOGGER.warn("Cannot find manifest file at " + manifest.getAbsolutePath());
			return projectDescription;
		}

		String text = null;
		try {
			text = new String(Files.readAllBytes(manifest.toPath()));
		} catch (IOException e) {
			LOGGER.warn("Cannot read manifest content at " + manifest.getAbsolutePath());
			return projectDescription;
		}

		if (Strings.isNullOrEmpty(text)) {
			LOGGER.warn("No content read for manifest content at " + manifest.getAbsolutePath());
			return projectDescription;
		}

		try {
			projectDescription = ManifestValuesParsingUtil.parseProjectDescription(text).getAST();
		} catch (Exception e) {
			LOGGER.warn("Cannot parse manifest content at " + manifest.getAbsolutePath());
			return projectDescription;
		}
		return projectDescription;
	}

}
