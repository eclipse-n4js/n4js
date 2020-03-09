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
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.ISynchronizable;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Utility that decides if a resource can be loaded from the index into a given resource set.
 *
 * This is specialized in the UI context to consider the dirty state.
 */
@Singleton
public class CanLoadFromDescriptionHelper {

	@Inject
	private IN4JSCore n4jsCore;

	/**
	 * Tells whether the load-from-source behavior is entirely deactivated.
	 */
	public boolean isLoadFromSourceDeactivated() {
		return true; // deactivated as of IDE-3180
	}

	/*
	 * This method is added to improve readability, e.g. if (canBeLoadedFromDescription(..)) vs if
	 * (!mustBeLoadedFromSource(..)).
	 */
	/**
	 * Returns true if the given resourceURI points to a resource that is allowed to be loaded from the index into the
	 * given resource set.
	 *
	 * This is the inverse operation to {@link #mustLoadFromSource(URI, ResourceSet)}.
	 *
	 * @param resourceURI
	 *            the URI of the to-be-loaded resource
	 * @param resourceSet
	 *            the target resource set
	 * @return true, if the resource can be loaded from source.
	 */
	public boolean canLoadFromDescription(URI resourceURI, ResourceSet resourceSet) {
		return !mustLoadFromSource(resourceURI, resourceSet);
	}

	/**
	 * Returns true if the given resourceURI points to a resource that is must be loaded from source into the given
	 * resource set. A resource must be loaded from source if the resource set contains resources, that were loaded from
	 * source and that are in the transitive closure of dependencies of the given resource.
	 *
	 * This is the inverse operation to {@link #canLoadFromDescription(URI, ResourceSet)}.
	 *
	 * @param resourceURI
	 *            the URI of the to-be-loaded resource
	 * @param resourceSet
	 *            the target resource set
	 * @return true, if the resource must be loaded from source.
	 */
	public boolean mustLoadFromSource(URI resourceURI, ResourceSet resourceSet) {
		if (isLoadFromSourceDeactivated()) {
			return false;
		}

		// We do already know the resource. Nothing fancy to happen here.
		Resource knownResource = resourceSet.getResource(resourceURI, false);
		if (knownResource != null) {
			return false;
		}

		/*
		 * Iterate all the resources in the resource set and check the instances that have been loaded from source. If
		 * the to-be-loaded resource has a transitive dependency to any resource, that was loaded from source, we need
		 * to load this resource from source, too.
		 */
		Set<URI> sourceURIs = Sets.newHashSet();
		/*
		 * We load resources concurrently in tests thus we may face a concurrent modification exception here if we
		 * simply iterate over the resources. Therefore a classical for loop is used here.
		 */
		List<Resource> listOfResources = resourceSet.getResources();
		for (int i = 0, size = listOfResources.size(); i < size; i++) {
			Resource existingResource = listOfResources.get(i);
			if (existingResource instanceof N4JSResource) {
				N4JSResource casted = (N4JSResource) existingResource;
				if (!casted.isLoadedFromDescription()) {
					sourceURIs.add(casted.getURI());
				}
			}
		}
		return dependsOnAny(resourceURI, sourceURIs, getIndex(resourceSet), true);
	}

	/**
	 * Tells if the receiving resource is among the dependencies of the resource represented by the given URI. For a
	 * definition of "dependencies", see {@link UserDataMapper#readDependenciesFromDescription(IResourceDescription)}.
	 */
	public boolean dependsOnAny(Resource resource, Set<URI> others) {
		if (dependsOnAny(
				resource.getURI(),
				others,
				getIndex(resource.getResourceSet()),
				false)) {
			return true;
		}
		return false;
	}

	/**
	 * Facade to access the index from subclasses.
	 */
	protected IResourceDescriptions getIndex(ResourceSet resourceSet) {
		return n4jsCore.getXtextIndex(resourceSet);
	}

	/**
	 * Returns true if the resource denoted by thisURI is part of a dependency cycle.
	 *
	 * @param thisURI
	 *            the resource
	 * @param index
	 *            the index to be used
	 * @return true, if this resource is part of a cycle.
	 */
	public boolean isPartOfDependencyCycle(URI thisURI, IResourceDescriptions index) {
		return dependsOnAny(thisURI, Collections.singleton(thisURI), index, true);
	}

