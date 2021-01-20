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
 */
public abstract class NestedTypeRefsSwitch extends TypeRefsSwitch<TypeArgument> {

	protected final RuleEnvironment G;

	public NestedTypeRefsSwitch(RuleEnvironment G) {
		this.G = G;
	}

	// the following two methods are provided to increase readability of recursive invocations of #doSwitch()

	protected abstract NestedTypeRefsSwitch derive(RuleEnvironment G);

	protected TypeRef modify(RuleEnvironment G2, TypeRef typeRef) {
		return (TypeRef) modify(G2, (TypeArgument) typeRef);
	}

	protected TypeArgument modify(RuleEnvironment G2, TypeArgument typeArg) {
		if (G2 == this.G) {
			return doSwitch(typeArg);
		} else {
			return derive(G2).doSwitch(typeArg);
		}
	}

	/** Base case. */
	@Override
	public TypeArgument caseTypeArgument(TypeArgument typeArg) {
		return typeArg;
	}

	@Override
	public TypeArgument caseWildcard(Wildcard wildcard) {
		return caseWildcard_modifyBounds(wildcard);
	}

	protected Wildcard caseWildcard_modifyBounds(Wildcard wildcard) {
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
		final TypeRef ubSubst = ub != null ? caseWildcard_modifyUpperBound(G2, ub) : null;
		final TypeRef lbSubst = lb != null ? caseWildcard_modifyLowerBound(G2, lb) : null;
		if (ubSubst != ub || lbSubst != lb) {
			final Wildcard cpy = TypeUtils.copy(wildcard);
			cpy.setDeclaredUpperBound(TypeUtils.copyIfContained(ubSubst));
			cpy.setDeclaredLowerBound(TypeUtils.copyIfContained(lbSubst));
			wildcard = cpy;
		}
		return wildcard;
	}

	protected TypeRef caseWildcard_modifyUpperBound(RuleEnvironment G2, TypeRef ub) {
		return modify(G2, ub);
	}

	protected TypeRef caseWildcard_modifyLowerBound(RuleEnvironment G2, TypeRef lb) {
		return modify(G2, lb);
	}

