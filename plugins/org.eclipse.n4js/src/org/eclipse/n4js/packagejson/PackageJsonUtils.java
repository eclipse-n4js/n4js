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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.projectDescription.BootstrapModule;
import org.eclipse.n4js.projectDescription.ModuleFilter;
import org.eclipse.n4js.projectDescription.ModuleFilterSpecifier;
import org.eclipse.n4js.projectDescription.ModuleFilterType;
import org.eclipse.n4js.projectDescription.ProjectDescriptionFactory;
import org.eclipse.n4js.projectDescription.ProjectDescriptionPackage;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.validation.validators.packagejson.N4JSProjectSetupJsonValidatorExtension;
import org.eclipse.n4js.validation.validators.packagejson.PackageJsonValidatorExtension;

import com.google.common.base.Strings;

/**
 * A utility methods for extracting N4JS-specific information from generic {@link JSONPackage} model instances
 * <p>
 * These utility methods do not validate the structure of the given {@link JSONPackage} instances. Rather, they will
 * defensively abort and return {@code null} in case the given {@link JSONValue} is not a valid representation of the
 * {@link ProjectDescriptionPackage} instance in question (for validation see {@link PackageJsonValidatorExtension} and
 * {@link N4JSProjectSetupJsonValidatorExtension}).
 * <p>
 * Example: obtain source containers in terms of {@link SourceContainerDescription}s from a given {@link JSONObject})
 */
public class PackageJsonUtils {

	/**
	 * Converts given JSON value to a {@link ProjectReference}; returns <code>null</code> if not possible.
	 */
	public static ProjectReference asProjectReferenceOrNull(JSONValue jsonValue) {
		String valueStr = asNonEmptyStringOrNull(jsonValue);
		if (!Strings.isNullOrEmpty(valueStr)) {
			final ProjectReference result = ProjectDescriptionFactory.eINSTANCE.createProjectReference();
			result.setProjectName(valueStr);
			return result;
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
	 * Converts given JSON value to a {@link BootstrapModule}; returns <code>null</code> if not possible.
	 */
	public static BootstrapModule asBootstrapModuleOrNull(JSONValue jsonValue) {
		String valueStr = asNonEmptyStringOrNull(jsonValue);
		if (!Strings.isNullOrEmpty(valueStr)) {
			final BootstrapModule result = ProjectDescriptionFactory.eINSTANCE.createBootstrapModule();
			result.setModuleSpecifier(valueStr);
			return result;
		}
		return null;
	}

	/**
	 * If the given JSON value is a {@link JSONArray}, returns its elements converted to {@link BootstrapModule}s with
	 * {@link #asBootstrapModuleOrNull(JSONValue)}; otherwise an empty list is returned.
	 */
	public static List<BootstrapModule> asBootstrapModulesInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(PackageJsonUtils::asBootstrapModuleOrNull)
				.filter(boomod -> boomod != null)
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
	 *
	 * // or:
	 *
	 * "noModuleWrap": [
	 *     // same as above
	 * ]
	 * </pre>
	 */
	public static ModuleFilter asModuleFilterOrNull(NameValuePair pair) {
		ModuleFilterType type = parseModuleFilterType(pair.getName());
		if (type != null) {
			List<ModuleFilterSpecifier> mspecs = asModuleFilterSpecifierInArrayOrEmpty(pair.getValue());
			if (!mspecs.isEmpty()) {
				ModuleFilter mfilter = ProjectDescriptionFactory.eINSTANCE.createModuleFilter();
				mfilter.setModuleFilterType(type);
				mfilter.getModuleSpecifiers().addAll(mspecs);
				return mfilter;
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
			return createModuleFilterSpecifier(null, singleString);
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
			return createModuleFilterSpecifier(pathStr, moduleStr);
		}
		return null;
	}

	private static ModuleFilterSpecifier createModuleFilterSpecifier(String sourcePath,
			String moduleSpecifierWithWildcard) {
		final ModuleFilterSpecifier result = ProjectDescriptionFactory.eINSTANCE.createModuleFilterSpecifier();
		result.setSourcePath(sourcePath);
		result.setModuleSpecifierWithWildcard(moduleSpecifierWithWildcard);
		return result;
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
			SourceContainerDescription sourceContainerDescription = ProjectDescriptionFactory.eINSTANCE
					.createSourceContainerDescription();
			sourceContainerDescription.setSourceContainerType(type);
			sourceContainerDescription.getPaths().addAll(paths);
			return sourceContainerDescription;
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
		if (value.equals("noValidate")) {
			return ModuleFilterType.NO_VALIDATE;
		} else {
			return null;
		}
	}

	/**
	 * Returns the string representation of the given {@link ModuleFilterType}.
	 */
	public static String getModuleFilterTypeStringRepresentation(ModuleFilterType type) {
		if (type == ModuleFilterType.NO_VALIDATE) {
			return "noValidate";
		} else {
			return "<invalid module filter type>";
		}
	}

	/**
	 * Parses a {@link ProjectType} from the given string representation.
	 *
	 * Returns {@code null} if {@code value} is not a valid string representation of a {@link ProjectType}.
	 */
	public static ProjectType parseProjectType(String projectTypeStr) {
		if ("runtimeEnvironment".equals(projectTypeStr))
			return ProjectType.RUNTIME_ENVIRONMENT;
		if ("runtimeLibrary".equals(projectTypeStr))
			return ProjectType.RUNTIME_LIBRARY;
		return parseEnumLiteral(ProjectDescriptionPackage.eINSTANCE.getProjectType(), ProjectType.class,
				projectTypeStr);
	}

	/**
	 * Parses a {@link SourceContainerType} from the given string representation.
	 *
	 * Returns {@code null} if {@code sourceContainerTypeStr} is not a valid source container type string
	 * representation.
	 */
	public static SourceContainerType parseSourceContainerType(String sourceContainerTypeStr) {
		return parseEnumLiteral(ProjectDescriptionPackage.eINSTANCE.getSourceContainerType(), SourceContainerType.class,
				sourceContainerTypeStr);
	}

	/**
	 * Returns the string representation of the given {@link SourceContainerType}.
	 *
	 * @throw {@link NullPointerException} if {@code type} is null.
	 */
	public static String getSourceContainerTypeStringRepresentation(SourceContainerType type) {
		return type.getLiteral().toLowerCase();
	}

	private static <T extends Enumerator> T parseEnumLiteral(EEnum emfEnumType, Class<T> javaEnumType,
			String enumLiteralStr) {
		EEnumLiteral emfLit = enumLiteralStr != null ? emfEnumType.getELiterals().stream()
				.filter(lit -> lit.getName().equalsIgnoreCase(enumLiteralStr))
				.findFirst().orElse(null) : null;
		if (emfLit == null) {
			return null;
		}
		final Enumerator javaLit = emfLit.getInstance();
		@SuppressWarnings("unchecked")
		T javaLitCasted = javaEnumType.isInstance(javaLit) ? (T) javaLit : null;
		return javaLitCasted;
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
}
