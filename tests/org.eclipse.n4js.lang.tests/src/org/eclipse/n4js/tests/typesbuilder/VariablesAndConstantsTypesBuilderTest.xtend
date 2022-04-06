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
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.Literal
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class VariablesAndConstantsTypesBuilderTest extends AbstractTypesBuilderTest {

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
		val textFileName = "VariablesAndConstants.n4js"
		val expectedTypesNamePairs = #[
			typeof(TVariable) -> "a",
			typeof(TVariable) -> "b",
			typeof(TVariable) -> "CONST1",
			typeof(TVariable) -> "CONST2"
		]

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		val expectedExportedTypeToNamePairsOnIndex = #[
			typeof(TModule) -> qualifiedNamePrefix + "VariablesAndConstants",
			typeof(TVariable) -> "a",
			typeof(TVariable) -> "b",
			typeof(TVariable) -> "CONST1",
			typeof(TVariable) -> "CONST2"
		]
		val expectedTypesCount = expectedTypesNamePairs.size
		val expectedExportedElementsCount = 3 // because of 3 variable statements
		executeTest(textFileName, expectedExportedTypeToNamePairsOnIndex, expectedTypesCount,
			expectedExportedElementsCount)
	}

	override getExpectedTypesSerialization() '''
		TModule {

		}'''

	override assertExampleTypeStructure(String phase, Resource newN4jsResource) {
		assertEquals("AST and TModule as root", 2, newN4jsResource.contents.size)

		val firstVariable = assertTVariable(phase, newN4jsResource, 0, "a", false)
		val firstVariableType = assertTypeRef(phase, firstVariable, newN4jsResource)
		assertBuiltinTypeFragmentURI(phase, newN4jsResource, firstVariableType, "/1/@types.8")  // int

		val secondVariable = assertTVariable(phase, newN4jsResource, 1, "b", false)
		val secondVariableType = assertTypeRef(phase, secondVariable, newN4jsResource)
		assertBuiltinTypeFragmentURI(phase, newN4jsResource, secondVariableType, "/1/@types.4")

		val thirdVariable = assertTVariable(phase, newN4jsResource, 2, "CONST1", true)
		assertTrue(thirdVariable.typeRef instanceof NumericLiteralTypeRef)
		assertEquals("6", thirdVariable.typeRef.typeRefAsString)

		val fourthVariable = assertTVariable(phase, newN4jsResource, 3, "CONST2", true)
		val fourthVariableType = assertTypeRef(phase, fourthVariable, newN4jsResource)
		assertBuiltinTypeFragmentURI(phase, newN4jsResource, fourthVariableType, "/1/@types.3")

		Assert.assertEquals("Should have only 4 exported variables as the fifth one is not marked as exported",
			4, (newN4jsResource.contents.get(1) as TModule).exportedVariables.size
		)
	}

	override assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.contents.size)

		val script = assertScript(phase, resource, 3)

		val firstExportableElement = assertExportDeclaration(phase, script, 0)
		val firstVariableStatement = assertVariableStatement(phase, firstExportableElement, 2)

		val firstVariable = assertVariable(phase, firstVariableStatement, "a", false)
		assertDefinedVariable(phase, firstVariable, resource, Literal)

		val secondVariable = assertVariable(phase, firstVariableStatement, "b", false)
		assertDefinedVariable(phase, secondVariable, resource, null)

		val secondExportableElement = assertExportDeclaration(phase, script, 1)
		val secondVariableStatement = assertVariableStatement(phase, secondExportableElement, 2)

		val thirdVariable = assertVariable(phase, secondVariableStatement, "CONST1", true)
		assertDefinedVariable(phase, thirdVariable, resource, Literal)

		val fourthVariable = assertVariable(phase, secondVariableStatement, "CONST2", true)
		assertDefinedVariable(phase, fourthVariable, resource, Literal)

		val thirdExportableElement = script.scriptElements.get(2) as VariableStatement
		val thirdVariableStatement = assertVariableStatement(phase, thirdExportableElement, 1)

		val fifthVariable = assertVariable(phase, thirdVariableStatement, "c", false)
		assertDefinedVariable(phase, fifthVariable, resource, FunctionExpression)
	}
}
