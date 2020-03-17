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

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.resource.UserDataMapper
import org.eclipse.n4js.tests.typesbuilder.utils.OrderedEmfFormatter
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.util.StringInputStream
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
abstract class AbstractN4JSClassDeclarationTypesBuilderTest {
	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;
	@Inject Provider<XtextResourceSet> resourceSetProvider

	def protected executeTest(String testFileName,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs,
			int expectedExportedElementsCount) {
		var rs = resourceSetProvider.get

		// load original resource
		val testResource = rs.createResource(URI.createURI("src/" + path + "/" + testFileName))
		testResource.load(emptyMap)

		executeUnloadLoadFromDescriptionsAndCompleteLoadTest(0, 1, testResource,
			expectedTypeToNamePairs, expectedExportedElementsCount)
	}

	def private void executeUnloadLoadFromDescriptionsAndCompleteLoadTest(int index, int max, Resource testResource,
			List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs,
			int expectedExportedElementsCount) {

		assertTrue(testResource.URI.trimFileExtension.lastSegment + " should have no errors but: " + testResource.errors.toString, testResource.errors.empty)
		val expectedTestContentCount = 1 + expectedTypeToNamePairs.size // 1 AST + 2 type roots
		assertEquals("test content count", 2, testResource.contents.size)

		val exportedScript = testResource.contents.last as TModule
		assertEquals(expectedExportedElementsCount, exportedScript.topLevelTypes.size + exportedScript.variables.size)

		assertExpectedTypes(testResource, expectedTypeToNamePairs)
		EcoreUtil2.resolveAll(testResource)
		assertTrue(testResource.errors.toString, testResource.errors.isEmpty)
		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(testResource);
		assertFalse("Test that the index has been filled", resourceDescriptions.allResourceDescriptions.empty);

		val eoDescs = resourceDescriptions.allResourceDescriptions.filter[URI.fileExtension == "n4js"].map[exportedObjects].flatten
		assertEquals("count of elements are marked as exported should equal to EResourceDescriptions", expectedTestContentCount, eoDescs.size)

		assertExampleJSStructure(1 + (3 * index) + ". step: complete load", testResource)

		assertExampleTypeStructure(1 + (3 * index) + ". step: complete load", testResource)

		val syntaxEoDesc = eoDescs.head
		val fromUserData = new XMIResourceImpl()
		fromUserData.load(new StringInputStream(syntaxEoDesc.getUserData(UserDataMapper::USER_DATA_KEY_SERIALIZED_SCRIPT), "UTF-8"), null)
		compareUserData(syntaxEoDesc, fromUserData)

		val uri = testResource.URI
		val resourceSet = testResource.resourceSet
		val updatedResourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
		val updatedResourceDescription = updatedResourceDescriptions.getResourceDescription(uri)

		testResource.unload
		resourceSet.resources.remove(testResource)

		var rs2 = resourceSetProvider.get
		val newN4jsResource = rs2.createResource(uri) as N4JSResource

		newN4jsResource.loadFromDescription(updatedResourceDescription);

		assertExampleTypeStructure(2 + (3 * index) + ". step: load from resource", newN4jsResource)

		assertTrue(2 + (3 * index) + ". step: load from resource - AST is proxy", (newN4jsResource.contents as BasicEList<? extends EObject>).basicGet(0).eIsProxy)

		// triggers resolve of AST
		assertFalse(2 + (3 * index) + ". step: load from resource - AST no proxy after first access", newN4jsResource.contents.get(0).eIsProxy)

		val astElement = (newN4jsResource.contents.get(1) as SyntaxRelatedTElement).astElement
		assertFalse(2 + (3 * index) + ". step: load from resource - AST element no proxy when fetched from type", astElement.eIsProxy)

		// rerun unload-load process until index > max
		if(index <= max) {
			executeUnloadLoadFromDescriptionsAndCompleteLoadTest(index+1, max, newN4jsResource,
				expectedTypeToNamePairs, expectedExportedElementsCount)
		}
	}

	private def compareUserData(IEObjectDescription syntaxEoDesc, XMIResourceImpl fromUserData) {
		assertEquals("Stored user data " + syntaxEoDesc, expectedTypesSerialization.toString, OrderedEmfFormatter.objToStr(fromUserData.contents.head))
	}

	def abstract CharSequence getExpectedTypesSerialization();

	def assertExpectedTypes(Resource testResource, List<? extends Pair<? extends Class<? extends EObject>, String>> expectedTypeToNamePairs) {
		val types = (testResource.contents.last as TModule).topLevelTypes
		val variables = (testResource.contents.last as TModule).variables
		val exported = types + variables
		assertEquals("expectedTypes count", expectedTypeToNamePairs.size, exported.size)
		val instanceCheck = [
			int index, Pair<? extends Class<? extends EObject>,String> pair |
				val IdentifiableElement element = exported.get(index)
				val elementType = element.class
				val assignable = pair.key.isAssignableFrom(elementType);
				if(!assignable) {
					println(pair.key + " is not instanceof " + element);
				} else {
					return switch(element) {
						Type: element.name == pair.value
						TVariable: element.name == pair.value
					}
				}
				assertTrue('''Expecting «pair.key» with name «pair.value» at position «index» but was «element»'''.toString, assignable)

		]
		for(i : 0..expectedTypeToNamePairs.size - 1) {
			val expectedTypeToNamePair = expectedTypeToNamePairs.get(i)
			instanceCheck.apply(i, expectedTypeToNamePair)
		}
	}

	def abstract void assertExampleTypeStructure(String phase, Resource resource);

	def abstract void assertExampleJSStructure(String phase, Resource resource);


	def private getPath() {
		AbstractN4JSClassDeclarationTypesBuilderTest.package.name.replaceAll("\\.", "/")
	}
}
