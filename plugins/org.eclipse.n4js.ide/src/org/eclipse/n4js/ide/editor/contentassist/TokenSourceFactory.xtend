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
package org.eclipse.n4js.ide.editor.contentassist

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.antlr.runtime.TokenSource
import org.eclipse.xtext.nodemodel.INode

/**
 * Facade that allows to deduce a TokenSource from a node model.
 */
@Singleton
class TokenSourceFactory {

	@Inject ContentAssistTokenTypeMapper tokenTypeMapper
	@Inject N4JSGrammarAccess grammarAccess

	def TokenSource toTokenSource(INode node, boolean filter) {
		return new NodeModelTokenSource(node, tokenTypeMapper, grammarAccess, filter)
	}

	def TokenSource toTokenSource(INode node, int endOffset, boolean filter) {
		return new NodeModelTokenSource(node, 0, endOffset, tokenTypeMapper, grammarAccess, filter)
	}

	def TokenSource toTokenSource(INode node, int startOffset, int endOffset, boolean filter) {
		return new NodeModelTokenSource(node, startOffset, endOffset, tokenTypeMapper, grammarAccess, filter)
	}
}
