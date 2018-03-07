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
package org.eclipse.n4js.generator.headless;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.N4JSBrokenProjectException;
import org.eclipse.n4js.projectModel.IN4JSProject;

/**
 * Helper Methods to deal with projects and FileBasedWorkspace.
 */
public class HeadlessHelper {

	/**
	 * Configure fbWorkspace with all projects found in Subfolders of projectLocations.
	 *
	 * @param projectLocations
	 *            list of project roots
	 * @param n4jsFileBasedWorkspace
	 *            instance of FileBasedWorkspace to configure (in N4JS injector)
	 * @throws N4JSCompileException
	 *             in error Case.
	 */
	public static void registerProjects(List<File> projectLocations, FileBasedWorkspace n4jsFileBasedWorkspace)
			throws N4JSCompileException {
		// make absolute, since downstream URI conversion doesn't work if relative dir only.
		List<File> absProjectRoots = HeadlessHelper.toAbsoluteFileList(projectLocations);

		// Collect all Projects in first Level
		List<File> pDir = HeadlessHelper.collectAllProjectPaths(absProjectRoots);

		for (File pdir : pDir) {
			URI puri = URI.createFileURI(pdir.toString());

			try {
				n4jsFileBasedWorkspace.registerProject(puri);
			} catch (N4JSBrokenProjectException e) {
				throw new N4JSCompileException("Unable to register project '" + puri + "'", e);
			}
		}
	}

	/**
	 * Creates a new list, calling {@link File#getAbsoluteFile()} on each member
	 *
	 * @param relativeFiles
	 *            list of possibly relative paths.
	 * @return new list of absolute paths.
	 * @throws N4JSCompileException
	 *             in case of underlying IO-problems
	 */
	static List<File> toAbsoluteFileList(List<File> relativeFiles) throws N4JSCompileException {
		List<File> absProjectRoots = new ArrayList<>(relativeFiles.size());
		for (File relPR : relativeFiles) {
			try {
				absProjectRoots.add(relPR.getCanonicalFile());
			} catch (IOException e) {
				throw new N4JSCompileException("Unable to create canonical filename of file '" + relPR + "'", e);
			}
		}
		return absProjectRoots;
	}

	/**
	 * Searches for direct sub-folders containing a File named {@link IN4JSProject#N4MF_MANIFEST}
	 *
	 * @param absProjectRoots
	 *            all project root (must be absolute)
	 * @return list of directories being a project
	 */
	static List<File> collectAllProjectPaths(List<File> absProjectRoots) {
		return absProjectRoots.stream()
				// find all contained folders
				.flatMap(root -> Arrays.asList(root.listFiles(File::isDirectory)).stream())
				// only those with manifest
				.filter(dir -> new File(dir, IN4JSProject.N4MF_MANIFEST).isFile())
				.collect(Collectors.toList());
	}

}
