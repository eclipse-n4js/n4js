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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.N4JSInjectorProviderWithIndex
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.resource.UserdataMapper
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.Log
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.containers.FlatResourceSetBasedAllContainersState
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.util.StringInputStream
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@InjectWith(N4JSInjectorProviderWithIndex)
@RunWith(XtextRunner)
@Log
class N4JSScopingTestWithIndexTest {

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject Provider<XtextResourceSet> resourceSetProvider

	def assertResourceNotLoaded(ResourceSet rs, URI uri) {
		val resource = rs.getResource(uri, false)
		if (resource !== null && resource.loaded) {
			fail("Resource (and AST) for " + uri + " is not expected to be loaded.");
		}
	}

	@Test
	def void testImportExportMemberDeserialize() {
		doTestImportExportMemberDeserialize("src/org/eclipse/n4js/tests/scoping/Supplier.n4js", "Supplier",
			"src/org/eclipse/n4js/tests/scoping/Client.n4js",
			'''
				<?xml version="1.0" encoding="ASCII"?>
				<types:TModule xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:typeRefs="http://www.eclipse.org/n4js/ts/TypeRefs" xmlns:types="http://www.eclipse.org/n4js/ts/Types" qualifiedName="org/eclipse/n4js/tests/scoping/Supplier" projectName="org.eclipse.n4js.lang.tests" vendorID="org.eclipse.n4js">
				  <astElement href="#/0"/>
				  <topLevelTypes xsi:type="types:TClass" name="Supplier" exportedName="Supplier">
				    <ownedMembers xsi:type="types:TMethod" name="foo" hasNoBody="true" declaredMemberAccessModifier="public">
				      <astElement href="#/0/@scriptElements.0/@exportedElement/@ownedMembersRaw.0"/>
				      <returnTypeRef xsi:type="typeRefs:ParameterizedTypeRef" declaredType="//@topLevelTypes.0"/>
				    </ownedMembers>
				    <astElement href="#/0/@scriptElements.0/@exportedElement"/>
				  </topLevelTypes>
				</types:TModule>
			''', "7db65ac965ae43f2b3673735d7296d9b");
	}

	/*
	 * Similar to testImportExportMemberDeserialize, but supplier contains a reference to a built-in type which
	 * must not be stored in the index.
	 */
	@Test
	def void testImportExportMemberDeserializeWithBuiltIn() {
		doTestImportExportMemberDeserialize("src/org/eclipse/n4js/tests/scoping/SupplierWithBuiltIn.n4js",
			"SupplierWithBuiltIn", "src/org/eclipse/n4js/tests/scoping/ClientWithBuiltIn.n4js",
			'''
				<?xml version="1.0" encoding="ASCII"?>
				<types:TModule xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:typeRefs="http://www.eclipse.org/n4js/ts/TypeRefs" xmlns:types="http://www.eclipse.org/n4js/ts/Types" qualifiedName="org/eclipse/n4js/tests/scoping/SupplierWithBuiltIn" projectName="org.eclipse.n4js.lang.tests" vendorID="org.eclipse.n4js">
				  <astElement href="#/0"/>
				  <topLevelTypes xsi:type="types:TClass" name="SupplierWithBuiltIn" exportedName="SupplierWithBuiltIn">
				    <ownedMembers xsi:type="types:TField" name="s" declaredMemberAccessModifier="public">
				      <astElement href="#/0/@scriptElements.0/@exportedElement/@ownedMembersRaw.0"/>
				      <typeRef xsi:type="typeRefs:ParameterizedTypeRef">
				        <declaredType href="n4scheme:/builtin_js.n4ts#//@types.3"/>
				      </typeRef>
				    </ownedMembers>
				    <ownedMembers xsi:type="types:TMethod" name="foo" hasNoBody="true" declaredMemberAccessModifier="public">
				      <astElement href="#/0/@scriptElements.0/@exportedElement/@ownedMembersRaw.1"/>
				      <returnTypeRef xsi:type="typeRefs:ParameterizedTypeRef" declaredType="//@topLevelTypes.0"/>
				    </ownedMembers>
				    <astElement href="#/0/@scriptElements.0/@exportedElement"/>
				  </topLevelTypes>
				</types:TModule>
			''', "7a7bda036db41c8e954e99535f496cff");
	}

