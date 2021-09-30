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
package org.eclipse.n4js.scoping.builtin

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Ensures all predefined type definitions contain no parse/validation errors. This is also checked indirectly in other tests, but this
 * test better marks the initial error.
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class BuiltInTypesDefinitionTest {

	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject ValidationTestHelper validationTestHelper

	@Test
	def void validatePredefinedTypesScopeInitialized() {
		val XtextResourceSet rs = resourceSetProvider.get
		// trigger lazy computation
		BuiltInTypeScope.get(rs).allElements
		GlobalObjectScope.get(rs).allElements
		doValidatePredefinedTypes(rs, false)
	}

	@Test
	def void validatePredefinedTypesScopeNotInitialized() {
		val XtextResourceSet rs = resourceSetProvider.get
		doValidatePredefinedTypes(rs, true)
	}

	protected def doValidatePredefinedTypes(XtextResourceSet rs, boolean loadOnDemand) {
		val predefined = newArrayList(BuiltInTypeScope.FILE_NAMES)
		predefined.addAll(GlobalObjectScope.FILE_NAMES)

		for (filename: predefined) {
			val URI uri = N4Scheme.N4URI.create(filename);
			val resource = rs.getResource(uri, loadOnDemand)
			assertNotNull(uri.toString + " not in " + rs.resources.map[uri], resource)
			assertEquals("Predefined types file must contain two objects", 2, resource.contents.size);
			val EObject content = resource.contents.head;
			assertTrue("Predefined types file must contain Script, but was " + content.eClass.name, content instanceof Script);
			if (filename == "builtin_js.n4jsd") {
				// unfortunately this file contains an error we cannot get rid of:
				validationTestHelper.assertWarning(resource, N4JSPackage.Literals.N4_FIELD_DECLARATION,
					IssueCodes.CLF_NO_FINAL_INTERFACE_MEMBER, "In interfaces, only methods may be declared final.");
			} else {
				validationTestHelper.assertNoIssues(resource)
			}
		}
	}
}
