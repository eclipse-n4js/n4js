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

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.dts.TypeScriptParser.StatementContext;

import com.google.common.collect.ImmutableList;

/**
 * This adapter is installed on nested/virtual resources and holds the {@link TokenStream} and {@link RuleContext} that
 * belong to the nested resource this adapter is installed on.
 */
public class NestedResourceAdapter implements Adapter {

	/** Returns true iff a {@link NestedResourceAdapter} is installed on the given resource. */
	public static boolean isInstalled(Resource resource) {
		return get(resource) != null;
	}

	/** Returns the {@link NestedResourceAdapter} that is installed on the given resource. */
	public static NestedResourceAdapter get(Resource resource) {
		for (Adapter adapter : resource.eAdapters()) {
			if (adapter instanceof NestedResourceAdapter) {
				return (NestedResourceAdapter) adapter;
			}
		}
		return null;
	}

	/** Update the {@link NestedResourceAdapter} on the given resource */
	public static void update(Resource resource, NestedResourceAdapter nra) {
		NestedResourceAdapter adapter = get(resource);
		if (adapter != nra) {
			resource.eAdapters().remove(adapter);
			resource.eAdapters().add(nra);
		}
	}

	/** Returns the {@link NestedResourceAdapter} that is installed on the given resource or null. */
	public static NestedResourceAdapter remove(Resource resource) {
		NestedResourceAdapter adapter = get(resource);
		if (adapter != null) {
			resource.eAdapters().remove(adapter);
			return adapter;
		}
		return adapter;
	}

	private final String contents;
	private final DtsTokenStream tokenStream;
	private final ParserRuleContext ctx;
	private final List<StatementContext> statements;

	/**
	 * Constructor
	 *
	 * @param ctx
	 *            see {@link #getContext()}.
	 * @param statements
	 *            see {@link #getStatements()}.
	 */
	public NestedResourceAdapter(DtsTokenStream tokenStream, ParserRuleContext ctx,
			Iterable<StatementContext> statements, String contents) {

		this.contents = contents;
		this.tokenStream = tokenStream;
		this.ctx = ctx;
		this.statements = ImmutableList.copyOf(statements);
	}

	@Override
	public void notifyChanged(Notification notification) {
	}

	@Override
	public Notifier getTarget() {
		return null;
	}

	@Override
	public void setTarget(Notifier newTarget) {
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return false;
	}

	/** Returns the complete contents of the base resource. */
	public String getContents() {
		return contents;
	}

	/** Returns the {@link ParserRuleContext} that belongs to the resource this adapter is installed on. */
	public ParserRuleContext getContext() {
		return ctx;
	}

	/**
	 * Returns the {@link StatementContext statements} to be treated as the top-level elements of the nested virtual
	 * resource.
	 */
	public List<StatementContext> getStatements() {
		return statements;
	}

	/** Returns the token stream that belongs to the resource this adapter is installed on. */
	public DtsTokenStream getTokenStream() {
		return tokenStream;
	}

}
