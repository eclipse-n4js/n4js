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
public class GeneratedSmokeTestCases2 {

	@Inject
	ParseHelper<Script> parseHelper;

	// Dummy method - needs to be called to record the expression and schedule it for smoke testing
	protected void assertNoException(CharSequence expression) throws Exception {
		parseHelper.parse(expression);
	}

	@Test
	public void test_0300() throws Exception {
		assertNoException("""
					do keep(); while (true);
				""");
	}

	@Test
	public void test_0301() throws Exception {
		assertNoException("""
					do { x++; y--; } while (x < 10)
				""");
	}

	@Test
	public void test_0302() throws Exception {
		assertNoException("""
					for(;;);
				""");
	}

	@Test
	public void test_0303() throws Exception {
		assertNoException("""
					for(;;){}
				""");
	}

	@Test
	public void test_0304() throws Exception {
		assertNoException("""
					for(x = 0;;);
				""");
	}

	@Test
	public void test_0305() throws Exception {
		assertNoException("""
					for(var x = 0;;);
				""");
	}

	@Test
	public void test_0306() throws Exception {
		assertNoException("""
					for(let x = 0;;);
				""");
	}

	@Test
	public void test_0307() throws Exception {
		assertNoException("""
					for(var x = 0, y = 1;;);
				""");
	}

	@Test
	public void test_0308() throws Exception {
		assertNoException("""
					for(x = 0; x < 42;);
				""");
	}

	@Test
	public void test_0309() throws Exception {
		assertNoException("""
					for(x = 0; x < 42; x++);
				""");
	}

	@Test
	public void test_0310() throws Exception {
		assertNoException("""
					for(x = 0; x < 42; x++) process(x);
				""");
	}

	@Test
	public void test_0311() throws Exception {
		assertNoException("""
					{ do { } while (false) false }
				""");
	}

	@Test
	public void test_0312() throws Exception {
		assertNoException("""
					{ do { } while (false)false }
				""");
	}

	@Test
	public void test_0313() throws Exception {
		assertNoException("""
					for(x of list) process(x);
				""");
	}

	@Test
	public void test_0314() throws Exception {
		assertNoException("""
					for(var x of list) process(x);
				""");
	}

	@Test
	public void test_0315() throws Exception {
		assertNoException("""
					for(var x = 42 of list) process(x);
				""");
	}

	@Test
	public void test_0316() throws Exception {
		assertNoException("""
					for(let x of list) process(x);
				""");
	}

	@Test
	public void test_0317() throws Exception {
		assertNoException("""
					for (var i = function() { return 10 in [] } of list) process(x);
				""");
	}

	@Test
	public void test_0318() throws Exception {
		assertNoException("""
					for (var i = function() {} of list) process(x);
				""");
	}

	@Test
	public void test_0319() throws Exception {
		assertNoException("""
					for (var i = "" of ["a","b"]) process(x);
				""");
	}

	@Test
	public void test_0320() throws Exception {
		assertNoException("""
					for (of of []) process(x);
				""");
	}

	@Test
	public void test_0321() throws Exception {
		assertNoException("""
					for (var of of []) process(x);
				""");
	}

	@Test
	public void test_0322() throws Exception {
		assertNoException("""
					for (var of = "" of []) process(x);
				""");
	}

	@Test
	public void test_0323() throws Exception {
		assertNoException("""
					for (let of of []) process(x);
				""");
	}

	@Test
	public void test_0324() throws Exception {
		assertNoException("""
					for (let of = "" of []) process(x);
				""");
	}

	@Test
	public void test_0325() throws Exception {
		assertNoException("""
					for (const of of []) process(x);
				""");
	}

	@Test
	public void test_0326() throws Exception {
		assertNoException("""
					for (const of = "" of []) process(x);
				""");
	}

	@Test
	public void test_0327() throws Exception {
		assertNoException("""
					for(var v = new X() of list) {};
				""");
	}

	@Test
	public void test_0328() throws Exception {
		assertNoException("""
					"use strict"; for(var v = new X() of list) {}
				""");
	}

	@Test
	public void test_0329() throws Exception {
		assertNoException("""
					for(var [a2,b2,c2] of arr2) {}
				""");
	}

	@Test
	public void test_0330() throws Exception {
		assertNoException("""
					`
					 ${}
					 ${}
					 `
				""");
	}

	@Test
	public void test_0331() throws Exception {
		assertNoException("""
					`a${{}}b`
				""");
	}

	@Test
	public void test_0332() throws Exception {
		assertNoException("""
					`$$ $$`
				""");
	}

	@Test
	public void test_0333() throws Exception {
		assertNoException("""
					`$`
				""");
	}

	@Test
	public void test_0334() throws Exception {
		assertNoException("""
					`$${}`
				""");
	}

	@Test
	public void test_0335() throws Exception {
		assertNoException("""
					`\\r`
				""");
	}

	@Test
	public void test_0336() throws Exception {
		assertNoException("""
					`$
				""");
	}

	@Test
	public void test_0337() throws Exception {
		assertNoException("""
					`
				""");
	}

	@Test
	public void test_0338() throws Exception {
		assertNoException("""
					`${
					             }
				""");
	}

	@Test
	public void test_0339() throws Exception {
		assertNoException("""
					`${}
				""");
	}

	@Test
	public void test_0340() throws Exception {
		assertNoException("""
					`\\
				""");
	}

