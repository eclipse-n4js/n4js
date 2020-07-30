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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.ide.xtext.server.build.BuildTask;
import org.eclipse.n4js.ide.xtext.server.build.XWorkspaceBuilder;
import org.eclipse.n4js.ide.xtext.server.build.XWorkspaceManager;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.tester.TestCatalogSupplier;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper;
import org.eclipse.n4js.utils.NodeModulesDiscoveryHelper.NodeModulesFolder;
import org.eclipse.n4js.utils.ProjectDiscoveryHelper;
import org.eclipse.xtext.resource.IResourceDescription.Event;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Temporary solution until GH-1810 is merged.
 */
public class N4JSWorkspaceBuilder extends XWorkspaceBuilder {
	private static final Logger LOG = LogManager.getLogger(N4JSWorkspaceBuilder.class);

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private TestCatalogSupplier testCatalogSupplier;

	@Inject
	private XWorkspaceManager workspaceManager;

	@Inject
	private NodeModulesDiscoveryHelper nodeModulesDiscoveryHelper;

	@Inject
	private ProjectDiscoveryHelper projectDiscoveryHelper;

	@Override
	public BuildTask createIncrementalBuildTask(List<URI> dirtyFiles, List<URI> deletedFiles) {
		BuildTask buildTask = super.createIncrementalBuildTask(dirtyFiles, deletedFiles);

		BuildTask wrapperBuildTask = new BuildTask() {
			@Override
			public Event build(CancelIndicator cancelIndicator) {
				Event returnedEvent = buildTask.build(cancelIndicator);
				writeTestCatalogs();
				return returnedEvent;
			}
		};
		return wrapperBuildTask;
	}

	@Override
	public BuildTask createInitialBuildTask() {
		BuildTask buildTask = super.createInitialBuildTask();

		BuildTask wrapperBuildTask = new BuildTask() {
			@Override
			public Event build(CancelIndicator cancelIndicator) {
				Event returnedEvent = buildTask.build(cancelIndicator);
				writeTestCatalogs();
				return returnedEvent;
			}
		};
		return wrapperBuildTask;
	}

	private void writeTestCatalogs() {
		Set<IN4JSProject> projects = new HashSet<>();
		Iterables.addAll(projects, n4jsCore.findAllProjects());

		while (!projects.isEmpty()) {
			IN4JSProject nextProject = projects.iterator().next();
			Set<IN4JSProject> catalogsCovered = writeTestCatalog(nextProject);
			projects.removeAll(catalogsCovered);
		}
	}

	private Set<IN4JSProject> writeTestCatalog(IN4JSProject project) {
		Path projectLocation = project.getLocation().toFileURI().toFile().toPath();
		NodeModulesFolder nmf = nodeModulesDiscoveryHelper.getNodeModulesFolder(projectLocation);
		File nmfFile = nmf.isYarnWorkspace() ? nmf.workspaceNodeModulesFolder : nmf.localNodeModulesFolder;
		File projectRoot = nmfFile.getParentFile();
		File testCatalog = new File(projectRoot, N4JSGlobals.TEST_CATALOG);

		Set<IN4JSProject> catalogsCovered = new HashSet<>();
		catalogsCovered.add(project);
		if (nmf.isYarnWorkspace()) {
			IN4JSProject yarnProject = n4jsCore.findProject(URI.createFileURI(projectRoot.getPath())).orNull();
			if (yarnProject != null) {
				catalogsCovered.add(yarnProject);
				Set<Path> yarnSubProjectPaths = new HashSet<>();
				projectDiscoveryHelper.collectYarnWorkspaceProjects(projectRoot.toPath(), new HashMap<>(),
						yarnSubProjectPaths);

				for (Path yarnSubProjectPath : yarnSubProjectPaths) {
					URI yarnSubProjectUri = URI.createFileURI(yarnSubProjectPath.toString());
					IN4JSProject yarnSubProject = n4jsCore.findProject(yarnSubProjectUri).orNull();
					if (yarnSubProject != null) {
						catalogsCovered.add(yarnSubProject);
					}
				}
			}
		}

		String catalog = testCatalogSupplier.get(
				(uri) -> workspaceManager.getProjectBuilder(uri).getResourceSet(),
				catalogsCovered,
				true); // do not include "endpoint" property here

		try (OutputStream os = new BufferedOutputStream(new FileOutputStream(testCatalog))) {
			os.write(catalog.getBytes(StandardCharsets.UTF_8));
			os.flush();

		} catch (IOException e) {
			LOG.error("Error while writing test catalog file: " + testCatalog);
		}

		return catalogsCovered;
	}
}
