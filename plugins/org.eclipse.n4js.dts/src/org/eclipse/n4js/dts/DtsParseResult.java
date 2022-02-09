/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.dts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;

/**
 *
 */
public class DtsParseResult implements IParseResult {
	EObject rootElement;
	ICompositeNode rootNode;
	Iterable<INode> syntaxErrors;

	/** Constructor */
	@SuppressWarnings("unchecked")
	public DtsParseResult(EObject rootElement, ICompositeNode rootNode, Iterable<? extends INode> syntaxErrors) {
		super();
		this.rootElement = rootElement;
		this.rootNode = rootNode;
		this.syntaxErrors = (Iterable<INode>) syntaxErrors;
	}

	@Override
	public EObject getRootASTElement() {
		return rootElement;
	}

	@Override
	public ICompositeNode getRootNode() {
		return rootNode;
	}

	@Override
	public Iterable<INode> getSyntaxErrors() {
		return syntaxErrors;
	}

	@Override
	public boolean hasSyntaxErrors() {
		return getSyntaxErrors().iterator().hasNext();
	}

}
