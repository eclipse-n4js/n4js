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

import static org.eclipse.n4js.tooling.organizeImports.InjectedTypesResolverUtility.findAllInjected;
import static org.eclipse.n4js.tooling.organizeImports.RefNameUtil.findTypeName;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isNullOrEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toMap;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toInvertedMap;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TDynamicElement;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.xtext.EcoreUtil2;

/**
 * Static analysis for {@link Script} dependencies. Analyzes all identifiers in the {@link Script}, and on (on demand)
 * {@link Type}s or {@link TypeRef}s. During analysis identifiers from {@link NamedImportSpecifier} and
 * {@link NamespaceImportSpecifier} are taken into account. Note that if result analysis does not contain descriptions
 * matching some of the imports, it means those imports are not used, and can be removed from script, or ignored during
 * compilation.
 *
 * Result of analysis is list of {@link ScriptDependency} instances describing what {@link EObject}s should be imported.
 * <ul>
 * Note:
 * <li>declarations marked with "@ProvidedByRuntime" are ignored (they are never in the result)</li>
 * <li>declarations marked with "@Global" are not ignored (can show up in the result)</li>
 * </ul>
 */
@SuppressWarnings("unused")
public class ScriptDependencyResolver {

	/**
	 * Resolves dependencies only from {@link IdentifierRef}s, no {@link Type}s or {@link TypeRef}s are taken into
	 * account.
	 */
	public static List<ScriptDependency> usedDependencies(Script script) {
		if (null == script) {
			return Collections.emptyList();
		}
		return allRequiredExternalDeclaration(script, Collections.emptySet(), Collections.emptySet());
	}

	/**
	 * Resolves dependencies from {@link IdentifierRef}s and {@link Type}s that are injected into local type
	 * declarations.
	 */
	public static List<ScriptDependency> usedDependenciesWithInejctedTypes(Script script) {
		if (null == script) {
			return Collections.emptyList();
		}
		return allRequiredExternalDeclaration(script, findAllInjected(script), Collections.emptySet());
	}

	/**
	 * Resolves dependencies from {@link IdentifierRef}s and all {@link TypeRef}s.
	 */
	public static List<ScriptDependency> usedDependenciesTypeRefs(Script script) {
		if (null == script) {
			return Collections.emptyList();
		}
		return allRequiredExternalDeclaration(script, Collections.emptySet(),
				toList(filter(script.eAllContents(), TypeRef.class)));
	}

	/**
	 * Looks through all {@link IdentifierRef} for external dependencies (from different module than currently analyzed
	 * script containing module). Additionally looks through all types used as super types and implemented interfaces.
	 * Not used types (see {@link #shouldBeImported}) are removed from external dependencies.
	 *
	 * @param script
	 *            to be analyzed
	 * @param typesToBeIncluded
	 *            force specific collection of {@link Type}s to be considered for as dependencies
	 * @param typeRefsToBeIncluded
	 *            force specific collection of {@link TypeRef}s to be considered for as dependencies
	 */
	private static List<ScriptDependency> allRequiredExternalDeclaration(Script script,
			Collection<Type> typesToBeIncluded,
			Collection<TypeRef> typeRefsToBeIncluded) {

		List<N4TypeDeclaration> indirectlyImported = toList(filter(script.eAllContents(), N4TypeDeclaration.class));
		List<IdentifierRef> identifierRefs = toList(filter(script.eAllContents(), IdentifierRef.class));

		List<EObject> potentialDependencies = new ArrayList<>();
		potentialDependencies.addAll(indirectlyImported);
		potentialDependencies.addAll(identifierRefs);
		potentialDependencies.addAll(typesToBeIncluded);
		potentialDependencies.addAll(typeRefsToBeIncluded);

		List<NamedImportSpecifier> namedImportSpecifiers = toList(
				filter(filter(script.eAllContents(), NamedImportSpecifier.class),
						nis -> nis.getImportedElement() != null));
		Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers = toInvertedMap(
				filter(script.eAllContents(), NamespaceImportSpecifier.class), x -> false);

		TModule baseModule = script.getModule();

		Set<ScriptDependency> sortedDeps = new TreeSet<>(Comparator.comparing(dep -> dep.localName));
		for (EObject eo : potentialDependencies) {
			Map<String, NamedImportSpecifier> nisByName = toMap(namedImportSpecifiers,
					nis -> nis.getImportedElement().getName());

			Iterable<ScriptDependency> deps = handle(eo, nisByName, usedNamespaceSpecifiers,
					eo2 -> shouldBeImported(baseModule, eo2));

			for (ScriptDependency dep : deps) {
				if (dep != null) {
					sortedDeps.add(dep);
				}
			}
		}

		return new ArrayList<>(sortedDeps);
	}

