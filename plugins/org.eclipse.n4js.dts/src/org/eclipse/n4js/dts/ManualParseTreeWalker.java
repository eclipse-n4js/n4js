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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 *
 */
public class ManualParseTreeWalker {
	private final ParserRuleContext startCtx;
	private ParseTreeListener treeListener;
	private ArrayList<ParserRuleContext> currentQueue;

	public ManualParseTreeWalker(ParserRuleContext ctx) {
		this(null, ctx);
	}

	public ManualParseTreeWalker(ParseTreeListener treeListener, ParserRuleContext startCtx) {
		this.treeListener = treeListener;
		this.startCtx = startCtx;
	}

	public void setParseTreeListener(ParseTreeListener treeListener) {
		if (this.treeListener == null) {
			this.treeListener = treeListener;
		}
	}

	public void start() {
		visit(startCtx);
	}

	private void visit(ParserRuleContext ctx) {
		currentQueue = new ArrayList<>();
		enter(ctx);

		for (ParserRuleContext child : currentQueue) {
			visit(child);
		}
		// currentQueue set to null before calling exit to fail fast in case enqueue is called inside exit()
		currentQueue = null;

		exit(ctx);
	}

	private void enter(ParserRuleContext ctx) {
		treeListener.enterEveryRule(ctx);
		ctx.enterRule(treeListener);
	}

	private void exit(ParserRuleContext ctx) {
		ctx.exitRule(treeListener);
		treeListener.exitEveryRule(ctx);

	}

	public void enqueue(ParserRuleContext ctx) {
		if (ctx == null) {
			return;
		}
		currentQueue.add(ctx);
	}

	public void enqueue(List<? extends ParserRuleContext> ctxs) {
		if (ctxs == null) {
			return;
		}
		currentQueue.addAll(ctxs);
	}

}
