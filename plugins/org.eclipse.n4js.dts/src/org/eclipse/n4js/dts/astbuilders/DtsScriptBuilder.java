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

import static org.eclipse.n4js.dts.TypeScriptParser.RULE_block;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_declarationStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_declareStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_exportStatement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_exportStatementTail;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statement;
import static org.eclipse.n4js.dts.TypeScriptParser.RULE_statementList;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.n4js.dts.DtsTokenStream;
import org.eclipse.n4js.dts.NestedResourceAdapter;
import org.eclipse.n4js.dts.TypeScriptParser.ClassDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.EnumDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ExportStatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.FunctionDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.GlobalScopeAugmentationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ImportStatementContext;
import org.eclipse.n4js.dts.TypeScriptParser.InterfaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ModuleDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.NamespaceDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.ProgramContext;
import org.eclipse.n4js.dts.TypeScriptParser.TypeAliasDeclarationContext;
import org.eclipse.n4js.dts.TypeScriptParser.VariableStatementContext;
import org.eclipse.n4js.dts.utils.DtsMode;
import org.eclipse.n4js.dts.utils.ParserContextUtils;
import org.eclipse.n4js.dts.utils.TripleSlashDirective;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

/**
 * Builder to create {@link Script} elements and all its children from d.ts parse tree elements
 */
public class DtsScriptBuilder extends AbstractDtsBuilder<ProgramContext, Script> {

	private NestedResourceAdapter nestedResourceAdapter;
	private String exportEqualsIdentifier;
	private TerminalNode moduleSpecifierOfImportEqualsMatchingExportEquals;
	private int globalScopeAugmentationCounter = 0;

	/** Constructor */
	public DtsScriptBuilder(DtsTokenStream tokenStream, String packageName, Path srcFolder,
			LazyLinkingResource resource, String contents) {

		super(tokenStream, packageName, srcFolder, resource, contents);
	}

	/** Returns true iff this resource is nested/virtual */
	protected boolean isNested() {
		return getNestedResourceAdapter() != null;
	}

	/**
	 * Returns the {@link NestedResourceAdapter} installed on {@link AbstractDtsBuilder#resource} or <code>null</code>.
	 */
	public NestedResourceAdapter getNestedResourceAdapter() {
		return nestedResourceAdapter;
	}

	/**
	 * Return true iff this module uses an 'export equals' statement to export elements like in the following pattern:
	 *
	 * <pre>
	 *  declare function N(): void
	 *
	 *  declare namespace N {
	 *  }
	 *  export = N;
	 * </pre>
	 */
	public boolean isExportedEquals() {
		return getExportEqualsIdentifier() != null;
	}

	/** Returns the namespace name iff there exists an export equals statement or null otherwise. */
	public String getExportEqualsIdentifier() {
		return exportEqualsIdentifier;
	}

	/**
	 * Iff {@link #isExportedEquals()}, then this returns the module specifier of the first import of the form
	 *
	 * <pre>
	 * import id = require('./some/module');
	 * </pre>
	 *
	 * with id == {@link #getExportEqualsIdentifier()} or <code>null</code> if no such import exists. Otherwise, i.e.
	 * not {@link #isExportedEquals()}, this always returns <code>null</code>.
	 */
	public TerminalNode getModuleSpecifierOfImportEqualsMatchingExportEquals() {
		return moduleSpecifierOfImportEqualsMatchingExportEquals;
	}

	/** Increments and returns the counter for <code>global { ... }</code> declarations in this script. */
	public int incrementAndGetGlobalScopeAugmentationCounter() {
		return ++globalScopeAugmentationCounter;
	}

	/** @return the script that was created during visiting the parse tree */
	public Script getScript() {
		return result;
	}

