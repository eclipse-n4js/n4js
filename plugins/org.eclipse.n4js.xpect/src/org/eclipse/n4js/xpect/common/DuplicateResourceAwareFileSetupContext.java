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
package org.eclipse.n4js.xpect.common;

import static com.google.common.collect.FluentIterable.from;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xpect.XpectFile;
import org.eclipse.xpect.XpectJavaModel;
import org.eclipse.xpect.state.StateContainer;
import org.eclipse.xpect.util.IXtInjectorProvider;
import org.eclipse.xpect.xtext.lib.setup.FileSetupContext;

import com.google.inject.Injector;

/**
 * Xpect file setup context that allow re-registering a resource for an already existing URI in the resource set.
 */
public class DuplicateResourceAwareFileSetupContext extends FileSetupContext {

	private final FileSetupContext context;

	/**
	 * Creates a new instance with the delegate file setup context instance.
	 *
	 * @param context
	 *            the delegate.
	 */
	public DuplicateResourceAwareFileSetupContext(final FileSetupContext context) {
		super(context.getState(), context.getXpectFile());
		this.context = context;
	}

	/** {@inheritDoc} */
	@Override
	public FileSetupContext create() {
		return context.create();
	}

	/** {@inheritDoc} */
	@Override
	public <T> T get(final Class<T> expectedType, final Object... annotations) {
		return context.get(expectedType, annotations);
	}

	/** {@inheritDoc} */
	@Override
	public StateContainer getState() {
		return context.getState();
	}

	/** {@inheritDoc} */
	@Override
	public XpectFile getXpectFile() {
		return context.getXpectFile();
	}

	/** {@inheritDoc} */
	@Override
	public URI getXpectFileURI() {
		return context.getXpectFileURI();
	}

	/** {@inheritDoc} */
	@Override
	public Resource load(final ResourceSet resourceSet, final URI uri, final InputStream input) throws IOException {
		final Injector injector = IXtInjectorProvider.INSTANCE.getInjector(context.get(XpectJavaModel.class), uri);
		final Resource resource = injector.getInstance(IResourceFactory.class).createResource(uri);
		final Resource existingResousce = from(resourceSet.getResources())
				.firstMatch(r -> r.getURI().equals(resource.getURI())).orNull();
		if (null != existingResousce) {
			// remove the existing one
			resourceSet.getResources().remove(existingResousce);
		}
		resourceSet.getResources().add(resource);
		try {
			resource.load(input, null);
		} finally {
			if (input != null)
				input.close();
		}
		return resource;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return context.hashCode();
	}

	/** {@inheritDoc} */
	@Override
	public URI resolve(final String uri) {
		return context.resolve(uri);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return context.toString();
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object obj) {
		return context.equals(obj);
	}

}
