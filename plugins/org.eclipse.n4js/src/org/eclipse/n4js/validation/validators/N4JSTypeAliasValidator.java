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
package org.eclipse.n4js.validation.validators;

import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;

import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * Validation for {@link N4TypeAliasDeclaration}s.
 */
public class N4JSTypeAliasValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private TypeSystemHelper tsh;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		/* nop */
	}

	/** Disallow cyclic alias declarations. */
	@Check
	public void checkCyclicAliasDeclaration(N4TypeAliasDeclaration n4TypeAliasDecl) {
		Stack<TypeAlias> cycle = collectTypeAliasCycle(n4TypeAliasDecl.getDefinedTypeAsTypeAlias(), null);
		if (cycle == null) {
			return; // no cycle
		}
		String cycleStr = cycle.stream().map(TypeAlias::getName).collect(Collectors.joining(" -> "));
		addIssue(IssueCodes.getMessageForALI_CYCLIC_TYPE_ALIAS(cycleStr),
				n4TypeAliasDecl, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
				IssueCodes.ALI_CYCLIC_TYPE_ALIAS);
	}

	/**
	 * @param guard
	 *            may be <code>null</code> to let the guard be created lazily on demand.
	 * @return <code>null</code> if no cycle was found, otherwise the first cyclic path is returned.
	 */
	private Stack<TypeAlias> collectTypeAliasCycle(TypeAlias typeAlias, RecursionGuard<TypeAlias> guard) {
		if (typeAlias == null) {
			return null; // broken AST
		}
		final TypeRef actualTypeRef = typeAlias.getTypeRef();
		if (actualTypeRef == null) {
			return null; // broken AST
		}
		final Iterable<TypeRef> nextTypeRefs = actualTypeRef instanceof ComposedTypeRef
				? ((ComposedTypeRef) actualTypeRef).getTypeRefs()
				: Collections.singleton(actualTypeRef);
		for (TypeRef nextTypeRef : nextTypeRefs) {
			if (nextTypeRef == null) {
				continue; // broken AST
			}
			if (nextTypeRef.isAliasUnresolved()) {
				final TypeAlias nextAlias = (TypeAlias) nextTypeRef.getDeclaredType();
				if (nextAlias == null) {
					continue; // broken AST
				}
				if (guard == null) {
					guard = new RecursionGuard<>();
				}
				if (guard.tryNext(nextAlias)) {
					try {
						final Stack<TypeAlias> result = collectTypeAliasCycle(nextAlias, guard);
						if (result != null) {
							return result;
						}
					} finally {
						guard.done(nextAlias);
					}
				} else {
					// cycle found!
					return guard.getElements();
				}
			}
		}
		return null;
	}

	/** Disallow use of type aliases as values (for now). */
	@Check
	public void checkAliasAsValue(IdentifierRef idRef) {
		IdentifiableElement id = idRef.getId();
		if (id != null && id instanceof Type && ((Type) id).isAlias()) {
			addIssue(IssueCodes.getMessageForALI_TYPE_ALIAS_AS_VALUE(), idRef, IssueCodes.ALI_TYPE_ALIAS_AS_VALUE);
		}
	}

	/**
	 * Check use of type modifiers, i.e. {@code ~} and {@code +}, on references to type aliases.
	 * <p>
	 * NOTE: only kind of {@link TypeRef} other than {@link ParameterizedTypeRef} that can have those modifiers is
	 * {@link ThisTypeRef}, but those cannot be used as actual type of an alias declaration. Therefore, we only need to
	 * consider {@code ParameterizedTypeRef} here.
	 */
	@Check
	public void checkTypeModifiersOfAlias(ParameterizedTypeRef typeRef) {
		if (!typeRef.isAliasUnresolved()) {
			return; // not a reference to an alias
		}
		final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(typeRef);
		final TypeRef typeRefResolved = tsh.resolveTypeAliasFlat(G, typeRef);
		if (typeRef.isDynamic()) {
			if (!(typeRefResolved instanceof ParameterizedTypeRef)) {
				addIssue(IssueCodes.getMessageForALI_INVALID_MODIFIER("dynamically"),
						typeRef, IssueCodes.ALI_INVALID_MODIFIER);
			} else {
				final Type declType = typeRefResolved.getDeclaredType();
				if (!N4JSLanguageUtils.mayBeReferencedDynamically(declType)) {
					addIssue(IssueCodes.getMessageForTYS_PRIMITIVE_TYPE_DYNAMIC(declType.getName()),
							typeRef, IssueCodes.TYS_PRIMITIVE_TYPE_DYNAMIC);
				}
			}
		}
		if (typeRef.isUseSiteStructuralTyping()) {
			if (!(typeRefResolved instanceof ParameterizedTypeRef)) {
				addIssue(IssueCodes.getMessageForALI_INVALID_MODIFIER("structurally"),
						typeRef, IssueCodes.ALI_INVALID_MODIFIER);
			} else {
				final Type declType = typeRefResolved.getDeclaredType();
				if (!N4JSLanguageUtils.mayBeReferencedStructurally(declType)) {
					addIssue(IssueCodes.getMessageForTYS_STRUCTURAL_PRIMITIVE(),
							typeRef, IssueCodes.TYS_STRUCTURAL_PRIMITIVE);
				}
			}
		}
		if (typeRef.eContainingFeature() == TypeRefsPackage.Literals.TYPE_TYPE_REF__TYPE_ARG) {
			// we here only enforce what is usually enforced by the grammar, as defined in rule TypeArgInTypeTypeRef in
			// file TypeExpressions.xtext:
			boolean isValidTypeArgInTypeTypeRef = typeRefResolved instanceof ParameterizedTypeRef
					&& !typeRefResolved.isUseSiteStructuralTyping()
					&& !typeRefResolved.isDynamic();
			// (note: no need to cover cases ThisTypeRefNominal and WildcardOldNotation from rule TypeArgInTypeTypeRef
			// in TypeExpressions.xtext, because this-types and wildcards cannot appear as actual type of an alias)
			if (!isValidTypeArgInTypeTypeRef) {
				addIssue(IssueCodes.getMessageForALI_INVALID_TYPE_ALIAS_IN_TYPE_TYPE_REF("{}"),
						typeRef, IssueCodes.ALI_INVALID_TYPE_ALIAS_IN_TYPE_TYPE_REF);
			}
		}
	}
}
