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

import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.SourceFragment
import org.eclipse.n4js.n4mf.SourceFragmentType
import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils

/**
 * This class provides basic change functionality for N4JS manifest files.
 */
class ManifestChangeProvider {

	private static val PROJECT_DEPENDENCIES_KEY = "ProjectDependencies";
	private static val REQUIRED_RUNTIME_LIBRARIES_KEY = "RequiredRuntimeLibraries";
	private static val EXTENDED_RUNTIME_ENVIRONMENT_KEY = "ExtendedRuntimeEnvironment"


	public static def IAtomicChange addSourceFoldersToManifest(Resource manifestResource, List<String> sourceFolders) {
		val manifestSourceFolders = new ArrayList<String>(sourceFolders);

		val sourceFragment = manifestResource.allContents.filter(SourceFragment).filter[ fragment | fragment.sourceFragmentType == SourceFragmentType.SOURCE].head;
		manifestSourceFolders.addAll(sourceFragment.paths);

		val sourceFragmentNode = NodeModelUtils.findActualNodeFor(sourceFragment);

		val newSourceFolderFragment =
		'''
		source {
		«FOR path : manifestSourceFolders SEPARATOR ','»
		«"\t\t"»"«path»"
		«ENDFOR»
			}
		''';

		return new Replacement(manifestResource.URI, sourceFragmentNode.offset, sourceFragmentNode.length, newSourceFolderFragment);
	}

	/**
	 *  Returns change instance to insert given project dependency into manifest file.
	 *
	 * @param resourceUri The URI of the manifest resource
	 * @param dependencies List of project identifiers
	 * @param description The ProjectDescription AST element of the manifest to modify.
	 */
	public static def IAtomicChange insertProjectDependencies(Resource manifestResource, Collection<String> dependencies, ProjectDescription description) {
		var offset = -1;
		var length = 0;
		var withFrame = false;

		// Remove all dependencies, that are already part of the list
		if (description.projectDependencies !== null) {
			dependencies.removeIf [ description.projectDependencies.projectDependencies.map[project.projectId].contains(it) ]
		}

		if (dependencies.length < 1) {
			return null;
		}


		var StringBuilder textToInsert = new StringBuilder();
		if (description.projectDependencies === null) { //If no dependency list (frame), create one.
			textToInsert.append("\n" + ManifestChangeProvider.PROJECT_DEPENDENCIES_KEY + " {");

			val INode globalDescriptionNode = NodeModelUtils.findActualNodeFor(description);
			offset = globalDescriptionNode.offset + globalDescriptionNode.length;
			withFrame = true;
		} else if (description.projectDependencies.projectDependencies.length > 0) { //If existing dependency list, append after the last one
			val INode lastDep = NodeModelUtils.findActualNodeFor(description.projectDependencies.projectDependencies.last);
			offset = lastDep.offset + lastDep.length;
			textToInsert.append(",");
		} else { //If empty dependency list, replace the whole empty block
			textToInsert.append(ManifestChangeProvider.PROJECT_DEPENDENCIES_KEY + " {");
			withFrame = true;
			val INode depsNode = NodeModelUtils.findActualNodeFor(description.projectDependencies);
			offset = depsNode.offset;
			length = depsNode.length;
		}

		textToInsert.append('''«FOR dep : dependencies SEPARATOR ","»«"\n\t" + dep»«ENDFOR»''')

		if (withFrame) {
			textToInsert.append("\n}");
		}

		new Replacement(manifestResource.URI, offset, length, textToInsert.toString);
	}

	/**
	 *  Returns change instance to insert given required runtime library into manifest file.
	 *
	 * @param resourceUri The URI of the manifest resource
	 * @param dependencies List of runtime library identifiers
	 * @param projectDescription The ProjectDescription object of the manifest
	 */
	public static def IAtomicChange insertRequiredRuntimeLibraries(Resource manifestResource, Collection<String> dependencies, ProjectDescription projectDescription) {
		var offset = -1;
		var length = 0;
		var withFrame = false;

		// Remove all dependencies, that are already part of the list
		if (projectDescription.requiredRuntimeLibraries !== null) {
			dependencies.removeIf [ projectDescription.requiredRuntimeLibraries.requiredRuntimeLibraries.map[project.projectId].contains(it)]
		}

		if (dependencies.length < 1) {
			return null;
		}

		var StringBuilder textToInsert = new StringBuilder();
		if (projectDescription.requiredRuntimeLibraries === null) { //If no runtime library list (frame), create one.
			textToInsert.append("\n" + ManifestChangeProvider.REQUIRED_RUNTIME_LIBRARIES_KEY + " {");
			val INode globalDescriptionNode = NodeModelUtils.findActualNodeFor(projectDescription);
			offset = globalDescriptionNode.offset + globalDescriptionNode.length;
			withFrame = true;
		} else if (projectDescription.requiredRuntimeLibraries.requiredRuntimeLibraries.length > 0) { //If existing runtime library list, append after the last one
			val INode lastDep = NodeModelUtils.findActualNodeFor(projectDescription.requiredRuntimeLibraries.requiredRuntimeLibraries.last);
			offset = lastDep.offset + lastDep.length;
			textToInsert.append(",");
		} else { //If empty dependency list, replace the whole empty block
			textToInsert.append(ManifestChangeProvider.REQUIRED_RUNTIME_LIBRARIES_KEY + " {");
			withFrame = true;
			val INode requiredRuntimeNode = NodeModelUtils.findActualNodeFor(projectDescription.requiredRuntimeLibraries);
			offset = requiredRuntimeNode.offset;
			length = requiredRuntimeNode.length;
		}

		textToInsert.append('''«FOR dep : dependencies SEPARATOR ","»«"\n\t" + dep»«ENDFOR»''')

		if (withFrame) {
			textToInsert.append("\n}");
		}

		new Replacement(manifestResource.URI, offset, length, textToInsert.toString);
	}

	/**
	 * Returns change instance to set the ExtendedRuntimeEnvironment to the given value.
	 *
	 * @param manifestResource The manifest resource
	 * @param runtimeEnvironment The runtime environment to set
	 * @param projectDescription The project description object of the manifest
	 */
	public static def IAtomicChange setExtendedRuntimeEnvironment(Resource manifestResource, String runtimeEnvironment, ProjectDescription projectDescription) {
		// Replace existing runtime environment statement
		if (projectDescription.extendedRuntimeEnvironment !== null) {
			val extendedRuntimeEnvNode = NodeModelUtils.findActualNodeFor(projectDescription.extendedRuntimeEnvironment);
			return new Replacement(manifestResource.URI, extendedRuntimeEnvNode.offset,
								   extendedRuntimeEnvNode.length, EXTENDED_RUNTIME_ENVIRONMENT_KEY + ": " + runtimeEnvironment);
		} else { // Or append a new one
			val projectDescriptionNode = NodeModelUtils.findActualNodeFor(projectDescription);
			return new Replacement(manifestResource.URI, projectDescriptionNode.offset + projectDescriptionNode.length, 0,
								   "\n" + EXTENDED_RUNTIME_ENVIRONMENT_KEY + ": " + runtimeEnvironment);
		}
	}
}
