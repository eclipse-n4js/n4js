/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.packagejson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.utils.JsonUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 *
 */
public class PackageJsonModificationUtils {

	/**
	 * Same as {@link #addSourceFoldersToPackageJsonFile(JsonElement, SourceContainerType, String...)}, but changes a
	 * <code>package.json</code> file on disk.
	 */
	public static void addSourceFoldersToPackageJsonFile(Path path, SourceContainerType type, String... srcFolders)
			throws FileNotFoundException, IOException {
		JsonElement root = JsonUtils.loadJson(path);
		addSourceFoldersToPackageJsonFile(root, type, srcFolders);
		JsonUtils.saveJson(path, root);
	}

	/**
	 * Adds the given source folders, assuming <code>root</code> is the root object of a <code>package.json</code> file.
	 * Throws an exception if one of those source folders is already contained in the <code>package.json</code>.
	 */
	public static void addSourceFoldersToPackageJsonFile(JsonElement root, SourceContainerType type,
			String... srcFolders) {
		Objects.requireNonNull(srcFolders);
		if (srcFolders.length == 0) {
			throw new IllegalArgumentException("no source folders given");
		}
		JsonArray srcFolderArray = JsonUtils.getOrCreateArrayDeepFailFast(root, UtilN4.PACKAGE_JSON__N4JS,
				UtilN4.PACKAGE_JSON__SOURCES, PackageJsonUtils.getSourceContainerTypeStringRepresentation(type));
		Set<String> existingSrcFolders = FluentIterable.from(srcFolderArray).filter(JsonPrimitive.class)
				.transform(prim -> prim.getAsString()).toSet();
		for (String srcFolder : srcFolders) {
			if (existingSrcFolders.contains(srcFolder)) {
				throw new IllegalStateException("package.json file already contains this source folder: " + srcFolder);
			}
			srcFolderArray.add(new JsonPrimitive(srcFolder));
		}
	}

	/**
	 * Same as {@link #removeSourceFoldersFromPackageJsonFile(JsonElement, String...)}, but changes a
	 * <code>package.json</code> file on disk.
	 */
	public static void removeSourceFoldersFromPackageJsonFile(Path path, String... srcFolders)
			throws FileNotFoundException, IOException {
		JsonElement root = JsonUtils.loadJson(path);
		removeSourceFoldersFromPackageJsonFile(root, srcFolders);
		JsonUtils.saveJson(path, root);
	}

	/**
	 * Removes the given source folders, assuming <code>root</code> is the root object of a <code>package.json</code>
	 * file. Throws an exception if one of those source folders is not contained in the <code>package.json</code>.
	 */
	public static void removeSourceFoldersFromPackageJsonFile(JsonElement root, String... srcFolders) {
		Objects.requireNonNull(srcFolders);
		if (srcFolders.length == 0) {
			throw new IllegalArgumentException("no source folders given");
		}
		JsonObject sourcesObject = JsonUtils.getObjectDeep(root, UtilN4.PACKAGE_JSON__N4JS,
				UtilN4.PACKAGE_JSON__SOURCES);
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
	}

	/**
	 * Same as {@link #addDependenciesToPackageJsonFile(JsonElement, Pair...)}, but changes a <code>package.json</code>
	 * file on disk.
	 */
	@SafeVarargs
	public static void addDependenciesToPackageJsonFile(Path path, Pair<String, String>... namesAndVersionConstraints)
			throws FileNotFoundException, IOException {
		JsonElement root = JsonUtils.loadJson(path);
		addDependenciesToPackageJsonFile(root, namesAndVersionConstraints);
		JsonUtils.saveJson(path, root);
	}

