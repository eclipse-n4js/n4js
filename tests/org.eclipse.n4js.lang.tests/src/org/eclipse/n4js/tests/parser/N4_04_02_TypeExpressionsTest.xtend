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
package org.eclipse.n4js.tests.parser

import org.junit.Test

/**
 * Parser tests for N4 specific type expressions. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_04_02_TypeExpressionsTest extends AbstractParserTest {

	static val TYPE_DEFS_PREFIX = '''
		class A {}
		class B extends A {}
		class C extends B {}
		class D {}

		class G<T> {}
		class H<T,S> {}

		interface R {}
		interface I {}
		enum E{ LITERAL } // cannot be empty

	'''

	@Test
	def void testTypeDefPrefix() {
		val script = parseHelper.parse(TYPE_DEFS_PREFIX);
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testStructuralType() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			var r: ~Object with {a: A;b: B;};
			var rd: ~A with{b: B;}+;
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testStructuralTypeES4_OLWithFields() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			var r: ~Object with {a: A;b: B;};
			var rd: ~A with{b: B;}+;
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testStructuralTypeES4_OLWithGetter() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»
			var rg: ~A with { get b(): string; };
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testStructuralTypeES4_OLWithSetter() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»
			var rs: ~A with { set b(s: string); };
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testStructuralTypeES4_OLWithMethod() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»
			var rm1: ~A with { foo(a: string); };
			var rm2: ~A with { foo(): string; };
			var rm3: ~A with { foo(a); };
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testParameterizedTypeRef() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			var a: A, g: G<A>, h: H<A,B>, ga: G<? extends A>, gb: G<? super B>, r: R, i: I, e: E;
			var ad: A+;
			var gd: G<A>+;
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testAny() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»
			var x: any;
			var xdyn: any+;
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testThisType() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			class X { foo(): void { var copy: This; } }
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testFunctionType() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			var f: {function()},
				map: {function(b: B):A},
				weird: {function(b: B):{function(a: A):C}};
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testFunctionTypeES4() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			var f: {function()},
				map: {function(b: B):A},
				weird: {function(b: B):{function(a: A):C}};
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}



	@Test
	def void testUnionType() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			var u: union{A,B,C};
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testIntersectionType() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			var i: intersection{A,D};
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testVoid() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			function foo(): void{};
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testTypeArguments() {
		val script = parseHelper.parse('''
			«TYPE_DEFS_PREFIX»

			var gTypeRef: G<A>;
			var gNested: G<G<G<A>>>;
			var gWildcard: G<?>;
			var gUpperA: G<? extends A>;
			var gLowerB: G<? super B>;


		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testTypeParameters() {
		val script = parseHelper.parse('''
			class A{}
			class G<T> {}
			class GUpperA<T extends A> {}
			class GUpperA<T extends G<T>> {}
		''');
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}




}
