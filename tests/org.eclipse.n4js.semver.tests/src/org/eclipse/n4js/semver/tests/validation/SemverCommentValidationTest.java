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
package org.eclipse.n4js.semver.tests.validation;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.List;

import org.eclipse.n4js.semver.SemverInjectorProvider;
import org.eclipse.n4js.semver.SemverParseHelper;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.validation.SemverIssueCodes;
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
 * Tests that checks for the validation of comments in SEMVER resources.
 */
@RunWith(XtextRunner.class)
@InjectWith(SemverInjectorProvider.class)
public class SemverCommentValidationTest extends Assert {

	@Inject
	SemverParseHelper semverParseHelper;
	@Inject
	ValidationTestHelper validationTestHelper;

	/** Test error of too many numbers */
	@Test
	public void testTooManyNumbers() throws Exception {
		assertIssues(
				"1.2.3.4", "1 1 ERROR: " + SemverIssueCodes.SEMVER_TOO_MANY_NUMBERS);
	}

	/** Test error of too many comparators */
	@Test
	public void testTooManyComparators1() throws Exception {
		assertIssues(
				"< > 1.2.3", "1 1 ERROR: " + SemverIssueCodes.SEMVER_TOO_MANY_COMPARATORS);
	}

	/** Test error of too many comparators */
	@Test
	public void testTooManyComparators2a() throws Exception {
		assertIssues(
				"< = 1.2.3", "1 1 ERROR: " + SemverIssueCodes.SEMVER_TOO_MANY_COMPARATORS);
	}

	/** Test error of too many comparators */
	@Test
	public void testTooManyComparators2b() throws Exception {
		assertIssues(
				"<= 1.2.3", "");
	}

	/** Test error of too many comparators */
	@Test
	public void testTooManyComparators2c() throws Exception {
		assertIssues(
				"=< 1.2.3", "1 1 ERROR: " + SemverIssueCodes.SEMVER_TOO_MANY_COMPARATORS);
	}

	/** Test error of too many comparators */
	@Test
	public void testTooManyComparators2d() throws Exception {
		assertIssues(
				"= < 1.2.3", "1 1 ERROR: " + SemverIssueCodes.SEMVER_TOO_MANY_COMPARATORS);
	}

	/** Test error of too many comparators */
	@Test
	public void testTooManyComparators3a() throws Exception {
		assertIssues(
				"> = 1.2.3", "1 1 ERROR: " + SemverIssueCodes.SEMVER_TOO_MANY_COMPARATORS);
	}

	/** Test error of too many comparators */
	@Test
	public void testTooManyComparators3b() throws Exception {
		assertIssues(
				">= 1.2.3", "");
	}

	/** Test error of too many comparators */
	@Test
	public void testTooManyComparators3c() throws Exception {
		assertIssues(
				"=> 1.2.3", "1 1 ERROR: " + SemverIssueCodes.SEMVER_TOO_MANY_COMPARATORS);
	}

	/** Test error of too many comparators */
	@Test
	public void testTooManyComparators3d() throws Exception {
		assertIssues(
				"= > 1.2.3", "1 1 ERROR: " + SemverIssueCodes.SEMVER_TOO_MANY_COMPARATORS);
	}

	/**
	 * Asserts that the given {@code SEMVERText} validated as SEMVER resources, yields a list of issues as described by
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
	private void assertIssues(CharSequence SEMVERText, String expectations) throws Exception {
		NPMVersionRequirement document = semverParseHelper.parse(SEMVERText);
		List<Issue> issues = validationTestHelper.validate(document);

		String issueDescription = Strings.join("\n", map(issues, (i) -> i.getLineNumber() + " "
				+ i.getLength() + " " + i.getSeverity().toString() + ": " + i.getCode()));

		assertEquals(expectations, issueDescription);
	}
}
