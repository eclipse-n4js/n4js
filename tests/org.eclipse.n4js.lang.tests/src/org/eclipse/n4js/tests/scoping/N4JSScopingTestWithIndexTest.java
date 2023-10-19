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
package org.eclipse.n4js.tests.scoping;

import static java.util.Collections.emptyMap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.findFirst;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSInjectorProviderWithIndex;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.Log;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.containers.FlatResourceSetBasedAllContainersState;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.StringInputStream;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@InjectWith(N4JSInjectorProviderWithIndex.class)
@RunWith(XtextRunner.class)
@Log
public class N4JSScopingTestWithIndexTest {

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	public void assertResourceNotLoaded(ResourceSet rs, URI uri) {
		Resource resource = rs.getResource(uri, false);
		if (resource != null && resource.isLoaded()) {
			fail("Resource (and AST) for " + uri + " is not expected to be loaded.");
		}
	}

	@Test
	public void testImportExportMemberDeserialize() throws IOException {
		doTestImportExportMemberDeserialize("src/org/eclipse/n4js/tests/scoping/Supplier.n4js", "Supplier",
				"src/org/eclipse/n4js/tests/scoping/Client.n4js",
				"""
						<?xml version="1.0" encoding="ASCII"?>
						<types:TModule xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:typeRefs="http://www.eclipse.org/n4js/ts/TypeRefs" xmlns:types="http://www.eclipse.org/n4js/ts/Types" simpleName="Supplier" qualifiedName="org/eclipse/n4js/tests/scoping/Supplier" packageName="org.eclipse.n4js.lang.tests" projectID="org.eclipse.n4js.lang.tests" vendorID="org.eclipse.n4js">
						  <exportDefinitions xsi:type="types:ElementExportDefinition" exportedName="Supplier" exportedElement="//@types.0"/>
						  <types xsi:type="types:TClass" name="Supplier" directlyExported="true" exportingExportDefinitions="//@exportDefinitions.0">
						    <ownedMembers xsi:type="types:TMethod" name="foo" hasNoBody="true" declaredMemberAccessModifier="public">
						      <astElement href="#/0/@scriptElements.0/@exportedElement/@ownedMembersRaw.0"/>
						      <returnTypeRef xsi:type="typeRefs:ParameterizedTypeRef" declaredType="//@types.0"/>
						    </ownedMembers>
						    <astElement href="#/0/@scriptElements.0/@exportedElement"/>
						  </types>
						  <astElement href="#/0"/>
						</types:TModule>
						""",
				"5ef0928a4a8827880a4bdb03ff26f5fc");
	}

	/*
	 * Similar to testImportExportMemberDeserialize, but supplier contains a reference to a built-in type which must not
	 * be stored in the index.
	 */
	@Test
	public void testImportExportMemberDeserializeWithBuiltIn() throws IOException {
		doTestImportExportMemberDeserialize("src/org/eclipse/n4js/tests/scoping/SupplierWithBuiltIn.n4js",
				"SupplierWithBuiltIn", "src/org/eclipse/n4js/tests/scoping/ClientWithBuiltIn.n4js",
				"""
						<?xml version="1.0" encoding="ASCII"?>
						<types:TModule xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:typeRefs="http://www.eclipse.org/n4js/ts/TypeRefs" xmlns:types="http://www.eclipse.org/n4js/ts/Types" simpleName="SupplierWithBuiltIn" qualifiedName="org/eclipse/n4js/tests/scoping/SupplierWithBuiltIn" packageName="org.eclipse.n4js.lang.tests" projectID="org.eclipse.n4js.lang.tests" vendorID="org.eclipse.n4js">
						  <exportDefinitions xsi:type="types:ElementExportDefinition" exportedName="SupplierWithBuiltIn" exportedElement="//@types.0"/>
						  <types xsi:type="types:TClass" name="SupplierWithBuiltIn" directlyExported="true" exportingExportDefinitions="//@exportDefinitions.0">
						    <ownedMembers xsi:type="types:TField" name="s" declaredMemberAccessModifier="public">
						      <astElement href="#/0/@scriptElements.0/@exportedElement/@ownedMembersRaw.0"/>
						      <typeRef xsi:type="typeRefs:ParameterizedTypeRef">
						        <declaredType href="n4scheme:/builtin_js.n4jsd#/1/@types.8"/>
						      </typeRef>
						    </ownedMembers>
						    <ownedMembers xsi:type="types:TMethod" name="foo" hasNoBody="true" declaredMemberAccessModifier="public">
						      <astElement href="#/0/@scriptElements.0/@exportedElement/@ownedMembersRaw.1"/>
						      <returnTypeRef xsi:type="typeRefs:ParameterizedTypeRef" declaredType="//@types.0"/>
						    </ownedMembers>
						    <astElement href="#/0/@scriptElements.0/@exportedElement"/>
						  </types>
						  <astElement href="#/0"/>
						</types:TModule>
						""",
				"e6baa3799092e2c27e34ed45f7d5e461");
	}