	@Test
	public void test_0341() throws Exception {
		assertNoException("""
					`\\u123`
				""");
	}

	@Test
	public void test_0342() throws Exception {
		assertNoException("""
					`${}`
				""");
	}

	@Test
	public void test_0343() throws Exception {
		assertNoException("""
					`a${}b`
				""");
	}

	@Test
	public void test_0344() throws Exception {
		assertNoException("""
					`no
					 Subst`
				""");
	}

	@Test
	public void test_0345() throws Exception {
		assertNoException("""
					` a ${} b ${} c `
				""");
	}

	@Test
	public void test_0346() throws Exception {
		assertNoException("""
					`\\\\`
				""");
	}

	@Test
	public void test_0347() throws Exception {
		assertNoException("""
					`\\'`
				""");
	}

	@Test
	public void test_0348() throws Exception {
		assertNoException("""
					`noSubst`
				""");
	}

	@Test
	public void test_0349() throws Exception {
		assertNoException("""
					`${`${`a`}`}`
				""");
	}

	@Test
	public void test_0350() throws Exception {
		assertNoException("""
					`${true}`
				""");
	}

	@Test
	public void test_0351() throws Exception {
		assertNoException("""
					tag `noSubst`
				""");
	}

	@Test
	public void test_0352() throws Exception {
		assertNoException("""
					`\\
					`
				""");
	}

	@Test
	public void test_0353() throws Exception {
		assertNoException("""
					`${}\\
					`
				""");
	}

	@Test
	public void test_0354() throws Exception {
		assertNoException("""
					class X { x: X; }
					@This(X) function f() {
						this.x // X.x
					}
				""");
	}

	@Test
	public void test_0355() throws Exception {
		assertNoException("""
					class X { x: X; }
					var x: X
					function f() {
						var nested = @This(X) function() {
							this.x // member X.x
						}
					}
				""");
	}

	@Test
	public void test_0356() throws Exception {
		assertNoException("""
					class Y { y: Y; }
					class X {
						@This(Y)
						m(): any {
							this.y
							return null;
						}
					}
				""");
	}

	@Test
	public void test_0357() throws Exception {
		assertNoException("""
					function f() {
						var nested = @This(~Object with{a: any;}) function() {
							this.a // member record.a
						}
					}
				""");
	}

	@Test
	public void test_0358() throws Exception {
		assertNoException("""
					1
				""");
	}

	@Test
	public void test_0359() throws Exception {
		assertNoException("""
					1/*
					*/2
				""");
	}

	@Test
	public void test_0360() throws Exception {
		assertNoException("""
					{ 1
					2 } 3
				""");
	}

	@Test
	public void test_0361() throws Exception {
		assertNoException("""
					{ 1
					2 }
				""");
	}

	@Test
	public void test_0362() throws Exception {
		assertNoException("""
					1
					+2
				""");
	}

	@Test
	public void test_0363() throws Exception {
		assertNoException("""
					1;
					-2
				""");
	}

	@Test
	public void test_0364() throws Exception {
		assertNoException("""
					1/*
					*/2
				""");
	}

	@Test
	public void test_0365() throws Exception {
		assertNoException("""
					1+
				""");
	}

	@Test
	public void test_0366() throws Exception {
		assertNoException("""
					1
					+
				""");
	}

	@Test
	public void test_0367() throws Exception {
		assertNoException("""
					1+2
				""");
	}

	@Test
	public void test_0368() throws Exception {
		assertNoException("""
					1/*
					*/+2
				""");
	}

	@Test
	public void test_0369() throws Exception {
		assertNoException("""
					1

					2
				""");
	}

	@Test
	public void test_0370() throws Exception {
		assertNoException("""
					/* some copyright header */
					1+2
				""");
	}

	@Test
	public void test_0371() throws Exception {
		assertNoException("""
					1
				""");
	}

	@Test
	public void test_0372() throws Exception {
		assertNoException("""
					a.
				""");
	}

	@Test
	public void test_0373() throws Exception {
		assertNoException("""
					return
				""");
	}

	@Test
	public void test_0374() throws Exception {
		assertNoException("""
					if (true) "string"
				""");
	}

	@Test
	public void test_0375() throws Exception {
		assertNoException("""
					f(): .. {}
				""");
	}

	@Test
	public void test_0376() throws Exception {
		assertNoException("""
					class target {
						f(): void {
							this;
						}
					}
				""");
	}

	@Test
	public void test_0377() throws Exception {
		assertNoException("""
					var target = class {
						x = this;
					}
				""");
	}

	@Test
	public void test_0378() throws Exception {
		assertNoException("""
					var target = class {
						get x() {
							this;
							return null;
						}
					}
				""");
	}

	@Test
	public void test_0379() throws Exception {
		assertNoException("""
					var notarget = {
						s: this
					}
				""");
	}

	@Test
	public void test_0380() throws Exception {
		assertNoException("""
					interface target {
						f(): void {
							this;
						}
					}
				""");
	}

	@Test
	public void test_0381() throws Exception {
		assertNoException("""
					var target = class {
						f(): void {
							this;
						}
					}
				""");
	}

	@Test
	public void test_0382() throws Exception {
		assertNoException("""
					var target = class {
						set x(y) {
							this;
						}
					}
				""");
	}

	@Test
	public void test_0383() throws Exception {
		assertNoException("""
					var target = {
						s: "hello",
						f: function() {
							this;
						}
					}
				""");
	}

