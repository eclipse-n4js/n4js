/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.scoping.builtin;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.n4js.xtext.resourceset.EmptyAuthorityAddingNormalizer;
import org.eclipse.n4js.xtext.resourceset.XtextResourceLocator;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.util.UriExtensions;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Resource set provider for the standalone environment.
 *
 * The created resource set is configured such that it supports reading resources with the n4scheme.
 */
@Singleton
public class ConfiguredResourceSetProvider extends BasicResourceSetProvider {

	private final BuiltInSchemeRegistrar builtInSchemeRegistrar;
	private final ClassLoader classLoader;
	private final ResourceSet resourceSetWithBuiltIns;
	private final UriExtensions uriExtensions;

	/**
	 * Standard constructor used by the injector.
	 */
	@Inject
	public ConfiguredResourceSetProvider(
			BuiltInSchemeRegistrar builtInSchemeRegistrar,
			ClassLoader classLoader,
			ResourceSetWithBuiltInSchemeProvider builtInProvider,
			UriExtensions uriExtensions) {
		this.builtInSchemeRegistrar = builtInSchemeRegistrar;
		this.classLoader = classLoader;
		this.uriExtensions = uriExtensions;
		this.resourceSetWithBuiltIns = builtInProvider.getResourceSet();
	}

	@Override
	public SynchronizedXtextResourceSet get() {
		SynchronizedXtextResourceSet result = super.get();
		result.setClasspathURIContext(classLoader);
		initialize(result);
		return result;
	}

	/* package */ <T extends SynchronizedXtextResourceSet> T getOfType(Class<T> type) {
		T result;
		try {
			result = type.getDeclaredConstructor().newInstance();
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException
				| NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		result.setClasspathURIContext(classLoader);
		initialize(result);
		return result;
	}

	/**
	 * Initialize the given resource set.
	 */
	protected void initialize(SynchronizedXtextResourceSet resourceSet) {
		attachXtextResourceLocator(resourceSet);
		attachBuiltInSchemeResourceLocator(resourceSet);
		resourceSet.setURIConverter(new EmptyAuthorityAddingNormalizer(resourceSet.getURIConverter(), uriExtensions));
		builtInSchemeRegistrar.registerScopes(resourceSet, resourceSetWithBuiltIns);
	}

	private ResourceLocator attachBuiltInSchemeResourceLocator(SynchronizedXtextResourceSet result) {
		return new BuiltInSchemeResourceLocator(result, resourceSetWithBuiltIns);
	}

	private ResourceLocator attachXtextResourceLocator(SynchronizedXtextResourceSet resourceSet) {
		return new XtextResourceLocator(resourceSet);
	}

}
