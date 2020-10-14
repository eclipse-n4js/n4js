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
import org.eclipse.n4js.ide.xtext.server.ResourceChangeSet;
import org.eclipse.n4js.ide.xtext.server.build.IBuildRequestFactory;
import org.eclipse.n4js.ide.xtext.server.build.ImmutableProjectState;
import org.eclipse.n4js.ide.xtext.server.build.ProjectBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XBuildRequest.AfterDeleteListener;
import org.eclipse.n4js.ide.xtext.server.build.XBuildResult;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.tester.TestCatalogSupplier;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

/**
 * Adds {@link #writeTestCatalog()} and {@link #removeTestCatalog()}.
 */
public class N4JSProjectBuilder extends ProjectBuilder {
	private static final Logger LOG = LogManager.getLogger(N4JSProjectBuilder.class);

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private TestCatalogSupplier testCatalogSupplier;

	@Override
	public N4JSProjectConfigSnapshot getProjectConfig() {
		return (N4JSProjectConfigSnapshot) super.getProjectConfig();
	}

	@Override
	public void setProjectConfig(ProjectConfigSnapshot newProjectConfig) {
		N4JSProjectConfigSnapshot oldPC = getProjectConfig();
		N4JSProjectConfigSnapshot newPCCasted = (N4JSProjectConfigSnapshot) newProjectConfig;
		boolean depsHaveChanged = oldPC == null
				|| !oldPC.getDependencies().equals(newProjectConfig.getDependencies());
		boolean sortedDepsHaveChanged = oldPC == null
				|| !oldPC.getSortedDependencies().equals(newPCCasted.getSortedDependencies());

		super.setProjectConfig(newProjectConfig);

		if (sortedDepsHaveChanged && !depsHaveChanged) {
			// note: not doing this if 'depsHaveChanged', because then the super method has already invoked
			// #onDependenciesChanged()
			onDependenciesChanged();
		}
	}

	@Override
	protected XBuildResult doBuild(IBuildRequestFactory buildRequestFactory, Set<URI> dirtyFiles, Set<URI> deletedFiles,
			List<Delta> externalDeltas, CancelIndicator cancelIndicator) {

		XBuildResult buildResult = super.doBuild(buildRequestFactory, dirtyFiles, deletedFiles, externalDeltas,
				cancelIndicator);

		writeTestCatalog();

		return buildResult;
	}

	@Override
	public void doClean(AfterDeleteListener deleteListener, CancelIndicator cancelIndicator) {
		super.doClean(deleteListener, cancelIndicator);

		removeTestCatalog();
	}

	@Override
	protected boolean handleProjectAdditionRemovalSinceProjectStateWasComputed(ResourceChangeSet result,
			ImmutableProjectState projectState) {

		if (getProjectConfig().getType() == ProjectType.PLAINJS) {
			return false;
		}
		return super.handleProjectAdditionRemovalSinceProjectStateWasComputed(result, projectState);
	}

	/** Generates the test catalog for the project. */
	private void writeTestCatalog() {
		ProjectConfigSnapshot projectConfig = getProjectConfig();
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
		ProjectConfigSnapshot projectConfig = getProjectConfig();
		File testCatalog = getTestCatalogFile(projectConfig);

		if (testCatalog.isFile()) {
			try {
				testCatalog.delete();

			} catch (Exception e) {
				LOG.error("Error while deleting test catalog file: " + testCatalog);
			}
		}
	}

	private File getTestCatalogFile(ProjectConfigSnapshot projectConfig) {
		return new File(projectConfig.getPath().toFileString(), N4JSGlobals.TEST_CATALOG);
	}
}
