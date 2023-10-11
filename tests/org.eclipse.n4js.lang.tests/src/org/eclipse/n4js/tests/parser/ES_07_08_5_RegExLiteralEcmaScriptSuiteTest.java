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

import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.junit.Test;

public class ES_07_08_5_RegExLiteralEcmaScriptSuiteTest extends AbstractParserTest {

	@Test
	public void test_7_8_5_1() {
		Script script = parseESWithError("var regExp = /\\\rn/;");
		VariableStatement statement = (VariableStatement) script.getScriptElements().get(0);
		RegularExpressionLiteral regex = (RegularExpressionLiteral) statement.getVarDecl().get(0).getExpression();
		assertEquals("/\\", regex.getValue());
	}

	@Test
	public void test_7_8_5_1gs() {
		parseESWithError("var re = //;");
	}

	@Test
	public void test_7_8_5_2gs() {
		parseESSuccessfully("var re = new RegExp(\"\");");
	}

	@Test
	public void test_7_8_5_A1_1_1() {
		parseESSuccessfully("/1/");
	}

	@Test
	public void test_7_8_5_A1_1_2() {
		parseESSuccessfully("/a/");
	}

	@Test
	public void test_7_8_5_A1_1_3() {
		parseESSuccessfully("/;/");
	}

	@Test
	public void test_7_8_5_A1_1_4() {
		parseESSuccessfully("/ /");
	}

	@Test
	public void test_7_8_5_A1_1_5() {
		parseESSuccessfully("/\\u0041/");
	}

	@Test
	public void test_7_8_5_A1_2_T1() {
		parseESWithError("/*/");
	}

	@Test
	public void test_7_8_5_A1_2_T3() {
		parseESWithError("///\n.source;");
	}

	@Test
	public void test_7_8_5_A1_2_T4() {
		parseESWithError("//\n.source;");
	}

	@Test
	public void test_7_8_5_A1_3_T1() {
		// this is parsed as two broken reg ex literals;
		parseESWithError("/\n/");
	}

	@Test
	public void test_7_8_5_A1_3_T2() {
		parseESSuccessfully("/\\u000A/");
	}

	@Test
	public void test_7_8_5_A1_3_T3() {
		// this is parsed as two broken reg ex literals;
		parseESWithError("/\r/");
	}

	@Test
	public void test_7_8_5_A1_3_T4() {
		parseESSuccessfully("/\\u000D/");
	}

	@Test
	public void test_7_8_5_A1_3_T5() {
		parseESSuccessfully("/\\u2028/");
	}

	@Test
	public void test_7_8_5_A1_3_T6() {
		parseESSuccessfully("/\\u2029/");
	}

	@Test
	public void test_7_8_5_A1_4_T1_1() {
		parseESSuccessfully("/\\1/");
	}

	@Test
	public void test_7_8_5_A1_4_T1_2() {
		parseESSuccessfully("/\\a/");
	}

	@Test
	public void test_7_8_5_A1_4_T1_3() {
		parseESSuccessfully("/\\;/");
	}

	@Test
	public void test_7_8_5_A1_4_T1_4() {
		parseESSuccessfully("/\\ /");
	}

	@Test
	public void test_7_8_5_A2_2_T1() {
		parseESWithError("/a\\/");
	}

	@Test
	public void test_7_8_5_A2_2_T2() {
		parseESWithError("/a//.source");
	}

	@Test
	public void test_7_8_5_A2_4_T1_1() {
		parseESSuccessfully("/a\\1/");
	}

	@Test
	public void test_7_8_5_A2_4_T1_2() {
		parseESSuccessfully("/a\\a/");
	}

	@Test
	public void test_7_8_5_A2_4_T1_3() {
		parseESSuccessfully("/,\\;/");
	}

	@Test
	public void test_7_8_5_A2_4_T1_4() {
		parseESSuccessfully("/ \\ /");
	}

	@Test
	public void test_7_8_5_A3_1_T1() {
		parseESSuccessfully("/(?:)/g");
	}

	@Test
	public void test_7_8_5_A3_1_T2() {
		parseESSuccessfully("/(?:)/i");
	}

	@Test
	public void test_7_8_5_A3_1_T3() {
		parseESSuccessfully("/(?:)/m");
	}

	@Test
	public void test_7_8_5_A3_1_T4() {
		parseESSuccessfully("/(?:)/gi");
	}

	@Test
	public void test_7_8_5_A3_1_T5() {
		parseESSuccessfully("/(?:)/mg");
	}

	@Test
	public void test_7_8_5_A3_1_T6() {
		parseESSuccessfully("/(?:)/mig");
	}

	@Test
	public void test_7_8_5_A3_1_T7() {
		parseESSuccessfully("/(?:)/\\u0067");
	}

	@Test
	public void test_7_8_5_A3_1_T8() {
		parseESSuccessfully("/(?:)/\\u0069");
	}

	@Test
	public void test_7_8_5_A3_1_T9() {
		parseESSuccessfully("/(?:)/\\u006D");
	}

}
