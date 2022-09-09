/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.ModuleSpecifierForm;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.naming.N4JSQualifiedNameProvider;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.utils.MainModuleAwareSelectableBasedScope;
import org.eclipse.n4js.scoping.utils.ProjectImportEnablingScope;
import org.eclipse.n4js.scoping.utils.QualifiedNameUtils;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.inject.Inject;

/**
 * Helper for handling declaration merging.
 */
public class DeclMergingHelper {

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Inject
	private ImportedNamesRecordingGlobalScopeAccess globalScopeAccess;

	@Inject
	private TypeSystemHelper tsh;

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private DeclMergingHelper declMergingHelper;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	/**
	 * For each set of merged elements in the given list, this method will retain the
	 * {@link DeclMergingUtils#compareForMerging(IEObjectDescription, IEObjectDescription) representative} of these
	 * merged elements and remove the others. The given list may contain more than one such set of merged elements.
	 * Elements without merged elements will be retained. Order will be preserved, except that representatives are moved
	 * to the position of the first of their merged elements.
	 * <p>
	 * ASSUMPTIONS:
	 * <ul>
	 * <li>for each set S of elements that are merged into each other, the given list is expected to contain
	 * <em>all</em> elements in S if it contains one of them. The index is not queried for additional merged elements.
	 * <li>the {@link IEObjectDescription}s in the given list are expected to return a global, fully qualified name
	 * (such as the ones provided by {@link N4JSQualifiedNameProvider}) from method
	 * {@link IEObjectDescription#getQualifiedName() #getQualifiedName()}, not some temporary, local name.
	 * </ul>
	 */
	@SuppressWarnings("null")
	public List<IEObjectDescription> chooseRepresentatives(List<IEObjectDescription> descs) {
		int len = descs.size();
		List<IEObjectDescription> result = null;
		ListMultimap<QualifiedName, IEObjectDescription> mergedElements = null;
		Map<QualifiedName, Integer> firstIndex = null;
		int idx = 0;
		for (int i = 0; i < len; i++) {
			IEObjectDescription curr = descs.get(i);
			if (DeclMergingUtils.mayBeMerged(curr)) {
				// we have an element that may be merged
				if (result == null) {
					result = new ArrayList<>(len);
					for (int j = 0; j < i; j++) {
						result.add(descs.get(j));
					}
					mergedElements = MultimapBuilder.linkedHashKeys().arrayListValues().build();
					firstIndex = new HashMap<>();
				}
				// do not use #getName() in next line! (see AliasedEObjectDescription for the difference)
				QualifiedName qn = curr.getQualifiedName();
				if (!mergedElements.containsKey(qn)) {
					firstIndex.put(qn, idx);
					idx++;
				}
				mergedElements.put(qn, curr);
			} else {
				// we have an ordinary element that cannot be merged
				if (result != null) {
					result.add(curr);
				}
				idx++;
			}
		}
		if (result == null) {
			return descs;
		}
		Comparator<IEObjectDescription> comparator = null;
		for (Entry<QualifiedName, Collection<IEObjectDescription>> entry : mergedElements.asMap().entrySet()) {
			Collection<IEObjectDescription> mergedModules = entry.getValue();
			IEObjectDescription representative;
			if (mergedModules.size() > 1) {
				if (comparator == null) {
					comparator = DeclMergingUtils::compareForMerging;
				}
				representative = IterableExtensions.sortWith(mergedModules, comparator).iterator().next();
			} else {
				representative = mergedModules.iterator().next();
			}
			result.add(firstIndex.get(entry.getKey()), representative);
		}
		return result;
	}

