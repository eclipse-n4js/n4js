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
package org.eclipse.n4js.tests.parser

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.parser.LazyTokenStream
import org.eclipse.n4js.ui.editor.syntaxcoloring.HighlightingParser
import org.eclipse.n4js.ui.editor.syntaxcoloring.InternalHighlightingParser
import org.eclipse.n4js.ui.editor.syntaxcoloring.InternalN4JSParser
import org.antlr.runtime.RecognitionException
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test for checking if the highlighting parser can properly recover from mismatched tokens instead of throwing exceptions.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class AT_IDEBUG_362_NPE_RecoverFromMismatchedTokenTest {

	@Inject
	private HighlightingParser parser

	@Inject
	private ThrowingHighlightingParser throwingParser

	static class ThrowingHighlightingParser extends HighlightingParser {

		override InternalN4JSParser createParser(LazyTokenStream stream) {
			return new InternalHighlightingParser(stream, getGrammarAccess(), getRewriter()) {
				override reportError(RecognitionException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

	def void parseESWithErrors(CharSequence input) {
		parser.getTokens(input)
	}

	def void parseESSuccessfully(CharSequence input) {
		throwingParser.getTokens(input)
	}

	@Test
	def void testParserCanRecoverFromMismatchedTokenExpectNoNPE_Old() {
		'''class A {
			async foo () {
				var that = this;
				@o
				await that.callAll();
			}
			callAll(): any { return null; }
		}
		'''.parseESWithErrors
	}

	@Test
	def void testParserCanRecoverFromMismatchedTokenExpectNoNPE() {
		'''class A {
			async foo () {
				var that = this;
				@o
				await that.callAll();
			}
			callAll(): any { return null; }
		}
		'''.parseESWithErrors
	}

	@Test
	def void testRegExWithLookAhead() {
		'''
			(function(){
				for (var k in null) {}
			});
		'''.parseESSuccessfully
	}


}