	@Test
	public void test_0384() throws Exception {
		assertNoException("""
					var target = {
						s: "hello",
						get x() {
							this;
							return null;
						}
					}
				""");
	}

	@Test
	public void test_0385() throws Exception {
		assertNoException("""
					var target = {
						s: "hello",
						set x(y) {
							this;
						}
					}
				""");
	}

	@Test
	public void test_0386() throws Exception {
		assertNoException("""
					var notarget = function() {
						this;
					}
				""");
	}

	@Test
	public void test_0387() throws Exception {
		assertNoException("""
					var notarget = {
						s: "hello",
						f: function() {
							var x = function() {
								this;
							}
						}
					}
				""");
	}

	@Test
	public void test_0388() throws Exception {
		assertNoException("""
					function notarget(){
						this;
					}
				""");
	}

	@Test
	public void test_0389() throws Exception {
		assertNoException("""
					@Internal public class C {
					}
				""");
	}

	@Test
	public void test_0390() throws Exception {
		assertNoException("""
					import { C as C } from 'c'
					import { C as B } from 'c'
					import { C as A } from 'c'

					@Internal public class D {
						m(b: B): A {
						}
					}
				""");
	}

	@Test
	public void test_0391() throws Exception {
		assertNoException("""
					@Internal public class C<A> {
						<B> m(b: B): A {
						}
					}
				""");
	}

	@Test
	public void test_0392() throws Exception {
		assertNoException("""
					export project enum StorageType {
						FILESYSTEM, DATABASE, CLOUD
					}
					export public interface Element {
					}
					export public class Storage<E extends Element> {
						private type: StorageType;
					}
				""");
	}

	@Test
	public void test_0393() throws Exception {
		assertNoException("""
					public class C {
						public constructor() {}
						public m() {}
					}
				""");
	}

	@Test
	public void test_0394() throws Exception {
		assertNoException("""
					enum Color {
						RED, GREEN, BLUE
					}

					var c: Color = Color.RED;
				""");
	}

	@Test
	public void test_0395() throws Exception {
		assertNoException("""
					enum E{ LITERAL } // cannot be empty
				""");
	}

	@Test
	public void test_0396() throws Exception {
		assertNoException("""
					enum E{  } // cannot be empty
				""");
	}

	@Test
	public void test_0397() throws Exception {
		assertNoException("""
					import A from "p/A"
					import {C,D,E} from "p/E"
					import * as F from "p/F"
					import {A as G} from "p/G"
					import {A as H, B as I} from "p/H"
				""");
	}

	@Test
	public void test_0398() throws Exception {
		assertNoException("""
					import A as B from "p/A"
				""");
	}

	@Test
	public void test_0399() throws Exception {
		assertNoException("""
					export * from 'p/A'/*
					*/export * from 'p/A'; export * from 'p/A'
					export * from "p/A"
				""");
	}

	@Test
	public void test_0400() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0401() throws Exception {
		assertNoException("""
					export @Export public class A{}
					export interface B{}
					export function foo() {}
					export var a;
					export const c="Hi";
				""");
	}

	@Test
	public void test_0402() throws Exception {
		assertNoException("""
					import 'p/A' /*
					*/ import 'p/A';import 'p/A'
					import "p/A"
				""");
	}

	@Test
	public void test_0403() throws Exception {
		assertNoException("""
					import ImportedBinding from 'p/A'
					import * as ImportedBinding from 'p/A'
				""");
	}

	@Test
	public void test_0404() throws Exception {
		assertNoException("""
					import {} from 'p/A'
					import { ImportsList } from 'p/A'
					import { ImportsList as X } from 'p/A'
					import { ImportsList, } from 'p/A'
					import { ImportsList as X, } from 'p/A'
					import { ImportsList, Second } from 'p/A'
					import { ImportsList as X, Second } from 'p/A'
					import { ImportsList, Second, } from 'p/A'
					import { ImportsList as X, Second as Y, } from 'p/A'
				""");
	}

	@Test
	public void test_0405() throws Exception {
		assertNoException("""
					import ImportedBinding, * as NameSpaceImport from 'p/A'
					import ImportedBinding, {} from 'p/A'
				""");
	}

	@Test
	public void test_0406() throws Exception {
		assertNoException("""
				    42
				""");
	}

	@Test
	public void test_0407() throws Exception {
		assertNoException("""
					null
				""");
	}

	@Test
	public void test_0408() throws Exception {
		assertNoException("""
					this
				""");
	}

	@Test
	public void test_0409() throws Exception {
		assertNoException("""
					(1 + 2 ) * 3
				""");
	}

	@Test
	public void test_0410() throws Exception {
		assertNoException("""
					(1) + (2 ) + 3
				""");
	}

	@Test
	public void test_0411() throws Exception {
		assertNoException("""
					4 + 5 << (6)
				""");
	}

	@Test
	public void test_0412() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0413() throws Exception {
		assertNoException("""
					class X {
					/* 2 */	a: any;
					/* 3 */	a: any;
					}
				""");
	}

	@Test
	public void test_0414() throws Exception {
		assertNoException("""
					var v = v
				""");
	}

	@Test
	public void test_0415() throws Exception {
		assertNoException("""
					class A {
						private ___a: string;

						getA(): string {  return this.___a;  }
					}
					class B extends A {
						private ___b: string;

						@Override
						getA(): string {  return this.___b;  }
					}
				""");
	}

