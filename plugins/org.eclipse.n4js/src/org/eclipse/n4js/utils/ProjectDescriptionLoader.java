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

import static org.eclipse.n4js.internal.N4JSModel.DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asStringOrNull;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.DEFAULT_VALUE_OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__DEV_DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__MAIN;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__VERSION;

import java.io.File;
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.packagejson.PackageJsonHelper;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Helper class for loading a {@link ProjectDescription} from disk, optionally also loading and merging additional
 * information from a {@code package-fragment.json} (when passing <code>true</code> as last argument to
 * {@link #loadProjectDescriptionAtLocation(URI, JSONDocument, boolean)}).
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
	 * Returns {@code null} if the project description cannot be loaded successfully (e.g. missing package.json and
	 * manifest.n4mf file).
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(URI location) {
		JSONDocument packageJSON = loadPackageJSONAtLocation(location);
		if (packageJSON == null) {
			return null;
		}
		return loadProjectDescriptionAtLocation(location, packageJSON, true);
	}

	/**
	 * Same as {@link #loadPackageJSONAtLocation(URI)}, but for cases in which the JSONDocument of the main package.json
	 * file (but not the fragment) has already been loaded.
	 *
	 * @param mergeFragment
	 *            if <code>true</code>, a {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON package.json fragment} will be loaded
	 *            and merged into the given package.json document before conversion to {@link ProjectDescription} (given
	 *            document will *not* be changed).
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(URI location, JSONDocument packageJSON,
			boolean mergeFragment) {
		if (mergeFragment) {
			packageJSON = EcoreUtil.copy(packageJSON);
			mergePackageJSONFragmentAtLocation(location, packageJSON);
		}
		adjustMainPath(location, packageJSON);
		String defaultProjectId = location.lastSegment(); // use name of folder containing the package.json file
		ProjectDescription pdFromPackageJSON = packageJSON != null
				? packageJsonHelper.convertToProjectDescription(packageJSON, true, defaultProjectId)
				: null;
		if (pdFromPackageJSON != null) {
			setInformationFromFileSystem(location, pdFromPackageJSON);
			return pdFromPackageJSON;
		} else {
			return null;
		}
	}

	/**
	 * Loads the project description defined in a {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON package.json fragment} at the
	 * given location or <code>null</code> if no fragment is found at this location.
	 */
	public ProjectDescription loadProjectDescriptionFragmentAtLocation(URI location) {
		JSONDocument packageJSON = JSONFactory.eINSTANCE.createJSONDocument();
		if (mergePackageJSONFragmentAtLocation(location, packageJSON)) {
			adjustMainPath(location, packageJSON);
			ProjectDescription pd = packageJsonHelper.convertToProjectDescription(packageJSON, false, null);
			setInformationFromFileSystem(location, pd);
			return pd;
		}
		return null;
	}

	/**
	 * Loads the project description of the N4JS project at the given {@code location} and returns the version string or
	 * <code>null</code> if undefined or in case of error.
	 */
	public String loadVersionFromProjectDescriptionAtLocation(URI location) {
		JSONDocument packageJSON = loadPackageJSONAtLocation(location);
		if (packageJSON == null) {
			return null;
		}
		JSONValue versionValue = JSONModelUtils.getProperty(packageJSON, PROP__VERSION).orElse(null);
		return asStringOrNull(versionValue);
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
		String main = asStringOrNull(JSONModelUtils.getProperty(contentCasted, PROP__MAIN).orElse(null));
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
			JSONModelUtils.setProperty(contentCasted, PROP__MAIN, main);
		} else if (isDirectory(resourceSet, locationWithMain)) {
			if (!(main.endsWith("/") || main.endsWith(File.separator))) {
				main += "/";
			}
			main += "index.js";
			JSONModelUtils.setProperty(contentCasted, PROP__MAIN, main);
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

	private JSONDocument loadPackageJSONAtLocation(URI location) {
		JSONDocument packageJSON = loadXtextFileAtLocation(location, IN4JSProject.PACKAGE_JSON, JSONDocument.class);

		if (packageJSON == null) {
			packageJSON = loadXtextFileAtLocation(location,
					IN4JSProject.PACKAGE_JSON + DEFAULT_VALUE_OUTPUT + N4JSGlobals.XT_FILE_EXTENSION,
					JSONDocument.class);
		}

		return packageJSON;
	}

	/** This method will change the given 'targetPackageJSON' document in place. */
	private boolean mergePackageJSONFragmentAtLocation(URI location, JSONDocument targetPackageJSON) {
		JSONDocument fragment = loadXtextFileAtLocation(location, N4JSGlobals.PACKAGE_FRAGMENT_JSON,
				JSONDocument.class);
		if (fragment == null) {
			return false;
		}
		if (fragment.getContent() instanceof JSONObject) {
			// note: dependencies are special in that we not actually merge (i.e. add) those from the fragment but
			// instead replace the dependencies in target. Rationale: the library manager is not interested in the
			// dependencies of the original project, only in those defined in the fragment.
			JSONValue targetContent = targetPackageJSON.getContent();
			if (targetContent instanceof JSONObject) {
				JSONModelUtils.removeProperty((JSONObject) targetContent, PROP__DEPENDENCIES);
				JSONModelUtils.removeProperty((JSONObject) targetContent, PROP__DEV_DEPENDENCIES);
			}
			// merge properties from fragment into targetPackageJSON
			JSONModelUtils.merge(targetPackageJSON, fragment, false, true);
			return true;
		}
		return false;
	}

	private <T extends EObject> T loadXtextFileAtLocation(URI location, String name, Class<T> expectedTypeOfRoot) {
		final T result;
		if (location.isPlatformResource() && location.segmentCount() == DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) {
			result = loadXtextFile(location.appendSegment(name), expectedTypeOfRoot);
		} else if (location.isFile()) {
			result = loadXtextFile(location.appendSegment(name), expectedTypeOfRoot);
		} else {
			// we only handle workspace and file-based cases
			return null;
		}
		return result;
	}

	private <T extends EObject> T loadXtextFile(URI uri, Class<T> expectedTypeOfRoot) {
		try {
			ResourceSet resourceSet = resourceSetProvider.get();

			// check whether a file exists at the given URI
			if (!exists(resourceSet, uri)) {
				return null;
			}

			Resource resource = resourceSet.getResource(uri, true);
			if (resource != null) {
				List<EObject> contents = resource.getContents();

				if (!contents.isEmpty()) {
					EObject root = contents.get(0);
					if (expectedTypeOfRoot.isInstance(root)) {
						@SuppressWarnings("unchecked")
						final T rootCasted = (T) root;
						contents.clear();
						return rootCasted;
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new WrappedException("failed to load Xtext file at " + uri, e);
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
