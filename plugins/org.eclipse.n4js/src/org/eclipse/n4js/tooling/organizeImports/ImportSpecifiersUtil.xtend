/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tooling.organizeImports

import com.google.common.collect.Lists
import java.util.List
import java.util.function.Consumer
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.DefaultImportSpecifier
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.ts.types.AbstractNamespace
import org.eclipse.n4js.ts.types.ElementExportDefinition
import org.eclipse.n4js.ts.types.ModuleExportDefinition
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.RecursionGuard
import org.eclipse.n4js.validation.JavaScriptVariantHelper

/**
 * Utilities for ImportSpecifiers
 */
class ImportSpecifiersUtil {

	/**
	 * @return {@link List} of {@link ImportProvidedElement}s describing imported elements
	 */
	public static def List<ImportProvidedElement> mapToImportProvidedElements(
		List<ImportSpecifier> importSpecifiers, JavaScriptVariantHelper jsVariantHelper
	) {
		return importSpecifiers.map(
			specifier |
				switch (specifier) {
					NamespaceImportSpecifier:
						return namespaceToProvidedElements(jsVariantHelper, specifier)
					NamedImportSpecifier:
						return newArrayList(
							new ImportProvidedElement(specifier.usedName, specifier.importedElementName,
								specifier as ImportSpecifier,
								N4JSLanguageUtils.isHollowElement(specifier.importedElement, jsVariantHelper)))
					default:
						return emptyList
				}
		).flatten.toList
	}

	/** Map all exported elements from namespace target module to the import provided elements. */
	private static def List<ImportProvidedElement> namespaceToProvidedElements(JavaScriptVariantHelper jsVariantHelper, NamespaceImportSpecifier specifier) {
		val importedModule = specifier.importedModule;
		if (importedModule === null || importedModule.eIsProxy)
			return emptyList

		val importProvidedElements = newArrayList
		// add import provided element for a namespace itself
		importProvidedElements.add(new ImportProvidedElement(specifier.alias,
			computeNamespaceActualName(specifier), specifier, false));

		val localNamesAdded = newHashSet;
		collectProvidedElements(importedModule, new RecursionGuard(), [ exportDef |
			val localName = specifier.importedElementName(exportDef);
			// function overloading and declaration merging in .d.ts can lead to multiple elements of same name
			// being imported via a single namespace import -> to avoid showing bogus "duplicate import" errors
			// in those cases we need to avoid adding more than one ImportProvidedElement in those cases:
			// TODO IDE-3604 no longer required for function overloading; should probably be removed once declaration merging is supported
			if (localNamesAdded.add(localName)) {
				importProvidedElements.add(
					new ImportProvidedElement(localName, exportDef.exportedName,
						specifier, N4JSLanguageUtils.isHollowElement(exportDef.exportedElement, jsVariantHelper)));
			}
		]);

		return importProvidedElements
	}

	private static def void collectProvidedElements(AbstractNamespace namespace, RecursionGuard<TModule> guard, Consumer<ElementExportDefinition> consumer) {
		for (exportDef : Lists.reverse(namespace.exportDefinitions)) {
			if (exportDef instanceof ElementExportDefinition) {
				consumer.accept(exportDef);
			} else if (exportDef instanceof ModuleExportDefinition) {
				val exportedModule = exportDef.exportedModule;
				if (exportedModule !== null && !exportedModule.eIsProxy) {
					if (guard.tryNext(exportedModule)) {
						collectProvidedElements(exportedModule, guard, consumer);
					}
				}
			}
		}
	}

	/**
	 * Computes 'actual' name of the namespace for {@link ImportProvidedElement} entry.
	 * If processed namespace refers to unresolved module, will return dummy name,
	 * otherwise returns artificial name composed of prefix and target module qualified name
	 *
	 */
	public static def String computeNamespaceActualName(NamespaceImportSpecifier specifier) {
		if (specifier.importedModule.eIsProxy)
			ImportProvidedElement.NAMESPACE_PREFIX + specifier.hashCode
		else
			ImportProvidedElement.NAMESPACE_PREFIX + specifier.importedModule.qualifiedName.toString
	}

	/**
	 * Computes exported name of the element imported by this specifier.
	 */
	public static def String importedElementName(NamedImportSpecifier specifier) {
		if (specifier instanceof DefaultImportSpecifier) {
			return N4JSLanguageConstants.EXPORT_DEFAULT_NAME;
		}

		val element = specifier.importedElement
		if (element === null)
			return "<" + specifier.importedElementAsText + ">(null)"

		if (element.eIsProxy) {
			if (specifier.declaredDynamic) {
				return specifier.importedElementAsText;
			}
			return "<" + specifier.importedElementAsText + ">(proxy)"
		}

		return specifier.importedElementAsText;
	}

	/** returns locally used name of element imported via {@link NamedImportSpecifier} */
	public static def String usedName(NamedImportSpecifier it) {
		if (alias === null) importedElementName else alias
	}

	/** returns locally used name of element imported via {@link NamespaceImportSpecifier} */
	public static def String importedElementName(NamespaceImportSpecifier is, ElementExportDefinition exportDef) {
		is.alias + "." + exportDef.exportedName
	}

	public static def TModule importedModule(ImportSpecifier it) {
		(eContainer as ImportDeclaration).module
	}

	/**
	 * Returns true if the module that is target of the import declaration containing provided import specifier is invalid (null, proxy, no name).
	 * Additionally for {@link NamedImportSpecifier} instances checks if linker failed to resolve target (is null, proxy, or has no name)
	 *
	 * @param spec - the ImportSpecifier to investigate
	 * @return true import looks broken
	 * */
	public static def boolean isBrokenImport(ImportSpecifier spec) {
		return isBrokenImport(spec.eContainer as ImportDeclaration, spec);
	}

	/**
	 * Returns true iff the target module of the given import declaration is invalid (null, proxy, no name).
	 * Import specifiers are not checked.
	 */
	public static def boolean isBrokenImport(ImportDeclaration decl) {
		return isBrokenImport(decl, null);
	}

	private static def boolean isBrokenImport(ImportDeclaration decl, ImportSpecifier spec) {
		val module = decl.module;

		// check target module
		if (module === null || module.eIsProxy || module.qualifiedName.isNullOrEmpty)
			return true

		// check import specifier
		if (spec instanceof NamedImportSpecifier && !spec.declaredDynamic) {
			val nis = spec as NamedImportSpecifier;
			if (nis === null || nis.eIsProxy || nis.importedElementAsText.isNullOrEmpty)
				return true

			// check what object that is linked
			val imported = nis.importedElement
			if (imported === null)
				return true
			if (imported.eIsProxy)
				return true
		}

		return false
	}
}