	@Test
	public void test_0416() throws Exception {
		assertNoException("""
					class A {
						private ___a: string;

						get a(): string {  return this.___a;  }
						set a(aParam: string) {   this.___a = aParam;   }
					}
				""");
	}

	@Test
	public void test_0417() throws Exception {
		assertNoException("""
					@Deprectated public class ~C {}
					@Deprectated public class D {}
				""");
	}

	@Test
	public void test_0418() throws Exception {
		assertNoException("""
					public class C {
						p: C;
					}
				""");
	}

	@Test
	public void test_0419() throws Exception {
		assertNoException("""
					public class ~C {}
					public class D {}
				""");
	}

	@Test
	public void test_0420() throws Exception {
		assertNoException("""
					try { } catch (arguments) { }
				""");
	}

	@Test
	public void test_0421() throws Exception {
		assertNoException("""
					try { } finally { cleanup(stuff) }
				""");
	}

	@Test
	public void test_0422() throws Exception {
		assertNoException("""
					try { } catch (e) { say(e) }
				""");
	}

	@Test
	public void test_0423() throws Exception {
		assertNoException("""
					try { } catch (e) { }
				""");
	}

	@Test
	public void test_0424() throws Exception {
		assertNoException("""
					try { } catch (e: any) { }
				""");
	}

	@Test
	public void test_0425() throws Exception {
		assertNoException("""
					"use strict"; try {} finally { function x() {} }
				""");
	}

	@Test
	public void test_0426() throws Exception {
		assertNoException("""
					try { doThat(); } catch (e) { say(e) } finally { cleanup(stuff) }
				""");
	}

	@Test
	public void test_0427() throws Exception {
		assertNoException("""
					try { doThat(); } catch (e) { say(e) }
				""");
	}

	@Test
	public void test_0428() throws Exception {
		assertNoException("""
					try {} finally { function x() {} }
				""");
	}

	@Test
	public void test_0429() throws Exception {
		assertNoException("""
					"use strict"; try {} catch (e) { function x() {} }
				""");
	}

	@Test
	public void test_0430() throws Exception {
		assertNoException("""
					try { } catch (e: any) { }
				""");
	}

	@Test
	public void test_0431() throws Exception {
		assertNoException("""
					try { } catch (eval) { }
				""");
	}

	@Test
	public void test_0432() throws Exception {
		assertNoException("""
					/\\/\\.\\//
				""");
	}

	@Test
	public void test_0433() throws Exception {
		assertNoException("""
					/[^\\/]+\\/\\.\\.\\//
				""");
	}

	@Test
	public void test_0434() throws Exception {
		assertNoException("""
					/()??/
				""");
	}

	@Test
	public void test_0435() throws Exception {
		assertNoException("""
					/[-]/
				""");
	}

	@Test
	public void test_0436() throws Exception {
		assertNoException("""
					/[^-]/
				""");
	}

	@Test
	public void test_0437() throws Exception {
		assertNoException("""
					/$/
				""");
	}

	@Test
	public void test_0438() throws Exception {
		assertNoException("""
					/=/
				""");
	}

	@Test
	public void test_0439() throws Exception {
		assertNoException("""
					/=/g
				""");
	}

	@Test
	public void test_0440() throws Exception {
		assertNoException("""
					/[]/
				""");
	}

	@Test
	public void test_0441() throws Exception {
		assertNoException("""
					/[^]/
				""");
	}

	@Test
	public void test_0442() throws Exception {
		assertNoException("""
					/[$]/
				""");
	}

	@Test
	public void test_0443() throws Exception {
		assertNoException("""
					/,:=![,:=!]/
				""");
	}

	@Test
	public void test_0444() throws Exception {
		assertNoException("""
					/^{{[#^>/]?\\s*[\\w.][^}]*}}/
				""");
	}

	@Test
	public void test_0445() throws Exception {
		assertNoException("""
					/a|b/
				""");
	}

	@Test
	public void test_0446() throws Exception {
		assertNoException("""
					/./
				""");
	}

	@Test
	public void test_0447() throws Exception {
		assertNoException("""
					/\\b/
				""");
	}

	@Test
	public void test_0448() throws Exception {
		assertNoException("""
					/\\B/
				""");
	}

	@Test
	public void test_0449() throws Exception {
		assertNoException("""
					/\\\\u000A/
				""");
	}

	@Test
	public void test_0450() throws Exception {
		assertNoException("""
					/\\\\u000D/
				""");
	}

	@Test
	public void test_0451() throws Exception {
		assertNoException("""
					/\\\\u2028/
				""");
	}

	@Test
	public void test_0452() throws Exception {
		assertNoException("""
					/\\\\u2029/
				""");
	}

	@Test
	public void test_0453() throws Exception {
		assertNoException("""
					/a|/
				""");
	}

	@Test
	public void test_0454() throws Exception {
		assertNoException("""
					/|a/
				""");
	}

	@Test
	public void test_0455() throws Exception {
		assertNoException("""
					/a||b/
				""");
	}

	@Test
	public void test_0456() throws Exception {
		assertNoException("""
					/\\./
				""");
	}

	@Test
	public void test_0457() throws Exception {
		assertNoException("""
					/123{123}/
				""");
	}

