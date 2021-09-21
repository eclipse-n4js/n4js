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
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ClassWithMethodsTypesBuilderTest extends AbstractTypesBuilderTest {

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
		val textFileName = "ClassWithMethods.n4js"
		val expectedTypesNamePairs = #[
			TClass -> "MySuperClass",
			TClass -> "MyInterface",
			TEnum -> "Storage",
			TClass -> "MyClass"
		]

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		val expectedExportedTypeToNamePairsOnIndex = #[
			TModule -> qualifiedNamePrefix + "ClassWithMethods",
			TClass -> "MySuperClass",
			TInterface -> "MyInterface",
			TEnum -> "Storage",
			TClass -> "MyClass"
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

		val tClass = assertTClass(phase, newN4jsResource, "MyClass", 4)

		assertFirstTField(phase, tClass, newN4jsResource)

		assertFirstTMethod(phase, tClass, newN4jsResource)

		assertSecondTMethod(phase, tClass, newN4jsResource)

		assertThirdTMethod(phase, tClass, newN4jsResource)
	}

	def private assertFirstTField(String phase, TClass tClass, Resource newN4jsResource) {
		val tField = assertTField(phase, tClass, "instanceCounter")

		val type = assertTypeRefOfTField(phase, tField, newN4jsResource)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, "/1/@topLevelTypes.5")
	}

	def private assertFirstTMethod(String phase, TClass tClass, Resource newN4jsResource) {
		val tMethod = assertTMethod(phase, tClass, "myMethod1", 0)

		val type = assertReturnTypeRef(phase, tMethod, newN4jsResource)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, "//@types.1")
	}

	def private assertSecondTMethod(String phase, TClass tClass, Resource newN4jsResource) {
		val tMethod = assertTMethod(phase, tClass, "myMethod2", 1)

		val returnType = assertReturnTypeRef(phase, tMethod, newN4jsResource)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, returnType, "//@types.0")

		val parameterType = assertParameter(phase, tMethod, newN4jsResource, "input", false)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, parameterType, "//@types.0")
	}

	def private assertThirdTMethod(String phase, TClass tClass, Resource newN4jsResource) {
		val tMethod = assertTMethod(phase, tClass, "secretMethod", 2)

		val type = assertReturnTypeRef(phase, tMethod, newN4jsResource)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, "//@types.0")

		val firstParameterType = assertParameter(phase, tMethod, newN4jsResource, "element", false)

		assertTInterface(phase, newN4jsResource, firstParameterType, "MyInterface", 0)

		val secondParameterType = assertParameter(phase, tMethod, newN4jsResource, "storages", true)

		assertTEnum(phase, secondParameterType, "Storage", "LITERAL")
	}

	override assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.contents.size)

		val script = assertScript(phase, resource, 4)

		val exportableElement = assertExportDeclaration(phase, script, 3)

		val n4Class = assertN4ClassDeclaration(phase, exportableElement, "MyClass", 4)

		val type = assertFirstN4Field(phase, n4Class, resource)

		assertBuiltinTypeFragmentURI(phase, resource, type, "/1/@topLevelTypes.5")

		assertFirstN4Method(phase, n4Class, resource)

		assertSecondN4Method(phase, n4Class, resource)

		assertThirdN4Method(phase, n4Class, resource)
	}

	def private assertFirstN4Field(String phase, N4ClassDeclaration n4Class, Resource resource) {
		val field = assertN4FieldDeclaration(phase, n4Class, "instanceCounter")

		return assertTypeRef(phase, field, resource)
	}

	def private assertFirstN4Method(String phase, N4ClassDeclaration n4Class, Resource resource) {
		val n4Method = assertN4Method(phase, n4Class, "myMethod1", 0)

		val type = assertReturnTypeRef(phase, n4Method, resource)

		assertBuiltinTypeFragmentURI(phase, resource, type, "//@types.1")
	}

	def private assertSecondN4Method(String phase, N4ClassDeclaration n4Class, Resource resource) {
		val n4Method = assertN4Method(phase, n4Class, "myMethod2", 1)

		val returnType = assertReturnTypeRef(phase, n4Method, resource)

		assertBuiltinTypeFragmentURI(phase, resource, returnType, "//@types.0")

		val parameterType = assertParameter(phase, n4Method, resource, "input", false)

		assertBuiltinTypeFragmentURI(phase, resource, parameterType, "//@types.0")
	}

	def private assertThirdN4Method(String phase, N4ClassDeclaration n4Class, Resource resource) {
		val n4Method = assertN4Method(phase, n4Class, "secretMethod", 2)

		val type = assertReturnTypeRef(phase, n4Method, resource)

		assertBuiltinTypeFragmentURI(phase, resource, type, "//@types.0")

		val firstParameterType = assertParameter(phase, n4Method, resource, "element", false)

		assertTInterface(phase, resource, firstParameterType, "MyInterface", 0)

		val secondParameterType = assertParameter(phase, n4Method, resource, "storages", true)

		assertTEnum(phase, secondParameterType, "Storage", "LITERAL")
	}
}
