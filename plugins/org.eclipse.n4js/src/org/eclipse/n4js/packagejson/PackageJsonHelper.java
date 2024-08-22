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

import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asBooleanOrDefault;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNameValuePairsOrEmpty;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNonEmptyStringOrNull;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asStringOrNull;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asStringsInArrayOrEmpty;
import static org.eclipse.n4js.json.model.utils.JSONModelUtils.getProperty;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.GENERATOR_DTS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.GENERATOR_REWRITE_CJS_IMPORTS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.GENERATOR_SOURCE_MAPS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.MAIN;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.MAIN_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PACKAGES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROJECT_TYPE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VENDOR_ID;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VERSION;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asModuleFiltersInObjectOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asProjectReferenceOrNull;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asProjectReferencesInArrayOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.asSourceContainerDescriptionsOrEmpty;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.parseProjectType;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.packagejson.projectDescription.DependencyType;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDependency;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescriptionBuilder;
import org.eclipse.n4js.packagejson.projectDescription.ProjectExports;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

/**
 * Helper for converting a {@link JSONDocument} representing a valid {@code package.json} file to a
 * {@link ProjectDescription}.
 */
public class PackageJsonHelper {

	@Inject
	private SemverHelper semverHelper;

	private VersionNumber cachedN4JSDefaultVersionNumber = null;

	private VersionNumber cachedJSDefaultVersionNumber = null;

	/**
	 * Transform the given {@code packageJSON} into an equivalent {@link ProjectDescriptionBuilder} instance.
	 *
	 * @param packageJSON
	 *            the JSON document to convert (should be the representation of a valid {@code package.json} file).
	 * @return the project description converted from the given JSON document or <code>null</code> if the root value of
	 *         the given JSON document is not a {@link JSONObject}.
	 */
	public ProjectDescriptionBuilder convertToProjectDescription(JSONDocument packageJSON) {
		JSONValue rootValue = packageJSON.getContent();
		if (!(rootValue instanceof JSONObject)) {
			return null;
		}

		ProjectDescriptionBuilder target = new ProjectDescriptionBuilder();
		List<NameValuePair> rootPairs = ((JSONObject) rootValue).getNameValuePairs();
		convertRootPairs(target, rootPairs);

		return target;
	}

	/**
	 * Adjusts the given target and applies default values.
	 * <p>
	 * Note: this method does not implement the package.json feature that a "main" path may point to a folder and then a
	 * file "index.js" in that folder will be used as main module (for details see
	 * {@link ProjectDescriptionUtils#convertMainPathToModuleSpecifier(String, List)}).
	 *
	 * @param packageJSON
	 *            the JSON document to convert (should be the representation of a valid {@code package.json} file).
	 * @param target
	 *            target that will be adjusted
	 * @param applyDefaultValues
	 *            whether default values should be applied to the project description after conversion.
	 * @param defaultProjectName
	 *            the default project ID (will be ignored if {@code applyDefaultValues} is set to <code>false</code>.
	 * @return the project description converted from the given JSON document or <code>null</code> if the root value of
	 *         the given JSON document is not a {@link JSONObject}.
	 */
	public ProjectDescriptionBuilder adjustAndApplyDefaults(JSONDocument packageJSON, ProjectDescriptionBuilder target,
			boolean applyDefaultValues, String defaultProjectName) {

		JSONValue rootValue = packageJSON.getContent();
		if (!(rootValue instanceof JSONObject)) {
			return null;
		}

		String valueOfPropMain = asNonEmptyStringOrNull(getProperty((JSONObject) rootValue, MAIN.name).orElse(null));
		adjustProjectDescriptionAfterConversion(target, applyDefaultValues, defaultProjectName, valueOfPropMain);

		return target;
	}

