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
package org.eclipse.n4js.ui.wizard.dependencies;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.n4js.N4JSGlobals;

import com.google.common.io.Files;

/**
 * Helper that scans file system of the projects in the workspace for {@code .npmrc} settings files. Considers projects
 * folders or their git repo folders as root folders to scan. Will avoid scanning same paths multiple times, even if
 * multiple workspace projects are contained in the same git repo.
 */
public final class ProjectsSettingsFilesLocator {
	private static final String GIT = ".git";
	private static final String NPMRC = "npmrc";
	private static final Logger LOGGER = Logger.getLogger(ProjectsSettingsFilesLocator.class);

	private final Set<File> foundNPMRC = new HashSet<>();

	/** force user to use {@link #findFiles(IProgressMonitor)} */
	private ProjectsSettingsFilesLocator() {
	}

	/** @return collected {@code .npmrc} files. */
	public Collection<File> getNPMRCs() {
		return new HashSet<>(this.foundNPMRC);
	}

	/**
	 * Performs scanning of the files system based on workspace projects.
	 *
	 * @return instance with all data found during scanning.
	 */
	public static ProjectsSettingsFilesLocator findFiles(IProgressMonitor monitor) {
		ProjectsSettingsFilesLocator locator = new ProjectsSettingsFilesLocator();

		if (monitor.isCanceled())
			return locator;

		Set<File> files = new HashSet<>();
		final Set<File> roots = new HashSet<>();
		IResource[] resources = getResources();
		if (resources == null)
			return locator;

		// initial sets of files and folders
		for (IResource resource : resources) {
			if (monitor.isCanceled())
				break;
			File file = resource.getLocation().makeAbsolute().toFile();
			if (file.isDirectory()) {
				roots.add(file);
			} else if (file.isFile()) {
				files.add(file);
			}
		}

		if (!monitor.isCanceled())
			locator.scan(roots, files, monitor);
		return locator;
	}

	private static IResource[] getResources() {
		IResource[] resources = null;
		try {
			resources = ResourcesPlugin.getWorkspace().getRoot().members();
		} catch (CoreException e) {
			LOGGER.error("Exception when scanning file system", e);
		}
		return resources;
	}

	/** performs shallow and then deep scan of the file system */
	private void scan(Set<File> roots, Set<File> files, IProgressMonitor monitor) {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		final SubMonitor shallowMonitor = subMonitor.split(10);
		shallowMonitor.beginTask("Scanning workspace roots for config files", 10);
		shallowMonitor.beginTask("Shallow workspace scan...", files.size());
		for (File file : files) {
			if (monitor.isCanceled())
				break;
			processFile(file);
			shallowMonitor.worked(1);
		}

		Set<Path> actualRoots = getProjectsRoots(roots);

		final SubMonitor deepMonitor = subMonitor.split(90);
		deepMonitor.beginTask("Deep workspace scan...", roots.size());
		for (Path path : actualRoots) {
			if (monitor.isCanceled())
				break;
			File root = path.toFile();
			deepMonitor.setTaskName("Scanning " + root.getName() + "...");
			processContainer(root);
			deepMonitor.worked(1);
		}
	}

	/**
	 * From the provided initial roots compute new set of actual roots. The actual root is either project folder or its
	 * parent that contains {@code .git} folder. Additionally roots that are sub-paths of other roots, will not be added
	 * (only other top most root).
	 */
	private Set<Path> getProjectsRoots(Set<File> roots) {
		Set<Path> actualRoots = new HashSet<>();

		for (File root : roots) {
			Path actualRoot = getRepoRootOrArgument(root);
			if (!isContained(actualRoot, actualRoots))
				actualRoots.add(actualRoot);
		}

		return actualRoots;
	}

	/**
	 * Traverse up hierarchy of folders, return first found folder containing {@code .git} folder, otherwise return
	 * start folder.
	 */
	private Path getRepoRootOrArgument(File file) {
		File result = file;
		File parent = file;
		while (parent != null) {
			if (parent.isDirectory() && Arrays.asList(parent.list()).contains(GIT))
				result = parent;
			parent = parent.getParentFile();
		}
		return result.toPath().toAbsolutePath().normalize();
	}

	/** Uses {@link Path#startsWith(Path)} for determining if a provided path is sub-path of other paths */
	private boolean isContained(Path path, Collection<Path> paths) {
		for (Path member : paths) {
			if (path.startsWith(member))
				return true;
		}
		return false;
	}

	/**
	 * If the argument is a file, than it is passed to {@link #processFile(File)}, if it is a folder, then its children
	 * are passed recursively to itself. Doesn't traverse children of folders with names {@link #GIT},
	 * {@link #NODE_MODULES}.
	 */
	private void processContainer(File file) {
		if (file.isFile())
			processFile(file);
		else if (file.isDirectory() && !file.getName().equals(GIT) && !file.getName().equals(N4JSGlobals.NODE_MODULES))
			Arrays.asList(file.listFiles()).forEach(this::processContainer);

	}

	/** If the extension of a given file matches {@link #NPMRC} then it is collected. */
	private void processFile(File file) {
		switch (Files.getFileExtension(file.getAbsolutePath())) {
		case NPMRC:
			foundNPMRC.add(file);
			break;
		default:
			break;
		}
	}

}
