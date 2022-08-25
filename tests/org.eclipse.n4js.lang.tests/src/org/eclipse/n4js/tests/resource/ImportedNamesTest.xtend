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
package org.eclipse.n4js.tests.resource

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ImportedNamesTest {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	URI myClassOne
	URI myClassTwo
	URI myInterfaceFour
	URI myRoleLikeInterface
	URI myVariableTwo

	URI resourceA
	URI resourceB
	URI resourceC

	URI resourceXURI
	URI resourceYURI

	@Test
	def void testImportedNamesVarDeclGeneric() {
		var rs = resourceSetProvider.get();
		resourceA = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/A.n4js"))
		resourceB = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/B.n4js"))
		resourceC = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/C.n4js"))

		rs.getResource(resourceA, true).contents
		rs.getResource(resourceB, true).contents
		rs.getResource(resourceC, true).contents

		EcoreUtil.resolveAll(rs)

		val expectedImportedNames = #["org.eclipse.n4js.tests.resource.C",
			"org.eclipse.n4js.tests.resource.A",
			"org.eclipse.n4js.tests.resource.C.!.C",
			"org.eclipse.n4js.tests.resource.C.!.Z",
			"#.!.org.eclipse.n4js.tests.resource.C"].toSet;
		val resourceA = rs.getResource(resourceA, true) as N4JSResource
		val actualImportedNames = resourceA.getImportedNames.map[toString].toSet
		assertEquals("The list of imported names is wrong", expectedImportedNames, actualImportedNames)
	}

	@Test
	def void testImportedNamesFunctionReturnType() {
		var rs = resourceSetProvider.get();
		myClassOne = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyClassOne.n4js"))
		myClassTwo = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyClassTwo.n4js"))
		myInterfaceFour = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyInterfaceFour.n4js"))
		myRoleLikeInterface = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyRoleLikeInterface.n4js"))
		myVariableTwo = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/MyVariableTwo.n4js"))

		rs.getResource(myClassOne, true).contents
		rs.getResource(myClassTwo, true).contents
		rs.getResource(myInterfaceFour, true).contents
		rs.getResource(myRoleLikeInterface, true).contents
		rs.getResource(myVariableTwo, true).contents

		EcoreUtil.resolveAll(rs)

		val expectedImportedNames = #[
			"#.!.!POLY.Object",
			"#.!.Object",
			"#.!.org.eclipse.n4js.tests.resource.MyVariableTwo",
			"#.!.two",
			"#.!.void",
			"org.eclipse.n4js.tests.resource.MyClassOne",
			"org.eclipse.n4js.tests.resource.MyClassTwo.!.MyClassTwo",
			"org.eclipse.n4js.tests.resource.MyInterfaceFour.!.MyInterfaceFour",
			"org.eclipse.n4js.tests.resource.MyRoleLikeInterface.!.MyRoleLikeInterface",
			"org.eclipse.n4js.tests.resource.MyVariableTwo",
			"void"
		].toSet
		val myClassOneResource = rs.getResource(myClassOne, true) as N4JSResource
		val actualImportedNames = myClassOneResource.getImportedNames.map[toString].toSet
		assertEquals("The list of imported names is wrong", expectedImportedNames, actualImportedNames)
	}

	@Test
	def void testImportedNamesComposedTypes() {
		var rs = resourceSetProvider.get();
		resourceXURI = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/X.n4js"))
		resourceYURI = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/Y.n4js"))

		rs.getResource(resourceXURI, true).contents
		rs.getResource(resourceYURI, true).contents
		EcoreUtil.resolveAll(rs)

		val resourceX = rs.getResource(resourceYURI, true) as N4JSResource

		val expectedImportedNames = #["#.!.org.eclipse.n4js.tests.resource.X",
									 "org.eclipse.n4js.tests.resource.X.!.J",
									 "org.eclipse.n4js.tests.resource.X.!.I",
									 "org.eclipse.n4js.tests.resource.X.!.X1",
									 "org.eclipse.n4js.tests.resource.X.!.X2",
									 "org.eclipse.n4js.tests.resource.Y",
									 "org.eclipse.n4js.tests.resource.X"].toSet;

		val actualImportedNames = resourceX.getImportedNames.map[toString].toSet
		assertEquals("The list of imported names is wrong", expectedImportedNames, actualImportedNames)
	}

	private def getImportedNames(N4JSResource resource) {
		val allDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resource);
		val result = allDescriptions.getResourceDescription(resource.URI)
		return result.importedNames;
	}
}
