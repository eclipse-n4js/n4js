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
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.EnumDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.FunctionDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.NamespaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeAliasDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableStatementContext;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.ExportedVariableStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4AbstractNamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4ModuleDeclaration;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.ts.types.TDeclaredModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Base class of the builders for namespace and module declarations.
 * <p>
 * Note that module declarations in .d.ts files may produce a {@code N4NamespaceDeclaration} instead of a
 * {@code N4ModuleDeclaration}:
 * <table border="1">
 * <tr>
 * <th>.d.ts source code</th>
 * <th>AST Element Created</th>
 * <th>TModule Element Created</th>
 * </tr>
 * <tr>
 * <td>{@link NamespaceDeclarationContext}</td>
 * <td>{@link N4NamespaceDeclaration}</td>
 * <td>{@link TNamespace}</td>
 * </tr>
 * <tr>
 * <td>{@link ModuleDeclarationContext}<br>
 * with identifier as name</td>
 * <td>{@link N4NamespaceDeclaration}</td>
 * <td>{@link TNamespace}</td>
 * </tr>
 * <tr>
 * <td>{@link ModuleDeclarationContext}<br>
 * with string literal as name</td>
 * <td>{@link N4ModuleDeclaration}</td>
 * <td>{@link TDeclaredModule}</td>
 * </tr>
 * </table>
 */
public abstract class AbstractDtsNamespaceBuilder<T extends ParserRuleContext>
		extends AbstractDtsSubBuilder<T, N4AbstractNamespaceDeclaration> {

	private final DtsClassBuilder classBuilder = new DtsClassBuilder(tokenStream, resource);
	private final DtsInterfaceBuilder interfaceBuilder = new DtsInterfaceBuilder(tokenStream, resource);
	private final DtsEnumBuilder enumBuilder = new DtsEnumBuilder(tokenStream, resource);
	private final DtsTypeAliasBuilder typeAliasBuilder = new DtsTypeAliasBuilder(tokenStream, resource);
	private final DtsFunctionBuilder functionBuilder = new DtsFunctionBuilder(tokenStream, resource);
	private final DtsVariableBuilder variableBuilder = new DtsVariableBuilder(tokenStream, resource);

	/** Builder for namespaces. */
	public static class DtsNamespaceBuilder
			extends AbstractDtsNamespaceBuilder<NamespaceDeclarationContext> {

		/** Constructor */
		public DtsNamespaceBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
			super(tokenStream, resource);
		}

		@Override
		public N4NamespaceDeclaration consume(NamespaceDeclarationContext ctx) {
			return (N4NamespaceDeclaration) super.consume(ctx);
		}
	}

	/** Builder for modules. */
	public static class DtsModuleBuilder
			extends AbstractDtsNamespaceBuilder<ModuleDeclarationContext> {

		/** Constructor */
		public DtsModuleBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
			super(tokenStream, resource);
		}
	}

	/** Constructor */
	public AbstractDtsNamespaceBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
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
		if (result == null) {
			boolean isExported = ParserContextUtil.isExported(ctx);
			result = doCreateN4NamespaceDeclaration(ctx.namespaceName().getText(), isExported);
			walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
		} else {
			N4NamespaceDeclaration nd = new DtsNamespaceBuilder(tokenStream, resource).consume(ctx);
			addAndHandleExported(ctx, nd);
		}
	}

	@Override
	public void enterModuleDeclaration(ModuleDeclarationContext ctx) {
		if (result == null) {
			ModuleNameContext ctxName = ctx.moduleName();
			if (ctxName != null) {
				TerminalNode strLit = ctxName.StringLiteral();
				TerminalNode identifier = ctxName.Identifier();
				if (strLit != null) {
					// this module declaration actually declares a module
					result = doCreateModuleDeclaration(ParserContextUtil.trimAndUnescapeStringLiteral(strLit));
					walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
				} else if (identifier != null) {
					// this module declaration declares a "legacy module" that acts like a namespace
					boolean isExported = ParserContextUtil.isExported(ctx);
					result = doCreateN4NamespaceDeclaration(identifier.getText(), isExported);
					walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
				}
			}
		} else {
			N4AbstractNamespaceDeclaration md = new DtsModuleBuilder(tokenStream, resource).consume(ctx);
			addAndHandleExported(ctx, md);
		}
	}

	/** Creates a {@link N4ModuleDeclaration}. The caller must assign it to {@link AbstractDtsSubBuilder#result}. */
	private N4ModuleDeclaration doCreateModuleDeclaration(String name) {
		N4ModuleDeclaration moduleDecl = N4JSFactory.eINSTANCE.createN4ModuleDeclaration();
		moduleDecl.setName(name);
		return moduleDecl;
	}

	/** Creates a {@link N4NamespaceDeclaration}. The caller must assign it to {@link AbstractDtsSubBuilder#result}. */
	private N4NamespaceDeclaration doCreateN4NamespaceDeclaration(String name, boolean isExported) {
		N4NamespaceDeclaration nsDecl = N4JSFactory.eINSTANCE.createN4NamespaceDeclaration();
		nsDecl.setName(name);
		if (isExported) {
			nsDecl.getDeclaredModifiers().add(N4Modifier.PUBLIC);
		}
		return nsDecl;
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

	private void addAndHandleExported(ParserRuleContext ctx, N4AbstractNamespaceDeclaration decl) {
		if (decl instanceof N4ModuleDeclaration) {
			addAndHandleExported(ctx, (N4ModuleDeclaration) decl);
		} else if (decl instanceof N4NamespaceDeclaration) {
			addAndHandleExported(ctx, (N4NamespaceDeclaration) decl);
		} else {
			throw new UnsupportedOperationException(
					"unsupported subclass of N4AbstractNamespaceDeclaration: " + decl.getClass().getSimpleName());
		}
	}

	private void addAndHandleExported(@SuppressWarnings("unused") ParserRuleContext ctx, N4ModuleDeclaration decl) {
		N4ModuleDeclaration md = decl;
		result.getOwnedElementsRaw().add(md);
	}

	private void addAndHandleExported(ParserRuleContext ctx, N4NamespaceDeclaration decl) {
		addAndHandleExported(ctx, (ExportableElement) decl);
	}

	private void addAndHandleExported(ParserRuleContext ctx, ExportableElement elem) {
		ParserContextUtil.addAndHandleExported(ctx, elem, result,
				N4JSPackage.Literals.N4_ABSTRACT_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW, true);
	}
}
