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

import com.google.inject.Inject
import java.util.ArrayList
import java.util.Collections
import java.util.List
import java.util.Set
import java.util.TreeSet
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.types.utils.TypeCompareHelper
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem

import static extension java.util.Collections.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Type System Helper Strategy for creating simplified composed types, i.e. union
 * and intersection types.
 */
package class SimplifyComputer extends TypeSystemHelperStrategy {

	private static final UnknownTypeRef UNKNOWN_TYPE_REF = TypeRefsFactory.eINSTANCE.createUnknownTypeRef;

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeCompareHelper typeCompareHelper;

	/**
	 * Creates a simplified union type containing the given types; since it is simplified, the result is not necessarily a union type.
	 * The result may be contained in another container, so clients may have to use Ecore2.cloneIfNecessary(EObject).
	 */
	def TypeRef createUnionType(RuleEnvironment G, TypeRef... elements) {
		simplify(G, TypeUtils.createNonSimplifiedUnionType(elements), true);
	}

	/**
	 * Creates a simplified intersection type containing the given types; since it is simplified, the result is not necessarily an intersection type.
	 * The result may be contained in another container, so clients may have to use Ecore2.cloneIfNecessary(EObject).
	 */
	def TypeRef createIntersectionType(RuleEnvironment G, TypeRef... elements) {
		simplify(G, TypeUtils.createNonSimplifiedIntersectionType(elements), true);
	}

	/**
	 * Returns a simplified copy of a given composed type, i.e. union or intersection type.
	 * The returned type may be one of the elements, without cloning it.
	 * So clients need to clone the result if necessary.
	 * @see [N4JS Spec], 4.13 Intersection Type
	 */
	def <T extends ComposedTypeRef> TypeRef simplify(RuleEnvironment G, T composedType, boolean checkSubtypes) {
		val List<TypeRef> typeRefs = getSimplifiedTypeRefs(G, composedType, checkSubtypes);
		switch (typeRefs.size()) {
			case 0:
				return G.undefinedTypeRef
			case 1:
				return typeRefs.head
			default: {
				val eClass = composedType.eClass;
				val simplified = EcoreUtil.create(eClass) as ComposedTypeRef;
				simplified.typeRefs.addAll(typeRefs); // note: typeRefs were already copied (if contained)
				return simplified;
			}
		}
	}

	def private <T extends ComposedTypeRef> List<TypeRef> getSimplifiedTypeRefs(RuleEnvironment G, T composedType, boolean checkSubtypes) {
		if (composedType === null) {
			return null;
		}
		val typeRefsFlattened = flattenComposedTypes(composedType.eClass, composedType);
		val typeRefsTrimmed = removeDuplicateAndTrivialTypes(G, typeRefsFlattened, composedType);
		if (!checkSubtypes) {
			return typeRefsTrimmed;
		}
		val typeRefsSimplified = simplifyBasedOnSubtypeRelations(G, typeRefsTrimmed, composedType);
		return typeRefsSimplified;
	}

	private def Iterable<TypeRef> flattenComposedTypes(EClass eClass, TypeRef typeRef) {
		if (eClass.isInstance(typeRef)) {
			(typeRef as ComposedTypeRef).typeRefs.map[flattenComposedTypes(eClass, it)].flatten
		} else {
			typeRef.singleton()
		};
	}

	private def List<TypeRef> removeDuplicateAndTrivialTypes(RuleEnvironment G, Iterable<TypeRef> typeRefs, ComposedTypeRef composedType) {
		// simplify cases related to the trivial types: any, Object, null, undefined
		val anyTypeRef = G.anyTypeRef;
		val objectTypeRef = G.objectTypeRef;
		val nullTypeRef = G.nullTypeRef;
		val undefinedTypeRef = G.undefinedTypeRef;
		var haveAny = false;
		var haveObject = false;
		var haveNull = false;
		var haveUndefined = false;
		var haveUnknown = false;

		// remove duplicates but keep original order
		val noDups = new ArrayList();
		{
			val Set<TypeRef> set = new TreeSet<TypeRef>(typeCompareHelper.getTypeRefComparator);
			for (typeRef : typeRefs) {
				if (!set.contains(typeRef)) {
					val isAny = typeCompareHelper.isEqual(anyTypeRef, typeRef);
					val isObject = typeCompareHelper.isEqual(objectTypeRef, typeRef);
					val isNull = typeCompareHelper.isEqual(nullTypeRef, typeRef);
					val isUndefined = typeCompareHelper.isEqual(undefinedTypeRef, typeRef);
					val isUnknown = typeCompareHelper.isEqual(UNKNOWN_TYPE_REF, typeRef);
					haveAny = haveAny || isAny;
					haveObject = haveObject || isObject;
					haveNull = haveNull || isNull;
					haveUndefined = haveUndefined || isUndefined;
					haveUnknown = haveUnknown || isUnknown;
					if (isAny || isObject || isNull || isUndefined || isUnknown) {
						// skip
					} else {
						set.add(typeRef);
						noDups.add(typeRef);
					}
				}
			}
		}
		val haveOthers = !noDups.isEmpty;

		if (composedType instanceof UnionTypeExpression) {
			// in a union, subtypes of other elements can be thrown away
//			if (haveUnknown) {
//				return Collections.singletonList(UNKNOWN_TYPE_REF);
//			} else
			if (haveAny) {
				return Collections.singletonList(anyTypeRef);
			} else if (haveOthers) {
				// proceed with others below ...
			} else if (haveObject) {
				return Collections.singletonList(objectTypeRef);
			} else if (haveNull) {
				return Collections.singletonList(nullTypeRef);
			} else if (haveUndefined) {
				return Collections.singletonList(undefinedTypeRef);
			} else {
				return Collections.emptyList();
			}
		} else {
			// in an intersection, super types of other elements can be thrown away
			if (haveUndefined) {
				return Collections.singletonList(undefinedTypeRef);
			} else if (haveOthers) {
				// proceed with others below ...
			} else if (haveNull) {
				return Collections.singletonList(nullTypeRef);
			} else if (haveObject) {
				return Collections.singletonList(objectTypeRef);
			} else if (haveAny) {
				return Collections.singletonList(anyTypeRef);
			} else {
				return Collections.emptyList();
			}
		}
		
		if (noDups.isEmpty) {
			return Collections.emptyList();
		} else if(noDups.size === 1) {
			return Collections.singletonList(noDups.head);
		}

		val List<TypeRef> typeRefsCleaned = new ArrayList<TypeRef>(noDups.size + 2);
		for (e : noDups) {
			val TypeRef cpy = TypeUtils.copyIfContained(e);
			typeRefsCleaned.add(cpy);
		}
		if (haveObject) {
			typeRefsCleaned.add(objectTypeRef);
		}
		// NOTE: no need to add 'any' or 'null'/'undefined' here, because, if they were present,
		// they have either rendered all other typeRefs obsolete (and we returned early above) OR
		// they were rendered obsolete by the other typeRefs. The relation between 'null' and
		// 'undefined' when not also having other type references was already handled above.

		return typeRefsCleaned;
	}

	/**
	 * Simplifies the given typeRefs based on general subtype relations between them. For example,
	 * if class B inherits from class A, then union{A,B} can be simplified to A and intersection{A,B}
	 * can be simplified to B. However, for performance reasons, this is *ONLY DONE* if
	 * <ul>
	 * <li>there are only 2 typeRefs, and
	 * <li>there's no structural typing involved.
	 * </ul>
	 */
	private def List<TypeRef> simplifyBasedOnSubtypeRelations(RuleEnvironment G, List<TypeRef> typeRefs, ComposedTypeRef composedType) {
		if (typeRefs.size === 2) {
			val fst = typeRefs.get(0);
			val snd = typeRefs.get(1);

			val isStructural = fst.isUseSiteStructuralTyping || fst.isDefSiteStructuralTyping
				|| snd.isUseSiteStructuralTyping || snd.isDefSiteStructuralTyping;
			if (!isStructural) {
				if (G.isAnyDynamic(fst)) {
					if (composedType instanceof UnionTypeExpression) {
						return Collections.singletonList(fst); // chose any+
					} else {
						return Collections.singletonList(snd); // chose more concrete type
					}
				}
				if (G.isAnyDynamic(snd)) {
					if (composedType instanceof UnionTypeExpression) {
						return Collections.singletonList(snd); // chose any+
					} else {
						return Collections.singletonList(fst); // chose more concrete type
					}
				}
				
				val fstIsSubtype = ts.subtypeSucceeded(G, fst, snd);
				val sndIsSubtype = ts.subtypeSucceeded(G, snd, fst);
				if (fstIsSubtype && sndIsSubtype) {
					// int/number, and others?
					return Collections.singletonList(fst);
				}
				if (fstIsSubtype) {
					if (composedType instanceof UnionTypeExpression) {
						return Collections.singletonList(snd); // subtype can be thrown away
					} else {
						return Collections.singletonList(fst); // super type can be thrown away
					}
				}
				if (sndIsSubtype) {
					if (composedType instanceof UnionTypeExpression) {
						return Collections.singletonList(fst); // subtype can be thrown away
					} else {
						return Collections.singletonList(snd); // super type can be thrown away
					}
				}
			}
		}
		return typeRefs;
	}
}
