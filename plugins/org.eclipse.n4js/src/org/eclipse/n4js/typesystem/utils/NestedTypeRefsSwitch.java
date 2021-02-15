/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem.utils;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.GUARD_TYPE_REFS_NESTED_MODIFICATION_SWITCH__MODIFY_BOUNDS_OF_WILDCARD;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.typeRefs.util.TypeRefsSwitch;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Base implementation of an EMF switch for applying some modification to a type argument/reference <em>including</em>
 * its contained nested type arguments/references.
 * <p>
 * This implementation is based on the following rules that also all subclasses must comply to:
 * <ol>
 * <li>only instances of {@link TypeArgument} are passed in,
 * <li>{@link TypeRef}s are never converted to {@link Wildcard}s, i.e. when passing in a {@code TypeRef} you get back a
 * {@code TypeRef},
 * <li>if a change occurs, a newly created instance is returned that may be used by client code in any way (e.g. adding
 * it to a containment reference without further copying); otherwise, the given instance is returned directly (thus,
 * client code can use an identity check to find out whether a change occurred).
 * </ol>
 */
@SuppressWarnings("javadoc")
public abstract class NestedTypeRefsSwitch extends TypeRefsSwitch<TypeArgument> {

	/** The {@link RuleEnvironment} to be used by this switch. */
	protected final RuleEnvironment G;

	/** Creates a new {@link NestedTypeRefsSwitch}. */
	public NestedTypeRefsSwitch(RuleEnvironment G) {
		this.G = G;
	}

	/**
	 * This overload implements the rule that type references are never converted to {@link Wildcard}s, i.e. when
	 * passing in a {@link TypeRef}, you get a {@code TypeRef} back.
	 */
	public TypeRef doSwitch(TypeRef typeRef) {
		return (TypeRef) super.doSwitch(typeRef);
	}

	/** Main entry method. */
	public TypeArgument doSwitch(TypeArgument eObject) {
		return super.doSwitch(eObject);
	}

	/** @deprecated will throw an exception; use {@link #doSwitch(TypeArgument)} instead. */
	@Override
	@Deprecated
	public TypeArgument doSwitch(EObject eObject) {
		throw new UnsupportedOperationException("this switch is only intended for TypeArguments");
	}

	// the following two methods are provided to increase readability of recursive invocations of #doSwitch()

	/**
	 * Subclasses must implement this to return a newly created switch of the same type as the receiving instance, but
	 * with the given rule environment. If subclasses add other configuration values, those should remain unchanged.
	 */
	protected abstract NestedTypeRefsSwitch derive(RuleEnvironment G_NEW);

	/** Same as {@link #processNested(RuleEnvironment, TypeArgument)}, but for {@link TypeRef}s. */
	protected TypeRef processNested(RuleEnvironment G_NEW, TypeRef typeRef) {
		return (TypeRef) processNested(G_NEW, (TypeArgument) typeRef);
	}

	/**
	 * Invoke {@link #doSwitch(TypeArgument)} for the given type argument, creating a new, temporary switch iff
	 * <code>G_NEW</code> is different from the receiving switch's rule environment.
	 */
	protected TypeArgument processNested(RuleEnvironment G_NEW, TypeArgument typeArg) {
		if (G_NEW == this.G) {
			return doSwitch(typeArg);
		} else {
			final NestedTypeRefsSwitch nestedSwitch = derive(G_NEW);
			return nestedSwitch.doSwitch(typeArg);
		}
	}

	/** Negative base case. */
	@Override
	public TypeArgument defaultCase(EObject object) {
		throw new UnsupportedOperationException("this switch is only intended for TypeArguments");
	}

	/** Positive base case. */
	@Override
	public TypeArgument caseTypeArgument(TypeArgument typeArg) {
		return typeArg;
	}

	@Override
	public TypeArgument caseWildcard(Wildcard wildcard) {
		return caseWildcard_processBounds(wildcard);
	}

