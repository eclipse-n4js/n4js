/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ts.types.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.TypingStrategy;

/**
 * Utility methods related to the type models defined in Types.xcore and TypeRefs.xcore.
 */
public class TypeModelUtils {

	/**
	 * Tells if the given URI points to a {@link TModule#getComposedMemberCaches() cached composed member} in the
	 * TModule of an N4JS resource.
	 */
	public static boolean isComposedMemberURI(URI uri) {
		return isComposedMemberURIFragment(uri.fragment());
	}

	/**
	 * Tells if the given URI fragment points to a {@link TModule#getComposedMemberCaches() cached composed member} in
	 * the TModule of an N4JS resource.
	 */
	public static boolean isComposedMemberURIFragment(String uriFragment) {
		return uriFragment.startsWith(
				"/1/@" + TypesPackage.eINSTANCE.getTModule_ComposedMemberCaches().getName() + ".");
	}

	/**
	 * Tells if the given URI fragment points to a {@link TModule#getComposedMemberCaches() cached composed member} or
	 * {@link TModule#getExposedInternalTypes() cache} in the TModule of an N4JS resource.
	 */
	public static boolean isURIFragmentToPostProcessingCache(String uriFragment) {
		return uriFragment.startsWith(
				"/1/@" + TypesPackage.eINSTANCE.getTModule_ComposedMemberCaches().getName() + ".") ||
				uriFragment.startsWith(
						"/1/@" + TypesPackage.eINSTANCE.getTModule_ExposedInternalTypes().getName() + ".");
	}

	/**
	 * @return true if the given EObject instance is a composed TMember
	 */
	public static boolean isComposedTElement(EObject eobj) {
		return ((eobj instanceof TMember) && ((TMember) eobj).isComposed());
	}

	/**
	 * @return the single list of that input element if it is not a composed element. Otherwise, return the list of
	 *         constituent members.
	 */
	public static List<EObject> getRealElements(EObject eobj) {
		List<EObject> result = new ArrayList<>();
		if (isComposedTElement(eobj)) {
			List<TMember> constituentMembers = ((TMember) eobj).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				result.add(constituentMember);
			}
		} else {
			result.add(eobj);
		}
		return result;
	}

	/** Tells whether the given type reference has a typing strategy that denotes structural typing. */
	public static boolean isStructural(TypeRef typeRef) {
		return typeRef != null && isStructural(typeRef.getTypingStrategy());
	}

	/**
	 * Tells whether the given typing strategy denotes structural typing.
	 */
	public static boolean isStructural(TypingStrategy typingStrategy) {
		return typingStrategy != null && typingStrategy != TypingStrategy.NOMINAL
				&& typingStrategy != TypingStrategy.DEFAULT;
	}

	/**
	 * Returns all type arguments: those given in this type reference as well as default arguments of <em>optional</em>
	 * type parameters for which the argument is omitted.
	 * <p>
	 * If a type argument is missing in this type reference for a <em>mandatory</em> type parameter (i.e. this type
	 * reference is a {@link ParameterizedTypeRef#isRaw() raw type reference}) then nothing will be added and only the
	 * type arguments actually given in this type reference will be returned (i.e. same return value as
	 * {@link ParameterizedTypeRef#getDeclaredTypeArgs() #getDeclaredTypeArgs()}). This is to ensure consistent behavior
	 * of this method and {@code #getDeclaredTypeArgs()} with respect to raw type references.
	 *
	 * @see TypeModelUtils#toString()
	 */
	public static EList<TypeArgument> getTypeArgsWithDefaults(Type declType, EList<TypeArgument> declTypeArgs) {
		if (declType != null && declType.isGeneric()) {
			int declTypeArgsCount = declTypeArgs.size();
			EList<TypeVariable> typeParams = declType.getTypeVars();
			int typeParamCount = typeParams.size();
			if (typeParamCount > declTypeArgsCount) {
				TypeArgument[] args = new TypeArgument[typeParamCount];
				for (var i = 0; i < typeParamCount; i++) {
					if (i < declTypeArgsCount) {
						args[i] = declTypeArgs.get(i);
					} else {
						// will be 'null' if type parameter #i isn't optional
						TypeRef defArg = typeParams.get(i).getDefaultArgument();

						if (defArg == null) {
							// no type argument given in declTypeArgs for non-optional type parameter #i
							// --> we have a raw type reference and want this method to behave consistently
							// with #getDeclaredTypeArgs() (see above)
							return declTypeArgs;
						}
						args[i] = defArg;
					}
				}
				return XcoreCollectionLiterals.<TypeArgument> newImmutableEList(args);
			}
		}
		return declTypeArgs;
	}

	/** Convenient method for {@link #getTypeArgsWithDefaults(Type, EList)} */
	public static EList<TypeArgument> getTypeArgsWithDefaults(FunctionTypeExprOrRef signatureTypeRef,
			List<? extends TypeArgument> declTypeArgs) {

		EList<TypeArgument> elist = XcoreCollectionLiterals.<TypeArgument> newBasicEList();
		elist.addAll(declTypeArgs);
		return getTypeArgsWithDefaults(signatureTypeRef.getDeclaredType(), elist);
	}
}
