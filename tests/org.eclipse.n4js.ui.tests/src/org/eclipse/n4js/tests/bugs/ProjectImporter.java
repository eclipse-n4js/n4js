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
package org.eclipse.n4js.tests.bugs;

import static com.google.common.collect.Sets.newHashSet;
import static org.eclipse.n4js.tests.util.ProjectTestsUtils.importProject;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.test.helper.hlc.N4jsLibsAccess;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil;

/**
 * Simple project importer implementation.
 */
public class ProjectImporter {

	private static final Logger LOG = LogManager.getLogger(ProjectImporter.class);

	/**
	 * The NOOP importer. Does not import anything into the workspace.
	 */
	public static ProjectImporter NOOP = new ProjectImporter();

	private final File rootFolder;
	private final Collection<N4JSProjectName> n4jsLibs;

	private ProjectImporter() {
		this(FileUtils.createTempDirectory().toFile());
	}

	/** Same as {@link #ProjectImporter(File, Collection)}, but never installs any 'n4js-libs'. */
	public ProjectImporter(final File rootFolder) {
		this(rootFolder, Collections.emptyList());
	}

	/**
	 * Creates a project importer with the root folder of all projects that has to be imported for the test.
	 *
	 * @param rootFolder
	 *            the root folder.
	 * @param n4jsLibs
	 *            names of 'n4js-libs' to install from local Git clone (see {@link N4jsLibsAccess}) or an empty list if
	 *            nothing should be installed.
	 */
	public ProjectImporter(final File rootFolder, Collection<N4JSProjectName> n4jsLibs) {
		assertNotNull("Root folder cannot be null.", rootFolder);
		assertTrue("Root folder does not exist: " + rootFolder, rootFolder.exists());
		assertTrue("Root folder must be a folder. But was a file: " + rootFolder, rootFolder.isDirectory());
		assertTrue("Root folder directory content cannot be read: " + rootFolder, rootFolder.canRead());
		assertTrue("No files were found in the directory: " + rootFolder, null != rootFolder.listFiles());
		this.rootFolder = rootFolder;
		this.n4jsLibs = new ArrayList<>(n4jsLibs);
	}

	void importProjects() throws Exception {
		for (final File file : rootFolder.listFiles()) {
			if (file.exists() && file.isDirectory() && null != file.listFiles() && 0 < file.listFiles().length) {

				if (!newHashSet(file.list()).contains("_project")) {
					final N4JSProjectName projectName = new N4JSProjectName(file.getName());
					LOG.info("Project \'" + projectName
							+ "\' does not have .project file. Creating a temporary one on the fly...");
					final File dotProject = new File(file, "_project");
					assertTrue("Error while trying to create " + dotProject + " file for \'" + projectName
							+ "\' project.", dotProject.createNewFile());
					try (final PrintWriter pw = new PrintWriter(dotProject)) {
						pw.print(DotProjectContentProvider.getDotProjectContentForProject(projectName));
					}
					dotProject.deleteOnExit();
					LOG.info("Temporary .project file was successfully created for \'" + projectName + "\' project.");
				}

				LOG.info("Importing " + file.getName() + " into workspace...");
				importProject(rootFolder, new N4JSProjectName(file.getName()), n4jsLibs);
				LOG.info("Project " + file.getName() + " was successfully imported into the workspace.");
			} else {
				LOG.warn("Skipped importing project from " + file + ".");
			}
		}
		LOG.info("Waiting for full-build to complete...");
		IResourcesSetupUtil.cleanBuild(); // using full build after imports.
		LOG.info("Auto-build successfully completed.");
	}

}
