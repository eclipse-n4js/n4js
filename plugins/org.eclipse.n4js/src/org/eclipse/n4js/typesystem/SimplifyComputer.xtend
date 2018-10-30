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
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.utils.TypeCompareHelper
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import java.util.LinkedList
import java.util.List
import java.util.Set
import java.util.TreeSet
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.util.EcoreUtil

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*
import static extension java.util.Collections.*

/**
 * Type System Helper Strategy for creating simplified composed types, i.e. union
 * and intersection types.
 */
class SimplifyComputer extends TypeSystemHelperStrategy {

	@Inject
	extension TypeCompareHelper;

	/**
	 * Creates a simplified union type containing the given types; since it is simplified, the result is not necessarily a union type.
	 * The result may be contained in another container, so clients may have to use Ecore2.cloneIfNecessary(EObject).
	 */
	def TypeRef createUnionType(RuleEnvironment G, TypeRef... elements) {
		simplify(G, TypeUtils.createNonSimplifiedUnionType(elements));
	}

	/**
	 * Creates a simplified intersection type containing the given types; since it is simplified, the result is not necessarily an intersection type.
	 * The result may be contained in another container, so clients may have to use Ecore2.cloneIfNecessary(EObject).
	 */
	def TypeRef createIntersectionType(RuleEnvironment G, TypeRef... elements) {
		simplify(G, TypeUtils.createNonSimplifiedIntersectionType(elements));
	}

	/**
	 * Returns a simplified copy of a given composed type, i.e. union or intersection type.
	 * The returned type may be one of the elements, without cloning it.
	 * So clients need to clone the result if necessary.
	 * @see [N4JS Spec], 4.13 Intersection Type
	 */
	def <T extends ComposedTypeRef> TypeRef simplify(RuleEnvironment G, T composedType) {
		val List<TypeRef> tRs = getSimplifiedTypeRefs(G, composedType, true);
		val eClass = composedType.eClass
		val simplified = EcoreUtil.create(eClass) as ComposedTypeRef
		val typeRefs = simplified.typeRefs
		typeRefs.addAll(tRs);

		switch (typeRefs.size()) {
			case 0:
				return G.undefinedTypeRef
			case 1:
				return typeRefs.head
			default: {
				return simplified;
			}
		}

	}

	/**
	 * Returns a simplified list of all TypeRefs of a composed type.
	 */
	def <T extends ComposedTypeRef> List<TypeRef> getSimplifiedTypeRefs(RuleEnvironment G, T composedType) {
		return getSimplifiedTypeRefs(G, composedType, false);
	}

	def private <T extends ComposedTypeRef> List<TypeRef> getSimplifiedTypeRefs(RuleEnvironment G, T composedType, boolean copyIfContained) {
		if (composedType === null) {
			return null;
		}
		val eClass = composedType.eClass

		// unique elements
		val Set<TypeRef> set = new TreeSet<TypeRef>(getTypeRefComparator);

		// flatten
		if (composedType.typeRefs.exists [
			eClass.isInstance(it)
		]) {
			set.addAll(flattenComposedTypes(eClass, composedType));
		} else {
			set.addAll(composedType.typeRefs);
		}

		val List<TypeRef> typeRefs = new LinkedList<TypeRef>();
		val undefinedTypeRef = G.undefinedTypeRef
		val nullTypeRef = G.nullTypeRef
		for (e : set) {
			if (compare(e, undefinedTypeRef) != 0 &&
				compare(e, nullTypeRef) != 0) {
				var TypeRef cpy = e;
				if (copyIfContained)
					cpy = TypeUtils.copyIfContained(e);
				typeRefs.add(cpy);
			}
		}
		return typeRefs;
	}

	private def Iterable<TypeRef> flattenComposedTypes(EClass eClass, TypeRef typeRef) {
		if (eClass.isInstance(typeRef)) {
			(typeRef as ComposedTypeRef).typeRefs.map[flattenComposedTypes(eClass, it)].flatten
		} else {
			typeRef.singleton()
		};
	}
}
