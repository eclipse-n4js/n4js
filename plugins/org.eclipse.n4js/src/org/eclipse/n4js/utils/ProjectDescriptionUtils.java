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

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.base.Joiner;

/**
 * Miscellaneous utilities for dealing with {@link ProjectDescription}s and values stored within them. In particular,
 * this class provides logic for dealing with project names.
 */
public class ProjectDescriptionUtils {

	/** Prefix denoting project names that include an npm scope. */
	public static final String NPM_SCOPE_PREFIX = "@";
	/** Character used in N4JS project names for separating the scope name from the plain project name. */
	public static final char NPM_SCOPE_SEPARATOR = '/';
	/** Like {@link #NPM_SCOPE_SEPARATOR}, but used in Eclipse project names. */
	public static final char NPM_SCOPE_SEPARATOR_ECLIPSE = '_';

	/**
	 * Tells if the given N4JS project name includes an npm scope, i.e. if it is of the form
	 * <code>@scopeName/projectName</code>. In this case, the substring before the {@value #NPM_SCOPE_SEPARATOR} is
	 * referred to as the <em>scope name</em> and the substring following this separator is referred to as <em>plain
	 * project name</em>.
	 *
	 * <h1>Overview of Project Name Handling</h1>
	 *
	 * Naming of N4JS projects is non-trivial, because it has to be in line with NPM conventions regarding npm package
	 * names, esp. the use of npm scopes.
	 * <p>
	 * The following names have to be distinguished:
	 * <table border=1 align="center">
	 * <tr>
	 * <th></th>
	 * <th>Description</th>
	 * <th>Example</th>
	 * </tr>
	 * <tr>
	 * <td>N4JS project name</td>
	 * <td>The value the value returned by {@link IN4JSProject#getProjectId()}. Always equal to<br>
	 * the value of the top-level property "name" in the project's <code>package.json</code> file.</td>
	 * <td>{@code @myScope/myProject}</td>
	 * </tr>
	 * <tr>
	 * <td>Eclipse project name</td>
	 * <td>The value returned by {@link IProject#getName()}. Different from the N4JS project name,<br>
	 * because Eclipse does not support NPM's scope separator character {@value #NPM_SCOPE_SEPARATOR} in project
	 * names.</td>
	 * <td>{@code @myScope_myProject}</td>
	 * </tr>
	 * <tr>
	 * <td>plain project name</td>
	 * <td>The N4JS project name without the scope name and without the scope separator.</td>
	 * <td>{@code myProject}</td>
	 * </tr>
	 * <tr>
	 * <td>scope name</td>
	 * <td>The name of the npm scope of a scoped N4JS project, usually including the scope prefix
	 * {@value #NPM_SCOPE_PREFIX}.</td>
	 * <td>{@code @myScope}</td>
	 * </tr>
	 * </table>
	 * In case the intended meaning is apparent from the context, the "N4JS project name" can simply be referred to as
	 * "project name" (as is common practice in the context of npm). For non-scoped projects, the N4JS project name, the
	 * Eclipse project name, and the plain project name are equal.
	 * <p>
	 * Due to conventions and rules we cannot influence, an N4JS project name including a scope has to be encoded in
	 * {@link URI}s and {@link QualifiedName}s in various ways:
	 * <table border=1 align="center">
	 * <tr>
	 * <th></th>
	 * <th>Encoding</th>
	 * <th>Example</th>
	 * </tr>
	 * <tr>
	 * <td>{@link URI#isPlatform() platform URIs}</td>
	 * <td>Scope and plain project name represented as <em>a single</em> segment.<br>
	 * {@link #NPM_SCOPE_SEPARATOR_ECLIPSE} used as separator.</td>
	 * <td>{@code platform:resource/@myScope_myProject}</td>
	 * </tr>
	 * <tr>
	 * <td>{@link URI#isFile() file URIs}</td>
	 * <td>Scope and plain project name represented as <em>two separate</em> segments.</td>
	 * <td>{@code file://a/b/wsp/@myScope/myProject}</td>
	 * </tr>
	 * <tr>
	 * <td>{@link QualifiedName}s</td>
	 * <td>Scope and plain project name represented as <em>a single</em> segment.<br>
	 * {@link #NPM_SCOPE_SEPARATOR} used as separator.</td>
	 * <td>{@code "@myScope/myProject"/"f"/"M"}</td>
	 * </tr>
	 * </table>
	 * These encoding rules are implemented in methods {@link #deriveN4JSProjectNameFromURI(URI)} and
	 * {@link N4JSQualifiedNameConverter#toQualifiedName(String)}.
	 */
	public static boolean isProjectNameWithScope(String projectName) {
		return projectName != null
				&& projectName.startsWith(NPM_SCOPE_PREFIX)
				&& projectName.indexOf(NPM_SCOPE_SEPARATOR) >= 0;
	}

