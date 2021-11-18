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

import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.junit.Test

class NoWhiteSpaceParserTest extends AbstractParserTest {

	@Test
	def void testCallSignatureWithAnnotation01() {
		val script = '''
			interface Ifc {
				@Override
				(p: string): number;
			}
		'''.parseN4jsSuccessfully;
		val decl = script.scriptElements.head as N4ClassifierDeclaration;
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	def void testCallSignatureWithAnnotation02() {
		val script = '''
			interface Ifc {
				@Override (p: string): number;
			}
		'''.parseN4jsSuccessfully;
		val decl = script.scriptElements.head as N4ClassifierDeclaration;
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	def void testCallSignatureWithAnnotation03() {
		val script = '''
			interface Ifc {
				@Override/**/(p: string): number;
			}
		'''.parseN4jsSuccessfully;
		val decl = script.scriptElements.head as N4ClassifierDeclaration;
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	def void testCallSignatureWithAnnotation04() {
		val script = '''
			interface Ifc {
				@Override()(p: string): number;
			}
		'''.parseN4jsSuccessfully;
		val decl = script.scriptElements.head as N4ClassifierDeclaration;
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	def void testCallSignatureWithAnnotation05_bad() {
		'''
			interface Ifc {
				@Override(p: string): number;
			}
		'''.parseN4jsWithError;
	}

	def private void assertCallSignatureWithAnnotation(N4ClassifierDeclaration decl) {
		val members = decl.ownedMembersRaw;
		assertEquals(1, members.size);
		val m = members.head;
		assertTrue(m.isCallSignature);
		val annotations = m.annotations;
		assertEquals(1, annotations.size);
		val ann = annotations.head;
		assertEquals("Override", ann.name);
	}
}
