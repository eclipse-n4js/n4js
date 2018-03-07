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
import org.eclipse.xsemantics.runtime.RuleEnvironment
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
	 * Returns a simplified copy of a given composed type, i.e. union or intersection type. This simplification may lead
	 * to the result no longer being a union/intersection type (for example, when given "A|A"), so any kind of type
	 * reference may be returned by this method.
	 * <p>
	 * The result will be,
	 * <ul>
	 * <li>a copy of one of the original's successors,
	 * <li>a newly created composed type reference, possibly containing copies of some of the original's successors, or
	 * <li>a newly created trivial type reference, such as 'null' or 'undefined'.
	 * </ul>
	 * Thus, this method will never directly return the passed-in original type reference or one of its successors
	 * (without creating a copy, first).
	 */
	def <T extends ComposedTypeRef> TypeRef simplify(RuleEnvironment G, T composedType) {
		val List<TypeRef> simplifiedAndCopiedTypeRefs = getSimplifiedTypeRefs(G, composedType, true);
		switch (simplifiedAndCopiedTypeRefs.size()) {
			case 0:
				return G.undefinedTypeRef
			case 1:
				return simplifiedAndCopiedTypeRefs.head
			default: {
				val eClass = composedType.eClass
				val result = EcoreUtil.create(eClass) as ComposedTypeRef
				result.typeRefs.addAll(simplifiedAndCopiedTypeRefs);
				return result;
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
