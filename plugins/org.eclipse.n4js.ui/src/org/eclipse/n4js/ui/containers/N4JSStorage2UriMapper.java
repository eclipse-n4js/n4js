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

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.resources.ExternalFile;
import org.eclipse.n4js.utils.resources.IExternalResource;
import org.eclipse.xtext.ui.resource.FileStoreStorage;
import org.eclipse.xtext.ui.resource.Storage2UriMapperImpl;
import org.eclipse.xtext.ui.resource.UriValidator;
import org.eclipse.xtext.util.Pair;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * N4JS specific {@link IStorage storage} to {@link URI URI} mapper that gracefully handles the URI creation for
 * {@link IExternalResource external resource} instances as well.
 */
@Singleton
public class N4JSStorage2UriMapper extends Storage2UriMapperImpl {

	@Inject
	private UriValidator uriValidator;

	/**
	 * GH-1190: disable a new behavior of Xtext, which adds pairs <code>FileStoreStorage -> null</code> for file URIs
	 * (see private method <code>#getStorages(URI, IFile)</code> of super class), because these pairs will lead to a
	 * NullPointerException in Xtext's {@code ToBeBuiltComputer#doRemoveProject(IProject, IProgressMonitor)} when
	 * applied to a project located in the external library workspace.
	 */
	@Override
	public Iterable<Pair<IStorage, IProject>> getStorages(URI uri) {
		Iterable<Pair<IStorage, IProject>> storages = super.getStorages(uri);
		if (!uri.isPlatformResource() && uri.isFile()) {
			storages = super.getContribution().getStorages(uri);
		} else {
			storages = super.getStorages(uri);
		}
		return Iterables.filter(storages,
				pair -> !(pair != null && pair.getFirst() instanceof FileStoreStorage));
	}

	@Override
	public URI getUri(IStorage storage) {
		if (!uriValidator.isPossiblyManaged(storage)) {
			return null;
		}
		URI uri = internalGetUri(storage);

		if (null == uri) {
			uri = super.getUri(storage);
		}

		if (uri != null && isValidUri(uri, storage)) {
			return uri;
		}
		return null;
	}

	private URI internalGetUri(IStorage storage) {
		if (storage instanceof IFile) {
			if (storage instanceof ExternalFile) {
				final File externalResource = ((ExternalFile) storage).getExternalResource();
				if (externalResource.isFile()) {
					return new FileURI(externalResource).toURI();
				}
			} else {
				return URI.createPlatformResourceURI(storage.getFullPath().toString(), true);
			}
		}
		return null;
	}

}
