/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ConditionalExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._LiteralOrComputedPropertyName;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Parenthesis;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableBinding;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableStatement;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.drop;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.ModuleSpecifierForm;
import org.eclipse.n4js.n4JS.N4JSFactory;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.VariableStatementKeyword;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesAfter;
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesBefore;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableListMultimap;
import com.google.inject.Inject;

/**
 * Since switching to node's native support for ES6 modules, we have to re-write all import declarations that import
 * from an old CommonJS module. Note that this behavior can be turned on/off in the package.json file, see
 * {@link PackageJsonProperties#GENERATOR_REWRITE_CJS_IMPORTS}.
 */
@ExcludesAfter(SanitizeImportsTransformation.class) // CommonJsImportsTransformation must not run before
													// SanitizeImportsTransformation
@ExcludesBefore(ModuleWrappingTransformation.class) // CommonJsImportsTransformation must not run after
													// ModuleWrappingTransformation
public class CommonJsImportsTransformation extends Transformation {

	@Inject
	private N4JSLanguageHelper n4jsLanguageHelper;

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Override
	public void assertPreConditions() {
		// true
	}

	@Override
	public void assertPostConditions() {
		// true
	}

	@Override
	public void analyze() {
		// nothing to be done
	}

	@Override
	public void transform() {
		if (!getState().project.getProjectDescription().isGeneratorEnabledRewriteCjsImports()) {
			// rewriting of CJS imports is not enabled for the containing project
			return;
		}

		ImmutableListMultimap<TModule, ImportDeclaration> importDeclsPerImportedModule = FluentIterable
				.from(getState().im.getScriptElements())
				.filter(ImportDeclaration.class)
				.filter(id -> !id.isBare()) // ignore bare imports
				.index(importDecl -> getState().info.getImportedModule(importDecl));

		List<VariableStatement> varStmnts = new ArrayList<>();
		for (TModule targetModule : importDeclsPerImportedModule.keySet()) {
			varStmnts.addAll(transformImportDecl(targetModule, importDeclsPerImportedModule.get(targetModule)));
		}

		ImportDeclaration lastImportDecl = last(filter(getState().im.getScriptElements(), ImportDeclaration.class));
		insertAfter(lastImportDecl, varStmnts.toArray(new VariableStatement[0]));
	}

	/**
	 * For those of the given imports that actually require rewriting, this method will change them in place *and*
	 * return one or more variable statements that have to be inserted (by the client code) after all imports.
	 * <p>
	 * For example: this method will rewrite the following imports
	 *
	 * <pre>
	 * import defaultImport+ from "plainJsModule"
	 * import {namedImport1+} from "plainJsModule"
	 * import {namedImport2+} from "plainJsModule"
	 * import * as NamespaceImport+ from "plainJsModule"
	 * </pre>
	 *
	 * to this import:
	 *
	 * <pre>
	 * import $tempVar from './plainJsModule.cjs'
	 * </pre>
	 *
	 * and will return these variable statements:
	 *
	 * <pre>
	 * const defaultImport = ($tempVar?.__esModule ? $tempVar.default : $tempVar);
	 * const NamespaceImport = $tempVar;
	 * const {
	 *     namedImport1,
	 *     namedImport2
	 * } = $tempVar;
	 * </pre>
	 */
	private List<VariableStatement> transformImportDecl(TModule targetModule,
			List<ImportDeclaration> allImportDeclsForThisModule) {
		if (allImportDeclsForThisModule.isEmpty()) {
			return Collections.emptyList();
		}
		if (!requiresRewrite(targetModule)) {
			return Collections.emptyList();
		}

		List<ImportDeclaration> importDeclsToRewrite = new ArrayList<>(allImportDeclsForThisModule);
		if (exists(importDeclsToRewrite, id -> id.getModuleSpecifierForm() == ModuleSpecifierForm.PROJECT)) {
			N4JSProjectConfigSnapshot targetProject = n4jsLanguageHelper.replaceDefinitionProjectByDefinedProject(
					getState().resource, workspaceAccess.findProjectContaining(targetModule), true);
			if (targetProject != null && targetProject.getProjectDescription().hasModuleProperty()) {
				// don't rewrite project imports in case the target project has a top-level property "module" in its
				// package.json,
				// because in that case project imports will be redirected to an esm-ready file:
				importDeclsToRewrite.removeIf(id -> id.getModuleSpecifierForm() == ModuleSpecifierForm.PROJECT);
			}
		}
		if (importDeclsToRewrite.isEmpty()) {
			return Collections.emptyList();
		}

		// special case with a simpler approach:
		if (importDeclsToRewrite.size() == 1 && importDeclsToRewrite.get(0).getImportSpecifiers().size() == 1) {
			ImportSpecifier importSpec = importDeclsToRewrite.get(0).getImportSpecifiers().get(0);
			if (importSpec instanceof NamespaceImportSpecifier) {
				// in this case we can simply replace the namespace import by a default import
				String namespaceName = ((NamespaceImportSpecifier) importSpec).getAlias();
				DefaultImportSpecifier newImportSpec = N4JSFactory.eINSTANCE.createDefaultImportSpecifier();
				newImportSpec.setImportedElementAsText(namespaceName);
				newImportSpec.setFlaggedUsedInCode(true);
				newImportSpec.setRetainedAtRuntime(true);
				// NOTE: don't copy the following lines to other places! Use methods #replace() instead!
				getState().tracer.copyTrace(importSpec, newImportSpec);
				insertAfter(importSpec, newImportSpec);
				remove(importSpec);
				return Collections.emptyList();
			}
		}

		String tempVarName = computeNameForIntermediateDefaultImport(targetModule);
		SymbolTableEntryInternal tempVarSTE = getSymbolTableEntryInternal(tempVarName, true);

		List<VariableDeclaration> varDecls = new ArrayList<>();
		List<BindingProperty> bindingProps = new ArrayList<>();

		createVarDeclsOrBindings(importDeclsToRewrite, tempVarSTE, varDecls, bindingProps);

		List<VariableStatement> result = new ArrayList<>();
		for (VariableDeclaration varDecl : varDecls) {
			result.add(_VariableStatement(VariableStatementKeyword.CONST, varDecl));
		}
		if (!bindingProps.isEmpty()) {
			result.add(_VariableStatement(VariableStatementKeyword.CONST,
					_VariableBinding(bindingProps, _IdentRef(tempVarSTE))));
		}

		ImportDeclaration firstImportDecl = importDeclsToRewrite.get(0);
		removeAll(firstImportDecl.getImportSpecifiers());
		DefaultImportSpecifier dsi = N4JSFactory.eINSTANCE.createDefaultImportSpecifier();
		firstImportDecl.getImportSpecifiers().add(dsi);
		dsi.setImportedElementAsText(tempVarSTE.getName());
		dsi.setFlaggedUsedInCode(true);
		dsi.setRetainedAtRuntime(true);
		removeAll(drop(importDeclsToRewrite, 1));

		return result;
	}

