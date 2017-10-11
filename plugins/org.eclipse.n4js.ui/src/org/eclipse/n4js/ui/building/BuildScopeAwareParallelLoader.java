/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.building;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScopeAccess;
import org.eclipse.xtext.builder.resourceloader.ParallelResourceLoader;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

/**
 * Adapted Xtext's parallel loader to inject the code that is necessary to handle the builtin type scopes.
 */
@SuppressWarnings("restriction")
class BuildScopeAwareParallelLoader extends ParallelResourceLoader {

	private static final class BuiltInTypesReferencingResourceSetProvider implements IResourceSetProvider {
		private final ResourceSet parent;
		private final IResourceSetProvider delegate;

		BuiltInTypesReferencingResourceSetProvider(ResourceSet parent, IResourceSetProvider delegate) {
			this.parent = parent;
			this.delegate = delegate;
		}

		@Override
		public ResourceSet get(IProject project) {
			ResourceSet result = delegate.get(project);

			BuiltInTypeScope typeScope = BuiltInTypeScope.get(parent);
			BuiltInTypeScopeAccess.registerBuiltInTypeScope(typeScope, result);

			return result;
		}
	}

	private IResourceSetProvider resourceSetProvider;

	BuildScopeAwareParallelLoader(IResourceSetProvider resourceSetProvider, Sorter sorter, int nThreads,
			int queueSize) {
		super(resourceSetProvider, sorter, nThreads, queueSize);
	}

	@Override
	public IResourceSetProvider getResourceSetProvider() {
		if (resourceSetProvider == null) {
			throw new IllegalStateException();
		}
		return resourceSetProvider;
	}

	@Override
	public LoadOperation create(ResourceSet parent, IProject project) {
		this.resourceSetProvider = new BuiltInTypesReferencingResourceSetProvider(parent,
				super.getResourceSetProvider());
		LoadOperation result = super.create(parent, project);
		return new LoadOperation() {

			@Override
			public LoadResult next() throws LoadOperationException {
				return result.next();
			}

			@Override
			public void load(Collection<URI> uris) {
				result.load(uris);
			}

			@Override
			public boolean hasNext() {
				return result.hasNext();
			}

			@Override
			public Collection<URI> cancel() {
				try {
					return result.cancel();
				} finally {
					BuildScopeAwareParallelLoader.this.resourceSetProvider = null;
				}
			}
		};
	}
}