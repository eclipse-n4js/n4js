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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.utils.JsonUtils;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
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
	 * Same as {@link #setVersionOfDependenciesInPackageJsonFile(Path, Set, String)}, but for all
	 * <code>package.json</code> files in the entire folder tree below the given root folder.
	 *
	 * @return list of all modified package.json files
	 */
	public static List<Path> setVersionOfDependenciesInAllPackageJsonFiles(Path root, Set<N4JSProjectName> projectNames,
			String versionConstraintToSet) throws IOException {

		List<Path> packageJsonFiles = new LinkedList<>();
		EnumSet<FileVisitOption> options = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
		Files.walkFileTree(root, options, Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
				if (dir.endsWith("node_modules") || dir.endsWith(".git")) {
					return FileVisitResult.SKIP_SUBTREE;
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (file.endsWith("package.json")) {
					packageJsonFiles.add(file);
				}
				return FileVisitResult.CONTINUE;
			}
		});

		List<Path> modifiedFiles = new LinkedList<>();
		for (Path file : packageJsonFiles) {
			boolean modified = setVersionOfDependenciesInPackageJsonFile(file, projectNames, versionConstraintToSet);
			if (modified) {
				modifiedFiles.add(file);
			}
		}
		return modifiedFiles;
	}

	/**
	 * Same as {@link #setVersionOfDependenciesInPackageJsonString(String, Set, String)}, but changes a
	 * <code>package.json</code> file on disk.
	 *
	 * @return true iff the package.json file was modified
	 */
	public static boolean setVersionOfDependenciesInPackageJsonFile(Path packageJsonFile,
			Set<N4JSProjectName> projectNames, String versionConstraintToSet) throws IOException {
		String packageJsonStr = Files.readString(packageJsonFile);
		Optional<String> result = setVersionOfDependenciesInPackageJsonString(packageJsonStr, projectNames,
				versionConstraintToSet);
		if (result.isPresent()) {
			Files.writeString(packageJsonFile, result.get(), StandardOpenOption.TRUNCATE_EXISTING);
		}
		return result.isPresent();
	}

	/**
	 * Changes the version of all dependencies and devDependencies to packages with a name contained in
	 * {@code projectNames} to the given version. Names given in the set that are not among the dependencies in the
	 * package.json file will be ignored (i.e. this method won't add any dependencies); dependencies in the package.json
	 * file that are not denoted by the names in the set will remain unchanged.
	 *
	 * @return the <code>package.json</code> string after all replacements were done, or {@link Optional#absent()
	 *         absent} in case the JSON string was successfully parsed, but this operation did not lead to any changes.
	 * @throws IllegalArgumentException
	 *             in case of parse errors.
	 */
	public static Optional<String> setVersionOfDependenciesInPackageJsonString(String packageJson,
			Set<N4JSProjectName> projectNames, String versionConstraintToSet) {
		List<ElementWithRegion> deps = findDependenciesWithRegion(packageJson);
		if (deps.isEmpty()) {
			return Optional.absent();
		}
		StringBuilder result = null;
		Collections.sort(deps, (dep1, dep2) -> Integer.compare(dep2.offset, dep1.offset));
		for (ElementWithRegion dep : deps) {
			if (projectNames.contains(new N4JSProjectName(dep.elementName))) {
				if (result == null) {
					result = new StringBuilder(packageJson);
				}
				result.replace(dep.offset + 1, dep.offset + dep.length - 1, versionConstraintToSet);
			}
		}
		return result != null ? Optional.of(result.toString()) : Optional.absent();
	}

	private static class ElementWithRegion {
		/** Parent property names */
		@SuppressWarnings("unused")
		public final Stack<String> propertyPath;
		/**
		 * Name of the json element. Can be null. Derived from
		 * {@link com.fasterxml.jackson.core.JsonParser#getCurrentName()}
		 */
		public final String elementName;
		/** Value of the parsed element */
		public final String value;
		/** Zero-based offset of the version constraint in the original JSON input string. */
		public final int offset;
		/** Length of the version constraint. */
		public final int length;

		public ElementWithRegion(Stack<String> propertyPath, String elementName, String value, int offset, int length) {
			this.propertyPath = propertyPath;
			this.elementName = elementName;
			this.value = value;
			this.offset = offset;
			this.length = length;
		}
	}

	/**
	 * Given a <code>package.json</code> as string, this method returns all dependencies and devDependencies, including
	 * their version constraint and the text region of the version constraint.
	 *
	 * @throw {@link IllegalArgumentException} in case of parse errors.
	 */
	private static List<ElementWithRegion> findDependenciesWithRegion(String jsonStr) {
		return findElementsWithRegion(jsonStr, "dependencies", "devDependencies");
	}

	/*
	 * Unfortunately, we cannot use GSON for implementing this method. Since Jackson was already among the dependencies
	 * of N4JS (at time of writing), we can use that. To avoid confusion with GSON, we use fully qualified names when
	 * referring to the types of Jackson in this method.
	 */
	private static List<ElementWithRegion> findElementsWithRegion(String jsonStr, String... parentPropertyPaths) {
		Multimap<Integer, String> parents = HashMultimap.create();
		for (String parentPropertyPath : parentPropertyPaths) {
			int depth = parentPropertyPath.split("/").length;
			parents.put(depth, parentPropertyPath);
		}

		List<ElementWithRegion> result = new ArrayList<>();
		com.fasterxml.jackson.core.JsonFactory factory = new com.fasterxml.jackson.core.JsonFactory();
		try (com.fasterxml.jackson.core.JsonParser parser = factory.createParser(jsonStr)) {
			com.fasterxml.jackson.core.JsonToken token = parser.nextToken();
			if (token != com.fasterxml.jackson.core.JsonToken.START_OBJECT) {
				throw new IllegalArgumentException("expected a JSON object at top level");
			}

			Stack<String> propertyPath = new Stack<>(); // may contain 'null' values

			while ((token = parser.nextToken()) != null) {
				if (token == com.fasterxml.jackson.core.JsonToken.START_ARRAY
						|| token == com.fasterxml.jackson.core.JsonToken.START_OBJECT) {
					propertyPath.push(parser.getCurrentName());
				} else if (token == com.fasterxml.jackson.core.JsonToken.END_ARRAY
						|| token == com.fasterxml.jackson.core.JsonToken.END_OBJECT) {
					if (!propertyPath.isEmpty()) {
						propertyPath.pop();
					}
				} else if (token == com.fasterxml.jackson.core.JsonToken.VALUE_STRING
						&& parents.containsKey(propertyPath.size())
						&& parents.get(propertyPath.size()).contains(Strings.join("/", propertyPath))) {
					String elementName = parser.getCurrentName();
					long offs = parser.getTokenLocation().getCharOffset();
					long len = parser.getTextLength() + 2;
					String valueRaw = jsonStr.substring((int) offs, (int) (offs + len));
					result.add(new ElementWithRegion(propertyPath, elementName, valueRaw, (int) offs, (int) len));
				}
			}
		} catch (IOException e) { // includes JsonParseException
			throw new IllegalArgumentException("failed to parse JSON: " + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Adds the given String to the 'workspaces' property of a package.json file of a yarn project. The 'workspaces'
	 * property is not created if it does not exist.
	 *
	 * @param packageJson
	 *            The file that is modified.
	 * @param additionalWorkspaces
	 *            String that is added to the 'workspaces' property.
	 */
	public static boolean addToWorkspaces(File packageJson, String additionalWorkspaces) throws IOException {
		String packageJsonStr = Files.readString(packageJson.toPath());
		List<ElementWithRegion> workspacesEntries = findElementsWithRegion(packageJsonStr,
				"workspaces", "workspaces/packages");
		if (workspacesEntries.isEmpty()) {
			return false;
		}
		ElementWithRegion workspacesEntry = workspacesEntries.get(0);
		String newValue = workspacesEntry.value + ", \"" + additionalWorkspaces + "\"";
		StringBuilder newPackageJsonStr = new StringBuilder(packageJsonStr);
		int startIdx = workspacesEntry.offset;
		int endIdx = workspacesEntry.offset + workspacesEntry.length;
		newPackageJsonStr.replace(startIdx, endIdx, newValue);
		try (FileWriter fw = new FileWriter(packageJson, false)) {
			fw.write(newPackageJsonStr.toString());
		}
		return true;
	}
}
