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
package org.eclipse.n4js.hlc.base;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.external.TargetPlatformInstallLocationProvider;
import org.eclipse.n4js.generator.headless.N4JSCompileException;
import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.utils.parsing.ProjectDescriptionProviderUtil;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.dependencies.DependenciesCollectingUtil;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Headless version of the org.eclipse.n4js.ui.handler.ProjectDependenciesHelper
 */
class DependenciesHelper {

	@Inject
	private TargetPlatformInstallLocationProvider installLocationProvider;

	@Inject
	private FileBasedWorkspace n4jsFileBasedWorkspace;

	// TODO copied from org.eclipse.n4js.generator.headless.N4HeadlessCompiler.collectAndRegisterProjects(List<File>,
	// List<File>, List<File>)
	Map<String, String> discoverMissingDependencies(String projectLocations, List<File> srcFiles) {

		List<File> allProjectsRoots = new ArrayList<>();

		List<File> locations = convertToFilesAddTargetPlatformAndCheckWritableDir(projectLocations);
		// Discover projects in search paths.
		List<File> discoveredProjectLocations = collectAllProjectPaths(locations);

		// Discover projects for single source files.
		List<File> singleSourceProjectLocations = new ArrayList<>();
		try {
			singleSourceProjectLocations.addAll(findProjectsForSingleFiles(srcFiles));
		} catch (N4JSCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("discoveredProjectLocations");
		discoveredProjectLocations.forEach(f -> System.out.println(" - " + f));

		System.out.println("singleSourceProjectLocations");
		singleSourceProjectLocations.forEach(f -> System.out.println(" - " + f));

		allProjectsRoots.addAll(discoveredProjectLocations);
		allProjectsRoots.addAll(singleSourceProjectLocations);

		Map<String, String> dependencies = new HashMap<>();

		DependenciesCollectingUtil.updateMissingDependneciesMap(dependencies,
				getAvailableProjectsDescriptions(allProjectsRoots));

		return dependencies;

	}

	private Iterable<ProjectDescription> getAvailableProjectsDescriptions(List<File> allProjectsRoots) {
		List<ProjectDescription> descriptions = new ArrayList<>();
		allProjectsRoots.forEach(root -> {
			File manifest = new File(root, IN4JSProject.N4MF_MANIFEST);
			if (!manifest.isFile()) {
				System.out.println("CANNOT READ MANIFEST AT " + root);
				return;
			}
			ProjectDescription projectDescription = ProjectDescriptionProviderUtil.getFromFile(manifest);
			descriptions.add(projectDescription);

		});
		return descriptions;
	}

	/**
	 * Collects the projects containing the given single source files.
	 *
	 * @param sourceFiles
	 *            the list of single source files
	 * @return list of N4JS project locations
	 * @throws N4JSCompileException
	 *             if no project cannot be found for one of the given files
	 */
	// TODO copied from org.eclipse.n4js.generator.headless.N4HeadlessCompiler.findProjectsForSingleFiles(List<File>)
	private List<File> findProjectsForSingleFiles(List<File> sourceFiles)
			throws N4JSCompileException {

		Set<URI> result = Sets.newLinkedHashSet();

		for (File sourceFile : sourceFiles) {
			URI sourceFileURI = URI.createFileURI(sourceFile.toString());
			URI projectURI = n4jsFileBasedWorkspace.findProjectWith(sourceFileURI);
			if (projectURI == null) {
				throw new N4JSCompileException("No project for file '" + sourceFile.toString() + "' found.");
			}
			result.add(projectURI);
		}

		// convert back to Files:
		return result.stream().map(u -> new File(u.toFileString())).collect(Collectors.toList());
	}

	/**
	 * Searches for direct sub-folders containing a File named {@link IN4JSProject#N4MF_MANIFEST}
	 *
	 * @param absProjectRoots
	 *            all project root (must be absolute)
	 * @return list of directories being a project
	 */
	// TODO copied org.eclipse.n4js.generator.headless.HeadlessHelper.collectAllProjectPaths(List<File>)
	static ArrayList<File> collectAllProjectPaths(List<File> absProjectRoots) {
		ArrayList<File> pDir = new ArrayList<>();
		for (File projectRoot : absProjectRoots) {
			Arrays.asList(projectRoot.listFiles(f -> {
				return f.isDirectory(); // all directrories
			}))//
					.stream() //
					.filter(f -> {
						File[] list = f.listFiles(f2 -> f2.getName().equals(IN4JSProject.N4MF_MANIFEST));
						return list != null && list.length > 0; // only those with manifest.n4mf
					}) //
					.forEach(f -> pDir.add(f));
		}
		return pDir;
	}

	/**
	 * @param dirpaths
	 *            one or more paths separated by {@link File#pathSeparatorChar} OR empty string if no paths given.
	 */
	// TODO copied from org.eclipse.n4js.hlc.base.N4jscBase.convertToFilesAddTargetPlatformAndCheckWritableDir(String)
	private List<File> convertToFilesAddTargetPlatformAndCheckWritableDir(String dirpaths) {
		final List<File> retList = new ArrayList<>();
		if (null != installLocationProvider.getTargetPlatformInstallLocation()) {
			final File tpLoc = new File(installLocationProvider.getTargetPlatformNodeModulesLocation());
			FileUtils.isExistingWriteableDir(tpLoc);
			retList.add(tpLoc);
		}
		if (!dirpaths.isEmpty()) {
			for (String dirpath : Splitter.on(File.pathSeparatorChar).split(dirpaths)) {
				final File ret = new File(dirpath);
				FileUtils.isExistingWriteableDir(ret);
				retList.add(ret);
			}
		}

		return retList;
	}

}
