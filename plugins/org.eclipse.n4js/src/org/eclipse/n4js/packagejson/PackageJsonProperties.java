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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.projectDescription.ModuleLoader;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;

/**
 * Constants for dealing with package.json files on the generic JSON level. Try to avoid this by using
 * {@link ProjectDescription}s instead.
 */
public enum PackageJsonProperties {

	/** Key of package.json property "name". */
	NAME("name"),
	/** Key of package.json property "version". */
	VERSION("version", "0.0.1"),
	/** Key of package.json property "dependencies". */
	DEPENDENCIES("dependencies"),
	/** Key of package.json property "devDependences". */
	DEV_DEPENDENCIES("devDependencies"),
	/** Key of package.json property "main". */
	MAIN("main"),
	/** Key of package.json property "n4js". */
	N4JS("n4js"),

	// properties in section "n4js":
	/** Key of package.json property "projectType". */
	PROJECT_TYPE("projectType", PackageJsonProperties.N4JS),
	/** Key of package.json property "vendorId". */
	VENDOR_ID("vendorId", "vendor.default", PackageJsonProperties.N4JS),
	/** Key of package.json property "vendorName". */
	VENDOR_NAME("vendorName", PackageJsonProperties.N4JS),
	/** Key of package.json property "output". */
	OUTPUT("output", ".", PackageJsonProperties.N4JS),
	/** Key of package.json property "sources". */
	SOURCES("sources", PackageJsonProperties.N4JS),
	/** Key of package.json property "moduleFilters". */
	MODULE_FILTERS("moduleFilters", PackageJsonProperties.N4JS),
	/** Key of package.json property "mainModule". */
	MAIN_MODULE("mainModule", "index", PackageJsonProperties.N4JS),
	/** Key of package.json property "testedProjects". */
	TESTED_PROJECTS("testedProjects", PackageJsonProperties.N4JS),
	/** Key of package.json property "implementationId". */
	IMPLEMENTATION_ID("implementationId", PackageJsonProperties.N4JS),
	/** Key of package.json property "implementedProjects". */
	IMPLEMENTED_PROJECTS("implementedProjects", PackageJsonProperties.N4JS),
	/** Key of package.json property "extendedRuntimeEnvironment". */
	EXTENDED_RUNTIME_ENVIRONMENT("extendedRuntimeEnvironment", PackageJsonProperties.N4JS),
	/** Key of package.json property "providedRuntimeLibraries". */
	PROVIDED_RUNTIME_LIBRARIES("providedRuntimeLibraries", PackageJsonProperties.N4JS),
	/** Key of package.json property "requiredRuntimeLibraries". */
	REQUIRED_RUNTIME_LIBRARIES("requiredRuntimeLibraries", PackageJsonProperties.N4JS),
	/** Key of package.json property "moduleLoader". */
	MODULE_LOADER("moduleLoader", ModuleLoader.N4JS.name(), PackageJsonProperties.N4JS),
	/** Key of package.json property "initModules". */
	INIT_MODULES("initModules", PackageJsonProperties.N4JS),
	/** Key of package.json property "execModule". */
	EXEC_MODULE("execModule", PackageJsonProperties.N4JS),
	/** Key of package.json property "definesPackage". */
	DEFINES_PACKAGE("definesPackage", PackageJsonProperties.N4JS),

	/** Key of package.json property "noValidate". */
	NO_VALIDATE("noValidate", PackageJsonProperties.N4JS),
	/** Key of package.json property "noValidate". */
	NO_MODULE_WRAP("noModuleWrap", PackageJsonProperties.N4JS),
	/** Key of package.json property "sourceContainer" of property "noValidate". */
	NV_SOURCE_CONTAINER("sourceContainer", PackageJsonProperties.N4JS, PackageJsonProperties.NO_VALIDATE),
	/** Key of package.json property "module" of property "noValidate". */
	NV_MODULE("module", PackageJsonProperties.N4JS, PackageJsonProperties.NO_VALIDATE),
	/** Key of package.json property "sourceContainer" of property "noModuleWrap". */
	NMW_SOURCE_CONTAINER("sourceContainer", PackageJsonProperties.N4JS, PackageJsonProperties.NO_MODULE_WRAP),
	/** Key of package.json property "module" of property "noModuleWrap". */
	NMW_MODULE("module", PackageJsonProperties.N4JS, PackageJsonProperties.NO_MODULE_WRAP);

	/** Default for property "moduleLoader" for *other* project types than {@link ProjectType#VALIDATION VALIDATION}. */
	public static final ModuleLoader DEFAULT_MODULE_LOADER = ModuleLoader.N4JS;
	/** Default for property "moduleLoader" for project type {@link ProjectType#VALIDATION VALIDATION}. */
	public static final ModuleLoader DEFAULT_MODULE_LOADER_FOR_VALIDATION = ModuleLoader.COMMONJS;

	/** section of the property within the package.json */
	final public PackageJsonProperties[] parents;
	/** name of the property */
	final public String name;
	/** default value of the property if the property is missing or null */
	final public String defaultValue;

	private PackageJsonProperties(String label, PackageJsonProperties... parents) {
		this(label, null, parents);
	}

	private PackageJsonProperties(String name, String defaultValue, PackageJsonProperties... parents) {
		this.parents = parents;
		this.name = name;
		this.defaultValue = defaultValue;
	}

	/**
	 * @return all enum values of {@link PackageJsonProperties} which are direct children of
	 *         {@link PackageJsonProperties#N4JS}
	 */
	static public Collection<PackageJsonProperties> getAllN4JSProperties() {
		List<PackageJsonProperties> n4jsProps = new LinkedList<>();
		for (PackageJsonProperties value : PackageJsonProperties.values()) {
			if (value.parents.length == 1 && value.parents[0] == PackageJsonProperties.N4JS) {
				n4jsProps.add(value);
			}
		}
		return n4jsProps;
	}

	/** @return a dot separated path of property names in the package.json */
	public String getPath() {
		String path = "";
		for (PackageJsonProperties parent : parents) {
			path += parent.name;
			if (!path.isEmpty()) {
				path += ".";
			}
		}
		path += name;
		return path;
	}

}
