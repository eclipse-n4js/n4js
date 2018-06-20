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

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;
import org.eclipse.xtext.builder.resourceloader.IResourceLoader;
import org.eclipse.xtext.builder.resourceloader.ResourceLoaderProviders.AbstractResourceLoaderProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

/**
 * Custom provider for parallel resource loader.
 *
 * Adjusted to return our own {@link BuildScopeAwareParallelLoader} and skip the registration of the
 * {@link BuiltInTypeScope} in the loaders resource set.
 */
@SuppressWarnings("restriction")
public class BuildScopeAwareParallelLoaderProvider extends AbstractResourceLoaderProvider {

	private static final class ProviderOfEmptyResourceSet implements IResourceSetProvider {
		@Override
		public ResourceSet get(IProject project) {
			return new XtextResourceSet();
		}
	}

	private static final int nrOfThreads;

	static {
		// default impl that is proven to work nicely and provide a good scale between i/o and parsing performance.
		int nProcessors = Runtime.getRuntime().availableProcessors();
		int nThreads = Math.max(2, Math.min(4, nProcessors));
		nrOfThreads = nThreads;
	}

	@Override
	public IResourceLoader get() {
		BuildScopeAwareParallelLoader resourceLoader = new BuildScopeAwareParallelLoader(getResourceSetProvider(),
				getResourceSorter(), nrOfThreads, 0);
		return resourceLoader;
	}

	@Override
	public IResourceSetProvider getResourceSetProvider() {
		return new ProviderOfEmptyResourceSet();
	}
}
