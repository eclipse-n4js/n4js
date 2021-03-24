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

import java.util.Collection
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.Type

import static org.eclipse.n4js.AnnotationDefinition.*

import static extension org.eclipse.n4js.tooling.organizeImports.DIUtility.*

/**
 * Utility for computing  dependencies for N4JS DI mechanisms.
 */
class InjectedTypesResolverUtility {

		/**
		 * Search through {@link Script} to find all {@link N4ClassifierDefinition}s.
		 * From found entries builds collection of {@link Type}s used with DI annotations.
		 */
		public static def Collection<Type> findAllInjected(Script script) {
			val injections = newArrayList;

			script.eAllContents.filter(N4ClassifierDefinition).forEach [ cl |

				// If has owned injected ctor with formal params, then use params types
				cl.getOwnedInjectedCtorParams.forEach[
					injections.add(getDeclaredTypeFromTypeRef(it.typeRef))
				];
				// Injected fields.
				cl.ownedFields.forEach [ f |
					if (INJECT.hasAnnotation(f)) {
						injections.add(getDeclaredTypeFromTypeRef(f.declaredTypeRef));
					}
				]

				//Binder specific
				if(BINDER.hasOwnedAnnotation(cl)){
					// Binder's @Bind dependencies.
					BIND.getAllOwnedAnnotations(cl).forEach [ an |
						// Since bind has only two arguments, both are TypeRefs.
						injections.add(getDeclaredTypeFromTypeRef(an.args.head.value as TypeRef));
						injections.add(getDeclaredTypeFromTypeRef(an.args.last.value as TypeRef));
					]

					// Binder's @Provides dependencies.
					cl.ownedMethods.forEach [ m |
						if (PROVIDES.hasAnnotation(m)) {
							injections.add(getDeclaredTypeFromTypeRef(m.declaredReturnTypeRef));
							m.fpars.forEach[injections.add(getDeclaredTypeFromTypeRef(it.declaredTypeRef))];
						}
					];
				}

				//DIComponent specific
				if(GENERATE_INJECTOR.hasOwnedAnnotation(cl)){
					if(WITH_PARENT_INJECTOR.hasOwnedAnnotation(cl)){
						var ann = WITH_PARENT_INJECTOR.getOwnedAnnotation(cl)
						if(ann !== null){
							injections.add((ann.args.head as TypeRefAnnotationArgument).typeRef.declaredType)
						}
					}

					if(USE_BINDER.hasOwnedAnnotation(cl)){
						var anns = USE_BINDER.getAllOwnedAnnotations(cl)
						if(anns !== null){
							var argsTypes = anns.map[(args.head as TypeRefAnnotationArgument)].filterNull.map[typeRef.declaredType];
							argsTypes.forEach[injections.add(it)];
						}
					}
				}

			];
			return injections.filterNull.toList;
	}

	private static def getOwnedInjectedCtorParams(N4ClassifierDefinition it) {
		if (it?.definedType instanceof TClass) {
			val ctor = (definedType as TClass).ownedCtor;
			if (null !== ctor) {
				if (INJECT.hasAnnotation(ctor)) return ctor.fpars;
			}
		}
		return emptyList;
	}

	/**
	 * resolves declared type from type ref. If declared type is
	 * N4Provider (can be nested) resolves to (nested) provided type.
	 */
	private static def getDeclaredTypeFromTypeRef(TypeRef typeRef) {
		if (typeRef.providerType) {
			val providedType = typeRef.providedType;
			if (null !== providedType) {
				return providedType;
			}
		} else {
			typeRef.declaredType;
		}
	}
}
