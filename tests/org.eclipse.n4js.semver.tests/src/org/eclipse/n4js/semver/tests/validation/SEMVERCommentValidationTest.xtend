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
package org.eclipse.n4js.semver.tests.validation

import com.google.inject.Inject
import org.eclipse.n4js.semver.SEMVERInjectorProvider
import org.eclipse.n4js.semver.SEMVERParseHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.eclipse.n4js.semver.validation.SEMVERIssueCodes

/**
 * Tests that checks for the validation of comments in SEMVER resources.
 */
@RunWith(XtextRunner)
@InjectWith(SEMVERInjectorProvider)
class SEMVERCommentValidationTest extends Assert {

	@Inject extension SEMVERParseHelper
	@Inject extension ValidationTestHelper

	/** Test error of too many numbers */
	@Test
	def void testTooManyNumbers() {
		'''1.2.3.4'''.assertIssues('''1 1 ERROR: «SEMVERIssueCodes.SEMVER_TOO_MANY_NUMBERS»''');
	}

	/** Test error of too many comparators */
	@Test
	def void testTooManyComparators1() {
		'''< > 1.2.3'''.assertIssues('''1 1 ERROR: «SEMVERIssueCodes.SEMVER_TOO_MANY_COMPARATORS»''');
	}

	/** Test error of too many comparators */
	@Test
	def void testTooManyComparators2a() {
		'''< = 1.2.3'''.assertIssues('''1 1 ERROR: «SEMVERIssueCodes.SEMVER_TOO_MANY_COMPARATORS»''');
	}

	/** Test error of too many comparators */
	@Test
	def void testTooManyComparators2b() {
		'''<= 1.2.3'''.assertIssues("");
	}

	/** Test error of too many comparators */
	@Test
	def void testTooManyComparators2c() {
		'''=< 1.2.3'''.assertIssues('''1 1 ERROR: «SEMVERIssueCodes.SEMVER_TOO_MANY_COMPARATORS»''');
	}

	/** Test error of too many comparators */
	@Test
	def void testTooManyComparators2d() {
		'''= < 1.2.3'''.assertIssues('''1 1 ERROR: «SEMVERIssueCodes.SEMVER_TOO_MANY_COMPARATORS»''');
	}

	/** Test error of too many comparators */
	@Test
	def void testTooManyComparators3a() {
		'''> = 1.2.3'''.assertIssues('''1 1 ERROR: «SEMVERIssueCodes.SEMVER_TOO_MANY_COMPARATORS»''');
	}

	/** Test error of too many comparators */
	@Test
	def void testTooManyComparators3b() {
		'''>= 1.2.3'''.assertIssues("");
	}

	/** Test error of too many comparators */
	@Test
	def void testTooManyComparators3c() {
		'''=> 1.2.3'''.assertIssues('''1 1 ERROR: «SEMVERIssueCodes.SEMVER_TOO_MANY_COMPARATORS»''');
	}

	/** Test error of too many comparators */
	@Test
	def void testTooManyComparators3d() {
		'''= > 1.2.3'''.assertIssues('''1 1 ERROR: «SEMVERIssueCodes.SEMVER_TOO_MANY_COMPARATORS»''');
	}

	/**
	 * Asserts that the given {@code SEMVERText} validated as SEMVER resources,
	 * yields a list of issues as described by {@code expectations} in the following format:
	 *
	 * <pre><code>
	 * <LINE> <LENGTH> <SEVERITY>: <ISSUE CODE>
	 * </code></pre>
	 *
	 * The issues must be listed in the order of appearance by issue offset.
	 */
	private def void assertIssues(CharSequence SEMVERText, String expectations) {
		val document = SEMVERText.parse;
		val issues = validate(document);
		val issueDescription = issues
			.map[issue | issue.lineNumber + " " + issue.length + " " + issue.severity.toString + ": " + issue.code]
			.join("\n");
		assertEquals(expectations, issueDescription);
	}
}
