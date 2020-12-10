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
package org.eclipse.n4js.organize.imports

import java.util.ArrayList
import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType
import org.eclipse.n4js.ts.types.TAnnotableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.xtext.EcoreUtil2

import static extension org.eclipse.n4js.organize.imports.InjectedTypesResolverUtility.*
import static extension org.eclipse.n4js.organize.imports.RefNameUtil.*

/**
 * Static analysis for {@link Script} dependencies. Analyzes all identifiers in the {@link Script},
 * and on (on demand) {@link Type}s or {@link TypeRef}s. During analysis identifiers from {@link NamedImportSpecifier}
 * and {@link NamespaceImportSpecifier} are taken into account. Note that if result analysis does not contain descriptions matching
 * some of the imports, it means those imports are not used, and can be removed from script, or ignored during compilation.
 *
 * Result of analysis is list of {@link ScriptDependency} instances describing what {@link EObject}s should be imported.
 * <ul>
 * Note:
 *  <li> declarations marked with "@ProvidedByRuntime" are ignored (they are never in the result)</li>
 *  <li> declarations marked with "@Global" are not ignored (can show up in the result)</li>
 * </ul>
 */
class ScriptDependencyResolver {

	/**
	 * Resolves dependencies only from {@link IdentifierRef}s, no {@link Type}s or {@link TypeRef}s
	 * are taken into account.
	 */
	def public static List<ScriptDependency> usedDependencies(Script script) {
		if (null === script) return emptyList
		allRequiredExternalDeclaration(script, Collections.EMPTY_SET, Collections.EMPTY_SET)
	}

	/**
	 * Resolves dependencies from {@link IdentifierRef}s and {@link Type}s that are
	 * injected into local type declarations.
	 */
	def public static List<ScriptDependency> usedDependenciesWithInejctedTypes(Script script) {
		if (null === script) return emptyList
		allRequiredExternalDeclaration(script, script.findAllInjected, Collections.EMPTY_SET)
	}

	/**
	 * Resolves dependencies from {@link IdentifierRef}s and all {@link TypeRef}s.
	 */
	def public static List<ScriptDependency> usedDependenciesTypeRefs(Script script) {
		if (null === script) return emptyList
		allRequiredExternalDeclaration(script, Collections.EMPTY_SET, script.eAllContents.filter(TypeRef).toList)
	}

	/**
	 * Looks through all {@link IdentifierRef} for external dependencies
	 * (from different module than currently analyzed script containing module).
	 * Additionally looks through all types used as super types and implemented interfaces.
	 * Not used types (see {@link #shouldBeImported}) are removed from external dependencies.
	 *
	 * @param script to be analyzed
	 * @param typesToBeIncluded force specific collection of {@link Type}s to be considered for as dependencies
	 * @param typeRefsToBeIncluded force specific collection of {@link TypeRef}s to be considered for as dependencies
	 */
	def private static List<ScriptDependency> allRequiredExternalDeclaration(Script script, Collection<Type> typesToBeIncluded,
		Collection<TypeRef> typeRefsToBeIncluded) {

		val indirectlyImported = script.eAllContents.filter(N4TypeDeclaration).toList
		val identifierRefs = script.eAllContents.filter(IdentifierRef).toList

		val potentialDependencies = <EObject>newArrayList
		potentialDependencies += indirectlyImported
		potentialDependencies += identifierRefs
		potentialDependencies += typesToBeIncluded
		potentialDependencies += typeRefsToBeIncluded

		val namedImportSpecifiers = script.eAllContents.filter(NamedImportSpecifier).
			filter[it.importedElement !== null].toList
		val usedNamespaceSpecifiers = script.eAllContents.filter(NamespaceImportSpecifier).toInvertedMap[false]

		val baseModule = script.module;
		var depsImportedWithName = potentialDependencies.map [
			handle(namedImportSpecifiers.toMap[importedElement.name], usedNamespaceSpecifiers,
				[ EObject eo | shouldBeImported(baseModule, eo) ])
		].flatten.filterNull.toSet.toList.sortBy[localName]

		return depsImportedWithName
	}

	/**
	 * Checks if a given EObject should be imported.
	 *
	 * <ul>
	 * Evaluates to true if:
	 * <li> provided EO is not from the module provided at creation time (that module is assumed to be one for which we analyze dependencies)</li>
	 * <li> provided EO is not from built in types (usually n4ts files)</li>
	 * <li> (in case of AST elements) is not annotated with {@link AnnotationDefinition.PROVIDED_BY_RUNTIME)</li>
	 * <li> (in case of TS elements) providedByRuntime evaluates to false</li>
	 * </ul>
	 *
	 * @returns true if given EO should be imported
	 */
	def public static boolean shouldBeImported(TModule baseModule, EObject eo) {
		if (eo instanceof ModuleNamespaceVirtualType) {
			return true;
		}

		if (eo instanceof AnnotableElement) {
			if (AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(eo)) {
				return false;
			}
		} else if (eo instanceof TAnnotableElement) {
			if (AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(eo)) {
				return false;
			} else if (eo instanceof Type) {
				// TODO is this dead code
				if (eo.providedByRuntime) {
					return false;
				}
			} else if (eo instanceof TVariable) {
				// TODO is this dead code
				if (eo.providedByRuntime) {
					return false;
				}
			}
		}

		// ignore built-in things as n4scheme:/console.n4ts :
		// in non platform realm check if URI describes file,
		// in eclipse platform realm check if URI describes platform resource
		return eo.eResource?.getURI !== null //
		&& ((eo.eResource.getURI.file || eo.eResource.getURI.platformResource)) //
		// and check if modules/files are different
		&& (! eo.eResource.getURI.toString.equals(baseModule.eResource.getURI.toString))
	}

