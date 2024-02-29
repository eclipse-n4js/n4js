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

import static org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.computeNamespaceActualName;
import static org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.isBrokenImport;
import static org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.mapToImportProvidedElements;
import static org.eclipse.n4js.tooling.organizeImports.ScriptDependencyResolver.usedDependenciesTypeRefs;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forEach;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.tail;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.postprocessing.ASTMetaInfoCache;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * Analyzes all imports in a script. Builds up a data structure of {@link RecordingImportState} to capture the findings.
 */
public class ImportStateCalculator {

	/**
	 * Algorithm to check the Model for Issues with Imports.
	 *
	 * @returns {@link RecordingImportState}
	 */
	public RecordingImportState calculateImportstate(Script script) {
		RecordingImportState reg = new RecordingImportState();

		ASTMetaInfoCache astMetaInfoCache = ((N4JSResource) script.eResource()).getASTMetaInfoCacheVerifyContext();

		// Calculate Available
		Iterable<ImportDeclaration> importDeclarationsALL = filter(script.getScriptElements(), ImportDeclaration.class);

		registerDuplicatedImoprtDeclarationsFrom(reg, importDeclarationsALL);

		List<ImportSpecifier> importSpecifiersUnAnalyzed = toList(
				flatten(map(filter(importDeclarationsALL, it -> !reg.isDuplicatingImportDeclaration(it)),
						decl -> decl.getImportSpecifiers())));

		// markDuplicatingSpecifiersAsUnused(importSpecifiersUnAnalyzed)

		// collect all unused if stable
		registerUnusedAndBrokenImports(reg, importSpecifiersUnAnalyzed, astMetaInfoCache);

		List<ImportProvidedElement> importProvidedElements = mapToImportProvidedElements(
				toList(filter(importSpecifiersUnAnalyzed, it -> !(reg.brokenImports.contains(it)))));

		// refactor into specific types, those are essentially Maps holding elements in insertion order (keys and
		// values)
		List<Pair<String, List<ImportProvidedElement>>> lN2IPE = new ArrayList<>();
		List<Pair<TModule, List<ImportProvidedElement>>> lM2IPE = new ArrayList<>();

		// TODO refactor this, those composed collections should be encapsulated as specific types with proper get/set
		// methods
		for (ImportProvidedElement ipe : importProvidedElements) {
			Pair<String, List<ImportProvidedElement>> pN2IPE = findFirst(lN2IPE,
					p -> Objects.equals(p.getKey(), ipe.getCollisionUniqueName()));

			if (pN2IPE != null) {
				pN2IPE.getValue().add(ipe);
			} else {
				List<ImportProvidedElement> al = new ArrayList<>();
				al.add(ipe);
				lN2IPE.add(Pair.of(ipe.getCollisionUniqueName(), al));
			}
			Pair<TModule, List<ImportProvidedElement>> pM2IPE = findFirst(lM2IPE,
					p -> Objects.equals(p.getKey(), ipe.getImportedModule()));
			if (pM2IPE != null) {
				pM2IPE.getValue().add(ipe);
			} else {
				List<ImportProvidedElement> al = new ArrayList<>();
				al.add(ipe);
				lM2IPE.add(Pair.of(ipe.getImportedModule(), al));
			}
		}

		registerUsedImportsLocalNamesCollisions(reg, lN2IPE);

		registerUsedImportsDuplicatedImportedElements(reg, lM2IPE);

		reg.registerAllUsedTypeNameToSpecifierTuples(importProvidedElements);

		// usages in script:
		List<ScriptDependency> externalDep = usedDependenciesTypeRefs(script);

		// mark used imports as seen in externalDep:
		for (ScriptDependency scriptDep : externalDep) {
			TModule mod = scriptDep.dependencyModule;
			Pair<TModule, List<ImportProvidedElement>> pM2IPE = findFirst(lM2IPE, p -> p.getKey() == mod);
			if (pM2IPE != null) {
				forEach(filter(pM2IPE.getValue(), ipe -> Objects.equals(ipe.getExportedName(), scriptDep.actualName)
						&& Objects.equals(ipe.getLocalName(), scriptDep.localName)), it -> it.markUsed());
			}
		}

		/*
		 * TODO review ambiguous imports looks like reference to type that is ambiguously imported can happen only if
		 * there are errors in the import declaratiosn, so should ignore those references and resolve issues in the
		 * imports only? Or can this information be used to resolve those issues in smarter way?
		 */
		// localname2importprovider.markAmbigousImports(script)

		return reg;
	}

