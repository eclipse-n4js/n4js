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

import static org.eclipse.n4js.internal.N4JSModel.DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNameValuePairsOrEmpty;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asStringOrNull;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.DEFAULT_VALUE_OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.DEFAULT_VALUE_VERSION;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__DEV_DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__EXEC_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__EXTENDED_RUNTIME_ENVIRONMENT;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__IMPLEMENTATION_ID;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__IMPLEMENTED_PROJECTS;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__INIT_MODULES;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__MAIN;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__MAIN_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__MODULE_FILTERS;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__MODULE_LOADER;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__N4JS;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__NAME;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__PROJECT_TYPE;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__PROVIDED_RUNTIME_LIBRARIES;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__REQUIRED_RUNTIME_LIBRARIES;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__SOURCES;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__TESTED_PROJECTS;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__VENDOR_ID;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__VENDOR_NAME;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.PROP__VERSION;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asBootstrapModuleOrNull;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asBootstrapModulesInArrayOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asModuleFiltersInObjectOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asProjectReferenceOrNull;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asProjectReferencesInArrayOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asSourceContainerDescriptionsOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.parseModuleLoader;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.parseProjectType;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.n4mf.ModuleLoader;
import org.eclipse.n4js.n4mf.N4mfFactory;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.semver.SEMVERHelper;
import org.eclipse.n4js.semver.SEMVER.VersionNumber;
import org.eclipse.n4js.semver.SEMVER.VersionRangeSet;
import org.eclipse.xtext.resource.XtextResourceSet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * Helper class for loading a {@link ProjectDescription} from disk, optionally also loading and merging additional
 * information from a {@code package-fragment.json} (when passing <code>true</code> as last argument to
 * {@link #loadProjectDescriptionAtLocation(URI, JSONDocument, boolean)}).
 */
@Singleton
public class ProjectDescriptionHelper {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	private SEMVERHelper semverHelper;

	/**
	 * Loads the project description of the N4JS project at the given {@code location}.
	 * <p>
	 * Returns {@code null} if the project description cannot be loaded successfully (e.g. missing package.json and
	 * manifest.n4mf file).
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(URI location) {
		JSONDocument packageJSON = loadPackageJSONAtLocation(location);
		if (packageJSON == null) {
			return null;
		}
		return loadProjectDescriptionAtLocation(location, packageJSON, true);
	}

	/**
	 * Same as {@link #loadPackageJSONAtLocation(URI)}, but for cases in which the JSONDocument of the main package.json
	 * file (but not the fragment) has already been loaded.
	 *
	 * @param mergeFragment
	 *            if <code>true</code>, a {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON package.json fragment} will be loaded
	 *            and merged into the given package.json document before conversion to {@link ProjectDescription} (given
	 *            document will *not* be changed).
	 */
	public ProjectDescription loadProjectDescriptionAtLocation(URI location, JSONDocument packageJSON,
			boolean mergeFragment) {
		if (mergeFragment) {
			packageJSON = EcoreUtil.copy(packageJSON);
			mergePackageJSONFragmentAtLocation(location, packageJSON);
		}
		adjustMainPath(location, packageJSON);
		ProjectDescription pdFromPackageJSON = packageJSON != null
				? convertToProjectDescription(location, packageJSON, true)
				: null;
		if (pdFromPackageJSON != null) {
			setInformationFromFileSystem(location, pdFromPackageJSON);
			return pdFromPackageJSON;
		} else {
			return null;
		}
	}

	/**
	 * Loads the project description defined in a {@link N4JSGlobals#PACKAGE_FRAGMENT_JSON package.json fragment} at the
	 * given location or <code>null</code> if no fragment is found at this location.
	 */
	public ProjectDescription loadProjectDescriptionFragmentAtLocation(URI location) {
		JSONDocument packageJSON = JSONFactory.eINSTANCE.createJSONDocument();
		if (mergePackageJSONFragmentAtLocation(location, packageJSON)) {
			adjustMainPath(location, packageJSON);
			ProjectDescription pd = convertToProjectDescription(location, packageJSON, false);
			setInformationFromFileSystem(location, pd);
			return pd;
		}
		return null;
	}

	/**
	 * Loads the project description of the N4JS project at the given {@code location} and returns the version string or
	 * <code>null</code> if undefined or in case of error.
	 */
	public String loadVersionFromProjectDescriptionAtLocation(URI location) {
		JSONDocument packageJSON = loadPackageJSONAtLocation(location);
		if (packageJSON == null) {
			return null;
		}
		JSONValue versionValue = JSONModelUtils.getProperty(packageJSON, PROP__VERSION).orElse(null);
		return asStringOrNull(versionValue);
	}

	/**
	 * Adjust the path value of the "main" property of the given package.json document as follows (in-place change of
	 * the given JSON document):
	 * <ol>
	 * <li>if the path points to a folder, then "/index.js" will be appended,
	 * <li>if neither a folder nor a file exist at the location the path points to and the path does not end in ".js",
	 * then ".js" will be appended.
	 * </ol>
	 */
	private void adjustMainPath(URI location, JSONDocument packageJSON) {
		JSONValue content = packageJSON.getContent();
		if (!(content instanceof JSONObject))
			return;
		JSONObject contentCasted = (JSONObject) content;
		String main = asStringOrNull(JSONModelUtils.getProperty(contentCasted, PROP__MAIN).orElse(null));
		if (main == null) {
			return;
		}
		String pattern = File.separatorChar != '/'
				? Pattern.quote(File.separator) + "|" + Pattern.quote("/")
				: Pattern.quote("/");
		String[] mainSegments = main.split(pattern, -1);
		URI locationWithMain = location.appendSegments(mainSegments);

		final ResourceSet resourceSet = resourceSetProvider.get();

		if (!main.endsWith(".js") && isFile(resourceSet, locationWithMain.appendFileExtension("js"))) {
			main += ".js";
			JSONModelUtils.setProperty(contentCasted, PROP__MAIN, main);
		} else if (isDirectory(resourceSet, locationWithMain)) {
			if (!(main.endsWith("/") || main.endsWith(File.separator))) {
				main += "/";
			}
			main += "index.js";
			JSONModelUtils.setProperty(contentCasted, PROP__MAIN, main);
		}
	}

	/**
	 * Store some ancillary information about the state of the file system at the location of the
	 * <code>package.json</code> file in the given JSON document.
	 */
	private void setInformationFromFileSystem(URI location, ProjectDescription pd) {
		final ResourceSet resourceSet = resourceSetProvider.get();
		final boolean hasNestedNodeModulesFolder = exists(resourceSet,
				location.appendSegment(N4JSGlobals.NODE_MODULES));
		pd.setHasNestedNodeModulesFolder(hasNestedNodeModulesFolder);
	}

	/**
	 * Apply default values to the given project description. This should be performed right after loading and
	 * converting the project description from JSON.
	 */
	private void applyDefaults(ProjectDescription pd, URI location) {
		// implementation note: we do not have to set the default for 'projectType' here,
		// because this default is already handled by EMF by defining VALIDATION as the
		// first literal of enum ProjectType in N4MF.xcore.
		if (pd.getProjectId() == null) {
			pd.setProjectId(location.lastSegment()); // name of folder containing the package.json file
		}
		if (pd.getProjectVersion() == null) {
			pd.setProjectVersion(parseVersion(DEFAULT_VALUE_VERSION));
		}
		if (pd.getVendorId() == null) {
			pd.setVendorId("vendor.default");
		}
		if (pd.getMainModule() == null) {
			pd.setMainModule("index");
		}
		if (pd.getOutputPathRaw() == null) {
			pd.setOutputPathRaw(DEFAULT_VALUE_OUTPUT);
		}
		// if no source containers are defined (no matter what type),
		// then add a default source container of type "source" with path "."
		Iterator<String> sourceContainerPaths = pd.getSourceContainers().stream()
				.flatMap(sc -> sc.getPathsRaw().stream()).iterator();
		if (!sourceContainerPaths.hasNext()) {
			SourceContainerDescription scd = pd.getSourceContainers().stream()
					.filter(sc -> sc.getSourceContainerType() == SourceContainerType.SOURCE)
					.findFirst().orElse(null);
			if (scd == null) {
				SourceContainerDescription scdNew = N4mfFactory.eINSTANCE.createSourceContainerDescription();
				scdNew.setSourceContainerType(SourceContainerType.SOURCE);
				scdNew.getPathsRaw().add(DEFAULT_VALUE_OUTPUT);
				pd.getSourceContainers().add(scdNew);
			} else if (scd.getPathsRaw().isEmpty()) {
				scd.getPathsRaw().add(DEFAULT_VALUE_OUTPUT);
			}
		}
		// module loader must be commonjs for VALIDATION projects
		if (pd.getProjectType() == ProjectType.VALIDATION) {
			pd.setModuleLoader(ModuleLoader.COMMONJS);
		}
	}

	private JSONDocument loadPackageJSONAtLocation(URI location) {
		JSONDocument packageJSON = loadXtextFileAtLocation(location, IN4JSProject.PACKAGE_JSON, JSONDocument.class);

		if (packageJSON == null) {
			packageJSON = loadXtextFileAtLocation(location,
					IN4JSProject.PACKAGE_JSON + DEFAULT_VALUE_OUTPUT + N4JSGlobals.XT_FILE_EXTENSION,
					JSONDocument.class);
		}

		return packageJSON;
	}

	/** This method will change the given 'targetPackageJSON' document in place. */
	private boolean mergePackageJSONFragmentAtLocation(URI location, JSONDocument targetPackageJSON) {
		JSONDocument fragment = loadXtextFileAtLocation(location, N4JSGlobals.PACKAGE_FRAGMENT_JSON,
				JSONDocument.class);
		if (fragment == null) {
			return false;
		}
		if (fragment.getContent() instanceof JSONObject) {
			// note: dependencies are special in that we not actually merge (i.e. add) those from the fragment but
			// instead replace the dependencies in target. Rationale: the library manager is not interested in the
			// dependencies of the original project, only in those defined in the fragment.
			JSONValue targetContent = targetPackageJSON.getContent();
			if (targetContent instanceof JSONObject) {
				JSONModelUtils.removeProperty((JSONObject) targetContent, PROP__DEPENDENCIES);
				JSONModelUtils.removeProperty((JSONObject) targetContent, PROP__DEV_DEPENDENCIES);
			}
			// merge properties from fragment into targetPackageJSON
			JSONModelUtils.merge(targetPackageJSON, fragment, false, true);
			return true;
		}
		return false;
	}

	private <T extends EObject> T loadXtextFileAtLocation(URI location, String name, Class<T> expectedTypeOfRoot) {
		final T result;
		if (location.isPlatformResource() && location.segmentCount() == DIRECT_RESOURCE_IN_PROJECT_SEGMENTCOUNT) {
			result = loadXtextFile(location.appendSegment(name), expectedTypeOfRoot);
		} else if (location.isFile()) {
			result = loadXtextFile(location.appendSegment(name), expectedTypeOfRoot);
		} else {
			// we only handle workspace and file-based cases
			return null;
		}
		return result;
	}

	private <T extends EObject> T loadXtextFile(URI uri, Class<T> expectedTypeOfRoot) {
		try {
			ResourceSet resourceSet = resourceSetProvider.get();

			// check whether a file exists at the given URI
			if (!exists(resourceSet, uri)) {
				return null;
			}

			Resource resource = resourceSet.getResource(uri, true);
			if (resource != null) {
				List<EObject> contents = resource.getContents();

				if (!contents.isEmpty()) {
					EObject root = contents.get(0);
					if (expectedTypeOfRoot.isInstance(root)) {
						@SuppressWarnings("unchecked")
						final T rootCasted = (T) root;
						contents.clear();
						return rootCasted;
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new WrappedException("failed to load Xtext file at " + uri, e);
		}
	}

	/**
	 * Checks whether {@code uri} points to a resource that actually exists on the file system.
	 *
	 * @param resourceSet
	 *            The resource set to use for the file system access.
	 * @param uri
	 *            The uri to check.
	 */
	private boolean exists(ResourceSet resourceSet, URI uri) {
		return resourceSet.getURIConverter().exists(uri, null);
	}

	/**
	 * Checks whether {@code uri} points to a directory on the file system.
	 *
	 * @param resourceSet
	 *            The resource set to use for the file system access.
	 * @param uri
	 *            The uri to check.
	 */
	private boolean isDirectory(ResourceSet resourceSet, URI uri) {
		final Map<String, ?> attributes = resourceSet.getURIConverter().getAttributes(uri, null);
		final boolean isDirectory = Objects.equals(attributes.get(URIConverter.ATTRIBUTE_DIRECTORY), Boolean.TRUE);
		return isDirectory;
	}

	private boolean isFile(ResourceSet resourceSet, URI uri) {
		return exists(resourceSet, uri) && !isDirectory(resourceSet, uri);
	}

	/**
	 * Transform the given {@code packageJSON} into an equivalent {@link ProjectDescription} instance.
	 * <p>
	 * Note: this methods does not implement the package.json feature that a "main" path may point to a folder and then
	 * a file "index.js" in that folder will be used as main module (for details see
	 * {@link ProjectDescriptionUtils#convertMainPathToModuleSpecifier(String, List)}).
	 */
	private ProjectDescription convertToProjectDescription(URI location, JSONDocument packageJSON,
			boolean applyDefaultValues) {
		JSONValue rootValue = packageJSON.getContent();
		if (rootValue instanceof JSONObject) {
			ProjectDescription result = N4mfFactory.eINSTANCE.createProjectDescription();
			List<NameValuePair> rootPairs = ((JSONObject) rootValue).getNameValuePairs();
			convertRootPairs(location, result, rootPairs, applyDefaultValues);
			return result;
		}
		return null;
	}

	private void convertRootPairs(URI location, ProjectDescription target, List<NameValuePair> rootPairs,
			boolean applyDefaultValues) {
		String valueOfTopLevelPropertyMain = null;
		for (NameValuePair pair : rootPairs) {
			String name = pair.getName();
			JSONValue value = pair.getValue();
			switch (name) {
			case PROP__NAME:
				target.setProjectId(asStringOrNull(value));
				break;
			case PROP__VERSION:
				target.setProjectVersion(parseVersion(asStringOrNull(value)));
				break;
			case PROP__DEPENDENCIES:
				convertDependencies(target, asNameValuePairsOrEmpty(value), true);
				break;
			case PROP__DEV_DEPENDENCIES:
				// for the moment, we do not separate devDependencies from ordinary dependencies in ProjectDescription
				convertDependencies(target, asNameValuePairsOrEmpty(value), true);
				break;
			case PROP__MAIN:
				// need to handle this value later after all source containers have been read (see below)
				valueOfTopLevelPropertyMain = asStringOrNull(value);
				break;
			case PROP__N4JS:
				// mark project with N4JS nature
				target.setHasN4JSNature(true);
				convertN4jsPairs(target, asNameValuePairsOrEmpty(value));
				break;
			}
		}
		// store whether target has a declared mainModule before applying the default values
		boolean hasN4jsSpecificMainModule = target.getMainModule() != null;

		// set default values
		if (applyDefaultValues) {
			applyDefaults(target, location);
		}
		// sanitize and set value of top-level property "main"
		// (note: this makes use of the source containers, so it possibly relies on default values having been applied)
		if (valueOfTopLevelPropertyMain != null) {
			if (!hasN4jsSpecificMainModule) { // only if no N4JS-specific "mainModule" property was given
				List<String> sourceContainerPaths = target.getSourceContainers().stream()
						.flatMap(scd -> scd.getPaths().stream())
						.collect(Collectors.toList());
				String mainModulePath = ProjectDescriptionUtils.convertMainPathToModuleSpecifier(
						valueOfTopLevelPropertyMain, sourceContainerPaths);
				if (mainModulePath != null) {
					target.setMainModule(mainModulePath);
				}
			}
		}
		// FIXME the following is a major hack!!!
		// sanitize dependencies: remove implementation projects from dependencies if API projects also given
		Set<String> projectIdsToRemove = target.getProjectDependencies().stream()
				.map(pd -> pd.getProjectId())
				.filter(id -> id.endsWith(".api"))
				.map(id -> id.substring(0, id.length() - 4))
				.collect(Collectors.toSet());
		target.getProjectDependencies().removeIf(pd -> projectIdsToRemove.contains(pd.getProjectId()));
	}

	private void convertN4jsPairs(ProjectDescription target, List<NameValuePair> n4jsPairs) {
		for (NameValuePair pair : n4jsPairs) {
			String name = pair.getName();
			JSONValue value = pair.getValue();
			switch (name) {
			case PROP__PROJECT_TYPE:
				// parseProjectType returns null if value is invalid, this will
				// cause the setProjectType setter to use the default value of ProjectType.
				target.setProjectType(parseProjectType(asStringOrNull(value)));
				break;
			case PROP__VENDOR_ID:
				target.setVendorId(asStringOrNull(value));
				break;
			case PROP__VENDOR_NAME:
				target.setVendorName(asStringOrNull(value));
				break;
			case PROP__OUTPUT:
				target.setOutputPath(asStringOrNull(value));
				break;
			case PROP__SOURCES:
				target.getSourceContainers().addAll(asSourceContainerDescriptionsOrEmpty(value));
				break;
			case PROP__MODULE_FILTERS:
				target.getModuleFilters().addAll(asModuleFiltersInObjectOrEmpty(value));
				break;
			case PROP__MAIN_MODULE:
				target.setMainModule(asStringOrNull(value));
				break;
			case PROP__TESTED_PROJECTS:
				target.getTestedProjects().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case PROP__IMPLEMENTATION_ID:
				target.setImplementationId(asStringOrNull(value));
				break;
			case PROP__IMPLEMENTED_PROJECTS:
				target.getImplementedProjects().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case PROP__EXTENDED_RUNTIME_ENVIRONMENT:
				target.setExtendedRuntimeEnvironment(asProjectReferenceOrNull(value));
				break;
			case PROP__PROVIDED_RUNTIME_LIBRARIES:
				target.getProvidedRuntimeLibraries().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case PROP__REQUIRED_RUNTIME_LIBRARIES:
				target.getRequiredRuntimeLibraries().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case PROP__MODULE_LOADER:
				target.setModuleLoader(parseModuleLoader(asStringOrNull(value)));
				break;
			case PROP__INIT_MODULES:
				target.getInitModules().addAll(asBootstrapModulesInArrayOrEmpty(value));
				break;
			case PROP__EXEC_MODULE:
				target.setExecModule(asBootstrapModuleOrNull(value));
				break;
			}
		}
	}

	private void convertDependencies(ProjectDescription target, List<NameValuePair> depPairs, boolean avoidDuplicates) {
		Set<String> existingProjectIds = new HashSet<>();
		if (avoidDuplicates) {
			for (ProjectDependency pd : target.getProjectDependencies()) {
				existingProjectIds.add(pd.getProjectId());
			}
		}

		for (NameValuePair pair : depPairs) {
			String projectId = pair.getName();

			boolean addProjectDependency = true;
			addProjectDependency &= projectId != null && !projectId.isEmpty();
			addProjectDependency &= !(avoidDuplicates && existingProjectIds.contains(projectId));
			existingProjectIds.add(projectId);

			if (addProjectDependency) {
				JSONValue value = pair.getValue();
				String valueStr = asStringOrNull(value);
				ProjectDependency dep = N4mfFactory.eINSTANCE.createProjectDependency();
				dep.setProjectId(projectId);
				dep.setVersionConstraintString(valueStr);

				boolean canParseSEMVER = true;
				canParseSEMVER = !"latest".equals(valueStr);
				if (canParseSEMVER) {
					VersionRangeSet vrs = semverHelper.parseVersionRangeSet(valueStr);
					dep.setVersionConstraint(vrs);
				} else {
					dep.setVersionConstraint(null);
				}
				target.getProjectDependencies().add(dep);
			}
		}
	}

	private VersionNumber parseVersion(String versionStr) {
		if (versionStr == null) {
			return null;
		}
		VersionNumber result = semverHelper.parseVersionNumber(versionStr);
		if (result == null) {
			System.err.println("WARNING: invalid or unsupported version: " + versionStr);
		}
		return result;
	}
}
