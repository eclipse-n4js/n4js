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
import java.util.Collection
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.postprocessing.ASTMetaInfoUtils
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeVariableMapping
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.utils.TypeCompareHelper
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.utils.RecursionGuard
import org.eclipse.n4js.typesystem.utils.RuleEnvironment

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Type System Helper Strategy for managing type variable mappings in RuleEnvironments of XSemantics.
 * Note that low-level method for adding type mappings to a rule environment are contained in
 * {@link RuleEnvironmentExtensions}.
 */
class GenericsComputer extends TypeSystemHelperStrategy {

	@Inject
	private N4JSTypeSystem ts

	@Inject
	extension TypeCompareHelper;


	/**
	 * Given a type reference to a generic type G where type variables are already bound, e.g.,
	 * G&lt;A,B>, this method adds to the given rule environment the mappings for the type variables
	 * of the declared type G using the type arguments of the passed type reference. Such mappings
	 * will be used later to bind type variables.
	 * <p>
	 * For instance, given a type reference G&lt;A,B> to a class
	 * <pre>
	 * interface I&lt;V> {
	 *     // ...
	 * }
	 * class G&lt;T,U> implements I&lt;C> {
	 *     // ...
	 * }
	 * </pre>
	 * this function will add the mappings T -> A, U -> B, V -> C.
	 */
	def void addSubstitutions(RuleEnvironment G, TypeRef typeRef) {
		val declType = typeRef.declaredType;
		if(typeRef instanceof BoundThisTypeRef) {
			addSubstitutions(G,typeRef.actualThisTypeRef);
		}
		else if(declType instanceof TypeVariable) {
			val currBound = declType.declaredUpperBound;
			if(currBound!==null) {
				addSubstitutions(G,currBound);
			}
		}
		else if(declType instanceof TClassifier) {
			for(currBaseType : collectSuperTypeRefs(typeRef)) // note: despite its name, collectSuperTypeRefs will also return typeRef itself!!
				primAddSubstitutions(G,currBaseType);
		}
		else {
			primAddSubstitutions(G,typeRef);
		}
	}
	/**
	 * Adds substitutions for an individual type reference (i.e. without taking
	 * into account inheritance hierarchies).
	 */
	private def void primAddSubstitutions(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof ParameterizedTypeRef) {
			if (! typeRef.typeArgs.empty) {
				var gen = typeRef.declaredType
				if (gen instanceof ContainerType<?>) {
					var varIter = gen.typeVars.iterator
					for (typeArg : typeRef.typeArgs) {
						if (varIter.hasNext) {
							var TypeVariable typeVar = varIter.next;
							addSubstitution(G,typeVar,typeArg);
						}
					}
				}
			}
			if(typeRef instanceof StructuralTypeRef) {
				G.restorePostponedSubstitutionsFrom(typeRef)
			}
		}
	}
	/**
	 * Adds an individual substitution.
	 * TODO proper handling of type variables with multiple solutions (union/intersection on TypeRef level, not on variable level! or: disallow entirely)
	 */
	private def addSubstitution(RuleEnvironment G, TypeVariable typeVar, TypeArgument typeArg) {

		// resolve typeArg
		var Object actualTypeArg = typeArg;
		while(G.hasSubstitutionFor(actualTypeArg)) {
			val actualTypeArgCasted = actualTypeArg as TypeRef; // otherwise #hasSubstitutionFor() would not have returned true
			val fromEnv = G.environment.get(actualTypeArgCasted.declaredType);
			actualTypeArg = if(fromEnv instanceof TypeRef) {
				TypeUtils.mergeTypeModifiers(fromEnv, actualTypeArgCasted)
			} else {
				fromEnv
			};
		}

		// resolve wildcards
		if(actualTypeArg instanceof Wildcard)
			actualTypeArg = TypeUtils.captureWildcard(typeVar, actualTypeArg);

		// (note: actualTypeArg must be a TypeRef now, because Wildcard was the only other option below TypeArgument)

		// combine actualTypeArg with current substitution target (if required)
		var currSubstitute = G.get(typeVar);

		// ignore reflexive
		if(currSubstitute===typeVar)
			currSubstitute=null;

		G.put(typeVar, mergeTypeArgs(currSubstitute,actualTypeArg));
	}
	private def mergeTypeArgs(Object... typeArgs) {
		val result = newArrayList

		for(currTypeArg : typeArgs) {
			val l = if(currTypeArg instanceof Collection<?>) currTypeArg else #[currTypeArg];
			for(Object currO : l) {
				if(currO!==null && !result.typeRefAwareContains(currO))
					result.add(currO);
			}
		}

		if(result.size >= 2)
			result
		else if(result.size === 1)
			result.get(0)
		else
			null
	}
	private def typeRefAwareContains(Collection<?> l, Object o) {
		if(o instanceof TypeRef) {
			for(Object currO : l)
				if(currO instanceof TypeRef)
					if(compare(currO,o)===0)
						return true;
			return false;
		}
		else
			return l.contains(o);
	}
	// note: this method is redundant with AllSuperTypeRefsCollector, but we cannot use it
	// here, because in case of diamond hierarchies (i.e. one type appears more than once)
	// we rely on (1) typeRefs appearing more than once in the returned list and (2) a
	// particular order (this situation seems not ideal and should maybe be refactored)
	private def collectSuperTypeRefs(TypeRef typeRef) {
		val result = newArrayList
		primCollectSuperTypeRefs(typeRef,result,new RecursionGuard<TypeRef>());
		return result;
	}
	private def void primCollectSuperTypeRefs(TypeRef typeRef, List<? super TypeRef> addHere, RecursionGuard<TypeRef> guard) {
		if(guard.tryNext(typeRef)) {
			addHere.add(typeRef);
			for(currSuperTypeRef : typeRef?.declaredType.allSuperTypes)
				primCollectSuperTypeRefs(currSuperTypeRef,addHere,guard);
			guard.done(typeRef);
		}
	}
	private def getAllSuperTypes(Type type) {
		switch(type) {
			TClass:		(if(type.superClassRef!==null) #[type.superClassRef] else #[]) + type.implementedInterfaceRefs
			TInterface:	type.superInterfaceRefs
			default:	#[]
		}
	}


	/**
	 * Add type variable substitutions for the given property access expression. If the property expression is
	 * parameterized, the explicitly provided type arguments will be used, otherwise this method will do nothing.
	 *
	 * @param G
	 * @param paExpr
	 */
	def void addSubstitutions(RuleEnvironment G, ParameterizedPropertyAccessExpression paExpr) {
		if (paExpr.parameterized) {
			val prop = paExpr.property;
			if(prop instanceof Type)
				G.addTypeMappings(prop.typeVars, paExpr.typeArgs);
		}
	}


	/**
	 * Add type variable substitutions for the given call expression. If the call expression is parameterized,
	 * the explicitly provided type arguments will be used, otherwise this method tries to infer type arguments
	 * from types of function/method arguments and the expected return type.
	 *
	 * @param G
	 * @param callExpr
	 * @param targetTypeRef  the inferred type of <code>callExpr.target</code> or <code>null</code> to let
	 *                       this method do the inference; only purpose of this argument is to avoid an
	 *                       unnecessary 2nd type inference if caller has already performed this.
	 */
	def void addSubstitutions(RuleEnvironment G, ParameterizedCallExpression callExpr, TypeRef targetTypeRef) {
		if(G===null || callExpr===null)
			return;

		val TypeRef actualTargetTypeRef =
				if(targetTypeRef!==null)
					targetTypeRef
				else
					ts.type(new RuleEnvironment(G),callExpr.target).value;

		if(!(actualTargetTypeRef instanceof FunctionTypeExprOrRef))
			return;
		val FunctionTypeExprOrRef F = actualTargetTypeRef as FunctionTypeExprOrRef;

		// restore type mappings from postponed substitutions
		// TODO what if the structural type ref is contained in another typeRef (e.g. in a ComposedTypeRef)???
		// TODO is there a better place to do this???
		if(F.returnTypeRef instanceof StructuralTypeRef)
			G.restorePostponedSubstitutionsFrom(F.returnTypeRef as StructuralTypeRef)
		for(currFpar : F.fpars) {
			if(currFpar.typeRef instanceof StructuralTypeRef)
				G.restorePostponedSubstitutionsFrom(currFpar.typeRef as StructuralTypeRef)
		}

		if(F.generic) {
			val typeArgs = if(callExpr.parameterized) {
				callExpr.typeArgs
			} else {
				ASTMetaInfoUtils.getInferredTypeArgs(callExpr) ?: #[]
			};
			G.addTypeMappings(F.typeVars, typeArgs);
		}
	}




	/**
	 * Helper method for Xsemantics substTypeVariables judgment. Will either directly substitute in a copy of 'typeRef'
	 * or postpone substitution. The given 'typeRef' is never changed.
	 */
	def TypeRef substTypeVariablesInStructuralMembers(RuleEnvironment G, StructuralTypeRef typeRef) {
		if(typeRef.structuralMembers.empty && typeRef.postponedSubstitutions.empty) {
			// nothing to store/postpone -> return unchanged
			return typeRef as TypeRef;
		}

		val result = TypeUtils.copy(typeRef);
		if(result.genStructuralMembers.size === result.structuralMembers.size) {
			// CASE 1: all structural members contained in the type reference
			// -> directly substitute within the members in 'result'
			// (rationale: we just had to create a copy of all members anyway; no point in postponing substitutions)

			result.genStructuralMembers.forEach[member|
				val l = newArrayList; // create list up-front to not confuse tree iterator when replacing nodes!
				val iter = member.eAllContents;
				while(iter.hasNext) {
					val obj = iter.next;
					if(obj instanceof TypeArgument) {
						l.add(obj);
						iter.prune();
					}
				}
				l.forEach[ta|
					val taSubst = ts.substTypeVariables(G, ta).value;
					if(taSubst!==null && taSubst!==ta) {
						EcoreUtil.replace(ta, taSubst);
					}
				];
			];

		} else {
			// CASE 2: the structural members reside in the AST or in a TStructuralType in the TModule
			// -> postpone substitutions
			// (rationale: we cannot change the AST nor the TModule and we want to avoid copying the members to
			// genStructuralMembers of 'result' for performance reasons (memory))

			storePostponedSubstitutionsIn(G, result);
		}
		return result as TypeRef;
	}

	/**
	 * For all type variables used in the structural members of 'typeRef', this method stores the type
	 * variable mappings defined in G to the given 'typeRef' (stored in property 'postponedSubstitutions').
	 * This allows to defer actual substitution in the structural members until a later point in time.
	 * <p>
	 * For more details, see the API documentation for property 'postponedSubstitutions' of
	 * {@link StructuralTypeRef}.
	 *
	 * @see #restorePostponedSubstitutionsFrom(RuleEnvironment,StructuralTypeRef)
	 */
	def void storePostponedSubstitutionsIn(RuleEnvironment G, StructuralTypeRef typeRef) {
		// collect all type variables used in the structural members of typeRef
		val Set<TypeVariable> typeVarsInMembers = TypeUtils.getTypeVarsInStructMembers(typeRef);
		// remove all type variables for which a postponed substitution exists (i.e. don't overwrite existing mappings!)
		// (rationale: we have to treat 'typeRef' as if the postponed substitutions were already applied; but then
		// those type variables would no longer be in the structural members of typeRef (they would have been replaced))
		// Example: 'typeRef' is ~Object with { T1 prop1; T2 prop2; } [[T1->A]] and 'G' contains T1->X, T2->X
		typeVarsInMembers.removeIf[currVar | typeRef.hasPostponedSubstitutionFor(currVar)];
		// add new mappings for these type variables
		val List<TypeVariableMapping> bindings = newArrayList;
		for (currVar : typeVarsInMembers) {
			val currArg = G.get(currVar);
			if (currArg instanceof TypeArgument) {
				val currArgCpy = TypeUtils.copy(currArg); // always copy!
				// important: store substitutions in argument!
				if (currArgCpy instanceof StructuralTypeRef)
					G.storePostponedSubstitutionsIn(currArgCpy);
				// create & add binding
				bindings += TypeUtils.createTypeVariableMapping(currVar, currArgCpy);
			}
		}
		typeRef.postponedSubstitutions += bindings;
		// apply substitutions to target values of mappings
		// Esp. important if 'typeRef' already contained postponed substitutions when this method was invoked.
		// Example: 'typeRef' is ~Object with { T1 prop1; T2 prop2; } [[T1->A, T2->α]] and 'G' contains α->B
		for (tvmapping : typeRef.postponedSubstitutions) {
			val typeArgSubst = ts.substTypeVariables(G, tvmapping.typeArg).value;
			if(typeArgSubst!==null && typeArgSubst!==tvmapping.typeArg) {
				tvmapping.typeArg = typeArgSubst;
			}
		}
	}

	/**
	 * Restores type variable mappings from the given structural type reference 'typeRef' to rule
	 * environment 'G'. Assumes that method {@link #storePostponedSubstitutionsIn(RuleEnvironment,StructuralTypeRef)}
	 * has been invoked on the given 'typeRef' before.
	 */
	def void restorePostponedSubstitutionsFrom(RuleEnvironment G, StructuralTypeRef typeRef) {
		typeRef.postponedSubstitutions.forEach[currMapping|
			G.put(currMapping.typeVar, TypeUtils.copy(currMapping.typeArg));
		]
	}





	// NOTE: this method is only used by JoinComputer; since JoinComputer and MeetComputer have not been
	//       used and therefore not been updated for a long time, probably also this method is out-dated!
	def package bindTypeVariables(RuleEnvironment G, TypeRef typeRef) {
		switch typeRef {
			FunctionTypeRef:
				return typeRef
			ParameterizedTypeRef:
				if (typeRef.declaredType instanceof TypeVariable) {
					val boundType = G.get(typeRef.declaredType) as TypeRef;
					return boundType
				} else if (typeRef.declaredType.generic) {
					val ptr = TypeUtils.copy(typeRef);

					var int i = 0;
					while (i < typeRef.declaredType.typeVars.size) {
						val typeVar = typeRef.declaredType.typeVars.get(i)
						val boundType = G.get(typeVar) as TypeRef;
						if (boundType !== null) {
							ptr.typeArgs.set(i, TypeUtils.copy(boundType))
						}
						i = i + 1;
					}
					return ptr
				} else {
					typeRef
				}
			default:
				return typeRef
		}
	}
}
