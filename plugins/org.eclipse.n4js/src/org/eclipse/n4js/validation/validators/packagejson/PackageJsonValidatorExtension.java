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
package org.eclipse.n4js.validation.validators.packagejson;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.validation.extension.AbstractJSONValidatorExtension;
import org.eclipse.n4js.json.validation.extension.CheckProperty;
import org.eclipse.n4js.n4mf.ModuleFilterType;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.n4mf.VersionConstraint;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.utils.PackageJsonHelper;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.validators.N4JSProjectSetupValidator;
import org.eclipse.xtext.validation.Check;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A JSON validator extension that includes lower-level, structural and local validations with regard to
 * {@code package.json} resources.
 *
 * For a higher-level validation that is more based on the resulting {@link ProjectDescription} model, see
 * {@link N4JSProjectSetupValidator}.
 */
@Singleton
public class PackageJsonValidatorExtension extends AbstractJSONValidatorExtension {

	/** regular expression for valid package.json identifier (e.g. package name, vendor ID) */
	private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("(^)?[A-z_][A-z_\\-\\.0-9]*");

	/** key for memoization of the n4js.sources section of a package.json. See #getSourceContainers(). */
	private static final String N4JS_SOURCE_CONTAINERS = "N4JS_SOURCE_CONTAINERS";

	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private XpectAwareFileExtensionCalculator fileExtensionCalculator;
	@Inject
	private PackageJsonHelper packageJsonHelper;

	@Override
	protected boolean isResponsible(Map<Object, Object> context, EObject eObject) {
		// this validator extension only applies to package.json files
		return fileExtensionCalculator.getFilenameWithoutXpectExtension(eObject.eResource().getURI())
				.equals(IN4JSProject.PACKAGE_JSON);
	}

	/**
	 * Validates the initial structure of a package.json {@link JSONDocument}.
	 *
	 * This includes checking for the existence of mandatory properties (e.g. name, version).
	 */
	@Check
	public void checkDocument(JSONDocument document) {
		final JSONValue rootValue = document.getContent();
		// early exit for broken AST
		if (rootValue == null) {
			return;
		}

		// make sure the document root is an object
		if (!checkIsType(rootValue, JSONPackage.Literals.JSON_OBJECT,
				" as document root of a package.json file.")) {
			return;
		}
	}

	/** Validates the project/package name. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__NAME)
	public void checkName(JSONValue projectNameValue) {
		// first check for the type of the name value
		if (!checkIsType(projectNameValue, JSONPackage.Literals.JSON_STRING_LITERAL, "as package name")) {
			return;
		}
		final JSONStringLiteral projectName = (JSONStringLiteral) projectNameValue;

		// make sure the name conforms to the IDENTIFIER_PATTERN
		if (!IDENTIFIER_PATTERN.matcher(projectName.getValue()).matches()) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_PROJECT_NAME(projectName.getValue()),
					projectNameValue, IssueCodes.PKGJ_INVALID_PROJECT_NAME);
		}

		// make sure the package name equals the package folder name
		final String packageFolderName = projectNameValue.eResource().getURI().trimSegments(1).lastSegment();
		if (!packageFolderName.equals(projectName.getValue())) {
			addIssue(IssueCodes.getMessageForPKGJ_PACKAGE_NAME_MISMATCH(projectName.getValue(), packageFolderName),
					projectName, IssueCodes.PKGJ_PACKAGE_NAME_MISMATCH);
		}

		// in case the Platform is running (can be UI and headless)
		if (Platform.isRunning()) {
			// TODO consider external workspace case in which we are dealing with
			// original package.json of npm packages
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			final URI packageJsonUri = projectNameValue.eResource().getURI();

			String platformProjectContainer = null;
			if (packageJsonUri.isPlatformResource()) {
				// in UI case, assume platform:resource URIs
				final String platformURI = packageJsonUri.trimSegments(1).toPlatformString(true);
				final IResource resolvedResource = root.findMember(platformURI);
				if (resolvedResource instanceof IProject) {
					platformProjectContainer = resolvedResource.getName();
				}
			} else {
				// in headless case, assume file-based workspace representation
				platformProjectContainer = new File(packageJsonUri.toFileString()).getParentFile().getName();
			}

			if (platformProjectContainer == null) {
				throw new IllegalStateException("Failed to determine project name "
						+ "for resource " + packageJsonUri.toString());
			}

			if (!platformProjectContainer.equals(projectName.getValue())) {
				final String message = IssueCodes.getMessageForPKGJ_PROJECT_NAME_ECLIPSE_MISMATCH(
						projectName.getValue(),
						platformProjectContainer);
				addIssue(message, projectName,
						IssueCodes.PKGJ_PROJECT_NAME_ECLIPSE_MISMATCH);
			}
		}

	}

	/** Check the version property. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__VERSION)
	public void checkVersion(JSONValue versionValue) {
		checkIsType(versionValue, JSONPackage.Literals.JSON_STRING_LITERAL, "as package version");
	}

	/** Check the dependencies section structure. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__DEPENDENCIES)
	public void checkDependenciesStructure(JSONValue dependenciesValue) {
		checkIsDependenciesSection(dependenciesValue);
	}

	/** Check the devDependencies section structure. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__DEV_DEPENDENCIES)
	public void checkDevDependenciesStructure(JSONValue devDependenciesValue) {
		checkIsDependenciesSection(devDependenciesValue);
	}

	/**
	 * Checks whether the given {@code sectionValue} is a structurally valid package.json dependency section (including
	 * the version constraints).
	 */
	private void checkIsDependenciesSection(JSONValue sectionValue) {
		if (!checkIsType(sectionValue, JSONPackage.Literals.JSON_OBJECT, "as list of dependencies")) {
			return;
		}
		final JSONObject dependenciesObject = (JSONObject) sectionValue;
		for (NameValuePair entry : dependenciesObject.getNameValuePairs()) {
			if (checkIsType(entry.getValue(), JSONPackage.Literals.JSON_STRING_LITERAL, "as version specifier")) {
				final String constraintValue = ((JSONStringLiteral) entry.getValue()).getValue();
				final VersionConstraint parsedConstraint = packageJsonHelper
						.parseVersionConstraint(constraintValue);
				if (parsedConstraint == null) {
					addIssue(IssueCodes.getMessageForPKGJ_INVALID_VERSION_CONSTRAINT(constraintValue),
							entry.getValue(), IssueCodes.PKGJ_INVALID_VERSION_CONSTRAINT);
				}
			}
		}
	}

