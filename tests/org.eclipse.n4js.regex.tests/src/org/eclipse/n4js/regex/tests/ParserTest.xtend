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
package org.eclipse.n4js.regex.tests

import com.google.inject.Inject
import org.eclipse.n4js.regex.RegularExpressionInjectorProvider
import org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(RegularExpressionInjectorProvider)
class ParserTest extends AbstractParserTests {

	@Inject extension ParseHelper<RegularExpressionLiteral>

	override assertValid(CharSequence expression) {
		val parsed = expression.parse
		val errors = parsed.eResource.errors
		assertTrue(errors.toString, errors.isEmpty)
	}

	@Test
	def void testEmptyRegex_01() {
		'//'.assertValid
	}

	@Test
	def void testEmptyRegex_02() {
		'//i'.assertValid
	}

	@Test
	def void testEmptyRegex_03() {
		'//\\u0050'.assertValid
	}

	@Test
	def void testEmptyRegex_04() {
		'//ä'.assertValid
	}

	@Test
	def void test_01() {
		'''/[a-z]/i'''.assertValid
	}

	@Test
	def void test_02() {
		'''/[x-z]/i'''.assertValid
	}

	@Test
	def void test_03() {
		'''/[a-c]/i'''.assertValid
	}

	@Test
	def void test_04() {
		'''/[P QR]/i'''.assertValid
	}

	@Test
	def void test_05() {
		'''/foo\/bar/'''.assertValid
	}

	@Test
	def void test_06() {
		'''/=([^=\s])+/g'''.assertValid
	}

	@Test
	def void test_07() {
		'''/42/g'''.assertValid
	}

	@Test
	def void test_08() {
		'''/1/'''.assertValid
	}

	@Test
	def void test_09() {
		'''/a/'''.assertValid
	}

	@Test
	def void test_10() {
		'''/;/'''.assertValid
	}

	@Test
	def void test_11() {
		'''/ /'''.assertValid
	}

	@Test
	def void test_12() {
		'''/\u0041/'''.assertValid
	}

	@Test
	def void test_13() {
		'''/\1/'''.assertValid
	}

	@Test
	def void test_14() {
		'''/\d/'''.assertValid
	}

	@Test
	def void test_15() {
		'''/\;/'''.assertValid
	}

	@Test
	def void test_16() {
		'''/\ /'''.assertValid
	}

	@Test
	def void test_17() {
		'''/(?:)/g'''.assertValid
	}

	@Test
	def void test_18() {
		'''/(?:)/i'''.assertValid
	}

	@Test
	def void test_19() {
		'''/(?:)/m'''.assertValid
	}

	@Test
	def void test_20() {
		'''/(?:)/gi'''.assertValid
	}

	@Test
	def void test_21() {
		'''/(?:)/mg'''.assertValid
	}

	@Test
	def void test_22() {
		'''/(?:)/mig'''.assertValid
	}

	@Test
	def void test_23() {
		'''/a\1/'''.assertValid
	}

	@Test
	def void test_24() {
		'''/a\D/'''.assertValid
	}

	@Test
	def void test_25() {
		'''/,\;/'''.assertValid
	}

	@Test
	def void test_26() {
		'''/ \ /'''.assertValid
	}

	@Test
	def void test_27() {
		'''/\a/'''.assertValid
	}

	@Test
	def void test_28() {
		'''/a\a/'''.assertValid
	}

	@Test
	def void test_29() {
		'''/\$/'''.assertValid
	}

	@Test
	def void test_30() {
		'''/a\^/'''.assertValid
	}

	@Test
	def void test_31() {
		'''/\{/'''.assertValid
	}

	@Test
	def void test_32() {
		'''/\(/'''.assertValid
	}

	@Test
	def void test_33() {
		'''/\_/'''.assertValid
	}

	@Test
	def void test_34() {
		'''/(?:)/\u0067'''.assertValid
	}

	@Test
	def void test_35() {
		'''/(?:)/\u0069'''.assertValid
	}

	@Test
	def void test_36() {
		'''/(?:)/\u006D'''.assertValid
	}
	
	@Test
	def void test_37() {
		'''/[(?]:/'''.assertValid
	}
	
	@Test
	def void test_38() {
		'''/[(?!]:/'''.assertValid
	}
	
	@Test
	def void test_39() {
		'''/[(?=!]:/'''.assertValid
	}
	
	@Test
	def void test_40() {
		'''/[(?<!]:/'''.assertValid
	}
	
	@Test
	def void test_41() {
		'''/[(?<]:/'''.assertValid
	}
	
	@Test
	def void test_42() {
		'''/[(?<=]:/'''.assertValid
	}

	@Test
	def void test_43() {
		'''/[_]/'''.assertValid
	}

	@Test
	def void test_44() {
		'''/[^_]/'''.assertValid
	}
	
	@Test
	def void test_45() {
		'''/[\_]/'''.assertValid
	}
		
	@Test
	def void testUnescapedBracket_01() {
		'/[]]/'.assertValid
	}

	@Test
	def void testUnescapedBracket_02() {
		'/[[]]/'.assertValid
	}
}
