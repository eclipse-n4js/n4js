/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * Utility methods for dealing with JSON files on a low level, using Google's GSON library.
 */
public class JsonUtils {

	/**
	 * Same as {@link #getDeep(JsonElement, String...)}, but returns the string representation of the last property's
	 * value, if it is a {@link JsonPrimitive}; otherwise returns <code>null</code>.
	 */
	public static String getDeepAsString(JsonElement jsonElement, String... propertyNames) {
		JsonElement result = getDeep(jsonElement, propertyNames);
		if (result != null && result.isJsonPrimitive()) {
			return ((JsonPrimitive) result).getAsString();
		}
		return null;
	}

	/**
	 * Traverse the object graph starting at the given {@link JsonObject} along the properties defined by the given
	 * property names and return the value of the last property.
	 * <p>
	 * Returns the given {@link JsonElement} if no property names are given. Returns <code>null</code> in case of error,
	 * e.g. if the given {@code JsonElement} or any element encountered along the way isn't a {@code JsonObject} or in
	 * case no property is found for one of the given property names.
	 */
	public static JsonElement getDeep(JsonElement jsonElement, String... propertyNames) {
		JsonElement curr = jsonElement;
		int i = 0;
		while (curr != null && i < propertyNames.length) {
			if (!curr.isJsonObject()) {
				return null;
			}
			curr = curr.getAsJsonObject().get(propertyNames[i++]);
		}
		return curr;
	}

	/**
	 * Overwrites any existing values of the properties defined by the given property names with 'newValue', but does
	 * not add new properties for property names that do not already exist in the given {@link JsonObject}.
	 */
	public static boolean changeProperties(JsonObject jsonObject, Set<String> propertyNames, JsonElement newValue) {
		Set<String> existingPropertyNames = Sets.newHashSet(propertyNames);
		existingPropertyNames.retainAll(jsonObject.keySet());
		if (existingPropertyNames.isEmpty()) {
			return false;
		}
		for (String propertyName : existingPropertyNames) {
			jsonObject.add(propertyName, newValue);
		}
		return true;
	}

	/**
	 * Load given file to a {@link JsonElement}.
	 */
	public static JsonElement loadJson(Path path) throws FileNotFoundException, IOException {
		try (BufferedReader in = Files.newBufferedReader(path)) {
			JsonParser parser = new JsonParser();
			return parser.parse(in);
		}
	}

	/**
	 * Save given {@link JsonElement} to given file. Will overwrite the file, if it exists.
	 */
	public static void saveJson(Path path, JsonElement jsonElement) throws FileNotFoundException, IOException {
		try (BufferedWriter out = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(jsonElement, out);
		}
	}

	public static void addDependenciesToPackageJsonFile(Path path,
			Pair<String, String>... namesAndVersionConstraints) throws FileNotFoundException, IOException {
		JsonElement root = loadJson(path);
		JsonElement depsValue = getDeep(root, UtilN4.PACKAGE_JSON__DEPENDENCIES);
		if (depsValue == null) {
			if (root instanceof JsonObject) {
				depsValue = new JsonObject();
				((JsonObject) root).add(UtilN4.PACKAGE_JSON__DEPENDENCIES, depsValue);
			} else {
				throw new IOException("root element in file is not an object");
			}
		} else if (!(depsValue instanceof JsonObject)) {
			throw new IOException("value of property \"" + UtilN4.PACKAGE_JSON__DEPENDENCIES + "\" is not an object");
		}
		JsonObject depsValueCasted = (JsonObject) depsValue;
		for (Pair<String, String> navc : namesAndVersionConstraints) {
			String name = navc.getKey();
			String versionConstraint = navc.getValue();
			if (depsValueCasted.keySet().contains(name)) {
				throw new IOException("package.json file already contains a dependency to \"" + name + "\"");
			}
			depsValueCasted.add(name, new JsonPrimitive(versionConstraint));
		}
		saveJson(path, root);
	}

	public static void removeDependenciesFromPackageJsonFile(Path path, String... namesToRemove)
			throws FileNotFoundException, IOException {
		JsonElement root = loadJson(path);
		JsonElement depsValue = getDeep(root, UtilN4.PACKAGE_JSON__DEPENDENCIES);
		if (depsValue == null) {
			throw new IOException("package.json does not contain any dependencies");
		} else if (!(depsValue instanceof JsonObject)) {
			throw new IOException("value of property \"" + UtilN4.PACKAGE_JSON__DEPENDENCIES + "\" is not an object");
		}
		JsonObject depsValueCasted = (JsonObject) depsValue;
		for (String name : namesToRemove) {
			if (!depsValueCasted.keySet().contains(name)) {
				throw new IOException("package.json file does not contain a dependency to \"" + name + "\"");
			}
			depsValueCasted.remove(name);
		}
		saveJson(path, root);
	}

	/**
	 * Same as {@link #setVersionOfDependenciesInPackageJsonFile(Path, Set, JsonElement)}, but for all package.json
	 * files in the entire folder tree below the given root folder.
	 */
	public static void setVersionOfDependenciesInAllPackageJsonFiles(Path rootFolder,
			Set<String> namesOfDependencies, JsonElement versionToSet) throws FileNotFoundException, IOException {
		List<Path> packageJsonFiles = Files.walk(rootFolder, FileVisitOption.FOLLOW_LINKS)
				.filter(p -> UtilN4.PACKAGE_JSON.equals(p.getFileName().toString()))
				.collect(Collectors.toList());
		for (Path packageJsonFile : packageJsonFiles) {
			setVersionOfDependenciesInPackageJsonFile(packageJsonFile, namesOfDependencies, versionToSet);
		}
	}

	/**
	 * Changes the version of all dependencies and devDependencies to packages with a name contained in
	 * {@code namesOfDependencies} to the given version. Names given in the set that are not among the dependencies in
	 * the package.json file will be ignored; dependencies in the package.json file that are not denoted by the names in
	 * the set will remain unchanged.
	 */
	public static void setVersionOfDependenciesInPackageJsonFile(Path packageJsonFile,
			Set<String> namesOfDependencies, JsonElement versionToSet) throws FileNotFoundException, IOException {
		if (namesOfDependencies.isEmpty()) {
			return;
		}
		JsonElement root = JsonUtils.loadJson(packageJsonFile);
		boolean changed = false;
		JsonElement deps = JsonUtils.getDeep(root, UtilN4.PACKAGE_JSON__DEPENDENCIES);
		if (deps != null && deps.isJsonObject()) {
			changed |= JsonUtils.changeProperties((JsonObject) deps, namesOfDependencies, versionToSet);
		}
		JsonElement devDeps = JsonUtils.getDeep(root, UtilN4.PACKAGE_JSON__DEV_DEPENDENCIES);
		if (devDeps != null && devDeps.isJsonObject()) {
			changed |= JsonUtils.changeProperties((JsonObject) devDeps, namesOfDependencies, versionToSet);
		}
		if (changed) {
			JsonUtils.saveJson(packageJsonFile, root);
		}
	}
}
