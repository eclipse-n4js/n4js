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
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Utilities to retrieve information from the parse tree
 */
public class ParserContextUtil {

	/** @return true iff the given rule is contained in an {@link TypeScriptParser#RULE_exportStatement} */
	public static boolean isExported(ParserRuleContext ctx) {
		ParserRuleContext exportedParentCtx = findParentContext(ctx, TypeScriptParser.RULE_exportStatement,
				TypeScriptParser.RULE_statement);
		return exportedParentCtx != null;
	}

	/**
	 * Traverses the parent relation upwards from the given context. In case a parent matches the given rule id, it is
	 * returned. In case a parent's rule id matches the given stopAtIds, null is returned. The given start context is
	 * not checked.
	 *
	 * @return parent context with the given id or null.
	 */
	public static ParserRuleContext findParentContext(ParserRuleContext ctx, int parentContextId,
			Integer... stopAtIds) {
		return findParentContext(ctx, parentContextId, false, stopAtIds);
	}

	/**
	 * Traverses the parent relation upwards from the given context. In case a parent matches the given rule id, it is
	 * returned. In case a parent's rule id matches the given stopAtIds, null is returned. The given start context is
	 * checked iff checkStart is true.
	 *
	 * @return parent context with the given id or null.
	 */
	public static ParserRuleContext findParentContext(ParserRuleContext ctx, int parentContextId, boolean checkStart,
			Integer... stopAtIds) {

		if (checkStart && ctx.getRuleIndex() == parentContextId) {
			return ctx;
		}
		Set<Integer> stopAtIdsSet = stopAtIds == null ? Collections.emptySet() : Set.of(stopAtIds);

		while (ctx.parent != null) {
			if (ctx.getRuleIndex() == parentContextId) {
				return ctx;
			}
			if (stopAtIdsSet.contains(ctx.getRuleIndex())) {
				return null;
			}
			ctx = (ParserRuleContext) ctx.parent;
		}
		return null;
	}

	/** @return the quoted string. Null safe. */
	public static String trimStringLiteral(TerminalNode stringLiteral) {
		if (stringLiteral == null || stringLiteral.getText() == null || stringLiteral.getText().length() < 2) {
			return "";
		}
		String str = stringLiteral.getText();
		return str.substring(1, str.length() - 1);
	}
}
