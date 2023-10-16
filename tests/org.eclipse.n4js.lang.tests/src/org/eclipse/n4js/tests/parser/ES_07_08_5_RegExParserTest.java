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

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.exists;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ES_07_08_5_RegExParserTest extends Assert {
	@Inject
	ParseHelper<Script> parseHelper;

	public Script parseAsBinaryOp(CharSequence js) {
		try {
			Script script = parseHelper.parse(js);
			assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
			assertFalse(exists(script.eAllContents(), it -> it instanceof RegularExpressionLiteral));
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	public Script parseWithRegEx(CharSequence js) {
		try {
			Script script = parseHelper.parse(js);
			assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
			assertTrue(script.eResource().getWarnings().toString(), script.eResource().getWarnings().isEmpty());
			assertTrue(exists(script.eAllContents(), it -> it instanceof RegularExpressionLiteral));
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	public Script parseWithInvalidRegEx(CharSequence js) {
		try {
			Script script = parseHelper.parse(js);
			assertTrue(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
			assertFalse(script.eResource().getWarnings().toString(), script.eResource().getWarnings().isEmpty());
			assertTrue(exists(script.eAllContents(), it -> it instanceof RegularExpressionLiteral));
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	public void failToParse(CharSequence js) {
		Script script;
		try {
			script = parseHelper.parse(js);
			assertFalse(script.eResource().getErrors().toString(), script.eResource().getErrors().isEmpty());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	public void hasChildren(Script script, int count) {
		assertEquals(count, script.getScriptElements().size());
	}

	@Test
	public void testRegExFirstToken() {
		parseWithRegEx("/1/");
	}

	@Test
	public void testRegExAfterComment_01() {
		parseWithRegEx("//\n/1/");
	}

	@Test
	public void testRegExAfterComment_02() {
		parseWithRegEx("/**//1/");
	}

	@Test
	public void testRegExAfterKeyword_01() {
		parseAsBinaryOp("null/a/g");
	}

	@Test
	public void testRegExAfterKeyword_02() {
		parseAsBinaryOp("true/a/g");
	}

	@Test
	public void testRegExAfterKeyword_03() {
		parseAsBinaryOp("false/a/g");
	}

	@Test
	public void testRegExAfterKeyword_04() {
		parseAsBinaryOp("this/a/g");
	}

	@Test
	public void testRegExAfterKeyword_05() {
		parseAsBinaryOp("from/as/get/set/g");
	}

	@Test
	public void testRegExAccess_01() {
		parseAsBinaryOp("a/b/g");
	}

	@Test
	public void testRegExAccess_02() {
		parseAsBinaryOp("a()/b/g");
	}

	@Test
	public void testRegExAccess_03() {
		parseAsBinaryOp("a[b]/c/g");
	}

	@Test
	public void testRegExAsArgument_01() {
		parseWithRegEx("a[/c/]");
	}

	@Test
	public void testRegExAsArgument_02() {
		parseWithRegEx("a(/c/)");
	}

	@Test
	public void testRegExAsArgument_03() {
		parseWithRegEx("a +/c/");
	}

	@Test
	public void testRegExAsArgument_04() {
		parseWithRegEx("var e=/^/g;");
	}

	@Test
	public void testBinaryOpInParen_01() {
		parseAsBinaryOp("({}/function(){return 1})");
	}

	@Test
	public void testBinaryOpWithoutParen() {
		// TODO improve error message;
		failToParse("{}/function(){return 1}");
	}

	@Test
	public void testRegExTwoBackspaces() {
		parseWithRegEx("/\\\\/");
	}

	@Test
	public void testTwoOpeningBrackets() {
		parseWithRegEx("/[[]/");
	}

	@Test
	public void testTwoClosingBrackets() {
		parseWithRegEx("/[]]/");
	}

	@Test
	public void testUnclosedBrace() {
		parseWithRegEx("/{/");
	}

	@Test
	public void testUnclosedBracket() {
		parseWithInvalidRegEx("/[/");
	}

	@Test
	public void testUnclosedParentheses() {
		parseWithInvalidRegEx("/(/");
	}

	@Test
	public void testObjectLiteral_01() {
		parseAsBinaryOp("({}/s)");
	}

	@Test
	public void testObjectLiteral_02() {
		parseAsBinaryOp("({})/s");
	}

	@Test
	public void tesEqualsSignInRegEx_01() {
		parseWithRegEx("/=1/g");
	}

	@Test
	public void tesEqualsSignInRegEx_02() {
		parseAsBinaryOp("i/=1/2");
	}
}
