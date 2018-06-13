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

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.N4JSModel;
import org.eclipse.n4js.internal.N4JSProject;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * Utility methods for wildcards in project description e.g. in module filters
 */
public class WildcardPathFilterHelper {

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private N4JSModel n4jsModel;

	/** @return true iff the given location is matched the given filter */
	public boolean isPathContainedByFilter(URI location, ModuleFilter filter) {

		for (ModuleFilterSpecifier spec : filter.getModuleSpecifiers()) {
			String prjRelativeLocation = getProjectRelativeLocation(location, spec);
			if (prjRelativeLocation != null) {
				return locationMatchesGlobSpecifier(spec, prjRelativeLocation);
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

		Path locationPath = Paths.get(location.toString());
		String filterSrcCont = spec.getSourcePath();
		if (filterSrcCont == null || filterSrcCont.equals(sourceContainer.getRelativeLocation())) {
			N4JSProject project = (N4JSProject) sourceContainer.getProject();
			Path prjLocationPath = Paths.get(project.getLocation().toString());
			Preconditions.checkState(locationPath.startsWith(prjLocationPath));
			Path prjRelativeLocationPath = prjLocationPath.relativize(locationPath);
			for (IN4JSSourceContainer srcCont : n4jsModel.getN4JSSourceContainers(project)) {
				Path srcContLocationPath = Paths.get(srcCont.getRelativeLocation());
				if (prjRelativeLocationPath.startsWith(srcContLocationPath)) {
					Path srcRelativeLocationPath = srcContLocationPath.relativize(prjRelativeLocationPath);
					return srcRelativeLocationPath.toString();
				}
			}
		}
		return null;
	}

	/** @return true iff the given location is matched the given GLOB specifier */
	private boolean locationMatchesGlobSpecifier(ModuleFilterSpecifier spec, String prjRelativeLocation) {
		String pathsToFind = spec.getModuleSpecifierWithWildcard();
		boolean matches = prjRelativeLocation.startsWith(pathsToFind);
		if (!matches) {
			String syntaxAndPattern = "glob:" + pathsToFind;
			PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(syntaxAndPattern);
			java.nio.file.Path path = Paths.get(prjRelativeLocation);
			matches = pathMatcher.matches(path);
		}
		return matches;
	}

}
