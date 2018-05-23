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
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.diagnostics.Diagnostic
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_224_ReferenceFunctionExpressionByTheirNameFromInsideTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Test
	def void test_01() {
		val script = '''
			var a = function foo() {
			  foo();
			};
		'''.parse
		script.assertNoErrors
	}
	@Test
	def void test_02() {
		val script = '''
			var a = function foo() {
			};
			foo();
		'''.parse
		script.assertError(N4JSPackage.Literals.IDENTIFIER_REF, Diagnostic.LINKING_DIAGNOSTIC, 'foo')
	}
	@Test
	def void test_03() {
		val script = '''
			var a = function foo( x ) {
			    var b = function bar( y ) {
			        if (y==1) {
			            foo(2);
			            bar(0);
			        }
			    }
			    if (x==1) {
			        b(1);
			    }
			};
			a(1);
		'''.parse
		script.assertNoErrors
	}
}
