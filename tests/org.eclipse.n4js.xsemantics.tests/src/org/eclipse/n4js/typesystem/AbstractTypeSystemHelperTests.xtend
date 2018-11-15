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

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.utils.TypeCompareHelper
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.validation.JavaScriptVariant

import static org.junit.Assert.*

/**
 * Abstract base class for tests of {@link TypeSystemHelper}, setting up
 * a sample type hierarchy and providing some specialized assert methods.
 * <p>
 * Needs to be run with Guice injector available (e.g. XTextRunner), as the type system helper and other objects are injected.
 * </p>
 */
abstract class AbstractTypeSystemHelperTests {

	@Inject protected TypeSystemHelper tsh;
	@Inject protected N4JSTypeSystem ts;

	@Inject protected TypeRefsToVariablesAssembler assembler;

	@Inject TypeCompareHelper typeCompareHelper;

	def setDefaultTypeDefinitions() {
		assembler.setScriptPrefix(getSophisticatedTypeHierarchyScript())
	}

	def setScriptTypeDefinitions(CharSequence scriptTypeDefs) {
		assembler.setScriptPrefix(scriptTypeDefs)
	}

	/**
	 * Asserts that the given type expression actually is evaluated to the expected type.
	 * This assert is basically used to check if the test set up is correct.
	 */
	def void assertType(String expectedType, String typeExpression) {
		assembler.prepareScriptAndCreateRuleEnvironment(typeExpression)
		var actualType = assembler.getTypeRef(typeExpression)
		assertEquals(expectedType, actualType);
	}

	def void assertSubTypeOfRef(String propertyRefName, SubTypeRelationForTest subtypeRel, String comparedTypeExpr) {
		val pair = assembler.doPrepareScriptAndCreateRuleEnvironment(JavaScriptVariant.n4js, comparedTypeExpr)
		val script = pair.key
		val G = pair.value
		var expectedTypeRef =  assembler.getTypeRef(comparedTypeExpr)
		var TypableElement identifierRef = script.eAllContents.filter(ParameterizedPropertyAccessExpression).filter[it.property.name==propertyRefName].head
		if (identifierRef===null) {
			identifierRef =  script.eAllContents.filter(IdentifierRef).filter[it.id.name==propertyRefName].head
		}
		assertNotNull("bogus test, identifier ref " + propertyRefName + " not found", identifierRef)
		val propertyTypeRef = ts.type(G, identifierRef)
		assertNotNull("Type identifier ref " + propertyRefName+ " is null", propertyTypeRef)
		assertFalse("Cannot type identifier ref " + propertyRefName + ": " + propertyTypeRef, propertyTypeRef instanceof UnknownTypeRef)

		val subTypeResult = ts.subtype(G, propertyTypeRef, expectedTypeRef)
		val superTypeResult = ts.subtype(G, expectedTypeRef, propertyTypeRef)

		val expectSub = subtypeRel==SubTypeRelationForTest._sub || subtypeRel==SubTypeRelationForTest._equals;
		val expectSuper = subtypeRel==SubTypeRelationForTest._super || subtypeRel==SubTypeRelationForTest._equals;

		if (expectSub) {
			assertFalse(propertyRefName + " <: " + comparedTypeExpr + " failed: " + subTypeResult.failureMessage, subTypeResult.failure)
			assertEquals(propertyRefName + " <: " + comparedTypeExpr + " failed", Boolean.TRUE, subTypeResult.success)
		} else {
			assertTrue(propertyRefName + " <: " + comparedTypeExpr + " should fail", subTypeResult.failure)
			assertNotEquals(propertyRefName + " <: " + comparedTypeExpr + " should be false", Boolean.TRUE, subTypeResult.success)
		}
		if (expectSuper) {
			assertFalse(comparedTypeExpr + " <: " + propertyRefName + " failed: " + superTypeResult.failureMessage, superTypeResult.failure)
			assertEquals(comparedTypeExpr + " <: " + propertyRefName + " failed", Boolean.TRUE, superTypeResult.success)
		} else {
			assertTrue(comparedTypeExpr + " <: " + propertyRefName + " should fail", superTypeResult.failure)
			assertNotEquals(comparedTypeExpr + " <: " + propertyRefName + " should be false", Boolean.TRUE, superTypeResult.success)
		}


	}

	/**
	 * Asserts that join of given type expressions equals a given expected type, for comparison
	 * {@link TypeRef#getTypeRefAsString()} is used.
	 */
	def void assertJoin(String expectedType, String... typeExpressionsToBeJoined) {
		assertJoin(#[], expectedType, typeExpressionsToBeJoined);
	}
	def void assertJoin(String[] expectedMessages, String expectedType, String... typeExpressionsToBeJoined) {
		val G = assembler.prepareScriptAndCreateRuleEnvironment(expectedMessages, typeExpressionsToBeJoined)
		var typeRefs = typeExpressionsToBeJoined.map[assembler.getTypeRef(it)]
		val join = TypeUtils.copy(tsh.join(G, typeRefs));
		join.sorted();
		assertEquals(expectedType, join.typeRefAsString);
	}

	/**
	 * Asserts that meet of given type expressions equals a given expected type, for comparison
	 * {@link TypeRef#getTypeRefAsString()} is used.
	 */
	def void assertMeet(String expectedType, String... typeExpressionsToBeMeet) {
		assertMeet(#[], expectedType, typeExpressionsToBeMeet);
	}
	def void assertMeet(String[] expectedMessages, String expectedType, String... typeExpressionsToBeMeet) {
		val G = assembler.prepareScriptAndCreateRuleEnvironment(expectedMessages, typeExpressionsToBeMeet)
		var typeRefs = typeExpressionsToBeMeet.map[assembler.getTypeRef(it)]
		val meet = TypeUtils.copy(tsh.meet(G, typeRefs));
		meet.sorted();
		assertEquals(expectedType, meet.typeRefAsString);
	}



	/**
	 * Typerefs of composed types are sorted in order to make (string) comparison independent from
	 * actual order, which may vary depending on JDKs (HashMaps) or algorithm.
	 * <p>
	 * By default, these type refs are not sorted as this is not required internally.
	 */
	def sorted(TypeRef typeRef) {
		if (typeRef instanceof ComposedTypeRef) {
			val sorted = typeRef.typeRefs.sortWith(typeCompareHelper.getTypeRefComparator);

			typeRef.typeRefs.clear();
			typeRef.typeRefs.addAll(sorted)
		}
		typeRef
	}

	def getSophisticatedTypeHierarchyScript() {
		'''
			class A{}
			class B extends A {}
			class C extends B {}
			class D {}
			class E {}
			class G<T>{}
			class H<T> extends G<T>{}
			class Kga extends G<B> {}
			class Kgb extends G<B> {}
			class Kgc extends G<B> {}
			interface I1 {}
			interface I2 extends I1 {}
			interface I3 extends I2 {}
			interface Q1 {}
			interface R1 {}
			interface R2 extends R1 {}
			interface R3 extends R2 {}
			interface GI<T> {}
			interface GR<T> {}
			class GRI<T> extends G<T> implements GR<T>, GI<T> {}
			class MR2I2 implements R2, I2 {}
			class MR3I1 implements R3, I1 {}
			class MR1I3 implements R1, I3 {}
			enum EnumA { EnumLitA1, EnumLitA2, EnumLitA3 }
			enum EnumB { EnumLitB1, EnumLitB2, EnumLitB3 }
		'''
	}

}
