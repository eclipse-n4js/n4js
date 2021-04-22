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
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.xtext.resource.XtextResourceSet
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_174_StructuralTypesTest {

	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Test
	def void test_01() {
		val script = '''
			function f(p: ~Object with {a: string; b: string;}) {
				var s: string;
				s = p.a;
				s = p.b;
			}
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_02() {
		val script = '''
			var r: ~Object with {a: string; b: string;};
			var s: string;
			s = r.a;
			s = r.b;
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_03() {
		val script = '''
			export var r: ~Object with {a: string; b: string;};
			var s: string;
			s = r.a;
			s = r.b;
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void test_04() {
		val rs = resourceSetProvider.get
		'''
			export var r: ~Object with {a: string; b: string;};
		'''.parse(URI.createURI("A.n4js"), rs)
		val script = '''
			import {r} from "A"
			var s: string;
			s = r.a;
			s = r.b;
		'''.parse(URI.createURI("B.n4js"), rs)
		script.assertNoErrors
	}

}