	private void convertRootPairs(ProjectDescriptionBuilder target, List<NameValuePair> rootPairs) {
		for (NameValuePair pair : rootPairs) {
			PackageJsonProperties property = PackageJsonProperties.valueOfNameValuePairOrNull(pair);
			if (property == null) {
				continue;
			}

			JSONValue value = pair.getValue();
			switch (property) {
			case NAME:
				target.setPackageName(asNonEmptyStringOrNull(value));
				break;
			case VERSION:
				target.setVersion(asVersionNumberOrNull(value));
				break;
			case TYPE:
				// legal values are 'commonjs' and 'module'
				target.setESM("module".equals(asNonEmptyStringOrNull(value)));
				break;
			case DEPENDENCIES:
				convertDependencies(target, asNameValuePairsOrEmpty(value), true, DependencyType.RUNTIME);
				break;
			case DEV_DEPENDENCIES:
				// for the moment, we do not separate devDependencies from ordinary dependencies in ProjectDescription
				convertDependencies(target, asNameValuePairsOrEmpty(value), true, DependencyType.DEVELOPMENT);
				break;
			case MAIN:
				target.setMain(asNonEmptyStringOrNull(value));
				// also handle this value later after all source containers have been read
				// (see method #adjustProjectDescriptionAfterConversion())
				break;
			case TYPES:
			case TYPINGS:
				target.setTypes(asNonEmptyStringOrNull(value));
				break;
			case TYPES_VERSIONS:
				target.setTypesVersions(convertTypesVersions(asNameValuePairsOrEmpty(value)));
				break;
			case MODULE:
				// we don't care about the actual value, just about the fact that property "module" is present
				target.setModuleProperty(true);
				break;
			case EXPORTS:
				List<NameValuePair> exportElems = asNameValuePairsOrEmpty(value);
				for (NameValuePair nvPair : exportElems) {
					String exportsPath = nvPair.getName();
					List<NameValuePair> nvPairExports = asNameValuePairsOrEmpty(nvPair.getValue());
					if (".".equals(exportsPath) || "./".equals(exportsPath)) {
						// only cases supported right now
						convertRootPairs(target, nvPairExports);
					} else {
						ProjectExports exports = convertExportPairs(exportsPath, nvPairExports);
						target.addExports(exports);
					}
				}
				break;
			case EXPORTS_TYPES:
				if (target.getTypes() == null) {
					target.setTypes(asNonEmptyStringOrNull(value));
				}
				break;
			case EXPORTS_IMPORT:
				if (target.getMain() == null) {
					target.setMain(asNonEmptyStringOrNull(value));
				}
				break;
			case EXPORTS_MODULE:
				// we don't care about the actual value, just about the fact that property "module" is present
				target.setModuleProperty(true);
				break;
			case N4JS:
				// mark project with N4JS nature
				target.setN4JSNature(true);
				convertN4jsPairs(target, asNameValuePairsOrEmpty(value));
				break;
			case WORKSPACES_ARRAY:
				target.setYarnWorkspaceRoot(true);
				target.getWorkspaces().addAll(asStringsInArrayOrEmpty(value));
				break;
			case WORKSPACES_OBJECT:
				target.setYarnWorkspaceRoot(true);
				JSONObject workspaces = (JSONObject) value;
				for (NameValuePair pairOfWorkspaces : workspaces.getNameValuePairs()) {
					PackageJsonProperties wProp = PackageJsonProperties.valueOfNameValuePairOrNull(pairOfWorkspaces);
					if (wProp == PACKAGES) {
						JSONValue packagesValue = pairOfWorkspaces.getValue();
						target.getWorkspaces().addAll(asStringsInArrayOrEmpty(packagesValue));
						break;
					}
				}
				break;
			default:
				break;
			}
		}
	}

	private String convertTypesVersions(List<NameValuePair> pairs) {
		if (pairs.isEmpty()) {
			return null;
		}

		NameValuePair fstEntry = pairs.get(0); // TODO: check all entries and select highest ts version
		List<NameValuePair> redefinitionPaths = asNameValuePairsOrEmpty(fstEntry.getValue());
		if (redefinitionPaths.isEmpty()) {
			return null;
		}

		// TODO: support path redefinitions
		for (NameValuePair pathPair : redefinitionPaths) {
			if ("*".equals(pathPair.getName())) {
				List<String> paths = asStringsInArrayOrEmpty(pathPair.getValue());
				for (String path : paths) {
					if (path.endsWith("*")) {
						return path.substring(0, path.length() - 1);
					}
				}
			}
		}

		return null;
	}

	private ProjectExports convertExportPairs(String exportsPath, List<NameValuePair> pairs) {
		String main = null, types = null;
		Boolean moduleProperty = null;

		for (NameValuePair pair : pairs) {
			PackageJsonProperties property = PackageJsonProperties.valueOfNameValuePairOrNull(pair);
			if (property == null) {
				continue;
			}

			JSONValue value = pair.getValue();
			switch (property) {
			case EXPORTS_TYPES:
				types = asNonEmptyStringOrNull(value);
				break;
			case EXPORTS_IMPORT:
				main = asNonEmptyStringOrNull(value);
				break;
			case EXPORTS_MODULE:
				// we don't care about the actual value, just about the fact that property "module" is present
				moduleProperty = true;
				break;
			default:
				break;
			}
		}
		QualifiedName mainModuleQN = createQNForExports(main, types);

		return new ProjectExports(exportsPath, main, types, mainModuleQN, moduleProperty);
	}

