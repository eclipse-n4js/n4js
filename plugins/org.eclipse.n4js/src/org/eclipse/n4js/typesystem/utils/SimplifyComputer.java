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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isAnyDynamic;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isBoolean;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isNumeric;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isString;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.nullTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedTypeRef;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.types.utils.TypeCompareHelper;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;

import com.google.inject.Inject;

/**
 * Type System Helper Strategy for creating simplified composed types, i.e. union and intersection types.
 */
class SimplifyComputer extends TypeSystemHelperStrategy {

	private static final UnknownTypeRef UNKNOWN_TYPE_REF = TypeRefsFactory.eINSTANCE.createUnknownTypeRef();

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeCompareHelper typeCompareHelper;

	/**
	 * Creates a simplified union type containing the given types; since it is simplified, the result is not necessarily
	 * a union type. The result may be contained in another container, so clients may have to use
	 * Ecore2.cloneIfNecessary(EObject).
	 */
	TypeRef createUnionType(RuleEnvironment G, TypeRef... elements) {
		return simplify(G, TypeUtils.createNonSimplifiedUnionType(elements), true);
	}

	/**
	 * Creates a simplified intersection type containing the given types; since it is simplified, the result is not
	 * necessarily an intersection type. The result may be contained in another container, so clients may have to use
	 * Ecore2.cloneIfNecessary(EObject).
	 */
	TypeRef createIntersectionType(RuleEnvironment G, TypeRef... elements) {
		return simplify(G, TypeUtils.createNonSimplifiedIntersectionType(elements), true);
	}

	/**
	 * Returns a simplified copy of a given composed type, i.e. union or intersection type. The returned type may be one
	 * of the elements, without cloning it. So clients need to clone the result if necessary.
	 *
	 * @apiNote [N4JS Spec], 4.13 Intersection Type
	 */
	<T extends ComposedTypeRef> TypeRef simplify(RuleEnvironment G, T composedType, boolean checkSubtypes) {
		List<TypeRef> typeRefs = getSimplifiedTypeRefs(G, composedType, checkSubtypes);
		switch (typeRefs.size()) {
		case 0:
			return undefinedTypeRef(G);
		case 1:
			return typeRefs.get(0);
		default: {
			EClass eClass = composedType.eClass();
			ComposedTypeRef simplified = (ComposedTypeRef) EcoreUtil.create(eClass);
			simplified.getTypeRefs().addAll(typeRefs); // note: typeRefs were already copied (if contained)
			return simplified;
		}
		}
	}

	private <T extends ComposedTypeRef> List<TypeRef> getSimplifiedTypeRefs(RuleEnvironment G, T composedType,
			boolean checkSubtypes) {
		if (composedType == null) {
			return null;
		}
		Iterable<TypeRef> typeRefsFlattened = flattenComposedTypes(composedType.eClass(), composedType);
		List<TypeRef> typeRefsTrimmed = removeDuplicateAndTrivialTypes(G, typeRefsFlattened, composedType);
		if (!checkSubtypes) {
			return typeRefsTrimmed;
		}
		List<TypeRef> typeRefsSimplified = simplifyBasedOnSubtypeRelations(G, typeRefsTrimmed, composedType);
		return typeRefsSimplified;
	}

	private Iterable<TypeRef> flattenComposedTypes(EClass eClass, TypeRef typeRef) {
		if (eClass.isInstance(typeRef)) {
			EList<TypeRef> typeRefs = ((ComposedTypeRef) typeRef).getTypeRefs();
			return flatten(map(typeRefs, it -> flattenComposedTypes(eClass, it)));
		} else {
			return Collections.singleton(typeRef);
		}
	}

