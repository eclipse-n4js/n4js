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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.emf.ProxyResolvingResource;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.resource.impl.ResourceSetBasedResourceDescriptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Tests resolution of module-to-module proxies, see {@link ProxyResolvingResource} and the special handling in
 * {@link N4JSResource#doResolveProxy(InternalEObject, EObject)}.
 */
// converted from ModuleToModuleProxyPluginTest
public class ModuleToModuleProxyIdeTest extends ConvertedIdeTest {

	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	private FileURI uriA;
	private FileURI uriB;
	private FileURI uriB2;

	/**
	 * All tests are based on having type information for two files A.n4js and B.n4js in the Xtext index. File B2.n4js
	 * is only used by some tests.
	 */
	@Before
	public void prepareIndex() {
		testWorkspaceManager.createTestProjectOnDisk(
				Pair.of("A", """
						export public class A {
							constructor() {}
						}
						"""),
				Pair.of("B", """
						import { A } from "A"
						export public class B extends A {}
						"""),
				Pair.of("B2", """
						import { A } from "A"
						export public class B2 extends A {}
						"""));
		startAndWaitForLspServer();
		assertNoIssues();

		uriA = getFileURIFromModuleName("A");
		uriB = getFileURIFromModuleName("B");
		uriB2 = getFileURIFromModuleName("B2");

		// we now have type information for files A, B, and B2 in the Xtext index
	}

	@Test
	public void testLoadFromIndexAfterLoadingFromIndex() {
		// (1) load file B from index
		N4JSResource resourceB = createNewResourceSetAndLoadFileFromIndex(uriB);
		ResourceSet resourceSet = resourceB.getResourceSet();

		// A.n4js should not be loaded yet
		assertEquals(List.of("B.n4js"), getResourceNames(resourceSet));

		// make sure the reference to A was properly created as a m2m URI
		TClass classB = (TClass) resourceB.getModule().getTypes().iterator().next();
		Type proxyToClassA = getDeclaredTypeNoResolve(classB.getSuperClassRef());
		assertTrue(proxyToClassA.eIsProxy());

		// (2) resolve the reference to A (this should load A.n4js from the index)
		TClass classA = (TClass) classB.getSuperClassRef().getDeclaredType();
		assertFalse(classA.eIsProxy());

		// now, A.n4js should be loaded
		assertEquals(List.of("B.n4js", "A.n4js"), getResourceNames(resourceSet));
		N4JSResource resourceA = (N4JSResource) resourceSet.getResources().get(1);

		// make sure it was loaded from the index (not from the file on disk)
		assertTrue(resourceA.isLoadedFromDescription());

		// make sure it was post-processed
		// (trivial in this case, because resources loaded from index are always post-processed)
		assertTrue(resourceA.isFullyProcessed());
		assertFalse(getCtor(classA).getReturnTypeRef() instanceof DeferredTypeRef);
		assertTrue(getCtor(classA).getReturnTypeRef() instanceof BoundThisTypeRef);
	}

	@Test
	public void testUnloadAndReloadAfterLoadingFromIndex() {
		// (1) load files B and B2 from index
		List<N4JSResource> resources = createNewResourceSetAndLoadFilesFromIndex(uriB, uriB2);
		N4JSResource resourceB = resources.get(0);
		N4JSResource resourceB2 = resources.get(1);
		ResourceSet resourceSet = resourceB.getResourceSet();

		// (2) load file A by resolving proxies in modules of B and B2
		TClass classB = (TClass) resourceB.getModule().getTypes().iterator().next();
		TClass classB2 = (TClass) resourceB2.getModule().getTypes().iterator().next();
		Type classA_beforeUnload = classB.getSuperClassRef().getDeclaredType();
		classB2.getSuperClassRef().getDeclaredType(); // also resolve the proxy in B2's module (required for tests after
														// unloading)
		assertEquals(List.of("B.n4js", "B2.n4js", "A.n4js"), getResourceNames(resourceSet));
		Resource resourceA_beforeUnload = resourceSet.getResources().get(2);

		// (3) unload file A
		resourceA_beforeUnload.unload();
		resourceSet.getResources().remove(resourceA_beforeUnload);
		assertNull(classA_beforeUnload.eResource());

		// make sure the references in module of B and B2 pointing to A were properly proxified with a m2m URI
		Type proxyToClassA_fromB = getDeclaredTypeNoResolve(classB.getSuperClassRef());
		assertTrue(proxyToClassA_fromB.eIsProxy());
		Type proxyToClassA_fromB2 = getDeclaredTypeNoResolve(classB2.getSuperClassRef());
		assertTrue(proxyToClassA_fromB2.eIsProxy());

		// (4) resolve both proxies pointing to A thus loading A.n4js from index (again)
		TClass classA_fromB = (TClass) classB.getSuperClassRef().getDeclaredType();
		assertFalse(classA_fromB.eIsProxy());
		assertNotSame(classA_fromB, classA_beforeUnload);
		TClass classA_fromB2 = (TClass) classB2.getSuperClassRef().getDeclaredType();
		assertFalse(classA_fromB2.eIsProxy());
		assertNotSame(classA_fromB2, classA_beforeUnload);

		// the two distinct proxies must have resolved to the same TClass
		assertSame(classA_fromB, classA_fromB2);
		TClass classA = classA_fromB;

		// now, A.n4js should be loaded (again)
		assertEquals(List.of("B.n4js", "B2.n4js", "A.n4js"), getResourceNames(resourceSet));

		// make sure it was loaded from the index (not from the file on disk)
		N4JSResource resourceA = (N4JSResource) resourceSet.getResources().get(1);
		assertTrue(resourceA.isLoadedFromDescription());

		// make sure it was post-processed
		// (trivial in this case, because resources loaded from index are always post-processed)
		assertTrue(resourceA.isFullyProcessed());
		assertFalse(getCtor(classA).getReturnTypeRef() instanceof DeferredTypeRef);
		assertTrue(getCtor(classA).getReturnTypeRef() instanceof BoundThisTypeRef);
	}

	@Test
	public void testLoadFromSourceAfterLoadingFromIndex() throws Exception {
		// (1) load file B from index
		N4JSResource resourceB = createNewResourceSetAndLoadFileFromIndex(uriB);
		ResourceSet resourceSet = resourceB.getResourceSet();

		// (2) we want to test proxy resolution in case the target resource is *not* in the index
		// -> temporarily remove info for file A from index
		IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);
		ResourceDescriptionsData data = getResourceDescriptionsData(index);
		IResourceDescription backupDesc = data.getResourceDescription(uriA.toURI());
		data.removeDescription(uriA.toURI());

		try {
			// (3) resolve the reference to A (this should load A.n4js from the file on disk)
			TClass classB = (TClass) resourceB.getModule().getTypes().iterator().next();
			TClass classA = (TClass) classB.getSuperClassRef().getDeclaredType();
			assertFalse(classA.eIsProxy());

			// now, A.n4js should be loaded
			assertEquals(List.of("B.n4js", "A.n4js"), getResourceNames(resourceSet));
			N4JSResource resourceA = (N4JSResource) resourceSet.getResources().get(1);

			// make sure it was loaded from the file on disk (not from the index)
			assertFalse(resourceA.isLoadedFromDescription());
			assertFalse(resourceA.getScript().eIsProxy());

			// make sure it was post-processed
			assertTrue(resourceA.isFullyProcessed());
			assertFalse(getCtor(classA).getReturnTypeRef() instanceof DeferredTypeRef);
			assertTrue(getCtor(classA).getReturnTypeRef() instanceof BoundThisTypeRef);
		} finally {
			// revert index to its original state
			data.addDescription(uriA.toURI(), backupDesc);
		}
	}

	private N4JSResource createNewResourceSetAndLoadFileFromIndex(FileURI uri) {
		return createNewResourceSetAndLoadFilesFromIndex(uri).get(0);
	}

	private List<N4JSResource> createNewResourceSetAndLoadFilesFromIndex(FileURI... fileUris) {
		ResourceSet resourceSet = createAndConfigureEmptyResourceSet();
		IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(resourceSet);

		List<N4JSResource> result = new ArrayList<>();
		for (FileURI fileUri : fileUris) {
			URI uriEMF = fileUri.toURI();
			N4JSResource resource = (N4JSResource) resourceSet.createResource(uriEMF);
			IResourceDescription resDesc = index.getResourceDescription(uriEMF);
			assertNotNull(resDesc);
			assertTrue(resource.loadFromDescription(resDesc));
			TModule module = resource.getModule();
			assertNotNull(module);
			assertFalse(module.eIsProxy());
			result.add(resource);
		}

		return result;
	}

	private Type getDeclaredTypeNoResolve(ParameterizedTypeRef typeRef) {
		return (Type) typeRef.eGet(TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType(), false);
	}

	private TMethod getCtor(TClass tClass) {
		for (TMember member : tClass.getOwnedMembers()) {
			if (member instanceof TMethod && ((TMethod) member).isConstructor()) {
				return (TMethod) member;
			}
		}
		return null;
	}

	private ResourceDescriptionsData getResourceDescriptionsData(IResourceDescriptions resDescs)
			throws IllegalArgumentException, IllegalAccessException {

		assertTrue(resDescs instanceof ResourceSetBasedResourceDescriptions);
		List<Field> dFields = Arrays.asList(ResourceSetBasedResourceDescriptions.class.getDeclaredFields());
		Field field = IterableExtensions.findFirst(dFields, dfield -> dfield.getName().equals("data"));

		field.setAccessible(true);// allow access to private field
		return (ResourceDescriptionsData) field.get(resDescs);
	}

	private Iterable<String> getResourceNames(ResourceSet resourceSet) {
		return toList(map(resourceSet.getResources(), res -> res.getURI().lastSegment()));
	}

	private ResourceSet createAndConfigureEmptyResourceSet() {
		XtextResourceSet resourceSet = resourceSetProvider.get();

		Iterable<IResourceDescription> allResDescs = IterableExtensions.flatMap(concurrentIndex.entries(),
				entry -> entry.getValue().getAllResourceDescriptions());

		ResourceDescriptionsData index = new ResourceDescriptionsData(allResDescs);
		ResourceDescriptionsData.ResourceSetAdapter.installResourceDescriptionsData(resourceSet, index);
		return resourceSet;
	}
}
