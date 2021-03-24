/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tooling.organizeImports

import java.util.List
import java.util.Set
import org.eclipse.emf.common.notify.Adapter
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.ts.types.TModule

/**
 * Register holding results of analyzing {@link ImportDeclaration}s
 * and contained {@link ImportSpecifier}s.
 */
class RecordingImportState {

	public List<Pair<ImportDeclaration, List<ImportDeclaration>>> duplicatedImportDeclarations = newArrayList();

	public List<ImportSpecifier> unusedImports = newArrayList();
	public List<ImportSpecifier> brokenImports = newArrayList();

	/*TODO refactor nested collections into specialized types */
	public List<Pair<Pair<String, TModule>, List<ImportProvidedElement>>> duplicateImportsOfSameElement = newArrayList();
	public List<Pair<String, List<ImportProvidedElement>>> localNameCollision = newArrayList();

	public List<ImportProvidedElement> allUsedTypeNameToSpecifierTuples = newArrayList();

	def registerDuplicatesOfImportDeclaration(ImportDeclaration declaration, List<ImportDeclaration> duplicates) {
		duplicatedImportDeclarations.add(declaration -> duplicates);
	}

	def boolean isDuplicatingImportDeclaration(ImportDeclaration importDeclaration){
		duplicatedImportDeclarations.exists[dupePair|
			dupePair.key.module === importDeclaration.module &&
			dupePair.value.contains(importDeclaration)
		]
	}

	def registerUnusedImport(ImportSpecifier specifier) {
		unusedImports.add(specifier);
	}

	def registerBrokenImport(ImportSpecifier specifier) {
		brokenImports.add(specifier);
	}

	/**
	 * Removes any information stored in the register.
	 */
	def cleanup() {

		// clean own
		#[unusedImports, brokenImports, allUsedTypeNameToSpecifierTuples].forEach[clear]

		localNameCollision.clear
		duplicatedImportDeclarations.clear
		duplicateImportsOfSameElement.clear

		// cut ref:
		allUsedTypeNameToSpecifierTuples = null;
	}

	def registerAllUsedTypeNameToSpecifierTuples(List<ImportProvidedElement> pairs) {
		allUsedTypeNameToSpecifierTuples = pairs
	}

	// remove all known unused imports from the passed in list.
	def removeDuplicatedImportsOfSameelement(List<ImportDeclaration> declarations, Adapter nodelessMarker ) {
		duplicateImportsOfSameElement.forEach[e|
			e.value.forEach[e2|
				newArrayList(e2.importSpecifier).removeFrom(declarations, nodelessMarker)
			]
		]
	}

	def removeLocalNameCollisions(List<ImportDeclaration> declarations, Adapter nodelessMarker ) {
		localNameCollision.forEach[e|
			e.value.forEach[e2|
				newArrayList(e2.importSpecifier).removeFrom(declarations, nodelessMarker)
			]
		]
		return
	}

	def removeDuplicatedImportDeclarations(List<ImportDeclaration> declarations) {
		duplicatedImportDeclarations.forEach[decl2dupes|
			declarations.removeAll(decl2dupes.value)
		]
	}

	def removeUnusedImports(List<ImportDeclaration> declarations, Adapter nodelessMarker ) {
		unusedImports.removeFrom(declarations, nodelessMarker)
	}

	def removeBrokenImports(List<ImportDeclaration> declarations, Adapter nodelessMarker ) {
		brokenImports.removeFrom(declarations, nodelessMarker )
	}

	private def removeFrom(List<ImportSpecifier> tobeRemoved, List<ImportDeclaration> declarations, Adapter nodelessMarker ) {
		//remove from declarations
		tobeRemoved.forEach [
			switch (it) {
				NamespaceImportSpecifier:
					declarations.remove(it.eContainer)
				NamedImportSpecifier: {
					val imp = it.eContainer as ImportDeclaration
					if (imp !== null) {
						if (imp.importSpecifiers !== null) {
							imp.importSpecifiers.remove(it);
							// mark as modified to rebuild whole node.
							imp.eAdapters.add(nodelessMarker)
						}
						if (imp.importSpecifiers === null || imp.importSpecifiers.empty) {
							declarations.remove(imp)
						}
					}
				}
			}
		]
	}

	/**
	 * Returns set of names, for which there are broken import specifiers. Happens after broken
	 * imports are removed, which makes name (usually IdRef/TypeRef) refer to ImportSpecifier, which
	 * is being removed from document. Providing list of broken names, allows organize imports to fix those.
	 */
	def Set<String> calcRemovedImportedNames() {
		var ret = newHashSet()
		for (e : allUsedTypeNameToSpecifierTuples) {
			if (e.used && e.importSpecifier.eContainer === null) {
				ret.add(e.getLocalName())
			}
		}
		return ret
	}

	def registerDuplicateImportsOfSameElement(String name, TModule module, List<ImportProvidedElement> elements) {
		duplicateImportsOfSameElement.add((name->module)->elements);
	}

	def registerLocalNameCollision(String string, List<ImportProvidedElement> elements) {
		val key = localNameCollision.findFirst[it.key == string];
		if(key !== null){
			key.value.addAll(elements)
		}else{
			localNameCollision.add(string -> elements.toList)
		}
	}

}