	private List<TypeRef> removeDuplicateAndTrivialTypes(RuleEnvironment G, Iterable<TypeRef> typeRefs,
			ComposedTypeRef composedType) {
		// simplify cases related to the trivial types: any, Object, null, undefined
		TypeRef anyTypeRef = anyTypeRef(G);
		TypeRef objectTypeRef = objectTypeRef(G);
		TypeRef nullTypeRef = nullTypeRef(G);
		TypeRef undefinedTypeRef = undefinedTypeRef(G);
		var haveAny = false;
		var haveObject = false;
		var haveNull = false;
		var haveUndefined = false;
		var haveUnknown = false;

		var haveNumeric = false;
		var haveBoolean = false;
		var haveString = false;

		// remove duplicates but keep original order
		ArrayList<TypeRef> noDups = new ArrayList<>();
		ArrayList<TypeRef> noDupsWithoutObject = new ArrayList<>();
		{
			Set<TypeRef> set = new TreeSet<>(typeCompareHelper.getTypeRefComparator());
			for (TypeRef typeRef : typeRefs) {
				if (!set.contains(typeRef)) {
					boolean isAny = typeCompareHelper.isEqual(anyTypeRef, typeRef);
					boolean isObject = typeCompareHelper.isEqual(objectTypeRef, typeRef);
					boolean isNull = typeCompareHelper.isEqual(nullTypeRef, typeRef);
					boolean isUndefined = typeCompareHelper.isEqual(undefinedTypeRef, typeRef);
					boolean isUnknown = typeCompareHelper.isEqual(UNKNOWN_TYPE_REF, typeRef);

					boolean isNumeric = isNumeric(G, typeRef);
					boolean isBoolean = isBoolean(G, typeRef);
					boolean isString = isString(G, typeRef);

					if (isAny || isNull || isUndefined
							|| (haveNumeric && isNumeric)
							|| (haveBoolean && isBoolean)
							|| (haveString && isString)) {
						// skip
					} else {
						set.add(typeRef);
						noDups.add(typeRef);
						if (!isObject) {
							noDupsWithoutObject.add(typeRef);
						}
					}

					haveAny = haveAny || isAny;
					haveObject = haveObject || isObject;
					haveNull = haveNull || isNull;
					haveUndefined = haveUndefined || isUndefined;
					haveUnknown = haveUnknown || isUnknown;
					haveNumeric = haveNumeric || isNumeric;
					haveBoolean = haveBoolean || isBoolean;
					haveString = haveString || isString;
				}
			}
		}
		boolean haveOthers = !noDupsWithoutObject.isEmpty();

		if (composedType instanceof UnionTypeExpression) {
			// in a union, subtypes of other elements can be thrown away
			// if (haveUnknown) {
			// return Collections.singletonList(UNKNOWN_TYPE_REF);
			// } else
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

		List<TypeRef> typeRefsCleaned = new ArrayList<>(noDups.size() + 2);
		for (TypeRef e : noDups) {
			TypeRef cpy = TypeUtils.copyIfContained(e);
			typeRefsCleaned.add(cpy);
		}
		// NOTE: no need to add 'any' or 'null'/'undefined' here, because, if they were present,
		// they have either rendered all other typeRefs obsolete (and we returned early above) OR
		// they were rendered obsolete by the other typeRefs. The relation between 'null' and
		// 'undefined' when not also having other type references was already handled above.

		return typeRefsCleaned;
	}

	/**
	 * Simplifies the given typeRefs based on general subtype relations between them. For example, if class B inherits
	 * from class A, then union{A,B} can be simplified to A and intersection{A,B} can be simplified to B. However, for
	 * performance reasons, this is *ONLY DONE* if
	 * <ul>
	 * <li>there are only 2 typeRefs, and
	 * <li>there's no structural typing involved.
	 * </ul>
	 */
	private List<TypeRef> simplifyBasedOnSubtypeRelations(RuleEnvironment G, List<TypeRef> typeRefs,
			ComposedTypeRef composedType) {
		if (typeRefs.size() == 2) {
			TypeRef fst = typeRefs.get(0);
			TypeRef snd = typeRefs.get(1);

			boolean isFstStructural = fst.isUseSiteStructuralTyping() || fst.isDefSiteStructuralTyping();
			boolean isSndStructural = snd.isUseSiteStructuralTyping() || snd.isDefSiteStructuralTyping();
			boolean doSimplify = !isFstStructural && !isSndStructural;

			if (doSimplify) {
				if (isAnyDynamic(G, fst)) {
					if (composedType instanceof UnionTypeExpression) {
						return Collections.singletonList(fst); // chose any+
					} else {
						return Collections.singletonList(snd); // chose more concrete type
					}
				}
				if (isAnyDynamic(G, snd)) {
					if (composedType instanceof UnionTypeExpression) {
						return Collections.singletonList(snd); // chose any+
					} else {
						return Collections.singletonList(fst); // chose more concrete type
					}
				}

				boolean fstIsSubtype = ts.subtypeSucceeded(G, fst, snd);
				boolean sndIsSubtype = ts.subtypeSucceeded(G, snd, fst);
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
