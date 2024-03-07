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
package org.eclipse.n4js.tooling.organizeImports;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Register holding results of analyzing {@link ImportDeclaration}s and contained {@link ImportSpecifier}s.
 */
@SuppressWarnings("javadoc")
public class RecordingImportState {

	public List<Pair<ImportDeclaration, List<ImportDeclaration>>> duplicatedImportDeclarations = new ArrayList<>();

	public List<ImportSpecifier> unusedImports = new ArrayList<>();
	public List<ImportSpecifier> brokenImports = new ArrayList<>();

	/* TODO refactor nested collections into specialized types */
	public List<Pair<Pair<String, TModule>, List<ImportProvidedElement>>> duplicateImportsOfSameElement = new ArrayList<>();
	public List<Pair<String, List<ImportProvidedElement>>> localNameCollision = new ArrayList<>();

	public List<ImportProvidedElement> allUsedTypeNameToSpecifierTuples = new ArrayList<>();

	public void registerDuplicatesOfImportDeclaration(ImportDeclaration declaration,
			List<ImportDeclaration> duplicates) {
		duplicatedImportDeclarations.add(Pair.of(declaration, duplicates));
	}

	public boolean isDuplicatingImportDeclaration(ImportDeclaration importDeclaration) {
		return exists(duplicatedImportDeclarations,
				dupePair -> dupePair.getKey().getModule() == importDeclaration.getModule() &&
						dupePair.getValue().contains(importDeclaration));
	}

	public void registerUnusedImport(ImportSpecifier specifier) {
		unusedImports.add(specifier);
	}

	public void registerBrokenImport(ImportSpecifier specifier) {
		brokenImports.add(specifier);
	}

	/**
	 * Removes any information stored in the register.
	 */
	public void cleanup() {
		// clean own
		unusedImports.clear();
		brokenImports.clear();
		allUsedTypeNameToSpecifierTuples.clear();

		localNameCollision.clear();
		duplicatedImportDeclarations.clear();
		duplicateImportsOfSameElement.clear();

		// cut ref:
		allUsedTypeNameToSpecifierTuples = null;
	}

	public void registerAllUsedTypeNameToSpecifierTuples(List<ImportProvidedElement> pairs) {
		allUsedTypeNameToSpecifierTuples = pairs;
	}

	// remove all known unused imports from the passed in list.
	public void removeDuplicatedImportsOfSameelement(List<ImportDeclaration> declarations, Adapter nodelessMarker) {
		for (Pair<Pair<String, TModule>, List<ImportProvidedElement>> e : duplicateImportsOfSameElement) {
			for (ImportProvidedElement e2 : e.getValue()) {
				List<ImportSpecifier> l = new ArrayList<>();
				l.add(e2.getImportSpecifier());
				removeFrom(l, declarations, nodelessMarker);
			}
		}
	}

	public void removeLocalNameCollisions(List<ImportDeclaration> declarations, Adapter nodelessMarker) {
		for (Pair<String, List<ImportProvidedElement>> e : localNameCollision) {
			for (ImportProvidedElement e2 : e.getValue()) {
				List<ImportSpecifier> l = new ArrayList<>();
				l.add(e2.getImportSpecifier());
				removeFrom(l, declarations, nodelessMarker);
			}
		}
	}

	public void removeDuplicatedImportDeclarations(List<ImportDeclaration> declarations) {
		for (Pair<ImportDeclaration, List<ImportDeclaration>> decl2dupes : duplicatedImportDeclarations) {
			declarations.removeAll(decl2dupes.getValue());
		}
	}

	public void removeUnusedImports(List<ImportDeclaration> declarations, Adapter nodelessMarker) {
		removeFrom(unusedImports, declarations, nodelessMarker);
	}

	public void removeBrokenImports(List<ImportDeclaration> declarations, Adapter nodelessMarker) {
		removeFrom(brokenImports, declarations, nodelessMarker);
	}

	private void removeFrom(List<ImportSpecifier> tobeRemoved, List<ImportDeclaration> declarations,
			Adapter nodelessMarker) {

		// remove from declarations
		for (ImportSpecifier it : tobeRemoved) {
			if (it instanceof NamespaceImportSpecifier) {
				declarations.remove(it.eContainer());

			} else if (it instanceof NamedImportSpecifier) {
				ImportDeclaration imp = (ImportDeclaration) it.eContainer();
				if (imp != null) {
					if (imp.getImportSpecifiers() != null) {
						imp.getImportSpecifiers().remove(it);
						// mark as modified to rebuild whole node.
						imp.eAdapters().add(nodelessMarker);
					}
					if (imp.getImportSpecifiers() == null || imp.getImportSpecifiers().isEmpty()) {
						declarations.remove(imp);
					}
				}
			}
		}
	}

	/**
	 * Returns set of names, for which there are broken import specifiers. Happens after broken imports are removed,
	 * which makes name (usually IdRef/TypeRef) refer to ImportSpecifier, which is being removed from document.
	 * Providing list of broken names, allows organize imports to fix those.
	 */
	public Set<String> calcRemovedImportedNames() {
		Set<String> ret = new HashSet<>();
		for (ImportProvidedElement e : allUsedTypeNameToSpecifierTuples) {
			if (e.isUsed() && e.getImportSpecifier().eContainer() == null) {
				ret.add(e.getLocalName());
			}
		}
		return ret;
	}

	public void registerDuplicateImportsOfSameElement(String name, TModule module,
			List<ImportProvidedElement> elements) {
		duplicateImportsOfSameElement.add(Pair.of(Pair.of(name, module), elements));
	}

	public void registerLocalNameCollision(String string, List<ImportProvidedElement> elements) {
		Pair<String, List<ImportProvidedElement>> key = findFirst(localNameCollision,
				it -> Objects.equals(it.getKey(), string));
		if (key != null) {
			key.getValue().addAll(elements);
		} else {
			localNameCollision.add(Pair.of(string, elements));
		}
	}

}
