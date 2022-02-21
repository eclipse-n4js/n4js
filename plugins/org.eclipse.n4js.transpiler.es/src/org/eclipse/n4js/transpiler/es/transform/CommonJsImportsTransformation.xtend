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
package org.eclipse.n4js.transpiler.es.transform

import com.google.common.collect.FluentIterable
import com.google.inject.Inject
import java.util.ArrayList
import java.util.List
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.n4JS.BindingProperty
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ModuleSpecifierForm
import org.eclipse.n4js.n4JS.N4JSFactory
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.VariableStatementKeyword
import org.eclipse.n4js.packagejson.PackageJsonProperties
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesAfter
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesBefore
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.N4JSLanguageHelper
import org.eclipse.n4js.utils.ProjectDescriptionUtils
import org.eclipse.n4js.utils.Strings
import org.eclipse.n4js.workspace.WorkspaceAccess

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Since switching to node's native support for ES6 modules, we have to re-write all import declarations
 * that import from an old CommonJS module. Note that this behavior can be turned on/off in the package.json
 * file, see {@link PackageJsonProperties#GENERATOR_CJS_DEFAULT_IMPORTS}.
 */
@ExcludesAfter(SanitizeImportsTransformation) // CommonJsImportsTransformation must not run before SanitizeImportsTransformation
@ExcludesBefore(ModuleWrappingTransformation) // CommonJsImportsTransformation must not run after ModuleWrappingTransformation
class CommonJsImportsTransformation extends Transformation {

	@Inject
	private N4JSLanguageHelper n4jsLanguageHelper;

	@Inject
	private WorkspaceAccess workspaceAccess;

	override void assertPreConditions() {
		// true
	}

	override void assertPostConditions() {
		// true
	}

	override void analyze() {
		// nothing to be done
	}

	override void transform() {
		if (!state.project.projectDescription.generatorEnabledRewriteCjsImports) {
			// rewriting of CJS imports is not enabled for the containing project
			return;
		}

		val importDeclsPerImportedModule = FluentIterable.from(state.im.scriptElements)
			.filter(ImportDeclaration)
			.filter[!bare] // ignore bare imports
			.index[importDecl | state.info.getImportedModule(importDecl)];

		val varStmnts = newArrayList;
		for (targetModule : importDeclsPerImportedModule.keySet) {
			varStmnts += transformImportDecl(targetModule, importDeclsPerImportedModule.get(targetModule));
		}

		val lastImportDecl = state.im.scriptElements.filter(ImportDeclaration).last();
		insertAfter(lastImportDecl, varStmnts);
	}

	/**
	 * For those of the given imports that actually require rewriting, this method will change them in place *and* return one or more
	 * variable statements that have to be inserted (by the client code) after all imports.
	 * <p>
	 * For example: this method will rewrite the following imports
	 * <pre>
	 * import defaultImport+ from "plainJsModule"
	 * import {namedImport1+} from "plainJsModule"
	 * import {namedImport2+} from "plainJsModule"
	 * import * as NamespaceImport+ from "plainJsModule"
	 * </pre>
	 * to this import:
	 * <pre>
	 * import $tempVar from './plainJsModule.cjs'
	 * </pre>
	 * and will return these variable statements:
	 * <pre>
	 * const defaultImport = ($tempVar?.__esModule ? $tempVar.default : $tempVar);
	 * const NamespaceImport = $tempVar;
	 * const {
	 *     namedImport1,
	 *     namedImport2
	 * } = $tempVar;
	 * </pre>
	 */
	def private List<VariableStatement> transformImportDecl(TModule targetModule, List<ImportDeclaration> allImportDeclsForThisModule) {
		if (allImportDeclsForThisModule.empty) {
			return #[];
		}
		if (!requiresRewrite(targetModule)) {
			return #[];
		}

		val importDeclsToRewrite = new ArrayList(allImportDeclsForThisModule);
		if (importDeclsToRewrite.exists[moduleSpecifierForm === ModuleSpecifierForm.PROJECT]) {
			val targetProject = n4jsLanguageHelper.replaceDefinitionProjectByDefinedProject(state.resource,
				workspaceAccess.findProjectContaining(targetModule), true);
			if (targetProject !== null && targetProject.projectDescription.hasModuleProperty) {
				// don't rewrite project imports in case the target project has a top-level property "module" in its package.json,
				// because in that case project imports will be redirected to an esm-ready file:
				importDeclsToRewrite.removeIf[moduleSpecifierForm === ModuleSpecifierForm.PROJECT];
			}
		}
		if (importDeclsToRewrite.empty) {
			return #[];
		}

		// special case with a simpler approach:
		if (importDeclsToRewrite.size === 1 && importDeclsToRewrite.head.importSpecifiers.size === 1) {
			val importSpec = importDeclsToRewrite.head.importSpecifiers.head;
			if (importSpec instanceof NamespaceImportSpecifier) {
				// in this case we can simply replace the namespace import by a default import
				val namespaceName = importSpec.alias;
				val newImportSpec = N4JSFactory.eINSTANCE.createDefaultImportSpecifier() => [
					importedElementAsText = namespaceName;
					flaggedUsedInCode = true;
					retainedAtRuntime = true;
				];
				// NOTE: don't copy the following lines to other places! Use methods #replace() instead!
				state.tracer.copyTrace(importSpec, newImportSpec);
				insertAfter(importSpec, newImportSpec);
				remove(importSpec);
				return #[]
			}
		}

		val tempVarName = computeNameForIntermediateDefaultImport(targetModule);
		val tempVarSTE = getSymbolTableEntryInternal(tempVarName, true);

		val varDecls = <VariableDeclaration>newArrayList;
		val bindingProps = <BindingProperty>newArrayList;

		createVarDeclsOrBindings(importDeclsToRewrite, tempVarSTE, varDecls, bindingProps);

		val result = <VariableStatement>newArrayList;
		for (varDecl : varDecls) {
			result += _VariableStatement(VariableStatementKeyword.CONST, varDecl);
		}
		if (!bindingProps.empty) {
			result += _VariableStatement(VariableStatementKeyword.CONST, _VariableBinding(bindingProps, _IdentRef(tempVarSTE)));
		}

		val firstImportDecl = importDeclsToRewrite.head;
		removeAll(firstImportDecl.importSpecifiers);
		firstImportDecl.importSpecifiers += N4JSFactory.eINSTANCE.createDefaultImportSpecifier() => [
			importedElementAsText = tempVarSTE.name;
			flaggedUsedInCode = true;
			retainedAtRuntime = true;
		];
		removeAll(importDeclsToRewrite.drop(1));

		return result;
	}

	def private void createVarDeclsOrBindings(List<ImportDeclaration> importDecls, SymbolTableEntry steTempVar,
		List<VariableDeclaration> varDecls, List<BindingProperty> bindingProps) {

		var steEsModule = null as SymbolTableEntry;
		var steDefault = null as SymbolTableEntry;
		for (importDecl : importDecls) {
			for (importSpec : importDecl.importSpecifiers) {
				switch (importSpec) {
					NamedImportSpecifier: { // including DefaultImportSpecifier
						val importedName = importSpec.importedElementAsText;
						val localName = importSpec.alias ?: importedName;
						val isDefaultImport = importSpec.isDefaultImport || importedName == "default";
						if (isDefaultImport) {
							if (steEsModule === null) {
								steEsModule = steFor_interopProperty_esModule();
							}
							if (steDefault === null) {
								steDefault = getSymbolTableEntryInternal("default", true);
							}
							varDecls += _VariableDeclaration(localName, _Parenthesis(_ConditionalExpr(
								_PropertyAccessExpr(steTempVar, steFor_interopProperty_esModule) => [ optionalChaining = true ],
								_PropertyAccessExpr(steTempVar, steDefault),
								_IdentRef(steTempVar)
							)));
						} else {
							bindingProps += N4JSFactory.eINSTANCE.createBindingProperty => [ newBindingProp |
								if (localName != importedName) {
									newBindingProp.declaredName = _LiteralOrComputedPropertyName(importedName);
								}
								newBindingProp.value = N4JSFactory.eINSTANCE.createBindingElement => [ newBindingElem |
									newBindingElem.varDecl = _VariableDeclaration(localName);
								]
							];
						}
					}
					NamespaceImportSpecifier: {
						val namespaceName = importSpec.alias;
						varDecls += _VariableDeclaration(namespaceName, _IdentRef(steTempVar));
					}
					default: {
						throw new IllegalStateException("unsupported subclass of ImportSpecifier: " + importSpec.eClass.name);
					}
				}
			}
		}
	}

	def private boolean requiresRewrite(TModule targetModule) {
		return !n4jsLanguageHelper.isES6Module(state.index, targetModule);
	}

	def private String computeNameForIntermediateDefaultImport(TModule targetModule) {
		var packageNameRaw = targetModule.packageName;
		if (packageNameRaw !== null) {
			val n4jsdScopeWithSep = N4JSGlobals.N4JSD_SCOPE + ProjectDescriptionUtils.NPM_SCOPE_SEPARATOR;
			if (packageNameRaw.startsWith(n4jsdScopeWithSep)) {
				packageNameRaw = packageNameRaw.substring(n4jsdScopeWithSep.length);
			}
		}

		val packageName = Strings.toIdentifier(packageNameRaw, '_');
		val moduleName = Strings.toIdentifier(targetModule.qualifiedName, '_');
		val baseName = "$cjsImport__" + packageName + "__" + moduleName;

		var idx = 0;
		var candidate = baseName;
		// TODO: we won't find name conflicts with SymbolTableEntryOriginals (requires refactoring in TranspilerState.STECache)
		while (getSymbolTableEntryInternal(candidate, false) !== null) {
			idx++;
			candidate = baseName + idx;
		}
		return candidate;
	}
}
