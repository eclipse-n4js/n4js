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
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.parser.AbstractParserTest;
import org.junit.Test;

/**
 * Parser tests for external modifier for classes.
 */
public class ClassExternalTypesBuilderTest extends AbstractParserTest {

	@Test
	public void testClassExternal() throws Exception {
		Script script = parseHelper.parse("""
				public class C {
				}
				external public class D {
				}
				""");

		assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());

		N4ClassDeclaration cdecl = (N4ClassDeclaration) script.getScriptElements().get(0);
		assertEquals("C", cdecl.getName());
		assertFalse(cdecl.isExternal());
		assertFalse(cdecl.getDefinedTypeAsClass().isExternal());

		N4ClassDeclaration ddecl = (N4ClassDeclaration) script.getScriptElements().get(1);
		assertEquals("D", ddecl.getName());
		assertTrue(ddecl.isExternal());
		assertTrue(ddecl.getDefinedTypeAsClass().isExternal());
	}
}