	/**
	 * Registers conflicting or duplicate (based on imported elements) imports in the provided
	 * {@link RecordingImportState}
	 */
	private void registerUsedImportsDuplicatedImportedElements(RecordingImportState reg,
			List<Pair<TModule, List<ImportProvidedElement>>> module2imported) {

		for (Pair<TModule, List<ImportProvidedElement>> pair : module2imported) {
			List<ImportProvidedElement> fromMod = pair.getValue();

			// find duplicates in actual name, report them as duplicateImport
			ArrayListMultimap<String, ImportProvidedElement> name2Import = ArrayListMultimap.create();
			for (ImportProvidedElement ipe : fromMod) {
				name2Import.put(ipe.getDuplicateImportUniqueName(), ipe);
			}

			for (String name : name2Import.keySet()) {
				List<ImportProvidedElement> v = name2Import.get(name);
				String actName = v.get(0).getExportedName();
				List<ImportProvidedElement> x = toList(filter(v, internalIPE -> {
					// filter out ImportProvidedElements that reflect Namespace element itself
					ImportSpecifier specifier = internalIPE.getImportSpecifier();
					if (specifier instanceof NamespaceImportSpecifier) {
						return !Objects.equals(internalIPE.getExportedName(),
								computeNamespaceActualName((NamespaceImportSpecifier) specifier));
					} else {
						return true;
					}
				}));
				if (x.size() > 1) {
					reg.registerDuplicateImportsOfSameElement(actName, pair.getKey(), x);
				}
			}
		}
	}

	/**
	 * Registers conflicting or duplicate (based on local name checks) imports in the provided
	 * {@link RecordingImportState}
	 */
	private void registerUsedImportsLocalNamesCollisions(RecordingImportState reg,
			List<Pair<String, List<ImportProvidedElement>>> localname2importprovider) {
		for (Pair<String, List<ImportProvidedElement>> pair : localname2importprovider) {
			if (pair.getValue().size() > 1) {
				reg.registerLocalNameCollision(pair.getKey(), pair.getValue());
			}
		}
	}

	/**
	 * analyzes provided {@link ImportDeclaration}s, if it finds *exact* duplicates, adds them to the
	 * {@link RecordingImportState#duplicatedImportDeclarations}
	 */
	private void registerDuplicatedImoprtDeclarationsFrom(RecordingImportState reg,
			Iterable<ImportDeclaration> importDeclarations) {
		Multimap<TModule, ImportDeclaration> nsisImports = LinkedHashMultimap.create();
		Multimap<TModule, ImportDeclaration> nisImports = LinkedHashMultimap.create();
		for (ImportDeclaration id : importDeclarations) {
			if (findFirst(id.getImportSpecifiers(), is -> is instanceof NamespaceImportSpecifier) != null) {
				nsisImports.put(id.getModule(), id);
			}
			if (findFirst(id.getImportSpecifiers(), is -> is instanceof NamedImportSpecifier) != null) {
				nisImports.put(id.getModule(), id);
			}
		}

		for (TModule module : nsisImports.keySet()) {
			registerImportDeclarationsWithNamespaceImportsForModule(nsisImports.get(module), reg);
		}

		for (TModule module : nisImports.keySet()) {
			registerImportDeclarationsWithNamedImportsForModule(nisImports.get(module), reg);
		}

		// importDeclarations
		// .filter[id| id.importSpecifiers.findFirst[is| is instanceof NamespaceImportSpecifier] !== null]
		// .groupBy[module]
		// .forEach[module, impDecls|
		// registerImportDeclarationsWithNamespaceImportsForModule(impDecls, reg)
		// ]
		//
		// for (ImportDeclaration id: importDeclarations ) {
		//
		// }
		// importDeclarations
		// .filter[id| id.importSpecifiers.findFirst[is| is instanceof NamedImportSpecifier] !== null]
		// .groupBy[module]
		// .forEach[module, impDecls|
		// registerImportDeclarationsWithNamedImportsForModule(impDecls, reg)
		// ]
	}

