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
package org.eclipse.n4js.tests.typesbuilder

import com.google.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class FunctionsTypesBuilderTest extends AbstractTypesBuilderTest {

	@Inject
	private extension ASTStructureAssertionExtension

	@Inject
	private extension TypesStructureAssertionExtension

	override protected enableUserDataCompare() {
		// to check the complete AST just change false to true
		false
		//true
	}

	@Test
	def test() {
		val textFileName = "Functions.n4js"
		val expectedTypesNamePairs = #[
			typeof(TFunction) -> "filterFunction",
			typeof(TFunction) -> "transformFunction"
		]

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		val expectedExportedTypeToNamePairsOnIndex = #[
			typeof(TModule) -> qualifiedNamePrefix + "Functions",
			typeof(TFunction) -> "filterFunction",
			typeof(TFunction) -> "transformFunction"
		]
		val expectedTypesCount = expectedTypesNamePairs.size
		val expectedExportedElementsCount = expectedExportedTypeToNamePairsOnIndex.size
		executeTest(textFileName, expectedExportedTypeToNamePairsOnIndex, expectedTypesCount,
			expectedExportedElementsCount)
	}

	override getExpectedTypesSerialization() '''
		TModule {
		}'''

	override assertExampleTypeStructure(String phase, Resource newN4jsResource) {
		assertEquals("AST and TModule as root", 2, newN4jsResource.contents.size)

		assertFirstTFunction(phase, newN4jsResource)

		assertSecondTFunction(phase, newN4jsResource)
	}

	def assertFirstTFunction(String phase, Resource newN4jsResource) {
		val tFunction = assertTFunction(phase, newN4jsResource, "filterFunction", 2)

		assertTypeVariables(phase, tFunction, newN4jsResource, "T","U")

		val type = assertReturnTypeRef(phase, tFunction, newN4jsResource)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, "//@types.3")

		val firstParameterType = assertParameter(phase, tFunction, newN4jsResource, "input", false)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, firstParameterType, "//@types.3")

		val secondParameterType = assertParameter(phase, tFunction, newN4jsResource, "hint", false)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, secondParameterType, "//@types.3")
	}

	def assertSecondTFunction(String phase, Resource newN4jsResource) {
		val tFunction = assertTFunction(phase, newN4jsResource, "transformFunction", 0)

		assertTypeVariables(phase, tFunction, newN4jsResource)
		assertNotNull("inferred return type expected", tFunction.returnTypeRef)
	}

	override assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.contents.size)

		val script = assertScript(phase, resource, 2)

		val exportableElement = assertExportDeclaration(phase, script, 0)

		assertFirstFunction(phase, resource, exportableElement)
	}

	def assertFirstFunction(String phase, Resource resource, ExportableElement exportableElement) {
		val n4Function = assertN4Function(phase, resource, exportableElement, "filterFunction", 2)

		assertTypeVariables(phase, n4Function, resource, "T","U")

		val type = assertReturnTypeRef(phase, n4Function, resource)

		assertBuiltinTypeFragmentURI(phase, resource, type, "//@types.3")

		val firstParameterType = assertParameter(phase, n4Function, resource, "input", false)

		assertBuiltinTypeFragmentURI(phase, resource, firstParameterType, "//@types.3")

		val secondParameterType = assertParameter(phase, n4Function, resource, "hint", false)

		assertBuiltinTypeFragmentURI(phase, resource, secondParameterType, "//@types.3")
	}

	def assertSecondFunction(String phase, Resource resource, ExportableElement exportableElement) {
		val n4Function = assertN4Function(phase, resource, exportableElement, "transformFunction", 0)

		assertTypeVariables(phase, n4Function, resource)

		Assert.assertNull("no return type expected", n4Function.declaredReturnTypeRef)
	}
}