	@Test
	public void test_0458() throws Exception {
		assertNoException("""
					/123{123,}/
				""");
	}

	@Test
	public void test_0459() throws Exception {
		assertNoException("""
					/123{123,123}/
				""");
	}

	@Test
	public void test_0460() throws Exception {
		assertNoException("""
					/123{123}?/
				""");
	}

	@Test
	public void test_0461() throws Exception {
		assertNoException("""
					/123+/
				""");
	}

	@Test
	public void test_0462() throws Exception {
		assertNoException("""
					/123+?/
				""");
	}

	@Test
	public void test_0463() throws Exception {
		assertNoException("""
					/123*?/
				""");
	}

	@Test
	public void test_0464() throws Exception {
		assertNoException("""
					/123*/
				""");
	}

	@Test
	public void test_0465() throws Exception {
		assertNoException("""
					/123??/
				""");
	}

	@Test
	public void test_0466() throws Exception {
		assertNoException("""
					/123?/
				""");
	}

	@Test
	public void test_0467() throws Exception {
		assertNoException("""
					/[\\b]/
				""");
	}

	@Test
	public void test_0468() throws Exception {
		assertNoException("""
					/123/
				""");
	}

	@Test
	public void test_0469() throws Exception {
		assertNoException("""
					/Hi You/gsm
				""");
	}

	@Test
	public void test_0470() throws Exception {
		assertNoException("""
					/(?:\\r\\n?|\\n).*/g
				""");
	}

	@Test
	public void test_0471() throws Exception {
		assertNoException("""
					/(?:\\r\\n?|\\n)/g
				""");
	}

	@Test
	public void test_0472() throws Exception {
		assertNoException("""
					/\\n/g
				""");
	}

	@Test
	public void test_0473() throws Exception {
		assertNoException("""
					/\\r?\\n?/
				""");
	}

	@Test
	public void test_0474() throws Exception {
		assertNoException("""
					/\\\\"/
				""");
	}

	@Test
	public void test_0475() throws Exception {
		assertNoException("""
					/\\\\'/
				""");
	}

	@Test
	public void test_0476() throws Exception {
		assertNoException("""
					/^(?:\\\\\\\\(?=(\\{\\{)))/
				""");
	}

	@Test
	public void test_0477() throws Exception {
		assertNoException("""
					/^(?:[^\\x00]*?(?=(\\{\\{)))/
				""");
	}

	@Test
	public void test_0478() throws Exception {
		assertNoException("""
					/^(?:[^\\x00]+)/
				""");
	}

	@Test
	public void test_0479() throws Exception {
		assertNoException("""
					/^(?:[^\\x00]{2,}?(?=(\\{\\{|$)))/
				""");
	}

	@Test
	public void test_0480() throws Exception {
		assertNoException("""
					/^(?:[\\s\\S]*?--\\}\\})/
				""");
	}

	@Test
	public void test_0481() throws Exception {
		assertNoException("""
					/^(?:\\{\\{>)/
				""");
	}

	@Test
	public void test_0482() throws Exception {
		assertNoException("""
					/^(?:\\{\\{#)/
				""");
	}

	@Test
	public void test_0483() throws Exception {
		assertNoException("""
					/^(?:\\{\\{\\/)/
				""");
	}

	@Test
	public void test_0484() throws Exception {
		assertNoException("""
					/^(?:\\{\\{\\^)/
				""");
	}

	@Test
	public void test_0485() throws Exception {
		assertNoException("""
					/^(?:\\{\\{\\s*else\\b)/
				""");
	}

	@Test
	public void test_0486() throws Exception {
		assertNoException("""
					/^(?:\\{\\{\\{)/
				""");
	}

	@Test
	public void test_0487() throws Exception {
		assertNoException("""
					/^(?:\\{\\{&)/
				""");
	}

	@Test
	public void test_0488() throws Exception {
		assertNoException("""
					/^(?:\\{\\{!--)/
				""");
	}

	@Test
	public void test_0489() throws Exception {
		assertNoException("""
					/^(?:\\{\\{![\\s\\S]*?\\}\\})/
				""");
	}

	@Test
	public void test_0490() throws Exception {
		assertNoException("""
					/^(?:\\{\\{)/
				""");
	}

	@Test
	public void test_0491() throws Exception {
		assertNoException("""
					/^(?:=)/
				""");
	}

	@Test
	public void test_0492() throws Exception {
		assertNoException("""
					/^(?:\\.(?=[}\\/ ]))/
				""");
	}

	@Test
	public void test_0493() throws Exception {
		assertNoException("""
					/^(?:\\.\\.)/
				""");
	}

	@Test
	public void test_0494() throws Exception {
		assertNoException("""
					/^(?:[\\/.])/
				""");
	}

	@Test
	public void test_0495() throws Exception {
		assertNoException("""
					/^(?:\\s+)/
				""");
	}

	@Test
	public void test_0496() throws Exception {
		assertNoException("""
					/^(?:\\}\\}\\})/
				""");
	}

	@Test
	public void test_0497() throws Exception {
		assertNoException("""
					/^(?:\\}\\})/
				""");
	}

	@Test
	public void test_0498() throws Exception {
		assertNoException("""
					/^(?:"(\\\\["]|[^"])*")/
				""");
	}

	@Test
	public void test_0499() throws Exception {
		assertNoException("""
					/^(?:'(\\\\[']|[^'])*')/
				""");
	}

