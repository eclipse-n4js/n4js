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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSInjectorProviderWithIndex;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.containers.FlatResourceSetBasedAllContainersState;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.util.OnChangeEvictingCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @see N4JSScopeProvider
 */
@InjectWith(N4JSInjectorProviderWithIndex.class)
@RunWith(XtextRunner.class)
public class CircularReferencesScopingTest implements N4Scheme {

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;
	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	XtextResourceSet rs;
	IResourceDescriptions resourceDescriptions;
	URI brotherURI;
	URI sisterURI;
	URI childURI;

	@Before
	public void setUp() {
		rs = resourceSetProvider.get();

		brotherURI = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Brother.n4js"));
		sisterURI = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Sister.n4js"));
		childURI = rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Child.n4js"));

		rs.getResource(brotherURI, true).getContents();
		rs.getResource(sisterURI, true).getContents();
		rs.getResource(childURI, true).getContents();

		EcoreUtil.resolveAll(rs);

		rs.eAdapters().add(new EnumerableAllContainersState(rs, List.of(brotherURI, sisterURI, childURI)));

		resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(rs);
		for (IResourceDescription rd : resourceDescriptions.getAllResourceDescriptions()) {
			for (IEObjectDescription od : rd.getExportedObjects()) {
				od.getUserDataKeys();
			}
		}
	}

	@After
	public void tearDown() {
		rs = null;
		resourceDescriptions = null;
		brotherURI = null;
		sisterURI = null;
		childURI = null;
	}

	@Test
	public void testCircularDependencies() {
		assertEquals(3, size(resourceDescriptions.getExportedObjectsByType(TypesPackage.Literals.TMODULE)));

		// unload the resources in the resource set but don't clear the cached information in the index
		for (var i = 0; i < rs.getResources().size(); i++) {
			XtextResource res = (XtextResource) rs.getResources().get(i);
			((OnChangeEvictingCache) res.getCache()).execWithoutCacheClear(res, (param) -> {
				param.unload();
				return null;
			});
		}
		// clear the resource set but again keep the cached index data
		List<Resource> resources = new ArrayList<>(rs.getResources());
		for (Resource res : resources) {
			res.eSetDeliver(false);
		}
		rs.getResources().clear();
		for (Resource res : resources) {
			res.eSetDeliver(true);
		}

		assertEquals(3, size(resourceDescriptions.getExportedObjectsByType(TypesPackage.Literals.TMODULE)));
		assertEquals(1, size(resourceDescriptions.getExportedObjects(TypesPackage.Literals.TMODULE,
				QualifiedName.create("org", "eclipse", "n4js", "tests", "scoping", "Sister"), false)));
		Resource brother = rs.getResource(brotherURI, true);
		assertEquals(1, rs.getResources().size());
		EcoreUtil.resolveAll(brother);
		assertEquals(brother.getErrors().toString(), 0, brother.getErrors().size());
		assertEquals(3,
				size(filter(rs.getResources(), res -> !N4Scheme.isResourceWithN4Scheme(res))));

		N4JSResource sister = (N4JSResource) rs.getResource(sisterURI, false);
		N4JSResource child = (N4JSResource) rs.getResource(childURI, false);
		assertNotNull(sister);
		assertNotNull(child);
		if (!CanLoadFromDescriptionHelper.isLoadFromSourceDeactivated()) {
			// due to cyclic dependency, sister must be loaded from disk (not index)
			assertTrue(sister.isLoaded());
			assertFalse(sister.getScript().eIsProxy());
		}
		assertFalse(child.isLoaded());// no cyclic dependency, so child can be loaded from index (not from disk)
		assertTrue(child.getScript().eIsProxy());
		assertEquals(2, sister.getContents().size());
		assertEquals(2, child.getContents().size());

		sister.getContents().get(0);// use an iterator here internally to check for concurrent modifications on the
									// contents list
		assertEquals(2, sister.getContents().size());
		TModule exportedScript = (TModule) last(sister.getContents());
		TClass exportedType = (TClass) exportedScript.getTypes().get(0);
		TMethod exportedMethod = (TMethod) exportedType.getOwnedMembers().get(0);
		TClass brotherType = (TClass) ((ParameterizedTypeRef) exportedMethod.getReturnTypeRef()).getDeclaredType();
		Type originalBrotherType = ((TModule) last(brother.getContents())).getTypes().get(0);
		assertSame(originalBrotherType, brotherType);

		for (Resource res : rs.getResources()) {
			if (!N4Scheme.isResourceWithN4Scheme(res)) {
				assertEquals(2, res.getContents().size());
			}
		}
	}

	@Test
	public void testLinksProperyResolved() {
		EcoreUtil.resolveAll(rs);

		List<Resource> noN4Ress = toList(filter(rs.getResources(), res -> !N4Scheme.isResourceWithN4Scheme(res)));
		assertEquals(Strings.join("\n", res -> res.getURI().toString(), noN4Ress), 3, noN4Ress.size());

		for (Resource res : noN4Ress) {
			assertEquals(res.getErrors().toString(), 0, res.getErrors().size());
			assertEquals(res.getContents().toString(), 2, res.getContents().size());
		}
		Resource sister = rs.getResource(sisterURI, false);
		Script script = (Script) sister.getContents().get(0);
		ParameterizedPropertyAccessExpression sisterGetBrotherGetChild = (ParameterizedPropertyAccessExpression) ((VariableStatement) last(
				script.getScriptElements())).getVarDecl().get(0).getExpression();
		IdentifiableElement member = sisterGetBrotherGetChild.getProperty();
		assertEquals("Brother", ((TClass) member.eContainer()).getName());
	}

	/**
	 * A container state implementation that works with a specific resource set but does not reflect the resource set's
	 * content 1:1 but uses a dedicated list of available resources instead. this allows to clear the resource set in a
	 * test but still have the original resources available in the index.
	 */
	static class EnumerableAllContainersState extends FlatResourceSetBasedAllContainersState {

		Collection<URI> uris;

		EnumerableAllContainersState(ResourceSet resourceSet, Collection<URI> uris) {
			super(resourceSet);
			this.uris = uris;
		}

		@Override
		public boolean isEmpty(String containerHandle) {
			return false;
		}

		@Override
		public Collection<URI> getContainedURIs(String containerHandle) {
			return uris;
		}

	}
}
