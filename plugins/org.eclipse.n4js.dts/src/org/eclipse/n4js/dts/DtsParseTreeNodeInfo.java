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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ITextRegionWithLineInformation;

/**
 * Provides information from the parse tree and token stream.
 */
public class DtsParseTreeNodeInfo implements Adapter, ITextRegionWithLineInformation {
	final DtsTokenStream tokenStream;
	final ParserRuleContext ctx;

	private EObject semanticElement;

	/** Constructor */
	public DtsParseTreeNodeInfo(DtsTokenStream tokenStream, ParserRuleContext ctx) {
		this.tokenStream = tokenStream;
		this.ctx = ctx;
	}

	/** @return true iff there exists jsdoc */
	public boolean hasJsDoc() {
		return getJsDoc() != null;
	}

	/** @return the jsdoc or null */
	public String getJsDoc() {
		Token jsDocToken = tokenStream.getPreviousJsDocToken(ctx.start);
		if (jsDocToken == null) {
			return null;
		}
		String jsDoc = jsDocToken.getText();
		if (jsDoc != null && jsDoc.startsWith("/***")) {
			jsDoc = jsDoc.substring(3); // fix this pattern since this is supported in TS but not in N4JS
		}
		return jsDoc;
	}

	@Override
	public void notifyChanged(Notification notification) {
		// ignore
	}

	@Override
	public Notifier getTarget() {
		return semanticElement;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		if (newTarget == null || newTarget instanceof EObject)
			semanticElement = (EObject) newTarget;
		else
			throw new IllegalArgumentException("Notifier must be an EObject");
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type instanceof Class<?> && INode.class.isAssignableFrom((Class<?>) type);
	}

	@Override
	public int getOffset() {
		return ctx.start.getStartIndex();
	}

	@Override
	public int getLength() {
		return ctx.stop.getStopIndex() - getOffset();
	}

	@Override
	public int getLineNumber() {
		return ctx.start.getLine() - 1; // we need 0-based line numbers
	}

	@Override
	public int getEndLineNumber() {
		return ctx.stop.getLine() - 1; // we need 0-based line numbers
	}

	@Override
	public boolean contains(ITextRegion other) {
		return false;
	}

	@Override
	public boolean contains(int offset) {
		return false;
	}

	@Override
	public ITextRegion merge(ITextRegion region) {
		return null;
	}

	@Override
	public ITextRegionWithLineInformation merge(ITextRegionWithLineInformation other) {
		return null;
	}

	/** @return the character position in the start line */
	public int getCharacter() {
		return ctx.start.getCharPositionInLine();
	}

	/** @return the character position in the end line */
	public int getEndCharacter() {
		return ctx.stop.getCharPositionInLine();
	}
}
