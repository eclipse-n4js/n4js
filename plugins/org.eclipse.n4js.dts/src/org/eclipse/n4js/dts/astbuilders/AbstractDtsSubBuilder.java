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
package org.eclipse.n4js.dts.astbuilders;

import java.util.Collections;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.dts.DtsParseTreeNodeInfo;
import org.eclipse.n4js.dts.ManualParseTreeWalker;
import org.eclipse.n4js.dts.TypeScriptParserBaseListener;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class AbstractDtsSubBuilder<T extends ParserRuleContext, R>
		extends TypeScriptParserBaseListener {

	protected final LazyLinkingResource resource;
	protected final Set<Integer> VISIT_CHILDREN_OF_RULES = getVisitChildrenOfRules();

	protected ManualParseTreeWalker walker;
	protected R result = getDefaultResult();

	/** Constructor */
	public AbstractDtsSubBuilder(LazyLinkingResource resource) {
		this.resource = resource;
	}

	protected Set<Integer> getVisitChildrenOfRules() {
		return Collections.emptySet();
	}

	protected R getDefaultResult() {
		return null;
	}

	/** Consumes the given context and all its children. */
	public R consume(T ctx) {
		if (ctx == null) {
			return result;
		}

		walker = new ManualParseTreeWalker(this, ctx, resource);
		walker.start();

		if (result instanceof EObject) {
			addLocationInfo((EObject) result, ctx);
		}

		try {
			return result;
		} finally {
			// reset for fail fast
			result = getDefaultResult();
		}
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		if (VISIT_CHILDREN_OF_RULES.contains(ctx.getRuleIndex())) {
			for (ParseTree pt : ctx.children) {
				if (pt instanceof RuleNode) {
					RuleNode rn = (RuleNode) pt;
					walker.enqueue((ParserRuleContext) rn.getRuleContext());
				}
			}
		}
	}

	/**  */
	protected void addLocationInfo(EObject obj, ParserRuleContext ctx) {
		obj.eAdapters().add(new DtsParseTreeNodeInfo(ctx));
	}
}
