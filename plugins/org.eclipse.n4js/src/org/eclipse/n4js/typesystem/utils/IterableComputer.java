/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem.utils;

import static org.eclipse.n4js.types.utils.TypeUtils.convertTypeArgsToRefs;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRefDynamic;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.asyncIterableType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isArrayN;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isIterableN;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.reduce;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Helper class for dealing with built-in types {@link BuiltInTypeScope#getIterableType() Iterable} and
 * {@link BuiltInTypeScope#getIterableNTypes() IterableN}.
 */
public class IterableComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private ContainerTypesHelper containerTypesHelper;
	@Inject
	private DeclMergingHelper declMergingHelper;

	/**
	 * Given a type that is or (directly or indirectly) implements one of the Iterable or IterableN built-in types, this
	 * method will return the type of the first N elements returned by the Iterable's iterator. The last returned type
	 * will be the type of all remaining elements (if any).
	 * <p>
	 * Never returns <code>null</code> but may return an empty result if 'typeRef' does not implement any of the
	 * Iterable or IterableN interfaces. Usually never returns a result longer than
	 * {@link BuiltInTypeScope#ITERABLE_N__MAX_LEN}, but if there are invalid type references with too many arguments,
	 * this might happen.
	 */
	public List<TypeRef> extractIterableElementTypes(RuleEnvironment G, TypeRef typeRef) {
		return Lists.newArrayList(extractIterableElementTypes(G, typeRef, iterableType(G), true));
	}

	/**
	 * Given a type that is or (directly or indirectly) implements the Iterable built-in types, this method will return
	 * the type of the elements returned by the Iterable's iterator.
	 * <p>
	 * Returns <code>null</code> if 'typeRef' does not implement Iterable.
	 */
	public TypeRef extractIterableElementType(RuleEnvironment G, TypeRef typeRef, boolean includeAsyncIterable) {
		TypeRef result = null;
		if (includeAsyncIterable) {
			result = extractIterableElementTypes(G, typeRef, asyncIterableType(G), false).get(0);
		}
		if (result == null) {
			result = extractIterableElementTypes(G, typeRef, iterableType(G), false).get(0);
		}
		return result;
	}

	@SuppressWarnings("null")
	private List<? extends TypeRef> extractIterableElementTypes(RuleEnvironment G, TypeRef typeRefRaw,
			Type iterableType, boolean includeIterableN) {

		Iterable<? extends TypeRef> result = null;
		TypeRef typeRef = ts.upperBoundWithReopenAndResolveBoth(G, typeRefRaw);
		Type declType = typeRef == null ? null : typeRef.getDeclaredType();
		if (declType == iterableType || (includeIterableN && isIterableN(G, declType))) {
			// simple: typeRef directly points to Iterable<> or an IterableN<>
			result = convertTypeArgsToRefs(typeRef.getDeclaredTypeArgs());
		} else if (declType == arrayType(G) || (includeIterableN && isArrayN(G, declType))) {
			// simple: typeRef directly points to Array<> or an ArrayN<>
			result = convertTypeArgsToRefs(typeRef.getTypeArgsWithDefaults());
		} else if (declType == anyType(G) && typeRef.isDynamic()) {
			result = List.of(anyTypeRefDynamic(G));
		} else if (declType instanceof PrimitiveType) {
			// note: the 'elementType' property we read in the next line is also used with certain instances of TClass
			// (e.g. upper-case 'String'), but we need not and should not handle those within this block, because those
			// types are expected to be structural subtypes of Iterable<>, which is handled below in the if-block for
			// ContainerType<?>
			TypeRef elementType = declType.getElementType();
			if (elementType != null) {
				// Type#getElementType() returns non-null for certain primitive types that are "array-like" (see entity
				// ArrayLike in Types.xcore); currently, this applies only to 'string'
				result = List.of(TypeUtils.copy(elementType));
			}
		} else if (typeRef instanceof ComposedTypeRef) {
			List<Iterable<? extends TypeRef>> results = new ArrayList<>();
			for (TypeRef containedTypeRef : ((ComposedTypeRef) typeRef).getTypeRefs()) {
				List<? extends TypeRef> currResult = extractIterableElementTypes(G, containedTypeRef, iterableType,
						includeIterableN);
				if (currResult.isEmpty()) {
					// one of the types in the ComposedTypeRef does not implement Iterable/IterableN at all
					// -> the entire composed type ref must be treated as if it did not implement them at all
					return Collections.emptyList();
				}
				results.add(currResult);
			}
			if (!results.isEmpty()) {
				result = mergeListsOfTypeRefs(G, ((ComposedTypeRef) typeRef).getClass(), results);
			}
		} else if (declType instanceof ContainerType<?>) {
			// step 1: look for Iterable<> and IterableN<...> among super types
			// (the IterableN<> are nominal, so they must be implemented explicitly anyway; Iterable<> is
			// structural but may still be implemented explicitly which would be simpler for us)
			List<Iterable<? extends TypeRef>> results = new ArrayList<>();
			for (TypeRef superTypeRef : AllSuperTypeRefsCollector.collect((ParameterizedTypeRef) typeRef,
					declMergingHelper)) {
				if (superTypeRef == null) {
					continue;
				}
				if (superTypeRef.getDeclaredType() == iterableType
						|| (includeIterableN && isIterableN(G, superTypeRef))) {
					// next if() is important: sorts out the super-type references to IterableN-1 in IterableN
					// (but only required if including the IterableN)
					boolean isContainedInIterableN = isIterableN(G, superTypeRef.eContainer());
					boolean isContainedInArrayN = isArrayN(G, superTypeRef.eContainer());
					if (!(includeIterableN && (isContainedInIterableN || isContainedInArrayN))) {
						results.add(convertTypeArgsToRefs(superTypeRef.getDeclaredTypeArgs()));
					}
				}
			}
			if (!results.isEmpty()) {
				// for results.size>=2 this covers a corner case:
				// a type implements several of the IterableN interfaces, e.g. Iterable2<A,B> and Iterable3<X,Y,Z>
				// -> here we assume intersection{A,X} for 1st element, intersection{B,Y} for 2nd, and intersection{B,Z}
				// for 3rd and all later elements.
				result = mergeListsOfTypeRefs(G, IntersectionTypeExpression.class, results);
			}
			// if Iterable<> / IterableN<...> was not found (i.e. not implemented nominally):
			// step 2: look for Iterable<T> structurally (Iterable<> is structural, so need not be implemented
			// explicitly)
			// -> we actually look for a member "public Iterator<T> [Symbol.iterator]() {...}"
			// and are interested in the type argument T
			if (result == null) {
				Object res = G.get(Resource.class);
				if (res instanceof Resource) {
					String memberName = (iterableType == asyncIterableType(G))
							? N4JSLanguageUtils.SYMBOL_ASYNC_ITERATOR_MANGLED
							: N4JSLanguageUtils.SYMBOL_ITERATOR_MANGLED;

					TMember m = containerTypesHelper.fromContext((Resource) res).findMember((ContainerType<?>) declType,
							memberName, false, false);
					if (m instanceof TMethod) {
						TypeRef returnTypeRef = ((TMethod) m).getReturnTypeRef();
						if (returnTypeRef != null) {
							// no problem if we set 'result' to null (it's the default anyway)
							result = convertTypeArgsToRefs(returnTypeRef.getDeclaredTypeArgs());
						}
					} else if (m instanceof TGetter) {
						TypeRef typeRef2 = ((TGetter) m).getTypeRef();
						if (typeRef2 != null) {
							// no problem if we set 'result' to null (it's the default anyway)
							result = convertTypeArgsToRefs(typeRef2.getDeclaredTypeArgs());
						}
					}
				} else {
					throw new IllegalArgumentException("no or invalid Resource defined in rule environment G");
				}
			}
		}
		if (result == null || !result.iterator().hasNext()) {
			return Collections.emptyList();
		}
		// substitute type variables in result
		RuleEnvironment G2 = wrap(G);
		tsh.addSubstitutions(G2, typeRef);
		Iterable<TypeRef> resultSubst = map(filter(map(result, it -> ts.substTypeVariables(G2, it)),
				TypeRef.class), // note the invariant of judgment 'substTypeVariables': if you put TypeRefs in, you'll
								// get TypeRefs back
				tref -> (tref instanceof ComposedTypeRef) ? tsh.simplify(G, (ComposedTypeRef) tref) : tref);

		return toList(resultSubst);
	}

	private Iterable<? extends TypeRef> mergeListsOfTypeRefs(RuleEnvironment G, Class<? extends ComposedTypeRef> type,
			List<Iterable<? extends TypeRef>> iterablesToMerge) {
		int rs = iterablesToMerge.size();
		if (rs == 0) {
			return Collections.emptyList();
		} else if (rs == 1) {
			return iterablesToMerge.iterator().next();
		} else {

			List<List<? extends TypeRef>> listsToMerge = toList(map(iterablesToMerge, it -> toList(it)));
			int maxNumOfElems = reduce(map(listsToMerge, l -> l.size()), (a, b) -> Math.max(a, b)).intValue();
			List<TypeRef> result = new ArrayList<>();
			TypeRef[] types_of_element_i_across_results = new TypeRef[rs];
			for (var i = 0; i < maxNumOfElems; i++) {
				// collect all types of element at index 'i'
				for (var j = 0; j < rs; j++) {
					List<? extends TypeRef> result_j = listsToMerge.get(j);
					if (result_j == null || result_j.isEmpty()) {
						throw new IllegalArgumentException(
								"iterablesToMerge may not contain null values or empty iterables");
					}
					int idxSafe = Math.min(i, result_j.size() - 1);
					TypeRef type_of_element_i_in_result_j = result_j.get(idxSafe);
					types_of_element_i_across_results[j] = type_of_element_i_in_result_j;
				}
				// combine them into a union or intersection type
				TypeRef type_of_element_i;
				if (UnionTypeExpression.class.isAssignableFrom(type)) {
					type_of_element_i = tsh.createUnionType(G, types_of_element_i_across_results);
				} else if (IntersectionTypeExpression.class.isAssignableFrom(type)) {
					type_of_element_i = tsh.createIntersectionType(G, types_of_element_i_across_results);
				} else {
					throw new IllegalArgumentException(
							"unknown subtype of ComposedTypeRef: " + (type == null ? "" : type.getName()));
				}
				// add this type to main result
				result.add(type_of_element_i);
			}
			return result;
		}
	}
}
