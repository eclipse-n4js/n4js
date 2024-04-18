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
package org.eclipse.n4js.tooling.organizeImports;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.DefaultImportSpecifier;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.ElementExportDefinition;
import org.eclipse.n4js.ts.types.ExportDefinition;
import org.eclipse.n4js.ts.types.ModuleExportDefinition;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.RecursionGuard;

import com.google.common.collect.Lists;

/**
 * Utilities for ImportSpecifiers
 */
public class ImportSpecifiersUtil {

	/**
	 * @return {@link List} of {@link ImportProvidedElement}s describing imported elements
	 */
	public static List<ImportProvidedElement> mapToImportProvidedElements(
			Collection<ImportSpecifier> importSpecifiers) {

		List<ImportProvidedElement> result = new ArrayList<>();
		for (ImportSpecifier specifier : importSpecifiers) {
			if (specifier instanceof NamespaceImportSpecifier) {
				result.addAll(namespaceToProvidedElements((NamespaceImportSpecifier) specifier));
			} else if (specifier instanceof NamedImportSpecifier) {
				NamedImportSpecifier nis = (NamedImportSpecifier) specifier;
				result.add(new ImportProvidedElement(usedName(nis), importedElementName(nis),
						nis, N4JSLanguageUtils.isHollowElement(nis.getImportedElement())));
			}
		}

		return result;
	}

	/** Map all exported elements from namespace target module to the import provided elements. */
	private static List<ImportProvidedElement> namespaceToProvidedElements(NamespaceImportSpecifier specifier) {
		TModule importedModule = importedModule(specifier);
		if (importedModule == null || importedModule.eIsProxy()) {
			return Collections.emptyList();
		}

		List<ImportProvidedElement> importProvidedElements = new ArrayList<>();
		// add import provided element for a namespace itself
		importProvidedElements.add(new ImportProvidedElement(specifier.getAlias(),
				computeNamespaceActualName(specifier), specifier, false));

		Set<String> localNamesAdded = new HashSet<>();
		collectProvidedElements(importedModule, new RecursionGuard<>(), exportDef -> {
			String localName = importedElementName(specifier, exportDef);
			// function overloading and declaration merging in .d.ts can lead to multiple elements of same name
			// being imported via a single namespace import -> to avoid showing bogus "duplicate import" errors
			// in those cases we need to avoid adding more than one ImportProvidedElement in those cases:
			// TODO IDE-3604 no longer required for function overloading; should probably be removed once declaration
			// merging is supported
			if (localNamesAdded.add(localName)) {
				importProvidedElements.add(
						new ImportProvidedElement(localName, exportDef.getExportedName(),
								specifier, N4JSLanguageUtils.isHollowElement(exportDef.getExportedElement())));
			}
		});

		return importProvidedElements;
	}

	private static void collectProvidedElements(AbstractNamespace namespace, RecursionGuard<TModule> guard,
			Consumer<ElementExportDefinition> consumer) {
		for (ExportDefinition exportDef : Lists.reverse(namespace.getExportDefinitions())) {
			if (exportDef instanceof ElementExportDefinition) {
				consumer.accept((ElementExportDefinition) exportDef);
			} else if (exportDef instanceof ModuleExportDefinition) {
				TModule exportedModule = ((ModuleExportDefinition) exportDef).getExportedModule();
				if (exportedModule != null && !exportedModule.eIsProxy()) {
					if (guard.tryNext(exportedModule)) {
						collectProvidedElements(exportedModule, guard, consumer);
					}
				}
			}
		}
	}

	/**
	 * Computes 'actual' name of the namespace for {@link ImportProvidedElement} entry. If processed namespace refers to
	 * unresolved module, will return dummy name, otherwise returns artificial name composed of prefix and target module
	 * qualified name
	 *
	 */
	public static String computeNamespaceActualName(NamespaceImportSpecifier specifier) {
		if (importedModule(specifier).eIsProxy())
			return ImportProvidedElement.NAMESPACE_PREFIX + specifier.hashCode();
		else
			return ImportProvidedElement.NAMESPACE_PREFIX + importedModule(specifier).getQualifiedName().toString();
	}

	/**
	 * Computes exported name of the element imported by this specifier.
	 */
	public static String importedElementName(NamedImportSpecifier specifier) {
		if (specifier instanceof DefaultImportSpecifier) {
			return N4JSLanguageConstants.EXPORT_DEFAULT_NAME;
		}

		TExportableElement element = specifier.getImportedElement();
		if (element == null)
			return "<" + specifier.getImportedElementAsText() + ">(null)";

		if (element.eIsProxy()) {
			if (specifier.isDeclaredDynamic()) {
				return specifier.getImportedElementAsText();
			}
			return "<" + specifier.getImportedElementAsText() + ">(proxy)";
		}

		return specifier.getImportedElementAsText();
	}

	/** returns locally used name of element imported via {@link NamedImportSpecifier} */
	public static String usedName(NamedImportSpecifier nis) {
		return (nis.getAlias() == null) ? importedElementName(nis) : nis.getAlias();
	}

	/** returns locally used name of element imported via {@link NamespaceImportSpecifier} */
	public static String importedElementName(NamespaceImportSpecifier is, ElementExportDefinition exportDef) {
		return is.getAlias() + "." + exportDef.getExportedName();
	}

	/***/
	public static TModule importedModule(ImportSpecifier is) {
		return ((ImportDeclaration) is.eContainer()).getModule();
	}

	/**
	 * Returns true if the module that is target of the import declaration containing provided import specifier is
	 * invalid (null, proxy, no name). Additionally for {@link NamedImportSpecifier} instances checks if linker failed
	 * to resolve target (is null, proxy, or has no name)
	 *
	 * @param spec
	 *            - the ImportSpecifier to investigate
	 * @return true import looks broken
	 */
	public static boolean isBrokenImport(ImportSpecifier spec) {
		return isBrokenImport((ImportDeclaration) spec.eContainer(), spec);
	}

	/**
	 * Returns true iff the target module of the given import declaration is invalid (null, proxy, no name). Import
	 * specifiers are not checked.
	 */
	public static boolean isBrokenImport(ImportDeclaration decl) {
		return isBrokenImport(decl, null);
	}

	private static boolean isBrokenImport(ImportDeclaration decl, ImportSpecifier spec) {
		TModule module = decl.getModule();

		// check target module
		if (module == null || module.eIsProxy() || isNullOrEmpty(module.getQualifiedName())) {
			return true;
		}

		// check import specifier
		if (spec instanceof NamedImportSpecifier && !spec.isDeclaredDynamic()) {
			NamedImportSpecifier nis = (NamedImportSpecifier) spec;
			if (nis.eIsProxy() || isNullOrEmpty(nis.getImportedElementAsText())) {
				return true;
			}

			// check what object that is linked
			TExportableElement imported = nis.getImportedElement();
			if (imported == null) {
				return true;
			}
			if (imported.eIsProxy()) {
				return true;
			}
		}

		return false;
	}
}
