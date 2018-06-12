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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.n4mf.ModuleFilterType;
import org.eclipse.n4js.n4mf.N4mfFactory;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;

/**
 * A helper for extracting N4JS-specific information from generic {@link JSONPackage} model instances
 *
 * The methods of this helper do not validate the structure of the given {@link JSONPackage} instances. Rather, they
 * will defensively abort and return {@code null} in case the given {@link JSONValue} is not a valid representation of
 * the {@link N4mfPackage} instance in question.
 *
 * Example: obtain source containers in terms of {@link SourceContainerDescription} from a given {@link JSONObject})
 */
public class PackageJsonHelper {

	/**
	 * Returns the list of {@link SourceContainerDescription} that can be extracted from the given {@code sources}
	 * section of a {@code package.json} file.
	 *
	 * Returns {@code null} if the given {@code sourcesSection} is invalid.
	 */
	public List<SourceContainerDescription> getSourceContainerDescriptions(JSONValue sourcesSection) {
		if (!(sourcesSection instanceof JSONObject)) {
			return null;
		}

		final List<SourceContainerDescription> descriptions = new ArrayList<>();

		for (NameValuePair pair : ((JSONObject) sourcesSection).getNameValuePairs()) {
			SourceContainerType type = parseSourceContainerType(pair.getName());
			List<String> paths = asStringsInArrayOrEmpty(pair.getValue());
			if (type != null && !paths.isEmpty()) {
				SourceContainerDescription sourceContainerDescription = N4mfFactory.eINSTANCE
						.createSourceContainerDescription();
				sourceContainerDescription.setSourceContainerType(type);
				sourceContainerDescription.getPathsRaw().addAll(paths);
				descriptions.add(sourceContainerDescription);
			}
		}

		return descriptions;
	}

	/**
	 * Parses the {@link ModuleFilterType} from the given string representation.
	 *
	 * Returns {@code null} if {@code value} is not a valid string representation of a {@link ModuleFilterType}.
	 */
	public ModuleFilterType parseModuleFilterType(String value) {
		if (value.equals("noValidate")) {
			return ModuleFilterType.NO_VALIDATE;
		} else if (value.equals("noModuleWrap")) {
			return ModuleFilterType.NO_MODULE_WRAPPING;
		} else {
			return null;
		}
	}

	/**
	 * Returns the string representation of the given {@link ModuleFilterType}.
	 */
	public String getModuleFilterTypeRepresentation(ModuleFilterType type) {
		if (type == ModuleFilterType.NO_VALIDATE) {
			return "noValidate";
		} else if (type == ModuleFilterType.NO_MODULE_WRAPPING) {
			return "noModuleWrap";
		} else {
			return "<invalid module filter type>";
		}
	}

	/**
	 * The following variants are supported:
	 *
	 * <pre>
	 * "abc*"
	 *
	 * ["src", "abc*"]
	 *
	 * {
	 *     "sourceContainer": "src"
	 *     "module": "abc*",
	 * }
	 * </pre>
	 */
	public ModuleFilterSpecifier getModuleFilterSpecifier(JSONValue jsonValue) {
		// 1st variant:
		String singleString = asStringOrNull(jsonValue);
		if (singleString != null) {
			return createModuleFilterSpecifier(null, singleString);
		}
		// 2nd variant:
		List<JSONValue> elements = asArrayElementsOrEmpty(jsonValue);
		if (elements.size() == 2) {
			String elem0 = asStringOrNull(elements.get(0));
			String elem1 = asStringOrNull(elements.get(1));
			if (elem0 != null && elem1 != null) {
				return createModuleFilterSpecifier(elem0, elem1);
			}
		}
		// 3rd variant:
		List<NameValuePair> pairs = asNameValuePairsOrEmpty(jsonValue);
		NameValuePair pathNVP = pairs.stream()
				.filter(p -> ProjectDescriptionHelper.PROP__SOURCE_CONTAINER.equals(p.getName())).findFirst()
				.orElse(null);
		NameValuePair moduleNVP = pairs.stream().filter(p -> ProjectDescriptionHelper.PROP__MODULE.equals(p.getName()))
				.findFirst().orElse(null);
		String pathStr = pathNVP != null ? asStringOrNull(pathNVP.getValue()) : null;
		String moduleStr = moduleNVP != null ? asStringOrNull(moduleNVP.getValue()) : null;
		if (moduleStr != null) { // pathStr may be null, i.e. "sourceContainer" is optional
			return createModuleFilterSpecifier(pathStr, moduleStr);
		}
		return null;
	}

	/**
	 * Parses a {@link ProjectType} from the given string representation.
	 *
	 * Returns {@code null} if {@code value} is not a valid string representation of a {@link ProjectType}.
	 */
	public ProjectType parseProjectType(String projectTypeStr) {
		if ("runtimeEnvironment".equals(projectTypeStr))
			return ProjectType.RUNTIME_ENVIRONMENT;
		if ("runtimeLibrary".equals(projectTypeStr))
			return ProjectType.RUNTIME_LIBRARY;
		return parseEnumLiteral(N4mfPackage.eINSTANCE.getProjectType(), ProjectType.class,
				projectTypeStr);
	}

	private ModuleFilterSpecifier createModuleFilterSpecifier(String sourcePath, String moduleSpecifierWithWildcard) {
		final ModuleFilterSpecifier result = N4mfFactory.eINSTANCE.createModuleFilterSpecifier();
		result.setSourcePath(sourcePath);
		result.setModuleSpecifierWithWildcard(moduleSpecifierWithWildcard);
		return result;
	}

	private SourceContainerType parseSourceContainerType(String sourceContainerTypeStr) {
		return parseEnumLiteral(N4mfPackage.eINSTANCE.getSourceContainerType(), SourceContainerType.class,
				sourceContainerTypeStr);
	}

	private <T extends Enumerator> T parseEnumLiteral(EEnum emfEnumType, Class<T> javaEnumType, String enumLiteralStr) {
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

	private List<String> asStringsInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(this::asStringOrNull)
				.filter(pref -> pref != null)
				.collect(Collectors.toList());
	}

	private String asStringOrNull(JSONValue jsonValue) {
		return jsonValue instanceof JSONStringLiteral ? ((JSONStringLiteral) jsonValue).getValue() : null;
	}

	private List<JSONValue> asArrayElementsOrEmpty(JSONValue jsonValue) {
		return jsonValue instanceof JSONArray ? ((JSONArray) jsonValue).getElements() : Collections.emptyList();
	}

	private List<NameValuePair> asNameValuePairsOrEmpty(JSONValue jsonValue) {
		return jsonValue instanceof JSONObject ? ((JSONObject) jsonValue).getNameValuePairs() : Collections.emptyList();
	}
}
