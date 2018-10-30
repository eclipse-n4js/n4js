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

import com.google.common.collect.Iterables
import com.google.common.collect.Lists
import com.google.inject.Inject
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Set
import org.eclipse.n4js.scoping.members.TypingStrategyFilter
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.types.AnyType
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TStructMember
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.utils.SuperTypesList
import org.eclipse.n4js.ts.utils.TypeCompareHelper
import org.eclipse.n4js.ts.utils.TypeHelper
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.typesystem.utils.RuleEnvironment

import static java.util.Collections.*
import static org.eclipse.n4js.ts.utils.SuperTypesList.*

import static extension org.eclipse.n4js.ts.utils.TypeUtils.*
import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Type System Helper Strategy computing the join of a given collection of types.
 * <p>
 * Definition from [Pierce02a, pp. 218]:
 * <pre>
 * Join J = S v T, if
 *     S <: J, T <:J,                         common super type
 *     forall U: S <:U and T <:U => J <: U    least
 * </pre>
 * <p>
 * It is also rigor with regard to auto-conversion. That is, {@code string} and {@String} are
 * not automatically converted into each other. This is because both types behave differently.
 * E.g., if used as a boolean expression, {@code ""} and {@code new String("")} will be evaluated differently.
 * <em>The object type will always return true.</em> This is even true for {@code new Boolean(false)}, which will also
 * be evaluated to true (note however that {@code Boolean(false)} will return a {@code boolean} value, which
 * is then evaluated to false).
 * <p>
 * Special pseudo sub types of string, e.g. pathSelector, are handled as well.
 */
class JoinComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts

	@Inject
	extension TypeCompareHelper;

	@Inject
	extension TypeHelper;

	@Inject ContainerTypesHelper containerTypesHelper;

	@Inject
	GenericsComputer genericsComputer;

	/**
  	 * Returns the join, sometimes called least common super type (LCST),
	 * of the given types. This may be an intersection type but not a union type.
	 * See class description for details.
	 *
	 * @return the join, may be a contained reference. Thus clients may need to create a copy!
	 */
	def TypeRef join(RuleEnvironment G, Iterable<? extends TypeRef> typeRefsToJoin) {

		if (typeRefsToJoin === null) { // quick return
			return null;
		}

		val typeRefs = typeRefsToJoin.filterNull;
		if (typeRefs.empty) { // quick return
			return null;
		}
		if (typeRefs.size === 1) { // quick return
			return typeRefs.head;
		}

		val unionTypeRefs = typeRefs.filter(UnionTypeExpression);
		val nonUnionTypeRefs = typeRefs.filter[! (it instanceof UnionTypeExpression)]

		val nonUnionJoin = joinNonUnionTypes(G, nonUnionTypeRefs);
		if (unionTypeRefs.empty) {
			return nonUnionJoin;
		} else {
			return joinNonUnionTypeWithUniontypes(G, nonUnionJoin, unionTypeRefs)
		}
	}

	/**
	 * join result of non-union types join with all union types
	 *  @see [N4JS Spec], 4.12 Union Type
	 * @param unionTypeRefs cannot be empty, called only from method above where it is checked
	 */
	private def TypeRef joinNonUnionTypeWithUniontypes(RuleEnvironment G, TypeRef nonUnionJoin,
		Iterable<UnionTypeExpression> unionTypeRefs) {

		if (nonUnionJoin === null && unionTypeRefs.size == 1) {
			return G.simplify(unionTypeRefs.head)
		}

		val union = TypeRefsFactory.eINSTANCE.createUnionTypeExpression();
		if (nonUnionJoin !== null) {
			union.typeRefs.add(TypeUtils.copyIfContained(nonUnionJoin));
		}
		union.typeRefs.addAll((unionTypeRefs.map[TypeUtils.copyIfContained(it)]))
		return G.simplify(union)
	}

	/**
	 * Called internally to compute the join of non-union types only.
	 * @param nonUnionTypeRefs type references, must not contain UnionTypeExpressions
	 */
	private def TypeRef joinNonUnionTypes(RuleEnvironment G, Iterable<? extends TypeRef> nonUnionTypeRefs) {
		if (nonUnionTypeRefs.empty) { // quick return
			return null;
		}
		if (nonUnionTypeRefs.size === 1) { // quick return
			return nonUnionTypeRefs.head;
		}

		// 1) find least common raw type. Although typerefs are used, the type arguments are ignored
		var List<TypeRef> commonSuperTypesIgnoreTypeArgs;
		val firstType = nonUnionTypeRefs.head.declaredType
		if (firstType !== null && nonUnionTypeRefs.forall[declaredType === firstType]) { // shortcut
			val head = nonUnionTypeRefs.head
			if (! head.parameterized && nonUnionTypeRefs.forall[! useSiteStructuralTyping]) { // quick return
				return head;
			}
			commonSuperTypesIgnoreTypeArgs = singletonList(head)
		} else {

			// 1.1) collect all common super (raw) types.
			commonSuperTypesIgnoreTypeArgs = commonSuperTypesTypeargsIgnored(G, nonUnionTypeRefs)
			if (commonSuperTypesIgnoreTypeArgs.empty) { // quick return
				return G.anyTypeRef
			}

			// 1.2) find LEAST common super (raw) type
			if (commonSuperTypesIgnoreTypeArgs.size > 1) {

				// remove all types which are super types of other types contained in commonSuperTypes
				// precondition: the list is sorted: index of a type is always less than index of its supertypes!
				// 		this is ensured by the order in which the elements are added to the list
				var i = 0;
				while (i < commonSuperTypesIgnoreTypeArgs.size) {
					commonSuperTypesIgnoreTypeArgs.removeAllSuperTypesOfType(commonSuperTypesIgnoreTypeArgs.get(i), G);
					i = i + 1;
				}
			}
		}

		// 2) handle type arguments
		var List<TypeRef> commonSuperTypesParameterized;
		if (commonSuperTypesIgnoreTypeArgs.forall[! parameterized]) { // shortcut
			commonSuperTypesParameterized = commonSuperTypesIgnoreTypeArgs;
		} else {
			commonSuperTypesParameterized = Lists.newArrayListWithCapacity(commonSuperTypesIgnoreTypeArgs.size)
			for (TypeRef superTypeIgnoreTypeArgs : commonSuperTypesIgnoreTypeArgs) {
				if (! superTypeIgnoreTypeArgs.parameterized) {

					// TODO raw, it it that simple? what about the bound in the generic declaration?!
					commonSuperTypesParameterized.add(TypeUtils.copyIfContained(superTypeIgnoreTypeArgs));
				} else {

					// collect all super types again with these bounds, but this time properly substitute type variables
					val parameterizedSuperTypes = collectParameterizedSuperType(nonUnionTypeRefs,
						superTypeIgnoreTypeArgs.declaredType, G);
					if (parameterizedSuperTypes.size == 1) {
						commonSuperTypesParameterized.add(TypeUtils.copyIfContained(parameterizedSuperTypes.head));
					} else {
						val TypeRef merged = TypeUtils.copy(parameterizedSuperTypes.head);
						merged.typeArgs.clear();
						var i = 0;
						while (i < merged.declaredType.typeVars.size) {
							val currentIndex = i;
							val upperBound = join(G,
								parameterizedSuperTypes.map [
									// (typeArgs.get(currentIndex) as TypeRef).declaredUpperBound
									ts.upperBound(G, (typeArgs.get(currentIndex))).value
								])
							val lowerBound = meet(G,
								parameterizedSuperTypes.map [
									// (typeArgs.get(currentIndex) as TypeRef).declaredLowerBound
									ts.lowerBound(G, (typeArgs.get(currentIndex))).value
								])
							if (compare(upperBound, lowerBound) == 0) {
								merged.typeArgs.add(TypeUtils.copyIfContained(upperBound))
							} else {
								val wildcard = TypeRefsFactory.eINSTANCE.createWildcard;
								if (upperBound.topType && ! lowerBound.bottomType) {

									// this is the Java 6 and 7 behavior with wildcard capture:
									// wildcard.declaredUpperBound = TypeUtils.copyIfContained(G.anyTypeRef)
									// this is the Java 8 (and probably correct) behavior:
									wildcard.declaredLowerBound = TypeUtils.copyIfContained(lowerBound);
								} else {
									wildcard.declaredUpperBound = TypeUtils.copyIfContained(upperBound);
								}
								merged.typeArgs.add(wildcard)
							}
							i = i + 1;
						}
						commonSuperTypesParameterized.add(merged);
					}
				}
			}
		}

		// 3) handle structurally added members, first version in IDE-691, tested in IDE-142
		if (nonUnionTypeRefs.forall[declaredType instanceof ContainerType<?>]) {
			val typingStrategy = if (nonUnionTypeRefs.exists[it.typingStrategy === TypingStrategy.STRUCTURAL_FIELDS]) {
					TypingStrategy.STRUCTURAL_FIELDS
				} else if (nonUnionTypeRefs.exists[it.typingStrategy === TypingStrategy.STRUCTURAL]) {
					TypingStrategy.STRUCTURAL
				} else {
					TypingStrategy.DEFAULT
				}

			if (typingStrategy !== TypingStrategy.DEFAULT) {
				val ptrs = TypeRefsFactory.eINSTANCE.createParameterizedTypeRefStructural();
				val trTemplate = if (commonSuperTypesParameterized.head instanceof ParameterizedTypeRef) {
						TypeUtils.copyIfContained(commonSuperTypesParameterized.head);
					} else {
						G.objectTypeRef?.copyIfContained
					}
				ptrs.definedTypingStrategy = typingStrategy
				ptrs.declaredType = trTemplate.declaredType;
				ptrs.typeArgs.addAll(trTemplate.typeArgs);

				val filter = new TypingStrategyFilter(typingStrategy);
				val structuralMembersByName = new HashMap<String, TMember>();
				val structuralMembersWithDifferentTypeOrAlreadyContained = new HashSet<String>();
				structuralMembersWithDifferentTypeOrAlreadyContained.addAll(
					containerTypesHelper.fromContext(G.contextResource).members(ptrs.declaredType as ContainerType<?>).filter[filter.apply(it)].map[name]);

				for (TypeRef tr : nonUnionTypeRefs.filter[declaredType !== ptrs.declaredType]) {
					for (TMember structMember : Iterables.concat(tr.structuralMembers,
						containerTypesHelper.fromContext(G.contextResource).members(tr.declaredType as ContainerType<?>).filter[filter.apply(it)])) {
						if (!structuralMembersWithDifferentTypeOrAlreadyContained.contains(structMember.name)) {
							val duplicate = structuralMembersByName.get(structMember.name);
							if (duplicate === null) {
								structuralMembersByName.put(structMember.name, structMember);
							} else if (! similarMember(G, duplicate, structMember)) {
								structuralMembersWithDifferentTypeOrAlreadyContained.add(structMember.name);
								structuralMembersByName.remove(structMember.name);
							}
						}
					}
				}

				ptrs.genStructuralMembers.addAll(structuralMembersByName.values().map[substituted(G, it)].filter(TStructMember));
				return ptrs;
			}
		}

		// 4) if more than one LCST has been found, create intersection
		val singleLCST = switch commonSuperTypesParameterized.size {
			case 0:
				throw new IllegalStateException(
					"Error processing least common super type, parameterization removed all types")
			case 1:
				commonSuperTypesParameterized.head
			default: {
				val intersectionTypeExpr = TypeRefsFactory.eINSTANCE.createIntersectionTypeExpression();
				commonSuperTypesParameterized.forEach[
					intersectionTypeExpr.typeRefs.add(TypeUtils.copyIfContained(it))];
				intersectionTypeExpr;
			}
		}

		return singleLCST;
	}

	private def dispatch substituted(RuleEnvironment G, TMember member) {
		return member;
	}

	private def dispatch substituted(RuleEnvironment G, TField member) {
		if (member.typeRef.parameterized) {
			val subst = TypesFactory.eINSTANCE.createTStructField();
			subst.name = member.name;
			subst.typeRef = ts.substTypeVariables(G, member.typeRef).value as TypeRef;
			if (subst.typeRef === null) {
				subst.typeRef = G.anyTypeRef;
			}
			return subst;
		} else {
			return member
		}
	}

	private def dispatch substituted(RuleEnvironment G, TGetter member) {
		if (member.declaredTypeRef !== null && member.declaredTypeRef.parameterized) {
			val subst = TypesFactory.eINSTANCE.createTStructGetter();
			subst.name = member.name;
			subst.declaredTypeRef = ts.substTypeVariables(G, member.declaredTypeRef).value as TypeRef;
			if (subst.declaredTypeRef === null) {
				subst.declaredTypeRef = G.anyTypeRef;
			}
			return subst;
		} else {
			return member
		}
	}

	private def dispatch substituted(RuleEnvironment G, TSetter member) {
		if (member.fpar !== null && member.fpar.typeRef !== null && member.fpar.typeRef.parameterized) {
			val subst = TypesFactory.eINSTANCE.createTStructSetter();
			subst.name = member.name;

			var tr = ts.substTypeVariables(G, member.fpar.typeRef).value as TypeRef;
			if (tr === null) {
				tr = G.anyTypeRef;
			}
			subst.fpar = TypesFactory.eINSTANCE.createTFormalParameter();
			subst.fpar.name = member.fpar.name;
			subst.fpar.typeRef = TypeUtils.copyIfContained(tr);
			return subst;
		} else {
			return member
		}
	}

	private def dispatch substituted(RuleEnvironment G, TMethod member) {
		val ftype = ts.type(G, member).value as FunctionTypeExpression
		val subst = TypesFactory.eINSTANCE.createTStructMethod();
		subst.name = member.name;
		subst.fpars.addAll(ftype.fpars);
		subst.returnTypeRef = ftype.returnTypeRef;
		subst.typeVars.addAll(member.typeVars.map[TypeUtils.copyIfContained(it)]);
		return subst;
	}

	def similarMember(RuleEnvironment G, TMember m1, TMember m2) {

		val t1 = ts.type(G, m1).value;
		val t2 = ts.type(G, m2).value;
		if (t1 === null || t2 === null) {
			return false;
		}
		return ts.subtypeSucceeded(G, t1, t2) && ts.subtypeSucceeded(G, t2, t1)
	}

	/*
	 * Removes all super types of ref from list of refs. This method is optimized for leastCommonSuperType and
	 * assumes that all types in orderedRefs are ordered as returned by collecAllDeclaredSuperTypes().
	 */
	private def void removeAllSuperTypesOfType(List<TypeRef> orderedRefs, TypeRef ref, RuleEnvironment G) {
		val Iterable<TypeRef> nonLeastSuperTypes = ref.collectAllDeclaredSuperTypesTypeargsIgnored(false) +
			G.collectAllImplicitSuperTypes(ref)

		for (nonLeastSuperType : nonLeastSuperTypes) {
			if (!orderedRefs.removeTypeRef(nonLeastSuperType)) {
				return;
			}
		}
	}

	/*
	 * Search for reference in list of super types of each type ref matching given rawSuperType, using a
	 * depth first search.
	 *
	 * @param refs 		collection of type references, size > 1
	 * @param rawSuperTypeRef a raw super type, which is a common super type of all types in refs
	 */
	private def Set<TypeRef> collectParameterizedSuperType(Iterable<? extends TypeRef> refs,
		Type rawSuperType, RuleEnvironment G) {

		val result = newTreeSet(getTypeRefComparator);
		for (typeRef : refs) {
			val pathFromSuperType = typeRef.computePathFromSuperTypeReflexive(rawSuperType, <Type>newHashSet())
			if (pathFromSuperType === null) {
				throw new IllegalStateException("Did not found " + rawSuperType + " in super types of " + typeRef);
			}
			val concreteSuperTypeRef = pathFromSuperType.head;

			result.add(
				TypeUtils.copyIfContained(
					if (! concreteSuperTypeRef.containsUnboundTypeVariables) {
						concreteSuperTypeRef
					} else {
						val Gnext = G.wrap;
// original code:
//						var Gnext = G;
//						// parameterize the references:
//						for (TypeRef tr : pathFromSuperType.reverseView) {
//							var Gnew = new RuleEnvironment();
//							Gnew.next = Gnext;
//							Gnext = Gnew;
//						}
						genericsComputer.bindTypeVariables(Gnext,concreteSuperTypeRef);
					}
				));
		}

		return result;
	}

	/**
	 * Returns transitive reflexive closure of <em>common</em> super types.
	 * Type arguments are ignored here.
	 * @return the returned list is sorted, that is, a type's super types are always AFTER the type in the list
	 */
	private def List<TypeRef> commonSuperTypesTypeargsIgnored(RuleEnvironment G, Iterable<? extends TypeRef> typeRefs) {
		val List<TypeRef> commonSuperTypes = newArrayList();
		for (TypeRef t : typeRefs) {
			if (! addSuperTypesToCommonList(G, t, commonSuperTypes)) {
				return emptyList
			}
		}
		return commonSuperTypes
	}

	/**
	 * Returns transitive, non-reflexive closure of implicit super types. Relexive means, that in case of e.g., Object, Object is returned itself.
	 */
	private def void collectAllImplicitSuperTypes(TypeRef ref, RuleEnvironment G,
		SuperTypesList<TypeRef> superTypesList) {
		superTypesList.addAll(G.collectAllImplicitSuperTypes(ref));
	}

	/**
	 * Adds or intersects types in reflexive transitive closure of a given type <i>t</i> to/with given list of super types.
	 *
	 * @return true, if super types have been added and if client should proceed; <br/>
	 * 		  false, if no common super type can ever by found and client can stop looking for it
	 */
	private def dispatch boolean addSuperTypesToCommonList(RuleEnvironment G, TypeRef t,
		List<TypeRef> commonSuperTypes) {
		switch t.declaredType {
			TClassifier: {
				val allDeclaredSuperTypes = newSuperTypesList(getTypeRefComparator);
				allDeclaredSuperTypes.add(t)
				t.collectAllDeclaredSuperTypesTypeargsIgnored(allDeclaredSuperTypes)
				t.collectAllImplicitSuperTypes(G, allDeclaredSuperTypes)

				addOrIntersectTypes(G, commonSuperTypes, allDeclaredSuperTypes)
			}
			AnyType:
				return false
			PrimitiveType:
				addOrIntersectTypeWithAssignmentCompatibles(G, commonSuperTypes, t)
			default: {
				// ignored (as they are common pseudo-sub types): NullType, UndefinedType, VoidType
				// or
				// handled in other addSuperTypesToCommonList, as they have different references
			}
		}
		return true;
	}

	private def dispatch  addSuperTypesToCommonList(RuleEnvironment G, IntersectionTypeExpression t,
		List<TypeRef> commonSuperTypes) {
		val allDeclaredSuperTypes = newSuperTypesList(getTypeRefComparator);
		for (TypeRef containedRef : t.typeRefs) {
			allDeclaredSuperTypes.add(containedRef)
			containedRef.collectAllDeclaredSuperTypesTypeargsIgnored(allDeclaredSuperTypes)
			containedRef.collectAllImplicitSuperTypes(G, allDeclaredSuperTypes)
		}
		addOrIntersectTypes(G, commonSuperTypes, allDeclaredSuperTypes)
		return true;
	}

	private def dispatch  addSuperTypesToCommonList(RuleEnvironment G, UnionTypeExpression t,
		List<TypeRef> commonSuperTypes) {
		val allDeclaredSuperTypes = newSuperTypesList(getTypeRefComparator);

		allDeclaredSuperTypes.add(t)
		addOrIntersectTypes(G, commonSuperTypes, allDeclaredSuperTypes)
		return true;
	}

	private def void addOrIntersectTypeWithAssignmentCompatibles(RuleEnvironment G, List<TypeRef> commonSuperTypes,
		TypeRef typeRef) {
		if (commonSuperTypes.empty) { // quick break
			commonSuperTypes.add(typeRef);
			return;
		}

		if (commonSuperTypes.containsByType(typeRef)) {
			if (commonSuperTypes.size == 1) {
				return;
			}
			commonSuperTypes.clear();
			commonSuperTypes.add(typeRef);
			return;
		}

		val type = typeRef.declaredType
		if (type instanceof PrimitiveType) {
			val index = commonSuperTypes.findTypeRefOrAssignmentCompatible(typeRef)
			if (index >= 0) {
				if (type.assignmentCompatible === null) { // e.g. we have string and found pathselector
					commonSuperTypes.clear();
					commonSuperTypes.add(typeRef);
				} else { // e.g., we have pathselector and found string
					if (commonSuperTypes.size != 1) {
						val TypeRef tr = commonSuperTypes.get(index)
						commonSuperTypes.clear();
						commonSuperTypes.add(tr);
					} // else e.g. there is only string in the list, leave it there
				}
				return;
			}
		}
		commonSuperTypes.clear();
	}

	private def dispatch addSuperTypesToCommonList(RuleEnvironment G, FunctionTypeExprOrRef f,
		List<TypeRef> commonSuperTypes) {
		val allDeclaredSuperTypes = newSuperTypesList(getTypeRefComparator);
		allDeclaredSuperTypes.add(f)
		f.collectAllImplicitSuperTypes(G, allDeclaredSuperTypes)
		addOrIntersectTypes(G, commonSuperTypes, allDeclaredSuperTypes)
		return true;
	}

	/*
	 * Returns path from super type to current ref, including the super type and the initial type.
	 * @return path, or null if the raw super type has not been found (which probably is an illegal result, except in combination with intersection types)
	 */
	private def dispatch List<TypeRef> computePathFromSuperTypeReflexive(TypeRef ref, Type rawSuperType,
		Set<Type> processedTypes) {
		if (ref.declaredType == rawSuperType) {
			return newArrayList(ref);
		} else {
			for (superTypeRef : ref.declaredType.declaredSuperTypes) {
				if (processedTypes.add(superTypeRef.declaredType)) {
					val superPath = computePathFromSuperTypeReflexive(superTypeRef, rawSuperType, processedTypes)
					if (superPath !== null) {
						superPath.add(ref);

						// we can break here, although there might be several paths to the rawSuperType. However,
						// all paths must result in the same parameterized version, so we only need one
						return superPath;
					}
				}
			}
		}
		return null;
	}

	/*
	 * For intersection types, this returns the first path found.
	 */
	private def dispatch List<TypeRef> computePathFromSuperTypeReflexive(IntersectionTypeExpression ref,
		Type rawSuperType, Set<Type> processedTypes) {
		for (typeRef : ref.typeRefs) {
			val path = typeRef.computePathFromSuperTypeReflexive(rawSuperType, processedTypes)
			if (path !== null) {
				return path;
			}
		}
		return null;
	}

	private def void addOrIntersectTypes(RuleEnvironment G, List<TypeRef> commonSuperTypes,
		SuperTypesList<TypeRef> allDeclaredSuperTypes) {
		if (commonSuperTypes.empty) {
			commonSuperTypes.addAll(allDeclaredSuperTypes)
		} else {

			// extract all functions, they have to be handled differently:
			// there must be only one FunctionTypeExprOrRef in the list:
			val FunctionTypeExprOrRef currentSuperFunction = allDeclaredSuperTypes.filter(FunctionTypeExprOrRef).head();
			val FunctionTypeExprOrRef prevCommonSuperFunction = if (currentSuperFunction !== null) {
					commonSuperTypes.filter(FunctionTypeExprOrRef).head()
				} else {
					null // do not search, would be removed anyway
				}

			commonSuperTypes.retainAllTypeRefs(allDeclaredSuperTypes);
			if (getTypeRefComparator.compare(prevCommonSuperFunction, currentSuperFunction) != 0) { // null or retained anyway
				val commonSuperFunction = joinFunctionTypeRefs(G, currentSuperFunction, prevCommonSuperFunction)
				if (commonSuperFunction !== null) {
					commonSuperTypes.add(commonSuperFunction)
				}
			}
		}

	}

	/**
	 * May return null if no join is possible, e.g., in f(string) and f(number)
	 */
	private def FunctionTypeExprOrRef joinFunctionTypeRefs(RuleEnvironment G, FunctionTypeExprOrRef f1,
		FunctionTypeExprOrRef f2) {
		val joinedFunctionTypeExpr = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression;

		if (f1.returnTypeRef !== null && f2.returnTypeRef !== null) {
			joinedFunctionTypeExpr.setReturnTypeRef(
				TypeUtils.copyIfContained(join(G, f1.returnTypeRef, f2.returnTypeRef)));
		}
		joinedFunctionTypeExpr.returnValueMarkedOptional = f1.returnValueOptional || f2.returnValueOptional;

		val maxParSize = Math.max(f1.fpars.size, f2.fpars.size);
		var i = 0;
		var varOrOpt1 = false;
		var varOrOpt2 = false;
		while (i < maxParSize) {
			val par1 = f1.getFParSmartAndFailSafe(i)
			val par2 = f2.getFParSmartAndFailSafe(i)

			var TFormalParameter fpar = null;
			if (par1 === null) {
				fpar = TypeUtils.copy(par2)
			} else if (par2 === null) {
				fpar = TypeUtils.copy(par1)
			} else {
				if (par1.variadicOrOptional) {
					varOrOpt1 = true;
				}
				if (par2.variadicOrOptional) {
					varOrOpt2 = true;
				}

				fpar = TypesFactory.eINSTANCE.createTFormalParameter();
				val meet = meet(G, par1.typeRef, par2.typeRef);

				if (meet === null) {
					if (varOrOpt1 && varOrOpt2) {
						return joinedFunctionTypeExpr; // cut optional or variadic non-matching arguments
					} else {
						return null; // no join of function possible
					}
				}

				val parType = TypeUtils.copyIfContained(meet);
				fpar.setTypeRef(parType);

				if (par1.variadic && par2.variadic) {
					fpar.setVariadic(true);
				} else if (par1.variadicOrOptional && par2.variadicOrOptional) {
					fpar.hasInitializerAssignment=true;
				}
			}
			joinedFunctionTypeExpr.fpars.add(fpar);
			i = i + 1;
		}
		return joinedFunctionTypeExpr
	}

	private def TFormalParameter getFParSmartAndFailSafe(FunctionTypeExprOrRef f, int index) {
		if (f.fpars.size == 0) {
			return null;
		}
		if (index < f.fpars.size) {
			return f.fpars.get(index);
		}
		val last = f.fpars.last;
		if (last.variadic) {
			return last;
		}
		return null;
	}

}
