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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.scoping.accessModifiers.FunctionVisibilityChecker
import org.eclipse.n4js.scoping.accessModifiers.TypeVisibilityChecker
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.ts.types.Type

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class TypeVisibilityCheckerTest {

	static val SAME_VENDOR = 'V'
	static val SAME_PROJECT = 'P'
	static val OTHER_VENDOR = 'V2'
	static val OTHER_PROJECT = 'P2'

	@Inject extension ParseHelper<Script>

	@Inject
	TypeVisibilityChecker typeVisibilityChecker
	
	@Inject
	FunctionVisibilityChecker functionVisibilityChecker

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	private def withVendorAndProject(Script script, String vendorID, String projectID) {
		script.eResource.contents.get(1) as TModule => [
			it.projectID = projectID
			it.vendorID = vendorID
		]
		return script
	}

	def void assertIsVisible(CharSequence type, String myVendor, String myProject) {
		doAssertVisibility(type, myVendor, myProject, true)
	}

	def void assertIsNotVisible(CharSequence type, String myVendor, String myProject) {
		doAssertVisibility(type, myVendor, myProject, false)
	}

	private def void doAssertVisibility(CharSequence type, String myVendor, String myProject, boolean expectation) {
		val rs = resourceSetProvider.get
		val script = type.parse(URI.createURI('''a.n4js'''), rs).withVendorAndProject(SAME_VENDOR, SAME_PROJECT)
		val parsedType = (script.eResource.contents.last as TModule).typesAndFunctions.head
		val myScript = '''class A{}'''.parse(URI.createURI('''b.n4js?«myVendor»|«myProject»'''), rs).withVendorAndProject(myVendor, myProject)

		if (parsedType instanceof Type) {
			Assert.assertEquals(expectation, typeVisibilityChecker.isVisible(myScript.eResource, parsedType).visibility)
		} else if (parsedType instanceof TFunction) {
			Assert.assertEquals(expectation, functionVisibilityChecker.isVisible(myScript.eResource, parsedType).visibility)
		}
	}

	/**
	 * Public API type visible from same project / same vendor
	 */
	@Test
	def void testPublicType_01() {
		'''export public class X {}'''.assertIsVisible(SAME_VENDOR, SAME_PROJECT)
	}

	/**
	 * Public API type visible from diff project / same vendor
	 */
	@Test
	def void testPublicType_02() {
		'''export public class X {}'''.assertIsVisible(SAME_VENDOR, OTHER_PROJECT)
	}

	/**
	 * Public API type visible from diff project / diff vendor
	 */
	@Test
	def void testPublicType_03() {
		'''export public class X {}'''.assertIsVisible(OTHER_VENDOR, OTHER_PROJECT)
	}

	/**
	 * Error condition: Public API type visible from same project / diff vendor
	 */
	@Test
	def void testPublicType_04() {
		'''export public class X {}'''.assertIsVisible(OTHER_VENDOR, SAME_PROJECT)
	}

	/**
	 * Public type visible from same project / same vendor
	 */
	@Test
	def void testPublicInternalType_01() {
		'''export @Internal public class X {}'''.assertIsVisible(SAME_VENDOR, SAME_PROJECT)
	}

	/**
	 * Public type visible from diff project / same vendor
	 */
	@Test
	def void testPublicInternalType_02() {
		'''export @Internal public class X {}'''.assertIsVisible(SAME_VENDOR, OTHER_PROJECT)
	}

	/**
	 * Public type not visible from diff project / diff vendor
	 */
	@Test
	def void testPublicInternalType_03() {
		'''export @Internal public class X {}'''.assertIsNotVisible(OTHER_VENDOR, OTHER_PROJECT)
	}

	/**
	 * Error condition: Public type not visible from same project / diff vendor
	 */
	@Test
	def void testPublicInternalType_04() {
		'''export @Internal public class X {}'''.assertIsNotVisible(OTHER_VENDOR, SAME_PROJECT)
	}

	/**
	 * Error condition: Project API type visible from same project / same vendor
	 */
	@Test
	def void testExportedProjectType_01() {
		'''export project class X {}'''.assertIsVisible(SAME_VENDOR, SAME_PROJECT)
	}

	/**
	 * Error condition: Project API type not visible from diff project / same vendor
	 */
	@Test
	def void testExportedProjectType_02() {
		'''export project class X {}'''.assertIsNotVisible(SAME_VENDOR, OTHER_PROJECT)
	}

	/**
	 * Error condition: Project API type not visible from diff project / diff vendor
	 */
	@Test
	def void testExportedProjectType_03() {
		'''export project class X {}'''.assertIsNotVisible(OTHER_VENDOR, OTHER_PROJECT)
	}

	/**
	 * Error condition: Project API type not visible from same project / diff vendor
	 */
	@Test
	def void testExportedProjectType_04() {
		'''export project class X {}'''.assertIsNotVisible(OTHER_VENDOR, SAME_PROJECT)
	}
}
