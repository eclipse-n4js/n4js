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
package org.eclipse.n4js.n4jsx.ui.contentassist

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.n4js.n4jsx.services.N4JSXGrammarAccess
import org.antlr.runtime.TokenSource
import org.eclipse.xtext.nodemodel.INode

/**
 * Facade that allows to deduce a TokenSource from a node model.
 */
// copied from org.eclipse.n4js.ui.contentassist.TokenSourceFactory
@Singleton
class TokenSourceFactory {

	@Inject ContentAssistTokenTypeMapper tokenTypeMapper
	@Inject N4JSXGrammarAccess grammarAccess

	def TokenSource toTokenSource(INode node) {
		return new NodeModelTokenSource(node, tokenTypeMapper, grammarAccess)
	}

	def TokenSource toTokenSource(INode node, int endOffset) {
		return new NodeModelTokenSource(node, 0, endOffset, tokenTypeMapper, grammarAccess)
	}

	def TokenSource toTokenSource(INode node, int startOffset, int endOffset) {
		return new NodeModelTokenSource(node, startOffset, endOffset, tokenTypeMapper, grammarAccess)
	}
}
