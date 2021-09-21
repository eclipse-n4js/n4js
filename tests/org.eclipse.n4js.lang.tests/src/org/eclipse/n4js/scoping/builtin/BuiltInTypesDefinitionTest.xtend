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
import java.util.List
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme
import org.eclipse.n4js.ts.types.TypeDefs
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.xtext.validation.Issue
import org.junit.Test
import org.junit.runner.RunWith

import static com.google.common.collect.Iterables.isEmpty
import static org.junit.Assert.*

/**
 * Ensures all predefined type definitions contain no errors. This is also checked indirectly in other tests, but this
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
			val numOfRootElems = if (resource.URI.lastSegment.endsWith(N4JSGlobals.N4TS_FILE_EXTENSION)) 1 else 2;
			assertEquals("Predefined types file must contain a single object", numOfRootElems, resource.contents.size);
			val EObject content = resource.contents.head;
			if (resource.URI.lastSegment.endsWith(N4JSGlobals.N4TS_FILE_EXTENSION)) {
				assertTrue("Predefined types file must contain TypeDefs, but was " + content.eClass.name, content instanceof TypeDefs);
			} else {
				assertTrue("Predefined types file must contain Script, but was " + content.eClass.name, content instanceof Script);
			}
			validationTestHelper.validate(content)
			assertNoIssues(content)
		}
	}

	/**
	 * @since 2.8
	 */
	def assertNoIssues(EObject object) {
		val resource = object.eResource
		val List<Issue> validate = validationTestHelper.validate(resource);
		if (!isEmpty(validate)) {
			fail("Expected no issues in " + resource.URI + ":\n    "+validate.map[it.message + " on line " + it.lineNumber].join("\n    "));
		}
	}

}
