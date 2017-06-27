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
class GeneratedSmokeTestCases1 {

	@Inject ParseHelper<Script> parseHelper

	// Dummy method - needs to be called to record the expression and schedule it for smoke testing
	def protected void assertNoException(CharSequence expression) throws Exception {
		parseHelper.parse(expression)
	}

	@Test
	def void test_0001() {
		'''
			class X {
				 a: any;
				 a: any; } }
		'''.assertNoException
	}

	@Test
	def void test_0002() {
		'''
			done: while (true) { break done }
		'''.assertNoException
	}

	@Test
	def void test_0003() {
		'''
			while (true) { break }
		'''.assertNoException
	}

	@Test
	def void test_0004() {
		'''
			__proto__: while (true) { break __proto__; }
		'''.assertNoException
	}

	@Test
	def void test_0005() {
		'''
			done: while (true) { break done; }
		'''.assertNoException
	}

	@Test
	def void test_0006() {
		'''
			start: for (;;) break start
		'''.assertNoException
	}

	@Test
	def void test_0007() {
		'''
			start: while (true) break start
		'''.assertNoException
	}

	@Test
	def void test_0008() {
		'''
			__proto__: test
		'''.assertNoException
	}

	@Test
	def void test_0009() {
		'''
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
		'''.assertNoException
	}

	@Test
	def void test_0010() {
		'''
			debugger;
		'''.assertNoException
	}

	@Test
	def void test_0011() {
		'''
			debugger
		'''.assertNoException
	}

	@Test
	def void test_0012() {
		'''
			x & y
		'''.assertNoException
	}

	@Test
	def void test_0013() {
		'''
			x ^ y
		'''.assertNoException
	}

	@Test
	def void test_0014() {
		'''
			x | y
		'''.assertNoException
	}

	@Test
	def void test_0015() {
		'''
			x^y
		'''.assertNoException
	}

	@Test
	def void test_0016() {
		'''
			"Hi\0You"
		'''.assertNoException
	}

	@Test
	def void test_0017() {
		'''
			'use strict';"Hi\112You"
		'''.assertNoException
	}

	@Test
	def void test_0018() {
		'''
			'use strict';"Hi\0112You"
		'''.assertNoException
	}

	@Test
	def void test_0019() {
		'''
			'use strict';"Hi\312You"
		'''.assertNoException
	}

	@Test
	def void test_0020() {
		'''
			'use strict';"Hi\412You"
		'''.assertNoException
	}

	@Test
	def void test_0021() {
		'''
			'use strict';"Hi\812You"
		'''.assertNoException
	}

	@Test
	def void test_0022() {
		'''
			'use strict';"Hi\712You"
		'''.assertNoException
	}

	@Test
	def void test_0023() {
		'''
			'use strict';"Hi\1You"
		'''.assertNoException
	}

	@Test
	def void test_0024() {
		'''
			"Hi\112You"
		'''.assertNoException
	}

	@Test
	def void test_0025() {
		'''
			"Hi\0112You"
		'''.assertNoException
	}

	@Test
	def void test_0026() {
		'''
			"Hi\312You"
		'''.assertNoException
	}

	@Test
	def void test_0027() {
		'''
			"Hi\412You"
		'''.assertNoException
	}

	@Test
	def void test_0028() {
		'''
			"Hi\812You"
		'''.assertNoException
	}

	@Test
	def void test_0029() {
		'''
			"Hi\712You"
		'''.assertNoException
	}

	@Test
	def void test_0030() {
		'''
			"Hi\1You"
		'''.assertNoException
	}

	@Test
	def void test_0031() {
		'''
			"Hi\02You"
		'''.assertNoException
	}

	@Test
	def void test_0032() {
		'''
			"Hi"
		'''.assertNoException
	}

	@Test
	def void test_0033() {
		'''
			"Hi\nworld"
		'''.assertNoException
	}

	@Test
	def void test_0034() {
		'''
			"Hi\012You"
		'''.assertNoException
	}

	@Test
	def void test_0035() {
		'''
			"\xt"
		'''.assertNoException
	}

	@Test
	def void test_0036() {
		'''
			"\n\r\t\v\b\f\\\'\"\0"
		'''.assertNoException
	}

	@Test
	def void test_0037() {
		'''
			"Hi\
			world"
		'''.assertNoException
	}

	@Test
	def void test_0038() {
		'''
			"Hi\
			world"
		'''.assertNoException
	}