	@Test
	public void test_0500() throws Exception {
		assertNoException("""
					/^(?:@)/
				""");
	}

	@Test
	public void test_0501() throws Exception {
		assertNoException("""
					/^(?:true(?=[}\\s]))/
				""");
	}

	@Test
	public void test_0502() throws Exception {
		assertNoException("""
					/^(?:false(?=[}\\s]))/
				""");
	}

	@Test
	public void test_0503() throws Exception {
		assertNoException("""
					/^(?:-?[0-9]+(?=[}\\s]))/
				""");
	}

	@Test
	public void test_0504() throws Exception {
		assertNoException("""
					/^(?:[^\\s!"#%-,\\.\\/;->@\\[-\\^`\\{-~]+(?=[=}\\s\\/.]))/
				""");
	}

	@Test
	public void test_0505() throws Exception {
		assertNoException("""
					/^(?:\\[[^\\]]*\\])/
				""");
	}

	@Test
	public void test_0506() throws Exception {
		assertNoException("""
					/^(?:.)/
				""");
	}

	@Test
	public void test_0507() throws Exception {
		assertNoException("""
					/^(?:$)/
				""");
	}

	@Test
	public void test_0508() throws Exception {
		assertNoException("""
					/[a-z]/
				""");
	}

	@Test
	public void test_0509() throws Exception {
		assertNoException("""
					/[aa-z]/
				""");
	}

	@Test
	public void test_0510() throws Exception {
		assertNoException("""
					/[a-za]/
				""");
	}

	@Test
	public void test_0511() throws Exception {
		assertNoException("""
					/[a-zA-Z1-z-]/
				""");
	}

	@Test
	public void test_0512() throws Exception {
		assertNoException("""
					/[-a-zA-Z1-z-]/
				""");
	}

	@Test
	public void test_0513() throws Exception {
		assertNoException("""
					/Hi You/
				""");
	}

	@Test
	public void test_0514() throws Exception {
		assertNoException("""
					/^/
				""");
	}

	@Test
	public void test_0515() throws Exception {
		assertNoException("""
					/(?=Hi You)/
				""");
	}

	@Test
	public void test_0516() throws Exception {
		assertNoException("""
					/(?!Hi You)/
				""");
	}

	@Test
	public void test_0517() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0518() throws Exception {
		assertNoException("""
					function f(){
						@This(any)
						function g() {}
					}
				""");
	}

	@Test
	public void test_0519() throws Exception {
		assertNoException("""
					/* var */ x*/
				""");
	}

	@Test
	public void test_0520() throws Exception {
		assertNoException(
				"""
							YUI.add("button-plugin",function(e,t){function n(){n.superclass.constructor.apply(this,arguments)}e.extend(n,e.ButtonCore,{_afterNodeGet:function(t){var n=this.constructor.ATTRS,r=n[t]&&n[t].getter&&this[n[t].getter];if(r)return new e.Do.AlterReturn("get "+t,r.call(this))},_afterNodeSet:function(e,t){var n=this.constructor.ATTRS,r=n[e]&&n[e].setter&&this[n[e].setter];r&&r.call(this,t)},_initNode:function(t){var n=t.host;this._host=n,e.Do.after(this._afterNodeGet,n,"get",this),e.Do.after(this._afterNodeSet,n,"set",this)},destroy:function(){}},{ATTRS:e.merge(e.ButtonCore.ATTRS),NAME:"buttonPlugin",NS:"button"}),n.createNode=function(t,n){var r;return t&&!n&&!t.nodeType&&!t.getDOMNode&&typeof t!="string"&&(n=t,t=n.srcNode),n=n||{},r=n.template||e.Plugin.Button.prototype.TEMPLATE,t=t||n.srcNode||e.DOM.create(r),e.one(t).plug(e.Plugin.Button,n)},e.namespace("Plugin").Button=n},"3.12.0",{requires:["button-core","cssbutton","node-pluginhost"]});
						""");
	}

	@Test
	public void test_0521() throws Exception {
		assertNoException("""
					replace(/[^\\/]+\\/\\.\\.\\//)
				""");
	}

	@Test
	public void test_0522() throws Exception {
		assertNoException("""
					var e=/^/g;
				""");
	}

	@Test
	public void test_0523() throws Exception {
		assertNoException("""
					if(true){}/[^\\s]/.test(a)
				""");
	}

	@Test
	public void test_0524() throws Exception {
		assertNoException("""
					{if(true){}}/[^\\s]/.test(a)
				""");
	}

	@Test
	public void test_0525() throws Exception {
		assertNoException("""
					{if(true){}/[^\\s]/.test(a)}
				""");
	}

	@Test
	public void test_0526() throws Exception {
		assertNoException("""
					if(true){({})/s}
				""");
	}

	@Test
	public void test_0527() throws Exception {
		assertNoException("""
					if(true){({}/s)}
				""");
	}

	@Test
	public void test_0528() throws Exception {
		assertNoException("""
					if ({} / 1 !== 1) {}
				""");
	}

	@Test
	public void test_0529() throws Exception {
		assertNoException("""
					isNaN(function(){return 1} / {}) !== true
				""");
	}

	@Test
	public void test_0530() throws Exception {
		assertNoException("""
					if ((x = 1) / x !== 1) {}
				""");
	}

	@Test
	public void test_0531() throws Exception {
		assertNoException("""
					if (isNaN({} / function(){return 1}) !== true) {}
				""");
	}

