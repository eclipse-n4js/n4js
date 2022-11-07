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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.utils.UtilN4;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Constants for dealing with package.json files on the generic JSON level. Try to avoid this by using
 * {@link ProjectDescription}s instead.
 */
public enum PackageJsonProperties {

	// NPM properties
	/** Key of package.json property "name". */
	NAME("name", "Npm name"),
	/** Key of package.json property "version". */
	VERSION("version", "Npm semver version", "0.0.1"),
	/** Key of package.json property "private". */
	PRIVATE("private", "Whether npm should refuse to publish this package.", JSONBooleanLiteral.class),
	/** Key of package.json property "type". */
	TYPE("type", "The module format used for .js files in this project. Either 'commonjs' or 'module'."),
	/** Key of package.json property "dependencies". */
	DEPENDENCIES(UtilN4.PACKAGE_JSON__DEPENDENCIES, "Dependencies of this npm", JSONObject.class),
	/** Key of package.json property "devDependences". */
	DEV_DEPENDENCIES(UtilN4.PACKAGE_JSON__DEV_DEPENDENCIES, "Development dependencies of this npm", JSONObject.class),
	/** Key of node's standard, top-level package.json property "main". Do not confuse with {@link #MAIN_MODULE}. */
	// default see https://docs.npmjs.com/cli/v8/configuring-npm/package-json#main
	MAIN("main", "Main module. Path is relative to package root", "index"),
	/** Key of TypeScript standard, top-level package.json property "types". */
	TYPES("types",
			"Type module. (TypesScript) Path is relative to package root. Enabled only when using project import."),
	/** Key of TypeScript standard, top-level package.json property "types". */
	TYPES_VERSIONS("typesVersions",
			"Defines source paths that contain type definition modules for specific TypeScript versions.",
			JSONObject.class),
	/** Key of top-level package.json property "module", used by webpack and other tools. */
	MODULE("module", "Like \"main\" but provides a different file with esm code"),
	/** Key of package.json property used to define entry point definitions. */
	EXPORTS("exports", "Entry point definitions", JSONObject.class),
	/** Key of package.json property used to define entry point definitions. */
	EXPORTS_PATH(".", true, "Relative path", JSONObject.class, ".", EXPORTS),
	/** Key of package.json property used to define the entry point for TypeScript type definitions. */
	EXPORTS_TYPES("types", "Entry-point for TypeScript type definitions", EXPORTS, EXPORTS_PATH),
	/** Key of package.json property used to define the entry point for ESM modules. */
	EXPORTS_IMPORT("import", "Entry-point for ESM modules", "index", EXPORTS, EXPORTS_PATH),
	/** Key of package.json property used by webpack and other tools. */
	EXPORTS_MODULE("module", "Like \"main\" but provides a different file with esm code", EXPORTS, EXPORTS_PATH),

	// Yarn properties
	/**
	 * Short version of {@link #WORKSPACES_OBJECT}. @see {@link #PACKAGES}.
	 */
	WORKSPACES_ARRAY("workspaces", "Array of projects names or glob that are members of the yarn workspace",
			JSONArray.class, "[packages/*]"),
	/** Key of package.json property used by yarn workspace concept to define the workspace. */
	WORKSPACES_OBJECT("workspaces", "Projects definition of the yarn workspace", JSONObject.class),
	/**
	 * Key of package.json property used by yarn workspace concept denoting projects Array of projects names or glob
	 * contained in the workspace.
	 */
	PACKAGES("packages", "Array of projects names or glob that are members of the yarn workspace", JSONArray.class,
			"[packages/*]", WORKSPACES_OBJECT),
	/** Key of package.json property used by yarn workspace concept denoting projects contained in the workspace. */
	NOHOIST("nohoist", "Array of project names or glob that will not be hoisted by yarn", JSONArray.class,
			"[packages/*]", WORKSPACES_OBJECT),

