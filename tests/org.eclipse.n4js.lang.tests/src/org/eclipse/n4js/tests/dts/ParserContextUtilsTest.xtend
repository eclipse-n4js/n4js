/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.dts

import org.eclipse.n4js.dts.utils.DtsMode
import org.eclipse.n4js.dts.utils.ParserContextUtils
import org.junit.Test

import static org.junit.Assert.*

import static extension org.eclipse.n4js.dts.DtsParser.parseDts

/**
 * Tests for methods in {@link ParserContextUtils}.
 */
class ParserContextUtilsTest {

	@Test
	def void testIsModule01() {
		val prog = '''
			class Cls {}
		'''.parseDts;
		assertSame(DtsMode.SCRIPT, ParserContextUtils.getDtsMode(prog));
	}

	@Test
	def void testIsModule02() {
		val prog = '''
			declare class Cls {}
		'''.parseDts;
		assertSame(DtsMode.SCRIPT, ParserContextUtils.getDtsMode(prog));
	}

	@Test
	def void testIsModule03() {
		val prog = '''
			import * as N from "other"
			declare class Cls {}
		'''.parseDts;
		assertSame(DtsMode.MODULE, ParserContextUtils.getDtsMode(prog));
	}

	@Test
	def void testIsModule04() {
		val prog = '''
			declare class Cls1 {}
			export class Cls2 {}
		'''.parseDts;
		assertSame(DtsMode.MODULE, ParserContextUtils.getDtsMode(prog));
	}
}
