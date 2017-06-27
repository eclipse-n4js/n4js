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
package org.eclipse.n4js.ui.editor;

import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorage;
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorageEditorInputFactory;

/**
 * Handle our specialized {@link IStorage} implementation. The resource is created from the URI in the
 * {@link URIBasedStorage} and the resource set is configured with the project information of the enclosing Eclipse
 * project.
 *
 * @see IN4JSEclipseCore#findN4JSSourceContainer(URI)
 * @see IN4JSSourceContainer#getProject()
 */
public class NFARAwareResourceForEditorInputFactory extends URIBasedStorageEditorInputFactory {

	@Inject
	private IN4JSEclipseCore eclipseCore;

	@Override
	protected ResourceSet getResourceSet(URI uriFromStorage) {
		if (uriFromStorage.isArchive()) {
			Optional<? extends IN4JSSourceContainer> containerOpt = eclipseCore.findN4JSSourceContainer(uriFromStorage);
			if (containerOpt.isPresent()) {
				IN4JSEclipseProject project = (IN4JSEclipseProject) containerOpt.get().getProject();
				return getResourceSetProvider().get(project.getProject());
			}
		}
		return getResourceSetProvider().get(null);
	}

}
