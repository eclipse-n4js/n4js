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
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_program;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statementList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.dts.LoadResultInfoAdapter;
import org.eclipse.n4js.dts.NestedResourceAdapter;
import org.eclipse.n4js.dts.TypeScriptParser.BlockContext;
import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.EnumDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.FunctionDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.GlobalScopeAugmentationContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleNameContext;
import org.eclipse.n4js.dts.TypeScriptParser.NamespaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.dts.TypeScriptParser.StatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeAliasDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableStatementContext;
import org.eclipse.n4js.dts.utils.ParserContextUtils;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.ide.server.build.ILoadResultInfoAdapter;

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
		public DtsNamespaceBuilder(AbstractDtsBuilder<?, ?> parent) {
			super(parent);
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
		public DtsModuleBuilder(AbstractDtsBuilder<?, ?> parent) {
			super(parent);
		}
	}

	/** Builder for global scope augmentations. */
	public static class DtsGlobalScopeAugmentationBuilder
			extends AbstractDtsNamespaceBuilder<GlobalScopeAugmentationContext> {

		/** Constructor */
		public DtsGlobalScopeAugmentationBuilder(AbstractDtsBuilder<?, ?> parent) {
			super(parent);
		}
	}

	/** Constructor */
	public AbstractDtsNamespaceBuilder(AbstractDtsBuilder<?, ?> parent) {
		super(parent);
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
			boolean isExported = ParserContextUtils.isExported(ctx);
			result = doCreateN4NamespaceDeclaration(ctx.namespaceName().getText(), isExported);
			walker.enqueue(ParserContextUtils.getStatements(ctx.block()));
		} else {
			N4NamespaceDeclaration nd = newNamespaceBuilder().consume(ctx);
			addAndHandleExported(ctx, nd);
		}
	}

	@Override
	public void exitNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		if (result != null) {
			ParserContextUtils.removeOverloadingFunctionDefs(resource, result.getOwnedElementsRaw());
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

					if (!ParserContextUtils.hasParentContexts(ctx,
							new int[] { RULE_namespaceDeclaration, RULE_moduleDeclaration })) {

						// nested modules inside namespaces or nested modules are unsupported by TypeScript
						if (ctx.block() != null) {
							createNestedModule(ctx, ParserContextUtils.getStatements(ctx.block()),
									ParserContextUtils.trimAndUnescapeStringLiteral(strLit));

							// declared modules may contain global state augmentations
							// (to avoid creating virtual resources from within a virtual resource,
							// we have to handle this ahead of time!)
							for (GlobalScopeAugmentationContext gsaCtx : ParserContextUtils.getGlobalScopeAugmentations(
									ctx)) {
								doHandleGlobalScopeAugmentation(gsaCtx);
							}
						}
					}
					result = null;
				} else if (identifier != null) {
					// this module declaration declares a "legacy module" that acts like a namespace
					boolean isExported = ParserContextUtils.isExported(ctx);
					result = doCreateN4NamespaceDeclaration(identifier.getText(), isExported);
					walker.enqueue(ParserContextUtils.getStatements(ctx.block()));
				}
			}
		} else {
			N4NamespaceDeclaration md = newModuleBuilder().consume(ctx);
			if (md != null) {
				addAndHandleExported(ctx, md);
			}
		}
	}

	@Override
	public void enterGlobalScopeAugmentation(GlobalScopeAugmentationContext ctx) {
		// note: global scope augmentations may only appear on top-level of files with DtsMode MODULE or
		// as direct children of declared modules
		if (result != null) {
			// nested inside a namespace or "legacy module" (this should be a compile error in .d.ts)
			// -> ignore
			return;
		}
		if (getScriptBuilder().isNested()) {
			// we are in a virtual resource
			// -> ignore this ctx here, because it was handled ahead of time in #enterModuleDeclaration()
			return;
		}
		doHandleGlobalScopeAugmentation(ctx);
	}

	/** Handles global scope augmentations, i.e. .d.ts code such as <code>global { ... }</code>. */
	protected void doHandleGlobalScopeAugmentation(GlobalScopeAugmentationContext ctx) {
		if (ctx.block() == null) {
			return;
		}
		String resName = URIUtils.SPECIAL_SEGMENT_MARKER + "globalScopeAugmentation"
				+ getScriptBuilder().incrementAndGetGlobalScopeAugmentationCounter();

		List<StatementContext> statements = new ArrayList<>();
		// add higher-level imports
		for (BlockContext bCtx : ParserContextUtils.getContainersOfType(ctx, BlockContext.class)) {
			for (StatementContext sCtx : ParserContextUtils.getStatements(bCtx)) {
				boolean isImport = sCtx.importStatement() != null;
				if (isImport) {
					statements.add(sCtx);
				}
			}
		}
		// add statements of 'ctx' itself
		statements.addAll(ParserContextUtils.getStatements(ctx.block()));

		createNestedModule(ctx, statements, resName);
	}

	/** Triggers the creation of a nested/virtual resource. */
	private void createNestedModule(ParserRuleContext ctx, Iterable<StatementContext> statements, String name) {
		URI resUri = resource.getURI();

		if (ParserContextUtils.isModuleAugmentationName(name)) {
			ProgramContext pgrCtx = (ProgramContext) ParserContextUtils.findParentContext(ctx, RULE_program);
			if (pgrCtx == null || !ParserContextUtils.hasImport(pgrCtx, name)) {
				// augmented modules need to be imported
				return;
			}

			// module augmentation
			Path resPath = Path.of(resUri.toFileString()).getParent();
			Path nestedModulePath = Path.of(name);
			Path resolvedPath = resPath.resolve(nestedModulePath).normalize();
			name = srcFolder.relativize(resolvedPath).toString();
		} else {
			// ambient module
		}

		URI virtualUri = URIUtils.createVirtualResourceURI(resUri, name + ".d.ts");

		LoadResultInfoAdapter loadResultInfo = (LoadResultInfoAdapter) ILoadResultInfoAdapter.get(resource);
		if (loadResultInfo == null) {
			loadResultInfo = LoadResultInfoAdapter.getOrInstall(resource);
		}
		NestedResourceAdapter nra = new NestedResourceAdapter(tokenStream, ctx, statements);
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
		VariableStatement vs = newVariableBuilder().consumeInNamespace(ctx);
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
		ParserContextUtils.addAndHandleExported(
				result, N4JSPackage.Literals.N4_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW,
				elem, true, ctx);
	}
}