	private QualifiedName createQNForExports(String main, String types) {
		String mainModuleName = types == null ? main : types;
		if (mainModuleName != null) {
			mainModuleName = mainModuleName.startsWith("./") ? mainModuleName.substring(2) : mainModuleName;
			if (mainModuleName.endsWith(N4JSGlobals.DTS_FILE_EXTENSION)) {
				mainModuleName = mainModuleName.substring(0, mainModuleName.length() - 5);
			} else if (mainModuleName.endsWith(N4JSGlobals.JS_FILE_EXTENSION)) {
				mainModuleName = mainModuleName.substring(0, mainModuleName.length() - 3);
			}
			return QualifiedName.create(mainModuleName.split(N4JSQualifiedNameConverter.DELIMITER));
		}
		return null;
	}

	private void convertN4jsPairs(ProjectDescriptionBuilder target, List<NameValuePair> n4jsPairs) {
		for (NameValuePair pair : n4jsPairs) {
			PackageJsonProperties property = PackageJsonProperties.valueOfNameValuePairOrNull(pair);
			if (property == null) {
				continue;
			}

			JSONValue value = pair.getValue();
			switch (property) {
			case PROJECT_TYPE:
				ProjectType projectType = parseProjectType(asNonEmptyStringOrNull(value));
				if (projectType != null) {
					target.setProjectType(projectType);
				}
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
			case GENERATOR:
				convertN4jsPairs(target, asNameValuePairsOrEmpty(value));
				break;
			case GENERATOR_SOURCE_MAPS:
				target.setGeneratorEnabledSourceMaps(
						asBooleanOrDefault(value, (Boolean) PackageJsonProperties.GENERATOR_SOURCE_MAPS.defaultValue));
				break;
			case GENERATOR_DTS:
				target.setGeneratorEnabledDts(
						asBooleanOrDefault(value, (Boolean) PackageJsonProperties.GENERATOR_DTS.defaultValue));
				break;
			case GENERATOR_REWRITE_MODULE_SPECIFIERS:
				for (NameValuePair nvp : asNameValuePairsOrEmpty(value)) {
					String n = nvp.getName();
					String v = asStringOrNull(nvp.getValue());
					if (n != null && v != null) { // note: we allow empty strings
						target.getGeneratorRewriteModuleSpecifiers().put(n, v);
					}
				}
				break;
			case GENERATOR_REWRITE_CJS_IMPORTS:
				target.setGeneratorEnabledRewriteCjsImports(
						asBooleanOrDefault(value,
								(Boolean) PackageJsonProperties.GENERATOR_REWRITE_CJS_IMPORTS.defaultValue));
				break;
			default:
				break;
			}
		}
	}

	private void convertDependencies(ProjectDescriptionBuilder target, List<NameValuePair> depPairs,
			boolean avoidDuplicates, DependencyType type) {

		Objects.requireNonNull(type);

		Set<String> existingPackageNames = new HashSet<>();
		if (avoidDuplicates) {
			for (ProjectDependency pd : target.getDependencies()) {
				existingPackageNames.add(pd.getPackageName());
			}
		}

		for (NameValuePair pair : depPairs) {
			String packageName = pair.getName();

			boolean addProjectDependency = true;
			addProjectDependency &= packageName != null && !packageName.isEmpty();
			addProjectDependency &= !(avoidDuplicates && existingPackageNames.contains(packageName));
			existingPackageNames.add(packageName);

			if (addProjectDependency) {
				JSONValue value = pair.getValue();
				String valueStr = asStringOrNull(value);
				NPMVersionRequirement versionRequirement = valueStr != null ? semverHelper.parse(valueStr) : null;
				ProjectDependency dep = new ProjectDependency(packageName, type, valueStr, versionRequirement);
				target.addDependency(dep);
			}
		}
	}

