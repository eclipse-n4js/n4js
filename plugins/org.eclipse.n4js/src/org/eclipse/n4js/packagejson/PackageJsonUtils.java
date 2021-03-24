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
package org.eclipse.n4js.packagejson;

import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asArrayElementsOrEmpty;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNameValuePairsOrEmpty;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNonEmptyStringOrNull;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNonEmptyStringsInArrayOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NV_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NV_SOURCE_CONTAINER;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.packagejson.projectDescription.KeywordEnum;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilter;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterSpecifier;
import org.eclipse.n4js.packagejson.projectDescription.ModuleFilterType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectReference;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.validation.validators.packagejson.N4JSProjectSetupJsonValidatorExtension;
import org.eclipse.n4js.validation.validators.packagejson.PackageJsonValidatorExtension;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Strings;
import com.google.gson.JsonElement;

/**
 * A utility methods for extracting N4JS-specific information from generic {@link JSONPackage} model instances
 * <p>
 * These utility methods do not validate the structure of the given {@link JSONPackage} instances. Rather, they will
 * defensively abort and return {@code null} in case the given {@link JSONValue} is not a valid representation of the
 * {@link ProjectDescription} instance in question (for validation see {@link PackageJsonValidatorExtension} and
 * {@link N4JSProjectSetupJsonValidatorExtension}).
 * <p>
 * Example: obtain source containers in terms of {@link SourceContainerDescription}s from a given {@link JSONObject})
 */
public class PackageJsonUtils {

	private static final Map<String, ModuleFilterType> keywordToModuleFilterType = KeywordEnum
			.createKeywordToLiteralMap(ModuleFilterType.class);
	private static final Map<String, ProjectType> keywordToProjectType = KeywordEnum
			.createKeywordToLiteralMap(ProjectType.class);
	private static final Map<String, SourceContainerType> keywordToSourceContainerType = KeywordEnum
			.createKeywordToLiteralMap(SourceContainerType.class);

	/**
	 * Converts given JSON value to a {@link ProjectReference}; returns <code>null</code> if not possible.
	 */
	public static ProjectReference asProjectReferenceOrNull(JSONValue jsonValue) {
		String valueStr = asNonEmptyStringOrNull(jsonValue);
		if (!Strings.isNullOrEmpty(valueStr)) {
			return new ProjectReference(valueStr);
		}
		return null;
	}

