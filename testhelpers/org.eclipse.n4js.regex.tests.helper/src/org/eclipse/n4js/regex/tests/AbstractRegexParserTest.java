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
package org.eclipse.n4js.regex.tests;

import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractRegexParserTest extends Assert {

	protected abstract void assertValid(CharSequence expression);

	@Test
	public void testHelloWorld() {
		assertValid("/Hello World/");
	}

	@Test
	public void testHelloWorldWithFlags() {
		assertValid("/Hello World/gsm");
	}

	@Test
	public void testNumber() {
		assertValid("/123/");
	}

	@Test
	public void testWildcard() {
		assertValid("/./");
	}

	@Test
	public void testEscapedWildcard() {
		assertValid("/\\./");
	}

	@Test
	public void testEscapedDecimal() {
		assertValid("/\\1/");
	}

	@Test
	public void testEscapedSpace() {
		assertValid("/\\ /");
	}

	@Test
	public void testAlternative() {
		assertValid("/a|b/");
	}

	@Test
	public void testEmptyAlternative_01() {
		assertValid("/a|/");
	}

	@Test
	public void testEmptyAlternative_02() {
		assertValid("/|a/");
	}

	@Test
	public void testEmptyAlternative_03() {
		assertValid("/a||b/");
	}

	@Test
	public void testAssertionLineStart() {
		assertValid("/^/");
	}

	@Test
	public void testAssertionLineEnd() {
		assertValid("/$/");
	}

	@Test
	public void testWordBoundary_01() {
		assertValid("/\\b/");
	}

	@Test
	public void testWordBoundary_02() {
		assertValid("/\\B/");
	}

	@Test
	public void testLookAhead_01() {
		assertValid("/(?=Hello World)/");
	}

	@Test
	public void testLookAhead_02() {
		assertValid("/(?!Hello World)/");
	}

	@Test
	public void testEmptyCharacterClass_01() {
		assertValid("/[]/");
	}

	@Test
	public void testEmptyCharacterClass_02() {
		assertValid("/[^]/");
	}

	@Test
	public void testDollarInCharacterClass() {
		assertValid("/[$]/");
	}

	@Test
	public void testDashInCharacterClass_01() {
		assertValid("/[-]/");
	}

	@Test
	public void testDashInCharacterClass_02() {
		assertValid("/[^-]/");
	}

	@Test
	public void testBracketInCharacterClass_01() {
		assertValid("/[[]/");
	}

	@Test
	public void testRangeInCharacterClass_01() {
		assertValid("/[a-z]/");
	}

	@Test
	public void testRangeInCharacterClass_02() {
		assertValid("/[aa-z]/");
	}

	@Test
	public void testRangeInCharacterClass_03() {
		assertValid("/[a-za]/");
	}

	@Test
	public void testRangeInCharacterClass_05() {
		assertValid("/[a-zA-Z1-z-]/");
	}

	@Test
	public void testRangeInCharacterClass_06() {
		assertValid("/[-a-zA-Z1-z-]/");
	}

	@Test
	public void testBackspaceInCharacterClass() {
		assertValid("/[\\b]/");
	}

	@Test
	public void testQuantifier_01() {
		assertValid("/123{123}/");
	}

	@Test
	public void testQuantifier_02() {
		assertValid("/123{123,}/");
	}

	@Test
	public void testQuantifier_03() {
		assertValid("/123{123,123}/");
	}

	@Test
	public void testQuantifier_04() {
		assertValid("/123{123}?/");
	}

	@Test
	public void testQuantifier_05() {
		assertValid("/123+/");
	}

	@Test
	public void testQuantifier_06() {
		assertValid("/123+?/");
	}

	@Test
	public void testQuantifier_07() {
		assertValid("/123*?/");
	}

	@Test
	public void testQuantifier_08() {
		assertValid("/123*/");
	}

	@Test
	public void testQuantifier_09() {
		assertValid("/123??/");
	}

	@Test
	public void testQuantifier_10() {
		assertValid("/123?/");
	}

	@Test
	public void testAssignWithWS() {
		assertValid("/,:=![,:=!]/");
	}

	@Test
	public void testIdentityEscape_01() {
		assertValid("/\\\\/");
	}

	@Test
	public void testIdentityEscape_02() {
		assertValid("/\\u000A/");
	}

	@Test
	public void testIdentityEscape_03() {
		assertValid("/\\u000D/");
	}

	@Test
	public void testIdentityEscape_04() {
		assertValid("/\\u2028/");
	}

	@Test
	public void testIdentityEscape_05() {
		assertValid("/\\u2029/");
	}

	@Test
	public void testLookBehind() {
		assertValid("/(?<=$abc)def/");
		assertValid("/^f.o(?<=foo)$/");
		assertValid("/^foo(?<!foo)$/");
		assertValid("/^f.o(?<!foo)$/");
		assertValid("/^foooo(?<=fo+)$/");
		assertValid("/^foooo(?<=fo*)$/");
	}

	@Test
	public void testNamedGroups() {
		assertValid("/(?<=(?<a>\\w){3})f/u");
		assertValid("/(?<=(?<a>\\w)+)f/");
		assertValid("/((?<=\\w{3}))f/");
		assertValid("/(?<a>(?<=\\w{3}))f/");
		assertValid("/(?<!(?<a>\\d){3})f/u");
		assertValid("/(?<!(?<a>\\D){3})f|f/u");
		assertValid("/(?<a>\\k<a>\\w)../");
		assertValid("/\\k<a>(?<a>b)\\w\\k<a>/");
	}

	@Test
	public void testExampleHandlebarsCompiler_01() {
		assertValid("/(?:\\r\\n?|\\n).*/g");
	}

	@Test
	public void testExampleHandlebarsCompiler_02() {
		assertValid("/(?:\\r\\n?|\\n)/g");
	}

	@Test
	public void testExampleHandlebarsCompiler_03() {
		assertValid("/\\n/g");
	}

	@Test
	public void testExampleHandlebarsCompiler_04() {
		assertValid("/\\r?\\n?/");
	}

	@Test
	public void testExampleHandlebarsCompiler_05() {
		assertValid("/\\\"/");
	}

	@Test
	public void testExampleHandlebarsCompiler_06() {
		assertValid("/\\'/");
	}

	@Test
	public void testExampleHandlebarsCompiler_07() {
		assertValid("/^(?:\\\\(?=(\\{\\{)))/");
	}

	@Test
	public void testExampleHandlebarsCompiler_08() {
		assertValid("/^(?:[^\\x00]*?(?=(\\{\\{)))/");
	}

	@Test
	public void testExampleHandlebarsCompiler_09() {
		assertValid("/^(?:[^\\x00]+)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_10() {
		assertValid("/^(?:[^\\x00]{2,}?(?=(\\{\\{|$)))/");
	}

	@Test
	public void testExampleHandlebarsCompiler_11() {
		assertValid("/^(?:[\\s\\S]*?--\\}\\})/");
	}

	@Test
	public void testExampleHandlebarsCompiler_12() {
		assertValid("/^(?:\\{\\{>)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_13() {
		assertValid("/^(?:\\{\\{#)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_14() {
		assertValid("/^(?:\\{\\{\\/)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_15() {
		assertValid("/^(?:\\{\\{\\^)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_16() {
		assertValid("/^(?:\\{\\{\\s*else\\b)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_17() {
		assertValid("/^(?:\\{\\{\\{)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_18() {
		assertValid("/^(?:\\{\\{&)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_19() {
		assertValid("/^(?:\\{\\{!--)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_20() {
		assertValid("/^(?:\\{\\{![\\s\\S]*?\\}\\})/");
	}

	@Test
	public void testExampleHandlebarsCompiler_21() {
		assertValid("/^(?:\\{\\{)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_22() {
		assertValid("/^(?:=)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_23() {
		assertValid("/^(?:\\.(?=[}\\/]))/");

	}

	@Test
	public void testExampleHandlebarsCompiler_24() {
		assertValid("/^(?:\\.\\.)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_25() {
		assertValid("/^(?:[\\/.])/");
	}

	@Test
	public void testExampleHandlebarsCompiler_26() {
		assertValid("/^(?:\s+)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_27() {
		assertValid("/^(?:\\}\\}\\})/");
	}

	@Test
	public void testExampleHandlebarsCompiler_28() {
		assertValid("/^(?:\\}\\})/");
	}

	@Test
	public void testExampleHandlebarsCompiler_29() {
		assertValid("/^(?:\"(\\[\"]|[^\"])*\")/");
	}

	@Test
	public void testExampleHandlebarsCompiler_30() {
		assertValid("/^(?:'(\\[']|[^'])*')/");
	}

	@Test
	public void testExampleHandlebarsCompiler_31() {
		assertValid("/^(?:@)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_32() {
		assertValid("/^(?:true(?=[}\s]))/");

	}

	@Test
	public void testExampleHandlebarsCompiler_33() {
		assertValid("/^(?:false(?=[}\s]))/");

	}

	@Test
	public void testExampleHandlebarsCompiler_34() {
		assertValid("/^(?:-?[0-9]+(?=[}\s]))/");

	}

	@Test
	public void testExampleHandlebarsCompiler_35() {
		assertValid("/^(?:[^\\s!\"#%-,\\.\\/;->@\\[-\\^`\\{-~]+(?=[=}\\s\\/.]))/");
	}

	@Test
	public void testExampleHandlebarsCompiler_36() {
		assertValid("/^(?:\\[[^\\]]*\\])/");
	}

	@Test
	public void testExampleHandlebarsCompiler_37() {
		assertValid("/^(?:.)/");
	}

	@Test
	public void testExampleHandlebarsCompiler_38() {
		assertValid("/^(?:$)/");
	}

	@Test
	public void testExamplePrettify_01() {
		assertValid("/^{{[#^>/]?\\s*[\\w.][^}]*}}/");

	}

	@Test
	public void testExampleAce_01() {
		assertValid("/\\/\\.\\//");
	}

	@Test
	public void testExampleAce_02() {
		assertValid("/[^\\/]+\\/\\.\\.\\//");
	}

	@Test
	public void testExampleAce_03() {
		assertValid("/()??/");
	}

	@Test
	public void testEqualsSign_01() {
		assertValid("/=/");
	}

	@Test
	public void testEqualsSign_02() {
		assertValid("/=/g");
	}

	@Test
	public void testWikipedia() {
		assertValid("/(?<=\\.){2,}(?=[A-Z])/");
	}
}