	/**
	 * Ordinary code should not have to use this. Only intended to be invoked from {@code TypeJudgment}.
	 */
	public TypeRef handleDeclarationMergingDuringTypeInference(RuleEnvironment G, TypeRef typeRef) {
		// only the case "function/class"; all other cases are simpler and are handled elsewhere
		Type type = typeRef instanceof TypeTypeRef ? tsh.getStaticType(G, (TypeTypeRef) typeRef, true) : null;
		if (type != null && !type.eIsProxy()
				&& type instanceof TClass
				&& DeclMergingUtils.mayBeMerged(type)) {
			List<Type> mergedElems = getMergedElements((N4JSResource) type.eResource(), type);
			List<TypeRef> mergedTypeRefs = new ArrayList<>();
			for (Type mergedElem : mergedElems) {
				if (mergedElem instanceof TFunction) {
					TypeRef mergedTypeRef = TypeUtils.wrapTypeInTypeRef(mergedElem);
					mergedTypeRefs.add(mergedTypeRef);
				}
			}
			if (!mergedTypeRefs.isEmpty()) {
				return TypeUtils.createNonSimplifiedIntersectionType(
						Iterables.concat(Collections.singleton(typeRef), mergedTypeRefs));
			}
		}
		return typeRef;
	}

	/**
	 * Returns those elements merged with the given namespace that are also {@link AbstractNamespace}s.
	 * <p>
	 * The given namespace does not have to be the
	 * {@link DeclMergingUtils#compareForMerging(IEObjectDescription, IEObjectDescription) representative}.
	 */
	public List<AbstractNamespace> getMergedElements(N4JSResource context, AbstractNamespace namespace) {
		return internalGetMergedElements(context, namespace, TypesPackage.Literals.ABSTRACT_NAMESPACE);
	}

	/**
	 * Returns those elements merged with the given type that are also {@link Type}s.
	 * <p>
	 * The given type does not have to be the
	 * {@link DeclMergingUtils#compareForMerging(IEObjectDescription, IEObjectDescription) representative}.
	 */
	public List<Type> getMergedElements(N4JSResource context, Type type) {
		return internalGetMergedElements(context, type, TypesPackage.Literals.TYPE);
	}

	@SuppressWarnings("unchecked")
	private <T extends EObject> List<T> internalGetMergedElements(N4JSResource context, T element, EClass eClass) {
		QualifiedName elemQN = qualifiedNameProvider.getFullyQualifiedName(element);
		if (elemQN == null) {
			return Collections.emptyList();
		}
		Set<EObject> resultSet = new LinkedHashSet<>();

		ProjectImportEnablingScope ctxPieScope = getProjectImportEnablingScope(context, eClass);
		if (QualifiedNameUtils.isGlobal(elemQN)) {
			if (ctxPieScope == null) {
				return Collections.emptyList();
			}

			resultSet.addAll(resolve(ctxPieScope.getElements(elemQN), context));
		} else {

			N4JSProjectConfigSnapshot elemPrj = workspaceAccess.findProjectContaining(element);
			N4JSProjectConfigSnapshot ctxPrj = workspaceAccess.findProjectContaining(context);

			resultSet.addAll(findAndResolve(ctxPieScope, context, elemQN, elemPrj));

			ModuleSpecifierForm importType = ctxPieScope.computeImportType(elemQN, elemPrj);
			if (importType != ModuleSpecifierForm.PLAIN) {
				// means that the qn of element is shadowed by a project import
				resultSet.addAll(findAndResolve(ctxPieScope, context, elemQN));
			}
			if (elemPrj != ctxPrj) {
				TModule tModule = EcoreUtil2.getContainerOfType(element, TModule.class);
				if (tModule != null && tModule.isMainModule()) {
					List<String> segments = new ArrayList<>(elemQN.getSegments());
					segments.remove(0);
					segments.add(0, tModule.getPackageName());
					QualifiedName projectFqn = QualifiedName.create(segments);
					resultSet.addAll(findAndResolve(ctxPieScope, context, projectFqn, null));
				}
			}
		}

		List<EObject> result = new ArrayList<>(resultSet);
		cleanListOfMergedElements(element, result);
		return (List<T>) result;
	}

	/**
	 * Returns those elements merged with the given element.
	 * <p>
	 * The given element does not have to be the
	 * {@link DeclMergingUtils#compareForMerging(IEObjectDescription, IEObjectDescription) representative}.
	 */
	public List<EObject> getMergedElements(N4JSResource context, IdentifiableElement elem) {
		QualifiedName qn = qualifiedNameProvider.getFullyQualifiedName(elem);
		List<EObject> result = globalScopeAccess.getElementsFromGlobalScope(context, EcorePackage.Literals.EOBJECT, qn);
		cleanListOfMergedElements(elem, result);
		return result;
	}

