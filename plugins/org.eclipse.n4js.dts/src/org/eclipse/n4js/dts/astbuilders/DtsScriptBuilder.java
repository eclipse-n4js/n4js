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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_declarationStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_declareStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_exportStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_exportStatementTail;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statementList;

import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.ParserContextUtil;
import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.EnumDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.FunctionDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ImportStatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.NamespaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeAliasDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableStatementContext;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link Script} elements and all its children from d.ts parse tree elements
 */
public class DtsScriptBuilder extends AbstractDtsSubBuilder<ProgramContext, Script> {
	private final DtsImportBuilder importBuilder = new DtsImportBuilder(tokenStream, resource);
	private final DtsTypeAliasBuilder typeAliasBuilder = new DtsTypeAliasBuilder(tokenStream, resource);
	private final DtsFunctionBuilder functionBuilder = new DtsFunctionBuilder(tokenStream, resource);
	private final DtsNamespaceBuilder namespaceBuilder = new DtsNamespaceBuilder(tokenStream, resource);
	private final DtsClassBuilder classBuilder = new DtsClassBuilder(tokenStream, resource);
	private final DtsInterfaceBuilder interfaceBuilder = new DtsInterfaceBuilder(tokenStream, resource);
	private final DtsEnumBuilder enumBuilder = new DtsEnumBuilder(tokenStream, resource);
	private final DtsVariableBuilder variableBuilder = new DtsVariableBuilder(tokenStream, resource);

	/** Constructor */
	public DtsScriptBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
	}

	/** @return the script that was created during visiting the parse tree */
	public Script getScript() {
		return result;
	}

	void addToScript(ScriptElement elem) {
		result.getScriptElements().add(elem);
	}

	@Override
	protected Set<Integer> getVisitChildrenOfRules() {
		return java.util.Set.of(
				RULE_statement,
				RULE_statementList,
				RULE_declareStatement,
				RULE_declarationStatement,
				RULE_exportStatement,
				RULE_exportStatementTail);
	}

	@Override
	public void enterProgram(ProgramContext ctx) {
		result = N4JSFactory.eINSTANCE.createScript();
		if (ctx.statementList() != null) {
			walker.enqueue(ctx.statementList().statement());
		}
	}

	@Override
	public void enterImportStatement(ImportStatementContext ctx) {
		ImportDeclaration id = importBuilder.consume(ctx);
		addToScript(id);
	}

	@Override
	public void enterNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		N4NamespaceDeclaration nd = namespaceBuilder.consume(ctx);
		nd.setName(ctx.namespaceName().getText());
		addAndHandleExported(ctx, nd);
	}

	@Override
	public void enterVariableStatement(VariableStatementContext ctx) {
		VariableStatement vs = variableBuilder.consumeInScript(ctx);
		addToScript(vs);
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