	private void registerImportDeclarationsWithNamespaceImportsForModule(
			Collection<ImportDeclaration> importDeclarations,
			RecordingImportState reg) {
		if (importDeclarations.size() < 2) {
			return;
		}

		List<ImportDeclaration> duplicates = new ArrayList<>();
		ImportDeclaration firstDeclaration = importDeclarations.iterator().next();
		String firstNamespaceName = head(filter(firstDeclaration.getImportSpecifiers(), NamespaceImportSpecifier.class))
				.getAlias();
		for (ImportDeclaration importDeclaration : tail(importDeclarations)) {
			String followingNamespaceName = head(
					filter(importDeclaration.getImportSpecifiers(), NamespaceImportSpecifier.class)).getAlias();
			if (Objects.equals(firstNamespaceName, followingNamespaceName)) {
				duplicates.add(importDeclaration);
			}
		}

		if (!duplicates.isEmpty()) {
			reg.registerDuplicatesOfImportDeclaration(firstDeclaration, duplicates);
		}
	}

	private void registerImportDeclarationsWithNamedImportsForModule(Collection<ImportDeclaration> importDeclarations,
			RecordingImportState reg) {
		if (importDeclarations.size() < 2) {
			return;
		}

		List<ImportDeclaration> duplicates = new ArrayList<>();
		ImportDeclaration firstDeclaration = importDeclarations.iterator().next();
		List<NamedImportSpecifier> firstDeclarationSpecifiers = toList(
				filter(firstDeclaration.getImportSpecifiers(), NamedImportSpecifier.class));
		for (ImportDeclaration importDeclaration : tail(importDeclarations)) {
			List<NamedImportSpecifier> followingDeclarationSpecifiers = toList(
					filter(importDeclaration.getImportSpecifiers(), NamedImportSpecifier.class));
			if ((!firstDeclarationSpecifiers.isEmpty())
					&& firstDeclarationSpecifiers.size() == followingDeclarationSpecifiers.size()) {
				if (allFollowingMatchByNameAndAlias(firstDeclarationSpecifiers, followingDeclarationSpecifiers)) {
					duplicates.add(importDeclaration);
				}
			}
		}

		if (!duplicates.isEmpty()) {
			reg.registerDuplicatesOfImportDeclaration(firstDeclaration, duplicates);
		}
	}

	private boolean allFollowingMatchByNameAndAlias(Iterable<NamedImportSpecifier> firstDeclarationSpecifiers,
			Iterable<NamedImportSpecifier> followingDeclarationSpecifiers) {

		for (NamedImportSpecifier namedImportSpecifier : firstDeclarationSpecifiers) {
			if (!exists(followingDeclarationSpecifiers,
					otherNamedImportSpecifier -> Objects.equals(namedImportSpecifier.getAlias(),
							otherNamedImportSpecifier.getAlias())
							&& Objects.equals(namedImportSpecifier.getImportedElement().getName(),
									otherNamedImportSpecifier.getImportedElement().getName()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Registers unused or broken (missing or unresolved imported module) import specifiers in the provided
	 * {@link RecordingImportState}
	 */
	private void registerUnusedAndBrokenImports(RecordingImportState reg, List<ImportSpecifier> importSpecifiers,
			ASTMetaInfoCache astMetaInfoCache) {
		for (ImportSpecifier is : importSpecifiers) {
			// avoid duplicate error messages
			if (!isNamedImportOfNonExportedElement(is, astMetaInfoCache) && !is.isFlaggedUsedInCode()) {
				reg.registerUnusedImport(is);
				if (isBrokenImport(is)) {
					reg.registerBrokenImport(is);
				}
			}
		}
	}

	private static boolean isNamedImportOfNonExportedElement(ImportSpecifier importSpec,
			ASTMetaInfoCache astMetaInfoCache) {
		return astMetaInfoCache
				.getLinkingIssueCodes(importSpec, N4JSPackage.Literals.NAMED_IMPORT_SPECIFIER__IMPORTED_ELEMENT)
				.contains(IssueCodes.IMP_NOT_EXPORTED.name());
	}
}