	/**
	 * Checks if a given EObject should be imported.
	 *
	 * <ul>
	 * Evaluates to true if:
	 * <li>provided EO is not from the module provided at creation time (that module is assumed to be one for which we
	 * analyze dependencies)</li>
	 * <li>provided EO is not from built in types</li>
	 * <li>(in case of AST elements) is not annotated with {@link AnnotationDefinition#PROVIDED_BY_RUNTIME}</li>
	 * <li>(in case of TS elements) providedByRuntime evaluates to false</li>
	 * </ul>
	 *
	 * @returns true if given EO should be imported
	 */
	public static boolean shouldBeImported(TModule baseModule, EObject eo) {
		if (eo instanceof ModuleNamespaceVirtualType
				|| eo instanceof TDynamicElement) {
			return true;
		}

		EObject containingModule = EcoreUtil.getRootContainer(eo);
		if (containingModule instanceof TModule) {
			if (AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation((TModule) containingModule)
					|| (ResourceType.getResourceType(containingModule) == ResourceType.DTS
							&& AnnotationDefinition.GLOBAL.hasAnnotation((TModule) containingModule))) {
				return false;
			}
		}

		if (eo instanceof AnnotableElement) {
			if (AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation((AnnotableElement) eo)) {
				return false;
			}
		} else if (eo instanceof TAnnotableElement) {
			if (AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation((TAnnotableElement) eo)) {
				return false;
			} else if (eo instanceof Type) {
				// TODO is this dead code
				if (((Type) eo).isProvidedByRuntime()) {
					return false;
				}
			} else if (eo instanceof TVariable) {
				// TODO is this dead code
				if (((TVariable) eo).isProvidedByRuntime()) {
					return false;
				}
			}
		}

		// ignore built-in things as n4scheme:/console.n4jsd:
		// in non platform realm check if URI describes file,
		// in eclipse platform realm check if URI describes platform resource
		Resource res = eo.eResource();
		return res != null && res.getURI() != null //
				&& ((res.getURI().isFile() || res.getURI().isPlatformResource())) //
				// and check if modules/files are different
				&& (!res.getURI().toString().equals(baseModule.eResource().getURI().toString()));
	}

	private static boolean isNamespaceDependencyHandlingNeeded(
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, TModule targMod) {

		return exists(usedNamespaceSpecifiers.keySet(),
				is -> ((ImportDeclaration) is.eContainer()).getModule() == targMod);
	}

	private static ScriptDependency createScriptDependency(Type type,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers) {
		if (nameToNamedImportSpecifiers.containsKey(type.getName())) {
			NamedImportSpecifier nis = nameToNamedImportSpecifiers.get(type.getName());
			TExportableElement identifiableElement = nis.getImportedElement();
			TModule module = EcoreUtil2.getContainerOfType(identifiableElement, TModule.class);
			return new ScriptDependency(nis.getAlias() != null ? nis.getAlias() : identifiableElement.getName(),
					identifiableElement.getName(), identifiableElement, module);
		} else if (isNamespaceDependencyHandlingNeeded(usedNamespaceSpecifiers, type.getContainingModule())) {
			return createDependencyOnNamespace(usedNamespaceSpecifiers, type.getContainingModule());
		} else {
			// add dependency, looks like something that is @Global but not @ProvidedByRuntime
			return new ScriptDependency(type.getName(), type.getName(), type, type.getContainingModule());
		}
	}

	private static ScriptDependency createDependencyOnNamespace(
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, TModule targMod) {
		NamespaceImportSpecifier is = findFirst(usedNamespaceSpecifiers.keySet(),
				nis -> ((ImportDeclaration) nis.eContainer()).getModule() == targMod);
		boolean used = usedNamespaceSpecifiers.get(is);
		if (!used) {
			// add dependency on the namespace
			usedNamespaceSpecifiers.put(is, true);
			return new ScriptDependency(is.getAlias(),
					// For namespace imports, the actual name is intentionally null.
					null,
					// For namespace imports, this is ModuleNamespaceVirtualType, but intentionally null
					null,
					targMod);
		} else {
			// namespace was already used
			return null;
		}
	}

