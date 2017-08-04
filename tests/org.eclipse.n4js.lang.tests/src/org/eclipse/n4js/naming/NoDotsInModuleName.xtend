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
import org.eclipse.n4js.N4JSInjectorProviderWithMockProject
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
 */
@InjectWith(N4JSInjectorProviderWithMockProject)
@RunWith(XtextRunner)
class NoDotsInModuleName {
	@Inject
	private extension ParseHelper<Script>;
	@Inject
	private Provider<XtextResourceSet> resourceSetProvider
	@Inject
	private extension N4JSValidationTestHelper

	/**
	 * Tests that module names with dots are not allowed.
	 */
	@Test
	public def testModuleWithDotInName() throws Exception {
		"class A {}"
			.parse(URI.createURI("test.dot.dot.n4js"), resourceSetProvider.get())
			.assertError(N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS,
				0, 0, IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Filename", "test.dot.dot"));
	}
	
	/**
	 * Tests that regular (dot-free) module names are fine.
	 */
	@Test
	public def testModuleWithoutDotInName() throws Exception {
		"class A {}"
			.parse(URI.createURI("test.n4js"), resourceSetProvider.get())
			.assertNoIssues;
	}
	
	/**
	 * Tests that otherwise dot-free N4JS-Xpect (.n4js.xt) resources are fine. 
	 */
	@Test
	public def testXpectTestModule() throws Exception {
		"class A {}"
			.parse(URI.createURI("test.n4js.xt"), resourceSetProvider.get())
			.assertNoIssues;
	}
	
	/**
	 * Tests that N4JS-Xpect (.n4js.xt) resources are validated excluding the double-extension.
	 */
	@Test
	public def testXpectTestModuleWithDot() throws Exception {
		"class A {}"
			.parse(URI.createURI("a.test.n4js.xt"), resourceSetProvider.get())
			.assertError(N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS,
				0, 0, IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Filename", "a.test"));
	}
	
	/**
	 * Tests that module names with dots in the containing folder hierarchy are validated correctly.
	 */
	@Test
	public def testModuleWithDotInContaingFolderName() throws Exception {
		"class A {}"
			.parse(URI.createURI("test.dot/dot.n4js"), resourceSetProvider.get())
			.assertError(N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS,
				0, 0, IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Containing folders", "test.dot/dot"));
	}
	
	/**
	 * Tests that module names with dots in the folder hierarchy as well as the filename are validated correctly.
	 */
	@Test
	public def testModuleWithDotInContaingFolderAndFilename() throws Exception {
		"class A {}"
			.parse(URI.createURI("test.dot/dot.dot.n4js"), resourceSetProvider.get())
			.assertError(N4JSPackage.Literals.SCRIPT, IssueCodes.MOD_NAME_MUST_NOT_CONTAIN_DOTS,
				0, 0, IssueCodes.getMessageForMOD_NAME_MUST_NOT_CONTAIN_DOTS("Filename and containing folders", "test.dot/dot.dot"));
	}
	
}