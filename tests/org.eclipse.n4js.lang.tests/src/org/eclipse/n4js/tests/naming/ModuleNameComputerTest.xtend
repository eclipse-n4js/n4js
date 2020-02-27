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
package org.eclipse.n4js.tests.naming

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.naming.ModuleNameComputer
import org.eclipse.n4js.utils.URIUtils
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class ModuleNameComputerTest {
	@Inject extension ModuleNameComputer
	@Inject IQualifiedNameConverter qualifiedNameConverter
	@Inject Provider<XtextResourceSet> resourceSetProvider

	@Test
	def testGetModulePath() {
		val uri = URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")
		val file = URIUtils.toFile(uri)
		assertTrue("File exists", file.exists)
		var rs = resourceSetProvider.get
		val supplierResource = rs.createResource(uri)
		assertNotNull("resource should be not null", supplierResource)
		val qualifiedModuleName = supplierResource.qualifiedModuleName
		assertNotNull("path should be not null", qualifiedModuleName)
		assertEquals("expected module path", "org/eclipse/n4js/tests/scoping/Supplier", qualifiedNameConverter.toString(qualifiedModuleName))
	}
}
