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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classElement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_classElementList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_declarationStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_declareStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_exportStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_exportStatementTail;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statementList;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeMember;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_typeMemberList;

import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.eclipse.n4js.dts.ManualParseTreeWalker;
import org.eclipse.n4js.dts.ParserContextUtil;
import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.EnumDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.FunctionDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.NamespaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeAliasDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableStatementContext;
import org.eclipse.n4js.dts.TypeScriptParserBaseListener;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.NamespaceElement;
import org.eclipse.n4js.n4JS.Script;

/**
 * Builder to create {@link Script} elements and all its children from d.ts parse tree elements
 */
public class DtsScriptBuilder extends TypeScriptParserBaseListener {
	final static Set<Integer> VISIT_CHILDREN_OF_RULES = java.util.Set.of(
			RULE_statement,
			RULE_statementList,
			RULE_declareStatement,
			RULE_declarationStatement,
			RULE_exportStatement,
			RULE_exportStatementTail,
			RULE_classElementList,
			RULE_classElement,
			RULE_typeMember,
			RULE_typeMemberList);

	private final ManualParseTreeWalker walker;
	private final DtsTypeRefBuilder typeRefBuilder = new DtsTypeRefBuilder();
	private final DtsTypeAliasBuilder typeAliasBuilder = new DtsTypeAliasBuilder();
	private final DtsFunctionBuilder functionBuilder = new DtsFunctionBuilder();
	private final DtsNamespaceBuilder namespaceBuilder = new DtsNamespaceBuilder();
	private final DtsClassBuilder classBuilder = new DtsClassBuilder();
	private final DtsInterfaceBuilder interfaceBuilder = new DtsInterfaceBuilder();
	private final DtsEnumBuilder enumBuilder = new DtsEnumBuilder();
	private final DtsExpressionBuilder expressionBuilder = new DtsExpressionBuilder();

	private Script script = null;
	private final N4ClassifierDefinition currentClassifierDefinition = null;

	/** Constructor */
	public DtsScriptBuilder(ManualParseTreeWalker walker) {
		this.walker = walker;
		walker.setParseTreeListener(this);
	}

	/** @return the script that was created during visiting the parse tree */
	public Script getScript() {
		return script;
	}

	void addToScript(NamespaceElement elem) {
		script.getScriptElements().add(elem);
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
	public void enterProgram(ProgramContext ctx) {
		script = N4JSFactory.eINSTANCE.createScript();
		if (ctx.statementList() != null) {
			walker.enqueue(ctx.statementList().statement());
		}
	}

	@Override
	public void enterNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		N4NamespaceDeclaration nd = namespaceBuilder.consume(ctx);
		nd.setName(ctx.namespaceName().getText());
		addAndHandleExported(ctx, nd);
	}

	@Override
	public void enterVariableStatement(VariableStatementContext ctx) {

	}

	@Override
	public void enterInterfaceDeclaration(InterfaceDeclarationContext ctx) {
		N4InterfaceDeclaration id = interfaceBuilder.consume(ctx);
		addAndHandleExported(ctx, id);
	}

	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		N4ClassDeclaration cd = classBuilder.consume(ctx);
		addAndHandleExported(ctx, cd);
	}

	@Override
	public void enterEnumDeclaration(EnumDeclarationContext ctx) {
		N4EnumDeclaration ed = enumBuilder.consume(ctx);
		addAndHandleExported(ctx, ed);
	}

	@Override
	public void enterTypeAliasDeclaration(TypeAliasDeclarationContext ctx) {
		N4TypeAliasDeclaration tad = typeAliasBuilder.consume(ctx);
		addAndHandleExported(ctx, tad);
	}

	@Override
	public void enterFunctionDeclaration(FunctionDeclarationContext ctx) {
		FunctionDeclaration fd = functionBuilder.consume(ctx);
		addAndHandleExported(ctx, fd);
	}

	private void addAndHandleExported(ParserRuleContext ctx, ExportableElement id) {
		boolean isExported = ParserContextUtil.isExported(ctx);
		if (isExported) {
			((ModifiableElement) id).getDeclaredModifiers().add(N4Modifier.PUBLIC);
			ExportDeclaration ed = N4JSFactory.eINSTANCE.createExportDeclaration();
			ed.setExportedElement(id);
			addToScript(ed);
		} else {
			addToScript(id);
		}
	}
}