	private static Iterable<ScriptDependency> handle(EObject tClass,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {
		if (tClass instanceof TClass
				&& compare != null) {
			return handle((TClass) tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare);
		} else if (tClass instanceof TInterface
				&& compare != null) {
			return handle((TInterface) tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare);
		} else if (tClass instanceof N4ClassDeclaration
				&& compare != null) {
			return handle((N4ClassDeclaration) tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare);
		} else if (tClass instanceof N4InterfaceDeclaration
				&& compare != null) {
			return handle((N4InterfaceDeclaration) tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers,
					compare);
		} else if (tClass instanceof TFunction
				&& compare != null) {
			return handle((TFunction) tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare);
		} else if (tClass instanceof ParameterizedTypeRef
				&& compare != null) {
			return handle((ParameterizedTypeRef) tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare);
		} else if (tClass instanceof IdentifierRef
				&& compare != null) {
			return handle((IdentifierRef) tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare);
		} else if (tClass instanceof TypeRef
				&& compare != null) {
			return handle((TypeRef) tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare);
		}
		return new ArrayList<>();
	}

	private static Iterable<ScriptDependency> handle(TypeRef eo,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {

		return new ArrayList<>();
	}

	private static Iterable<ScriptDependency> handle(Void eo,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {

		return new ArrayList<>();
	}

	private static Iterable<ScriptDependency> handle(TFunction tFunction,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {
		// TODO is there nothing to do?

		return new ArrayList<>();
	}

	private static Iterable<ScriptDependency> handle(N4ClassDeclaration eo,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {

		TClass tClass = (TClass) eo.getDefinedType();
		return handle(tClass, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare);
	}

	private static Iterable<ScriptDependency> handle(N4InterfaceDeclaration eo,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {

		TInterface tInterface = (TInterface) eo.getDefinedType();
		return handle(tInterface, nameToNamedImportSpecifiers, usedNamespaceSpecifiers, compare);
	}

	private static Iterable<ScriptDependency> handle(ParameterizedTypeRef eo,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {

		// added check for container instance, as polyfill tests were crashing when
		// eo.declared type was TVariable and its container TClass
		if (eo.getDeclaredType() != null && eo.getDeclaredType().eContainer() instanceof TModule &&
				compare.apply(eo.getDeclaredType())) {
			String typeName = findTypeName(eo);
			if (typeName != null) { // null means not typed in script (e.g. TypesComputer)-> no import necessary
				TModule module = EcoreUtil2.getContainerOfType(eo.getDeclaredType(), TModule.class);
				return List.of(
						new ScriptDependency(typeName, eo.getDeclaredType().getName(), eo.getDeclaredType(), module));
			}
		}
		return new ArrayList<>();
	}

	/**
	 * Resolves dependency from identifier reference.
	 */
	private static Iterable<ScriptDependency> handle(IdentifierRef idRef,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {

		IdentifiableElement targetElem = idRef.getId();
		if (targetElem == null) {
			// broken identifier ref? smoke tests?
			return new ArrayList<>();
		}

		if (compare.apply(targetElem)) {
			TModule containingModule = EcoreUtil2.getContainerOfType(targetElem, TModule.class);
			return List.of(
					new ScriptDependency(RefNameUtil.findIdentifierName(idRef), targetElem.getName(), targetElem,
							containingModule));
		} else if (targetElem instanceof ModuleNamespaceVirtualType) {
			TModule targMod = ((ModuleNamespaceVirtualType) targetElem).getModule();

			if (isNamespaceDependencyHandlingNeeded(usedNamespaceSpecifiers, targMod)) {
				return List.of(createDependencyOnNamespace(usedNamespaceSpecifiers, targMod));
			}
		}
		return new ArrayList<>();
	}

	private static Iterable<ScriptDependency> handle(TClass tClass,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {

		List<Type> deps = new ArrayList<>();

		deps.add(tClass);

		EList<ParameterizedTypeRef> interfaces = tClass.getImplementedInterfaceRefs();
		if (!isNullOrEmpty(interfaces))
			deps.addAll(toList(filterNull(map(interfaces, i -> i.getDeclaredType()))));

		ParameterizedTypeRef superClass = tClass.getSuperClassRef();
		if (superClass != null)
			deps.add(superClass.getDeclaredType());

		return map(filter(deps, it -> compare.apply(it)),
				it -> createScriptDependency(it, nameToNamedImportSpecifiers, usedNamespaceSpecifiers));
	}

	private static Iterable<ScriptDependency> handle(TInterface tInterface,
			Map<String, NamedImportSpecifier> nameToNamedImportSpecifiers,
			Map<NamespaceImportSpecifier, Boolean> usedNamespaceSpecifiers, Function<EObject, Boolean> compare) {

		List<Type> deps = new ArrayList<>();

		deps.add(tInterface);

		EList<ParameterizedTypeRef> rs = tInterface.getSuperInterfaceRefs();
		if (!isNullOrEmpty(rs))
			deps.addAll(toList(filterNull(map(rs, it -> it.getDeclaredType()))));

		return map(filter(deps, d -> compare.apply(d)),
				it -> createScriptDependency(it, nameToNamedImportSpecifiers, usedNamespaceSpecifiers));
	}

}