	private ProjectImportEnablingScope getProjectImportEnablingScope(N4JSResource resource, EClass elementType) {
		IScope initialScope = globalScopeAccess.getRecordingGlobalScope(resource, elementType);
		EReference reference = N4JSPackage.eINSTANCE.getModuleRef_Module();

		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(resource);
		IScope delegateMainModuleAwareScope = MainModuleAwareSelectableBasedScope.createMainModuleAwareScope(
				initialScope, resourceDescriptions, reference.getEReferenceType());

		N4JSWorkspaceConfigSnapshot ws = workspaceAccess.getWorkspaceConfig(resource);
		IScope scope = ProjectImportEnablingScope
				.create(ws, resource, Optional.absent(), initialScope, delegateMainModuleAwareScope, declMergingHelper);

		if (scope instanceof ProjectImportEnablingScope) {
			return (ProjectImportEnablingScope) scope;
		}

		return null;
	}

	private List<EObject> findAndResolve(ProjectImportEnablingScope pieScope, N4JSResource resource, QualifiedName qn) {
		Collection<IEObjectDescription> elems = pieScope.findElementsInProject(qn);
		return resolve(elems, resource);
	}

	private List<EObject> resolve(Iterable<IEObjectDescription> elems, N4JSResource resource) {
		List<EObject> result = new ArrayList<>();

		// contextScope.getElements(fqn) returns all polyfills, since shadowing is handled differently
		// for them!
		for (IEObjectDescription descr : elems) {
			EObject polyfillType = descr.getEObjectOrProxy();
			if (polyfillType.eIsProxy()) {
				// TODO review: this seems odd... is this a test setup problem (since we do not use the
				// index
				// there and load the resource separately)?
				polyfillType = EcoreUtil.resolve(polyfillType, resource);
				if (polyfillType.eIsProxy()) {
					continue;
					// throw new IllegalStateException("unexpected proxy");
				}
			}
			result.add(polyfillType);
		}
		return result;
	}

	private List<EObject> findAndResolve(ProjectImportEnablingScope pieScope, N4JSResource resource, QualifiedName qn,
			N4JSProjectConfigSnapshot targetProject) {
		Collection<IEObjectDescription> elems = pieScope.getElementsWithDesiredProjectName(qn, targetProject);
		return resolve(elems, resource);
	}

	/**
	 * Since package names are not included in qualified names, equality of qualified names is not a sufficient
	 * requirement for finding merged elements. Therefore, the list of elements we obtain from the scoping contains
	 * false positives. This method will remove them.
	 * <p>
	 * In addition, this method removes the start element, because we do not want to include it in the result list.
	 */
	private void cleanListOfMergedElements(EObject targetElem, List<? extends EObject> mergedElemsFromScoping) {
		if (DeclMergingUtils.isGlobal(targetElem)) {
			// TODO: check all are global
			// for global elements and elements in declared modules, name equality is sufficient
			mergedElemsFromScoping.removeIf(e -> e == targetElem || !DeclMergingUtils.mayBeMerged(e));
		} else {
			boolean givenIsAugmentationOrModule = DeclMergingUtils.isAugmentationModuleOrModule(targetElem);
			// in all other cases, we have to require both elements to be either a declared module or to be an
			// augmentation / real module. In case there is a main module, declared and augmentation / real modules are
			// allowed.

			List<EObject> potRemove = new ArrayList<>();
			boolean containsMainModule = DeclMergingUtils.isOrInMainModule(targetElem);
			for (Iterator<? extends EObject> iter = mergedElemsFromScoping.iterator(); iter.hasNext();) {
				EObject elem = iter.next();
				containsMainModule |= DeclMergingUtils.isOrInMainModule(elem);
				if (elem == targetElem || !DeclMergingUtils.mayBeMerged(elem)) {
					iter.remove();
				}
				if (!containsMainModule
						&& givenIsAugmentationOrModule != DeclMergingUtils.isAugmentationModuleOrModule(elem)) {
					potRemove.add(elem);
				}
			}
			if (!containsMainModule) {
				mergedElemsFromScoping.removeAll(potRemove);
			}
		}
	}
}
