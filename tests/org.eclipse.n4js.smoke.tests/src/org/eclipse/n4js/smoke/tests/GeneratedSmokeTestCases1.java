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
public class GeneratedSmokeTestCases1 {

	@Inject
	ParseHelper<Script> parseHelper;

	// Dummy method - needs to be called to record the expression and schedule it for smoke testing
	protected void assertNoException(CharSequence expression) throws Exception {
		parseHelper.parse(expression);
	}

	@Test
	public void test_0001() throws Exception {
		assertNoException("""
					class X {
						 a: any;
						 a: any; } }
				""");
	}

	@Test
	public void test_0002() throws Exception {
		assertNoException("""
					done: while (true) { break done }
				""");
	}

	@Test
	public void test_0003() throws Exception {
		assertNoException("""
					while (true) { break }
				""");
	}

	@Test
	public void test_0004() throws Exception {
		assertNoException("""
					__proto__: while (true) { break __proto__; }
				""");
	}

	@Test
	public void test_0005() throws Exception {
		assertNoException("""
					done: while (true) { break done; }
				""");
	}

	@Test
	public void test_0006() throws Exception {
		assertNoException("""
					start: for (;;) break start
				""");
	}

	@Test
	public void test_0007() throws Exception {
		assertNoException("""
					start: while (true) break start
				""");
	}

	@Test
	public void test_0008() throws Exception {
		assertNoException("""
					__proto__: test
				""");
	}

