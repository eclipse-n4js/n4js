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
package org.eclipse.n4js.types.utils

import com.google.inject.Inject
import java.util.Iterator
import java.util.List
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.Type

import static org.eclipse.n4js.types.utils.SuperTypesList.*

import static extension org.eclipse.n4js.types.utils.TypeUtils.*

/**
 * Helper providing utility methods for type and type ref handling, needs to be injected. Static utility methods
 * can be found in {@link TypeUtils}.
 */
public class TypeHelper {

	@Inject
	extension TypeCompareHelper

	/*
	 * Collects all declared super types of a type referenced by a type references, recognizing cyclic dependencies in
	 * case of invalid type hierarchies. Type arguments are not considered during comparison, thus G<A> and G<B>
	 * will result in a single reference in the result.
	 *
	 * @param reflexive if true, type itself is also added to the list of super types
	 * @return ordered list of super types, using a depth first search, starting with classes, then roles, and eventually interfaces.
	 */
	// TODO functions and other type expressions are not supported yet!
	public def SuperTypesList<TypeRef> collectAllDeclaredSuperTypesTypeargsIgnored(TypeRef ref, boolean reflexive) {
		val allDeclaredSuperTypes = newSuperTypesList(typeRefComparator);

		allDeclaredSuperTypes.add(ref)
		ref.collectAllDeclaredSuperTypesTypeargsIgnored(allDeclaredSuperTypes)

		// in case of cycles, we do not want the current type to be contained in the super types list
		// if reflexive is false
		if (!reflexive) {
			allDeclaredSuperTypes.remove(ref);
		}
		return allDeclaredSuperTypes;
	}

	/*
	 * Collects all declared super types of a type referenced by a type references, recognizing cyclic dependencies in
	 * case of invalid type hierarchies. Type arguments are considered during comparison, thus G<A> and G<B>
	 * will both be part of the result.
	 *
	 * @param reflexive if true, type itself is also added to the list of super types
	 * @return ordered list of super types, using a depth first search, starting with classes, then roles, and eventually interfaces.
	 */
	// TODO functions and other type expressions are not supported yet!
	public def SuperTypesList<TypeRef> collectAllDeclaredSuperTypesWithTypeargs(TypeRef ref, boolean reflexive) {
		val allDeclaredSuperTypes = newSuperTypesList(typeRefComparator);

		allDeclaredSuperTypes.add(ref)
		ref.collectAllDeclaredSuperTypesTypeargsIgnored(allDeclaredSuperTypes)

		// in case of cycles, we do not want the current type to be contained in the super types list
		// if reflexive is false
		if (!reflexive) {
			allDeclaredSuperTypes.remove(ref);
		}
		return allDeclaredSuperTypes;
	}

	public def SuperTypesList<Type> collectAllDeclaredSuperTypes(Type type, boolean reflexive) {
		val allDeclaredSuperTypes = newSuperTypesList(typeComparator);

		allDeclaredSuperTypes.add(type)
		type.collectAllDeclaredSuperTypes(allDeclaredSuperTypes)

		// in case of cycles, we do not want the current type to be contained in the super types list
		// if reflexive is false
		if (!reflexive) {
			allDeclaredSuperTypes.remove(type);
		}
		return allDeclaredSuperTypes;
	}

	/**
	 * Collects all type references of given type reference and add these types to both given collections. This method requires the tree set to use the
	 * {@link TypeCompareHelper#getTypeRefComparator()}. In most cases, you probably will call {@link #collectAllDeclaredSuperTypes(TypeRef, boolean)} instead of calling this method
	 * directly. However, for some optimized methods, it may be usefull to call this method directly.
	 *
	 * @param allDeclaredSuperTypes
	 *            needs to be instantiated with correct comparator, see {@link collectAllDeclaredSuperTypes(TypeRef)}, that is the types are ordered by their
	 * 				qualified name
	 * @param allDeclaredSuperTypesOrdered ordered list of super types, using a depth first search, starting with classes, then roles, and eventually interfaces.
	 */
	public def void collectAllDeclaredSuperTypesTypeargsIgnored(TypeRef ref, SuperTypesList<TypeRef> allDeclaredSuperTypes) {
		if (ref !== null && ref.declaredType !== null) {
			for (superTypeRef : ref.declaredType.declaredSuperTypes) {
				if (allDeclaredSuperTypes.add(superTypeRef)) {
					collectAllDeclaredSuperTypesTypeargsIgnored(superTypeRef, allDeclaredSuperTypes)
				}
			}
		}
	}

	public def void collectAllDeclaredSuperTypes(Type type, SuperTypesList<Type> allDeclaredSuperTypes) {
		if (type !== null) {
			for (superType : type.declaredSuperTypes.map[declaredType]) {
				if (allDeclaredSuperTypes.add(superType)) {
					collectAllDeclaredSuperTypes(superType, allDeclaredSuperTypes)
				}
			}
		}
	}

	/**
	 * Removes the first occurrence of the given type from the iterable, whereby the type is found by name using the
	 * {@link TypeCompareHelper#getTypeRefComparator()}. The iterator of the iterable needs to support the {@link Iterator#remove()} operation.
	 * @return true if type has been found, false otherwise
	 */
	public def boolean removeTypeRef(Iterable<TypeRef> typeRefs, TypeRef toBeRemoved) {
		val Iterator<TypeRef> iter = typeRefs.iterator;
		while (iter.hasNext()) {
			if (compare(toBeRemoved, iter.next()) === 0) {
				iter.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * Retains all types from list of refs.
	 * <p>
	 * This method is optimized for leastCommonSuperType and
	 * assumes that all types in orderedRefs are ordered as returned by collecAllDeclaredSuperTypes().
	 * The iterator returned by typeRefs must support the {@link Iterator#remove()} operation.
	 */
	public def void retainAllTypeRefs(Iterable<TypeRef> typeRefs, SuperTypesList<TypeRef> typesToBeRetained) {
		val iter = typeRefs.iterator
		while (iter.hasNext) {
			if (! typesToBeRetained.contains(iter.next())) {
				iter.remove()
			}
		}
	}

	/**
	 * Returns true if given iterable contains the type ref, using the {@link TypeCompareHelper#getTypeRefComparator()} for finding the type ref.
	 */
	public def boolean containsByType(Iterable<TypeRef> typeRefs, TypeRef typeRef) {
		typeRefs.exists[compare(typeRef, it) == 0]
	}

	/**
	 * Returns the index of the type ref contained in the typeRefs collections, which either equals the given type ref
	 * by means of the {@link TypeCompareHelper#getTypeRefComparator()}, or which is assignment compatible in case of primitive types.
	 *
	 * @return index or -1, if type ref has not been found
	 */
	public def int findTypeRefOrAssignmentCompatible(List<TypeRef> typeRefs, TypeRef typeRef) {
		val assignmentCompatible = if (typeRef.declaredType instanceof PrimitiveType) {
			(typeRef.declaredType as PrimitiveType).assignmentCompatible;
		} else {
			null;
		}
		var i = 0;
		while (i<typeRefs.length) {
			val t = typeRefs.get(i);

			if (compare(typeRef, t) == 0) {
				return i;
			}
			if (assignmentCompatible !== null) {
				val type = t.declaredType;
				if (type instanceof PrimitiveType) {
					if (typeComparator.compare(assignmentCompatible, type) == 0 ||
						typeComparator.compare(typeRef.declaredType, type.assignmentCompatible) == 0) {
						return i;
					}
				}
			}
			i = i+1;
		}
		return -1;
	}
}
