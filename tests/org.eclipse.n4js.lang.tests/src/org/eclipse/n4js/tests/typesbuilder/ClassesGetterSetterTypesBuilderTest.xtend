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
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ClassesGetterSetterTypesBuilderTest extends AbstractTypesBuilderTest {

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
		val textFileName = "ClassesGetterSetter.n4js"
		val expectedTypesNamePairs = #[
			typeof(TClass) -> "Callee"
		]

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		val expectedExportedTypeToNamePairsOnIndex = #[
			typeof(TModule) -> qualifiedNamePrefix + "ClassesGetterSetter",
			typeof(TClass) -> "Callee"
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

		val tClass = assertTClass(phase, newN4jsResource, "Callee", 8)

		assertTField(phase, tClass, newN4jsResource, "a", "//@types.3")
		assertTField(phase, tClass, newN4jsResource, "data_property_b", "//@types.3")
		assertTField(phase, tClass, newN4jsResource, "data_property_c", "//@types.3")
		assertTField(phase, tClass, newN4jsResource, "data_property_d", "//@types.3")

		assertTGetter(phase, tClass, newN4jsResource, "b", "//@types.3")
		assertTGetter(phase, tClass, newN4jsResource, "d", "//@types.3")


		assertTSetter(phase, tClass, newN4jsResource, "c", "newC", "//@types.3")
		assertTSetter(phase, tClass, newN4jsResource, "d", "newD", "//@types.3")
	}

	def private assertTField(String phase, TClass tClass, Resource newN4jsResource, String expectedName, String expectedTypeFragmentURI) {
		val tField = assertTField(phase, tClass, expectedName)

		val type = assertTypeRefOfTField(phase, tField, newN4jsResource)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, expectedTypeFragmentURI)
	}

	def private assertTGetter(String phase, TClass tClass, Resource newN4jsResource, String expectedName, String expectedReturnTypeFragmentURI) {
		val tGetter = assertTGetter(phase, tClass, expectedName)

		val type = assertReturnTypeRef(phase, tGetter, newN4jsResource)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, type, expectedReturnTypeFragmentURI)
	}


	def private assertTSetter(String phase, TClass tClass, Resource newN4jsResource, String expectedName, String expectedParameterName, String expectedReturnTypeFragmentURI) {
		val tSetter = assertTSetter(phase, tClass, expectedName)

		val parameterType = assertParameter(phase, tSetter, newN4jsResource, expectedParameterName, false)

		assertBuiltinTypeFragmentURI(phase, newN4jsResource, parameterType, expectedReturnTypeFragmentURI)
	}

	override assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.contents.size)

		val script = assertScript(phase, resource, 1)

		val exportableElement = assertExportDeclaration(phase, script, 0)
		val n4Class = assertN4ClassDeclaration(phase, exportableElement, "Callee", 8)

		assertN4Field(phase, n4Class, resource, "a", "//@types.3")
		assertN4Field(phase, n4Class, resource, "data_property_b", "//@types.3")
		assertN4Field(phase, n4Class, resource, "data_property_c", "//@types.3")
		assertN4Field(phase, n4Class, resource, "data_property_d", "//@types.3")

		assertN4Getter(phase, n4Class, resource, "b", "//@types.3")
		assertN4Getter(phase, n4Class, resource, "d", "//@types.3")

		assertN4Setter(phase, n4Class, resource, "c", "newC", "//@types.3")
		assertN4Setter(phase, n4Class, resource, "d", "newD", "//@types.3")
	}

	def private void assertN4Field(String phase, N4ClassDeclaration n4Class, Resource resource, String expectedName, String expectedTypeFragmentURI) {
		val field = assertN4FieldDeclaration(phase, n4Class, expectedName)
		val type = assertTypeRef(phase, field, resource)
		assertBuiltinTypeFragmentURI(phase, resource, type, expectedTypeFragmentURI)
	}


	def private assertN4Getter(String phase, N4ClassDeclaration n4Class, Resource resource, String expectedName, String expectedReturnTypeFragmentURI) {
		val n4Getter = assertN4Getter(phase, n4Class, expectedName)

		assertTrue("Should have parameterized type ref", n4Getter.declaredTypeRefInAST instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = n4Getter.declaredTypeRefInAST as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, resource.contents.size)
		// test whether reference can be resolved
		val type = parameterizedTypeRef.declaredType;
		assertNotNull(phase + ": declaredType should not be null", type)

		assertBuiltinTypeFragmentURI(phase, resource, type, expectedReturnTypeFragmentURI)
	}

	def private assertN4Setter(String phase, N4ClassDeclaration n4Class, Resource resource, String expectedName, String expectedParameterName, String expectedReturnTypeFragmentURI) {
		val n4Setter = assertN4Setter(phase, n4Class, expectedName)
		val parameter = n4Setter.fpar
		assertEquals(expectedParameterName, parameter.name)
		assertEquals(phase + ": Should have the expected variadic setup", false, parameter.variadic)
		assertTrue("Should have parameterized type ref", parameter.declaredTypeRefInAST instanceof ParameterizedTypeRef)
		val parameterizedTypeRef = parameter.declaredTypeRefInAST as ParameterizedTypeRef
		assertEquals(phase + ": expected resource content size", 2, resource.contents.size)
		val type = parameterizedTypeRef.declaredType;
		assertNotNull(phase + ": declaredType should not be null", type)

		assertBuiltinTypeFragmentURI(phase, resource, type, expectedReturnTypeFragmentURI)
	}
}
