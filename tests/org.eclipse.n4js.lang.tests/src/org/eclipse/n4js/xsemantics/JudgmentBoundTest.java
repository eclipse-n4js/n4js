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
package org.eclipse.n4js.xsemantics;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.bottomTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.stringTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.topTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.map;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.N4JSTestHelper;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.EnumLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StringLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeCompareHelper;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for {@coud BoundJudgment}.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class JudgmentBoundTest extends AbstractTypesystemTest {

	@Inject
	N4JSTestHelper n4TestHelper;

	@Inject
	private TypeCompareHelper typeCompareHelper;

	private RuleEnvironment _G;
	private TClass C;
	private TClass G;
	private TClass GI;
	private TClass GO;
	private TEnum E;

	@Before
	public void before() {
		Script script = n4TestHelper.parseAndValidateSuccessfully("""
				class C {}
				class G<T> {}
				class GO<out T> {}
				class GI<in T> {}
				enum E { L1, L2 }
				""");

		_G = RuleEnvironmentExtensions.newRuleEnvironment(script);

		List<TClass> classes = toList(
				map(filter(script.eAllContents(), N4ClassDeclaration.class), cd -> cd.getDefinedTypeAsClass()));
		C = classes.get(0);
		G = classes.get(1);
		GO = classes.get(2);
		GI = classes.get(3);
		E = head(map(filter(script.eAllContents(), N4EnumDeclaration.class), ed -> ed.getDefinedTypeAsEnum()));
		assertEquals("C", C.getName());
		assertEquals("G", G.getName());
		assertEquals("GO", GO.getName());
		assertEquals("GI", GI.getName());
		assertEquals("E", E.getName());
	}

	@Test
	public void testModes() {
		ExistentialTypeRef captureExtC = TypeUtils.captureWildcard(wildcardExtends(C));
		ExistentialTypeRef captureSupC = TypeUtils.captureWildcard(wildcardSuper(C));
		BoundThisTypeRef thisOfC = TypeUtils.createBoundThisTypeRef((ParameterizedTypeRef) ref(C));
		TypeVariable typeVarBelowCVar = TypesFactory.eINSTANCE.createTypeVariable();
		typeVarBelowCVar.setName("T");
		typeVarBelowCVar.setDeclaredUpperBound(ref(C));
		TypeRef typeVarBelowC = ref(typeVarBelowCVar);

		StringLiteralTypeRef strLitHello = TypeRefsFactory.eINSTANCE.createStringLiteralTypeRef();
		strLitHello.setValue("hello");

		EnumLiteralTypeRef enumLit1 = TypeRefsFactory.eINSTANCE.createEnumLiteralTypeRef();
		enumLit1.setValue(findFirst(E.getLiterals(), lit -> "L1".equals(lit.getName())));

		// @formatter:off
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

		assertTypeEquals(ref(C),           ts.upperBoundWithReopen(_G, captureExtC));
		assertTypeEquals(topTypeRef(_G),   ts.upperBoundWithReopen(_G, captureSupC));
		assertTypeEquals(ref(C),           ts.upperBoundWithReopen(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.upperBoundWithReopen(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.upperBoundWithReopen(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.upperBoundWithReopen(_G, enumLit1));

		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopen(_G, captureExtC));
		assertTypeEquals(ref(C),           ts.lowerBoundWithReopen(_G, captureSupC));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopen(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.lowerBoundWithReopen(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.lowerBoundWithReopen(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.lowerBoundWithReopen(_G, enumLit1));

		assertTypeEquals(ref(C),           ts.upperBoundWithReopenAndResolveTypeVars(_G, captureExtC));
		assertTypeEquals(topTypeRef(_G),   ts.upperBoundWithReopenAndResolveTypeVars(_G, captureSupC));
		assertTypeEquals(ref(C),           ts.upperBoundWithReopenAndResolveTypeVars(_G, thisOfC));
		assertTypeEquals(ref(C),           ts.upperBoundWithReopenAndResolveTypeVars(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.upperBoundWithReopenAndResolveTypeVars(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.upperBoundWithReopenAndResolveTypeVars(_G, enumLit1));

		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveTypeVars(_G, captureExtC));
		assertTypeEquals(ref(C),           ts.lowerBoundWithReopenAndResolveTypeVars(_G, captureSupC));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveTypeVars(_G, thisOfC));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveTypeVars(_G, typeVarBelowC));
		assertTypeEquals(strLitHello,      ts.lowerBoundWithReopenAndResolveTypeVars(_G, strLitHello));
		assertTypeEquals(enumLit1,         ts.lowerBoundWithReopenAndResolveTypeVars(_G, enumLit1));

		assertTypeEquals(ref(C),           ts.upperBoundWithReopenAndResolveLiteralTypes(_G, captureExtC));
		assertTypeEquals(topTypeRef(_G),   ts.upperBoundWithReopenAndResolveLiteralTypes(_G, captureSupC));
		assertTypeEquals(ref(C),           ts.upperBoundWithReopenAndResolveLiteralTypes(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.upperBoundWithReopenAndResolveLiteralTypes(_G, typeVarBelowC));
		assertTypeEquals(stringTypeRef(_G),ts.upperBoundWithReopenAndResolveLiteralTypes(_G, strLitHello));
		assertTypeEquals(ref(E),           ts.upperBoundWithReopenAndResolveLiteralTypes(_G, enumLit1));

		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, captureExtC));
		assertTypeEquals(ref(C),           ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, captureSupC));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, thisOfC));
		assertTypeEquals(typeVarBelowC,    ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, typeVarBelowC));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, strLitHello));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveLiteralTypes(_G, enumLit1));

		assertTypeEquals(ref(C),           ts.upperBoundWithReopenAndResolveBoth(_G, captureExtC));
		assertTypeEquals(topTypeRef(_G),   ts.upperBoundWithReopenAndResolveBoth(_G, captureSupC));
		assertTypeEquals(ref(C),           ts.upperBoundWithReopenAndResolveBoth(_G, thisOfC));
		assertTypeEquals(ref(C),           ts.upperBoundWithReopenAndResolveBoth(_G, typeVarBelowC));
		assertTypeEquals(stringTypeRef(_G),ts.upperBoundWithReopenAndResolveBoth(_G, strLitHello));
		assertTypeEquals(ref(E),           ts.upperBoundWithReopenAndResolveBoth(_G, enumLit1));

		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveBoth(_G, captureExtC));
		assertTypeEquals(ref(C),           ts.lowerBoundWithReopenAndResolveBoth(_G, captureSupC));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveBoth(_G, thisOfC));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveBoth(_G, typeVarBelowC));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveBoth(_G, strLitHello));
		assertTypeEquals(bottomTypeRef(_G),ts.lowerBoundWithReopenAndResolveBoth(_G, enumLit1));
		// @formatter:on
	}

	@Test
	public void testParameteriedTypeRef_wildcards() {
		ExistentialTypeRef captureExtC = TypeUtils.captureWildcard(wildcardExtends(C));
		ExistentialTypeRef captureSupC = TypeUtils.captureWildcard(wildcardSuper(C));

		// @formatter:off
		assertTypeEquals(of(G,wildcardExtends(C)),              ts.upperBoundWithReopen(_G, of(G,captureExtC)));
		assertTypeEquals(of(G,wildcardExtends(C)),              ts.upperBoundWithReopen(_G, of(G,wildcardExtends(captureExtC))));
		assertTypeEquals(of(G,wildcardExtends(anyType(_G))),    ts.upperBoundWithReopen(_G, of(G,wildcardExtends(captureSupC))));
		assertTypeEquals(of(G,wildcardSuper(undefinedType(_G))),ts.upperBoundWithReopen(_G, of(G,wildcardSuper(captureExtC))));
		assertTypeEquals(of(G,wildcardSuper(C)),                ts.upperBoundWithReopen(_G, of(G,wildcardSuper(captureSupC))));
		// @formatter:on
	}

	@Test
	public void testParameteriedTypeRef_defSiteVariance() {
		ExistentialTypeRef captureExtC = TypeUtils.captureWildcard(wildcardExtends(C));
		ExistentialTypeRef captureSupC = TypeUtils.captureWildcard(wildcardSuper(C));

		// @formatter:off
		assertTypeEquals(of(G,wildcardExtends(C)), ts.upperBoundWithReopen(_G, of(G,captureExtC)));
		assertTypeEquals(of(GO,C),                 ts.upperBoundWithReopen(_G, of(GO,captureExtC)));
		assertTypeEquals(of(GO,anyType(_G)),       ts.upperBoundWithReopen(_G, of(GO,captureSupC)));
		assertTypeEquals(of(GI,undefinedType(_G)), ts.upperBoundWithReopen(_G, of(GI,captureExtC)));
		assertTypeEquals(of(GI,C),                 ts.upperBoundWithReopen(_G, of(GI,captureSupC)));
		// @formatter:on
	}

	private void assertTypeEquals(TypeArgument expected, TypeArgument actual) {
		assertTypeEquals("unexpected type, expected: " + expected.getTypeRefAsString() + ", actual: "
				+ actual.getTypeRefAsString(), expected, actual);
	}

	private void assertTypeEquals(String msg, TypeArgument expected, TypeArgument actual) {
		assertTrue(msg, typeCompareHelper.compare(expected, actual) == 0);
	}
}
