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
package org.eclipse.n4js.typesbuilder

import java.util.HashMap
import java.util.Map
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TDynamicElement
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory

/**
 * A types builder that creates/relinks type model elements related to imports, as described
 * {@link #createTypeModelElementsForImports(Script, TModule, boolean) here}.
 */
class N4JSImportTypesBuilder {
	
	/**
	 * Relinks the elements created by {@link #createTypeModelElementsForImports(Script, TModule, boolean)}.
	 * <p>
	 * If for a given {@link NamespaceImportSpecifier} no {@link ModuleNamespaceVirtualType} 
	 * instance exists yet, a new one is created and added to the module's {@link TModule#internalTypes} 
	 */
	def void relinkTypeModelElementsForImports(Script script, TModule target, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			val namespaceTypesByName = getExistingNamespaceTypesByName(target);
			val dynElemsByName = getExistingDynamicElementsByName(target);

			for (importDecl : script.scriptElements.filter(ImportDeclaration).toList) {
				val namespaceImport = getNamespaceImportSpecifier(importDecl)
				if(namespaceImport !== null) {
					val importedModule = importDecl.eGet(N4JSPackage.eINSTANCE.moduleRef_Module, false) as TModule
					val existingNamespaceType = namespaceTypesByName.get(namespaceImport.alias);
					
					if (existingNamespaceType !== null) {
						// if a namespace import type exists, relink it to the new AST 
						relinkNamespaceType(existingNamespaceType, namespaceImport, importedModule);
					} else {
						// otherwise re-create a new namespace import type and add it to the internal types of the module
						target.internalTypes += createModuleNamespaceVirtualType(namespaceImport, importedModule);
					}
					
				} else {

					for (importSpec : importDecl.importSpecifiers) {
						if (importSpec instanceof NamedImportSpecifier) { // includes DefaultImportSpecifier
							if (importSpec.declaredDynamic) {
								val existingDynElem = dynElemsByName.get(getNameForDynamicElement(importSpec));
								
								if (existingDynElem !== null) {
									relinkDynamicElement(existingDynElem, importSpec);
								} else {
									target.internalDynamicElements += createDynamicElement(importSpec);
								}
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
	def void createTypeModelElementsForImports(Script script, TModule target, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			for (importDecl : script.scriptElements.filter(ImportDeclaration).toList) {
				val namespaceImport = getNamespaceImportSpecifier(importDecl)
				if (namespaceImport !== null) {
					val importedModule = importDecl.eGet(N4JSPackage.eINSTANCE.moduleRef_Module,
						false) as TModule
					target.internalTypes += createModuleNamespaceVirtualType(namespaceImport, importedModule);
				} else {
					for (importSpec : importDecl.importSpecifiers) {
						if (importSpec instanceof NamedImportSpecifier) { // includes DefaultImportSpecifier
							if (importSpec.declaredDynamic) {
								target.internalDynamicElements += createDynamicElement(importSpec);
							}
						}
					}
				}
			}
		}
	}

	private def ModuleNamespaceVirtualType createModuleNamespaceVirtualType(NamespaceImportSpecifier importSpecifier, 
		TModule importedModule) {
		//for NamespaceImportpecifiers there is only ImportSpecifier one in the ImportDeclaration
		val type = TypesFactory.eINSTANCE.createModuleNamespaceVirtualType
		type.name = importSpecifier.alias
		/* don't resolve module proxy */
		type.module = importedModule;
		type.declaredDynamic = importSpecifier.declaredDynamic;
		// link
		type.astElement = importSpecifier;
		importSpecifier.definedType = type;
		
		return type;
	}
	
	private def TDynamicElement createDynamicElement(NamedImportSpecifier importSpecifier) {
		val elem = TypesFactory.eINSTANCE.createTDynamicElement();
		elem.name = getNameForDynamicElement(importSpecifier);
		elem.astElement = importSpecifier;
		importSpecifier.definedDynamicElement = elem;
		return elem;
	}
	
	private def String getNameForDynamicElement(NamedImportSpecifier importSpecifier) {
		return importSpecifier.alias ?: importSpecifier.importedElementAsText;
	}
	
	/** 
	 * Obtains the {@link NamespaceImportSpecifier} of the given {@code importDeclaration}.
	 * 
	 * Returns {@code null}, if the given import declaration does not represent a namespace import.
	 */
	private def NamespaceImportSpecifier getNamespaceImportSpecifier(ImportDeclaration importDeclaration) {
		val namespaceImportSpecifiers = importDeclaration.importSpecifiers.filter(NamespaceImportSpecifier).toList
		if (!namespaceImportSpecifiers.empty) {
			return namespaceImportSpecifiers.head
		} else {
			return null;
		}
	}
	
	/**
	 * Relinks the given {@link ModuleNamespaceVirtualType} to the given import specifier.
	 */
	private def void relinkNamespaceType(ModuleNamespaceVirtualType namespaceType, 
		NamespaceImportSpecifier namespaceImportSpecifier, TModule importedModule) {
		
		namespaceType.module = importedModule;
		namespaceType.astElement = namespaceImportSpecifier;
		namespaceImportSpecifier.definedType = namespaceType;
	}
	
	private def void relinkDynamicElement(TDynamicElement elem,  NamedImportSpecifier namedImportSpecifier) {
		elem.astElement = namedImportSpecifier;
		namedImportSpecifier.definedDynamicElement = elem;
	}
	
	/**
	 * Returns a map of all existing {@link ModuleNamespaceVirtualType} contained in the
	 * given {@link TModule}.
	 * 
	 * Includes exposed and non-exposed internal types.
	 */
	private def Map<String, ModuleNamespaceVirtualType> getExistingNamespaceTypesByName(TModule module) {
		val namespaceTypesByName = new HashMap<String, ModuleNamespaceVirtualType>();
		if (module.internalTypes !== null) {
			module.internalTypes
				.filter(ModuleNamespaceVirtualType)
				.forEach[type | namespaceTypesByName.put(type.name, type) ]
		}
		if (module.exposedInternalTypes !== null) {
			module.exposedInternalTypes
				.filter(ModuleNamespaceVirtualType)
				.forEach[type | namespaceTypesByName.put(type.name, type) ]
		}
		return namespaceTypesByName;
	}

	private def Map<String, TDynamicElement> getExistingDynamicElementsByName(TModule module) {
		val dynElemsByName = new HashMap<String, TDynamicElement>();
		if (module.internalDynamicElements !== null) {
			module.internalDynamicElements
				.forEach[elem | dynElemsByName.put(elem.name, elem) ]
		}
		return dynElemsByName;
	}
}
