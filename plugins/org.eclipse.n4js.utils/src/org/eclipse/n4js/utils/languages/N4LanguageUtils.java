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
package org.eclipse.n4js.utils.languages;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceServiceProvider;

/**
 * Utility methods for our custom Xtext languages (N4JS, N4JSX, N4IDL, ...) that are <em>language independent</em>, i.e.
 * applicable without change to all those languages.
 * <p>
 * Note that there are language-specific variants of this class, e.g. {@code N4JSLanguageUtils}.
 */
public class N4LanguageUtils {

	/**
	 * Same as {@link #getServiceForContext(URI, Class)}, but accepts any {@link EObject} contained in an Xtext language
	 * resource. Returns <code>null</code> if the given context object is not contained in a {@link Resource}.
	 */
	public static <T> Optional<T> getServiceForContext(EObject context, Class<T> serviceType) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(serviceType);
		final Resource res = context.eResource();
		final URI uri = res != null ? res.getURI() : null;
		return uri != null ? getServiceForContext(uri, serviceType) : Optional.empty();
	}

	/**
	 * Same as {@link #getServiceForContext(URI, Class)}, but accepts any {@link IFile} as context and converts it to
	 * the {@link URI}. Returns <code>null</code> if the given file object cannot be converted to the {@link Resource
	 * resource uri}.
	 *
	 * @see <a href="https://wiki.eclipse.org/EMF/FAQ#How_do_I_map_between_an_EMF_Resource_and_an_Eclipse_IFile.3F">How
	 *      do I map between an EMF Resource and an Eclipse IFile?</a>
	 */
	public static <T> Optional<T> getServiceForContext(IFile iFile, Class<T> serviceType) {
		Objects.requireNonNull(iFile);
		Objects.requireNonNull(serviceType);
		final URI uri = URI.createPlatformResourceURI(iFile.getFullPath().toString(), true);
		return uri != null ? getServiceForContext(uri, serviceType) : Optional.empty();
	}

	/**
	 * Same as {@link #getServiceForContext(URI, Class)}, but accepts any {@link IResource} as context and converts it
	 * to the {@link URI}. Returns <code>null</code> if the given file object cannot be converted to the {@link Resource
	 * resource uri}.
	 *
	 * @see <a href="https://wiki.eclipse.org/EMF/FAQ#How_do_I_map_between_an_EMF_Resource_and_an_Eclipse_IFile.3F">How
	 *      do I map between an EMF Resource and an Eclipse IFile?</a>
	 */
	public static <T> Optional<T> getServiceForContext(IResource iResource, Class<T> serviceType) {
		Objects.requireNonNull(iResource);
		Objects.requireNonNull(serviceType);
		final URI uri = URI.createPlatformResourceURI(iResource.getFullPath().toString(), true);
		return uri != null ? getServiceForContext(uri, serviceType) : Optional.empty();
	}

	/**
	 * Utility method for obtaining the correct instance of a language-specific service for the context identified by
	 * the given {@link URI}. For example, if the URI denotes an N4JS resource, this method will return the service
	 * instance for N4JS, if it denotes an N4JSX resource the service instance for N4JSX will be returned.
	 * <p>
	 * Using this method is like injecting a service with <code>@Inject</code> but makes sure that the correct service
	 * instance is used for the given context URI.
	 *
	 * @param uri
	 *            the URI of an Xtext language resource, e.g. an N4JS, N4JSX, or N4IDL resource.
	 * @param serviceType
	 *            the type of the service to obtain.
	 * @return the service instance or {@link Optional#empty()} if no Xtext resource service provide was found for the
	 *         given URI.
	 */
	public static <T> Optional<T> getServiceForContext(URI uri, Class<T> serviceType) {
		Objects.requireNonNull(uri);
		Objects.requireNonNull(serviceType);
		final IResourceServiceProvider serviceProvider = IResourceServiceProvider.Registry.INSTANCE
				.getResourceServiceProvider(uri);
		return serviceProvider != null ? Optional.ofNullable(serviceProvider.get(serviceType)) : Optional.empty();
	}
}
