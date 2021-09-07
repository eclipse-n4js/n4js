/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xsemantics

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.N4JSTestHelper
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeCompareHelper
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Tests for {@coud BoundJudgment}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class JudgmentBoundTest extends AbstractTypesystemTest {

	@Inject
	extension N4JSTestHelper

	@Inject
	private TypeCompareHelper typeCompareHelper;

	private RuleEnvironment _G;
	private TClass C;
	private TClass G;
	private TClass GI;
	private TClass GO;
	private TEnum E;


	@Before
	def void before() {
		val script = '''
			class C {}
			class G<T> {}
			class GO<out T> {}
			class GI<in T> {}
			enum E { L1, L2 }
		'''.parseAndValidateSuccessfully

		_G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		val classes = script.eAllContents.filter(N4ClassDeclaration).map[definedTypeAsClass].toList;
		C = classes.get(0);
		G = classes.get(1);
		GO = classes.get(2);
		GI = classes.get(3);
		E = script.eAllContents.filter(N4EnumDeclaration).map[definedTypeAsEnum].head;
		assertEquals("C", C.name);
		assertEquals("G", G.name);
		assertEquals("GO", GO.name);
		assertEquals("GI", GI.name);
		assertEquals("E", E.name);
	}


	@Test
	def void testModes() {
		val captureExtC = TypeUtils.captureWildcard(wildcardExtends(C));
		val captureSupC = TypeUtils.captureWildcard(wildcardSuper(C));
		val thisOfC = TypeUtils.createBoundThisTypeRef(C.ref as ParameterizedTypeRef);
		val typeVarBelowC = (TypesFactory.eINSTANCE.createTypeVariable() => [name = "T";declaredUpperBound = C.ref]).ref;
		val strLitHello = TypeRefsFactory.eINSTANCE.createStringLiteralTypeRef() => [value = "hello"];
		val enumLit1 = TypeRefsFactory.eINSTANCE.createEnumLiteralTypeRef() => [value = E.literals.findFirst[name == "L1"]];

		assertTypeEquals(captureExtC,      ts.upperBound(_G, captureExtC));
		assertTypeEquals(captureSupC,      ts.upperBound(_G, captureSupC));
		assertTypeEquals(thisOfC,          ts.upperBound(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.upperBound(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.upperBound(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.upperBound(_G, enumLit1));

		assertTypeEquals(captureExtC,      ts.lowerBound(_G, captureExtC));
		assertTypeEquals(captureSupC,      ts.lowerBound(_G, captureSupC));
		assertTypeEquals(thisOfC,          ts.lowerBound(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.lowerBound(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.lowerBound(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.lowerBound(_G, enumLit1));

		assertTypeEquals(C.ref,            ts.upperBoundWithReopen(_G, captureExtC));
		assertTypeEquals(_G.topTypeRef,    ts.upperBoundWithReopen(_G, captureSupC));
		assertTypeEquals(C.ref,            ts.upperBoundWithReopen(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.upperBoundWithReopen(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.upperBoundWithReopen(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.upperBoundWithReopen(_G, enumLit1));

		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopen(_G, captureExtC));
		assertTypeEquals(C.ref,            ts.lowerBoundWithReopen(_G, captureSupC));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopen(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.lowerBoundWithReopen(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.lowerBoundWithReopen(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.lowerBoundWithReopen(_G, enumLit1));

		assertTypeEquals(C.ref,            ts.upperBoundWithReopenAndResolveTypeVars(_G, captureExtC));
		assertTypeEquals(_G.topTypeRef,    ts.upperBoundWithReopenAndResolveTypeVars(_G, captureSupC));
		assertTypeEquals(C.ref,            ts.upperBoundWithReopenAndResolveTypeVars(_G, thisOfC));
		assertTypeEquals(C.ref,            ts.upperBoundWithReopenAndResolveTypeVars(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.upperBoundWithReopenAndResolveTypeVars(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.upperBoundWithReopenAndResolveTypeVars(_G, enumLit1));

		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveTypeVars(_G, captureExtC));
		assertTypeEquals(C.ref,            ts.lowerBoundWithReopenAndResolveTypeVars(_G, captureSupC));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveTypeVars(_G, thisOfC));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveTypeVars(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.lowerBoundWithReopenAndResolveTypeVars(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.lowerBoundWithReopenAndResolveTypeVars(_G, enumLit1));

		assertTypeEquals(C.ref,            ts.upperBoundWithReopenAndResolveLiteralTypes(_G, captureExtC));
		assertTypeEquals(_G.topTypeRef,    ts.upperBoundWithReopenAndResolveLiteralTypes(_G, captureSupC));
		assertTypeEquals(C.ref,            ts.upperBoundWithReopenAndResolveLiteralTypes(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.upperBoundWithReopenAndResolveLiteralTypes(_G, typeVarBelowC));
		assertTypeEquals(_G.stringTypeRef, ts.upperBoundWithReopenAndResolveLiteralTypes(_G, strLitHello));
		assertTypeEquals(E.ref,            ts.upperBoundWithReopenAndResolveLiteralTypes(_G, enumLit1));

		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, captureExtC));
		assertTypeEquals(C.ref,            ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, captureSupC));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, typeVarBelowC));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, strLitHello));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, enumLit1));

		assertTypeEquals(C.ref,            ts.upperBoundWithReopenAndResolveBoth(_G, captureExtC));
		assertTypeEquals(_G.topTypeRef,    ts.upperBoundWithReopenAndResolveBoth(_G, captureSupC));
		assertTypeEquals(C.ref,            ts.upperBoundWithReopenAndResolveBoth(_G, thisOfC));
		assertTypeEquals(C.ref,            ts.upperBoundWithReopenAndResolveBoth(_G, typeVarBelowC));
		assertTypeEquals(_G.stringTypeRef, ts.upperBoundWithReopenAndResolveBoth(_G, strLitHello));
		assertTypeEquals(E.ref,            ts.upperBoundWithReopenAndResolveBoth(_G, enumLit1));

		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveBoth(_G, captureExtC));
		assertTypeEquals(C.ref,            ts.lowerBoundWithReopenAndResolveBoth(_G, captureSupC));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveBoth(_G, thisOfC));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveBoth(_G, typeVarBelowC));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveBoth(_G, strLitHello));
		assertTypeEquals(_G.bottomTypeRef, ts.lowerBoundWithReopenAndResolveBoth(_G, enumLit1));
	}

	@Test
	def void testParameteriedTypeRef_wildcards() {
		val captureExtC = TypeUtils.captureWildcard(wildcardExtends(C));
		val captureSupC = TypeUtils.captureWildcard(wildcardSuper(C));

		assertTypeEquals(G.of(wildcardExtends(C)),              ts.upperBoundWithReopen(_G, G.of(captureExtC)));
		assertTypeEquals(G.of(wildcardExtends(C)),              ts.upperBoundWithReopen(_G, G.of(wildcardExtends(captureExtC))));
		assertTypeEquals(G.of(wildcardExtends(_G.anyType)),     ts.upperBoundWithReopen(_G, G.of(wildcardExtends(captureSupC))));
		assertTypeEquals(G.of(wildcardSuper(_G.undefinedType)), ts.upperBoundWithReopen(_G, G.of(wildcardSuper(captureExtC))));
		assertTypeEquals(G.of(wildcardSuper(C)),                ts.upperBoundWithReopen(_G, G.of(wildcardSuper(captureSupC))));
	}

	@Test
	def void testParameteriedTypeRef_defSiteVariance() {
		val captureExtC = TypeUtils.captureWildcard(wildcardExtends(C));
		val captureSupC = TypeUtils.captureWildcard(wildcardSuper(C));

		assertTypeEquals(G.of(wildcardExtends(C)), ts.upperBoundWithReopen(_G, G.of(captureExtC)));
		assertTypeEquals(GO.of(C),                 ts.upperBoundWithReopen(_G, GO.of(captureExtC)));
		assertTypeEquals(GO.of(_G.anyType),        ts.upperBoundWithReopen(_G, GO.of(captureSupC)));
		assertTypeEquals(GI.of(_G.undefinedType),  ts.upperBoundWithReopen(_G, GI.of(captureExtC)));
		assertTypeEquals(GI.of(C),                 ts.upperBoundWithReopen(_G, GI.of(captureSupC)));
	}


	def private void assertTypeEquals(TypeArgument expected, TypeArgument actual) {
		assertTypeEquals("unexpected type, expected: " + expected.typeRefAsString + ", actual: " + actual.typeRefAsString, expected, actual);
	}
	def private void assertTypeEquals(String msg, TypeArgument expected, TypeArgument actual) {
		assertTrue(msg, typeCompareHelper.compare(expected, actual) === 0);
	}
}