	// N4JS properties
	/** Key of package.json property "n4js". */
	N4JS(UtilN4.PACKAGE_JSON__N4JS, "N4JS section", JSONObject.class),
	/** Key of package.json property "projectType". */
	PROJECT_TYPE("projectType", "project type",
			PackageJsonUtils.getProjectTypeStringRepresentation(ProjectType.PLAINJS), N4JS),
	/** Key of package.json property "vendorId". */
	VENDOR_ID("vendorId", "", "vendor.default", N4JS),
	/** Key of package.json property "vendorName". */
	VENDOR_NAME("vendorName", "", N4JS),
	/** Key of package.json property "output". */
	OUTPUT("output", "Output folder. Default is '.'", ".", N4JS),
	/** Key of package.json property "sources". */
	SOURCES(UtilN4.PACKAGE_JSON__SOURCES, "Source folders", JSONObject.class, N4JS),
	/** Key of package.json property "moduleFilters". */
	MODULE_FILTERS("moduleFilters", "", JSONObject.class, N4JS),
	/** Key of the N4JS-specific package.json property "mainModule". Do not confuse with {@link #MAIN}. */
	MAIN_MODULE("mainModule", "Main module specifier. Starts from source folder(s)", "index", N4JS),
	/** Key of package.json property "testedProjects". */
	TESTED_PROJECTS("testedProjects", "Projects that are tested by this project", JSONArray.class, N4JS),
	/** Key of package.json property "implementationId". */
	IMPLEMENTATION_ID("implementationId", "", N4JS),
	/** Key of package.json property "implementedProjects". */
	IMPLEMENTED_PROJECTS("implementedProjects", "", JSONArray.class, N4JS),
	/** Key of package.json property "extendedRuntimeEnvironment". */
	EXTENDED_RUNTIME_ENVIRONMENT("extendedRuntimeEnvironment", "", N4JS),
	/** Key of package.json property "providedRuntimeLibraries". */
	PROVIDED_RUNTIME_LIBRARIES("providedRuntimeLibraries", "", JSONArray.class, N4JS),
	/** Key of package.json property "requiredRuntimeLibraries". */
	REQUIRED_RUNTIME_LIBRARIES("requiredRuntimeLibraries", "", JSONArray.class, N4JS),
	/** Key of package.json property "definesPackage". */
	DEFINES_PACKAGE("definesPackage", "", N4JS),

	/** Key of package.json property "noValidate". */
	NO_VALIDATE("noValidate", "", JSONObject.class, N4JS),
	/** Key of package.json property "sourceContainer" of property "noValidate". */
	NV_SOURCE_CONTAINER("sourceContainer", "", JSONObject.class, N4JS, NO_VALIDATE),
	/** Key of package.json property "module" of property "noValidate". */
	NV_MODULE("module", "", N4JS, NO_VALIDATE),

	/** Key of package.json property "source" inside "sources". */
	SOURCE("source", "List of source folders", JSONArray.class, N4JS, SOURCES),
	/** Key of package.json property "test" inside "sources". */
	TEST("test", "List of source folders for tests", JSONArray.class, N4JS, SOURCES),

	/** Key of package.json property "generator". */
	GENERATOR("generator", "Configurations for the generator", JSONObject.class, N4JS),
	/** Key of package.json property "generator"/"source-maps". */
	GENERATOR_SOURCE_MAPS("source-maps", "Turn on/off generation of source maps", JSONBooleanLiteral.class, true,
			N4JS, GENERATOR),
	/** Key of package.json property "generator"/"d.ts". */
	GENERATOR_DTS("d.ts", "Turn on/off generation of d.ts files", JSONBooleanLiteral.class, false, N4JS, GENERATOR),
	/** Key of package.json property "generator"/"rewriteModuleSpecifiers". */
	GENERATOR_REWRITE_MODULE_SPECIFIERS("rewriteModuleSpecifiers",
			"Defines how certain module specifiers should be emitted to the output code",
			JSONObject.class, N4JS, GENERATOR),
	/** Key of package.json property "generator"/"rewriteCjsImports". */
	GENERATOR_REWRITE_CJS_IMPORTS("rewriteCjsImports",
			"Emit all imports from a CJS module as default imports with destructuring",
			JSONBooleanLiteral.class, false, N4JS, GENERATOR),

	;

	/** section of the property within the package.json as a path of parents starting at the top level */
	final public PackageJsonProperties[] parents;
	/** all direct children of this property */
	final public List<PackageJsonProperties> children = new ArrayList<>();
	/** name of the property */
	final public String name;
	/** true iff this property name can have any postfix */
	final public boolean anyPostfix;
	/** description of the property */
	final public String description;
	/** default value of the property if the property is missing or null */
	final public Object defaultValue;
	/** json value type of the property */
	final public Class<? extends JSONValue> valueType;

	private PackageJsonProperties(String name, String description, PackageJsonProperties... parents) {
		this(name, false, description, JSONStringLiteral.class, null, parents);
	}

	private PackageJsonProperties(String name, String description, String defaultValue,
			PackageJsonProperties... parents) {

		this(name, false, description, JSONStringLiteral.class, defaultValue, parents);
	}

	private PackageJsonProperties(String name, String description, Class<? extends JSONValue> valueType,
			PackageJsonProperties... parents) {

		this(name, false, description, valueType, null, parents);
	}

	private PackageJsonProperties(String name, String description,
			Class<? extends JSONValue> valueType,
			Object defaultValue, PackageJsonProperties... parents) {

		this(name, false, description, valueType, defaultValue, parents);
	}

	private PackageJsonProperties(String name, boolean anyPostfix, String description,
			Class<? extends JSONValue> valueType,
			Object defaultValue, PackageJsonProperties... parents) {

		this.parents = parents;
		this.name = name;
		this.anyPostfix = anyPostfix;
		this.description = description;
		this.defaultValue = defaultValue;
		this.valueType = valueType;
		if (parents != null && parents.length > 0) {
			parents[parents.length - 1].children.add(this);
		}
	}

