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
		val script = 'var regExp = /\\\rn/;'.parseESWithError
		val statement = script.scriptElements.head as VariableStatement
		val regex = statement.varDecl.head.expression as RegularExpressionLiteral
		assertEquals('/\\', regex.value)
	}
	@Test
	def void test_7_8_5_1gs() {
		'var re = //;'.parseESWithError
	}
	@Test
	def void test_7_8_5_2gs() {
		'var re = new RegExp("");'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_1() {
		'/1/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_2() {
		'/a/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_3() {
		'/;/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_4() {
		'/ /'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_1_5() {
		'/\\u0041/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_2_T1() {
		'/*/'.parseESWithError
	}
	@Test
	def void test_7_8_5_A1_2_T3() {
		'///\n.source;'.parseESWithError
	}
	@Test
	def void test_7_8_5_A1_2_T4() {
		'//\n.source;'.parseESWithError
	}
	@Test
	def void test_7_8_5_A1_3_T1() {
		// this is parsed as two broken reg ex literals
		'/\n/'.parseESWithError
	}
	@Test
	def void test_7_8_5_A1_3_T2() {
		'/\\u000A/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_3_T3() {
		// this is parsed as two broken reg ex literals
		'/\r/'.parseESWithError
	}
	@Test
	def void test_7_8_5_A1_3_T4() {
		'/\\u000D/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_3_T5() {
		'/\\u2028/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_3_T6() {
		'/\\u2029/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_4_T1_1() {
		'/\\1/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_4_T1_2() {
		'/\\a/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_4_T1_3() {
		'/\\;/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A1_4_T1_4() {
		'/\\ /'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A2_2_T1() {
		'/a\\/'.parseESWithError
	}
	@Test
	def void test_7_8_5_A2_2_T2() {
		'/a//.source'.parseESWithError
	}
	@Test
	def void test_7_8_5_A2_4_T1_1() {
		'/a\\1/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A2_4_T1_2() {
		'/a\\a/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A2_4_T1_3() {
		'/,\\;/'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A2_4_T1_4() {
		'/ \\ /'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T1() {
		'/(?:)/g'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T2() {
		'/(?:)/i'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T3() {
		'/(?:)/m'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T4() {
		'/(?:)/gi'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T5() {
		'/(?:)/mg'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T6() {
		'/(?:)/mig'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T7() {
		'/(?:)/\\u0067'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T8() {
		'/(?:)/\\u0069'.parseESSuccessfully
	}
	@Test
	def void test_7_8_5_A3_1_T9() {
		'/(?:)/\\u006D'.parseESSuccessfully
	}

}
