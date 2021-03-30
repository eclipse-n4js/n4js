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
package org.eclipse.n4js.tests.scoping

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.Arrays
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.tests.issues.IssueUtils
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class AT_223_UseTypeLikeVarTest {

	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Test
	def void test_01() {
		val script = '''
			class A{}
			var a = new A();
		'''.parse
		script.assertNoErrors
	}
	@Test
	def void test_02() {
		val rs = resourceSetProvider.get
		'''
			export class A {}
		'''.parse(URI.createURI("A.js"), rs)
		val script = '''
			import { A } from "A"
			var a = new A();
		'''.parse(URI.createURI("B.js"), rs)
		script.assertNoErrors
	}
	@Test
	def void test_03() {
		val rs = resourceSetProvider.get
		'''
			export class A {}
		'''.parse(URI.createURI("A.js"), rs)
		val script = '''
			import { A } from "A"
			var a;
			if (a instanceof A) {
			}
		'''.parse(URI.createURI("B.js"), rs)
		script.assertNoErrors
	}
	@Test
	def void test_04() {
		val script = '''
			function foo() {};
			var a = foo;
			foo = "Hello";
			a();
		'''.parse
		script.assertErrors(
			"ERROR:string is not a subtype of {function():void}. (__synthetic0.n4js line : 3 column : 7)"
		)
	}

	def private void assertErrors(EObject model, String... errorMsgs) {
		val issues = model.eResource.validate;
		Assert.assertEquals(issues.map[IssueUtils.toString(it)], Arrays.asList(errorMsgs));
	}
}
