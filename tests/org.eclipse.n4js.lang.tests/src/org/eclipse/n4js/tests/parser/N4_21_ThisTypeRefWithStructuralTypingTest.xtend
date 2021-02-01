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

import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.types.TypingStrategy
import org.junit.Test

/**
 * Parser tests for N4 structurally reference this type. Test methods with suffix "example" are taken from the N4JS spec.
 * IDE-691
 */
class N4_21_ThisTypeRefWithStructuralTypingTest extends AbstractStructuralTypingTest {

	@Test
	def void testThisTypeRefWithStructuralTypingConstructorPlain() {
		val script = '''
			class A {
				constructor(p: ~this) {}
			}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(1, script.scriptElements.size);
		val classDecl = script.scriptElements.head as N4ClassDeclaration;
		val ctor = classDecl.ownedCtor
		assertEquals(1, ctor.fpars.size);

		val p = ctor.fpars.get(0);
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL, p)
	}

	@Test
	def void testThisTypeRefWithStructuralTypingConstructorWithMembers() {
		val script = '''
			class A {
				constructor(p: ~this with { s: string; }) {}
			}
		'''.parse
		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
		assertEquals(1, script.scriptElements.size);
		val classDecl = script.scriptElements.head as N4ClassDeclaration;
		val ctor = classDecl.ownedCtor
		assertEquals(1, ctor.fpars.size);

		val p = ctor.fpars.get(0);
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL, p)

		assertAdditionalFieldsThis(TypingStrategy.STRUCTURAL, #["s" -> "string"],
			p.declaredTypeRefInAST);



	}


	def void assertTypingStrategyOfFPar(TypingStrategy expectedStrategy, FormalParameter fpar) {
		assertType(ThisTypeRef, fpar.declaredTypeRefInAST)
		val ptrOfFPar =fpar.declaredTypeRefInAST as ThisTypeRef
		assertEquals("Expected " + expectedStrategy.getName + " but was " + ptrOfFPar.typingStrategy?.getName + ": ",
			expectedStrategy, ptrOfFPar.typingStrategy);
	}



}