	def void doTestImportExportMemberDeserialize(String supplierFileName, String supplierClassName,
		String clientFileName, String supplierUserData, String astMD5) {
		var rs = resourceSetProvider.get

		val supplierJS = rs.URIConverter.normalize(URI.createURI(supplierFileName))
		val supplierResource = rs.createResource(supplierJS)
		supplierResource.load(emptyMap)
		EcoreUtil.resolveAll(supplierResource)
		assertTrue(supplierResource.errors.toString(), supplierResource.errors.empty)
		assertEquals("supplier content count", 2, supplierResource.contents.size)
		assertTrue("supplier content count at position one is Script",
			supplierResource.contents.get(0) instanceof Script)
		assertTrue("supplier content count at position two is TClass",
			supplierResource.contents.get(1) instanceof TModule)

		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(supplierResource);
		assertFalse("Test that the index has been filled", resourceDescriptions.allResourceDescriptions.empty);

		val eoDescs = resourceDescriptions.getResourceDescription(supplierJS).exportedObjects
		assertEquals("Wrong number, found: " + eoDescs.map[it.name], 2, eoDescs.size)
		
		
		val moduleDescription = eoDescs.iterator.toList.findFirst[name.lastSegment == supplierClassName];
		assertEquals("Stored user data", supplierUserData, UserdataMapper.getDeserializedModuleFromDescriptionAsString(
				moduleDescription, supplierJS))
		assertEquals("Stored astMD5 hash",
				astMD5, moduleDescription.getUserData(UserdataMapper.USERDATA_KEY_AST_MD5))
//			eoDescs.iterator.toList.findFirst[name.lastSegment == supplierClassName].getUserData(
//				UserdataMapper::USERDATA_KEY_SERIALIZED_SCRIPT))

		rs.resources.forEach[it.unload];

		val clientJS = rs.URIConverter.normalize(URI.createURI(clientFileName))
		val clientResource = rs.createResource(clientJS)
		clientResource.load(emptyMap)
		assertTrue(clientResource.errors.empty)
		assertEquals("client content count", 2, clientResource.contents.size)
		val clientObject = clientResource.contents.get(0)
		assertTrue(clientObject instanceof Script)
		val client = clientObject as Script

		// syntax ok?
		assertTrue(client.eResource.errors.empty)
		assertTrue(clientResource.errors.empty)

		val parameterizedCallExpression = ((client.scriptElements.last as ExpressionStatement).expression as ParameterizedCallExpression);
		assertNotNull(parameterizedCallExpression);
		assertEquals(0, parameterizedCallExpression.arguments.size)
		assertNotNull(parameterizedCallExpression.target)
		assertTrue(parameterizedCallExpression.target instanceof ParameterizedPropertyAccessExpression)

		val propertyAccess = parameterizedCallExpression.target as ParameterizedPropertyAccessExpression
		assertFalse("Should not been loaded (1)", supplierResource.loaded)
		assertFalse("Proxy cannot be resolved, type deserialization not working", propertyAccess.property.eIsProxy)
		assertFalse("Resource should not have been loaded due to index access", supplierResource.loaded)
		assertEquals("foo", propertyAccess.property.name);
	}

