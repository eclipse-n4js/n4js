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
package org.eclipse.n4js.json.tests.validation;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.List;

import org.eclipse.n4js.json.JSONInjectorProvider;
import org.eclipse.n4js.json.JSONParseHelper;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.validation.JSONIssueCodes;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests that checks for the validation of comments in JSON resources.
 */
@RunWith(XtextRunner.class)
@InjectWith(JSONInjectorProvider.class)
@SuppressWarnings("javadoc")
public class JSONCommentValidationTest extends Assert {

	@Inject
	JSONParseHelper jsonParserHelper;

	@Inject
	ValidationTestHelper validationTestHelper;

	/**
	 * Tests that comments in JSON resources cause warnings
	 */
	@Test
	public void testCommentsValidation() throws Exception {
		assertIssues(
				"""
						//single line comment
						{
							"content": "json",
							"other": "key"
							/*
							   multi-line comment
							*/
						}""", """
						1 21 WARNING: %s
						5 29 WARNING: %s"""
						.formatted(JSONIssueCodes.JSON_COMMENT_UNSUPPORTED, JSONIssueCodes.JSON_COMMENT_UNSUPPORTED));
	}

	@Test
	public void testTrailingComment() throws Exception {
		assertIssues(
				"""
						{
							"a" : "stre"// trailing comment
						}
						""", "2 19 WARNING: JSON_COMMENT_UNSUPPORTED");
	}

	@Test
	public void testInlineComment() throws Exception {
		assertIssues(
				"[1, 2, /* comment */ 3, 4]",
				"1 13 WARNING: JSON_COMMENT_UNSUPPORTED");
	}

	@Test
	public void testCopyrightHeader() throws Exception {
		assertIssues(
				"""
						/*
						 * Copyright (c) 2016 NumberFour AG.
						 * All rights reserved. This program and the accompanying materials
						 * are made available under the terms of the Eclipse Public License v1.0
						 * which accompanies this distribution, and is available at
						 * http://www.eclipse.org/legal/epl-v10.html
						 *
						 * Contributors:
						 *   NumberFour AG - Initial API and implementation
						 */ {"a" : "key"}""",
				"1 361 WARNING: JSON_COMMENT_UNSUPPORTED");
	}

	/**
	 * Asserts that the given {@code jsonText} validated as JSON resources, yields a list of issues as described by
	 * {@code expectations} in the following format:
	 *
	 * <pre>
	 * <code>
	 * <LINE> <LENGTH> <SEVERITY>: <ISSUE CODE>
	 * </code>
	 * </pre>
	 *
	 * The issues must be listed in the order of appearance by issue offset.
	 */
	private void assertIssues(CharSequence jsonText, String expectations) throws Exception {
		JSONDocument document = jsonParserHelper.parse(jsonText);
		List<Issue> issues = validationTestHelper.validate(document);

		String issueDescription = Strings.join("\n", map(issues, (i) -> i.getLineNumber() + " "
				+ i.getLength() + " " + i.getSeverity().toString() + ": " + i.getCode()));

		assertEquals(expectations, issueDescription);
	}
}
