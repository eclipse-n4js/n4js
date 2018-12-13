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
package org.eclipse.n4js.ts.ui.navigation;

import java.util.Collections;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeRegistrar;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.xtext.ui.resource.IStorage2UriMapperContribution;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;

/**
 */
public class BuiltinSchemeUriMapperContribution implements IStorage2UriMapperContribution {

	@Inject
	private BuiltInSchemeRegistrar registrar;

	@Override
	public void initializeCache() {
		// nothing to do
	}

	@Override
	public boolean isRejected(IFolder folder) {
		return false;
	}

	@Override
	public Iterable<Pair<IStorage, IProject>> getStorages(URI uri) {
		if (N4Scheme.isN4Scheme(uri)) {
			Pair<IStorage, IProject> result = Tuples.create(new N4SchemeURIBasedStorage(uri.trimFragment(), registrar),
					null);
			return Collections.singletonList(result);
		}
		return Collections.emptyList();
	}

	@Override
	public URI getUri(IStorage storage) {
		if (storage instanceof IURIBasedStorage) {
			return ((IURIBasedStorage) storage).getURI();
		}
		return null;
	}

}