	public void doTestImportExportMemberDeserialize(String supplierFileName, String supplierClassName,
			String clientFileName, String supplierUserData, String astMD5) throws IOException {
		XtextResourceSet rs = resourceSetProvider.get();

		URI supplierJS = rs.getURIConverter().normalize(URI.createURI(supplierFileName));
		Resource supplierResource = rs.createResource(supplierJS);
		supplierResource.load(Collections.emptyMap());
		EcoreUtil.resolveAll(supplierResource);
		assertTrue(supplierResource.getErrors().toString(), supplierResource.getErrors().isEmpty());
		assertEquals("supplier content count", 2, supplierResource.getContents().size());
		assertTrue("supplier content count at position one is Script",
				supplierResource.getContents().get(0) instanceof Script);
		assertTrue("supplier content count at position two is TClass",
				supplierResource.getContents().get(1) instanceof TModule);

		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider
				.getResourceDescriptions(supplierResource);
		assertFalse("Test that the index has been filled", isEmpty(resourceDescriptions.getAllResourceDescriptions()));

		Iterable<IEObjectDescription> eoDescs = resourceDescriptions.getResourceDescription(supplierJS)
				.getExportedObjects();
		assertEquals("Wrong number, found: " + map(eoDescs, od -> od.getName()), 2, size(eoDescs));

		IEObjectDescription moduleDescription = findFirst(eoDescs.iterator(),
				od -> Objects.equals(od.getName().getLastSegment(), supplierClassName));
		assertEquals("Stored user data", supplierUserData, UserDataMapper.getDeserializedModuleFromDescriptionAsString(
				moduleDescription, supplierJS));
		assertEquals("Stored astMD5 hash",
				astMD5, moduleDescription.getUserData(UserDataMapper.USER_DATA_KEY_AST_MD5));
		// eoDescs.iterator().toList.findFirst[name.lastSegment == supplierClassName].getUserData(
		// UserDataMapper::USER_DATA_KEY_SERIALIZED_SCRIPT))

		for (Resource res : rs.getResources()) {
			res.unload();
		}

		URI clientJS = rs.getURIConverter().normalize(URI.createURI(clientFileName));
		Resource clientResource = rs.createResource(clientJS);
		clientResource.load(emptyMap());
		assertTrue(clientResource.getErrors().isEmpty());
		assertEquals("client content count", 2, clientResource.getContents().size());
		EObject clientObject = clientResource.getContents().get(0);
		assertTrue(clientObject instanceof Script);
		Script client = (Script) clientObject;

		// syntax ok?
		assertTrue(client.eResource().getErrors().isEmpty());
		assertTrue(clientResource.getErrors().isEmpty());

		ParameterizedCallExpression parameterizedCallExpression = (ParameterizedCallExpression) (((ExpressionStatement) last(
				client.getScriptElements())).getExpression());
		assertNotNull(parameterizedCallExpression);
		assertEquals(0, parameterizedCallExpression.getArguments().size());
		assertNotNull(parameterizedCallExpression.getTarget());
		assertTrue(parameterizedCallExpression.getTarget() instanceof ParameterizedPropertyAccessExpression);

		ParameterizedPropertyAccessExpression propertyAccess = (ParameterizedPropertyAccessExpression) parameterizedCallExpression
				.getTarget();
		assertFalse("Should not been loaded (1)", supplierResource.isLoaded());
		assertFalse("Proxy cannot be resolved, type deserialization not working",
				propertyAccess.getProperty().eIsProxy());
		assertFalse("Resource should not have been loaded due to index access", supplierResource.isLoaded());
		assertEquals("foo", propertyAccess.getProperty().getName());
	}

