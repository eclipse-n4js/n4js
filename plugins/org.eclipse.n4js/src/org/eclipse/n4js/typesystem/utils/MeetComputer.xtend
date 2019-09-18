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
package org.eclipse.n4js.typesystem.utils

import com.google.common.annotations.VisibleForTesting
import com.google.inject.Inject
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import java.util.LinkedList

import static extension java.util.Collections.*
import org.eclipse.n4js.typesystem.N4JSTypeSystem

/**
 * Type System Helper Strategy computing the meet of a given collection of types.
 * <p>
 * Definition from [Pierce02a, pp. 218]:
 * <pre>
 * Meet M = S ^ T, if
 *     M <: S, M <: T,                    common sub type
 *     forall L: L<:T and L<:S => L<:M    least
 * </pre>
 */
class MeetComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts

	/**
  	 * Returns the meet (first common sub type) of the given types, maybe an intersection type, if types have no declared meet.
  	 * This may be an intersection type, but not a union type. This method considers generics, but it does not
  	 * compute lower or upper bounds of them.
  	 * See class description for details.
	 * @return The meet which might be an intersection type.
	 * This method only returns null if all input type refs are null.
	 * Note that the returned type ref is a newly created type ref which may be inserted into any container.
	 */
	def meet(RuleEnvironment G, Iterable<? extends TypeRef> typeRefs) {
		if (typeRefs.nullOrEmpty) {
			return null;
		}
		val flatTypeRefs = typeRefs.map[it.flattenTypeRefs].flatten

		// we have to copy the reference, as it might be inserted into an intersection type
		var TypeRef meet = null;
		for (tr : flatTypeRefs) {
			if (meet === null) {
				meet = TypeUtils.copyIfContained(tr);
			} else {
				if (ts.subtypeSucceeded(G, tr, meet)) {
					meet = TypeUtils.copyIfContained(tr);
				} else if (! ts.subtypeSucceeded(G, meet, tr)) { //?
					meet = intersectRelaxed(G, meet, tr)
				} // else meet is a subtype of tr and nothing is left to do
			}
		}
		return meet;
	}

	private def dispatch Iterable<? extends TypeRef> flattenTypeRefs(TypeRef ref) {
		return singleton(ref);
	}

	private def dispatch Iterable<? extends TypeRef> flattenTypeRefs(ComposedTypeRef ref) {
		ref.typeRefs;
	}


	/**
	 * Creates the intersection according to [N4JS, 4.13 Intersection Type], but does not check for uniqueness
	 * of class in the typerefs of the intersection.
	 * The given type ref instances are only copied when added to the intersection
	 * if they have a container, otherwise the method assumes that the caller has newly created the typerefs which can be added directly.
	 * If one of the given type refs is any, then this any ref is returned; if the intersection contains only a single type, then this single type ios returned.
	 */
	// TODO see IDE-142/IDE-385
	@VisibleForTesting
	def TypeRef intersectRelaxed(RuleEnvironment G, TypeRef... typeRefs) {
		val intersectTRs = new LinkedList<TypeRef>();
		val flattenedTypeRefs = typeRefs.map[flattenIntersectionTypes].flatten;
		val containedAny = flattenedTypeRefs.findFirst[it !== null && topType];
		if (containedAny !== null) {
			intersectTRs.add(containedAny);
		} else {
			intersectTRs.addAll(tsh.getSubtypesOnly(G, flattenedTypeRefs));
		}

		if (intersectTRs.size() == 1) {
			val tR = intersectTRs.get(0)
			return TypeUtils.copyIfContained(tR);
		}

		val intersection = TypeRefsFactory.eINSTANCE.createIntersectionTypeExpression();
		for (s : intersectTRs) {
			intersection.typeRefs.add(TypeUtils.copyIfContained((s)));
		}
		return intersection
	}


	private def Iterable<TypeRef> flattenIntersectionTypes(TypeRef typeRef) {
		switch typeRef {
			IntersectionTypeExpression: typeRef.typeRefs.map[flattenIntersectionTypes(it)].flatten
			default: typeRef.singleton()
		};
	}
}