	@Test
	def void test_0039() {
		'''
			"\u0061"
		'''.assertNoException
	}

	@Test
	def void test_0040() {
		'''
			"\u{0061}"
		'''.assertNoException
	}

	@Test
	def void test_0041() {
		'''
			"\u{61}"
		'''.assertNoException
	}

	@Test
	def void test_0042() {
		'''
			"\u00"
		'''.assertNoException
	}

	@Test
	def void test_0043() {
		'''
			"\u{00"
		'''.assertNoException
	}

	@Test
	def void test_0044() {
		'''
			"\u{}"
		'''.assertNoException
	}

	@Test
	def void test_0045() {
		'''
			"\x61"
		'''.assertNoException
	}

	@Test
	def void test_0046() {
		'''
			function f(_$) {}
		'''.assertNoException
	}

	@Test
	def void test_0047() {
		'''
			L1: while(true) break L1
			L1: while(true) break L1
		'''.assertNoException
	}

	@Test
	def void test_0048() {
		'''
			L1: while(true) break L1
		'''.assertNoException
	}

	@Test
	def void test_0049() {
		'''
			L1: while(true)
				L2: while(true) break L1
			while(true) break L2
		'''.assertNoException
	}

	@Test
	def void test_0050() {
		'''
			L1: while(true)
				L2: while(true) break L1
		'''.assertNoException
	}

	@Test
	def void test_0051() {
		'''
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
		'''.assertNoException
	}

	@Test
	def void test_0052() {
		'''
			function testcase() {
					        "use strict";

					        try {
					            eval("var yield = 1;")
					            return false;
					        } catch (e) {
					            return e instanceof SyntaxError;
					        }
					}
		'''.assertNoException
	}

	@Test
	def void test_0053() {
		'''
			@Annotation function testcase() {}
		'''.assertNoException
	}

	@Test
	def void test_0054() {
		'''
			(@Dummy function testcase() {})
		'''.assertNoException
	}

	@Test
	def void test_0055() {
		'''
			{ function testcase() {} }
		'''.assertNoException
	}

	@Test
	def void test_0056() {
		'''
			(function testcase() {})
		'''.assertNoException
	}

