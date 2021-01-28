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

import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4idl.lang.AbstractN4IDLParserTest
import org.junit.Test

public class ParseMigrationScript extends AbstractN4IDLParserTest {

	@Test
	def void testEmptyMigrationScript() {
		val Script script = '''
			class C # 1 {}
			// TODO: add after scoping is implemented: class C # 2 {}
			@Migration
			function (c1: C#1) : C#2 {
				return null;
			}
		'''.parseSuccessfully
		val mig = script.scriptElements.get(1) as FunctionDeclaration
		assertEquals(1, mig.fpars.head.declaredTypeRef.version)
		assertEquals(2, mig.declaredReturnTypeRefInAST.version)
	}


}