	@Override
	public TypeRef caseExistentialTypeRef(ExistentialTypeRef typeRef) {
		final Wildcard w = typeRef.getWildcard();
		if (w != null) {
			final Wildcard wModified = caseWildcard_modifyBounds(w);
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
			final TypeRef boundRefFromEnvUncasted = RuleEnvironmentExtensions.getThisType(G);
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
				final ParameterizedTypeRef actualThisTypeRefNew = caseThisTypeRef_modifyActualTypeRefAfterBinding(
						actualThisTypeRef);
				if (actualThisTypeRefNew != actualThisTypeRef) {
					resultTypeRef.setActualThisTypeRef(actualThisTypeRefNew);
				}

				return resultTypeRef;
			}
		}
		return typeRef;
	}

	protected boolean caseThisTypeRef_shouldBind(ThisTypeRef thisTypeRef) {
		return false;
	}

	protected ParameterizedTypeRef caseThisTypeRef_modifyActualTypeRefAfterBinding(
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
		final ParameterizedTypeRef actualThisTypeRefNew = caseBoundThisTypeRef_modifyActualTypeRef(actualThisTypeRef);
		if (actualThisTypeRefNew != actualThisTypeRef) {
			final BoundThisTypeRef resultTypeRef = TypeUtils.copy(typeRef);
			resultTypeRef.setActualThisTypeRef(actualThisTypeRefNew);
			return resultTypeRef;
		}
		return typeRef;
	}

	protected boolean caseBoundThisTypeRef_shouldRebind(BoundThisTypeRef boundThisTypeRef) {
		return false;
	}

	protected ParameterizedTypeRef caseBoundThisTypeRef_modifyActualTypeRef(ParameterizedTypeRef actualThisTypeRef) {
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
		final List<TypeVariable> resultUnboundTypeVars = new ArrayList<>();
		final List<TypeRef> resultUnboundTypeVarsUpperBounds = new ArrayList<>();
		for (TypeVariable currTV : F.getTypeVars()) {
			if (currTV != null && caseFunctionTypeExprOrRef_isTypeParameterRetained(F, currTV)) {
				resultUnboundTypeVars.add(currTV);
				// handle upper bound of currTV
				final TypeRef declUB = currTV.getDeclaredUpperBound();
				if (declUB != null) {
					final TypeRef oldUB = F.getTypeVarUpperBound(currTV);
					final TypeRef newUB = caseFunctionTypeExprOrRef_modifyTypeParameterUpperBound(oldUB);
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
				? caseFunctionTypeExprOrRef_modifyDeclaredThisType(declaredThisType)
				: null;
		haveReplacement |= resultDeclaredThisType != declaredThisType;

		// handle return type
		final TypeRef returnTypeRef = F.getReturnTypeRef();
		final TypeRef resultReturnTypeRef = returnTypeRef != null
				? caseFunctionTypeExprOrRef_modifyReturnType(returnTypeRef)
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
				final TypeRef newParTypeRef = caseFunctionTypeExprOrRef_modifyParameterType(oldParTypeRef);
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

	protected boolean caseFunctionTypeExprOrRef_isTypeParameterRetained(FunctionTypeExprOrRef F, TypeVariable tv) {
		return true;
	}

	protected TypeRef caseFunctionTypeExprOrRef_modifyTypeParameterUpperBound(TypeRef ub) {
		return modify(G, ub);
	}

	protected TypeRef caseFunctionTypeExprOrRef_modifyDeclaredThisType(TypeRef declThisType) {
		return modify(G, declThisType);
	}

	protected TypeRef caseFunctionTypeExprOrRef_modifyReturnType(TypeRef returnTypeRef) {
		return modify(G, returnTypeRef);
	}

	protected TypeRef caseFunctionTypeExprOrRef_modifyParameterType(TypeRef fparTypeRef) {
		return modify(G, fparTypeRef);
	}

	@Override
	public TypeRef caseComposedTypeRef(ComposedTypeRef typeRef) {
		boolean haveReplacement = false;
		final ArrayList<TypeRef> resultTypeRefs = CollectionLiterals.newArrayList();
		for (TypeRef currTypeRef : typeRef.getTypeRefs()) {
			TypeRef resultTypeRef = caseComposedTypeRef_modifyMemberType(currTypeRef);
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

	protected TypeRef caseComposedTypeRef_modifyMemberType(TypeRef memberTypeRef) {
		return modify(G, memberTypeRef);
	}

	@Override
	public TypeRef caseTypeTypeRef(TypeTypeRef typeTypeRef) {
		TypeArgument typeArg = typeTypeRef.getTypeArg();
		if (typeArg != null) {
			// substitute in type argument
			TypeArgument resultTypeArg = caseTypeTypeRef_modifyTypeArg(typeArg);
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

	protected TypeArgument caseTypeTypeRef_modifyTypeArg(TypeArgument typeArg) {
		return modify(G, typeArg);
	}

	@Override
	public TypeRef caseParameterizedTypeRef(ParameterizedTypeRef typeRef) {
		TypeRef resultTypeRef = caseParameterizedTypeRef_modifyDeclaredType(typeRef);

		final Type resultDeclType = resultTypeRef.getDeclaredType();
		if (resultDeclType != null && resultDeclType.isGeneric()) {
			resultTypeRef = caseParameterizedTypeRef_modifyTypeArguments(resultTypeRef, resultDeclType,
					resultTypeRef != typeRef);
		}

		if (resultTypeRef instanceof StructuralTypeRef) {
			resultTypeRef = caseParameterizedTypeRef_modifyStructuralMembers((StructuralTypeRef) resultTypeRef,
					resultTypeRef != typeRef);
		}

		return resultTypeRef;
	}

	protected TypeRef caseParameterizedTypeRef_modifyDeclaredType(ParameterizedTypeRef typeRef) {
		return typeRef;
	}

	protected TypeRef caseParameterizedTypeRef_modifyTypeArguments(TypeRef typeRef, Type declType,
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
			TypeArgument argChanged = caseParameterizedTypeRef_modifyTypeArgument(G2, param, arg);
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

	protected TypeArgument caseParameterizedTypeRef_modifyTypeArgument(RuleEnvironment G2, TypeVariable typeParam,
			TypeArgument typeArg) {
		return modify(G2, typeArg);
	}

	protected TypeRef caseParameterizedTypeRef_modifyStructuralMembers(StructuralTypeRef typeRef,
			boolean alreadyCopied) {
		return (TypeRef) typeRef;
	}
}
