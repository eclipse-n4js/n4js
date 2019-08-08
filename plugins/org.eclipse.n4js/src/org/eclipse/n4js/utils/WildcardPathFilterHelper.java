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
package org.eclipse.n4js.utils;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectDescription.ModuleFilter;
import org.eclipse.n4js.projectDescription.ModuleFilterSpecifier;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * Utility methods for wildcards in project description, e.g. in module filters
 */
public class WildcardPathFilterHelper {

	@Inject
	private IN4JSCore n4jsCore;

	/** @return true iff the given location is matched the given filter */
	public boolean isPathContainedByFilter(URI location, ModuleFilter filter) {
		for (ModuleFilterSpecifier spec : filter.getModuleSpecifiers()) {
			String prjRelativeLocation = getProjectRelativeLocation(location, spec);
			if (prjRelativeLocation != null) {
				boolean isContained = locationMatchesGlobSpecifier(spec, prjRelativeLocation);
				if (isContained) {
					return true;
				}
			}
		}
		return false;
	}

	/** @return true iff the given location is matched the given specifier */
	public boolean isPathContainedByFilter(URI location, ModuleFilterSpecifier spec) {
		String prjRelativeLocation = getProjectRelativeLocation(location, spec);
		if (prjRelativeLocation != null) {
			return locationMatchesGlobSpecifier(spec, prjRelativeLocation);
		}
		return false;
	}

	private String getProjectRelativeLocation(URI location, ModuleFilterSpecifier spec) {
		IN4JSSourceContainer sourceContainer = n4jsCore.findN4JSSourceContainer(location).orNull();
		if (sourceContainer == null) {
			return null;
		}

		IN4JSProject project = sourceContainer.getProject();
		Path prjLocationPath = Paths.get(project.getLocation().toURI().toString());
		Path locationPath = Paths.get(location.toString());
		Preconditions.checkState(locationPath.startsWith(prjLocationPath));
		Path prjRelativeLocationPath = prjLocationPath.relativize(locationPath);

		String filterSrcCont = spec.getSourcePath();
		if (filterSrcCont == null) {
			// e.g. noValidate { "**/*" }
			for (IN4JSSourceContainer srcCont : project.getSourceContainers()) {
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

	/** @return true iff the given location is matched the given GLOB specifier */
	private boolean locationMatchesGlobSpecifier(ModuleFilterSpecifier spec, String prjRelativeLocation) {
		String pathsToFind = spec.getModuleSpecifierWithWildcard();
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
	public static PathMatcher createPathMatcher(String pattern) {
		pattern = pattern.replace("\\", "\\\\"); // disable \ as an escape character (by escaping it)
		pattern = pattern.replace("{", "\\{").replace("}", "\\}"); // disable pattern groups
		pattern = pattern.replace("[", "\\[").replace("]", "\\]"); // disable character groups
		return FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}
}
