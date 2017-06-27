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
package org.eclipse.n4js.tests.highlighting

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.editor.syntaxcoloring.HighlightingParserTester
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class HighlightingErrorTest {

	@Inject
	private extension HighlightingParserTester highlightingParser;

	@Test
	def void testSmokeTestFailure_01() {
		'var ~Object with {string a; string b;} r'.tokens
	}

	@Test
	def void testSmokeTestFailure_02() {
		'var v = { a: (b = "".(/^a/)), c: null };'.tokens
	}

	@Test
	def void testSmokeTestFailure_03() {
		'"".(/^a/);'.tokens
	}

	@Test
	def void testSmokeTestFailure_04() {
		'(/^a/)'.tokens
	}

}
