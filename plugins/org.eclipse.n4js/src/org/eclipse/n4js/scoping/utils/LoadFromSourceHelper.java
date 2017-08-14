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
package org.eclipse.n4js.scoping.utils;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.UserdataMapper;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.util.Strings;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 *
 */
@Singleton
public class LoadFromSourceHelper {

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 */
	public boolean mustLoadFromSource(URI resourceURI, ResourceSet resourceSet) {
		// in case of a cyclic dependency between contextResource and resourceURI, we need to force loading from
		// source file (because Xtext index is out-dated); but no need to check for a full cycle, because we already
		// know contextResource depends on resourceURI

		Resource knownResource = resourceSet.getResource(resourceURI, false);
		if (knownResource != null) {
			return false;
		}

		// FIXME extract method
		for (Resource existingResource : resourceSet.getResources()) {
			if (existingResource instanceof N4JSResource) {
				N4JSResource casted = (N4JSResource) existingResource;
				if (!casted.isLoadedFromDescription()) {
					// TODO check is same project in place in isBackwardDR?
					if (isBackwardDependentResource(casted, resourceURI)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Tells if the receiving resource is among the dependencies of the resource represented by the given URI. For a
	 * definition of "dependencies", see {@link UserdataMapper#readDependenciesFromDescription(IResourceDescription)}.
	 */
	public boolean isBackwardDependentResource(Resource resource, URI other) {
		return isTransitivlyDependingOn(other, resource.getURI(), resource.getResourceSet(), true);
	}

	/**
	 * Tells if the receiving resource is among the dependencies of the resource represented by the given URI. For a
	 * definition of "dependencies", see {@link UserdataMapper#readDependenciesFromDescription(IResourceDescription)}.
	 */
	public boolean isDependingOn(Resource resource, Set<URI> others) {
		if (isTransitivlyDependingOn(resource.getURI(), others, getIndex(resource.getResourceSet()),
				false)) {
			return true;
		}
		return false;
	}

	protected IResourceDescriptions getIndex(ResourceSet resourceSet) {
		return n4jsCore.getXtextIndex(resourceSet);
	}

	protected boolean isTransitivlyDependingOn(URI thisURI, URI other, ResourceSet resSet,
			boolean considerOnlySameProject) {
		if (other.equals(thisURI)) {
			return false;
		}
		if (considerOnlySameProject && !n4jsCore.isInSameProject(thisURI, other)) {
			return false;
		}
		return isTransitivlyDependingOn(thisURI, Collections.singleton(other), n4jsCore.getXtextIndex(resSet),
				considerOnlySameProject);
	}

	public boolean isPartOfCycle(URI thisURI, IResourceDescriptions resourceDescriptions) {
		return isTransitivlyDependingOn(thisURI, Collections.singleton(thisURI), resourceDescriptions, true);
	}

	protected boolean isTransitivlyDependingOn(URI thisURI, Set<URI> others, IResourceDescriptions index,
			boolean considerOnlySameProject) {
		if (others.isEmpty()) {
			return false;
		}
		final Set<URI> seen = Sets.newHashSet();
		// breaths first search since it is more likely to find resources from the same project
		// in our own dependencies rather than in the transitive dependencies
		final ArrayDeque<URI> next = new ArrayDeque<>();
		next.add(thisURI);
		while (!next.isEmpty()) {
			IResourceDescription description = index.getResourceDescription(next.pollFirst());
			if (description == null) {
				return true;
			}
			String[] dependencies = UserdataMapper.readDependenciesFromDescription(description);
			if (dependencies != null) {
				for (String dependency : dependencies) {
					if (!Strings.isEmpty(dependency)) {
						URI dependencyURI = URI.createURI(dependency);
						if (seen.add(dependencyURI)) {
							if (considerOnlySameProject) {
								if (n4jsCore.isInSameProject(thisURI, dependencyURI)) {
									if (others.contains(dependencyURI)) {
										return true;
									}
									next.add(dependencyURI);
								}
							} else {
								if (others.contains(dependencyURI)) {
									return true;
								}
								next.add(dependencyURI);
							}
						}
					}
				}
			}
		}
		return false;
	}

}
