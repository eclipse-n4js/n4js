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
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * This parse tree walker is a variant of the {@link ParseTreeWalker} with the difference that it will not visit all
 * children per default. Instead, the listener has to select the children that have to be visited next. The reason is
 * that converting the parse tree to an AST is done by visiting elements driven by this walker but also by manually
 * picking children and their information to create and initialize AST elements.
 */
public class ManualParseTreeWalker {
	public final LazyLinkingResource resource;
	public final ParserRuleContext startCtx;
	
	private ParseTreeListener treeListener;
	private ArrayList<ParserRuleContext> currentQueue;

	/** Constructor. You must call {@link #setParseTreeListener(ParseTreeListener)}. */
	public ManualParseTreeWalker(ParserRuleContext ctx, LazyLinkingResource resource) {
		this(null, ctx, resource);
	}

	/** Constructor. */
	public ManualParseTreeWalker(ParseTreeListener treeListener, ParserRuleContext startCtx,
			LazyLinkingResource resource) {

		this.treeListener = treeListener;
		this.startCtx = startCtx;
		this.resource = resource;
	}

	/** Call this setter to initialize the listener. */
	public void setParseTreeListener(ParseTreeListener treeListener) {
		if (this.treeListener == null) {
			this.treeListener = treeListener;
		}
	}

	/** Starts the walker. Be sure to have the {@link #treeListener} set. */
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

	/** Adds the given context to be visited by this walker */
	public void enqueue(ParserRuleContext ctx) {
		if (ctx == null) {
			return;
		}
		currentQueue.add(ctx);
	}

	/** Adds all given contexts to be visited by this walker */
	public void enqueue(List<? extends ParserRuleContext> ctxs) {
		if (ctxs == null) {
			return;
		}
		currentQueue.addAll(ctxs);
	}

}
