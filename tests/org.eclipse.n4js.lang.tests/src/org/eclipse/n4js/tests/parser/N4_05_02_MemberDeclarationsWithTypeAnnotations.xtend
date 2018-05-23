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
 * Parser tests for type annotations in functions.
 */
class N4_05_02_MemberDeclarationsWithTypeAnnotations extends AbstractParserTest {


	@Test
	def void test_Members_OLD() {
		val script = '''
			class C {
			  a: int = 5;
			  b: string;
			  c(p: int): string { return ""; }
			  d(s: union{int, string}, p: int) {}
			  get e(): string { return "";}
			  set f(v: string) {}

			}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}
	@Test
	def void test_Members() {
		val script = '''
			class C {
			  a: int = 5;
			  b: string;
			  c (p: int): string { return ""; }
			  d (s: union{int, string}, p: int) {}
			  get e(): string { return "";}
			  set f(v: string) { }
			}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

	@Test
	def void test_MembersWithAnnotation_OLD() {
		val script = '''
			class C {
			  @Final a: int = 5;
			  @Final b: string;
			  @Final c(p: int): string { return ""; }
			  @Final d(s: union{int, string}, p: int) {}
			  @Final get e(): string { return "";}
			  @Final set f(v: string) {}

			}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}
	@Test
	def void test_MembersWithAnntation() {
		val script = '''
			class C {
			  @Final a: int = 5;
			  @Final b: string;
			  @Final c(p: int): string { return ""; }
			  @Final d(s: union{int, string}, p: int) {}
			  @Final get e(): string { return "";}
			  @Final set f(v: string) { }
			}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}

}
