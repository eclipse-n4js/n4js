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

import java.util.Stack;
import java.util.stream.Collectors;

import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.utils.RecursionGuard;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

/**
 * Validation for {@link N4TypeAliasDeclaration}s.
 */
public class N4JSTypeAliasValidator extends AbstractN4JSDeclarativeValidator {

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
		Stack<TypeAlias> cycle = collectTypeAliasCycle(n4TypeAliasDecl.getDefinedTypeAsTypeAlias());
		if (cycle == null) {
			return; // no cycle
		}
		String cycleStr = cycle.stream().map(TypeAlias::getName).collect(Collectors.joining(" -> "));
		addIssue(IssueCodes.getMessageForALI_CYCLIC_TYPE_ALIAS(cycleStr),
				n4TypeAliasDecl, N4JSPackage.Literals.N4_TYPE_ALIAS_DECLARATION__ACTUAL_TYPE_REF,
				IssueCodes.ALI_CYCLIC_TYPE_ALIAS);
	}

	private Stack<TypeAlias> collectTypeAliasCycle(TypeAlias typeAlias) {
		RecursionGuard<TypeAlias> guard = null;
		TypeAlias currAlias = typeAlias;
		do {
			if (currAlias == null) {
				return null; // broken AST
			}
			final TypeRef currActualTypeRef = currAlias.getActualTypeRef();
			if (currActualTypeRef == null) {
				return null; // broken AST
			}
			if (!currActualTypeRef.isAliasUnresolved()) {
				return null;
			}
			if (guard == null) {
				guard = new RecursionGuard<>();
			}
			currAlias = (TypeAlias) currActualTypeRef.getDeclaredType();
		} while (guard.tryNext(currAlias));
		return guard.getElements();
	}

	/** Disallow use of type aliases as values (for now). */
	@Check
	public void checkAliasAsValue(IdentifierRef idRef) {
		IdentifiableElement id = idRef.getId();
		if (id != null && id instanceof Type && ((Type) id).isAlias()) {
			addIssue(IssueCodes.getMessageForALI_TYPE_ALIAS_AS_VALUE(), idRef, IssueCodes.ALI_TYPE_ALIAS_AS_VALUE);
		}
	}
}
