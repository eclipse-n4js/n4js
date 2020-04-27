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
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_04_12_5_GenericsTest extends AbstractParserTest{


	@Test
	def void testGenericClassDeclarations() {
		val script = '''
			class A{}
			class C<T> {}
			class D extends C<A> {}
			class E<S> extends C<S> {}


			class F<S,T> {}
			class G<U,V> extends F<U,V> {}
			class H extends F<A, A> {}

			class I<S,T> extends F<C<T>,F<S,C<T>>> {}

		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}


	@Test
	def void testGenericClassWithMembers() {
		val script = '''
			class A{}
			class C<T> {
				public t: T;
				public foo(): t {return null;}
				public bar(t: T): void {}
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testClassWithGenericMethodDeclaration() {
		val script = '''
			class A{}
			class C {
				public <T> foo(): T {return null;}
				public <T> bar(t: T): void {}
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testGenericFunctionDeclaration() {
		val script = '''
			function <T> foo(t: T): void {}
			function <T> bar(): T {}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testParameterizedClass() {
		val script = '''
			class A{}
			class C<T> {}
			class F<S,T> {}

			var c: C<A>;
			var f: F<A,C<A>>;
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testParameterizedMethodCall() {
		val script = '''
			class A{}
			class C {
				public <T> foo(): T {}
			}
			var c: C;
			c.<A>foo();
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testParameterizedMethodCallNegative() {
		val script = '''
			class A{}
			class C {
				public <T> foo(): T {return null;}
				public <T> bar(t: T): void {}
			}

			var c: C;
			<A>c.foo();
		'''.parse

		assertEquals(1, script.eResource.errors.size)
	}

	@Test
	def void testGenericFunctionCall() {
		val script = '''
			function <T> foo(t: T): void {}
			var a: A;
			this.<A>foo(a);
			bar();
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testGenericTypeDefExample() {
		val script = '''
			class Container<T> {
				private item: T;

				getItem(): T {
					return item;
				}

				setItem(item: T): void {
					this.item = item;
				}
			}
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testGenericTypeUsageExample() {
		val script = '''
			import { Container } from "p/Container"

			var stringContainer: Container<String> = new Container();
			stringContainer.setItem("Hello");
			var s: String = stringContainer.getItem();
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testGenericMultipleTypesBindingExample() {
		val script = '''
			class A{}
			class B{}
			class C extends A{}

			class G<S, T extends A, U extends B> {
			}

			var x: G<Number,C,B>;
		'''.parse

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}
}
