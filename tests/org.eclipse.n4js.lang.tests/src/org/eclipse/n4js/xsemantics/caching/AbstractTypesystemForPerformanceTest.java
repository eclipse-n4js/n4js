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
package org.eclipse.n4js.xsemantics.caching;

import java.util.List;

import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.validation.JavaScriptVariant;
import org.eclipse.n4js.xsemantics.AbstractTypesystemTest;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

/**
 * Reusable methods for testing performance with and without caching
 */
abstract class AbstractTypesystemForPerformanceTest extends AbstractTypesystemTest {

	@Inject
	ValidationTestHelper valTestHelper;

	protected void assertValidate(CharSequence scriptInput, int expectedIssues) {
		Script script = createScript(JavaScriptVariant.n4js, scriptInput.toString());
		List<Issue> issues = valTestHelper.validate(script);
		if (expectedIssues >= 0) {
			assertIssueCount(expectedIssues, issues);
		}
	}
}
