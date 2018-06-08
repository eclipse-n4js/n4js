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
package org.eclipse.n4js.tester.ui;

import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.tester.TestCatalogSupplier;
import org.eclipse.n4js.tester.TestDiscoveryHelper;
import org.eclipse.n4js.tester.domain.TestTree;

import com.google.inject.Inject;

/**
 * Service for supplying the the test catalog based on all tests available available in the ({@link IN4JSCore N4JS core}
 * based) workspace. The content of the provided test catalog depends on the built state of the workspace.
 */
public class EclipseTestCatalogSupplier extends TestCatalogSupplier {

	@Inject
	private TestDiscoveryHelper testDiscoveryHelper;

	@Override
	protected TestTree getTreeForAllTests() {
		return testDiscoveryHelper.collectAllTestsFromWorkspace();
	}

}
