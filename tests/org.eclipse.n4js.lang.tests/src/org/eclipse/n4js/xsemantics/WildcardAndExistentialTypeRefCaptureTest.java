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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addTypeMapping;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeCompareHelper;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for capturing wildcards, i.e. turning {@link Wildcard}s into non-reopened {@link ExistentialTypeRef}s.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class WildcardAndExistentialTypeRefCaptureTest extends AbstractTypesystemTest {

	@Inject
	private TypeCompareHelper typeCompareHelper;

	private RuleEnvironment G;
	private Script script;
	private TClass A;
	private TClass B;
	private TClass C;
	private TClass List;
	private TClass Pair;

	@Before
	public void before() {
		script = createAndValidateScript(JavaScriptVariant.n4js, """
				class A {}
				class B extends A {}
				class C extends B {}
				class List<T> {}
				class Pair<T1,T2> {}
				""");

		G = newRuleEnvironment(script);

		EList<Type> types = script.getModule().getTypes();
		A = (TClass) findFirst(types, t -> "A".equals(t.getName()));
		B = (TClass) findFirst(types, t -> "B".equals(t.getName()));
		C = (TClass) findFirst(types, t -> "C".equals(t.getName()));
		List = (TClass) findFirst(types, t -> "List".equals(t.getName()));
		Pair = (TClass) findFirst(types, t -> "Pair".equals(t.getName()));
		assertNotNull(A);
		assertNotNull(B);
		assertNotNull(C);
		assertNotNull(List);
		assertNotNull(Pair);
	}

	@Test
	public void testCaptureIdentity() {
		TypeRef typeRef = of(List, wildcard());
		TypeRef capture1 = ts.substTypeVariablesWithFullCapture(G, typeRef);
		TypeRef capture1Cpy = TypeUtils.copy(capture1);
		TypeRef capture2 = ts.substTypeVariablesWithFullCapture(G, typeRef);

		// trivial case: capture compared to itself
		assertTrue("capture #1 should be a subtype of capture #1", ts.subtypeSucceeded(G, capture1, capture1));
		assertTrue("capture #1 should be a subtype of capture #1", ts.subtypeSucceeded(G, capture1, capture1));
		assertTrue("capture #1 should be equal to itself according to semi-semantics equality check",
				typeCompareHelper.compare(capture1, capture1) == 0);

		assertTrue("capture #1 should be a subtype of copy of capture #1",
				ts.subtypeSucceeded(G, capture1, capture1Cpy));
		assertTrue("copy of capture #1 should be a subtype of capture #1",
				ts.subtypeSucceeded(G, capture1Cpy, capture1));
		assertTrue("capture #1 and its copy should be equal according to semi-semantics equality check",
				typeCompareHelper.compare(capture1, capture1Cpy) == 0);

		assertFalse("capture #1 should not be a subtype of capture #2", ts.subtypeSucceeded(G, capture1, capture2));
		assertFalse("capture #2 should not be a subtype of capture #1", ts.subtypeSucceeded(G, capture2, capture1));
		assertFalse("capture #1 and capture #2 should be equal according to semi-semantics equality check",
				typeCompareHelper.compare(capture1, capture2) == 0);
	}

	@Test
	public void testFullVsPartialCapture() {
		TypeVariable TX = TypesFactory.eINSTANCE.createTypeVariable();
		TX.setName("TX");
		TypeRef typeRef = of(Pair, wildcard(), ref(TX)); // Pair<?, TX>

		RuleEnvironment G2 = wrap(G);
		addTypeMapping(G2, TX, wildcard());

		TypeRef typeRefCaptureNone = ts.substTypeVariables(G2, typeRef);
		TypeRef typeRefCaptureFull = ts.substTypeVariablesWithFullCapture(G2, typeRef);
		TypeRef typeRefCapturePartial = ts.substTypeVariablesWithPartialCapture(G2, typeRef);

		EList<TypeArgument> typeArgsOfCaptureNone = ((ParameterizedTypeRef) typeRefCaptureNone).getDeclaredTypeArgs();
		assertTrue(typeArgsOfCaptureNone.get(0) instanceof Wildcard);
		// this reopened existential will act like a wildcard (was converted only for technical reasons)
		assertTrue(isReopenedExistentialTypeRef(typeArgsOfCaptureNone.get(1)));

		EList<TypeArgument> typeArgsOfCaptureFull = ((ParameterizedTypeRef) typeRefCaptureFull).getDeclaredTypeArgs();
		assertTrue(isClosedExistentialTypeRef(typeArgsOfCaptureFull.get(0)));
		assertTrue(isClosedExistentialTypeRef(typeArgsOfCaptureFull.get(1)));

		EList<TypeArgument> typeArgsOfCapturePartial = ((ParameterizedTypeRef) typeRefCapturePartial)
				.getDeclaredTypeArgs();
		assertTrue(typeArgsOfCapturePartial.get(0) instanceof Wildcard);
		assertTrue(isClosedExistentialTypeRef(typeArgsOfCapturePartial.get(1)));
	}

	@Test
	public void testMustNotCaptureWildcardsContainedInUpperBoundsOfWildcard() {
		TypeRef typeRef = of(List, wildcardExtends(of(List, wildcard()))); // List<? extends List<?>>>
		TypeRef typeRefCaptured = ts.substTypeVariablesWithFullCapture(G, typeRef);

		// the higher-level wildcard must have been captured
		TypeArgument typeArg1 = ((ParameterizedTypeRef) typeRefCaptured).getDeclaredTypeArgs().get(0);
		assertEquals("higher-level type argument should be an ExistentialTypeRef",
				TypeRefsPackage.eINSTANCE.getExistentialTypeRef(), typeArg1.eClass());
		assertFalse("higher-level type argument should be closed, i.e. not reopened",
				((ExistentialTypeRef) typeArg1).isReopened());
		// the lower-level wildcard must *not* have been captured
		TypeRef upperBoundOfTypeArg1 = ((ExistentialTypeRef) typeArg1).getWildcard().getDeclaredUpperBound();
		TypeArgument typeArg2 = ((ParameterizedTypeRef) upperBoundOfTypeArg1).getDeclaredTypeArgs().get(0);
		assertEquals("lower-level type argument should still be a wildcard", TypeRefsPackage.eINSTANCE.getWildcard(),
				typeArg2.eClass());
	}

	@Test
	public void testMustNotCaptureWildcardsContainedInLowerBoundsOfWildcard() {
		TypeRef typeRef = of(List, wildcardSuper(of(List, wildcard()))); // List<? super List<?>>>
		TypeRef typeRefCaptured = ts.substTypeVariablesWithFullCapture(G, typeRef);

		// the higher-level wildcard must have been captured
		TypeArgument typeArg1 = ((ParameterizedTypeRef) typeRefCaptured).getDeclaredTypeArgs().get(0);
		assertEquals("higher-level type argument should be an ExistentialTypeRef",
				TypeRefsPackage.eINSTANCE.getExistentialTypeRef(), typeArg1.eClass());
		assertFalse("higher-level type argument should be closed, i.e. not reopened",
				((ExistentialTypeRef) typeArg1).isReopened());
		// the lower-level wildcard must *not* have been captured
		TypeRef lowerBoundOfTypeArg1 = ((ExistentialTypeRef) typeArg1).getWildcard().getDeclaredLowerBound();
		TypeArgument typeArg2 = ((ParameterizedTypeRef) lowerBoundOfTypeArg1).getDeclaredTypeArgs().get(0);
		assertEquals("lower-level type argument should still be a wildcard", TypeRefsPackage.eINSTANCE.getWildcard(),
				typeArg2.eClass());
	}

	private static boolean isClosedExistentialTypeRef(TypeArgument typeArg) {
		return (typeArg instanceof ExistentialTypeRef) ? !((ExistentialTypeRef) typeArg).isReopened() : false;
	}

	private static boolean isReopenedExistentialTypeRef(TypeArgument typeArg) {
		return (typeArg instanceof ExistentialTypeRef) ? ((ExistentialTypeRef) typeArg).isReopened() : false;
	}
}
