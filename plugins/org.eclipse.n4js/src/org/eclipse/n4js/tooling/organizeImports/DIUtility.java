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
package org.eclipse.n4js.tooling.organizeImports;

import static org.eclipse.n4js.AnnotationDefinition.BINDER;
import static org.eclipse.n4js.AnnotationDefinition.GENERATE_INJECTOR;
import static org.eclipse.n4js.AnnotationDefinition.INJECT;
import static org.eclipse.n4js.AnnotationDefinition.USE_BINDER;
import static org.eclipse.n4js.AnnotationDefinition.WITH_PARENT_INJECTOR;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4ProviderType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationArgument;
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.typesystem.utils.AllSuperTypesCollector;
import org.eclipse.n4js.utils.DeclMergingHelper;

/**
 * Collection of utility methods for working with N4JS DI.
 */
public class DIUtility {

	/***/
	public static boolean isSingleton(Type type) {
		return exists(type.getAnnotations(), a -> AnnotationDefinition.SINGLETON.name.equals(a.getName()));
	}

	/***/
	public static boolean hasSuperType(Type type) {
		if (type instanceof TClass) {
			TClass tc = (TClass) type;
			if (tc.getSuperClassRef() != null) {
				return tc.getSuperClassRef().getDeclaredType() instanceof TClass;
			}
		}
		return false;
	}

	/**
	 * Returns true if provided type is instanceof {@link TClass} and at least owned member is annotated with
	 * {@link AnnotationDefinition#INJECT}.
	 */
	public static boolean hasInjectedMembers(Type type, DeclMergingHelper declMergingHelper) {
		if (type instanceof TClass) {
			return exists(AllSuperTypesCollector.collect((TClass) type, declMergingHelper),
					t -> exists(t.getOwnedMembers(), m -> INJECT.hasAnnotation(m)));
		}
		return false;
	}

	/**
	 * Generate DI meta info for classes that have injected members, or are can be injected and have DI relevant
	 * information, e.g. scope annotation. Also if type has a super type (injection of inherited members)
	 */
	public static boolean isInjectedClass(Type type, DeclMergingHelper declMergingHelper) {
		return isSingleton(type) || hasInjectedMembers(type, declMergingHelper) || hasSuperType(type);
	}

	/***/
	public static boolean isBinder(Type type) {
		return BINDER.hasAnnotation(type);
	}

	/***/
	public static boolean isDIComponent(Type type) {
		return GENERATE_INJECTOR.hasAnnotation(type);
	}

	/**
	 * Checks if diComponent has parent component, that is one specified by the superclass or by the inheritance.
	 */
	public static boolean hasParentInjector(Type type) {
		if (WITH_PARENT_INJECTOR.hasAnnotation(type)) {
			return true;
		}

		if (type instanceof TClass) {
			return ((TClass) type).getSuperClassRef() != null;
		} else
			return false;
	}

	/**
	 * @returns {@link Type} of the parent DIComponent. Throws {@link RuntimeException} if no parent on provided type.
	 */
	public static Type findParentDIC(Type type) {
		TypeRef parent = null;
		if (WITH_PARENT_INJECTOR.hasAnnotation(type)) {
			TAnnotation ann = WITH_PARENT_INJECTOR.getOwnedAnnotation(type);
			if (ann != null) {
				parent = ((TAnnotationTypeRefArgument) ann.getArgs().get(0)).getTypeRef();
			}

		} else if (type instanceof TClass) {
			parent = ((TClass) type).getSuperClassRef();
		}

		if (parent != null) {
			return parent.getDeclaredType();
		}

		throw new RuntimeException("no parent on " + type.getName());
	}

	/**
	 * returns list of types that are parameters of {@link AnnotationDefinition#USE_BINDER} annotations attached to a
	 * given type or empty list
	 */
	public static List<Type> resolveBinders(Type type) {
		List<Type> argTypes = new ArrayList<>();
		for (TAnnotation ann : USE_BINDER.getAllOwnedAnnotations(type)) {
			for (TAnnotationArgument annArg : ann.getArgs()) {
				Type argType = ((TAnnotationTypeRefArgument) annArg).getTypeRef().getDeclaredType();
				if (argType != null) {
					argTypes.add(argType);
				}
			}
		}

		return argTypes;
	}

	/**
	 * Returns with {@code true} if one or more members of the given type are annotated with {@code @Inject} annotation.
	 */
	public static boolean requiresInjection(Type type, DeclMergingHelper declMergingHelper) {
		if (type instanceof TClass) {
			return exists(AllSuperTypesCollector.collect((TClass) type, declMergingHelper),
					tc -> exists(tc.getOwnedMembers(), m -> INJECT.hasAnnotation(m)));
		}
		return false;
	}

	/**
	 * Returns with {@code true} if the type reference argument requires injection. Either the declared type requires
	 * injection, or the type reference represents an N4 provider, and the dependency of the provider requires
	 * injection. Otherwise returns with {@code false}.
	 */
	public static boolean requiresInjection(TypeRef tr, DeclMergingHelper declMergingHelper) {
		return requiresInjection(tr.getDeclaredType(), declMergingHelper)
				|| isProviderType(tr) && requiresInjection(getProvidedType(tr), declMergingHelper);
	}

	/**
	 * Returns with {@code true} if the type reference argument is an N4 provider. Otherwise returns with {@code false}.
	 * Also returns with {@code false} if the type reference is sub interface of N4 provider or a class which implements
	 * the N4 provider interface.
	 */
	public static boolean isProviderType(TypeRef tr) {
		return null != tr && tr.getDeclaredType() == n4ProviderType(newRuleEnvironment(tr));
	}

	/**
	 * Returns with the type most nested dependency if the type reference argument represents and N4 provider. Otherwise
	 * returns with {@code null}.
	 */
	public static Type getProvidedType(TypeRef tr) {
		if (!isProviderType(tr)) {
			return null;
		}
		TypeRef nestedTypeRef = tr;
		while (isProviderType(nestedTypeRef) && nestedTypeRef instanceof ParameterizedTypeRef) {
			List<TypeRef> typeArgs = toList(
					filter(((ParameterizedTypeRef) nestedTypeRef).getDeclaredTypeArgs(), TypeRef.class));
			nestedTypeRef = (typeArgs.isEmpty()) ? null : typeArgs.get(0);
		}
		return nestedTypeRef == null ? null : nestedTypeRef.getDeclaredType();
	}
}
