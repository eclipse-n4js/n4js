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
package org.eclipse.n4js.tests.scoping;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.issues.IssueUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class AT_136_AccessPredefinedTypesTest {

	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void test_01() throws Exception {
		Script script = parseHelper.parse("""
				var s: string = ''
				s.toLowerCase().toLowerCase()
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_02() throws Exception {
		Script script = parseHelper.parse("""
				var b: boolean = true
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_03() throws Exception {
		Script script = parseHelper.parse("""
				var d: Date
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_04() throws Exception {
		Script script = parseHelper.parse("""
				var u: undefined // may be a type error later
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_05() throws Exception {
		Script script = parseHelper.parse("""
				var n: number = 1
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_06() throws Exception {
		Script script = parseHelper.parse("""
				var s: String = ''
				s.toLowerCase().toLowerCase()
				""");
		assertErrors(script,
				"ERROR:\"\" is not a subtype of String. (__synthetic0.n4js line : 1 column : 17)");
	}

	@Test
	public void test_07() throws Exception {
		Script script = parseHelper.parse("""
				var b: Boolean = true
				""");
		assertErrors(script,
				"ERROR:true is not a subtype of Boolean. (__synthetic0.n4js line : 1 column : 18)");
	}

	@Test
	public void test_08() throws Exception {
		Script script = parseHelper.parse("""
				var r: RegExp = /abc/g
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_09() throws Exception {
		Script script = parseHelper.parse("""
				var n: Number = 1
				""");
		assertErrors(script,
				"ERROR:1 is not a subtype of Number. (__synthetic0.n4js line : 1 column : 17)");
	}

	private void assertErrors(EObject model, String... errorMsgs) {
		List<Issue> issues = valTestHelper.validate(model.eResource());
		Assert.assertEquals(toList(map(issues, issue -> IssueUtils.toString(issue))), Arrays.asList(errorMsgs));
	}
}
