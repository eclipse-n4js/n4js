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
 package org.eclipse.n4js.smoke.tests

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class GeneratedSmokeTestCases3 {

	@Inject ParseHelper<Script> parseHelper

	// Dummy method - needs to be called to record the expression and schedule it for smoke testing
	def protected void assertNoException(CharSequence expression) throws Exception {
		parseHelper.parse(expression)
	}


	@Test
	def void test_0600() {
		'''
			var x = 1
			~x
		'''.assertNoException
	}

	@Test
	def void test_0601() {
		'''
			var x = 1;
			x++;
		'''.assertNoException
	}

	@Test
	def void test_0602() {
		'''
			var x = 1
			x++
		'''.assertNoException
	}

	@Test
	def void test_0603() {
		'''
			const x = 1
		'''.assertNoException
	}

	@Test
	def void test_0604() {
		'''
			var x: any = 1
		'''.assertNoException
	}

	@Test
	def void test_0605() {
		'''
			var x = 1
		'''.assertNoException
	}

	@Test
	def void test_0606() {
		'''
			var foo = 5,
						class A { a = new A(); }
		'''.assertNoException
	}

	@Test
	def void test_0607() {
		'''
			var x: any= 1
		'''.assertNoException
	}

	@Test
	def void test_0608() {
		'''
			var foo = 5,/*
						*/
						class A { a = new A(); }
		'''.assertNoException
	}

	@Test
	def void test_0609() {
		'''
			var foo = 5,
						class A { a = new A(); }
		'''.assertNoException
	}

	@Test
	def void test_0610() {
		'''
			var foo = 5, /* hello */
						class A { a = new A(); }
		'''.assertNoException
	}

	@Test
	def void test_0611() {
		'''
			var foo = 5;
					   console.log(foo)
		'''.assertNoException
	}

	@Test
	def void test_0612() {
		'''
			var foo = 5
					   console.log(foo)
		'''.assertNoException
	}

	@Test
	def void test_0613() {
		'''
			var foo = 5,
					   console.log(foo)
		'''.assertNoException
	}

	@Test
	def void test_0614() {
		'''
			var foo = 5,
					   qualified.Name x = (foo)
		'''.assertNoException
	}

	@Test
	def void test_0615() {
		'''
			class C {
				var;
				catch;
				if;
				class;
				true;
				private;
			}

			var c: C= new C();
			c.var = 1;
			c.catch = c.if;
			if (c.class == c.true) {}
			c.private = 0;
		'''.assertNoException
	}

	@Test
	def void test_0616() {
		'''
			class C {
				var;
				catch;
				if;
				class;
				true;
				private;
			}
		'''.assertNoException
	}

	@Test
	def void test_0617() {
		'''
			class A{}
			var a = new A();
		'''.assertNoException
	}

	@Test
	def void test_0618() {
		'''
			export class A {}
		'''.assertNoException
	}

	@Test
	def void test_0619() {
		'''
			import A from "A"
			var a = new A();
		'''.assertNoException
	}

	@Test
	def void test_0620() {
		'''
			import A from "A"
			var a;
			if (a instanceof A) {
			}
		'''.assertNoException
	}

	@Test
	def void test_0621() {
		'''
			function foo() {};
			var a = foo;
			foo = "Hi";
			a();
		'''.assertNoException
	}

	@Test
	def void test_0622() {
		'''
			__proto__: while (true) { continue __proto__; }
		'''.assertNoException
	}

	@Test
	def void test_0623() {
		'''
			done: while (true) { continue done }
		'''.assertNoException
	}

	@Test
	def void test_0624() {
		'''
			while (true) { continue; }
		'''.assertNoException
	}

	@Test
	def void test_0625() {
		'''
			done: while (true) { continue done; }
		'''.assertNoException
	}

	@Test
	def void test_0626() {
		'''
			while (true) { continue }
		'''.assertNoException
	}

	@Test
	def void test_0627() {
		'''
			export public class A {
				@Internal protected a1(): void {}
			}
			export public class B extends A {
				@Internal protected b1(): void {}
			}
			export public class C extends B {
				@Internal protected c1(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0628() {
		'''
			import * as N from "A"
			class D extends N.B {
				m(N.r: C): void {
					r.b1()
				}
			}
			class E extends D {
			}
		'''.assertNoException
	}

	@Test
	def void test_0629() {
		'''
			export public class C {
				project m(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0630() {
		'''
			import * as N from "A"
			var N.c: C;
			c.m()
		'''.assertNoException
	}

	@Test
	def void test_0631() {
		'''
			export public class C {
				@Internal protected m(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0632() {
		'''
			export public class C {
				protected m(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0633() {
		'''
			export public class C {
				@Internal public m(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0634() {
		'''
			export public class C {
				public m(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0635() {
		'''
			export public class C {
				/* project is default */ m(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0636() {
		'''
			export public class C {
				private m(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0637() {
		'''
			export public class C {
				project m(): void {}
			}
			var c: C;
			c.m()
		'''.assertNoException
	}

	@Test
	def void test_0638() {
		'''
			export public class C {
				@Internal protected m(): void {}
			}
			var c: C;
			c.m()
		'''.assertNoException
	}

	@Test
	def void test_0639() {
		'''
			export public class C {
				protected m(): void {}
			}
			var c: C;
			c.m()
		'''.assertNoException
	}

	@Test
	def void test_0640() {
		'''
			export public class C {
				@Internal public m(): void {}
			}
			var c: C;
			c.m()
		'''.assertNoException
	}

	@Test
	def void test_0641() {
		'''
			export public class C {
				public m(): void {}
			}
			var c: C;
			c.m()
		'''.assertNoException
	}

	@Test
	def void test_0642() {
		'''
			export public class C {
				/* project is default */ m(): void {}
			}
			var c: C;
			c.m()
		'''.assertNoException
	}

	@Test
	def void test_0643() {
		'''
			export public class C {
				private m(): void {}
			}
			var c: C;
			c.m()
		'''.assertNoException
	}

	@Test
	def void test_0644() {
		'''
			export public class A {
				@Internal protected a(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0645() {
		'''
			import * as N from "A"
			class B extends N.A {
				m(): void {
					this.a()
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0646() {
		'''
			import * as N from "A"
			class B extends N.A {
			}
			class C {
				m(b: B): void {
					b.a()
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0647() {
		'''
			export public class A {
				 protected a1(): void {}
			}
			export public class B extends A {
				 protected b1(): void {}
			}
			export public class C extends B {
				 protected c1(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0648() {
		'''
			import * as N from "A"
			class D extends N.B {
				m(N.r: B): void {
					r.b1()
				}
			}
			class E extends D {
			}
		'''.assertNoException
	}

	@Test
	def void test_0649() {
		'''
			import * as N from "A"
			class D extends N.B {
				m(N.r: B): void {
					r.a1()
				}
			}
			class E extends D {
			}
		'''.assertNoException
	}

	@Test
	def void test_0650() {
		'''
			import * as N from "A"
			class D extends N.B {
				m(N.r: C): void {
					r.c1()
				}
			}
			class E extends D {
			}
		'''.assertNoException
	}

	@Test
	def void test_0651() {
		'''
			import * as N from "A"
			class D extends N.B {
				m(D r): void {
					r.b1()
				}
			}
			class E extends D {
			}
		'''.assertNoException
	}

	@Test
	def void test_0652() {
		'''
			import * as N from "A"
			class D extends N.B {
				m(E r): void {
					r.b1()
				}
			}
			class E extends D {
			}
		'''.assertNoException
	}

	@Test
	def void test_0653() {
		'''
			y ? 1 : 2
		'''.assertNoException
	}

	@Test
	def void test_0654() {
		'''
			x && y ? 1 : 2
		'''.assertNoException
	}

	@Test
	def void test_0655() {
		'''

			export @Internal public class C {}
		'''.assertNoException
	}

	@Test
	def void test_0656() {
		'''

			@Internal
			export public class C {}
		'''.assertNoException
	}

	@Test
	def void test_0657() {
		'''

			@Internal
			export @Internal public class C {}
		'''.assertNoException
	}

	@Test
	def void test_0658() {
		'''

			@Internal
			export @Internal public class C {
				@Internal
				m(){}
			}
		'''.assertNoException
	}

	@Test
	def void test_0659() {
		'''

			export public interface ITires {}
			export public class Fuel {}

			export public class BiasPly implements ITires {}
			export public class Gasoline extends Fuel {}

			@Binder
			@Bind(ITires, BiasPly)
			@Bind(Fuel, Gasoline)
			export public class OldificationBinder {}
		'''.assertNoException
	}

	@Test
	def void test_0660() {
		'''

			@Internal
			export @Internal public class C {
				m(){}
			}
		'''.assertNoException
	}

	@Test
	def void test_0661() {
		'''

			@IDEBUG(1, "1")
			@IDEBUG(2, "2")
			export @IDEBUG(3, "3") @IDEBUG(4, "4") public class C {
			    @IDEBUG(5, "5")
			    @IDEBUG(6, "6")
			    m(){}
			}
		'''.assertNoException
	}

	@Test
	def void test_0662() {
		'''

			export public interface ITires {}
			export public class Fuel {}

			export public class BiasPly implements ITires {}
			export public class Gasoline extends Fuel {}

			@Binder
			export @Bind(ITires, BiasPly) @Bind(Fuel, Gasoline) public class OldificationBinder {}
		'''.assertNoException
	}

	@Test
	def void test_0663() {
		'''
			/[^\/]+/\
		'''.assertNoException
	}

	@Test
	def void test_0664() {
		'''
			/(?<=\.) {2,}(?=[A-Z])/
		'''.assertNoException
	}

	@Test
	def void test_0665() {
		'''
			/(?=a)+/
		'''.assertNoException
	}

	@Test
	def void test_0666() {
		'''
			/^*/
		'''.assertNoException
	}

	@Test
	def void test_0667() {
		'''
			public class C {
				recursive(): C {
					this.recursive().recursive
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0668() {
		'''
			function a() {}
			a = null
		'''.assertNoException
	}

	@Test
	def void test_0669() {
		'''
			function a() {}
			if (null instanceof a) {}
		'''.assertNoException
	}

	@Test
	def void test_0670() {
		'''
			public class C {
				D m1() {}
			}
			public class D extends C {
				C m2() {
					this.m1
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0671() {
		'''
			function a() {}
			new a()
		'''.assertNoException
	}

	@Test
	def void test_0672() {
		'''
			var a = function() {}
			new a()
		'''.assertNoException
	}

	@Test
	def void test_0673() {
		'''
			class A {}

			class C {
				private _data: A= null;

				public get data() {
					if (this._data==null) {
						this._data = new A();
					}
					return this._data;
				}

				public set data(data: A) {
					this._data = data;
					this.notifyListeners();
				}

				notifyListeners(): void {
					// ...
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0674() {
		'''
			throw { message: "Error" }
		'''.assertNoException
	}

	@Test
	def void test_0675() {
		'''
			throw x * y
		'''.assertNoException
	}

	@Test
	def void test_0676() {
		'''
			throw x;
		'''.assertNoException
	}

	@Test
	def void test_0677() {
		'''
			var C = class {
				a: any;
				b: any;
				c: any;
			};
		'''.assertNoException
	}

	@Test
	def void test_0678() {
		'''
			class X {}
			class Y {
				abc: any= null;
				foox(): any {
					this.abc;
				}
			}
			class Z extends Y {
			}
		'''.assertNoException
	}

	@Test
	def void test_0679() {
		'''
			0o2$;
		'''.assertNoException
	}

	@Test
	def void test_0680() {
		'''
			.14i
		'''.assertNoException
	}

	@Test
	def void test_0681() {
		'''
			0X04_;
		'''.assertNoException
	}

	@Test
	def void test_0682() {
		'''
			0x10$;
		'''.assertNoException
	}

	@Test
	def void test_0683() {
		'''
			0X1A_;
		'''.assertNoException
	}

	@Test
	def void test_0684() {
		'''
			3_
		'''.assertNoException
	}

	@Test
	def void test_0685() {
		'''
			5$
		'''.assertNoException
	}

	@Test
	def void test_0686() {
		'''
			0i
		'''.assertNoException
	}

	@Test
	def void test_0687() {
		'''
			0o129;
		'''.assertNoException
	}

	@Test
	def void test_0688() {
		'''
			1.492417830e-10a
		'''.assertNoException
	}

	@Test
	def void test_0689() {
		'''
			3.14159m
		'''.assertNoException
	}

	@Test
	def void test_0690() {
		'''
			0b1_;
		'''.assertNoException
	}

	@Test
	def void test_0691() {
		'''
			0b
		'''.assertNoException
	}

	@Test
	def void test_0692() {
		'''
			0b_;
		'''.assertNoException
	}

	@Test
	def void test_0693() {
		'''
			0b2;
		'''.assertNoException
	}

	@Test
	def void test_0694() {
		'''
			02$;
		'''.assertNoException
	}

	@Test
	def void test_0695() {
		'''
			6.02214179e+23a
		'''.assertNoException
	}

	@Test
	def void test_0696() {
		'''
			0e+100_
		'''.assertNoException
	}

	@Test
	def void test_0697() {
		'''
			0x0G
		'''.assertNoException
	}

	@Test
	def void test_0698() {
		'''
			0x0$;
		'''.assertNoException
	}

	@Test
	def void test_0699() {
		'''
			42a
		'''.assertNoException
	}

	@Test
	def void test_0700() {
		'''
			0o012o;
		'''.assertNoException
	}

	@Test
	def void test_0701() {
		'''
			"use strict";0o129;
		'''.assertNoException
	}

	@Test
	def void test_0702() {
		'''
			\u0030
		'''.assertNoException
	}

	@Test
	def void test_0703() {
		'''
			0x100q;
		'''.assertNoException
	}

	@Test
	def void test_0704() {
		'''
			0xabch;
		'''.assertNoException
	}

	@Test
	def void test_0705() {
		'''
			0xdefi;
		'''.assertNoException
	}

	@Test
	def void test_0706() {
		'''
			"use strict";0129;
		'''.assertNoException
	}

	@Test
	def void test_0707() {
		'''
			0012o;
		'''.assertNoException
	}

	@Test
	def void test_0708() {
		'''
			x
		'''.assertNoException
	}

	@Test
	def void test_0709() {
		'''
			x, y
		'''.assertNoException
	}

	@Test
	def void test_0710() {
		'''
			var a; \u0061
		'''.assertNoException
	}

	@Test
	def void test_0711() {
		'''
			var aa; a\u0061
		'''.assertNoException
	}

	@Test
	def void test_0712() {
		'''
			var aa; \u0061a
		'''.assertNoException
	}

	@Test
	def void test_0713() {
		'''
			var a = class A {
				b = class B {
					bar(): any {
						var c = a.field1
						a.foo()
						return null;
					}
				};
				static field1: any;
				static foo(): any { return null; }
			}
		'''.assertNoException
	}

	@Test
	def void test_0714() {
		'''
			var a = class {
				b = class {
					bar(): any {
						var c = a.field1
						a.foo()
						return null;
					}
				};
				static field1: any;
				static foo(): any {return null;}
			}
		'''.assertNoException
	}

	@Test
	def void test_0715() {
		'''
			var a = class {
				field1: any;
				foo(): any {
					return this.field1;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0716() {
		'''
			var a = class {
			    items_: any;
			    public indexOf(item: any): void {
			    	return _.indexOf(this.items_, item);
			    }
			}
			var _ = class _ {
				static indexOf(p1: any, p2: any): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0717() {
		'''
			export var a = class {
			    items_: any;
			    public indexOf(item: any): void {
			    	return _.indexOf(this.items_, item);
			    }
			}
			class _ {
				static indexOf(p1: any, p2: any): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0718() {
		'''
			public class C {
				c: C= syntax error
			}
		'''.assertNoException
	}

	@Test
	def void test_0719() {
		'''
			{ /* empty block followed by unclosed regex*/ } /

		'''.assertNoException
	}

	@Test
	def void test_0720() {
		'''
			{ /* empty block followed by unclosed regex*/ } /=

		'''.assertNoException
	}

	@Test
	def void test_0721() {
		'''
			var A<?
		'''.assertNoException
	}

	@Test
	def void test_0722() {
		'''
			var A<
		'''.assertNoException
	}

	@Test
	def void test_0723() {
		'''
			class C {}
			class D<A,> extends C {}
		'''.assertNoException
	}

	@Test
	def void test_0724() {
		'''
			var A<? extends
		'''.assertNoException
	}

	@Test
	def void test_0725() {
		'''
			var A<? super
		'''.assertNoException
	}

	@Test
	def void test_0726() {
		'''
			var s};
		'''.assertNoException
	}

	@Test
	def void test_0727() {
		'''
			var v = a + b,
			        c + d;
		'''.assertNoException
	}

	@Test
	def void test_0728() {
		'''
			var v = a + b,
			        class A { a = new A(); };
		'''.assertNoException
	}

	@Test
	def void test_0729() {
		'''
			var ~Object with {b;}
			var s;
		'''.assertNoException
	}

	@Test
	def void test_0730() {
		'''
			var ~Object with {
			var s;
		'''.assertNoException
	}

	@Test
	def void test_0731() {
		'''
			class A {
				method() {
					new class {
						var x: string;
					};
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0732() {
		'''
			new class {
				var x: string;
			};
		'''.assertNoException
	}

	@Test
	def void test_0733() {
		'''
			var testArray = ["A""];
			testArray[0];
		'''.assertNoException
	}

	@Test
	def void test_0734() {
		'''
			var ~Object with {a: string; b: string;;
			var s: string;
			s = r.a;
			s = r.b;
		'''.assertNoException
	}

	@Test
	def void test_0735() {
		'''
			var ~Object with {a: string; b: string;}
			var s: string;
			s = r.a;
			s = r.b;
		'''.assertNoException
	}

	@Test
	def void test_0736() {
		'''
			export var ~Object with {a: string; b: string;}
			var s: string;
			s = r.a;
			s = r.b;
		'''.assertNoException
	}

	@Test
	def void test_0737() {
		'''
			var ~Object with {}
			var s;
		'''.assertNoException
	}

	@Test
	def void test_0738() {
		'''
			class A {}
			class B extends A {}
			class C extends B {}
			class D {}

			class G<T> {}
			class H<T,S> {}

			interface R {}
			interface I {}
			enum E{ LITERAL } // cannot be empty


			var ~Object with {a: A;b: B;;
			var ~A with{b: B;}+ rd;
		'''.assertNoException
	}

	@Test
	def void test_0739() {
		'''
			class A {}
			class B extends A {}
			class C extends B {}
			class D {}

			class G<T> {}
			class H<T,S> {}

			interface R {}
			interface I {}
			enum E{ LITERAL } // cannot be empty


			var ~Object with {a: A;b: B;}
			var ~A with{b: B;}+ rd;
		'''.assertNoException
	}

	@Test
	def void test_0740() {
		'''
			class A {
				method() {
					new class {
						var;
						x: string;
					};
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0741() {
		'''
			var class;
			var 1a = null;
		'''.assertNoException
	}

	@Test
	def void test_0742() {
		'''
			async function  asyncParty(): number {
			    return 4;
			}

			async function doSomeStuff() {
			    var hello = "hi"
			    await asyncParty();
			    hello.charAt(0)

			    await asyncParty()
			    hello.charAt(0)

			    await asyncParty()
			}
		'''.assertNoException
	}

	@Test
	def void test_0743() {
		'''
			a >>>= b =
		'''.assertNoException
	}

	@Test
	def void test_0744() {
		'''
			`${}\
		'''.assertNoException
	}

	@Test
	def void test_0745() {
		'''
			"\
		'''.assertNoException
	}

	@Test
	def void test_0746() {
		'''
			'\
		'''.assertNoException
	}

	@Test
	def void test_0747() {
		'''
			var v = {
			    a: (b = "".match(/^a/)),
			    c: null
			};
		'''.assertNoException
	}

	@Test
	def void test_0748() {
		'''
			function f({function():type?} x) {}
		'''.assertNoException
	}

	@Test
	def void test_0749() {
		'''
			function f({function():constructor?} x) {}
		'''.assertNoException
	}

	@Test
	def void test_0750() {
		'''
			class C {}
		'''.assertNoException
	}

	@Test
	def void test_0751() {
		'''
			export @Internal public var any c
		'''.assertNoException
	}

	@Test
	def void test_0752() {
		'''
			export public function c() {}
		'''.assertNoException
	}

	@Test
	def void test_0753() {
		'''
			export class C {}
		'''.assertNoException
	}

	@Test
	def void test_0754() {
		'''
			function c() {}
		'''.assertNoException
	}

	@Test
	def void test_0755() {
		'''
			export function c() {}
		'''.assertNoException
	}

	@Test
	def void test_0756() {
		'''
			export project class C {}
		'''.assertNoException
	}

	@Test
	def void test_0757() {
		'''
			export @Internal public class C {}
		'''.assertNoException
	}

	@Test
	def void test_0758() {
		'''
			export public class C {}
		'''.assertNoException
	}

	@Test
	def void test_0759() {
		'''
			export project var any c
		'''.assertNoException
	}

	@Test
	def void test_0760() {
		'''
			export public var any c
		'''.assertNoException
	}

	@Test
	def void test_0761() {
		'''
			export @Internal public function c() {}
		'''.assertNoException
	}

	@Test
	def void test_0762() {
		'''
			export project function c() {}
		'''.assertNoException
	}

	@Test
	def void test_0763() {
		'''
			export var any c
		'''.assertNoException
	}

	@Test
	def void test_0764() {
		'''
			var any c
		'''.assertNoException
	}

	@Test
	def void test_0765() {
		'''
			x && y
		'''.assertNoException
	}

	@Test
	def void test_0766() {
		'''
			x || y && z
		'''.assertNoException
	}

	@Test
	def void test_0767() {
		'''
			x || y ^ z
		'''.assertNoException
	}

	@Test
	def void test_0768() {
		'''
			x || y || z
		'''.assertNoException
	}

	@Test
	def void test_0769() {
		'''
			x || y
		'''.assertNoException
	}

	@Test
	def void test_0770() {
		'''
			x && y && z
		'''.assertNoException
	}

	@Test
	def void test_0771() {
		'''
			var x = /[a-z]/i
		'''.assertNoException
	}

	@Test
	def void test_0772() {
		'''
			var x = /[x-z]/i
		'''.assertNoException
	}

	@Test
	def void test_0773() {
		'''
			var x = /[a-c]/i
		'''.assertNoException
	}

	@Test
	def void test_0774() {
		'''
			var x = /[P QR]/i
		'''.assertNoException
	}

	@Test
	def void test_0775() {
		'''
			var x = /foo\/bar/
		'''.assertNoException
	}

	@Test
	def void test_0776() {
		'''
			var x = /=([^=\s])+/g
		'''.assertNoException
	}

	@Test
	def void test_0777() {
		'''
			var x = /42/g.test
		'''.assertNoException
	}

	@Test
	def void test_0778() {
		'''
			日本語 = []
		'''.assertNoException
	}

	@Test
	def void test_0779() {
		'''
			T‿ = []
		'''.assertNoException
	}

	@Test
	def void test_0780() {
		'''
			T‌ = []
		'''.assertNoException
	}

	@Test
	def void test_0781() {
		'''
			T‍ = []
		'''.assertNoException
	}

	@Test
	def void test_0782() {
		'''
			ⅣⅡ = []
		'''.assertNoException
	}

	@Test
	def void test_0783() {
		'''
			ⅣⅡ = []
		'''.assertNoException
	}

	@Test
	def void test_0784() {
		'''
			var	@Undefined x;
			x.selector;
		'''.assertNoException
	}

	@Test
	def void test_0785() {
		'''
			function f() {
				"use strict"
				this.selector;
			}
		'''.assertNoException
	}

	@Test
	def void test_0786() {
		'''
			function f() {
				this.undefined;
			}
		'''.assertNoException
	}

	@Test
	def void test_0787() {
		'''
			undefined.selector;
		'''.assertNoException
	}

	@Test
	def void test_0788() {
		'''
			class C {
				public s: string;
			}
			function f(~C with { n: number; } p) {
				p.s;
				p.n;
			}
		'''.assertNoException
	}

	@Test
	def void test_0789() {
		'''
			class C {
				public s: string;
			}
			function f(): ~C with { n: number; } { return null; }
			var p = f();
			p.s;
			p.n;
		'''.assertNoException
	}

	@Test
	def void test_0790() {
		'''
			class C {
				public s: string;

				constructor(~this with { n: number; } p) {
					p.n;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0791() {
		'''
			class C {
				public s: string;

				constructor(~this with { n: number; } p) {
					p.s;
					p.n;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0792() {
		'''
			class C {
				public s: string;

				constructor(~this p) {
					p.s;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0793() {
		'''
			x = [,,]
		'''.assertNoException
	}

	@Test
	def void test_0794() {
		'''
			x / y
		'''.assertNoException
	}

	@Test
	def void test_0795() {
		'''
			x % y
		'''.assertNoException
	}

	@Test
	def void test_0796() {
		'''
			x * y
		'''.assertNoException
	}

	@Test
	def void test_0797() {
		'''
			x * y * z
		'''.assertNoException
	}

	@Test
	def void test_0798() {
		'''
			public class A {
				private a: A;
				public 	b: A= null;

				foo() {}
				public bar(p: A): any { }
			}

			public abstract class C<T extends A> {
				t: T;
				a: A;

				static STATICVAR: any;


				static C<T> create() { return null; }
				<S> foo( union{A,C} p) {}
				protected abstract bar()
				baz(p: A?) {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0799() {
		'''
			class C {
				data: any;

				constructor(data: any) {
					this.data = data;
				}

				foo(): void { }
			}
		'''.assertNoException
	}

	@Test
	def void test_0800() {
		'''
			class C1 {
				s: any= null;
				s: any = null;
				s = null;
			}
		'''.assertNoException
	}

	@Test
	def void test_0801() {
		'''
			class C1 {
				string ['m'] = "1";
				m: string= "2";
				string ["@abc"] = "3";
				string [    '@abc1'    ] = "4";
				string [ /*comment*/ '@abc2'] = "5";
				string ['@abc3'  /*comment*/ ] = "6";
			}
		'''.assertNoException
	}

	@Test
	def void test_0802() {
		'''
			class A {}

			class C {
				data: A= null;

				get A() {
					if (data==null) {
						data = new A();
					}
					return this.data;
				}

				set A(data: A) {
					this.data = data;
					this.notifyListeners();
				}

				notifyListeners(): void {
					// ...
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0803() {
		'''
			class C {}
		'''.assertNoException
	}

	@Test
	def void test_0804() {
		'''
			public class A {

				f1(): void {}

				abstract f1() : void
				abstract f2() : void;

				@Internal
				public abstract f3() : void

				protected f4(): any { return null; }

				public f5() : any { return null; }

				@Internal
				public <T> f6(): T { return null; }

				@Internal
				public f7(p1: any, p2: any) : any { return p1; }
				private <T> f8(p1: T, p2:T): void { }

				@Internal
				public f9(p1: any, p2: any?):any {}
				@Internal
				public f10(p1: any, p2: any): any {}
				@Internal
				public f11(p1: any?, p2: any?): void {}

				static s1(): void {}
				static s1(): any { return null; }
				@Internal
				public static <T> s1(): T { return null; }
			}
		'''.assertNoException
	}

	@Test
	def void test_0805() {
		'''
			public class A {}
			public class B {}

			public class C<T extends A> {
				<S> foo( union{A,B} p) {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0806() {
		'''
			interface I {
				foo(): void
			}
			interface R {
				bar(): void {}
			}
			class C{}
			class X extends C implements R, I {
				@Overrides
				foo(): void;
			}
		'''.assertNoException
	}

	@Test
	def void test_0807() {
		'''
			x >= y
		'''.assertNoException
	}

	@Test
	def void test_0808() {
		'''
			x <= y
		'''.assertNoException
	}

	@Test
	def void test_0809() {
		'''
			x < y
		'''.assertNoException
	}

	@Test
	def void test_0810() {
		'''
			x < y < z
		'''.assertNoException
	}

	@Test
	def void test_0811() {
		'''
			x in y
		'''.assertNoException
	}

	@Test
	def void test_0812() {
		'''
			x instanceof y
		'''.assertNoException
	}

	@Test
	def void test_0813() {
		'''
			x > y
		'''.assertNoException
	}

	@Test
	def void test_0814() {
		'''
			(function(){ return
			 x*y })
		'''.assertNoException
	}

	@Test
	def void test_0815() {
		'''
			(function(){ return x * y })
		'''.assertNoException
	}

	@Test
	def void test_0816() {
		'''
			(function(){ return; })
		'''.assertNoException
	}

	@Test
	def void test_0817() {
		'''
			(function(){ return x
			 * y })
		'''.assertNoException
	}

	@Test
	def void test_0818() {
		'''
			(function(){ return })
		'''.assertNoException
	}

	@Test
	def void test_0819() {
		'''
			(function(){ return x })
		'''.assertNoException
	}

	@Test
	def void test_0820() {
		'''
			(function(){ return x; })
		'''.assertNoException
	}

	@Test
	def void test_0821() {
		'''
			var a = a || {};
			a.b
		'''.assertNoException
	}

	@Test
	def void test_0822() {
		'''
			var u =
			u.either
		'''.assertNoException
	}

	@Test
	def void test_0823() {
		'''
			interface R1 {
				f(p: number): void {}
			}
			interface R2 {
				f(p: string): void {
			class C implements R1, R2 {}
		'''.assertNoException
	}

	@Test
	def void test_0824() {
		'''
			interface R1 {
				x: string;
			}
			class S {
				x(): void {
			class C1 extends S implements R1 {}
		'''.assertNoException
	}

	@Test
	def void test_0825() {
		'''
			import * as N2 from 'a/X';
			import * as 1 from 'a/X';
			var N1.x: X;
		'''.assertNoException
	}

	@Test
	def void test_0826() {
		'''
			import X1 from 'a/X';
			import X2 from 'a/X';
			var X1 x = X2;
		'''.assertNoException
	}

	@Test
	def void test_0827() {
		'''
			function(a){
				return a.b
			}
		'''.assertNoException
	}

	@Test
	def void test_0828() {
		'''
			class C<T> {
			    boolean b = null
			    x() {
				    spec
			    }
			    y(n: number?) {
			    }
			}
		'''.assertNoException
	}

	@Test
	def void test_0829() {
		'''
			class C<T> {
			    m(? p) {
			    }
			    n() {
			        this.m();
			    }
			}
		'''.assertNoException
	}

	@Test
	def void test_0830() {
		'''
			var target = {
				s: "hello",
				set x
		'''.assertNoException
	}

	@Test
	def void test_0831() {
		'''
			class C {}
			function f( ~C with {
				s: string;
				n;
				f1(string)
				f2(): void
				f3(boolean, C): number
				get y(): C
				get z()
				set a(
		'''.assertNoException
	}

	@Test
	def void test_0832() {
		'''
			class C {}
			function f( ~C with {
				s: string;
				n;
				f1(string)
				f2(): void
				f3(boolean, C): number
				get y(): C
				get z()
				set a()
			} p) {}
		'''.assertNoException
	}

	@Test
	def void test_0833() {
		'''
			var fo = 5,/*
						*/
						class A { a = new A(); }
		'''.assertNoException
	}

	@Test
	def void test_0834() {
		'''
			public class C {
				f0;
				private f1;
				project
				@Internal protected f4;
				protected f5;
				@Internal public f6;
				public f7;
			}
		'''.assertNoException
	}

	@Test
	def void test_0835() {
		'''
			var ol = {
				et target() {return null}
			}
			ol.target=null;
		'''.assertNoException
	}

	@Test
	def void test_0836() {
		'''
			var a = function foo() {
			  foo();
			};
		'''.assertNoException
	}

	@Test
	def void test_0837() {
		'''
			var a = function foo() {
			};
			foo();
		'''.assertNoException
	}

	@Test
	def void test_0838() {
		'''
			var a = function foo( x ) {
			    var b = function bar( y ) {
			        if (y==1) {
			            foo(2);
			            bar(0);
			        }
			    }
			    if (x==1) {
			        b(1);
			    }
			};
			a(1);
		'''.assertNoException
	}

	@Test
	def void test_0839() {
		'''
			var s: string= ''
			s.toLowerCase().toLowerCase()
		'''.assertNoException
	}

	@Test
	def void test_0840() {
		'''
			var boolean b = true
		'''.assertNoException
	}

	@Test
	def void test_0841() {
		'''
			var Date d
		'''.assertNoException
	}

	@Test
	def void test_0842() {
		'''
			var undefined u // may be a type error later
		'''.assertNoException
	}

	@Test
	def void test_0843() {
		'''
			var n: number= 1
		'''.assertNoException
	}

	@Test
	def void test_0844() {
		'''
			var String s = ''
			s.toLowerCase().toLowerCase()
		'''.assertNoException
	}

	@Test
	def void test_0845() {
		'''
			var Boolean b = true
		'''.assertNoException
	}

	@Test
	def void test_0846() {
		'''
			var RegExp r = /abc/g
		'''.assertNoException
	}

	@Test
	def void test_0847() {
		'''
			var Number n = 1
		'''.assertNoException
	}

	@Test
	def void test_0848() {
		'''
			var s: any= 1;
			var x =1;
			s as number/x;
		'''.assertNoException
	}

	@Test
	def void test_0849() {
		'''
			class A {
				d() {}
			}
			var s: any= new A();
			s as A;
		'''.assertNoException
	}

	@Test
	def void test_0850() {
		'''
			export public class Dup {}
		'''.assertNoException
	}

	@Test
	def void test_0851() {
		'''
			import Dup from "A"
			import Dup from "B"
			var d = new Dup();
		'''.assertNoException
	}

	@Test
	def void test_0852() {
		'''
			export var String dup = ""
		'''.assertNoException
	}

	@Test
	def void test_0853() {
		'''
			import dup from "A"
			import dup from "B"
			var d = dup
		'''.assertNoException
	}

	@Test
	def void test_0854() {
		'''
			var String dup = ""
		'''.assertNoException
	}

	@Test
	def void test_0855() {
		'''
			class A {}
		'''.assertNoException
	}

	@Test
	def void test_0856() {
		'''
			import * as Namespace from "A"
			var s = ""
		'''.assertNoException
	}

	@Test
	def void test_0857() {
		'''
			import A from "A"
			var s = ""
		'''.assertNoException
	}

	@Test
	def void test_0858() {
		'''
			import * as N from "A"
			var N.a: A= null
		'''.assertNoException
	}

	@Test
	def void test_0859() {
		'''
			import A from "A"
			var a: A= null
		'''.assertNoException
	}

	@Test
	def void test_0860() {
		'''
			import * as N from "A"
			var a = N.A
		'''.assertNoException
	}

	@Test
	def void test_0861() {
		'''
			import A from "A"
			var a = A
		'''.assertNoException
	}

	@Test
	def void test_0862() {
		'''
			import * as N from "A"
			var N.a: A= ""
		'''.assertNoException
	}

	@Test
	def void test_0863() {
		'''
			export var String s = ""
		'''.assertNoException
	}

	@Test
	def void test_0864() {
		'''
			import s from "A"
			var x = s
		'''.assertNoException
	}

	@Test
	def void test_0865() {
		'''
			export @Internal public var String s = ""
		'''.assertNoException
	}

	@Test
	def void test_0866() {
		'''
			import * as N from "A"
			var x = N.s
		'''.assertNoException
	}

	@Test
	def void test_0867() {
		'''
			export public var String s = ""
		'''.assertNoException
	}

	@Test
	def void test_0868() {
		'''
			export function f() {}
		'''.assertNoException
	}

	@Test
	def void test_0869() {
		'''
			import f from "A"
			var x = f
		'''.assertNoException
	}

	@Test
	def void test_0870() {
		'''
			export @Internal public function f() {}
		'''.assertNoException
	}

	@Test
	def void test_0871() {
		'''
			import * as N from "A"
			var x = N.f
		'''.assertNoException
	}

	@Test
	def void test_0872() {
		'''
			export public function f() {}
		'''.assertNoException
	}

	@Test
	def void test_0873() {
		'''
			export public enum E { Literal }
		'''.assertNoException
	}

	@Test
	def void test_0874() {
		'''
			import * as N from "A"
			var x = N.E.Literal
		'''.assertNoException
	}

	@Test
	def void test_0875() {
		'''
			export @Internal public enum E { Literal }
		'''.assertNoException
	}

	@Test
	def void test_0876() {
		'''
			class Base {
				@Internal public m(): void {}
			}
			export public class Sub extends Base {}
		'''.assertNoException
	}

	@Test
	def void test_0877() {
		'''
			import * as N from "A"
			var N.Sub s;
			s.m()
		'''.assertNoException
	}

	@Test
	def void test_0878() {
		'''
			export class C {
				m(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0879() {
		'''
			export class C {
				private m(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0880() {
		'''
			export class A {}
			export class B extends A {
				protected m(): void {}
			}
			export class C extends B {}
		'''.assertNoException
	}

	@Test
	def void test_0881() {
		'''
			var fibonacci = function* (numbers: number) {
			    var pre: number= 0, cur = 1;
			    while (numbers-- > 0) {
			        [ pre, cur ] = [ cur, pre + cur ];
			        yield cur;
			    }
			};
		'''.assertNoException
	}

	@Test
	def void test_0882() {
		'''
			class C {}
			function f( ~C with {
				s: string;
				n;
				f1(string)
				f2(): void
				f3(boolean, C): number
				get y(): C
				get z()
				set a(boolean)
			} p) {}
		'''.assertNoException
	}

	@Test
	def void test_0883() {
		'''
			class C {}
			class D {
				~C with { s; } x;
				f( ~C with { s; } p): void {}
			}
			function f( ~C with { s; } p) {}
		'''.assertNoException
	}

	@Test
	def void test_0884() {
		'''
			class C {}
			class D {
				~C with { s: string; } x;
				f( ~C with { s: string; } p): void {}
			}
			function f( ~C with { s: string; } p) {}
		'''.assertNoException
	}

	@Test
	def void test_0885() {
		'''
			public class C extends D implements R {
			}
			public class D implements I {
			}
			public interface I {
				public D m()
			}
			public interface R extends R2, I {
			}
			public interface R2 {
				public C m()
			}
		'''.assertNoException
	}

	@Test
	def void test_0886() {
		'''
			public class C extends D implements R {
			}
			public class D implements I {
				public C m()
			}
			public interface I {
				public C m()
			}
			public interface R2 {
				public C m()
			}
			public interface R extends R2, I {
				public C m()
			}
		'''.assertNoException
	}

	@Test
	def void test_0887() {
		'''
			public class C extends D implements I {
			}
			public class D implements R, J {
			}
			public interface I {
				public C m()
			}
			public interface J extends I {
				public D m()
			}
			public interface K {
				public C m()
			}
			public interface R extends K {
			}
		'''.assertNoException
	}

	@Test
	def void test_0888() {
		'''
			public class C extends D implements R {
			}
			public class D implements I {
				public C m()
			}
			public interface I {
				public C m()
			}
			public interface R extends R2, I {
			}
			public interface R2 {
				public C m()
			}
		'''.assertNoException
	}

	@Test
	def void test_0889() {
		'''
			public class C extends D implements R {
			}
			public class D {
				public C m()
			}
			public interface I {
				public C m()
			}
			public interface R extends I {
			}
		'''.assertNoException
	}

	@Test
	def void test_0890() {
		'''
			public class C {
				public C exists()
			}
		'''.assertNoException
	}

	@Test
	def void test_0891() {
		'''
			public class C {
				public C dup()
				public C dup()
			}
		'''.assertNoException
	}

	@Test
	def void test_0892() {
		'''
			public class C {
			}
		'''.assertNoException
	}

	@Test
	def void test_0893() {
		'''
			public class C {
				public exists: C;
			}
		'''.assertNoException
	}

	@Test
	def void test_0894() {
		'''
			public class C extends D {
			}
			public class D {
				public C exists()
			}
		'''.assertNoException
	}

	@Test
	def void test_0895() {
		'''
			public class C extends D {
			}
			public class D {
				public C dup()
				public C dup()
			}
		'''.assertNoException
	}

	@Test
	def void test_0896() {
		'''
			public class C extends D {
			}
			public class D {
			}
		'''.assertNoException
	}

	@Test
	def void test_0897() {
		'''
			public class C extends D {
			}
			public class D extends C {
				public C exists()
			}
		'''.assertNoException
	}

	@Test
	def void test_0898() {
		'''
			public class C extends D {
			}
			public class D extends C {
			}
		'''.assertNoException
	}

	@Test
	def void test_0899() {
		'''
			public class C extends C {
			}
		'''.assertNoException
	}

}
