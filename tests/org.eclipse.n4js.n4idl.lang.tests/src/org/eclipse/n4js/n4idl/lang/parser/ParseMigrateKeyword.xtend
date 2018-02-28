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

import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4idl.lang.AbstractN4IDLParserTest
import org.junit.Test

public class ParseMigrateKeyword extends AbstractN4IDLParserTest {

	@Test
	def void testMigrateKeyword() {
		val Script script = '''
			class C # 1 {}
			class C # 2 {}
			@Migration
			function (c1: C#1) : C#2 {
				return migrate c1;
			}
		'''.parseSuccessfully
		println(script);
	}

}