	/**
	 * If the given JSON value is a {@link JSONArray}, returns its elements converted to {@link ProjectReference}s with
	 * {@link #asProjectReferenceOrNull(JSONValue)}; otherwise an empty list is returned.
	 */
	public static List<ProjectReference> asProjectReferencesInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(PackageJsonUtils::asProjectReferenceOrNull)
				.filter(ref -> ref != null)
				.collect(Collectors.toList());
	}

	/**
	 * Converts given name/value pair to a {@link ModuleFilter}; returns <code>null</code> if not possible.
	 * <p>
	 * Expected format of argument:
	 *
	 * <pre>
	 * "noValidate": [
	 *     "abc*",
	 *     {
	 *         "sourceContainer": "src",
	 *         "module": "abc*"
	 *     }
	 * ]
	 * </pre>
	 */
	public static ModuleFilter asModuleFilterOrNull(NameValuePair pair) {
		ModuleFilterType type = parseModuleFilterType(pair.getName());
		if (type != null) {
			List<ModuleFilterSpecifier> mspecs = asModuleFilterSpecifierInArrayOrEmpty(pair.getValue());
			if (!mspecs.isEmpty()) {
				return new ModuleFilter(type, mspecs);
			}
		}
		return null;
	}

	/**
	 * If the given JSON value is a {@link JSONObject}, returns its name/value pairs converted to {@link ModuleFilter}s
	 * with {@link #asModuleFilterOrNull(NameValuePair)}; otherwise an empty list is returned.
	 */
	public static List<ModuleFilter> asModuleFiltersInObjectOrEmpty(JSONValue jsonValue) {
		return asNameValuePairsOrEmpty(jsonValue).stream()
				.map(PackageJsonUtils::asModuleFilterOrNull)
				.filter(mfilter -> mfilter != null)
				.collect(Collectors.toList());
	}

	/**
	 * Converts given JSON value to a {@link ModuleFilterSpecifier}; returns <code>null</code> if not possible.<br>
	 * The following variants are supported:
	 *
	 * <pre>
	 * "abc*"
	 *
	 * // or:
	 *
	 * {
	 *     "sourceContainer": "src"
	 *     "module": "abc*",
	 * }
	 * </pre>
	 */
	public static ModuleFilterSpecifier asModuleFilterSpecifierOrNull(JSONValue jsonValue) {
		// 1st variant:
		String singleString = asNonEmptyStringOrNull(jsonValue);
		if (singleString != null) {
			return new ModuleFilterSpecifier(singleString, null);
		}
		// 2nd variant:
		List<NameValuePair> pairs = asNameValuePairsOrEmpty(jsonValue);
		NameValuePair pathNVP = pairs.stream()
				.filter(p -> NV_SOURCE_CONTAINER.name.equals(p.getName())).findFirst()
				.orElse(null);
		NameValuePair moduleNVP = pairs.stream().filter(p -> NV_MODULE.name.equals(p.getName()))
				.findFirst().orElse(null);
		String pathStr = pathNVP != null ? asNonEmptyStringOrNull(pathNVP.getValue()) : null;
		String moduleStr = moduleNVP != null ? asNonEmptyStringOrNull(moduleNVP.getValue()) : null;
		if (moduleStr != null) { // pathStr may be null, i.e. "sourceContainer" is optional
			return new ModuleFilterSpecifier(moduleStr, pathStr);
		}
		return null;
	}

	/**
	 * If the given JSON value is a {@link JSONArray}, returns its elements converted to {@link ModuleFilterSpecifier}s
	 * with {@link #asModuleFilterSpecifierOrNull(JSONValue)}; otherwise an empty list is returned.
	 */
	public static List<ModuleFilterSpecifier> asModuleFilterSpecifierInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(PackageJsonUtils::asModuleFilterSpecifierOrNull)
				.filter(mspec -> mspec != null)
				.collect(Collectors.toList());
	}

	/**
	 * Converts given name/value pair to a {@link SourceContainerDescription}; returns <code>null</code> if not
	 * possible.
	 * <p>
	 * Expected format of argument:
	 *
	 * <pre>
	 * "source": [
	 *     "src1",
	 *     "src2"
	 * ]
	 *
	 * // or:
	 *
	 * "external": [
	 *     "src-ext"
	 * ]
	 * </pre>
	 */
	public static SourceContainerDescription asSourceContainerDescriptionOrNull(NameValuePair pair) {
		SourceContainerType type = parseSourceContainerType(pair.getName());
		List<String> paths = asNonEmptyStringsInArrayOrEmpty(pair.getValue());
		if (type != null && !paths.isEmpty()) {
			return new SourceContainerDescription(type, paths);
		}
		return null;
	}

	/**
	 * If the given JSON value is a {@link JSONObject}, returns its name/value pairs converted to
	 * {@link SourceContainerDescription}s with {@link #asSourceContainerDescriptionOrNull(NameValuePair)}; otherwise an
	 * empty list is returned.
	 */
	public static List<SourceContainerDescription> asSourceContainerDescriptionsOrEmpty(JSONValue sourcesSection) {
		return asNameValuePairsOrEmpty(sourcesSection).stream()
				.map(PackageJsonUtils::asSourceContainerDescriptionOrNull)
				.filter(scd -> scd != null)
				.collect(Collectors.toList());
	}

	/**
	 * Parses the {@link ModuleFilterType} from the given string representation.
	 *
	 * Returns {@code null} if {@code value} is not a valid string representation of a {@link ModuleFilterType}.
	 */
	public static ModuleFilterType parseModuleFilterType(String value) {
		return value != null ? keywordToModuleFilterType.get(value) : null;
	}

	/**
	 * Returns the string representation of the given {@link ModuleFilterType} or <code>null</code>.
	 */
	public static String getModuleFilterTypeStringRepresentation(ModuleFilterType type) {
		return type != null ? type.getKeyword() : null;
	}

	/**
	 * Parses a {@link ProjectType} from the given string representation.
	 *
	 * Returns {@code null} if {@code value} is not a valid string representation of a {@link ProjectType}.
	 */
	public static ProjectType parseProjectType(String projectTypeStr) {
		return projectTypeStr != null ? keywordToProjectType.get(projectTypeStr) : null;
	}

	/**
	 * Returns the string representation of the given {@link ProjectType} or <code>null</code>.
	 */
	public static String getProjectTypeStringRepresentation(ProjectType projectType) {
		return projectType != null ? projectType.getKeyword() : null;
	}

	/**
	 * Parses a {@link SourceContainerType} from the given string representation.
	 *
	 * Returns {@code null} if {@code sourceContainerTypeStr} is not a valid source container type string
	 * representation.
	 */
	public static SourceContainerType parseSourceContainerType(String sourceContainerTypeStr) {
		return sourceContainerTypeStr != null ? keywordToSourceContainerType.get(sourceContainerTypeStr) : null;
	}

	/**
	 * Returns the string representation of the given {@link SourceContainerType} or <code>null</code>.
	 */
	public static String getSourceContainerTypeStringRepresentation(SourceContainerType type) {
		return type != null ? type.getKeyword() : null;
	}

	/**
	 * Traverses the given {@link Resource} and finds all {@link JSONValue}s that match the given
	 * {@link PackageJsonProperties}. The list of results is filtered in case a {@link Class} is given.
	 *
	 * @return all {@link NameValuePair}s for the given {@link PackageJsonProperties} in the given json resource
	 */
	public static <T extends JSONValue> List<T> findNameValuePairs(Resource jsonResource, PackageJsonProperties prop,
			Class<T> clazz) {

		String[] pathElements = prop.getPathElements();
		List<T> nameValuePairs = new LinkedList<>();
		EList<EObject> contents = jsonResource.getContents();
		EObject rootElem = contents.get(0);
		if (rootElem instanceof JSONDocument) {
			JSONDocument jsonDocument = (JSONDocument) rootElem;
			JSONValue jsonContent = jsonDocument.getContent();
			if (jsonContent instanceof JSONObject) {
				JSONObject jsonObj = (JSONObject) jsonContent;
				for (NameValuePair child : jsonObj.getNameValuePairs()) {
					searchNameValuePair(child, pathElements, 0, clazz, nameValuePairs);
				}
			}
		}

		return nameValuePairs;
	}

	@SuppressWarnings("unchecked")
	private static <T extends JSONValue> void searchNameValuePair(NameValuePair valuePair, String[] pathElems, int i,
			Class<T> clazz, List<T> result) {

		String searchName = pathElems[i];
		String jsonName = valuePair.getName();
		JSONValue jsonValue = valuePair.getValue();
		if (i >= pathElems.length || !searchName.equals(jsonName)) {
			return;
		}

		if (i == pathElems.length - 1) {
			if (clazz == null || clazz.isAssignableFrom(jsonValue.getClass())) {
				result.add((T) jsonValue);
				return;
			}
		}

		if (jsonValue instanceof JSONObject) {
			JSONObject jObj = (JSONObject) jsonValue;
			for (NameValuePair child : jObj.getNameValuePairs()) {
				searchNameValuePair(child, pathElems, i + 1, clazz, result);
			}
		}
	}

	/**
	 * See {@link PackageJsonModificationUtils#addSourceFoldersToPackageJsonFile(Path, SourceContainerType, String...)}.
	 */
	public static void addSourceFoldersToPackageJsonFile(Path path, SourceContainerType type, String... srcFolders)
			throws FileNotFoundException, IOException {
		PackageJsonModificationUtils.addSourceFoldersToPackageJsonFile(path, type, srcFolders);
	}

	/** See {@link PackageJsonModificationUtils#removeSourceFoldersFromPackageJsonFile(Path, String...)}. */
	public static void removeSourceFoldersFromPackageJsonFile(Path path, String... srcFolders)
			throws FileNotFoundException, IOException {
		PackageJsonModificationUtils.removeSourceFoldersFromPackageJsonFile(path, srcFolders);
	}

	/** See {@link PackageJsonModificationUtils#addDependenciesToPackageJsonFile(Path, Pair...)}. */
	@SafeVarargs
	public static void addDependenciesToPackageJsonFile(Path path, Pair<String, String>... namesAndVersionConstraints)
			throws FileNotFoundException, IOException {
		PackageJsonModificationUtils.addDependenciesToPackageJsonFile(path, namesAndVersionConstraints);
	}

	/** See {@link PackageJsonModificationUtils#removeDependenciesFromPackageJsonFile(Path, String...)}. */
	public static void removeDependenciesFromPackageJsonFile(Path path, String... projectNamesToRemove)
			throws FileNotFoundException, IOException {
		PackageJsonModificationUtils.removeDependenciesFromPackageJsonFile(path, projectNamesToRemove);
	}

	/**
	 * See {@link PackageJsonModificationUtils#setVersionOfDependenciesInAllPackageJsonFiles(Path, Set, JsonElement)}.
	 */
	public static void setVersionOfDependenciesInAllPackageJsonFiles(Path rootFolder,
			Set<String> namesOfDependencies, JsonElement versionToSet) throws FileNotFoundException, IOException {
		PackageJsonModificationUtils.setVersionOfDependenciesInAllPackageJsonFiles(rootFolder, namesOfDependencies,
				versionToSet);
	}

	/** See {@link PackageJsonModificationUtils#setVersionOfDependenciesInPackageJsonFile(Path, Set, JsonElement)}. */
	public static void setVersionOfDependenciesInPackageJsonFile(Path packageJsonFile,
			Set<String> namesOfDependencies, JsonElement versionToSet) throws FileNotFoundException, IOException {
		PackageJsonModificationUtils.setVersionOfDependenciesInPackageJsonFile(packageJsonFile, namesOfDependencies,
				versionToSet);
	}
}
