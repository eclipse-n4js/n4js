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
		val script = parseHelper.parse('''
			class A{}
			class C<T> {}
			class D extends C<A> {}
			class E<S> extends C<S> {}


			class F<S,T> {}
			class G<U,V> extends F<U,V> {}
			class H extends F<A, A> {}

			class I<S,T> extends F<C<T>,F<S,C<T>>> {}

		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}


	@Test
	def void testGenericClassWithMembers() {
		val script = parseHelper.parse('''
			class A{}
			class C<T> {
				public t: T;
				public foo(): t {return null;}
				public bar(t: T): void {}
			}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testClassWithGenericMethodDeclaration() {
		val script = parseHelper.parse('''
			class A{}
			class C {
				public <T> foo(): T {return null;}
				public <T> bar(t: T): void {}
			}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testGenericFunctionDeclaration() {
		val script = parseHelper.parse('''
			function <T> foo(t: T): void {}
			function <T> bar(): T {}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testParameterizedClass() {
		val script = parseHelper.parse('''
			class A{}
			class C<T> {}
			class F<S,T> {}

			var c: C<A>;
			var f: F<A,C<A>>;
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testParameterizedMethodCall() {
		val script = parseHelper.parse('''
			class A{}
			class C {
				public <T> foo(): T {}
			}
			var c: C;
			c.<A>foo();
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testParameterizedMethodCallNegative() {
		val script = parseHelper.parse('''
			class A{}
			class C {
				public <T> foo(): T {return null;}
				public <T> bar(t: T): void {}
			}

			var c: C;
			<A>c.foo();
		''');

		assertEquals(1, script.eResource.errors.size)
	}

	@Test
	def void testGenericFunctionCall() {
		val script = parseHelper.parse('''
			function <T> foo(t: T): void {}
			var a: A;
			<A>foo(a);
			bar();
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testGenericTypeDefExample() {
		val script = parseHelper.parse('''
			class Container<T> {
				private item: T;

				getItem(): T {
					return item;
				}

				setItem(item: T): void {
					this.item = item;
				}
			}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testGenericTypeUsageExample() {
		val script = parseHelper.parse('''
			import { Container } from "p/Container"

			var stringContainer: Container<String> = new Container();
			stringContainer.setItem("Hello");
			var s: String = stringContainer.getItem();
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void testGenericMultipleTypesBindingExample() {
		val script = parseHelper.parse('''
			class A{}
			class B{}
			class C extends A{}

			class G<S, T extends A, U extends B> {
			}

			var x: G<Number,C,B>;
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}
}
