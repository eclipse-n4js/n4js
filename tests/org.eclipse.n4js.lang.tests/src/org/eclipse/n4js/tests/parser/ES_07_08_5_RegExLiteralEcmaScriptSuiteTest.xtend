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

import org.eclipse.n4js.n4JS.RegularExpressionLiteral
import org.eclipse.n4js.n4JS.VariableStatement
import org.junit.Test

class ES_07_08_5_RegExLiteralEcmaScriptSuiteTest extends AbstractParserTest {

	@Test
	def void test_7_8_5_1() {
		val script = 'var regExp = /\\\rn/;'.parseWithError
		val statement = script.scriptElements.head as VariableStatement
		val regex = statement.varDecl.head.expression as RegularExpressionLiteral
		assertEquals('/\\', regex.value)
	}
	@Test
	def void test_7_8_5_1gs() {
		'var re = //;'.parseWithError
	}
	@Test
	def void test_7_8_5_2gs() {
		'var re = new RegExp("");'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_1() {
		'/1/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_2() {
		'/a/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_3() {
		'/;/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_4() {
		'/ /'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_5() {
		'/\\u0041/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_2_T1() {
		'/*/'.parseWithError
	}
	@Test
	def void test_7_8_5_A1_2_T3() {
		'///\n.source;'.parseWithError
	}
	@Test
	def void test_7_8_5_A1_2_T4() {
		'//\n.source;'.parseWithError
	}
	@Test
	def void test_7_8_5_A1_3_T1() {
		// this is parsed as two broken reg ex literals
		'/\n/'.parseWithError
	}
	@Test
	def void test_7_8_5_A1_3_T2() {
		'/\\u000A/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_3_T3() {
		// this is parsed as two broken reg ex literals
		'/\r/'.parseWithError
	}
	@Test
	def void test_7_8_5_A1_3_T4() {
		'/\\u000D/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_3_T5() {
		'/\\u2028/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_3_T6() {
		'/\\u2029/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_4_T1_1() {
		'/\\1/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_4_T1_2() {
		'/\\a/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_4_T1_3() {
		'/\\;/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A1_4_T1_4() {
		'/\\ /'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A2_2_T1() {
		'/a\\/'.parseWithError
	}
	@Test
	def void test_7_8_5_A2_2_T2() {
		'/a//.source'.parseWithError
	}
	@Test
	def void test_7_8_5_A2_4_T1_1() {
		'/a\\1/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A2_4_T1_2() {
		'/a\\a/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A2_4_T1_3() {
		'/,\\;/'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A2_4_T1_4() {
		'/ \\ /'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T1() {
		'/(?:)/g'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T2() {
		'/(?:)/i'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T3() {
		'/(?:)/m'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T4() {
		'/(?:)/gi'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T5() {
		'/(?:)/mg'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T6() {
		'/(?:)/mig'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T7() {
		'/(?:)/\\u0067'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T8() {
		'/(?:)/\\u0069'.parseSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T9() {
		'/(?:)/\\u006D'.parseSuccessfully
	}

}
