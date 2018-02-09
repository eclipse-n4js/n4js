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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.google.common.io.Files;

/**
 *
 */
public final class FillesLocator {

	private final Set<File> foundNPMRC = new HashSet<>();
	private final Set<File> foundN4TP = new HashSet<>();
	private final Set<File> roots = new HashSet<>();

	private FillesLocator() {
	}

	public static FillesLocator findFiles(IProgressMonitor monitor) {

		try {
			// https://stackoverflow.com/questions/26530445/compare-directories-to-check-if-one-is-sub-directory-of-another
			// shallow scan
			Set<IContainer> folders = new HashSet<>();
			Set<File> files = new HashSet<>();
			final Set<File> roots = new HashSet<>();
			IContainer root = ResourcesPlugin.getWorkspace().getRoot();
			for (IResource member : root.members()) {
				File file = member.getLocation().makeAbsolute().toFile();
				if (file.isDirectory()) {
					roots.add(file);
				} else if (file.isFile()) {
					files.add(file);
				}
			}
			// shallowMonitor.worked(1);
			FillesLocator fl = new FillesLocator();

			fl.deepScan(roots, files, monitor);

			return fl;

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void deepScan(Set<File> roots, Set<File> files, IProgressMonitor monitor) throws CoreException {
		final SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
		final SubMonitor shallowMonitor = subMonitor.split(10);
		shallowMonitor.beginTask("Scanning workspace roots for config files", 10);
		shallowMonitor.beginTask("Shallow workspace scan...", files.size());
		for (File file : files) {
			processFile(file);
			shallowMonitor.worked(1);
		}

		Set<Path> actualRoots = getProjectsRoots(roots);

		final SubMonitor deepMonitor = subMonitor.split(90);
		deepMonitor.beginTask("Deep workspace scan...", roots.size());

		// deep scan
		for (Path path : actualRoots) {
			File root = path.toFile();
			deepMonitor.setTaskName("Scanning " + root.getName() + "...");
			processContainer(root);
			deepMonitor.worked(1);
		}
	}

	/**
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

	private Path getRepoRootOrArgument(File file) {
		File result = file;
		File parent = file;
		while (parent != null) {
			if (parent.isDirectory() && Arrays.asList(parent.list()).contains(".git"))
				result = parent;
			parent = parent.getParentFile();
		}
		return result.toPath().toAbsolutePath().normalize();
	}

	private boolean isContained(Path path, Collection<Path> paths) {
		for (Path member : paths) {
			if (path.startsWith(member))
				return true;
		}
		return false;
	}

	public Collection<File> getNPMRCs() {
		return new HashSet<>(this.foundNPMRC);
	}

	public Collection<File> getN4TPs() {
		return new HashSet<>(this.foundN4TP);
	}

	private void processContainer(File file) {
		if (file.isFile())
			processFile(file);
		else if (file.isDirectory() && !file.getName().equals(".git") && !file.getName().equals("node_modules"))
			Arrays.asList(file.listFiles()).forEach(this::processContainer);

	}

	private void processFile(File file) {
		if (isNPMRC(file))
			foundNPMRC.add(file);
		else if (isN4TP(file))
			foundN4TP.add(file);
	}

	private static boolean isNPMRC(File file) {
		return "npmrc".equals(Files.getFileExtension(file.getAbsolutePath()));
	}

	private static boolean isN4TP(File file) {
		return "n4tp".equals(Files.getFileExtension(file.getAbsolutePath()));
	}

}
