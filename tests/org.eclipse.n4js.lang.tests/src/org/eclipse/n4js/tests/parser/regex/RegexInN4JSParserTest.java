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
package org.eclipse.n4js.tests.parser.regex;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.RegularExpressionLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.regex.tests.AbstractRegexParserTest;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class RegexInN4JSParserTest extends AbstractRegexParserTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Override
	public void assertValid(CharSequence expression) {
		Script parsed;
		try {
			parsed = parseHelper.parse(expression);
			List<Diagnostic> errors = parsed.eResource().getErrors();
			assertTrue(errors.toString(), errors.isEmpty());
			ExpressionStatement expressionStatement = (ExpressionStatement) parsed.getScriptElements().get(0);
			RegularExpressionLiteral regEx = (RegularExpressionLiteral) expressionStatement.getExpression();
			assertEquals(expression.toString().trim(), regEx.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testRegExCaptureGroupWarning_01() throws Exception {
		Script script = parseHelper.parse("/(?<abc>)/");
		List<Diagnostic> warnings = script.eResource().getWarnings();
		assertEquals(1, warnings.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) warnings.get(0);
		assertEquals(IssueCodes.getMessageForVCO_REGEX_NAMED_GROUP(), error.getMessage());
		assertEquals(3, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(4, error.getOffset());
	}

	@Test
	public void testRegExCaptureGroupWarning_02() throws Exception {
		Script script = parseHelper.parse("/(?<aa>)(?<bb>)/");
		List<Diagnostic> warnings = script.eResource().getWarnings();
		assertEquals(2, warnings.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) IterableExtensions.last(warnings);
		assertEquals(IssueCodes.getMessageForVCO_REGEX_NAMED_GROUP(), error.getMessage());
		assertEquals(2, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(11, error.getOffset());
	}

	@Test
	public void testRegExCaptureGroupWarning_03() throws Exception {
		Script script = parseHelper.parse("/abc(def/");
		List<Diagnostic> warnings = script.eResource().getWarnings();
		assertEquals(1, warnings.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) warnings.get(0);
		assertEquals("missing ')' at '/'", error.getMessage());
		assertEquals(1, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(8, error.getOffset());
	}

	@Test
	public void testRegExCaptureGroupWarning_04() throws Exception {
		Script script = parseHelper.parse("\n  /(?<abcef>)/");
		List<Diagnostic> warnings = script.eResource().getWarnings();
		assertEquals(1, warnings.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) warnings.get(0);
		assertEquals(IssueCodes.getMessageForVCO_REGEX_NAMED_GROUP(), error.getMessage());
		assertEquals(5, error.getLength());
		assertEquals(2, error.getLine());
		assertEquals(7, error.getOffset());
	}

	@Test
	public void testRegExCaptureGroupWarningAfterEscapeSequence_01() throws Exception {
		Script script = parseHelper.parse("  /\\u1234(?<abce>)/");
		List<Diagnostic> warnings = script.eResource().getWarnings();
		assertEquals(1, warnings.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) warnings.get(0);
		assertEquals(IssueCodes.getMessageForVCO_REGEX_NAMED_GROUP(), error.getMessage());
		assertEquals(4, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(12, error.getOffset());
	}

	@Test
	public void testRegExCaptureGroupWarningAfterEscapeSequence_02() throws Exception {
		Script script = parseHelper.parse("  /\\u{1234}(?<abce>)/");
		List<Diagnostic> warnings = script.eResource().getWarnings();
		assertEquals(1, warnings.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) warnings.get(0);
		assertEquals(IssueCodes.getMessageForVCO_REGEX_NAMED_GROUP(), error.getMessage());
		assertEquals(4, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(14, error.getOffset());
	}

	@Test
	public void testRegExCaptureGroupWarningAfterEscapeSequence_03() throws Exception {
		Script script = parseHelper.parse("  /\\u{1234}(?<abce>)\\u{1234} /");
		List<Diagnostic> warnings = script.eResource().getWarnings();
		assertEquals(1, warnings.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) warnings.get(0);
		assertEquals(IssueCodes.getMessageForVCO_REGEX_NAMED_GROUP(), error.getMessage());
		assertEquals(4, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(14, error.getOffset());
	}

	@Test
	public void testRegExCaptureGroupWarningAfterEscapeSequence_04() throws Exception {
		Script script = parseHelper.parse("  /\\u{1234} \\u{1234}(?<abce>)\\u{1234} /");
		List<Diagnostic> warnings = script.eResource().getWarnings();
		assertEquals(1, warnings.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) warnings.get(0);
		assertEquals(IssueCodes.getMessageForVCO_REGEX_NAMED_GROUP(), error.getMessage());
		assertEquals(4, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(23, error.getOffset());
	}

	@Test
	public void testRegExIllegalEscapeSequence_01() throws Exception {
		Script script = parseHelper.parse("  /\\u1/");
		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(1, errors.size());
		XtextSyntaxDiagnostic error = (XtextSyntaxDiagnostic) errors.get(0);
		assertEquals(IssueCodes.getMessageForVCO_REGEX_ILLEGAL_ESCAPE("/\\u1/"), error.getMessage());
		assertEquals(1, error.getLength());
		assertEquals(1, error.getLine());
		assertEquals(4, error.getOffset());
	}
}
