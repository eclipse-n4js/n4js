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

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;

import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class CallSignatureParserTest extends AbstractParserTest {

	@Test
	public void testCallSignature_withSemicolon() throws Exception {
		assertCallSignature(parseESSuccessfully("""
				export external public class C {
					field: N4Object;
					(param: string): void;
					constructor(param: number);
				}
				"""));
	}

	@Test
	public void testCallSignature_withoutSemicolon() throws Exception {
		assertCallSignature(parseESSuccessfully("""
				export external public class C {
					field: N4Object;
					(param: string): void
					constructor(param: number)
				}
				"""));
	}

	@Test
	public void testCallSignature_withoutArgsAndReturnType() throws Exception {
		assertCallSignature(parseESSuccessfully("""
				export external public class C {
					field: N4Object;
					()
					constructor()
				}
				"""));
	}

	@Test
	public void testCallSignature_betweenAccessors() throws Exception {
		assertCallSignature(parseESSuccessfully("""
				export external public class C {
					set s(value)()get g()
				}
				"""));
	}

	@Test
	public void testCallSignature_betweenMethods() throws Exception {
		assertCallSignature(parseESSuccessfully("""
				export external public class C {
					m1()()m2()
				}
				"""));
	}

	private void assertCallSignature(Script script) {
		N4ClassDeclaration C = head(filter(script.eAllContents(), N4ClassDeclaration.class));
		assertEquals(3, C.getOwnedMembersRaw().size());
		assertEquals(2, C.getOwnedMembers().size());
		N4MethodDeclaration callSig = C.getOwnedCallSignature();
		assertNotNull(callSig);
		assertTrue(callSig.isCallSignature());
		assertEquals(1, C.getOwnedMembersRaw().indexOf(callSig));
		assertFalse(C.getOwnedMembers().contains(callSig));
	}
}
