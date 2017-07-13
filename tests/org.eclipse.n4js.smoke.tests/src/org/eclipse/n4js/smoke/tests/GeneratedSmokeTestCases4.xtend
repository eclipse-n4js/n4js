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
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class GeneratedSmokeTestCases4 {

	@Inject ParseHelper<Script> parseHelper

	// Dummy method - needs to be called to record the expression and schedule it for smoke testing
	def protected void assertNoException(CharSequence expression) throws Exception {
		parseHelper.parse(expression)
	}


	@Test
	def void test_0900() {
		'''
			public class C extends DoesNotExist {
			}
		'''.assertNoException
	}

	@Test
	def void test_0901() {
		'''
			public class C extends D implements I {
			}
			public class D {
				public C m()
			}
			public interface I {
				public C m()
			}
		'''.assertNoException
	}

	@Test
	def void test_0902() {
		'''
			public class C extends D implements J {
			}
			public class D implements I {
			}
			public interface I {
				public D m()
			}
			public interface J extends I {
				public C m()
			}
		'''.assertNoException
	}

	@Test
	def void test_0903() {
		'''
			public class C extends D implements I {
			}
			public class D implements J {
			}
			public interface I {
				public C m()
			}
			public interface J extends I {
				public D m()
			}
		'''.assertNoException
	}

	@Test
	def void test_0904() {
		'''
			public class C extends D implements J {
			}
			public class D implements I {
				public D m()
			}
			public interface I {
				public D m()
			}
			public interface J extends I {
				public C m()
			}
		'''.assertNoException
	}

	@Test
	def void test_0905() {
		'''
			public class C extends D implements I {
			}
			public class D implements J {
				public D m()
			}
			public interface I {
				public C m()
			}
			public interface J extends I {
				public D m()
			}
		'''.assertNoException
	}

	@Test
	def void test_0906() {
		'''
			public class C implements R {
			}
			public interface R {
				public C exists()
			}
		'''.assertNoException
	}

	@Test
	def void test_0907() {
		'''
			public class C implements R {
			}
			public interface R {
				public C dup()
				public C dup()
			}
		'''.assertNoException
	}

	@Test
	def void test_0908() {
		'''
			public class C with R {
			}
			public interface R {
			}
		'''.assertNoException
	}

	@Test
	def void test_0909() {
		'''
			public class C implements I {
			}
			public interface I {
				public C exists()
			}
		'''.assertNoException
	}

	@Test
	def void test_0910() {
		'''
			public class C implements I {
			}
			public interface I {
				public C dup()
				public C dup()
			}
		'''.assertNoException
	}

	@Test
	def void test_0911() {
		'''
			public class C implements I {
			}
			public interface I {
			}
		'''.assertNoException
	}

	@Test
	def void test_0912() {
		'''
			interface A {
				s: string;
				foo() { this.s }
			}
		'''.assertNoException
	}

	@Test
	def void test_0913() {
		'''
			class A extends B {
				foo() { this.s }
			}
			class B {
				s: string;
			}
		'''.assertNoException
	}

	@Test
	def void test_0914() {
		'''
			var ol = {
				s: "hello",
				get x() {
					this.s;
					return null;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0915() {
		'''
			class A {
				s: string;
				foo() { this.s }
			}
		'''.assertNoException
	}

	@Test
	def void test_0916() {
		'''
			var ol = {
				s: "hello",
				set x(y) {
					this.s;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0917() {
		'''
			var ol = {
				s: {
					s:"Hi",
					f: function() {
						this.s;
					}
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0918() {
		'''
			var ol = {
				s: "hello",
				f: function() {
					this.s; // force object literal type creation on demand
					var g = function nested () {
						this.s;
					};
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0919() {
		'''
			class A { s: string; }
			@This(A) function f() { this.s }
		'''.assertNoException
	}

	@Test
	def void test_0920() {
		'''
			interface B {
				s: string;
			}
			interface A extends B {
				foo() { this.s }
			}
		'''.assertNoException
	}

	@Test
	def void test_0921() {
		'''
			var ol = {
				s: "hello",
				f: function() {
					this.s;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0922() {
		'''
			0o2;
		'''.assertNoException
	}

	@Test
	def void test_0923() {
		'''
			.14
		'''.assertNoException
	}

	@Test
	def void test_0924() {
		'''
			0X04;
		'''.assertNoException
	}

	@Test
	def void test_0925() {
		'''
			0x10;
		'''.assertNoException
	}

	@Test
	def void test_0926() {
		'''
			0X1A;
		'''.assertNoException
	}

	@Test
	def void test_0927() {
		'''
			3
		'''.assertNoException
	}

	@Test
	def void test_0928() {
		'''
			5
		'''.assertNoException
	}

	@Test
	def void test_0929() {
		'''
			0
		'''.assertNoException
	}

	@Test
	def void test_0930() {
		'''
			1.492417830e-10
		'''.assertNoException
	}

	@Test
	def void test_0931() {
		'''
			3.14159
		'''.assertNoException
	}

	@Test
	def void test_0932() {
		'''
			0b0;
		'''.assertNoException
	}

	@Test
	def void test_0933() {
		'''
			0b1;
		'''.assertNoException
	}

	@Test
	def void test_0934() {
		'''
			0b10;
		'''.assertNoException
	}

	@Test
	def void test_0935() {
		'''
			0b11;
		'''.assertNoException
	}

	@Test
	def void test_0936() {
		'''
			0b10000000000;
		'''.assertNoException
	}

	@Test
	def void test_0937() {
		'''
			0b10000000000000000000000000000000;
		'''.assertNoException
	}

	@Test
	def void test_0938() {
		'''
			0b01111111100000000000000000000000;
		'''.assertNoException
	}

	@Test
	def void test_0939() {
		'''
			0B00000000011111111111111111111111;
		'''.assertNoException
	}

	@Test
	def void test_0940() {
		'''
			02;
		'''.assertNoException
	}

	@Test
	def void test_0941() {
		'''
			6.02214179e+23
		'''.assertNoException
	}

	@Test
	def void test_0942() {
		'''
			0e+100
		'''.assertNoException
	}

	@Test
	def void test_0943() {
		'''
			0x0
		'''.assertNoException
	}

	@Test
	def void test_0944() {
		'''
			0x0;
		'''.assertNoException
	}

	@Test
	def void test_0945() {
		'''
			42
		'''.assertNoException
	}

	@Test
	def void test_0946() {
		'''
			0o012;
		'''.assertNoException
	}

	@Test
	def void test_0947() {
		'''
			0O127;
		'''.assertNoException
	}

	@Test
	def void test_0948() {
		'''
			0x100;
		'''.assertNoException
	}

	@Test
	def void test_0949() {
		'''
			0xabc;
		'''.assertNoException
	}

	@Test
	def void test_0950() {
		'''
			0xdef;
		'''.assertNoException
	}

	@Test
	def void test_0951() {
		'''
			0O0
		'''.assertNoException
	}

	@Test
	def void test_0952() {
		'''
			0o12;
		'''.assertNoException
	}

	@Test
	def void test_0953() {
		'''
			012;
		'''.assertNoException
	}

	@Test
	def void test_0954() {
		'''
			0012;
		'''.assertNoException
	}

	@Test
	def void test_0955() {
		'''
			0129;
		'''.assertNoException
	}

	@Test
	def void test_0956() {
		'''
			a.
		'''.assertNoException
	}

	@Test
	def void test_0957() {
		'''
			return

		'''.assertNoException
	}

	@Test
	def void test_0958() {
		'''
			a.

		'''.assertNoException
	}

	@Test
	def void test_0959() {
		'''
			a./*
			*/
		'''.assertNoException
	}

	@Test
	def void test_0960() {
		'''
			const constOne = null;
			const constTwo = null;
			const f = function ({function(): void} f) { };
			a.
		'''.assertNoException
	}

	@Test
	def void test_0961() {
		'''
			const constOne = null;
			const constTwo = null;
			const f = function ({function(): void} f) { }
			a.
		'''.assertNoException
	}

	@Test
	def void test_0962() {
		'''
			const constOne = null;
			const constTwo = null;
			const f = function ({function(): void} f) { }
			export public class A {
				test1() {
					f(() => { return null; });
					var c = constOne;
				}
				test2() {
					f(() => { return null; });
					var c = constOne;
				}
			}
			a.
		'''.assertNoException
	}

	@Test
	def void test_0963() {
		'''
			const constOne = null;
			const constTwo = null;
			const f = function ({function(): void} f) { }
			export public class A {
				test1() {
					f(() => { return null });
					var c = constOne;
				}
				test2() {
					f(() => { return null });
					var c = constOne;
				}
			}
			a.
		'''.assertNoException
	}

	@Test
	def void test_0964() {
		'''
			f(() => { return null });
			a.
		'''.assertNoException
	}

	@Test
	def void test_0965() {
		'''
			1
			++i
		'''.assertNoException
	}

	@Test
	def void test_0966() {
		'''
			1;++i
		'''.assertNoException
	}

	@Test
	def void test_0967() {
		'''
			1/*
			*/++i
		'''.assertNoException
	}

	@Test
	def void test_0968() {
		'''
			1;/*
			*/
		'''.assertNoException
	}

	@Test
	def void test_0969() {
		'''
			return/*
			*/
		'''.assertNoException
	}

	@Test
	def void test_0970() {
		'''
			return/*
			*/
		'''.assertNoException
	}

	@Test
	def void test_0971() {
		'''
			return
		'''.assertNoException
	}

	@Test
	def void test_0972() {
		'''
			1/*
			*/
		'''.assertNoException
	}

	@Test
	def void test_0973() {
		'''
			1;
		'''.assertNoException
	}

	@Test
	def void test_0974() {
		'''
			1;
		'''.assertNoException
	}

	@Test
	def void test_0975() {
		'''
			1+2
		'''.assertNoException
	}

	@Test
	def void test_0976() {
		'''
			1
			+2
		'''.assertNoException
	}

	@Test
	def void test_0977() {
		'''
			1/*
			*/+2
		'''.assertNoException
	}

	@Test
	def void test_0978() {
		'''
			var x
		'''.assertNoException
	}

	@Test
	def void test_0979() {
		'''
			var \u006F
		'''.assertNoException
	}

	@Test
	def void test_0980() {
		'''
			var implements, interface, package
		'''.assertNoException
	}

	@Test
	def void test_0981() {
		'''
			var private, protected, public, static
		'''.assertNoException
	}

	@Test
	def void test_0982() {
		'''
			var x = 14, y = 3, z = 1977
		'''.assertNoException
	}

	@Test
	def void test_0983() {
		'''
			var eval = 42, arguments = 23
		'''.assertNoException
	}

	@Test
	def void test_0984() {
		'''
			var \u06F
		'''.assertNoException
	}

	@Test
	def void test_0985() {
		'''
			var \u06Fh
		'''.assertNoException
	}

	@Test
	def void test_0986() {
		'''
			var x = 42
		'''.assertNoException
	}

	@Test
	def void test_0987() {
		'''
			var v0 = v0;
		'''.assertNoException
	}

	@Test
	def void test_0988() {
		'''
			var ಠ_ಠ
		'''.assertNoException
	}

	@Test
	def void test_0989() {
		'''
			var x, y;
		'''.assertNoException
	}

	@Test
	def void test_0990() {
		'''
			class A {
				constructor(~this with { s: string; } p) {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0991() {
		'''
			class A {
				constructor(~this p) {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0992() {
		'''
			var name: any= 'global'
		'''.assertNoException
	}

	@Test
	def void test_0993() {
		'''
			public interface I {
				f: string;
			}
			class C {}
		'''.assertNoException
	}

	@Test
	def void test_0994() {
		'''
			@Deprectated interface ~I {}
			@Deprectated interface J {}
		'''.assertNoException
	}

	@Test
	def void test_0995() {
		'''
			export public interface I {
				m(): string;
				get g(): string;
				set s(p: string);
			}
		'''.assertNoException
	}

	@Test
	def void test_0996() {
		'''
			public interface ~I {}
			public interface J {}
		'''.assertNoException
	}

	@Test
	def void test_0997() {
		'''
			foo(bar, baz)
		'''.assertNoException
	}

	@Test
	def void test_0998() {
		'''
			(    foo  )()
		'''.assertNoException
	}

	@Test
	def void test_0999() {
		'''
			new foo.bar()
		'''.assertNoException
	}

	@Test
	def void test_1000() {
		'''
			( new foo).bar()
		'''.assertNoException
	}

	@Test
	def void test_1001() {
		'''
			new foo().bar()
		'''.assertNoException
	}

	@Test
	def void test_1002() {
		'''
			universe.if
		'''.assertNoException
	}

	@Test
	def void test_1003() {
		'''
			universe.true
		'''.assertNoException
	}

	@Test
	def void test_1004() {
		'''
			universe.false
		'''.assertNoException
	}

	@Test
	def void test_1005() {
		'''
			universe.null
		'''.assertNoException
	}

	@Test
	def void test_1006() {
		'''
			new foo[bar]
		'''.assertNoException
	}

	@Test
	def void test_1007() {
		'''
			universe[galaxyName, otherUselessName]
		'''.assertNoException
	}

	@Test
	def void test_1008() {
		'''
			universe[galaxyName]
		'''.assertNoException
	}

	@Test
	def void test_1009() {
		'''
			new new foo
		'''.assertNoException
	}

	@Test
	def void test_1010() {
		'''
			new new foo()
		'''.assertNoException
	}

	@Test
	def void test_1011() {
		'''
			new Button
		'''.assertNoException
	}

	@Test
	def void test_1012() {
		'''
			new Button()
		'''.assertNoException
	}

	@Test
	def void test_1013() {
		'''
			universe.milkyway
		'''.assertNoException
	}

	@Test
	def void test_1014() {
		'''
			universe.milkyway.solarsystem
		'''.assertNoException
	}

	@Test
	def void test_1015() {
		'''
			universe.milkyway.solarsystem.Earth
		'''.assertNoException
	}

	@Test
	def void test_1016() {
		'''
			universe[42].galaxies
		'''.assertNoException
	}

	@Test
	def void test_1017() {
		'''
			universe(42).galaxies
		'''.assertNoException
	}

	@Test
	def void test_1018() {
		'''
			universe(42).galaxies(14, 3, 77).milkyway
		'''.assertNoException
	}

	@Test
	def void test_1019() {
		'''
			earth.asia.Indonesia.prepareForElection(2014)
		'''.assertNoException
	}

	@Test
	def void test_1020() {
		'''
			public interface R {
				public C exists()
			}
		'''.assertNoException
	}

	@Test
	def void test_1021() {
		'''
			public interface R {
				public C dup()
				public C dup()
			}
		'''.assertNoException
	}

	@Test
	def void test_1022() {
		'''
			public interface R {
			}
		'''.assertNoException
	}

	@Test
	def void test_1023() {
		'''
			{ x; ++y; }
		'''.assertNoException
	}

	@Test
	def void test_1024() {
		'''
			{
				x
				++y
			}
		'''.assertNoException
	}

	@Test
	def void test_1025() {
		'''
			{ x; --y; }
		'''.assertNoException
	}

	@Test
	def void test_1026() {
		'''
			{
				x
				--y
			}
		'''.assertNoException
	}

	@Test
	def void test_1027() {
		'''
			var x;
		'''.assertNoException
	}

	@Test
	def void test_1028() {
		'''
			var x /* comment */;
		'''.assertNoException
	}

	@Test
	def void test_1029() {
		'''
			{
				var x = 14, y = 3;
				z;
			}
		'''.assertNoException
	}

	@Test
	def void test_1030() {
		'''
			{ var x = 14, y = 3
			z; }
		'''.assertNoException
	}

	@Test
	def void test_1031() {
		'''
			while (true) {
				continue;
				there;
			}
		'''.assertNoException
	}

	@Test
	def void test_1032() {
		'''
			while (true) { continue
			there; }
		'''.assertNoException
	}

	@Test
	def void test_1033() {
		'''
			while (true) { continue // Comment
			there; }
		'''.assertNoException
	}

	@Test
	def void test_1034() {
		'''
			while (true) { continue /* Multiline
			Comment */ there; }
		'''.assertNoException
	}

	@Test
	def void test_1035() {
		'''
			while (true) {
				break;
				there;
			}
		'''.assertNoException
	}

	@Test
	def void test_1036() {
		'''
			while (true) { break
			there; }
		'''.assertNoException
	}

	@Test
	def void test_1037() {
		'''
			while (true) { break // Comment
			there; }
		'''.assertNoException
	}

	@Test
	def void test_1038() {
		'''
			while (true) { break /* Multiline
			Comment */ there; }
		'''.assertNoException
	}

	@Test
	def void test_1039() {
		'''
			(function(){
				return;
				x;
			})
		'''.assertNoException
	}

	@Test
	def void test_1040() {
		'''
			(function(){ return
			x; })
		'''.assertNoException
	}

	@Test
	def void test_1041() {
		'''
			(function(){ return//Comment
			x; })
		'''.assertNoException
	}

	@Test
	def void test_1042() {
		'''
			(function(){ return/* Multiline
			Comment */ x; })
		'''.assertNoException
	}

	@Test
	def void test_1043() {
		'''
			{
				throw error;
				error;
			}
		'''.assertNoException
	}

	@Test
	def void test_1044() {
		'''
			{ throw error
			error; }
		'''.assertNoException
	}

	@Test
	def void test_1045() {
		'''
			{ throw error// Comment
			error; }
		'''.assertNoException
	}

	@Test
	def void test_1046() {
		'''
			{ throw error/* Multiline
			Comment */ error; }
		'''.assertNoException
	}

	@Test
	def void test_1047() {
		'''
			1;++2;
		'''.assertNoException
	}

	@Test
	def void test_1048() {
		'''
			1
			++2
		'''.assertNoException
	}

	@Test
	def void test_1049() {
		'''
			1+ ++2;
		'''.assertNoException
	}

	@Test
	def void test_1050() {
		'''
			1+
			++2
		'''.assertNoException
	}

	@Test
	def void test_1051() {
		'''
			var name = "You"["Hi", "Goodbye"].forEach(function(val) {
				value.print();
			};
		'''.assertNoException
	}

	@Test
	def void test_1052() {
		'''
			var name = "You"
			["Hi", "Goodbye"].forEach(function(val) {
				value.print()
			}
		'''.assertNoException
	}

	@Test
	def void test_1053() {
		'''
			namespace.makeCounter = function() {
				var counter = 0;
				return function() {
					return counter++;
				};
			}(function() {
				namespace.exportedObject = function() {
					1+2;
				};
			})();
		'''.assertNoException
	}

	@Test
	def void test_1054() {
		'''
			namespace.makeCounter = function() {
				var counter = 0
				return function() {
					return counter++
				}
			}
			(function() {
				namespace.exportedObject = function() {
					1+2
				}
			})()
		'''.assertNoException
	}

	@Test
	def void test_1055() {
		'''
			1++;2;
		'''.assertNoException
	}

	@Test
	def void test_1056() {
		'''
			1++
			2
		'''.assertNoException
	}

	@Test
	def void test_1057() {
		'''
			1++ +2;
		'''.assertNoException
	}

	@Test
	def void test_1058() {
		'''
			1++ +
			2
		'''.assertNoException
	}

	@Test
	def void test_1059() {
		'''
			{1;2;} 3;
		'''.assertNoException
	}

	@Test
	def void test_1060() {
		'''
			return; 1+2;
		'''.assertNoException
	}

	@Test
	def void test_1061() {
		'''
			return
			1 + 2
		'''.assertNoException
	}

	@Test
	def void test_1062() {
		'''
			a=b;++c;
		'''.assertNoException
	}

	@Test
	def void test_1063() {
		'''
			a = b
			++c
		'''.assertNoException
	}

	@Test
	def void test_1064() {
		'''
			b(1+2).print();
		'''.assertNoException
	}

	@Test
	def void test_1065() {
		'''
			b
			(1+2).print()
		'''.assertNoException
	}

	@Test
	def void test_1066() {
		'''
			a=b/hi/g.exec(c).map(d);
		'''.assertNoException
	}

	@Test
	def void test_1067() {
		'''
			a = b
			/hi/g.exec(c).map(d)
		'''.assertNoException
	}

	@Test
	def void test_1068() {
		'''
			1+2;
		'''.assertNoException
	}

	@Test
	def void test_1069() {
		'''
			1+
			2
		'''.assertNoException
	}

	@Test
	def void test_1070() {
		'''
			var x;
			y
		'''.assertNoException
	}

	@Test
	def void test_1071() {
		'''
			var x
			y
		'''.assertNoException
	}

	@Test
	def void test_1072() {
		'''
			var x y;
		'''.assertNoException
	}

	@Test
	def void test_1073() {
		'''
			var x y
		'''.assertNoException
	}

	@Test
	def void test_1074() {
		'''
			var x /*
			*/ y
		'''.assertNoException
	}

	@Test
	def void test_1075() {
		'''
			@This(any)
			function <S extends string, T, P> len(S s, t: T, P p, a, n: number?, string... vas) : number {
				return (s+t+p+a+n+vas).length
			}
			(@This(any)
			function <S extends string, T, P> len(S s, t: T, P p, a, n: number?, string... vas) : number {
				return (s+t+p+a+n+vas).length
			})
		'''.assertNoException
	}

	@Test
	def void test_1076() {
		'''
			var x = @This(any) function <T> () {
			}
			var y = function <T> () {
			}
			(@This(any) function <T> () {
			})
			(function <T> () {
			})
		'''.assertNoException
	}

	@Test
	def void test_1077() {
		'''
			var x = @This(any) function <T> f() {
			}
			var y = function <T> f() {
			}
			@This(any) function <T> f() {
			}
			function <T> f() {
			}
		'''.assertNoException
	}

	@Test
	def void test_1078() {
		'''
			export class X {}
			export class Z {}
		'''.assertNoException
	}

	@Test
	def void test_1079() {
		'''
			export class Y {}
			export class Z {}
		'''.assertNoException
	}

	@Test
	def void test_1080() {
		'''
			import * as NX from 'a/X';
			import * as NX from 'b/Y';
		'''.assertNoException
	}

	@Test
	def void test_1081() {
		'''
			import X from 'a/X';
			import { Y as X } from 'b/Y';
		'''.assertNoException
	}

	@Test
	def void test_1082() {
		'''
			import { X as N } from 'a/X';
			import * as N from 'b/Y';
		'''.assertNoException
	}

	@Test
	def void test_1083() {
		'''
			import * as N from 'a/X';
			import { Y as N } from 'b/Y';
		'''.assertNoException
	}

	@Test
	def void test_1084() {
		'''
			import X from 'a/X';
			class X{};
		'''.assertNoException
	}

	@Test
	def void test_1085() {
		'''
			import * as X from 'a/X';
			class X{};
		'''.assertNoException
	}

	@Test
	def void test_1086() {
		'''
			import *  as N from 'a/X';
			var x: X;
		'''.assertNoException
	}

	@Test
	def void test_1087() {
		'''
			export class Z {}
		'''.assertNoException
	}

	@Test
	def void test_1088() {
		'''
			import Z from 'c/Z';
			import { Z as Z1 } from 'b/Y';
			import * as N from 'a/X';
			var a: any;
		'''.assertNoException
	}

	@Test
	def void test_1089() {
		'''
			import * as N from 'a/X';
			import * as N from 'a/X';
		'''.assertNoException
	}

	@Test
	def void test_1090() {
		'''
			import X from 'a/X';
			import X from 'a/X';
		'''.assertNoException
	}

	@Test
	def void test_1091() {
		'''
			import { X as XXX } from 'a/X';
			var x: X;
		'''.assertNoException
	}

	@Test
	def void test_1092() {
		'''
			import * as N1 from 'a/X';
			import * as N2 from 'a/X';
			var N1.x: X;
		'''.assertNoException
	}

	@Test
	def void test_1093() {
		'''
			import Z from 'a/X';
			import { Z as Z1 } from 'a/X';
		'''.assertNoException
	}

	@Test
	def void test_1094() {
		'''
			import { Z as Z1 } from 'a/X';
			import Z from 'a/X';
		'''.assertNoException
	}

	@Test
	def void test_1095() {
		'''
			import { Z as Z1 } from 'a/X';
			import { Z as Z2 } from 'a/X';
		'''.assertNoException
	}

	@Test
	def void test_1096() {
		'''
			import * as NX from 'a/X';
			import Z from 'a/X';
		'''.assertNoException
	}

	@Test
	def void test_1097() {
		'''
			import Z from 'a/X';
			import * as NX from 'a/X';
		'''.assertNoException
	}

	@Test
	def void test_1098() {
		'''
			function myFunction(x, y, z) { }
			var args = [0, 1, 2];
			myFunction(...args);
		'''.assertNoException
	}

	@Test
	def void test_1099() {
		'''
			switch (answer) { case 42: hi(); break; }
		'''.assertNoException
	}

	@Test
	def void test_1100() {
		'''
			switch (x) {}
		'''.assertNoException
	}

	@Test
	def void test_1101() {
		'''
			switch (answer) { case 42: hi(); break; default: break }
		'''.assertNoException
	}

	@Test
	def void test_1102() {
		'''
			class A { @
		'''.assertNoException
	}

	@Test
	def void test_1103() {
		'''
			function <T> foo(t: T): void {}
			function <T> bar(): T {}
		'''.assertNoException
	}

	@Test
	def void test_1104() {
		'''
			class A{}
			class B{}
			class C extends A{}

			class G<S, T extends A, U extends B> {
			}

			var G<Number,C,B> x;
		'''.assertNoException
	}

	@Test
	def void test_1105() {
		'''
			class A{}
			class C<T> {}
			class F<S,T> {}

			var C<A> c;
			var F<A,C<A>> f;
		'''.assertNoException
	}

	@Test
	def void test_1106() {
		'''
			class A{}
			class C {
				public <T> foo(): T {return null;}
				public <T> bar(t: T): void {}
			}

			var c: C;
			<A>c.foo();
		'''.assertNoException
	}

	@Test
	def void test_1107() {
		'''
			class A{}
			class C<T> {
				public t: T;
				public foo(): T {return null;}
				public bar(t: T): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_1108() {
		'''
			class A{}
			class C<T> {}
			class D extends C<A> {}
			class E<S> extends C<S> {}


			class F<S,T> {}
			class G<U,V> extends F<U,V> {}
			class H extends F<A, A> {}

			class I<S,T> extends F<C<T>,F<S,C<T>>> {}

		'''.assertNoException
	}

	@Test
	def void test_1109() {
		'''
			import Container from "p/Container"

			var Container<String> stringContainer = new Container();
			stringContainer.setItem("Hi");
			var String s = stringContainer.getItem();
		'''.assertNoException
	}

	@Test
	def void test_1110() {
		'''
			class A{}
			class C {
				public <T> foo(): T {return null;}
				public <T> bar(t: T): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_1111() {
		'''
			class A{}
			class C {
				public <T> foo(): T {}
			}
			var c: C;
			c.<A>foo();
		'''.assertNoException
	}

	@Test
	def void test_1112() {
		'''
			class Container<T> {
				private item: T;

				getItem(): T {
					return item;
				}

				setItem(item: T): void {
					this.item = item;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_1113() {
		'''
			function <T> foo(t: T): void {}
			 var a: A;
			<A>foo(a);
			bar();
		'''.assertNoException
	}

	@Test
	def void test_1114() {
		'''
			interface I {
				foo(): void
			}
			interface I2 extends I {
				bar(): void
			}
			interface R extends I {
				data: any;
				foo(): void {}
			}
			interface S extends R, I2 {
				bar(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_1115() {
		'''
			public interface A {
				foo() { return null}
				public abstract bar(p: A): any
			}

			public interface C<T extends A> {
				<S> foo(p: union{A,C}) { return null; }
				abstract bar()
				baz(p: A?) { }
			}
		'''.assertNoException
	}

	@Test
	def void test_1116() {
		'''
			interface R {}

		'''.assertNoException
	}

	@Test
	def void test_1117() {
		'''
			interface R {
				data: any;
				foo(): void {}
			}
			interface S extends R {
				bar(): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_1118() {
		'''
			var String s = null
			s.doesNotExist
		'''.assertNoException
	}

	@Test
	def void test_1119() {
		'''
			var UnknownType u = null
			u.doesNotExist
		'''.assertNoException
	}

	@Test
	def void test_1120() {
		'''
			var String s = null
			s.doesNotExist.either
		'''.assertNoException
	}

	@Test
	def void test_1121() {
		'''
			var String+ s = null
			s.doesNotExist
		'''.assertNoException
	}

	@Test
	def void test_1122() {
		'''
			var u = doesNotExist
			u.either
		'''.assertNoException
	}

	@Test
	def void test_1123() {
		'''
			({a,prop:b}=null); // simple case
			({a,prop1:{b},prop2:[x,{c},y]}=null); // nesting cases
			for({a,prop1:{b},prop2:[x,{c},y]} in null){} // for..in loop
			for({a,prop1:{b},prop2:[x,{c},y]} of null){} // for..of loop
		'''.assertNoException
	}

	@Test
	def void test_1124() {
		'''
			x = [1,...2,3]
		'''.assertNoException
	}

	@Test
	def void test_1125() {
		'''
			x = {a,prop:b};
		'''.assertNoException
	}

	@Test
	def void test_1126() {
		'''
			([a,...b]=null); // simple case
			([a,{prop:[b,...c]},[x,[...d],y]]=null); // nesting cases
		'''.assertNoException
	}

	@Test
	def void test_1127() {
		'''
			public interface I {
				public C exists()
			}
		'''.assertNoException
	}

	@Test
	def void test_1128() {
		'''
			public interface I {
				public C dup()
				public C dup()
			}
		'''.assertNoException
	}

	@Test
	def void test_1129() {
		'''
			public interface I {
			}
		'''.assertNoException
	}

	@Test
	def void test_1130() {
		'''
			public interface I {
				name: string;
			}
		'''.assertNoException
	}

	@Test
	def void test_1131() {
		'''
			export public class A {}
		'''.assertNoException
	}

	@Test
	def void test_1132() {
		'''
			export public class X {}
		'''.assertNoException
	}

	@Test
	def void test_1133() {
		'''
			export @Deprecated interface I {}
			export @Deprecated interface ~J {}
		'''.assertNoException
	}

	@Test
	def void test_1134() {
		'''
			@Deprecated interface I {}
			@Deprecated	interface ~J {}
		'''.assertNoException
	}

	@Test
	def void test_1135() {
		'''
			public interface I {}
			public class C {
				public fNominal(I p): void
				public fStructual(~I p): void
				public fStucturalField(~~I p): void
			}
		'''.assertNoException
	}

	@Test
	def void test_1136() {
		'''
			interface I {}
			interface ~J {}
		'''.assertNoException
	}

	@Test
	def void test_1137() {
		'''
			public interface ~~I {}
		'''.assertNoException
	}

	@Test
	def void test_1138() {
		'''
			export interface I {}
			export interface ~J {}
		'''.assertNoException
	}

	@Test
	def void test_1139() {
		'''
			x = []
		'''.assertNoException
	}

	@Test
	def void test_1140() {
		'''
			x = [ ]
		'''.assertNoException
	}

	@Test
	def void test_1141() {
		'''
			x = [ 42 ]
		'''.assertNoException
	}

	@Test
	def void test_1142() {
		'''
			x = [ 42, ]
		'''.assertNoException
	}

	@Test
	def void test_1143() {
		'''
			x = [ ,, 42 ]
		'''.assertNoException
	}

	@Test
	def void test_1144() {
		'''
			x = [ 1, 2, 3, ]
		'''.assertNoException
	}

	@Test
	def void test_1145() {
		'''
			x = [ 1, 2,, 3, ]
		'''.assertNoException
	}

	@Test
	def void test_1146() {
		'''
			x = [ 1, 2,, 3,, ]
		'''.assertNoException
	}

	@Test
	def void test_1147() {
		'''
			x >> y
		'''.assertNoException
	}

	@Test
	def void test_1148() {
		'''
			x << y
		'''.assertNoException
	}

	@Test
	def void test_1149() {
		'''
			x >>> y
		'''.assertNoException
	}

	@Test
	def void test_1150() {
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


			var G<A> gTypeRef;
			var G<G<G<A>>> gNested;
			var G<?> gWildcard;
			var G<? extends A> gUpperA;
			var G<? super B> gLowerB;


		'''.assertNoException
	}

	@Test
	def void test_1151() {
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

			var x: any;
			var any+ xdyn;
		'''.assertNoException
	}

	@Test
	def void test_1152() {
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

		'''.assertNoException
	}

	@Test
	def void test_1153() {
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


			function foo(): void{};
		'''.assertNoException
	}

	@Test
	def void test_1154() {
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


			var intersection{A,D} i;
		'''.assertNoException
	}

	@Test
	def void test_1155() {
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


			var union{A,B,C} u;
		'''.assertNoException
	}

	@Test
	def void test_1156() {
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


			var ~Object with {a: A;b: B;} r;
			var ~A with{b: B;}+ rd;
		'''.assertNoException
	}

	@Test
	def void test_1157() {
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


			var a: A, G<A> g, H<A,B> h, G<? extends A> ga, G<? super B> gb, R r, I i, E e;
			var A+ ad;
			var G<A>+ gd;
		'''.assertNoException
	}

	@Test
	def void test_1158() {
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


			var {function()} f,
				{function(b: B):A} map,
				{function(b: B):{function(a: A):C}} weird;
		'''.assertNoException
	}

	@Test
	def void test_1159() {
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


			class X { foo(): void { var This copy; } }
		'''.assertNoException
	}

	@Test
	def void test_1160() {
		'''
			class A{}
			class G<T> {}
			class GUpperA<T extends A> {}
			class GUpperA<T extends G<T>> {}
		'''.assertNoException
	}

	@Test
	def void test_1161() {
		'''
			interface I {
				foo(): void
			}
			interface I2 extends I {
				bar(): void
			}
		'''.assertNoException
	}

	@Test
	def void test_1162() {
		'''
			interface I {}
		'''.assertNoException
	}

	@Test
	def void test_1163() {
		'''
			public interface A {
				foo()
				public bar(p: A): any
			}

			public interface C<T extends A> {
				<S> foo(p: union{A,C})
				abstract bar()
				baz(p: A?)
			}

		'''.assertNoException
	}

	@Test
	def void test_1164() {
		'''
			var a = 1
			class C {
				a: C;
				m() {
					a // outer a
					this.a // local A
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_1165() {
		'''
			var a = 1
			function f() {
				"use strict"
				this.a
			}
		'''.assertNoException
	}

	@Test
	def void test_1166() {
		'''
			var o = {
				nested: {
					g: function() {
					}
				},
				callNestedG: function () {
					this.nested.g();
				},
			}
		'''.assertNoException
	}

	@Test
	def void test_1167() {
		'''
			export class C<T> {
			    items_: any;
			    public indexOf(item: T): void {
			    	return _.indexOf(this.items_, item);
			    }
			}
			var _ = class _ {
				static indexOf(p1: any, p2: any): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_1168() {
		'''
			export class C<T> {
			    items_: any;
			    public indexOf(item: T): void {
			    	return _.indexOf(this.items_, item);
			    }
			}
			class _ {
				static indexOf(p1: any, p2: any): void {}
			}
		'''.assertNoException
	}

	@Test
	def void test_1169() {
		'''
			var name = 'a'
			var x = {
			    name : 'b',
			    y: {
			        name: 'c',
			        get z() {
			        	return this.name
			        }
			    }
			}
		'''.assertNoException
	}

	@Test
	def void test_1170() {
		'''
			var name = 'a'
			var x = {
			    name : 'b',
			 get y(): string {
			    	return {
				        name: 'c',
				        z: this.name
				    }.name
			    }
			}
		'''.assertNoException
	}

	@Test
	def void test_1171() {
		'''
			"use strict"
			"here"
		'''.assertNoException
	}

	@Test
	def void test_1172() {
		'''
			function foo() {
				"use strict"
				"here"
			}
		'''.assertNoException
	}

	@Test
	def void test_1173() {
		'''
			function foo() {
				"nothere"
			}
			function bar() {
				"use strict"
				"here"
			}
		'''.assertNoException
	}

	@Test
	def void test_1174() {
		'''
			"here"
		'''.assertNoException
	}

	@Test
	def void test_1175() {
		'''
			function foo() {
				"here"
			}
		'''.assertNoException
	}

	@Test
	def void test_1176() {
		'''
			function foo() {
				"use strict"
				"nothere"
			}
			function bar() {
				"here"
			}
		'''.assertNoException
	}

	@Test
	def void test_1177() {
		'''
			public class C {
			}
			external public class D {
			}
		'''.assertNoException
	}

	@Test
	def void test_1178() {
		'''
			(function() { super; })
		'''.assertNoException
	}

	@Test
	def void test_1179() {
		'''
			(function() { super(); })
		'''.assertNoException
	}

	@Test
	def void test_1180() {
		'''
			(function() { super.m(); })
		'''.assertNoException
	}

	@Test
	def void test_1181() {
		'''
			super;
		'''.assertNoException
	}

	@Test
	def void test_1182() {
		'''
			super();
		'''.assertNoException
	}

	@Test
	def void test_1183() {
		'''
			super.m();
		'''.assertNoException
	}

	@Test
	def void test_1184() {
		'''
			{ super; }
		'''.assertNoException
	}

	@Test
	def void test_1185() {
		'''
			{ super(); }
		'''.assertNoException
	}

	@Test
	def void test_1186() {
		'''
			{ super.m(); }
		'''.assertNoException
	}

	@Test
	def void test_1187() {
		'''
			class C { constructor() { super; } }
		'''.assertNoException
	}

	@Test
	def void test_1188() {
		'''
			class C { constructor() { super(); } }
		'''.assertNoException
	}

	@Test
	def void test_1189() {
		'''
			class C { constructor() { super.a(); } }
		'''.assertNoException
	}

	@Test
	def void test_1190() {
		'''
			{ a: super; }
		'''.assertNoException
	}

	@Test
	def void test_1191() {
		'''
			{ a: super(); }
		'''.assertNoException
	}

	@Test
	def void test_1192() {
		'''
			{ a: super.m(); }
		'''.assertNoException
	}

	@Test
	def void test_1193() {
		'''
			interface C { m() { super; } }
		'''.assertNoException
	}

	@Test
	def void test_1194() {
		'''
			interface C { m() { super(); } }
		'''.assertNoException
	}

	@Test
	def void test_1195() {
		'''
			interface C { m() { super.a(); } }
		'''.assertNoException
	}

	@Test
	def void test_1196() {
		'''
			class C { m() { super; } }
		'''.assertNoException
	}

	@Test
	def void test_1197() {
		'''
			class C { m() { super(); } }
		'''.assertNoException
	}

	@Test
	def void test_1198() {
		'''
			class C { m() { super.a(); } }
		'''.assertNoException
	}

	@Test
	def void test_1199() {
		'''
			public class C {
				public fNominal(p: C): void
				public fStructual(~p: C): void
				public fStucturalField(~~p: C): void
			}
		'''.assertNoException
	}

	@Test
	def void test_1200() {
		'''
			@Deprectated class C {}
			@Deprectated class ~D {}
		'''.assertNoException
	}

	@Test
	def void test_1201() {
		'''
			export class C {}
			export class ~D {}
		'''.assertNoException
	}

	@Test
	def void test_1202() {
		'''
			class C{};
			var ~~x: C, i = ~~1, i = ~~~1;
		'''.assertNoException
	}

	@Test
	def void test_1203() {
		'''
			class C {}
			class ~D {}
		'''.assertNoException
	}

	@Test
	def void test_1204() {
		'''
			~~x
		'''.assertNoException
	}

	@Test
	def void test_1205() {
		'''
			export @Deprecated class C {}
			export @Deprecated class ~D {}
		'''.assertNoException
	}

	@Test
	def void test_1206() {
		'''
			public class ~~D {}
		'''.assertNoException
	}

	@Test
	def void test_1207() {
		'''
			function test(t, t) { }
		'''.assertNoException
	}

	@Test
	def void test_1208() {
		'''
			function eval() { }
		'''.assertNoException
	}

	@Test
	def void test_1209() {
		'''
			var hi = function eval() { };
		'''.assertNoException
	}

	@Test
	def void test_1210() {
		'''
			var hi = function arguments() { };
		'''.assertNoException
	}

	@Test
	def void test_1211() {
		'''
			(function test(t, t) { })
		'''.assertNoException
	}

	@Test
	def void test_1212() {
		'''
			function arguments() { }
		'''.assertNoException
	}

	@Test
	def void test_1213() {
		'''
			function eval() { function inner() { "use strict" } }
		'''.assertNoException
	}

	@Test
	def void test_1214() {
		'''
			function hello() { sayHi(); }
		'''.assertNoException
	}

	@Test
	def void test_1215() {
		'''
			var hi = function() { sayHi() };
		'''.assertNoException
	}

	@Test
	def void test_1216() {
		'''
			function hello(a) { sayHi(); }
		'''.assertNoException
	}

	@Test
	def void test_1217() {
		'''
			function hello(a, b) { sayHi(); }
		'''.assertNoException
	}

	@Test
	def void test_1218() {
		'''
			var ol = {
				target: "hello"
			}
			ol.target;
		'''.assertNoException
	}

	@Test
	def void test_1219() {
		'''
			var ol = {
				get target() {return null}
			}
			ol.target=null;
		'''.assertNoException
	}

	@Test
	def void test_1220() {
		'''
			var ol = {
				get target() {return null}
			}
			ol.target;
		'''.assertNoException
	}

	@Test
	def void test_1221() {
		'''
			var ol = {
				set target(x) {}
			}
			ol.target;
		'''.assertNoException
	}

	@Test
	def void test_1222() {
		'''
			var ol = {
				set target(x) {}
			}
			ol.target=null;
		'''.assertNoException
	}

	@Test
	def void test_1223() {
		'''
			function <T> f1() {
			    var t: T;
			}
		'''.assertNoException
	}

	@Test
	def void test_1224() {
		'''
			function <T> f1(t: T, U u): T {
			    return null;
			}
		'''.assertNoException
	}

	@Test
	def void test_1225() {
		'''
			function f(any) {
						f({});
						g;
					}
		'''.assertNoException
	}

	@Test
	def void test_1226() {
		'''
			function g ( any ) {
				f({})
				g ;
			}


			function f ( any ) {
				f({}
				g ;
			}
		'''.assertNoException
	}

	@Test
	def void test_1227() {
		'''
			function f ( any ) {
				f({}
				g ;
			}
			function g ( any ) {
				f({})
				g ;
			}

		'''.assertNoException
	}

	@Test
	def void test_1228() {
		'''
			function f(any) {
						f({})
						g;
					}
		'''.assertNoException
	}

	@Test
	def void test_1229() {
		'''
			function f(any) {
				f({}
				g;
			}
		'''.assertNoException
	}

	@Test
	def void test_1230() {
		'''
			enum E {
				A , B
			}
			var  e: E = E.A
		'''.assertNoException
	}

	@Test
	def void test_1231() {
		'''
			export class A {
			}
		'''.assertNoException
	}

	@Test
	def void test_1232() {
		'''
			import * as N from 'a'
			var a: N.A
		'''.assertNoException
	}

	@Test
	def void test_1233() {
		'''
			import * as N from 'a'
			export var a: N.A
		'''.assertNoException
	}

	@Test
	def void test_1234() {
		'''
			export class A {
				get p(): String {}
				set p(newP: String) {}
			}
		'''.assertNoException
	}

	@Test
	def void test_1235() {
		'''
			import * as N from 'a'
			var a: N.A= null
			a.p = ""
		'''.assertNoException
	}

	@Test
	def void test_1236() {
		'''
			var list: List<List<C>>;
		'''.assertNoException
	}

	@Test
	def void test_1237() {
		'''
			var list: List<List<List<C>>>;
		'''.assertNoException
	}

	/**
	 * This tests contains an error (old type annotation). Nevertheless this
	 * must not lead to any exceptions! (Cf. test_1163)
	 */
	@Test
	def void test_1238() {
		'''
			public interface <T extends A> {
				<S> foo(union{A,C} p)
				abstract bar()
				baz(p: A?)
			}
		'''.assertNoException
	}
}
