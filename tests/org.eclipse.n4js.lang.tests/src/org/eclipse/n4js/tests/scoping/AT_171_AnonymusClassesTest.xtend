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
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.validation.validators.UnsupportedFeatureValidator.*

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class AT_171_AnonymusClassesTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Test
	def void test_01() {
		val script = '''
			var a = class A {
				b = class B {
					bar(): any {
						var c = a.field1
						a.foo()
						return null;
					}
				};
				static field1: any;
				static foo(): any { return null; }
			}
		'''.parse
allowClassExpressions[
		script.assertNoErrors
]
	}

	@Test
	def void test_02() {
		val script = '''
			var a = class {
				b = class {
					bar(): any {
						var c = a.field1
						a.foo()
						return null;
					}
				};
				static field1: any;
				static foo(): any {return null;}
			}
		'''.parse
allowClassExpressions[
		script.assertNoErrors
]
	}

	@Test
	def void test_03() {
		val script = '''
			var a = class {
				field1: any;
				foo(): any {
					return this.field1;
				}
			}
		'''.parse
allowClassExpressions[
		script.assertNoErrors
]
	}

	@Test
	def void test_04() {
		val script = '''
			var a = class {
			    items_: any;
			    public indexOf(item: any): void {
			    	return _.indexOf(this.items_, item);
			    }
			}
			var _ = class _ {
				static indexOf(p1: any, p2: any): void {}
			}
		'''.parse
allowClassExpressions[
		script.assertNoErrors
]
	}

	@Test
	def void test_05() {
		val script = '''
			export var a = class {
			    items_: any;
			    public indexOf(item: any): void {
			    	return _.indexOf(this.items_, item);
			    }
			}
			class _ {
				static indexOf(p1: any, p2: any): void {}
			}
		'''.parse
allowClassExpressions[
		script.assertNoErrors
]
	}

}
