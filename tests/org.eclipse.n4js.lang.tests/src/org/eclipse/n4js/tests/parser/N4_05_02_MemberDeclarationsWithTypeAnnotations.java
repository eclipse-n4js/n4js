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
package org.eclipse.n4js.tests.parser;

import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

/**
 * Parser tests for type annotations in functions.
 */
public class N4_05_02_MemberDeclarationsWithTypeAnnotations extends AbstractParserTest {

	@Test
	public void test_Members_OLD() throws Exception {
		Script script = parseHelper.parse("""
				class C {
				  a: int = 5;
				  b: string;
				  c(p: int): string { return ""; }
				  d(s: union{int, string}, p: int) {}
				  get e(): string { return "";}
				  set f(v: string) {}

				}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void test_Members() throws Exception {
		Script script = parseHelper.parse("""
				class C {
				  a: int = 5;
				  b: string;
				  c (p: int): string { return ""; }
				  d (s: union{int, string}, p: int) {}
				  get e(): string { return "";}
				  set f(v: string) { }
				}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void test_MembersWithAnnotation_OLD() throws Exception {
		Script script = parseHelper.parse("""
				class C {
				  @Final a: int = 5;
				  @Final b: string;
				  @Final c(p: int): string { return ""; }
				  @Final d(s: union{int, string}, p: int) {}
				  @Final get e(): string { return "";}
				  @Final set f(v: string) {}

				}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

	@Test
	public void test_MembersWithAnntation() throws Exception {
		Script script = parseHelper.parse("""
				class C {
				  @Final a: int = 5;
				  @Final b: string;
				  @Final c(p: int): string { return ""; }
				  @Final d(s: union{int, string}, p: int) {}
				  @Final get e(): string { return "";}
				  @Final set f(v: string) { }
				}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
	}

}
