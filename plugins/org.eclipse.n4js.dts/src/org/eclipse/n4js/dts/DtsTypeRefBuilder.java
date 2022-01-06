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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_arrayTypeExpression;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_conditionalTypeRef;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_intersectionTypeExpression;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_operatorTypeRef;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_primaryTypeExpression;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeRef;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeRefWithModifiers;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_unionTypeExpression;

import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.eclipse.n4js.dts.TypeScriptParser.ColonSepTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.ParameterizedTypeRefContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeRefContext;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;

/**
 *
 */
public class DtsTypeRefBuilder extends TypeScriptParserBaseListener {
	final static Set<Integer> VISIT_CHILDREN_OF_RULES = java.util.Set.of(
			RULE_typeRef, RULE_conditionalTypeRef,
			RULE_unionTypeExpression,
			RULE_intersectionTypeExpression,
			RULE_operatorTypeRef,
			RULE_arrayTypeExpression,
			RULE_primaryTypeExpression,
			RULE_typeRefWithModifiers);

	private ManualParseTreeWalker walker;
	private TypeReferenceNode<TypeRef> resultTypeRefNode;

	public TypeReferenceNode<TypeRef> consume(ColonSepTypeRefContext ctx) {
		if (ctx == null) {
			return null;
		}
		return consume(ctx.typeRef());
	}

	public TypeReferenceNode<TypeRef> consume(TypeRefContext ctx) {
		if (ctx == null) {
			return null;
		}

		walker = new ManualParseTreeWalker(this, ctx);
		walker.start();
		return resultTypeRefNode;
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

	@Override
	public void enterParameterizedTypeRef(ParameterizedTypeRefContext ctx) {
		ParameterizedTypeRef pTypeRef = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
		pTypeRef.setDeclaredTypeAsText(ctx.typeName().getText());
		resultTypeRefNode = N4JSFactory.eINSTANCE.createTypeReferenceNode();
		resultTypeRefNode.setTypeRefInAST(pTypeRef);
	}

}
