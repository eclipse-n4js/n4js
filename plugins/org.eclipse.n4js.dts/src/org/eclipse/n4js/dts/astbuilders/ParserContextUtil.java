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
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.n4js.dts.TypeScriptParser;
import org.eclipse.n4js.dts.TypeScriptParser.BlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.StatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.StatementListContext;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

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

	/** @return the statements in the given block or an empty list if not available. */
	public static List<StatementContext> getStatements(BlockContext block) {
		if (block != null) {
			StatementListContext statementList = block.statementList();
			if (statementList != null) {
				List<StatementContext> statement = statementList.statement();
				if (statement != null) {
					return statement;
				}
			}
		}
		return Collections.emptyList();
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

	/** Installs proxy information that is later used for linking */
	public static void installProxy(LazyLinkingResource resource, EObject container, EReference eRef, EObject proxy,
			String crossRefStr) {

		int fragmentNumber = resource.addLazyProxyInformation(container, eRef, new PseudoLeafNode(crossRefStr));
		URI encodedLink = resource.getURI().appendFragment("|" + fragmentNumber);
		((InternalEObject) proxy).eSetProxyURI(encodedLink);
	}
}
