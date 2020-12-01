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
package org.eclipse.n4js.typesystem.utils

import com.google.inject.Inject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.n4js.ts.conversions.ComputedPropertyNameValueConverter
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.util.AllSuperTypeRefsCollector
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.ContainerTypesHelper

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Helper class for dealing with built-in types {@link BuiltInTypeScope#getIterableType() Iterable} and
 * {@link BuiltInTypeScope#getIterableNTypes() IterableN}.
 */
class IterableComputer extends TypeSystemHelperStrategy {

	@Inject private N4JSTypeSystem ts;
	@Inject private ContainerTypesHelper containerTypesHelper;

	/**
	 * Same as {@link #extractIterableElementTypes(RuleEnvironment,TypeRef)}, but returns the upper bounds.
	 */
	public def Iterable<TypeRef> extractIterableElementTypesUBs(RuleEnvironment G, TypeRef typeRef) {
		return extractIterableElementTypes(G,typeRef).map[ts.upperBound(G,it)];
	}

	/**
	 * Given a type that is or (directly or indirectly) implements one of the Iterable or IterableN built-in types,
	 * this method will return the type of the first N elements returned by the Iterable's iterator. The last returned
	 * type will be the type of all remaining elements (if any).
	 *
	 * Never returns <code>null</code> but may return an empty result if 'typeRef' does not implement any of the
	 * Iterable or IterableN interfaces. Usually never returns a result longer than {@link BuiltInTypeScope#ITERABLE_N__MAX_LEN},
	 * but if there are invalid type references with too many arguments, this might happen.
	 */
	public def Iterable<? extends TypeRef> extractIterableElementTypes(RuleEnvironment G, TypeRef typeRef) {
		return extractIterableElementTypes(G, typeRef, G.iterableType, true);
	}

	/**
	 * Same as {@link #extractIterableElementType(RuleEnvironment,TypeRef)}, but returns the upper bound.
	 */
	public def TypeRef extractIterableElementTypeUB(RuleEnvironment G, TypeRef typeRef) {
		return extractIterableElementTypes(G, typeRef, G.iterableType, false).map[ts.upperBound(G,it)].head;
	}

	/**
	 * Given a type that is or (directly or indirectly) implements the Iterable built-in types, this method will
	 * return the type of the elements returned by the Iterable's iterator.
	 *
	 * Returns <code>null</code> if 'typeRef' does not implement Iterable.
	 */
	public def TypeArgument extractIterableElementType(RuleEnvironment G, TypeRef typeRef, boolean includeAsyncIterable) {
		var result = null as TypeArgument;
		if (includeAsyncIterable) {
			result = extractIterableElementTypes(G, typeRef, G.asyncIterableType, false).head;
		}
		if (result === null) {
			result = extractIterableElementTypes(G, typeRef, G.iterableType, false).head;
		}
		return result;
	}

	private def Iterable<? extends TypeRef> extractIterableElementTypes(RuleEnvironment G, TypeRef typeRef, Type iterableType, boolean includeIterableN) {
		var Iterable<? extends TypeRef> result = null;
		val declType = typeRef?.declaredType;
		if(declType===iterableType || (includeIterableN && G.isIterableN(declType))) {
			// simple: typeRef directly points to Iterable<> or an IterableN<>
			result = typeRef.typeArgs.toUpperBounds(G);
		} else if(declType instanceof PrimitiveType) {
			// note: the 'elementType' property we read in the next line is also used with instances of TObjectPrototype
			// (e.g. upper-case 'String'), but we need not and should not handle those within this block, because those
			// types are expected to be structural subtypes of Iterable<>, which is handled below in the if-block for
			// ContainerType<?>
			val elementType = declType.elementType;
			if(elementType!==null) {
				// Type#getElementType() returns non-null for certain primitive types that are "array-like" (see entity
				// ArrayLike in Types.xcore); currently, this applies only to 'string'
				result = #[ TypeUtils.copy(elementType) ];
			}
		} else if(typeRef instanceof ComposedTypeRef) {
			val results = newArrayList;
			for(containedTypeRef : typeRef.typeRefs) {
				val currResult = extractIterableElementTypes(G, containedTypeRef, iterableType, includeIterableN);
				if(currResult.empty) {
					// one of the types in the ComposedTypeRef does not implement Iterable/IterableN at all
					// -> the entire composed type ref must be treated as if it did not implement them at all
					return #[];
				}
				results.add(currResult);
			}
			if(!results.empty) {
				result = mergeListsOfTypeRefs(G, typeRef.getClass, results);
			}
		} else if(declType instanceof ContainerType<?>) {
			// step 1: look for Iterable<> and IterableN<...> among super types
			// (the IterableN<> are nominal, so they must be implemented explicitly anyway; Iterable<> is
			// structural but may still be implemented explicitly which would be simpler for us)
			val results = newArrayList;
			for(superTypeRef : AllSuperTypeRefsCollector.collect(declType)) {
				val superDeclType = superTypeRef?.declaredType;
				if(superDeclType===iterableType || (includeIterableN && G.isIterableN(superTypeRef))) {
					// next if() is important: sorts out the super-type references to IterableN-1 in IterableN
					// (but only required if including the IterableN)
					val isContainedInIterable = G.isIterableN(superTypeRef.eContainer);
					if(!(includeIterableN && isContainedInIterable)) {
						results.add(superTypeRef.typeArgs.toUpperBounds(G));
					}
				}
			}
			if(!results.empty) {
				// for results.size>=2 this covers a corner case:
				// a type implements several of the IterableN interfaces, e.g. Iterable2<A,B> and Iterable3<X,Y,Z>
				// -> here we assume intersection{A,X} for 1st element, intersection{B,Y} for 2nd, and intersection{B,Z}
				// for 3rd and all later elements.
				result = mergeListsOfTypeRefs(G, IntersectionTypeExpression, results);
			}
			// if Iterable<> / IterableN<...> was not found (i.e. not implemented nominally):
			// step 2: look for Iterable<T> structurally (Iterable<> is structural, so need not be implemented explicitly)
			// -> we actually look for a member "public Iterator<T> [Symbol.iterator]() {...}"
			// and are interested in the type argument T
			if(result===null) {
				val res = G.get(Resource);
				if(res instanceof Resource) {
					val memberName = if(iterableType===G.asyncIterableType) {
						ComputedPropertyNameValueConverter.SYMBOL_ASYNC_ITERATOR_MANGLED;
					} else {
						ComputedPropertyNameValueConverter.SYMBOL_ITERATOR_MANGLED;
					};
					val m = containerTypesHelper.fromContext(res).findMember(declType,memberName,false,false);
					if(m instanceof TMethod) {
						result = m.returnTypeRef?.typeArgs.toUpperBounds(G); // no problem if we set 'result' to null (it's the default anyway)
					}
					else if(m instanceof TGetter) {
						result = m.declaredTypeRef?.typeArgs.toUpperBounds(G); // no problem if we set 'result' to null (it's the default anyway)
					}
				}
				else {
					throw new IllegalArgumentException("no or invalid Resource defined in rule environment G")
				}
			}
		}
		if(result===null || result.empty) {
			return #[];
		}
		// substitute type variables in result
		val G2 = G.wrap;
		tsh.addSubstitutions(G2,typeRef);
		val resultSubst = result.map[ts.substTypeVariables(G2,it)]
				.filter(TypeRef); // note the invariant of judgment 'substTypeVariables': if you put TypeRefs in, you'll get TypeRefs back
		return resultSubst;
	}

	private def Iterable<TypeRef> toUpperBounds(Iterable<TypeArgument> typeArgs, RuleEnvironment G) {
		typeArgs.map[ts.upperBound(G,it)]
	}

	private def Iterable<? extends TypeRef> mergeListsOfTypeRefs(RuleEnvironment G, Class<? extends ComposedTypeRef> type, Iterable<? extends TypeRef>... iterablesToMerge) {
		val rs = iterablesToMerge.size;
		if(rs===0) {
			return emptyList;
		}
		else if(rs===1) {
			return iterablesToMerge.head;
		}
		else {
			val listsToMerge = iterablesToMerge.map[toList].toList;
			val maxNumOfElems = listsToMerge.map[size].reduce[a,b|Math.max(a,b)].intValue;
			val result = newArrayList;
			val types_of_element_i_across_results = newArrayOfSize(rs);
			for(var i=0;i<maxNumOfElems;i++) {
				// collect all types of element at index 'i'
				for(var j=0;j<rs;j++) {
					val result_j = listsToMerge.get(j);
					if(result_j===null || result_j.empty) {
						throw new IllegalArgumentException("iterablesToMerge may not contain null values or empty iterables");
					}
					val idxSafe = Math.min(i,result_j.size-1);
					val type_of_element_i_in_result_j = result_j.get(idxSafe);
					types_of_element_i_across_results.set(j, type_of_element_i_in_result_j);
				}
				// combine them into a union or intersection type
				val type_of_element_i = if(UnionTypeExpression.isAssignableFrom(type)) {
					tsh.createUnionType(G,types_of_element_i_across_results)
				} else if(IntersectionTypeExpression.isAssignableFrom(type)) {
					tsh.createIntersectionType(G,types_of_element_i_across_results)
				} else {
					throw new IllegalArgumentException("unknown subtype of ComposedTypeRef: "+type?.name)
				};
				// add this type to main result
				result += type_of_element_i;
			}
			return result;
		}
	}
}
