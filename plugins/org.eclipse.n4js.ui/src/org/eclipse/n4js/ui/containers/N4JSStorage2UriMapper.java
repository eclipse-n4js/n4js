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
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.utils.resources.ExternalFile;
import org.eclipse.n4js.utils.resources.IExternalResource;
import org.eclipse.xtext.ui.resource.Storage2UriMapperImpl;
import org.eclipse.xtext.ui.resource.UriValidator;

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
					return URI.createFileURI(externalResource.getAbsolutePath());
				}
			} else {
				return URI.createPlatformResourceURI(storage.getFullPath().toString(), true);
			}
		}
		return null;
	}

}
