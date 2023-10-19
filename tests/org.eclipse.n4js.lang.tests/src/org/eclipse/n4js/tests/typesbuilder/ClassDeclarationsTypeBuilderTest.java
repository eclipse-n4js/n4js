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
package org.eclipse.n4js.tests.typesbuilder;

import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.parser.AbstractParserTest;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.junit.Test;

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
public class ClassDeclarationsTypeBuilderTest extends AbstractParserTest {

	@Test
	public void testClassDeclarations3() throws Exception {
		Script script = parseHelper.parse("""
				public class C {
					p: C;
				}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4ClassDeclaration cdecl = (N4ClassDeclaration) script.getScriptElements().get(0);
		assertEquals("C", cdecl.getName());

		N4FieldDeclaration fieldDecl = (N4FieldDeclaration) cdecl.getOwnedMembers().get(0);
		assertEquals("p", fieldDecl.getName());

		ParameterizedTypeRef typeOfP = (ParameterizedTypeRef) fieldDecl.getDeclaredTypeRefInAST();
		assertFalse(typeOfP.eIsProxy());

		assertEquals("C", typeOfP.getDeclaredType().getName());
	}

	@Test
	public void testClassDeclarationsWithSructuralTyping() throws Exception {
		Script script = parseHelper.parse("""
				public interface ~C {}
				public class D {}
				""");
		doTestClassDeclarationsWithSructuralTyping(script);
	}

	@Test
	public void testClassDeclarationsWithSructuralTypingAnnotated() throws Exception {
		Script script = parseHelper.parse("""
				@Deprectated public interface ~C {}
				@Deprectated public class D {}
				""");
		doTestClassDeclarationsWithSructuralTyping(script);
	}

	void doTestClassDeclarationsWithSructuralTyping(Script script) {
		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4InterfaceDeclaration cdecl = (N4InterfaceDeclaration) script.getScriptElements().get(0);
		assertEquals("C", cdecl.getName());
		TInterface c = (TInterface) cdecl.getDefinedType();
		assertEquals("C", c.getName());
		assertEquals(TypingStrategy.STRUCTURAL, c.getTypingStrategy());

		N4ClassDeclaration ddecl = (N4ClassDeclaration) script.getScriptElements().get(1);
		assertEquals("D", ddecl.getName());
		TClass d = (TClass) ddecl.getDefinedType();
		assertEquals("D", d.getName());
		assertEquals(TypingStrategy.NOMINAL, d.getTypingStrategy());
	}
}