	protected Wildcard caseWildcard_processBounds(Wildcard wildcard) {
		final RuleEnvironment G2;
		final TypeRef ub;
		if (wildcard.isImplicitUpperBoundInEffect()) {
			final Pair<String, TypeArgument> guardKey = Pair.of(
					GUARD_TYPE_REFS_NESTED_MODIFICATION_SWITCH__MODIFY_BOUNDS_OF_WILDCARD, wildcard);
			final boolean isGuarded = G.get(guardKey) != null;
			if (!isGuarded) {
				// first time here for 'wildcard'
				ub = wildcard.getDeclaredOrImplicitUpperBound();
				G2 = wrap(G);
				G2.put(guardKey, Boolean.TRUE);
			} else {
				// returned here for the same wildcard!
				ub = null;
				G2 = G;
			}
		} else {
			ub = wildcard.getDeclaredOrImplicitUpperBound();
			G2 = G;
		}
		final TypeRef lb = wildcard.getDeclaredLowerBound();
		final TypeRef ubSubst = ub != null ? caseWildcard_processUpperBound(G2, ub) : null;
		final TypeRef lbSubst = lb != null ? caseWildcard_processLowerBound(G2, lb) : null;
		if (ubSubst != ub || lbSubst != lb) {
			final Wildcard cpy = TypeUtils.copy(wildcard);
			cpy.setDeclaredUpperBound(TypeUtils.copyIfContained(ubSubst));
			cpy.setDeclaredLowerBound(TypeUtils.copyIfContained(lbSubst));
			wildcard = cpy;
		}
		return wildcard;
	}

	protected TypeRef caseWildcard_processUpperBound(RuleEnvironment G2, TypeRef ub) {
		return processNested(G2, ub);
	}

	protected TypeRef caseWildcard_processLowerBound(RuleEnvironment G2, TypeRef lb) {
		return processNested(G2, lb);
	}

	@Override
	public TypeRef caseExistentialTypeRef(ExistentialTypeRef typeRef) {
		final Wildcard w = typeRef.getWildcard();
		if (w != null) {
			final Wildcard wModified = caseWildcard_processBounds(w);
			if (wModified != w) {
				final ExistentialTypeRef typeRefCpy = TypeUtils.copy(typeRef);
				typeRefCpy.setWildcard(TypeUtils.copyIfContained(wModified));
				return typeRefCpy;
			}
		}
		return typeRef;
	}

	@Override
	public TypeRef caseThisTypeRef(ThisTypeRef typeRef) {
		// note: if 'typeRef' is already a BoundThisTypeRef (which is legal), we will "re-bind" it to the new
		// this-binding in the rule environment (if any).

		if (caseThisTypeRef_shouldBind(typeRef)) {
			final TypeRef boundRefFromEnvUncasted = RuleEnvironmentExtensions.getThisBinding(G);
			if (boundRefFromEnvUncasted instanceof BoundThisTypeRef) {
				final BoundThisTypeRef boundRefFromEnv = (BoundThisTypeRef) boundRefFromEnvUncasted;
				final BoundThisTypeRef resultTypeRef = typeRef instanceof ThisTypeRefStructural
						? TypeUtils.createBoundThisTypeRefStructural(boundRefFromEnv.getActualThisTypeRef(),
								(ThisTypeRefStructural) typeRef)
						: TypeUtils.createBoundThisTypeRef(boundRefFromEnv.getActualThisTypeRef());
				// must take use-site typing-strategy from 'thisTypeRef', not the one stored in the environment:
				resultTypeRef.setTypingStrategy(typeRef.getTypingStrategy());
				// must take use-site type modifiers (e.g. optional):
				TypeUtils.copyTypeModifiers(resultTypeRef, typeRef);

				final ParameterizedTypeRef actualThisTypeRef = resultTypeRef.getActualThisTypeRef();
				final ParameterizedTypeRef actualThisTypeRefNew = caseThisTypeRef_processActualTypeRefAfterBinding(
						actualThisTypeRef);
				if (actualThisTypeRefNew != actualThisTypeRef) {
					resultTypeRef.setActualThisTypeRef(actualThisTypeRefNew);
				}

				return resultTypeRef;
			}
		}
		return typeRef;
	}

	protected boolean caseThisTypeRef_shouldBind(@SuppressWarnings("unused") ThisTypeRef thisTypeRef) {
		return false;
	}

	protected ParameterizedTypeRef caseThisTypeRef_processActualTypeRefAfterBinding(
			ParameterizedTypeRef actualThisTypeRef) {
		// cannot delegate back to #modify()/#doSwitch() methods, because they do not guarantee to return a
		// ParameterizedTypeRef -> subclasses must handle this as a special case (if required)
		return actualThisTypeRef;
	}

