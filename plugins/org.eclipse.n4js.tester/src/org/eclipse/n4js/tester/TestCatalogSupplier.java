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
package org.eclipse.n4js.tester;

import java.util.Collections;
import java.util.function.Function;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.tester.domain.TestTree;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

/**
 * Service for supplying the test catalog based on all tests available in the ({@link IN4JSCore N4JS core} based)
 * workspace. The content of the provided test catalog depends on the built state of the workspace.
 * <p>
 * By default, i.e. when method {@link #get(Function)} is invoked, the generated JSON will include a top-level property
 * "endpoint" with a URL pointing to the Jetty server for test reporting. Method {@link #get(Function, boolean)} may be
 * used to avoid this property.
 */
public class TestCatalogSupplier {

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private TestTreeTransformer treeTransformer;

	@Inject
	private TestDiscoveryHelper testDiscoveryHelper;

	/**
	 * Returns with the test catalog as a string representing all available tests in the workspace. This method may
	 * return with a test catalog of an empty {@link TestTree test tree} if the the {@link Platform platform} is not
	 * running.
	 *
	 * @return the test catalog as a JSON formatted string.
	 */
	public String get() {
		ResourceSet resourceSet = testDiscoveryHelper.newResourceSet();
		return get(any -> resourceSet, false);
	}

	/**
	 * Returns with the test catalog as a string representing all available tests in the workspace. This method may
	 * return with a test catalog of an empty {@link TestTree test tree} if the the {@link Platform platform} is not
	 * running.
	 *
	 * @return the test catalog as a JSON formatted string.
	 */
	public String get(boolean suppressEndpointProperty) {
		ResourceSet resourceSet = testDiscoveryHelper.newResourceSet();
		return get(any -> resourceSet, suppressEndpointProperty);
	}

	/**
	 * Returns with the test catalog as a string representing all available tests in the workspace. This method may
	 * return with a test catalog of an empty {@link TestTree test tree} if the the {@link Platform platform} is not
	 * running.
	 *
	 * @return the test catalog as a JSON formatted string.
	 */
	public String get(Function<? super URI, ? extends ResourceSet> resourceSetAccess) {
		return get(resourceSetAccess, false);
	}

	/**
	 * Same as {@link #get(Function)}, except that this method can be configured to not emit property "endpoint" to the
	 * returned JSON string, by passing in <code>true</code> as argument for parameter
	 * <code>suppressEndpointProperty</code>.
	 */
	public String get(Function<? super URI, ? extends ResourceSet> resourceSetAccess,
			boolean suppressEndpointProperty) {

		return get(resourceSetAccess, n4jsCore.findAllProjects(), suppressEndpointProperty);
	}

	/**
	 * Same as {@link #get(Function, boolean)}, except that this method only returns those tests that are contained in
	 * the given projects.
	 */
	public String get(Function<? super URI, ? extends ResourceSet> resourceSetAccess,
			Iterable<? extends IN4JSProject> projects, boolean suppressEndpointProperty) {

		final TestTree testTree = testDiscoveryHelper.collectAllTestsFromProjects(resourceSetAccess, projects);
		return serializeTestTree(testTree, suppressEndpointProperty);
	}

	/** Serializes the given {@link TestTree}. */
	protected String serializeTestTree(TestTree testTree, boolean suppressEndpointProperty) {
		try {
			final Object testCatalogObject = suppressEndpointProperty
					? treeTransformer.apply(testTree, Collections.emptyMap())
					: treeTransformer.apply(testTree);

			return objectMapper.writeValueAsString(testCatalogObject);

		} catch (final Throwable e) {
			throw new RuntimeException("Error while assembling test catalog.", e);
		}
	}

}
