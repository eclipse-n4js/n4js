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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

/**
 * Parser tests for N4 specific type expressions. Test methods with suffix "example" are taken from the N4JS spec.
 */
public class N4_04_02_TypeExpressionsTest extends AbstractParserTest {

	static String TYPE_DEFS_PREFIX = """
				class A {}
				class B extends A {}
				class C extends B {}
				class D {}

				class G<T> {}
				class H<T,S> {}

				interface R {}
				interface I {}
				enum E{ LITERAL } // cannot be empty

			""";

	@Test
	public void testTypeDefPrefix() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX);
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testStructuralType() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					var r: ~Object with {a: A;b: B;};
					var rd: ~A with{b: B;}+;
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testStructuralTypeES4_OLWithFields() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					var r: ~Object with {a: A;b: B;};
					var rd: ~A with{b: B;}+;
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testStructuralTypeES4_OLWithGetter() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """

					var rg: ~A with { get b(): string; };
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testStructuralTypeES4_OLWithSetter() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """

					var rs: ~A with { set b(s: string); };
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testStructuralTypeES4_OLWithMethod() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """

					var rm1: ~A with { foo(a: string); };
					var rm2: ~A with { foo(): string; };
					var rm3: ~A with { foo(a); };
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testParameterizedTypeRef() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					var a: A, g: G<A>, h: H<A,B>, ga: G<? extends A>, gb: G<? super B>, r: R, i: I, e: E;
					var ad: A+;
					var gd: G<A>+;
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testAny() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """

					var x: any;
					var xdyn: any+;
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testThisType() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					class X { foo(): void { var copy: This; } }
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testFunctionType() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					var f: {function()},
						map: {function(b: B):A},
						weird: {function(b: B):{function(a: A):C}};
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testFunctionTypeES4() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					var f: {function()},
						map: {function(b: B):A},
						weird: {function(b: B):{function(a: A):C}};
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testUnionType() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					var u: union{A,B,C};
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testIntersectionType() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					var i: intersection{A,D};
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testVoid() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					function foo(): void{};
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testTypeArguments() throws Exception {
		Script script = parseHelper.parse(TYPE_DEFS_PREFIX + """


					var gTypeRef: G<A>;
					var gNested: G<G<G<A>>>;
					var gWildcard: G<?>;
					var gUpperA: G<? extends A>;
					var gLowerB: G<? super B>;


				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void testTypeParameters() throws Exception {
		Script script = parseHelper.parse("""
					class A{}
					class G<T> {}
					class GUpperA<T extends A> {}
					class GUpperA<T extends G<T>> {}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

}
