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
package org.eclipse.n4js.ui.organize.imports

import java.util.Collections
import java.util.Comparator
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier

/**
 * Provides methods for import declarations sorting.
 */
class ImportsSorter {

	/**
	 * Sorting a List of import declarations (mixed content Named / Namespace)
	 * Order is: First all Named imports, then all Namespace imports.
	 * Exception: bare imports will be moved to the front and will keep their order relative to themselves.
	 */
	final static def sortByImport(List<ImportDeclaration> declarations) {
		// remember original index of all bare imports in 'declarations'
		val bareImport2OriginalIndex = newHashMap;
		declarations.filter[bare].forEach[decl, idx | bareImport2OriginalIndex.put(decl, idx)];

		// perform actual sorting
		declarations.sort(new Comparator<ImportDeclaration>() {
			override compare(ImportDeclaration o1, ImportDeclaration o2) {
				// 1) sort by "bareness" (i.e. move bare imports to the front)
				val cmpBareness = Boolean.compare(o1.bare, o2.bare) * -1;
				if (cmpBareness !== 0) {
					return cmpBareness;
				}
				// 2) sort bare imports by their original index (i.e. keep their order relative to themselves)
				if (o1.bare) {
					// both are bare imports:
					return Integer.compare(bareImport2OriginalIndex.get(o1), bareImport2OriginalIndex.get(o2));
				}
				// both are non-bare imports:
				// 3) sort by names ...
				switch ( o1.importSpecifiers.get(0) ) {
					NamespaceImportSpecifier: {
						if (o2.importSpecifiers.get(0) instanceof NamespaceImportSpecifier) {
							compModules(o1, o2)

						} else {
							1; // positive, since wildcards are lasts
						}
					}
					NamedImportSpecifier: {
						if (o2.importSpecifiers.get(0) instanceof NamespaceImportSpecifier) {
							-1; // negative, wildcard last.
						} else {
							var cmp = compModules(o1, o2)
							if (cmp === 0) {
								compNamedImports(o1.importSpecifiers, o2.importSpecifiers)
							} else
								cmp
						}
					}
					default:
						throw new UnsupportedOperationException("Unknown ImportSpecifier")
				}
			}
		}) // end sort.
	}

	/**
	 * Sorting a List of import specifiers based on used names.
	 * Order is: First all Named imports, then all Namespace imports.
	 */
	final static def sortByName(List<ImportSpecifier> list) {
		Collections.sort(list, new Comparator<ImportSpecifier>() {
			override compare(ImportSpecifier o1, ImportSpecifier o2) {
				if (o1 instanceof NamespaceImportSpecifier) {
					return 1;
				} else if (o2 instanceof NamespaceImportSpecifier) {
					return -1;
				} else {
					return compNamedImport(o1 as NamedImportSpecifier, o2 as NamedImportSpecifier);
				}
			}
		})
	}

	/** Compare list of NamedImports.
	 *  Comparing elements in sequential order until inequality is found.
	 */
	final static private def int compNamedImports(EList<ImportSpecifier> l1, EList<ImportSpecifier> l2) {
		val comparable_elements = Math.min(l1.size, l2.size)
		for (var int i = 0; i < comparable_elements; i++) {
			val cmp = compNamedImport(l1.get(i) as NamedImportSpecifier, l2.get(i) as NamedImportSpecifier)
			if (cmp !== 0) return cmp
		}

		// longer list below:
		return l1.size - l2.size
	}

	/** Compares two NamedImport specifier: e.g. "Z as A" <--> "X as B" */
	final static private def int compNamedImport(NamedImportSpecifier o1, NamedImportSpecifier o2) {
		// o1.findActualNodeFor().tokenText. compareTo( o2.findActualNodeFor.tokenText )
		var name1 = o1?.importedElement?.name
		var name2 = o2?.importedElement?.name
		val cmp1 = (name1 ?: "").compareTo(name2 ?: "")
		if (cmp1 == 0) {
			(o1.alias ?: "").compareTo(o2.alias ?: "")
		} else
			cmp1
	}

	/** compare based on Qualified name */
	final static private def int compModules(ImportDeclaration o1, ImportDeclaration o2) {
		(o1.module.qualifiedName ?: "").compareTo(o2.module.qualifiedName ?: "")
	}
}
