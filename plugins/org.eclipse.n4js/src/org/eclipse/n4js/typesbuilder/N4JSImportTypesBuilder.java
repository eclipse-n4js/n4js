/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesbuilder;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TDynamicElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesFactory;

import com.google.inject.Inject;

/**
 * A types builder that creates/relinks type model elements related to imports, as described
 * {@link #createTypeModelElementsForImports(Script, TModule, boolean) here}.
 */
class N4JSImportTypesBuilder {

	@Inject
	N4JSTypesBuilderHelper _N4JSTypesBuilderHelper;

	/**
	 * Relinks the elements created by {@link #createTypeModelElementsForImports(Script, TModule, boolean)}.
	 * <p>
	 * If for a given {@link NamespaceImportSpecifier} no {@link ModuleNamespaceVirtualType} instance exists yet, a new
	 * one is created and added to the module's {@link TModule#getInternalTypes()}
	 */
	void relinkTypeModelElementsForImports(Script script, TModule target, boolean preLinkingPhase) {
		if (preLinkingPhase) {
			return;
		}
		Map<String, ModuleNamespaceVirtualType> namespaceTypesByName = getExistingNamespaceTypesByName(target);
		Map<String, TDynamicElement> dynElemsByName = getExistingDynamicElementsByName(target);
		List<ImportDeclaration> ids = toList(filter(script.getScriptElements(), ImportDeclaration.class));

		for (ImportDeclaration importDecl : ids) {
			NamespaceImportSpecifier namespaceImport = getNamespaceImportSpecifier(importDecl);
			if (namespaceImport != null) {
				TModule importedModule = (TModule) importDecl.eGet(N4JSPackage.eINSTANCE.getModuleRef_Module(), false);
				ModuleNamespaceVirtualType existingNamespaceType = namespaceTypesByName.get(namespaceImport.getAlias());

				if (existingNamespaceType != null) {
					// if a namespace import type exists, relink it to the new AST
					relinkNamespaceType(existingNamespaceType, namespaceImport, importedModule);
				} else {
					// otherwise re-create a new namespace import type and add it to the internal types of the module
					_N4JSTypesBuilderHelper.addNewModuleNamespaceVirtualType(target, namespaceImport.getAlias(),
							importedModule, namespaceImport.isDeclaredDynamic(), namespaceImport);
				}

			} else {

				for (ImportSpecifier importSpec : importDecl.getImportSpecifiers()) {
					if (importSpec instanceof NamedImportSpecifier) { // includes DefaultImportSpecifier
						NamedImportSpecifier nis = (NamedImportSpecifier) importSpec;
						if (importSpec.isDeclaredDynamic()) {
							TDynamicElement existingDynElem = dynElemsByName.get(getNameForDynamicElement(nis));

							if (existingDynElem != null) {
								relinkDynamicElement(existingDynElem, nis);
							} else {
								target.getInternalDynamicElements().add(createDynamicElement(nis));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Creates ...
	 * <ul>
	 * <li>{@link ModuleNamespaceVirtualType} instances for namespace imports.
	 * <li>{@link TDynamicElement} instances for <em>dynamic</em> named and default imports.
	 * </ul>
	 */
	void createTypeModelElementsForImports(Script script, TModule target, boolean preLinkingPhase) {
		if (preLinkingPhase) {
			return;
		}
		List<ImportDeclaration> ids = toList(filter(script.getScriptElements(), ImportDeclaration.class));
		for (ImportDeclaration importDecl : ids) {
			NamespaceImportSpecifier namespaceImport = getNamespaceImportSpecifier(importDecl);
			if (namespaceImport != null) {
				TModule importedModule = (TModule) importDecl.eGet(N4JSPackage.eINSTANCE.getModuleRef_Module(),
						false);
				_N4JSTypesBuilderHelper.addNewModuleNamespaceVirtualType(target, namespaceImport.getAlias(),
						importedModule, namespaceImport.isDeclaredDynamic(), namespaceImport);
			} else {
				for (ImportSpecifier importSpec : importDecl.getImportSpecifiers()) {
					if (importSpec instanceof NamedImportSpecifier) { // includes DefaultImportSpecifier
						if (importSpec.isDeclaredDynamic()) {
							target.getInternalDynamicElements()
									.add(createDynamicElement((NamedImportSpecifier) importSpec));
						}
					}
				}
			}
		}
	}

	private TDynamicElement createDynamicElement(NamedImportSpecifier importSpecifier) {
		TDynamicElement elem = TypesFactory.eINSTANCE.createTDynamicElement();
		elem.setName(getNameForDynamicElement(importSpecifier));
		elem.setAstElement(importSpecifier);
		importSpecifier.setDefinedDynamicElement(elem);
		return elem;
	}

	private String getNameForDynamicElement(NamedImportSpecifier importSpecifier) {
		return importSpecifier.getAlias() != null ? importSpecifier.getAlias()
				: importSpecifier.getImportedElementAsText();
	}

	/**
	 * Obtains the {@link NamespaceImportSpecifier} of the given {@code importDeclaration}.
	 *
	 * Returns {@code null}, if the given import declaration does not represent a namespace import.
	 */
	private NamespaceImportSpecifier getNamespaceImportSpecifier(ImportDeclaration importDeclaration) {
		List<NamespaceImportSpecifier> namespaceImportSpecifiers = toList(
				filter(importDeclaration.getImportSpecifiers(), NamespaceImportSpecifier.class));
		if (!namespaceImportSpecifiers.isEmpty()) {
			return namespaceImportSpecifiers.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Relinks the given {@link ModuleNamespaceVirtualType} to the given import specifier.
	 */
	private void relinkNamespaceType(ModuleNamespaceVirtualType namespaceType,
			NamespaceImportSpecifier namespaceImportSpecifier, TModule importedModule) {

		namespaceType.setModule(importedModule);
		namespaceType.setAstElement(namespaceImportSpecifier);
		namespaceImportSpecifier.setDefinedType(namespaceType);
	}

	private void relinkDynamicElement(TDynamicElement elem, NamedImportSpecifier namedImportSpecifier) {
		elem.setAstElement(namedImportSpecifier);
		namedImportSpecifier.setDefinedDynamicElement(elem);
	}

	/**
	 * Returns a map of all existing {@link ModuleNamespaceVirtualType} contained in the given {@link TModule}.
	 *
	 * Includes exposed and non-exposed internal types.
	 */
	private Map<String, ModuleNamespaceVirtualType> getExistingNamespaceTypesByName(TModule module) {
		Map<String, ModuleNamespaceVirtualType> namespaceTypesByName = new HashMap<>();
		if (module.getInternalTypes() != null) {
			for (Type type : module.getInternalTypes()) {
				if (type instanceof ModuleNamespaceVirtualType) {
					ModuleNamespaceVirtualType mnvt = (ModuleNamespaceVirtualType) type;
					namespaceTypesByName.put(mnvt.getName(), mnvt);
				}
			}
		}
		if (module.getExposedInternalTypes() != null) {
			for (Type type : module.getExposedInternalTypes()) {
				if (type instanceof ModuleNamespaceVirtualType) {
					ModuleNamespaceVirtualType mnvt = (ModuleNamespaceVirtualType) type;
					namespaceTypesByName.put(mnvt.getName(), mnvt);
				}
			}
		}
		return namespaceTypesByName;
	}

	private Map<String, TDynamicElement> getExistingDynamicElementsByName(TModule module) {
		HashMap<String, TDynamicElement> dynElemsByName = new HashMap<>();
		if (module.getInternalDynamicElements() != null) {
			for (TDynamicElement elem : module.getInternalDynamicElements()) {
				dynElemsByName.put(elem.getName(), elem);
			}
		}
		return dynElemsByName;
	}
}
