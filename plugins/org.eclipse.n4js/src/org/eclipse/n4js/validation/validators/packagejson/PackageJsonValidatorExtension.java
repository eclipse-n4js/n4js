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

import static org.eclipse.n4js.json.model.utils.JSONModelUtils.asNonEmptyStringOrNull;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEFINES_PACKAGE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEV_DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.EXTENDED_RUNTIME_ENVIRONMENT;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.IMPLEMENTATION_ID;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.IMPLEMENTED_PROJECTS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.MAIN_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.MODULE_FILTERS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.N4JS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NAME;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NV_MODULE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NV_SOURCE_CONTAINER;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROJECT_TYPE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROVIDED_RUNTIME_LIBRARIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.REQUIRED_RUNTIME_LIBRARIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.SOURCES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.TESTED_PROJECTS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VENDOR_ID;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VENDOR_NAME;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VERSION;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.packagejson.PackageJsonUtils;
import org.eclipse.n4js.projectDescription.ModuleFilterType;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.Semver.GitHubVersionRequirement;
import org.eclipse.n4js.semver.Semver.LocalPathVersionRequirement;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.Semver.SimpleVersion;
import org.eclipse.n4js.semver.Semver.TagVersionRequirement;
import org.eclipse.n4js.semver.Semver.URLVersionRequirement;
import org.eclipse.n4js.semver.Semver.VersionRangeConstraint;
import org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.ProjectDescriptionUtils.ProjectNameInfo;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.Issue;

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
 * {@link N4JSProjectSetupJsonValidatorExtension}.
 */
@Singleton
public class PackageJsonValidatorExtension extends AbstractJSONValidatorExtension {

