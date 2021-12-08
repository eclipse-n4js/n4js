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
import java.util.List
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.n4JS.BindingProperty
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4JSFactory
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.VariableStatementKeyword
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesAfter
import org.eclipse.n4js.transpiler.TransformationDependency.ExcludesBefore
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.N4JSLanguageHelper
import org.eclipse.n4js.utils.ProjectDescriptionUtils
import org.eclipse.n4js.utils.Strings

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Since switching to node's native support for ES6 modules, we have to re-write all import declarations
 * that import from an old CommonJS module.
 */
@ExcludesAfter(SanitizeImportsTransformation) // CommonJsImportsTransformation must not run before SanitizeImportsTransformation
@ExcludesBefore(ModuleWrappingTransformation) // CommonJsImportsTransformation must not run after ModuleWrappingTransformation
class CommonJsImportsTransformation extends Transformation {

	@Inject
	private N4JSLanguageHelper n4jsLanguageHelper;

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
		val importDeclsPerImportedModule = FluentIterable.from(state.im.scriptElements)
			.filter(ImportDeclaration)
			.index[importDecl | state.info.getImportedModule(importDecl)];

		val varStmnts = newArrayList;
		for (targetModule : importDeclsPerImportedModule.keySet) {
			varStmnts += transformImportDecl(targetModule, importDeclsPerImportedModule.get(targetModule));
		}

		val lastImportDecl = state.im.scriptElements.filter(ImportDeclaration).last();
		insertAfter(lastImportDecl, varStmnts);
	}

	def private List<VariableStatement> transformImportDecl(TModule targetModule, List<ImportDeclaration> allImportDeclsForThisModule) {
		if (allImportDeclsForThisModule.empty) {
			return #[];
		}
		if (!requiresRewrite(targetModule)) {
			return #[];
		}

		// special case with a simpler approach:
		if (allImportDeclsForThisModule.size === 1 && allImportDeclsForThisModule.head.importSpecifiers.size === 1) {
			val importSpec = allImportDeclsForThisModule.head.importSpecifiers.head;
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

		val steName = computeNameForIntermediateDefaultImport(targetModule);
		val ste = getSymbolTableEntryInternal(steName, true);

		val bindingProps = <BindingProperty>newArrayList;
		val varDecls = <VariableDeclaration>newArrayList;

		for (importDecl : allImportDeclsForThisModule) {
			for (importSpec : importDecl.importSpecifiers) {
				switch (importSpec) {
					NamedImportSpecifier: { // including DefaultImportSpecifier
						val importedName = importSpec.importedElementAsText;
						val localName = importSpec.alias ?: importedName;
						bindingProps += N4JSFactory.eINSTANCE.createBindingProperty => [ newBindingProp |
							val isDefaultImport = importSpec.isDefaultImport || importedName == "default";
							if (isDefaultImport) {
								newBindingProp.declaredName = _LiteralOrComputedPropertyName("default");
							} else if (localName != importedName) {
								newBindingProp.declaredName = _LiteralOrComputedPropertyName(importedName);
							}
							newBindingProp.value = N4JSFactory.eINSTANCE.createBindingElement => [ newBindingElem |
								newBindingElem.varDecl = _VariableDeclaration(localName);
								if (isDefaultImport) {
									newBindingElem.varDecl.expression = _IdentRef(ste);
								}
							]
						];
					}
					NamespaceImportSpecifier: {
						val namespaceName = importSpec.alias;
						varDecls += _VariableDeclaration(namespaceName, _IdentRef(ste));
					}
					default: {
						throw new IllegalStateException("unsupported subclass of ImportSpecifier: " + importSpec.eClass.name);
					}
				}
			}
		}

		val result = _VariableStatement(VariableStatementKeyword.CONST);
		if (!varDecls.empty) {
			result.varDeclsOrBindings += varDecls;
		}
		if (!bindingProps.empty) {
			result.varDeclsOrBindings += _VariableBinding(bindingProps, _IdentRef(ste));
		}

		val firstImportDecl = allImportDeclsForThisModule.head;
		removeAll(firstImportDecl.importSpecifiers);
		firstImportDecl.importSpecifiers += N4JSFactory.eINSTANCE.createDefaultImportSpecifier() => [
			importedElementAsText = ste.name;
			flaggedUsedInCode = true;
			retainedAtRuntime = true;
		];
		removeAll(allImportDeclsForThisModule.drop(1));

		return #[ result ];
	}

	def private boolean requiresRewrite(TModule targetModule) {
		return !n4jsLanguageHelper.isES6Module(targetModule.eResource);
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
