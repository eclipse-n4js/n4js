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

import com.google.common.collect.ArrayListMultimap
import java.util.List
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.Log

import static extension org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.*
import static extension org.eclipse.n4js.tooling.organizeImports.ScriptDependencyResolver.*

/**
 * Analyzes all imports in a script. Builds up a data structure of {@link RecordingImportState} to capture the findings.
 */
@Log
class ImportStateCalculator {

	/**
	 * Algorithm to check the Model for Issues with Imports.
	 * @returns {@link RecordingImportState}
	 */
	public def RecordingImportState calculateImportstate(Script script) {
		val reg = new RecordingImportState();

		// Calculate Available
		val importDeclarationsALL = script.scriptElements.filter(ImportDeclaration)

		reg.registerDuplicatedImoprtDeclarationsFrom(importDeclarationsALL)

		val importSpecifiersUnAnalyzed = importDeclarationsALL.filter[!reg.isDuplicatingImportDeclaration(it)].map[importSpecifiers].flatten.toList

//		markDuplicatingSpecifiersAsUnused(importSpecifiersUnAnalyzed)

		// collect all unused if stable
		reg.registerUnusedAndBrokenImports(importSpecifiersUnAnalyzed)

		val List<ImportProvidedElement> importProvidedElements = importSpecifiersUnAnalyzed.filter[!(reg.brokenImports.contains(it))].toList.mapToImportProvidedElements

		//refactor into specific types, those are essentially Maps holding elements in insertion order (keys and values)
		val List<Pair<String, List<ImportProvidedElement>>> lN2IPE = newArrayList()
		val List<Pair<TModule, List<ImportProvidedElement>>> lM2IPE = newArrayList()

		//TODO refactor this, those composed collections should be encapsulated as specific types with proper get/set methods
		for (ipe : importProvidedElements) {
			val pN2IPE = lN2IPE.findFirst[it.key == ipe.getLocalName()];
			if(pN2IPE !== null){
				pN2IPE.value.add(ipe)
			}else{
				lN2IPE.add(ipe.getLocalName() -> newArrayList(ipe))
			}
			val pM2IPE = lM2IPE.findFirst[it.key == ipe.importedModule];
			if(pM2IPE !== null){
				pM2IPE.value.add(ipe)
			}else{
				lM2IPE.add(ipe.importedModule -> newArrayList(ipe))
			}
		}

		reg.registerUsedImportsLocalNamesCollisions(lN2IPE)

		reg.registerUsedImportsDuplicatedImportedElements(lM2IPE)

		reg.registerAllUsedTypeNameToSpecifierTuples(importProvidedElements)


		// usages in script:
		val externalDep = script.usedDependenciesTypeRefs

		// mark used imports as seen in externalDep:
		for( scriptDep : externalDep ) {
			val mod = scriptDep.dependencyModule
			val pM2IPE = lM2IPE.findFirst[it.key == mod]
			if(pM2IPE !== null){
				pM2IPE.value.filter[it.exportedName == scriptDep.actualName && it.getLocalName() == scriptDep.localName ].forEach[ it.markUsed];
			}
		}

/*TODO review ambiguous imports
 * looks like reference to type that is ambigously imported can happen only if there are errors in the import declaratiosn,
 * so should ignore those references and resolve issues in the imports only?
 * Or can this information be used to resolve those isseus in smarter way?
 */
//		localname2importprovider.markAmbigousImports(script)


		return reg;
	}

	/**
	 * Registers conflicting or duplicate (based on imported elements) imports in the provided {@link RecordingImportState}
	 */
	private def registerUsedImportsDuplicatedImportedElements(RecordingImportState reg, List<Pair<TModule, List<ImportProvidedElement>>> module2imported) {
		for (pair : module2imported) {
			val fromMod = pair.value

			// find duplicates in actual name, report them as duplicateImport
			val actname2Import = ArrayListMultimap.create
			for (ipe : fromMod)
				actname2Import.put(ipe.exportedName, ipe)

			for (act : actname2Import.keySet) {
				val v = actname2Import.get(act).toList
				val x = v
				 //filter out ImportProvidedElements that reflect Namespace element itself
				.filter[internalIPE|
					val specifier = internalIPE.importSpecifier;
					if(specifier instanceof NamespaceImportSpecifier){
						internalIPE.exportedName != computeNamespaceActualName(specifier)
					}else{
						true
					}
				].toList
				if (x.size > 1) 
					reg.registerDuplicateImportsOfSameElement(act, pair.key, x)
			}
		}
	}
	
