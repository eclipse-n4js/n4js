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
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.Annotation
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.LiteralAnnotationArgument
import org.eclipse.n4js.n4JS.ModifierUtils
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.PropertyNameKind
import org.eclipse.n4js.n4JS.PropertyNameOwner
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument
import org.eclipse.n4js.postprocessing.ComputedNameProcessor
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.DeclaredTypeWithAccessModifier
import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TAnnotableElement
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import java.util.Collection
import java.util.List
import org.eclipse.emf.ecore.EObject

@Singleton
package class N4JSTypesBuilderHelper {

	@Inject	private JavaScriptVariantHelper jsVariantHelper;

	def package List<Annotation> getAllAnnotations(AnnotableElement annotableElement) {
		val annotations = <Annotation>newArrayList
		val parent = annotableElement.eContainer
		if(parent instanceof ExportDeclaration) {
			annotations += parent.annotations
		}
		annotations += annotableElement.annotations
		annotations
	}

	def package void setTypeAccessModifier(EObject eo, (TypeAccessModifier)=>void typeAccessModifierAssignment,
		Collection<? extends N4Modifier> modifiers, List<Annotation> annotations) {
		val isPlainJS = jsVariantHelper.isPlainJS(eo);
		// IDEBUG-861 assume public visibility if plain JS
		if (isPlainJS) {
			typeAccessModifierAssignment.apply(TypeAccessModifier.PUBLIC);
		} else {
			typeAccessModifierAssignment.apply(ModifierUtils.convertToTypeAccessModifier(modifiers, annotations));
		}
	}

	def package void setProvidedByRuntime(DeclaredTypeWithAccessModifier declaredType, AnnotableElement annotableElement, boolean preLinkingPhase) {
		declaredType.declaredProvidedByRuntime = AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(annotableElement);
	}

	/**
	 * Adds references to a feature (via the closure), but copies the references in order to avoid problems with containment relations.
	 * The references are copied with proxies (see {@link TypeUtils#copyWithProxies(EObject)} in order to avoid
	 * resolving of proxies here.
	 *
	 * @param typeListAssignment closure, actually adds the processed list
	 * @param listToAssign the list with references, there must be no proxy in the list
	 * @param preLinkingPhase if true, references are not set (they are only set in the linking phase)
	 * @param <T> reference type, e.g., ParameterizedTypeRef
	 */
	def package <T extends EObject> addCopyOfReferences((List<T>) => void typeListAssignment, List<T> listToAssign, boolean preLinkingPhase) {
		if (listToAssign===null || listToAssign.empty) {
			return
		}
		if (!preLinkingPhase) { // not in prelinking
			if(listToAssign.exists[it!==null && eIsProxy]) {
				throw new IllegalStateException("There is a proxy in the list, cannot copy and set references");
			}
			// TODO this is a hack
			if (listToAssign.exists[it===null]) {
				return
			}
			// TODO maybe optimized with the usage of DelegatingTypeRefs
			typeListAssignment.apply(listToAssign.map[if (it===null) it else TypeUtils.copyWithProxies(it)])
		}
	}

	/**
	 * Adds copy of single cross references to a feature (via the closure) in order to avoid problems with containment relations.
	 * The references are copied with proxies (see {@link TypeUtils#copyWithProxies(EObject)} in order to avoid
	 * resolving of proxies here.
	 *
	 * @param typeAssignment closure, actually sets the reference (after having been copied)
	 * @param typeToAssign the cross reference, must not be a proxy; may be null
	 * @param preLinkingPhase if true, cross reference is not set (only set in the linking phase)
	 * @param <T> cross reference type, e.g., ParameterizedTypeRef
	 */
	def package <T extends EObject> setCopyOfReference((T) => void typeAssignment, T typeToAssign, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			if (typeToAssign===null) {
				typeAssignment.apply(null);
			} else {
				if (typeToAssign.eIsProxy) {
					throw new IllegalStateException("Cannot set copy of proxy reference");
				}
				typeAssignment.apply(TypeUtils.copyWithProxies(typeToAssign))
			}
		}
	}

	/**
	 * Initializes the name of a TMember in the TModule based the member/property declaration in the AST. In case of
	 * computed property names, this method will keep the name in the TModule set to <code>null</code> and
	 * {@link ComputedNameProcessor} will later change it to the actual name during post-processing.
	 */
	def package void setMemberName(TMember tMember, PropertyNameOwner n4MemberOrPropertyAssignment) {
		tMember.name = n4MemberOrPropertyAssignment.name; // this will be 'null' in case of a computed property name
		tMember.hasComputedName = n4MemberOrPropertyAssignment.declaredName?.kind === PropertyNameKind.COMPUTED;
	}

	/**
	 * Translates AST related member access modifier (and annotation {@code @Interanl}) to type model related member access modifier.
	 */
	def package setMemberAccessModifier((MemberAccessModifier) => void memberAccessModifierAssignment, Collection<? extends N4Modifier> modifiers, List<Annotation> annotations) {
		memberAccessModifierAssignment.apply(ModifierUtils.convertToMemberAccessModifier(modifiers,annotations));
	}

	/** Returns newly created reference to built-in type <code>any</code>. */
	def package createAnyTypeRef(EObject object) {
		val rs = object?.eResource?.resourceSet
		if(rs!==null)
			BuiltInTypeScope.get(rs).anyTypeRef
		else
			null
	}

	/**
	 * Copies annotations from AST to Type model. Note that not all annotations are copied, only the ones listed in
	 * {@link #ANNOTATIONS_IN_TYPE_MODEL}. Also annotations contained in containing export declaration are copied, as this
	 * construct is not present in type model and its annotations are to be defined at the type.
	 * <p>
	 * Since this mechanism is changed anyway, no type check (whether annotation is allowed for given element type) is
	 * performed.
	 */
	def package void copyAnnotations(TAnnotableElement typeTarget, AnnotableElement astSource, boolean preLinkingPhase) {
		astSource.annotations.filter[AnnotationDefinition.isInTypeModel(name)]
			.forEach[
				val ta = TypesFactory.eINSTANCE.createTAnnotation();
				ta.name = it.name;
				ta.args.addAll(args.map[switch(it) {
					LiteralAnnotationArgument: {
						val arg = TypesFactory.eINSTANCE.createTAnnotationStringArgument();
						arg.value = it.literal.valueAsString;
						return arg;
					}
					TypeRefAnnotationArgument: {
						val arg = TypesFactory.eINSTANCE.createTAnnotationTypeRefArgument();
						setCopyOfReference([TypeRef typeRefToSet|arg.typeRef = typeRefToSet], typeRef, preLinkingPhase)
						return arg;
					}
				}])
				typeTarget.annotations.add(ta);
			]
		if (astSource.eContainer instanceof ExportDeclaration) {
			copyAnnotations(typeTarget, astSource.eContainer as ExportDeclaration, preLinkingPhase);
		}
	}

	/** Returns newly created reference to built-in type <code>any</code>. */
	def package getObjectType(EObject object) {
		val rs = object?.eResource?.resourceSet
		if(rs!==null)
			BuiltInTypeScope.get(rs).objectType
		else
			null
	}

	/** @see TClassifier#isDeclaredCovariantConstructor() */
	def package boolean isDeclaredCovariantConstructor(N4ClassifierDeclaration classifierDecl) {
		if(AnnotationDefinition.COVARIANT_CONSTRUCTOR.hasAnnotation(classifierDecl)) {
			return true;
		}
		val ctor = classifierDecl.ownedMembers.findFirst[isConstructor];
		return ctor!==null && AnnotationDefinition.COVARIANT_CONSTRUCTOR.hasAnnotation(ctor);
	}

	/** Handle optional "@This" annotation and set its argument as declared this type in types model element. */
	def protected void setDeclaredThisTypeFromAnnotation(TFunction functionType, FunctionDefinition functionDef,
		boolean preLinkingPhase) {
		setCopyOfReference([TypeRef tr | functionType.declaredThisType = tr], internalGetDeclaredThisTypeFromAnnotation(functionDef), preLinkingPhase);
	}
	/** Handle optional "@This" annotation and set its argument as declared this type in types model element. */
	def protected void setDeclaredThisTypeFromAnnotation(FieldAccessor accessorType, org.eclipse.n4js.n4JS.FieldAccessor accessorDecl, boolean preLinkingPhase) {
		setCopyOfReference([TypeRef tr | accessorType.declaredThisType = tr], internalGetDeclaredThisTypeFromAnnotation(accessorDecl), preLinkingPhase);
	}
	def private TypeRef internalGetDeclaredThisTypeFromAnnotation(AnnotableElement element) {
		val annThis = AnnotationDefinition.THIS.getAnnotation(element);
		return annThis?.args?.filter(TypeRefAnnotationArgument)?.head?.typeRef;
	}
}
