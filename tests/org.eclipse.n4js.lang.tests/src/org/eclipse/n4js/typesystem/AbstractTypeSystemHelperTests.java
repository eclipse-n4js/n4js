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
package org.eclipse.n4js.typesystem;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sortWith;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.types.utils.TypeCompareHelper;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

/**
 * Abstract base class for tests of {@link TypeSystemHelper}, setting up a sample type hierarchy and providing some
 * specialized assert methods.
 * <p>
 * Needs to be run with Guice injector available (e.g. XTextRunner), as the type system helper and other objects are
 * injected.
 * </p>
 */
abstract public class AbstractTypeSystemHelperTests {

	@Inject
	protected TypeSystemHelper tsh;
	@Inject
	protected N4JSTypeSystem ts;

	@Inject
	protected TypeRefsToVariablesAssembler assembler;

	@Inject
	TypeCompareHelper typeCompareHelper;

	public void setDefaultTypeDefinitions() {
		assembler.setScriptPrefix(getSophisticatedTypeHierarchyScript());
	}

	public void setScriptTypeDefinitions(CharSequence scriptTypeDefs) {
		assembler.setScriptPrefix(scriptTypeDefs);
	}

	/**
	 * Asserts that the given type expression actually is evaluated to the expected type. This assert is basically used
	 * to check if the test set up is correct.
	 */
	public void assertType(String expectedType, String typeExpression) {
		assembler.prepareScriptAndCreateRuleEnvironment(typeExpression);
		TypeRef actualType = assembler.getTypeRef(typeExpression);
		assertEquals(expectedType, actualType);
	}

	public void assertSubTypeOfRef(String propertyRefName, SubTypeRelationForTest subtypeRel, String comparedTypeExpr) {
		Pair<Script, RuleEnvironment> pair = assembler.doPrepareScriptAndCreateRuleEnvironment(JavaScriptVariant.n4js,
				comparedTypeExpr);
		Script script = pair.getKey();
		RuleEnvironment G = pair.getValue();
		var expectedTypeRef = assembler.getTypeRef(comparedTypeExpr);
		TypableElement identifierRef = head(
				filter(filter(script.eAllContents(), ParameterizedPropertyAccessExpression.class),
						ppae -> Objects.equals(ppae.getProperty().getName(), propertyRefName)));
		if (identifierRef == null) {
			identifierRef = head(filter(filter(script.eAllContents(), IdentifierRef.class),
					ir -> Objects.equals(ir.getId().getName(), propertyRefName)));
		}
		assertNotNull("bogus test, identifier ref " + propertyRefName + " not found", identifierRef);
		TypeRef propertyTypeRef = ts.type(G, identifierRef);
		assertNotNull("Type identifier ref " + propertyRefName + " is null", propertyTypeRef);
		assertFalse("Cannot type identifier ref " + propertyRefName + ": " + propertyTypeRef,
				propertyTypeRef instanceof UnknownTypeRef);

		Result subTypeResult = ts.subtype(G, propertyTypeRef, expectedTypeRef);
		Result superTypeResult = ts.subtype(G, expectedTypeRef, propertyTypeRef);

		boolean expectSub = subtypeRel == SubTypeRelationForTest._sub || subtypeRel == SubTypeRelationForTest._equals;
		boolean expectSuper = subtypeRel == SubTypeRelationForTest._super
				|| subtypeRel == SubTypeRelationForTest._equals;

		if (expectSub) {
			assertFalse(propertyRefName + " <: " + comparedTypeExpr + " failed: " + subTypeResult.getFailureMessage(),
					subTypeResult.isFailure());
			assertEquals(propertyRefName + " <: " + comparedTypeExpr + " failed", Boolean.TRUE,
					subTypeResult.isSuccess());
		} else {
			assertTrue(propertyRefName + " <: " + comparedTypeExpr + " should fail", subTypeResult.isFailure());
			assertNotEquals(propertyRefName + " <: " + comparedTypeExpr + " should be false", Boolean.TRUE,
					subTypeResult.isSuccess());
		}
		if (expectSuper) {
			assertFalse(comparedTypeExpr + " <: " + propertyRefName + " failed: " + superTypeResult.getFailureMessage(),
					superTypeResult.isFailure());
			assertEquals(comparedTypeExpr + " <: " + propertyRefName + " failed", Boolean.TRUE,
					superTypeResult.isSuccess());
		} else {
			assertTrue(comparedTypeExpr + " <: " + propertyRefName + " should fail", superTypeResult.isFailure());
			assertNotEquals(comparedTypeExpr + " <: " + propertyRefName + " should be false", Boolean.TRUE,
					superTypeResult.isSuccess());
		}

	}

	/**
	 * Asserts that join of given type expressions equals a given expected type, for comparison
	 * {@link TypeRef#getTypeRefAsString()} is used.
	 */
	public void assertJoin(String expectedType, String... typeExpressionsToBeJoined) {
		assertJoin(new IssueCodes[0], expectedType, typeExpressionsToBeJoined);
	}

	public void assertJoin(IssueCodes[] expectedMessages, String expectedType, String... typeExpressionsToBeJoined) {
		RuleEnvironment G = assembler.prepareScriptAndCreateRuleEnvironment(expectedMessages,
				typeExpressionsToBeJoined);
		Iterable<TypeRef> typeRefs = map(Arrays.asList(typeExpressionsToBeJoined), tExp -> assembler.getTypeRef(tExp));
		TypeRef join = TypeUtils.copy(tsh.join(G, typeRefs));
		sorted(join);
		assertEquals(expectedType, join.getTypeRefAsString());
	}

	/**
	 * Asserts that meet of given type expressions equals a given expected type, for comparison
	 * {@link TypeRef#getTypeRefAsString()} is used.
	 */
	public void assertMeet(String expectedType, String... typeExpressionsToBeMeet) {
		assertMeet(new IssueCodes[0], expectedType, typeExpressionsToBeMeet);
	}

	public void assertMeet(IssueCodes[] expectedMessages, String expectedType, String... typeExpressionsToBeMeet) {
		RuleEnvironment G = assembler.prepareScriptAndCreateRuleEnvironment(expectedMessages, typeExpressionsToBeMeet);
		Iterable<TypeRef> typeRefs = map(Arrays.asList(typeExpressionsToBeMeet), tExp -> assembler.getTypeRef(tExp));
		TypeRef meet = TypeUtils.copy(tsh.meet(G, typeRefs));
		sorted(meet);
		assertEquals(expectedType, meet.getTypeRefAsString());
	}

	/**
	 * Typerefs of composed types are sorted in order to make (string) comparison independent from actual order, which
	 * may vary depending on JDKs (HashMaps) or algorithm.
	 * <p>
	 * By default, these type refs are not sorted as this is not required internally.
	 */
	public TypeRef sorted(TypeRef typeRef) {
		if (typeRef instanceof ComposedTypeRef) {
			EList<TypeRef> typeRefs = ((ComposedTypeRef) typeRef).getTypeRefs();
			List<TypeRef> sorted = sortWith(typeRefs, typeCompareHelper.getTypeRefComparator());
			typeRefs.clear();
			typeRefs.addAll(sorted);
		}
		return typeRef;
	}

	public String getSophisticatedTypeHierarchyScript() {
		return """
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
				""";
	}

}