	@Test
	public void testDescriptionFromIndexCreatesResource() throws IOException {
		XtextResourceSet rs = resourceSetProvider.get();

		URI supplierJS = rs.getURIConverter().normalize(
				URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		URI clientJS = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Client.n4js"));

		Resource supplierResource = rs.getResource(supplierJS, true);
		supplierResource.load(emptyMap());
		EcoreUtil.resolveAll(supplierResource);
		rs.eAdapters().add(new MyFlatResourceSetBasedAllContainersState(rs, supplierJS));
		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider
				.getResourceDescriptions(supplierResource);

		for (IEObjectDescription od : resourceDescriptions.getResourceDescription(supplierJS).getExportedObjects()) {
			od.getUserDataKeys();
		}

		assertFalse("Test that the index has been filled", isEmpty(resourceDescriptions.getAllResourceDescriptions()));

		supplierResource.unload();
		rs.getResources().clear();
		assertResourceNotLoaded(rs, supplierJS);

		Resource clientResource = rs.createResource(clientJS);

		clientResource.load(emptyMap());
		assertTrue(clientResource.getErrors().isEmpty());
		assertEquals("client content count", 2, clientResource.getContents().size());
		EObject clientObject = clientResource.getContents().get(0);
		assertTrue(clientObject instanceof Script);
		Script client = (Script) clientObject;

		assertResourceNotLoaded(rs, supplierJS);

		// syntax ok?
		assertTrue(client.eResource().getErrors().isEmpty());
		assertTrue(clientResource.getErrors().isEmpty());

		ParameterizedCallExpression parameterizedCallExpression = (ParameterizedCallExpression) (((ExpressionStatement) last(
				client.getScriptElements())).getExpression());
		assertNotNull(parameterizedCallExpression);
		assertEquals(0, parameterizedCallExpression.getArguments().size());
		assertNotNull(parameterizedCallExpression.getTarget());
		assertTrue(parameterizedCallExpression.getTarget() instanceof ParameterizedPropertyAccessExpression);

		ParameterizedPropertyAccessExpression propertyAccess = (ParameterizedPropertyAccessExpression) parameterizedCallExpression
				.getTarget();

		// TODO: Should have failed here;
		assertResourceNotLoaded(rs, supplierJS);
		propertyAccess.getProperty();
		assertResourceNotLoaded(rs, supplierJS);
		assertFalse("Proxy cannot be resolved, type deserialization not working",
				propertyAccess.getProperty().eIsProxy());
		assertResourceNotLoaded(rs, supplierJS);
		assertEquals("foo", propertyAccess.getProperty().getName());

		assertResourceNotLoaded(rs, supplierJS);
	}

	@Test
	public void testTypeUsedTwice() throws IOException {
		XtextResourceSet rs = resourceSetProvider.get();

		URI supplierJS = rs.getURIConverter()
				.normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		var supplierResource = rs.getResource(supplierJS, true);
		supplierResource.load(emptyMap());
		EcoreUtil.resolveAll(supplierResource);
		rs.eAdapters().add(new MyFlatResourceSetBasedAllContainersState(rs, supplierJS));
		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider
				.getResourceDescriptions(supplierResource);
		assertFalse("Test that the index has been filled", isEmpty(resourceDescriptions.getAllResourceDescriptions()));

		for (IEObjectDescription od : resourceDescriptions.getResourceDescription(supplierJS).getExportedObjects()) {
			od.getUserDataKeys();
		}

		supplierResource.unload();
		rs.getResources().clear();

		URI clientJS = URI.createURI("src/org/eclipse/n4js/tests/scoping/Client.n4js");
		Resource clientResource = rs.createResource(rs.getURIConverter().normalize(clientJS));
		clientResource.load(
				new StringInputStream(
						"""
								import { Supplier } from "org/eclipse/n4js/tests/scoping/Supplier";
								var a: Supplier;
								var b: Supplier;
								a.foo();
								b.foo();
								"""),
				emptyMap());

		EcoreUtil.resolveAll(clientResource);
		assertTrue(clientResource.getErrors().toString(), clientResource.getErrors().isEmpty());
		supplierResource = rs.getResource(supplierJS, false);
		assertNotNull(supplierResource);
		assertFalse("Resource was loaded transparently due to index access in scope provider",
				supplierResource.isLoaded());
	}

	@Test
	public void testASTLoadingSuccessfullyProxifies() throws IOException {
		XtextResourceSet rs = resourceSetProvider.get();

		URI supplierJS = rs.getURIConverter()
				.normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		N4JSResource supplierResource = (N4JSResource) rs.getResource(supplierJS, true);
		supplierResource.load(emptyMap());
		supplierResource.performPostProcessing();
		assertFalse("resource should not be marked as. reconciled", supplierResource.isReconciled());

		rs.eAdapters().add(new MyFlatResourceSetBasedAllContainersState(rs, supplierJS));
		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider
				.getResourceDescriptions(supplierResource);
		assertFalse("Test that the index has been filled", isEmpty(resourceDescriptions.getAllResourceDescriptions()));

		for (IEObjectDescription od : resourceDescriptions.getResourceDescription(supplierJS).getExportedObjects()) {
			od.getUserDataKeys();
		}

		for (Resource res : rs.getResources()) {
			res.unload();
		}
		rs.getResources().clear();

		URI clientJS = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Client.n4js"));
		Resource clientResource = rs.createResource(clientJS);
		clientResource.load(emptyMap());

		EcoreUtil.resolveAll(clientResource); // will trigger loading of Supplier.n4js from index

		Script client = (Script) clientResource.getContents().get(0);
		ParameterizedCallExpression callExpr = (ParameterizedCallExpression) (((ExpressionStatement) last(
				client.getScriptElements())).getExpression());

		assertNotNull(callExpr);
		assertEquals(0, callExpr.getArguments().size());
		assertNotNull(callExpr.getTarget());
		assertTrue(callExpr.getTarget() instanceof ParameterizedPropertyAccessExpression);
		ParameterizedPropertyAccessExpression accessExpr = (ParameterizedPropertyAccessExpression) callExpr.getTarget();
		EObject wontBecomeAProxy = (EObject) accessExpr
				.eGet(N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY, false);

		assertFalse("property should not be a proxy", wontBecomeAProxy.eIsProxy());
		assertEquals("property should point to resource Supplier.n4js", supplierJS,
				wontBecomeAProxy.eResource().getURI());

		supplierResource = (N4JSResource) rs.getResource(supplierJS, false);
		assertNotNull(supplierResource);
		assertFalse(supplierResource.isLoaded());
		assertFalse("resource should not be marked as. reconciled", supplierResource.isReconciled());
		assertEquals(2, supplierResource.getContents().size());
		assertTrue(supplierResource.getScript().eIsProxy());

		head(supplierResource.getContents()); // trigger AST loading after loading from index (with reconciliation)

		assertTrue("resource should be marked as. reconciled", supplierResource.isReconciled());
		assertTrue(supplierResource.isFullyInitialized());
		assertFalse(supplierResource.isFullyProcessed());

		assertFalse("wontBecomeAProxy should not have been proxified", wontBecomeAProxy.eIsProxy());
		assertSame("accessExpr.getProperty() should still point to the very same instance", wontBecomeAProxy,
				accessExpr.getProperty());
		assertTrue(supplierResource.isLoaded());

		assertEquals("foo", accessExpr.getProperty().getName());

		TModule supplierTModule = (TModule) last(supplierResource.getContents());
		TClass supplierType = (TClass) head(supplierTModule.getTypes());
		assertSame(head(supplierType.getOwnedMembers()), accessExpr.getProperty());
	}

	/**
	 * We need a faked container state in the test because we want to simulate a resource set that does not contain all
	 * resources that are available in the index. In the runtime scenario, this is usually not possible because all
	 * resources will be loaded up-front, but the test case deletes them from the resource set as. soon as. the index
	 * was populated.
	 *
	 * This custom IAllContainersState keeps track of a single resource and convinces the framework that it exists.
	 */
	class MyFlatResourceSetBasedAllContainersState extends FlatResourceSetBasedAllContainersState {

		URI additionalURI;

		MyFlatResourceSetBasedAllContainersState(ResourceSet resourceSet, URI additionalURI) {
			super(resourceSet);
			this.additionalURI = additionalURI;
		}

		@Override
		public boolean isEmpty(String containerHandle) {
			return false;
		}

		@Override
		public List<URI> getContainedURIs(String containerHandle) {
			List<URI> result = new ArrayList<>();
			result.addAll(super.getContainedURIs(containerHandle));
			result.add(additionalURI);
			return result;
		}

	}
}