	@Override
	public TypeRef caseBoundThisTypeRef(BoundThisTypeRef typeRef) {
		if (caseBoundThisTypeRef_shouldRebind(typeRef)) {
			// re-binding of the already bound 'typeRef' was requested
			// -> delegate to #caseThisTypeRef()
			final TypeRef reboundTypeRef = caseThisTypeRef(typeRef);
			if (reboundTypeRef != typeRef) {
				return reboundTypeRef;
			}
			// re-binding did not take place (e.g. no this-binding in rule environment)
			// -> proceed as usual for BoundThisTypeRefs
		}
		final ParameterizedTypeRef actualThisTypeRef = typeRef.getActualThisTypeRef();
		final ParameterizedTypeRef actualThisTypeRefNew = caseBoundThisTypeRef_processActualTypeRef(actualThisTypeRef);
		if (actualThisTypeRefNew != actualThisTypeRef) {
			final BoundThisTypeRef resultTypeRef = TypeUtils.copy(typeRef);
			resultTypeRef.setActualThisTypeRef(actualThisTypeRefNew);
			return resultTypeRef;
		}
		return typeRef;
	}

	protected boolean caseBoundThisTypeRef_shouldRebind(@SuppressWarnings("unused") BoundThisTypeRef boundThisTypeRef) {
		return false;
	}

	protected ParameterizedTypeRef caseBoundThisTypeRef_processActualTypeRef(ParameterizedTypeRef actualThisTypeRef) {
		// cannot delegate back to #modify()/#doSwitch() methods, because they do not guarantee to return a
		// ParameterizedTypeRef -> subclasses must handle this as a special case (if required)
		return actualThisTypeRef;
	}

	// required due to multiple inheritance, to ensure FunctionTypeRef is handled like a FunctionTypeExpression,
	// not as a ParameterizedTypeRef
	@Override
	public TypeRef caseFunctionTypeRef(FunctionTypeRef typeRef) {
		return caseFunctionTypeExprOrRef(typeRef);
	}

