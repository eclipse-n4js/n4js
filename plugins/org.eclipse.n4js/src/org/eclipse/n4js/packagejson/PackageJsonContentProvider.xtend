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
package org.eclipse.n4js.packagejson

import com.google.common.base.Optional
import java.util.Map
import java.util.SortedMap
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONFactory
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectDescription.SourceContainerType

import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEPENDENCIES
import static org.eclipse.n4js.packagejson.PackageJsonProperties.DEV_DEPENDENCIES
import static org.eclipse.n4js.packagejson.PackageJsonProperties.EXTENDED_RUNTIME_ENVIRONMENT
import static org.eclipse.n4js.packagejson.PackageJsonProperties.IMPLEMENTATION_ID
import static org.eclipse.n4js.packagejson.PackageJsonProperties.IMPLEMENTED_PROJECTS
import static org.eclipse.n4js.packagejson.PackageJsonProperties.N4JS
import static org.eclipse.n4js.packagejson.PackageJsonProperties.NAME
import static org.eclipse.n4js.packagejson.PackageJsonProperties.OUTPUT
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PRIVATE
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROJECT_TYPE
import static org.eclipse.n4js.packagejson.PackageJsonProperties.PROVIDED_RUNTIME_LIBRARIES
import static org.eclipse.n4js.packagejson.PackageJsonProperties.REQUIRED_RUNTIME_LIBRARIES
import static org.eclipse.n4js.packagejson.PackageJsonProperties.SOURCES
import static org.eclipse.n4js.packagejson.PackageJsonProperties.TESTED_PROJECTS
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VENDOR_ID
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VENDOR_NAME
import static org.eclipse.n4js.packagejson.PackageJsonProperties.VERSION
import static org.eclipse.n4js.packagejson.PackageJsonProperties.WORKSPACES_ARRAY

/**
 * Class for providing the content of N4JS-specific package.json files.
 * 
 * Use {@link PackageJsonBuilder} for creating package.json model instances and file content.
 */
