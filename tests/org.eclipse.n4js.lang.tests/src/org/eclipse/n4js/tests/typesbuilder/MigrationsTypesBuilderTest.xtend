/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.typesbuilder

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.tests.typesbuilder.utils.AbstractTypesBuilderTest
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMigration
import org.eclipse.n4js.ts.types.TModule
import org.junit.Test
import org.eclipse.n4js.tests.typesbuilder.extensions.TypesStructureAssertionExtension
import com.google.inject.Inject
import org.junit.Assert

/**
 * This test assures, that {@link TMigration} instances are populated 
 * correctly and {@link TMigration#getTargetTypeRefs} and {@link TMigration#getTargetTypeRefs}
 * return the expected list of type references.
 */
class MigrationsTypesBuilderTest extends AbstractTypesBuilderTest {
	
	@Inject
	private extension TypesStructureAssertionExtension
	
	@Test
	public def testTMigrationBuilding() {
		val expectedTypesNamePairs = #[
			typeof(TClass)		-> "A",
			typeof(TClass)		-> "A",
			typeof(TFunction)	-> "notAMigration",
			typeof(TMigration)	-> "noInputNoOutput",
			typeof(TMigration)	-> "singleInputNoOutput",
			typeof(TMigration)	-> "noInputSingleOutput",
			typeof(TMigration)	-> "singleInputSingleOutput",
			typeof(TMigration)	-> "twoInputNoOutput",
			typeof(TMigration)	-> "noInputTwoOutput",
			typeof(TMigration)	-> "twoInputSingleOutput",
			typeof(TMigration)	-> "singleInputTwoOutput",
			typeof(TMigration)	-> "twoInputTwoOutput",
			typeof(TMigration) -> "primitiveInputNoOutput",
			typeof(TMigration) -> "noInputPrimitiveOutput",
			typeof(TMigration) -> "structuralInputNoOutput",
			typeof(TMigration) -> "noInputMethodStructuralOutput",
			typeof(TMigration) -> "noInputNestedStructuralOutput",
			typeof(TMigration) -> "explicitMigration1",
			typeof(TMigration) -> "explicitMigration2"
		]

		val expectedExportedTypeToNamePairsOnIndex = #[
			typeof(TModule) -> qualifiedNamePrefix + "Migrations",
			typeof(TClass)		-> "A",
			typeof(TClass)		-> "A",
			typeof(TFunction) -> "notAMigration",
			typeof(TMigration) -> "noInputNoOutput",
			typeof(TMigration) -> "singleInputNoOutput",
			typeof(TMigration) -> "noInputSingleOutput",
			typeof(TMigration) -> "singleInputSingleOutput",
			typeof(TMigration) -> "twoInputNoOutput",
			typeof(TMigration) -> "noInputTwoOutput",
			typeof(TMigration) -> "twoInputSingleOutput",
			typeof(TMigration) -> "singleInputTwoOutput",
			typeof(TMigration) -> "twoInputTwoOutput",
			typeof(TMigration) -> "primitiveInputNoOutput",
			typeof(TMigration) -> "noInputPrimitiveOutput",
			typeof(TMigration) -> "structuralInputNoOutput",
			typeof(TMigration) -> "noInputMethodStructuralOutput",
			typeof(TMigration) -> "noInputNestedStructuralOutput",
			typeof(TMigration) -> "explicitMigration1",
			typeof(TMigration) -> "explicitMigration2"
		]
		val expectedTypesCount = expectedTypesNamePairs.size
		val expectedExportedElementsCount = expectedExportedTypeToNamePairsOnIndex.size
		
		executeTest("Migrations.n4js", expectedExportedTypeToNamePairsOnIndex, 
			expectedTypesCount, expectedExportedElementsCount);
	}
	
	override protected enableUserDataCompare() { false }
	
	override getExpectedTypesSerialization() { "" }
	
	override assertExampleTypeStructure(String phase, Resource resource) {
		val tFunction = assertTFunction(phase, resource, "notAMigration", 0);
		Assert.assertFalse("Non-migrations do not translate to TMigration instances", tFunction instanceof TMigration);
		
		// the following assertion check for (in that order): sourceVersion, targetVerison, sourceTypeRef.size, targetTypeRef.size
		assertTMigration(phase, resource, "noInputNoOutput", 0, 0, 0, 0, false);
		assertTMigration(phase, resource, "singleInputNoOutput", 1, 0, 1, 0, false);
		assertTMigration(phase, resource, "noInputSingleOutput", 0, 2, 0, 1, false);
		assertTMigration(phase, resource, "singleInputSingleOutput", 1, 2, 1, 1, false);
		assertTMigration(phase, resource, "twoInputNoOutput", 1, 0, 2, 1, false); // target type is any
		assertTMigration(phase, resource, "noInputTwoOutput", 0, 2, 0, 2, false);
		assertTMigration(phase, resource, "twoInputSingleOutput", 2, 2, 2, 1, false);
		assertTMigration(phase, resource, "singleInputTwoOutput", 1, 2, 1, 2, false);
		assertTMigration(phase, resource, "twoInputTwoOutput", 1, 2, 2, 2, false);
		assertTMigration(phase, resource, "primitiveInputNoOutput", 0, 0, 1, 0, false);
		assertTMigration(phase, resource, "noInputPrimitiveOutput", 0, 0, 0, 1, false);
		assertTMigration(phase, resource, "structuralInputNoOutput", 0, 0, 1, 0, false);
		assertTMigration(phase, resource, "noInputMethodStructuralOutput", 0, 0, 0, 0, false);
		assertTMigration(phase, resource, "noInputNestedStructuralOutput", 0, 0, 0, 1, false);
		
		assertTMigration(phase, resource, "explicitMigration1", 3, 4, 1, 1, true);
		assertTMigration(phase, resource, "explicitMigration2", 5, 6, 1, 1, true);
	}
	
	override assertExampleJSStructure(String phase, Resource resource) {
		// nothing to assert
	}
	
}