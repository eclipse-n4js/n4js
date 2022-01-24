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
package org.eclipse.n4js.regex.smoke.tests

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.analysis.SmokeTester
import org.eclipse.n4js.regex.tests.AbstractRegexParserTest
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith

/**
 */
@InjectWith(N4JSInjectorProvider)
@RunWith(XtextRunner)
class RegexInN4JSSmokeTest extends AbstractRegexParserTest {

	@Inject extension SmokeTester

	override assertValid(CharSequence expression) {
		expression.assertNoException
	}

}
