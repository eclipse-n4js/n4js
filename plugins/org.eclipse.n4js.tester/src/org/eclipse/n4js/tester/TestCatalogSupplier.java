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

import static java.lang.String.valueOf;
import static java.util.UUID.randomUUID;

import org.eclipse.core.runtime.Platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Supplier;
import com.google.inject.Inject;
import com.google.inject.Injector;

import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.tester.domain.ID;
import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.utils.injector.InjectorBroker;

/**
 * Service for supplying the the test catalog based on all tests available available in the ({@link IN4JSCore N4JS core}
 * based) workspace. The content of the provided test catalog depends on the built state of the workspace.
 */
public class TestCatalogSupplier implements Supplier<String> {

	private static final String N4JS_INJECTOR_ID = "org.eclipse.n4js.N4JS";

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private TestTreeTransformer treeTransformer;

	@Inject
	private InjectorBroker injectorBroker;

	@Inject(optional = true)
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

	private TestTree getTreeForAllTests() {
		if (null == testDiscoveryHelper) {
			this.testDiscoveryHelper = getTestDiscoveryHelper();
		}
		if (null == testDiscoveryHelper) {
			return new TestTree(new ID(valueOf(randomUUID())));
		}
		return testDiscoveryHelper.collectAllTestsFromWorkspace();
	}

	private TestDiscoveryHelper getTestDiscoveryHelper() {
		final Injector injector = injectorBroker.getInjector(N4JS_INJECTOR_ID);
		return null == injector ? null : injector.getInstance(TestDiscoveryHelper.class);
	}

}
