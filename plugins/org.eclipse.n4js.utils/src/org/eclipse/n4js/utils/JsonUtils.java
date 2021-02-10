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
import java.math.BigInteger;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * Utility methods for dealing with JSON files on a low level, using Google's GSON library.
 */
public class JsonUtils {

	/**
	 * Same as {@link #getArrayDeep(JsonElement, String...)}, but creates new objects along the given property chain and
	 * a new array at the end of the chain, if necessary, and will throw an exception in case of error. Never returns
	 * {@code null}.
	 */
	public static JsonArray getOrCreateArrayDeepFailFast(JsonElement root, String... propertyNames) {
		return internalGet(JsonArray.class, Optional.of(JsonArray::new), true, root, propertyNames);
	}

	/**
	 * Same as {@link #getObjectDeep(JsonElement, String...)}, but creates new objects along the given property chain,
	 * if necessary, and will throw an exception in case of error. Never returns {@code null}.
	 */
	public static JsonObject getOrCreateObjectDeepFailFast(JsonElement root, String... propertyNames) {
		return internalGet(JsonObject.class, Optional.of(JsonObject::new), true, root, propertyNames);
	}

	/** Same as {@link #getDeep(JsonElement, String...)}, but returns {@code null} if value isn't a boolean. */
	public static boolean getBooleanDeep(JsonElement root, String... propertyNames) {
		JsonPrimitive result = internalGet(JsonPrimitive.class, Optional.absent(), false, root, propertyNames);
		return result != null && result.isBoolean() ? result.getAsBoolean() : Boolean.FALSE;
	}

	/** Same as {@link #getDeep(JsonElement, String...)}, but returns {@code null} if value isn't a Java int. */
	public static int getIntDeep(JsonElement root, String... propertyNames) {
		JsonPrimitive result = internalGet(JsonPrimitive.class, Optional.absent(), false, root, propertyNames);
		return result != null && result.isNumber() ? result.getAsInt() : 0;
	}

	/** Same as {@link #getDeep(JsonElement, String...)}, but returns {@code null} if value isn't a JSON number. */
	public static Number getNumberDeep(JsonElement root, String... propertyNames) {
		JsonPrimitive result = internalGet(JsonPrimitive.class, Optional.absent(), false, root, propertyNames);
		return result != null && result.isNumber() ? result.getAsNumber() : BigInteger.ZERO;
	}

	/** Same as {@link #getDeep(JsonElement, String...)}, but returns {@code null} if value isn't a JSON string. */
	public static String getStringDeep(JsonElement root, String... propertyNames) {
		JsonPrimitive result = internalGet(JsonPrimitive.class, Optional.absent(), false, root, propertyNames);
		return result != null && result.isString() ? result.getAsString() : null;
	}

	/** Same as {@link #getDeep(JsonElement, String...)}, but returns {@code null} if value isn't a JSON primitive. */
	public static JsonPrimitive getPrimitiveDeep(JsonElement root, String... propertyNames) {
		return internalGet(JsonPrimitive.class, Optional.absent(), false, root, propertyNames);
	}

	/**
	 * Same as {@link #getPrimitiveDeep(JsonElement, String...)}, but returns the string representation of the JSON
	 * primitive (if any).
	 */
	public static String getDeepAsString(JsonElement root, String... propertyNames) {
		JsonPrimitive result = internalGet(JsonPrimitive.class, Optional.absent(), false, root, propertyNames);
		return result != null ? result.getAsString() : null;
	}

	/** Same as {@link #getDeep(JsonElement, String...)}, but returns {@code null} if value isn't a JSON array. */
	public static JsonArray getArrayDeep(JsonElement root, String... propertyNames) {
		return internalGet(JsonArray.class, Optional.absent(), false, root, propertyNames);
	}

	/** Same as {@link #getDeep(JsonElement, String...)}, but returns {@code null} if value isn't a JSON object. */
	public static JsonObject getObjectDeep(JsonElement root, String... propertyNames) {
		return internalGet(JsonObject.class, Optional.absent(), false, root, propertyNames);
	}

	/**
	 * Traverse the object graph starting at the given {@link JsonObject} along the properties defined by the given
	 * property names and return the value of the last property.
	 * <p>
	 * Returns the given {@link JsonElement} if no property names are given. Returns {@code null} in case of error, e.g.
	 * if the given {@code JsonElement} or any element encountered along the way isn't a {@code JsonObject} or in case
	 * no property is found for one of the given property names.
	 */
	public static JsonElement getDeep(JsonElement root, String... propertyNames) {
		return internalGet(JsonElement.class, Optional.absent(), false, root, propertyNames);
	}

