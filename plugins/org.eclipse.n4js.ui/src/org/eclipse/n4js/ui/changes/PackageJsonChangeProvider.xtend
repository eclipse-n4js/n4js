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
package org.eclipse.n4js.ui.changes

import java.util.Arrays
import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONFactory
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.utils.ProjectDescriptionHelper
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification

import static org.eclipse.n4js.utils.ProjectDescriptionHelper.PROP__N4JS

/**
 * This class provides basic change functionality for N4JS package.json files
 * in terms of {@link ISemanticModification}s.
 */
class PackageJsonChangeProvider {

	/**
	 * Returns a semantic modification that appends the given list of new project dependencies to the "dependencies"
	 * section of a package.json file. 
	 *
	 * @param dependencies 
	 * 			The list of project dependencies to insert.
	 */
	public static def ISemanticModification insertProjectDependencies(Collection<String> dependencies) {
		return new JSONDocumentModification() {
			override apply(JSONDocument document) {
				if (dependencies.empty) {
					return; // nothing to add
				}
				
				val root = getDocumentRoot(document);
				for (String dependency : dependencies) {
					addProjectDependency(root, dependency, "*");
				}
			}
		}
	}

	/**
	 * Returns a semantic modification that appends the given list of new required runtime libraries to the required-runtime-libraries-section
	 * of a package.json file.
	 *
	 * @param resourceUri 
	 * 			The URI of the package.json file.
	 * @param runtimeLibraries 
	 * 			The list of newly required runtime libraries.
	 */
	public static def ISemanticModification insertRequiredRuntimeLibraries(Collection<String> runtimeLibraries) {
		return new JSONDocumentModification() {
			override apply(JSONDocument document) {
				if (runtimeLibraries.empty) {
					return; // nothing to add
				}
				
				val root = getDocumentRoot(document);
				val n4jsSection = getOrCreateN4JSSection(root);
				val requiredRuntimeLibrariesList = getOrCreateArray(n4jsSection, ProjectDescriptionHelper.PROP__REQUIRED_RUNTIME_LIBRARIES);
				
				for(library : runtimeLibraries) {
					requiredRuntimeLibrariesList.elements.add(JSONModelUtils.createStringLiteral(library));
				}
			}
		}
	}

	/**
	 * Returns a semantic modification that sets the name of the extended runtime environment to the given string.
	 *
	 * @param runtimeEnvironment
	 * 			The new extended runtime environment.
	 */
	public static def ISemanticModification setExtendedRuntimeEnvironment(String runtimeEnvironment) {
		return new JSONDocumentModification() {
			override apply(JSONDocument document) {
				val root = getDocumentRoot(document);
				val n4jsSection = getOrCreateN4JSSection(root);
				
				JSONModelUtils.setProperty(n4jsSection, ProjectDescriptionHelper.PROP__EXTENDED_RUNTIME_ENVIRONMENT, runtimeEnvironment);
			}
		}
	}

	/**
	 * Returns change instance to set the ProjectType to the given value.
	 *
	 * @param manifestResource The manifest resource
	 * @param runtimeEnvironment The runtime environment to set
	 * @param projectDescription The project description object of the manifest
	 */
	public static def ISemanticModification setProjectType(String projectType) {
		return new JSONDocumentModification() {
			override apply(JSONDocument document) {
				val root = getDocumentRoot(document);
				val n4jsSection = getOrCreateN4JSSection(root);
				
				JSONModelUtils.setProperty(n4jsSection, ProjectDescriptionHelper.PROP__PROJECT_TYPE, projectType)
			}
		}
	}
	
	private static def JSONObject getDocumentRoot(JSONDocument document) {
		val content = document.content;
		if (!(content instanceof JSONObject)) {
			throw new IllegalArgumentException("The given resource does not represent a valid package.json file." + 
				"Make sure the document root is a JSON object. (URI=" + document.eResource.URI + ")");
		}
		return content as JSONObject;
	}
	
	public static def void addProjectDependency(JSONObject root, String projectId, String versionConstraint) {
		JSONModelUtils.setPath(root, Arrays.asList(ProjectDescriptionHelper.PROP__DEPENDENCIES, projectId),
				JSONModelUtils.createStringLiteral(versionConstraint));
	}
	
	private static def JSONArray getOrCreateArray(JSONObject root, String property) {
		return JSONModelUtils.getProperty(root, property).orElseGet(
				[JSONModelUtils.addProperty(root, property, JSONFactory.eINSTANCE.createJSONArray())]) as JSONArray;
	}

	private static def JSONObject getOrCreateObject(JSONObject root, String property) {
		return JSONModelUtils.getProperty(root, property).orElseGet(
				[JSONModelUtils.addProperty(root, property, JSONFactory.eINSTANCE.createJSONObject())]) as JSONObject;
	}

	private static def JSONObject getOrCreateN4JSSection(JSONObject root) {
		return getOrCreateObject(root, PROP__N4JS);
	}
	
	/** 
	 * Specialized {@link ISemanticModification} for {@link JSONDocument} instances. 
	 */
	private static abstract class JSONDocumentModification implements ISemanticModification {
		
		override apply(EObject element, IModificationContext context) throws Exception {
			if (!(element instanceof JSONDocument)) {
				System.err.println("Cannot perform JSONDocumentModification on non-JSONDocument object " + element);
				return;
			}
			this.apply(element as JSONDocument);
		}
		
		
		abstract def void apply(JSONDocument document);
	}
}
