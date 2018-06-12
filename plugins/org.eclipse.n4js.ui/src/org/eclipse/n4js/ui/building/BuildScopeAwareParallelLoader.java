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

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScopeAccess;
import org.eclipse.xtext.builder.resourceloader.ParallelResourceLoader;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

/**
 * Adapted Xtext's parallel loader to inject the code that is necessary to handle the builtin type scopes. Also we only
 * parallelize the resource loading, if we have a reasonable number of resources to load.
 */
@SuppressWarnings("restriction")
class BuildScopeAwareParallelLoader extends ParallelResourceLoader {

	final class SerialLoader implements LoadOperation {
		private final Queue<URI> queue = new ArrayDeque<>();
		private final ResourceSet parent;

		public SerialLoader(ResourceSet parent) {
			this.parent = parent;
		}

		@Override
		public LoadResult next() {
			URI uri = queue.poll();
			try {
				Resource resource = parent.getResource(uri, true);
				return new LoadResult(resource, uri);
			} catch (WrappedException e) {
				throw new LoadOperationException(uri, (Exception) e.getCause());
			}
		}

		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public Collection<URI> cancel() {
			return queue;
		}

		@Override
		public void load(Collection<URI> uris) {
			queue.addAll(getSorter().sort(uris));
		}
	}

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
	private final int nThreads;

	BuildScopeAwareParallelLoader(IResourceSetProvider resourceSetProvider, Sorter sorter, int nThreads,
			int queueSize) {
		super(resourceSetProvider, sorter, nThreads, queueSize);
		this.nThreads = nThreads;
	}

	@Override
	public IResourceSetProvider getResourceSetProvider() {
		if (resourceSetProvider == null) {
			throw new IllegalStateException();
		}
		return resourceSetProvider;
	}

	private boolean shouldParallelize(Collection<URI> uris) {
		return uris.size() > nThreads * 3;
	}

	@Override
	public LoadOperation create(ResourceSet parent, IProject project) {
		this.resourceSetProvider = new BuiltInTypesReferencingResourceSetProvider(parent,
				super.getResourceSetProvider());
		// when we only need to load few resources (threshold < 3 * numThreads), we load on the main thread
		return new LoadOperation() {

			LoadOperation delegate;

			@Override
			public LoadResult next() throws LoadOperationException {
				return delegate.next();
			}

			@Override
			public void load(Collection<URI> uris) {
				if (delegate == null) {
					if (shouldParallelize(uris)) {
						delegate = BuildScopeAwareParallelLoader.super.create(parent, project);
					} else {
						delegate = new CheckedLoadOperation(new SerialLoader(parent));
					}
				}
				delegate.load(uris);
			}

			@Override
			public boolean hasNext() {
				return delegate.hasNext();
			}

			@Override
			public Collection<URI> cancel() {
				try {
					return delegate.cancel();
				} finally {
					BuildScopeAwareParallelLoader.this.resourceSetProvider = null;
				}
			}
		};
	}
}