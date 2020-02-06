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

import org.junit.Assert
import org.junit.Test

abstract class AbstractParserTests extends Assert {

	def void assertValid(CharSequence expression)

	@Test
	def void testHelloWorld() {
		'/Hello World/'.assertValid
	}

	@Test
	def void testHelloWorldWithFlags() {
		'/Hello World/gsm'.assertValid
	}

	@Test
	def void testNumber() {
		'/123/'.assertValid
	}

	@Test
	def void testWildcard() {
		'/./'.assertValid
	}

	@Test
	def void testEscapedWildcard() {
		'/\\./'.assertValid
	}

	@Test
	def void testEscapedDecimal() {
		'/\\1/'.assertValid
	}

	@Test
	def void testEscapedSpace() {
		'/\\ /'.assertValid
	}

	@Test
	def void testAlternative() {
		'/a|b/'.assertValid
	}

	@Test
	def void testEmptyAlternative_01() {
		'/a|/'.assertValid
	}

	@Test
	def void testEmptyAlternative_02() {
		'/|a/'.assertValid
	}

	@Test
	def void testEmptyAlternative_03() {
		'/a||b/'.assertValid
	}

	@Test
	def void testAssertionLineStart() {
		'/^/'.assertValid
	}

	@Test
	def void testAssertionLineEnd() {
		'/$/'.assertValid
	}

	@Test
	def void testWordBoundary_01() {
		'/\\b/'.assertValid
	}

	@Test
	def void testWordBoundary_02() {
		'/\\B/'.assertValid
	}

	@Test
	def void testLookAhead_01() {
		'/(?=Hello World)/'.assertValid
	}

	@Test
	def void testLookAhead_02() {
		'/(?!Hello World)/'.assertValid
	}

	@Test
	def void testEmptyCharacterClass_01() {
		'/[]/'.assertValid
	}

	@Test
	def void testEmptyCharacterClass_02() {
		'/[^]/'.assertValid
	}

	@Test
	def void testDollarInCharacterClass() {
		'/[$]/'.assertValid
	}

	@Test
	def void testDashInCharacterClass_01() {
		'/[-]/'.assertValid
	}

	@Test
	def void testDashInCharacterClass_02() {
		'/[^-]/'.assertValid
	}

	@Test
	def void testBracketInCharacterClass_01() {
		'/[[]/'.assertValid
	}

	@Test
	def void testRangeInCharacterClass_01() {
		'/[a-z]/'.assertValid
	}

	@Test
	def void testRangeInCharacterClass_02() {
		'/[aa-z]/'.assertValid
	}

	@Test
	def void testRangeInCharacterClass_03() {
		'/[a-za]/'.assertValid
	}

	@Test
	def void testRangeInCharacterClass_05() {
		'/[a-zA-Z1-z-]/'.assertValid
	}

	@Test
	def void testRangeInCharacterClass_06() {
		'/[-a-zA-Z1-z-]/'.assertValid
	}

	@Test
	def void testBackspaceInCharacterClass() {
		'/[\\b]/'.assertValid
	}

	@Test
	def void testQuantifier_01() {
		'/123{123}/'.assertValid
	}

	@Test
	def void testQuantifier_02() {
		'/123{123,}/'.assertValid
	}

	@Test
	def void testQuantifier_03() {
		'/123{123,123}/'.assertValid
	}

	@Test
	def void testQuantifier_04() {
		'/123{123}?/'.assertValid
	}

	@Test
	def void testQuantifier_05() {
		'/123+/'.assertValid
	}

	@Test
	def void testQuantifier_06() {
		'/123+?/'.assertValid
	}

	@Test
	def void testQuantifier_07() {
		'/123*?/'.assertValid
	}

	@Test
	def void testQuantifier_08() {
		'/123*/'.assertValid
	}

	@Test
	def void testQuantifier_09() {
		'/123??/'.assertValid
	}

	@Test
	def void testQuantifier_10() {
		'/123?/'.assertValid
	}

	@Test
	def void testAssignWithWS() {
		'/,:=![,:=!]/'.assertValid
	}

	@Test
	def void testIdentityEscape_01() {
		'''/\\/'''.assertValid
	}

	@Test
	def void testIdentityEscape_02() {
		'''/\\u000A/'''.assertValid
	}