	/**
	 * Given an N4JS project name that may or may not {@link #isProjectNameWithScope(String) include an npm scope}, this
	 * method returns the scope name or <code>null</code> if the given project name does not include an npm scope.
	 * <p>
	 * NOTE: the returned scope name includes the {@link #NPM_SCOPE_PREFIX}!
	 * <p>
	 * For details on N4JS project name handling, see {@link #isProjectNameWithScope(String)}.
	 */
	public static String getScopeName(String projectName) {
		return isProjectNameWithScope(projectName)
				? projectName.substring(0, projectName.indexOf(NPM_SCOPE_SEPARATOR))
				: null;
	}

	/**
	 * Given an N4JS project name that may or may not {@link #isProjectNameWithScope(String) include an npm scope}, this
	 * method returns the plain project name without scope. If the given project name does not include an npm scope,
	 * that name is returned without change.
	 * <p>
	 * For details on N4JS project name handling, see {@link #isProjectNameWithScope(String)}.
	 */
	public static String getPlainProjectName(String projectName) {
		return isProjectNameWithScope(projectName)
				? projectName.substring(projectName.indexOf(NPM_SCOPE_SEPARATOR) + 1)
				: projectName;
	}

	/**
	 * Given a URI as used internally to identify N4JS projects, this method returns the corresponding N4JS project name
	 * which may or may not {@link #isProjectNameWithScope(String) include an npm scope}. The given URI may be a
	 * {@link URI#isFile() file URI} (headless case) or a {@link URI#isPlatform() platform URI} (UI case).
	 * <p>
	 * For details on N4JS project name handling, see {@link #isProjectNameWithScope(String)}.
	 */
	public static String deriveN4JSProjectNameFromURI(URI uri) {
		int segCount = uri.segmentCount();
		String last = segCount > 0 ? uri.segment(segCount - 1) : null;
		if (uri.isPlatform()) {
			// due to Eclipse conventions we cannot influence, the scope name and plain project name are represented
			// within a single segment in platform URIs:
			if (last != null && last.startsWith(NPM_SCOPE_PREFIX)) {
				last = replaceFirst(last, NPM_SCOPE_SEPARATOR_ECLIPSE, NPM_SCOPE_SEPARATOR);
			}
			return last;
		} else if (uri.isFile()) {
			// due to conventions we cannot influence, the scope name and plain project name are represented as two
			// separate segments in file URIs:
			String secondToLast = segCount > 1 ? uri.segment(segCount - 2) : null;
			if (secondToLast != null && secondToLast.startsWith(NPM_SCOPE_PREFIX)) {
				return secondToLast + NPM_SCOPE_SEPARATOR + last;
			}
			return last;
		}
		throw new IllegalArgumentException("neither a file nor a platform URI: " + uri);
	}

	/**
	 * Given the location of an N4JS project on disk, this method returns a {@link URI#isFile() file URI}, as used
	 * internally to uniquely identify N4JS projects.
	 * <p>
	 * Since this methods always returns file URIs, it is only intended for use in the headless case. In the UI case,
	 * URIs for identifying projects will be created by Eclipse.
	 * <p>
	 * For details on N4JS project name handling, see {@link #isProjectNameWithScope(String)}.
	 */
	public static URI deriveProjectURIFromFileLocation(File file) {
		try {
			URI createURI = URI.createURI(file.getAbsoluteFile().toURI().toURL().toString());
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

	/**
	 * Converts the given N4JS project name into an Eclipse project name. Returns given name unchanged if it does not
	 * {@link #isProjectNameWithScope(String) include an npm scope}.
	 * <p>
	 * For details on N4JS project name handling, see {@link #isProjectNameWithScope(String)}.
	 */
	public static String convertN4JSProjectNameToEclipseProjectName(String name) {
		if (name != null && name.startsWith(NPM_SCOPE_PREFIX)) {
			return replaceFirst(name, NPM_SCOPE_SEPARATOR, NPM_SCOPE_SEPARATOR_ECLIPSE);
		}
		return name;
	}

	/**
	 * Converts the given Eclipse project name into an N4JS project name.
	 * <p>
	 * For details on N4JS project name handling, see {@link #isProjectNameWithScope(String)}.
	 */
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