	private void createVarDeclsOrBindings(List<ImportDeclaration> importDecls, SymbolTableEntry steTempVar,
			List<VariableDeclaration> varDecls, List<BindingProperty> bindingProps) {

		SymbolTableEntry steEsModule = null;
		SymbolTableEntry steDefault = null;
		for (ImportDeclaration importDecl : importDecls) {
			for (ImportSpecifier importSpec : importDecl.getImportSpecifiers()) {
				if (importSpec instanceof NamedImportSpecifier) {
					NamedImportSpecifier nis = (NamedImportSpecifier) importSpec;
					String importedName = nis.getImportedElementAsText();
					String localName = nis.getAlias() != null ? nis.getAlias() : importedName;
					boolean isDefaultImport = nis.isDefaultImport() || importedName == "default";
					if (isDefaultImport) {
						if (steEsModule == null) {
							steEsModule = steFor_interopProperty_esModule();
						}
						if (steDefault == null) {
							steDefault = getSymbolTableEntryInternal("default", true);
						}
						ParameterizedPropertyAccessExpression_IM pae = _PropertyAccessExpr(steTempVar,
								steFor_interopProperty_esModule());
						pae.setOptionalChaining(true);
						varDecls.add(_VariableDeclaration(localName, _Parenthesis(_ConditionalExpr(
								pae,
								_PropertyAccessExpr(steTempVar, steDefault),
								_IdentRef(steTempVar)))));
					} else {
						BindingProperty bp = N4JSFactory.eINSTANCE.createBindingProperty();
						bindingProps.add(bp);
						if (localName != importedName) {
							bp.setDeclaredName(_LiteralOrComputedPropertyName(importedName));
						}
						BindingElement be = N4JSFactory.eINSTANCE.createBindingElement();
						bp.setValue(be);
						be.setVarDecl(_VariableDeclaration(localName));
					}
				} else if (importSpec instanceof NamespaceImportSpecifier) {
					NamespaceImportSpecifier nis = (NamespaceImportSpecifier) importSpec;
					String namespaceName = nis.getAlias();
					varDecls.add(_VariableDeclaration(namespaceName, _IdentRef(steTempVar)));
				} else {
					throw new IllegalStateException(
							"unsupported subclass of ImportSpecifier: " + importSpec.eClass().getName());
				}
			}
		}
	}

	private boolean requiresRewrite(TModule targetModule) {
		return !n4jsLanguageHelper.isES6Module(getState().index, targetModule);
	}

	private String computeNameForIntermediateDefaultImport(TModule targetModule) {
		String packageNameRaw = targetModule.getPackageName();
		if (packageNameRaw != null) {
			String n4jsdScopeWithSep = N4JSGlobals.N4JSD_SCOPE + ProjectDescriptionUtils.NPM_SCOPE_SEPARATOR;
			if (packageNameRaw.startsWith(n4jsdScopeWithSep)) {
				packageNameRaw = packageNameRaw.substring(n4jsdScopeWithSep.length());
			}
		}

		String packageName = Strings.toIdentifier(packageNameRaw, '_');
		String moduleName = Strings.toIdentifier(targetModule.getQualifiedName(), '_');
		String baseName = "$cjsImport__" + packageName + "__" + moduleName;

		int idx = 0;
		String candidate = baseName;
		// TODO: we won't find name conflicts with SymbolTableEntryOriginals (requires refactoring in
		// TranspilerState.STECache)
		while (getSymbolTableEntryInternal(candidate, false) != null) {
			idx++;
			candidate = baseName + idx;
		}
		return candidate;
	}
}
