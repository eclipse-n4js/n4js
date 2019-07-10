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

import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNonEmptyStringOrNull;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.MAIN;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.N4JS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VERSION;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.WORKSPACES;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.packagejson.PackageJsonHelper;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Helper class for loading a {@link ProjectDescription} from disk.
 */
@Singleton
public class ProjectDescriptionLoader {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private PackageJsonHelper packageJsonHelper;

	/**
	 * Loads the project description of the N4JS project at the given {@code location}.
	 * <p>
	 * Returns {@code null} if the project description cannot be loaded successfully (e.g. missing package.json).
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(SafeURI<?> location) {
		JSONDocument packageJSON = loadPackageJSONAtLocation(location);
		if (packageJSON == null) {
			return null;
		}
		return loadProjectDescriptionAtLocation(location.toURI(), packageJSON);
	}

	/**
	 * Same as {@link #loadPackageJSONAtLocation(SafeURI)}.
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(URI location, JSONDocument packageJSON) {
		adjustMainPath(location, packageJSON);
		String defaultProjectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(location);
		ProjectDescription pdFromPackageJSON = packageJSON != null
				? packageJsonHelper.convertToProjectDescription(packageJSON, true, defaultProjectName)
				: null;
		if (pdFromPackageJSON != null) {
			setInformationFromFileSystem(location, pdFromPackageJSON);
			return pdFromPackageJSON;
		} else {
			return null;
		}
	}

	/**
	 * Loads the project description of the N4JS project at the given {@code location} and returns the version string or
	 * <code>null</code> if undefined or in case of error.
	 */
	public Pair<String, Boolean> loadVersionAndN4JSNatureFromProjectDescriptionAtLocation(SafeURI<?> location) {
		JSONDocument packageJSON = loadPackageJSONAtLocation(location);
		JSONValue versionValue = null;
		boolean hasN4JSNature = false;
		if (packageJSON != null) {
			versionValue = JSONModelUtils.getProperty(packageJSON, VERSION.name).orElse(null);
			hasN4JSNature = JSONModelUtils.getProperty(packageJSON, N4JS.name).isPresent();
		}
		Pair<String, Boolean> result = Tuples.create(asNonEmptyStringOrNull(versionValue), hasN4JSNature);
		return result;
	}

	/**
	 * Loads the project description of the N4JS project at the given {@code location} and returns the value of the
	 * "workspaces" property or <code>null</code> if undefined or in case of error.
	 */
	public List<String> loadWorkspacesFromProjectDescriptionAtLocation(SafeURI<?> location) {
		JSONDocument packageJSON = loadPackageJSONAtLocation(location);
		if (packageJSON != null) {
			JSONValue value = JSONModelUtils.getProperty(packageJSON, WORKSPACES.name).orElse(null);
			if (value instanceof JSONArray) {
				List<String> result = new ArrayList<>();
				for (JSONValue element : ((JSONArray) value).getElements()) {
					if (element instanceof JSONStringLiteral) {
						result.add(((JSONStringLiteral) element).getValue());
					}
				}
				return result;
			}
		}
		return null;
	}

	/**
	 * Adjust the path value of the "main" property of the given package.json document as follows (in-place change of
	 * the given JSON document):
	 * <ol>
	 * <li>if the path points to a folder, then "/index.js" will be appended,
	 * <li>if neither a folder nor a file exist at the location the path points to and the path does not end in ".js",
	 * then ".js" will be appended.
	 * </ol>
	 */
	private void adjustMainPath(URI location, JSONDocument packageJSON) {
		JSONValue content = packageJSON.getContent();
		if (!(content instanceof JSONObject))
			return;
		JSONObject contentCasted = (JSONObject) content;
		String main = asNonEmptyStringOrNull(JSONModelUtils.getProperty(contentCasted, MAIN.name).orElse(null));
		if (main == null) {
			return;
		}
		String pattern = File.separatorChar != '/'
				? Pattern.quote(File.separator) + "|" + Pattern.quote("/")
				: Pattern.quote("/");
		String[] mainSegments = main.split(pattern, -1);
		URI locationWithMain = location.appendSegments(mainSegments);

		final ResourceSet resourceSet = resourceSetProvider.get();

		if (!main.endsWith(".js") && isFile(resourceSet, locationWithMain.appendFileExtension("js"))) {
			main += ".js";
			JSONModelUtils.setProperty(contentCasted, MAIN.name, main);
		} else if (isDirectory(resourceSet, locationWithMain)) {
			if (!(main.endsWith("/") || main.endsWith(File.separator))) {
				main += "/";
			}
			main += "index.js";
			JSONModelUtils.setProperty(contentCasted, MAIN.name, main);
		}
	}