	@Override
	public TypeRef caseFunctionTypeExprOrRef(FunctionTypeExprOrRef F) {
		boolean haveReplacement = false;

		// collect type variables in 'F.typeVars' that do not have a type mapping in 'G' and perform substitution on
		// their upper bounds
		/*
		 * IMPLEMENTATION NOTE
		 *
		 * Performing substitution on the upper bound of an unbound(!) type variable is non-trivial, because we aren't
		 * allowed to copy the type variable and change its upper bound (short version: a type variable is a type and
		 * therefore needs to be contained in a Resource; but our new FunctionTypeExpression 'result' is a TypeRef which
		 * may not be contained in any Resource).
		 *
		 * If type variable substitution on <code>currTV</code>'s upper bound leads to a change of that upper bound (and
		 * only then!), the modified upper bound will be stored in property 'unboundTypeVarsUpperBounds' of
		 * <code>result</code>.
		 *
		 * This has to be carefully aligned with {@link FunctionTypeExpression#getUnboundTypeVarsUpperBounds()} and
		 * {@link FunctionTypeExpression#getTypeVarUpperBound(TypeVariable)}.
		 */
		final List<TypeVariable> resultUnboundTypeVars = new ArrayList<>();
		final List<TypeRef> resultUnboundTypeVarsUpperBounds = new ArrayList<>();
		for (TypeVariable currTV : F.getTypeVars()) {
			if (currTV != null && caseFunctionTypeExprOrRef_isTypeParameterRetained(F, currTV)) {
				resultUnboundTypeVars.add(currTV);
				// handle upper bound of currTV
				final TypeRef declUB = currTV.getDeclaredUpperBound();
				if (declUB != null) {
					final TypeRef oldUB = F.getTypeVarUpperBound(currTV);
					final TypeRef newUB = caseFunctionTypeExprOrRef_processTypeParameterUpperBound(oldUB);
					if (newUB != declUB) { // note: identity compare is what we want
						while (resultUnboundTypeVarsUpperBounds.size() < resultUnboundTypeVars.size() - 1) {
							// add an UnknownTypeRef as padding entry
							resultUnboundTypeVarsUpperBounds.add(TypeRefsFactory.eINSTANCE.createUnknownTypeRef());
						}
						resultUnboundTypeVarsUpperBounds.add(newUB);
						haveReplacement = true;
					}
				}
				// no upper bound OR upper bound after substitution is identical to the one stored in type variable:
				// --> no need to copy it over to 'resultUnboundTypeVarsUpperBounds' because operation
				// FunctionTypeExpression#getTypeVarUpperBounds() will use currTV's original upper bound
				// if 'unboundTypeVarsUpperBounds' doesn't contain an upper bound for currTV
			}
		}
		// The following is important for cases such as {function <T> foo():void}:
		// if T is bound, i.e. has a type mapping in 'G', we want to create a new, non-generic
		// FunctionTypeExpression even though there won't be any actual substitution happening inside F.
		haveReplacement |= resultUnboundTypeVars.size() < F.getTypeVars().size();

		// handle declared this type
		final TypeRef declaredThisType = F.getDeclaredThisType();
		final TypeRef resultDeclaredThisType = declaredThisType != null
				? caseFunctionTypeExprOrRef_processDeclaredThisType(declaredThisType)
				: null;
		haveReplacement |= resultDeclaredThisType != declaredThisType;

		// handle return type
		final TypeRef returnTypeRef = F.getReturnTypeRef();
		final TypeRef resultReturnTypeRef = returnTypeRef != null
				? caseFunctionTypeExprOrRef_processReturnType(returnTypeRef)
				: null;
		haveReplacement |= resultReturnTypeRef != returnTypeRef;

		// handle parameter types
		final List<TFormalParameter> fpars = F.getFpars();
		final List<TFormalParameter> resultFpars = new ArrayList<>(fpars.size());
		for (TFormalParameter oldPar : fpars) {
			if (oldPar == null) {
				resultFpars.add(null);
				continue;
			}
			final TFormalParameter newPar = TypesFactory.eINSTANCE.createTFormalParameter();
			newPar.setName(oldPar.getName());
			newPar.setVariadic(oldPar.isVariadic());
			newPar.setHasInitializerAssignment(oldPar.isHasInitializerAssignment());
			// note: property 'astInitializer' is not copied since it's part of the AST

			final TypeRef oldParTypeRef = oldPar.getTypeRef();
			if (oldParTypeRef != null) {
				final TypeRef newParTypeRef = caseFunctionTypeExprOrRef_processParameterType(oldParTypeRef);
				newPar.setTypeRef(TypeUtils.copyIfContained(newParTypeRef));
				haveReplacement |= newParTypeRef != oldParTypeRef;
			}

			resultFpars.add(newPar);
		}

		if (haveReplacement) {
			final FunctionTypeExpression result = TypeRefsFactory.eINSTANCE.createFunctionTypeExpression();

			// let posterity know that the newly created FunctionTypeExpression
			// represents the binding of another FunctionTypeExprOrRef
			result.setBinding(true);

			// if the original 'typeRef' was a FunctionTypeRef, then retain the
			// pointer to its declared type in the copied FunctionTypeExpression
			// (see API doc of FunctionTypeExpression#declaredType for more info)
			result.setDeclaredType(F.getFunctionType());

			result.getUnboundTypeVars().addAll(resultUnboundTypeVars);
			result.getUnboundTypeVarsUpperBounds().addAll(
					TypeUtils.copyAllIfContained(resultUnboundTypeVarsUpperBounds));

			if (resultDeclaredThisType != null) {
				result.setDeclaredThisType(TypeUtils.copyIfContained(resultDeclaredThisType));
			}

			result.setReturnTypeRef(TypeUtils.copyIfContained(resultReturnTypeRef));
			result.setReturnValueMarkedOptional(F.isReturnValueOptional());

			result.getFpars().addAll(resultFpars); // no need to copy; all fpars were newly created above!

			TypeUtils.copyTypeModifiers(result, F);

			return result;
		}
		return F;
	}

	protected boolean caseFunctionTypeExprOrRef_isTypeParameterRetained(
			@SuppressWarnings("unused") FunctionTypeExprOrRef F,
			@SuppressWarnings("unused") TypeVariable tv) {
		return true;
	}

	protected TypeRef caseFunctionTypeExprOrRef_processTypeParameterUpperBound(TypeRef ub) {
		return processNested(G, ub);
	}

	protected TypeRef caseFunctionTypeExprOrRef_processDeclaredThisType(TypeRef declThisType) {
		return processNested(G, declThisType);
	}

	protected TypeRef caseFunctionTypeExprOrRef_processReturnType(TypeRef returnTypeRef) {
		return processNested(G, returnTypeRef);
	}

	protected TypeRef caseFunctionTypeExprOrRef_processParameterType(TypeRef fparTypeRef) {
		return processNested(G, fparTypeRef);
	}