package class PackageJsonContentProvider {

	/**
	 * Creates and returns with the N4JS package.json {@link JSONDocument} representation 
	 * based on the given arguments.
	 * 
	 * @param projectName the N4JS project name of the project (cf. name).
	 * @param version The declared version of the project.
	 * @param type The type of the N4JS project.
	 * @param vendorId The vendorId to use.
	 * @param vendorName The name of the vendor as string.
	 * @param output The relative output folder location.
	 * @param extendedRE The optional extended runtime environment.
	 * @param dependencies A map of dependencies of the project (maps dependencies to their version constraints).
	 * @param providedRL An iterable of provided runtime libraries.
	 * @param requiredRL An iterable of required runtime libraries.
	 * @param implementationId The implementationId of the project.
	 * @param testedProject A list of all projects that are being tested.
	 * @param sourceContainers A map of all source containers of the project.
	 * 
	 * @return the N4JS package.json content as a string.
	 */
	package static def JSONDocument getModel(
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
		Map<SourceContainerType, String> sourceContainers
	) {
		val JSONObject root = JSONFactory.eINSTANCE.createJSONObject();

		if (projectName.present)
			JSONModelUtils.addProperty(root, NAME.name, projectName.get());

		if (version.present)
			JSONModelUtils.addProperty(root, VERSION.name, version.get());

		if (_private.present) {
			JSONModelUtils.addProperty(root, PRIVATE.name,
				JSONModelUtils.createBooleanLiteral(_private.get()));
		}

		if (!workspaces.empty) {
			JSONModelUtils.addProperty(root, WORKSPACES_ARRAY.name,
				JSONModelUtils.createStringArray(workspaces));
		}

		// add "dependencies" section
		if (!dependencies.empty) {
			val dependenciesValue = createDependenciesValue(dependencies);
			JSONModelUtils.addProperty(root, DEPENDENCIES.name, dependenciesValue);
		}
		
		// add "devDependencies" section
		if (!devDependencies.empty) {
			val devDependenciesValue = createDependenciesValue(devDependencies);
			JSONModelUtils.addProperty(root, DEV_DEPENDENCIES.name, devDependenciesValue);
		}
		
		// create "n4js" section (will be added below iff it will be non-empty)
		val JSONObject n4jsRoot = JSONFactory.eINSTANCE.createJSONObject();
		
		// project type
		if (type.present) {
			val projectTypeStr = PackageJsonUtils.getProjectTypeStringRepresentation(type.get());
			JSONModelUtils.addProperty(n4jsRoot, PROJECT_TYPE.name, projectTypeStr);
		}

		// add vendor related properties
		if (vendorId.present)
			JSONModelUtils.addProperty(n4jsRoot, VENDOR_ID.name, vendorId.get());
		if (vendorName.present)
			JSONModelUtils.addProperty(n4jsRoot, VENDOR_NAME.name, vendorName.get());

		// add sources section
		if (!sourceContainers.empty) {
			val JSONObject sourcesSection = JSONModelUtils.addProperty(n4jsRoot, SOURCES.name,
				JSONFactory.eINSTANCE.createJSONObject());
	
			// add sources sub-sections
			sourceContainers.entrySet
				// sort by container type
				.sortBy[ e | PackageJsonUtils.getSourceContainerTypeStringRepresentation(e.key) ]
				// group by source container type
				.groupBy[ e | e.key ]
				// add source container sub-section for each specified source container type 
				.forEach[containerType, paths| 
					val JSONArray typeSectionArray = JSONModelUtils.addProperty(sourcesSection,
						PackageJsonUtils.getSourceContainerTypeStringRepresentation(containerType),
						JSONFactory.eINSTANCE.createJSONArray());
					val pathLiterals = paths.map[pathEntry | JSONModelUtils.createStringLiteral(pathEntry.value) ];
					typeSectionArray.getElements().addAll(pathLiterals);
				];
		}

		// add output folder
		if (output.present)
			JSONModelUtils.addProperty(n4jsRoot, OUTPUT.name, output.get());

		// add provided and required runtime libraries if given
		if (!providedRL.empty) {
			JSONModelUtils.addProperty(n4jsRoot, PROVIDED_RUNTIME_LIBRARIES.name,
				JSONModelUtils.createStringArray(providedRL));
		}
		if (!requiredRL.empty) {
			JSONModelUtils.addProperty(n4jsRoot, REQUIRED_RUNTIME_LIBRARIES.name,
				JSONModelUtils.createStringArray(requiredRL));
		}
			
		if (extendedRE.isPresent) {
			JSONModelUtils.addProperty(n4jsRoot, EXTENDED_RUNTIME_ENVIRONMENT.name,
				extendedRE.get());
		}
		if (implementationId.isPresent) {
			JSONModelUtils.addProperty(n4jsRoot, IMPLEMENTATION_ID.name,
				implementationId.get());
		}

		if (!implementedProjects.empty) {
			JSONModelUtils.addProperty(n4jsRoot, IMPLEMENTED_PROJECTS.name,
				JSONModelUtils.createStringArray(implementedProjects));
		}
		
		if (!testedProjects.empty) {
			JSONModelUtils.addProperty(n4jsRoot, TESTED_PROJECTS.name,
				JSONModelUtils.createStringArray(testedProjects));
		}

		// add "n4js" section (if non-empty)
		if (!n4jsRoot.nameValuePairs.empty) {
			JSONModelUtils.addProperty(root, N4JS.name, n4jsRoot);
		}

		// finally serialize as JSONDocument
		val JSONDocument document = JSONFactory.eINSTANCE.createJSONDocument();
		document.setContent(root);

		return document;
	}

	private def static JSONObject createDependenciesValue(Map<String,String> dependencies) {
		val JSONObject dependenciesValue = JSONFactory.eINSTANCE.createJSONObject();
		dependenciesValue.nameValuePairs.addAll(dependencies.entrySet.map [ e |
			val pair = JSONFactory.eINSTANCE.createNameValuePair();
			pair.name = e.key;
			pair.value = JSONModelUtils.createStringLiteral(e.value);
			return pair;
		]);
		return dependenciesValue;
	}
}
