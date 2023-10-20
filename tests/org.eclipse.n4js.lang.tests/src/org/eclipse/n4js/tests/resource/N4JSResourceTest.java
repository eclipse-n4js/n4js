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
package org.eclipse.n4js.tests.resource;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IRegion;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.IDerivedStateComputer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.OutdatedStateManager;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.AbstractResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N4JSResourceTest {

	@Inject
	IQualifiedNameConverter qualifiedNameConverter;

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	OutdatedStateManager outdatedStateManager;

	@Inject
	OperationCanceledManager cancelManager;

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	IResourceDescription emptyDescription = new EmptyResourceDescription();

	@Inject
	ParseHelper<Script> parseHelper;

	private N4JSResource loadAndResolve(String path, XtextResourceSet rs) {
		N4JSResource result = (N4JSResource) rs.getResource(URI.createURI(path), true);
		result.performPostProcessing();
		return result;
	}

	private N4JSResource loadAndResolve(String path) {
		N4JSResource result = (N4JSResource) resourceSetProvider.get().getResource(URI.createURI(path), true);
		result.performPostProcessing();
		return result;
	}

	private IResourceDescription getInitializedDescription(N4JSResource resource) {
		IResourceDescriptions allDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resource);
		IResourceDescription result = allDescriptions.getResourceDescription(resource.getURI());
		// init description and user data
		for (IEObjectDescription od : result.getExportedObjects()) {
			od.getUserDataKeys();
		}
		return result;
	}

	@Test
	public void testLoadFromDescription() {
		XtextResourceSet rs = resourceSetProvider.get();
		N4JSResource supplierResource = loadAndResolve("src/org/eclipse/n4js/tests/scoping/Supplier.n4js", rs);

		IResourceDescription resourceDescription = getInitializedDescription(supplierResource);

		supplierResource.unload();

		QualifiedName qn = QualifiedName.create("org", "eclipse", "n4js", "tests", "scoping", "Supplier");
		IEObjectDescription typeDescription = head(resourceDescription.getExportedObjects(
				TypesPackage.Literals.TMODULE,
				qn, false));

		assertNotNull("Did not find module with name " + qualifiedNameConverter.toString(qn), typeDescription);
		EObject typeFromDescription = typeDescription.getEObjectOrProxy();
		assertTrue("typeFromDescription.eIsProxy()", typeFromDescription.eIsProxy());

		rs.getResources().clear();

		N4JSResource newResource = (N4JSResource) rs
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		newResource.loadFromDescription(resourceDescription);
		assertFalse(newResource.isLoaded());
		EList<EObject> contents = newResource.getContents();
		assertEquals(2, contents.size());
		TModule deserializedScript = (TModule) contents.get(1);
		TClass deserializedType = (TClass) head(deserializedScript.getTypes());
		assertEquals("Supplier", deserializedType.getName());
		EObject astProxy = (EObject) deserializedType.eGet(TypesPackage.Literals.SYNTAX_RELATED_TELEMENT__AST_ELEMENT,
				false);
		assertTrue(astProxy.toString(), astProxy.eIsProxy());
		URI proxyURI = EcoreUtil.getURI(astProxy);
		assertEquals("/0/@scriptElements.0/@exportedElement", proxyURI.fragment());
		EObject resolvedAstElement = deserializedType.getAstElement();
		assertFalse(resolvedAstElement.eIsProxy());
	}

	@Test
	public void testLoadFromDescriptionFailsIfResourceIsLoaded() {
		XtextResourceSet rs = resourceSetProvider.get();
		N4JSResource supplierResource = (N4JSResource) rs
				.getResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"), true);
		assertTrue(supplierResource.isLoaded());
		try {
			supplierResource.loadFromDescription(emptyDescription);
			fail("Expected IllegalStateException");
		} catch (IllegalStateException ise) {
			// success
		}
	}

	@Test
	public void testLoadFromDescriptionDoesNotFailIfRunTwice() {
		XtextResourceSet rs = resourceSetProvider.get();
		N4JSResource supplierResource = (N4JSResource) rs
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		assertFalse(supplierResource.isLoaded());
		supplierResource.loadFromDescription(emptyDescription);// failed attempt to load from description should not
																// change state of supplierResource
		assertFalse(supplierResource.isLoaded());
		supplierResource.loadFromDescription(emptyDescription);// thus, this should not throw an exception
	}

	@Test
	public void testNoEventsOnLoadFromDescription() {
		N4JSResource supplierResource = loadAndResolve("src/org/eclipse/n4js/tests/scoping/Supplier.n4js");
		IResourceDescription resourceDescription = getInitializedDescription(supplierResource);

		XtextResourceSet secondResourceSet = resourceSetProvider.get();
		N4JSResource emptyResource = (N4JSResource) secondResourceSet
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		RecordingAdapter recorder = new RecordingAdapter();
		emptyResource.eAdapters().add(recorder);
		emptyResource.loadFromDescription(resourceDescription);

		assertTrue(recorder.notifications.isEmpty());
		assertEquals(2, emptyResource.getContents().size());
		assertFalse(emptyResource.isLoaded());
		assertFalse(emptyResource.isLoading());
	}

	@Test
	public void testDemandLoadResetsResourceInformation() {
		N4JSResource supplierResource = loadAndResolve("src/org/eclipse/n4js/tests/scoping/Supplier.n4js");

		IResourceDescription resourceDescription = getInitializedDescription(supplierResource);

		XtextResourceSet secondResourceSet = resourceSetProvider.get();
		N4JSResource loadedFromDescription = (N4JSResource) secondResourceSet
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		RecordingAdapter recorder = new RecordingAdapter();

		loadedFromDescription.loadFromDescription(resourceDescription);
		TModule exported = (TModule) last(loadedFromDescription.getContents());
		assertFalse(exported.eIsProxy());

		loadedFromDescription.eAdapters().add(recorder);

		EObject demandLoaded = exported.getAstElement();
		assertFalse(exported.eIsProxy());
		assertNotNull(exported.eResource());
		assertFalse(demandLoaded.eIsProxy());
		assertSame(loadedFromDescription, demandLoaded.eResource());

		// three events are expected:
		// load (false -> true)
		// modified (touch, false -> false)
		// resolve first proxy
		assertEquals(recorder.notifications.toString(), 3, recorder.notifications.size());
		assertEquals(Notification.SET, recorder.notifications.get(0).getEventType());
		assertEquals(Notification.SET, recorder.notifications.get(1).getEventType());
		assertTrue(recorder.notifications.get(1).isTouch());
		assertEquals(Notification.RESOLVE, recorder.notifications.get(2).getEventType());
		assertEquals(2, loadedFromDescription.getContents().size());
		assertTrue(loadedFromDescription.isLoaded());
		assertFalse(loadedFromDescription.isLoading());
	}

	@Test
	public void testResolveProxyMarksResourceLoadedButDoesNotLoad() {
		N4JSResource supplierResource = loadAndResolve("src/org/eclipse/n4js/tests/scoping/Supplier.n4js");
		IResourceDescription resourceDescription = getInitializedDescription(supplierResource);

		XtextResourceSet secondResourceSet = resourceSetProvider.get();
		N4JSResource loadedFromDescription = (N4JSResource) secondResourceSet
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));

		loadedFromDescription.loadFromDescription(resourceDescription);

		URI uri = EcoreUtil.getURI(last(loadedFromDescription.getContents()));
		assertFalse(loadedFromDescription.isLoaded());
		secondResourceSet.getEObject(uri, true);
		assertTrue(loadedFromDescription.isLoaded());

		BasicEList<EObject> basicList = (BasicEList<EObject>) loadedFromDescription.getContents();
		assertTrue(basicList.basicGet(0).eIsProxy());
	}

	@Test
	public void testDisposeDocumentDoesntTriggerResolution() {
		N4JSResource someResource = (N4JSResource) resourceSetProvider.get()
				.getResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"), true);
		someResource.setDerivedStateComputer(new IDerivedStateComputer() {

			@Override
			public void discardDerivedState(DerivedStateAwareResource resource) {
				fail("Unexpected access to the derived state computer");
			}

			@Override
			public void installDerivedState(DerivedStateAwareResource resource, boolean preLinkingPhase) {
				fail("Unexpected access to the derived state computer");
			}

		});
		XtextDocument doc = new XtextDocument(new DocumentTokenSource() {
			@Override
			protected IRegion computeDamageRegion(DocumentEvent e) {
				return null;
			}
		}, null, outdatedStateManager, cancelManager);
		doc.setInput(someResource);
		Assert.assertFalse(someResource.getScript().eIsProxy());
		try {
			// as of Xtext 2.16.0 the following will trigger loading of the resource, leading to an exception as
			// asserted below;
			// see https://github.com/eclipse/xtext-eclipse/issues/967
			// (remove this once issue #967 is fixed)
			doc.disposeInput(); // no exception
			Assert.fail("Xtext issue #967 seems to be fixed! Remove temporary workaround!");
		} catch (IllegalStateException e) {
			// original error behavior (still observable when run locally as headless(!) plugin test AND on
			// maven/Jenkins):
			Assert.assertEquals("unexpected exception message", "Missing adapter for BuiltInTypeScope", e.getMessage());
		} catch (NoClassDefFoundError e) {
			// error behavior as of July 2019 when executed locally as plain JUnit test (i.e. as non-plugin test):
			Assert.assertEquals("unexpected exception message", "org/eclipse/e4/ui/workbench/IWorkbench",
					e.getMessage());
		}
	}

	/**
	 * Explicitly given type on property in object literal must lead to valid ResourceDescription. (Was broken and
	 * reported by GHOLD-183)
	 */
	@Test
	public void testDescriptionOfInternalObjectLiteralExplicitTypesNotBroken() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		Script script = parseHelper.parse("""
					export let someObjectLiteral =
					{
							string  g : ""
					};
				""", rs);
		TModule module = (TModule) script.eResource().getContents().get(1);
		Map<String, String> udata = UserDataMapper.createUserData(module);

		assertTrue("Serialized script missing.", udata.containsKey(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT));
		assertFalse("Time stamp not expected.", udata.containsKey(UserDataMapper.USER_DATA_KEY_TIMESTAMP));

	}

	/**
	 * Inferred type on property in object literal must lead to valid ResourceDescription. (Sibling to
	 * {@link #testDescriptionOfInternalObjectLiteralExplicitTypesNotBroken()} )
	 */
	@Test
	public void testDescriptionOfInternalObjectLiteralWithInferredTypeNotBroken() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		Script script = parseHelper.parse("""
					export let someObjectLiteral =
					{
							/*string*/  g : ""
					};
				""", rs);
		TModule module = (TModule) script.eResource().getContents().get(1);
		Map<String, String> udata = UserDataMapper.createUserData(module);

		assertTrue("Main module data missing.", udata.containsKey(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT));
		assertFalse("Time stamp not expected.", udata.containsKey(UserDataMapper.USER_DATA_KEY_TIMESTAMP));
	}

	@Test
	public void testUnloadASTAfterCreated() {
		XtextResourceSet rs = resourceSetProvider.get();
		N4JSResource res = (N4JSResource) rs
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));

		// Just to make sure that our assumptions are correct.
		assertFalse("Resource is not initially loaded", res.isLoaded());
		assertFalse("Resource is not initially loading", res.isLoading());
		assertFalse("Resource is not initially fully initialized", res.isFullyInitialized());
		assertFalse("Resource is not initially fully processed", res.isFullyProcessed());

		res.unloadAST();

		assertFalse("Resource is not loaded after unloading AST", res.isLoaded());
		assertFalse("Resource is not loading after unloading AST", res.isLoading());
		assertFalse("Resource is not fully initialized after unloading AST", res.isFullyInitialized());
		assertFalse("Resource is not fully processed after unloading AST", res.isFullyProcessed());

		assertNull("Script is null after unloading AST", res.getScript());
		assertNull("TModule is null after unloading AST", res.getModule());
	}

	@Test
	public void testUnloadASTAfterLoad() throws IOException {
		XtextResourceSet rs = resourceSetProvider.get();
		N4JSResource res = (N4JSResource) rs
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		res.load(new HashMap<>());

		// Just to make sure that our assumptions are correct.
		assertTrue("Resource is initially loaded", res.isLoaded());
		assertFalse("Resource is not initially loading", res.isLoading());
		assertFalse("Resource is not initially fully initialized", res.isFullyInitialized());
		assertFalse("Resource is not initially fully processed", res.isFullyProcessed());

		res.unloadAST();

		assertFalse("Resource is not loaded after unloading AST", res.isLoaded());
		assertFalse("Resource is not loading after unloading AST", res.isLoading());
		assertFalse("Resource is not fully initialized after unloading AST", res.isFullyInitialized());
		assertFalse("Resource is not fully processed after unloading AST", res.isFullyProcessed());

		assertNull("Script is still non-null after unloading AST", res.getScript());
		assertNull("TModule is null after unloading AST", res.getModule());
	}

	@Test
	public void testUnloadASTAfterPrelinking() throws IOException {
		XtextResourceSet rs = resourceSetProvider.get();
		N4JSResource res = (N4JSResource) rs
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		res.load(new HashMap<>());
		getInitializedDescription(res); // Trigger prelinking

		assertTrue("Resource is initially loaded", res.isLoaded());
		assertFalse("Resource is not initially loading", res.isLoading());
		assertFalse("Resource is not initially fully initialized", res.isFullyInitialized());
		assertFalse("Resource is not initially fully processed", res.isFullyProcessed());

		res.unloadAST();

		assertFalse("Resource is not loaded after unloading AST", res.isLoaded());
		assertFalse("Resource is not loading after unloading AST", res.isLoading());
		assertFalse("Resource is not fully initialized after unloading AST", res.isFullyInitialized());
		assertFalse("Resource is not fully processed after unloading AST", res.isFullyProcessed());

		assertNull("Script is still non-null after unloading AST", res.getScript());
		assertNull("TModule is null after unloading AST", res.getModule());
	}

	@Test
	public void testUnloadASTAfterFullyProcessed() throws IOException {
		XtextResourceSet rs = resourceSetProvider.get();
		N4JSResource res = (N4JSResource) rs
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		res.load(new HashMap<>());
		res.performPostProcessing();

		assertTrue("Resource is initially loaded", res.isLoaded());
		assertFalse("Resource is not initially loading", res.isLoading());
		assertTrue("Resource is initially fully initialized", res.isFullyInitialized());
		assertTrue("Resource is not initially fully processed", res.isFullyProcessed());

		res.unloadAST();

		assertFalse("Resource is loaded after unloading AST", res.isLoaded());
		assertFalse("Resource is not loading after unloading AST", res.isLoading());
		assertTrue("Resource is fully initialized after unloading AST", res.isFullyInitialized());
		assertTrue("Resource is fully processed after unloading AST", res.isFullyProcessed());

		assertNotNull("Script is null after unloading AST", res.getScript());
		assertTrue("Script is proxified after unloading AST", res.getScript().eIsProxy());
		assertNotNull("TModule is available after unloading AST", res.getModule());
		assertFalse("TModule is not proxified after unloading AST", res.getModule().eIsProxy());
		assertTrue("TModule AST node is script proxy after unloading AST",
				res.getScript() == res.getModule().getAstElement());
	}

	@Test
	public void testUnloadASTAfterLoadFromDescription() {
		XtextResourceSet rs = resourceSetProvider.get();
		N4JSResource supplierResource = loadAndResolve("src/org/eclipse/n4js/tests/scoping/Supplier.n4js", rs);

		IResourceDescription resourceDescription = getInitializedDescription(supplierResource);

		supplierResource.unload();

		QualifiedName qn = QualifiedName.create("org", "eclipse", "n4js", "tests", "scoping", "Supplier");
		IEObjectDescription typeDescription = head(resourceDescription.getExportedObjects(
				TypesPackage.Literals.TMODULE,
				qn, false));

		assertNotNull("Did not find module with name " + qualifiedNameConverter.toString(qn), typeDescription);
		EObject typeFromDescription = typeDescription.getEObjectOrProxy();
		assertTrue("typeFromDescription.eIsProxy()", typeFromDescription.eIsProxy());

		rs.getResources().clear();

		N4JSResource res = (N4JSResource) rs
				.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"));
		res.loadFromDescription(resourceDescription);

		assertFalse("Resource is initially loaded", res.isLoaded());
		assertFalse("Resource is not initially loading", res.isLoading());
		assertTrue("Resource is initially fully initialized", res.isFullyInitialized());
		assertTrue("Resource is not initially fully processed", res.isFullyProcessed());

		res.unloadAST();

		assertFalse("Resource is loaded after unloading AST", res.isLoaded());
		assertFalse("Resource is not loading after unloading AST", res.isLoading());
		assertTrue("Resource is fully initialized after unloading AST", res.isFullyInitialized());
		assertTrue("Resource is fully processed after unloading AST", res.isFullyProcessed());

		assertNotNull("Script is null after unloading AST", res.getScript());
		assertTrue("Script is proxified after unloading AST", res.getScript().eIsProxy());
		assertNotNull("TModule is available after unloading AST", res.getModule());
		assertFalse("TModule is not proxified after unloading AST", res.getModule().eIsProxy());
	}
}

class RecordingAdapter extends AdapterImpl {

	List<Notification> notifications = new ArrayList<>();

	@Override
	public void notifyChanged(Notification msg) {
		notifications.add(msg);
	}

}

class EmptyResourceDescription extends AbstractResourceDescription {

	@Override
	protected List<IEObjectDescription> computeExportedObjects() {
		return Collections.emptyList();
	}

	@Override
	public Iterable<QualifiedName> getImportedNames() {
		return Collections.emptyList();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjects() {
		return Collections.emptyList();
	}

	@Override
	public Iterable<IEObjectDescription> getExportedObjectsByType(EClass type) {
		return Collections.emptyList();
	}

	@Override
	public Iterable<IReferenceDescription> getReferenceDescriptions() {
		return Collections.emptyList();
	}

	@Override
	public URI getURI() {
		throw new UnsupportedOperationException("Should never be invoked in the test");
	}

}
