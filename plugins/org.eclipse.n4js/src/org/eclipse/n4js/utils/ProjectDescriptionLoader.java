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
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PACKAGES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VERSION;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.WORKSPACES_ARRAY;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONNumericLiteral;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.packagejson.PackageJsonHelper;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectDescriptionBuilder;
import org.eclipse.n4js.workspace.locations.SafeURI;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.RuntimeIOException;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.Tuples;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
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
		ProjectDescriptionBuilder pdbFromPackageJSON = packageJSON != null
				? packageJsonHelper.convertToProjectDescription(packageJSON, true, defaultProjectName)
				: null;
		if (pdbFromPackageJSON != null) {
			setInformationFromFileSystem(location, pdbFromPackageJSON);
			ProjectDescription result = pdbFromPackageJSON.build();
			return result;
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
			JSONValue value = JSONModelUtils.getProperty(packageJSON, WORKSPACES_ARRAY.name).orElse(null);
			if (value == null) {
				value = JSONModelUtils.getProperty(packageJSON, PACKAGES.name).orElse(null);
			}
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

	private static final Pattern windowsPattern = Pattern.compile("[\\/]");

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
		NameValuePair mainProperty = JSONModelUtils.getNameValuePair(contentCasted, MAIN.name).orElse(null);
		if (mainProperty == null) {
			return;
		}
		String main = asNonEmptyStringOrNull(mainProperty.getValue());
		if (main == null) {
			return;
		}
		String[] mainSegments;
		if (File.separatorChar == '/') {
			List<String> splitted = Strings.split(main, '/');
			mainSegments = splitted.toArray(String[]::new);
		} else {
			mainSegments = windowsPattern.split(main);
		}

		URI locationWithMain = location.appendSegments(mainSegments);

		if (!main.endsWith(".js") && isFile(URIConverter.INSTANCE, locationWithMain.appendFileExtension("js"))) {
			main += ".js";
			mainProperty.setValue(JSONModelUtils.createStringLiteral(main));
		} else if (isDirectory(URIConverter.INSTANCE, locationWithMain)) {
			if (!(main.endsWith("/") || main.endsWith(File.separator))) {
				main += "/";
			}
			main += "index.js";
			mainProperty.setValue(JSONModelUtils.createStringLiteral(main));
		}
	}

	/**
	 * Store some ancillary information about the state of the file system at the location of the
	 * <code>package.json</code> file in the given JSON document.
	 */
	private void setInformationFromFileSystem(URI location, ProjectDescriptionBuilder target) {
		boolean hasNestedNodeModulesFolder = exists(URIConverter.INSTANCE,
				location.appendSegment(N4JSGlobals.NODE_MODULES));
		target.setNestedNodeModulesFolder(hasNestedNodeModulesFolder);
	}

	private JSONDocument loadPackageJSONAtLocation(SafeURI<?> location) {
		Path path = location.appendSegment(N4JSGlobals.PACKAGE_JSON).toFileSystemPath();
		if (!Files.isReadable(path)) {
			path = location.appendSegment(N4JSGlobals.PACKAGE_JSON + "." + N4JSGlobals.XT_FILE_EXTENSION)
					.toFileSystemPath();
			if (!Files.isReadable(path)) {
				return null;
			}
		}
		try {
			String jsonString = Files.readString(path, StandardCharsets.UTF_8);
			try {
				JSONDocument doc = JSONFactory.eINSTANCE.createJSONDocument();
				JsonElement jsonElement = new JsonParser().parse(jsonString);
				doc.setContent(copy(jsonElement));
				return doc;
			} catch (JsonParseException e) {
				JSONDocument packageJSON = loadXtextFileAtLocation(location, N4JSGlobals.PACKAGE_JSON, jsonString,
						JSONDocument.class);
				return packageJSON;
			}
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
	}

	private <T extends EObject> T loadXtextFileAtLocation(SafeURI<?> location, String name, String content,
			Class<T> expectedTypeOfRoot) {
		SafeURI<?> fullLocation = location.appendSegment(name);
		try {
			// check whether a file exists at the given URI
			if (!fullLocation.exists()) {
				return null;
			}
			ResourceSet resourceSet = resourceSetProvider.get();
			Resource resource = resourceSet.createResource(fullLocation.toURI());
			if (resource != null) {
				try (LazyStringInputStream contentAsStream = new LazyStringInputStream(content,
						StandardCharsets.UTF_8)) {
					resource.load(contentAsStream, resourceSet.getLoadOptions());

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
			}
			return null;
		} catch (Exception e) {
			throw new WrappedException("failed to load Xtext file at " + fullLocation, e);
		}
	}

	private JSONValue copy(JsonElement jsonElement) {
		if (jsonElement.isJsonNull()) {
			return JSONFactory.eINSTANCE.createJSONNullLiteral();
		}
		if (jsonElement.isJsonPrimitive()) {
			JsonPrimitive primitive = jsonElement.getAsJsonPrimitive();
			if (primitive.isBoolean()) {
				JSONBooleanLiteral result = JSONFactory.eINSTANCE.createJSONBooleanLiteral();
				result.setBooleanValue(primitive.getAsBoolean());
				return result;
			}
			if (primitive.isNumber()) {
				JSONNumericLiteral result = JSONFactory.eINSTANCE.createJSONNumericLiteral();
				result.setValue(primitive.getAsBigDecimal());
				return result;
			}
			if (primitive.isString()) {
				JSONStringLiteral result = JSONFactory.eINSTANCE.createJSONStringLiteral();
				result.setValue(primitive.getAsString());
				return result;
			}
			throw new IllegalArgumentException(jsonElement.toString());
		}
		if (jsonElement.isJsonObject()) {
			JsonObject object = jsonElement.getAsJsonObject();
			JSONObject result = JSONFactory.eINSTANCE.createJSONObject();
			for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
				NameValuePair pair = JSONFactory.eINSTANCE.createNameValuePair();
				pair.setName(entry.getKey());
				pair.setValue(copy(entry.getValue()));
				result.getNameValuePairs().add(pair);
			}
			return result;
		}
		if (jsonElement.isJsonArray()) {
			JsonArray array = jsonElement.getAsJsonArray();
			JSONArray result = JSONFactory.eINSTANCE.createJSONArray();
			for (JsonElement arrayElement : array) {
				result.getElements().add(copy(arrayElement));
			}
			return result;
		}
		throw new IllegalArgumentException(jsonElement.toString());
	}

	/**
	 * Checks whether {@code uri} points to a resource that actually exists on the file system.
	 *
	 * @param uriConverter
	 *            The uri converter to use
	 * @param uri
	 *            The uri to check.
	 */
	private boolean exists(URIConverter uriConverter, URI uri) {
		return uriConverter.exists(uri, null);
	}

	/**
	 * Checks whether {@code uri} points to a directory on the file system.
	 *
	 * @param uriConverter
	 *            The uri converter to use
	 * @param uri
	 *            The uri to check.
	 */
	private boolean isDirectory(URIConverter uriConverter, URI uri) {
		final Map<String, ?> attributes = uriConverter.getAttributes(uri, Collections.singletonMap(
				URIConverter.OPTION_REQUESTED_ATTRIBUTES, Collections.singleton(URIConverter.ATTRIBUTE_DIRECTORY)));
		final boolean isDirectory = Boolean.TRUE.equals(attributes.get(URIConverter.ATTRIBUTE_DIRECTORY));
		return isDirectory;
	}

	private boolean isFile(URIConverter uriConverter, URI uri) {
		return exists(uriConverter, uri) && !isDirectory(uriConverter, uri);
	}
}
