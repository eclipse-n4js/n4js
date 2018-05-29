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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.validation.extension.AbstractJSONValidatorExtension;
import org.eclipse.n4js.json.validation.extension.CheckProperty;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.n4js.utils.io.FileUtils;
import org.eclipse.n4js.validation.IssueCodes;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * A JSON validator extension that adds custom validation to {@code package.json} resources.
 */
public class PackageJsonValidatorExtension extends AbstractJSONValidatorExtension {

	/** regular expression for valid package.json identifier (e.g. package name, vendor ID) */
	private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("(^)?[A-z_][A-z_\\-\\.0-9]*");

	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private XpectAwareFileExtensionCalculator fileExtensionCalculator;

	@Override
	protected boolean isResponsible(Map<Object, Object> context, EObject eObject) {
		// this validator extension only applies to package.json files
		return fileExtensionCalculator.getFilenameWithoutXpectExtension(eObject.eResource().getURI())
				.equals(IN4JSProject.PACKAGE_JSON);
	}

	/** Validates the project/package name. */
	@CheckProperty(propertyPath = ProjectDescriptionHelper.PROP__NAME, mandatory = true)
	public void checkName(JSONValue projectNameValue) {
		// first check for the type of the name value
		if (!checkIsType(projectNameValue, JSONPackage.Literals.JSON_STRING_LITERAL, "as package name")) {
			return;
		}
		final JSONStringLiteral projectName = (JSONStringLiteral) projectNameValue;

		if (!IDENTIFIER_PATTERN.matcher(projectName.getValue()).matches()) {
			addIssue(IssueCodes.getMessageForPKGJ_INVALID_PROJECT_NAME(projectName.getValue()),
					projectNameValue, IssueCodes.PKGJ_INVALID_PROJECT_NAME);
		}
	}

	/** Validates the source container section of N4JS package.json files */
	@CheckProperty(propertyPath = "n4js." + ProjectDescriptionHelper.PROP__SOURCES, mandatory = true)
	public void checkSourceContainers(JSONValue sourceContainerValue) {
		// obtain source-container-related content of the section and validate its structure
		Multimap<SourceContainerType, List<JSONStringLiteral>> sourceContainers = getSourceContainers(
				sourceContainerValue);

		// check each source container sub-section (e.g. sources, external, etc.)
		for (Entry<SourceContainerType, List<JSONStringLiteral>> subSection : sourceContainers
				.entries()) {
			// check each specified source folder to actually exist
			for (JSONStringLiteral containerLiteral : subSection.getValue()) {
				// check path for existence
				if (!pathExists(containerLiteral)) {
					addIssue(IssueCodes.getMessageForPKGJ_EXISTING_SOURCE_PATH(containerLiteral.getValue()),
							containerLiteral, IssueCodes.PKGJ_EXISTING_SOURCE_PATH);
				}
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
			final Set<SourceContainerType> types = duplicateGroup.stream()
					.map(d -> getSourceContainerType(d))
					.collect(Collectors.toSet());

			// Construct in-clause (e.g. "in external, test"). Use empty clause, if all duplicates
			// originate from the same source container type
			final String inClause = types.size() == 1 ? ""
					: " in " + types.stream().map(t -> t.getLiteral().toLowerCase())
							.collect(Collectors.joining(", "));

			for (JSONStringLiteral duplicate : duplicateGroup) {
				addIssue(IssueCodes.getMessageForPKGJ_DUPLICATE_SOURCE_CONTAINER(normalizedPath, inClause),
						duplicate, IssueCodes.PKGJ_DUPLICATE_SOURCE_CONTAINER);
			}
		}
	}

	/**
	 * Returns {@code true} iff the given project-relative path as given by the string value of the given
	 * {@code pathLiteral} exists on the file system.
	 *
	 * Returns {@code false} otherwise.
	 */
	private boolean pathExists(JSONStringLiteral pathLiteral) {
		final URI resourceURI = pathLiteral.eResource().getURI();
		final Path absoluteProjectPath = getAbsoluteProjectPath(resourceURI);

		if (absoluteProjectPath == null) {
			throw new IllegalStateException(
					"Failed to compute project path for package.json at " + resourceURI.toString());
		}

		final String relativePath = pathLiteral.getValue();
		return new File(absoluteProjectPath.toString() + "/" + relativePath).exists();
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
	 * Validates the correct structure of a {@link ProjectDescriptionHelper#PROP__SOURCES} section and returns a map
	 * between the declared source container types and corresponding {@link JSONStringLiteral}s that specify the various
	 * source container paths.
	 */
	private Multimap<SourceContainerType, List<JSONStringLiteral>> getSourceContainers(JSONValue sourcesValue) {
		// first check for the type of the source-container value
		if (!checkIsType(sourcesValue, JSONPackage.Literals.JSON_OBJECT, "as source container section")) {
			// return an empty map
			return ImmutableMultimap.<SourceContainerType, List<JSONStringLiteral>> of();
		}
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
