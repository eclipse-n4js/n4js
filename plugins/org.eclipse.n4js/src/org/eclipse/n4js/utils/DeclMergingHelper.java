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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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
	private ImportedNamesRecordingScopeAccess globalScopeAccess;

	@Inject
	private TypeSystemHelper tsh;

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
				QualifiedName qn = curr.getQualifiedName(); // FIXME maybe need to ask global scope provider here!
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
			List<Type> mergedElems = getMergedElements(type.eResource(), type);
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

	public List<AbstractNamespace> getMergedElements(Resource context, AbstractNamespace namespace) {
		List<EObject> result = getMergedElements(context, TypesPackage.Literals.ABSTRACT_NAMESPACE, namespace);
		return (List<AbstractNamespace>) ((List<?>) result);
	}

	public List<Type> getMergedElements(Resource context, Type type) {
		List<EObject> result = getMergedElements(context, TypesPackage.Literals.TYPE, type);
		return (List<Type>) ((List<?>) result);
	}

	private List<EObject> getMergedElements(Resource context, EClass elementType, EObject elem) {
		QualifiedName qn = qualifiedNameProvider.getFullyQualifiedName(elem);
		List<EObject> mergedElems = getMergedElementsFromScope(context, elementType, qn);
		mergedElems.removeIf(t -> t == elem);
		return mergedElems;
	}

	// taken from ContainerTypesHelper.MemberCollector
	private List<EObject> getMergedElementsFromScope(Resource context, EClass elementType, QualifiedName fqn) {

		IScope contextScope = globalScopeAccess.getRecordingPolyfillScope(context, elementType);
		List<EObject> types = new ArrayList<>();

		// contextScope.getElements(fqn) returns all polyfills, since shadowing is handled differently
		// for them!
		for (IEObjectDescription descr : contextScope.getElements(fqn)) {
			EObject polyfillType = descr.getEObjectOrProxy();
			if (polyfillType.eIsProxy()) {
				// TODO review: this seems odd... is this a test setup problem (since we do not use the
				// index
				// there and load the resource separately)?
				polyfillType = EcoreUtil.resolve(polyfillType, context);
				if (polyfillType.eIsProxy()) {
					throw new IllegalStateException("unexpected proxy");
				}
			}
			if (DeclMergingUtils.mayBeMerged(polyfillType)) {
				types.add(polyfillType);
			}
		}
		// }

		return types;
	}
}
