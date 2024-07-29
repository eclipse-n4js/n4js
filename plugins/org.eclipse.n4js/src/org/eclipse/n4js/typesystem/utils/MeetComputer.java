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
package org.eclipse.n4js.typesystem.utils;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isNullOrEmpty;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;

/**
 * Type System Helper Strategy computing the meet of a given collection of types.
 * <p>
 * Definition from [Pierce02a, pp. 218]:
 *
 * <pre>
 * Meet M = S ^ T, if
 *     M <: S, M <: T,                    common sub type
 *     forall L: L<:T and L<:S => L<:M    least
 * </pre>
 */
public class MeetComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts;

	/**
	 * Returns the meet (first common sub type) of the given types, maybe an intersection type, if types have no
	 * declared meet. This may be an intersection type, but not a union type. This method considers generics, but it
	 * does not compute lower or upper bounds of them. See class description for details.
	 *
	 * @return The meet which might be an intersection type. This method only returns null if all input type refs are
	 *         null. Note that the returned type ref is a newly created type ref which may be inserted into any
	 *         container.
	 */
	public TypeRef meet(RuleEnvironment G, Iterable<? extends TypeRef> typeRefs) {
		if (isNullOrEmpty(typeRefs)) {
			return null;
		}
		List<TypeRef> flatTypeRefs = new ArrayList<>();
		for (TypeRef tr : typeRefs) {
			if (tr instanceof ComposedTypeRef) {
				flatTypeRefs.addAll(((ComposedTypeRef) tr).getTypeRefs());
			} else {
				flatTypeRefs.add(tr);
			}
		}

		// we have to copy the reference, as it might be inserted into an intersection type
		TypeRef meet = null;
		for (TypeRef tr : flatTypeRefs) {
			if (meet == null) {
				meet = TypeUtils.copyIfContained(tr);
			} else {
				if (ts.subtypeSucceeded(G, tr, meet)) {
					meet = TypeUtils.copyIfContained(tr);
				} else if (!ts.subtypeSucceeded(G, meet, tr)) { // ?
					meet = intersectRelaxed(G, meet, tr);
				} // else meet is a subtype of tr and nothing is left to do
			}
		}
		return meet;
	}

	/**
	 * Creates the intersection according to [N4JS, 4.13 Intersection Type], but does not check for uniqueness of class
	 * in the typerefs of the intersection. The given type ref instances are only copied when added to the intersection
	 * if they have a container, otherwise the method assumes that the caller has newly created the typerefs which can
	 * be added directly. If one of the given type refs is any, then this any ref is returned; if the intersection
	 * contains only a single type, then this single type ios returned.
	 */
	// TODO see IDE-142/IDE-385
	@VisibleForTesting
	public TypeRef intersectRelaxed(RuleEnvironment G, TypeRef... typeRefs) {
		List<TypeRef> intersectTRs = new ArrayList<>();
		List<TypeRef> flattenedTypeRefs = new ArrayList<>();

		if (typeRefs != null) {
			for (TypeRef tr : typeRefs) {
				flattenIntersectionTypes2(tr, flattenedTypeRefs);
			}
		}

		TypeRef containedAny = findFirst(flattenedTypeRefs, it -> it != null && it.isTopType());

		if (containedAny != null) {
			intersectTRs.add(containedAny);
		} else {
			intersectTRs.addAll(tsh.getSubtypesOnly(G, flattenedTypeRefs.toArray(new TypeRef[0])));
		}

		if (intersectTRs.size() == 1) {
			TypeRef tR = intersectTRs.get(0);
			return TypeUtils.copyIfContained(tR);
		}

		IntersectionTypeExpression intersection = TypeRefsFactory.eINSTANCE.createIntersectionTypeExpression();
		for (TypeRef s : intersectTRs) {
			intersection.getTypeRefs().add(TypeUtils.copyIfContained((s)));
		}
		return intersection;
	}

	private void flattenIntersectionTypes2(TypeRef typeRef, List<TypeRef> addHere) {
		if (typeRef instanceof IntersectionTypeExpression) {
			IntersectionTypeExpression ite = (IntersectionTypeExpression) typeRef;
			for (TypeRef child : ite.getTypeRefs()) {
				flattenIntersectionTypes2(child, addHere);
			}
		} else {
			addHere.add(typeRef);
		}
	}
}
