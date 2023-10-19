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
package org.eclipse.n4js.tests.types.utils;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.last;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.util.CancelIndicator;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class TypeUtilsTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Test
	public void testDeclaredSuperTypesClass() throws Exception {
		Script script = parseHelper.parse(
				"""
						public class A {}
						public class B extends A{}
						public class C extends A implements R {}
						public class D extends B implements R, I{}
						public class E extends B implements R,S, I,J{}
						public class F implements R,S, I,J{}
						public interface R {}
						public interface S {}
						public interface I {}
						public interface J {}
						""");

		assertNotNull(script);

		Type A = script.getModule().getTypes().get(0);
		Type B = script.getModule().getTypes().get(1);
		Type C = script.getModule().getTypes().get(2);
		Type D = script.getModule().getTypes().get(3);
		Type E = script.getModule().getTypes().get(4);
		Type F = script.getModule().getTypes().get(5);

		assertEquals("", join(",", map(TypeUtils.declaredSuperTypes(A), st -> st.getTypeRefAsString())));
		assertEquals("A", join(",", map(TypeUtils.declaredSuperTypes(B), st -> st.getTypeRefAsString())));
		assertEquals("A,R", join(",", map(TypeUtils.declaredSuperTypes(C), st -> st.getTypeRefAsString())));
		assertEquals("B,R,I", join(",", map(TypeUtils.declaredSuperTypes(D), st -> st.getTypeRefAsString())));
		assertEquals("B,R,S,I,J", join(",", map(TypeUtils.declaredSuperTypes(E), st -> st.getTypeRefAsString())));
		assertEquals("R,S,I,J", join(",", map(TypeUtils.declaredSuperTypes(F), st -> st.getTypeRefAsString())));
	}

	@Test
	public void testDeclaredSuperTypesInterfaces() throws Exception {
		Script script = parseHelper.parse(
				"""
						public interface I {}
						public interface J extends I {}
						public interface L extends I,K {}
						public interface K  {}
						""");

		assertNotNull(script);

		Type I = script.getModule().getTypes().get(0);
		Type J = script.getModule().getTypes().get(1);
		Type L = script.getModule().getTypes().get(2);

		assertEquals("", join(",", map(TypeUtils.declaredSuperTypes(I), st -> st.getTypeRefAsString())));
		assertEquals("I", join(",", map(TypeUtils.declaredSuperTypes(J), st -> st.getTypeRefAsString())));
		assertEquals("I,K", join(",", map(TypeUtils.declaredSuperTypes(L), st -> st.getTypeRefAsString())));
	}

	@Test
	public void testDeclaredSuperTypesOthers() throws Exception {
		Script script = parseHelper.parse(
				"""
							true;
						""");

		assertNotNull(script);

		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		Type undefType = RuleEnvironmentExtensions.undefinedType(G);
		Type nullType = RuleEnvironmentExtensions.nullType(G);
		Type booleanType = RuleEnvironmentExtensions.booleanType(G);
		Type anyType = RuleEnvironmentExtensions.anyType(G);
		Type voidType = RuleEnvironmentExtensions.voidType(G);

		assertEquals("", join(",", map(TypeUtils.declaredSuperTypes(null), st -> st.getTypeRefAsString())));
		assertEquals("", join(",", map(TypeUtils.declaredSuperTypes(undefType), st -> st.getTypeRefAsString())));
		assertEquals("", join(",", map(TypeUtils.declaredSuperTypes(nullType), st -> st.getTypeRefAsString())));
		assertEquals("", join(",", map(TypeUtils.declaredSuperTypes(booleanType), st -> st.getTypeRefAsString())));
		assertEquals("", join(",", map(TypeUtils.declaredSuperTypes(anyType), st -> st.getTypeRefAsString())));
		assertEquals("", join(",", map(TypeUtils.declaredSuperTypes(voidType), st -> st.getTypeRefAsString())));
	}

	@Test
	public void testCopy_recursiveUpperBounds_direct() throws Exception {
		testCopy_recursiveUpperBounds(
				"""
							public class A<T extends A<?>> {}
						""",
				"A<? extends A<?>>");
	}

	@Test
	public void testCopy_recursiveUpperBounds_indirect1() throws Exception {
		testCopy_recursiveUpperBounds(
				"""
							public class X<T extends B<?>> {}
							public class Y<T extends X<?>> {}
							public class B<T extends Y<?>> {}
						""",
				"X<? extends B<?>>");
	}

	@Test
	public void testCopy_recursiveUpperBounds_indirect1_outside() throws Exception {
		testCopy_recursiveUpperBounds(
				"""
							public class X<T extends B<?>> {}
							public class Y<T extends X<?>> {}
							public class B<T extends Y<?>> {}

							public class Other {
								public field: B<?>; // <-- this time using a wildcard outside the recursion cycle
							}
						""",
				"Y<? extends X<?>>");
	}

	@Test
	public void testCopy_recursiveUpperBounds_indirect2_outside() throws Exception {
		testCopy_recursiveUpperBounds(
				"""
							public class G<T> {}
							public class A<T extends G<? extends A<?>>> {}

							public class Other {
								public field: A<?>; // <-- this time using a wildcard outside the recursion cycle
							}
						""",
				"G<? extends A<? extends G<? extends A<?>>>>");
	}

	private void testCopy_recursiveUpperBounds(CharSequence code, String expectedImplicitUpperBoundOfLastWildcard) {
		try {
			Script script = parseHelper.parse(code);
			((LazyLinkingResource) script.eResource()).resolveLazyCrossReferences(CancelIndicator.NullImpl); // important!

			Wildcard wildcard = last(filter(script.eAllContents(), Wildcard.class)); // take *last* wildcard in code
			assertNotNull(wildcard);

			// make sure we can copy the wildcard without a StackOverflowException
			Wildcard cpy = TypeUtils.copy(wildcard);

			// make sure we can obtain the implicit upper bound from cpy,
			// even though it does not have a parent ParameterizedTypeRef
			assertEquals(
					expectedImplicitUpperBoundOfLastWildcard,
					cpy.getDeclaredOrImplicitUpperBound().getTypeRefAsString());

			// make sure we can copy the wildcard's parent ParameterizedTypeRef without a StackOverflowException
			ParameterizedTypeRef paramTypeRef = (ParameterizedTypeRef) wildcard.eContainer();
			ParameterizedTypeRef cpy2 = TypeUtils.copy(paramTypeRef);

			// make sure we can obtain the implicit upper bound from the parent's child wildcard
			Wildcard copiedWildcardInCpy2 = (Wildcard) cpy2.getDeclaredTypeArgs().get(0);
			assertEquals(
					expectedImplicitUpperBoundOfLastWildcard,
					copiedWildcardInCpy2.getDeclaredOrImplicitUpperBound().getTypeRefAsString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
