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

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.utils.io.FileUtils;

import com.google.common.base.Joiner;

/**
 * Miscellaneous utilities for dealing with {@link ProjectDescription}s and values stored within them.
 */
public class ProjectDescriptionUtils {

	public static final String NPM_SCOPE_PREFIX = "@";
	public static final char NPM_SCOPE_SEPARATOR = '/';
	public static final char NPM_SCOPE_SEPARATOR_ECLIPSE = '_';

	/**
	 * Tells if the given project name uses an npm scope, i.e. is of the form <code>@scopeName/projectName</code>.
	 */
	public static boolean isProjectNameWithScope(String projectName) {
		return projectName != null
				&& projectName.startsWith(NPM_SCOPE_PREFIX)
				&& projectName.indexOf(NPM_SCOPE_SEPARATOR) >= 0;
	}

	// returned name includes the NPM_SCOPE_PREFIX!
	public static String getScopeName(String projectName) {
		return isProjectNameWithScope(projectName)
				? projectName.substring(0, projectName.indexOf(NPM_SCOPE_SEPARATOR))
				: null;
	}

	public static String getProjectNameWithoutScope(String projectName) {
		return isProjectNameWithScope(projectName)
				? projectName.substring(projectName.indexOf(NPM_SCOPE_SEPARATOR))
				: projectName;
	}

	public static String deriveProjectNameFromURI(URI uri) {
		int segCount = uri.segmentCount();
		String last = segCount > 0 ? uri.segment(segCount - 1) : null;
		if (uri.isPlatform()) {
			if (last != null && last.startsWith(NPM_SCOPE_PREFIX)) {
				last = replaceFirst(last, NPM_SCOPE_SEPARATOR_ECLIPSE, NPM_SCOPE_SEPARATOR);
			}
			return last;
		}
		String secondToLast = segCount > 1 ? uri.segment(segCount - 2) : null;
		if (secondToLast != null && secondToLast.startsWith(NPM_SCOPE_PREFIX)) {
			return secondToLast + NPM_SCOPE_SEPARATOR + last;
		}
		return last;
	}

	// FIXME consider supporting the UI case, i.e. creating a platform URI
	public static URI deriveProjectURIFromFileLocation(File file) {
		try {
			URI createURI = URI.createURI(file.toURI().toURL().toString());
			// by convention IN4JSProject URI does not end with '/'
			// i.e. last segment must not be empty
			String last = createURI.lastSegment();
			if (last != null && last.isEmpty()) {
				createURI = createURI.trimSegments(1);
			}
			return createURI;
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static String convertN4JSProjectNameToEclipseProjectName(String name) {
		if (name != null && name.startsWith(NPM_SCOPE_PREFIX)) {
			return replaceFirst(name, NPM_SCOPE_SEPARATOR, NPM_SCOPE_SEPARATOR_ECLIPSE);
		}
		return name;
	}

	public static String convertEclipseProjectNameToN4JSProjectName(String name) {
		if (name != null && name.startsWith(NPM_SCOPE_PREFIX)) {
			return replaceFirst(name, NPM_SCOPE_SEPARATOR_ECLIPSE, NPM_SCOPE_SEPARATOR);
		}
		return name;
	}

	private static String replaceFirst(String str, char oldChar, char newChar) {
		if (str != null) {
			final int idx = str.indexOf(oldChar);
			if (idx >= 0) {
				return str.substring(0, idx) + newChar + str.substring(idx + 1);
			}
		}
		return str;
	}

	/**
	 * Given a path to the main module of an NPM project as given by the "main" property in a package.json, this method
	 * will return the corresponding N4JS module specifier. Returns <code>null</code> if given <code>null</code> or an
	 * invalid path (e.g. absolute path).
	 * <p>
	 * Note: this methods does not implement the package.json feature that a "main" path may point to a folder and then
	 * a file "index.js" in that folder will be used as main module (reason: this method cannot access the file system);
	 * to support this, the path given to this method must be adjusted *before* invoking this method (i.e. append
	 * "/index.js" iff the path points to a folder).
	 */
	public static String convertMainPathToModuleSpecifier(String path, List<String> sourceContainerPaths) {
		if (path == null) {
			return null;
		}
		// strip file extension
		if (path.endsWith(".js")) {
			path = path.substring(0, path.length() - 3);
		} else {
			return null; // in the standard package.json property "main", we ignore all files other than plain js files
		}
		// normalize path segments
		path = normalizeRelativePath(path);
		if (path == null) {
			return null;
		}
		// Now 'path' must point to a file inside a source container.
		// If that is true, then we want to return a path relative to that source container:
		List<String> sourceContainerPathsNormalized = sourceContainerPaths.stream()
				.map(ProjectDescriptionUtils::normalizeRelativePath)
				.filter(p -> p != null)
				.collect(Collectors.toList());
		for (String scp : sourceContainerPathsNormalized) {
			if (".".equals(scp)) {
				return path;
			} else {
				String scpSlash = scp + "/";
				if (path.startsWith(scpSlash)) {
					return path.substring(scpSlash.length());
				}
			}
		}
		return null;
	}

	private static String normalizeRelativePath(String path) {
		if (path == null || path.isEmpty()) {
			return null;
		}
		// enforce relative path
		if (path.startsWith("/")) {
			return null;
		}
		// normalize separator character
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar, '/');
		}
		// normalize ".", "..", and empty path segments
		List<String> segmentsNew = new ArrayList<>();
		for (String segment : path.split("/", -1)) {
			if (segment.isEmpty()) {
				continue; // simply ignore //
			} else if (".".equals(segment)) {
				continue; // simply ignore /./
			} else if ("..".equals(segment)) {
				if (segmentsNew.isEmpty()) {
					return null;
				}
				segmentsNew.remove(segmentsNew.size() - 1);
			} else {
				segmentsNew.add(segment);
			}
		}
		if (segmentsNew.isEmpty()) {
			return ".";
		}
		return Joiner.on('/').join(segmentsNew);
	}

	/**
	 * Compares the given source container descriptions based on the natural ordering of the wrapped
	 * {@link SourceContainerDescription source container type}. For more details, see
	 * {@link Comparator#compare(Object, Object)}.
	 */
	public static int compareBySourceContainerType(SourceContainerDescription first, SourceContainerDescription other) {
		if (first == null)
			return other == null ? 0 : 1;
		if (other == null)
			return -1;
		return first.getSourceContainerType().compareTo(other.getSourceContainerType());
	}

	/**
	 * Returns the {@link SourceContainerDescription#getPaths() paths} of the given source container description but
	 * normalized with {@link FileUtils#normalizeToDotWhenEmpty(String)}.
	 */
	public static List<String> getPathsNormalized(SourceContainerDescription scd) {
		List<String> normalizedPaths = new ArrayList<>(scd.getPaths().size());
		for (String path : scd.getPaths()) {
			String normalizedPath = FileUtils.normalizeToDotWhenEmpty(path);
			normalizedPaths.add(normalizedPath);
		}
		return normalizedPaths;
	}

	private ProjectDescriptionUtils() {
		// non-instantiable utility class
	}
}