	private void addToScript(ScriptElement elem) {
		if (elem == null) {
			return;
		}
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
				RULE_exportStatementTail,
				RULE_block); // temp
	}

	@Override
	public void enterProgram(ProgramContext ctx) {
		result = N4JSFactory.eINSTANCE.createScript();

		nestedResourceAdapter = NestedResourceAdapter.get(resource);
		exportEqualsIdentifier = ParserContextUtils.findExportEqualsIdentifier(ctx);
		moduleSpecifierOfImportEqualsMatchingExportEquals = exportEqualsIdentifier != null
				? ParserContextUtils.findImportEqualsModuleSpecifier(ctx, exportEqualsIdentifier)
				: null;

		// add @@Global (if necessary)
		if (isNested()) {
			ParserRuleContext prCtx = getNestedResourceAdapter().getContext();
			if (prCtx instanceof GlobalScopeAugmentationContext) {
				ParserContextUtils.makeGlobal(result);
			} else if (prCtx instanceof ModuleDeclarationContext) {
				ModuleDeclarationContext mdCtx = (ModuleDeclarationContext) prCtx;
				if (mdCtx.moduleName() != null && mdCtx.moduleName().StringLiteral() != null) {
					String name = ParserContextUtils.trimAndUnescapeStringLiteral(mdCtx.moduleName().StringLiteral());
					if (ParserContextUtils.isModuleAugmentationName(name)) {
						ParserContextUtils.addAnnotationModuleAugmentation(result);
					}
				}
			}
		} else {
			DtsMode dtsMode = ParserContextUtils.getDtsMode(ctx);
			if (dtsMode == DtsMode.SCRIPT) {
				ParserContextUtils.makeGlobal(result);
			}
		}

		// add @@ExportEquals (if necessary)
		if (isExportedEquals()) {
			if (getModuleSpecifierOfImportEqualsMatchingExportEquals() != null) {
				// a re-export will be created in DtsExportBuilder#enterExportEquals()
			} else {
				ParserContextUtils.addAnnotationExportEquals(result, getExportEqualsIdentifier());
			}
		}

		List<TripleSlashDirective> tripleSlashDirectives = tokenStream.getTripleSlashDirectives();
		List<ImportDeclaration> importDecls = newImportBuilder().consumeTripleSlashDirectives(tripleSlashDirectives);
		result.getScriptElements().addAll(importDecls);

		if (ctx.statementList() != null) {
			walker.enqueue(ctx.statementList().statement());
		}
	}

	@Override
	public void exitProgram(ProgramContext ctx) {
		ParserContextUtils.transformPromisifiables(result.getScriptElements());
		ParserContextUtils.removeOverloadingFunctionDefs(resource, result.getScriptElements());
	}

	@Override
	public void enterImportStatement(ImportStatementContext ctx) {
		ImportDeclaration id = newImportBuilder().consume(ctx);
		addToScript(id);
	}

	@Override
	public void enterExportStatement(ExportStatementContext ctx) {
		ExportDeclaration ed = newExportBuilder().consume(ctx);
		addToScript(ed);
	}

	@Override
	public void enterNamespaceDeclaration(NamespaceDeclarationContext ctx) {
		N4NamespaceDeclaration nd = newNamespaceBuilder().consume(ctx);
		addAndHandleExported(ctx, nd);
	}

	@Override
	public void enterModuleDeclaration(ModuleDeclarationContext ctx) {
		N4NamespaceDeclaration d = newModuleBuilder().consume(ctx);
		addAndHandleExported(ctx, d);
	}

	@Override
	public void enterGlobalScopeAugmentation(GlobalScopeAugmentationContext ctx) {
		newGlobalScopeAugmentationBuilder().consume(ctx);
	}

	@Override
	public void enterVariableStatement(VariableStatementContext ctx) {
		VariableStatement vs = newVariableBuilder().consumeInScript(ctx);
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
	public void enterEnumDeclaration(EnumDeclarationContext ctx) {
		N4EnumDeclaration ed = newEnumBuilder().consume(ctx);
		addAndHandleExported(ctx, ed);
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

	private void addAndHandleExported(ParserRuleContext ctx, ExportableElement elem) {
		ParserContextUtils.addAndHandleExported(result, N4JSPackage.Literals.SCRIPT__SCRIPT_ELEMENTS,
				elem, false, ctx);
	}
}
