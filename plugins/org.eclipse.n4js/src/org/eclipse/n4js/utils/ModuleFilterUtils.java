/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilter;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterSpecifier;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshot;

import com.google.common.base.Preconditions;

/**
 * Utility methods for wildcards in {@link ModuleFilter module filters} of a {@link ProjectDescription project
 * description}.
 */
public class ModuleFilterUtils {

	/** @return true iff the given location is matched by the given filter. */
	public static boolean isPathContainedByFilter(N4JSProjectConfigSnapshot project, URI location,
			ModuleFilter filter) {
		return isPathContainedByFilter(project, location, filter.getSpecifiers());
	}

	/** @return true iff the given location is matched by one of the given filter specifiers. */
	public static boolean isPathContainedByFilter(N4JSProjectConfigSnapshot project, URI location,
			Iterable<? extends ModuleFilterSpecifier> filterSpecifiers) {
		for (ModuleFilterSpecifier spec : filterSpecifiers) {
			String prjRelativeLocation = getProjectRelativeLocation(project, location, spec);
			if (prjRelativeLocation != null) {
				boolean isContained = locationMatchesGlobSpecifier(spec, prjRelativeLocation);
				if (isContained) {
					return true;
				}
			}
		}
		return false;
	}

	/** @return true iff the given location is matched the given specifier. */
	public static boolean isPathContainedByFilter(N4JSProjectConfigSnapshot project, URI location,
			ModuleFilterSpecifier spec) {
		String prjRelativeLocation = getProjectRelativeLocation(project, location, spec);
		if (prjRelativeLocation != null) {
			return locationMatchesGlobSpecifier(spec, prjRelativeLocation);
		}
		return false;
	}

	private static String getProjectRelativeLocation(N4JSProjectConfigSnapshot project, URI location,
			ModuleFilterSpecifier spec) {

		N4JSSourceFolderSnapshot sourceContainer = project.findSourceFolderContaining(location);
		if (sourceContainer == null) {
			return null;
		}

		Path prjLocationPath = Paths.get(project.getPath().toString());
		Path locationPath = Paths.get(location.toString());
		Preconditions.checkState(locationPath.startsWith(prjLocationPath));
		Path prjRelativeLocationPath = prjLocationPath.relativize(locationPath);

		String filterSrcCont = spec.getSourcePath();
		if (filterSrcCont == null) {
			// e.g. noValidate { "**/*" }
			for (N4JSSourceFolderSnapshot srcCont : project.getSourceFolders()) {
				Path srcContLocationPath = Paths.get(srcCont.getRelativeLocation());
				if (prjRelativeLocationPath.startsWith(srcContLocationPath)) {
					Path srcRelativeLocationPath = srcContLocationPath.relativize(prjRelativeLocationPath);
					return srcRelativeLocationPath.toString();
				}
			}
		} else {
			// e.g. noValidate { "**/*" in "src" }
			if (filterSrcCont.equals(sourceContainer.getRelativeLocation())) {
				Path srcContLocationPath = Paths.get(sourceContainer.getRelativeLocation());
				Preconditions.checkState(prjRelativeLocationPath.startsWith(srcContLocationPath));
				Path srcRelativeLocationPath = srcContLocationPath.relativize(prjRelativeLocationPath);
				return srcRelativeLocationPath.toString();
			}
		}
		return null;
	}

	/** @return true iff the given location is matched the given GLOB specifier. */
	private static boolean locationMatchesGlobSpecifier(ModuleFilterSpecifier spec, String prjRelativeLocation) {
		String pathsToFind = spec.getSpecifierWithWildcard();
		if (pathsToFind == null) {
			return false;
		}
		boolean matches = prjRelativeLocation.startsWith(pathsToFind);
		if (!matches) {
			PathMatcher pathMatcher = createPathMatcher(pathsToFind);
			java.nio.file.Path path = Paths.get(prjRelativeLocation);
			matches = pathMatcher.matches(path);
		}
		return matches;
	}

	/**
	 * Similar to {@link FileSystem#getPathMatcher(String)} for "glob" syntax, but disables certain advance matching
	 * functionality that we do not want to make available in the N4JS language.
	 */
	@SuppressWarnings("resource")
	public static PathMatcher createPathMatcher(String pattern) {
		pattern = pattern.replace("\\", "\\\\"); // disable \ as an escape character (by escaping it)
		pattern = pattern.replace("{", "\\{").replace("}", "\\}"); // disable pattern groups
		pattern = pattern.replace("[", "\\[").replace("]", "\\]"); // disable character groups
		return FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}
}
