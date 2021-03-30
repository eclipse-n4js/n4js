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
package org.eclipse.n4js.tests.typesbuilder

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TModule
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class NamespaceImportTypesBuilderTest extends AbstractTypesBuilderTest {

	override protected enableUserDataCompare() {
		// to check the complete AST just change false to true
		false
		//true
	}

	@Test
	def void test() {
		val textFileName = "NamespaceImports.n4js"
		val expectedTypesNamePairs = #[]

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"

		val expectedExportedTypeToNamePairsOnIndex = #[]
		val expectedTypesCount = expectedTypesNamePairs.size
		val expectedExportedElementsCount = expectedExportedTypeToNamePairsOnIndex.size
		executeTestWithExternals(textFileName, #[ "NamespaceImportsMod.n4jsd", "NamespaceImportsPlain.js"], expectedExportedTypeToNamePairsOnIndex, expectedTypesCount,
			expectedExportedElementsCount)
	}

	def protected executeTestWithExternals(String testFileName, String[] externalFiles,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedExportedTypeToNamePairs,
			int expectedTypesCount, int expectedTypesOnIndexCount) {
		var rs = resourceSetProvider.get();
		for (String externalFile: externalFiles) {
			val testResource = rs.createResource(URI.createURI("src/" + path + "/" + externalFile))
			testResource.load(emptyMap)
			resolve(testResource)
			testResource.allContents;
		}

		// load original resource
		val testResource = rs.createResource(URI.createURI("src/" + path + "/" + testFileName))
		testResource.load(emptyMap)

		resolve(testResource);
		assertResourceHasNoErrors("x", testResource)
		checkTModule(testResource.contents.get(1) as TModule)
	}

	def private checkTModule(TModule module) {
		checkStaticNSVType(module.internalTypes.get(0) as ModuleNamespaceVirtualType);
		checkDynamicNSVType(module.internalTypes.get(1) as ModuleNamespaceVirtualType);
	}

	def checkDynamicNSVType(ModuleNamespaceVirtualType type) {
		assertTrue(type.name + " is expected to be dynamic", type.declaredDynamic)
	}

	def checkStaticNSVType(ModuleNamespaceVirtualType type) {
		assertFalse(type.name + " is expected to be non-dynamic", type.declaredDynamic)
	}

	override getExpectedTypesSerialization() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override assertExampleTypeStructure(String phase, Resource resource) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override assertExampleJSStructure(String phase, Resource resource) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
}
