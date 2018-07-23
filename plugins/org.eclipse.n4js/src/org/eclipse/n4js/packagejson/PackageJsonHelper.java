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

import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNameValuePairsOrEmpty;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asStringOrNull;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.getPropertyAsStringOrNull;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.DEFAULT_MAIN_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.DEFAULT_MODULE_LOADER_FOR_VALIDATION;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.DEFAULT_OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.DEFAULT_VENDOR_ID;
import static org.eclipse.n4js.packagejson.PackageJsonConstants.DEFAULT_VERSION;
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.n4mf.N4mfFactory;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

import com.google.inject.Inject;

/**
 * Helper for converting a {@link JSONDocument} representing a valid {@code package.json} file to a
 * {@link ProjectDescription}.
 */
public class PackageJsonHelper {

	@Inject
	private SemverHelper semverHelper;

	/**
	 * Transform the given {@code packageJSON} into an equivalent {@link ProjectDescription} instance.
	 * <p>
	 * Note: this methods does not implement the package.json feature that a "main" path may point to a folder and then
	 * a file "index.js" in that folder will be used as main module (for details see
	 * {@link ProjectDescriptionUtils#convertMainPathToModuleSpecifier(String, List)}).
	 *
	 * @param packageJSON
	 *            the JSON document to convert (should be the representation of a valid {@code package.json} file).
	 * @param applyDefaultValues
	 *            whether default values should be applied to the project description after conversion.
	 * @param defaultProjectId
	 *            the default project ID (will be ignored if {@code applyDefaultValues} is set to <code>false</code>.
	 * @return the project description converted from the given JSON document.
	 */
	public ProjectDescription convertToProjectDescription(JSONDocument packageJSON, boolean applyDefaultValues,
			String defaultProjectId) {
		JSONValue rootValue = packageJSON.getContent();
		if (rootValue instanceof JSONObject) {
			ProjectDescription result = N4mfFactory.eINSTANCE.createProjectDescription();
			List<NameValuePair> rootPairs = ((JSONObject) rootValue).getNameValuePairs();
			convertRootPairs(result, rootPairs);
			adjustProjectDescriptionAfterConversion(result, applyDefaultValues, defaultProjectId,
					getPropertyAsStringOrNull((JSONObject) rootValue, PROP__MAIN));
			return result;
		}
		return null;
	}

	private void convertRootPairs(ProjectDescription target, List<NameValuePair> rootPairs) {
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
				// need to handle this value later after all source containers have been read
				// (see method #adjustProjectDescriptionAfterConversion())
				break;
			case PROP__N4JS:
				// mark project with N4JS nature
				target.setHasN4JSNature(true);
				convertN4jsPairs(target, asNameValuePairsOrEmpty(value));
				break;
			}
		}
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
				dep.setVersionRequirementString(valueStr);

				VersionRangeSetRequirement vrs = semverHelper.parseVersionRangeSet(valueStr);
				dep.setVersionRequirement(vrs);
				target.getProjectDependencies().add(dep);
			}
		}
	}

	private void adjustProjectDescriptionAfterConversion(ProjectDescription target, boolean applyDefaultValues,
			String defaultProjectId, String valueOfTopLevelPropertyMain) {

		// store whether target has a declared mainModule *before* applying the default values
		boolean hasN4jsSpecificMainModule = target.getMainModule() != null;

		// apply default values (if desired)
		if (applyDefaultValues) {
			applyDefaults(target, defaultProjectId);
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

		// sanitize dependencies: remove implementation projects from dependencies if API projects also given
		// (this is a work-around for supporting the API/Impl concept with npm/yarn: in a client project, both the API
		// and implementation projects will be specified as dependency in the package.json and the following code will
		// filter out implementation projects to not confuse API/Impl logic in other places)
		Set<String> projectIdsToRemove = target.getProjectDependencies().stream()
				.map(pd -> pd.getProjectId())
				.filter(id -> id.endsWith(".api"))
				.map(id -> id.substring(0, id.length() - 4))
				.collect(Collectors.toSet());
		target.getProjectDependencies().removeIf(pd -> projectIdsToRemove.contains(pd.getProjectId()));
	}

	/**
	 * Apply default values to the given project description. This should be performed right after loading and
	 * converting the project description from JSON.
	 */
	private void applyDefaults(ProjectDescription target, String defaultProjectId) {
		// implementation note: we do not have to set the default for 'projectType' here,
		// because this default is already handled by EMF by defining VALIDATION as the
		// first literal of enum ProjectType in N4MF.xcore.
		if (target.getProjectId() == null) {
			target.setProjectId(defaultProjectId);
		}
		if (target.getProjectVersion() == null) {
			target.setProjectVersion(parseVersion(DEFAULT_VERSION));
		}
		if (target.getVendorId() == null) {
			target.setVendorId(DEFAULT_VENDOR_ID);
		}
		if (target.getMainModule() == null) {
			target.setMainModule(DEFAULT_MAIN_MODULE);
		}
		if (target.getOutputPathRaw() == null) {
			target.setOutputPathRaw(DEFAULT_OUTPUT);
		}
		// if no source containers are defined (no matter what type),
		// then add a default source container of type "source" with path "."
		Iterator<String> sourceContainerPaths = target.getSourceContainers().stream()
				.flatMap(sc -> sc.getPathsRaw().stream()).iterator();
		if (!sourceContainerPaths.hasNext()) {
			SourceContainerDescription scd = target.getSourceContainers().stream()
					.filter(sc -> sc.getSourceContainerType() == SourceContainerType.SOURCE)
					.findFirst().orElse(null);
			if (scd == null) {
				SourceContainerDescription scdNew = N4mfFactory.eINSTANCE.createSourceContainerDescription();
				scdNew.setSourceContainerType(SourceContainerType.SOURCE);
				scdNew.getPathsRaw().add(DEFAULT_OUTPUT);
				target.getSourceContainers().add(scdNew);
			} else if (scd.getPathsRaw().isEmpty()) {
				scd.getPathsRaw().add(DEFAULT_OUTPUT);
			}
		}
		// module loader must be commonjs for VALIDATION projects
		// (no need to set default in case of other project types, because this is handled by EMF)
		if (target.getProjectType() == ProjectType.VALIDATION) {
			target.setModuleLoader(DEFAULT_MODULE_LOADER_FOR_VALIDATION);
		}
	}

	private VersionNumber parseVersion(String versionStr) {
		if (versionStr == null) {
			return null;
		}
		VersionNumber result = semverHelper.parseVersionNumber(versionStr);
		return result;
	}
}