	private void adjustProjectDescriptionAfterConversion(ProjectDescriptionBuilder target,
			boolean applyDefaultValues, String defaultProjectName, String valueOfTopLevelPropertyMain) {

		if (target.getProjectType() == null || target.getProjectType() == ProjectType.PLAINJS) {
			if (applyDefaultValues) {
				applyPlainJSDefaults(target, defaultProjectName);
			}
			return;
		}

		// store whether target has a declared mainModule *before* applying the default values
		boolean hasN4jsSpecificMainModule = target.getMainModule() != null;

		// apply default values (if desired)
		if (applyDefaultValues) {
			applyN4JSDefaults(target, defaultProjectName);
		}

		// sanitize and set value of top-level property "main"
		// (note: this makes use of the source containers, so it possibly relies on default values having been applied)
		if (valueOfTopLevelPropertyMain != null) {
			if (!hasN4jsSpecificMainModule) { // only if no N4JS-specific "mainModule" property was given
				String mainModulePath = getMainModulePath(target, valueOfTopLevelPropertyMain);
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
		List<ProjectDependency> projectDependencies = target.getDependencies();
		for (ProjectDependency dep : projectDependencies) {
			String otherProject = dep.getPackageName();
			for (String suffix : N4JSGlobals.API_PROJECT_NAME_SUFFIXES) {
				if (otherProject.endsWith(suffix)) {
					projectNamesToRemove.add(otherProject.substring(0, otherProject.length() - suffix.length()));
					break;
				}
			}
		}
		if (!projectNamesToRemove.isEmpty()) {
			for (int i = projectDependencies.size() - 1; i >= 0; i--) {
				if (projectNamesToRemove.contains(projectDependencies.get(i).getPackageName())) {
					projectDependencies.remove(i);
				}
			}
		}
	}

	private String getMainModulePath(ProjectDescriptionBuilder target, String main) {
		List<String> sourceContainerPaths = target.getSourceContainers().stream()
				.flatMap(scd -> ProjectDescriptionUtils.getPathsNormalized(scd.getPaths()).stream())
				.collect(Collectors.toList());
		String mainModulePath = ProjectDescriptionUtils.convertMainPathToModuleSpecifier(
				main, sourceContainerPaths);
		return mainModulePath;
	}

	/**
	 * Apply default values to the given project description of a plain js project. This should be performed right after
	 * loading and converting the project description from JSON.
	 */
	private void applyPlainJSDefaults(ProjectDescriptionBuilder target, String defaultProjectName) {

		if (target.getMain() == null) {
			target.setMain(PackageJsonProperties.MAIN.defaultValue.toString());
		}
		if (target.getVersion() == null) {
			target.setVersion(createJSDefaultVersionNumber());
		}
		if (target.getProjectType() == null) {
			target.setProjectType(ProjectType.PLAINJS);
		}

		String mainOrTypesModulePath;
		if (target.getTypes() == null) {
			mainOrTypesModulePath = target.getMain();
		} else {
			mainOrTypesModulePath = target.getTypes();
			if (target.getTypesVersions() != null) {
				setSourceContainer(target, target.getTypesVersions(), true);
				trimProjectExports(target.getExports(), target.getTypesVersions());
			}
		}
		if (!target.isWorkspaceRoot()) {
			// workspace projects my contain unrelated sources hence do not use the default source folder
			setSourceContainer(target, (String) OUTPUT.defaultValue, false);
		}

		if (mainOrTypesModulePath != null) {
			if (mainOrTypesModulePath.startsWith("./")) {
				mainOrTypesModulePath = mainOrTypesModulePath.substring(2);
			}
			mainOrTypesModulePath = URIUtils.trimFileExtension(URI.createFileURI(mainOrTypesModulePath)).toString();
			target.setMainModule(mainOrTypesModulePath);
		}

		if (target.getPackageName() == null) {
			target.setPackageName(defaultProjectName);
		}
		if (target.getOutputPath() == null) {
			// note that in case the project is a yarn workspace project and there is a 'clean build' running
			// the entire contents will be deleted.
			target.setOutputPath(".");
		}
	}

	// as a convention 'types', 'module', etc. of a subpath have the subpath's name as a prefix
	private void trimProjectExports(List<ProjectExports> pes, String typesVersions) {
		if (typesVersions == null) {
			return;
		}
		for (int i = 0; i < pes.size(); i++) {
			ProjectExports pe = pes.get(i);
			if (pe.getExportsPathClean() == null) {
				continue;
			}
			String newMain = stripPrefix(pe.getMain(), typesVersions);
			String newTypes = stripPrefix(pe.getTypes(), typesVersions);
			String newEP = "./" + stripPrefix(pe.getExportsPathClean(), typesVersions);
			QualifiedName mainModuleQN = createQNForExports(newMain, newTypes);
			pes.set(i, new ProjectExports(newEP, newMain, newTypes, mainModuleQN, pe.getModuleProperty()));
		}
	}

	private String stripPrefix(String path, String prefix) {
		if (path == null) {
			return path;
		}
		if (path.startsWith("./")) {
			if (path.startsWith(prefix)) {
				return "./" + path.substring(prefix.length());
			}
		} else {
			if (path.startsWith(prefix)) {
				return path.substring(prefix.length());
			}
		}
		return path;
	}

	/**
	 * Apply default values to the given n4js project description. This should be performed right after loading and
	 * converting the project description from JSON.
	 */
	private void applyN4JSDefaults(ProjectDescriptionBuilder target, String defaultProjectName) {

		if (!target.hasN4JSNature() || target.getProjectType() == null) {
			// for non-N4JS projects, and if the project type is unset, enforce the default project type, i.e.
			// project type 'PLAINJS':
			target.setProjectType(parseProjectType((String) PROJECT_TYPE.defaultValue));
		}
		if (target.getPackageName() == null) {
			target.setPackageName(defaultProjectName);
		}
		if (target.getVersion() == null) {
			target.setVersion(createN4JSDefaultVersionNumber());
		}
		if (target.getVendorId() == null) {
			target.setVendorId((String) VENDOR_ID.defaultValue);
		}
		if (target.getMainModule() == null) {
			target.setMainModule((String) MAIN_MODULE.defaultValue);
		}
		if (target.getOutputPath() == null) {
			// note that in case the project is a yarn workspace project and there is a 'clean build' running
			// the entire contents will be deleted.
			target.setOutputPath((String) OUTPUT.defaultValue);
		}
		if (target.isGeneratorEnabledSourceMaps() == null) {
			target.setGeneratorEnabledSourceMaps((Boolean) GENERATOR_SOURCE_MAPS.defaultValue);
		}
		if (target.isGeneratorEnabledDts() == null) {
			target.setGeneratorEnabledDts((Boolean) GENERATOR_DTS.defaultValue);
		}
		if (target.isGeneratorEnabledRewriteCjsImports() == null) {
			target.setGeneratorEnabledRewriteCjsImports((Boolean) GENERATOR_REWRITE_CJS_IMPORTS.defaultValue);
		}

		setSourceContainer(target, (String) OUTPUT.defaultValue, false);
	}

	private void setSourceContainer(ProjectDescriptionBuilder target, String path, boolean replace) {
		// if no source containers are defined (no matter what type),
		// then add a default source container of type "source" with path "."
		// EXCEPT target represents a workspace root

		if (!target.isWorkspaceRoot()) {
			List<SourceContainerDescription> sourceContainers = target.getSourceContainers();
			SourceContainerDescription sourceContainerOfTypeSource = null;
			for (SourceContainerDescription sourceContainer : sourceContainers) {
				if (!replace && !sourceContainer.getPaths().isEmpty()) {
					return;
				}
				if (sourceContainerOfTypeSource == null
						&& sourceContainer.getType() == SourceContainerType.SOURCE) {
					sourceContainerOfTypeSource = sourceContainer;
				}
			}
			if (sourceContainerOfTypeSource != null) {
				sourceContainers.remove(sourceContainerOfTypeSource);
			}
			sourceContainers.add(new SourceContainerDescription(
					SourceContainerType.SOURCE, Collections.singleton(path)));
		}
	}

	private VersionNumber asVersionNumberOrNull(JSONValue value) {
		String versionStr = asNonEmptyStringOrNull(value);
		return versionStr != null ? semverHelper.parseVersionNumber(versionStr) : null;
	}

	private VersionNumber createN4JSDefaultVersionNumber() {
		if (cachedN4JSDefaultVersionNumber == null) {
			cachedN4JSDefaultVersionNumber = semverHelper.parseVersionNumber((String) VERSION.defaultValue);
		}
		return EcoreUtil.copy(cachedN4JSDefaultVersionNumber);
	}

	private VersionNumber createJSDefaultVersionNumber() {
		if (cachedJSDefaultVersionNumber == null) {
			cachedJSDefaultVersionNumber = semverHelper.parseVersionNumber("");
		}
		return EcoreUtil.copy(cachedJSDefaultVersionNumber);
	}
}
