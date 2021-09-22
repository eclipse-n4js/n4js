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
package org.eclipse.n4js.tests.types.utils

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.TypeDefs
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.xtext.linking.lazy.LazyLinkingResource
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.util.CancelIndicator
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TypeUtilsTest {

	@Inject
	extension ParseHelper<TypeDefs>


	@Test
	def void testDeclaredSuperTypesClass() {
		val typeDefs =
		'''
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
		'''.parse();

		assertNotNull(typeDefs);

		val A = typeDefs.types.get(0);
		val B = typeDefs.types.get(1);
		val C = typeDefs.types.get(2);
		val D = typeDefs.types.get(3);
		val E = typeDefs.types.get(4);
		val F = typeDefs.types.get(5);

		assertEquals("", TypeUtils.declaredSuperTypes(A).map[typeRefAsString].join(","));
		assertEquals("A", TypeUtils.declaredSuperTypes(B).map[typeRefAsString].join(","));
		assertEquals("A,R", TypeUtils.declaredSuperTypes(C).map[typeRefAsString].join(","));
		assertEquals("B,R,I", TypeUtils.declaredSuperTypes(D).map[typeRefAsString].join(","));
		assertEquals("B,R,S,I,J", TypeUtils.declaredSuperTypes(E).map[typeRefAsString].join(","));
		assertEquals("R,S,I,J", TypeUtils.declaredSuperTypes(F).map[typeRefAsString].join(","));
	}

	@Test
	def void testDeclaredSuperTypesInterfaces() {
		val typeDefs =
		'''
		public interface I {}
		public interface J extends I {}
		public interface L extends I,K {}
		public interface K  {}
		'''.parse();

		assertNotNull(typeDefs);

		val I = typeDefs.types.get(0);
		val J = typeDefs.types.get(1);
		val L = typeDefs.types.get(2);

		assertEquals("", TypeUtils.declaredSuperTypes(I).map[typeRefAsString].join(","));
		assertEquals("I", TypeUtils.declaredSuperTypes(J).map[typeRefAsString].join(","));
		assertEquals("I,K", TypeUtils.declaredSuperTypes(L).map[typeRefAsString].join(","));
	}

	@Test
	def void testDeclaredSuperTypesOthers() {
		val typeDefs =
		'''
			undefined{}
			null{}
			primitive boolean {}
			any{}
			void{}
		'''.parse();

		assertNotNull(typeDefs);

		val undefType = typeDefs.types.get(0);
		val nullType = typeDefs.types.get(1);
		val booleanType = typeDefs.types.get(2);
		val anyType = typeDefs.types.get(2);
		val voidType = typeDefs.types.get(2);

		assertEquals("", TypeUtils.declaredSuperTypes(null).map[typeRefAsString].join(","));
		assertEquals("", TypeUtils.declaredSuperTypes(undefType).map[typeRefAsString].join(","));
		assertEquals("", TypeUtils.declaredSuperTypes(nullType).map[typeRefAsString].join(","));
		assertEquals("", TypeUtils.declaredSuperTypes(booleanType).map[typeRefAsString].join(","));
		assertEquals("", TypeUtils.declaredSuperTypes(anyType).map[typeRefAsString].join(","));
		assertEquals("", TypeUtils.declaredSuperTypes(voidType).map[typeRefAsString].join(","));
	}

	@Test
	def void testCopy_recursiveUpperBounds_direct() {
		testCopy_recursiveUpperBounds(
			'''
				public class A<T extends A<?>> {}
			''',
			"A<? extends A<?>>");
	}

	@Test
	def void testCopy_recursiveUpperBounds_indirect1() {
		testCopy_recursiveUpperBounds(
			'''
				public class X<T extends B<?>> {}
				public class Y<T extends X<?>> {}
				public class B<T extends Y<?>> {}
			''',
			"X<? extends B<?>>");
	}

	@Test
	def void testCopy_recursiveUpperBounds_indirect1_outside() {
		testCopy_recursiveUpperBounds(
			'''
				public class X<T extends B<?>> {}
				public class Y<T extends X<?>> {}
				public class B<T extends Y<?>> {}

				public class Other {
					public field: B<?>; // <-- this time using a wildcard outside the recursion cycle
				}
			''',
			"Y<? extends X<?>>");
	}

	@Test
	def void testCopy_recursiveUpperBounds_indirect2_outside() {
		testCopy_recursiveUpperBounds(
			'''
				public class G<T> {}
				public class A<T extends G<? extends A<?>>> {}

				public class Other {
					public field: A<?>; // <-- this time using a wildcard outside the recursion cycle
				}
			''',
			"G<? extends A<? extends G<? extends A<?>>>>");
	}

	def private void testCopy_recursiveUpperBounds(CharSequence code, String expectedImplicitUpperBoundOfLastWildcard) {
		val typeDefs = code.parse();
		(typeDefs.eResource as LazyLinkingResource).resolveLazyCrossReferences(CancelIndicator.NullImpl); // important!

		val wildcard = typeDefs.eAllContents.filter(Wildcard).last // take *last* wildcard in code
		assertNotNull(wildcard);

		// make sure we can copy the wildcard without a StackOverflowException
		val cpy = TypeUtils.copy(wildcard);

		// make sure we can obtain the implicit upper bound from cpy,
		// even though it does not have a parent ParameterizedTypeRef
		assertEquals(
			expectedImplicitUpperBoundOfLastWildcard,
			cpy.declaredOrImplicitUpperBound?.typeRefAsString);

		// make sure we can copy the wildcard's parent ParameterizedTypeRef without a StackOverflowException
		val paramTypeRef = wildcard.eContainer as ParameterizedTypeRef;
		val cpy2 = TypeUtils.copy(paramTypeRef);

		// make sure we can obtain the implicit upper bound from the parent's child wildcard
		val copiedWildcardInCpy2 = cpy2.typeArgs.get(0) as Wildcard;
		assertEquals(
			expectedImplicitUpperBoundOfLastWildcard,
			copiedWildcardInCpy2.declaredOrImplicitUpperBound?.typeRefAsString);
	}
}
