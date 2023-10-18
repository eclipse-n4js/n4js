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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.issues.IssueUtils;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class AT_223_UseTypeLikeVarTest {

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void test_01() throws Exception {
		Script script = parseHelper.parse("""
				class A{}
				var a = new A();
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_02() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		parseHelper.parse("""
				export class A {}
				""", URI.createURI("A.n4js"), rs);
		Script script = parseHelper.parse("""
				import { A } from "A";
				var a = new A();
				""", URI.createURI("B.n4js"), rs);
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_03() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		parseHelper.parse("""
				export class A {}
				""", URI.createURI("A.n4js"), rs);
		Script script = parseHelper.parse("""
				import { A } from "A";
				var a;
				if (a instanceof A) {
				}
				""", URI.createURI("B.n4js"), rs);
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_04() throws Exception {
		Script script = parseHelper.parse("""
				function foo() {};
				var a = foo;
				foo = "Hello";
				a();
				""");
		assertErrors(script,
				"ERROR:\"Hello\" is not a subtype of {function():void}. (__synthetic0.n4js line : 3 column : 7)");
	}

	private void assertErrors(EObject model, String... errorMsgs) {
		List<Issue> issues = valTestHelper.validate(model.eResource());
		Assert.assertEquals(toList(map(issues, issue -> IssueUtils.toString(issue))), Arrays.asList(errorMsgs));
	}
}
