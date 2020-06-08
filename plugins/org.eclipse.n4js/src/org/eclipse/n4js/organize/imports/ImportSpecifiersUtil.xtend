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
package org.eclipse.n4js.organize.imports

import java.util.List
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.ts.types.TExportableElement

/**
 * Utilities for ImportSpecifiers
 */
class ImportSpecifiersUtil {

	/**
	 * @return {@link List} of {@link ImportProvidedElement}s describing imported elements
	 */
	public static def List<ImportProvidedElement> mapToImportProvidedElements(List<ImportSpecifier> importSpecifiers) {
		return importSpecifiers.map(
			specifier |
				switch (specifier) {
					NamespaceImportSpecifier:
						return namespaceToProvidedElements(specifier)
					NamedImportSpecifier:
						return newArrayList(
							new ImportProvidedElement(specifier.usedName, specifier.importedElementName,
								specifier as ImportSpecifier))
					default:
						return emptyList
				}
		).flatten.toList
	}

	/** Map all exported elements from namespace target module to the import provided elements. */
	private static def namespaceToProvidedElements(NamespaceImportSpecifier specifier) {
		if (specifier.importedModule === null)
			return emptyList

		val importProvidedElements = newArrayList
		// add import provided element for a namespace itself
		importProvidedElements.add(
			new ImportProvidedElement(specifier.alias, computeNamespaceActualName(specifier), specifier))

		val topExportedTypes = specifier.importedModule.topLevelTypes.filter[isExported].map[it as TExportableElement]
		val topExportedVars = specifier.importedModule.variables.filter[it.isExported].map[it as TExportableElement];
		val topExported = topExportedTypes + topExportedVars

		topExported.forEach [ type |
			importProvidedElements.add(
				new ImportProvidedElement(specifier.importedElementName(type), type.exportedName,
					specifier as ImportSpecifier))
		]
		return importProvidedElements
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
		val element = specifier.importedElement
		if (element === null)
			return "<" + specifier.importedElementAsText + ">(null)"

		if (element.eIsProxy)
			return "<" + specifier.importedElementAsText + ">(proxy)"

		return element.exportedName
	}

	/** returns locally used name of element imported via {@link NamedImportSpecifier} */
	public static def usedName(NamedImportSpecifier it) {
		if (alias === null) importedElementName else alias
	}

	/** returns locally used name of element imported via {@link NamespaceImportSpecifier} */
	public static def importedElementName(NamespaceImportSpecifier is, TExportableElement element) {
		is.alias + "." + element.exportedName
	}

	public static def importedModule(ImportSpecifier it) {
		(eContainer as ImportDeclaration).module
	}

	/**
	 * Returns true if the module that is target of the import declaration containing provided import specifier is invalid (null, proxy, no name).
	 * Additionally for {@link NamedImportSpecifier} instances checks if linker failed to resolve target (is null, proxy, or has no name)
	 *
	 * @param spec - the ImportSpecifier to investigate
	 * @return true import looks broken
	 * */
	public static def isBrokenImport(ImportSpecifier spec) {
		return isBrokenImport(spec.eContainer as ImportDeclaration, spec);
	}

	/**
	 * Returns true iff the target module of the given import declaration is invalid (null, proxy, no name).
	 * Import specifiers are not checked.
	 */
	public static def isBrokenImport(ImportDeclaration decl) {
		return isBrokenImport(decl, null);
	}

	private static def isBrokenImport(ImportDeclaration decl, ImportSpecifier spec) {
		val module = decl.module;

		// check target module
		if (module === null || module.eIsProxy || module.qualifiedName.isNullOrEmpty)
			return true

		// check import specifier
		if (spec instanceof NamedImportSpecifier) {
			val nis = spec
			if (nis === null || nis.eIsProxy || nis.importedElementAsText.isNullOrEmpty)
				return true

			// check what object that is linked
			val imported = nis.importedElement
			if (imported === null)
				return true
			if (imported.eIsProxy)
				return true
			if (imported.exportedName.isNullOrEmpty)
				return true
		}

		return false
	}
}
