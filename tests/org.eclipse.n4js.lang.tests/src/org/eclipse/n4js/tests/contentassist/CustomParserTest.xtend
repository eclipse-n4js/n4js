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
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistDataCollectors
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistTokenTypeMapper
import org.eclipse.n4js.ide.editor.contentassist.CustomN4JSParser
import org.eclipse.n4js.ide.editor.contentassist.ParamAwareEntryPointFinder
import org.eclipse.n4js.ide.editor.contentassist.TokenSourceFactory
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement
import org.eclipse.xtext.ide.editor.contentassist.antlr.RequiredRuleNameComputer
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions
import org.eclipse.xtext.xtext.RuleNames
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class CustomParserTest extends AbstractContentAssistParserTest implements Provider<IUnorderedGroupHelper> {

	@Inject IUnorderedGroupHelper unorderedGroupHelper
	@Inject TokenSourceFactory tokenSourceFactory
	@Inject ParamAwareEntryPointFinder entryPointFinder
	@Inject ContentAssistTokenTypeMapper tokenMapper
	@Inject RequiredRuleNameComputer ruleNameComputer
	@Inject RuleNames ruleNames

	@Inject extension ReflectExtensions

	def getParser() {
		return new CustomN4JSParser => [
			it.grammarAccess = grammarAccess
			it.nameMappings = nameMappings;
			it.tokenSourceFactory = tokenSourceFactory
			it.dataCollectors = new ContentAssistDataCollectors(null)
			it.entryPointFinder = entryPointFinder
			it.unorderedGroupHelper = this
			it.initializeTokenTypes(tokenMapper, grammarAccess)
			it.set('requiredRuleNameComputer', ruleNameComputer)
 			it.set('ruleNames', ruleNames)
 			it.initializeFor(ruleNames.allParserRules.head)
		]
	}

	override getFollowElements(INode node, int start, int end, boolean strict) {
		parser.getFollowElements(node, start, end, strict)
	}
	
	override getFollowElements(FollowElement prev) {
		if (prev.lookAhead == 1) {
			return #{};
		} else {
			return parser.getFollowElements(prev)	
		}
		
	}

	override get() {
		return unorderedGroupHelper
	}

}
