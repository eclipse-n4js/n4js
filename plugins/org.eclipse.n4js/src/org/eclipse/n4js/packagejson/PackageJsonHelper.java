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
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNonEmptyStringOrNull;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asStringOrNull;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asStringsInArrayOrEmpty;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.getProperty;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.MAIN;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.MAIN_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROJECT_TYPE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VENDOR_ID;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VERSION;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asModuleFiltersInObjectOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asProjectReferenceOrNull;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asProjectReferencesInArrayOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asSourceContainerDescriptionsOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.parseProjectType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.projectDescription.DependencyType;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectDescriptionFactory;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.semver.SemverHelper;
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
	 * @param defaultProjectName
	 *            the default project ID (will be ignored if {@code applyDefaultValues} is set to <code>false</code>.
	 * @return the project description converted from the given JSON document.
	 */
	public ProjectDescription convertToProjectDescription(JSONDocument packageJSON, boolean applyDefaultValues,
			String defaultProjectName) {
		JSONValue rootValue = packageJSON.getContent();
		if (rootValue instanceof JSONObject) {
			LazyParsingProjectDescriptionImpl result = new LazyParsingProjectDescriptionImpl();
			List<NameValuePair> rootPairs = ((JSONObject) rootValue).getNameValuePairs();
			convertRootPairs(result, rootPairs);

			JSONValue property = getProperty((JSONObject) rootValue, MAIN.name).orElse(null);
			String propertyAsString = asNonEmptyStringOrNull(property);
			adjustProjectDescriptionAfterConversion(result, applyDefaultValues, defaultProjectName, propertyAsString);
			return result;
		}
		return null;
	}

	private void convertRootPairs(LazyParsingProjectDescriptionImpl target, List<NameValuePair> rootPairs) {
		for (NameValuePair pair : rootPairs) {
			String name = pair.getName();
			PackageJsonProperties property = PackageJsonProperties.valueOfNameOrNull(name);
			if (property == null) {
				continue;
			}

			JSONValue value = pair.getValue();
			switch (property) {
			case NAME:
				target.setProjectName(asNonEmptyStringOrNull(value));
				break;
			case VERSION:
				target.setLazyProjectVersion(semverHelper, asNonEmptyStringOrNull(value));
				break;
			case DEPENDENCIES:
				convertDependencies(target, asNameValuePairsOrEmpty(value), true, DependencyType.RUNTIME);
				break;
			case DEV_DEPENDENCIES:
				// for the moment, we do not separate devDependencies from ordinary dependencies in ProjectDescription
				convertDependencies(target, asNameValuePairsOrEmpty(value), true, DependencyType.DEVELOPMENT);
				break;
			case MAIN:
				// need to handle this value later after all source containers have been read
				// (see method #adjustProjectDescriptionAfterConversion())
				break;
			case N4JS:
				// mark project with N4JS nature
				target.setHasN4JSNature(true);
				convertN4jsPairs(target, asNameValuePairsOrEmpty(value));
				break;
			case WORKSPACES:
				target.setYarnWorkspaceRoot(true);
				target.getWorkspaces().addAll(asStringsInArrayOrEmpty(value));
				break;
			default:
				break;
			}
		}
	}

	private void convertN4jsPairs(ProjectDescription target, List<NameValuePair> n4jsPairs) {
		for (NameValuePair pair : n4jsPairs) {
			String name = pair.getName();
			PackageJsonProperties property = PackageJsonProperties.valueOfNameOrNull(name);
			if (property == null) {
				continue;
			}

			JSONValue value = pair.getValue();
			switch (property) {
			case PROJECT_TYPE:
				// parseProjectType returns null if value is invalid, this will
				// cause the setProjectType setter to use the default value of ProjectType.
				target.setProjectType(parseProjectType(asNonEmptyStringOrNull(value)));
				break;
			case VENDOR_ID:
				target.setVendorId(asNonEmptyStringOrNull(value));
				break;
			case VENDOR_NAME:
				target.setVendorName(asNonEmptyStringOrNull(value));
				break;
			case OUTPUT:
				target.setOutputPath(asNonEmptyStringOrNull(value));
				break;
			case SOURCES:
				target.getSourceContainers().addAll(asSourceContainerDescriptionsOrEmpty(value));
				break;
			case MODULE_FILTERS:
				target.getModuleFilters().addAll(asModuleFiltersInObjectOrEmpty(value));
				break;
			case MAIN_MODULE:
				target.setMainModule(asNonEmptyStringOrNull(value));
				break;
			case TESTED_PROJECTS:
				target.getTestedProjects().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case IMPLEMENTATION_ID:
				target.setImplementationId(asNonEmptyStringOrNull(value));
				break;
			case IMPLEMENTED_PROJECTS:
				target.getImplementedProjects().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case EXTENDED_RUNTIME_ENVIRONMENT:
				target.setExtendedRuntimeEnvironment(asProjectReferenceOrNull(value));
				break;
			case PROVIDED_RUNTIME_LIBRARIES:
				target.getProvidedRuntimeLibraries().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case REQUIRED_RUNTIME_LIBRARIES:
				target.getRequiredRuntimeLibraries().addAll(asProjectReferencesInArrayOrEmpty(value));
				break;
			case DEFINES_PACKAGE:
				target.setDefinesPackage(asStringOrNull(value));
				break;
			default:
				break;
			}
		}
	}

	private void convertDependencies(ProjectDescription target, List<NameValuePair> depPairs, boolean avoidDuplicates,
			DependencyType type) {
		Set<String> existingProjectNames = new HashSet<>();
		if (avoidDuplicates) {
			for (ProjectDependency pd : target.getProjectDependencies()) {
				existingProjectNames.add(pd.getProjectName());
			}
		}

		for (NameValuePair pair : depPairs) {
			String projectName = pair.getName();

			boolean addProjectDependency = true;
			addProjectDependency &= projectName != null && !projectName.isEmpty();
			addProjectDependency &= !(avoidDuplicates && existingProjectNames.contains(projectName));
			existingProjectNames.add(projectName);

			if (addProjectDependency) {
				JSONValue value = pair.getValue();
				String valueStr = asStringOrNull(value);
				LazyParsingProjectDependencyImpl dep = new LazyParsingProjectDependencyImpl();
				dep.setProjectName(projectName);
				dep.setLazyVersionRequirement(semverHelper, valueStr);
				dep.setType(type);

				target.getProjectDependencies().add(dep);
			}
		}
	}

	private void adjustProjectDescriptionAfterConversion(LazyParsingProjectDescriptionImpl target,
			boolean applyDefaultValues,
			String defaultProjectName, String valueOfTopLevelPropertyMain) {
		// store whether target has a declared mainModule *before* applying the default values
		boolean hasN4jsSpecificMainModule = target.getMainModule() != null;

		// apply default values (if desired)
		if (applyDefaultValues) {
			applyDefaults(target, defaultProjectName);
		}

		// sanitize and set value of top-level property "main"
		// (note: this makes use of the source containers, so it possibly relies on default values having been applied)
		if (valueOfTopLevelPropertyMain != null) {
			if (!hasN4jsSpecificMainModule) { // only if no N4JS-specific "mainModule" property was given
				List<String> sourceContainerPaths = target.getSourceContainers().stream()
						.flatMap(scd -> ProjectDescriptionUtils.getPathsNormalized(scd).stream())
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
		Set<String> projectNamesToRemove = new HashSet<>();
		List<ProjectDependency> projectDependencies = target.getProjectDependencies();
		for (ProjectDependency dep : projectDependencies) {
			String otherProject = dep.getProjectName();
			if (otherProject.endsWith(".api")) {
				projectNamesToRemove.add(otherProject.substring(0, otherProject.length() - ".api".length()));
			}
		}
		if (!projectNamesToRemove.isEmpty()) {
			for (int i = projectDependencies.size() - 1; i >= 0; i--) {
				if (projectNamesToRemove.contains(projectDependencies.get(i).getProjectName())) {
					projectDependencies.remove(i);
				}
			}
		}
	}

	/**
	 * Apply default values to the given project description. This should be performed right after loading and
	 * converting the project description from JSON.
	 */
	private void applyDefaults(LazyParsingProjectDescriptionImpl target, String defaultProjectName) {
		if (!target.isHasN4JSNature()) {
			target.setProjectType(parseProjectType(PROJECT_TYPE.defaultValue));
		}
		if (target.getProjectName() == null) {
			target.setProjectName(defaultProjectName);
		}
		if (target.getLazyProjectVersion() == null) {
			target.setLazyProjectVersion(semverHelper, VERSION.defaultValue);
		}
		if (target.getVendorId() == null) {
			target.setVendorId(VENDOR_ID.defaultValue);
		}
		if (target.getMainModule() == null) {
			target.setMainModule(MAIN_MODULE.defaultValue);
		}
		if (target.getOutputPath() == null) {
			// a default output path will cause all files inside to be deleted on a 'clean build'
			// this causes problems especially if this project is a yarn workspace project
		}

		// if no source containers are defined (no matter what type),
		// then add a default source container of type "source" with path "."
		// EXCEPT target represents a yarn workspace root

		if (target.isYarnWorkspaceRoot()) {
			return;
		}
		List<SourceContainerDescription> sourceContainers = target.getSourceContainers();
		SourceContainerDescription sourceContainerTypeSource = null;
		for (SourceContainerDescription sourceContainer : sourceContainers) {
			if (!sourceContainer.getPaths().isEmpty()) {
				return;
			}
			if (sourceContainerTypeSource == null
					&& sourceContainer.getSourceContainerType() == SourceContainerType.SOURCE) {
				sourceContainerTypeSource = sourceContainer;
			}
		}
		if (sourceContainerTypeSource == null) {
			sourceContainerTypeSource = ProjectDescriptionFactory.eINSTANCE.createSourceContainerDescription();
			sourceContainerTypeSource.setSourceContainerType(SourceContainerType.SOURCE);
			sourceContainers.add(sourceContainerTypeSource);
		}
		sourceContainerTypeSource.getPaths().add(OUTPUT.defaultValue);
	}

}
