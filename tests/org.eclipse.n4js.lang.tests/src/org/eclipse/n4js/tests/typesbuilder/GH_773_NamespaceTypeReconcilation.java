/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.typesbuilder;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TModule;
import org.junit.Test;

/**
 * A test that ensures that during the reconciliation of {@link TModule} instances, no duplicate
 * {@link ModuleNamespaceVirtualType} instances are created.
 */
public class GH_773_NamespaceTypeReconcilation extends AbstractN4JSTest {
	static URI NAMESPACE_FILE = URI.createURI(
			"src/org/eclipse/n4js/tests/typesbuilder/GH_733_NamespaceImport.n4js");
	static URI NAMESPACE_FILE_OTHER = URI.createURI(
			"src/org/eclipse/n4js/tests/typesbuilder/GH_733_NamespaceModule.n4js");

	@Test
	public void testNamespaceImportTypeReconcilation() throws Exception {
		List<N4JSResource> resources = loadFromDescriptions(NAMESPACE_FILE, NAMESPACE_FILE_OTHER);
		N4JSResource res = resources.get(0);
		res.getContents().get(0); // trigger demand-loading of AST (with reconciliation)
		TModule module = (TModule) res.getContents().get(1);

		validationTestHelper.assertNoIssues(res);

		assertEquals("No duplicates in internalTypes + exposedInternalTypes", 1,
				module.getInternalTypes().size() + module.getExposedInternalTypes().size());
		assertEquals("No types in internalTypes", 0, module.getInternalTypes().size());
		assertTrue("Only one instance of ModuleNamespaceVirtualType",
				module.getExposedInternalTypes().get(0) instanceof ModuleNamespaceVirtualType);

	}
}
