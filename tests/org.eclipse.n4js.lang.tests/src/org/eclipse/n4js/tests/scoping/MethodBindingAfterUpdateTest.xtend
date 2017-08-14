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
import org.eclipse.xtext.resource.XtextResource

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class MethodBindingAfterUpdateTest {

	@Inject extension ParseHelper<Script>
	@Inject extension ValidationTestHelper

	@Test
	def void testPlain() {
		val script = '''
			class C {
			   getString(): void {
			    	var s: string = ''
			    	s.slice(0, 10)
			   }
			}
		'''.parse
		script.assertNoErrors
	}

	@Test
	def void testEditAndResolve() {
		val script = '''
			class C {
			   getString(): void {
			    	var s: string = ''
			    	s.slice(0, 10)
			   }
			}
		'''.parse()
		val resource = script.eResource as XtextResource
		resource.update(0, 0, ' ')
		resource.contents.head.assertNoErrors
	}

	@Test
	def void testResolveEditAndResolveAgain() {
		val script = '''
			class C {
			   getString(): void {
			    	var s: string = ''
			    	s.slice(0, 10)
			   }
			}
		'''.parse()
		script.assertNoErrors
		val resource = script.eResource as XtextResource
		resource.update(0, 0, ' ')
		resource.contents.head.assertNoErrors
	}
}
