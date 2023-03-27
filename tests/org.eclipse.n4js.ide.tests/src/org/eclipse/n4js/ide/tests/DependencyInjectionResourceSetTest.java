/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.scoping.builtin.BuiltInSchemeResourceLocator;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.scoping.builtin.ResourceSetWithBuiltInSchemeProvider;
import org.eclipse.n4js.utils.ReflectionUtils;
import org.eclipse.n4js.xtext.ide.server.build.WorkspaceAwareResourceSet;
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Ensures that EMF resource sets are created and configured correctly when using various ways of obtaining them via
 * dependency injection.
 */
@SuppressWarnings("javadoc")
public class DependencyInjectionResourceSetTest extends AbstractIdeTest {

	@Inject
	private ResourceSetWithBuiltInSchemeProvider resourceSetWithBuiltInSchemeProvider;

	@Inject
	private ResourceSet rs1;
	@Inject
	private XtextResourceSet rs2;
	@Inject
	private SynchronizedXtextResourceSet rs3;
	@Inject
	private WorkspaceAwareResourceSet rs4;

	@Inject
	private Provider<ResourceSet> rsp5;
	@Inject
	private Provider<XtextResourceSet> rsp6;
	@Inject
	private Provider<SynchronizedXtextResourceSet> rsp7;
	@Inject
	private Provider<WorkspaceAwareResourceSet> rsp8;

	@Test
	public void testInjectionOfResourceSets() {
		// This test is not actually interested in the LSP server. But built-in types are loaded lazily and
		// we want to test a state when everything is completely set up without faking anything; therefore
		// we here start an LSP server and compile a resource with a reference to a built-in type:
		testWorkspaceManager.createTestProjectOnDisk(Pair.of("A", "String;"));
		startAndWaitForLspServer(); // runs initial build on A.n4js
		assertNoIssues();

		ResourceSet rs5 = rsp5.get();
		ResourceSet rs6 = rsp6.get();
		ResourceSet rs7 = rsp7.get();
		ResourceSet rs8 = rsp8.get();

		// assert correct types
		assertType(SynchronizedXtextResourceSet.class, rs1);
		assertType(SynchronizedXtextResourceSet.class, rs2);
		assertType(SynchronizedXtextResourceSet.class, rs3);
		assertType(WorkspaceAwareResourceSet.class, rs4);
		assertType(SynchronizedXtextResourceSet.class, rs5);
		assertType(SynchronizedXtextResourceSet.class, rs6);
		assertType(SynchronizedXtextResourceSet.class, rs7);
		assertType(WorkspaceAwareResourceSet.class, rs8);

		// assert correct configuration via ConfiguredResourceSetProvider and
		// ConfiguredWorkspaceAwareResourceSetProvider
		assertTrue(isBuiltInSchemeAware(rs1));
		assertTrue(isBuiltInSchemeAware(rs2));
		assertTrue(isBuiltInSchemeAware(rs3));
		assertTrue(isBuiltInSchemeAware(rs4));
		assertTrue(isBuiltInSchemeAware(rs5));
		assertTrue(isBuiltInSchemeAware(rs6));
		assertTrue(isBuiltInSchemeAware(rs7));
		assertTrue(isBuiltInSchemeAware(rs8));

		// assert all resource sets are wired to the same, global resource set for built-in types
		ResourceSet correctBuiltInSchemeResourceSet = resourceSetWithBuiltInSchemeProvider.getResourceSet();
		assertSame(correctBuiltInSchemeResourceSet, getBuiltInSchemeResourceSet(rs1));
		assertSame(correctBuiltInSchemeResourceSet, getBuiltInSchemeResourceSet(rs2));
		assertSame(correctBuiltInSchemeResourceSet, getBuiltInSchemeResourceSet(rs3));
		assertSame(correctBuiltInSchemeResourceSet, getBuiltInSchemeResourceSet(rs4));
		assertSame(correctBuiltInSchemeResourceSet, getBuiltInSchemeResourceSet(rs5));
		assertSame(correctBuiltInSchemeResourceSet, getBuiltInSchemeResourceSet(rs6));
		assertSame(correctBuiltInSchemeResourceSet, getBuiltInSchemeResourceSet(rs7));
		assertSame(correctBuiltInSchemeResourceSet, getBuiltInSchemeResourceSet(rs8));
	}

	private boolean isBuiltInSchemeAware(ResourceSet rs) {
		var l = ReflectionUtils.getFieldValue(ResourceSetImpl.class, rs, "resourceLocator");
		while (l != null) {
			if (l instanceof BuiltInSchemeResourceLocator) {
				return true;
			}
			l = ReflectionUtils.getFieldValue(ResourceLocator.class, l, "previousResourceLocator");
		}
		return false;
	}

	private ResourceSet getBuiltInSchemeResourceSet(ResourceSet rs) {
		URI uri = URI.createURI(N4Scheme.SCHEME + ":/builtin_n4.n4jsd");
		Resource res = rs.getResource(uri, false);
		return res == null ? null : res.getResourceSet();
	}

	private void assertType(Class<?> expectedType, Object obj) {
		assertSame(
				"expected obj to be of type " + expectedType.getSimpleName() + ", but was of type "
						+ obj.getClass().getSimpleName(),
				expectedType, obj.getClass());
	}
}
