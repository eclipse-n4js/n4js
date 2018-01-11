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
package org.eclipse.n4js.n4idl.lang.parser

import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4idl.lang.AbstractN4IDLParserTest
import org.junit.Test

public class ParseVersionedClassTest extends AbstractN4IDLParserTest {

	@Test
	def void testVersionedClass_01() {
		val Script script = 'class C #1 {}'.parseSuccessfully
		val decl = script.scriptElements.head as N4ClassDeclaration
		assertEquals("C", decl.name)
		assertEquals(1, decl.declaredVersion.intValue)
	}
	
	
	@Test
	def void testUnVersionedClass_01() {
		// The grammar allows for unversioned types in N4IDL.
		// The validator may have different constraints, but at this point (after parsing)
		// this should be valid code.
		'class C {}'.parseSuccessfully
	}
}