	/** key for memoization of the n4js.sources section of a package.json. See #getSourceContainers(). */
	private static final String N4JS_SOURCE_CONTAINERS = "N4JS_SOURCE_CONTAINERS";

	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private XpectAwareFileExtensionCalculator fileExtensionCalculator;
	@Inject
	private SemverHelper semverHelper;

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
	@CheckProperty(property = NAME)
	public void checkName(JSONValue projectNameValue) {
		// first check for the type of the name value
		if (!checkIsType(projectNameValue, JSONPackage.Literals.JSON_STRING_LITERAL, "as package name")) {
			return;
		}
		final JSONStringLiteral projectNameLiteral = (JSONStringLiteral) projectNameValue;
		final String projectName = projectNameLiteral.getValue();
		final String projectNameWithoutScope = ProjectDescriptionUtils.getPlainProjectName(projectName);
		final String scopeName = ProjectDescriptionUtils.getScopeName(projectName);

		// make sure the name conforms to the IDENTIFIER_PATTERN
		if (!ProjectDescriptionUtils.isValidPlainProjectName(projectNameWithoutScope)) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_PROJECT_NAME(projectNameWithoutScope),
					projectNameValue, IssueCodes.PKGJ_INVALID_PROJECT_NAME);
		}
		if (scopeName != null) {
			String scopeNameWithoutPrefix = scopeName.substring(1);
			if (!ProjectDescriptionUtils.isValidScopeName(scopeNameWithoutPrefix)) {
				String msg = IssueCodes.getMessageForPKGJ_INVALID_SCOPE_NAME(scopeNameWithoutPrefix);
				addIssue(msg, projectNameValue, IssueCodes.PKGJ_INVALID_SCOPE_NAME);
			}
		}

		// compute names of project folder, parent folder, Eclipse project folder
		final URI projectUri = projectNameValue.eResource().getURI().trimSegments(1);
		final ProjectNameInfo nameInfo = ProjectNameInfo.of(projectUri);

		// make sure the project name equals the name of the project folder
		if (!projectNameWithoutScope.equals(nameInfo.projectFolderName)) {
			String msg = IssueCodes.getMessageForPKGJ_PACKAGE_NAME_MISMATCH(
					projectNameWithoutScope, nameInfo.projectFolderName);
			addIssue(msg, projectNameLiteral, IssueCodes.PKGJ_PACKAGE_NAME_MISMATCH);
		}

		// make sure the scope name (if any) equals the name of the parent folder
		// (i.e. the folder containing the project folder)
		if (scopeName != null && !scopeName.equals(nameInfo.parentFolderName)) {
			String msg = IssueCodes.getMessageForPKGJ_SCOPE_NAME_MISMATCH(scopeName, nameInfo.parentFolderName);
			addIssue(msg, projectNameLiteral, IssueCodes.PKGJ_SCOPE_NAME_MISMATCH);
		}

		// make sure the project name equals the name of the Eclipse workspace project
		if (Platform.isRunning()) {

			if (!nameInfo.eclipseProjectName.isPresent()) {
				// Eclipse project name cannot be determined, fail gracefully. We currently assume that this case will
				// only occur (1) in the headless case and (2) in the UI case when a nested package.json is validated in
				// an editor (we ignore nested package.json files in the builder)
				return;
			}

			String expectedEclipseProjectName = ProjectDescriptionUtils
					.convertN4JSProjectNameToEclipseProjectName(projectName);
			if (!expectedEclipseProjectName.equals(nameInfo.eclipseProjectName.get())) {
				String msg = IssueCodes.getMessageForPKGJ_PROJECT_NAME_ECLIPSE_MISMATCH(
						expectedEclipseProjectName, nameInfo.eclipseProjectName.get());
				addIssue(msg, projectNameLiteral, IssueCodes.PKGJ_PROJECT_NAME_ECLIPSE_MISMATCH);
			}
		}
	}

	/** Check the version property. */
	@CheckProperty(property = VERSION)
	public void checkVersion(JSONValue versionValue) {
		if (!checkIsType(versionValue, JSONPackage.Literals.JSON_STRING_LITERAL, "as package version")) {
			return;
		}
		JSONStringLiteral versionJsonString = (JSONStringLiteral) versionValue;
		String versionString = versionJsonString.getValue();

		IParseResult parseResult = validateSemver(versionValue, versionString);

		NPMVersionRequirement npmVersion = semverHelper.parse(parseResult);
		VersionRangeSetRequirement vrs = semverHelper.parseVersionRangeSet(parseResult);
		if (vrs == null) {
			String reason = "Cannot parse given string";
			if (npmVersion != null) {
				reason = "Given string is parsed as " + getVersionRequirementType(npmVersion);
			}
			String msg = IssueCodes.getMessageForPKGJ_INVALID_VERSION_NUMBER(versionString, reason);
			addIssue(msg, versionValue, IssueCodes.PKGJ_INVALID_VERSION_NUMBER);
			return;
		}

		if (!vrs.getRanges().isEmpty() && vrs.getRanges().get(0) instanceof VersionRangeConstraint) {
			VersionRangeConstraint vrc = (VersionRangeConstraint) vrs.getRanges().get(0);
			SimpleVersion simpleVersion = vrc.getVersionConstraints().get(0);
			if (!simpleVersion.getComparators().isEmpty()) {
				String comparator = SemverSerializer.serialize(simpleVersion.getComparators().get(0));
				String reason = "Version numbers must not have comparators: '" + comparator + "'";
				String msg = IssueCodes.getMessageForPKGJ_INVALID_VERSION_NUMBER(versionString, reason);
				addIssue(msg, versionValue, IssueCodes.PKGJ_INVALID_VERSION_NUMBER);
				return;
			}
		}
	}

	private IParseResult validateSemver(JSONValue versionValue, String versionString) {
		IParseResult parseResult = semverHelper.getParseResult(versionString);

		if (parseResult.hasSyntaxErrors()) {
			Iterator<INode> errorIterator = parseResult.getSyntaxErrors().iterator();

			while (errorIterator.hasNext()) {
				INode firstErrorNode = errorIterator.next();

				String reason = firstErrorNode.getSyntaxErrorMessage().getMessage();
				String msg = IssueCodes.getMessageForPKGJ_INVALID_VERSION_NUMBER(versionString, reason);

				ICompositeNode actualNode = NodeModelUtils.findActualNodeFor(versionValue);
				int actOffset = actualNode.getOffset();
				int actLength = actualNode.getLength();
				int offset = actOffset + firstErrorNode.getOffset() + 1; // +1 due to " char
				int lengthTmp = actLength - firstErrorNode.getOffset() - 2; // -2 due to "" chars
				int length = Math.max(1, lengthTmp);
				addIssue(msg, versionValue, offset, length, IssueCodes.PKGJ_INVALID_VERSION_NUMBER);
			}
			return parseResult;
		}

		List<Issue> issues = semverHelper.validate(parseResult);
		for (Issue issue : issues) {
			String msg = "";
			String issueCode = IssueCodes.PKGJ_INVALID_VERSION_NUMBER;
			switch (issue.getSeverity()) {
			case WARNING:
				msg = IssueCodes.getMessageForPKGJ_SEMVER_WARNING(issue.getMessage());
				issueCode = IssueCodes.PKGJ_SEMVER_WARNING;
				break;
			case ERROR:
				msg = IssueCodes.getMessageForPKGJ_SEMVER_ERROR(issue.getMessage());
				issueCode = IssueCodes.PKGJ_SEMVER_ERROR;
				break;
			default:
				break;
			}

			ICompositeNode actualNode = NodeModelUtils.findActualNodeFor(versionValue);
			int offset = actualNode.getOffset() + issue.getOffset() + 1;
			int length = issue.getLength();
			addIssue(msg, versionValue, offset, length, issueCode);
		}

		return parseResult;
	}

	private String getVersionRequirementType(NPMVersionRequirement npmVersion) {
		if (npmVersion instanceof TagVersionRequirement) {
			return "tag";
		}
		if (npmVersion instanceof URLVersionRequirement) {
			return "url";
		}
		if (npmVersion instanceof GitHubVersionRequirement) {
			return "github location";
		}
		if (npmVersion instanceof LocalPathVersionRequirement) {
			return "local path";
		}
		return "unknown";
	}

	/** Check the dependencies section structure. */
	@CheckProperty(property = DEPENDENCIES)
	public void checkDependenciesStructure(JSONValue dependenciesValue) {
		checkIsDependenciesSection(dependenciesValue);
	}

	/** Check the devDependencies section structure. */
	@CheckProperty(property = DEV_DEPENDENCIES)
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
			final JSONValue versionRequirement = entry.getValue();
			if (checkIsType(versionRequirement, JSONPackage.Literals.JSON_STRING_LITERAL, "as version specifier")) {
				JSONStringLiteral jsonStringVersionRequirement = (JSONStringLiteral) versionRequirement;
				String constraintValue = jsonStringVersionRequirement.getValue();
				validateSemver(jsonStringVersionRequirement, constraintValue);
			}
		}
	}

	/** Checks basic structural properties of the 'n4js' section (e.g. mandatory properties). */
	@CheckProperty(property = N4JS)
	public void checkN4JSSection(JSONValue n4jsSection) {
		// make sure n4js section is an object
		if (!checkIsType(n4jsSection, JSONPackage.Literals.JSON_OBJECT,
				"as package.json n4js section.")) {
			return;
		}

		JSONObject n4jsSectionJO = (JSONObject) n4jsSection;
		final Multimap<String, JSONValue> n4jsValues = collectObjectValues(n4jsSectionJO);

		// Check for correct types (null-values (non-existent) will not lead to issues)
		// Properties that are not checked here, have their own check-method which also validates their types.
		checkIsType(n4jsValues.get(VENDOR_ID.name),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as vendor ID");
		checkIsType(n4jsValues.get(VENDOR_NAME.name),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as vendor name");
		checkIsType(n4jsValues.get(OUTPUT.name),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as output folder path");

		checkIsType(n4jsValues.get(EXTENDED_RUNTIME_ENVIRONMENT.name),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as reference to extended runtime environment");
		checkIsArrayOfType(n4jsValues.get(PROVIDED_RUNTIME_LIBRARIES.name),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as provided runtime libraries", "as library reference");
		checkIsArrayOfType(n4jsValues.get(REQUIRED_RUNTIME_LIBRARIES.name),
				JSONPackage.Literals.JSON_STRING_LITERAL, "as required runtime libraries", "as library reference");

		// Check for empty strings
		checkIsNonEmptyString(n4jsValues.get(VENDOR_ID.name), VENDOR_ID);
		checkIsNonEmptyString(n4jsValues.get(VENDOR_NAME.name), VENDOR_NAME);

		Set<String> allN4JSPropertyNames = PackageJsonProperties.getAllN4JSPropertyNames();
		for (NameValuePair nameValuePair : n4jsSectionJO.getNameValuePairs()) {
			String nvpName = nameValuePair.getName();
			if (!allN4JSPropertyNames.contains(nvpName)) {
				String msg = IssueCodes.getMessageForPKGJ_PROPERTY_UNKNOWN(nvpName);
				addIssue(msg, nameValuePair, JSONPackage.Literals.NAME_VALUE_PAIR__NAME,
						IssueCodes.PKGJ_PROPERTY_UNKNOWN);
			}
		}
	}

	/** Check the projectType value structure and limitations. */
	@CheckProperty(property = PROJECT_TYPE)
	public void checkProjectType(JSONValue projectTypeValue) {
		if (!checkIsType(projectTypeValue, JSONPackage.Literals.JSON_STRING_LITERAL)) {
			return;
		}
		if (!checkIsNonEmptyString((JSONStringLiteral) projectTypeValue, PROJECT_TYPE)) {
			return;
		}

		// check whether the given value represents a valid project type
		final String projectTypeString = ((JSONStringLiteral) projectTypeValue).getValue();
		final ProjectType type = PackageJsonUtils.parseProjectType(projectTypeString);

		// check type can be parsed successfully
		if (type == null) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_PROJECT_TYPE(projectTypeString),
					projectTypeValue, IssueCodes.PKGJ_INVALID_PROJECT_TYPE);
			return;
		}

		// check limitations of specific project types

		final boolean isDefType = type == ProjectType.DEFINITION;
		final JSONValue propDefinesPck = getSingleDocumentValue(DEFINES_PACKAGE);
		final boolean hasDefPck = propDefinesPck != null;
		if (isDefType != hasDefPck) {
			EObject issueObj = propDefinesPck == null ? projectTypeValue : propDefinesPck.eContainer();
			String not = propDefinesPck == null ? "" : "not ";
			String msg = IssueCodes.getMessageForPKGJ_DEFINES_PROPERTY(type.toString(), not, "definesPackage");
			addIssue(msg, issueObj, IssueCodes.PKGJ_DEFINES_PROPERTY);
		}

		if (isRequiresOutputAndSourceFolder(type)) {
			// make sure non-validation projects always declare an output and at least one source folder
			final boolean hasSources = getSingleDocumentValue(SOURCES) != null;
			final boolean hasOutput = getSingleDocumentValue(OUTPUT) != null;
			if (!hasSources || !hasOutput) {
				String msg = IssueCodes.getMessageForPKGJ_PROJECT_TYPE_MANDATORY_OUTPUT_AND_SOURCES(projectTypeString);
				addIssue(msg, projectTypeValue, IssueCodes.PKGJ_PROJECT_TYPE_MANDATORY_OUTPUT_AND_SOURCES);
			}
		}
	}

	/**
	 * Returns {@code true} iff the given project type requires the declaration of at least one output and source
	 * folder.
	 */
	private boolean isRequiresOutputAndSourceFolder(ProjectType type) {
		return type != ProjectType.DEFINITION &&
				type != ProjectType.VALIDATION &&
				type != ProjectType.PLAINJS;
	}

	/**
	 * Checks all given {@code values} to be {@link JSONStringLiteral} with non-empty string value.
	 *
	 * Skips values of non {@link JSONStringLiteral} type.
	 *
	 * @See {@link #checkIsType(JSONValue, EClass, String)}
	 * @See {@link #checkIsNonEmptyString(JSONStringLiteral, PackageJsonProperties)}
	 */
	private boolean checkIsNonEmptyString(Iterable<JSONValue> values, PackageJsonProperties property) {
		boolean overallResult = true;
		for (JSONValue value : values) {
			if (value instanceof JSONStringLiteral) {
				overallResult &= checkIsNonEmptyString((JSONStringLiteral) value, property);
			}
		}
		return overallResult;
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
	@CheckProperty(property = SOURCES)
	public void checkSourceContainers() {
		// obtain source-container-related content of the section and validate its structure
		Multimap<SourceContainerType, List<JSONStringLiteral>> sourceContainers = getSourceContainers();

		final List<JSONStringLiteral> allDeclaredSourceContainers = sourceContainers.entries().stream()
				// flat-map to a list of all source containers that are specified (for all types combined)
				.flatMap(entry -> entry.getValue().stream())
				.collect(Collectors.toList());

		// check each source container sub-section (e.g. sources, external, etc.)
		final List<JSONStringLiteral> validSourceContainerLiterals = allDeclaredSourceContainers.stream()
				.filter(l -> internalCheckSourceContainerLiteral(l)).collect(Collectors.toList());

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

		// check for nested source containers (within valid source container literals)
		internalCheckNoNestedSourceContainers(validSourceContainerLiterals);
	}

	/**
	 * Validates that the given list of declared source container paths do not declare source containers that are nested
	 * within each other.
	 *
	 * This validation runs in O(n^2) wrt. the number of source containers.
	 */
	private void internalCheckNoNestedSourceContainers(final List<JSONStringLiteral> sourceContainers) {
		// collect conflicting prefixes (containers) per offending source container path (string literal)
		final Multimap<ASTTraceable<Path>, ASTTraceable<Path>> conflictingPrefixes = HashMultimap.create();

		final List<ASTTraceable<Path>> containerPaths = sourceContainers.stream()
				.map(ASTTraceable.map(l -> new File(l.getValue()).toPath().normalize()))
				.collect(Collectors.toList());

		// check paths that prefix each other
		for (ASTTraceable<Path> path : containerPaths) {
			for (ASTTraceable<Path> otherPaths : containerPaths) {
				// do not check with itself
				if (path == otherPaths) {
					continue;
				}
				// skip exact matches, this is detected in duplicate validation
				if (path.element.equals(otherPaths.element)) {
					continue;
				}
				if (path.element.startsWith(otherPaths.element) || otherPaths.element.toString().isEmpty()) {
					conflictingPrefixes.put(path, otherPaths);
				}
			}
		}

		// eventually, add issues for all offending source container literals
		for (ASTTraceable<Path> path : conflictingPrefixes.keySet()) {
			final Collection<ASTTraceable<Path>> containers = conflictingPrefixes.get(path);
			final String containersDescription = containers.stream()
					.map(c -> "\"" + ((JSONStringLiteral) c.astElement).getValue() + "\"")
					.sorted((s1, s2) -> s1.length() - s2.length()) // sort by ascending length
					.collect(Collectors.joining(", "));

			addIssue(IssueCodes.getMessageForPKGJ_NESTED_SOURCE_CONTAINER(containersDescription), path.astElement,
					IssueCodes.PKGJ_NESTED_SOURCE_CONTAINER);
		}
	}

	/** Checks a single source container literal for validity. */
	private boolean internalCheckSourceContainerLiteral(JSONStringLiteral containerLiteral) {
		// check path for empty strings
		if (containerLiteral.getValue().isEmpty()) {
			addIssue(IssueCodes.getMessageForPKGJ_EMPTY_SOURCE_PATH(), containerLiteral,
					IssueCodes.PKGJ_EMPTY_SOURCE_PATH);
			return false;
		}

		return holdsValidRelativePath(containerLiteral) && // check path for validity
				holdsExistingDirectoryPath(containerLiteral); // check directory for existence
	}

	/**
	 * Checks the <code>n4js.mainModule</code> property of the {@code package.json}.
	 */
	@CheckProperty(property = MAIN_MODULE)
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
	@CheckProperty(property = IMPLEMENTATION_ID)
	public void checkImplementationId(JSONValue value) {
		final JSONArray implementedProjectsValue = getSingleDocumentValue(IMPLEMENTED_PROJECTS, JSONArray.class);

		// check basic constraints
		if (!checkIsType(value, JSONPackage.Literals.JSON_STRING_LITERAL, "as implementation ID")) {
			return;
		}
		if (!checkIsNonEmptyString((JSONStringLiteral) value, IMPLEMENTATION_ID)) {
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
	@CheckProperty(property = IMPLEMENTED_PROJECTS)
	public void checkImplementedProjects(JSONValue value) {
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
		final JSONStringLiteral declaredProjectNameValue = getSingleDocumentValue(NAME, JSONStringLiteral.class);

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
	}

	/**
	 * Checks the n4js.testedProjects section of the {@code package.json}.
	 */
	@CheckProperty(property = TESTED_PROJECTS)
	public void checkTestedProjects(JSONValue testedProjectsValues) {
		if (!checkIsArrayOfType(testedProjectsValues, JSONPackage.Literals.JSON_STRING_LITERAL,
				"as list of tested projects", "as tested project reference")) {
			return;
		}
	}

	/**
	 * Converts the given {@code relativePath} to an absolute URI by resolving it based on the location of
	 * {@code resource}.
	 */
	private URI getResourceRelativeURI(Resource resource, String relativePath) {
		final String normalizedStringPath = FileUtils.normalize(relativePath);
		final URI relativeLocation = URI.createURI(normalizedStringPath);
		// obtain URI of resource container (including trailing slash)
		final URI resourceContainerLocation = resource.getURI().trimSegments(1).appendSegment("");
		return relativeLocation.resolve(resourceContainerLocation);
	}

	/**
	 * Checks that the output folder and the declared source containers are not nested in way that can lead to conflicts
	 * wrt. transpile loops and workspace clean operations (e.g. output folder is considered source folder).
	 *
	 * This check runs on the whole {@link JSONDocument}, since we must also validate in case of the implicit output
	 * folder as given by {@link PackageJsonProperties#OUTPUT}.
	 */
	@Check
	public void checkOutputFolder(@SuppressWarnings("unused") JSONDocument document) {
		final JSONValue outputPathValue = getSingleDocumentValue(OUTPUT);

		// only check basic JSONValue constraints, when an explicit outputPathValue is present
		if (outputPathValue != null) {
			if ((!(outputPathValue instanceof JSONStringLiteral))) {
				return;
			}

			// check value to be non-empty
			if (!checkIsNonEmptyString((JSONStringLiteral) outputPathValue, OUTPUT)) {
				return;
			}
		}

		if (outputPathValue != null) {
			// if available, run check with explicitly declared output folder
			internalCheckOutput(((JSONStringLiteral) outputPathValue).getValue(),
					Optional.fromNullable(outputPathValue));
		} else {
			// otherwise, run check with default value for output folder
			internalCheckOutput(OUTPUT.defaultValue, Optional.absent());
		}
	}

	/**
	 * Checks the given {@code outputPath} for validity.
	 *
	 * @param astOutputValue
	 *            If present, the ast representation. May be {@code null} if {@code outputPath} is a default value.
	 */
	private void internalCheckOutput(String outputPath, Optional<JSONValue> astOutputValue) {
		final Resource resource = getDocument().eResource();
		final URI absoluteOutputLocation = getResourceRelativeURI(resource, outputPath);

		// forbid output folder for 'definition' projects
		final ProjectType projectType = getProjectType();
		if (projectType == ProjectType.DEFINITION && astOutputValue.isPresent()) {
			String message = IssueCodes.getMessageForPKGJ_DEFINES_PROPERTY(projectType.name(), "not ", "output");
			addIssue(message, astOutputValue.get().eContainer(), IssueCodes.PKGJ_DEFINES_PROPERTY);
		}
		// do not perform check for projects which do not require an output folder
		if (!isRequiresOutputAndSourceFolder(projectType)) {
			return;
		}

		final Multimap<SourceContainerType, List<JSONStringLiteral>> sourceContainers = getSourceContainers();

		for (Entry<SourceContainerType, List<JSONStringLiteral>> sourceContainerType : sourceContainers.entries()) {
			// iterate over all source container paths (in terms of string literals)
			for (JSONStringLiteral sourceContainerSpecifier : sourceContainerType.getValue()) {
				// compute absolute source container location
				final URI absoluteSourceLocation = getResourceRelativeURI(resource,
						sourceContainerSpecifier.getValue());

				// obtain descriptive name of the current source container type
				final String srcFrgmtName = sourceContainerType.getKey().getLiteral().toLowerCase();

				// handle case that source container is nested within output directory (or equal)
				if (isContainedOrEqual(absoluteSourceLocation, absoluteOutputLocation)) {
					final String containingFolder = ("A " + srcFrgmtName + " folder");
					final String nestedFolder = astOutputValue.isPresent() ? "the output folder"
							: "the default output folder \"" + OUTPUT.defaultValue + "\"";
					final String message = IssueCodes
							.getMessageForOUTPUT_AND_SOURCES_FOLDER_NESTING(containingFolder, nestedFolder);

					addIssue(message, sourceContainerSpecifier, IssueCodes.OUTPUT_AND_SOURCES_FOLDER_NESTING);
				}

				// if "output" AST element is available (outputPath is not a default value)
				if (astOutputValue.isPresent()) {
					// handle case that output path is nested within a source folder (or equal)
					if (isContainedOrEqual(absoluteOutputLocation, absoluteSourceLocation)) {
						final String containingFolder = "The output folder";
						final String nestedFolder = ("a " + srcFrgmtName + " folder");
						final String message = IssueCodes
								.getMessageForOUTPUT_AND_SOURCES_FOLDER_NESTING(containingFolder, nestedFolder);

						addIssue(message, astOutputValue.get(), IssueCodes.OUTPUT_AND_SOURCES_FOLDER_NESTING);
					}
				}
			}
		}
	}

	private boolean isContainedOrEqual(URI uri, URI container) {
		if (uri.equals(container)) {
			return true;
		}
		if (!container.hasTrailingPathSeparator()) {
			container = container.appendSegment("");
		}
		URI relative = uri.deresolve(container, true, true, false);
		if (relative != uri) {
			if (relative.isEmpty()) {
				return true;
			}
			if ("..".equals(relative.segment(0))) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks the n4js.moduleFilters section of the {@code package.json}.
	 */
	@CheckProperty(property = MODULE_FILTERS)
	public void checkModuleFilters(JSONValue moduleFilterSection) {
		if (!checkIsType(moduleFilterSection, JSONPackage.Literals.JSON_OBJECT, "as moduleFilters section")) {
			return;
		}

		for (NameValuePair pair : ((JSONObject) moduleFilterSection).getNameValuePairs()) {
			internalCheckModuleFilterEntry(pair);
		}
	}

	/**
	 * Checks whether the given {@code moduleFilterPair} represents a valid module-filter section entry (e.g. noValidate
	 * section).
	 */
	private void internalCheckModuleFilterEntry(NameValuePair moduleFilterPair) {
		// obtain enum-representation of the validated module filter type
		final ModuleFilterType filterType = PackageJsonUtils.parseModuleFilterType(moduleFilterPair.getName());

		// make sure the module filter type could be parsed successfully
		if (filterType == null) {
			final String message = IssueCodes.getMessageForPKGJ_INVALID_MODULE_FILTER_TYPE(
					moduleFilterPair.getName(), "noValidate");
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

		// compute set of all duplicate module filter specifiers
		final Set<JSONValue> duplicateFilterSpecifiers = new HashSet<>();

		// add an issue for all duplicate module filter specifiers
		duplicateGroups.entrySet().stream()
				// filter actual duplicate groups (more than one entry)
				.filter(e -> e.getValue().size() > 1)
				// add an issue for all duplicates but their first occurrences
				.flatMap(group -> group.getValue().stream().skip(1))
				.forEach(duplicate -> duplicateFilterSpecifiers.add(duplicate.astRepresentation));

		for (JSONValue duplicateFilterSpecifier : duplicateFilterSpecifiers) {
			addIssue(IssueCodes.getMessageForPKGJ_DUPLICATE_MODULE_FILTER_SPECIFIER(),
					duplicateFilterSpecifier, IssueCodes.PKGJ_DUPLICATE_MODULE_FILTER_SPECIFIER);
		}
	}

	/** Validates the given {@link ValidationModuleFilterSpecifier}. */
	private void checkModuleFilterSpecifier(ValidationModuleFilterSpecifier specifier) {
		final Set<String> sourceContainerPaths = getAllSourceContainerPaths();

		if (specifier != null) {
			// make sure moduleFilterSpecifier.sourceContainerPath has been declared as source container
			if (specifier.sourceContainerPath != null &&
					!sourceContainerPaths.contains(specifier.sourceContainerPath)) {
				addIssue(
						IssueCodes.getMessageForPKGJ_SRC_IN_FILTER_IS_NO_DECLARED_SOURCE(specifier.sourceContainerPath),
						specifier.astRepresentation, IssueCodes.PKGJ_SRC_IN_FILTER_IS_NO_DECLARED_SOURCE);
			}
		}
	}

	/**
	 * Intermediate validator-only representation of a module filter specifier.
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
	 * Similar to {@link PackageJsonUtils#asModuleFilterSpecifierOrNull(JSONValue)} but also validates the structure
	 * along the way.
	 */
	private ValidationModuleFilterSpecifier getModuleFilterInformation(JSONValue value, ModuleFilterType type) {
		// 1st variant:
		if (value instanceof JSONStringLiteral) {
			return new ValidationModuleFilterSpecifier(((JSONStringLiteral) value).getValue(), null, type, value);
		}
		// 2nd variant:
		if (value instanceof JSONObject) {
			final List<NameValuePair> pairs = ((JSONObject) value).getNameValuePairs();

			final NameValuePair sourceContainerPair = pairs.stream()
					.filter(p -> NV_SOURCE_CONTAINER.name.equals(p.getName()))
					.findFirst().orElse(null);
			final NameValuePair moduleFilterPair = pairs.stream()
					.filter(p -> NV_MODULE.name.equals(p.getName())).findFirst()
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
	 * Checks whether the given {@code pathLiteral} represents an existing relative path to the currently validated
	 * {@link Resource}.
	 *
	 * Returns {@code false} and adds issues to {@code pathLiteral} otherwise.
	 */
	private boolean holdsExistingDirectoryPath(JSONStringLiteral pathLiteral) {
		final URI resourceURI = pathLiteral.eResource().getURI();
		final Optional<? extends IN4JSProject> n4jsProject = n4jsCore.findProject(resourceURI);

		if (!n4jsProject.isPresent()) {
			// container project cannot be determined, fail gracefully (validation running on non-N4JS project?)
			return true;
		}

		final URI projectLocation = n4jsProject.get().getLocation().toURI();
		// resolve against project uri with trailing slash
		final URI projectRelativeResourceURI = resourceURI.deresolve(projectLocation.appendSegment(""));

		final Path absoluteProjectPath = n4jsProject.get().getLocation().toFileSystemPath();
		if (absoluteProjectPath == null) {
			throw new IllegalStateException(
					"Failed to compute project path for package.json at " + resourceURI.toString());
		}

		// compute the path of the folder that contains the currently validated package.json file
		final Path baseResourcePath = new File(
				absoluteProjectPath.toFile(),
				projectRelativeResourceURI.trimSegments(1).toFileString()).toPath();

		final String relativePath = pathLiteral.getValue();
		final File file = new File(baseResourcePath.toString(), relativePath);

		if (!file.exists()) {
			addIssue(IssueCodes.getMessageForPKGJ_NON_EXISTING_SOURCE_PATH(relativePath),
					pathLiteral, IssueCodes.PKGJ_NON_EXISTING_SOURCE_PATH);
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
		return n4jsProject.get().getLocation().toFileSystemPath();
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
	 * Returns {@link ProjectType#PLAINJS} if the project type cannot be determined.
	 */
	private ProjectType getProjectType() {
		final JSONValue projectTypeValue = getSingleDocumentValue(PROJECT_TYPE);
		if (projectTypeValue instanceof JSONStringLiteral) {
			return PackageJsonUtils.parseProjectType(asNonEmptyStringOrNull(projectTypeValue));
		} else {
			return ProjectType.PLAINJS;
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
	 * Validates the correct structure of a {@link PackageJsonProperties#SOURCES} section and returns a map between the
	 * declared source container types and corresponding {@link JSONStringLiteral}s that specify the various source
	 * container paths.
	 */
	private Multimap<SourceContainerType, List<JSONStringLiteral>> doGetSourceContainers() {
		final Collection<JSONValue> sourcesValues = getDocumentValues(SOURCES);

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
