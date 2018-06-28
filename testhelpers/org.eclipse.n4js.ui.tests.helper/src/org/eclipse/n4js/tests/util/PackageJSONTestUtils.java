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
package org.eclipse.n4js.tests.util;

import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__N4JS;
import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__SOURCES;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONPackage;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.n4mf.ModuleFilterType;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.packagejson.PackageJsonBuilder;
import org.eclipse.n4js.utils.ProjectDescriptionHelper;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;

/**
 * Test utility methods for creating and modifying N4JS package.json files in terms of its {@link JSONPackage} model
 * representation.
 */
public class PackageJSONTestUtils {

	/**
	 * Obtains the root object of the package.json file that is represented by the given {@code resource}.
	 */
	public static JSONObject getPackageJSONRoot(Resource resource) {
		if (resource.getContents().isEmpty()) {
			return null;
		}
		final EObject firstSlot = resource.getContents().get(0);
		if (!(firstSlot instanceof JSONDocument) || !(((JSONDocument) firstSlot).getContent() instanceof JSONObject)) {
			return null;
		}
		final JSONDocument document = (JSONDocument) firstSlot;
		return (JSONObject) document.getContent();
	}

	/**
	 * Adds a new project dependency to the dependencies section of the given {@link JSONObject} representation of an
	 * N4JS package.json file.
	 *
	 * Replaces an existing dependency for the same {@code projectId}.
	 *
	 * @param root
	 *            The package.json root object.
	 * @param projectId
	 *            The ID of the project to add a dependency for.
	 * @param versionConstraint
	 *            The version constraint of the dependency.
	 */
	public static void addProjectDependency(JSONObject root, String projectId, String versionConstraint) {
		JSONModelUtils.setPath(root, Arrays.asList(ProjectDescriptionHelper.PROP__DEPENDENCIES, projectId),
				JSONModelUtils.createStringLiteral(versionConstraint));
	}

	/**
	 * Sets (replaces) the list of source container specifiers of the given {@code type} (e.g. source, external, test)
	 * for the given {@link JSONObject} representation of an N4JS package.json file.
	 *
	 * @see ProjectDescriptionHelper#PROP__SOURCES
	 */
	public static void setSourceContainerSpecifiers(JSONObject root, SourceContainerType type,
			List<String> sourceContainerSpecifiers) {
		// make sure n4js section exists
		JSONObject n4jsSection = (JSONObject) JSONModelUtils.getProperty(root, PROP__N4JS).orElseGet(
				() -> JSONModelUtils.addProperty(root, PROP__N4JS,
						JSONFactory.eINSTANCE.createJSONObject()));
		// make sure n4js.sources section exists
		JSONObject sourcesSection = (JSONObject) JSONModelUtils.getProperty(n4jsSection, PROP__SOURCES)
				.orElseGet(() -> JSONModelUtils.addProperty(n4jsSection, PROP__SOURCES,
						JSONFactory.eINSTANCE.createJSONObject()));
		// create new entry for SourceContainerType and set it to the list of source container specifiers
		JSONModelUtils.setProperty(sourcesSection, type.getLiteral().toLowerCase(),
				JSONModelUtils.createStringArray(sourceContainerSpecifiers));
	}

	/**
	 * Adds the given source container specifier (path) to the corresponding list of sources containers of {@code type}
	 * in the {@code n4js.sources} section.
	 */
	public static void addSourceContainerSpecifier(JSONObject root, SourceContainerType type,
			String specifier) {
		JSONObject n4jsSection = getOrCreateN4JSSection(root);
		// make sure n4js.sources section exists
		JSONObject sourcesSection = (JSONObject) JSONModelUtils.getProperty(n4jsSection, PROP__SOURCES)
				.orElseGet(() -> JSONModelUtils.addProperty(n4jsSection, PROP__SOURCES,
						JSONFactory.eINSTANCE.createJSONObject()));

		final String typeLabel = type.getLiteral().toLowerCase();

		final JSONArray specifierList = (JSONArray) JSONModelUtils.getProperty(sourcesSection, typeLabel)
				.orElseGet(() -> JSONModelUtils.addProperty(sourcesSection, typeLabel,
						JSONFactory.eINSTANCE.createJSONArray()));

		specifierList.getElements().add(JSONModelUtils.createStringLiteral(specifier));
	}

