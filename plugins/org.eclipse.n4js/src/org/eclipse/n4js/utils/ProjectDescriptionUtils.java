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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.n4mf.ModuleFilterType;
import org.eclipse.n4js.n4mf.ModuleLoader;
import org.eclipse.n4js.n4mf.N4mfFactory;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.n4mf.VersionConstraint;
import org.eclipse.n4js.validation.validators.packagejson.N4JSProjectSetupJsonValidatorExtension;
import org.eclipse.n4js.validation.validators.packagejson.PackageJsonValidatorExtension;

import com.google.common.base.Joiner;

/**
 * A utility methods for extracting N4JS-specific information from generic {@link JSONPackage} model instances
 *
 * These utility methods do not validate the structure of the given {@link JSONPackage} instances. Rather, they will
 * defensively abort and return {@code null} in case the given {@link JSONValue} is not a valid representation of the
 * {@link N4mfPackage} instance in question (for validation see {@link PackageJsonValidatorExtension} and
 * {@link N4JSProjectSetupJsonValidatorExtension}).
 *
 * Example: obtain source containers in terms of {@link SourceContainerDescription}s from a given {@link JSONObject})
 */
public class ProjectDescriptionUtils {

	private static final Pattern PATTERN_DOT = Pattern.compile("\\.");