	/**
	 * Registers conflicting or duplicate (based on local name checks) imports in the provided {@link RecordingImportState}
	 */
	private def registerUsedImportsLocalNamesCollisions(RecordingImportState reg, List<Pair<String, List<ImportProvidedElement>>> localname2importprovider) {
		for(pair : localname2importprovider){
			if(pair.value.size>1){
				reg.registerLocalNameCollision(pair.key, pair.value)
			}
		}
	}

	/**
	 * analyzes provided {@link ImportDeclaration}s, if it finds *exact* duplicates, adds them to the {@link RecordingImportState#duplicatedImportDeclarations}
	 */
	private def void registerDuplicatedImoprtDeclarationsFrom(RecordingImportState reg, Iterable<ImportDeclaration> importDeclarations) {

		importDeclarations
			.filter[id| id.importSpecifiers.findFirst[is| is instanceof NamespaceImportSpecifier] !== null]
			.groupBy[module]
			.forEach[module, impDecls|
				registerImportDeclarationsWithNamespaceImportsForModule(impDecls, reg)
			]

		importDeclarations
			.filter[id| id.importSpecifiers.findFirst[is| is instanceof NamedImportSpecifier] !== null]
			.groupBy[module]
			.forEach[module, impDecls|
				registerImportDeclarationsWithNamedImportsForModule(impDecls, reg)
			]
	}

	private def void registerImportDeclarationsWithNamespaceImportsForModule(List<ImportDeclaration> importDeclarations,
		RecordingImportState reg) {
		if (importDeclarations.size < 2)
			return;

		val duplicates = newArrayList
		val firstDeclaration = importDeclarations.head
		val firstNamespaceName = (firstDeclaration.importSpecifiers.filter(NamespaceImportSpecifier).head).alias
		importDeclarations.tail.forEach [ importDeclaration |
			val followingNamespaceName = importDeclaration.importSpecifiers.filter(NamespaceImportSpecifier).head.alias
			if (firstNamespaceName == followingNamespaceName)
				duplicates.add(importDeclaration)
		]

		if (!duplicates.empty)
			reg.registerDuplicatesOfImportDeclaration(firstDeclaration, duplicates);
	}

	private def void registerImportDeclarationsWithNamedImportsForModule(List<ImportDeclaration> importDeclarations,
		RecordingImportState reg) {
		if (importDeclarations.size < 2)
			return;

		val duplicates = newArrayList
		val firstDeclaration = importDeclarations.head
		val firstDeclarationSpecifiers = firstDeclaration.importSpecifiers.filter(NamedImportSpecifier)
		importDeclarations.tail.forEach [ importDeclaration |
			val followingDeclarationSpecifiers = importDeclaration.importSpecifiers.filter(NamedImportSpecifier)
			if ((!firstDeclarationSpecifiers.empty) &&
				firstDeclarationSpecifiers.size === followingDeclarationSpecifiers.size) {
				if (firstDeclarationSpecifiers.allFollowingMatchByNameAndAlias(followingDeclarationSpecifiers))
					duplicates.add(importDeclaration)
			}
		]

		if (!duplicates.empty)
			reg.registerDuplicatesOfImportDeclaration(firstDeclaration, duplicates);
	}

	private def boolean allFollowingMatchByNameAndAlias(Iterable<NamedImportSpecifier> firstDeclarationSpecifiers,
		Iterable<NamedImportSpecifier> followingDeclarationSpecifiers) {

		firstDeclarationSpecifiers.forall [ namedImportSpecifier |
			followingDeclarationSpecifiers.exists [ otherNamedImportSpecifier |
				namedImportSpecifier.alias == otherNamedImportSpecifier.alias &&
					namedImportSpecifier.importedElement.name == otherNamedImportSpecifier.importedElement.name
			]
		]
	}

	/**
	 * Registers unused or broken (missing or unresolved imported module) import specifiers in the provided {@link RecordingImportState}
	 */
	private def void registerUnusedAndBrokenImports(RecordingImportState reg, List<ImportSpecifier> importSpecifiers) {
		for (is : importSpecifiers) {
			if (! is.isFlaggedUsedInCode) {
				reg.registerUnusedImport(is);
				if (is.isBrokenImport)
					reg.registerBrokenImport(is);
			}
		}
	}
}