	/**
	 * Checks if the resource denoted by {@code thisURI} has a transitive dependency to any of the resources in others.
	 * If others is empty, returns false.
	 *
	 * Implements a BFS algorithm.
	 *
	 * The direct dependencies are taken from the index. If a resource is missing form the index, we consider a
	 * dependency to exist so in that sense we are pessimistic. If a resource description is available from the index
	 * but does not have any dependency information, we consider a dependency to exist, too.
	 *
	 * @param thisURI
	 *            the URI under consideration.
	 * @param candidates
	 *            the URIs to be checked against
	 * @param index
	 *            the index to be used.
	 * @param considerOnlySameProject
	 *            flag to consider / ignore project boundaries.
	 * @return true, if this resource has a transitive dependency to any of the others.
	 */
	protected boolean dependsOnAny(URI thisURI, Set<URI> candidates, IResourceDescriptions index,
			boolean considerOnlySameProject) {
		IN4JSProject thisProject = null;
		if (considerOnlySameProject && !candidates.isEmpty()) {
			// early check whether the candidates stem from the same project as the requested thisURI
			// (note: this is based on the assumption that there cannot be a cyclic dependency between modules of
			// different projects, because cyclic dependencies between projects are disallowed)
			thisProject = n4jsCore.findProject(thisURI).orNull();
			candidates = filterCandidatesByProject(candidates, thisProject);
		}
		// are there any relevant candidates at all?
		if (candidates.isEmpty()) {
			return false;
		}
		// Keep track of all visited URIs
		final Set<URI> visited = Sets.newHashSet();
		// breadth first search since it is more likely to find resources from the same project
		// in our own dependencies rather than in the transitive dependencies
		final Queue<URI> queue = new ArrayDeque<>();
		// the starting point. It is deliberately not added to the visited resources
		// to allow to detect cycles.
		queue.add(thisURI);
		while (!queue.isEmpty()) {
			// try to find the direct dependencies for the next URI in the queue
			Optional<List<String>> dependencies = readDirectDependencies(index, queue.poll());
			if (!dependencies.isPresent()) {
				// none found - be pessimistic and announce a dependency
				return true;
			}
			// traverse the direct dependencies
			for (String dependency : dependencies.get()) {
				// and convert each string based dependency to a URI
				URI dependencyURI = URI.createURI(dependency);
				// mark the dependency as visited and if its the first occurrence
				if (visited.add(dependencyURI)) {
					// are we only interested in the project local dependency graph?
					// or does the initial URI and the current candidate stem from the same project?
					if (!considerOnlySameProject || projectContainsURI(thisProject, dependencyURI)) {
						// it is part of the interesting resources, return true
						if (candidates.contains(dependencyURI)) {
							return true;
						}
						// enque the dependency
						queue.add(dependencyURI);
					}
				}
			}
		}
		// the entire relevant graph was successfully traversed. There is no transitive dependency
		// to one of the candidates
		return false;
	}

	private Set<URI> filterCandidatesByProject(Set<URI> candidates, IN4JSProject project) {
		if (project == null) {
			return Collections.emptySet();
		}
		return FluentIterable.from(candidates)
				.filter(candidate -> projectContainsURI(project, candidate))
				.toSet();
	}

	private boolean projectContainsURI(IN4JSProject project, URI candidate) {
		return project.equals(n4jsCore.findProject(candidate).orNull());
	}

	private Optional<List<String>> readDirectDependencies(IResourceDescriptions index, URI next) {
		IResourceDescription description = index.getResourceDescription(next);
		return Optional.ofNullable(description).flatMap(UserDataMapper::readDependenciesFromDescription);
	}

	/**
	 * Create a resource in the context of the given resource set. This is only necessary when working with
	 * {@link UserDataAwareScope} where we don't use {@link ResourceSet#getResource(URI, boolean) getResource} but
	 * {@link ResourceSet#createResource(URI) createResource} to implement a custom semantics with
	 * {@link N4JSResource#loadFromDescription(IResourceDescription)}.
	 *
	 * It is not a generic replacement for all clients of the resource set.
	 */
	public Resource createResource(ResourceSet resourceSet, URI resourceURI) {
		/*
		 * Implementation note: In LSP we need to redirect the request to create a resource to a well defined resource
		 * set. LSP associates projects to resource sets and we have to make sure that we do not create resources in the
		 * wrong project context. Therefore this method if overridden for LSP.
		 */
		if (resourceSet instanceof ISynchronizable<?>) {
			synchronized (((ISynchronizable<?>) resourceSet).getLock()) {
				Resource resource = resourceSet.getResource(resourceURI, false);
				if (resource == null) {
					resource = resourceSet.createResource(resourceURI);
				}
				return resource;
			}
		} else {
			return resourceSet.createResource(resourceURI);
		}
	}

}
