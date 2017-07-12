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
package org.eclipse.n4js.tests.parser.regex

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.regex.tests.AbstractErrorTests
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.runner.RunWith
import org.eclipse.n4js.n4JS.Script
import org.junit.Test

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class ErrorTest extends AbstractErrorTests {

	@Inject extension ParseHelper<Script>

	override assertInvalid(CharSequence expression) {
		val parsed = expression.parse
		val errors = parsed.eResource.errors
		assertTrue(errors.toString, errors.isEmpty)
	}

	@Test
	def void testSmoke_01() {
		val parsed = '''/[^\/]+/\'''.parse
		val errors = parsed.eResource.errors
		assertFalse(errors.toString, errors.isEmpty)
	}

}