	@Test
	public void test_0532() throws Exception {
		assertNoException("""
					if(true){if(true)b}/[^\\s]/.test(a)
				""");
	}

	@Test
	public void test_0533() throws Exception {
		assertNoException("""
					if (isNaN(function(){return 1} / {}) !== true) {}
				""");
	}

	@Test
	public void test_0534() throws Exception {
		assertNoException("""
					if (true)/a/.test(a)
				""");
	}

	@Test
	public void test_0535() throws Exception {
		assertNoException(
				"""
							YUI.add("app-transitions",function(e,t){function n(){}n.ATTRS={transitions:{setter:"_setTransitions",value:!1}},n.FX={fade:{viewIn:"app:fadeIn",viewOut:"app:fadeOut"},slideLeft:{viewIn:"app:slideLeft",viewOut:"app:slideLeft"},slideRight:{viewIn:"app:slideRight",viewOut:"app:slideRight"}},n.prototype={transitions:{navigate:"fade",toChild:"slideLeft",toParent:"slideRight"},_setTransitions:function(t){var n=this.transitions;return t&&t===!0?e.merge(n):t}},e.App.Transitions=n,e.Base.mix(e.App,[n]),e.mix(e.App.CLASS_NAMES,{transitioning:e.ClassNameManager.getClassName("app","transitioning")})},"3.12.0",{requires:["app-base"]});
						""");
	}

	@Test
	public void test_0536() throws Exception {
		assertNoException("""
					e=+e,e!==e?e=0:e!==0&&e!==1/0&&e!==-1/0&&(e=(e>0||-1)*Math.floor(Math.abs(e))),e
				""");
	}

	@Test
	public void test_0537() throws Exception {
		assertNoException("""
					"use strict";
					'\\0'
				""");
	}

	@Test
	public void test_0538() throws Exception {
		assertNoException("""
					x0() / y0()
				""");
	}

	@Test
	public void test_0539() throws Exception {
		assertNoException("""
					var reflect = value => value;

					// effectively equivalent to:

					var reflect = function(value) {
					    return value;
					};
				""");
	}

	@Test
	public void test_0540() throws Exception {
		assertNoException("""
					var sum = (num1, num2) => num1 + num2;

					// effectively equivalent to:

					var sum = function(num1, num2) {
					    return num1 + num2;
					};
				""");
	}

	@Test
	public void test_0541() throws Exception {
		assertNoException("""
					var sum = () => 1 + 2;

					// effectively equivalent to:

					var sum = function() {
					    return 1 + 2;
					};
				""");
	}

	@Test
	public void test_0542() throws Exception {
		assertNoException("""
					var sum = (num1, num2) => { return num1 + num2; }

					// effectively equivalent to:

					var sum = function(num1, num2) {
					    return num1 + num2;
					};
				""");
	}

	@Test
	public void test_0543() throws Exception {
		assertNoException("""
					var getTempItem = id => ({ id: id, name: "Temp" });

					// effectively equivalent to:

					var getTempItem = function(id) {

					    return {
					        id: id,
					        name: "Temp"
					    };
					};
				""");
	}

	@Test
	public void test_0544() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0545() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0546() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0547() throws Exception {
		assertNoException("""
					var result = values.sort(function(a, b) {
					    return a - b;
					});
					var result = values.sort((a, b) => a - b);
				""");
	}

	@Test
	public void test_0548() throws Exception {
		assertNoException("""
					var identity = (identityParam) => identityParam;
					assert.equal(1234, identity(1234));
				""");
	}

	@Test
	public void test_0549() throws Exception {
		assertNoException("""
					((a, b)=>a + b)
				""");
	}

	@Test
	public void test_0550() throws Exception {
		assertNoException("""
					(... x: any)=>x
				""");
	}

	@Test
	public void test_0551() throws Exception {
		assertNoException("""
					function CommentCtrl($scope, articles) {
					    $scope.comments = [];

					    articles.getList()
					        .then((articles) => Promise.all(articles.map((article) => article.comments.getList())))
					        .then((commentLists) => {
					            $scope.comments = commentLists.reduce((a, b) => a.concat(b));
					        });
					}
				""");
	}

	@Test
	public void test_0552() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0553() throws Exception {
		assertNoException("""
					({a = 0});
				""");
	}

	@Test
	public void test_0554() throws Exception {
		assertNoException("""
					((a, b)=>a + b)()
				""");
	}

	@Test
	public void test_0555() throws Exception {
		assertNoException("""
					var f = (x, ...xs);
				""");
	}

	@Test
	public void test_0556() throws Exception {
		assertNoException("""
					var identity = (x) => {x}.bind({});
				""");
	}

	@Test
	public void test_0557() throws Exception {
		assertNoException("""
					function f() {
					  ({a = (0, {a = 0})} = {})
					}
				""");
	}

	@Test
	public void test_0558() throws Exception {
		assertNoException("""
					x
					=>x
				""");
	}

	@Test
	public void test_0559() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0560() throws Exception {
		assertNoException("""
					(a, b)=>a + b
				""");
	}

	@Test
	public void test_0561() throws Exception {
		assertNoException("""
					x=>x
				""");
	}

	@Test
	public void test_0562() throws Exception {
		assertNoException("""
					()=>{}
				""");
	}

