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
class GeneratedSmokeTestCases2 {

	@Inject ParseHelper<Script> parseHelper

	// Dummy method - needs to be called to record the expression and schedule it for smoke testing
	def protected void assertNoException(CharSequence expression) throws Exception {
		parseHelper.parse(expression)
	}


	@Test
	def void test_0300() {
		'''
			do keep(); while (true);
		'''.assertNoException
	}

	@Test
	def void test_0301() {
		'''
			do { x++; y--; } while (x < 10)
		'''.assertNoException
	}

	@Test
	def void test_0302() {
		'''
			for(;;);
		'''.assertNoException
	}

	@Test
	def void test_0303() {
		'''
			for(;;){}
		'''.assertNoException
	}

	@Test
	def void test_0304() {
		'''
			for(x = 0;;);
		'''.assertNoException
	}

	@Test
	def void test_0305() {
		'''
			for(var x = 0;;);
		'''.assertNoException
	}

	@Test
	def void test_0306() {
		'''
			for(let x = 0;;);
		'''.assertNoException
	}

	@Test
	def void test_0307() {
		'''
			for(var x = 0, y = 1;;);
		'''.assertNoException
	}

	@Test
	def void test_0308() {
		'''
			for(x = 0; x < 42;);
		'''.assertNoException
	}

	@Test
	def void test_0309() {
		'''
			for(x = 0; x < 42; x++);
		'''.assertNoException
	}

