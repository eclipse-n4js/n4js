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

import org.eclipse.core.runtime.Platform;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.tester.domain.TestTree;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Supplier;
import com.google.inject.Inject;

/**
 * Service for supplying the the test catalog based on all tests available available in the ({@link IN4JSCore N4JS core}
 * based) workspace. The content of the provided test catalog depends on the built state of the workspace.
 */
public class TestCatalogSupplier implements Supplier<String> {

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
	@Override
	public String get() {
		try {
			final TestTree testTree = getTreeForAllTests();
			final Object testCatalogObject = treeTransformer.apply(testTree);
			return objectMapper.writeValueAsString(testCatalogObject);
		} catch (final Exception e) {
			throw new RuntimeException("Error while assembling test catalog.", e);
		}
	}

	/** @return the {@link TestTree} for all tests */
	// protected TestTree getTreeForAllTests() {
	// return new TestTree(new ID(valueOf(randomUUID())));
	// }

	protected TestTree getTreeForAllTests() {
		return testDiscoveryHelper.collectAllTestsFromWorkspace();
	}
}
