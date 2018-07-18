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

import org.eclipse.n4js.n4mf.ModuleLoader;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectType;

/**
 * Constants for dealing with package.json files on the generic JSON level. Try to avoid this by using
 * {@link ProjectDescription}s instead.
 */
public class PackageJsonConstants {

	// root-level properties:

	/** Default value for the property "version". */
	public static final String DEFAULT_VERSION = "0.0.1";
	/** Default value for the property "vendorId". */
	public static final String DEFAULT_VENDOR_ID = "vendor.default";
	/** Default value for the property "mainModule". */
	public static final String DEFAULT_MAIN_MODULE = "index";
	/** Default value for the property "output". */
	public static final String DEFAULT_OUTPUT = ".";
	/** Default for property "moduleLoader" for *other* project types than {@link ProjectType#VALIDATION VALIDATION}. */
	public static final ModuleLoader DEFAULT_MODULE_LOADER = ModuleLoader.N4JS;
	/** Default for property "moduleLoader" for project type {@link ProjectType#VALIDATION VALIDATION}. */
	public static final ModuleLoader DEFAULT_MODULE_LOADER_FOR_VALIDATION = ModuleLoader.COMMONJS;

	/** Key of package.json property "name". */
	public static final String PROP__NAME = "name";
	/** Key of package.json property "version". */
	public static final String PROP__VERSION = "version";
	/** Key of package.json property "dependencies". */
	public static final String PROP__DEPENDENCIES = "dependencies";
	/** Key of package.json property "devDependences". */
	public static final String PROP__DEV_DEPENDENCIES = "devDependencies";
	/** Key of package.json property "main". */
	public static final String PROP__MAIN = "main";
	/** Key of package.json property "n4js". */
	public static final String PROP__N4JS = "n4js";

	// properties in section "n4js":

	/** Key of package.json property "projectType". */
	public static final String PROP__PROJECT_TYPE = "projectType";
	/** Key of package.json property "vendorId". */
	public static final String PROP__VENDOR_ID = "vendorId";
	/** Key of package.json property "vendorName". */
	public static final String PROP__VENDOR_NAME = "vendorName";
	/** Key of package.json property "output". */
	public static final String PROP__OUTPUT = "output";
	/** Key of package.json property "sources". */
	public static final String PROP__SOURCES = "sources";
	/** Key of package.json property "moduleFilters". */
	public static final String PROP__MODULE_FILTERS = "moduleFilters";
	/** Key of package.json property "mainModule". */
	public static final String PROP__MAIN_MODULE = "mainModule";
	/** Key of package.json property "testedProjects". */
	public static final String PROP__TESTED_PROJECTS = "testedProjects";
	/** Key of package.json property "implementationId". */
	public static final String PROP__IMPLEMENTATION_ID = "implementationId";
	/** Key of package.json property "implementedProjects". */
	public static final String PROP__IMPLEMENTED_PROJECTS = "implementedProjects";
	/** Key of package.json property "extendedRuntimeEnvironment". */
	public static final String PROP__EXTENDED_RUNTIME_ENVIRONMENT = "extendedRuntimeEnvironment";
	/** Key of package.json property "providedRuntimeLibraries". */
	public static final String PROP__PROVIDED_RUNTIME_LIBRARIES = "providedRuntimeLibraries";
	/** Key of package.json property "requiredRuntimeLibraries". */
	public static final String PROP__REQUIRED_RUNTIME_LIBRARIES = "requiredRuntimeLibraries";
	/** Key of package.json property "moduleLoader". */
	public static final String PROP__MODULE_LOADER = "moduleLoader";
	/** Key of package.json property "initModules". */
	public static final String PROP__INIT_MODULES = "initModules";
	/** Key of package.json property "execModule". */
	public static final String PROP__EXEC_MODULE = "execModule";

	// properties of module filter specifiers:

	/** Key of package.json property "sourceContainer". */
	public static final String PROP__SOURCE_CONTAINER = "sourceContainer";
	/** Key of package.json property "module". */
	public static final String PROP__MODULE = "module";
}
