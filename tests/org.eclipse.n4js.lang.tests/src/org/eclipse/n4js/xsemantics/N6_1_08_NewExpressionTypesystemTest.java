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
package org.eclipse.n4js.xsemantics;

import java.util.List;

import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * TODO add tests for generic constructors (has been postponed in IDE-159; now part of IDE-654).
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class N6_1_08_NewExpressionTypesystemTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void testDirectReferenceToExplicitlyDefinedConstructorViaClassName() {
		assertValidationErrors("""
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
				""",
				"""
						A is not a subtype of B.
						""");
	}

	@Test
	public void testDirectReferenceToExplicitlyDefinedConstructorViaVariable() {
		assertValidationErrors("""
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
				""",
				"""
						A is not a subtype of B.
						""");
	}

	@Test
	public void testIndirectReferenceToExplicitlyDefinedConstructorViaClassName() {
		assertValidationErrors("""
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
				""",
				"""
						A is not a subtype of B.
						""");
	}

	@Test
	public void testIndirectReferenceToExplicitlyDefinedConstructorViaVariable() {
		assertValidationErrors("""
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
				""",
				"""
						A is not a subtype of B.
						""");
	}

	@Test
	public void testParameterizedNew_SimpleCases() {
		assertValidationErrors("""
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
				""",
				"""
						X is not a subtype of A.
						""");
	}

	@Test
	public void testParameterizedNew_TypeVariableInCtor() {
		assertValidationErrors("""
				class C<T> {
					constructor(p: T) {}
				}

				new C<string>("hello");
				new C<string>(42);			// should fail
				""",
				"""
						42 is not a subtype of string.
						""");
	}

	@Test
	public void testNewWithStructuralThis_DirectReferenceToExplicitCtor() {
		assertValidationErrors("""
				class C {

					public f: string;

					constructor(spec: ~~this) {}
				}

				new C( {f: "hello"} )		// ok
				new C( {f: 42} )			// fail
				""",
				"""
						~Object with { f: int } is not a structural subtype of ~~C: f failed: int is not equal to string.
						""");
	}

	@Test
	public void testNewWithStructuralThis_IndirectReferenceToExplicitCtor() {
		assertValidationErrors("""
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
				""",
				"""
						~Object with { f: string; n: string } is not a structural subtype of ~~E: n failed: string is not equal to number.
						""");
	}

	//@formatter:off
/*  NOTE: deactivated following test case after removing constructor(~~this) from N4Object ...

	@Test
	public void testNewWithStructuralThis_ReferenceToDefaultCtor() {
		assertValidationErrors("""
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
		""",
		"""
		ObjectLiteral is not a subtype of ~~this[E].
		""");
	}
*/
	//@formatter:on

	private void assertValidationErrors(CharSequence input, CharSequence expectedErrors) {
		Script script = createScript(JavaScriptVariant.n4js, input.toString());
		List<Issue> issues = valTestHelper.validate(script);
		assertErrorMessages(issues, expectedErrors);
	}
}
