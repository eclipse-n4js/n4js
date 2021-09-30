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
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyNameKind
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TStructField
import org.eclipse.n4js.ts.types.TStructGetter
import org.eclipse.n4js.ts.types.TStructSetter
import org.eclipse.n4js.ts.types.TStructuralType
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ObjectLiteralGetterSetterTypesBuilderTest extends AbstractTypesBuilderTest {

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
		val textFileName = "ObjectLiteralGetterSetter.n4js"
		val expectedTypesNamePairs = #[
			typeof(TVariable) -> "callee"
		]

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		val expectedExportedTypeToNamePairsOnIndex = #[
			typeof(TModule) -> qualifiedNamePrefix + "ObjectLiteralGetterSetter",
			typeof(TVariable) -> "callee"
		]
		val expectedTypesCount = expectedTypesNamePairs.size
		val expectedExportedElementsCount = 1 // because of 1 variable statements
		executeTest(textFileName, expectedExportedTypeToNamePairsOnIndex, expectedTypesCount,
			expectedExportedElementsCount)
	}

	override getExpectedTypesSerialization() '''
		TModule {

		}'''

	override assertExampleTypeStructure(String phase, Resource newN4jsResource) {
		assertEquals("AST and TModule as root", 2, newN4jsResource.contents.size)

		val firstVariable = assertTVariable(phase, newN4jsResource, 0, "callee", false)
		val firstVariableType = assertTypeRef(phase, firstVariable, newN4jsResource)
		assertBuiltinTypeFragmentURI(phase, newN4jsResource, firstVariableType, "/1/@topLevelTypes.0")  // any
	}

	override assertExampleJSStructure(String phase, Resource resource) {
		assertEquals(phase + ": AST and TModule as root", 2, resource.contents.size)

		val tModule = resource.contents.get(1) as TModule
		assertEquals(phase + ": one internal type", 1, tModule.exposedInternalTypes.size)

		val script = assertScript(phase, resource, 1)

		val firstExportableElement = assertExportDeclaration(phase, script, 0)
		val firstVariableStatement = assertVariableStatement(phase, firstExportableElement, 1)

		val firstVariable = assertVariable(phase, firstVariableStatement, "callee", false)
		assertDefinedVariable(phase, firstVariable, resource, ObjectLiteral)

		val objectLiteral = firstVariable.expression as ObjectLiteral

		assertEquals(phase + ": expected ObjectLiteral property assignment count", 7, objectLiteral.propertyAssignments.size)

		assertPropertyNameValuePair(phase, objectLiteral, "a", PropertyNameKind.IDENTIFIER, "a", stringLiteralValueCalculation(phase))
		assertPropertyNameValuePair(phase, objectLiteral, "$data_property_b", PropertyNameKind.IDENTIFIER, "undefined", identifierRefValueCalculation(phase))
		assertPropertyNameValuePair(phase, objectLiteral, "c", PropertyNameKind.IDENTIFIER, "c", stringLiteralValueCalculation(phase))
		assertPropertyNameValuePair(phase, objectLiteral, "d", PropertyNameKind.IDENTIFIER, "d", stringLiteralValueCalculation(phase))
		assertPropertyNameValuePair(phase, objectLiteral, "$data_property_e", PropertyNameKind.IDENTIFIER, "undefined", identifierRefValueCalculation(phase))

		assertPropertyGetter(phase, objectLiteral, "b")
		assertPropertySetter(phase, objectLiteral, "e", "newE")

		assertEquals(phase + ": still one internal types", 1, tModule.exposedInternalTypes.size)

		assertTrue(phase + ": object literal should reference a TStructuralType", objectLiteral.definedType instanceof TStructuralType)
		val tStructType = objectLiteral.definedType as TStructuralType

		assertEquals(phase + ": TStructuralType is the one internal types", tStructType, tModule.exposedInternalTypes.head)

		assertEquals(phase + ": expected TStructuralType structural members count", 5, tStructType.ownedMembers.filter(TStructField).size)
		assertEquals(phase + ": expected TStructuralType getter count", 1, tStructType.ownedMembers.filter(TStructGetter).size)
		assertEquals(phase + ": expected TStructuralType setter count", 1, tStructType.ownedMembers.filter(TStructSetter).size)

		assertTStructField(phase, tStructType, "a", "/1/@topLevelTypes.4")
		assertTStructField(phase, tStructType, "$data_property_b", "/1/@topLevelTypes.6")
		assertTStructField(phase, tStructType, "c", "/1/@topLevelTypes.4")
		assertTStructField(phase, tStructType, "d", "/1/@topLevelTypes.4")
		assertTStructField(phase, tStructType, "$data_property_e", "/1/@topLevelTypes.6")

		assertTGetter(phase, tStructType, "b")
		assertTSetter(phase, tStructType, "e", "newE", "/1/@topLevelTypes.4")
	}
}