	def private static boolean isNamespaceDependencyHandlingNeeded(
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, TModule targMod) {

		return usedNamespaceSpecifiers.keySet.exists[is|(is.eContainer as ImportDeclaration).module === targMod]
	}

	def private static createScriptDependency(Type type, Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers) {
		if (nameToNamedImportSpecifiers.containsKey(type.name)) {
			val nis = nameToNamedImportSpecifiers.get(type.name)
			val identifiableElement = nis.importedElement
			new ScriptDependency(nis.alias ?: identifiableElement.name, identifiableElement.name, identifiableElement,
				(identifiableElement.eContainer as TModule))
		} else if (isNamespaceDependencyHandlingNeeded(usedNamespaceSpecifiers, type.containingModule)) {
			createDependencyOnNamespace(usedNamespaceSpecifiers, type.containingModule)
		} else {
			// add dependency, looks like something that is @Global but not @ProvidedByRuntime
			new ScriptDependency(type.name, type.name, type, type.containingModule)
		}
	}

	def private static ScriptDependency createDependencyOnNamespace(
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, TModule targMod) {
		val is = usedNamespaceSpecifiers.keySet.findFirst [ is |
			(is.eContainer as ImportDeclaration).module === targMod
		]
		val used = usedNamespaceSpecifiers.get(is)
		if (!used) {
			// add dependency on the namespace
			usedNamespaceSpecifiers.put(is, true)
			new ScriptDependency(is.alias, null, // For namespace imports, the actual name is intentionally null.
			null, // For namespace imports, this is ModuleNamespaceVirtualType, but intentionally null
			targMod)
		} else {
			// namespace was already used
			null
		}
	}

	def private static dispatch Iterable<ScriptDependency> handle(EObject eo,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {

		return newArrayList()
	}

	def private static dispatch Iterable<ScriptDependency> handle(TypeRef eo,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {

		return newArrayList()
	}

	def private static dispatch Iterable<ScriptDependency> handle(Void eo,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {

		return newArrayList()
	}

	def private static dispatch Iterable<ScriptDependency> handle(TFunction tFunction,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {
		// TODO is there nothing to do?

		return newArrayList()
	}

	def private static dispatch Iterable<ScriptDependency> handle(N4ClassDeclaration eo,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {

		val tClass = eo.definedType as TClass
		handle(tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare)
	}

	def private static dispatch Iterable<ScriptDependency> handle(N4InterfaceDeclaration eo,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {

		val tInterface = eo.definedType as TInterface
		handle(tInterface, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare)
	}

	def private static dispatch Iterable<ScriptDependency> handle(ParameterizedTypeRef eo,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {

		// added check for container instance, as polyfill tests were crashing when
		// eo.declared type was TVariable and its container TClass
		if (eo.declaredType !== null && eo.declaredType.eContainer instanceof TModule &&
			compare.apply(eo.declaredType)) {
			val typeName = eo.findTypeName
			if (typeName !== null) { // null means not typed in script (e.g. TypesComputer)-> no import necessary
				return newArrayList(
					new ScriptDependency(typeName, eo.declaredType.name, eo.declaredType,
						(eo.declaredType.eContainer as TModule)))
			}
		}
		return newArrayList()
	}

	/**
	 * Resolves dependency from identifier reference.
	 */
	def private static dispatch Iterable<ScriptDependency> handle(IdentifierRef idRef,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {

		val targetElem = idRef.id;
		if(targetElem === null){
			//broken identifier ref? smoke tests?
			return newArrayList()
		}

		if (compare.apply(targetElem)) {
			val containingModule = EcoreUtil2.getContainerOfType(targetElem, TModule);
			return newArrayList(
				new ScriptDependency(idRef.findIdentifierName, targetElem.name, targetElem, containingModule))
		} else if (targetElem instanceof ModuleNamespaceVirtualType) {
			val targMod = targetElem.module

			if (isNamespaceDependencyHandlingNeeded(usedNamespaceSpecifiers, targMod)) {
				return newArrayList(createDependencyOnNamespace(usedNamespaceSpecifiers, targMod))
			}
		}
		return newArrayList()
	}

	def private static dispatch Iterable<ScriptDependency> handle(TClass tClass,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {

		val deps = new ArrayList()

		deps.add(tClass)

		val interfaces = tClass.implementedInterfaceRefs
		if (!interfaces.nullOrEmpty) deps.addAll(interfaces.map[declaredType].filterNull)

		val superClass = tClass.superClassRef
		if (superClass !== null) deps.add(superClass.declaredType)

		deps.filter[compare.apply(it)].map[createScriptDependency(nameToNamedImportSpecifiers, usedNamespaceSpecifiers)]
	}

	def private static dispatch Iterable<ScriptDependency> handle(TInterface tInterface,
		Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, (EObject)=>boolean compare) {

		val deps = new ArrayList()

		deps.add(tInterface)

		val rs = tInterface.superInterfaceRefs
		if (!rs.nullOrEmpty) deps.addAll(rs.map[declaredType].filterNull)

		deps.filter[compare.apply(it)].map[createScriptDependency(nameToNamedImportSpecifiers, usedNamespaceSpecifiers)]
	}

}