	/**
	 * Adds a new module filter specifier of {@code type} to the package.json files represented by {@code root}.
	 *
	 * @param filterSpecifier
	 *            The filter specifier to add.
	 * @param sourceContainer
	 *            The source container the filter should apply to. May be null if the filter applies to all source
	 *            containers.
	 */
	public static void addModuleFilter(JSONObject root, ModuleFilterType type, String filterSpecifier,
			String sourceContainer) {
		final JSONObject n4jsSection = getOrCreateN4JSSection(root);
		final JSONObject moduleFilterSection = getOrCreateObject(n4jsSection,
				ProjectDescriptionHelper.PROP__MODULE_FILTERS);
		final JSONArray filterTypeSection = getOrCreateArray(moduleFilterSection,
				ProjectDescriptionUtils.getModuleFilterTypeRepresentation(type));

		if (sourceContainer == null) {
			filterTypeSection.getElements().add(JSONModelUtils.createStringLiteral(filterSpecifier));
		} else {
			final JSONObject filterObject = JSONFactory.eINSTANCE.createJSONObject();

			final NameValuePair filterPair = JSONFactory.eINSTANCE.createNameValuePair();
			filterPair.setName(ProjectDescriptionHelper.PROP__MODULE);
			filterPair.setValue(JSONModelUtils.createStringLiteral(filterSpecifier));

			final NameValuePair sourceContainerPair = JSONFactory.eINSTANCE.createNameValuePair();
			sourceContainerPair.setName(ProjectDescriptionHelper.PROP__SOURCE_CONTAINER);
			sourceContainerPair.setValue(JSONModelUtils.createStringLiteral(sourceContainer));

			filterObject.getNameValuePairs().add(filterPair);
			filterObject.getNameValuePairs().add(sourceContainerPair);

			filterTypeSection.getElements().add(filterObject);
		}
	}

	/**
	 * Removes all declared module filters from the package.json represented by the given {@code root} that match the
	 * given {@code filterSpecifierToRemove}.
	 *
	 * Only support the object and string-literal notation of module filters.
	 *
	 * @See {@link ProjectDescriptionUtils#getModuleFilterSpecifier(JSONValue)}
	 */
	public static void removeModuleFilter(JSONObject root, ModuleFilterType type, String filterSpecifierToRemove) {
		final JSONObject n4jsSection = getOrCreateN4JSSection(root);
		final JSONObject moduleFilterSection = getOrCreateObject(n4jsSection,
				ProjectDescriptionHelper.PROP__MODULE_FILTERS);
		final JSONArray filterTypeSection = getOrCreateArray(moduleFilterSection,
				ProjectDescriptionUtils.getModuleFilterTypeRepresentation(type));

		// remove all module filter that match 'filterSpecifierToRemove'
		filterTypeSection.getElements().removeIf(e -> {
			if (e instanceof JSONStringLiteral) { // check string literal value (syntax variant 1)
				return ((JSONStringLiteral) e).getValue().equals(filterSpecifierToRemove);
			} else if (e instanceof JSONObject) { // check 'module' property of the object syntax (syntax variant 2)
				Optional<JSONValue> moduleValue = JSONModelUtils.getProperty(root,
						ProjectDescriptionHelper.PROP__MODULE);
				if (moduleValue.isPresent() && moduleValue.get() instanceof JSONStringLiteral) {
					return ((JSONStringLiteral) moduleValue.get()).getValue().equals(filterSpecifierToRemove);
				}
			}
			return false;
		});
	}

	private static JSONArray getOrCreateArray(JSONObject root, String property) {
		return (JSONArray) JSONModelUtils.getProperty(root, property).orElseGet(
				() -> JSONModelUtils.addProperty(root, property,
						JSONFactory.eINSTANCE.createJSONArray()));
	}

	private static JSONObject getOrCreateObject(JSONObject root, String property) {
		return (JSONObject) JSONModelUtils.getProperty(root, property).orElseGet(
				() -> JSONModelUtils.addProperty(root, property,
						JSONFactory.eINSTANCE.createJSONObject()));
	}

	private static JSONObject getOrCreateN4JSSection(JSONObject root) {
		return getOrCreateObject(root, PROP__N4JS);
	}

	/**
	 * Sets the N4JS implementation ID in the given {@link JSONObject} representation of an N4JS package.json file.
	 *
	 * @see ProjectDescriptionHelper#PROP__IMPLEMENTATION_ID
	 */
	public static void setImplementationId(JSONObject root, String implementationId) {
		final String path = ProjectDescriptionHelper.PROP__N4JS + "."
				+ ProjectDescriptionHelper.PROP__IMPLEMENTATION_ID;
		JSONModelUtils.setPath(root, path, implementationId);
	}

	/**
	 * Sets the N4JS implemented-projects list in the given {@link JSONObject} representation of an N4JS package.json
	 * file.
	 *
	 * @see ProjectDescriptionHelper#PROP__IMPLEMENTED_PROJECTS
	 */
	public static void setImplementedProjects(JSONObject root, List<String> implementedProjects) {
		final String path = ProjectDescriptionHelper.PROP__N4JS + "."
				+ ProjectDescriptionHelper.PROP__IMPLEMENTED_PROJECTS;
		JSONModelUtils.setPath(root, path, JSONModelUtils.createStringArray(implementedProjects));
	}

