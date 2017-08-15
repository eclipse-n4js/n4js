/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.naming

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.N4JSValidationTestHelper
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests validation that disallows the use of dots in module filenames.
 * 
 * This test distinguishes between synthesized and regular modules. 
 * 
 * Synthesized modules are resources, that are not contained in a  N4JS project / source container 
 * environment. (modules with top-level URIs e.g. '__synthetic.n4js').
 * 
 * <p> Note: This test requires the containing project to have a manifest.n4mf with 
 * this file being contained in a declared N4JS source container. </p> 
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class NoDotsInModuleNameTest {
	@Inject
	private extension ParseHelper<Script>;
	@Inject
	private Provider<XtextResourceSet> resourceSetProvider
	@Inject
	private extension N4JSValidationTestHelper

	/**
	 * Tests that synthesized module names with dots are not allowed.
	 */
	@Test
	public def testSynthesizedModuleWithDotInName() throws Exception {
		"class A {}".parse(URI.createURI("test.some.some.n4js"), resourceSetProvider.get()).assertError(
			N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS, 0, 0,
			IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Filename", "test.some.some"));
	}

	/**
	 * Tests that regular (dot-free) synthesized module names are fine.
	 */
	@Test
	public def testSynthesizedModuleWithoutDotInName() throws Exception {
		"class A {}".parse(URI.createURI("test.n4js"), resourceSetProvider.get()).assertNoIssues;
	}

	/**
	 * Tests that otherwise dot-free synthesized N4JS-Xpect resources  (.n4js.xt) are fine. 
	 */
	@Test
	public def testSynthesizedXpectTestModule() throws Exception {
		"class A {}".parse(URI.createURI("test.n4js.xt"), resourceSetProvider.get()).assertNoIssues;
	}

	/**
	 * Tests that synthesized N4JS-Xpect resources (.n4js.xt) are validated excluding the double-extension.
	 */
	@Test
	public def testSynthesizedXpectTestModuleWithDot() throws Exception {
		"class A {}".parse(URI.createURI("a.test.n4js.xt"), resourceSetProvider.get()).assertError(
			N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS, 0, 0,
			IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Filename", "a.test"));
	}

	/**
	 * Tests that module names without dots in name nor folder hierarchy are fine.
	 */
	@Test
	public def testValidModuleName() throws Exception {
		"class A {}".parse(createNormalizedURI("src/org/eclipse/test/some/test.n4js"), resourceSetProvider.get()).
			assertNoIssues();
	}

	/**
	 * Tests that module names with dots in the name are not allowed.
	 */
	@Test
	public def testModuleWithDotInName() throws Exception {
		"class A {}".parse(createNormalizedURI("src/org/eclipse/test/some.test.n4js"), resourceSetProvider.get()).
			assertError(N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS, 0, 0,
				IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Filename", "org/eclipse/test/some.test"));
	}

	/**
	 * Tests that module names with dots in the containing folder hierarchy are invalid.
	 */
	@Test
	public def testModuleWithDotInContaingFolderName() throws Exception {
		"class A {}".parse(createNormalizedURI("src/org/eclipse/test.some/some.n4js"), resourceSetProvider.get()).
			assertError(N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS, 0, 0,
				IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Containing folders",
					"org/eclipse/test.some/some"));
	}

	/**
	 * Tests that module names with dots in the folder hierarchy as well as the filename are invalid.
	 */
	@Test
	public def testModuleWithDotInContaingFolderAndFilename() throws Exception {
		"class A {}".parse(createNormalizedURI("src/org/eclipse/test.some/some.some.n4js"), resourceSetProvider.get()).
			assertError(N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS, 0, 0,
				IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Filename and containing folders",
					"org/eclipse/test.some/some.some"));
	}

	/**
	 * Tests that XPECT module names without dots in name nor folder hierarchy are fine.
	 */
	@Test
	public def testValidXpectModuleName() throws Exception {
		"class A {}".parse(createNormalizedURI("src/org/eclipse/test/some/test.n4js.xt"), resourceSetProvider.get()).
			assertNoIssues();
	}

	/**
	 * Tests that XPECT module names with dots in the name are not allowed.
	 */
	@Test
	public def testXpectModuleWithDotInName() throws Exception {
		"class A {}".parse(createNormalizedURI("src/org/eclipse/test/some.test.n4js.xt"), resourceSetProvider.get()).
			assertError(N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS, 0, 0,
				IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Filename", "org/eclipse/test/some.test"));
	}

	/**
	 * Tests that XPECT module names with dots in the containing folder hierarchy are invalid.
	 */
	@Test
	public def testXpectModuleWithDotInContaingFolderName() throws Exception {
		"class A {}".parse(createNormalizedURI("src/org/eclipse/test.some/some.n4js.xt"), resourceSetProvider.get()).
			assertError(N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS, 0, 0,
				IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Containing folders",
					"org/eclipse/test.some/some"));
	}

	/**
	 * Tests that XPECT module names with dots in the folder hierarchy as well as the filename are invalid.
	 */
	@Test
	public def testXpectModuleWithDotInContaingFolderAndFilename() throws Exception {
		"class A {}".parse(createNormalizedURI("src/org/eclipse/test.some/some.some.n4js.xt"),
			resourceSetProvider.get()).assertError(N4JSPackage.Literals.SCRIPT,
			IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS, 0, 0,
			IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Filename and containing folders",
				"org/eclipse/test.some/some.some"));
	}

	/**
	 * Creates a normalized URI from the given URI string using the {@link XtextResourceSet} 
	 * provided by {@link #resourceSetProvider}.
	 */
	private def URI createNormalizedURI(String uri) {
		return resourceSetProvider.get.URIConverter.normalize(URI.createURI(uri));
	}

}
