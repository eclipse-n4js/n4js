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
		importSpecifiers.map(
			specifier |
				switch (specifier) {
					NamespaceImportSpecifier:
						if (specifier.importedModule !== null) {
							val importProvidedElements = newArrayList
							// add import provided element for a namespace itself
							importProvidedElements.add(
								new ImportProvidedElement(specifier.alias, computeNamespaceActualName(specifier),
									specifier))

							val topExported = specifier.importedModule.topLevelTypes.filter[isExported].map [
								it as TExportableElement
							] + specifier.importedModule.variables.filter[it.isExported].map[it as TExportableElement];

							topExported.forEach [ type |
								importProvidedElements.add(
									new ImportProvidedElement(specifier.importedElementName(type), type.exportedName,
										specifier as ImportSpecifier))
							]
							return importProvidedElements
						} else
							emptyList
					NamedImportSpecifier:
						newArrayList(
							new ImportProvidedElement(specifier.usedName, specifier.importedElementName,
								specifier as ImportSpecifier))
					default:
						emptyList
				}
		).flatten.toList
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
			return "<unkown>"

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
}
