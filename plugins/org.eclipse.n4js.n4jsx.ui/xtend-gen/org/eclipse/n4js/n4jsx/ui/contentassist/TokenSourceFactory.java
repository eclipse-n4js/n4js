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
package org.eclipse.n4js.n4jsx.ui.contentassist;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.antlr.runtime.TokenSource;
import org.eclipse.n4js.n4jsx.services.N4JSXGrammarAccess;
import org.eclipse.n4js.n4jsx.ui.contentassist.ContentAssistTokenTypeMapper;
import org.eclipse.n4js.n4jsx.ui.contentassist.NodeModelTokenSource;
import org.eclipse.xtext.nodemodel.INode;

/**
 * Facade that allows to deduce a TokenSource from a node model.
 */
@Singleton
@SuppressWarnings("all")
public class TokenSourceFactory {
  @Inject
  private ContentAssistTokenTypeMapper tokenTypeMapper;
  
  @Inject
  private N4JSXGrammarAccess grammarAccess;
  
  public TokenSource toTokenSource(final INode node) {
    return new NodeModelTokenSource(node, this.tokenTypeMapper, this.grammarAccess);
  }
  
  public TokenSource toTokenSource(final INode node, final int endOffset) {
    return new NodeModelTokenSource(node, 0, endOffset, this.tokenTypeMapper, this.grammarAccess);
  }
  
  public TokenSource toTokenSource(final INode node, final int startOffset, final int endOffset) {
    return new NodeModelTokenSource(node, startOffset, endOffset, this.tokenTypeMapper, this.grammarAccess);
  }
}
