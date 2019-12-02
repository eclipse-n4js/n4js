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
package org.eclipse.n4js.ts.scoping.builtin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;
import org.eclipse.xtext.resource.ClassloaderClasspathUriResolver;
import org.eclipse.xtext.resource.ClasspathUriUtil;

import com.google.inject.Inject;

/**
 */
public class BuiltInSchemeRegistrar implements N4Scheme {

	private static final URI SAMPLE_URI = URI.createURI(SCHEME + ":/unnecessary");

	@Inject
	private ClassLoader classLoader;

	/**
	 * Configure the resourceSet such that it understands the n4js scheme. Use the injected classLoader to lookup
	 * resources.
	 */
	public void registerScheme(ResourceSet resourceSet) {
		// tell EMF to resolve a classpath URI which actually has not been a classpath URI (but a SCHEME/n4js
		// URI):
		URIConverter converter = resourceSet.getURIConverter();
		if (registerScheme(converter, classLoader)) {
			ExecutionEnvironmentDescriptor descriptor = new ExecutionEnvironmentDescriptor(resourceSet);
			register(resourceSet, descriptor);
		}
	}

	/**
	 * Register all well defined scopes at the resource set.
	 */
	protected void register(ResourceSet resourceSet, ExecutionEnvironmentDescriptor descriptor) {
		BuiltInTypeScope typeScope = new BuiltInTypeScope(descriptor);
		BuiltInTypeScopeAccess.registerBuiltInTypeScope(typeScope, resourceSet);
	}

	/**
	 * Configure the uri converter such that it understands the n4js scheme. Use the given classLoader to lookup the
	 * resources.
	 *
	 * @return true if the converter was changed. Otherwise false.
	 */
	public boolean registerScheme(URIConverter converter) {
		return registerScheme(converter, classLoader);
	}

	private boolean registerScheme(URIConverter converter, @SuppressWarnings("hiding") ClassLoader classLoader) {
		URIHandler uriHandler = converter.getURIHandlers().get(0);
		if (uriHandler instanceof MyURIHandler || uriHandler.canHandle(SAMPLE_URI)) {
			return false;
		}
		converter.getURIHandlers().add(0, createURIHandler(classLoader, converter));
		return true;
	}

	private URIHandler createURIHandler(ClassLoader theClassLoader, URIConverter converter) {
		return new MyURIHandler(theClassLoader, converter);
	}

	/**
	 * Creates a new URI Handler for the n4scheme.
	 */
	public URIHandler createURIHandler(URIConverter converter) {
		return createURIHandler(classLoader, converter);
	}

	/**
	 * Converts a n4scheme:/ URI into a classpath URI, so a resource is loaded by this classpath URI.
	 */
	protected static class MyURIHandler extends URIHandlerImpl implements N4Scheme {

		private final ClassLoader classLoader;

		private final ClassloaderClasspathUriResolver uriResolver = new ClassloaderClasspathUriResolver();

		private final URIConverter original;

		/**
		 * Creates a new URI handler that uses the context of the given class loader.
		 *
		 * @param classLoader
		 *            the classloader to use.
		 */
		public MyURIHandler(ClassLoader classLoader, URIConverter original) {
			this.classLoader = classLoader;
			this.original = original;
		}

		@Override
		public boolean canHandle(URI uri) {
			return N4Scheme.isN4Scheme(uri);
		}

		@Override
		public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
			String[] allSegments = new String[uri.segmentCount() + 1];
			allSegments[0] = "env";
			for (int i = 0; i < uri.segmentCount(); i++) {
				allSegments[i + 1] = uri.segment(i);
			}
			URI classpathURI = URI.createHierarchicalURI(
					ClasspathUriUtil.CLASSPATH_SCHEME,
					uri.authority(),
					uri.device(),
					allSegments,
					uri.query(),
					uri.fragment());
			URI resolvedURI = uriResolver.resolve(classLoader, classpathURI);
			return original.createInputStream(resolvedURI, options);
		}
	}

}
