/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.packagejson;

import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEV_DEPENDENCIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.EXTENDED_RUNTIME_ENVIRONMENT;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.IMPLEMENTATION_ID;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.IMPLEMENTED_PROJECTS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.N4JS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NAME;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.OUTPUT;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PRIVATE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROJECT_TYPE;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROVIDED_RUNTIME_LIBRARIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.REQUIRED_RUNTIME_LIBRARIES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.SOURCES;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.TESTED_PROJECTS;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VENDOR_ID;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VENDOR_NAME;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VERSION;
import static org.eclipse.n4js.packagejson.PackageJsonProperties.WORKSPACES_ARRAY;
import static org.eclipse.n4js.packagejson.PackageJsonUtils.getSourceContainerTypeStringRepresentation;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.groupBy;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONFactory;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;

import com.google.common.base.Optional;

/**
 * Class for providing the content of N4JS-specific package.json files.
 *
 * Use {@link PackageJsonBuilder} for creating package.json model instances and file content.
 */
class PackageJsonContentProvider {

	/**
	 * Creates and returns with the N4JS package.json {@link JSONDocument} representation based on the given arguments.
	 *
	 * @param projectName
	 *            the N4JS project name of the project (cf. name).
	 * @param version
	 *            The declared version of the project.
	 * @param type
	 *            The type of the N4JS project.
	 * @param vendorId
	 *            The vendorId to use.
	 * @param vendorName
	 *            The name of the vendor as string.
	 * @param output
	 *            The relative output folder location.
	 * @param extendedRE
	 *            The optional extended runtime environment.
	 * @param dependencies
	 *            A map of dependencies of the project (maps dependencies to their version constraints).
	 * @param providedRL
	 *            An iterable of provided runtime libraries.
	 * @param requiredRL
	 *            An iterable of required runtime libraries.
	 * @param implementationId
	 *            The implementationId of the project.
	 * @param testedProjects
	 *            A list of all projects that are being tested.
	 * @param sourceContainers
	 *            A map of all source containers of the project.
	 *
	 * @return the N4JS package.json content as a string.
	 */
	static JSONDocument getModel(
			Optional<String> projectName,
			Optional<String> version,
			Optional<Boolean> _private,
			Iterable<String> workspaces,
			Optional<ProjectType> type,
			Optional<String> vendorId,
			Optional<String> vendorName,
			Optional<String> output,
			Optional<String> extendedRE,
			SortedMap<String, String> dependencies,
			SortedMap<String, String> devDependencies,
			Iterable<String> providedRL,
			Iterable<String> requiredRL,
			Optional<String> implementationId,
			Iterable<String> implementedProjects,
			Iterable<String> testedProjects,
			Map<SourceContainerType, String> sourceContainers) {
		JSONObject root = JSONFactory.eINSTANCE.createJSONObject();

		if (projectName.isPresent())
			JSONModelUtils.addProperty(root, NAME.name, projectName.get());

		if (version.isPresent())
			JSONModelUtils.addProperty(root, VERSION.name, version.get());

		if (_private.isPresent()) {
			JSONModelUtils.addProperty(root, PRIVATE.name,
					JSONModelUtils.createBooleanLiteral(_private.get()));
		}

		if (workspaces.iterator().hasNext()) {
			JSONModelUtils.addProperty(root, WORKSPACES_ARRAY.name,
					JSONModelUtils.createStringArray(workspaces));
		}

		// add "dependencies" section
		if (!dependencies.isEmpty()) {
			JSONObject dependenciesValue = createDependenciesValue(dependencies);
			JSONModelUtils.addProperty(root, DEPENDENCIES.name, dependenciesValue);
		}

		// add "devDependencies" section
		if (!devDependencies.isEmpty()) {
			JSONObject devDependenciesValue = createDependenciesValue(devDependencies);
			JSONModelUtils.addProperty(root, DEV_DEPENDENCIES.name, devDependenciesValue);
		}

		// create "n4js" section (will be added below iff it will be non-empty)
		JSONObject n4jsRoot = JSONFactory.eINSTANCE.createJSONObject();

		// project type
		if (type.isPresent()) {
			String projectTypeStr = PackageJsonUtils.getProjectTypeStringRepresentation(type.get());
			JSONModelUtils.addProperty(n4jsRoot, PROJECT_TYPE.name, projectTypeStr);
		}

		// add vendor related properties
		if (vendorId.isPresent())
			JSONModelUtils.addProperty(n4jsRoot, VENDOR_ID.name, vendorId.get());
		if (vendorName.isPresent())
			JSONModelUtils.addProperty(n4jsRoot, VENDOR_NAME.name, vendorName.get());

		// add sources section
		if (!sourceContainers.isEmpty()) {
			JSONObject sourcesSection = JSONFactory.eINSTANCE.createJSONObject();
			JSONModelUtils.addProperty(n4jsRoot, SOURCES.name, sourcesSection);

			// add sources sub-sections
			List<Entry<SourceContainerType, String>> srcConts = new ArrayList<>(sourceContainers.entrySet());
			// sort by container type
			Collections.sort(srcConts,
					Comparator.comparing(e -> getSourceContainerTypeStringRepresentation(e.getKey())));
			// group by source container type
			Map<SourceContainerType, List<Entry<SourceContainerType, String>>> srcContsByPath = groupBy(srcConts,
					e -> e.getKey());

			// add source container sub-section for each specified source container type
			for (SourceContainerType sct : srcContsByPath.keySet()) {
				List<Entry<SourceContainerType, String>> paths = srcContsByPath.get(sct);
				JSONArray typeSectionArray = JSONFactory.eINSTANCE.createJSONArray();
				JSONModelUtils.addProperty(sourcesSection,
						PackageJsonUtils.getSourceContainerTypeStringRepresentation(sct), typeSectionArray);
				List<JSONStringLiteral> pathLiterals = toList(
						map(paths, pathEntry -> JSONModelUtils.createStringLiteral(pathEntry.getValue())));
				typeSectionArray.getElements().addAll(pathLiterals);
			}
		}

		// add output folder
		if (output.isPresent())
			JSONModelUtils.addProperty(n4jsRoot, OUTPUT.name, output.get());

		// add provided and required runtime libraries if given
		if (providedRL.iterator().hasNext()) {
			JSONModelUtils.addProperty(n4jsRoot, PROVIDED_RUNTIME_LIBRARIES.name,
					JSONModelUtils.createStringArray(providedRL));
		}
		if (requiredRL.iterator().hasNext()) {
			JSONModelUtils.addProperty(n4jsRoot, REQUIRED_RUNTIME_LIBRARIES.name,
					JSONModelUtils.createStringArray(requiredRL));
		}

		if (extendedRE.isPresent()) {
			JSONModelUtils.addProperty(n4jsRoot, EXTENDED_RUNTIME_ENVIRONMENT.name,
					extendedRE.get());
		}
		if (implementationId.isPresent()) {
			JSONModelUtils.addProperty(n4jsRoot, IMPLEMENTATION_ID.name,
					implementationId.get());
		}

		if (implementedProjects.iterator().hasNext()) {
			JSONModelUtils.addProperty(n4jsRoot, IMPLEMENTED_PROJECTS.name,
					JSONModelUtils.createStringArray(implementedProjects));
		}

		if (testedProjects.iterator().hasNext()) {
			JSONModelUtils.addProperty(n4jsRoot, TESTED_PROJECTS.name,
					JSONModelUtils.createStringArray(testedProjects));
		}

		// add "n4js" section (if non-empty)
		if (!n4jsRoot.getNameValuePairs().isEmpty()) {
			JSONModelUtils.addProperty(root, N4JS.name, n4jsRoot);
		}

		// finally serialize as JSONDocument
		JSONDocument document = JSONFactory.eINSTANCE.createJSONDocument();
		document.setContent(root);

		return document;
	}

	private static JSONObject createDependenciesValue(Map<String, String> dependencies) {
		JSONObject dependenciesValue = JSONFactory.eINSTANCE.createJSONObject();

		List<NameValuePair> map = toList(map(dependencies.entrySet(), e -> {
			NameValuePair pair = JSONFactory.eINSTANCE.createNameValuePair();
			pair.setName(e.getKey());
			pair.setValue(JSONModelUtils.createStringLiteral(e.getValue()));
			return pair;
		}));
		dependenciesValue.getNameValuePairs().addAll(map);
		return dependenciesValue;
	}
}
