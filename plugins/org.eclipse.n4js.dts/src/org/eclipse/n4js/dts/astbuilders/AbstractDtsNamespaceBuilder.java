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
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_moduleDeclaration;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_namespaceDeclaration;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statementList;

import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.LoadResultInfoAdapter;
import org.eclipse.n4js.dts.NestedResourceAdapter;
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
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.xtext.ide.server.build.ILoadResultInfoAdapter;
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
 * <td>{@code N4ModuleDeclaration}</td>
 * <td>{@code TDeclaredModule}</td>
 * </tr>
 * </table>
 */
public abstract class AbstractDtsNamespaceBuilder<T extends ParserRuleContext>
		extends AbstractDtsBuilder<T, N4NamespaceDeclaration> {

	/** Builder for namespaces. */
	public static class DtsNamespaceBuilder
			extends AbstractDtsNamespaceBuilder<NamespaceDeclarationContext> {

		/** Constructor */
		public DtsNamespaceBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource) {
			super(tokenStream, resource, null);
		}

		@Override
		public N4NamespaceDeclaration consume(NamespaceDeclarationContext ctx) {
			return super.consume(ctx);
		}
	}

	/** Builder for modules. */
	public static class DtsModuleBuilder
			extends AbstractDtsNamespaceBuilder<ModuleDeclarationContext> {

		/** Constructor */
		public DtsModuleBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource, URI srcFolder) {
			super(tokenStream, resource, srcFolder);
		}
	}

	private final URI srcFolder;

	/** Constructor */
	public AbstractDtsNamespaceBuilder(DtsTokenStream tokenStream, LazyLinkingResource resource, URI srcFolder) {
		super(tokenStream, resource);
		this.srcFolder = srcFolder;
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
			N4NamespaceDeclaration nd = newNamespaceBuilder().consume(ctx);
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

					if (!ParserContextUtil.hasParentContexts(ctx,
							new int[] { RULE_namespaceDeclaration, RULE_moduleDeclaration })) {

						// nested modules inside namespaces or nested modules are unsupported by TypeScript
						createNestedModule(ctx, ParserContextUtil.trimAndUnescapeStringLiteral(strLit));
					}
					result = null;
				} else if (identifier != null) {
					// this module declaration declares a "legacy module" that acts like a namespace
					boolean isExported = ParserContextUtil.isExported(ctx);
					result = doCreateN4NamespaceDeclaration(identifier.getText(), isExported);
					walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
				}
			}
		} else {
			N4NamespaceDeclaration md = newModuleBuilder(srcFolder).consume(ctx);
			if (md != null) {
				addAndHandleExported(ctx, md);
			}
		}
	}

	/** Triggers the creation of a nested/virtual resource. */
	private void createNestedModule(ModuleDeclarationContext ctx, String name) {
		URI virtualUri = URI.createFileURI(name + ".d.ts").resolve(srcFolder);

		LoadResultInfoAdapter loadResultInfo = (LoadResultInfoAdapter) ILoadResultInfoAdapter.get(resource);
		if (loadResultInfo == null) {
			loadResultInfo = LoadResultInfoAdapter.getOrInstall(resource);
		}
		NestedResourceAdapter nra = new NestedResourceAdapter(resource.getURI(), tokenStream, ctx);
		loadResultInfo.addNestedResource(virtualUri, nra);
	}

	/** Creates a {@link N4NamespaceDeclaration}. The caller must assign it to {@link AbstractDtsBuilder#result}. */
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
		ExportedVariableStatement vs = newVariableBuilder().consumeInNamespace(ctx);
		addAndHandleExported(ctx, vs);
	}

	@Override
	public void enterInterfaceDeclaration(InterfaceDeclarationContext ctx) {
		N4InterfaceDeclaration id = newInterfaceBuilder().consume(ctx);
		addAndHandleExported(ctx, id);
	}

	@Override
	public void enterClassDeclaration(ClassDeclarationContext ctx) {
		N4ClassDeclaration cd = newClassBuilder().consume(ctx);
		addAndHandleExported(ctx, cd);
	}

	@Override
	public void enterTypeAliasDeclaration(TypeAliasDeclarationContext ctx) {
		N4TypeAliasDeclaration tad = newTypeAliasBuilder().consume(ctx);
		addAndHandleExported(ctx, tad);
	}

	@Override
	public void enterFunctionDeclaration(FunctionDeclarationContext ctx) {
		FunctionDeclaration fd = newFunctionBuilder().consume(ctx);
		addAndHandleExported(ctx, fd);
	}

	@Override
	public void enterEnumDeclaration(EnumDeclarationContext ctx) {
		N4EnumDeclaration ed = newEnumBuilder().consume(ctx);
		addAndHandleExported(ctx, ed);
	}

	private void addAndHandleExported(ParserRuleContext ctx, N4NamespaceDeclaration decl) {
		addAndHandleExported(ctx, (ExportableElement) decl);
	}

	private void addAndHandleExported(ParserRuleContext ctx, ExportableElement elem) {
		ParserContextUtil.addAndHandleExported(
				result, N4JSPackage.Literals.N4_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW,
				ctx, elem, true);
	}
}
