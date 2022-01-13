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

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.xtext.nodemodel.BidiIterable;
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
public class RootNode implements ICompositeNode {
	final ProgramContext program;

	/** Constructor */
	public RootNode(ProgramContext program) {
		this.program = program;
	}

	@Override
	public ICompositeNode getParent() {
		return null;
	}

	@Override
	public boolean hasSiblings() {
		return false;
	}

	@Override
	public boolean hasPreviousSibling() {
		return false;
	}

	@Override
	public boolean hasNextSibling() {
		return false;
	}

	@Override
	public INode getPreviousSibling() {
		return null;
	}

	@Override
	public INode getNextSibling() {
		return null;
	}

	@Override
	public ICompositeNode getRootNode() {
		return this;
	}

	@Override
	public Iterable<ILeafNode> getLeafNodes() {
		return Collections.emptyList();
	}

	@Override
	public int getTotalOffset() {
		return 0;
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public int getTotalLength() {
		return 0;
	}

	@Override
	public int getLength() {
		return 0;
	}

	@Override
	public int getTotalEndOffset() {
		return 0;
	}

	@Override
	public int getEndOffset() {
		return 0;
	}

	@Override
	public int getTotalStartLine() {
		return 0;
	}

	@Override
	public int getStartLine() {
		return 0;
	}

	@Override
	public int getTotalEndLine() {
		return 0;
	}

	@Override
	public int getEndLine() {
		return 0;
	}

	@Override
	public String getText() {
		return "";
	}

	@Override
	public EObject getGrammarElement() {
		return null;
	}

	@Override
	public EObject getSemanticElement() {
		return null;
	}

	@Override
	public boolean hasDirectSemanticElement() {
		return false;
	}

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage() {
		return null;
	}

	@Override
	public BidiTreeIterable<INode> getAsTreeIterable() {
		return null;
	}

	@Override
	public ITextRegion getTextRegion() {
		return null;
	}

	@Override
	public ITextRegion getTotalTextRegion() {
		return null;
	}

	@Override
	public ITextRegionWithLineInformation getTextRegionWithLineInformation() {
		return null;
	}

	@Override
	public ITextRegionWithLineInformation getTotalTextRegionWithLineInformation() {
		return null;
	}

	@Override
	public BidiIterable<INode> getChildren() {
		return null;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public INode getFirstChild() {
		return null;
	}

	@Override
	public INode getLastChild() {
		return null;
	}

	@Override
	public int getLookAhead() {
		return 0;
	}

}
