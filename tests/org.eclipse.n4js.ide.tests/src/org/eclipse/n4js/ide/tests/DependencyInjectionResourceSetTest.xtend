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
package org.eclipse.n4js.ide.tests

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl.ResourceLocator
import org.eclipse.n4js.ide.tests.server.AbstractIdeTest
import org.eclipse.n4js.ide.xtext.server.build.WorkspaceAwareResourceSet
import org.eclipse.n4js.ts.scoping.builtin.BuiltInSchemeResourceLocator
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme
import org.eclipse.n4js.ts.scoping.builtin.ResourceSetWithBuiltInSchemeProvider
import org.eclipse.n4js.utils.ReflectionUtils
import org.eclipse.xtext.resource.SynchronizedXtextResourceSet
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.Test

import static org.junit.Assert.*

/**
 * Ensures that EMF resource sets are created and configured correctly when using
 * various ways of obtaining them via dependency injection.
 */
class DependencyInjectionResourceSetTest extends AbstractIdeTest {

	@Inject private ResourceSetWithBuiltInSchemeProvider resourceSetWithBuiltInSchemeProvider;

	@Inject private ResourceSet rs1;
	@Inject private XtextResourceSet rs2;
	@Inject private SynchronizedXtextResourceSet rs3;
	@Inject private WorkspaceAwareResourceSet rs4;

	@Inject private Provider<ResourceSet> rsp5;
	@Inject private Provider<XtextResourceSet> rsp6;
	@Inject private Provider<SynchronizedXtextResourceSet> rsp7;
	@Inject private Provider<WorkspaceAwareResourceSet> rsp8;

	@Test
	def void testInjectionOfResourceSets() {
		// This test is not actually interested in the LSP server. But built-in types are loaded lazily and
		// we want to test a state when everything is completely set up without faking anything; therefore
		// we here start an LSP server and compile a resource with a reference to a built-in type:
		testWorkspaceManager.createTestProjectOnDisk("A" -> '''String;''');
		startAndWaitForLspServer(); // runs initial build on A.n4js
		assertNoIssues();

		val rs5 = rsp5.get();
		val rs6 = rsp6.get();
		val rs7 = rsp7.get();
		val rs8 = rsp8.get();

		// assert correct types
		assertType(SynchronizedXtextResourceSet, rs1);
		assertType(SynchronizedXtextResourceSet, rs2);
		assertType(SynchronizedXtextResourceSet, rs3);
		assertType(WorkspaceAwareResourceSet, rs4);
		assertType(SynchronizedXtextResourceSet, rs5);
		assertType(SynchronizedXtextResourceSet, rs6);
		assertType(SynchronizedXtextResourceSet, rs7);
		assertType(WorkspaceAwareResourceSet, rs8);

		// assert correct configuration via ConfiguredResourceSetProvider and ConfiguredWorkspaceAwareResourceSetProvider
		assertTrue(rs1.isBuiltInSchemeAware);
		assertTrue(rs2.isBuiltInSchemeAware);
		assertTrue(rs3.isBuiltInSchemeAware);
		assertTrue(rs4.isBuiltInSchemeAware);
		assertTrue(rs5.isBuiltInSchemeAware);
		assertTrue(rs6.isBuiltInSchemeAware);
		assertTrue(rs7.isBuiltInSchemeAware);
		assertTrue(rs8.isBuiltInSchemeAware);

		// assert all resource sets are wired to the same, global resource set for built-in types
		val correctBuiltInSchemeResourceSet = resourceSetWithBuiltInSchemeProvider.getResourceSet();
		assertSame(correctBuiltInSchemeResourceSet, rs1.getBuiltInSchemeResourceSet);
		assertSame(correctBuiltInSchemeResourceSet, rs2.getBuiltInSchemeResourceSet);
		assertSame(correctBuiltInSchemeResourceSet, rs3.getBuiltInSchemeResourceSet);
		assertSame(correctBuiltInSchemeResourceSet, rs4.getBuiltInSchemeResourceSet);
		assertSame(correctBuiltInSchemeResourceSet, rs5.getBuiltInSchemeResourceSet);
		assertSame(correctBuiltInSchemeResourceSet, rs6.getBuiltInSchemeResourceSet);
		assertSame(correctBuiltInSchemeResourceSet, rs7.getBuiltInSchemeResourceSet);
		assertSame(correctBuiltInSchemeResourceSet, rs8.getBuiltInSchemeResourceSet);
	}

	def private boolean isBuiltInSchemeAware(ResourceSet rs) {
		var l = ReflectionUtils.getFieldValue(ResourceSetImpl, rs, "resourceLocator");
		while (l !== null) {
			if (l instanceof BuiltInSchemeResourceLocator) {
				return true;
			}
			l = ReflectionUtils.getFieldValue(ResourceLocator, l, "previousResourceLocator");
		}
		return false;
	}

	def private ResourceSet getBuiltInSchemeResourceSet(ResourceSet rs) {
		val uri = URI.createURI(N4Scheme.SCHEME + ":/builtin_n4.n4ts");
		val res = rs.getResource(uri, false);
		return res?.resourceSet;
	}

	def private void assertType(Class<?> expectedType, Object obj) {
		assertSame(
			"expected obj to be of type " + expectedType.simpleName + ", but was of type " + obj.getClass.simpleName,
			expectedType, obj.getClass);
	}
}
