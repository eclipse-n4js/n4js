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
package org.eclipse.n4js.typesystem

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/*
 * Tests for {@link TypeSystemHelper#join(RuleEnvironment, TypeRef...)} method with union types.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class TypeSystemHelper_SimplifyUnionTypesTest extends AbstractTypeSystemHelperTests {

	@Before
	def void prepareTypeDefs() {
		setDefaultTypeDefinitions()
	}

	/**
	 * Asserts that join of given type expressions equals a given expected type, for comparison
	 * {@link TypeRef#getTypeRefAsString()} is used.
	 */
	def void assertSimplify(String expectedType, String typeExpressionsToBeSimplified) {
		assertSimplify(expectedType, typeExpressionsToBeSimplified, #[]);
	}

	def void assertSimplify(String expectedType, String typeExpressionsToBeSimplified, String[] expectedIssueMsg) {
		val G = assembler.prepareScriptAndCreateRuleEnvironment(expectedIssueMsg, typeExpressionsToBeSimplified)
		var typeRef = assembler.getTypeRef(typeExpressionsToBeSimplified)
		assertTrue("Error in test setup, expected union type", typeRef instanceof UnionTypeExpression);
		val simplified = TypeUtils.copy(tsh.simplify(G, typeRef as UnionTypeExpression));
		assertEquals(expectedType, simplified.typeRefAsString);
	}


	/*
	 * Test some assumptions.
	 */
	@Test
	def void testJoinAsumptions() {
		assertJoin("G<? extends A>", "G<A>", "G<B>");
		assertJoin("A", "A", "B");

		// G is instanceof of N4OBject ;-)
		assertJoin("N4Object", "G<A>", "A");
	}

	@Test
	def void testSimplifyDuplicates() {
		assertSimplify("A", "union{A}");
		assertSimplify("A", "union{A,B}", #[IssueCodes.UNI_REDUNDANT_SUBTYPE]);
		assertSimplify("A", "union{A,B,A}", #[IssueCodes.UNI_REDUNDANT_SUBTYPE]);
	}

	@Test
	def void testSimplifyNestedUnions() {
		assertSimplify("A", "union{A,B,union{A,B}}", #[IssueCodes.UNI_REDUNDANT_SUBTYPE, IssueCodes.UNI_REDUNDANT_SUBTYPE]);
		assertSimplify("union{A,B,C}", "union{A,B,union{B,C}}", #[IssueCodes.UNI_REDUNDANT_SUBTYPE, IssueCodes.UNI_REDUNDANT_SUBTYPE, IssueCodes.UNI_REDUNDANT_SUBTYPE]);
	}

	@Test
	def void testSimplifyUndefinedAndNull() {
		assertSimplify("A", "union{A,B,undefined}", #[IssueCodes.UNI_REDUNDANT_SUBTYPE]);
		assertSimplify("A", "union{A,undefined,B}", #[IssueCodes.UNI_REDUNDANT_SUBTYPE]);
		assertSimplify("A", "union{A,undefined}");
		assertSimplify("A", "union{undefined,A}");
		assertSimplify("undefined", "union{undefined,undefined}");
	}

	@Test
	def void testDontSimplifyNonDuplicates() {
		assertSimplify(
			"union{Array<union{A,D}>,Array<union{string,number}>}", // must not be simplified to "Array<union{A,D}>"
			"union{Array<union{A,D}>,Array<union{string,number}>}"
		);
		assertSimplify(
			"union{type{A},type{D}}", // must not be simplified to "type{A}" (note: D is not a subtype of A)
			"union{type{A},type{D}}"
		);
		assertSimplify(
			"union{constructor{? extends A},constructor{? extends D}}", // must not be simplified to "type{A}" (note: D is not a subtype of A)
			"union{constructor{? extends A},constructor{? extends D}}"
		);
		assertSimplify(
			"union{Array<type{A}>,Array<type{D}>}", // must not be simplified to "Array<type{A}>"
			"union{Array<type{A}>,Array<type{D}>}"
		);
		assertSimplify(
			"union{Array<constructor{? extends A}>,Array<constructor{? extends D}>}", // must not be simplified to "Array<type{A}>"
			"union{Array<constructor{? extends A}>,Array<constructor{? extends D}>}"
		);
		// this was already working correctly before the bug fix:
		assertSimplify(
			"union{EnumA,EnumB}", // must not be simplified to "EnumA"
			"union{EnumA,EnumB}");
		assertSimplify(
			"union{Array<EnumA>,Array<EnumB>}", // must not be simplified to "Array<EnumA>"
			"union{Array<EnumA>,Array<EnumB>}");
	}
}
