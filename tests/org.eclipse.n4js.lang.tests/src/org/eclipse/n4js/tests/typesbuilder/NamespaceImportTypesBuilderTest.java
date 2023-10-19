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
package org.eclipse.n4js.tests.typesbuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class NamespaceImportTypesBuilderTest extends AbstractTypesBuilderTest {

	@Override
	protected boolean enableUserDataCompare() {
		// to check the complete AST just change false to true
		return false;
		// true
	}

	@Test
	public void test() {
		String textFileName = "NamespaceImports.n4js";
		List<?> expectedTypesNamePairs = Collections.emptyList();

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"

		List<?> expectedExportedTypeToNamePairsOnIndex = Collections.emptyList();
		int expectedTypesCount = expectedTypesNamePairs.size();
		int expectedExportedElementsCount = expectedExportedTypeToNamePairsOnIndex.size();
		try {
			executeTestWithExternals(textFileName, List.of("NamespaceImportsMod.n4jsd", "NamespaceImportsPlain.js"),
					expectedExportedTypeToNamePairsOnIndex, expectedTypesCount,
					expectedExportedElementsCount);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@SuppressWarnings("unused")
	protected void executeTestWithExternals(String testFileName, List<String> externalFiles,
			List<?> expectedExportedTypeToNamePairs,
			int expectedTypesCount, int expectedTypesOnIndexCount) throws Exception {

		XtextResourceSet rs = resourceSetProvider.get();
		for (String externalFile : externalFiles) {
			Resource testResource = rs.createResource(URI.createURI("src/" + getPath() + "/" + externalFile));
			testResource.load(Collections.emptyMap());
			super._n4JSResourceExtensions.resolve(testResource);
			testResource.getAllContents();
		}

		// load original resource
		Resource testResource = rs.createResource(URI.createURI("src/" + getPath() + "/" + testFileName));
		testResource.load(Collections.emptyMap());

		super._n4JSResourceExtensions.resolve(testResource);
		super._resourceAssertionsExtensions.assertResourceHasNoErrors("x", testResource);
		checkTModule((TModule) testResource.getContents().get(1));
	}

	private void checkTModule(TModule module) {
		checkStaticNSVType((ModuleNamespaceVirtualType) module.getInternalTypes().get(0));
		checkDynamicNSVType((ModuleNamespaceVirtualType) module.getInternalTypes().get(1));
	}

	void checkDynamicNSVType(ModuleNamespaceVirtualType type) {
		assertTrue(type.getName() + " is expected to be dynamic", type.isDeclaredDynamic());
	}

	void checkStaticNSVType(ModuleNamespaceVirtualType type) {
		assertFalse(type.getName() + " is expected to be non-dynamic", type.isDeclaredDynamic());
	}

	@Override
	public CharSequence getExpectedTypesSerialization() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}

	@Override
	public void assertExampleTypeStructure(String phase, Resource resource) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}

	@Override
	public void assertExampleJSStructure(String phase, Resource resource) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}
}