	@Test
	def void test_0057() {
		'''
			class Callee {
			   private static myPrivateStaticField: string= "myPrivateStaticField";
			   private myPrivateNonStaticField: string= "myPrivateNonStaticField";

			   static get myPrivateStaticAccessor() {
					return this.myPrivateStaticField;
					 }

			   static set myPrivateStaticAccessor(myPrivateStaticParam: string) {
					/*this*/Callee.myPrivateStaticField = myPrivateStaticParam;
					 }

			   get myPrivateNonStaticAccessor() {
					return this.myPrivateNonStaticField;
					 }

			   set myPrivateNonStaticAccessor(myPrivateParam: string) {
					this.myPrivateNonStaticField = myPrivateParam;
					 }

			}
			class Caller {

				call() {
					Callee.myPrivateStaticAccessor = "a"
					var a = Callee.myPrivateStaticAccessor

					var callee: Callee= null;
					callee.myPrivateNonStaticAccessor = "a"
					a = callee.myPrivateNonStaticAccessor
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0058() {
		'''
			class A {
				static m() {
					return this; // type {A}
				}
				m() {
					return this; // A
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0059() {
		'''
			class A extends N4Object {
				oneField = 1;
				static secondField = 1;
			}
			class B extends N4Object {
				thirdField = (new A).oneField;
				static forthField = A.secondField;
			}
		'''.assertNoException
	}

	@Test
	def void test_0060() {
		'''
			class Control extends N4Object {
				x  =1; static y;
				i = (new Control).x;
				static j = new Control.y;

			}
		'''.assertNoException
	}

	@Test
	def void test_0061() {
		'''
			class C {}
			var x = C
			var c: C;
			var y = c.constructor
			var z1 = new y()
			var z2 = new C()
		'''.assertNoException
	}

	@Test
	def void test_0062() {
		'''
			class A {
				one() {
				}
				static two() {
				}
			}
			class B {

				execute() {
					var a: A = null
					a.one
					A.two()
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0063() {
		'''
			class A {

				one() {
				}

				two() {
				this.one()
				}

				static one() {
				}

				static two() {
				this.one()
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0064() {
		'''
			export function foo1() {
			    foo1;
			    foo1();
			    var b = function foo2() {
					foo2()
			    	var c = function foo3() {
			    		foo3()
			    		var d = function foo4() {
			    			foo4()
			    		}
			    	}
			    }
			}
		'''.assertNoException
	}

	@Test
	def void test_0065() {
		'''
			function f1() {
			    var v = function f1() {
					f1()
			    }
			}
		'''.assertNoException
	}

	@Test
	def void test_0066() {
		'''
			var a = function foo( x ) {
				var b = function bar( y ) {
					if (y==1) { foo(2); bar(0); }
				}
				if (x==1) { b(1); }
			};
			a(1);
		'''.assertNoException
	}

	@Test
	def void test_0067() {
		'''
			class A {
				a: any;
				b(): void {}
				any get c() { return null }
				set d(p: any) {}
			}
			class B extends A {}
		'''.assertNoException
	}

	@Test
	def void test_0068() {
		'''
			interface R1 {
				f(p: number): void {}
			}
			interface R2 {
				f(p: string): void {}
			}
			class C implements R1, R2 {}
		'''.assertNoException
	}

	@Test
	def void test_0069() {
		'''
			interface R1 {
				x: string;
			}
			class S {
				x(): void {}
			}
			class C1 extends S implements R1 {}
		'''.assertNoException
	}

	@Test
	def void test_0070() {
		'''
			class A {
				a: any;
				b(): void {}
				any get c() { return null }
				set d(p: any) {}
			}
			class B extends A {
				@Override b(): void {}
				@Override any get c() { return null }
				@Override set d(p: any) {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0071() {
		'''
			"abc
			1+1
		'''.assertNoException
	}

	@Test
	def void test_0072() {
		'''
			'
		'''.assertNoException
	}

	@Test
	def void test_0073() {
		'''
			"
		'''.assertNoException
	}

	@Test
	def void test_0074() {
		'''
			'\'
		'''.assertNoException
	}

	@Test
	def void test_0075() {
		'''
			"\"
		'''.assertNoException
	}

	@Test
	def void test_0076() {
		'''
			'\\'
		'''.assertNoException
	}

	@Test
	def void test_0077() {
		'''
			"\\"
		'''.assertNoException
	}

	@Test
	def void test_0078() {
		'''
			'\123'
		'''.assertNoException
	}

	@Test
	def void test_0079() {
		'''
			'\0123'
		'''.assertNoException
	}

	@Test
	def void test_0080() {
		'''
			"Hi\212You"
		'''.assertNoException
	}

	@Test
	def void test_0081() {
		'''
			"Hi\512You"
		'''.assertNoException
	}

	@Test
	def void test_0082() {
		'''
			"Hi\612You"
		'''.assertNoException
	}

	@Test
	def void test_0083() {
		'''
			""
		'''.assertNoException
	}

	@Test
	def void test_0084() {
		'''
			''
		'''.assertNoException
	}

	@Test
	def void test_0085() {
		'''
			var string = "Hi";
			var num = 1;
			var bool = true;
			var nullL = null;
			var regexp = /someregex/;
			var division = 12 / 5;
		'''.assertNoException
	}

	@Test
	def void test_0086() {
		'''
			x * y + z
		'''.assertNoException
	}

	@Test
	def void test_0087() {
		'''
			x + y / z
		'''.assertNoException
	}

	@Test
	def void test_0088() {
		'''
			x + y % z
		'''.assertNoException
	}

	@Test
	def void test_0089() {
		'''
			x + y * z
		'''.assertNoException
	}

	@Test
	def void test_0090() {
		'''
			x - y % z
		'''.assertNoException
	}

	@Test
	def void test_0091() {
		'''
			/(/
		'''.assertNoException
	}

	@Test
	def void test_0092() {
		'''
			/[[]/
		'''.assertNoException
	}

	@Test
	def void test_0093() {
		'''
			a[/c/]
		'''.assertNoException
	}

	@Test
	def void test_0094() {
		'''
			a(/c/)
		'''.assertNoException
	}

	@Test
	def void test_0095() {
		'''
			a +/c/
		'''.assertNoException
	}

	@Test
	def void test_0096() {
		'''
			var e=/^/g;
		'''.assertNoException
	}

	@Test
	def void test_0097() {
		'''
			/=1/g
		'''.assertNoException
	}

	@Test
	def void test_0098() {
		'''
			i/=1/2
		'''.assertNoException
	}

	@Test
	def void test_0099() {
		'''
			/{/
		'''.assertNoException
	}

	@Test
	def void test_0100() {
		'''
			({}/s)
		'''.assertNoException
	}

	@Test
	def void test_0101() {
		'''
			({})/s
		'''.assertNoException
	}

	@Test
	def void test_0102() {
		'''
			a/b/g
		'''.assertNoException
	}

	@Test
	def void test_0103() {
		'''
			a()/b/g
		'''.assertNoException
	}

	@Test
	def void test_0104() {
		'''
			a[b]/c/g
		'''.assertNoException
	}

	@Test
	def void test_0105() {
		'''
			null/a/g
		'''.assertNoException
	}

	@Test
	def void test_0106() {
		'''
			true/a/g
		'''.assertNoException
	}

	@Test
	def void test_0107() {
		'''
			false/a/g
		'''.assertNoException
	}

	@Test
	def void test_0108() {
		'''
			this/a/g
		'''.assertNoException
	}

	@Test
	def void test_0109() {
		'''
			from/as/get/set/g
		'''.assertNoException
	}

	@Test
	def void test_0110() {
		'''
			{}/function(){return 1}
		'''.assertNoException
	}

	@Test
	def void test_0111() {
		'''
			/1/
		'''.assertNoException
	}

	@Test
	def void test_0112() {
		'''
			/[]]/
		'''.assertNoException
	}

	@Test
	def void test_0113() {
		'''
			/[/
		'''.assertNoException
	}

	@Test
	def void test_0114() {
		'''
			({}/function(){return 1})
		'''.assertNoException
	}

	@Test
	def void test_0115() {
		'''
			//
			/1/
		'''.assertNoException
	}

	@Test
	def void test_0116() {
		'''
			/**//1/
		'''.assertNoException
	}

	@Test
	def void test_0117() {
		'''
			/\\/
		'''.assertNoException
	}

	@Test
	def void test_0118() {
		'''
			x |= 42
		'''.assertNoException
	}

	@Test
	def void test_0119() {
		'''
			x ^= 42
		'''.assertNoException
	}

	@Test
	def void test_0120() {
		'''
			x += 42
		'''.assertNoException
	}

	@Test
	def void test_0121() {
		'''
			x -= 42
		'''.assertNoException
	}

	@Test
	def void test_0122() {
		'''
			x = 42
		'''.assertNoException
	}

	@Test
	def void test_0123() {
		'''
			eval = 42
		'''.assertNoException
	}

	@Test
	def void test_0124() {
		'''
			arguments = 42
		'''.assertNoException
	}

	@Test
	def void test_0125() {
		'''
			x &= 42
		'''.assertNoException
	}

	@Test
	def void test_0126() {
		'''
			x /= 42
		'''.assertNoException
	}

	@Test
	def void test_0127() {
		'''
			x >>= 42
		'''.assertNoException
	}

	@Test
	def void test_0128() {
		'''
			x %= 42
		'''.assertNoException
	}

	@Test
	def void test_0129() {
		'''
			x <<= 42
		'''.assertNoException
	}

	@Test
	def void test_0130() {
		'''
			x *= 42
		'''.assertNoException
	}

	@Test
	def void test_0131() {
		'''
			x = y = z
		'''.assertNoException
	}

	@Test
	def void test_0132() {
		'''
			x >>>= 42
		'''.assertNoException
	}

	@Test
	def void test_0133() {
		'''
			export public class X {}
		'''.assertNoException
	}

	@Test
	def void test_0134() {
		'''
			class A{}
		'''.assertNoException
	}

	@Test
	def void test_0135() {
		'''
			export @Internal public class X {}
		'''.assertNoException
	}

	@Test
	def void test_0136() {
		'''
			export project class X {}
		'''.assertNoException
	}

	@Test
	def void test_0137() {
		'''
			export function x() {}
		'''.assertNoException
	}

	@Test
	def void test_0138() {
		'''
			export @Internal public function x() {}
		'''.assertNoException
	}

	@Test
	def void test_0139() {
		'''
			export public function x() {}
		'''.assertNoException
	}

	@Test
	def void test_0140() {
		'''
			export project function x() {}
		'''.assertNoException
	}

	@Test
	def void test_0141() {
		'''
			public class C {
				f0;
				private f1;
				project f2;
				@Internal protected f4;
				protected f5;
				@Internal public f6;
				public f7;
			}
		'''.assertNoException
	}

	@Test
	def void test_0142() {
		'''
			class								Cprv {}
			export class						Cdef {}
			export project class				Cpro {}
			export @Internal public class		Cpub {}
			export public class 				Cep {}
			interface 							Iprv {}
			export interface 					Idef {}
			export project interface 			Ipro {}
			export @Internal public interface	Ipub {}
			export public interface				Iep {}
			interface 							Rprv {}
			export interface 					Rdef {}
			export project interface 			Rpro {}
			export @Internal public interface	Rpub {}
			export public interface				Rep {}
			enum 								Eprv {L}
			export enum 						Edef {L}
			export project enum 				Epro {L}
			export @Internal public enum		Epub {L}
			export public enum					Eep {L}
		'''.assertNoException
	}

	@Test
	def void test_0143() {
		'''
			export @Internal public var x = ""
		'''.assertNoException
	}

	@Test
	def void test_0144() {
		'''
			export project var x = ""
		'''.assertNoException
	}

	@Test
	def void test_0145() {
		'''
			export @Internal public const x = ""
		'''.assertNoException
	}

	@Test
	def void test_0146() {
		'''
			export project const x = ""
		'''.assertNoException
	}

	@Test
	def void test_0147() {
		'''
			this.undefined
		'''.assertNoException
	}

	@Test
	def void test_0148() {
		'''
			undefined
		'''.assertNoException
	}

	@Test
	def void test_0149() {
		'''
			class C {}
			var c: C= null
		'''.assertNoException
	}

	@Test
	def void test_0150() {
		'''
			x--
		'''.assertNoException
	}

	@Test
	def void test_0151() {
		'''
			eval--
		'''.assertNoException
	}

	@Test
	def void test_0152() {
		'''
			arguments--
		'''.assertNoException
	}

	@Test
	def void test_0153() {
		'''
			x++
		'''.assertNoException
	}

	@Test
	def void test_0154() {
		'''
			eval++
		'''.assertNoException
	}

	@Test
	def void test_0155() {
		'''
			arguments++
		'''.assertNoException
	}

	@Test
	def void test_0156() {
		'''
			class C {
			   getString(): void {
			    	var s: string= ''
			    	s.slice(0, 10)
			   }
			}
		'''.assertNoException
	}

	@Test
	def void test_0157() {
		'''
			x === y
		'''.assertNoException
	}

	@Test
	def void test_0158() {
		'''
			x == y
		'''.assertNoException
	}

	@Test
	def void test_0159() {
		'''
			x != y
		'''.assertNoException
	}

	@Test
	def void test_0160() {
		'''
			x !== y
		'''.assertNoException
	}

	@Test
	def void test_0161() {
		'''
			var re = //;
		'''.assertNoException
	}

	@Test
	def void test_0162() {
		'''
			var re = new RegExp("");
		'''.assertNoException
	}

	@Test
	def void test_0163() {
		'''
			var regExp = /\
			n/;
		'''.assertNoException
	}

	@Test
	def void test_0164() {
		'''
			/a/
		'''.assertNoException
	}

	@Test
	def void test_0165() {
		'''
			/;/
		'''.assertNoException
	}

	@Test
	def void test_0166() {
		'''
			/ /
		'''.assertNoException
	}

	@Test
	def void test_0167() {
		'''
			/\u0041/
		'''.assertNoException
	}

	@Test
	def void test_0168() {
		'''
			/\1/
		'''.assertNoException
	}

	@Test
	def void test_0169() {
		'''
			/\a/
		'''.assertNoException
	}

	@Test
	def void test_0170() {
		'''
			/\;/
		'''.assertNoException
	}

	@Test
	def void test_0171() {
		'''
			/\ /
		'''.assertNoException
	}

	@Test
	def void test_0172() {
		'''
			/*/
		'''.assertNoException
	}

	@Test
	def void test_0173() {
		'''
			///
			.source;
		'''.assertNoException
	}

	@Test
	def void test_0174() {
		'''
			//
			.source;
		'''.assertNoException
	}

	@Test
	def void test_0175() {
		'''
			/
			/
		'''.assertNoException
	}

	@Test
	def void test_0176() {
		'''
			/\u000A/
		'''.assertNoException
	}

	@Test
	def void test_0177() {
		'''
			/
			/
		'''.assertNoException
	}

	@Test
	def void test_0178() {
		'''
			/\u000D/
		'''.assertNoException
	}

	@Test
	def void test_0179() {
		'''
			/\u2028/
		'''.assertNoException
	}

	@Test
	def void test_0180() {
		'''
			/\u2029/
		'''.assertNoException
	}

	@Test
	def void test_0181() {
		'''
			/a\/
		'''.assertNoException
	}

	@Test
	def void test_0182() {
		'''
			/a//.source
		'''.assertNoException
	}

	@Test
	def void test_0183() {
		'''
			/(?:)/g
		'''.assertNoException
	}

	@Test
	def void test_0184() {
		'''
			/(?:)/i
		'''.assertNoException
	}

	@Test
	def void test_0185() {
		'''
			/(?:)/m
		'''.assertNoException
	}

	@Test
	def void test_0186() {
		'''
			/(?:)/gi
		'''.assertNoException
	}

	@Test
	def void test_0187() {
		'''
			/(?:)/mg
		'''.assertNoException
	}

	@Test
	def void test_0188() {
		'''
			/(?:)/mig
		'''.assertNoException
	}

	@Test
	def void test_0189() {
		'''
			/(?:)/\u0067
		'''.assertNoException
	}

	@Test
	def void test_0190() {
		'''
			/(?:)/\u0069
		'''.assertNoException
	}

	@Test
	def void test_0191() {
		'''
			/(?:)/\u006D
		'''.assertNoException
	}

	@Test
	def void test_0192() {
		'''
			/a\1/
		'''.assertNoException
	}

	@Test
	def void test_0193() {
		'''
			/a\a/
		'''.assertNoException
	}

	@Test
	def void test_0194() {
		'''
			/,\;/
		'''.assertNoException
	}

	@Test
	def void test_0195() {
		'''
			/ \ /
		'''.assertNoException
	}

	@Test
	def void test_0196() {
		'''
			x + y
		'''.assertNoException
	}

	@Test
	def void test_0197() {
		'''
			x - y
		'''.assertNoException
	}

	@Test
	def void test_0198() {
		'''
			"use strict" + y
		'''.assertNoException
	}

	@Test
	def void test_0199() {
		'''
			x + y + z
		'''.assertNoException
	}

	@Test
	def void test_0200() {
		'''

			;
		'''.assertNoException
	}

	@Test
	def void test_0201() {
		'''
			/*
			*/
		'''.assertNoException
	}

	@Test
	def void test_0202() {
		'''
			;
		'''.assertNoException
	}

	@Test
	def void test_0203() {
		'''
			//
		'''.assertNoException
	}

	@Test
	def void test_0204() {
		'''
		'''.assertNoException
	}

	@Test
	def void test_0205() {
		'''
			// comment
		'''.assertNoException
	}

	@Test
	def void test_0206() {
		'''
			// comment
		'''.assertNoException
	}

	@Test
	def void test_0207() {
		'''

		'''.assertNoException
	}

	@Test
	def void test_0208() {
		'''
			/(\w+)\s(\w+)/
		'''.assertNoException
	}

	@Test
	def void test_0209() {
		'''
			/[Tt]he\sr\w+/
		'''.assertNoException
	}

	@Test
	def void test_0210() {
		'''
			/\d+/g
		'''.assertNoException
	}

	@Test
	def void test_0211() {
		'''
			/[\(\)-]/g
		'''.assertNoException
	}

	@Test
	def void test_0212() {
		'''
			/(<)|(>)/g
		'''.assertNoException
	}

	@Test
	def void test_0213() {
		'''
			/(\w.+)\s(\w.+)/
		'''.assertNoException
	}

	@Test
	def void test_0214() {
		'''
			/^\d{5}$/
		'''.assertNoException
	}

	@Test
	def void test_0215() {
		'''
			/\s*,\s*/
		'''.assertNoException
	}

	@Test
	def void test_0216() {
		'''
			/^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/
		'''.assertNoException
	}

	@Test
	def void test_0217() {
		'''
			/(^-*\d+$)|(^-*\d+\.\d+$)/
		'''.assertNoException
	}

	@Test
	def void test_0218() {
		'''
			/(http:\/\/\S*)/g
		'''.assertNoException
	}

	@Test
	def void test_0219() {
		'''
			var _ = 2
			var a = function fun1() {
				var x = 1;
				var b = function fun2() {
					var y = 2;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0220() {
		'''
			var a = class class1 {
				x = 1;
				b = class class2 {
					y = 2;
				};
			};
		'''.assertNoException
	}

	@Test
	def void test_0221() {
		'''
			function f(p: ~Object with {a: string; b: string;}) {
				var s: string;
				s = p.a;
				s = p.b;
			}
		'''.assertNoException
	}

	@Test
	def void test_0222() {
		'''
			var r: ~Object with {a: string; b: string;};
			var s: string;
			s = r.a;
			s = r.b;
		'''.assertNoException
	}

	@Test
	def void test_0223() {
		'''
			export var r: ~Object with {a: string; b: string;};
			var s: string;
			s = r.a;
			s = r.b;
		'''.assertNoException
	}

	@Test
	def void test_0224() {
		'''
			export var r: ~Object with {a: string; b: string;};
		'''.assertNoException
	}

	@Test
	def void test_0225() {
		'''
			import r from "A"
			var s: string;
			s = r.a;
			s = r.b;
		'''.assertNoException
	}

	@Test
	def void test_0226() {
		'''
			// line comment
			42
		'''.assertNoException
	}

	@Test
	def void test_0227() {
		'''
			42// line comment
		'''.assertNoException
	}

	@Test
	def void test_0228() {
		'''
			// Hi, world!
			42
		'''.assertNoException
	}

	@Test
	def void test_0229() {
		'''
			//
			42
		'''.assertNoException
	}

	@Test
	def void test_0230() {
		'''
			// Hi, world!

			//   Another hello
			42
		'''.assertNoException
	}

	@Test
	def void test_0231() {
		'''
			/* block comment */ 42
		'''.assertNoException
	}

	@Test
	def void test_0232() {
		'''
			42 /*The*/ /*Answer*/
		'''.assertNoException
	}

	@Test
	def void test_0233() {
		'''
			42 /*The * Answer*/
		'''.assertNoException
	}

	@Test
	def void test_0234() {
		'''
			/* multiline
			comment
			should
			be
			ignored */ 42
		'''.assertNoException
	}

	@Test
	def void test_0235() {
		'''
			/*a
			b*/ 42
		'''.assertNoException
	}

	@Test
	def void test_0236() {
		'''
			/*a
			b*/ 42
		'''.assertNoException
	}

	@Test
	def void test_0237() {
		'''
			/*a
			b*/ 42
		'''.assertNoException
	}

	@Test
	def void test_0238() {
		'''
			/**/ 42
		'''.assertNoException
	}

	@Test
	def void test_0239() {
		'''
			if (x) { // Some comment
			42 }
		'''.assertNoException
	}

	@Test
	def void test_0240() {
		'''
			switch (answer) { case 42: /* perfect */ 42; }
		'''.assertNoException
	}

	@Test
	def void test_0241() {
		'''
			x = { set if(w) { m_if = w } }
		'''.assertNoException
	}

	@Test
	def void test_0242() {
		'''
			x = { set true(w) { m_true = w } }
		'''.assertNoException
	}

	@Test
	def void test_0243() {
		'''
			x = { set false(w) { m_false = w } }
		'''.assertNoException
	}

	@Test
	def void test_0244() {
		'''
			x = { set null(w) { m_null = w } }
		'''.assertNoException
	}

	@Test
	def void test_0245() {
		'''
			x = { answer: 42 }
		'''.assertNoException
	}

	@Test
	def void test_0246() {
		'''
			x = { get: 42 }
		'''.assertNoException
	}

	@Test
	def void test_0247() {
		'''
			x = { set: 43 }
		'''.assertNoException
	}

	@Test
	def void test_0248() {
		'''
			x = { __proto__: 2 }
		'''.assertNoException
	}

	@Test
	def void test_0249() {
		'''
			x = { if: 42 }
		'''.assertNoException
	}

	@Test
	def void test_0250() {
		'''
			x = { true: 42 }
		'''.assertNoException
	}

	@Test
	def void test_0251() {
		'''
			x = { false: 42 }
		'''.assertNoException
	}

	@Test
	def void test_0252() {
		'''
			x = { null: 42 }
		'''.assertNoException
	}

	@Test
	def void test_0253() {
		'''
			x = { get width() { return m_width }, set width(width) { m_width = width; } }
		'''.assertNoException
	}

	@Test
	def void test_0254() {
		'''
			x = { x: 1, x: 2 }
		'''.assertNoException
	}

	@Test
	def void test_0255() {
		'''
			x = { answer: 42, }
		'''.assertNoException
	}

	@Test
	def void test_0256() {
		'''
			x = { get 10() {} }
		'''.assertNoException
	}

	@Test
	def void test_0257() {
		'''
			x = { get width() { return m_width } }
		'''.assertNoException
	}

	@Test
	def void test_0258() {
		'''
			x = { get undef() {} }
		'''.assertNoException
	}

	@Test
	def void test_0259() {
		'''
			x = { get "undef"() {} }
		'''.assertNoException
	}

	@Test
	def void test_0260() {
		'''
			x = { answer: 42,, }
		'''.assertNoException
	}

	@Test
	def void test_0261() {
		'''
			x = { set "null"(w) { m_null = w } }
		'''.assertNoException
	}

	@Test
	def void test_0262() {
		'''
			x = { set 10(w) { m_null = w } }
		'''.assertNoException
	}

	@Test
	def void test_0263() {
		'''
			x = { get if() {} }
		'''.assertNoException
	}

	@Test
	def void test_0264() {
		'''
			x = { get true() {} }
		'''.assertNoException
	}

	@Test
	def void test_0265() {
		'''
			x = { get false() {} }
		'''.assertNoException
	}

	@Test
	def void test_0266() {
		'''
			x = { get null() {} }
		'''.assertNoException
	}

	@Test
	def void test_0267() {
		'''
			x = { "answer": 42 }
		'''.assertNoException
	}

	@Test
	def void test_0268() {
		'''
			x = { "__proto__": 2 }
		'''.assertNoException
	}

	@Test
	def void test_0269() {
		'''
			x = { set width(w) { m_width = w } }
		'''.assertNoException
	}

	@Test
	def void test_0270() {
		'''
			x = {}
		'''.assertNoException
	}

	@Test
	def void test_0271() {
		'''
			x = { }
		'''.assertNoException
	}

	@Test
	def void test_0272() {
		'''
			void x
		'''.assertNoException
	}

	@Test
	def void test_0273() {
		'''
			delete x
		'''.assertNoException
	}

	@Test
	def void test_0274() {
		'''
			typeof x
		'''.assertNoException
	}

	@Test
	def void test_0275() {
		'''
			--x
		'''.assertNoException
	}

	@Test
	def void test_0276() {
		'''
			--eval
		'''.assertNoException
	}

	@Test
	def void test_0277() {
		'''
			--arguments
		'''.assertNoException
	}

	@Test
	def void test_0278() {
		'''
			++x
		'''.assertNoException
	}

	@Test
	def void test_0279() {
		'''
			++eval
		'''.assertNoException
	}

	@Test
	def void test_0280() {
		'''
			++arguments
		'''.assertNoException
	}

	@Test
	def void test_0281() {
		'''
			~x
		'''.assertNoException
	}

	@Test
	def void test_0282() {
		'''
			-x
		'''.assertNoException
	}

	@Test
	def void test_0283() {
		'''
			!x
		'''.assertNoException
	}

	@Test
	def void test_0284() {
		'''
			+x
		'''.assertNoException
	}

	@Test
	def void test_0285() {
		'''
			type\u006ff x
		'''.assertNoException
	}

	@Test
	def void test_0286() {
		'''
			type\u006Ff x
		'''.assertNoException
	}

	@Test
	def void test_0287() {
		'''
			var c = function() {
				return
				1
			}
		'''.assertNoException
	}

	@Test
	def void test_0288() {
		'''
			for(var x: any = 0; ;) {}
		'''.assertNoException
	}

	@Test
	def void test_0289() {
		'''
			for(var x: any= 0; ;) {}
		'''.assertNoException
	}

	@Test
	def void test_0290() {
		'''
			while (false) doSomething()
		'''.assertNoException
	}

	@Test
	def void test_0291() {
		'''
			while (x < 10) { x++; y--; }
		'''.assertNoException
	}

	@Test
	def void test_0292() {
		'''
			for(x in list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0293() {
		'''
			for(var x in list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0294() {
		'''
			for(var x = 42 in list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0295() {
		'''
			for(let x in list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0296() {
		'''
			for (var i = function() { return 10 in [] } in list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0297() {
		'''
			for (var i = function() {} in list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0298() {
		'''
			const Object_keys = typeof Object.keys === 'function'
			    ? Object.keys
			    : function (obj) {
			        var keys: Array<string> = [];
			        for (var key in obj) keys.push(key as string);
			        return keys;
			    }
			;
		'''.assertNoException
	}

	@Test
	def void test_0299() {
		'''
			do keep(); while (true)
		'''.assertNoException
	}


}
