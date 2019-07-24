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
package org.eclipse.n4js.external;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

import com.google.common.collect.ImmutableList;

/**
 * Utilities and core rules for external libraries.
 */
public final class ExternalLibraryHelper {

	/** Unique name of the {@code npm} category. */
	public static final String NPM_CATEGORY = "node_modules";

	/** List of all categories. Latter entries shadow former entries. */
	public static final List<String> CATEGORY_SHADOWING_ORDER = ImmutableList.<String> builder()
			.add(NPM_CATEGORY)
			.build();

	/**
	 * Returns {@code true} iff the given {@link File} represents a project directory in the workspace that is available
	 * to the projects in the N4JS workspace in that workspace projects may declare dependencies on them.
	 *
	 * This excludes packages that have been installed to the external workspace as transitive dependency of a package
	 * that has been explicitly installed on user request.
	 */
	public boolean isExternalProjectDirectory(SafeURI<?> projectDirectory) {
		return projectDirectory instanceof FileURI && projectDirectory.isProjectRootDirectory();
	}

	/**
	 * Returns {@code true} iff the given {@link File} represents a directory that is considered an npm scope directory.
	 */
	public boolean isScopeDirectory(File scopeDirectory) {
		final String name = scopeDirectory.getName();
		return name.startsWith(ProjectDescriptionUtils.NPM_SCOPE_PREFIX) &&
				ProjectDescriptionUtils.isValidScopeName(name);
	}

	/** Sorts given set of locations and returns sorted list */
	public static List<FileURI> sortByShadowing(Collection<FileURI> locations) {
		Map<String, FileURI> knownLocations = new HashMap<>();
		List<FileURI> unknownLocations = new LinkedList<>();

		for (FileURI location : locations) {
			String locStr = location.toString();
			locStr = locStr.endsWith("/") ? locStr.substring(0, locStr.length() - 1) : locStr;

			boolean locationFound = false;
			for (String knownLocation : CATEGORY_SHADOWING_ORDER) {
				if (locStr.endsWith(knownLocation) && !knownLocations.containsKey(knownLocation)) {
					knownLocations.put(knownLocation, location);
					locationFound = true;
				}
			}

			if (!locationFound) {
				unknownLocations.add(location);
			}
		}

		List<FileURI> sortedLocations = new LinkedList<>();
		for (String knownLocation : CATEGORY_SHADOWING_ORDER) {
			FileURI location = knownLocations.get(knownLocation);
			if (location != null) {
				sortedLocations.add(location);
			}
		}
		sortedLocations.addAll(unknownLocations);

		return sortedLocations;
	}
}