	/** Checks basic structural properties of the 'n4js' section (e.g. mandatory properties). */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS)
	public void checkN4JSSection(JSONValue n4jsSection) {
		// make sure n4js section is an object
		if (!checkIsType(n4jsSection, JSONPackage.Literals.JSON_OBJECT,
				"as package.json n4js section.")) {
			return;
		}

		final Multimap<String, JSONValue> n4jsValues = collectObjectValues((JSONObject) n4jsSection);

		// Check for correct types (null-values (non-existent) will not lead to issues)
		// Properties that are not checked here, have their own check-method which also validates their types.
		checkIsType(n4jsValues.get(ProjectDescriptionHelper.PROP__VENDOR_ID),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as vendor ID");
		checkIsType(n4jsValues.get(ProjectDescriptionHelper.PROP__VENDOR_NAME),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as vendor name");
		checkIsType(n4jsValues.get(ProjectDescriptionHelper.PROP__OUTPUT),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as output folder path");

		checkIsType(n4jsValues.get(ProjectDescriptionHelper.PROP__EXTENDED_RUNTIME_ENVIRONMENT),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as reference to extended runtime environment");
		checkIsArrayOfType(n4jsValues.get(ProjectDescriptionHelper.PROP__PROVIDED_RUNTIME_LIBRARIES),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as provided runtime libraries", "as library reference");
		checkIsArrayOfType(n4jsValues.get(ProjectDescriptionHelper.PROP__REQUIRED_RUNTIME_LIBRARIES),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as required runtime libraries", "as library reference");

		checkIsArrayOfType(n4jsValues.get(ProjectDescriptionHelper.PROP__INIT_MODULES),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as init modules", "as init module reference");
		checkIsType(n4jsValues.get(ProjectDescriptionHelper.PROP__EXEC_MODULE),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as exec module");

		// // special error message in case of a missing output property
		// if (n4jsValues.get(ProjectDescriptionHelper.PROP__OUTPUT).isEmpty()) {
		// addIssue(IssueCodes.getMessageForPKGJ_NO_OUTPUT_FOLDER(), n4jsSection.eContainer(),
		// JSONPackage.Literals.NAME_VALUE_PAIR__NAME, IssueCodes.PKGJ_NO_OUTPUT_FOLDER);
		// }
	}

	/** Check the projectType value structure. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "."
			+ ProjectDescriptionHelper.PROP__PROJECT_TYPE)
	public void checkProjectTypeStructure(JSONValue projectTypeValue) {
		if (!checkIsType(projectTypeValue, JSONPackage.Literals.JSON_STRING_LITERAL)) {
			return;
		}
		// check whether the given value represents a valid project type
		final String projectTypeString = ((JSONStringLiteral) projectTypeValue).getValue();
		if (packageJsonHelper.parseProjectType(projectTypeString) == null) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_PROJECT_TYPE(projectTypeString),
					projectTypeValue, IssueCodes.PKGJ_INVALID_PROJECT_TYPE);
		}
	}

	/** Check the projectType value structure. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "."
			+ ProjectDescriptionHelper.PROP__MODULE_LOADER)
	public void checkModuleLoaderStructure(JSONValue moduleLoaderValue) {
		if (!checkIsType(moduleLoaderValue, JSONPackage.Literals.JSON_STRING_LITERAL)) {
			return;
		}
		// check whether the given value represents a valid project type
		final String moduleLoaderString = ((JSONStringLiteral) moduleLoaderValue).getValue();
		if (packageJsonHelper.parseModuleLoader(moduleLoaderString) == null) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_MODULE_LOADER(moduleLoaderString),
					moduleLoaderValue, IssueCodes.PKGJ_INVALID_MODULE_LOADER);
		}
	}

	/**
	 * Checks all given {@code values} to be instances of the given {@code valueClass}.
	 *
	 * @See {@link #checkIsType(JSONValue, EClass, String)}
	 */
	private boolean checkIsType(Iterable<JSONValue> values, EClass valueClass, String locationClause) {
		boolean overallResult = true;
		for (JSONValue value : values) {
			overallResult &= checkIsType(value, valueClass, locationClause);
		}
		return overallResult;
	}

	/**
	 * Checks the given {@code value} to be a {@link JSONArray} with element type {@code elemetnClass}.
	 *
	 * @See {@link #checkIsType(JSONValue, EClass, String)}.
	 */
	private boolean checkIsArrayOfType(JSONValue value, EClass elementClass,
			String locationClass, String elementLocation) {
		return checkIsType(value, JSONPackage.Literals.JSON_ARRAY, locationClass) &&
				checkIsType(((JSONArray) value).getElements(), elementClass, elementLocation);
	}

	/**
	 * Checks the given {@code values} to be {@link JSONArray}s with element type {@code elementClass}.
	 *
	 * @See {@link #checkIsType(JSONValue, EClass, String)}.
	 */
	private boolean checkIsArrayOfType(Iterable<JSONValue> values, EClass elementClass,
			String locationClass, String elementLocation) {
		boolean overallResult = true;
		for (JSONValue value : values) {
			overallResult &= checkIsType(value, JSONPackage.Literals.JSON_ARRAY, locationClass) &&
					checkIsType(((JSONArray) value).getElements(), elementClass, elementLocation);
		}
		return overallResult;
	}

	/** Validates the source container section of N4JS package.json files */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__SOURCES)
	public void checkSourceContainers() {
		// obtain source-container-related content of the section and validate its structure
		Multimap<SourceContainerType, List<JSONStringLiteral>> sourceContainers = getSourceContainers();

		// check each source container sub-section (e.g. sources, external, etc.)
		for (Entry<SourceContainerType, List<JSONStringLiteral>> subSection : sourceContainers
				.entries()) {
			// check each specified source path to actually exist
			for (JSONStringLiteral containerLiteral : subSection.getValue()) {
				checkSourceContainerLiteral(containerLiteral);
			}
		}

		final List<JSONStringLiteral> allDeclaredSourceContainers = sourceContainers.entries().stream()
				// flat-map to a list of all source containers that are specified (for all types combined)
				.flatMap(entry -> entry.getValue().stream())
				.collect(Collectors.toList());

		// find all groups of duplicate paths
		final List<List<JSONStringLiteral>> containerDuplicates = findPathDuplicates(allDeclaredSourceContainers);
		for (List<JSONStringLiteral> duplicateGroup : containerDuplicates) {
			// indicates whether the duplicates are spread across multiple container types (e.g. external, sources)
			final String normalizedPath = FileUtils.normalize(duplicateGroup.get(0).getValue());

			for (JSONStringLiteral duplicate : duplicateGroup) {
				final String inClause = createInSourceContainerTypeClause(duplicate, duplicateGroup);
				addIssue(IssueCodes.getMessageForPKGJ_DUPLICATE_SOURCE_CONTAINER(normalizedPath, inClause),
						duplicate, IssueCodes.PKGJ_DUPLICATE_SOURCE_CONTAINER);
			}
		}
	}

	/** Checks a single source container literal for validity. */
	private boolean checkSourceContainerLiteral(JSONStringLiteral containerLiteral) {
		return holdsValidRelativePath(containerLiteral) && // check path for validity
				holdsExistingDirectoryPath(containerLiteral); // check directory for existence
	}

	/**
	 * Checks the <code>n4js.mainModule</code> property of the {@code package.json}.
	 */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "."
			+ ProjectDescriptionHelper.PROP__MAIN_MODULE)
	public void checkMainModule(JSONValue mainModuleValue) {
		if (!checkIsType(mainModuleValue, JSONPackage.Literals.JSON_STRING_LITERAL, "as main module specifier")) {
			return;
		}

		final JSONStringLiteral mainModuleLiteral = (JSONStringLiteral) mainModuleValue;
		final String mainModuleSpecifier = mainModuleLiteral.getValue();

		if (mainModuleSpecifier.isEmpty() || !isExistingModule(mainModuleLiteral)) {
			final String specifierToShow = mainModuleSpecifier.isEmpty() ? "<empty string>" : mainModuleSpecifier;
			addIssue(IssueCodes.getMessageForPKGJ_NON_EXISTING_MAIN_MODULE(specifierToShow),
					mainModuleLiteral, IssueCodes.PKGJ_NON_EXISTING_MAIN_MODULE);
		}
	}

	/**
	 * Validates basic properties of the {@code n4js.implementationId}.
	 */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS +
			"." + ProjectDescriptionHelper.PROP__IMPLEMENTATION_ID)
	public void checkImplementationId(JSONValue value) {
		final JSONArray implementedProjectsValue = getSingleDocumentValue(ProjectDescriptionHelper.PROP__N4JS + "." +
				ProjectDescriptionHelper.PROP__IMPLEMENTED_PROJECTS, JSONArray.class);

		// check for correct type of implementationId
		if (!checkIsType(value, JSONPackage.Literals.JSON_STRING_LITERAL, "as implementation ID")) {
			return;
		}

		final JSONStringLiteral implementationId = (JSONStringLiteral) value;

		// at this point we may assume that the implementationId was set
		if ((implementedProjectsValue == null || implementedProjectsValue.getElements().isEmpty())) {
			// missing implemented projects
			addIssue(IssueCodes.getMessageForPKGJ_APIIMPL_MISSING_IMPL_PROJECTS(),
					implementationId.eContainer(), JSONPackage.Literals.NAME_VALUE_PAIR__NAME,
					IssueCodes.PKGJ_APIIMPL_MISSING_IMPL_PROJECTS);
		}
	}

	/**
	 * Validates basic properties of the list of {@code n4js.implementedProjects}.
	 */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS +
			"." + ProjectDescriptionHelper.PROP__IMPLEMENTED_PROJECTS)
	public void checkImplementedProjects(JSONValue value) {
		// obtain implementationId, if present. null otherwise
		final JSONStringLiteral implementationId = getSingleDocumentValue(ProjectDescriptionHelper.PROP__N4JS +
				"." + ProjectDescriptionHelper.PROP__IMPLEMENTATION_ID, JSONStringLiteral.class);

		// check for correct types of implementedProjects
		if (!checkIsType(value, JSONPackage.Literals.JSON_ARRAY, "as list of implemented projects")) {
			return;
		}

		// check for correct types of all implementedProjects elements (they represent project references)
		final List<JSONValue> implementedProjectValues = ((JSONArray) value).getElements();
		List<JSONStringLiteral> implementedProjectLiterals = implementedProjectValues.stream()
				.map(v -> {
					if (!checkIsType(v, JSONPackage.Literals.JSON_STRING_LITERAL, "as implemented project reference")) {
						return null;
					}
					return ((JSONStringLiteral) v);
				})
				// filter null-projects and obtain list of readable project references
				.filter(p -> p != null).collect(Collectors.toList());

		// obtain the declared project name (name property)
		final JSONStringLiteral declaredProjectNameValue = getSingleDocumentValue(ProjectDescriptionHelper.PROP__NAME,
				JSONStringLiteral.class);

		// exit early if project name cannot be determined
		if (declaredProjectNameValue == null) {
			return;
		}

		for (JSONStringLiteral implementedProjectLiteral : implementedProjectLiterals) {
			if (implementedProjectLiteral.getValue().equals(declaredProjectNameValue.getValue())) {
				// reflexive implementation
				addIssue(IssueCodes.getMessageForPKGJ_APIIMPL_REFLEXIVE(), implementedProjectLiteral,
						IssueCodes.PKGJ_APIIMPL_REFLEXIVE);
			}
		}

		if (implementationId == null && !implementedProjectLiterals.isEmpty()) {
			addIssue(IssueCodes.getMessageForPKGJ_APIIMPL_MISSING_IMPL_PROJECTS(), value.eContainer(),
					JSONPackage.Literals.NAME_VALUE_PAIR__NAME, IssueCodes.PKGJ_APIIMPL_MISSING_IMPL_PROJECTS);
		}
	}

	/**
	 * Checks the n4js.testedProjects section of the {@code package.json}.
	 */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "." +
			ProjectDescriptionHelper.PROP__TESTED_PROJECTS)
	public void checkTestedProjects(JSONValue testedProjectsValues) {
		if (!checkIsArrayOfType(testedProjectsValues, JSONPackage.Literals.JSON_STRING_LITERAL,
				"as list of tested projects", "as tested project reference")) {
			return;
		}
	}

	/**
	 * Checks that the output folder and the declared source containers are not nested in way that can lead to conflicts
	 * wrt. transpile loops and workspace clean operations (e.g. output folder is considered source folder).
	 */
	@CheckProperty(propertyPath = ((ProjectDescriptionHelper.PROP__N4JS + ".") + ProjectDescriptionHelper.PROP__OUTPUT))
	public void checkOutputFolder(final JSONValue outputPathValue) {
		if ((!(outputPathValue instanceof JSONStringLiteral))) {
			return;
		}
		// use normalized output path for validation
		final String normalizedOutputPathValue = FileUtils.normalize(((JSONStringLiteral) outputPathValue).getValue());
		final Path outputPath = new File(normalizedOutputPathValue).toPath();

		// do not perform check for projects of type 'validation'
		if (((getProjectType() == ProjectType.VALIDATION) || (outputPath == null))) {
			return;
		}

		Multimap<SourceContainerType, List<JSONStringLiteral>> sourceContainers = getSourceContainers();

		for (Entry<SourceContainerType, List<JSONStringLiteral>> sourceContainerType : sourceContainers.entries()) {
			// iterate over all source container paths (in terms of string literals)
			for (JSONStringLiteral sourceContainerSpecifier : sourceContainerType.getValue()) {
				// use normalized source path for checks
				final String normalizedSourcePathValue = FileUtils.normalize(sourceContainerSpecifier.getValue());
				final Path sourcePath = new File(normalizedSourcePathValue).toPath();

				// obtain descriptive name of the source container type
				final String srcFrgmtName = sourceContainerType.getKey().getLiteral().toLowerCase();

				// handle case that output path is nested within a source folder (or equal)
				if (((".".equals(sourcePath.toString()) || sourcePath.equals(outputPath))
						|| outputPath.startsWith(sourcePath))) {
					final String containingFolder = "The output";
					final String nestedFolder = ("a " + srcFrgmtName);
					final String message = IssueCodes
							.getMessageForOUTPUT_AND_SOURCES_FOLDER_NESTING(containingFolder, nestedFolder);

					addIssue(message, outputPathValue, IssueCodes.OUTPUT_AND_SOURCES_FOLDER_NESTING);
				}

				// handle case that source container is nested within output directory (or equal)
				if ((".".equals(outputPath.toString()) || sourcePath.startsWith(outputPath))) {
					final String containingFolder = ("A " + srcFrgmtName);
					final String nestedFolder = "the output";
					final String message = IssueCodes
							.getMessageForOUTPUT_AND_SOURCES_FOLDER_NESTING(containingFolder, nestedFolder);

					addIssue(message, sourceContainerSpecifier, IssueCodes.OUTPUT_AND_SOURCES_FOLDER_NESTING);
				}
			}
		}
	}

	/**
	 * Checks the n4js.moduleFilters section of the {@code package.json}.
	 */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__N4JS + "."
			+ ProjectDescriptionHelper.PROP__MODULE_FILTERS)
	public void checkModuleFilters(JSONValue moduleFilterSection) {
		if (!checkIsType(moduleFilterSection, JSONPackage.Literals.JSON_OBJECT, "as moduleFilters section")) {
			return;
		}
		((JSONObject) moduleFilterSection).getNameValuePairs().stream()
				.forEach(pair -> checkModuleFilterType(pair));
	}

	/** Checks whether the given {@code moduleFilterPair} represents a valid module-filter section entry. */
	private void checkModuleFilterType(NameValuePair moduleFilterPair) {
		// obtain enum-representation of the validated module filter type
		final ModuleFilterType filterType = packageJsonHelper.parseModuleFilterType(moduleFilterPair.getName());

		// make sure the module filter type could be parsed successfully
		if (filterType == null) {
			final String message = IssueCodes.getMessageForPKGJ_INVALID_MODULE_FILTER_TYPE(
					moduleFilterPair.getName(), "noValidate and noModuleWrap");
			addIssue(message, moduleFilterPair, JSONPackage.Literals.NAME_VALUE_PAIR__NAME,
					IssueCodes.PKGJ_INVALID_MODULE_FILTER_TYPE);
		}

		// check type of RHS
		if (!checkIsType(moduleFilterPair.getValue(), JSONPackage.Literals.JSON_ARRAY, "as module filter specifiers")) {
			return;
		}

		// obtain a list of all declared filter specifiers
		final List<JSONValue> specifierValues = ((JSONArray) moduleFilterPair.getValue()).getElements();
		// obtain a list of all declared source container paths
		final Set<String> sourceContainerPaths = getAllSourceContainerPaths();

		// first, validate all declared filter specifiers
		final List<ValidationModuleFilterSpecifier> declaredFilterSpecifiers = specifierValues.stream()
				.map(v -> getModuleFilterInformation(v, filterType)).collect(Collectors.toList());
		declaredFilterSpecifiers.forEach(specifier -> checkModuleFilterSpecifier(specifier));

		// determine the groups of duplicate module filter specifiers (same source container and same filter)
		final Map<String, List<ValidationModuleFilterSpecifier>> duplicateGroups = declaredFilterSpecifiers.stream()
				.filter(i -> i != null)
				.flatMap(i -> {
					if (i.sourceContainerPath == null) {
						// If no source container path has been declared, the filter applies to all declared
						// source container paths.
						return sourceContainerPaths.stream()
								.map(sourceContainer -> new ValidationModuleFilterSpecifier(i.filter, sourceContainer,
										i.filterType, i.astRepresentation));
					} else {
						// the module specifier filter only applies to the declared source container path
						return Stream.of(i);
					}
				})
				.collect(Collectors.groupingBy(s -> s.filter + ":" + s.sourceContainerPath));

		// compute set of AST elements that declare duplicate module filters
		final Set<JSONValue> duplicateASTElements = new HashSet<>();

		// add an issue for all duplicate module filter specifiers
		duplicateGroups.entrySet().stream()
				// filter actual duplicates
				.filter(e -> e.getValue().size() > 1)
				.forEach(e -> {
					// add an issue for each duplicate but its first occurrence
					for (ValidationModuleFilterSpecifier duplicate : e.getValue()) {
						duplicateASTElements.add(duplicate.astRepresentation);
					}
				});

		// finally add one issue per duplicate-defining AST element
		duplicateASTElements.forEach(duplicateAstElement -> {
			addIssue(IssueCodes.getMessageForPKGJ_DUPLICATE_MODULE_FILTER_SPECIFIER(),
					duplicateAstElement, IssueCodes.PKGJ_DUPLICATE_MODULE_FILTER_SPECIFIER);
		});
	}

	/** Validates the given {@link ValidationModuleFilterSpecifier}. */
	private void checkModuleFilterSpecifier(ValidationModuleFilterSpecifier specifier) {
		if (specifier != null && holdsIsValidModuleFilterSpecifier(specifier)) {
			holdsModuleFilterSpecifierHasMatches(specifier);
		}
	}

	private boolean holdsIsValidModuleFilterSpecifier(ValidationModuleFilterSpecifier specifier) {
		final Set<String> sourceContainerPaths = getAllSourceContainerPaths();

		// make sure moduleFilterSpecifier.sourceContainerPath has been declared as source container
		if (specifier.sourceContainerPath != null &&
				!sourceContainerPaths.contains(specifier.sourceContainerPath)) {
			addIssue(IssueCodes.getMessageForPKGJ_SRC_IN_IN_IS_NO_DECLARED_SOURCE(specifier.sourceContainerPath),
					specifier.astRepresentation, IssueCodes.PKGJ_SRC_IN_IN_IS_NO_DECLARED_SOURCE);
			return false;
		}

		// make sure moduleFilterSpecifier does not use invalid wildcard patterns
		if (specifier.filter != null) {
			final String wrongWildcardPattern = "***";
			if (specifier.filter.contains(wrongWildcardPattern)) {
				addIssue(IssueCodes.getMessageForPKGJ_INVALID_WILDCARD(wrongWildcardPattern),
						specifier.astRepresentation,
						IssueCodes.PKGJ_INVALID_WILDCARD);
				return false;
			}
			final String wrongRelativeNavigation = "../";

			if (specifier.filter.contains(wrongRelativeNavigation)) {
				addIssue(IssueCodes.getMessageForPKGJ_NO_RELATIVE_NAVIGATION(),
						specifier.astRepresentation,
						IssueCodes.PKGJ_NO_RELATIVE_NAVIGATION);
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether the given module filter {@code specifier} matches any existing modules/files and add an
	 * appropriate issue otherwise.
	 */
	private boolean holdsModuleFilterSpecifierHasMatches(ValidationModuleFilterSpecifier specifier) {
		final URI uri = specifier.astRepresentation.eResource().getURI();
		final Path absoluteProjectPath = getAbsoluteProjectPath(uri);

		// first determine the set of source container paths to resolve the filter against (e.g. all paths or a
		// specifier path)
		Collection<String> sourceContainerPathsToCheck;
		if (specifier.sourceContainerPath != null) {
			sourceContainerPathsToCheck = Arrays.asList(specifier.sourceContainerPath);
		} else {
			sourceContainerPathsToCheck = getAllSourceContainerPaths();
		}

		final boolean hasAnyMatches = !sourceContainerPathsToCheck.stream()
				.filter(sourceContainerPath -> {
					final String basePathToCheck = absoluteProjectPath.toString() + File.separator
							+ sourceContainerPath;
					final String pathsToFind = File.separator + specifier.filter;
					final List<String> results = WildcardPathFilterUtils.collectPathsByWildcardPath(basePathToCheck,
							pathsToFind);

					// check whether this module filter matches N4JS modules/files
					if (results.stream().filter(r -> r.endsWith(N4JSGlobals.N4JS_FILE_EXTENSION)
							|| r.endsWith(N4JSGlobals.N4JS_FILE_EXTENSION)).findAny().isPresent()) {
						handleN4JSModuleMatchForModuleFilter(specifier);
					}
					return !results.isEmpty();
				}).findAny().isPresent();

		if (hasAnyMatches) {
			addIssue(IssueCodes.getMessageForPKGJ_MODULE_FILTER_DOES_NOT_MATCH(specifier.filter),
					specifier.astRepresentation, IssueCodes.PKGJ_MODULE_FILTER_DOES_NOT_MATCH);
			return false;
		}

		return true;
	}

	/**
	 * Adds an issue to the given {@link ValidationModuleFilterSpecifier} which indicates that filters of such type must
	 * never match N4JS modules/resources assuming they do in the validated project.
	 */
	private void handleN4JSModuleMatchForModuleFilter(ValidationModuleFilterSpecifier specifier) {
		addIssue(
				IssueCodes.getMessageForPKGJ_FILTER_NO_N4JS_MATCH(
						packageJsonHelper.getModuleFilterTypeRepresentation(specifier.filterType)),
				specifier.astRepresentation,
				IssueCodes.PKGJ_FILTER_NO_N4JS_MATCH);
	}

	/**
	 * Intermediate validatory-only representation of a module filter specifier.
	 */
	private static class ValidationModuleFilterSpecifier {
		final String filter;
		final String sourceContainerPath;
		final ModuleFilterType filterType;

		final JSONValue astRepresentation;

		/** Instantiates a new module filter specifier from the given values. */
		public ValidationModuleFilterSpecifier(String filter, String sourceContainerPath,
				ModuleFilterType filterType, JSONValue astRepresentation) {
			this.filter = filter;
			this.sourceContainerPath = sourceContainerPath;
			this.filterType = filterType;
			this.astRepresentation = astRepresentation;
		}

		@Override
		public String toString() {
			return "ModuleFilterSpecifer(" + filter +
					(this.sourceContainerPath != null ? " in " + this.sourceContainerPath : "") + ")";
		}
	}

	/**
	 * Validates the structure of the given {@code value} as module filter specifier and returns the information that
	 * can be extracted from it.
	 *
	 * Returns the module filter specifier information of {@code value} in terms of a
	 * {@link ValidationModuleFilterSpecifier}.
	 *
	 * Returns {@code null} if the given {@code value} is not a valid representation of a module filter specifier.
	 *
	 * Similar to {@link PackageJsonHelper#getModuleFilterSpecifier(JSONValue)} but also validates the structure along
	 * the way.
	 */
	private ValidationModuleFilterSpecifier getModuleFilterInformation(JSONValue value, ModuleFilterType type) {
		// 1st variant:
		if (value instanceof JSONStringLiteral) {
			return new ValidationModuleFilterSpecifier(((JSONStringLiteral) value).getValue(), null, type, value);
		}
		// 2nd variant:
		if (value instanceof JSONArray) {
			final List<JSONValue> elements = ((JSONArray) value).getElements();
			if (elements.size() == 2) {
				final JSONValue sourceContainer = elements.get(0);
				final JSONValue moduleFilter = elements.get(1);
				if (sourceContainer instanceof JSONStringLiteral && moduleFilter instanceof JSONStringLiteral) {
					return new ValidationModuleFilterSpecifier(
							((JSONStringLiteral) moduleFilter).getValue(),
							((JSONStringLiteral) sourceContainer).getValue(), type, value);
				}
			}
		}
		// 3rd variant:
		if (value instanceof JSONObject) {
			final List<NameValuePair> pairs = ((JSONObject) value).getNameValuePairs();

			final NameValuePair sourceContainerPair = pairs.stream()
					.filter(p -> ProjectDescriptionHelper.PROP__SOURCE_CONTAINER.equals(p.getName()))
					.findFirst().orElse(null);
			final NameValuePair moduleFilterPair = pairs.stream()
					.filter(p -> ProjectDescriptionHelper.PROP__MODULE.equals(p.getName())).findFirst()
					.orElse(null);

			// make sure the pairs are of correct type (or null in case of sourceContainerPair)
			if ((sourceContainerPair == null
					|| checkIsType(sourceContainerPair.getValue(), JSONPackage.Literals.JSON_STRING_LITERAL)) &&
					(moduleFilterPair != null
							&& checkIsType(moduleFilterPair.getValue(), JSONPackage.Literals.JSON_STRING_LITERAL))) {
				final String sourceContainer = sourceContainerPair != null
						? ((JSONStringLiteral) sourceContainerPair.getValue()).getValue()
						: null;
				final String moduleFilter = ((JSONStringLiteral) moduleFilterPair.getValue()).getValue();

				return new ValidationModuleFilterSpecifier(moduleFilter, sourceContainer, type, value);
			}
		}

		// otherwise 'value' does not represent a valid module filter specifier
		addIssue(IssueCodes.getMessageForPKGJ_INVALID_MODULE_FILTER_SPECIFIER(), value,
				IssueCodes.PKGJ_INVALID_MODULE_FILTER_SPECIFIER);

		return null;
	}

	/**
	 * Tells if for the given moduleSpecifier of the form "a/b/c/M" (without project ID) a module exists in the N4JS
	 * project with the given module specifier.
	 *
	 * Checks if a corresponding .js, .jsx, .n4js, .n4jsx, or .n4jsd file exists in any of the project's source
	 * containers.
	 */
	private boolean isExistingModule(JSONStringLiteral moduleSpecifierLiteral) {
		final URI uri = moduleSpecifierLiteral.eResource().getURI();
		final String moduleSpecifier = moduleSpecifierLiteral.getValue();
		final String relativeModulePath = moduleSpecifier.replace('/', File.separator.charAt(0));

		final Path absoluteProjectPath = getAbsoluteProjectPath(uri);

		// obtain a stream of File representations of all declared source containers
		final Stream<File> sourceFolders = getAllSourceContainerPaths().stream()
				.map(sourcePath -> new File(absoluteProjectPath.toFile(), sourcePath));

		// all file extension that represent a valid module
		final List<String> moduleExtensions = Arrays.asList(
				N4JSGlobals.N4JS_FILE_EXTENSION,
				N4JSGlobals.N4JSX_FILE_EXTENSION,
				N4JSGlobals.N4JSD_FILE_EXTENSION,
				N4JSGlobals.JS_FILE_EXTENSION,
				N4JSGlobals.JSX_FILE_EXTENSION);

		// checks whether any of the declared sourceFolders contains a file at moduleSpecifier
		// using any of the abovementioned file extensions
		return sourceFolders
				.filter(sourceFolder -> moduleExtensions.stream() // check each file extension
						.filter(ext -> new File(sourceFolder, relativeModulePath + "." + ext).exists())
						.findAny().isPresent())
				.findAny().isPresent();
	}

	/**
	 * Checks whether the given path literal represents a valid (relative) file-system path and adds a
	 * {@link IssueCodes#PKGJ_INVALID_PATH} or {@link IssueCodes#PKGJ_INVALID_ABSOLUTE_PATH} otherwise.
	 */
	private boolean holdsValidRelativePath(JSONStringLiteral pathLiteral) {
		try {
			// this will throw for invalid paths
			final Path path = Paths.get(pathLiteral.getValue());
			// check for path being relative
			if (path.isAbsolute()) {
				addIssue(IssueCodes.getMessageForPKGJ_INVALID_ABSOLUTE_PATH(pathLiteral.getValue()),
						pathLiteral, IssueCodes.PKGJ_INVALID_ABSOLUTE_PATH);
				return false;
			}
			// check for the use of the '*' character (e.g. invalid wildcards)
			if (pathLiteral.getValue().contains("*")) {
				addIssue(IssueCodes.getMessageForPKGJ_INVALID_PATH(pathLiteral.getValue()),
						pathLiteral, IssueCodes.PKGJ_INVALID_PATH);
				return false;
			}
			return true;
		} catch (InvalidPathException e) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_PATH(pathLiteral.getValue()),
					pathLiteral, IssueCodes.PKGJ_INVALID_PATH);
			return false;
		}
	}

	/**
	 * Checks whether the given {@code pathLiteral} represents an existing relative path to a directory in the project.
	 *
	 * Returns {@code false} and adds issues to {@code pathLiteral} otherwise.
	 */
	private boolean holdsExistingDirectoryPath(JSONStringLiteral pathLiteral) {
		final URI resourceURI = pathLiteral.eResource().getURI();
		final Path absoluteProjectPath = getAbsoluteProjectPath(resourceURI);

		if (absoluteProjectPath == null) {
			throw new IllegalStateException(
					"Failed to compute project path for package.json at " + resourceURI.toString());
		}

		final String relativePath = pathLiteral.getValue();
		final File file = new File(absoluteProjectPath.toString() + "/" + relativePath);

		if (!file.exists()) {
			addIssue(IssueCodes.getMessageForPKGJ_EXISTING_SOURCE_PATH(relativePath),
					pathLiteral, IssueCodes.PKGJ_EXISTING_SOURCE_PATH);
			return false;
		}

		if (!file.isDirectory()) {
			addIssue(IssueCodes.getMessageForPKGJ_EXPECTED_DIRECTORY_PATH(relativePath),
					pathLiteral, IssueCodes.PKGJ_EXPECTED_DIRECTORY_PATH);
			return false;
		}

		return true;

	}

	/**
	 * Constructs in-clause (e.g. "in external, test") for sections in which a source container path can be declared.
	 *
	 * If the source container type of {@code issueTarget} is the only section (e.g. external) in which
	 * {@code duplicates} appear, the in-clause is empty. Otherwise the in-clause lists all source container types for
	 * which duplicates have been declared.
	 */
	private String createInSourceContainerTypeClause(JSONStringLiteral issueTarget,
			List<JSONStringLiteral> duplicates) {
		final SourceContainerType targetContainerType = getSourceContainerType(issueTarget);
		final Set<SourceContainerType> otherTypes = duplicates.stream()
				.filter(d -> d != issueTarget)
				.map(d -> getSourceContainerType(d))
				.collect(Collectors.toSet());

		// if issueTarget's type is the only type for which there have been declared duplicate paths
		if (otherTypes.size() == 1 && otherTypes.iterator().next() == targetContainerType) {
			return ""; // do not use an in-clause
		}

		// otherwise list all other types for which the path of issueTarget has been declared as well
		return " in " + otherTypes.stream().map(t -> t.getLiteral().toLowerCase())
				.collect(Collectors.joining(", "));
	}

	/**
	 * Returns the absolute path of the N4JS project that contains the given {@code nestedLocation}.
	 *
	 * Returns {@code null} if no N4JS project can be found that contains the given {@code nestedLocation}.
	 */
	private Path getAbsoluteProjectPath(URI nestedLocation) {
		Optional<? extends IN4JSProject> n4jsProject = n4jsCore.findProject(nestedLocation);
		if (!n4jsProject.isPresent()) {
			return null;
		}
		return n4jsProject.get().getLocationPath().toAbsolutePath();
	}

	/**
	 * Find path duplicate groups of {@link JSONStringLiteral}s in the given list of {@code paths}.
	 *
	 * Before comparison the string values of the given literals are interpreted as paths and normalized appropriately.
	 *
	 * Duplicate Groups are groups of more than one {@link JSONStringLiteral} that share the same path value.
	 */
	private List<List<JSONStringLiteral>> findPathDuplicates(List<JSONStringLiteral> paths) {

		// group literals by the normalized path they represent
		Map<String, List<JSONStringLiteral>> groupedStrings = paths.stream()
				.collect(Collectors.groupingBy(s -> FileUtils.normalize(s.getValue())));

		// find groups of duplicates and return them (more than one literal per path)
		return groupedStrings.entrySet().stream()
				.filter(entry -> entry.getValue().size() > 1)
				.map(entry -> entry.getValue())
				.collect(Collectors.toList());
	}

	/**
	 * Returns the set of all declared source container paths in the currently validated {@code package.json} file.
	 */
	private Set<String> getAllSourceContainerPaths() {
		return this.getSourceContainers().entries().stream()
				.flatMap(e -> e.getValue().stream())
				.map(literal -> literal.getValue())
				.collect(Collectors.toSet());
	}

	/**
	 * Returns the project type as declared by the currently validated {@link JSONDocument}.
	 *
	 * Returns {@link ProjectType#VALIDATION} if the project type cannot be determined.
	 */
	private ProjectType getProjectType() {
		final JSONValue projectTypeValue = getSingleDocumentValue(
				ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__PROJECT_TYPE);
		if (projectTypeValue instanceof JSONStringLiteral) {
			return packageJsonHelper.getProjectType(projectTypeValue);
		} else {
			return ProjectType.get(0);
		}
	}

	/**
	 * Returns the source container information that can be extracted from the currently validated {@link JSONDocument}.
	 *
	 * Adds validation issues in case of an invalid source container structure.
	 */
	private Multimap<SourceContainerType, List<JSONStringLiteral>> getSourceContainers() {
		return contextMemoize(N4JS_SOURCE_CONTAINERS, this::doGetSourceContainers);
	}

	/**
	 * Validates the correct structure of a {@link ProjectDescriptionHelper#PROP__SOURCES} section and returns a map
	 * between the declared source container types and corresponding {@link JSONStringLiteral}s that specify the various
	 * source container paths.
	 */
	private Multimap<SourceContainerType, List<JSONStringLiteral>> doGetSourceContainers() {
		final Collection<JSONValue> sourcesValues = getDocumentValues(
				ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__SOURCES);

		// first check whether n4js.sources section has been defined at all
		if (sourcesValues.isEmpty()) {
			// return an empty map
			return ImmutableMultimap.<SourceContainerType, List<JSONStringLiteral>> of();
		}

		// first check type of all occuring 'sources' sections
		if (!checkIsType(sourcesValues, JSONPackage.Literals.JSON_OBJECT, "as source container section")) {
			// return an empty map
			return ImmutableMultimap.<SourceContainerType, List<JSONStringLiteral>> of();
		}

		// only consider the first n4js.sources section for further validation (in case of duplicates)
		final JSONValue sourcesValue = sourcesValues.iterator().next();
		final JSONObject sourceContainerObject = (JSONObject) sourcesValue;

		final Multimap<SourceContainerType, List<JSONStringLiteral>> sourceContainerValues = HashMultimap.create();

		for (NameValuePair pair : sourceContainerObject.getNameValuePairs()) {
			final String sourceContainerType = pair.getName();

			// check that sourceContainerType represents a valid source container type
			if (!isValidSourceContainerTypeLiteral(sourceContainerType)) {
				addIssue(IssueCodes.getMessageForPKGJ_INVALID_SOURCE_CONTAINER_TYPE(sourceContainerType),
						pair, JSONPackage.Literals.NAME_VALUE_PAIR__NAME,
						IssueCodes.PKGJ_INVALID_SOURCE_CONTAINER_TYPE);
				continue;
			}
			// compute type of source container sub-section
			final SourceContainerType containerType = SourceContainerType.get(pair.getName().toUpperCase());

			// check type of RHS (list of source paths)
			if (!checkIsType(pair.getValue(), JSONPackage.Literals.JSON_ARRAY, "as source container list")) {
				continue;
			}
			final JSONArray sourceContainerSpecifiers = (JSONArray) pair.getValue();
			// collect all source container paths in this list
			final List<JSONStringLiteral> specifierLiterals = new ArrayList<>();

			for (JSONValue specifier : sourceContainerSpecifiers.getElements()) {
				if (!checkIsType(specifier, JSONPackage.Literals.JSON_STRING_LITERAL,
						"as source container specifier")) {
					continue;
				}
				specifierLiterals.add((JSONStringLiteral) specifier);
			}

			// This may override a value in case of a duplicate containerType (e.g. two external sections).
			// However, this will also issue an appropriate warning for a duplicate key and
			// is therefore not handled here.
			sourceContainerValues.put(containerType, specifierLiterals);
		}

		return sourceContainerValues;
	}

	/**
	 * Returns the {@link SourceContainerType} of the given {@code containerSpecifierLiteral}.
	 */
	private SourceContainerType getSourceContainerType(JSONStringLiteral containerSpecifierLiteral) {
		// first check within limits whether the AST structure is valid
		if (!(containerSpecifierLiteral.eContainer() instanceof JSONArray &&
				containerSpecifierLiteral.eContainer().eContainer() instanceof NameValuePair &&
				isValidSourceContainerTypeLiteral(
						((NameValuePair) containerSpecifierLiteral.eContainer().eContainer()).getName()))) {
			return null;
		}
		final NameValuePair containerTypeAssignment = (NameValuePair) containerSpecifierLiteral.eContainer()
				.eContainer();
		return SourceContainerType.get(containerTypeAssignment.getName().toUpperCase());
	}

	/**
	 * Returns {@code true} iff the given {@code typeLiteral} represents a valid SourceContainerType (e.g. source,
	 * test).
	 */
	private boolean isValidSourceContainerTypeLiteral(String typeLiteral) {
		// check that typeLiteral is all lower-case and a corresponding enum literal exists
		return typeLiteral.toLowerCase().equals(typeLiteral) &&
				SourceContainerType.get(typeLiteral.toUpperCase()) != null;
	}
}
