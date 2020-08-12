/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.build;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.xtext.server.build.IBuildRequestFactory;
import org.eclipse.n4js.ide.xtext.server.build.ProjectBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XBuildResult;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tester.TestCatalogSupplier;
import org.eclipse.n4js.xtext.workspace.XIProjectConfig;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

/**
 * Adds {@link #writeTestCatalog()} and {@link #removeTestCatalog()}.
 */
@SuppressWarnings("restriction")
public class N4JSProjectBuilder extends ProjectBuilder {
	private static final Logger LOG = LogManager.getLogger(N4JSProjectBuilder.class);

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private TestCatalogSupplier testCatalogSupplier;

	@Override
	protected XBuildResult doBuild(IBuildRequestFactory buildRequestFactory, Set<URI> dirtyFiles, Set<URI> deletedFiles,
			List<Delta> externalDeltas, CancelIndicator cancelIndicator) {

		XBuildResult buildResult = super.doBuild(buildRequestFactory, dirtyFiles, deletedFiles, externalDeltas,
				cancelIndicator);

		writeTestCatalog();

		return buildResult;
	}

	@Override
	protected void doClearWithoutNotification() {
		super.doClearWithoutNotification();

		removeTestCatalog();
	}

	/** Generates the test catalog for the project. */
	private void writeTestCatalog() {
		XIProjectConfig projectConfig = getProjectConfig();
		IN4JSProject project = n4jsCore.findProject(new N4JSProjectName(projectConfig.getName())).orNull();
		File testCatalog = getTestCatalogFile(projectConfig);

		String catalog = testCatalogSupplier.get(
				getResourceSet(),
				project,
				true); // do not include "endpoint" property here

		if (catalog != null) {
			try (FileWriter fileWriter = new FileWriter(testCatalog)) {
				fileWriter.write(catalog);

			} catch (IOException e) {
				LOG.error("Error while writing test catalog file: " + testCatalog);
			}
		}
	}

	/** Removes the test catalog for the project. */
	private void removeTestCatalog() {
		XIProjectConfig projectConfig = getProjectConfig();
		File testCatalog = getTestCatalogFile(projectConfig);

		if (testCatalog.isFile()) {
			try {
				testCatalog.delete();

			} catch (Exception e) {
				LOG.error("Error while deleting test catalog file: " + testCatalog);
			}
		}
	}

	private File getTestCatalogFile(XIProjectConfig projectConfig) {
		return new File(projectConfig.getPath().toFileString(), N4JSGlobals.TEST_CATALOG);
	}
}
