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
package org.eclipse.n4js.ui.wizard.workspace

import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.utils.beans.PropertyChangeSupport
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IResource
import org.eclipse.core.runtime.IPath
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.jface.viewers.IStructuredSelection

/**
 * An abstract model for workspace wizard elements.
 */
@PropertyChangeSupport
class WorkspaceWizardModel {
	var IPath project = new Path('');
	var IPath sourceFolder = new Path('');
	var String moduleSpecifier = '';

	/**
	 * Tries to extract as much information possible from a structured selection.
	 *
	 * @param model
	 *            The model
	 * @param selection
	 *            The selection
	 * @param extractModuleFile
	 *            {@code true} if the module file should be filled in as well, {@code false} otherwise
	 * @param n4jsCore
	 *            IN4JSCore implementation
	 */
	public static def populateModelFromInitialSelection(WorkspaceWizardModel model, IStructuredSelection selection, boolean extractModuleFile, IN4JSCore n4jsCore) {

		val firstElement = selection.getFirstElement();
		if (firstElement instanceof IResource) {
			var path = firstElement.fullPath;

			// Remove the file specifying part as this isn't used for inference
			if (firstElement instanceof IFile) {
				path = path.removeLastSegments(1);
			}

			// Note: The following calls creates the rootURI manually
			//       since SWTBotTests crash with this call (GH-269):
			//       URI.createPlatformResourceURI("", true);
			val rootURI = URI.createURI("platform:/resource/");

			// Find project of path
			val pathURI = URI.createPlatformResourceURI(path.toString(), true);
			val n4jsProject = n4jsCore.findProject(pathURI).orNull;

			if (null !== n4jsProject && n4jsProject.exists) {
				// If project exists set the project property of the model
				val projectUri = n4jsProject.getLocation.toURI;
				val projectPath = new Path(projectUri.deresolve(rootURI).toString());
				model.setProject(projectPath);

				// If the path also specifies a folder inside the project, use this information as well
				if (!pathURI.equals(projectUri)) {
					val sourceFolder = n4jsCore.findN4JSSourceContainer(pathURI).orNull;
					if (null !== sourceFolder) {
						val sourceFolderPath = sourceFolder.relativeLocation;
						model.setSourceFolder(new Path(sourceFolderPath));

						// Finally parse the module specifier
						val sourceFolderURI = new Path(sourceFolder.location.toURI.deresolve(rootURI).toString());
						val moduleSpecifierPath = new Path(pathURI.deresolve(rootURI).toString());
						val moduleSpecifier = moduleSpecifierPath.makeRelativeTo(sourceFolderURI).toString();

						if (!moduleSpecifier.isEmpty()) {
							model.setModuleSpecifier(moduleSpecifier + "/");
						}

						// If extractModuleFile is true and the selection is a file
						if (extractModuleFile && firstElement instanceof IFile) {
							model.moduleSpecifier = model.moduleSpecifier + firstElement.fullPath.removeFileExtension.lastSegment;
						}
					}
				}

			}
		}
	}

}
