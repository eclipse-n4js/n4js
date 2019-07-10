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
package org.eclipse.n4js.utils;

import static org.eclipse.n4js.utils.N4JSLanguageUtils.isContainedInStaticPolyfillAware;

import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper for static polyfills.
 */
@Singleton
public final class StaticPolyfillHelper {

	@Inject
	private ProjectResolveHelper projectResolver;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ResourceDescriptionsProvider indexAccess;

	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	/**
	 * For a given N4JSResource annotated with {@code @@StaticPolyfillAware} lookup the filling Module. returns
	 * {@code true} if the filling Module exists in the project.
	 */
	public boolean hasStaticPolyfill(Resource resource) {
		// ensure right resource
		if (resource instanceof N4JSResource) {
			final N4JSResource res = (N4JSResource) resource;
			if (isContainedInStaticPolyfillAware(res.getScript())) {

				// TODO GHOLD-196 resolve inconsistency in logic between this method and #findStaticPolyfiller(Resource)
				// (i.e. if possible, delete strategy #1 and only use strategy #2; but make sure this isn't a
				// performance issue, esp. with respect to the call "srcConti.findArtifact(fqn, fileExtension)" in
				// #findStaticPolyfiller(Resource))
				boolean strategyIndex = true;

				if (strategyIndex) {
					// 1. query index
					final QualifiedName qnFilled = qualifiedNameConverter
							.toQualifiedName(res.getModule().getQualifiedName());
					final IResourceDescriptions index = indexAccess.getResourceDescriptions(res.getResourceSet());
					final java.util.Optional<QualifiedName> optQnFilling = N4TSQualifiedNameProvider
							.toStaticPolyfillFQN(qnFilled);
					if (optQnFilling.isPresent()) {
						final QualifiedName qnFilling = optQnFilling.get();

						final Iterable<IEObjectDescription> modules = index
								.getExportedObjectsByType(TypesPackage.Literals.TMODULE);
						for (IEObjectDescription module : modules) {
							if (module.getQualifiedName() == qnFilling) {
								return true;
							}
						}
					}
				} else {
					// 2. query all source-containers for file with same QN
					final SafeURI<?> fillingURI = findStaticPolyfiller(res);
					if (null != fillingURI)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Find the corresponding static-polyfill to this {@code @@PolyfillAware} resource in the same project. returns null
	 * if not found or this resource has no {@code @@PolyfillAware} annotation.
	 */
	public SafeURI<?> findStaticPolyfiller(Resource resource) {
		// ensure right resource
		if (resource instanceof N4JSResource) {
			final N4JSResource res = (N4JSResource) resource;
			if (!isContainedInStaticPolyfillAware(res.getScript()))
				return null;

			final QualifiedName qnFilled = qualifiedNameConverter.toQualifiedName(res.getModule().getQualifiedName());
			final IN4JSProject project = projectResolver.resolveProject(res.getURI());
			final QualifiedName fqn = qnFilled;
			final Optional<String> fileExtension = Optional.of(res.getURI().fileExtension()); // see Req.155#4: "Both
																								// extensions are
																								// equal."
			final IN4JSSourceContainer filledSrcContainer = n4jsCore.findN4JSSourceContainer(res.getURI()).get();
			for (IN4JSSourceContainer srcConti : project.getSourceContainers()) {
				if (!Objects.equals(filledSrcContainer, srcConti)) {
					final SafeURI<?> uri = srcConti.findArtifact(fqn, fileExtension);
					if (uri != null) {
						return uri;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Helper to load a StaticPolyfill resource into the same ResourceSet. Takes an {@code @@StaticPolyfillAware}
	 * {@code N4JSResource}. Returns the filling resource if any or {@code null}
	 */
	public N4JSResource getStaticPolyfillResource(Resource res) {
		final SafeURI<?> uri = findStaticPolyfiller(res);
		if (null != uri) {
			return (N4JSResource) res.getResourceSet().getResource(uri.toURI(), true);
		}
		return null;
	}

	/**
	 * Currently the StaticPolyfill Annotation is only allowed on N4ClassDeclarations. If type is something else
	 * {@code null} will be returned. N4TypeDefinition <: (N4Enum, N4Classifier <: ( N4Interface, N4Classdefintion <:
	 * (N4Classdeclaration,N4classExpression)))
	 *
	 * The containing resource will be loaded on demand in the same ResourceSet, in which the passed in type is kept.
	 */
	public N4ClassDeclaration getStaticPolyfill(Type type) {
		return getStaticPolyfill(getStaticPolyfillResource(type.eResource()), type);
	}

	/**
	 * Same as {@link #getStaticPolyfill}, but the resource of the filler can be provided in case it is already known
	 * (to avoid unnecessary search).
	 */
	public N4ClassDeclaration getStaticPolyfill(N4JSResource fillingResource, Type type) {
		if (type instanceof TClass) {
			if (fillingResource == null)
				return null;

			final Script scriptFiller = fillingResource.getScriptResolved();
			if (null != scriptFiller) {
				final N4ClassDeclaration staticPolyfiller = EcoreUtil2
						.getAllContentsOfType(scriptFiller, N4ClassDeclaration.class).stream().filter(it -> {
							return it.getDefinedTypeAsClass().isDeclaredStaticPolyfill() // is a static-polyfill
									&& it.getSuperClassRef().getDeclaredType() == type; // extends this class explicitly
						}).findFirst().orElseGet(() -> {
							return null;
						});

				return staticPolyfiller;
			}
		}
		return null;
	}

}