	@Test
	public void test_0009() throws Exception {
		assertNoException("""
					public class A {

						f1(): void {}

						abstract f1(): void
						abstract f2(): void;

						@Internal
						public abstract f3(): void

						protected f4(): any { return null; }

						public f5(): any { return null; }

						@Internal
						public <T> f6(): T { return null; }

						@Internal
						public f7(p1: any, p2: any): any { return p1; }
						private <T> f8(p1: T, p2: T): void { }

						@Internal
						public f9(p1: any, p2: any?): void {}
						@Internal
						public f10(p1: any, p2: any): void {}
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
	public void test_0010() throws Exception {
		assertNoException("""
					debugger;
				""");
	}

	@Test
	public void test_0011() throws Exception {
		assertNoException("""
					debugger
				""");
	}

	@Test
	public void test_0012() throws Exception {
		assertNoException("""
					x & y
				""");
	}

	@Test
	public void test_0013() throws Exception {
		assertNoException("""
					x ^ y
				""");
	}

	@Test
	public void test_0014() throws Exception {
		assertNoException("""
					x | y
				""");
	}

	@Test
	public void test_0015() throws Exception {
		assertNoException("""
					x^y
				""");
	}

	@Test
	public void test_0016() throws Exception {
		assertNoException("""
					"Hi\\0You"
				""");
	}

	@Test
	public void test_0017() throws Exception {
		assertNoException("""
					'use strict';"Hi\\112You"
				""");
	}

	@Test
	public void test_0018() throws Exception {
		assertNoException("""
					'use strict';"Hi\\0112You"
				""");
	}

	@Test
	public void test_0019() throws Exception {
		assertNoException("""
					'use strict';"Hi\\312You"
				""");
	}

	@Test
	public void test_0020() throws Exception {
		assertNoException("""
					'use strict';"Hi\\412You"
				""");
	}

	@Test
	public void test_0021() throws Exception {
		assertNoException("""
					'use strict';"Hi\\812You"
				""");
	}

	@Test
	public void test_0022() throws Exception {
		assertNoException("""
					'use strict';"Hi\\712You"
				""");
	}

	@Test
	public void test_0023() throws Exception {
		assertNoException("""
					'use strict';"Hi\\1You"
				""");
	}

	@Test
	public void test_0024() throws Exception {
		assertNoException("""
					"Hi\\112You"
				""");
	}

	@Test
	public void test_0025() throws Exception {
		assertNoException("""
					"Hi\\0112You"
				""");
	}

	@Test
	public void test_0026() throws Exception {
		assertNoException("""
					"Hi\\312You"
				""");
	}

	@Test
	public void test_0027() throws Exception {
		assertNoException("""
					"Hi\\412You"
				""");
	}

	@Test
	public void test_0028() throws Exception {
		assertNoException("""
					"Hi\\812You"
				""");
	}

	@Test
	public void test_0029() throws Exception {
		assertNoException("""
					"Hi\\712You"
				""");
	}

	@Test
	public void test_0030() throws Exception {
		assertNoException("""
					"Hi\\1You"
				""");
	}

	@Test
	public void test_0031() throws Exception {
		assertNoException("""
					"Hi\\02You"
				""");
	}

	@Test
	public void test_0032() throws Exception {
		assertNoException("""
					"Hi"
				""");
	}

	@Test
	public void test_0033() throws Exception {
		assertNoException("""
					"Hi\\nworld"
				""");
	}

	@Test
	public void test_0034() throws Exception {
		assertNoException("""
					"Hi\\012You"
				""");
	}

	@Test
	public void test_0035() throws Exception {
		assertNoException("""
					"\\xt"
				""");
	}

	@Test
	public void test_0036() throws Exception {
		assertNoException("""
					"\\n\\r\\t\\v\\b\\f\\\\\\'\\"\\0"
				""");
	}

	@Test
	public void test_0037() throws Exception {
		assertNoException("""
					"Hi\\
					world"
				""");
	}

	@Test
	public void test_0038() throws Exception {
		assertNoException("""
					"Hi\\
					world"
				""");
	}

	@Test
	public void test_0039() throws Exception {
		assertNoException("""
					"\\u0061"
				""");
	}

	@Test
	public void test_0040() throws Exception {
		assertNoException("""
					"\\u{0061}"
				""");
	}

	@Test
	public void test_0041() throws Exception {
		assertNoException("""
					"\\u{61}"
				""");
	}

	@Test
	public void test_0042() throws Exception {
		assertNoException("""
					"\\u00"
				""");
	}

	@Test
	public void test_0043() throws Exception {
		assertNoException("""
					"\\u{00"
				""");
	}

	@Test
	public void test_0044() throws Exception {
		assertNoException("""
					"\\u{}"
				""");
	}

	@Test
	public void test_0045() throws Exception {
		assertNoException("""
					"\\x61"
				""");
	}

	@Test
	public void test_0046() throws Exception {
		assertNoException("""
					function f(_$) {}
				""");
	}

	@Test
	public void test_0047() throws Exception {
		assertNoException("""
					L1: while(true) break L1
					L1: while(true) break L1
				""");
	}

	@Test
	public void test_0048() throws Exception {
		assertNoException("""
					L1: while(true) break L1
				""");
	}

	@Test
	public void test_0049() throws Exception {
		assertNoException("""
					L1: while(true)
						L2: while(true) break L1
					while(true) break L2
				""");
	}

	@Test
	public void test_0050() throws Exception {
		assertNoException("""
					L1: while(true)
						L2: while(true) break L1
				""");
	}

	@Test
	public void test_0051() throws Exception {
		assertNoException("""
					@@DoNotCompile
					import A from "p/A"
					@Force
					import B from "p/B"
					@Exlude
					import C from "p/C"

					@Internal @Final @NoInstantiate
					public class C {
						@ReadOnly
						public A = "Hi";
					}
				""");
	}

	@Test
	public void test_0052() throws Exception {
		assertNoException("""
					function testcase() throws Exception {
							        "use strict";

							        try {
							            eval("var yield = 1;")
							            return false;
							        } catch (e) {
							            return e instanceof SyntaxError;
							        }
							}
				""");
	}

	@Test
	public void test_0053() throws Exception {
		assertNoException("""
					@Annotation function testcase() throws Exception {}
				""");
	}

	@Test
	public void test_0054() throws Exception {
		assertNoException("""
					(@Dummy function testcase() throws Exception {})
				""");
	}

	@Test
	public void test_0055() throws Exception {
		assertNoException("""
					{ function testcase() throws Exception {} }
				""");
	}

	@Test
	public void test_0056() throws Exception {
		assertNoException("""
					(function testcase() throws Exception {})
				""");
	}

	@Test
	public void test_0057() throws Exception {
		assertNoException("""
					class Callee {
					   private static myPrivateStaticField: string= "myPrivateStaticField";
					   private myPrivateNonStaticField: string= "myPrivateNonStaticField";

					   static get myPrivateStaticAccessor() throws Exception {
							return this.myPrivateStaticField;
							 }

					   static set myPrivateStaticAccessor(myPrivateStaticParam: string) {
							/*this*/Callee.myPrivateStaticField = myPrivateStaticParam;
							 }

					   get myPrivateNonStaticAccessor() throws Exception {
							return this.myPrivateNonStaticField;
							 }

					   set myPrivateNonStaticAccessor(myPrivateParam: string) {
							this.myPrivateNonStaticField = myPrivateParam;
							 }

					}
					class Caller {

						call() throws Exception {
							Callee.myPrivateStaticAccessor = "a"
							var a = Callee.myPrivateStaticAccessor

							var callee: Callee= null;
							callee.myPrivateNonStaticAccessor = "a"
							a = callee.myPrivateNonStaticAccessor
						}
					}
				""");
	}

	@Test
	public void test_0058() throws Exception {
		assertNoException("""
					class A {
						static m() throws Exception {
							return this; // type {A}
						}
						m() throws Exception {
							return this; // A
						}
					}
				""");
	}

	@Test
	public void test_0059() throws Exception {
		assertNoException("""
					class A extends N4Object {
						oneField = 1;
						static secondField = 1;
					}
					class B extends N4Object {
						thirdField = (new A).oneField;
						static forthField = A.secondField;
					}
				""");
	}

	@Test
	public void test_0060() throws Exception {
		assertNoException("""
					class Control extends N4Object {
						x  =1; static y;
						i = (new Control).x;
						static j = new Control.y;

					}
				""");
	}

	@Test
	public void test_0061() throws Exception {
		assertNoException("""
					class C {}
					var x = C
					var c: C;
					var y = c.constructor
					var z1 = new y()
					var z2 = new C()
				""");
	}

	@Test
	public void test_0062() throws Exception {
		assertNoException("""
					class A {
						one() throws Exception {
						}
						static two() throws Exception {
						}
					}
					class B {

						execute() throws Exception {
							var a: A = null
							a.one
							A.two()
						}
					}
				""");
	}

	@Test
	public void test_0063() throws Exception {
		assertNoException("""
					class A {

						one() throws Exception {
						}

						two() throws Exception {
						this.one()
						}

						static one() throws Exception {
						}

						static two() throws Exception {
						this.one()
						}
					}
				""");
	}

	@Test
	public void test_0064() throws Exception {
		assertNoException("""
					export function foo1() throws Exception {
					    foo1;
					    foo1();
					    var b = function foo2() throws Exception {
							foo2()
					    	var c = function foo3() throws Exception {
					    		foo3()
					    		var d = function foo4() throws Exception {
					    			foo4()
					    		}
					    	}
					    }
					}
				""");
	}

	@Test
	public void test_0065() throws Exception {
		assertNoException("""
					function f1() throws Exception {
					    var v = function f1() throws Exception {
							f1()
					    }
					}
				""");
	}

	@Test
	public void test_0066() throws Exception {
		assertNoException("""
					var a = function foo( x ) {
						var b = function bar( y ) {
							if (y==1) { foo(2); bar(0); }
						}
						if (x==1) { b(1); }
					};
					a(1);
				""");
	}

	@Test
	public void test_0067() throws Exception {
		assertNoException("""
					class A {
						a: any;
						b(): void {}
						any get c() throws Exception { return null }
						set d(p: any) {}
					}
					class B extends A {}
				""");
	}

	@Test
	public void test_0068() throws Exception {
		assertNoException("""
					interface R1 {
						f(p: number): void {}
					}
					interface R2 {
						f(p: string): void {}
					}
					class C implements R1, R2 {}
				""");
	}

	@Test
	public void test_0069() throws Exception {
		assertNoException("""
					interface R1 {
						x: string;
					}
					class S {
						x(): void {}
					}
					class C1 extends S implements R1 {}
				""");
	}

	@Test
	public void test_0070() throws Exception {
		assertNoException("""
					class A {
						a: any;
						b(): void {}
						any get c() throws Exception { return null }
						set d(p: any) {}
					}
					class B extends A {
						@Override b(): void {}
						@Override any get c() throws Exception { return null }
						@Override set d(p: any) {}
					}
				""");
	}

	@Test
	public void test_0071() throws Exception {
		assertNoException("""
					"abc
					1+1
				""");
	}

	@Test
	public void test_0072() throws Exception {
		assertNoException("""
					'
				""");
	}

	@Test
	public void test_0073() throws Exception {
		assertNoException("""
					"
				""");
	}

	@Test
	public void test_0074() throws Exception {
		assertNoException("""
					'\\'
				""");
	}

	@Test
	public void test_0075() throws Exception {
		assertNoException("""
					"\\"
				""");
	}

	@Test
	public void test_0076() throws Exception {
		assertNoException("""
					'\\\\'
				""");
	}

	@Test
	public void test_0077() throws Exception {
		assertNoException("""
					"\\\\"
				""");
	}

	@Test
	public void test_0078() throws Exception {
		assertNoException("""
					'\\123'
				""");
	}

	@Test
	public void test_0079() throws Exception {
		assertNoException("""
					'\\0123'
				""");
	}

	@Test
	public void test_0080() throws Exception {
		assertNoException("""
					"Hi\\212You"
				""");
	}

	@Test
	public void test_0081() throws Exception {
		assertNoException("""
					"Hi\\512You"
				""");
	}

	@Test
	public void test_0082() throws Exception {
		assertNoException("""
					"Hi\\612You"
				""");
	}

	@Test
	public void test_0083() throws Exception {
		assertNoException("""
					""
				""");
	}

	@Test
	public void test_0084() throws Exception {
		assertNoException("""
					''
				""");
	}

	@Test
	public void test_0085() throws Exception {
		assertNoException("""
					var string = "Hi";
					var num = 1;
					var bool = true;
					var nullL = null;
					var regexp = /someregex/;
					var division = 12 / 5;
				""");
	}

	@Test
	public void test_0086() throws Exception {
		assertNoException("""
					x * y + z
				""");
	}

	@Test
	public void test_0087() throws Exception {
		assertNoException("""
					x + y / z
				""");
	}

	@Test
	public void test_0088() throws Exception {
		assertNoException("""
					x + y % z
				""");
	}

	@Test
	public void test_0089() throws Exception {
		assertNoException("""
					x + y * z
				""");
	}

	@Test
	public void test_0090() throws Exception {
		assertNoException("""
					x - y % z
				""");
	}

	@Test
	public void test_0091() throws Exception {
		assertNoException("""
					/(/
				""");
	}

	@Test
	public void test_0092() throws Exception {
		assertNoException("""
					/[[]/
				""");
	}

	@Test
	public void test_0093() throws Exception {
		assertNoException("""
					a[/c/]
				""");
	}

	@Test
	public void test_0094() throws Exception {
		assertNoException("""
					a(/c/)
				""");
	}

	@Test
	public void test_0095() throws Exception {
		assertNoException("""
					a +/c/
				""");
	}

	@Test
	public void test_0096() throws Exception {
		assertNoException("""
					var e=/^/g;
				""");
	}

	@Test
	public void test_0097() throws Exception {
		assertNoException("""
					/=1/g
				""");
	}

	@Test
	public void test_0098() throws Exception {
		assertNoException("""
					i/=1/2
				""");
	}

	@Test
	public void test_0099() throws Exception {
		assertNoException("""
					/{/
				""");
	}

	@Test
	public void test_0100() throws Exception {
		assertNoException("""
					({}/s)
				""");
	}

	@Test
	public void test_0101() throws Exception {
		assertNoException("""
					({})/s
				""");
	}

	@Test
	public void test_0102() throws Exception {
		assertNoException("""
					a/b/g
				""");
	}

	@Test
	public void test_0103() throws Exception {
		assertNoException("""
					a()/b/g
				""");
	}

	@Test
	public void test_0104() throws Exception {
		assertNoException("""
					a[b]/c/g
				""");
	}

	@Test
	public void test_0105() throws Exception {
		assertNoException("""
					null/a/g
				""");
	}

	@Test
	public void test_0106() throws Exception {
		assertNoException("""
					true/a/g
				""");
	}

	@Test
	public void test_0107() throws Exception {
		assertNoException("""
					false/a/g
				""");
	}

	@Test
	public void test_0108() throws Exception {
		assertNoException("""
					this/a/g
				""");
	}

	@Test
	public void test_0109() throws Exception {
		assertNoException("""
					from/as/get/set/g
				""");
	}

	@Test
	public void test_0110() throws Exception {
		assertNoException("""
					{}/function(){return 1}
				""");
	}

	@Test
	public void test_0111() throws Exception {
		assertNoException("""
					/1/
				""");
	}

	@Test
	public void test_0112() throws Exception {
		assertNoException("""
					/[]]/
				""");
	}

	@Test
	public void test_0113() throws Exception {
		assertNoException("""
					/[/
				""");
	}

	@Test
	public void test_0114() throws Exception {
		assertNoException("""
					({}/function(){return 1})
				""");
	}

	@Test
	public void test_0115() throws Exception {
		assertNoException("""
					//
					/1/
				""");
	}

	@Test
	public void test_0116() throws Exception {
		assertNoException("""
					/**//1/
				""");
	}

	@Test
	public void test_0117() throws Exception {
		assertNoException("""
					/\\\\/
				""");
	}

	@Test
	public void test_0118() throws Exception {
		assertNoException("""
					x |= 42
				""");
	}

	@Test
	public void test_0119() throws Exception {
		assertNoException("""
					x ^= 42
				""");
	}

	@Test
	public void test_0120() throws Exception {
		assertNoException("""
					x += 42
				""");
	}

	@Test
	public void test_0121() throws Exception {
		assertNoException("""
					x -= 42
				""");
	}

	@Test
	public void test_0122() throws Exception {
		assertNoException("""
					x = 42
				""");
	}

	@Test
	public void test_0123() throws Exception {
		assertNoException("""
					eval = 42
				""");
	}

	@Test
	public void test_0124() throws Exception {
		assertNoException("""
					arguments = 42
				""");
	}

	@Test
	public void test_0125() throws Exception {
		assertNoException("""
					x &= 42
				""");
	}

	@Test
	public void test_0126() throws Exception {
		assertNoException("""
					x /= 42
				""");
	}

	@Test
	public void test_0127() throws Exception {
		assertNoException("""
					x >>= 42
				""");
	}

	@Test
	public void test_0128() throws Exception {
		assertNoException("""
					x %= 42
				""");
	}

	@Test
	public void test_0129() throws Exception {
		assertNoException("""
					x <<= 42
				""");
	}

	@Test
	public void test_0130() throws Exception {
		assertNoException("""
					x *= 42
				""");
	}

	@Test
	public void test_0131() throws Exception {
		assertNoException("""
					x = y = z
				""");
	}

	@Test
	public void test_0132() throws Exception {
		assertNoException("""
					x >>>= 42
				""");
	}

	@Test
	public void test_0133() throws Exception {
		assertNoException("""
					export public class X {}
				""");
	}

	@Test
	public void test_0134() throws Exception {
		assertNoException("""
					class A{}
				""");
	}

	@Test
	public void test_0135() throws Exception {
		assertNoException("""
					export @Internal public class X {}
				""");
	}

	@Test
	public void test_0136() throws Exception {
		assertNoException("""
					export project class X {}
				""");
	}

	@Test
	public void test_0137() throws Exception {
		assertNoException("""
					export function x() throws Exception {}
				""");
	}

	@Test
	public void test_0138() throws Exception {
		assertNoException("""
					export @Internal public function x() throws Exception {}
				""");
	}

	@Test
	public void test_0139() throws Exception {
		assertNoException("""
					export public function x() throws Exception {}
				""");
	}

	@Test
	public void test_0140() throws Exception {
		assertNoException("""
					export project function x() throws Exception {}
				""");
	}

	@Test
	public void test_0141() throws Exception {
		assertNoException("""
					public class C {
						f0;
						private f1;
						project f2;
						@Internal protected f4;
						protected f5;
						@Internal public f6;
						public f7;
					}
				""");
	}

	@Test
	public void test_0142() throws Exception {
		assertNoException("""
					class								Cprv {}
					export class						Cpublic {}
					export project class				Cpro {}
					export @Internal public class		Cpub {}
					export public class 				Cep {}
					interface 							Iprv {}
					export interface 					Ipublic {}
					export project interface 			Ipro {}
					export @Internal public interface	Ipub {}
					export public interface				Iep {}
					interface 							Rprv {}
					export interface 					Rpublic {}
					export project interface 			Rpro {}
					export @Internal public interface	Rpub {}
					export public interface				Rep {}
					enum 								Eprv {L}
					export enum 						Epublic {L}
					export project enum 				Epro {L}
					export @Internal public enum		Epub {L}
					export public enum					Eep {L}
				""");
	}

	@Test
	public void test_0143() throws Exception {
		assertNoException("""
					export @Internal public var x = ""
				""");
	}

	@Test
	public void test_0144() throws Exception {
		assertNoException("""
					export project var x = ""
				""");
	}

	@Test
	public void test_0145() throws Exception {
		assertNoException("""
					export @Internal public const x = ""
				""");
	}

	@Test
	public void test_0146() throws Exception {
		assertNoException("""
					export project const x = ""
				""");
	}

	@Test
	public void test_0147() throws Exception {
		assertNoException("""
					this.undefined
				""");
	}

	@Test
	public void test_0148() throws Exception {
		assertNoException("""
					undefined
				""");
	}

	@Test
	public void test_0149() throws Exception {
		assertNoException("""
					class C {}
					var c: C= null
				""");
	}

	@Test
	public void test_0150() throws Exception {
		assertNoException("""
					x--
				""");
	}

	@Test
	public void test_0151() throws Exception {
		assertNoException("""
					eval--
				""");
	}

	@Test
	public void test_0152() throws Exception {
		assertNoException("""
					arguments--
				""");
	}

	@Test
	public void test_0153() throws Exception {
		assertNoException("""
					x++
				""");
	}

	@Test
	public void test_0154() throws Exception {
		assertNoException("""
					eval++
				""");
	}

	@Test
	public void test_0155() throws Exception {
		assertNoException("""
					arguments++
				""");
	}

	@Test
	public void test_0156() throws Exception {
		assertNoException("""
					class C {
					   getString(): void {
					    	var s: string= ''
					    	s.slice(0, 10)
					   }
					}
				""");
	}

	@Test
	public void test_0157() throws Exception {
		assertNoException("""
					x === y
				""");
	}

	@Test
	public void test_0158() throws Exception {
		assertNoException("""
					x == y
				""");
	}

	@Test
	public void test_0159() throws Exception {
		assertNoException("""
					x != y
				""");
	}

	@Test
	public void test_0160() throws Exception {
		assertNoException("""
					x !== y
				""");
	}

	@Test
	public void test_0161() throws Exception {
		assertNoException("""
					var re = //;
				""");
	}

	@Test
	public void test_0162() throws Exception {
		assertNoException("""
					var re = new RegExp("");
				""");
	}

	@Test
	public void test_0163() throws Exception {
		assertNoException("""
					var regExp = /\\
					n/;
				""");
	}

	@Test
	public void test_0164() throws Exception {
		assertNoException("""
					/a/
				""");
	}

	@Test
	public void test_0165() throws Exception {
		assertNoException("""
					/;/
				""");
	}

	@Test
	public void test_0166() throws Exception {
		assertNoException("""
					/ /
				""");
	}

	@Test
	public void test_0167() throws Exception {
		assertNoException("""
					/\\u0041/
				""");
	}

	@Test
	public void test_0168() throws Exception {
		assertNoException("""
					/\\1/
				""");
	}

	@Test
	public void test_0169() throws Exception {
		assertNoException("""
					/\\a/
				""");
	}

	@Test
	public void test_0170() throws Exception {
		assertNoException("""
					/\\;/
				""");
	}

	@Test
	public void test_0171() throws Exception {
		assertNoException("""
					/\\ /
				""");
	}

	@Test
	public void test_0172() throws Exception {
		assertNoException("""
					/*/
				""");
	}

	@Test
	public void test_0173() throws Exception {
		assertNoException("""
					///
					.source;
				""");
	}

	@Test
	public void test_0174() throws Exception {
		assertNoException("""
					//
					.source;
				""");
	}

	@Test
	public void test_0175() throws Exception {
		assertNoException("""
					/
					/
				""");
	}

	@Test
	public void test_0176() throws Exception {
		assertNoException("""
					/\\u000A/
				""");
	}

	@Test
	public void test_0177() throws Exception {
		assertNoException("""
					/
					/
				""");
	}

	@Test
	public void test_0178() throws Exception {
		assertNoException("""
					/\\u000D/
				""");
	}

	@Test
	public void test_0179() throws Exception {
		assertNoException("""
					/\\u2028/
				""");
	}

	@Test
	public void test_0180() throws Exception {
		assertNoException("""
					/\\u2029/
				""");
	}

	@Test
	public void test_0181() throws Exception {
		assertNoException("""
					/a\\/
				""");
	}

	@Test
	public void test_0182() throws Exception {
		assertNoException("""
					/a//.source
				""");
	}

	@Test
	public void test_0183() throws Exception {
		assertNoException("""
					/(?:)/g
				""");
	}

	@Test
	public void test_0184() throws Exception {
		assertNoException("""
					/(?:)/i
				""");
	}

	@Test
	public void test_0185() throws Exception {
		assertNoException("""
					/(?:)/m
				""");
	}

	@Test
	public void test_0186() throws Exception {
		assertNoException("""
					/(?:)/gi
				""");
	}

	@Test
	public void test_0187() throws Exception {
		assertNoException("""
					/(?:)/mg
				""");
	}

	@Test
	public void test_0188() throws Exception {
		assertNoException("""
					/(?:)/mig
				""");
	}

	@Test
	public void test_0189() throws Exception {
		assertNoException("""
					/(?:)/\\u0067
				""");
	}

	@Test
	public void test_0190() throws Exception {
		assertNoException("""
					/(?:)/\\u0069
				""");
	}

	@Test
	public void test_0191() throws Exception {
		assertNoException("""
					/(?:)/\\u006D
				""");
	}

	@Test
	public void test_0192() throws Exception {
		assertNoException("""
					/a\\1/
				""");
	}

	@Test
	public void test_0193() throws Exception {
		assertNoException("""
					/a\\a/
				""");
	}

	@Test
	public void test_0194() throws Exception {
		assertNoException("""
					/,\\;/
				""");
	}

	@Test
	public void test_0195() throws Exception {
		assertNoException("""
					/ \\ /
				""");
	}

	@Test
	public void test_0196() throws Exception {
		assertNoException("""
					x + y
				""");
	}

	@Test
	public void test_0197() throws Exception {
		assertNoException("""
					x - y
				""");
	}

	@Test
	public void test_0198() throws Exception {
		assertNoException("""
					"use strict" + y
				""");
	}

	@Test
	public void test_0199() throws Exception {
		assertNoException("""
					x + y + z
				""");
	}

	@Test
	public void test_0200() throws Exception {
		assertNoException("""

					;
				""");
	}

	@Test
	public void test_0201() throws Exception {
		assertNoException("""
					/*
					*/
				""");
	}

	@Test
	public void test_0202() throws Exception {
		assertNoException("""
					;
				""");
	}

	@Test
	public void test_0203() throws Exception {
		assertNoException("""
					//
				""");
	}

	@Test
	public void test_0204() throws Exception {
		assertNoException("""
				""");
	}

	@Test
	public void test_0205() throws Exception {
		assertNoException("""
					// comment
				""");
	}

	@Test
	public void test_0206() throws Exception {
		assertNoException("""
					// comment
				""");
	}

	@Test
	public void test_0207() throws Exception {
		assertNoException("""

				""");
	}

	@Test
	public void test_0208() throws Exception {
		assertNoException("""
					/(\\w+)\\s(\\w+)/
				""");
	}

	@Test
	public void test_0209() throws Exception {
		assertNoException("""
					/[Tt]he\\sr\\w+/
				""");
	}

	@Test
	public void test_0210() throws Exception {
		assertNoException("""
					/\\d+/g
				""");
	}

	@Test
	public void test_0211() throws Exception {
		assertNoException("""
					/[\\(\\)-]/g
				""");
	}

	@Test
	public void test_0212() throws Exception {
		assertNoException("""
					/(<)|(>)/g
				""");
	}

	@Test
	public void test_0213() throws Exception {
		assertNoException("""
					/(\\w.+)\\s(\\w.+)/
				""");
	}

	@Test
	public void test_0214() throws Exception {
		assertNoException("""
					/^\\d{5}$/
				""");
	}

	@Test
	public void test_0215() throws Exception {
		assertNoException("""
					/\\s*,\\s*/
				""");
	}

	@Test
	public void test_0216() throws Exception {
		assertNoException("""
					/^\\d{1,2}(\\-|\\/|\\.)\\d{1,2}\\1\\d{4}$/
				""");
	}

	@Test
	public void test_0217() throws Exception {
		assertNoException("""
					/(^-*\\d+$)|(^-*\\d+\\.\\d+$)/
				""");
	}

	@Test
	public void test_0218() throws Exception {
		assertNoException("""
					/(http:\\/\\/\\S*)/g
				""");
	}

	@Test
	public void test_0219() throws Exception {
		assertNoException("""
					var _ = 2
					var a = function fun1() throws Exception {
						var x = 1;
						var b = function fun2() throws Exception {
							var y = 2;
						}
					}
				""");
	}

	@Test
	public void test_0220() throws Exception {
		assertNoException("""
					var a = class class1 {
						x = 1;
						b = class class2 {
							y = 2;
						};
					};
				""");
	}

	@Test
	public void test_0221() throws Exception {
		assertNoException("""
					function f(p: ~Object with {a: string; b: string;}) {
						var s: string;
						s = p.a;
						s = p.b;
					}
				""");
	}

	@Test
	public void test_0222() throws Exception {
		assertNoException("""
					var r: ~Object with {a: string; b: string;};
					var s: string;
					s = r.a;
					s = r.b;
				""");
	}

	@Test
	public void test_0223() throws Exception {
		assertNoException("""
					export var r: ~Object with {a: string; b: string;};
					var s: string;
					s = r.a;
					s = r.b;
				""");
	}

	@Test
	public void test_0224() throws Exception {
		assertNoException("""
					export var r: ~Object with {a: string; b: string;};
				""");
	}

	@Test
	public void test_0225() throws Exception {
		assertNoException("""
					import r from "A"
					var s: string;
					s = r.a;
					s = r.b;
				""");
	}

	@Test
	public void test_0226() throws Exception {
		assertNoException("""
					// line comment
					42
				""");
	}

	@Test
	public void test_0227() throws Exception {
		assertNoException("""
					42// line comment
				""");
	}

	@Test
	public void test_0228() throws Exception {
		assertNoException("""
					// Hi, world!
					42
				""");
	}

	@Test
	public void test_0229() throws Exception {
		assertNoException("""
					//
					42
				""");
	}

	@Test
	public void test_0230() throws Exception {
		assertNoException("""
					// Hi, world!

					//   Another hello
					42
				""");
	}

	@Test
	public void test_0231() throws Exception {
		assertNoException("""
					/* block comment */ 42
				""");
	}

	@Test
	public void test_0232() throws Exception {
		assertNoException("""
					42 /*The*/ /*Answer*/
				""");
	}

	@Test
	public void test_0233() throws Exception {
		assertNoException("""
					42 /*The * Answer*/
				""");
	}

	@Test
	public void test_0234() throws Exception {
		assertNoException("""
					/* multiline
					comment
					should
					be
					ignored */ 42
				""");
	}

	@Test
	public void test_0235() throws Exception {
		assertNoException("""
					/*a
					b*/ 42
				""");
	}

	@Test
	public void test_0236() throws Exception {
		assertNoException("""
					/*a
					b*/ 42
				""");
	}

	@Test
	public void test_0237() throws Exception {
		assertNoException("""
					/*a
					b*/ 42
				""");
	}

	@Test
	public void test_0238() throws Exception {
		assertNoException("""
					/**/ 42
				""");
	}

	@Test
	public void test_0239() throws Exception {
		assertNoException("""
					if (x) { // Some comment
					42 }
				""");
	}

	@Test
	public void test_0240() throws Exception {
		assertNoException("""
					switch (answer) { case 42: /* perfect */ 42; }
				""");
	}

	@Test
	public void test_0241() throws Exception {
		assertNoException("""
					x = { set if(w) { m_if = w } }
				""");
	}

	@Test
	public void test_0242() throws Exception {
		assertNoException("""
					x = { set true(w) { m_true = w } }
				""");
	}

	@Test
	public void test_0243() throws Exception {
		assertNoException("""
					x = { set false(w) { m_false = w } }
				""");
	}

	@Test
	public void test_0244() throws Exception {
		assertNoException("""
					x = { set null(w) { m_null = w } }
				""");
	}

	@Test
	public void test_0245() throws Exception {
		assertNoException("""
					x = { answer: 42 }
				""");
	}

	@Test
	public void test_0246() throws Exception {
		assertNoException("""
					x = { get: 42 }
				""");
	}

	@Test
	public void test_0247() throws Exception {
		assertNoException("""
					x = { set: 43 }
				""");
	}

	@Test
	public void test_0248() throws Exception {
		assertNoException("""
					x = { __proto__: 2 }
				""");
	}

	@Test
	public void test_0249() throws Exception {
		assertNoException("""
					x = { if: 42 }
				""");
	}

	@Test
	public void test_0250() throws Exception {
		assertNoException("""
					x = { true: 42 }
				""");
	}

	@Test
	public void test_0251() throws Exception {
		assertNoException("""
					x = { false: 42 }
				""");
	}

	@Test
	public void test_0252() throws Exception {
		assertNoException("""
					x = { null: 42 }
				""");
	}

	@Test
	public void test_0253() throws Exception {
		assertNoException("""
					x = { get width() throws Exception { return m_width }, set width(width) { m_width = width; } }
				""");
	}

	@Test
	public void test_0254() throws Exception {
		assertNoException("""
					x = { x: 1, x: 2 }
				""");
	}

	@Test
	public void test_0255() throws Exception {
		assertNoException("""
					x = { answer: 42, }
				""");
	}

	@Test
	public void test_0256() throws Exception {
		assertNoException("""
					x = { get 10() throws Exception {} }
				""");
	}

	@Test
	public void test_0257() throws Exception {
		assertNoException("""
					x = { get width() throws Exception { return m_width } }
				""");
	}

	@Test
	public void test_0258() throws Exception {
		assertNoException("""
					x = { get undef() throws Exception {} }
				""");
	}

	@Test
	public void test_0259() throws Exception {
		assertNoException("""
					x = { get "undef"() throws Exception {} }
				""");
	}

	@Test
	public void test_0260() throws Exception {
		assertNoException("""
					x = { answer: 42,, }
				""");
	}

	@Test
	public void test_0261() throws Exception {
		assertNoException("""
					x = { set "null"(w) { m_null = w } }
				""");
	}

	@Test
	public void test_0262() throws Exception {
		assertNoException("""
					x = { set 10(w) { m_null = w } }
				""");
	}

	@Test
	public void test_0263() throws Exception {
		assertNoException("""
					x = { get if() throws Exception {} }
				""");
	}

	@Test
	public void test_0264() throws Exception {
		assertNoException("""
					x = { get true() throws Exception {} }
				""");
	}

	@Test
	public void test_0265() throws Exception {
		assertNoException("""
					x = { get false() throws Exception {} }
				""");
	}

	@Test
	public void test_0266() throws Exception {
		assertNoException("""
					x = { get null() throws Exception {} }
				""");
	}

	@Test
	public void test_0267() throws Exception {
		assertNoException("""
					x = { "answer": 42 }
				""");
	}

	@Test
	public void test_0268() throws Exception {
		assertNoException("""
					x = { "__proto__": 2 }
				""");
	}

	@Test
	public void test_0269() throws Exception {
		assertNoException("""
					x = { set width(w) { m_width = w } }
				""");
	}

	@Test
	public void test_0270() throws Exception {
		assertNoException("""
					x = {}
				""");
	}

	@Test
	public void test_0271() throws Exception {
		assertNoException("""
					x = { }
				""");
	}

	@Test
	public void test_0272() throws Exception {
		assertNoException("""
					void x
				""");
	}

	@Test
	public void test_0273() throws Exception {
		assertNoException("""
					delete x
				""");
	}

	@Test
	public void test_0274() throws Exception {
		assertNoException("""
					typeof x
				""");
	}

	@Test
	public void test_0275() throws Exception {
		assertNoException("""
					--x
				""");
	}

	@Test
	public void test_0276() throws Exception {
		assertNoException("""
					--eval
				""");
	}

	@Test
	public void test_0277() throws Exception {
		assertNoException("""
					--arguments
				""");
	}

	@Test
	public void test_0278() throws Exception {
		assertNoException("""
					++x
				""");
	}

	@Test
	public void test_0279() throws Exception {
		assertNoException("""
					++eval
				""");
	}

	@Test
	public void test_0280() throws Exception {
		assertNoException("""
					++arguments
				""");
	}

	@Test
	public void test_0281() throws Exception {
		assertNoException("""
					~x
				""");
	}

	@Test
	public void test_0282() throws Exception {
		assertNoException("""
					-x
				""");
	}

	@Test
	public void test_0283() throws Exception {
		assertNoException("""
					!x
				""");
	}

	@Test
	public void test_0284() throws Exception {
		assertNoException("""
					+x
				""");
	}

	@Test
	public void test_0285() throws Exception {
		assertNoException("""
					type\\u006ff x
				""");
	}

	@Test
	public void test_0286() throws Exception {
		assertNoException("""
					type\\u006Ff x
				""");
	}

	@Test
	public void test_0287() throws Exception {
		assertNoException("""
					var c = function() throws Exception {
						return
						1
					}
				""");
	}

	@Test
	public void test_0288() throws Exception {
		assertNoException("""
					for(var x: any = 0; ;) {}
				""");
	}

	@Test
	public void test_0289() throws Exception {
		assertNoException("""
					for(var x: any= 0; ;) {}
				""");
	}

	@Test
	public void test_0290() throws Exception {
		assertNoException("""
					while (false) doSomething()
				""");
	}

	@Test
	public void test_0291() throws Exception {
		assertNoException("""
					while (x < 10) { x++; y--; }
				""");
	}

	@Test
	public void test_0292() throws Exception {
		assertNoException("""
					for(x in list) process(x);
				""");
	}

	@Test
	public void test_0293() throws Exception {
		assertNoException("""
					for(var x in list) process(x);
				""");
	}

	@Test
	public void test_0294() throws Exception {
		assertNoException("""
					for(var x = 42 in list) process(x);
				""");
	}

	@Test
	public void test_0295() throws Exception {
		assertNoException("""
					for(let x in list) process(x);
				""");
	}

	@Test
	public void test_0296() throws Exception {
		assertNoException("""
					for (var i = function() throws Exception { return 10 in [] } in list) process(x);
				""");
	}

	@Test
	public void test_0297() throws Exception {
		assertNoException("""
					for (var i = function() throws Exception {} in list) process(x);
				""");
	}

	@Test
	public void test_0298() throws Exception {
		assertNoException("""
					const Object_keys = typeof Object.keys === 'function'
					    ? Object.keys
					    : function (obj) {
					        var keys: Array<string> = [];
					        for (var key in obj) keys.push(key as string);
					        return keys;
					    }
					;
				""");
	}

	@Test
	public void test_0299() throws Exception {
		assertNoException("""
					do keep(); while (true)
				""");
	}

}
