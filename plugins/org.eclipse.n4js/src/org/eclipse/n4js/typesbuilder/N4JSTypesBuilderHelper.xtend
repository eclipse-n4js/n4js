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
import java.util.Collection
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.Annotation
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.LiteralAnnotationArgument
import org.eclipse.n4js.n4JS.ModifiableElement
import org.eclipse.n4js.n4JS.ModifierUtils
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.PropertyNameKind
import org.eclipse.n4js.n4JS.PropertyNameOwner
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument
import org.eclipse.n4js.postprocessing.ComputedNameProcessor
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.AccessibleTypeElement
import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TAnnotableElement
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.validation.JavaScriptVariantHelper

@Singleton
package class N4JSTypesBuilderHelper {

	@Inject private JavaScriptVariantHelper jsVariantHelper;

	private static Logger logger = Logger.getLogger(N4JSTypesBuilderHelper);


	def protected <T extends AnnotableElement & ModifiableElement> void setTypeAccessModifier(
		AccessibleTypeElement classifier, T definition) {

		val isPlainJS = jsVariantHelper.isPlainJS(definition);
		// IDEBUG-861 assume public visibility if plain JS
		if (isPlainJS) {
			classifier.declaredTypeAccessModifier = TypeAccessModifier.PUBLIC;
		} else {
			classifier.declaredTypeAccessModifier = ModifierUtils.convertToTypeAccessModifier(
				definition.declaredModifiers, definition.allAnnotations);
		}
	}

	def package void setProvidedByRuntime(AccessibleTypeElement declaredType,
		AnnotableElement annotableElement, boolean preLinkingPhase) {

		declaredType.declaredProvidedByRuntime = AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(
			annotableElement);
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
	def package <T extends EObject> void addCopyOfReferences(List<? super T> target, List<T> values) {
		if (values.isNullOrEmpty) {
			return
		}
		if (values.exists[it !== null && eIsProxy]) {
			throw new IllegalStateException("There is a proxy in the list, cannot copy and set references");
		}
		// TODO this is a hack
		if (values.exists[it === null]) {
			return
		}
		target += values.map[TypeUtils.copyWithProxies(it)]
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
	def package void setMemberAccessModifier((MemberAccessModifier)=>void memberAccessModifierAssignment,
		Collection<? extends N4Modifier> modifiers, List<Annotation> annotations) {
		memberAccessModifierAssignment.apply(ModifierUtils.convertToMemberAccessModifier(modifiers, annotations));
	}

	/** Returns newly created reference to built-in type <code>any</code>. */
	def package ParameterizedTypeRef createAnyTypeRef(EObject object) {
		val rs = object?.eResource?.resourceSet
		if (rs !== null)
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
	def package void copyAnnotations(TAnnotableElement typeTarget, AnnotableElement ast, boolean preLinkingPhase) {
		typeTarget.annotations += ast.allAnnotations.filter[AnnotationDefinition.isInTypeModel(name)].map [
			val ta = TypesFactory.eINSTANCE.createTAnnotation();
			ta.name = it.name;
			ta.args.addAll(args.map [
				switch (it) {
					LiteralAnnotationArgument: {
						val arg = TypesFactory.eINSTANCE.createTAnnotationStringArgument();
						arg.value = it.literal.valueAsString;
						return arg;
					}
					TypeRefAnnotationArgument: {
						val arg = TypesFactory.eINSTANCE.createTAnnotationTypeRefArgument();
						arg.typeRef = TypeUtils.copyWithProxies(typeRefNode?.typeRefInAST);
						return arg;
					}
				}
			])
			return ta
		]
	}

	/** Returns newly created reference to built-in type <code>any</code>. */
	def package TClassifier getObjectType(EObject object) {
		val rs = object?.eResource?.resourceSet
		if (rs !== null)
			BuiltInTypeScope.get(rs).objectType
		else
			null
	}

	/** @see TClassifier#isDeclaredCovariantConstructor() */
	def package boolean isDeclaredCovariantConstructor(N4ClassifierDeclaration classifierDecl) {
		if (AnnotationDefinition.COVARIANT_CONSTRUCTOR.hasAnnotation(classifierDecl)) {
			return true;
		}
		val ctor = classifierDecl.ownedMembers.findFirst[isConstructor];
		return ctor !== null && AnnotationDefinition.COVARIANT_CONSTRUCTOR.hasAnnotation(ctor);
	}

	/** Handle optional "@This" annotation and set its argument as declared this type in types model element. */
	def protected void setDeclaredThisTypeFromAnnotation(TFunction functionType, FunctionDefinition functionDef,
		boolean preLinkingPhase) {
		if (!preLinkingPhase)
			functionType.declaredThisType = TypeUtils.copyWithProxies(
				internalGetDeclaredThisTypeFromAnnotation(functionDef));
	}

	/** Handle optional "@This" annotation and set its argument as declared this type in types model element. */
	def protected void setDeclaredThisTypeFromAnnotation(FieldAccessor accessorType,
		org.eclipse.n4js.n4JS.FieldAccessor accessorDecl, boolean preLinkingPhase) {
		if (!preLinkingPhase)
			accessorType.declaredThisType = TypeUtils.copyWithProxies(
				internalGetDeclaredThisTypeFromAnnotation(accessorDecl));
	}

	def private TypeRef internalGetDeclaredThisTypeFromAnnotation(AnnotableElement element) {
		val annThis = AnnotationDefinition.THIS.getAnnotation(element);
		return annThis?.args?.filter(TypeRefAnnotationArgument)?.head?.typeRefNode?.typeRefInAST;
	}


	/**
	 * Used during {@link N4JSTypesBuilder#relinkTModuleToSource(org.eclipse.xtext.resource.DerivedStateAwareResource,
	 * boolean) relinking}, to ensure consistency of named elements between newly loaded AST and original TModule.
	 */
	def protected void ensureEqualName(NamedElement astNode, IdentifiableElement moduleElement) {
		val nameInAST = astNode.name;
		val nameInModule = moduleElement.name;
		if (nameInAST !== null) { // note: no check if no name available in AST (don't fiddle with computed property names, etc.)
			if (!nameInAST.equals(nameInModule)) {
				val msg = "inconsistency between newly loaded AST and to-be-linked TModule: "
					+ "nameInAST=" + nameInAST + ", "
					+ "nameInModule=" + nameInModule + ", "
					+ "in: " + astNode.eResource?.URI;
				logger.error(msg);
				throw new IllegalStateException(msg);
			}
		}
	}
}
