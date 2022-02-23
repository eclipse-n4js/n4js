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
 * Builder for namespace and module declarations.
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
public class DtsNamespaceBuilder
		// cannot use more specific type arguments, because:
		// - 1st argument: need to accept NamespaceDeclarationContext and ModuleDeclarationContext
		// - 2nd argument: will create N4NamespaceDeclaration or N4ModuleDeclaration
		extends AbstractReentrantDtsSubBuilder<ParserRuleContext, N4AbstractNamespaceDeclaration> {

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

	/**
	 * Overloads {@link #consume(ParserRuleContext)} to codify the rule that when a {@link NamespaceDeclarationContext}
	 * is passed in, you will always get back a {@link N4NamespaceDeclaration} (never a {@link N4ModuleDeclaration}).
	 */
	public N4NamespaceDeclaration consume(NamespaceDeclarationContext ctx) {
		return (N4NamespaceDeclaration) super.consume(ctx);
	}

	/**
	 * Overloads {@link #consume(ParserRuleContext)} to codify the rule that when a {@link ModuleDeclarationContext} is
	 * passed in, you might get back an {@link N4NamespaceDeclaration} or an {@link N4ModuleDeclaration}.
	 */
	public N4AbstractNamespaceDeclaration consume(ModuleDeclarationContext ctx) {
		return super.consume(ctx);
	}

	@Override
	public N4AbstractNamespaceDeclaration consume(ParserRuleContext ctx) {
		if (!(ctx instanceof NamespaceDeclarationContext)
				&& !(ctx instanceof ModuleDeclarationContext)) {
			throw new IllegalArgumentException(
					"may only be called with a namespace or module declaration context, but got: "
							+ ctx.getClass().getSimpleName());
		}
		return super.consume(ctx);
	}

	@Override
	public void enterNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		boolean isExported = ParserContextUtil.isExported(ctx);
		N4NamespaceDeclaration nd = doCreateN4NamespaceDeclaration(ctx.namespaceName().getText(), isExported);
		pushResult(nd);
		walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
	}

	@Override
	public void enterModuleDeclaration(ModuleDeclarationContext ctx) {
		ModuleNameContext ctxName = ctx.moduleName();
		if (ctxName != null) {
			TerminalNode strLit = ctxName.StringLiteral();
			if (strLit != null) {
				// this module declaration actually declares a module
				N4ModuleDeclaration md = doCreateModuleDeclaration(ParserContextUtil.trimStringLiteral(strLit));
				pushResult(md);
				walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
			} else {
				TerminalNode identifier = ctxName.Identifier();
				if (identifier != null) {
					// this module declaration declares a "legacy module" that acts like a namespace
					boolean isExported = ParserContextUtil.isExported(ctx);
					N4NamespaceDeclaration nd = doCreateN4NamespaceDeclaration(identifier.getText(), isExported);
					pushResult(nd);
					walker.enqueue(ParserContextUtil.getStatements(ctx.block()));
				}
			}
		}
	}

	@Override
	public void exitNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		doExit(ctx);
	}

	@Override
	public void exitModuleDeclaration(ModuleDeclarationContext ctx) {
		doExit(ctx);
	}

	private void doExit(ParserRuleContext ctx) {
		N4AbstractNamespaceDeclaration previousResult = popResult();
		if (resultStack.isEmpty()) {
			// we just popped the last, i.e. the root namespace/module off the stack, so the caller of #consume() is
			// responsible for adding it to its parent (usually the Script)
			// --> nothing to do here
		} else {
			// we just popped a nested namespace/module off the stack, i.e. the current 'result' is the parent of
			// 'previousResult'
			// --> we have to add 'previousResult' to 'result'
			addAndHandleExported(ctx, previousResult);
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

	private void addAndHandleExported(ParserRuleContext ctx, N4AbstractNamespaceDeclaration decl) {
		if (decl instanceof N4ModuleDeclaration) {
			N4ModuleDeclaration md = (N4ModuleDeclaration) decl;
			result.getOwnedElementsRaw().add(md);
		} else if (decl instanceof N4NamespaceDeclaration) {
			addAndHandleExported(ctx, (ExportableElement) decl);
		} else {
			throw new UnsupportedOperationException(
					"unsupported subclass of N4AbstractNamespaceDeclaration: " + decl.getClass().getSimpleName());
		}
	}

	private void addAndHandleExported(ParserRuleContext ctx, ExportableElement elem) {
		ParserContextUtil.addAndHandleExported(ctx, elem, result,
				N4JSPackage.Literals.N4_ABSTRACT_NAMESPACE_DECLARATION__OWNED_ELEMENTS_RAW, true);
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
}
