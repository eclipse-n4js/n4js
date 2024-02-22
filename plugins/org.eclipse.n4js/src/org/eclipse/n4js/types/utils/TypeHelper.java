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
package org.eclipse.n4js.types.utils;

import static org.eclipse.n4js.types.utils.SuperTypesList.newSuperTypesList;
import static org.eclipse.n4js.types.utils.TypeUtils.declaredSuperTypes;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.Iterator;
import java.util.List;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.inject.Inject;

/**
 * Helper providing utility methods for type and type ref handling, needs to be injected. Static utility methods can be
 * found in {@link TypeUtils}.
 */
public class TypeHelper {

	@Inject
	TypeCompareHelper tch;

	/**
	 * Collects all declared super types of a type referenced by a type references, recognizing cyclic dependencies in
	 * case of invalid type hierarchies. Type arguments are not considered during comparison, thus G<A> and G<B> will
	 * result in a single reference in the result.
	 *
	 * @param reflexive
	 *            if true, type itself is also added to the list of super types
	 *
	 * @return ordered list of super types, using a depth first search, starting with classes, then roles, and
	 *         eventually interfaces.
	 */
	// TODO functions and other type expressions are not supported yet!
	public SuperTypesList<TypeRef> collectAllDeclaredSuperTypesTypeargsIgnored(TypeRef ref, boolean reflexive) {
		SuperTypesList<TypeRef> allDeclaredSuperTypes = newSuperTypesList(tch.getTypeRefComparator());

		allDeclaredSuperTypes.add(ref);
		collectAllDeclaredSuperTypesTypeargsIgnored(ref, allDeclaredSuperTypes);

		// in case of cycles, we do not want the current type to be contained in the super types list
		// if reflexive is false
		if (!reflexive) {
			allDeclaredSuperTypes.remove(ref);
		}
		return allDeclaredSuperTypes;
	}

	/**
	 * Collects all declared super types of a type referenced by a type references, recognizing cyclic dependencies in
	 * case of invalid type hierarchies. Type arguments are considered during comparison, thus G<A> and G<B> will both
	 * be part of the result.
	 *
	 * @param reflexive
	 *            if true, type itself is also added to the list of super types
	 *
	 * @return ordered list of super types, using a depth first search, starting with classes, then roles, and
	 *         eventually interfaces.
	 */
	// TODO functions and other type expressions are not supported yet!
	public SuperTypesList<TypeRef> collectAllDeclaredSuperTypesWithTypeargs(TypeRef ref, boolean reflexive) {
		SuperTypesList<TypeRef> allDeclaredSuperTypes = newSuperTypesList(tch.getTypeRefComparator());

		allDeclaredSuperTypes.add(ref);
		collectAllDeclaredSuperTypesTypeargsIgnored(ref, allDeclaredSuperTypes);

		// in case of cycles, we do not want the current type to be contained in the super types list
		// if reflexive is false
		if (!reflexive) {
			allDeclaredSuperTypes.remove(ref);
		}
		return allDeclaredSuperTypes;
	}

	/***/
	public SuperTypesList<Type> collectAllDeclaredSuperTypes(Type type, boolean reflexive) {
		SuperTypesList<Type> allDeclaredSuperTypes = newSuperTypesList(tch.getTypeComparator());

		allDeclaredSuperTypes.add(type);
		collectAllDeclaredSuperTypes(type, allDeclaredSuperTypes);

		// in case of cycles, we do not want the current type to be contained in the super types list
		// if reflexive is false
		if (!reflexive) {
			allDeclaredSuperTypes.remove(type);
		}
		return allDeclaredSuperTypes;
	}

