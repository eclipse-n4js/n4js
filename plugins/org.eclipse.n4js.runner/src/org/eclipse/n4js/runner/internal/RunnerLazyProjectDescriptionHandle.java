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
package org.eclipse.n4js.runner.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.LazyProjectDescriptionHandle;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.utils.parsing.ManifestValuesParsingUtil;

import com.google.common.base.Strings;

/**
 * {@link LazyProjectDescriptionHandle} with custom manifest loading.
 */
class RunnerLazyProjectDescriptionHandle extends LazyProjectDescriptionHandle {
	private static final Logger LOGGER = Logger.getLogger(RunnerLazyProjectDescriptionHandle.class);

	RunnerLazyProjectDescriptionHandle(URI location, boolean archive) {
		super(location, archive, null);
	}

	@Override
	protected ProjectDescription loadManifest(URI manifest) {
		File manifestFile = new File(manifest.toFileString());

		ProjectDescription projectDescription = null;
		if (manifestFile.exists() == false || manifestFile.isDirectory()) {
			LOGGER.warn("Cannot find manifest file at " + manifestFile.getAbsolutePath());
			return projectDescription;
		}

		String text = null;
		try {
			text = new String(Files.readAllBytes(manifestFile.toPath()));
		} catch (IOException e) {
			LOGGER.warn("Cannot read manifest content at " + manifestFile.getAbsolutePath());
			return projectDescription;
		}

		if (Strings.isNullOrEmpty(text)) {
			LOGGER.warn("Cannot read manifest content at " + manifestFile.getAbsolutePath());
			return projectDescription;
		}

		try {
			projectDescription = ManifestValuesParsingUtil.parseProjectDescription(text).getAST();
		} catch (Exception e) {
			LOGGER.warn("Cannot parse manifest content at " + manifestFile.getAbsolutePath());
			return projectDescription;
		}
		return projectDescription;
	}
}