	@Test
	public void test_0563() throws Exception {
		assertNoException("""
					(x) + (y) => y;
				""");
	}

	@Test
	public void test_0564() throws Exception {
		assertNoException("""
					(x) + y => y;
				""");
	}

	@Test
	public void test_0565() throws Exception {
		assertNoException("""
					function f() {
					  (1 ? ({a=0}) => {} : 1);
					}
				""");
	}

	@Test
	public void test_0566() throws Exception {
		assertNoException("""
					x /*
					*/ =>x
				""");
	}

	@Test
	public void test_0567() throws Exception {
		assertNoException("""
					(a: any, b: any)=>a + b
				""");
	}

	@Test
	public void test_0568() throws Exception {
		assertNoException("""
					x /* comment */ =>x
				""");
	}

	@Test
	public void test_0569() throws Exception {
		assertNoException("""
					(Unknown... x)=>x
				""");
	}

	@Test
	public void test_0570() throws Exception {
		assertNoException("""
					( a: any, b: any )=>a + b
				""");
	}

	@Test
	public void test_0571() throws Exception {
		assertNoException("""
					x => x => x
				""");
	}

	@Test
	public void test_0572() throws Exception {
		assertNoException("""
					var f = (a, b + 5) => a + b;
				""");
	}

	@Test
	public void test_0573() throws Exception {
		assertNoException("""
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
				""");
	}

	@Test
	public void test_0574() throws Exception {
		assertNoException("""
					function f() {
					  var args = (() => arguments)();
					  assert.equal(args, arguments);
					}

					f();
				""");
	}

	@Test
	public void test_0575() throws Exception {
		assertNoException("""
					{
					  var f = (...xs, x) => xs;
					}
				""");
	}

	@Test
	public void test_0576() throws Exception {
		assertNoException("""
					function f() {
						var z = (x)=>{
							()=>
							return z(x)
						}
					}
				""");
	}

	@Test
	public void test_0577() throws Exception {
		assertNoException("""
					function f() {
						var z = (x)=>{
							()=>
							return z(x);
						}
					}
				""");
	}

	@Test
	public void test_0578() throws Exception {
		assertNoException("""
					function f() {
						var z = (x)=>{
							()=>
							return z(x);
						};
					};
				""");
	}

	@Test
	public void test_0579() throws Exception {
		assertNoException("""
					with (x) foo = bar;
				""");
	}

	@Test
	public void test_0580() throws Exception {
		assertNoException("""
					with (x) foo = bar
				""");
	}

	@Test
	public void test_0581() throws Exception {
		assertNoException("""
					with (x) { foo = bar }
				""");
	}

	@Test
	public void test_0582() throws Exception {
		assertNoException("""
					export var s = class Supplier {
						Supplier foo() {}
					}
				""");
	}

	@Test
	public void test_0583() throws Exception {
		assertNoException("""
					import s from "org.eclipse.n4js/tests/scoping/Supplier";
					s.foo()
				""");
	}

	@Test
	public void test_0584() throws Exception {
		assertNoException(
				"""
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
						""");
	}

	@Test
	public void test_0585() throws Exception {
		assertNoException("""
					var a=1;
					var b=a;
				""");
	}

	@Test
	public void test_0586() throws Exception {
		assertNoException("""
					export class A {
						public methodOfA(): any
					}
					var a: A;
					a.methodOfA()
				""");
	}

	@Test
	public void test_0587() throws Exception {
		assertNoException("""
					export class Supplier {
						Supplier foo();
					}
				""");
	}

	@Test
	public void test_0588() throws Exception {
		assertNoException("""
					import Supplier from "org.eclipse.n4js/tests/scoping/Supplier";
					var Supplier a;
					a;
				""");
	}

	@Test
	public void test_0589() throws Exception {
		assertNoException("""
					export class Supplier {
						Supplier foo() {}
					}
				""");
	}

	@Test
	public void test_0590() throws Exception {
		assertNoException("""
					import Supplier from "org.eclipse.n4js/tests/scoping/Supplier";
					var Supplier a;
				""");
	}

	@Test
	public void test_0591() throws Exception {
		assertNoException("""
					export class Supplier {
						Supplier foo()  {}
					}
				""");
	}

	@Test
	public void test_0592() throws Exception {
		assertNoException("""
					import Supplier from "org.eclipse.n4js/tests/scoping/Supplier";
					var Supplier a;
					a.foo();
				""");
	}

	@Test
	public void test_0593() throws Exception {
		assertNoException("""
					import { Supplier as MySupplier } from "org.eclipse.n4js/tests/scoping/Supplier";
					var MySupplier a;
				""");
	}

	@Test
	public void test_0594() throws Exception {
		assertNoException("""
					export class A {
						foo(): any {
						}
					}
				""");
	}

	@Test
	public void test_0595() throws Exception {
		assertNoException("""
					class A {
						foo(): any {}
					}
					var a: A;
					a.foo();
				""");
	}

	@Test
	public void test_0596() throws Exception {
		assertNoException("""
					class A {}
					var a: A;
				""");
	}

	@Test
	public void test_0597() throws Exception {
		assertNoException("""
					var x = 1;
					++x;
				""");
	}

	@Test
	public void test_0598() throws Exception {
		assertNoException("""
					var x = 1
					++x
				""");
	}

	@Test
	public void test_0599() throws Exception {
		assertNoException("""
					var x = 1
					--x
				""");
	}

}
