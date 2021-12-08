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

import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.Script
import org.junit.Test

/**
 */
class CallSignatureParserTest extends AbstractParserTest {


	@Test
	def void testCallSignature_withSemicolon() {
		'''
			export external public class C {
				field: N4Object;
				(param: string): void;
				constructor(param: number);
			}
		'''.parseESSuccessfully.assertCallSignature;
	}

	@Test
	def void testCallSignature_withoutSemicolon() {
		'''
			export external public class C {
				field: N4Object;
				(param: string): void
				constructor(param: number)
			}
		'''.parseESSuccessfully.assertCallSignature;
	}

	@Test
	def void testCallSignature_withoutArgsAndReturnType() {
		'''
			export external public class C {
				field: N4Object;
				()
				constructor()
			}
		'''.parseESSuccessfully.assertCallSignature;
	}

	@Test
	def void testCallSignature_betweenAccessors() {
		'''
			export external public class C {
				set s(value)()get g()
			}
		'''.parseESSuccessfully.assertCallSignature;
	}

	@Test
	def void testCallSignature_betweenMethods() {
		'''
			export external public class C {
				m1()()m2()
			}
		'''.parseESSuccessfully.assertCallSignature;
	}

	def private void assertCallSignature(Script script) {
		val C = script.eAllContents.filter(N4ClassDeclaration).head;
		assertEquals(3, C.ownedMembersRaw.size);
		assertEquals(2, C.ownedMembers.size);
		val callSig = C.ownedCallSignature;
		assertNotNull(callSig);
		assertTrue(callSig.isCallSignature);
		assertEquals(1, C.ownedMembersRaw.indexOf(callSig));
		assertFalse(C.ownedMembers.contains(callSig));
	}
}
