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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.n4JS.N4JSASTUtils.isStringLiteralExpression;
import static org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.importedElementName;
import static org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.importedModule;
import static org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.isBrokenImport;
import static org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.mapToImportProvidedElements;
import static org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.usedName;
import static org.eclipse.n4js.validation.IssueCodes.IMP_DEFAULT_EXPORT_DUPLICATE;
import static org.eclipse.n4js.validation.IssueCodes.IMP_DISCOURAGED_PLACEMENT;
import static org.eclipse.n4js.validation.IssueCodes.IMP_DUPLICATE;
import static org.eclipse.n4js.validation.IssueCodes.IMP_DUPLICATE_NAMESPACE;
import static org.eclipse.n4js.validation.IssueCodes.IMP_DYNAMIC_IMPORT_N4JS;
import static org.eclipse.n4js.validation.IssueCodes.IMP_DYNAMIC_IMPORT_N4JSD;
import static org.eclipse.n4js.validation.IssueCodes.IMP_IMPORTED_ELEMENT_FROM_REEXPORTING_PROJECT;
import static org.eclipse.n4js.validation.IssueCodes.IMP_LOCAL_NAME_CONFLICT;
import static org.eclipse.n4js.validation.IssueCodes.IMP_STATIC_IMPORT_PLAIN_JS;
import static org.eclipse.n4js.validation.IssueCodes.IMP_STMT_DUPLICATE_NAMED;
import static org.eclipse.n4js.validation.IssueCodes.IMP_STMT_DUPLICATE_NAMESPACE;
import static org.eclipse.n4js.validation.IssueCodes.IMP_UNRESOLVED;
import static org.eclipse.n4js.validation.IssueCodes.IMP_UNUSED_IMPORT;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.groupBy;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.tail;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.groupBy;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.tooling.organizeImports.ImportProvidedElement;
import org.eclipse.n4js.tooling.organizeImports.ImportStateCalculator;
import org.eclipse.n4js.tooling.organizeImports.RecordingImportState;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.Pair;

/** Validations for the import statements. */
@SuppressWarnings("javadoc")
public class N4JSImportValidator extends AbstractN4JSDeclarativeValidator {
	private final static Logger LOGGER = Logger.getLogger(N4JSImportValidator.class);

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
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	@Check
	public void checkMultipleDefaultExports(Script script) {
		List<ExportDeclaration> defaultExports = toList(
				filter(filter(script.eAllContents(), ExportDeclaration.class), ed -> ed.isDefaultExport()));
		for (ExportDeclaration ed : tail(defaultExports)) {
			addIssue(ed, N4JSPackage.eINSTANCE.getExportDeclaration_DefaultExport(),
					IMP_DEFAULT_EXPORT_DUPLICATE.toIssueItem());
		}
	}

	@Check
	public void checkConflictingImports(Script script) {
		Map<EObject, String> eObjectToIssueCode = new HashMap<>();
		analyzeAndCheckConflictingImports(script, eObjectToIssueCode);
		// regardless of other issues, add markers for scattered imports
		markScatteredImports(script);
	}

	@Check
	public void checkStaticVsDynamicImport(ImportSpecifier importSpecifier) {
		EObject parent = importSpecifier.eContainer();
		if (parent instanceof ImportDeclaration) {
			TModule impModule = ((ImportDeclaration) parent).getModule();
			if (impModule != null && !impModule.eIsProxy()) {
				if (importSpecifier.isDeclaredDynamic()) {
					if (jsVariantHelper.isN4JSMode(impModule)) {
						addIssue(importSpecifier, IMP_DYNAMIC_IMPORT_N4JS.toIssueItem(impModule.getModuleSpecifier()));
					} else if (jsVariantHelper.isExternalMode(impModule)) {
						String variant = fileExtensionCalculator.getXpectAwareFileExtension(impModule);
						addIssue(importSpecifier,
								IMP_DYNAMIC_IMPORT_N4JSD.toIssueItem(variant, impModule.getModuleSpecifier()));
					}
				} else {
					if (jsVariantHelper.isPlainJS(impModule)) {
						addIssue(importSpecifier,
								IMP_STATIC_IMPORT_PLAIN_JS.toIssueItem(impModule.getModuleSpecifier()));
					}
				}
				if (importSpecifier instanceof NamedImportSpecifier) {
					NamedImportSpecifier nis = (NamedImportSpecifier) importSpecifier;
					TExportableElement importedElement = nis.getImportedElement();
					if (importedElement != null && importedElement.eContainer() instanceof TModule) {
						EObject elemModule = importedElement.eContainer();
						if (elemModule != impModule) {
							N4JSProjectConfigSnapshot impModulePrj = workspaceAccess.findProjectContaining(impModule);
							N4JSProjectConfigSnapshot impElemPrj = workspaceAccess.findProjectContaining(elemModule);
							if (impModulePrj != impElemPrj) {
								if (isDependingOn(nis, impModulePrj, impElemPrj)) {
									// that's how it should be: re-export
								} else {
									IssueItem issueItem = IMP_IMPORTED_ELEMENT_FROM_REEXPORTING_PROJECT.toIssueItem(
											nis.getImportedElementAsText(),
											impElemPrj.getN4JSPackageName().toString(),
											impModulePrj.getN4JSPackageName().toString());
									addIssue(nis, issueItem);
								}
							}
						}
					}
				}
			}
		}
	}

