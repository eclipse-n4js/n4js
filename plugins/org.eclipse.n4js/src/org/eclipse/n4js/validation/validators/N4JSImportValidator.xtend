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
package org.eclipse.n4js.validation.validators

import com.google.inject.Inject
import java.util.List
import java.util.Map
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.EmptyStatement
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ImportSpecifier
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator
import org.eclipse.n4js.tooling.organizeImports.ImportProvidedElement
import org.eclipse.n4js.tooling.organizeImports.ImportStateCalculator
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.utils.Log
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.IssueItem
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot
import org.eclipse.n4js.workspace.WorkspaceAccess
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.*

/** Validations for the import statements. */
@Log
class N4JSImportValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private ImportStateCalculator importStateCalculator;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	@Inject
	protected XpectAwareFileExtensionCalculator fileExtensionCalculator;

	@Inject
	protected WorkspaceAccess workspaceAccess;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	@Check
	def checkMultipleDefaultExports(Script script) {
		val defaultExports = script.eAllContents.filter(ExportDeclaration).filter[isDefaultExport].toList;
		defaultExports.tail.forEach[
			addIssue(it, N4JSPackage.eINSTANCE.exportDeclaration_DefaultExport, IMP_DEFAULT_EXPORT_DUPLICATE.toIssueItem());
		]
	}

	@Check
	def checkConflictingImports(Script script) {
		val eObjectToIssueCode = newHashMap
		analyzeAndCheckConflictingImports(script, eObjectToIssueCode)
		// regardless of other issues, add markers for scattered imports
		markScatteredImports(script)
	}

	@Check
	def checkStaticVsDynamicImport(ImportSpecifier importSpecifier) {
		val parent = importSpecifier.eContainer;
		if (parent instanceof ImportDeclaration) {
			val impModule = parent.module;
			if (impModule !== null && !impModule.eIsProxy()) {
				if (importSpecifier.declaredDynamic) {
					if (jsVariantHelper.isN4JSMode(impModule)) {
						addIssue(importSpecifier, IMP_DYNAMIC_IMPORT_N4JS.toIssueItem(impModule.moduleSpecifier));
					} else if (jsVariantHelper.isExternalMode(impModule)) {
						val variant = fileExtensionCalculator.getXpectAwareFileExtension(impModule);
						addIssue(importSpecifier, IMP_DYNAMIC_IMPORT_N4JSD.toIssueItem(variant, impModule.moduleSpecifier));
					}
				} else {
					if (jsVariantHelper.isPlainJS(impModule)) {
						addIssue(importSpecifier, IMP_STATIC_IMPORT_PLAIN_JS.toIssueItem(impModule.moduleSpecifier));
					}
				}
				if (importSpecifier instanceof NamedImportSpecifier) {
					if (importSpecifier.importedElement !== null && importSpecifier.importedElement.eContainer instanceof TModule) {
						val elemModule = importSpecifier.importedElement.eContainer;
						if (elemModule !== impModule) {
							val impModulePrj = workspaceAccess.findProjectContaining(impModule);
							val impElemPrj = workspaceAccess.findProjectContaining(elemModule);
							if (impModulePrj !== impElemPrj) {
								if (isDependingOn(importSpecifier, impModulePrj, impElemPrj)) {
									// that's how it should be: re-export
								} else {
									val IssueItem issueItem = IMP_IMPORTED_ELEMENT_FROM_REEXPORTING_PROJECT.toIssueItem(
										importSpecifier.importedElementAsText,
										impElemPrj.n4JSPackageName.toString,
										impModulePrj.n4JSPackageName.toString
									);
									addIssue(importSpecifier, issueItem);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/** Poor man's project dependency predicate */
	private def boolean isDependingOn(Notifier context, N4JSProjectConfigSnapshot p1, N4JSProjectConfigSnapshot p2) {
		if (p1.dependencies.contains(p2.name)) {
			return true;
		}
		
		for (projectID : p1.dependencies) {
			val depPrj = workspaceAccess.findProjectByName(context, projectID);
			if (depPrj !== null && depPrj.dependencies.contains(p2.name)) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Algorithm to check the Model for Issues with Imports.
	 */
	private def analyzeAndCheckConflictingImports(Script script, Map<EObject, String> eObjectToIssueCode) {
		val reg = importStateCalculator.calculateImportstate(script);

		reg.duplicatedImportDeclarations.forEach[handleDuplicatedImportDeclarations(eObjectToIssueCode)]
		
		handleNameCollisions(reg.localNameCollision, eObjectToIssueCode)

		handleTypeCollisions(reg.duplicateImportsOfSameElement, eObjectToIssueCode)

		handleBrokenImports(reg.brokenImports, eObjectToIssueCode)

		handleUnusedImports(reg.unusedImports, eObjectToIssueCode)

		handleNotImportedTypeRefs(script, eObjectToIssueCode.keySet.filter(ImportSpecifier).toList, eObjectToIssueCode)
	}

	/** Create issue (warning) 'unused import' if  import doesn't contribute to referenced Types.
	 * @param specifier import to be marked
	 * */
	private def void handleBrokenImports(List<ImportSpecifier> importSpecifiers,
		Map<EObject, String> eObjectToIssueCode) {
		for (is : importSpecifiers) {
			if (!eObjectToIssueCode.keySet.contains(is)) {
				addIssueUnresolved(is, eObjectToIssueCode)
			}
		}
	}

	/** Create issue (warning) 'unused import' if  import doesn't contribute to referenced Types.
	 * @param specifier import to be marked
	 * */
	private def void handleUnusedImports(List<ImportSpecifier> importSpecifiers,
		Map<EObject, String> eObjectToIssueCode) {
		for (is : importSpecifiers) {
			if (!eObjectToIssueCode.keySet.contains(is)) {
				addIssueUnusedImport(is, eObjectToIssueCode);
			}
		}
	}

	/**
	 * Computes a user-facing name for the given {@link NamedImportSpecifier}
	 * that can be used in error/warning messages.
	 *
	 * @param spec
	 * @return name from NamedImportSpecifier or AST-text if unresolved.
	 */
	private def String computeImportSpecifierName(NamedImportSpecifier spec) {
		if (spec.isBrokenImport) {
			// find AST for Message:
			NodeModelUtils.findActualNodeFor(spec).text.trim
		} else
			spec.importedElementName
	}

	/** Mark all imports that don't appear in the header.
	 * @param script the script
	 */
	private def markScatteredImports(Script script) {
		var boolean stillInHeader = true
		for (se : script.scriptElements) {
			if (stillInHeader) {
				if (! ( se instanceof ImportDeclaration || se instanceof EmptyStatement || N4JSASTUtils.isStringLiteralExpression(se) )) stillInHeader = false;
			} else {
				if (se instanceof ImportDeclaration) handleScatteredImport(se)
			}
		}
	}

	/**
	 * Mark with discouraged_placement it not yet marked with other error.
	 * @param importDecl - statement to mark
	 */
	private def handleScatteredImport(ImportDeclaration importDecl) {
		addIssueScatteredImport(importDecl)
	}

	private def handleDuplicatedImportDeclarations(Pair<ImportDeclaration, List<ImportDeclaration>> duplicatePair,
		Map<EObject, String> eObjectToIssueCode) {
		val specifier = duplicatePair.key.importSpecifiers.head
		val duplicates = duplicatePair.value
		duplicates.forEach [ duplicateImportDeclaration |
			val duplicate = duplicateImportDeclaration.importSpecifiers.head
			switch (specifier) {
				NamespaceImportSpecifier: {
					switch (duplicate) {
						NamespaceImportSpecifier: {
							addIssueDuplicateNamespaceImportDeclaration(specifier, duplicate,
								duplicateImportDeclaration, eObjectToIssueCode)
						}
						default:
							logger.error(
								"cannot register duplicate import declaration for different kinds of specifiers")
					}
				}
				NamedImportSpecifier: {
					switch (duplicate) {
						NamedImportSpecifier: {
							addIssueDuplicateNamedImportDeclaration(specifier, duplicate, duplicateImportDeclaration,
								eObjectToIssueCode)
						}
						default:
							logger.error(
								"cannot register duplicate import declaration for different kinds of specifiers")
					}
				}
				default:
					logger.error("cannot register duplicate import declaration for  specifiers")
			}
		]
	}

	private def handleNameCollisions(List<Pair<String, List<ImportProvidedElement>>> multimap,
		Map<EObject, String> eObjectToIssueCode) {
		multimap.forEach [ pair |
			val providers = pair.value
			// assuming they came in order
			val first = providers.head
			val name = first.localName
			val firstImportSpecifier = first.importSpecifier
			val name2reason = switch (firstImportSpecifier) {
				NamespaceImportSpecifier: {
					name -> "namespace name for " + firstImportSpecifier.importedModule.qualifiedName.toString
				}
				NamedImportSpecifier: {
					val importedElemName = firstImportSpecifier.importedElement?.name ?: firstImportSpecifier.importedElementAsText;
					if (firstImportSpecifier.alias !== null) {
						name -> "alias name for named import " + importedElemName + " from " +
							firstImportSpecifier.importedModule.qualifiedName.toString
					} else {
						name -> "name for named import " + importedElemName + " from " +
							firstImportSpecifier.importedModule.qualifiedName.toString
					}
				}
				default: {
					logger.error("unhandled type of " + ImportSpecifier.name);
					throw new RuntimeException("Cannot validate local name collisions");
				}
			}
			providers.tail.forEach [ importProvidedElement |
				addLocalNameCollision(importProvidedElement.importSpecifier, name2reason.key, name2reason.value,
					eObjectToIssueCode);
			]
		]
	}

	private def handleTypeCollisions(List<Pair<Pair<String, TModule>, List<ImportProvidedElement>>> duplicateslist,
		Map<EObject, String> eObjectToIssueCode) {
			
		for (duplicateEntry : duplicateslist) {
			val entry = duplicateEntry.key
			val entryName = entry.key
			val entryModule = entry.value
			val imports = duplicateEntry.value
			val firstImportSpecifier = imports.head.importSpecifier
			val firstImportName = switch (firstImportSpecifier) {
				NamespaceImportSpecifier: {
					firstImportSpecifier.alias + "." + entryName
				}
				NamedImportSpecifier: {
					firstImportSpecifier.alias ?: entryName
				}
			}
			val firstImportIsDefault = if(firstImportSpecifier instanceof NamedImportSpecifier) {
				firstImportSpecifier.isDefaultImport
			};

			for (dupe : imports.tail) {
				val duplicateImportSpecifier = dupe.importSpecifier
				if (firstImportIsDefault && duplicateImportSpecifier instanceof NamespaceImportSpecifier) {
					addIssueDuplicate(firstImportSpecifier, entryName, entryModule, firstImportName, eObjectToIssueCode)
				}
				if (firstImportSpecifier instanceof NamespaceImportSpecifier &&
					duplicateImportSpecifier instanceof NamespaceImportSpecifier) {
					addIssueDuplicateNamespace(dupe, duplicateImportSpecifier as NamespaceImportSpecifier,
						firstImportSpecifier as NamespaceImportSpecifier, eObjectToIssueCode)
				} else {
					addIssueDuplicate(dupe.importSpecifier, entryName, entryModule, firstImportName, eObjectToIssueCode)
				}
			}
		}
	}

	private def regUnresolvedImport(ParameterizedTypeRef ref, String name, Map<EObject, String> eObjectToIssueCode) {
		val issueCode = IMP_UNRESOLVED.name();
		if (eObjectToIssueCode.put(ref, issueCode) === null) {
			addIssue(ref, IMP_UNRESOLVED.toIssueItem(name));
		}
	}

	private def handleNotImportedTypeRefs(Script script, List<ImportSpecifier> specifiersWithIssues,
		Map<EObject, String> eObjectToIssueCode) {
		val importedProvidedElementsWithIssuesByModule = mapToImportProvidedElements(specifiersWithIssues).groupBy [
			importedModule
		]
		val potentiallyAffectedTypeRefs = script.eAllContents.filter(ParameterizedTypeRef).filter [
			declaredType !== null && declaredType.containingModule !== null
		].groupBy[declaredType.containingModule]

		potentiallyAffectedTypeRefs.forEach [ module, typeRefs |
			val conflict = importedProvidedElementsWithIssuesByModule.get(module);
			if (conflict !== null) {
				typeRefs.forEach [ typeRef |
					val typeRefName = typeRef.typeRefUsedName;
					if (conflict.exists[ipe|ipe.getLocalName() == typeRefName]) {
						regUnresolvedImport(typeRef, typeRefName, eObjectToIssueCode);
					}
				]
			}
		]
	}

	private def String typeRefUsedName(ParameterizedTypeRef ref) {
		NodeModelUtils.getTokenText(NodeModelUtils.findActualNodeFor(ref))
	}

	/**
	 * Notice, that unlike other issues {@link IssueCodes#IMP_DISCOURAGED_PLACEMENT} is always added, even if there are other issues
	 * reported for given import declaration.
	 */
	private def addIssueScatteredImport(ImportDeclaration importDecl) {
		addIssue(importDecl, IMP_DISCOURAGED_PLACEMENT.toIssueItem())
	}

	private def addLocalNameCollision(ImportSpecifier duplicate, String name, String reason,
		Map<EObject, String> eObjectToIssueCode) {
		if (eObjectToIssueCode.get(duplicate) === null) {
			val IssueItem issueItem = IMP_LOCAL_NAME_CONFLICT.toIssueItem(name, reason);
			addIssue(duplicate, issueItem)
			eObjectToIssueCode.put(duplicate, issueItem.ID);
		}
	}

	private def addIssueDuplicateNamespaceImportDeclaration(NamespaceImportSpecifier specifier,
		NamespaceImportSpecifier duplicate, ImportDeclaration duplicateImportDeclaration,
		Map<EObject, String> eObjectToIssueCode) {
		val String issueCode = IMP_STMT_DUPLICATE_NAMESPACE.name();
		if (eObjectToIssueCode.get(specifier) === null) {
			val IssueItem issueItem = IMP_STMT_DUPLICATE_NAMESPACE.toIssueItem(specifier.alias, duplicate.importedModule.qualifiedName);
			addIssue(duplicateImportDeclaration, issueItem)
		}

		duplicateImportDeclaration.importSpecifiers.forEach [ is |
			eObjectToIssueCode.put(specifier, issueCode)
		]
	}

	/**
	 * Adds an issue for duplicate named import specifiers.
	 *
	 * @param specifier The first import of the element
	 * @param duplicate The duplicated import of the element
	 * @param duplicateImportDeclaration The import declaration of the duplicated import
	 * @param eObjectToIssueCode A map to keep track of all added issues
	 */
	private def addIssueDuplicateNamedImportDeclaration(NamedImportSpecifier specifier,
		NamedImportSpecifier duplicate, ImportDeclaration duplicateImportDeclaration,
		Map<EObject, String> eObjectToIssueCode) {
			
		val String issueCode = IMP_STMT_DUPLICATE_NAMED.name();
		if (eObjectToIssueCode.get(specifier) === null) {
			val IssueItem issueItem = IMP_STMT_DUPLICATE_NAMED.toIssueItem(specifier.usedName, duplicate.importedModule.qualifiedName);
			addIssue(duplicateImportDeclaration, issueItem)
		}

		duplicateImportDeclaration.importSpecifiers.forEach [ is |
			eObjectToIssueCode.put(specifier, issueCode)
		]
	}

	private def addIssueDuplicateNamespace(ImportProvidedElement ipe, NamespaceImportSpecifier duplicateImportSpecifier,
		NamespaceImportSpecifier firstImportSpecifier, Map<EObject, String> eObjectToIssueCode) {
		if (eObjectToIssueCode.get(duplicateImportSpecifier) === null) {
			val IssueItem issueItem = IMP_DUPLICATE_NAMESPACE.toIssueItem(ipe.localName, firstImportSpecifier.importedModule.qualifiedName, firstImportSpecifier.alias)
			addIssue(duplicateImportSpecifier, issueItem)
			eObjectToIssueCode.put(duplicateImportSpecifier, issueItem.getID());
		}
	}

	private def addIssueDuplicate(ImportSpecifier specifier, String actualName, TModule module,
		String firstImportName, Map<EObject, String> eObjectToIssueCode) {
		if (eObjectToIssueCode.get(specifier) === null) {
			val IssueItem issueItem = IMP_DUPLICATE.toIssueItem(actualName, module.qualifiedName, firstImportName);
			addIssue(specifier, issueItem);
			eObjectToIssueCode.put(specifier, issueItem.getID());
		}
	}

	private def addIssueUnresolved(ImportSpecifier specifier, Map<EObject, String> eObjectToIssueCode) {
		var String issueCode = IMP_UNRESOLVED.name();
		if (eObjectToIssueCode.get(specifier) === null) {
			if (!isChildOfUnresolvedImportDecl(specifier)) { // avoid redundant follow-up error messages
				val IssueItem issueItem = IMP_UNRESOLVED.toIssueItem(computeUnusedOrUnresolvedMessage(specifier));
				addIssue(specifier, issueItem);
			}
			eObjectToIssueCode.put(specifier, issueCode)
		}
	}

	private def addIssueUnusedImport(ImportSpecifier specifier, Map<EObject, String> eObjectToIssueCode) {
		if (eObjectToIssueCode.get(specifier) === null) {
			val IssueItem issueItem = IMP_UNUSED_IMPORT.toIssueItem(computeUnusedOrUnresolvedMessage(specifier));
			addIssue(specifier, issueItem);
			eObjectToIssueCode.put(specifier, issueItem.getID());
		}
	}

	private def String computeUnusedOrUnresolvedMessage(ImportSpecifier specifier) {
		switch (specifier) {
			NamedImportSpecifier: computeImportSpecifierName(specifier)
			NamespaceImportSpecifier: "* as " + specifier.alias + " from " + computeModuleSpecifier(specifier)
		}
	}

	private def String computeModuleSpecifier(NamespaceImportSpecifier specifier) {
		val importedModule = specifier.importedModule
		if (importedModule !== null && !importedModule.eIsProxy) {
			importedModule.moduleSpecifier
		} else {
			"module was a proxy"
		}
	}

	private def boolean isChildOfUnresolvedImportDecl(ImportSpecifier importSpec) {
		val parent = importSpec.eContainer();
		if (parent instanceof ImportDeclaration) {
			val module = parent.module;
			if (module !== null) {
				return module.eIsProxy;
			}
		}
		return false;
	}
}
