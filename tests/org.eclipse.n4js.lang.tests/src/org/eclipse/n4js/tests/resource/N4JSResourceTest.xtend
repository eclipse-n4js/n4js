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
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.jface.text.DocumentEvent
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.resource.UserdataMapper
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.DerivedStateAwareResource
import org.eclipse.xtext.resource.IDerivedStateComputer
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.OutdatedStateManager
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.impl.AbstractResourceDescription
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N4JSResourceTest {

	@Inject IQualifiedNameConverter qualifiedNameConverter

	@Inject Provider<XtextResourceSet> resourceSetProvider

	@Inject OutdatedStateManager outdatedStateManager

	@Inject OperationCanceledManager cancelManager

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	IResourceDescription emptyDescription = new EmptyResourceDescription

	@Inject extension ParseHelper<Script>

	private def loadAndResolve(String path, XtextResourceSet rs) {
		val result = rs.getResource(URI.createURI(path), true) as N4JSResource
		result.performPostProcessing
		return result
	}

	private def loadAndResolve(String path) {
		val result = resourceSetProvider.get.getResource(URI.createURI(path), true) as N4JSResource
		result.performPostProcessing
		return result
	}

	private def getInitializedDescription(N4JSResource resource) {
		val allDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resource);
		val result = allDescriptions.getResourceDescription(resource.URI)
		// init description and user data
		result.getExportedObjects.forEach[ userDataKeys ]
		return result
	}

	@Test
	def void testLoadFromDescription() {
		var rs = resourceSetProvider.get();
		val supplierResource = "src/org/eclipse/n4js/tests/scoping/Supplier.n4js".loadAndResolve(rs)

		val resourceDescription = supplierResource.initializedDescription

		supplierResource.unload

		val qn = QualifiedName.create("org", "eclipse", "n4js", "tests", "scoping", "Supplier");
		val typeDescription = resourceDescription.getExportedObjects(
			TypesPackage.Literals.TMODULE,
			qn, false
		).head

		assertNotNull("Did not find module with name " + qualifiedNameConverter.toString(qn), typeDescription)
		val typeFromDescription = typeDescription.EObjectOrProxy
		assertTrue('typeFromDescription.eIsProxy', typeFromDescription.eIsProxy)

		rs.resources.clear

		val newResource = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource
		newResource.loadFromDescription(resourceDescription)
		assertFalse(newResource.loaded)
		val contents = newResource.contents
		assertEquals(2, contents.size)
		val deserializedScript = contents.get(1) as TModule
		val deserializedType = deserializedScript.topLevelTypes.head as TClass
		assertEquals("Supplier", deserializedType.name)
		val astProxy = deserializedType.eGet(TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT, false) as EObject
		assertTrue(astProxy.toString, astProxy.eIsProxy)
		val proxyURI = EcoreUtil.getURI(astProxy)
		assertEquals("/0/@scriptElements.0/@exportedElement", proxyURI.fragment)
		val resolvedAstElement = deserializedType.astElement
		assertFalse(resolvedAstElement.eIsProxy)
	}

	@Test
	def void testLoadFromDescriptionFailsIfResourceIsLoaded() {
		var rs = resourceSetProvider.get();
		val supplierResource = rs.getResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"), true) as N4JSResource
		assertTrue(supplierResource.loaded)
		try {
			supplierResource.loadFromDescription(emptyDescription)
			fail("Expected IllegalStateException")
		} catch(IllegalStateException ise) {
			// success
		}
	}

	@Test
	def void testLoadFromDescriptionDoesNotFailIfRunTwice() {
		var rs = resourceSetProvider.get();
		val supplierResource = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource
		assertFalse(supplierResource.loaded)
		supplierResource.loadFromDescription(emptyDescription) // failed attempt to load from description should not change state of supplierResource
		assertFalse(supplierResource.loaded)
		supplierResource.loadFromDescription(emptyDescription) // thus, this should not throw an exception
	}

	@Test
	def void testNoEventsOnLoadFromDescription() {
		val supplierResource = "src/org/eclipse/n4js/tests/scoping/Supplier.n4js".loadAndResolve
		val resourceDescription = supplierResource.initializedDescription

		val secondResourceSet = resourceSetProvider.get();
		val emptyResource = secondResourceSet.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource
		val recorder = new RecordingAdapter
		emptyResource.eAdapters += recorder
		emptyResource.loadFromDescription(resourceDescription)

		assertTrue(recorder.notifications.empty)
		assertEquals(2, emptyResource.contents.size)
		assertFalse(emptyResource.loaded)
		assertFalse(emptyResource.loading)
	}

	@Test
	def void testDemandLoadResetsResourceInformation() {
		val supplierResource = "src/org/eclipse/n4js/tests/scoping/Supplier.n4js".loadAndResolve

		val resourceDescription = supplierResource.initializedDescription

		val secondResourceSet = resourceSetProvider.get();
		val loadedFromDescription = secondResourceSet.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource
		val recorder = new RecordingAdapter

		loadedFromDescription.loadFromDescription(resourceDescription)
		val exported = loadedFromDescription.contents.last as TModule
		assertFalse(exported.eIsProxy)

		loadedFromDescription.eAdapters += recorder

		val demandLoaded = exported.astElement
		assertFalse(exported.eIsProxy)
		assertNotNull(exported.eResource)
		assertFalse(demandLoaded.eIsProxy)
		assertSame(loadedFromDescription, demandLoaded.eResource)

		// three events are expected:
		// load (false -> true)
		// modified (touch, false -> false)
		// resolve first proxy
		assertEquals(recorder.notifications.toString, 3, recorder.notifications.size)
		assertEquals(Notification.SET, recorder.notifications.get(0).eventType)
		assertEquals(Notification.SET, recorder.notifications.get(1).eventType)
		assertTrue(recorder.notifications.get(1).touch)
		assertEquals(Notification.RESOLVE, recorder.notifications.get(2).eventType)
		assertEquals(2, loadedFromDescription.contents.size)
		assertTrue(loadedFromDescription.loaded)
		assertFalse(loadedFromDescription.loading)
	}

	@Test
	def void testResolveProxyMarksResourceLoadedButDoesNotLoad() {
		val supplierResource = "src/org/eclipse/n4js/tests/scoping/Supplier.n4js".loadAndResolve
		val resourceDescription = supplierResource.initializedDescription

		val secondResourceSet = resourceSetProvider.get();
		val loadedFromDescription = secondResourceSet.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource

		loadedFromDescription.loadFromDescription(resourceDescription)

		val uri = EcoreUtil.getURI(loadedFromDescription.contents.last)
		assertFalse(loadedFromDescription.loaded)
		secondResourceSet.getEObject(uri, true)
		assertTrue(loadedFromDescription.loaded)

		val basicList = loadedFromDescription.contents as BasicEList<EObject>
		assertTrue(basicList.basicGet(0).eIsProxy)
	}

	@Test
	def void testDisposeDocumentDoesntTriggerResolution() {
		val someResource = resourceSetProvider.get.getResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"), true) as N4JSResource
		someResource.derivedStateComputer = new IDerivedStateComputer() {

			override discardDerivedState(DerivedStateAwareResource resource) {
				Assert.fail('Unexpected access to the derived state computer')
			}

			override installDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase) {
				Assert.fail('Unexpected access to the derived state computer')
			}

		}
		val doc = new XtextDocument(new DocumentTokenSource() {
			override protected computeDamageRegion(DocumentEvent e) {
				return null
			}
		}, null, outdatedStateManager, cancelManager)
		doc.input = someResource
		Assert.assertFalse(someResource.script.eIsProxy)
