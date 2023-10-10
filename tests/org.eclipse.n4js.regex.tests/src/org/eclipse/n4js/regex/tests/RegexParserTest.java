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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.regex.RegularExpressionInjectorProvider;
import org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(RegularExpressionInjectorProvider.class)
public class RegexParserTest extends AbstractRegexParserTest {

	@Inject
	ParseHelper<RegularExpressionLiteral> parseHelper;

	@Override
	public void assertValid(CharSequence expression) {
		RegularExpressionLiteral parsed;
		try {
			parsed = parseHelper.parse(expression);
			EList<Diagnostic> errors = parsed.eResource().getErrors();
			assertTrue(errors.toString(), errors.isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testEmptyRegex_01() {
		assertValid("//");
	}

	@Test
	public void testEmptyRegex_02() {
		assertValid("//i");
	}

	@Test
	public void testEmptyRegex_03() {
		assertValid("//\\u0050");
	}

	@Test
	public void testEmptyRegex_04() {
		assertValid("//ä");
	}

	@Test
	public void test_01() {
		assertValid("/[a-z]/i");
	}

	@Test
	public void test_02() {
		assertValid("/[x-z]/i");
	}

	@Test
	public void test_03() {
		assertValid("/[a-c]/i");
	}

	@Test
	public void test_04() {
		assertValid("/[P QR]/i");
	}

	@Test
	public void test_05() {
		assertValid("/foo\\/bar/");
	}

	@Test
	public void test_06() {
		assertValid("/=([^=\s])+/g");
	}

	@Test
	public void test_07() {
		assertValid("/42/g");
	}

	@Test
	public void test_08() {
		assertValid("/1/");
	}

	@Test
	public void test_09() {
		assertValid("/a/");
	}

	@Test
	public void test_10() {
		assertValid("/;/");
	}

	@Test
	public void test_11() {
		assertValid("/ /");
	}

	@Test
	public void test_12() {
		assertValid("/\u0041/");
	}

	@Test
	public void test_13() {
		assertValid("/\1/");
	}

	@Test
	public void test_14() {
		assertValid("/\\d/");
	}

	@Test
	public void test_15() {
		assertValid("/\\;/");
	}

	@Test
	public void test_16() {
		assertValid("/\\ /");
	}

	@Test
	public void test_17() {
		assertValid("/(?:)/g");
	}

	@Test
	public void test_18() {
		assertValid("/(?:)/i");
	}

	@Test
	public void test_19() {
		assertValid("/(?:)/m");
	}

	@Test
	public void test_20() {
		assertValid("/(?:)/gi");
	}

	@Test
	public void test_21() {
		assertValid("/(?:)/mg");
	}

	@Test
	public void test_22() {
		assertValid("/(?:)/mig");
	}

	@Test
	public void test_23() {
		assertValid("/a\1/");
	}

	@Test
	public void test_24() {
		assertValid("/a\\D/");
	}

	@Test
	public void test_25() {
		assertValid("/,\\;/");
	}

	@Test
	public void test_26() {
		assertValid("/ \\ /");
	}

	@Test
	public void test_27() {
		assertValid("/\\a/");
	}

	@Test
	public void test_28() {
		assertValid("/a\\a/");
	}

	@Test
	public void test_29() {
		assertValid("/\\$/");
	}

	@Test
	public void test_30() {
		assertValid("/a\\^/");
	}

	@Test
	public void test_31() {
		assertValid("/\\{/");
	}

	@Test
	public void test_32() {
		assertValid("/\\(/");
	}

	@Test
	public void test_33() {
		assertValid("/\\_/");
	}

	@Test
	public void test_34() {
		assertValid("/(?:)/\u0067");
	}

	@Test
	public void test_35() {
		assertValid("/(?:)/\u0069");
	}

	@Test
	public void test_36() {
		assertValid("/(?:)/\u006D");
	}

	@Test
	public void test_37() {
		assertValid("/[(?]:/");
	}

	@Test
	public void test_38() {
		assertValid("/[(?!]:/");
	}

	@Test
	public void test_39() {
		assertValid("/[(?=!]:/");
	}

	@Test
	public void test_40() {
		assertValid("/[(?<!]:/");
	}

	@Test
	public void test_41() {
		assertValid("/[(?<]:/");
	}

	@Test
	public void test_42() {
		assertValid("/[(?<=]:/");
	}

	@Test
	public void test_43() {
		assertValid("/[_]/");
	}

	@Test
	public void test_44() {
		assertValid("/[^_]/");
	}

	@Test
	public void test_45() {
		assertValid("/[\\_]/");
	}

	@Test
	public void test_46() {
		assertValid("/_A/");
	}

	@Test
	public void test_47() {
		assertValid("/B_/");
	}

	@Test
	public void test_48() {
		assertValid("/_C_/");
	}

	@Test
	public void test_49() {
		assertValid("/__D__E__/");
	}

	@Test
	public void test_50() {
		assertValid("/1_1/");
	}

	@Test
	public void testUnescapedBracket_01() {
		assertValid("/[]]/");
	}

	@Test
	public void testUnescapedBracket_02() {
		assertValid("/[[]]/");
	}
}