	@Test
	def void testDescriptionFromIndexCreatesResource() {
		var rs = resourceSetProvider.get

		val URI supplierJS = rs.URIConverter.normalize(
			URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"))
		val URI clientJS = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Client.n4js"))

		var Resource supplierResource = rs.getResource(supplierJS, true)
		supplierResource.load(emptyMap)
		EcoreUtil.resolveAll(supplierResource)
		rs.eAdapters += new MyFlatResourceSetBasedAllContainersState(rs, supplierJS)
		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(supplierResource);
		resourceDescriptions.getResourceDescription(supplierJS).exportedObjects.forEach[ userDataKeys ]
		assertFalse("Test that the index has been filled", resourceDescriptions.allResourceDescriptions.empty);

		supplierResource.unload
		rs.resources.clear
		assertResourceNotLoaded(rs, supplierJS)

		val clientResource = rs.createResource(clientJS);

		clientResource.load(emptyMap)
		assertTrue(clientResource.errors.empty)
		assertEquals("client content count", 2, clientResource.contents.size)
		val clientObject = clientResource.contents.get(0)
		assertTrue(clientObject instanceof Script)
		val client = clientObject as Script

		assertResourceNotLoaded(rs, supplierJS)

		// syntax ok?
		assertTrue(client.eResource.errors.empty)
		assertTrue(clientResource.errors.empty)

		val parameterizedCallExpression = ((client.scriptElements.last as ExpressionStatement).expression as ParameterizedCallExpression);
		assertNotNull(parameterizedCallExpression);
		assertEquals(0, parameterizedCallExpression.arguments.size)
		assertNotNull(parameterizedCallExpression.target)
		assertTrue(parameterizedCallExpression.target instanceof ParameterizedPropertyAccessExpression)

		val propertyAccess = parameterizedCallExpression.target as ParameterizedPropertyAccessExpression

		// TODO: Should have failed here
		assertResourceNotLoaded(rs, supplierJS)
		propertyAccess.property;
		assertResourceNotLoaded(rs, supplierJS)
		assertFalse("Proxy cannot be resolved, type deserialization not working", propertyAccess.property.eIsProxy)
		assertResourceNotLoaded(rs, supplierJS)
		assertEquals("foo", propertyAccess.property.name);

		assertResourceNotLoaded(rs, supplierJS)
	}

	@Test
	def void testTypeUsedTwice() {
		var rs = resourceSetProvider.get

		val supplierJS = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"))
		var supplierResource = rs.getResource(supplierJS, true)
		supplierResource.load(emptyMap)
		EcoreUtil.resolveAll(supplierResource)
		rs.eAdapters += new MyFlatResourceSetBasedAllContainersState(rs, supplierJS)
		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(supplierResource);
		assertFalse("Test that the index has been filled", resourceDescriptions.allResourceDescriptions.empty);
		resourceDescriptions.getResourceDescription(supplierJS).exportedObjects.forEach[ userDataKeys ]
		supplierResource.unload
		rs.resources.clear

		val clientJS = URI.createURI("src/org/eclipse/n4js/tests/scoping/Client.n4js")
		val clientResource = rs.createResource(rs.URIConverter.normalize(clientJS))
		clientResource.load(
			new StringInputStream(
				'''
					import { Supplier } from "org/eclipse/n4js/tests/scoping/Supplier"
					var a: Supplier;
					var b: Supplier;
					a.foo();
					b.foo();
				'''), emptyMap)

		EcoreUtil.resolveAll(clientResource)
		assertTrue(clientResource.errors.toString, clientResource.errors.isEmpty)
		supplierResource = rs.getResource(supplierJS, false)
		assertNotNull(supplierResource)
		assertFalse("Resource was loaded transparently due to index access in scope provider", supplierResource.loaded)
	}

	@Test
	def void testASTLoadingSuccessfullyProxifies() {
		var rs = resourceSetProvider.get

		val supplierJS = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"))
		var supplierResource = rs.getResource(supplierJS, true) as N4JSResource
		supplierResource.load(emptyMap)
		supplierResource.performPostProcessing
		assertFalse("resource should not be marked as reconciled", supplierResource.isReconciled);

		rs.eAdapters += new MyFlatResourceSetBasedAllContainersState(rs, supplierJS)
		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(supplierResource);
		assertFalse("Test that the index has been filled", resourceDescriptions.allResourceDescriptions.empty);
		resourceDescriptions.getResourceDescription(supplierJS).exportedObjects.forEach[userDataKeys]

		rs.resources.forEach[unload]
		rs.resources.clear

		val clientJS = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Client.n4js"))
		val clientResource = rs.createResource(clientJS)
		clientResource.load(emptyMap)

		EcoreUtil.resolveAll(clientResource) // will trigger loading of Supplier.n4js from index

		val client = clientResource.contents.get(0) as Script
		val callExpr = ((client.scriptElements.last as ExpressionStatement).expression as ParameterizedCallExpression);
		assertNotNull(callExpr);
		assertEquals(0, callExpr.arguments.size)
		assertNotNull(callExpr.target)
		assertTrue(callExpr.target instanceof ParameterizedPropertyAccessExpression)
		val accessExpr = callExpr.target as ParameterizedPropertyAccessExpression
		val wontBecomeAProxy = accessExpr.eGet(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY, false) as EObject

		assertFalse("property should not be a proxy", wontBecomeAProxy.eIsProxy)
		assertEquals("property should point to resource Supplier.n4js", supplierJS, wontBecomeAProxy.eResource.URI)

		supplierResource = rs.getResource(supplierJS, false) as N4JSResource
		assertNotNull(supplierResource)
		assertFalse(supplierResource.loaded)
		assertFalse("resource should not be marked as reconciled", supplierResource.isReconciled);
		assertEquals(2, supplierResource.contents.size)
		assertTrue(supplierResource.script.eIsProxy)

		supplierResource.contents.head // trigger AST loading after loading from index (with reconciliation)

		assertTrue("resource should be marked as reconciled", supplierResource.isReconciled);
		assertTrue(supplierResource.isFullyInitialized);
		assertFalse(supplierResource.isFullyProcessed);

		assertFalse("wontBecomeAProxy should not have been proxified", wontBecomeAProxy.eIsProxy)
		assertSame("accessExpr.property should still point to the very same instance", wontBecomeAProxy, accessExpr.property)
		assertTrue(supplierResource.loaded)

		assertEquals("foo", accessExpr.property.name);

		val supplierTModule = supplierResource.contents.last as TModule
		val supplierType = supplierTModule.topLevelTypes.head as TClass
		assertSame(supplierType.ownedMembers.head, accessExpr.property)
	}
}

/**
 * We need a faked container state in the test because we want to simulate a resource set that does
 * not contain all resources that are available in the index. In the runtime scenario, this is usually
 * not possible because all resources will be loaded up-front, but the test case deletes them from
 * the resource set as soon as the index was populated.
 *
 * This custom IAllContainersState keeps track of a single resource and convinces the framework that it exists.
 */
class MyFlatResourceSetBasedAllContainersState extends FlatResourceSetBasedAllContainersState {

	URI additionalURI

	new(ResourceSet resourceSet, URI additionalURI) {
		super(resourceSet)
		this.additionalURI = additionalURI
	}

	override isEmpty(String containerHandle) {
		return false
	}

	override getContainedURIs(String containerHandle) {
		val result = newArrayList
		result.addAll(super.getContainedURIs(containerHandle))
		result += additionalURI
		return result
	}

}