	/** Poor man's project dependency predicate */
	private boolean isDependingOn(Notifier context, N4JSProjectConfigSnapshot p1, N4JSProjectConfigSnapshot p2) {
		if (p1.getDependencies().contains(p2.getName())) {
			return true;
		}

		for (String projectID : p1.getDependencies()) {
			N4JSProjectConfigSnapshot depPrj = workspaceAccess.findProjectByName(context, projectID);
			if (depPrj != null && depPrj.getDependencies().contains(p2.getName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Algorithm to check the Model for Issues with Imports.
	 */
	private void analyzeAndCheckConflictingImports(Script script, Map<EObject, String> eObjectToIssueCode) {
		RecordingImportState reg = importStateCalculator.calculateImportstate(script);

		for (Pair<ImportDeclaration, List<ImportDeclaration>> p : reg.duplicatedImportDeclarations) {
			handleDuplicatedImportDeclarations(p, eObjectToIssueCode);
		}

		handleNameCollisions(reg.localNameCollision, eObjectToIssueCode);

		handleTypeCollisions(reg.duplicateImportsOfSameElement, eObjectToIssueCode);

		handleBrokenImports(reg.brokenImports, eObjectToIssueCode);

		handleUnusedImports(reg.unusedImports, eObjectToIssueCode);

		handleNotImportedTypeRefs(script, toList(filter(eObjectToIssueCode.keySet(), ImportSpecifier.class)),
				eObjectToIssueCode);
	}

	/**
	 * Create issue (warning) 'unused import' if import doesn't contribute to referenced Types.
	 */
	private void handleBrokenImports(List<ImportSpecifier> importSpecifiers,
			Map<EObject, String> eObjectToIssueCode) {
		for (ImportSpecifier is : importSpecifiers) {
			if (!eObjectToIssueCode.keySet().contains(is)) {
				addIssueUnresolved(is, eObjectToIssueCode);
			}
		}
	}

	/**
	 * Create issue (warning) 'unused import' if import doesn't contribute to referenced Types.
	 */
	private void handleUnusedImports(List<ImportSpecifier> importSpecifiers, Map<EObject, String> eObjectToIssueCode) {
		for (ImportSpecifier is : importSpecifiers) {
			if (!eObjectToIssueCode.keySet().contains(is)) {
				addIssueUnusedImport(is, eObjectToIssueCode);
			}
		}
	}

	/**
	 * Computes a user-facing name for the given {@link NamedImportSpecifier} that can be used in error/warning
	 * messages.
	 *
	 * @return name from NamedImportSpecifier or AST-text if unresolved.
	 */
	private String computeImportSpecifierName(NamedImportSpecifier spec) {
		if (isBrokenImport(spec)) {
			// find AST for Message:
			return NodeModelUtils.findActualNodeFor(spec).getText().trim();
		} else
			return importedElementName(spec);
	}

	/**
	 * Mark all imports that don't appear in the header.
	 *
	 * @param script
	 *            the script
	 */
	private void markScatteredImports(Script script) {
		boolean stillInHeader = true;
		for (ScriptElement se : script.getScriptElements()) {
			if (stillInHeader) {
				if (!(se instanceof ImportDeclaration || se instanceof EmptyStatement || isStringLiteralExpression(se)))
					stillInHeader = false;
			} else {
				if (se instanceof ImportDeclaration) {
					handleScatteredImport((ImportDeclaration) se);
				}
			}
		}
	}

	/**
	 * Mark with discouraged_placement it not yet marked with other error.
	 *
	 * @param importDecl
	 *            - statement to mark
	 */
	private void handleScatteredImport(ImportDeclaration importDecl) {
		addIssueScatteredImport(importDecl);
	}

	private void handleDuplicatedImportDeclarations(Pair<ImportDeclaration, List<ImportDeclaration>> duplicatePair,
			Map<EObject, String> eObjectToIssueCode) {
		ImportSpecifier specifier = head(duplicatePair.getKey().getImportSpecifiers());
		List<ImportDeclaration> duplicates = duplicatePair.getValue();

		for (ImportDeclaration duplicateImportDeclaration : duplicates) {
			ImportSpecifier duplicate = head(duplicateImportDeclaration.getImportSpecifiers());
			if (specifier instanceof NamespaceImportSpecifier) {
				if (duplicate instanceof NamespaceImportSpecifier) {

					addIssueDuplicateNamespaceImportDeclaration((NamespaceImportSpecifier) specifier,
							(NamespaceImportSpecifier) duplicate, duplicateImportDeclaration, eObjectToIssueCode);
				} else {
					LOGGER.error(
							"cannot register duplicate import declaration for different kinds of specifiers");
				}

			} else if (specifier instanceof NamedImportSpecifier) {
				if (duplicate instanceof NamedImportSpecifier) {
					addIssueDuplicateNamedImportDeclaration((NamedImportSpecifier) specifier,
							(NamedImportSpecifier) duplicate, duplicateImportDeclaration, eObjectToIssueCode);
				} else {
					LOGGER.error(
							"cannot register duplicate import declaration for different kinds of specifiers");
				}
			} else {
				LOGGER.error("cannot register duplicate import declaration for  specifiers");
			}
		}
	}

	private void handleNameCollisions(List<Pair<String, List<ImportProvidedElement>>> multimap,
			Map<EObject, String> eObjectToIssueCode) {
		for (Pair<String, List<ImportProvidedElement>> pair : multimap) {
			List<ImportProvidedElement> providers = pair.getValue();
			// assuming they came in order
			ImportProvidedElement first = head(providers);
			String name = first.getLocalName();
			ImportSpecifier firstImportSpecifier = first.getImportSpecifier();
			Pair<String, String> name2reason;
			if (firstImportSpecifier instanceof NamespaceImportSpecifier) {
				name2reason = Pair.of(name,
						"namespace name for " + importedModule(firstImportSpecifier).getQualifiedName().toString());
			} else if (firstImportSpecifier instanceof NamedImportSpecifier) {
				NamedImportSpecifier nis = (NamedImportSpecifier) firstImportSpecifier;
				String importedElemName = nis.getImportedElement() == null ? null : nis.getImportedElement().getName();
				if (importedElemName == null) {
					importedElemName = nis.getImportedElementAsText();
				}
				if (nis.getAlias() != null) {
					name2reason = Pair.of(name, "alias name for named import " + importedElemName + " from " +
							importedModule(nis).getQualifiedName().toString());
				} else {
					name2reason = Pair.of(name, "name for named import " + importedElemName + " from " +
							importedModule(nis).getQualifiedName().toString());
				}
			} else {
				LOGGER.error("unhandled type of " + ImportSpecifier.class.getName());
				throw new RuntimeException("Cannot validate local name collisions");
			}

			for (ImportProvidedElement importProvidedElement : tail(providers)) {
				addLocalNameCollision(importProvidedElement.getImportSpecifier(), name2reason.getKey(),
						name2reason.getValue(),
						eObjectToIssueCode);
			}
		}
	}

	private void handleTypeCollisions(List<Pair<Pair<String, TModule>, List<ImportProvidedElement>>> duplicateslist,
			Map<EObject, String> eObjectToIssueCode) {

		for (Pair<Pair<String, TModule>, List<ImportProvidedElement>> duplicateEntry : duplicateslist) {
			Pair<String, TModule> entry = duplicateEntry.getKey();
			String entryName = entry.getKey();
			TModule entryModule = entry.getValue();
			List<ImportProvidedElement> imports = duplicateEntry.getValue();
			ImportSpecifier firstImportSpecifier = head(imports).getImportSpecifier();
			String firstImportName = null;
			if (firstImportSpecifier instanceof NamespaceImportSpecifier) {
				firstImportName = ((NamespaceImportSpecifier) firstImportSpecifier).getAlias() + "." + entryName;
			}
			if (firstImportSpecifier instanceof NamedImportSpecifier) {
				firstImportName = ((NamedImportSpecifier) firstImportSpecifier).getAlias();
				if (firstImportName == null) {
					firstImportName = entryName;
				}
			}
			boolean firstImportIsDefault = firstImportSpecifier instanceof NamedImportSpecifier
					&& ((NamedImportSpecifier) firstImportSpecifier).isDefaultImport();

			for (ImportProvidedElement dupe : tail(imports)) {
				ImportSpecifier duplicateImportSpecifier = dupe.getImportSpecifier();
				if (firstImportIsDefault && duplicateImportSpecifier instanceof NamespaceImportSpecifier) {
					addIssueDuplicate(firstImportSpecifier, entryName, entryModule, firstImportName,
							eObjectToIssueCode);
				}
				if (firstImportSpecifier instanceof NamespaceImportSpecifier &&
						duplicateImportSpecifier instanceof NamespaceImportSpecifier) {
					addIssueDuplicateNamespace(dupe, (NamespaceImportSpecifier) duplicateImportSpecifier,
							(NamespaceImportSpecifier) firstImportSpecifier, eObjectToIssueCode);
				} else {
					addIssueDuplicate(dupe.getImportSpecifier(), entryName, entryModule, firstImportName,
							eObjectToIssueCode);
				}
			}
		}
	}

	private void regUnresolvedImport(ParameterizedTypeRef ref, String name, Map<EObject, String> eObjectToIssueCode) {
		String issueCode = IMP_UNRESOLVED.name();
		if (eObjectToIssueCode.put(ref, issueCode) == null) {
			addIssue(ref, IMP_UNRESOLVED.toIssueItem(name));
		}
	}

	private void handleNotImportedTypeRefs(Script script, List<ImportSpecifier> specifiersWithIssues,
			Map<EObject, String> eObjectToIssueCode) {

		Map<TModule, List<ImportProvidedElement>> importedProvidedElementsWithIssuesByModule = groupBy(
				mapToImportProvidedElements(specifiersWithIssues), it -> it.getImportedModule());
		Map<TModule, List<ParameterizedTypeRef>> potentiallyAffectedTypeRefs = groupBy(filter(filter(
				script.eAllContents(), ParameterizedTypeRef.class),
				ptr -> ptr.getDeclaredType() != null && ptr.getDeclaredType().getContainingModule() != null),
				ptr -> ptr.getDeclaredType().getContainingModule());

		for (TModule module : potentiallyAffectedTypeRefs.keySet()) {
			List<ParameterizedTypeRef> typeRefs = potentiallyAffectedTypeRefs.get(module);
			List<ImportProvidedElement> conflict = importedProvidedElementsWithIssuesByModule.get(module);
			if (conflict != null) {
				for (ParameterizedTypeRef typeRef : typeRefs) {
					String typeRefName = typeRefUsedName(typeRef);
					if (exists(conflict, ipe -> Objects.equals(ipe.getLocalName(), typeRefName))) {
						regUnresolvedImport(typeRef, typeRefName, eObjectToIssueCode);
					}
				}
			}
		}
	}

	private String typeRefUsedName(ParameterizedTypeRef ref) {
		return NodeModelUtils.getTokenText(NodeModelUtils.findActualNodeFor(ref));
	}

	/**
	 * Notice, that unlike other issues {@link IssueCodes#IMP_DISCOURAGED_PLACEMENT} is always added, even if there are
	 * other issues reported for given import declaration.
	 */
	private void addIssueScatteredImport(ImportDeclaration importDecl) {
		addIssue(importDecl, IMP_DISCOURAGED_PLACEMENT.toIssueItem());
	}

	private void addLocalNameCollision(ImportSpecifier duplicate, String name, String reason,
			Map<EObject, String> eObjectToIssueCode) {
		if (eObjectToIssueCode.get(duplicate) == null) {
			IssueItem issueItem = IMP_LOCAL_NAME_CONFLICT.toIssueItem(name, reason);
			addIssue(duplicate, issueItem);
			eObjectToIssueCode.put(duplicate, issueItem.getID());
		}
	}

	private void addIssueDuplicateNamespaceImportDeclaration(NamespaceImportSpecifier specifier,
			NamespaceImportSpecifier duplicate, ImportDeclaration duplicateImportDeclaration,
			Map<EObject, String> eObjectToIssueCode) {
		String issueCode = IMP_STMT_DUPLICATE_NAMESPACE.name();
		if (eObjectToIssueCode.get(specifier) == null) {
			IssueItem issueItem = IMP_STMT_DUPLICATE_NAMESPACE.toIssueItem(specifier.getAlias(),
					importedModule(duplicate).getQualifiedName());
			addIssue(duplicateImportDeclaration, issueItem);
		}

		for (ImportSpecifier is : duplicateImportDeclaration.getImportSpecifiers()) {
			eObjectToIssueCode.put(is, issueCode);
		}
	}

	/**
	 * Adds an issue for duplicate named import specifiers.
	 *
	 * @param specifier
	 *            The first import of the element
	 * @param duplicate
	 *            The duplicated import of the element
	 * @param duplicateImportDeclaration
	 *            The import declaration of the duplicated import
	 * @param eObjectToIssueCode
	 *            A map to keep track of all added issues
	 */
	private void addIssueDuplicateNamedImportDeclaration(NamedImportSpecifier specifier,
			NamedImportSpecifier duplicate, ImportDeclaration duplicateImportDeclaration,
			Map<EObject, String> eObjectToIssueCode) {

		String issueCode = IMP_STMT_DUPLICATE_NAMED.name();
		if (eObjectToIssueCode.get(specifier) == null) {
			IssueItem issueItem = IMP_STMT_DUPLICATE_NAMED.toIssueItem(usedName(specifier),
					importedModule(duplicate).getQualifiedName());
			addIssue(duplicateImportDeclaration, issueItem);
		}

		for (ImportSpecifier is : duplicateImportDeclaration.getImportSpecifiers()) {
			eObjectToIssueCode.put(is, issueCode);
		}
	}

	private void addIssueDuplicateNamespace(ImportProvidedElement ipe,
			NamespaceImportSpecifier duplicateImportSpecifier,
			NamespaceImportSpecifier firstImportSpecifier, Map<EObject, String> eObjectToIssueCode) {
		if (eObjectToIssueCode.get(duplicateImportSpecifier) == null) {
			IssueItem issueItem = IMP_DUPLICATE_NAMESPACE.toIssueItem(ipe.getLocalName(),
					importedModule(firstImportSpecifier).getQualifiedName(), firstImportSpecifier.getAlias());
			addIssue(duplicateImportSpecifier, issueItem);
			eObjectToIssueCode.put(duplicateImportSpecifier, issueItem.getID());
		}
	}

	private void addIssueDuplicate(ImportSpecifier specifier, String actualName, TModule module,
			String firstImportName, Map<EObject, String> eObjectToIssueCode) {
		if (eObjectToIssueCode.get(specifier) == null) {
			IssueItem issueItem = IMP_DUPLICATE.toIssueItem(actualName, module.getQualifiedName(), firstImportName);
			addIssue(specifier, issueItem);
			eObjectToIssueCode.put(specifier, issueItem.getID());
		}
	}

	private void addIssueUnresolved(ImportSpecifier specifier, Map<EObject, String> eObjectToIssueCode) {
		String issueCode = IMP_UNRESOLVED.name();
		if (eObjectToIssueCode.get(specifier) == null) {
			if (!isChildOfUnresolvedImportDecl(specifier)) { // avoid redundant follow-up error messages
				IssueItem issueItem = IMP_UNRESOLVED.toIssueItem(computeUnusedOrUnresolvedMessage(specifier));
				addIssue(specifier, issueItem);
			}
			eObjectToIssueCode.put(specifier, issueCode);
		}
	}

	private void addIssueUnusedImport(ImportSpecifier specifier, Map<EObject, String> eObjectToIssueCode) {
		if (eObjectToIssueCode.get(specifier) == null) {
			IssueItem issueItem = IMP_UNUSED_IMPORT.toIssueItem(computeUnusedOrUnresolvedMessage(specifier));
			addIssue(specifier, issueItem);
			eObjectToIssueCode.put(specifier, issueItem.getID());
		}
	}

	private String computeUnusedOrUnresolvedMessage(ImportSpecifier specifier) {
		if (specifier instanceof NamedImportSpecifier) {
			return computeImportSpecifierName((NamedImportSpecifier) specifier);
		}
		if (specifier instanceof NamespaceImportSpecifier) {
			NamespaceImportSpecifier nis = (NamespaceImportSpecifier) specifier;
			return "* as " + nis.getAlias() + " from " + computeModuleSpecifier(nis);
		}
		return null;
	}

	private String computeModuleSpecifier(NamespaceImportSpecifier specifier) {
		TModule importedModule = importedModule(specifier);
		if (importedModule != null && !importedModule.eIsProxy()) {
			return importedModule.getModuleSpecifier();
		} else {
			return "module was a proxy";
		}
	}

	private boolean isChildOfUnresolvedImportDecl(ImportSpecifier importSpec) {
		EObject parent = importSpec.eContainer();
		if (parent instanceof ImportDeclaration) {
			TModule module = ((ImportDeclaration) parent).getModule();
			if (module != null) {
				return module.eIsProxy();
			}
		}
		return false;
	}
}
