/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.assistants

import com.google.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.packagejson.projectDescription.ProjectType
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.workspace.WorkspaceAccess
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider

/**
 * 
 */
class ModuleSpecifierAssistant extends TransformationAssistant {

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	private WorkspaceAccess workspaceAccess;

	// FIXME improve implementation & reconsider performance!!!
	def String getActualFileExtensionForN4jsdFile(Resource targetResource, TModule targetModule) {
		val targetQN = qualifiedNameConverter.toQualifiedName(targetModule.getQualifiedName());
		val index = resourceDescriptionsProvider.getResourceDescriptions(targetResource);
		val matchingTModules = index.getExportedObjects(TypesPackage.Literals.TMODULE, targetQN, false);
		var boolean gotJS = false;
		var boolean gotCJS = false;
		var boolean gotMJS = false;
		for (desc : matchingTModules) {
			val ext = desc.EObjectURI.fileExtension;
			if (ext == N4JSGlobals.JS_FILE_EXTENSION) {
				gotJS = true;
			} else if (ext == N4JSGlobals.CJS_FILE_EXTENSION) {
				gotCJS = true;
			} else if (ext == N4JSGlobals.MJS_FILE_EXTENSION) {
				gotMJS = true;
			}
		}
		if (gotMJS) {
			return N4JSGlobals.MJS_FILE_EXTENSION;
		} else if (gotCJS) {
			return N4JSGlobals.CJS_FILE_EXTENSION;
		} else if (gotJS) {
			return N4JSGlobals.JS_FILE_EXTENSION;
		}
		// no plain JS file found, check for "directory import"

		if (isDirectoryWithPackageJson(index, targetResource, targetQN)) {
			return ""; // no file extension for directory imports
		}

// FIXME this approach would be more elegant, but would require different computation of FQNs for package.json files in source folders (in N4JSQualifiedNameProvider):
//		val matchingPackageJsonDesc = index.getExportedObjects(JSONPackage.Literals.JSON_DOCUMENT, targetQN.append(N4JSQualifiedNameProvider.PACKAGE_JSON_SEGMENT), false).head;
//		if (matchingPackageJsonDesc !== null) {
//			return ""; // no file extension for directory imports
//		}

		// use .js as fall back
		return N4JSGlobals.JS_FILE_EXTENSION;
	}

	def private boolean isDirectoryWithPackageJson(IResourceDescriptions index, Resource targetResource, QualifiedName targetQN) {

		// FIXME pass in targetProject to avoid redundant look ups!!!!!
		val targetProject = workspaceAccess.findProjectContaining(targetResource);
		if (targetProject === null) {
			return false;
		}


		var actualTargetProject = targetProject;
		if (targetProject.type === ProjectType.DEFINITION) {
			actualTargetProject = null;
			val definedPackageName = targetProject.getDefinesPackage();
			if (definedPackageName !== null) {
				val definedProjectId = targetProject.getProjectIdForPackageName(definedPackageName.getRawName());
				if (definedProjectId !== null) {
					val definedProject = workspaceAccess.findProjectByName(targetResource, definedProjectId);
					if (definedProject !== null) {
						actualTargetProject = definedProject;
					}
				}
			}
		}
		if (actualTargetProject === null) {
			return false;
		}
		val String[] segments = targetQN.segments + #[ N4JSGlobals.PACKAGE_JSON ];
		for (srcFolder : actualTargetProject.sourceFolders) {
			val packageJsonURI = srcFolder.pathAsFileURI.appendSegments(segments);
			if (index.getResourceDescription(packageJsonURI.toURI) !== null) {
				return true;
			}
		}
		return false;
	}
}
