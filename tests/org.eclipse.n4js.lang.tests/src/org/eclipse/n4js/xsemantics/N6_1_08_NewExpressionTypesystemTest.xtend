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
package org.eclipse.n4js.xsemantics

import com.google.inject.Inject
import org.eclipse.n4js.validation.JavaScriptVariant
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression

/**
 * TODO add tests for generic constructors (has been postponed in IDE-159; now part of IDE-654).
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class N6_1_08_NewExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject
	extension ValidationTestHelper


	@Test
	def void testDirectReferenceToExplicitlyDefinedConstructorViaClassName() {
		'''
		class A {}
		class B extends A {}
		class C extends B {}

		class X {
			constructor(param: B) {}
		}

		var a: A;
		var b: B;
		var c: C;

		new X(a);		// fail
		new X(b);		// ok
		new X(c);		// ok
		'''.assertValidationErrors(
		'''
		A is not a subtype of B.
		''')
	}

	@Test
	def void testDirectReferenceToExplicitlyDefinedConstructorViaVariable() {
		'''
		class A {}
		class B extends A {}
		class C extends B {}

		class X {
			constructor(param: B) {}
		}

		function func(ctor: constructor{X}) {
			var a: A;
			var b: B;
			var c: C;

			new ctor(a);		// fail
			new ctor(b);		// ok
			new ctor(c);		// ok
		}
		'''.assertValidationErrors(
		'''
		A is not a subtype of B.
		''')
	}

	@Test
	def void testIndirectReferenceToExplicitlyDefinedConstructorViaClassName() {
		'''
		class A {}
		class B extends A {}
		class C extends B {}

		class X {
			constructor(param: B) {}
		}
		class Y extends X {
		}

		var a: A;
		var b: B;
		var c: C;

		new Y(a);		// fail
		new Y(b);		// ok
		new Y(c);		// ok
		'''.assertValidationErrors(
		'''
		A is not a subtype of B.
		''')
	}

	@Test
	def void testIndirectReferenceToExplicitlyDefinedConstructorViaVariable() {
		'''
		class A {}
		class B extends A {}
		class C extends B {}

		class X {
			constructor(param: B) {}
		}
		class Y extends X {
		}

		function func(ctor: constructor{Y}) {
			var a: A;
			var b: B;
			var c: C;

			new ctor(a);		// fail
			new ctor(b);		// ok
			new ctor(c);		// ok
		}
		'''.assertValidationErrors(
		'''
		A is not a subtype of B.
		''')
	}

	@Test
	def void testParameterizedNew_SimpleCases() {
		'''
		class G<T> {
		  get(): T { return null }
		}

		class A {}
		class B extends A {}
		class X {}

		var gA = new G<A>();
		var a: A = gA.get();
		var gB = new G<B>();
		var b1: A = gB.get();
		var b2: B = gB.get();		// makes sure gB.get() is really typed to B
		var gX = new G<X>();
		var x: A = gX.get();			// omg, should fail!!
		'''.assertValidationErrors(
		'''
		X is not a subtype of A.
		''')
	}

	@Test
	def void testParameterizedNew_TypeVariableInCtor() {
		'''
		class C<T> {
			constructor(p: T) {}
		}

		new C<string>("hello");
		new C<string>(42);			// should fail
		'''.assertValidationErrors(
		'''
		int is not a subtype of string.
		''')
	}

	@Test
	def void testNewWithStructuralThis_DirectReferenceToExplicitCtor() {

		'''
		class C {

			public f: string;

			constructor(spec: ~~this) {}
		}

		new C( {f: "hello"} )		// ok
		new C( {f: 42} )			// fail
		'''.assertValidationErrors(
		'''
		~Object with { f: int } is not a structural subtype of ~~C: f failed: int is not equal to string.
		''')
	}

	@Test
	def void testNewWithStructuralThis_IndirectReferenceToExplicitCtor() {

		'''
		class C {

			public f: string;

			constructor(spec: ~~this) {}
		}
		class D extends C {}
		class E extends D {
			public n: number;
		}

		new E( {f: "hello", n: 42} )		// ok
		new E( {f: "hello", n: "oops"} )	// fail
		'''.assertValidationErrors(
		'''
		~Object with { f: string; n: string } is not a structural subtype of ~~E: n failed: string is not equal to number.
		''')
	}

/*  NOTE: deactivated following test case after removing constructor(~~this) from N4Object ...

	@Test
	def void testNewWithStructuralThis_ReferenceToDefaultCtor() {

		'''
		class C {

			public string f;

												// no constructor!
		}
		class D extends C {}
		class E extends D {
			public number n;
		}

		new E( {f: "hello", n: 42} )		// ok
		new E( {f: "hello", n: "oops"} )	// fail
		'''.assertValidationErrors(
		'''
		ObjectLiteral is not a subtype of ~~this[E].
		''')
	}
*/

	def private assertValidationErrors(CharSequence input, CharSequence expectedErrors) {
		val script = createScript(JavaScriptVariant.n4js,input.toString)
		val issues = script.validate();
		issues.assertErrorMessages(expectedErrors)
	}
}
