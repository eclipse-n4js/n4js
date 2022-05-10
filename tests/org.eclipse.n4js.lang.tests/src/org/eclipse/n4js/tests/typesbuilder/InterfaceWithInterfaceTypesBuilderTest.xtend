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
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class InterfaceWithInterfaceTypesBuilderTest extends AbstractTypesBuilderTest {

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
		val textFileName = "InterfaceWithInterface.n4js"
		val expectedTypesNamePairs = #[
			typeof(TInterface) -> "OtherInterface",
			typeof(TInterface) -> "MyInterface",
			typeof(TInterface) -> "PrivateInterface",
			typeof(TInterface) -> "PublicApiInterface"
		]

		// currently everything is exported to user data and Xtext index, e.g. to be able to
		// use in IDE "Open Type"
		val expectedExportedTypeToNamePairsOnIndex = #[
			typeof(TModule) -> qualifiedNamePrefix + "InterfaceWithInterface",
			typeof(TInterface) -> "OtherInterface",
			typeof(TInterface) -> "MyInterface",
			typeof(TInterface) -> "PrivateInterface",
			typeof(TInterface) -> "PublicApiInterface"
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

		val firstInterface = assertTInterface(phase, newN4jsResource, "OtherInterface", 0)

		// TODO to be supported in the next Sprint
		//assertAnnotations(phase, firstInterface, newN4jsResource, )

		assertAccessModifier(phase, firstInterface, newN4jsResource, TypeAccessModifier.PROJECT)

		assertSuperInterfaces(phase, firstInterface, newN4jsResource)

		val secondInterface = assertTInterface(phase, newN4jsResource, "MyInterface", 2)

		// TODO to be supported in the next Sprint
		//assertAnnotations(phase, firstInterface, newN4jsResource, )

		assertAccessModifier(phase, secondInterface, newN4jsResource, TypeAccessModifier.PUBLIC)

		assertSuperInterfaces(phase, secondInterface, newN4jsResource, "OtherInterface")

		val privateInterface = assertTInterface(phase, newN4jsResource, "PrivateInterface", 0)

		// TODO to be supported in the next Sprint
		//assertAnnotations(phase, firstInterface, newN4jsResource, )
		assertAccessModifier(phase, privateInterface, newN4jsResource, TypeAccessModifier.PRIVATE)
		assertSuperInterfaces(phase, privateInterface, newN4jsResource)

		val publicApiInterface = assertTInterface(phase, newN4jsResource, "PublicApiInterface", 0)
		// TODO to be supported in the next Sprint
		//assertAnnotations(phase, firstInterface, newN4jsResource, )
		assertAccessModifier(phase, publicApiInterface, newN4jsResource, TypeAccessModifier.PUBLIC_INTERNAL)
		assertSuperInterfaces(phase, publicApiInterface, newN4jsResource)
	}

	override assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.contents.size)

		val script = assertScript(phase, resource, 4)

		val firstExportedElement = (script.scriptElements.head as ExportDeclaration).exportedElement

		val firstInterface = assertN4InterfaceDeclaration(phase, firstExportedElement, "OtherInterface", 0)
		// TODO to be supported in the next Sprint
		//assertAnnotations(phase, firstInterface, newN4jsResource, )
		assertDeclaredAccessModifier(phase, firstInterface, resource, N4Modifier.UNDEFINED)
		assertSuperInterfaces(phase, firstInterface, resource)

		val secondExportedElement = assertExportDeclaration(phase, script, 1)
		val secondInterface = assertN4InterfaceDeclaration(phase, secondExportedElement, "MyInterface", 2)
		// TODO to be supported in the next Sprint
		//assertAnnotations(phase, firstInterface, newN4jsResource, )
		assertDeclaredAccessModifier(phase, secondInterface, resource, N4Modifier.PUBLIC)
		assertSuperInterfaces(phase, secondInterface, resource, "OtherInterface")

		val thirdExportableElement = script.scriptElements.get(2) as ExportableElement
		val thirdInterface = assertN4InterfaceDeclaration(phase, thirdExportableElement, "PrivateInterface", 0)
		// TODO to be supported in the next Sprint
		//assertAnnotations(phase, firstInterface, newN4jsResource, )
		assertDeclaredAccessModifier(phase, thirdInterface, resource, N4Modifier.UNDEFINED)
		assertSuperInterfaces(phase, thirdInterface, resource)

		val forthExportableElement = (script.scriptElements.last as ExportDeclaration).exportedElement
		val forthInterface = assertN4InterfaceDeclaration(phase, forthExportableElement, "PublicApiInterface", 0)
		// TODO to be supported in the next Sprint
		//assertAnnotations(phase, firstInterface, newN4jsResource, )
		assertDeclaredAccessModifier(phase, forthInterface, resource, N4Modifier.PUBLIC)
		assertSuperInterfaces(phase, forthInterface, resource)
	}
}