	@Test
	def void testIdentityEscape_03() {
		'''/\\u000D/'''.assertValid
	}

	@Test
	def void testIdentityEscape_04() {
		'''/\\u2028/'''.assertValid
	}

	@Test
	def void testIdentityEscape_05() {
		'''/\\u2029/'''.assertValid
	}
	
	@Test
	def void testLookBehind() {
		'''/(?<=$abc)def/'''.assertValid
		'''/^f.o(?<=foo)$/'''.assertValid
		'''/^foo(?<!foo)$/'''.assertValid
		'''/^f.o(?<!foo)$/'''.assertValid
		'''/^foooo(?<=fo+)$/'''.assertValid
		'''/^foooo(?<=fo*)$/'''.assertValid
	}
	
	@Test
	def void testNamedGroups() {
		'''/(?<=(?<a>\w){3})f/u'''.assertValid
		'''/(?<=(?<a>\w)+)f/'''.assertValid
		'''/((?<=\w{3}))f/'''.assertValid
		'''/(?<a>(?<=\w{3}))f/'''.assertValid
		'''/(?<!(?<a>\d){3})f/u'''.assertValid
		'''/(?<!(?<a>\D){3})f|f/u'''.assertValid
		'''/(?<a>\k<a>\w)../'''.assertValid
		'''/\k<a>(?<a>b)\w\k<a>/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_01() {
		'''/(?:\r\n?|\n).*/g'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_02() {
		'''/(?:\r\n?|\n)/g'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_03() {
		'''/\n/g'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_04() {
		'''/\r?\n?/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_05() {
		'''/\\"/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_06() {
		'''/\\'/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_07() {
		'''/^(?:\\\\(?=(\{\{)))/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_08() {
		'''/^(?:[^\x00]*?(?=(\{\{)))/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_09() {
		'''/^(?:[^\x00]+)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_10() {
		'''/^(?:[^\x00]{2,}?(?=(\{\{|$)))/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_11() {
		'''/^(?:[\s\S]*?--\}\})/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_12() {
		'''/^(?:\{\{>)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_13() {
		'''/^(?:\{\{#)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_14() {
		'''/^(?:\{\{\/)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_15() {
		'''/^(?:\{\{\^)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_16() {
		'''/^(?:\{\{\s*else\b)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_17() {
		'''/^(?:\{\{\{)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_18() {
		'''/^(?:\{\{&)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_19() {
		'''/^(?:\{\{!--)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_20() {
		'''/^(?:\{\{![\s\S]*?\}\})/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_21() {
		'''/^(?:\{\{)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_22() {
		'''/^(?:=)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_23() {
		'''/^(?:\.(?=[}\/ ]))/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_24() {
		'''/^(?:\.\.)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_25() {
		'''/^(?:[\/.])/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_26() {
		'''/^(?:\s+)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_27() {
		'''/^(?:\}\}\})/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_28() {
		'''/^(?:\}\})/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_29() {
		'''/^(?:"(\\["]|[^"])*")/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_30() {
		'''/^(?:'(\\[']|[^'])*')/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_31() {
		'''/^(?:@)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_32() {
		'''/^(?:true(?=[}\s]))/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_33() {
		'''/^(?:false(?=[}\s]))/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_34() {
		'''/^(?:-?[0-9]+(?=[}\s]))/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_35() {
		'''/^(?:[^\s!"#%-,\.\/;->@\[-\^`\{-~]+(?=[=}\s\/.]))/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_36() {
		'''/^(?:\[[^\]]*\])/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_37() {
		'''/^(?:.)/'''.assertValid
	}

	@Test
	def void testExampleHandlebarsCompiler_38() {
		'''/^(?:$)/'''.assertValid
	}

	@Test
	def void testExamplePrettify_01() {
		'''/^{{[#^>/]?\s*[\w.][^}]*}}/'''.assertValid
	}

	@Test
	def void testExampleAce_01() {
		'''/\/\.\//'''.assertValid
	}

	@Test
	def void testExampleAce_02() {
		'''/[^\/]+\/\.\.\//'''.assertValid
	}

	@Test
	def void testExampleAce_03() {
		'''/()??/'''.assertValid
	}

	@Test
	def void testEqualsSign_01() {
		'/=/'.assertValid
	}

	@Test
	def void testEqualsSign_02() {
		'/=/g'.assertValid
	}
}