	/**
	 * Collects all type references of given type reference and add these types to both given collections. This method
	 * requires the tree set to use the {@link TypeCompareHelper#getTypeRefComparator()}. In most cases, you probably
	 * will call {@link #collectAllDeclaredSuperTypes(Type, boolean)} instead of calling this method directly. However,
	 * for some optimized methods, it may be useful to call this method directly.
	 *
	 * @param allDeclaredSuperTypes
	 *            needs to be instantiated with correct comparator, that is the types are ordered by their qualified
	 *            name
	 */
	public void collectAllDeclaredSuperTypesTypeargsIgnored(TypeRef ref,
			SuperTypesList<TypeRef> allDeclaredSuperTypes) {

		if (ref != null && ref.getDeclaredType() != null) {
			for (ParameterizedTypeRef superTypeRef : declaredSuperTypes(ref.getDeclaredType())) {
				if (allDeclaredSuperTypes.add(superTypeRef)) {
					collectAllDeclaredSuperTypesTypeargsIgnored(superTypeRef, allDeclaredSuperTypes);
				}
			}
		}
	}

	/***/
	public void collectAllDeclaredSuperTypes(Type type, SuperTypesList<Type> allDeclaredSuperTypes) {
		if (type != null) {
			for (Type superType : map(declaredSuperTypes(type), t -> t.getDeclaredType())) {
				if (allDeclaredSuperTypes.add(superType)) {
					collectAllDeclaredSuperTypes(superType, allDeclaredSuperTypes);
				}
			}
		}
	}

	/**
	 * Removes the first occurrence of the given type from the iterable, whereby the type is found by name using the
	 * {@link TypeCompareHelper#getTypeRefComparator()}. The iterator of the iterable needs to support the
	 * {@link Iterator#remove()} operation.
	 *
	 * @return true if type has been found, false otherwise
	 */
	public boolean removeTypeRef(Iterable<TypeRef> typeRefs, TypeRef toBeRemoved) {
		Iterator<TypeRef> iter = typeRefs.iterator();
		while (iter.hasNext()) {
			if (tch.compare(toBeRemoved, iter.next()) == 0) {
				iter.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * Retains all types from list of refs.
	 * <p>
	 * This method is optimized for leastCommonSuperType and assumes that all types in orderedRefs are ordered as
	 * returned by collecAllDeclaredSuperTypes(). The iterator returned by typeRefs must support the
	 * {@link Iterator#remove()} operation.
	 */
	public void retainAllTypeRefs(Iterable<TypeRef> typeRefs, SuperTypesList<TypeRef> typesToBeRetained) {
		Iterator<TypeRef> iter = typeRefs.iterator();
		while (iter.hasNext()) {
			if (!typesToBeRetained.contains(iter.next())) {
				iter.remove();
			}
		}
	}

	/**
	 * Returns true if given iterable contains the type ref, using the {@link TypeCompareHelper#getTypeRefComparator()}
	 * for finding the type ref.
	 */
	public boolean containsByType(Iterable<TypeRef> typeRefs, TypeRef typeRef) {
		return IterableExtensions.exists(typeRefs, tr -> tch.compare(typeRef, tr) == 0);
	}

	/**
	 * Returns the index of the type ref contained in the typeRefs collections, which either equals the given type ref
	 * by means of the {@link TypeCompareHelper#getTypeRefComparator()}, or which is assignment compatible in case of
	 * primitive types.
	 *
	 * @return index or -1, if type ref has not been found
	 */
	public int findTypeRefOrAssignmentCompatible(List<TypeRef> typeRefs, TypeRef typeRef) {
		PrimitiveType assignmentCompatible = (typeRef.getDeclaredType() instanceof PrimitiveType)
				? ((PrimitiveType) typeRef.getDeclaredType()).getAssignmentCompatible()
				: null;
		int i = 0;
		while (i < typeRefs.size()) {
			TypeRef t = typeRefs.get(i);

			if (tch.compare(typeRef, t) == 0) {
				return i;
			}
			if (assignmentCompatible != null) {
				Type type = t.getDeclaredType();
				if (type instanceof PrimitiveType) {
					if (tch.getTypeComparator().compare(assignmentCompatible, type) == 0 ||
							tch.getTypeComparator().compare(typeRef.getDeclaredType(),
									((PrimitiveType) type).getAssignmentCompatible()) == 0) {
						return i;
					}
				}
			}
			i = i + 1;
		}
		return -1;
	}
}
