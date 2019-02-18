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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.projectDescription.ModuleLoader;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;

import com.google.common.collect.Lists;

/**
 * Constants for dealing with package.json files on the generic JSON level. Try to avoid this by using
 * {@link ProjectDescription}s instead.
 */
public enum PackageJsonProperties {

	/** Key of package.json property "name". */
	NAME("name", "Npm name"),
	/** Key of package.json property "version". */
	VERSION("version", "Npm semver version", "0.0.1"),
	/** Key of package.json property "dependencies". */
	DEPENDENCIES("dependencies", "Dependencies of this npm", JSONObject.class),
	/** Key of package.json property "devDependences". */
	DEV_DEPENDENCIES("devDependencies", "Development dependencies of this npm", JSONObject.class),
	/** Key of package.json property "main". */
	MAIN("main", "Main module. Path is relative to package root"),
	/** Key of package.json property "n4js". */
	N4JS("n4js", "N4JS section", JSONObject.class),

	// properties of other tools:
	/** Key of package.json property used by yarn workspace concept denoting projects contained in the workspace. */
	WORKSPACES("workspaces", "Projects that are members of the yarn workspace", JSONArray.class),

	// properties in section "n4js":
	/** Key of package.json property "projectType". */
	PROJECT_TYPE("projectType", ProjectType.PLAINJS.getLiteral().toLowerCase(), N4JS),
	/** Key of package.json property "vendorId". */
	VENDOR_ID("vendorId", "", "vendor.default", N4JS),
	/** Key of package.json property "vendorName". */
	VENDOR_NAME("vendorName", "", N4JS),
	/** Key of package.json property "output". */
	OUTPUT("output", "Output folder. Default is '.'", ".", N4JS),
	/** Key of package.json property "sources". */
	SOURCES("sources", "Source folders", JSONObject.class, N4JS),
	/** Key of package.json property "moduleFilters". */
	MODULE_FILTERS("moduleFilters", "", JSONObject.class, N4JS),
	/** Key of package.json property "mainModule". */
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
	/** Key of package.json property "moduleLoader". */
	MODULE_LOADER("moduleLoader", "", ModuleLoader.N4JS.name(), N4JS),
	/** Key of package.json property "initModules". */
	INIT_MODULES("initModules", "", JSONArray.class, N4JS),
	/** Key of package.json property "execModule". */
	EXEC_MODULE("execModule", "", N4JS),
	/** Key of package.json property "definesPackage". */
	DEFINES_PACKAGE("definesPackage", "", N4JS),

	/** Key of package.json property "noValidate". */
	NO_VALIDATE("noValidate", "", JSONObject.class, N4JS),
	/** Key of package.json property "noValidate". */
	NO_MODULE_WRAP("noModuleWrap", "", JSONObject.class, N4JS),
	/** Key of package.json property "sourceContainer" of property "noValidate". */
	NV_SOURCE_CONTAINER("sourceContainer", "", JSONObject.class, N4JS, NO_VALIDATE),
	/** Key of package.json property "module" of property "noValidate". */
	NV_MODULE("module", "", N4JS, NO_VALIDATE),
	/** Key of package.json property "sourceContainer" of property "noModuleWrap". */
	NMW_SOURCE_CONTAINER("sourceContainer", "", JSONObject.class, N4JS, NO_MODULE_WRAP),
	/** Key of package.json property "module" of property "noModuleWrap". */
	NMW_MODULE("module", "", N4JS, NO_MODULE_WRAP),

	/** Key of package.json property "source" inside "sources". */
	SOURCE("source", "List of source folders", JSONArray.class, N4JS, SOURCES);

	/**
	 * Default for property "moduleLoader" for *other* project types than {@link ProjectType#VALIDATION VALIDATION} and
	 * {@link ProjectType#PLAINJS PLAINJS}.
	 */
	public static final ModuleLoader DEFAULT_MODULE_LOADER = ModuleLoader.N4JS;
	/** Default for property "moduleLoader" for project type {@link ProjectType#VALIDATION VALIDATION}. */
	public static final ModuleLoader DEFAULT_MODULE_LOADER_FOR_PLAINJS_AND_VALIDATION = ModuleLoader.COMMONJS;

	/** section of the property within the package.json */
	final public PackageJsonProperties[] parents;
	/** name of the property */
	final public String name;
	/** description of the property */
	final public String description;
	/** default value of the property if the property is missing or null */
	final public String defaultValue;
	/** json value type of the property */
	final public Class<? extends JSONValue> valueType;

	private PackageJsonProperties(String name, String description, PackageJsonProperties... parents) {
		this(name, description, JSONStringLiteral.class, null, parents);
	}

	private PackageJsonProperties(String name, String description, String defaultValue,
			PackageJsonProperties... parents) {

		this(name, description, JSONStringLiteral.class, defaultValue, parents);
	}

	private PackageJsonProperties(String name, String description, Class<? extends JSONValue> valueType,
			PackageJsonProperties... parents) {

		this(name, description, valueType, null, parents);
	}

	private PackageJsonProperties(String name, String description, Class<? extends JSONValue> valueType,
			String defaultValue, PackageJsonProperties... parents) {

		this.parents = parents;
		this.name = name;
		this.description = description;
		this.defaultValue = defaultValue;
		this.valueType = valueType;
	}

	static private Map<String, PackageJsonProperties> nameToEnum = new HashMap<>();
	static {
		for (PackageJsonProperties prop : PackageJsonProperties.values()) {
			if (nameToEnum.containsKey(prop.name)) {
				nameToEnum.put(prop.name, null); // ensure that props with the same name return null here
			} else {
				nameToEnum.put(prop.name, prop);
			}
		}
	}

	/** @return the result of {@link Enum#valueOf(Class, String)} or null. Does not throw an {@link Exception}. */
	static public PackageJsonProperties valueOfNameOrNull(String name) {
		return nameToEnum.getOrDefault(name, null);
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
