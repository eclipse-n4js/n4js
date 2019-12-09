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
package org.eclipse.n4js.scoping.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.scoping.PolyfillAwareSelectableBasedScope;
import org.eclipse.n4js.xtext.scoping.ForwardingEObjectDescription;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * A scope that provides access to types stored in user data of {@link EObjectDescription} of JS resources.
 */
public class UserDataAwareScope extends PolyfillAwareSelectableBasedScope {

	/**
	 * A resolved description wraps an existing description but returns the explicitly resolved instance rather than the
	 * proxy from the delegate.
	 *
	 */
	public static class ResolvedDescription extends ForwardingEObjectDescription {

		private final EObject resolved;

		ResolvedDescription(EObject resolved, IEObjectDescription delegate) {
			super(delegate);
			if (resolved == null) {
				throw new NullPointerException("resolved instance may not be null");
			}
			this.resolved = resolved;
		}

		@Override
		public EObject getEObjectOrProxy() {
			return resolved;
		}

	}

	/**
	 * An EObject description that will look as if resolved when asked for EObjectOrProxy but does that only on demand.
	 */
	public class LazyResolvedDescription extends ForwardingEObjectDescription {

		private EObject resolved;

		LazyResolvedDescription(IEObjectDescription delegate) {
			super(delegate);
		}

		@Override
		public EObject getEObjectOrProxy() {
			if (resolved == null) {
				resolved = resolve(delegate()).getEObjectOrProxy();
			}
			return resolved;
		}

	}

	/**
	 * Factory method to produce a scope. The factory pattern allows to bypass the explicit object creation if the
	 * produced scope would be empty.
	 *
	 * @param canLoadFromDescriptionHelper
	 *            utility to decide if a resource must be loaded from source or may be loaded from the index.
	 */
	public static IScope createScope(
			IScope outer,
			ISelectable selectable,
			Predicate<IEObjectDescription> filter,
			EClass type, boolean ignoreCase,
			ResourceSet resourceSet,
			CanLoadFromDescriptionHelper canLoadFromDescriptionHelper,
			IContainer container) {
		if (selectable == null || selectable.isEmpty())
			return outer;
		IScope scope = new UserDataAwareScope(outer, selectable, filter, type, ignoreCase, resourceSet,
				canLoadFromDescriptionHelper,
				container);
		return scope;
	}

	private final ResourceSet resourceSet;
	/**
	 * @see #createScope(IScope, ISelectable, Predicate, EClass, boolean, ResourceSet, CanLoadFromDescriptionHelper,
	 *      IContainer)
	 */
	private final CanLoadFromDescriptionHelper canLoadFromDescriptionHelper;
	private final IContainer container;
	private final EClass type;

	UserDataAwareScope(IScope outer, ISelectable selectable, Predicate<IEObjectDescription> filter, EClass type,
			boolean ignoreCase, ResourceSet resourceSet, CanLoadFromDescriptionHelper canLoadFromDescriptionHelper,
			IContainer container) {
		super(outer, selectable, filter, type, ignoreCase);
		this.resourceSet = resourceSet;
		this.canLoadFromDescriptionHelper = canLoadFromDescriptionHelper;
		this.container = container;
		this.type = type;
	}

	/**
	 * Fetches the single element via the super implementation of the is method. If that single element is a proxy and
	 * its meta class is assignable from the type of this scope class instance it is tried to resolve the resource with
	 * the URI of the proxy. If the resource doesn't exist, it is created. If the resource is already loaded the single
	 * element is returned directly. If the resource is not loaded and its content is empty, the resource content is
	 * reconstructed from the types model in the Xtext index. In the end the EObject is tried to be resolved against the
	 * resource and this result is return (encapsulated in {@link ResolvedDescription}, to avoid the recursive resolving
	 * logic).
	 */
	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		IEObjectDescription result = super.getSingleElement(name);
		return resolve(result);
	}

	private IEObjectDescription lazyResolve(IEObjectDescription original) {
		if (original instanceof LazyResolvedDescription) {
			return original;
		}
		if (original instanceof ResolvedDescription) {
			return original;
		}
		return new LazyResolvedDescription(original);
	}

	private IEObjectDescription resolve(IEObjectDescription original) {
		if (original instanceof ResolvedDescription) {
			return original;
		}
		if (original != null && original.getEObjectOrProxy().eIsProxy()
				&& EcoreUtil2.isAssignableFrom(type, original.getEClass())) {
			final URI objectURI = original.getEObjectURI();
			final URI resourceURI = objectURI.trimFragment();
			Resource resource = resourceSet.getResource(resourceURI, false);
			if (resource != null && resource.isLoaded()) {
				return original;
			}
			final boolean mustLoadFromSource = canLoadFromDescriptionHelper.mustLoadFromSource(resourceURI,
					resourceSet);
			resource = resourceSet.getResource(resourceURI, mustLoadFromSource);
			if (resource != null && resource.isLoaded()) {
				return original;
			}
			if (mustLoadFromSource) {
				// error case: forced loading failed
				// --> still avoid loading from index; instead simply return 'original' as in other error cases
				return original;
			}
			if (resource == null) {
				resource = canLoadFromDescriptionHelper.createResource(resourceSet, resourceURI);
			}
			if (resource instanceof N4JSResource) {
				if (resource.getContents().isEmpty()) {
					N4JSResource casted = (N4JSResource) resource;
					IResourceDescription resourceDescription = container.getResourceDescription(resourceURI);
					if (resourceDescription != null) {
						if (casted.isLoaded()) {
							// LiveShadowingResourceDescriptions (part of Xtext's rename refactoring)
							// will load that stuff on #getResourceDescription
							return original;
						}
						try {
							if (!casted.loadFromDescription(resourceDescription)) {
								return original;
							}
						} catch (Exception e) {
							casted.unload();
							return original;
						}
					} else {
						return original;
					}
				}
				// resolveProxy is implemented recursively thus we have to avoid
				// that here by decorating the
				// description and return the resolved instance instead
				EObject resolved = resolveObject(objectURI, resource);
				if (resolved == null) {
					return original;
				}
				return new ResolvedDescription(resolved, original);
			}
		}
		return original;
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		Iterable<IEObjectDescription> parent = super.getElements(name);
		return Iterables.transform(parent, new Function<IEObjectDescription, IEObjectDescription>() {
			@Override
			public IEObjectDescription apply(IEObjectDescription input) {
				return lazyResolve(input);
			}
		});
	}

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		Iterable<IEObjectDescription> parent = super.getAllElements();
		return Iterables.transform(parent, new Function<IEObjectDescription, IEObjectDescription>() {
			@Override
			public IEObjectDescription apply(IEObjectDescription input) {
				return lazyResolve(input);
			}
		});
	}

	/**
	 * {@link EcoreUtil#resolve(EObject, EObject)} pretty much does the same thing but will eagerly load the resource.
	 * We are happy with the content that we just put into it.
	 */
	private EObject resolveObject(URI objectURI, Resource resource) {
		if (resource.isLoaded())
			throw new IllegalStateException("Should not be loaded beforehand");
		EObject result = resource.getEObject(objectURI.fragment());
		if (resource.isLoaded())
			throw new IllegalStateException("Should not be loaded due to fragment traversal");
		return result;
	}

}