	/**
	 * Adds the given projects as dependencies, assuming <code>root</code> is the root object of a
	 * <code>package.json</code> file. Throws an exception if one of those dependencies already exists.
	 */
	@SafeVarargs
	public static void addDependenciesToPackageJsonFile(JsonElement root,
			Pair<String, String>... projectNamesAndVersionConstraints) {
		Objects.requireNonNull(projectNamesAndVersionConstraints);
		if (projectNamesAndVersionConstraints.length == 0) {
			throw new IllegalArgumentException("no dependencies given");
		}
		JsonObject depsValue = JsonUtils.getOrCreateObjectDeepFailFast(root, UtilN4.PACKAGE_JSON__DEPENDENCIES);
		for (Pair<String, String> navc : projectNamesAndVersionConstraints) {
			String name = navc.getKey();
			String versionConstraint = navc.getValue();
			if (depsValue.keySet().contains(name)) {
				throw new IllegalStateException("package.json file already contains a dependency to \"" + name + "\"");
			}
			depsValue.add(name, new JsonPrimitive(versionConstraint));
		}
	}

	/**
	 * Same as {@link #removeDependenciesFromPackageJsonFile(JsonElement, String...)}, but changes a
	 * <code>package.json</code> file on disk.
	 */
	public static void removeDependenciesFromPackageJsonFile(Path path, String... projectNamesToRemove)
			throws FileNotFoundException, IOException {
		JsonElement root = JsonUtils.loadJson(path);
		removeDependenciesFromPackageJsonFile(root, projectNamesToRemove);
		JsonUtils.saveJson(path, root);
	}

	/**
	 * Removes the dependencies for the given project names, assuming <code>root</code> is the root object of a
	 * <code>package.json</code> file. Throws an exception if those dependencies do not exist.
	 */
	public static void removeDependenciesFromPackageJsonFile(JsonElement root, String... projectNamesToRemove) {
		Objects.requireNonNull(projectNamesToRemove);
		if (projectNamesToRemove.length == 0) {
			return;
		}
		JsonObject depsValue = JsonUtils.getObjectDeep(root, UtilN4.PACKAGE_JSON__DEPENDENCIES);
		if (depsValue == null) {
			throw new IllegalStateException("package.json does not contain any dependencies");
		}
		for (String name : projectNamesToRemove) {
			if (!depsValue.keySet().contains(name)) {
				throw new IllegalStateException("package.json file does not contain a dependency to \"" + name + "\"");
			}
			depsValue.remove(name);
		}
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
	 * Same as {@link #setVersionOfDependenciesInPackageJsonFile(JsonElement, Set, JsonElement)}, but changes a
	 * <code>package.json</code> file on disk.
	 */
	public static void setVersionOfDependenciesInPackageJsonFile(Path packageJsonFile,
			Set<String> namesOfDependencies, JsonElement versionToSet) throws FileNotFoundException, IOException {
		JsonElement root = JsonUtils.loadJson(packageJsonFile);
		boolean changed = setVersionOfDependenciesInPackageJsonFile(root, namesOfDependencies, versionToSet);
		if (changed) {
			JsonUtils.saveJson(packageJsonFile, root);
		}
	}

	/**
	 * Changes the version of all dependencies and devDependencies to packages with a name contained in
	 * {@code namesOfDependencies} to the given version. Names given in the set that are not among the dependencies in
	 * the package.json file will be ignored; dependencies in the package.json file that are not denoted by the names in
	 * the set will remain unchanged.
	 */
	public static boolean setVersionOfDependenciesInPackageJsonFile(JsonElement root, Set<String> namesOfDependencies,
			JsonElement versionToSet) {
		if (namesOfDependencies.isEmpty()) {
			return false;
		}
		boolean changed = false;
		JsonElement deps = JsonUtils.getDeep(root, UtilN4.PACKAGE_JSON__DEPENDENCIES);
		if (deps != null && deps.isJsonObject()) {
			changed |= JsonUtils.changeProperties((JsonObject) deps, namesOfDependencies, versionToSet);
		}
		JsonElement devDeps = JsonUtils.getDeep(root, UtilN4.PACKAGE_JSON__DEV_DEPENDENCIES);
		if (devDeps != null && devDeps.isJsonObject()) {
			changed |= JsonUtils.changeProperties((JsonObject) devDeps, namesOfDependencies, versionToSet);
		}
		return changed;
	}
}
