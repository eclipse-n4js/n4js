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
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory

/**
 * A types builder that creates/relinks {@link ModuleNamespaceVirtualType} instances
 * based on given {@link ImportDeclaration}s.
 */
class N4JSNamespaceImportTypesBuilder {
	
	/**
	 * Relinks the namespace types after reconciling the given {@link TModule}
	 * 
	 * Relinks existing instances of {@link ModuleNamespaceVirtualType} instances to the new AST. 
	 * 
	 * If for a given {@link NamespaceImportSpecifier} no {@link ModuleNamespaceVirtualType} 
	 * instance exists yet, a new one is created and added to the module's {@link TModule#internalTypes} 
	 */
	def void relinkNamespaceTypes(Script script, TModule target, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			// first construct a map of existing namespace types by name 
			val namespaceTypesByName = getExistingNamespaceTypesByName(target);
			//process namespace imports
			for (importDeclaration : script.scriptElements.filter(ImportDeclaration).toList) {
				val namespaceImport = getNamespaceImportSpecifier(importDeclaration)
				if(namespaceImport !== null) {
					val importedModule = importDeclaration.eGet(N4JSPackage.eINSTANCE.importDeclaration_Module, false) as TModule
					val existingNamespaceType = namespaceTypesByName.get(namespaceImport.alias);
					
					if (existingNamespaceType !== null) {
						// if a namespace import type exists, relink it to the new AST 
						relinkNamespaceType(existingNamespaceType, namespaceImport, importedModule);
					} else {
						// otherwise re-create a new namespace import type and add it to the internal types of the module
						target.internalTypes += createModuleNamespaceVirtualType(namespaceImport, importedModule);
					}
					
				}
			}
		}
	}
	
	/**
	 * Creates a new {@link ModuleNamespaceVirtualType} instance for the given {@link ImportDeclaration}
	 * and return it.
	 */
	def ModuleNamespaceVirtualType createModuleNamespaceVirtualType(NamespaceImportSpecifier importSpecifier, 
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
	
	/** 
	 * Obtains the {@link NamespaceImportSpecifier} of the given {@code importDeclaration}.
	 * 
	 * Returns {@code null}, if the given import declaration does not represent a namespace import.
	 */
	def NamespaceImportSpecifier getNamespaceImportSpecifier(ImportDeclaration importDeclaration) {
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
}