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
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.tests.typesbuilder.extensions.ASTStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ClassWithEnumTypesBuilderTest extends AbstractTypesBuilderTest {
	@Inject
	private extension TypesStructureAssertionExtension;

	@Inject
	private extension ASTStructureAssertionExtension;

	override protected enableUserDataCompare() {
		// to check the complete AST just change false to true
		false
		//true
	}

	@Test
	def test() {
		val textFileName = "ClassWithEnum.n4js"
		val expectedExportedTypeToNamePairs = #[
			typeof(TModule) -> qualifiedNamePrefix + "ClassWithEnum",
			typeof(TEnum) -> "StorageType",
			typeof(TClass) -> "Storage"
		]
		val expectedTypesCount = 2
		val expectedExportedElementsCount = expectedExportedTypeToNamePairs.size // one function is not exported
		executeTest(textFileName, expectedExportedTypeToNamePairs, expectedTypesCount, expectedExportedElementsCount)
	}

	override getExpectedTypesSerialization() '''
		TModule {
		}'''

	override assertExampleJSStructure(String phase, Resource resource) {
		assertEquals("AST and TModule as root", 2, resource.contents.size)

		val script = assertScript(phase, resource, 2)

		val exportedElement = assertExportDeclaration(phase, script, 1)

		val n4Class = assertN4ClassDeclaration(phase, exportedElement, "Storage", 1)

		val n4ClassField = assertN4FieldDeclaration(phase, n4Class, "type")

		val enumType = assertTypeRef(phase, n4ClassField, resource)

		assertTypeFragmentURI(phase, resource, enumType, "/1/@types.0")

		assertTEnum(phase, enumType, "StorageType", "FILESYSTEM", "DATABASE")
	}

	override assertExampleTypeStructure(String phase, Resource newN4jsResource) {
		assertEquals("AST and TModule as root", 2, newN4jsResource.contents.size)

		val storageTClass = assertTClass(phase, newN4jsResource, "Storage", 1)

		val tField = assertTField(phase, storageTClass, "type")

		val enumType = assertTypeRefOfTField(phase, tField, newN4jsResource)

		assertTypeFragmentURI(phase, newN4jsResource, enumType, "/1/@types.0")

		assertTEnum(phase, enumType, "StorageType", "FILESYSTEM", "DATABASE")
	}
}
