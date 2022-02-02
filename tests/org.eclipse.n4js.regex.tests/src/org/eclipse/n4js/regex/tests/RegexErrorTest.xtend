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
package org.eclipse.n4js.regex.tests

import com.google.inject.Inject
import org.eclipse.n4js.regex.RegularExpressionInjectorProvider
import org.eclipse.n4js.regex.regularExpression.RegularExpressionLiteral
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(RegularExpressionInjectorProvider)
class RegexErrorTest extends AbstractRegexErrorTest {

	@Inject extension ParseHelper<RegularExpressionLiteral>

	override assertInvalid(CharSequence expression) {
		val parsed = expression.parse
		val errors = parsed.eResource.errors
		assertFalse(errors.toString, errors.isEmpty)
	}

	@Test
	def void test_05() {
		'''/a\/'''.assertInvalid
	}

	@Test
	def void test_06() {
		'''/\'''.assertInvalid
	}

	@Test
	def void test_07() {
		'''/*/'''.assertInvalid
	}

	@Test
	def void test_08() {
		'''///'''.assertInvalid
	}

	@Test
	@Ignore("Validation? --> Value Converter RegExLiteralConverter of *.ide.n4js does the Job.")
	def void test_09() {
		'''/
/'''.assertInvalid
	}
}
