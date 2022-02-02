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
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.NamespaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeAliasDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableStatementContext;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link Script} elements and all its children from d.ts parse tree elements
 */
public class DtsNamespaceBuilder extends AbstractDtsSubBuilder<NamespaceDeclarationContext, N4NamespaceDeclaration> {
	private final DtsClassBuilder classBuilder = new DtsClassBuilder(tokenStream, resource);
	private final DtsInterfaceBuilder interfaceBuilder = new DtsInterfaceBuilder(tokenStream, resource);
	private final DtsEnumBuilder enumBuilder = new DtsEnumBuilder(tokenStream, resource);
	private final DtsTypeAliasBuilder typeAliasBuilder = new DtsTypeAliasBuilder(tokenStream, resource);
	private final DtsFunctionBuilder functionBuilder = new DtsFunctionBuilder(tokenStream, resource);
	private final DtsVariableBuilder variableBuilder = new DtsVariableBuilder(tokenStream, resource);

	/** Constructor */
	public DtsNamespaceBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
		super(tokenStream, resource);
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
	public void enterNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		result = N4JSFactory.eINSTANCE.createN4NamespaceDeclaration();
		result.setName(ctx.namespaceName().getText());
		boolean isExported = ParserContextUtil.isExported(ctx);
		if (isExported) {
			result.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		}
	}

	@Override
	public void enterVariableStatement(VariableStatementContext ctx) {
		ExportedVariableStatement vs = variableBuilder.consumeInNamespace(ctx);
		addAndHandleExported(ctx, vs);
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
	public void enterTypeAliasDeclaration(TypeAliasDeclarationContext ctx) {
		N4TypeAliasDeclaration tad = typeAliasBuilder.consume(ctx);
		addAndHandleExported(ctx, tad);
	}

	@Override
	public void enterFunctionDeclaration(FunctionDeclarationContext ctx) {
		FunctionDeclaration fd = functionBuilder.consume(ctx);
		addAndHandleExported(ctx, fd);
	}

	@Override
	public void enterEnumDeclaration(EnumDeclarationContext ctx) {
		N4EnumDeclaration ed = enumBuilder.consume(ctx);
		addAndHandleExported(ctx, ed);
	}

	private void addAndHandleExported(ParserRuleContext ctx, ExportableElement elem) {
		boolean isExported = ParserContextUtil.isExported(ctx);
		if (isExported) {
			((ModifiableElement) elem).getDeclaredModifiers().add(N4Modifier.PUBLIC);
		}
		result.getOwnedElementsRaw().add(elem);
	}
}
