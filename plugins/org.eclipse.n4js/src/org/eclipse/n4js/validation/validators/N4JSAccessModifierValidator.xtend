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
package org.eclipse.n4js.validation.validators

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.StaticPolyfillHelper
import org.eclipse.n4js.utils.StructuralTypesHelper
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
class N4JSAccessModifierValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	protected N4JSTypeSystem ts;

	@Inject protected ContainerTypesHelper containerTypesHelper;

	@Inject StructuralTypesHelper structuralTypesHelper;

	@Inject StaticPolyfillHelper staticPolyfillHelper;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	@Check
	def checkExportedWhenVisibilityHigherThanPrivate(TypeDefiningElement typeDefiningElement) {
		if (!jsVariantHelper.requireCheckExportedWhenVisibilityHigherThanPrivate(typeDefiningElement)) {
			return; // does not apply to plain JS files
		}

		if (typeDefiningElement instanceof ObjectLiteral) {
			return; // does not apply to ObjectLiterals and their defined type TStructuralType
		}
		if (typeDefiningElement instanceof NamespaceImportSpecifier) {
			return; // does not apply to NamespaceImportSpecifier and their defined type ModuleNamespaceVirtualType
		}

		val type = typeDefiningElement.definedType

		if (type !== null && !type.exported && type.typeAccessModifier.ordinal > TypeAccessModifier.PRIVATE.ordinal) {
			if (type instanceof SyntaxRelatedTElement) {
				if (type.astElement !== null) {
					val message = getMessageForCLF_NOT_EXPORTED_NOT_PRIVATE(type.keyword,
						type.typeAccessModifier.keyword)
					val eObjectToNameFeature = type.astElement.findNameFeature
					addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value,
						CLF_NOT_EXPORTED_NOT_PRIVATE)
				}
			}
		}
	}

	@Check
	def checkInternalUsedOnlyWithPublicAndProtected(ExportableElement exportableElement) {
		val parent = exportableElement.eContainer
		var annotation = if (exportableElement instanceof AnnotableElement) {
				AnnotationDefinition.INTERNAL.getAnnotation(exportableElement);
			}
		if (annotation === null) {
			annotation = if (parent instanceof ExportDeclaration) {
				AnnotationDefinition.INTERNAL.getAnnotation(parent);
			}
		}
		if (annotation !== null) {
			val typeAccessModifier = switch (it : exportableElement) {
				VariableStatement:
					it.varDecl.filter(ExportedVariableDeclaration).head?.definedVariable?.typeAccessModifier
				FunctionDeclaration:
					definedType?.typeAccessModifier
				TypeDefiningElement:
					definedType?.typeAccessModifier
				default:
					null
			}
			if (typeAccessModifier !== null && typeAccessModifier.ordinal <= TypeAccessModifier.PROJECT.ordinal) {
				val message = getMessageForCLF_LOW_ACCESSOR_WITH_INTERNAL(exportableElement.keyword,
					typeAccessModifier.keyword)
				val eObjectToNameFeature = exportableElement.findNameFeature
				addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value, CLF_LOW_ACCESSOR_WITH_INTERNAL)
			}
		}
	}

	@Check
	def checkTypeRefOptionalFlag(TypeRef typeRef) {
		if (typeRef.isFollowedByQuestionMark) {
			val parent = typeRef.eContainer;

			val isLegalUseOfOptional = isReturnTypeButNotOfAGetter(typeRef, parent);

			if(!isLegalUseOfOptional) {

				if(parent instanceof TFormalParameter) {
					return; // avoid duplicate error messages
				} else if(parent instanceof N4FieldDeclaration || parent instanceof TField) {
					val message = messageForCLF_FIELD_OPTIONAL_OLD_SYNTAX;
					val node = NodeModelUtils.findActualNodeFor(typeRef)
					if (node !== null) {
						addIssue(message, typeRef, node.offset, node.length, CLF_FIELD_OPTIONAL_OLD_SYNTAX)
					}
				} else {
					val message = messageForEXP_OPTIONAL_INVALID_PLACE;
					val node = NodeModelUtils.findActualNodeFor(typeRef)
					if (node !== null) {
						addIssue(message, typeRef, node.offset, node.length, EXP_OPTIONAL_INVALID_PLACE)
					}
				}
			}
		}
	}

	def private isReturnTypeButNotOfAGetter(TypeRef typeRefInAST, EObject parent) {
		(parent instanceof FunctionDefinition && (parent as FunctionDefinition).declaredReturnTypeRefInAST === typeRefInAST &&
			!(parent instanceof N4GetterDeclaration)) ||
			(parent instanceof TFunction && (parent as TFunction).returnTypeRef === typeRefInAST &&
				!(parent instanceof TGetter)) ||
			(parent instanceof FunctionTypeExpression && (parent as FunctionTypeExpression).returnTypeRef === typeRefInAST)
	}

	@Check
	def checkFieldConstFinalValidCombinations(N4FieldDeclaration n4field) {
		if (n4field.const && n4field.declaredStatic) {
			val msg = getMessageForCLF_FIELD_CONST_STATIC;
			addIssue(msg, n4field, N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName, CLF_FIELD_CONST_STATIC);
		} else if (n4field.const && n4field.final) {
			val msg = getMessageForCLF_FIELD_CONST_FINAL;
			addIssue(msg, n4field, N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName, CLF_FIELD_CONST_FINAL);
		} else if (n4field.final && n4field.declaredStatic) {
			val msg = getMessageForCLF_FIELD_FINAL_STATIC;
			addIssue(msg, n4field, N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName, CLF_FIELD_FINAL_STATIC);
		}
	}

	@Check
	def checkFieldConstInitialization(N4FieldDeclaration n4field) {
		if (!jsVariantHelper.constantHasInitializer(n4field)) {
			return; // in .n4jsd --> anything goes
		}

		if (n4field.const && n4field.expression === null) {
			val msg = getMessageForCLF_FIELD_CONST_MISSING_INIT(n4field.name);
			addIssue(msg, n4field, N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName, CLF_FIELD_CONST_MISSING_INIT);
		}
	}

	/**
	 * 5.2.3.1, Constraint 58.3
	 */
	@Check
	def checkFieldFinalInitialization(N4ClassifierDefinition n4classifier) {
		if (!jsVariantHelper.requireCheckFinalFieldIsInitialized(n4classifier)) {
			return; // in .n4jsd --> anything goes
		}

		var Iterable<N4FieldDeclaration> finalFieldsWithoutInit = n4classifier.ownedFields.filterNull.filter [
			final && expression === null
		];
		if (finalFieldsWithoutInit.empty) {
			return; // nothing to check here
		}

		finalFieldsWithoutInit = filterFieldsInitializedViaSpecConstructor(n4classifier, finalFieldsWithoutInit);
		if (finalFieldsWithoutInit.empty) {
			return; // nothing to anymore
		}

		finalFieldsWithoutInit = filterFieldsInitializedExplicitlyInConstructor(n4classifier, finalFieldsWithoutInit);
		if (finalFieldsWithoutInit.empty) {
			return; // nothing to do anymore
		}

		// create error messages:
		val boolean replacedByPolyfill = n4classifier.ownedCtor !== n4classifier.polyfilledOrOwnCtor;
		if (replacedByPolyfill) {
			finalFieldsWithoutInit.forEach [
				val msg = getMessageForCLF_FIELD_FINAL_MISSING_INIT_IN_STATIC_POLYFILL(it.name);
				addIssue(msg, it, N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName,
					CLF_FIELD_FINAL_MISSING_INIT_IN_STATIC_POLYFILL)
			]
		} else {
			finalFieldsWithoutInit.forEach [
				val msg = getMessageForCLF_FIELD_FINAL_MISSING_INIT(it.name);
				addIssue(msg, it, N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName, CLF_FIELD_FINAL_MISSING_INIT)
			]
		}
	}

	private def Iterable<N4FieldDeclaration> filterFieldsInitializedViaSpecConstructor(
		N4ClassifierDefinition n4classifier, Iterable<N4FieldDeclaration> finalFieldsWithoutInit) {

		if (null === n4classifier.definedType) {
			return emptyList
		}

		val ctor = n4classifier.polyfilledOrOwnCtor;
		val tctor = if (ctor === null) {
				containerTypesHelper.fromContext(n4classifier).findConstructor(
					n4classifier.definedType as ContainerType<?>);
			} else {
				ctor.definedTypeElement as TMethod;
			}

		val specPar = tctor?.fpars?.findFirst[AnnotationDefinition.SPEC.hasAnnotation(it)];
		val typeRef = specPar?.typeRef;
		if (typeRef instanceof ThisTypeRefStructural) {
			val boundThisTypeRef = TypeUtils.createBoundThisTypeRefStructural(
				TypeUtils.createTypeRef(n4classifier.definedType), typeRef);
			val specMemberFieldName = structuralTypesHelper.collectStructuralMembers(n4classifier.newRuleEnvironment,
				boundThisTypeRef, TypingStrategy.STRUCTURAL_FIELDS).map[name].toSet;
			return finalFieldsWithoutInit.filter[! specMemberFieldName.contains(name)];
		}
		return finalFieldsWithoutInit;
	}

	private def Iterable<N4FieldDeclaration> filterFieldsInitializedExplicitlyInConstructor(
		N4ClassifierDefinition n4classifier, Iterable<N4FieldDeclaration> finalFieldsWithoutInit) {

		val N4MethodDeclaration ctor = n4classifier.polyfilledOrOwnCtor;

		val finalFieldsAssignedInCtor = if (ctor?.body === null) {
				#[]
			} else {
				ctor.body.allStatements.map[eAllContents.toIterable].toIterable.flatten.map [
					isAssignmentToFinalFieldInThis
				].filterNull.toSet
			};
		return finalFieldsWithoutInit.filter[!finalFieldsAssignedInCtor.contains(it.definedField)];
	}

	private def TField isAssignmentToFinalFieldInThis(EObject astNode) {
		if (astNode instanceof AssignmentExpression) {
			val lhs = astNode.lhs;
			if (lhs instanceof ParameterizedPropertyAccessExpression) {
				if (lhs.target instanceof ThisLiteral) {
					val prop = lhs.property;
					if (prop instanceof TField) {
						if (prop.final) {
							return prop;
						}
					}
				}
			}
		}
		return null;
	}

	/** fetch the statically polyfilled ctor, or the own one. returns null if both are not defined. */
	private def N4MethodDeclaration polyfilledOrOwnCtor(N4ClassifierDefinition n4classifier) {

		// if static polyfill is available, then look for  filled in ctor.
		val polyAware = n4classifier.definedType.containingModule.isStaticPolyfillAware
		val polyfill = if (polyAware) staticPolyfillHelper.getStaticPolyfill(n4classifier.definedType) else null;
		val polyfillCtor = polyfill?.ownedCtor

		val N4MethodDeclaration ctor = polyfillCtor ?: n4classifier.ownedCtor;
		return ctor;
	}
}