try {
// as of Xtext 2.16.0 the following will trigger loading of the resource, leading to an exception as asserted below;
// see https://github.com/eclipse/xtext-eclipse/issues/967
// (remove this once issue #967 is fixed)
		doc.disposeInput // no exception
Assert.fail("Xtext issue #967 seems to be fixed! Remove temporary workaround!");
} catch(IllegalStateException e) {
Assert.assertEquals("unexpected exception message", "Missing adapter for BuiltInTypeScope", e.message)
}
	}


	/**
	 * Explicitly given type on property in object literal must lead to valid ResourceDescription.
	 * (Was broken and reported by GHOLD-183)
	 */
	@Test
	def void testDescriptionOfInternalObjectLiteralExplicitTypesNotBroken() {
		var rs = resourceSetProvider.get();
		val script = '''
			export let someObjectLiteral =
			{
					string  g : ""
			};
		'''.parse(rs)
		val TModule module = script.eResource.contents.get(1) as TModule;
		val udata = UserdataMapper.createUserData(module);

		assertTrue("Serialized script missing.",udata.containsKey( UserdataMapper.USERDATA_KEY_SERIALIZED_SCRIPT))
		assertFalse("Time stamp not expected.",udata.containsKey( UserdataMapper.USERDATA_KEY_TIMESTAMP))

	}

	/**
	 * Inferred type on property in object literal must lead to valid ResourceDescription.
	 * (Sibling to {@link #testDescriptionOfInternalObjectLiteralExplicitTypesNotBroken()} )
	 */
	@Test
	def void testDescriptionOfInternalObjectLiteralWithInferredTypeNotBroken() {
		var rs = resourceSetProvider.get();
		val script = '''
			export let someObjectLiteral =
			{
					/*string*/  g : ""
			};
		'''.parse(rs)
		val TModule module = script.eResource.contents.get(1) as TModule;
		val udata = UserdataMapper.createUserData(module);

		assertTrue("Main module data missing.",udata.containsKey( UserdataMapper.USERDATA_KEY_SERIALIZED_SCRIPT))
		assertFalse("Time stamp not expected.",udata.containsKey( UserdataMapper.USERDATA_KEY_TIMESTAMP))
	}

	@Test
	def void testUnloadASTAfterCreated() {
		val rs = resourceSetProvider.get();
		val N4JSResource res = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource;

		// Just to make sure that our assumptions are correct.
		assertFalse("Resource is not initially loaded", res.loaded);
		assertFalse("Resource is not initially loading", res.loading);
		assertFalse("Resource is not initially fully initialized", res.fullyInitialized);
		assertFalse("Resource is not initially fully processed", res.fullyProcessed);

		res.unloadAST();

		assertFalse("Resource is not loaded after unloading AST", res.loaded);
		assertFalse("Resource is not loading after unloading AST", res.loading);
		assertFalse("Resource is not fully initialized after unloading AST", res.fullyInitialized);
		assertFalse("Resource is not fully processed after unloading AST", res.fullyProcessed);

		assertNull("Script is null after unloading AST", res.script);
		assertNull("TModule is null after unloading AST", res.module);
	}

	@Test
	def void testUnloadASTAfterLoad() {
		val rs = resourceSetProvider.get();
		val N4JSResource res = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource;
		res.load(newHashMap());

		// Just to make sure that our assumptions are correct.
		assertTrue("Resource is initially loaded", res.loaded);
		assertFalse("Resource is not initially loading", res.loading);
		assertFalse("Resource is not initially fully initialized", res.fullyInitialized);
		assertFalse("Resource is not initially fully processed", res.fullyProcessed);

		res.unloadAST();

		assertFalse("Resource is not loaded after unloading AST", res.loaded);
		assertFalse("Resource is not loading after unloading AST", res.loading);
		assertFalse("Resource is not fully initialized after unloading AST", res.fullyInitialized);
		assertFalse("Resource is not fully processed after unloading AST", res.fullyProcessed);

		assertNull("Script is still non-null after unloading AST", res.script);
		assertNull("TModule is null after unloading AST", res.module);
	}

	@Test
	def void testUnloadASTAfterPrelinking() {
		val rs = resourceSetProvider.get();
		val N4JSResource res = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource;
		res.load(newHashMap());
		res.initializedDescription // Trigger prelinking

		assertTrue("Resource is initially loaded", res.loaded);
		assertFalse("Resource is not initially loading", res.loading);
		assertFalse("Resource is not initially fully initialized", res.fullyInitialized);
		assertFalse("Resource is not initially fully processed", res.fullyProcessed);

		res.unloadAST();

		assertFalse("Resource is not loaded after unloading AST", res.loaded);
		assertFalse("Resource is not loading after unloading AST", res.loading);
		assertFalse("Resource is not fully initialized after unloading AST", res.fullyInitialized);
		assertFalse("Resource is not fully processed after unloading AST", res.fullyProcessed);

		assertNull("Script is still non-null after unloading AST", res.script);
		assertNull("TModule is null after unloading AST", res.module);
	}

	@Test
	def void testUnloadASTAfterFullyProcessed() {
		val rs = resourceSetProvider.get();
		val N4JSResource res = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource;
		res.load(newHashMap());
		res.performPostProcessing

		assertTrue("Resource is initially loaded", res.loaded);
		assertFalse("Resource is not initially loading", res.loading);
		assertTrue("Resource is initially fully initialized", res.fullyInitialized);
		assertTrue("Resource is not initially fully processed", res.fullyProcessed);

		res.unloadAST();

		assertFalse("Resource is loaded after unloading AST", res.loaded);
		assertFalse("Resource is not loading after unloading AST", res.loading);
		assertTrue("Resource is fully initialized after unloading AST", res.fullyInitialized);
		assertTrue("Resource is fully processed after unloading AST", res.fullyProcessed);

		assertNotNull("Script is null after unloading AST", res.script);
		assertTrue("Script is proxified after unloading AST", res.script.eIsProxy);
		assertNotNull("TModule is available after unloading AST", res.module);
		assertFalse("TModule is not proxified after unloading AST", res.module.eIsProxy);
		assertTrue("TModule AST node is script proxy after unloading AST", res.script === res.module.astElement);
	}

	@Test
	def void testUnloadASTAfterLoadFromDescription() {
		var rs = resourceSetProvider.get();
		val supplierResource = "src/org/eclipse/n4js/tests/scoping/Supplier.n4js".loadAndResolve(rs)

		val resourceDescription = supplierResource.initializedDescription

		supplierResource.unload

		val qn = QualifiedName.create("org", "eclipse", "n4js", "tests", "scoping", "Supplier");
		val typeDescription = resourceDescription.getExportedObjects(
			TypesPackage.Literals.TMODULE,
			qn, false
		).head

		assertNotNull("Did not find module with name " + qualifiedNameConverter.toString(qn), typeDescription)
		val typeFromDescription = typeDescription.EObjectOrProxy
		assertTrue('typeFromDescription.eIsProxy', typeFromDescription.eIsProxy)

		rs.resources.clear

		val res = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")) as N4JSResource
		res.loadFromDescription(resourceDescription)

		assertFalse("Resource is initially loaded", res.loaded);
		assertFalse("Resource is not initially loading", res.loading);
		assertTrue("Resource is initially fully initialized", res.fullyInitialized);
		assertTrue("Resource is not initially fully processed", res.fullyProcessed);

		res.unloadAST();

		assertFalse("Resource is loaded after unloading AST", res.loaded);
		assertFalse("Resource is not loading after unloading AST", res.loading);
		assertTrue("Resource is fully initialized after unloading AST", res.fullyInitialized);
		assertTrue("Resource is fully processed after unloading AST", res.fullyProcessed);

		assertNotNull("Script is null after unloading AST", res.script);
		assertTrue("Script is proxified after unloading AST", res.script.eIsProxy);
		assertNotNull("TModule is available after unloading AST", res.module);
		assertFalse("TModule is not proxified after unloading AST", res.module.eIsProxy);
	}
}

package class RecordingAdapter extends AdapterImpl {

	package val notifications = <Notification>newArrayList

	override notifyChanged(Notification msg) {
		notifications += msg
	}

}

package class EmptyResourceDescription extends AbstractResourceDescription {

	override protected computeExportedObjects() {
	}

	override getImportedNames() {
		return emptyList
	}

	override getExportedObjects() {
		return emptyList
	}

	override getExportedObjectsByType(EClass type) {
		return emptyList
	}

	override getReferenceDescriptions() {
		return emptyList
	}

	override getURI() {
		throw new UnsupportedOperationException("Should never be invoked in the test")
	}

}
