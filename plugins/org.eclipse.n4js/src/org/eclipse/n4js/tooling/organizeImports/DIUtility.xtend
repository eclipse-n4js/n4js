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
package org.eclipse.n4js.tooling.organizeImports

import java.util.List
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.Type

import static org.eclipse.n4js.AnnotationDefinition.*
import static org.eclipse.n4js.ts.types.util.AllSuperTypesCollector.collect

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Collection of utility methods for working with N4JS DI.
 */
class DIUtility {

	def public static boolean isSingleton(Type type) {
		type.annotations.exists[name == AnnotationDefinition.SINGLETON.name]
	}

	def public static boolean hasSuperType(Type type) {
		if(type instanceof TClass){
			type.superClassRef?.declaredType instanceof TClass
		}else{
			false
		}
	}

	/**
	 * Returns true if provided type is instanceof {@link TClass}
	 * and at least owned member is annotated with {@link AnnotationDefinition#INJECT}.
	 */
	def public static boolean hasInjectedMembers(Type type) {
		if (type instanceof TClass)
			collect(type).exists[ownedMembers.exists[INJECT.hasAnnotation(it)]]
		else
			false;
	}

	/**
	 * Generate DI meta info for classes that have injected members,
	 * or are can be injected and have DI relevant information, e.g. scope annotation.
	 * Also if type has a super type (injection of inherited members)
	 */
	def public static boolean isInjectedClass(Type it){
		isSingleton || hasInjectedMembers || hasSuperType
	}

	def public static boolean isBinder(Type type) {
		BINDER.hasAnnotation(type)
	}

	def public static boolean isDIComponent(Type type) {
		GENERATE_INJECTOR.hasAnnotation(type)
	}

	/**
	 * Checks if diComponent has parent component, that is one specified by the superclass
	 * or by the inheritance.
	 */
	def public static boolean hasParentInjector(Type type) {
		return WITH_PARENT_INJECTOR.hasAnnotation(type) ||
			if (type instanceof TClass) {
				type.superClassRef !== null;
			} else
				false;
	}

	/**
	 * @returns {@link Type} of the parent DIComponent.
	 * @throws {@link RuntimeException} if no parent on provided type.
	 */
	def public static Type findParentDIC(Type type) {
		var TypeRef parent = if (WITH_PARENT_INJECTOR.hasAnnotation(type)) {
				(WITH_PARENT_INJECTOR.getOwnedAnnotation(type)?.args.head as TAnnotationTypeRefArgument).typeRef;
			} else if (type instanceof TClass) {
				type.superClassRef;
			} else
				null;

		if (parent !== null) {
			return parent.declaredType
		}

		throw new RuntimeException("no parent on " + type.name);
	}

	/**
	 * returns list of types that are parameters of {@link AnnotationDefinition#USE_BINDER} annotations
	 * attached to a given type or empty list
	 */
	def public static List<Type> resolveBinders(Type type) {
		return USE_BINDER.getAllOwnedAnnotations(type)
			.map[args].flatten
			.map[(it as TAnnotationTypeRefArgument).typeRef.declaredType].filterNull.toList
	}

		/** Returns with {@code true} if one or more members of the given type are annotated with {@code @Inject} annotation. */
	def public static boolean requiresInjection(Type type) {
		return if (type instanceof TClass) collect(type).exists[ownedMembers.exists[INJECT.hasAnnotation(it)]] else false;
	}

	/**
	 * Returns with {@code true} if the type reference argument requires injection. Either the declared type requires injection,
	 * or the type reference represents an N4 provider, and the dependency of the provider requires injection. Otherwise
	 * returns with {@code false}.
	 */
	def public static boolean requiresInjection(TypeRef it) {
		return declaredType.requiresInjection || (providerType && providedType.requiresInjection)
	}

	/**
	 * Returns with {@code true} if the type reference argument is an N4 provider. Otherwise returns with {@code false}.
	 * Also returns with {@code false} if the type reference is sub interface of N4 provider or a class which implements
	 * the N4 provider interface.
	 */
	def public static boolean isProviderType(TypeRef it) {
		null !== it && declaredType === newRuleEnvironment.n4ProviderType;
	}

	/**
	 * Returns with the type most nested dependency if the type reference argument represents and N4 provider.
	 * Otherwise returns with {@code null}.
	 */
	def public static getProvidedType(TypeRef it) {
		if (!providerType) {
			return null;
		}
		var nestedTypeRef = it;
		while (nestedTypeRef.providerType && nestedTypeRef instanceof ParameterizedTypeRef) {
			val typeArgs = (nestedTypeRef as ParameterizedTypeRef).typeArgs.filter(TypeRef);
			nestedTypeRef = if (typeArgs.nullOrEmpty) null else typeArgs.head;
		}
		return nestedTypeRef?.declaredType;
	}
}