	@Override
	public TypeRef caseComposedTypeRef(ComposedTypeRef typeRef) {
		boolean haveReplacement = false;
		final ArrayList<TypeRef> resultTypeRefs = CollectionLiterals.newArrayList();
		for (TypeRef currTypeRef : typeRef.getTypeRefs()) {
			TypeRef resultTypeRef = caseComposedTypeRef_processMemberType(currTypeRef);
			resultTypeRefs.add(resultTypeRef);
			haveReplacement |= resultTypeRef != currTypeRef;
		}
		if (haveReplacement) {
			ComposedTypeRef result = TypeUtils.copyPartial(typeRef,
					TypeRefsPackage.Literals.COMPOSED_TYPE_REF__TYPE_REFS);
			result.getTypeRefs().clear();
			result.getTypeRefs().addAll(TypeUtils.copyAll(resultTypeRefs));
			return result;
		}
		return typeRef;
	}

	protected TypeRef caseComposedTypeRef_processMemberType(TypeRef memberTypeRef) {
		return processNested(G, memberTypeRef);
	}

	@Override
	public TypeRef caseTypeTypeRef(TypeTypeRef typeTypeRef) {
		TypeArgument typeArg = typeTypeRef.getTypeArg();
		if (typeArg != null) {
			// substitute in type argument
			TypeArgument resultTypeArg = caseTypeTypeRef_processTypeArg(typeArg);
			if (resultTypeArg != typeArg) {
				// changed
				TypeTypeRef result = TypeUtils.copyPartial(typeTypeRef,
						TypeRefsPackage.Literals.TYPE_TYPE_REF__TYPE_ARG);
				result.setTypeArg(resultTypeArg);
				return result;
			}
		}
		// nothing changed
		return typeTypeRef;
	}

	protected TypeArgument caseTypeTypeRef_processTypeArg(TypeArgument typeArg) {
		return processNested(G, typeArg);
	}

	@Override
	public TypeRef caseParameterizedTypeRef(ParameterizedTypeRef typeRef) {
		TypeRef resultTypeRef = caseParameterizedTypeRef_processDeclaredType(typeRef);

		final Type resultDeclType = resultTypeRef.getDeclaredType();
		if (resultDeclType != null && resultDeclType.isGeneric()) {
			resultTypeRef = caseParameterizedTypeRef_processTypeArguments(resultTypeRef, resultDeclType,
					resultTypeRef != typeRef);
		}

		if (resultTypeRef instanceof StructuralTypeRef) {
			resultTypeRef = caseParameterizedTypeRef_processStructuralMembers((StructuralTypeRef) resultTypeRef,
					resultTypeRef != typeRef);
		}

		return resultTypeRef;
	}

	protected TypeRef caseParameterizedTypeRef_processDeclaredType(ParameterizedTypeRef typeRef) {
		return typeRef;
	}

	protected TypeRef caseParameterizedTypeRef_processTypeArguments(TypeRef typeRef, Type declType,
			boolean alreadyCopied) {

		final List<TypeVariable> typeParams = declType.getTypeVars();
		final List<TypeArgument> typeArgs = typeRef.getTypeArgs();

		final int lenParams = typeParams.size();
		final int lenArgs = typeArgs.size();

		// (a) without changing 'typeRef', perform modifications on all type arguments and remember if they changed
		final RuleEnvironment G2 = RuleEnvironmentExtensions.wrap(G);
		final TypeArgument[] argsChanged = new TypeArgument[lenArgs];
		boolean haveChange = false;
		for (int i = 0; i < lenArgs; i++) {
			final TypeVariable param = i < lenParams ? typeParams.get(i) : null;
			final TypeArgument arg = typeArgs.get(i);
			TypeArgument argChanged = caseParameterizedTypeRef_processTypeArgument(G2, param, arg);
			if (argChanged != arg) {
				// n.b.: will only add to argsChanged if changed! (otherwise argsChanged[i] will remain null)
				argsChanged[i] = argChanged;
				haveChange = true;
			}
		}

		// (b) update 'typeRef' with changed type arguments iff(!) one or more have changed
		if (haveChange) {
			if (!alreadyCopied) {
				typeRef = TypeUtils.copy(typeRef);
			}
			for (int i = 0; i < lenArgs; i++) {
				final TypeArgument argCh = argsChanged[i];
				if (argCh != null) {
					typeRef.getTypeArgs().set(i, argCh);
				}
			}
		}

		return typeRef;
	}

	protected TypeArgument caseParameterizedTypeRef_processTypeArgument(RuleEnvironment G2,
			@SuppressWarnings("unused") TypeVariable typeParam, TypeArgument typeArg) {
		return processNested(G2, typeArg);
	}

	protected TypeRef caseParameterizedTypeRef_processStructuralMembers(StructuralTypeRef typeRef,
			@SuppressWarnings("unused") boolean alreadyCopied) {
		return (TypeRef) typeRef;
	}
}
