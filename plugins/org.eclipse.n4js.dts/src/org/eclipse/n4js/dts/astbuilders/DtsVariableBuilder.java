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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_arrayLiteral;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_elementList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_objectLiteral;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_variableDeclarationList;

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.BindingPatternBlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableStatementContext;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link TypeReferenceNode} from parse tree elements
 */
public class DtsVariableBuilder extends AbstractDtsBuilderWithHelpers<VariableStatementContext, VariableStatement> {
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder(tokenStream, resource);

	private boolean parentIsNamespace;

	/** Constructor */
	public DtsVariableBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_variableDeclarationList,
				RULE_arrayLiteral,
				RULE_elementList,
				RULE_objectLiteral);
	}

	/** Call this method iff the parent of ctx is a script */
	public VariableStatement consumeInScript(VariableStatementContext ctx) {
		this.parentIsNamespace = false;
		return consume(ctx);
	}

	/** Call this method iff the parent of ctx is a namespace */
	public ExportedVariableStatement consumeInNamespace(VariableStatementContext ctx) {
		this.parentIsNamespace = true;
		return (ExportedVariableStatement) consume(ctx);
	}

	@Override
	public void enterVariableStatement(VariableStatementContext ctx) {
		boolean exported = ParserContextUtil.isExported(ctx);
		if (exported || parentIsNamespace) {
			result = N4JSFactory.eINSTANCE.createExportedVariableStatement();
			EList<N4Modifier> declaredModifiers = ((ExportedVariableStatement) result).getDeclaredModifiers();
			declaredModifiers.add(N4Modifier.EXTERNAL);
			declaredModifiers.add(N4Modifier.PUBLIC);
		} else {
			result = N4JSFactory.eINSTANCE.createVariableStatement();
			// TODO: missing modifier N4Modifier.EXTERNAL
		}
		VariableStatementKeyword keyword = VariableStatementKeyword.VAR;
		if (ctx.varModifier() != null && ctx.varModifier().Const() != null) {
			keyword = VariableStatementKeyword.CONST;
		} else if (ctx.varModifier() != null && ctx.varModifier().Let() != null) {
			keyword = VariableStatementKeyword.LET;
		}
		result.setVarStmtKeyword(keyword);

		walker.enqueue(ctx.bindingPatternBlock());
		walker.enqueue(ctx.variableDeclarationList());
	}

	@Override
	public void enterBindingPatternBlock(BindingPatternBlockContext ctx) {
		boolean exported = ParserContextUtil.isExported(ctx);
		VariableBinding varBinding = exported ? N4JSFactory.eINSTANCE.createExportedVariableBinding()
				: N4JSFactory.eINSTANCE.createVariableBinding();

		BindingPattern bindingPattern = new DtsBindingPatternBuilder(this).consume(ctx.bindingPattern());

		varBinding.setPattern(bindingPattern);

		result.getVarDeclsOrBindings().add(varBinding);
	}

	@Override
	public void enterVariableDeclaration(VariableDeclarationContext ctx) {
		boolean exported = ParserContextUtil.isExported(ctx);
		VariableDeclaration varDecl = exported ? N4JSFactory.eINSTANCE.createExportedVariableDeclaration()
				: N4JSFactory.eINSTANCE.createVariableDeclaration();
		varDecl.setName(ctx.identifierName().getText());

		if (ctx.singleExpression() != null && ctx.colonSepTypeRef() == null) {
			// in .d.ts, the only the following initializers are allowed:
			// const: only string literal, numeric literal, or literal enum reference,
			// var/let: none at all.
			Expression expr = new DtsExpressionBuilder(tokenStream, resource).consume(ctx.singleExpression());
			varDecl.setExpression(expr);
		} else {
			TypeReferenceNode<TypeRef> typeRef = typeRefBuilder.consume(ctx.colonSepTypeRef());
			varDecl.setDeclaredTypeRefNode(orAnyPlus(typeRef));
		}

		result.getVarDeclsOrBindings().add(varDecl);
	}
}
