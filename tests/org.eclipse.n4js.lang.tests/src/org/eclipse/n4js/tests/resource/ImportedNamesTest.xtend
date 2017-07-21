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

	URI resourceX
	URI resourceY

	private def getImportedNames(N4JSResource resource) {
		val allDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resource);
		val result = allDescriptions.getResourceDescription(resource.URI)
		return result.importedNames;
	}
	
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

		val resourceA = rs.getResource(resourceA, true) as N4JSResource
		val importedQFNs = resourceA.getImportedNames
		val importedNames = importedQFNs.filterNull.toSet.join(",")
		// TODO GH-73: Expected?
		println("imported names = " + importedNames)
	}
	
	@Test
	def void testImportedNamesComposedTypes() {
		var rs = resourceSetProvider.get();
		resourceX = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/X.n4js"))
		resourceY = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/resource/Y.n4js"))

		rs.getResource(resourceX, true).contents
		rs.getResource(resourceY, true).contents
		EcoreUtil.resolveAll(rs)

		val resourceX = rs.getResource(resourceY, true) as N4JSResource
		val importedQFNs = resourceX.getImportedNames
		val importedNames = importedQFNs.filterNull.toSet.join(",")
		// TODO GH-73: Expected?
		println("imported names = " + importedNames)
	}

	@Test
	def void testImportedNames() {
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
		
		val myClassOneResource = rs.getResource(myClassOne, true) as N4JSResource
		val importedQFNs = myClassOneResource.getImportedNames
		val importedNames = importedQFNs.filterNull.join(",")
		// TODO GH-73: Expected?
		println("imported names = " + importedNames)
	}
}
