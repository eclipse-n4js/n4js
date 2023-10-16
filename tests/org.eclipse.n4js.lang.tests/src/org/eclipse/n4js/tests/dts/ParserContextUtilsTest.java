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
package org.eclipse.n4js.tests.dts;

import static org.eclipse.n4js.dts.DtsParser.parseDts;
import static org.junit.Assert.assertSame;

import java.io.IOException;

import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.dts.utils.DtsMode;
import org.eclipse.n4js.dts.utils.ParserContextUtils;
import org.junit.Test;

/**
 * Tests for methods in {@link ParserContextUtils}.
 */
public class ParserContextUtilsTest {

	@Test
	public void testIsModule01() throws IOException {
		ProgramContext prog = parseDts("""
				class Cls {}
				""");
		assertSame(DtsMode.SCRIPT, ParserContextUtils.getDtsMode(prog));
	}

	@Test
	public void testIsModule02() throws IOException {
		ProgramContext prog = parseDts("""
				declare class Cls {}
				""");
		assertSame(DtsMode.SCRIPT, ParserContextUtils.getDtsMode(prog));
	}

	@Test
	public void testIsModule03() throws IOException {
		ProgramContext prog = parseDts("""
				import * as N from "other"
				declare class Cls {}
				""");
		assertSame(DtsMode.MODULE, ParserContextUtils.getDtsMode(prog));
	}

	@Test
	public void testIsModule04() throws IOException {
		ProgramContext prog = parseDts("""
				declare class Cls1 {}
				export class Cls2 {}
				""");
		assertSame(DtsMode.MODULE, ParserContextUtils.getDtsMode(prog));
	}
}
