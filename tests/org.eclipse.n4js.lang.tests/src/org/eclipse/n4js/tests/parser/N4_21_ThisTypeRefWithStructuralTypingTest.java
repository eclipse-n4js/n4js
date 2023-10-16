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

import java.util.List;

import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Parser tests for N4 structurally reference this type. Test methods with suffix "example" are taken from the N4JS
 * spec. IDE-691
 */
public class N4_21_ThisTypeRefWithStructuralTypingTest extends AbstractStructuralTypingTest {

	@Test
	public void testThisTypeRefWithStructuralTypingConstructorPlain() throws Exception {
		Script script = parseHelper.parse("""
				class A {
					constructor(p: ~this) {}
				}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(1, script.getScriptElements().size());
		N4ClassDeclaration classDecl = (N4ClassDeclaration) script.getScriptElements().get(0);
		N4MethodDeclaration ctor = classDecl.getOwnedCtor();
		assertEquals(1, ctor.getFpars().size());

		FormalParameter p = ctor.getFpars().get(0);
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL, p);
	}

	@Test
	public void testThisTypeRefWithStructuralTypingConstructorWithMembers() throws Exception {
		Script script = parseHelper.parse("""
				class A {
					constructor(p: ~this with { s: string; }) {}
				}
				""");
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		assertEquals(1, script.getScriptElements().size());
		N4ClassDeclaration classDecl = (N4ClassDeclaration) script.getScriptElements().get(0);
		N4MethodDeclaration ctor = classDecl.getOwnedCtor();
		assertEquals(1, ctor.getFpars().size());

		FormalParameter p = ctor.getFpars().get(0);
		assertTypingStrategyOfFPar(TypingStrategy.STRUCTURAL, p);

		assertAdditionalFieldsThis(TypingStrategy.STRUCTURAL, List.of(Pair.of("s", "string")),
				p.getDeclaredTypeRefInAST());

	}

	public void assertTypingStrategyOfFPar(TypingStrategy expectedStrategy, FormalParameter fpar) throws Exception {
		assertType(ThisTypeRef.class, fpar.getDeclaredTypeRefInAST());
		ThisTypeRef ptrOfFPar = (ThisTypeRef) fpar.getDeclaredTypeRefInAST();
		assertEquals(
				"Expected " + expectedStrategy.getName() + " but was " + ptrOfFPar.getTypingStrategy().getName() + ": ",
				expectedStrategy, ptrOfFPar.getTypingStrategy());
	}

}
