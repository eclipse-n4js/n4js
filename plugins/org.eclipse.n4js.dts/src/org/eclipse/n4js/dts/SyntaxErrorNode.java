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

import org.antlr.v4.runtime.RecognitionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.BidiTreeIterable;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ITextRegionWithLineInformation;

/**
 *
 */
public class SyntaxErrorNode implements INode {
	final Object offendingSymbol;
	final int line;
	final int charPositionInLine;
	final String msg;
	final RecognitionException e;

	/** Constructor */
	public SyntaxErrorNode(Object offendingSymbol, int line, int charPositionInLine, String msg,
			RecognitionException e) {

		this.offendingSymbol = offendingSymbol;
		this.line = line;
		this.charPositionInLine = charPositionInLine;
		this.msg = msg;
		this.e = e;
	}

	@Override
	public ICompositeNode getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasSiblings() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasPreviousSibling() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasNextSibling() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public INode getPreviousSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INode getNextSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompositeNode getRootNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ILeafNode> getLeafNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalEndOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEndOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalStartLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStartLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalEndLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEndLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EObject getGrammarElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EObject getSemanticElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasDirectSemanticElement() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BidiTreeIterable<INode> getAsTreeIterable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITextRegion getTextRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITextRegion getTotalTextRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITextRegionWithLineInformation getTextRegionWithLineInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITextRegionWithLineInformation getTotalTextRegionWithLineInformation() {
		// TODO Auto-generated method stub
		return null;
	}

}