	static private Map<List<String>, Map<Class<? extends JSONValue>, PackageJsonProperties>> pathToEnum = new HashMap<>();

	static {
		for (PackageJsonProperties prop : PackageJsonProperties.values()) {
			List<String> path = ImmutableList.copyOf(prop.getPathElements());
			if (!pathToEnum.containsKey(path)) {
				pathToEnum.put(path, new HashMap<>());
			}
			Map<Class<? extends JSONValue>, PackageJsonProperties> typeMap = pathToEnum.get(path);
			if (typeMap.containsKey(prop.valueType)) {
				throw new IllegalStateException("found multiple properties for the same path and type: " + path + ", "
						+ prop.valueType.getName());
			}
			typeMap.put(prop.valueType, prop);
		}
	}

	/** @return the result of {@link Enum#valueOf(Class, String)} or null. Does not throw an {@link Exception}. */
	static public PackageJsonProperties valueOfNameValuePairOrNull(NameValuePair nvPair) {
		if (nvPair.getName() == null || nvPair.getValue() == null) {
			return null; // syntax error in JSON file
		}
		List<String> path = JSONModelUtils.getPathToNameValuePairOrNull(nvPair);
		path = rectifyPathNames(path); // not supported at the moment

		Map<Class<? extends JSONValue>, PackageJsonProperties> typeMap = pathToEnum.get(path);
		if (typeMap != null) {
			Class<? extends JSONValue> valueClass = nvPair.getValue().getClass();
			if (valueClass != null) {
				if (valueClass.isInterface()) {
					return typeMap.get(valueClass);
				} else if (valueClass.getInterfaces().length > 0) {
					Class<?>[] valueInterfaces = valueClass.getInterfaces();
					return typeMap.get(valueInterfaces[0]);
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private static List<String> rectifyPathNames(List<String> path) {
		if (!pathToEnum.containsKey(path)) {
			// replace placeholders
			for (int idx = 1; idx < path.size(); idx++) {
				Map<Class<? extends JSONValue>, PackageJsonProperties> subPath = pathToEnum.get(path.subList(0, idx));
				if (subPath == null) {
					subPath = pathToEnum.get(path.subList(0, idx - 1));
					if (subPath == null) {
						return path; // can't help it
					}

					// find correct property
					String pathParentName = path.get(idx - 1);
					OUTER_LOOP: for (PackageJsonProperties prop : subPath.values()) {
						for (PackageJsonProperties child : prop.children) {

							if (child.anyPostfix && pathParentName.startsWith(child.name)) {
								path.set(idx - 1, child.name); // rectify name
								break OUTER_LOOP;
							}
						}
					}
				}
			}
		}
		return path;
	}

	/**
	 * @return all enum values of {@link PackageJsonProperties} which are direct children of
	 *         {@link PackageJsonProperties#N4JS}
	 */
	static public Set<String> getAllN4JSPropertyNames() {
		Set<String> n4jsPropNames = new HashSet<>();
		List<PackageJsonProperties> n4jsProps = valuesOfPath(Lists.newArrayList(PackageJsonProperties.N4JS.name));
		for (PackageJsonProperties value : n4jsProps) {
			n4jsPropNames.add(value.name);
		}
		return n4jsPropNames;
	}

	/** @return a dot separated path of property names in the package.json */
	public String[] getPathElements() {
		String[] pathElements = new String[parents.length + 1];
		for (int i = 0; i < parents.length; i++) {
			pathElements[i] = parents[i].name;
		}
		pathElements[parents.length] = name;

		return pathElements;
	}

	/** @return a dot separated path of property names in the package.json */
	public String getPath() {
		return String.join(".", getPathElements());
	}

	/**
	 * @param namePath
	 *            path of nested names
	 * @return all {@link PackageJsonProperties} whose {@link #getPathElements()} is equal to the given argument
	 */
	static public List<PackageJsonProperties> valuesOfPath(List<String> namePath) {
		List<PackageJsonProperties> resultList = new LinkedList<>();
		String[] npArr = namePath.toArray(new String[namePath.size()]);
		String pathString = String.join(".", npArr);
		for (PackageJsonProperties pjp : PackageJsonProperties.values()) {
			String pjpPathString = joinPackageJsonProperties(".", pjp);
			if (pathString.equals(pjpPathString)) {
				resultList.add(pjp);
			}
		}
		return resultList;
	}

	private static String joinPackageJsonProperties(String delimiter, PackageJsonProperties pjp) {
		String pjpPathString = "";
		for (PackageJsonProperties parent : pjp.parents) {
			if (pjpPathString.length() > 0) {
				pjpPathString += delimiter;
			}
			pjpPathString += parent.name;
		}
		return pjpPathString;
	}

}
