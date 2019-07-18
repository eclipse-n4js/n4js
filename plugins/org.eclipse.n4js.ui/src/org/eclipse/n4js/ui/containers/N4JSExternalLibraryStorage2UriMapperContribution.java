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
package org.eclipse.n4js.ui.containers;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.ts.ui.navigation.URIBasedStorage;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseProject;
import org.eclipse.n4js.utils.resources.IExternalResource;
import org.eclipse.xtext.ui.resource.IStorage2UriMapperContribution;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implementation of a {@link IStorage storage} to {@link URI URI} mapper contribution for external N4JS libraries.
 */
@Singleton
public class N4JSExternalLibraryStorage2UriMapperContribution implements IStorage2UriMapperContribution {

	@Inject
	private IN4JSEclipseCore eclipseCore;

	@Override
	public void initializeCache() {
		// Does nothing.
	}

	@Override
	public boolean isRejected(final IFolder folder) {
		return false;
	}

	@Override
	public Iterable<Pair<IStorage, IProject>> getStorages(final URI uri) {
		final IN4JSEclipseProject project = eclipseCore.findProject(uri).orNull();
		if (null != project && project.exists()) {
			final IProject resourceProject = project.getProject();
			return singleton(Tuples.create(new URIBasedStorage(uri), resourceProject));
		}
		return emptyList();
	}

	@Override
	public URI getUri(final IStorage storage) {
		if (storage instanceof IExternalResource) {
			final File externalResource = ((IExternalResource) storage).getExternalResource();
			if (externalResource.isFile()) {
				return new FileURI(externalResource).toURI();
			}
		}
		return null;
	}

}
