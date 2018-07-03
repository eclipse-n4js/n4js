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
import java.io.IOException
import java.io.StringWriter
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONFactory
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.JSONGlobals
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.n4mf.ProjectType
import org.eclipse.n4js.n4mf.SourceContainerType
import org.eclipse.n4js.utils.ProjectDescriptionHelper
import org.eclipse.n4js.utils.languages.N4LanguageUtils
import org.eclipse.xtext.resource.SaveOptions
import org.eclipse.xtext.serializer.ISerializer

import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__N4JS
import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__NAME
import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__OUTPUT
import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__PROJECT_TYPE
import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__SOURCES
import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__VENDOR_ID
import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__VENDOR_NAME
import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__VERSION
import java.util.Map

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
	 * @param projectId the projectId of the project (cf. name).
	 * @param type The type of the N4JS project.
	 * @param version The declared version of the project.
	 * @param extendedRE The optional extended runtime environment.
	 * @param projectDependencies An iterable of direct project dependencies for the N4JS project.
	 * @param providedRL An iterable of provided runtime libraries.
	 * @param requiredRL An iterable of required runtime libraries.
	 * 
	 * @return the N4JS package.json content as a string.
	 */
	package static def JSONDocument getModel(
		String projectId,
		Optional<String> version,
		ProjectType type,
		Optional<String> vendorId,
		Optional<String> vendorName,
		Optional<String> output,
		Optional<String> extendedRE,
		Iterable<String> dependencies,
		Iterable<String> providedRL,
		Iterable<String> requiredRL,
		Optional<String> implementationId,
		Iterable<String> implementedProjects,
		Iterable<String> testedProjects,
		Map<SourceContainerType, String> sourceContainers
	) {
		val JSONObject root = JSONFactory.eINSTANCE.createJSONObject();
		
		JSONModelUtils.addProperty(root, PROP__NAME, projectId);
		
		if (version.present)
			JSONModelUtils.addProperty(root, PROP__VERSION, version.get());

		// add "dependencies" section
		if (!dependencies.empty) {
			// add dependencies section
			val JSONObject dependenciesSection = JSONFactory.eINSTANCE.createJSONObject();
			dependenciesSection.nameValuePairs.addAll(dependencies.map [ d |
				val pair = JSONFactory.eINSTANCE.createNameValuePair();
				pair.name = d;
				pair.value = JSONModelUtils.createStringLiteral("*");
				return pair;
			]);
			JSONModelUtils.addProperty(root, ProjectDescriptionHelper.PROP__DEPENDENCIES, dependenciesSection);
		}
		
		// add "n4js" section
		val JSONObject n4jsRoot = JSONModelUtils.addProperty(root, PROP__N4JS,
			JSONFactory.eINSTANCE.createJSONObject());
		
		// project type
		JSONModelUtils.addProperty(n4jsRoot, PROP__PROJECT_TYPE, getEnumAsString(type));

		// add vendor related properties
		if (vendorId.present)
			JSONModelUtils.addProperty(n4jsRoot, PROP__VENDOR_ID, vendorId.get());
		if (vendorName.present)
			JSONModelUtils.addProperty(n4jsRoot, PROP__VENDOR_NAME, vendorName.get());

		// add sources section
		val JSONObject sourcesSection = JSONModelUtils.addProperty(n4jsRoot, PROP__SOURCES,
			JSONFactory.eINSTANCE.createJSONObject());

		// add sources sub-sections
		sourceContainers.entrySet
			// sort by container type
			.sortBy[ e | e.key.literal ]
			// group by source container type
			.groupBy[ e | e.key ]
			// add source container sub-section for each specified source container type 
			.forEach[containerType, paths| 
				val JSONArray typeSectionArray = JSONModelUtils.addProperty(sourcesSection,
					containerType.getLiteral().toLowerCase(), JSONFactory.eINSTANCE.createJSONArray());
				val pathLiterals = paths.map[pathEntry | JSONModelUtils.createStringLiteral(pathEntry.value) ];
				typeSectionArray.getElements().addAll(pathLiterals);
			]

		// add output folder
		if (output.present)
			JSONModelUtils.addProperty(n4jsRoot, PROP__OUTPUT, output.get());

		// add provided and required runtime libraries if given
		if (!providedRL.empty) {
			JSONModelUtils.addProperty(n4jsRoot, ProjectDescriptionHelper.PROP__PROVIDED_RUNTIME_LIBRARIES,
				JSONModelUtils.createStringArray(providedRL));
		}
		if (!requiredRL.empty) {
			JSONModelUtils.addProperty(n4jsRoot, ProjectDescriptionHelper.PROP__REQUIRED_RUNTIME_LIBRARIES,
				JSONModelUtils.createStringArray(requiredRL));
		}
			
		if (extendedRE.isPresent) {
			JSONModelUtils.addProperty(n4jsRoot, ProjectDescriptionHelper.PROP__EXTENDED_RUNTIME_ENVIRONMENT,
				extendedRE.get());
		}
		if (implementationId.isPresent) {
			JSONModelUtils.addProperty(n4jsRoot, ProjectDescriptionHelper.PROP__IMPLEMENTATION_ID,
				implementationId.get());
		}

		if (!implementedProjects.empty) {
			JSONModelUtils.addProperty(n4jsRoot, ProjectDescriptionHelper.PROP__IMPLEMENTED_PROJECTS,
				JSONModelUtils.createStringArray(implementedProjects));
		}
		
		if (!testedProjects.empty) {
			JSONModelUtils.addProperty(n4jsRoot, ProjectDescriptionHelper.PROP__TESTED_PROJECTS,
				JSONModelUtils.createStringArray(testedProjects));
		}

		// finally serialize as JSONDocument
		val JSONDocument document = JSONFactory.eINSTANCE.createJSONDocument();
		document.setContent(root);

		return document;
	}

	/** Returns the string representation of the given {@link ProjectType} */
	private def static String getEnumAsString(ProjectType projectType) {
		if (projectType == ProjectType.RUNTIME_ENVIRONMENT)
			return "runtimeEnvironment";
		if (projectType == ProjectType.RUNTIME_LIBRARY)
			return "runtimeLibrary";
		return projectType.getName().toLowerCase();
	}

	/** Serializes the given {@link JSONDocument} using the Xtext serialization facilities provided by the JSON language. */
	package static def String serializeJSON(JSONDocument document) {
		val ISerializer jsonSerializer = N4LanguageUtils.getServiceForContext(JSONGlobals.FILE_EXTENSION, ISerializer).
			get();
		val ResourceSet resourceSet = N4LanguageUtils.getServiceForContext(JSONGlobals.FILE_EXTENSION, ResourceSet).
			get();

		// Use temporary Resource as AbstractFormatter2 implementations can only format
		// semantic elements that are contained in a Resource.
		val Resource temporaryResource = resourceSet.createResource(URI.createFileURI("__synthetic.json"));
		temporaryResource.getContents().add(document);

		// create string writer as serialization output
		val StringWriter writer = new StringWriter();

		// enable formatting as serialization option
		val SaveOptions serializerOptions = SaveOptions.newBuilder().format().getOptions();
		try {
			jsonSerializer.serialize(document, writer, serializerOptions)
			return writer.toString;
		} catch (IOException e) {
			throw new RuntimeException("Failed to serialize JSONDocument " + document, e);
		}
	}
}
