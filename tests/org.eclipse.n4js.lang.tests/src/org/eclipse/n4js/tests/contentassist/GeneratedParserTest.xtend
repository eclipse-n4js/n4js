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
package org.eclipse.n4js.tests.contentassist

import com.google.inject.Inject
import com.google.inject.Provider
import org.antlr.runtime.CharStream
import org.eclipse.n4js.ide.contentassist.antlr.N4JSParser
import org.eclipse.n4js.ide.contentassist.antlr.lexer.InternalN4JSLexer
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement
import org.eclipse.xtext.ide.editor.contentassist.antlr.RequiredRuleNameComputer
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions
import org.eclipse.xtext.xtext.RuleNames
import org.junit.Ignore

/**
 * Used to document bogus behavior of the default parser
 */
@Ignore
class GeneratedParserTest extends AbstractContentAssistParserTest implements Provider<IUnorderedGroupHelper> {

	@Inject IUnorderedGroupHelper unorderedGroupHelper
	@Inject RequiredRuleNameComputer ruleNameComputer
	@Inject RuleNames ruleNames

	@Inject extension ReflectExtensions

	def getParser() {
		return new TestableN4JSParser => [
			it.grammarAccess = grammarAccess
			it.nameMappings = nameMappings;
			it.unorderedGroupHelper = this
			it.set('requiredRuleNameComputer', ruleNameComputer)
 			it.set('ruleNames', ruleNames)
 			it.initializeFor(ruleNames.allParserRules.head)
		]
	}

	override getFollowElements(INode node, int start, int end, boolean strict) {
		val s = node.text.substring(start, end)
		parser.getFollowElements(s, strict)
	}
	
	override getFollowElements(FollowElement prev) {
		parser.getFollowElements(prev)
	}

	override get() {
		return unorderedGroupHelper
	}
}

class TestableN4JSParser extends N4JSParser {

	override protected createLexer(CharStream stream) {
		new InternalN4JSLexer => [
			it.setCharStream(stream)
		]
	}
	
}
