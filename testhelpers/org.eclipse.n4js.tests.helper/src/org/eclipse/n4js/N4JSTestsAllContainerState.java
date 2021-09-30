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
package org.eclipse.n4js;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.FlatResourceSetBasedAllContainersState;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersStateProvider;

/**
 * An {@link IAllContainersState} for N4JS testing with synthesized workspace. (Xpect files without workspace setup or
 * JUnit tests)
 *
 * This container state declares two containers. A 'test' container and a 'N4Scheme' container.
 *
 * On a container level all the testing resources (e.g. Xpect *.xt resources or synthesized N4JS resources of JUnit
 * tests) reside in the 'test' container. All the N4Scheme resources reside in the 'N4Scheme' container. Both of the
 * containers are invisible to each other.
 *
 * The built-in types, which are defined in the N4Scheme resources, are not included via container-based scoping but in
 * an explicit manner.
 */
public class N4JSTestsAllContainerState extends FlatResourceSetBasedAllContainersState {

	/**
	 * A provider for {@link N4JSTestsAllContainerState}.
	 */
	public static class Provider extends ResourceSetBasedAllContainersStateProvider {
		@Override
		public IAllContainersState get(IResourceDescriptions context) {
			return new N4JSTestsAllContainerState(this.getResourceSet(context));
		}
	}

	private static final String N4SCHEME_HANDLE = N4Scheme.SCHEME;
	private static final String TEST_HANDLE = "test";

	/**
	 * Initializes a new {@link N4JSTestsAllContainerState} with the given ResourceSet.
	 */
	public N4JSTestsAllContainerState(ResourceSet set) {
		super(set);
	}

	@Override
	public boolean isEmpty(String handle) {
		// For both handles we can assume that they aren't empty
		// (we must always have test resources as well as n4scheme resources)
		if (handle.equals(TEST_HANDLE) || handle.equals(N4SCHEME_HANDLE)) {
			return false;
		} else {
			// unknown handles are assumed to be empty
			return true;
		}
	}

	@Override
	public List<String> getVisibleContainerHandles(String handle) {
		// the two known container handles can only see themselves.
		if (handle.equals(TEST_HANDLE) || handle.equals(N4SCHEME_HANDLE)) {
			return Collections.singletonList(handle);
		} else {
			// unknown handles can't see anything
			return Collections.emptyList();
		}
	}

	@Override
	public Collection<URI> getContainedURIs(String containerHandle) {
		if (containerHandle.equals(TEST_HANDLE)) {
			Collection<URI> allResourceURIs = super.getContainedURIs(
					FlatResourceSetBasedAllContainersState.getHandle());
			// exclude all the n4scheme URIs
			return allResourceURIs.parallelStream()
					.filter(uri -> !N4Scheme.isN4Scheme(uri))
					.collect(Collectors.toList());
		} else if (containerHandle.equals(N4SCHEME_HANDLE)) {
			Collection<URI> allResourceURIs = super.getContainedURIs(
					FlatResourceSetBasedAllContainersState.getHandle());
			// exclude all non-n4scheme URIs
			return allResourceURIs.parallelStream()
					.filter(uri -> N4Scheme.isN4Scheme(uri))
					.collect(Collectors.toList());
		} else {
			// in case of an unknown handle
			return Collections.emptyList();
		}
	}

	@Override
	public String getContainerHandle(URI uri) {
		if (N4Scheme.isN4Scheme(uri)) {
			return N4SCHEME_HANDLE;
		} else {
			return TEST_HANDLE;
		}
	}
}
