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
class CallableCtorParserTest extends AbstractParserTest {


	@Test
	def void testCallableCtor_withSemicolon() {
		'''
			export external public class C {
				field: N4Object;
				(param: string): void;
				constructor(param: number);
			}
		'''.parseSuccessfully.assertCallableCtor;
	}

	@Test
	def void testCallableCtor_withoutSemicolon() {
		'''
			export external public class C {
				field: N4Object;
				(param: string): void
				constructor(param: number)
			}
		'''.parseSuccessfully.assertCallableCtor;
	}

	@Test
	def void testCallableCtor_withoutArgsAndReturnType() {
		'''
			export external public class C {
				field: N4Object;
				()
				constructor()
			}
		'''.parseSuccessfully.assertCallableCtor;
	}

	@Test
	def void testCallableCtor_betweenAccessors() {
		'''
			export external public class C {
				set s(value)()get g()
			}
		'''.parseSuccessfully.assertCallableCtor;
	}

	@Test
	def void testCallableCtor_betweenMethods() {
		'''
			export external public class C {
				m1()()m2()
			}
		'''.parseSuccessfully.assertCallableCtor;
	}

	def private void assertCallableCtor(Script script) {
		val C = script.eAllContents.filter(N4ClassDeclaration).head;
		assertEquals(3, C.ownedMembersRaw.size);
		assertEquals(2, C.ownedMembers.size);
		val callableCtor = C.ownedCallableCtor;
		assertNotNull(callableCtor);
		assertTrue(callableCtor.isCallableConstructor);
		assertEquals(1, C.ownedMembersRaw.indexOf(callableCtor));
		assertFalse(C.ownedMembers.contains(callableCtor));
	}
}
