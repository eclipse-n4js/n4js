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

import static org.eclipse.n4js.AnnotationDefinition.BIND;
import static org.eclipse.n4js.AnnotationDefinition.BINDER;
import static org.eclipse.n4js.AnnotationDefinition.GENERATE_INJECTOR;
import static org.eclipse.n4js.AnnotationDefinition.INJECT;
import static org.eclipse.n4js.AnnotationDefinition.PROVIDES;
import static org.eclipse.n4js.AnnotationDefinition.USE_BINDER;
import static org.eclipse.n4js.AnnotationDefinition.WITH_PARENT_INJECTOR;
import static org.eclipse.n4js.tooling.organizeImports.DIUtility.getProvidedType;
import static org.eclipse.n4js.tooling.organizeImports.DIUtility.isProviderType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toIterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.Type;

/**
 * Utility for computing dependencies for N4JS DI mechanisms.
 */
public class InjectedTypesResolverUtility {

	/**
	 * Search through {@link Script} to find all {@link N4ClassifierDefinition}s. From found entries builds collection
	 * of {@link Type}s used with DI annotations.
	 */
	public static List<Type> findAllInjected(Script script) {
		List<Type> injections = new ArrayList<>();

		for (N4ClassifierDefinition cl : toIterable(filter(script.eAllContents(), N4ClassifierDefinition.class))) {

			// If has owned injected ctor with formal params, then use params types
			for (TFormalParameter fp : getOwnedInjectedCtorParams(cl)) {
				injections.add(getDeclaredTypeFromTypeRef(fp.getTypeRef()));
			}

			// Injected fields.
			for (N4FieldDeclaration fd : cl.getOwnedFields()) {
				if (INJECT.hasAnnotation(fd)) {
					injections.add(getDeclaredTypeFromTypeRef(fd.getDeclaredTypeRef()));
				}
			}

			// Binder specific
			if (BINDER.hasOwnedAnnotation(cl)) {
				// Binder's @Bind dependencies.
				for (Annotation an : BIND.getAllOwnedAnnotations(cl)) {
					// Since bind has only two arguments, both are TypeRefs.
					injections.add(getDeclaredTypeFromTypeRef((TypeRef) an.getArgs().get(0).value()));
					injections.add(getDeclaredTypeFromTypeRef((TypeRef) last(an.getArgs()).value()));
				}

				// Binder's @Provides dependencies.
				for (N4MethodDeclaration m : cl.getOwnedMethods()) {
					if (PROVIDES.hasAnnotation(m)) {
						injections.add(getDeclaredTypeFromTypeRef(m.getDeclaredReturnTypeRef()));

						for (FormalParameter fp : m.getFpars()) {
							injections.add(getDeclaredTypeFromTypeRef(fp.getDeclaredTypeRef()));
						}
					}
				}
			}

			// DIComponent specific
			if (GENERATE_INJECTOR.hasOwnedAnnotation(cl)) {
				if (WITH_PARENT_INJECTOR.hasOwnedAnnotation(cl)) {
					var ann = WITH_PARENT_INJECTOR.getOwnedAnnotation(cl);
					if (ann != null) {
						injections
								.add(((TypeRefAnnotationArgument) ann.getArgs().get(0)).getTypeRef().getDeclaredType());
					}
				}

				if (USE_BINDER.hasOwnedAnnotation(cl)) {
					Iterable<Annotation> anns = USE_BINDER.getAllOwnedAnnotations(cl);
					if (anns != null) {
						var argsTypes = map(
								filterNull(map(anns, a -> ((TypeRefAnnotationArgument) a.getArgs().get(0)))),
								arg -> arg.getTypeRef().getDeclaredType());

						for (Type at : argsTypes) {
							injections.add(at);
						}
					}
				}
			}
		}
		return toList(filterNull(injections));
	}

	private static List<TFormalParameter> getOwnedInjectedCtorParams(N4ClassifierDefinition cd) {
		if (cd != null && cd.getDefinedType() instanceof TClass) {
			TMethod ctor = ((TClass) cd.getDefinedType()).getOwnedCtor();
			if (null != ctor) {
				if (INJECT.hasAnnotation(ctor))
					return ctor.getFpars();
			}
		}
		return Collections.emptyList();
	}

	/**
	 * resolves declared type from type ref. If declared type is N4Provider (can be nested) resolves to (nested)
	 * provided type.
	 */
	private static Type getDeclaredTypeFromTypeRef(TypeRef typeRef) {
		if (isProviderType(typeRef)) {
			Type providedType = getProvidedType(typeRef);
			if (null != providedType) {
				return providedType;
			}
		} else {
			return typeRef.getDeclaredType();
		}
		return null;
	}
}