	@Test
	def void test_0310() {
		'''
			for(x = 0; x < 42; x++) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0311() {
		'''
			{ do { } while (false) false }
		'''.assertNoException
	}

	@Test
	def void test_0312() {
		'''
			{ do { } while (false)false }
		'''.assertNoException
	}

	@Test
	def void test_0313() {
		'''
			for(x of list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0314() {
		'''
			for(var x of list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0315() {
		'''
			for(var x = 42 of list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0316() {
		'''
			for(let x of list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0317() {
		'''
			for (var i = function() { return 10 in [] } of list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0318() {
		'''
			for (var i = function() {} of list) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0319() {
		'''
			for (var i = "" of ["a","b"]) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0320() {
		'''
			for (of of []) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0321() {
		'''
			for (var of of []) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0322() {
		'''
			for (var of = "" of []) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0323() {
		'''
			for (let of of []) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0324() {
		'''
			for (let of = "" of []) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0325() {
		'''
			for (const of of []) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0326() {
		'''
			for (const of = "" of []) process(x);
		'''.assertNoException
	}

	@Test
	def void test_0327() {
		'''
			for(var v = new X() of list) {};
		'''.assertNoException
	}

	@Test
	def void test_0328() {
		'''
			"use strict"; for(var v = new X() of list) {}
		'''.assertNoException
	}

	@Test
	def void test_0329() {
		'''
			for(var [a2,b2,c2] of arr2) {}
		'''.assertNoException
	}

	@Test
	def void test_0330() {
		'''
			`
			 ${}
			 ${}
			 `
		'''.assertNoException
	}

	@Test
	def void test_0331() {
		'''
			`a${{}}b`
		'''.assertNoException
	}

	@Test
	def void test_0332() {
		'''
			`$$ $$`
		'''.assertNoException
	}

	@Test
	def void test_0333() {
		'''
			`$`
		'''.assertNoException
	}

	@Test
	def void test_0334() {
		'''
			`$${}`
		'''.assertNoException
	}

	@Test
	def void test_0335() {
		'''
			`\r`
		'''.assertNoException
	}

	@Test
	def void test_0336() {
		'''
			`$
		'''.assertNoException
	}

	@Test
	def void test_0337() {
		'''
			`
		'''.assertNoException
	}

	@Test
	def void test_0338() {
		'''
			`${
			             }
		'''.assertNoException
	}

	@Test
	def void test_0339() {
		'''
			`${}
		'''.assertNoException
	}

	@Test
	def void test_0340() {
		'''
			`\
		'''.assertNoException
	}

	@Test
	def void test_0341() {
		'''
			`\u123`
		'''.assertNoException
	}

	@Test
	def void test_0342() {
		'''
			`${}`
		'''.assertNoException
	}

	@Test
	def void test_0343() {
		'''
			`a${}b`
		'''.assertNoException
	}

	@Test
	def void test_0344() {
		'''
			`no
			 Subst`
		'''.assertNoException
	}

	@Test
	def void test_0345() {
		'''
			` a ${} b ${} c `
		'''.assertNoException
	}

	@Test
	def void test_0346() {
		'''
			`\\`
		'''.assertNoException
	}

	@Test
	def void test_0347() {
		'''
			`\'`
		'''.assertNoException
	}

	@Test
	def void test_0348() {
		'''
			`noSubst`
		'''.assertNoException
	}

	@Test
	def void test_0349() {
		'''
			`${`${`a`}`}`
		'''.assertNoException
	}

	@Test
	def void test_0350() {
		'''
			`${true}`
		'''.assertNoException
	}

	@Test
	def void test_0351() {
		'''
			tag `noSubst`
		'''.assertNoException
	}

	@Test
	def void test_0352() {
		'''
			`\
			`
		'''.assertNoException
	}

	@Test
	def void test_0353() {
		'''
			`${}\
			`
		'''.assertNoException
	}

	@Test
	def void test_0354() {
		'''
			class X { x: X; }
			@This(X) function f() {
				this.x // X.x
			}
		'''.assertNoException
	}

	@Test
	def void test_0355() {
		'''
			class X { x: X; }
			var x: X
			function f() {
				var nested = @This(X) function() {
					this.x // member X.x
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0356() {
		'''
			class Y { y: Y; }
			class X {
				@This(Y)
				m(): any {
					this.y
					return null;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0357() {
		'''
			function f() {
				var nested = @This(~Object with{a: any;}) function() {
					this.a // member record.a
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0358() {
		'''
			1
		'''.assertNoException
	}

	@Test
	def void test_0359() {
		'''
			1/*
			*/2
		'''.assertNoException
	}

	@Test
	def void test_0360() {
		'''
			{ 1
			2 } 3
		'''.assertNoException
	}

	@Test
	def void test_0361() {
		'''
			{ 1
			2 }
		'''.assertNoException
	}

	@Test
	def void test_0362() {
		'''
			1
			+2
		'''.assertNoException
	}

	@Test
	def void test_0363() {
		'''
			1;
			-2
		'''.assertNoException
	}

	@Test
	def void test_0364() {
		'''
			1/*
			*/2
		'''.assertNoException
	}

	@Test
	def void test_0365() {
		'''
			1+
		'''.assertNoException
	}

	@Test
	def void test_0366() {
		'''
			1
			+
		'''.assertNoException
	}

	@Test
	def void test_0367() {
		'''
			1+2
		'''.assertNoException
	}

	@Test
	def void test_0368() {
		'''
			1/*
			*/+2
		'''.assertNoException
	}

	@Test
	def void test_0369() {
		'''
			1

			2
		'''.assertNoException
	}

	@Test
	def void test_0370() {
		'''
			/* some copyright header */
			1+2
		'''.assertNoException
	}

	@Test
	def void test_0371() {
		'''
			1
		'''.assertNoException
	}

	@Test
	def void test_0372() {
		'''
			a.
		'''.assertNoException
	}

	@Test
	def void test_0373() {
		'''
			return
		'''.assertNoException
	}

	@Test
	def void test_0374() {
		'''
			if (true) "string"
		'''.assertNoException
	}

	@Test
	def void test_0375() {
		'''
			f(): .. {}
		'''.assertNoException
	}

	@Test
	def void test_0376() {
		'''
			class target {
				f(): void {
					this;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0377() {
		'''
			var target = class {
				x = this;
			}
		'''.assertNoException
	}

	@Test
	def void test_0378() {
		'''
			var target = class {
				get x() {
					this;
					return null;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0379() {
		'''
			var notarget = {
				s: this
			}
		'''.assertNoException
	}

	@Test
	def void test_0380() {
		'''
			interface target {
				f(): void {
					this;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0381() {
		'''
			var target = class {
				f(): void {
					this;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0382() {
		'''
			var target = class {
				set x(y) {
					this;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0383() {
		'''
			var target = {
				s: "hello",
				f: function() {
					this;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0384() {
		'''
			var target = {
				s: "hello",
				get x() {
					this;
					return null;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0385() {
		'''
			var target = {
				s: "hello",
				set x(y) {
					this;
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0386() {
		'''
			var notarget = function() {
				this;
			}
		'''.assertNoException
	}

	@Test
	def void test_0387() {
		'''
			var notarget = {
				s: "hello",
				f: function() {
					var x = function() {
						this;
					}
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0388() {
		'''
			function notarget(){
				this;
			}
		'''.assertNoException
	}

	@Test
	def void test_0389() {
		'''
			@Internal public class C {
			}
		'''.assertNoException
	}

	@Test
	def void test_0390() {
		'''
			import { C as C } from 'c'
			import { C as B } from 'c'
			import { C as A } from 'c'

			@Internal public class D {
				m(b: B): A {
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0391() {
		'''
			@Internal public class C<A> {
				<B> m(b: B): A {
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0392() {
		'''
			export project enum StorageType {
				FILESYSTEM, DATABASE, CLOUD
			}
			export public interface Element {
			}
			export public class Storage<E extends Element> {
				private type: StorageType;
			}
		'''.assertNoException
	}

	@Test
	def void test_0393() {
		'''
			public class C {
				public constructor() {}
				public m() {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0394() {
		'''
			enum Color {
				RED, GREEN, BLUE
			}

			var c: Color = Color.RED;
		'''.assertNoException
	}

	@Test
	def void test_0395() {
		'''
			enum E{ LITERAL } // cannot be empty
		'''.assertNoException
	}

	@Test
	def void test_0396() {
		'''
			enum E{  } // cannot be empty
		'''.assertNoException
	}

	@Test
	def void test_0397() {
		'''
			import A from "p/A"
			import {C,D,E} from "p/E"
			import * as F from "p/F"
			import {A as G} from "p/G"
			import {A as H, B as I} from "p/H"
		'''.assertNoException
	}

	@Test
	def void test_0398() {
		'''
			import A as B from "p/A"
		'''.assertNoException
	}

	@Test
	def void test_0399() {
		'''
			export * from 'p/A'/*
			*/export * from 'p/A'; export * from 'p/A'
			export * from "p/A"
		'''.assertNoException
	}

	@Test
	def void test_0400() {
		'''
			export * from 'p/A'
			export {} from 'p/A'
			export { exported } from 'p/A'
			export { exported as alias, }
			export var a, b
			export class A {}
			export let a = 7
			export const a = 7
			export function * f() {}
			export function f() {}
			export default function * f() {}
			export default function f() {}
			export default function * () {}
			export default function () {}
			export default class {}
			export default x = 7
		'''.assertNoException
	}

	@Test
	def void test_0401() {
		'''
			export @Export public class A{}
			export interface B{}
			export function foo() {}
			export var a;
			export const c="Hi";
		'''.assertNoException
	}

	@Test
	def void test_0402() {
		'''
			import 'p/A' /*
			*/ import 'p/A';import 'p/A'
			import "p/A"
		'''.assertNoException
	}

	@Test
	def void test_0403() {
		'''
			import ImportedBinding from 'p/A'
			import * as ImportedBinding from 'p/A'
		'''.assertNoException
	}

	@Test
	def void test_0404() {
		'''
			import {} from 'p/A'
			import { ImportsList } from 'p/A'
			import { ImportsList as X } from 'p/A'
			import { ImportsList, } from 'p/A'
			import { ImportsList as X, } from 'p/A'
			import { ImportsList, Second } from 'p/A'
			import { ImportsList as X, Second } from 'p/A'
			import { ImportsList, Second, } from 'p/A'
			import { ImportsList as X, Second as Y, } from 'p/A'
		'''.assertNoException
	}

	@Test
	def void test_0405() {
		'''
			import ImportedBinding, * as NameSpaceImport from 'p/A'
			import ImportedBinding, {} from 'p/A'
		'''.assertNoException
	}

	@Test
	def void test_0406() {
		'''
		    42
		'''.assertNoException
	}

	@Test
	def void test_0407() {
		'''
			null
		'''.assertNoException
	}

	@Test
	def void test_0408() {
		'''
			this
		'''.assertNoException
	}

	@Test
	def void test_0409() {
		'''
			(1 + 2 ) * 3
		'''.assertNoException
	}

	@Test
	def void test_0410() {
		'''
			(1) + (2 ) + 3
		'''.assertNoException
	}

	@Test
	def void test_0411() {
		'''
			4 + 5 << (6)
		'''.assertNoException
	}

	@Test
	def void test_0412() {
		'''
			class X {
			/* 2 */	a: any;
			/* 3 */	b: any;
			/* 4 */	a: any;
			/* 5 */	d: any;
			/* 6 */	c(x: number): string { return "" }
			/* 7 */	c(v: string): string { return ""}
			/* 8 */ get b(): string { return "" }
			/* 9 */	set d(u: string) {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0413() {
		'''
			class X {
			/* 2 */	a: any;
			/* 3 */	a: any;
			}
		'''.assertNoException
	}

	@Test
	def void test_0414() {
		'''
			var v = v
		'''.assertNoException
	}

	@Test
	def void test_0415() {
		'''
			class A {
				private ___a: string;

				getA(): string {  return this.___a;  }
			}
			class B extends A {
				private ___b: string;

				@Override
				getA(): string {  return this.___b;  }
			}
		'''.assertNoException
	}

	@Test
	def void test_0416() {
		'''
			class A {
				private ___a: string;

				get a(): string {  return this.___a;  }
				set a(aParam: string) {   this.___a = aParam;   }
			}
		'''.assertNoException
	}

	@Test
	def void test_0417() {
		'''
			@Deprectated public class ~C {}
			@Deprectated public class D {}
		'''.assertNoException
	}

	@Test
	def void test_0418() {
		'''
			public class C {
				p: C;
			}
		'''.assertNoException
	}

	@Test
	def void test_0419() {
		'''
			public class ~C {}
			public class D {}
		'''.assertNoException
	}

	@Test
	def void test_0420() {
		'''
			try { } catch (arguments) { }
		'''.assertNoException
	}

	@Test
	def void test_0421() {
		'''
			try { } finally { cleanup(stuff) }
		'''.assertNoException
	}

	@Test
	def void test_0422() {
		'''
			try { } catch (e) { say(e) }
		'''.assertNoException
	}

	@Test
	def void test_0423() {
		'''
			try { } catch (e) { }
		'''.assertNoException
	}

	@Test
	def void test_0424() {
		'''
			try { } catch (e: any) { }
		'''.assertNoException
	}

	@Test
	def void test_0425() {
		'''
			"use strict"; try {} finally { function x() {} }
		'''.assertNoException
	}

	@Test
	def void test_0426() {
		'''
			try { doThat(); } catch (e) { say(e) } finally { cleanup(stuff) }
		'''.assertNoException
	}

	@Test
	def void test_0427() {
		'''
			try { doThat(); } catch (e) { say(e) }
		'''.assertNoException
	}

	@Test
	def void test_0428() {
		'''
			try {} finally { function x() {} }
		'''.assertNoException
	}

	@Test
	def void test_0429() {
		'''
			"use strict"; try {} catch (e) { function x() {} }
		'''.assertNoException
	}

	@Test
	def void test_0430() {
		'''
			try { } catch (e: any) { }
		'''.assertNoException
	}

	@Test
	def void test_0431() {
		'''
			try { } catch (eval) { }
		'''.assertNoException
	}

	@Test
	def void test_0432() {
		'''
			/\/\.\//
		'''.assertNoException
	}

	@Test
	def void test_0433() {
		'''
			/[^\/]+\/\.\.\//
		'''.assertNoException
	}

	@Test
	def void test_0434() {
		'''
			/()??/
		'''.assertNoException
	}

	@Test
	def void test_0435() {
		'''
			/[-]/
		'''.assertNoException
	}

	@Test
	def void test_0436() {
		'''
			/[^-]/
		'''.assertNoException
	}

	@Test
	def void test_0437() {
		'''
			/$/
		'''.assertNoException
	}

	@Test
	def void test_0438() {
		'''
			/=/
		'''.assertNoException
	}

	@Test
	def void test_0439() {
		'''
			/=/g
		'''.assertNoException
	}

	@Test
	def void test_0440() {
		'''
			/[]/
		'''.assertNoException
	}

	@Test
	def void test_0441() {
		'''
			/[^]/
		'''.assertNoException
	}

	@Test
	def void test_0442() {
		'''
			/[$]/
		'''.assertNoException
	}

	@Test
	def void test_0443() {
		'''
			/,:=![,:=!]/
		'''.assertNoException
	}

	@Test
	def void test_0444() {
		'''
			/^{{[#^>/]?\s*[\w.][^}]*}}/
		'''.assertNoException
	}

	@Test
	def void test_0445() {
		'''
			/a|b/
		'''.assertNoException
	}

	@Test
	def void test_0446() {
		'''
			/./
		'''.assertNoException
	}

	@Test
	def void test_0447() {
		'''
			/\b/
		'''.assertNoException
	}

	@Test
	def void test_0448() {
		'''
			/\B/
		'''.assertNoException
	}

	@Test
	def void test_0449() {
		'''
			/\\u000A/
		'''.assertNoException
	}

	@Test
	def void test_0450() {
		'''
			/\\u000D/
		'''.assertNoException
	}

	@Test
	def void test_0451() {
		'''
			/\\u2028/
		'''.assertNoException
	}

	@Test
	def void test_0452() {
		'''
			/\\u2029/
		'''.assertNoException
	}

	@Test
	def void test_0453() {
		'''
			/a|/
		'''.assertNoException
	}

	@Test
	def void test_0454() {
		'''
			/|a/
		'''.assertNoException
	}

	@Test
	def void test_0455() {
		'''
			/a||b/
		'''.assertNoException
	}

	@Test
	def void test_0456() {
		'''
			/\./
		'''.assertNoException
	}

	@Test
	def void test_0457() {
		'''
			/123{123}/
		'''.assertNoException
	}

	@Test
	def void test_0458() {
		'''
			/123{123,}/
		'''.assertNoException
	}

	@Test
	def void test_0459() {
		'''
			/123{123,123}/
		'''.assertNoException
	}

	@Test
	def void test_0460() {
		'''
			/123{123}?/
		'''.assertNoException
	}

	@Test
	def void test_0461() {
		'''
			/123+/
		'''.assertNoException
	}

	@Test
	def void test_0462() {
		'''
			/123+?/
		'''.assertNoException
	}

	@Test
	def void test_0463() {
		'''
			/123*?/
		'''.assertNoException
	}

	@Test
	def void test_0464() {
		'''
			/123*/
		'''.assertNoException
	}

	@Test
	def void test_0465() {
		'''
			/123??/
		'''.assertNoException
	}

	@Test
	def void test_0466() {
		'''
			/123?/
		'''.assertNoException
	}

	@Test
	def void test_0467() {
		'''
			/[\b]/
		'''.assertNoException
	}

	@Test
	def void test_0468() {
		'''
			/123/
		'''.assertNoException
	}

	@Test
	def void test_0469() {
		'''
			/Hi You/gsm
		'''.assertNoException
	}

	@Test
	def void test_0470() {
		'''
			/(?:\r\n?|\n).*/g
		'''.assertNoException
	}

	@Test
	def void test_0471() {
		'''
			/(?:\r\n?|\n)/g
		'''.assertNoException
	}

	@Test
	def void test_0472() {
		'''
			/\n/g
		'''.assertNoException
	}

	@Test
	def void test_0473() {
		'''
			/\r?\n?/
		'''.assertNoException
	}

	@Test
	def void test_0474() {
		'''
			/\\"/
		'''.assertNoException
	}

	@Test
	def void test_0475() {
		'''
			/\\'/
		'''.assertNoException
	}

	@Test
	def void test_0476() {
		'''
			/^(?:\\\\(?=(\{\{)))/
		'''.assertNoException
	}

	@Test
	def void test_0477() {
		'''
			/^(?:[^\x00]*?(?=(\{\{)))/
		'''.assertNoException
	}

	@Test
	def void test_0478() {
		'''
			/^(?:[^\x00]+)/
		'''.assertNoException
	}

	@Test
	def void test_0479() {
		'''
			/^(?:[^\x00]{2,}?(?=(\{\{|$)))/
		'''.assertNoException
	}

	@Test
	def void test_0480() {
		'''
			/^(?:[\s\S]*?--\}\})/
		'''.assertNoException
	}

	@Test
	def void test_0481() {
		'''
			/^(?:\{\{>)/
		'''.assertNoException
	}

	@Test
	def void test_0482() {
		'''
			/^(?:\{\{#)/
		'''.assertNoException
	}

	@Test
	def void test_0483() {
		'''
			/^(?:\{\{\/)/
		'''.assertNoException
	}

	@Test
	def void test_0484() {
		'''
			/^(?:\{\{\^)/
		'''.assertNoException
	}

	@Test
	def void test_0485() {
		'''
			/^(?:\{\{\s*else\b)/
		'''.assertNoException
	}

	@Test
	def void test_0486() {
		'''
			/^(?:\{\{\{)/
		'''.assertNoException
	}

	@Test
	def void test_0487() {
		'''
			/^(?:\{\{&)/
		'''.assertNoException
	}

	@Test
	def void test_0488() {
		'''
			/^(?:\{\{!--)/
		'''.assertNoException
	}

	@Test
	def void test_0489() {
		'''
			/^(?:\{\{![\s\S]*?\}\})/
		'''.assertNoException
	}

	@Test
	def void test_0490() {
		'''
			/^(?:\{\{)/
		'''.assertNoException
	}

	@Test
	def void test_0491() {
		'''
			/^(?:=)/
		'''.assertNoException
	}

	@Test
	def void test_0492() {
		'''
			/^(?:\.(?=[}\/ ]))/
		'''.assertNoException
	}

	@Test
	def void test_0493() {
		'''
			/^(?:\.\.)/
		'''.assertNoException
	}

	@Test
	def void test_0494() {
		'''
			/^(?:[\/.])/
		'''.assertNoException
	}

	@Test
	def void test_0495() {
		'''
			/^(?:\s+)/
		'''.assertNoException
	}

	@Test
	def void test_0496() {
		'''
			/^(?:\}\}\})/
		'''.assertNoException
	}

	@Test
	def void test_0497() {
		'''
			/^(?:\}\})/
		'''.assertNoException
	}

	@Test
	def void test_0498() {
		'''
			/^(?:"(\\["]|[^"])*")/
		'''.assertNoException
	}

	@Test
	def void test_0499() {
		'''
			/^(?:'(\\[']|[^'])*')/
		'''.assertNoException
	}

	@Test
	def void test_0500() {
		'''
			/^(?:@)/
		'''.assertNoException
	}

	@Test
	def void test_0501() {
		'''
			/^(?:true(?=[}\s]))/
		'''.assertNoException
	}

	@Test
	def void test_0502() {
		'''
			/^(?:false(?=[}\s]))/
		'''.assertNoException
	}

	@Test
	def void test_0503() {
		'''
			/^(?:-?[0-9]+(?=[}\s]))/
		'''.assertNoException
	}

	@Test
	def void test_0504() {
		'''
			/^(?:[^\s!"#%-,\.\/;->@\[-\^`\{-~]+(?=[=}\s\/.]))/
		'''.assertNoException
	}

	@Test
	def void test_0505() {
		'''
			/^(?:\[[^\]]*\])/
		'''.assertNoException
	}

	@Test
	def void test_0506() {
		'''
			/^(?:.)/
		'''.assertNoException
	}

	@Test
	def void test_0507() {
		'''
			/^(?:$)/
		'''.assertNoException
	}

	@Test
	def void test_0508() {
		'''
			/[a-z]/
		'''.assertNoException
	}

	@Test
	def void test_0509() {
		'''
			/[aa-z]/
		'''.assertNoException
	}

	@Test
	def void test_0510() {
		'''
			/[a-za]/
		'''.assertNoException
	}

	@Test
	def void test_0511() {
		'''
			/[a-zA-Z1-z-]/
		'''.assertNoException
	}

	@Test
	def void test_0512() {
		'''
			/[-a-zA-Z1-z-]/
		'''.assertNoException
	}

	@Test
	def void test_0513() {
		'''
			/Hi You/
		'''.assertNoException
	}

	@Test
	def void test_0514() {
		'''
			/^/
		'''.assertNoException
	}

	@Test
	def void test_0515() {
		'''
			/(?=Hi You)/
		'''.assertNoException
	}

	@Test
	def void test_0516() {
		'''
			/(?!Hi You)/
		'''.assertNoException
	}

	@Test
	def void test_0517() {
		'''
			function lenFunctionDeclaration(s: string) : number {
				return s.length;
			}

			function wrap(){
				@This(any)
				function lenAnnotatedFunctionDeclarationYYY(s: string) : number {
					return s.length;
				}

				function lenAnnotatedFunctionDeclarationXXX(s: string) : number {
					return s.length;
				}
			}

			@This(any)
			function lenAnnotatedScriptElement(s: string) : number {
				return s.length;
			}

			export function lenExportedFunctionDeclaration(s: string) : number {
				return s.length;
			}

			@This(any)
			export function lenAnnotatedExportableElement(s: string) : number {
				return s.length;
			}
		'''.assertNoException
	}

	@Test
	def void test_0518() {
		'''
			function f(){
				@This(any)
				function g() {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0519() {
		'''
			/* var */ x*/
		'''.assertNoException
	}

	@Test
	def void test_0520() {
		'''
			YUI.add("button-plugin",function(e,t){function n(){n.superclass.constructor.apply(this,arguments)}e.extend(n,e.ButtonCore,{_afterNodeGet:function(t){var n=this.constructor.ATTRS,r=n[t]&&n[t].getter&&this[n[t].getter];if(r)return new e.Do.AlterReturn("get "+t,r.call(this))},_afterNodeSet:function(e,t){var n=this.constructor.ATTRS,r=n[e]&&n[e].setter&&this[n[e].setter];r&&r.call(this,t)},_initNode:function(t){var n=t.host;this._host=n,e.Do.after(this._afterNodeGet,n,"get",this),e.Do.after(this._afterNodeSet,n,"set",this)},destroy:function(){}},{ATTRS:e.merge(e.ButtonCore.ATTRS),NAME:"buttonPlugin",NS:"button"}),n.createNode=function(t,n){var r;return t&&!n&&!t.nodeType&&!t.getDOMNode&&typeof t!="string"&&(n=t,t=n.srcNode),n=n||{},r=n.template||e.Plugin.Button.prototype.TEMPLATE,t=t||n.srcNode||e.DOM.create(r),e.one(t).plug(e.Plugin.Button,n)},e.namespace("Plugin").Button=n},"3.12.0",{requires:["button-core","cssbutton","node-pluginhost"]});
		'''.assertNoException
	}

	@Test
	def void test_0521() {
		'''
			replace(/[^\/]+\/\.\.\//)
		'''.assertNoException
	}

	@Test
	def void test_0522() {
		'''
			var e=/^/g;
		'''.assertNoException
	}

	@Test
	def void test_0523() {
		'''
			if(true){}/[^\s]/.test(a)
		'''.assertNoException
	}

	@Test
	def void test_0524() {
		'''
			{if(true){}}/[^\s]/.test(a)
		'''.assertNoException
	}

	@Test
	def void test_0525() {
		'''
			{if(true){}/[^\s]/.test(a)}
		'''.assertNoException
	}

	@Test
	def void test_0526() {
		'''
			if(true){({})/s}
		'''.assertNoException
	}

	@Test
	def void test_0527() {
		'''
			if(true){({}/s)}
		'''.assertNoException
	}

	@Test
	def void test_0528() {
		'''
			if ({} / 1 !== 1) {}
		'''.assertNoException
	}

	@Test
	def void test_0529() {
		'''
			isNaN(function(){return 1} / {}) !== true
		'''.assertNoException
	}

	@Test
	def void test_0530() {
		'''
			if ((x = 1) / x !== 1) {}
		'''.assertNoException
	}

	@Test
	def void test_0531() {
		'''
			if (isNaN({} / function(){return 1}) !== true) {}
		'''.assertNoException
	}

	@Test
	def void test_0532() {
		'''
			if(true){if(true)b}/[^\s]/.test(a)
		'''.assertNoException
	}

	@Test
	def void test_0533() {
		'''
			if (isNaN(function(){return 1} / {}) !== true) {}
		'''.assertNoException
	}

	@Test
	def void test_0534() {
		'''
			if (true)/a/.test(a)
		'''.assertNoException
	}

	@Test
	def void test_0535() {
		'''
			YUI.add("app-transitions",function(e,t){function n(){}n.ATTRS={transitions:{setter:"_setTransitions",value:!1}},n.FX={fade:{viewIn:"app:fadeIn",viewOut:"app:fadeOut"},slideLeft:{viewIn:"app:slideLeft",viewOut:"app:slideLeft"},slideRight:{viewIn:"app:slideRight",viewOut:"app:slideRight"}},n.prototype={transitions:{navigate:"fade",toChild:"slideLeft",toParent:"slideRight"},_setTransitions:function(t){var n=this.transitions;return t&&t===!0?e.merge(n):t}},e.App.Transitions=n,e.Base.mix(e.App,[n]),e.mix(e.App.CLASS_NAMES,{transitioning:e.ClassNameManager.getClassName("app","transitioning")})},"3.12.0",{requires:["app-base"]});
		'''.assertNoException
	}

	@Test
	def void test_0536() {
		'''
			e=+e,e!==e?e=0:e!==0&&e!==1/0&&e!==-1/0&&(e=(e>0||-1)*Math.floor(Math.abs(e))),e
		'''.assertNoException
	}

	@Test
	def void test_0537() {
		'''
			"use strict";
			'\0'
		'''.assertNoException
	}

	@Test
	def void test_0538() {
		'''
			x0() / y0()
		'''.assertNoException
	}

	@Test
	def void test_0539() {
		'''
			var reflect = value => value;

			// effectively equivalent to:

			var reflect = function(value) {
			    return value;
			};
		'''.assertNoException
	}

	@Test
	def void test_0540() {
		'''
			var sum = (num1, num2) => num1 + num2;

			// effectively equivalent to:

			var sum = function(num1, num2) {
			    return num1 + num2;
			};
		'''.assertNoException
	}

	@Test
	def void test_0541() {
		'''
			var sum = () => 1 + 2;

			// effectively equivalent to:

			var sum = function() {
			    return 1 + 2;
			};
		'''.assertNoException
	}

	@Test
	def void test_0542() {
		'''
			var sum = (num1, num2) => { return num1 + num2; }

			// effectively equivalent to:

			var sum = function(num1, num2) {
			    return num1 + num2;
			};
		'''.assertNoException
	}

	@Test
	def void test_0543() {
		'''
			var getTempItem = id => ({ id: id, name: "Temp" });

			// effectively equivalent to:

			var getTempItem = function(id) {

			    return {
			        id: id,
			        name: "Temp"
			    };
			};
		'''.assertNoException
	}

	@Test
	def void test_0544() {
		'''
			var PageHandler = {

			    id: "123456",

			    init: function() {
			        document.addEventListener("click", function(event) {
			            this.doSomething(event.type);     // error
			        }, false);
			    },

			    doSomething: function(type) {
			        console.log("Handling " + type  + " for " + this.id);
			    }
			};
		'''.assertNoException
	}

	@Test
	def void test_0545() {
		'''
			var PageHandler = {

			    id: "123456",

			    init: function() {
			        document.addEventListener("click", (function(event) {
			            this.doSomething(event.type);     // error
			        }).bind(this), false);
			    },

			    doSomething: function(type) {
			        console.log("Handling " + type  + " for " + this.id);
			    }
			};
		'''.assertNoException
	}

	@Test
	def void test_0546() {
		'''
			var PageHandler = {

			    id: "123456",

			    init: function() {
			        document.addEventListener("click",
			                event => this.doSomething(event.type), false);
			    },

			    doSomething: function(type) {
			        console.log("Handling " + type  + " for " + this.id);
			    }
			};
		'''.assertNoException
	}

	@Test
	def void test_0547() {
		'''
			var result = values.sort(function(a, b) {
			    return a - b;
			});
			var result = values.sort((a, b) => a - b);
		'''.assertNoException
	}

	@Test
	def void test_0548() {
		'''
			var identity = (identityParam) => identityParam;
			assert.equal(1234, identity(1234));
		'''.assertNoException
	}

	@Test
	def void test_0549() {
		'''
			((a, b)=>a + b)
		'''.assertNoException
	}

	@Test
	def void test_0550() {
		'''
			(... x: any)=>x
		'''.assertNoException
	}

	@Test
	def void test_0551() {
		'''
			function CommentCtrl($scope, articles) {
			    $scope.comments = [];

			    articles.getList()
			        .then((articles) => Promise.all(articles.map((article) => article.comments.getList())))
			        .then((commentLists) => {
			            $scope.comments = commentLists.reduce((a, b) => a.concat(b));
			        });
			}
		'''.assertNoException
	}

	@Test
	def void test_0552() {
		'''
			function CommentCtrl($scope, articles) {
			    $scope.comments = [];

			    articles.getList()
			        .then(function (articles) {
			            return Promise.all(articles.map(function (article) {
			                return article.comments.getList();
			            }));
			        })
			        .then(function (commentLists) {
			            $scope.comments = commentLists.reduce(function (a, b) {
			                return a.concat(b);
			            });
			        });
			}
		'''.assertNoException
	}

	@Test
	def void test_0553() {
		'''
			({a = 0});
		'''.assertNoException
	}

	@Test
	def void test_0554() {
		'''
			((a, b)=>a + b)()
		'''.assertNoException
	}

	@Test
	def void test_0555() {
		'''
			var f = (x, ...xs);
		'''.assertNoException
	}

	@Test
	def void test_0556() {
		'''
			var identity = (x) => {x}.bind({});
		'''.assertNoException
	}

	@Test
	def void test_0557() {
		'''
			function f() {
			  ({a = (0, {a = 0})} = {})
			}
		'''.assertNoException
	}

	@Test
	def void test_0558() {
		'''
			x
			=>x
		'''.assertNoException
	}

	@Test
	def void test_0559() {
		'''
			const obj = {
			  method: function () {
			    return () => this;
			  }
			};
			assert.equal(obj.method()(), obj);

			var fake = {steal: obj.method()};
			assert.equal(fake.steal(), obj);

			var real = {borrow: obj.method};
			assert.equal(real.borrow()(), real);
		'''.assertNoException
	}

	@Test
	def void test_0560() {
		'''
			(a, b)=>a + b
		'''.assertNoException
	}

	@Test
	def void test_0561() {
		'''
			x=>x
		'''.assertNoException
	}

	@Test
	def void test_0562() {
		'''
			()=>{}
		'''.assertNoException
	}

	@Test
	def void test_0563() {
		'''
			(x) + (y) => y;
		'''.assertNoException
	}

	@Test
	def void test_0564() {
		'''
			(x) + y => y;
		'''.assertNoException
	}

	@Test
	def void test_0565() {
		'''
			function f() {
			  (1 ? ({a=0}) => {} : 1);
			}
		'''.assertNoException
	}

	@Test
	def void test_0566() {
		'''
			x /*
			*/ =>x
		'''.assertNoException
	}

	@Test
	def void test_0567() {
		'''
			(a: any, b: any)=>a + b
		'''.assertNoException
	}

	@Test
	def void test_0568() {
		'''
			x /* comment */ =>x
		'''.assertNoException
	}

	@Test
	def void test_0569() {
		'''
			(Unknown... x)=>x
		'''.assertNoException
	}

	@Test
	def void test_0570() {
		'''
			( a: any, b: any )=>a + b
		'''.assertNoException
	}

	@Test
	def void test_0571() {
		'''
			x => x => x
		'''.assertNoException
	}

	@Test
	def void test_0572() {
		'''
			var f = (a, b + 5) => a + b;
		'''.assertNoException
	}

	@Test
	def void test_0573() {
		'''
			var global = this;
			var self = {};

			function outer() {
			  var f = () => {
			    assert.equal(this, self);

			    var g = () => {
			      assert.equal(this, self);
			    };
			    g();

			    var h = function() {
			      assert.equal(this, global);
			    };
			    h();
			  };

			  f();
			}

			outer.call(self);
		'''.assertNoException
	}

	@Test
	def void test_0574() {
		'''
			function f() {
			  var args = (() => arguments)();
			  assert.equal(args, arguments);
			}

			f();
		'''.assertNoException
	}

	@Test
	def void test_0575() {
		'''
			{
			  var f = (...xs, x) => xs;
			}
		'''.assertNoException
	}

	@Test
	def void test_0576() {
		'''
			function f() {
				var z = (x)=>{
					()=>
					return z(x)
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0577() {
		'''
			function f() {
				var z = (x)=>{
					()=>
					return z(x);
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0578() {
		'''
			function f() {
				var z = (x)=>{
					()=>
					return z(x);
				};
			};
		'''.assertNoException
	}

	@Test
	def void test_0579() {
		'''
			with (x) foo = bar;
		'''.assertNoException
	}

	@Test
	def void test_0580() {
		'''
			with (x) foo = bar
		'''.assertNoException
	}

	@Test
	def void test_0581() {
		'''
			with (x) { foo = bar }
		'''.assertNoException
	}

	@Test
	def void test_0582() {
		'''
			export var s = class Supplier {
				Supplier foo() {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0583() {
		'''
			import s from "org.eclipse.n4js/tests/scoping/Supplier";
			s.foo()
		'''.assertNoException
	}

	@Test
	def void test_0584() {
		'''
			var a=1;

			function foo() {
				(function(s){})('myArgument')
				var a=3;
				var b1 = a; // binds to local a (= 3)
				foo; // binds to function as identifier ref
				foo(); // binds to function as identifier ref contained in a call expression
			}
			var b2 = c; // binds to c declaration, even c is declared after b2 and c is contained in block doesn't lower visibility
			{
				var c = a; // = 1 // binds to global a (= 1)
			}
			var d = d; // binds to itself
			foo(); // binds to function as identifier ref contained in a call expression
			var e = foo() // binds to function as identifier ref contained in a call expression
			var f = foo // binds to function as identifier ref
		'''.assertNoException
	}

	@Test
	def void test_0585() {
		'''
			var a=1;
			var b=a;
		'''.assertNoException
	}

	@Test
	def void test_0586() {
		'''
			export class A {
				public methodOfA(): any
			}
			var a: A;
			a.methodOfA()
		'''.assertNoException
	}

	@Test
	def void test_0587() {
		'''
			export class Supplier {
				Supplier foo();
			}
		'''.assertNoException
	}

	@Test
	def void test_0588() {
		'''
			import Supplier from "org.eclipse.n4js/tests/scoping/Supplier";
			var Supplier a;
			a;
		'''.assertNoException
	}

	@Test
	def void test_0589() {
		'''
			export class Supplier {
				Supplier foo() {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0590() {
		'''
			import Supplier from "org.eclipse.n4js/tests/scoping/Supplier";
			var Supplier a;
		'''.assertNoException
	}

	@Test
	def void test_0591() {
		'''
			export class Supplier {
				Supplier foo()  {}
			}
		'''.assertNoException
	}

	@Test
	def void test_0592() {
		'''
			import Supplier from "org.eclipse.n4js/tests/scoping/Supplier";
			var Supplier a;
			a.foo();
		'''.assertNoException
	}

	@Test
	def void test_0593() {
		'''
			import { Supplier as MySupplier } from "org.eclipse.n4js/tests/scoping/Supplier";
			var MySupplier a;
		'''.assertNoException
	}

	@Test
	def void test_0594() {
		'''
			export class A {
				foo(): any {
				}
			}
		'''.assertNoException
	}

	@Test
	def void test_0595() {
		'''
			class A {
				foo(): any {}
			}
			var a: A;
			a.foo();
		'''.assertNoException
	}

	@Test
	def void test_0596() {
		'''
			class A {}
			var a: A;
		'''.assertNoException
	}

	@Test
	def void test_0597() {
		'''
			var x = 1;
			++x;
		'''.assertNoException
	}

	@Test
	def void test_0598() {
		'''
			var x = 1
			++x
		'''.assertNoException
	}

	@Test
	def void test_0599() {
		'''
			var x = 1
			--x
		'''.assertNoException
	}



}
