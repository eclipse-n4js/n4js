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
package org.eclipse.n4js.smoke.tests;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class GeneratedSmokeTestCases3 {

	@Inject
	ParseHelper<Script> parseHelper;

	// Dummy method - needs to be called to record the expression and schedule it for smoke testing
	protected void assertNoException(CharSequence expression) throws Exception {
		parseHelper.parse(expression);
	}

	@Test
	public void test_0600() throws Exception {
		assertNoException("""
					var x = 1
					~x
				""");
	}

	@Test
	public void test_0601() throws Exception {
		assertNoException("""
					var x = 1;
					x++;
				""");
	}

	@Test
	public void test_0602() throws Exception {
		assertNoException("""
					var x = 1
					x++
				""");
	}

	@Test
	public void test_0603() throws Exception {
		assertNoException("""
					const x = 1
				""");
	}

	@Test
	public void test_0604() throws Exception {
		assertNoException("""
					var x: any = 1
				""");
	}

	@Test
	public void test_0605() throws Exception {
		assertNoException("""
					var x = 1
				""");
	}

	@Test
	public void test_0606() throws Exception {
		assertNoException("""
					var foo = 5,
								class A { a = new A(); }
				""");
	}

	@Test
	public void test_0607() throws Exception {
		assertNoException("""
					var x: any= 1
				""");
	}

	@Test
	public void test_0608() throws Exception {
		assertNoException("""
					var foo = 5,/*
								*/
								class A { a = new A(); }
				""");
	}

	@Test
	public void test_0609() throws Exception {
		assertNoException("""
					var foo = 5,
								class A { a = new A(); }
				""");
	}

	@Test
	public void test_0610() throws Exception {
		assertNoException("""
					var foo = 5, /* hello */
								class A { a = new A(); }
				""");
	}

	@Test
	public void test_0611() throws Exception {
		assertNoException("""
					var foo = 5;
							   console.log(foo)
				""");
	}

	@Test
	public void test_0612() throws Exception {
		assertNoException("""
					var foo = 5
							   console.log(foo)
				""");
	}

	@Test
	public void test_0613() throws Exception {
		assertNoException("""
					var foo = 5,
							   console.log(foo)
				""");
	}

	@Test
	public void test_0614() throws Exception {
		assertNoException("""
					var foo = 5,
							   qualified.Name x = (foo)
				""");
	}

	@Test
	public void test_0615() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0616() throws Exception {
		assertNoException("""
					class C {
						var;
						catch;
						if;
						class;
						true;
						private;
					}
				""");
	}

	@Test
	public void test_0617() throws Exception {
		assertNoException("""
					class A{}
					var a = new A();
				""");
	}

	@Test
	public void test_0618() throws Exception {
		assertNoException("""
					export class A {}
				""");
	}

	@Test
	public void test_0619() throws Exception {
		assertNoException("""
					import A from "A"
					var a = new A();
				""");
	}

	@Test
	public void test_0620() throws Exception {
		assertNoException("""
					import A from "A"
					var a;
					if (a instanceof A) {
					}
				""");
	}

	@Test
	public void test_0621() throws Exception {
		assertNoException("""
					function foo() {};
					var a = foo;
					foo = "Hi";
					a();
				""");
	}

	@Test
	public void test_0622() throws Exception {
		assertNoException("""
					__proto__: while (true) { continue __proto__; }
				""");
	}

	@Test
	public void test_0623() throws Exception {
		assertNoException("""
					done: while (true) { continue done }
				""");
	}

	@Test
	public void test_0624() throws Exception {
		assertNoException("""
					while (true) { continue; }
				""");
	}

	@Test
	public void test_0625() throws Exception {
		assertNoException("""
					done: while (true) { continue done; }
				""");
	}

	@Test
	public void test_0626() throws Exception {
		assertNoException("""
					while (true) { continue }
				""");
	}

	@Test
	public void test_0627() throws Exception {
		assertNoException("""
					export public class A {
						@Internal protected a1(): void {}
					}
					export public class B extends A {
						@Internal protected b1(): void {}
					}
					export public class C extends B {
						@Internal protected c1(): void {}
					}
				""");
	}

	@Test
	public void test_0628() throws Exception {
		assertNoException("""
					import * as N from "A"
					class D extends N.B {
						m(N.r: C): void {
							r.b1()
						}
					}
					class E extends D {
					}
				""");
	}

	@Test
	public void test_0629() throws Exception {
		assertNoException("""
					export public class C {
						project m(): void {}
					}
				""");
	}

	@Test
	public void test_0630() throws Exception {
		assertNoException("""
					import * as N from "A"
					var N.c: C;
					c.m()
				""");
	}

	@Test
	public void test_0631() throws Exception {
		assertNoException("""
					export public class C {
						@Internal protected m(): void {}
					}
				""");
	}

	@Test
	public void test_0632() throws Exception {
		assertNoException("""
					export public class C {
						protected m(): void {}
					}
				""");
	}

	@Test
	public void test_0633() throws Exception {
		assertNoException("""
					export public class C {
						@Internal public m(): void {}
					}
				""");
	}

	@Test
	public void test_0634() throws Exception {
		assertNoException("""
					export public class C {
						public m(): void {}
					}
				""");
	}

	@Test
	public void test_0635() throws Exception {
		assertNoException("""
					export public class C {
						/* project is default */ m(): void {}
					}
				""");
	}

	@Test
	public void test_0636() throws Exception {
		assertNoException("""
					export public class C {
						private m(): void {}
					}
				""");
	}

	@Test
	public void test_0637() throws Exception {
		assertNoException("""
					export public class C {
						project m(): void {}
					}
					var c: C;
					c.m()
				""");
	}

	@Test
	public void test_0638() throws Exception {
		assertNoException("""
					export public class C {
						@Internal protected m(): void {}
					}
					var c: C;
					c.m()
				""");
	}

	@Test
	public void test_0639() throws Exception {
		assertNoException("""
					export public class C {
						protected m(): void {}
					}
					var c: C;
					c.m()
				""");
	}

	@Test
	public void test_0640() throws Exception {
		assertNoException("""
					export public class C {
						@Internal public m(): void {}
					}
					var c: C;
					c.m()
				""");
	}

	@Test
	public void test_0641() throws Exception {
		assertNoException("""
					export public class C {
						public m(): void {}
					}
					var c: C;
					c.m()
				""");
	}

	@Test
	public void test_0642() throws Exception {
		assertNoException("""
					export public class C {
						/* project is default */ m(): void {}
					}
					var c: C;
					c.m()
				""");
	}

	@Test
	public void test_0643() throws Exception {
		assertNoException("""
					export public class C {
						private m(): void {}
					}
					var c: C;
					c.m()
				""");
	}

	@Test
	public void test_0644() throws Exception {
		assertNoException("""
					export public class A {
						@Internal protected a(): void {}
					}
				""");
	}

	@Test
	public void test_0645() throws Exception {
		assertNoException("""
					import * as N from "A"
					class B extends N.A {
						m(): void {
							this.a()
						}
					}
				""");
	}

	@Test
	public void test_0646() throws Exception {
		assertNoException("""
					import * as N from "A"
					class B extends N.A {
					}
					class C {
						m(b: B): void {
							b.a()
						}
					}
				""");
	}

	@Test
	public void test_0647() throws Exception {
		assertNoException("""
					export public class A {
						 protected a1(): void {}
					}
					export public class B extends A {
						 protected b1(): void {}
					}
					export public class C extends B {
						 protected c1(): void {}
					}
				""");
	}

	@Test
	public void test_0648() throws Exception {
		assertNoException("""
					import * as N from "A"
					class D extends N.B {
						m(N.r: B): void {
							r.b1()
						}
					}
					class E extends D {
					}
				""");
	}

	@Test
	public void test_0649() throws Exception {
		assertNoException("""
					import * as N from "A"
					class D extends N.B {
						m(N.r: B): void {
							r.a1()
						}
					}
					class E extends D {
					}
				""");
	}

	@Test
	public void test_0650() throws Exception {
		assertNoException("""
					import * as N from "A"
					class D extends N.B {
						m(N.r: C): void {
							r.c1()
						}
					}
					class E extends D {
					}
				""");
	}

	@Test
	public void test_0651() throws Exception {
		assertNoException("""
					import * as N from "A"
					class D extends N.B {
						m(D r): void {
							r.b1()
						}
					}
					class E extends D {
					}
				""");
	}

	@Test
	public void test_0652() throws Exception {
		assertNoException("""
					import * as N from "A"
					class D extends N.B {
						m(E r): void {
							r.b1()
						}
					}
					class E extends D {
					}
				""");
	}

	@Test
	public void test_0653() throws Exception {
		assertNoException("""
					y ? 1 : 2
				""");
	}

	@Test
	public void test_0654() throws Exception {
		assertNoException("""
					x && y ? 1 : 2
				""");
	}

	@Test
	public void test_0655() throws Exception {
		assertNoException("""

					export @Internal public class C {}
				""");
	}

	@Test
	public void test_0656() throws Exception {
		assertNoException("""

					@Internal
					export public class C {}
				""");
	}

	@Test
	public void test_0657() throws Exception {
		assertNoException("""

					@Internal
					export @Internal public class C {}
				""");
	}

	@Test
	public void test_0658() throws Exception {
		assertNoException("""

					@Internal
					export @Internal public class C {
						@Internal
						m(){}
					}
				""");
	}

	@Test
	public void test_0659() throws Exception {
		assertNoException("""

					export public interface ITires {}
					export public class Fuel {}

					export public class BiasPly implements ITires {}
					export public class Gasoline extends Fuel {}

					@Binder
					@Bind(ITires, BiasPly)
					@Bind(Fuel, Gasoline)
					export public class OldificationBinder {}
				""");
	}

	@Test
	public void test_0660() throws Exception {
		assertNoException("""

					@Internal
					export @Internal public class C {
						m(){}
					}
				""");
	}

	@Test
	public void test_0661() throws Exception {
		assertNoException("""

					@IDEBUG(1, "1")
					@IDEBUG(2, "2")
					export @IDEBUG(3, "3") @IDEBUG(4, "4") public class C {
					    @IDEBUG(5, "5")
					    @IDEBUG(6, "6")
					    m(){}
					}
				""");
	}

	@Test
	public void test_0662() throws Exception {
		assertNoException("""

					export public interface ITires {}
					export public class Fuel {}

					export public class BiasPly implements ITires {}
					export public class Gasoline extends Fuel {}

					@Binder
					export @Bind(ITires, BiasPly) @Bind(Fuel, Gasoline) public class OldificationBinder {}
				""");
	}

	@Test
	public void test_0663() throws Exception {
		assertNoException("""
					/[^\\/]+/\\
				""");
	}

	@Test
	public void test_0664() throws Exception {
		assertNoException("""
					/(?<=\\.) {2,}(?=[A-Z])/
				""");
	}

	@Test
	public void test_0665() throws Exception {
		assertNoException("""
					/(?=a)+/
				""");
	}

	@Test
	public void test_0666() throws Exception {
		assertNoException("""
					/^*/
				""");
	}

	@Test
	public void test_0667() throws Exception {
		assertNoException("""
					public class C {
						recursive(): C {
							this.recursive().recursive
						}
					}
				""");
	}

	@Test
	public void test_0668() throws Exception {
		assertNoException("""
					function a() {}
					a = null
				""");
	}

	@Test
	public void test_0669() throws Exception {
		assertNoException("""
					function a() {}
					if (null instanceof a) {}
				""");
	}

	@Test
	public void test_0670() throws Exception {
		assertNoException("""
					public class C {
						D m1() {}
					}
					public class D extends C {
						C m2() {
							this.m1
						}
					}
				""");
	}

	@Test
	public void test_0671() throws Exception {
		assertNoException("""
					function a() {}
					new a()
				""");
	}

	@Test
	public void test_0672() throws Exception {
		assertNoException("""
					var a = function() {}
					new a()
				""");
	}

	@Test
	public void test_0673() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0674() throws Exception {
		assertNoException("""
					throw { message: "Error" }
				""");
	}

	@Test
	public void test_0675() throws Exception {
		assertNoException("""
					throw x * y
				""");
	}

	@Test
	public void test_0676() throws Exception {
		assertNoException("""
					throw x;
				""");
	}

	@Test
	public void test_0677() throws Exception {
		assertNoException("""
					var C = class {
						a: any;
						b: any;
						c: any;
					};
				""");
	}

	@Test
	public void test_0678() throws Exception {
		assertNoException("""
					class X {}
					class Y {
						abc: any= null;
						foox(): any {
							this.abc;
						}
					}
					class Z extends Y {
					}
				""");
	}

	@Test
	public void test_0679() throws Exception {
		assertNoException("""
					0o2$;
				""");
	}

	@Test
	public void test_0680() throws Exception {
		assertNoException("""
					.14i
				""");
	}

	@Test
	public void test_0681() throws Exception {
		assertNoException("""
					0X04_;
				""");
	}

	@Test
	public void test_0682() throws Exception {
		assertNoException("""
					0x10$;
				""");
	}

	@Test
	public void test_0683() throws Exception {
		assertNoException("""
					0X1A_;
				""");
	}

	@Test
	public void test_0684() throws Exception {
		assertNoException("""
					3_
				""");
	}

	@Test
	public void test_0685() throws Exception {
		assertNoException("""
					5$
				""");
	}

	@Test
	public void test_0686() throws Exception {
		assertNoException("""
					0i
				""");
	}

	@Test
	public void test_0687() throws Exception {
		assertNoException("""
					0o129;
				""");
	}

	@Test
	public void test_0688() throws Exception {
		assertNoException("""
					1.492417830e-10a
				""");
	}

	@Test
	public void test_0689() throws Exception {
		assertNoException("""
					3.14159m
				""");
	}

	@Test
	public void test_0690() throws Exception {
		assertNoException("""
					0b1_;
				""");
	}

	@Test
	public void test_0691() throws Exception {
		assertNoException("""
					0b
				""");
	}

	@Test
	public void test_0692() throws Exception {
		assertNoException("""
					0b_;
				""");
	}

	@Test
	public void test_0693() throws Exception {
		assertNoException("""
					0b2;
				""");
	}

	@Test
	public void test_0694() throws Exception {
		assertNoException("""
					02$;
				""");
	}

	@Test
	public void test_0695() throws Exception {
		assertNoException("""
					6.02214179e+23a
				""");
	}

	@Test
	public void test_0696() throws Exception {
		assertNoException("""
					0e+100_
				""");
	}

	@Test
	public void test_0697() throws Exception {
		assertNoException("""
					0x0G
				""");
	}

	@Test
	public void test_0698() throws Exception {
		assertNoException("""
					0x0$;
				""");
	}

	@Test
	public void test_0699() throws Exception {
		assertNoException("""
					42a
				""");
	}

	@Test
	public void test_0700() throws Exception {
		assertNoException("""
					0o012o;
				""");
	}

	@Test
	public void test_0701() throws Exception {
		assertNoException("""
					"use strict";0o129;
				""");
	}

	@Test
	public void test_0702() throws Exception {
		assertNoException("""
					\\u0030
				""");
	}

	@Test
	public void test_0703() throws Exception {
		assertNoException("""
					0x100q;
				""");
	}

	@Test
	public void test_0704() throws Exception {
		assertNoException("""
					0xabch;
				""");
	}

	@Test
	public void test_0705() throws Exception {
		assertNoException("""
					0xdefi;
				""");
	}

	@Test
	public void test_0706() throws Exception {
		assertNoException("""
					"use strict";0129;
				""");
	}

	@Test
	public void test_0707() throws Exception {
		assertNoException("""
					0012o;
				""");
	}

	@Test
	public void test_0708() throws Exception {
		assertNoException("""
					x
				""");
	}

	@Test
	public void test_0709() throws Exception {
		assertNoException("""
					x, y
				""");
	}

	@Test
	public void test_0710() throws Exception {
		assertNoException("""
					var a; \\u0061
				""");
	}

	@Test
	public void test_0711() throws Exception {
		assertNoException("""
					var aa; a\\u0061
				""");
	}

	@Test
	public void test_0712() throws Exception {
		assertNoException("""
					var aa; \\u0061a
				""");
	}

	@Test
	public void test_0713() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0714() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0715() throws Exception {
		assertNoException("""
					var a = class {
						field1: any;
						foo(): any {
							return this.field1;
						}
					}
				""");
	}

	@Test
	public void test_0716() throws Exception {
		assertNoException("""
					var a = class {
					    items_: any;
					    public indexOf(item: any): void {
					    	return _.indexOf(this.items_, item);
					    }
					}
					var _ = class _ {
						static indexOf(p1: any, p2: any): void {}
					}
				""");
	}

	@Test
	public void test_0717() throws Exception {
		assertNoException("""
					export var a = class {
					    items_: any;
					    public indexOf(item: any): void {
					    	return _.indexOf(this.items_, item);
					    }
					}
					class _ {
						static indexOf(p1: any, p2: any): void {}
					}
				""");
	}

	@Test
	public void test_0718() throws Exception {
		assertNoException("""
					public class C {
						c: C= syntax error
					}
				""");
	}

	@Test
	public void test_0719() throws Exception {
		assertNoException("""
					{ /* empty block followed by unclosed regex*/ } /

				""");
	}

	@Test
	public void test_0720() throws Exception {
		assertNoException("""
					{ /* empty block followed by unclosed regex*/ } /=

				""");
	}

	@Test
	public void test_0721() throws Exception {
		assertNoException("""
					var A<?
				""");
	}

	@Test
	public void test_0722() throws Exception {
		assertNoException("""
					var A<
				""");
	}

	@Test
	public void test_0723() throws Exception {
		assertNoException("""
					class C {}
					class D<A,> extends C {}
				""");
	}

	@Test
	public void test_0724() throws Exception {
		assertNoException("""
					var A<? extends
				""");
	}

	@Test
	public void test_0725() throws Exception {
		assertNoException("""
					var A<? super
				""");
	}

	@Test
	public void test_0726() throws Exception {
		assertNoException("""
					var s};
				""");
	}

	@Test
	public void test_0727() throws Exception {
		assertNoException("""
					var v = a + b,
					        c + d;
				""");
	}

	@Test
	public void test_0728() throws Exception {
		assertNoException("""
					var v = a + b,
					        class A { a = new A(); };
				""");
	}

	@Test
	public void test_0729() throws Exception {
		assertNoException("""
					var ~Object with {b;}
					var s;
				""");
	}

	@Test
	public void test_0730() throws Exception {
		assertNoException("""
					var ~Object with {
					var s;
				""");
	}

	@Test
	public void test_0731() throws Exception {
		assertNoException("""
					class A {
						method() {
							new class {
								var x: string;
							};
						}
					}
				""");
	}

	@Test
	public void test_0732() throws Exception {
		assertNoException("""
					new class {
						var x: string;
					};
				""");
	}

	@Test
	public void test_0733() throws Exception {
		assertNoException("""
					var testArray = ["A""];
					testArray[0];
				""");
	}

	@Test
	public void test_0734() throws Exception {
		assertNoException("""
					var ~Object with {a: string; b: string;;
					var s: string;
					s = r.a;
					s = r.b;
				""");
	}

	@Test
	public void test_0735() throws Exception {
		assertNoException("""
					var ~Object with {a: string; b: string;}
					var s: string;
					s = r.a;
					s = r.b;
				""");
	}

	@Test
	public void test_0736() throws Exception {
		assertNoException("""
					export var ~Object with {a: string; b: string;}
					var s: string;
					s = r.a;
					s = r.b;
				""");
	}

	@Test
	public void test_0737() throws Exception {
		assertNoException("""
					var ~Object with {}
					var s;
				""");
	}

	@Test
	public void test_0738() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0739() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0740() throws Exception {
		assertNoException("""
					class A {
						method() {
							new class {
								var;
								x: string;
							};
						}
					}
				""");
	}

	@Test
	public void test_0741() throws Exception {
		assertNoException("""
					var class;
					var 1a = null;
				""");
	}

	@Test
	public void test_0742() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0743() throws Exception {
		assertNoException("""
					a >>>= b =
				""");
	}

	@Test
	public void test_0744() throws Exception {
		assertNoException("""
					`${}\\
				""");
	}

	@Test
	public void test_0745() throws Exception {
		assertNoException("""
					"\\
				""");
	}

	@Test
	public void test_0746() throws Exception {
		assertNoException("""
					'\\
				""");
	}

	@Test
	public void test_0747() throws Exception {
		assertNoException("""
					var v = {
					    a: (b = "".match(/^a/)),
					    c: null
					};
				""");
	}

	@Test
	public void test_0748() throws Exception {
		assertNoException("""
					function f({function():type?} x) {}
				""");
	}

	@Test
	public void test_0749() throws Exception {
		assertNoException("""
					function f({function():constructor?} x) {}
				""");
	}

	@Test
	public void test_0750() throws Exception {
		assertNoException("""
					class C {}
				""");
	}

	@Test
	public void test_0751() throws Exception {
		assertNoException("""
					export @Internal public var any c
				""");
	}

	@Test
	public void test_0752() throws Exception {
		assertNoException("""
					export public function c() {}
				""");
	}

	@Test
	public void test_0753() throws Exception {
		assertNoException("""
					export class C {}
				""");
	}

	@Test
	public void test_0754() throws Exception {
		assertNoException("""
					function c() {}
				""");
	}

	@Test
	public void test_0755() throws Exception {
		assertNoException("""
					export function c() {}
				""");
	}

	@Test
	public void test_0756() throws Exception {
		assertNoException("""
					export project class C {}
				""");
	}

	@Test
	public void test_0757() throws Exception {
		assertNoException("""
					export @Internal public class C {}
				""");
	}

	@Test
	public void test_0758() throws Exception {
		assertNoException("""
					export public class C {}
				""");
	}

	@Test
	public void test_0759() throws Exception {
		assertNoException("""
					export project var any c
				""");
	}

	@Test
	public void test_0760() throws Exception {
		assertNoException("""
					export public var any c
				""");
	}

	@Test
	public void test_0761() throws Exception {
		assertNoException("""
					export @Internal public function c() {}
				""");
	}

	@Test
	public void test_0762() throws Exception {
		assertNoException("""
					export project function c() {}
				""");
	}

	@Test
	public void test_0763() throws Exception {
		assertNoException("""
					export var any c
				""");
	}

	@Test
	public void test_0764() throws Exception {
		assertNoException("""
					var any c
				""");
	}

	@Test
	public void test_0765() throws Exception {
		assertNoException("""
					x && y
				""");
	}

	@Test
	public void test_0766() throws Exception {
		assertNoException("""
					x || y && z
				""");
	}

	@Test
	public void test_0767() throws Exception {
		assertNoException("""
					x || y ^ z
				""");
	}

	@Test
	public void test_0768() throws Exception {
		assertNoException("""
					x || y || z
				""");
	}

	@Test
	public void test_0769() throws Exception {
		assertNoException("""
					x || y
				""");
	}

	@Test
	public void test_0770() throws Exception {
		assertNoException("""
					x && y && z
				""");
	}

	@Test
	public void test_0771() throws Exception {
		assertNoException("""
					var x = /[a-z]/i
				""");
	}

	@Test
	public void test_0772() throws Exception {
		assertNoException("""
					var x = /[x-z]/i
				""");
	}

	@Test
	public void test_0773() throws Exception {
		assertNoException("""
					var x = /[a-c]/i
				""");
	}

	@Test
	public void test_0774() throws Exception {
		assertNoException("""
					var x = /[P QR]/i
				""");
	}

	@Test
	public void test_0775() throws Exception {
		assertNoException("""
					var x = /foo\\/bar/
				""");
	}

	@Test
	public void test_0776() throws Exception {
		assertNoException("""
					var x = /=([^=\\s])+/g
				""");
	}

	@Test
	public void test_0777() throws Exception {
		assertNoException("""
					var x = /42/g.test
				""");
	}

	@Test
	public void test_0778() throws Exception {
		assertNoException("""
					日本語 = []
				""");
	}

	@Test
	public void test_0779() throws Exception {
		assertNoException("""
					T‿ = []
				""");
	}

	@Test
	public void test_0780() throws Exception {
		assertNoException("""
					T‌ = []
				""");
	}

	@Test
	public void test_0781() throws Exception {
		assertNoException("""
					T‍ = []
				""");
	}

	@Test
	public void test_0782() throws Exception {
		assertNoException("""
					ⅣⅡ = []
				""");
	}

	@Test
	public void test_0783() throws Exception {
		assertNoException("""
					ⅣⅡ = []
				""");
	}

	@Test
	public void test_0784() throws Exception {
		assertNoException("""
					var	@Undefined x;
					x.selector;
				""");
	}

	@Test
	public void test_0785() throws Exception {
		assertNoException("""
					function f() {
						"use strict"
						this.selector;
					}
				""");
	}

	@Test
	public void test_0786() throws Exception {
		assertNoException("""
					function f() {
						this.undefined;
					}
				""");
	}

	@Test
	public void test_0787() throws Exception {
		assertNoException("""
					undefined.selector;
				""");
	}

	@Test
	public void test_0788() throws Exception {
		assertNoException("""
					class C {
						public s: string;
					}
					function f(~C with { n: number; } p) {
						p.s;
						p.n;
					}
				""");
	}

	@Test
	public void test_0789() throws Exception {
		assertNoException("""
					class C {
						public s: string;
					}
					function f(): ~C with { n: number; } { return null; }
					var p = f();
					p.s;
					p.n;
				""");
	}

	@Test
	public void test_0790() throws Exception {
		assertNoException("""
					class C {
						public s: string;

						constructor(~this with { n: number; } p) {
							p.n;
						}
					}
				""");
	}

	@Test
	public void test_0791() throws Exception {
		assertNoException("""
					class C {
						public s: string;

						constructor(~this with { n: number; } p) {
							p.s;
							p.n;
						}
					}
				""");
	}

	@Test
	public void test_0792() throws Exception {
		assertNoException("""
					class C {
						public s: string;

						constructor(~this p) {
							p.s;
						}
					}
				""");
	}

	@Test
	public void test_0793() throws Exception {
		assertNoException("""
					x = [,,]
				""");
	}

	@Test
	public void test_0794() throws Exception {
		assertNoException("""
					x / y
				""");
	}

	@Test
	public void test_0795() throws Exception {
		assertNoException("""
					x % y
				""");
	}

	@Test
	public void test_0796() throws Exception {
		assertNoException("""
					x * y
				""");
	}

	@Test
	public void test_0797() throws Exception {
		assertNoException("""
					x * y * z
				""");
	}

	@Test
	public void test_0798() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0799() throws Exception {
		assertNoException("""
					class C {
						data: any;

						constructor(data: any) {
							this.data = data;
						}

						foo(): void { }
					}
				""");
	}

	@Test
	public void test_0800() throws Exception {
		assertNoException("""
					class C1 {
						s: any= null;
						s: any = null;
						s = null;
					}
				""");
	}

	@Test
	public void test_0801() throws Exception {
		assertNoException("""
					class C1 {
						string ['m'] = "1";
						m: string= "2";
						string ["@abc"] = "3";
						string [    '@abc1'    ] = "4";
						string [ /*comment*/ '@abc2'] = "5";
						string ['@abc3'  /*comment*/ ] = "6";
					}
				""");
	}

	@Test
	public void test_0802() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0803() throws Exception {
		assertNoException("""
					class C {}
				""");
	}

	@Test
	public void test_0804() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0805() throws Exception {
		assertNoException("""
					public class A {}
					public class B {}

					public class C<T extends A> {
						<S> foo( union{A,B} p) {}
					}
				""");
	}

	@Test
	public void test_0806() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0807() throws Exception {
		assertNoException("""
					x >= y
				""");
	}

	@Test
	public void test_0808() throws Exception {
		assertNoException("""
					x <= y
				""");
	}

	@Test
	public void test_0809() throws Exception {
		assertNoException("""
					x < y
				""");
	}

	@Test
	public void test_0810() throws Exception {
		assertNoException("""
					x < y < z
				""");
	}

	@Test
	public void test_0811() throws Exception {
		assertNoException("""
					x in y
				""");
	}

	@Test
	public void test_0812() throws Exception {
		assertNoException("""
					x instanceof y
				""");
	}

	@Test
	public void test_0813() throws Exception {
		assertNoException("""
					x > y
				""");
	}

	@Test
	public void test_0814() throws Exception {
		assertNoException("""
					(function(){ return
					 x*y })
				""");
	}

	@Test
	public void test_0815() throws Exception {
		assertNoException("""
					(function(){ return x * y })
				""");
	}

	@Test
	public void test_0816() throws Exception {
		assertNoException("""
					(function(){ return; })
				""");
	}

	@Test
	public void test_0817() throws Exception {
		assertNoException("""
					(function(){ return x
					 * y })
				""");
	}

	@Test
	public void test_0818() throws Exception {
		assertNoException("""
					(function(){ return })
				""");
	}

	@Test
	public void test_0819() throws Exception {
		assertNoException("""
					(function(){ return x })
				""");
	}

	@Test
	public void test_0820() throws Exception {
		assertNoException("""
					(function(){ return x; })
				""");
	}

	@Test
	public void test_0821() throws Exception {
		assertNoException("""
					var a = a || {};
					a.b
				""");
	}

	@Test
	public void test_0822() throws Exception {
		assertNoException("""
					var u =
					u.either
				""");
	}

	@Test
	public void test_0823() throws Exception {
		assertNoException("""
					interface R1 {
						f(p: number): void {}
					}
					interface R2 {
						f(p: string): void {
					class C implements R1, R2 {}
				""");
	}

	@Test
	public void test_0824() throws Exception {
		assertNoException("""
					interface R1 {
						x: string;
					}
					class S {
						x(): void {
					class C1 extends S implements R1 {}
				""");
	}

	@Test
	public void test_0825() throws Exception {
		assertNoException("""
					import * as N2 from 'a/X';
					import * as 1 from 'a/X';
					var N1.x: X;
				""");
	}

	@Test
	public void test_0826() throws Exception {
		assertNoException("""
					import X1 from 'a/X';
					import X2 from 'a/X';
					var X1 x = X2;
				""");
	}

	@Test
	public void test_0827() throws Exception {
		assertNoException("""
					function(a){
						return a.b
					}
				""");
	}

	@Test
	public void test_0828() throws Exception {
		assertNoException("""
					class C<T> {
					    boolean b = null
					    x() {
						    spec
					    }
					    y(n: number?) {
					    }
					}
				""");
	}

	@Test
	public void test_0829() throws Exception {
		assertNoException("""
					class C<T> {
					    m(? p) {
					    }
					    n() {
					        this.m();
					    }
					}
				""");
	}

	@Test
	public void test_0830() throws Exception {
		assertNoException("""
					var target = {
						s: "hello",
						set x
				""");
	}

	@Test
	public void test_0831() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0832() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0833() throws Exception {
		assertNoException("""
					var fo = 5,/*
								*/
								class A { a = new A(); }
				""");
	}

	@Test
	public void test_0834() throws Exception {
		assertNoException("""
					public class C {
						f0;
						private f1;
						project
						@Internal protected f4;
						protected f5;
						@Internal public f6;
						public f7;
					}
				""");
	}

	@Test
	public void test_0835() throws Exception {
		assertNoException("""
					var ol = {
						et target() {return null}
					}
					ol.target=null;
				""");
	}

	@Test
	public void test_0836() throws Exception {
		assertNoException("""
					var a = function foo() {
					  foo();
					};
				""");
	}

	@Test
	public void test_0837() throws Exception {
		assertNoException("""
					var a = function foo() {
					};
					foo();
				""");
	}

	@Test
	public void test_0838() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0839() throws Exception {
		assertNoException("""
					var s: string= ''
					s.toLowerCase().toLowerCase()
				""");
	}

	@Test
	public void test_0840() throws Exception {
		assertNoException("""
					var boolean b = true
				""");
	}

	@Test
	public void test_0841() throws Exception {
		assertNoException("""
					var Date d
				""");
	}

	@Test
	public void test_0842() throws Exception {
		assertNoException("""
					var undefined u // may be a type error later
				""");
	}

	@Test
	public void test_0843() throws Exception {
		assertNoException("""
					var n: number= 1
				""");
	}

	@Test
	public void test_0844() throws Exception {
		assertNoException("""
					var String s = ''
					s.toLowerCase().toLowerCase()
				""");
	}

	@Test
	public void test_0845() throws Exception {
		assertNoException("""
					var Boolean b = true
				""");
	}

	@Test
	public void test_0846() throws Exception {
		assertNoException("""
					var RegExp r = /abc/g
				""");
	}

	@Test
	public void test_0847() throws Exception {
		assertNoException("""
					var Number n = 1
				""");
	}

	@Test
	public void test_0848() throws Exception {
		assertNoException("""
					var s: any= 1;
					var x =1;
					s as number/x;
				""");
	}

	@Test
	public void test_0849() throws Exception {
		assertNoException("""
					class A {
						d() {}
					}
					var s: any= new A();
					s as A;
				""");
	}

	@Test
	public void test_0850() throws Exception {
		assertNoException("""
					export public class Dup {}
				""");
	}

	@Test
	public void test_0851() throws Exception {
		assertNoException("""
					import Dup from "A"
					import Dup from "B"
					var d = new Dup();
				""");
	}

	@Test
	public void test_0852() throws Exception {
		assertNoException("""
					export var String dup = ""
				""");
	}

	@Test
	public void test_0853() throws Exception {
		assertNoException("""
					import dup from "A"
					import dup from "B"
					var d = dup
				""");
	}

	@Test
	public void test_0854() throws Exception {
		assertNoException("""
					var String dup = ""
				""");
	}

	@Test
	public void test_0855() throws Exception {
		assertNoException("""
					class A {}
				""");
	}

	@Test
	public void test_0856() throws Exception {
		assertNoException("""
					import * as Namespace from "A"
					var s = ""
				""");
	}

	@Test
	public void test_0857() throws Exception {
		assertNoException("""
					import A from "A"
					var s = ""
				""");
	}

	@Test
	public void test_0858() throws Exception {
		assertNoException("""
					import * as N from "A"
					var N.a: A= null
				""");
	}

	@Test
	public void test_0859() throws Exception {
		assertNoException("""
					import A from "A"
					var a: A= null
				""");
	}

	@Test
	public void test_0860() throws Exception {
		assertNoException("""
					import * as N from "A"
					var a = N.A
				""");
	}

	@Test
	public void test_0861() throws Exception {
		assertNoException("""
					import A from "A"
					var a = A
				""");
	}

	@Test
	public void test_0862() throws Exception {
		assertNoException("""
					import * as N from "A"
					var N.a: A= ""
				""");
	}

	@Test
	public void test_0863() throws Exception {
		assertNoException("""
					export var String s = ""
				""");
	}

	@Test
	public void test_0864() throws Exception {
		assertNoException("""
					import s from "A"
					var x = s
				""");
	}

	@Test
	public void test_0865() throws Exception {
		assertNoException("""
					export @Internal public var String s = ""
				""");
	}

	@Test
	public void test_0866() throws Exception {
		assertNoException("""
					import * as N from "A"
					var x = N.s
				""");
	}

	@Test
	public void test_0867() throws Exception {
		assertNoException("""
					export public var String s = ""
				""");
	}

	@Test
	public void test_0868() throws Exception {
		assertNoException("""
					export function f() {}
				""");
	}

	@Test
	public void test_0869() throws Exception {
		assertNoException("""
					import f from "A"
					var x = f
				""");
	}

	@Test
	public void test_0870() throws Exception {
		assertNoException("""
					export @Internal public function f() {}
				""");
	}

	@Test
	public void test_0871() throws Exception {
		assertNoException("""
					import * as N from "A"
					var x = N.f
				""");
	}

	@Test
	public void test_0872() throws Exception {
		assertNoException("""
					export public function f() {}
				""");
	}

	@Test
	public void test_0873() throws Exception {
		assertNoException("""
					export public enum E { Literal }
				""");
	}

	@Test
	public void test_0874() throws Exception {
		assertNoException("""
					import * as N from "A"
					var x = N.E.Literal
				""");
	}

	@Test
	public void test_0875() throws Exception {
		assertNoException("""
					export @Internal public enum E { Literal }
				""");
	}

	@Test
	public void test_0876() throws Exception {
		assertNoException("""
					class Base {
						@Internal public m(): void {}
					}
					export public class Sub extends Base {}
				""");
	}

	@Test
	public void test_0877() throws Exception {
		assertNoException("""
					import * as N from "A"
					var N.Sub s;
					s.m()
				""");
	}

	@Test
	public void test_0878() throws Exception {
		assertNoException("""
					export class C {
						m(): void {}
					}
				""");
	}

	@Test
	public void test_0879() throws Exception {
		assertNoException("""
					export class C {
						private m(): void {}
					}
				""");
	}

	@Test
	public void test_0880() throws Exception {
		assertNoException("""
					export class A {}
					export class B extends A {
						protected m(): void {}
					}
					export class C extends B {}
				""");
	}

	@Test
	public void test_0881() throws Exception {
		assertNoException("""
					var fibonacci = function* (numbers: number) {
					    var pre: number= 0, cur = 1;
					    while (numbers-- > 0) {
					        [ pre, cur ] = [ cur, pre + cur ];
					        yield cur;
					    }
					};
				""");
	}

	@Test
	public void test_0882() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0883() throws Exception {
		assertNoException("""
					class C {}
					class D {
						~C with { s; } x;
						f( ~C with { s; } p): void {}
					}
					function f( ~C with { s; } p) {}
				""");
	}

	@Test
	public void test_0884() throws Exception {
		assertNoException("""
					class C {}
					class D {
						~C with { s: string; } x;
						f( ~C with { s: string; } p): void {}
					}
					function f( ~C with { s: string; } p) {}
				""");
	}

	@Test
	public void test_0885() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0886() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0887() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0888() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0889() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0890() throws Exception {
		assertNoException("""
					public class C {
						public C exists()
					}
				""");
	}

	@Test
	public void test_0891() throws Exception {
		assertNoException("""
					public class C {
						public C dup()
						public C dup()
					}
				""");
	}

	@Test
	public void test_0892() throws Exception {
		assertNoException("""
					public class C {
					}
				""");
	}

	@Test
	public void test_0893() throws Exception {
		assertNoException("""
					public class C {
						public exists: C;
					}
				""");
	}

	@Test
	public void test_0894() throws Exception {
		assertNoException("""
					public class C extends D {
					}
					public class D {
						public C exists()
					}
				""");
	}

	@Test
	public void test_0895() throws Exception {
		assertNoException("""
					public class C extends D {
					}
					public class D {
						public C dup()
						public C dup()
					}
				""");
	}

	@Test
	public void test_0896() throws Exception {
		assertNoException("""
					public class C extends D {
					}
					public class D {
					}
				""");
	}

	@Test
	public void test_0897() throws Exception {
		assertNoException("""
					public class C extends D {
					}
					public class D extends C {
						public C exists()
					}
				""");
	}

	@Test
	public void test_0898() throws Exception {
		assertNoException("""
					public class C extends D {
					}
					public class D extends C {
					}
				""");
	}

	@Test
	public void test_0899() throws Exception {
		assertNoException("""
					public class C extends C {
					}
				""");
	}

}
