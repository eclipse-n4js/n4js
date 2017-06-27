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
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ClassWithSuperClassAndRolesTypesBuilderTest extends AbstractTypesBuilderTest {

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
		val textFileName = "ClassWithSuperClassAndRoles.n4js"
		val expectedTypesNamePairs = #[
			typeof(TClass) -> "MyClass",
			typeof(TInterface) -> "Persistable",
			typeof(TInterface) -> "Comparable",
			typeof(TClass) -> "MySubClass"
		]

		val expectedExportedTypeToNamePairsOnIndex = #[
			typeof(TModule) -> qualifiedNamePrefix + "ClassWithSuperClassAndRoles",
			typeof(TClass) -> "MyClass",
			typeof(TInterface) -> "Persistable",
			typeof(TInterface) -> "Comparable",
			typeof(TClass) -> "MySubClass"
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

		val tClass = assertTClass(phase, newN4jsResource, "MySubClass", 0)

		// TODO to be supported in the next Sprint
		//assertAnnotations(phase, tClass, newN4jsResource, )

		assertAccessModifier(phase, tClass, newN4jsResource, TypeAccessModifier.PUBLIC_INTERNAL)

		assertSuperClass(phase, tClass, newN4jsResource, "MyClass")

		assertConsumedRoles(phase, tClass, newN4jsResource, "Persistable", "Comparable")
	}

	override assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.contents.size)

		val script = assertScript(phase, resource, 4)

		val exportedElement = assertExportDeclaration(phase, script, 3)

		val n4Class = assertN4ClassDeclaration(phase, exportedElement, "MySubClass", 0)

		assertAnnotations(phase, n4Class, resource, "Internal")

		assertDeclaredAccessModifier(phase, n4Class, resource, N4Modifier.PUBLIC)

		assertSuperClass(phase, n4Class, resource, "MyClass")

		assertConsumedRoles(phase, n4Class, resource, "Persistable", "Comparable")
	}
}