	/**
	 * Returns the list of {@link SourceContainerDescription} that can be extracted from the given {@code sources}
	 * section of a {@code package.json} file.
	 *
	 * Returns {@code null} if the given {@code sourcesSection} is invalid.
	 */
	public static List<SourceContainerDescription> getSourceContainerDescriptions(JSONValue sourcesSection) {
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
	public static ModuleFilterType parseModuleFilterType(String value) {
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
	public static String getModuleFilterTypeRepresentation(ModuleFilterType type) {
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
	public static ModuleFilterSpecifier getModuleFilterSpecifier(JSONValue jsonValue) {
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
	 * Returns the {@link ProjectType} that is represented by the given {@link JSONValue}.
	 *
	 * Returns {@code null} if {@code projectTypeValue} is not a valid representation of a {@link ProjectType}.
	 *
	 * For simple project type parsing see {@link #parseProjectType(String)}.
	 */
	public static ProjectType getProjectType(JSONValue projectTypeValue) {
		return parseProjectType(asStringOrNull(projectTypeValue));
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
		return parseEnumLiteral(N4mfPackage.eINSTANCE.getProjectType(), ProjectType.class,
				projectTypeStr);
	}

	/**
	 * Parses a {@link ModuleLoader} from the given string representation.
	 *
	 * Returns {@code null} if {@code value} is not a valid string representation of a {@link ModuleLoader}.
	 */
	public static ModuleLoader parseModuleLoader(String moduleLoaderStr) {
		return parseEnumLiteral(N4mfPackage.eINSTANCE.getModuleLoader(), ModuleLoader.class,
				moduleLoaderStr);
	}

	/**
	 * Parses a {@link DeclaredVersion} from the given string representation.
	 *
	 * Returns {@code null} if {@code value} is not a valid string representation of a {@link DeclaredVersion}.
	 */

	/**
	 * Parses a {@link VersionConstraint} from the given string representation.
	 *
	 * Returns {@code null} if {@code versionConstraintStr} is not a valid version constraint string representation.
	 */
	public static VersionConstraint parseVersionConstraint(String versionConstraintStr) {
		if (versionConstraintStr == null) {
			return null;
		}
		VersionConstraint result = ProjectDescriptionUtils.parseVersionRange(versionConstraintStr);
		if (result == null) {
			System.err.println("WARNING: invalid or unsupported version constraint: " + versionConstraintStr);
		}
		return result;
	}

	/**
	 * Parses a {@link SourceContainerType} from the given string representation.
	 *
	 * Returns {@code null} if {@code sourceContainerTypeStr} is not a valid source container type string
	 * representation.
	 */
	public static SourceContainerType parseSourceContainerType(String sourceContainerTypeStr) {
		return parseEnumLiteral(N4mfPackage.eINSTANCE.getSourceContainerType(), SourceContainerType.class,
				sourceContainerTypeStr);
	}

	/**
	 * Returns the string representation of the given {@link SourceContainerType}.
	 *
	 * @throw {@link NullPointerException} if {@code type} is null.
	 */
	public static String getSourceContainerTypeRepresentation(SourceContainerType type) {
		return type.getLiteral().toLowerCase();
	}

	/**
	 * Given a path to the main module of an NPM project as given by the "main" property in a package.json, this method
	 * will return the corresponding N4JS module specifier. Returns <code>null</code> if given <code>null</code> or an
	 * invalid path (e.g. absolute path).
	 * <p>
	 * Note: this methods does not implement the package.json feature that a "main" path may point to a folder and then
	 * a file "index.js" in that folder will be used as main module (reason: this method cannot access the file system);
	 * to support this, the path given to this method must be adjusted *before* invoking this method (i.e. append
	 * "/index.js" iff the path points to a folder).
	 */
	public static String convertMainPathToModuleSpecifier(String path, List<String> sourceContainerPaths) {
		if (path == null) {
			return null;
		}
		// strip file extension
		if (path.endsWith(".js")) {
			path = path.substring(0, path.length() - 3);
		} else {
			return null; // in the standard package.json property "main", we ignore all files other than plain js files
		}
		// normalize path segments
		path = normalizeRelativePath(path);
		if (path == null) {
			return null;
		}
		// Now 'path' must point to a file inside a source container.
		// If that is true, then we want to return a path relative to that source container:
		List<String> sourceContainerPathsNormalized = sourceContainerPaths.stream()
				.map(ProjectDescriptionUtils::normalizeRelativePath)
				.filter(p -> p != null)
				.collect(Collectors.toList());
		for (String scp : sourceContainerPathsNormalized) {
			if (".".equals(scp)) {
				return path;
			} else {
				String scpSlash = scp + "/";
				if (path.startsWith(scpSlash)) {
					return path.substring(scpSlash.length());
				}
			}
		}
		return null;
	}

	private static String normalizeRelativePath(String path) {
		if (path == null || path.isEmpty()) {
			return null;
		}
		// enforce relative path
		if (path.startsWith("/")) {
			return null;
		}
		// normalize separator character
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar, '/');
		}
		// normalize ".", "..", and empty path segments
		// FIXME consider using Path#normalize() instead
		List<String> segmentsNew = new ArrayList<>();
		for (String segment : path.split("/", -1)) {
			if (segment.isEmpty()) {
				continue; // simply ignore //
			} else if (".".equals(segment)) {
				continue; // simply ignore /./
			} else if ("..".equals(segment)) {
				if (segmentsNew.isEmpty()) {
					return null;
				}
				segmentsNew.remove(segmentsNew.size() - 1);
			} else {
				segmentsNew.add(segment);
			}
		}
		if (segmentsNew.isEmpty()) {
			return ".";
		}
		return Joiner.on('/').join(segmentsNew);
	}

	/**
	 * Parses a SemVer version string according to the SemVer Specification at https://semver.org/
	 *
	 * Very simple, temporary implementation. For example, well-formedness of pre-release version and build meta-data
	 * are not checked.
	 */
	public static DeclaredVersion parseVersion(String str) {
		DeclaredVersion result = parseVersionPartial(str);
		if (result != null && result.getMinor() >= 0 && result.getMicro() >= 0) {
			return result;
		}
		return null;
	}

	/**
	 * Same as {@link #parseVersion(String)}, but allows for partial version, i.e. omitting minor version and patch
	 * version. A missing segment is denoted by -1.
	 */
	private static DeclaredVersion parseVersionPartial(String str) {
		DeclaredVersion result = N4mfFactory.eINSTANCE.createDeclaredVersion();
		// strip leading 'v' and '=' (see https://docs.npmjs.com/misc/semver, Section "Versions")
		if (str.startsWith("v") || str.startsWith("=")) {
			str = str.substring(1);
		}
		// strip & parse build meta-data (cf. SemVer Specification, no. 10)
		int idxBuildMeta = str.indexOf('+');
		if (idxBuildMeta >= 0) {
			String strBuildMeta = str.substring(idxBuildMeta + 1);
			if (strBuildMeta.isEmpty()) {
				return null;
			}
			str = str.substring(0, idxBuildMeta);
			result.setBuildMetaData(strBuildMeta);
		}
		// strip & parse pre-release version (cf. SemVer Specification, no. 9)
		int idxPreRelVer = str.indexOf('-');
		if (idxPreRelVer >= 0) {
			String strPreRelVer = str.substring(idxPreRelVer + 1);
			if (strPreRelVer.isEmpty()) {
				return null;
			}
			str = str.substring(0, idxPreRelVer);
			result.setQualifier(strPreRelVer);
		}
		// parse "normal version number" (cf. SemVer Specification, no. 2)
		String[] segments = PATTERN_DOT.split(str, -1);
		int segCount = segments.length;
		if (segCount < 1 || segCount > 3) {
			return null; // syntax error
		}
		try {
			int major = Integer.parseInt(segments[0]);
			int minor = segCount >= 2 ? Integer.parseInt(segments[1]) : -1;
			int patch = segCount >= 3 ? Integer.parseInt(segments[2]) : -1;
			result.setMajor(major);
			result.setMinor(minor);
			result.setMicro(patch);
		} catch (Exception e) {
			return null; // syntax error
		}
		return result;
	}

	/**
	 * Parses a small subset of SemVer version ranges as defined at https://docs.npmjs.com/misc/semver, Section
	 * "Ranges".
	 */
	public static VersionConstraint parseVersionRange(String str) {
		VersionConstraint result = N4mfFactory.eINSTANCE.createVersionConstraint();
		result.setExclLowerBound(false);
		result.setExclUpperBound(true);
		if ("*".equals(str)) {
			result.setLowerVersion(parseVersion("0.0.0")); // >=0.0.0
			return result;
		}
		if ("latest".equals(str)) {
			// cannot represent "latest" exactly with class VersionConstraint
			// -> using ">=0.0.0"
			result.setLowerVersion(parseVersion("0.0.0"));
			return result;
		}
		if (str.startsWith("~")) {
			str = str.substring(1);
			DeclaredVersion ver = parseVersionPartial(str);
			if (ver == null) {
				return null;
			}
			if (ver.getMinor() == -1) {
				// ~1 := >=1.0.0 <(1+1).0.0 := >=1.0.0 <2.0.0
				// ~0 := >=0.0.0 <(0+1).0.0 := >=0.0.0 <1.0.0
				ver.setMinor(0);
				ver.setMicro(0);
				DeclaredVersion lower = ver;
				DeclaredVersion upper = EcoreUtil.copy(ver);
				upper.setMajor(upper.getMajor() + 1);
				result.setLowerVersion(lower);
				result.setUpperVersion(upper);
			} else if (ver.getMicro() == -1) {
				// ~1.2 := >=1.2.0 <1.(2+1).0 := >=1.2.0 <1.3.0
				// ~0.2 := >=0.2.0 <0.(2+1).0 := >=0.2.0 <0.3.0
				ver.setMicro(0);
				DeclaredVersion lower = ver;
				DeclaredVersion upper = EcoreUtil.copy(ver);
				upper.setMinor(upper.getMinor() + 1);
				result.setLowerVersion(lower);
				result.setUpperVersion(upper);
			} else {
				// ~1.2.3 := >=1.2.3 <1.(2+1).0 := >=1.2.3 <1.3.0
				// ~0.2.3 := >=0.2.3 <0.(2+1).0 := >=0.2.3 <0.3.0
				DeclaredVersion lower = ver;
				DeclaredVersion upper = EcoreUtil.copy(ver);
				upper.setMinor(upper.getMinor() + 1);
				upper.setMicro(0);
				result.setLowerVersion(lower);
				result.setUpperVersion(upper);
			}
		} else if (str.startsWith("^")) {
			str = str.substring(1);
			DeclaredVersion ver = parseVersionPartial(str);
			if (ver == null) {
				return null;
			}
			if (ver.getMicro() == -1) {
				// partial version with caret not supported, yet
				// (e.g. ^1.2, ^1)
				return null;
			}
			DeclaredVersion lower = ver;
			DeclaredVersion upper = EcoreUtil.copy(ver);
			if (ver.getMajor() == 0 && ver.getMinor() == 0 && ver.getMicro() == 0) {
				// ^0.0.0 := >=0.0.0 <1.0.0
				upper.setMajor(upper.getMajor() + 1);
			} else if (ver.getMajor() == 0 && ver.getMinor() == 0) {
				// ^0.0.3 := >=0.0.3 <0.0.4
				upper.setMicro(upper.getMicro() + 1);
			} else if (ver.getMajor() == 0) {
				// ^0.2.3 := >=0.2.3 <0.3.0
				upper.setMinor(upper.getMinor() + 1);
				upper.setMicro(0);
			} else {
				// ^1.2.3 := >=1.2.3 <2.0.0
				upper.setMajor(upper.getMajor() + 1);
				upper.setMinor(0);
				upper.setMicro(0);
			}
			result.setLowerVersion(lower);
			result.setUpperVersion(upper);
		} else {
			DeclaredVersion ver = parseVersionPartial(str);
			if (ver == null) {
				return null;
			}
			// allow partial versions, i.e. 1 == 1.0.0 and 1.2 == 1.2.0
			// (strictly speaking, this is not part of SemVer but of coercion;
			// see API doc of function #coerce(version) of npm package 'semver')
			if (ver.getMinor() == -1) {
				ver.setMinor(0);
			}
			if (ver.getMicro() == -1) {
				ver.setMicro(0);
			}
			DeclaredVersion lower = ver;
			DeclaredVersion upper = EcoreUtil.copy(ver);
			result.setLowerVersion(lower);
			result.setUpperVersion(upper);
			result.setExclLowerBound(false);
			result.setExclUpperBound(false);
		}
		return result;
	}

	private static ModuleFilterSpecifier createModuleFilterSpecifier(String sourcePath,
			String moduleSpecifierWithWildcard) {
		final ModuleFilterSpecifier result = N4mfFactory.eINSTANCE.createModuleFilterSpecifier();
		result.setSourcePath(sourcePath);
		result.setModuleSpecifierWithWildcard(moduleSpecifierWithWildcard);
		return result;
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

	private static List<String> asStringsInArrayOrEmpty(JSONValue jsonValue) {
		return asArrayElementsOrEmpty(jsonValue).stream()
				.map(ProjectDescriptionUtils::asStringOrNull)
				.filter(pref -> pref != null)
				.collect(Collectors.toList());
	}

	private static String asStringOrNull(JSONValue jsonValue) {
		return jsonValue instanceof JSONStringLiteral ? ((JSONStringLiteral) jsonValue).getValue() : null;
	}

	private static List<JSONValue> asArrayElementsOrEmpty(JSONValue jsonValue) {
		return jsonValue instanceof JSONArray ? ((JSONArray) jsonValue).getElements() : Collections.emptyList();
	}

	private static List<NameValuePair> asNameValuePairsOrEmpty(JSONValue jsonValue) {
		return jsonValue instanceof JSONObject ? ((JSONObject) jsonValue).getNameValuePairs() : Collections.emptyList();
	}

	private ProjectDescriptionUtils() {
		// non-instantiable utility class
	}
}
