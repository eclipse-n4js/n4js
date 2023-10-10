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
package org.eclipse.n4js.tests.typesbuilder

import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.ts.types.TModule
import org.junit.Test
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType

/**
 * A test that ensures that during the reconciliation of {@link TModule}
 * instances, no duplicate {@link ModuleNamespaceVirtualType} instances are created.
 */
class GH_773_NamespaceTypeReconcilation extends AbstractN4JSTest {
	static val NAMESPACE_FILE = URI.createURI(
		"src/org/eclipse/n4js/tests/typesbuilder/GH_733_NamespaceImport.n4js");
	static val NAMESPACE_FILE_OTHER = URI.createURI(
		"src/org/eclipse/n4js/tests/typesbuilder/GH_733_NamespaceModule.n4js");

	@Test
	def void testNamespaceImportTypeReconcilation() throws Exception {
		val resources = loadFromDescriptions(NAMESPACE_FILE, NAMESPACE_FILE_OTHER);
		val res = resources.get(0);
		res.contents.get(0); // trigger demand-loading of AST (with reconciliation)
		val module = res.contents.get(1) as TModule;

		validationTestHelper.assertNoIssues(res);

		assertEquals("No duplicates in internalTypes + exposedInternalTypes", 1,
			(module.internalTypes + module.exposedInternalTypes).size);
		assertEquals("No types in internalTypes", 0, module.internalTypes.size);
		assertTrue("Only one instance of ModuleNamespaceVirtualType",
			module.exposedInternalTypes.get(0) instanceof ModuleNamespaceVirtualType);

	}
}
