/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.typesystem.utils.AllSuperTypeRefsCollector;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.xtext.ide.server.build.ProjectBuilder;
import org.eclipse.n4js.xtext.ide.server.build.WorkspaceAwareResourceSet;
import org.eclipse.n4js.xtext.ide.server.build.XWorkspaceManager;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * Tests that the {@link AllSuperTypeRefsCollector} does not collect the given type or its merged types.
 */
public class AllSuperTypeRefsCollectorTest extends AbstractIdeTest {
	@Inject
	DeclMergingHelper declMergingHelper;

	@Inject
	XWorkspaceManager workspaceManager;

	/** Enable source maps */
	@Test
	public void testEnableSourceMaps() {
		test(
				Pair.of("MyModule.d.ts",
						"export class C {\n"
								+ "	fieldC: string = \"test value\";\n"
								+ "}"
								+ "export class D extends C {\n"
								+ "	fieldD: string = \"test value\";\n"
								+ "}"
								+ "export interface C {\n"
								+ "	fieldCInterf: string = \"test value\";\n"
								+ "}"
								+ "export interface D {\n"
								+ "	fieldDInterf: string = \"test value\";\n"
								+ "}")

		);

		ResourceDescriptionsData projectIndex = concurrentIndex.getProjectIndex("test-project");
		Iterable<IEObjectDescription> modules = projectIndex.getExportedObjects(TypesPackage.eINSTANCE.getTModule(),
				QualifiedName.create("MyModule"), true);

		ProjectBuilder projectBuilder = workspaceManager.getProjectBuilder("test-project");
		WorkspaceAwareResourceSet resourceSet = projectBuilder.getResourceSet();

		assertTrue(modules.iterator().hasNext());
		IEObjectDescription moduleDescr = modules.iterator().next();
		TModule tModule = (TModule) moduleDescr.getEObjectOrProxy();
		tModule = (TModule) EcoreUtil.resolve(tModule, resourceSet);

		Iterable<? extends TExportableElement> exportableElements = tModule.getExportableElements();
		assertTrue(exportableElements.iterator().hasNext());
		Iterator<? extends TExportableElement> iterator = exportableElements.iterator();
		@SuppressWarnings("unused")
		TClass clsC = (TClass) iterator.next();
		TClass clsD = (TClass) iterator.next();

		ParameterizedTypeRef ptr = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
		ptr.setDeclaredTypeAsText("D");
		ptr.setDeclaredType(clsD);

		List<ParameterizedTypeRef> collect = AllSuperTypeRefsCollector.collect(ptr, declMergingHelper);
		assertEquals(2, collect.size());
	}

	@SafeVarargs
	final void test(Pair<String, String>... projectsModulesContents) {
		testWorkspaceManager.createTestProjectOnDisk(projectsModulesContents);
		startAndWaitForLspServer();
		assertNoErrors();
	}
}
