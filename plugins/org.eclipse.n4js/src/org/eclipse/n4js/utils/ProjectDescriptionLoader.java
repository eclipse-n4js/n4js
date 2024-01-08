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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
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
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescriptionBuilder;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.LazyStringInputStream;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.Tuples;

import com.google.common.collect.Multimap;
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
	private final static Logger LOGGER = Logger.getLogger(ProjectDescriptionLoader.class);

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private PackageJsonHelper packageJsonHelper;

	/**
	 * Loads the project description of the N4JS project at the given {@code location}.
	 * <p>
	 * Returns {@code null} if the project description cannot be loaded successfully (e.g. missing package.json).
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(FileURI location, FileURI relatedRootLocation) {
		JSONDocument packageJSON = loadPackageJSONAtLocation(location);
		if (packageJSON == null) {
			return null;
		}
		URI relatedRootLocationUri = relatedRootLocation == null ? null : relatedRootLocation.toURI();
		return loadProjectDescriptionAtLocation(location, relatedRootLocationUri, packageJSON);
	}

	/**
	 * Same as {@link #loadPackageJSONAtLocation(FileURI)}.
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(FileURI location, URI relatedRootLocation,
			JSONDocument packageJSON) {

		if (location == null) {
			return null;
		}

		adjustMainPath(location, packageJSON);
		ProjectDescriptionBuilder pdbFromPackageJSON = packageJSON != null
				? packageJsonHelper.convertToProjectDescription(packageJSON, true, null)
				: null;
		if (pdbFromPackageJSON != null) {
			setInformationFromFileSystem(location, pdbFromPackageJSON);
			setInformationFromTSConfig(location, pdbFromPackageJSON);
			setInformationFromPnpmWorkspace(location, pdbFromPackageJSON);
			pdbFromPackageJSON.setLocation(location);
			pdbFromPackageJSON.setRelatedRootLocation(relatedRootLocation);

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
	public Pair<String, Boolean> loadVersionAndN4JSNatureFromProjectDescriptionAtLocation(FileURI location) {
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
	public List<String> loadWorkspacesFromProjectDescriptionAtLocation(FileURI location) {
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
	 * <li>if this is a {@code @types}-project, then continue with the value of {@code types}-property.
	 * <li>if the path points to a folder, then "/index.js" will be appended,
	 * <li>if neither a folder nor a file exist at the location the path points to and the path does not end in ".js",
	 * then ".js" will be appended.
	 * </ol>
	 */
	private void adjustMainPath(FileURI location, JSONDocument packageJSON) {
		adjustMainPath(location, packageJSON, MAIN, "js");
		adjustMainPath(location, packageJSON, PackageJsonProperties.TYPES, "d.ts");
	}

	private void adjustMainPath(FileURI location, JSONDocument packageJSON, PackageJsonProperties prop,
			String fileExt) {
		JSONValue content = packageJSON.getContent();
		if (!(content instanceof JSONObject)) {
			return;
		}
		JSONObject contentCasted = (JSONObject) content;
		NameValuePair mainProperty = JSONModelUtils.getNameValuePair(contentCasted, prop.name).orElse(null);

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

		URI locationWithMain = location.appendSegments(mainSegments).toURI();

		if (!main.endsWith("." + fileExt)
				&& isFile(URIConverter.INSTANCE, locationWithMain.appendFileExtension(fileExt))) {
			main += "." + fileExt;
			mainProperty.setValue(JSONModelUtils.createStringLiteral(main));
		} else if (isDirectory(URIConverter.INSTANCE, locationWithMain)) {
			if (!(main.endsWith("/") || main.endsWith(File.separator))) {
				main += "/";
			}
			main += "index." + fileExt;
			mainProperty.setValue(JSONModelUtils.createStringLiteral(main));
		}
	}

	/**
	 * Store some ancillary information about the state of the file system at the location of the
	 * <code>package.json</code> file in the given JSON document.
	 */
	private void setInformationFromFileSystem(FileURI location, ProjectDescriptionBuilder target) {
		boolean hasNestedNodeModulesFolder = exists(URIConverter.INSTANCE,
				location.appendSegment(N4JSGlobals.NODE_MODULES).toURI());
		target.setNestedNodeModulesFolder(hasNestedNodeModulesFolder);
	}

	/**
	 * Store some information from {@code tsconfig.json} file iff existent in the project folders root.
	 */
	private void setInformationFromTSConfig(FileURI location, ProjectDescriptionBuilder target) {
		ProjectType type = target.getProjectType();
		if (type != ProjectType.PLAINJS && type != ProjectType.DEFINITION) {
			// Note that n4js projects also create/modify tsconfig.json files iff they generate d.ts files
			// from n4js sources. These generated tsconfig files state the 'srg-gen' folder as an 'includes'
			// glob.
			return;
		}

		Path path = location.appendSegment(N4JSGlobals.TS_CONFIG).toFileSystemPath();
		if (!Files.isReadable(path)) {
			path = location.appendSegment(N4JSGlobals.TS_CONFIG + "." + N4JSGlobals.XT_FILE_EXTENSION)
					.toFileSystemPath();
			if (!Files.isReadable(path)) {
				return;
			}
		}
		JSONDocument tsconfig = loadJSONAtLocation(path);
		JSONValue content = tsconfig == null ? null : tsconfig.getContent();
		if (!(content instanceof JSONObject)) {
			return;
		}
		JSONObject contentCasted = (JSONObject) content;
		NameValuePair filesProperty = JSONModelUtils.getNameValuePair(contentCasted, "files").orElse(null);
		if (filesProperty != null) {
			for (String tsFile : JSONModelUtils.asStringsInArrayOrEmpty(filesProperty.getValue())) {
				target.addTsFile(tsFile);
			}
		}
		NameValuePair includeProperty = JSONModelUtils.getNameValuePair(contentCasted, "include").orElse(null);
		if (includeProperty != null) {
			for (String tsInclude : JSONModelUtils.asStringsInArrayOrEmpty(includeProperty.getValue())) {
				target.addTsInclude(tsInclude);
			}
		}
		NameValuePair excludeProperty = JSONModelUtils.getNameValuePair(contentCasted, "exclude").orElse(null);
		if (excludeProperty != null) {
			for (String tsExclude : JSONModelUtils.asStringsInArrayOrEmpty(excludeProperty.getValue())) {
				target.addTsExclude(tsExclude);
			}
		}
	}

	/**
	 * Store some information from {@code pnpm-workspaces.yaml} file iff existent in the project folders root.
	 */
	private void setInformationFromPnpmWorkspace(FileURI location, ProjectDescriptionBuilder target) {
		Path path = location.appendSegment(N4JSGlobals.PNPM_WORKSPACE).toFileSystemPath();
		if (!Files.isReadable(path)) {
			path = location.appendSegment(N4JSGlobals.PNPM_WORKSPACE + "." + N4JSGlobals.XT_FILE_EXTENSION)
					.toFileSystemPath();
			if (!Files.isReadable(path)) {
				return;
			}
		}

		Multimap<String, String> pnpmWorkspacesYaml = YamlUtil.loadYamlAtLocation(path);
		Collection<String> packagesEntries = pnpmWorkspacesYaml.get("packages");
		if (!packagesEntries.isEmpty()) {
			// check for property discussed here: https://github.com/pnpm/pnpm/issues/2255#issuecomment-576866891
			Collection<String> useYarnConfigEntries = pnpmWorkspacesYaml.get("useYarnConfig");
			if (useYarnConfigEntries.isEmpty()
					|| !"true".equals(useYarnConfigEntries.iterator().next().toString().toLowerCase())) {

				target.setPnpmWorkspaceRoot(true);
				target.getWorkspaces().clear();
				target.getWorkspaces().addAll(packagesEntries);
			}
		}
	}

	private JSONDocument loadPackageJSONAtLocation(FileURI location) {
		Path path = location.appendSegment(N4JSGlobals.PACKAGE_JSON).toFileSystemPath();
		if (!Files.isReadable(path)) {
			path = location.appendSegment(N4JSGlobals.PACKAGE_JSON + "." + N4JSGlobals.XT_FILE_EXTENSION)
					.toFileSystemPath();
			if (!Files.isReadable(path)) {
				return null;
			}
		}
		return loadJSONAtLocation(path);
	}

	private JSONDocument loadJSONAtLocation(Path path) {
		try {
			String jsonString = Files.readString(path, StandardCharsets.UTF_8);
			try {
				JSONDocument doc = JSONFactory.eINSTANCE.createJSONDocument();
				JsonElement jsonElement = JsonParser.parseString(jsonString);
				doc.setContent(copy(jsonElement));
				return doc;
			} catch (JsonParseException e) {
				FileURI fileURI = new FileURI(path);
				JSONDocument packageJSON = loadXtextFileAtLocation(fileURI, jsonString, JSONDocument.class);
				return packageJSON;
			}
		} catch (IOException e) {
			LOGGER.error("Could not load " + path.toString(), e);
			return null;
		}
	}

	private <T extends EObject> T loadXtextFileAtLocation(FileURI fullLocation, String content,
			Class<T> expectedTypeOfRoot) {
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