	private static <T extends JsonElement> T internalGet(Class<T> expectedType,
			Optional<Supplier<T>> createOnDemand, boolean failFast, JsonElement root, String... propertyNames) {
		if (root == null) {
			if (failFast) {
				throw new IllegalArgumentException("root JSON element must be non-null");
			}
			return null;
		}
		JsonElement curr = root;
		int i = 0;
		while (i < propertyNames.length) {
			if (!curr.isJsonObject()) {
				if (failFast) {
					if (i == 0) {
						throw new IllegalArgumentException("root JSON element must be a JsonObject");
					}
					throw new IllegalStateException(
							"encountered non-object JSON element along the path defined by properties: "
									+ Joiner.on(".").join(Arrays.copyOf(propertyNames, i)));
				}
				return null;
			}
			JsonElement next = curr.getAsJsonObject().get(propertyNames[i]);
			if (next == null) {
				if (createOnDemand.isPresent()) {
					if (i < propertyNames.length - 1) {
						next = new JsonObject();
					} else {
						next = createOnDemand.get().get();
						if (next == null) {
							if (failFast) {
								throw new IllegalStateException("supplier for new JSON element returned null");
							}
							return null;
						}
					}
					curr.getAsJsonObject().add(propertyNames[i], next);
				} else {
					if (failFast) {
						throw new IllegalStateException(
								"no JSON value found at: " + Joiner.on(".").join(propertyNames));
					}
					return null;
				}
			}
			curr = next;
			i++;
		}
		if (!expectedType.isInstance(curr)) {
			if (failFast) {
				throw new IllegalStateException("the JSON value is not an instance of the expected type");
			}
			return null;
		}
		return expectedType.cast(curr);
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

	public static void addSourceFoldersToPackageJsonFile(Path path, String sourceContainerType, String... srcFolders)
			throws FileNotFoundException, IOException {
		Objects.requireNonNull(srcFolders);
		if (srcFolders.length == 0) {
			throw new IllegalArgumentException("no source folders given");
		}
		JsonElement root = loadJson(path);
		JsonArray srcFolderArray = getOrCreateArrayDeepFailFast(root, UtilN4.PACKAGE_JSON__N4JS,
				UtilN4.PACKAGE_JSON__SOURCES, sourceContainerType);
		Set<String> existingSrcFolders = FluentIterable.from(srcFolderArray).filter(JsonPrimitive.class)
				.transform(prim -> prim.getAsString()).toSet();
		for (String srcFolder : srcFolders) {
			if (existingSrcFolders.contains(srcFolder)) {
				throw new IllegalStateException("package.json file already contains this source folder: " + srcFolder);
			}
			srcFolderArray.add(new JsonPrimitive(srcFolder));
		}
		saveJson(path, root);
	}

	public static void removeSourceFoldersFromPackageJsonFile(Path path, String... srcFolders)
			throws FileNotFoundException, IOException {
		Objects.requireNonNull(srcFolders);
		if (srcFolders.length == 0) {
			throw new IllegalArgumentException("no source folders given");
		}
		JsonElement root = loadJson(path);
		JsonObject sourcesObject = getObjectDeep(root, UtilN4.PACKAGE_JSON__N4JS, UtilN4.PACKAGE_JSON__SOURCES);
		if (sourcesObject == null || sourcesObject.keySet().isEmpty()) {
			throw new IllegalStateException("package.json file does not contain any source folders");
		}
		Set<String> srcFoldersAsSet = Sets.newHashSet(srcFolders);
		Set<String> pending = new HashSet<>(srcFoldersAsSet);
		for (String sourceContainerType : sourcesObject.keySet()) {
			JsonElement srcFoldersOfThisType = sourcesObject.get(sourceContainerType);
			if (srcFoldersOfThisType != null && srcFoldersOfThisType.isJsonArray()) {
				Iterables.removeIf((JsonArray) srcFoldersOfThisType, elem -> {
					String name = elem != null && elem.isJsonPrimitive() && elem.getAsJsonPrimitive().isString()
							? elem.getAsString()
							: null;
					if (name != null && srcFoldersAsSet.contains(name)) {
						pending.remove(name);
						return true;
					}
					return false;
				});
			}
		}
		if (!pending.isEmpty()) {
			throw new IllegalStateException("package.json file does not contain the following source folders: "
					+ Joiner.on(", ").join(pending));
		}
		saveJson(path, root);
	}

	public static void addDependenciesToPackageJsonFile(Path path,
			Pair<String, String>... namesAndVersionConstraints) throws FileNotFoundException, IOException {
		Objects.requireNonNull(namesAndVersionConstraints);
		if (namesAndVersionConstraints.length == 0) {
			throw new IllegalArgumentException("no dependencies given");
		}
		JsonElement root = loadJson(path);
		JsonObject depsValue = getOrCreateObjectDeepFailFast(root, UtilN4.PACKAGE_JSON__DEPENDENCIES);
		for (Pair<String, String> navc : namesAndVersionConstraints) {
			String name = navc.getKey();
			String versionConstraint = navc.getValue();
			if (depsValue.keySet().contains(name)) {
				throw new IOException("package.json file already contains a dependency to \"" + name + "\"");
			}
			depsValue.add(name, new JsonPrimitive(versionConstraint));
		}
		saveJson(path, root);
	}

	public static void removeDependenciesFromPackageJsonFile(Path path, String... namesToRemove)
			throws FileNotFoundException, IOException {
		Objects.requireNonNull(namesToRemove);
		if (namesToRemove.length == 0) {
			return;
		}
		JsonElement root = loadJson(path);
		JsonObject depsValue = getObjectDeep(root, UtilN4.PACKAGE_JSON__DEPENDENCIES);
		if (depsValue == null) {
			throw new IOException("package.json does not contain any dependencies");
		}
		for (String name : namesToRemove) {
			if (!depsValue.keySet().contains(name)) {
				throw new IOException("package.json file does not contain a dependency to \"" + name + "\"");
			}
			depsValue.remove(name);
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