	/**
	 * Store some ancillary information about the state of the file system at the location of the
	 * <code>package.json</code> file in the given JSON document.
	 */
	private void setInformationFromFileSystem(URI location, ProjectDescription target) {
		final ResourceSet resourceSet = resourceSetProvider.get();
		final boolean hasNestedNodeModulesFolder = exists(resourceSet,
				location.appendSegment(N4JSGlobals.NODE_MODULES));
		target.setHasNestedNodeModulesFolder(hasNestedNodeModulesFolder);
	}

	private JSONDocument loadPackageJSONAtLocation(SafeURI<?> location) {
		JSONDocument packageJSON = loadXtextFileAtLocation(location, IN4JSProject.PACKAGE_JSON, JSONDocument.class);

		if (packageJSON == null) {
			packageJSON = loadXtextFileAtLocation(location,
					IN4JSProject.PACKAGE_JSON + OUTPUT.defaultValue + N4JSGlobals.XT_FILE_EXTENSION,
					JSONDocument.class);
		}

		return packageJSON;
	}

	private <T extends EObject> T loadXtextFileAtLocation(SafeURI<?> location, String name,
			Class<T> expectedTypeOfRoot) {
		final T result;
		if (location.exists()) {
			result = loadXtextFile(location.appendSegment(name), expectedTypeOfRoot);
		} else {
			// we only handle workspace and file-based cases
			return null;
		}
		return result;
	}

	private <T extends EObject> T loadXtextFile(SafeURI<?> location, Class<T> expectedTypeOfRoot) {
		try {
			// check whether a file exists at the given URI
			if (!location.exists()) {
				return null;
			}
			ResourceSet resourceSet = resourceSetProvider.get();
			Resource resource = resourceSet.getResource(location.toURI(), true);
			if (resource != null) {
				List<EObject> contents = resource.getContents();

				if (!contents.isEmpty()) {
					EObject root = contents.get(0);
					if (expectedTypeOfRoot.isInstance(root)) {
						final T rootCasted = expectedTypeOfRoot.cast(root);
						contents.clear();
						return rootCasted;
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new WrappedException("failed to load Xtext file at " + location, e);
		}
	}

	/**
	 * Checks whether {@code uri} points to a resource that actually exists on the file system.
	 *
	 * @param resourceSet
	 *            The resource set to use for the file system access.
	 * @param uri
	 *            The uri to check.
	 */
	private boolean exists(ResourceSet resourceSet, URI uri) {
		return resourceSet.getURIConverter().exists(uri, null);
	}

	/**
	 * Checks whether {@code uri} points to a directory on the file system.
	 *
	 * @param resourceSet
	 *            The resource set to use for the file system access.
	 * @param uri
	 *            The uri to check.
	 */
	private boolean isDirectory(ResourceSet resourceSet, URI uri) {
		final Map<String, ?> attributes = resourceSet.getURIConverter().getAttributes(uri, null);
		final boolean isDirectory = Objects.equals(attributes.get(URIConverter.ATTRIBUTE_DIRECTORY), Boolean.TRUE);
		return isDirectory;
	}

	private boolean isFile(ResourceSet resourceSet, URI uri) {
		return exists(resourceSet, uri) && !isDirectory(resourceSet, uri);
	}
}