	/**
	 * Sets the package name (project ID) in the given {@link JSONObject} representation of an N4JS package.json file.
	 *
	 * @see ProjectDescriptionHelper#PROP__NAME
	 */
	public static void setProjectId(JSONObject root, String name) {
		final String path = ProjectDescriptionHelper.PROP__NAME;
		JSONModelUtils.setPath(root, path, name);
	}

	/**
	 * Sets the N4JS {@link ProjectType} in the given {@link JSONObject} representation of an N4JS package.json file.
	 *
	 * @see ProjectDescriptionHelper#PROP__PROJECT_TYPE
	 */
	public static void setProjectType(JSONObject root, ProjectType type) {
		final String path = ProjectDescriptionHelper.PROP__N4JS + "." + ProjectDescriptionHelper.PROP__PROJECT_TYPE;
		JSONModelUtils.setPath(root, path, type.getLiteral().toLowerCase());
	}

	/**
	 * Sets/Replaces the list of module filters for the given {@link ModuleFilterType}.
	 *
	 * @param root
	 *            {@link JSONObject} representation of an N4JS package.json file.
	 * @param type
	 *            The module filter type to set the filter specifiers for.
	 * @param filterSpecifiers
	 *            The new list of filter specifiers.
	 */
	public static void setModuleFilters(JSONObject root, ModuleFilterType type, List<String> filterSpecifiers) {
		JSONModelUtils.setPath(root,
				Arrays.asList(PROP__N4JS, ProjectDescriptionHelper.PROP__MODULE_FILTERS, getStringRepresentation(type)),
				JSONModelUtils.createStringArray(filterSpecifiers));
	}

	/**
	 * Removes all occurrences of {@code filterSpecifier} from the given {@link ModuleFilterType}.
	 *
	 * Only removes exact matches between the value of a {@link JSONStringLiteral} and {@code filterSpecifier}.
	 *
	 * Does not remove entries that are declared using a syntax that restricts a module filter to a specific source
	 * container.
	 *
	 * @See {@link ProjectDescriptionHelper#PROP__MODULE_FILTERS}
	 * @See {@link ProjectDescriptionHelper#PROP__SOURCE_CONTAINER}
	 * @See {@link ProjectDescriptionHelper#PROP__MODULE}
	 */
	public static void removePathFromModuleFilter(JSONObject root, ModuleFilterType type, String filterSpecifier) {
		Optional<JSONValue> moduleFilterSection = JSONModelUtils.getPath(root,
				Arrays.asList(PROP__N4JS, ProjectDescriptionHelper.PROP__MODULE_FILTERS,
						getStringRepresentation(type)));
		// only remove specifier, if corresponding module filter section is present
		if (moduleFilterSection.isPresent()) {
			final JSONValue sectionValue = moduleFilterSection.get();
			if (sectionValue instanceof JSONArray) {
				// remove all JSONStringLiterals that match the filterSpecifier to be removed
				((JSONArray) sectionValue).getElements().removeIf(path -> (path instanceof JSONStringLiteral)
						&& ((JSONStringLiteral) path).getValue().equals(filterSpecifier));
			}
		}

	}

	/**
	 * Returns the string representation of the given {@link ModuleFilterType} as it may be used in a package.json file.
	 */
	private static String getStringRepresentation(ModuleFilterType type) {
		switch (type) {
		case NO_MODULE_WRAPPING:
			return "noModuleWrapping";
		case NO_VALIDATE:
			return "noValidate";
		default:
			throw new IllegalStateException("Encountered unknown ModuleFilterType " + type.getName());
		}
	}

	/**
	 * Creates a project description in terms of a package.json {@link JSONObject} with the given base parameters.
	 *
	 * Defaults to project type {@link ProjectType#LIBRARY}, version {@code 0.0.1}, vendor name
	 * {@code Eclipse N4JS Project} and vendor ID {@code org.eclipse.n4js}.
	 *
	 */
	public static PackageJsonBuilder createSimplePackageJSON(String projectId, String sourceFolder,
			String outputFolder) {
		return PackageJsonBuilder.newBuilder()
				.withName(projectId)
				.withVersion("0.0.1")
				.withVendorId("org.eclipse.n4js")
				.withVendorName("Eclipse N4JS Project")
				.withType(ProjectType.LIBRARY)
				.withOutput(outputFolder)
				.withSourceContainer(SourceContainerType.SOURCE, sourceFolder);
	}

}
