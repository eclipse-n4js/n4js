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
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.Annotation
import org.eclipse.n4js.n4JS.AnnotationList
import org.eclipse.n4js.n4JS.EmptyStatement
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ExportableElement
import org.eclipse.n4js.n4JS.ExportedVariableStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.projectDescription.ProjectType
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TObjectPrototype
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.util.Tuples
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.validation.IssueCodes.*

/**
 */
class N4JSExternalValidator extends AbstractN4JSDeclarativeValidator {

	/**
	 * Helper structure for 13.1. constraints validation
	 */
	private val PROVIDES_ANNOTATION_INFO = #[
		Tuples.create(AnnotationDefinition.PROVIDES_DEFAULT_IMPLEMENTATION, "interfaces", N4InterfaceDeclaration),
		Tuples.create(AnnotationDefinition.PROVIDES_INITIALZER, "classifiers", N4ClassifierDeclaration)
	]

	@Inject
	private IN4JSCore n4jsCore;

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

	/**
	 * @see N4Spec, 4.20. External Classes
	 */
	@Check
	def checkExternalClassesDefinedInN4JSDFile(N4ClassDeclaration clazz) {
		if (! holdsExternalOnlyInDefinitionFile(clazz, "Classes")) {
			return;
		}
	}

	/**
	 * @see N4Spec, 4.20. External Interfaces
	 */
	@Check
	def checkExternalInterfacesDefinedInN4JSDFile(N4InterfaceDeclaration interfaceDecl) {
		if (! holdsExternalOnlyInDefinitionFile(interfaceDecl, "Interfaces")) {
			return;
		}
	}

	/**
	 * @see N4Spec, 4.20. External Enums
	 */
	@Check
	def checkExternalEnumsDefinedInN4JSDFile(N4EnumDeclaration enumDecl) {
		if (! holdsExternalOnlyInDefinitionFile(enumDecl, "Enumerations")) {
			return;
		}
	}

	private def boolean holdsExternalOnlyInDefinitionFile(N4TypeDeclaration typeDecl, String typesName) {
		if (typeDecl.external && !jsVariantHelper.isExternalMode(typeDecl)) {
			val message = getMessageForCLF_EXT_EXTERNAL_N4JSD(typesName)
			addIssue(message, typeDecl, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME, CLF_EXT_EXTERNAL_N4JSD)
			return false;
		}
		return true;
	}

	/**
	 * 13.1 ExternalDeclarations, Constraints 138.4: External class/interface members {@code @ProvidesDefaultImplementation}.
	 */
	@Check
	def checkProvidesDefaultImplementationAnnotation(N4MemberDeclaration memberDecl) {
		if (memberDecl.name === null) { // ignore invalid/incomplete situations which would lead to duplicate errors
			return;
		}

		for (valInfo : PROVIDES_ANNOTATION_INFO) {

			// @ProvidesInitializer is supported for setters in n4js files as well. (IDE-1996)
			if (AnnotationDefinition.PROVIDES_INITIALZER === valInfo.first &&
				memberDecl instanceof N4SetterDeclaration) {
				return;
			}

			val AnnotationDefinition annodef = valInfo.first;
			val String typeName = valInfo.second;
			val Class<?> metaType = valInfo.third;

			if (annodef.hasAnnotation(memberDecl)) {
				if (!jsVariantHelper.isExternalMode(memberDecl)) {
					val msg = getMessageForCLF_EXT_PROVIDES_IMPL_ONLY_IN_DEFFILES(annodef.name, typeName);
					addIssue(msg, memberDecl, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_EXT_PROVIDES_IMPL_ONLY_IN_DEFFILES);
					return;
				}

				val N4ClassifierDefinition owner = memberDecl.owner;

				if (!(metaType.isInstance(owner))) {
					val msg = getMessageForCLF_EXT_PROVIDES_IMPL_ONLY_IN_INTERFACE_MEMBERS(annodef.name, typeName);
					addIssue(msg, memberDecl, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_EXT_PROVIDES_IMPL_ONLY_IN_INTERFACE_MEMBERS);
					return;
				}

				if (! AnnotationDefinition.N4JS.hasAnnotation(owner)) {
					val msg = getMessageForCLF_EXT_PROVIDES_IMPL_ONLY_IN_N4JS_INTERFACES(annodef.name, typeName);
					addIssue(msg, memberDecl, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_EXT_PROVIDES_IMPL_ONLY_IN_N4JS_INTERFACES);
					return;

				}
			}
		}
	}

	/**
	 * @see N4Spec, 4.20. External Classes and Roles
	 */
	@Check
	def checkExternalFunctionsDefinedInN4JSDFile(FunctionDeclaration funDecl) {
		if (funDecl.external && !jsVariantHelper.isExternalMode(funDecl)) {
			val message = getMessageForCLF_EXT_EXTERNAL_N4JSD("Functions")
			addIssue(message, funDecl, N4JSPackage.Literals.FUNCTION_DECLARATION__NAME, CLF_EXT_EXTERNAL_N4JSD)
		}
	}

	/**
	 * No assignment in n4jsd.
	 */
	@Check
	def checkExternalVariableStatementAssigments(ExportedVariableStatement variableStatement) {

		if (variableStatement.external &&
			!jsVariantHelper.isExternalMode(variableStatement)) {
			val message = getMessageForCLF_EXT_EXTERNAL_N4JSD("Variables")
			addIssue(message, variableStatement, CLF_EXT_EXTERNAL_N4JSD)
		}

		variableStatement.varDecl.forEach [ evd |
			if (jsVariantHelper.isExternalMode(evd) && evd.expression !== null) {
				val mod = if (evd.const) {
						"constant"
					} else {
						"variable"
					}
				val message = getMessageForCLF_EXT_VAR_NO_VAL(mod)
				addIssue(message, evd, CLF_EXT_VAR_NO_VAL)
			}
		]
	}

	/**
	 * @see N4Spec, 4.20. External Classes and Roles
	 */
	@Check
	def checkAllowedElementsInN4JSDFile(EObject eo) {
		if (jsVariantHelper.isExternalMode(eo) && eo.eContainer instanceof Script) {
			val found = eo.findUnallowedElement
			if (found) {
				handleUnallowedElement(eo)
			} else if (eo instanceof ExportDeclaration) {
				handleExportDeclaration(eo)
			} else if (!(eo instanceof AnnotationList || eo instanceof Annotation || eo instanceof EmptyStatement ||
				eo instanceof ImportDeclaration)) {
				// relaxed by IDEBUG-561:		handleNotExported(eo)
			}
		}
	}

	def private handleExportDeclaration(ExportDeclaration eo) {
		val exported = eo.exportedElement
		holdsExternalImplementation(exported)
		switch (exported) {
			N4ClassDeclaration: handleN4ClassDeclaration(eo, exported)
			N4InterfaceDeclaration: handleN4InterfaceDeclaration(eo, exported)
			N4EnumDeclaration: handleN4EnumDeclaration(eo, exported)
			FunctionDeclaration: handleFunctionDeclaration(eo, exported)
		}
	}

	def private handleN4ClassDeclaration(ExportDeclaration eo, N4ClassDeclaration exported) {
		validateClassifierIsExternal(exported.external, "classes", eo)
		// relaxed by IDEBUG-561:	exported.validateClassifierIsPublicApi("classes", eo)
		if (AnnotationDefinition.N4JS.hasAnnotation(exported)) {
			validateExtensionOfNotAnnotatedClass(exported, eo)
			validateConsumptionOfNonAnnotatedInterfaces(exported.implementedInterfaceRefs, eo, "classes")
		} else {
			val superClass = exported.superClassRef?.hasExpectedTypes(TClass)
			validateNonAnnotatedClassDoesntExtendN4Object(exported, superClass, eo)
			validateConsumptionOfNonExternalInterfaces(exported.implementedInterfaceRefs, eo, "classes")
		}
		validateNoObservableAtClassifier(eo, exported, "classes")
		// relaxed by IDEBUG-561:	validatePublicConstructor(exported)
		validateMembers(exported, "classes")
	}

	def private handleN4InterfaceDeclaration(ExportDeclaration eo, N4InterfaceDeclaration exported) {
		if (exported.typingStrategy == TypingStrategy.NOMINAL || exported.typingStrategy == TypingStrategy.DEFAULT) {
			validateClassifierIsExternal(exported.external, "interfaces", eo)
		}
		// relaxed by IDEBUG-561:		exported.validateClassifierIsPublicApi("interfaces", eo)
		if (AnnotationDefinition.N4JS.hasAnnotation(exported)) {
			validateConsumptionOfNonAnnotatedInterfaces(exported.superInterfaceRefs, eo, "interfaces")
		} else {
			validateConsumptionOfNonExternalInterfaces(exported.superInterfaceRefs, eo, "interfaces")
		}
		validateNoObservableAtClassifier(eo, exported, "interfaces")
		validateMembers(exported, "interfaces")
	}

	def private handleN4EnumDeclaration(ExportDeclaration eo, N4EnumDeclaration exported) {
		// relaxed by IDEBUG-561:		exported.validateEnumIsPublicApi(eo)
		if (!exported.isStringBased) { // note: literal in a string-based enum may have value even in .n4jsd files!
			for (literal : exported.literals.filter[it.value !== null]) {
				val message = getMessageForCLF_EXT_LITERAL_NO_VALUE
				addIssue(message, literal, N4JSPackage.Literals.N4_ENUM_LITERAL__VALUE, CLF_EXT_LITERAL_NO_VALUE)
			}
		}
	}

	def private handleFunctionDeclaration(ExportDeclaration eo, FunctionDeclaration exported) {
		// relaxed by IDEBUG-561:		exported.validateFunctionIsPublicApi(eo)
		if (exported.body !== null) {
			val message = getMessageForCLF_EXT_FUN_NO_BODY
			val eObjectToNameFeature = eo.findNameFeature
			addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value, CLF_EXT_FUN_NO_BODY)
		}
	}

	/**
	 * Constraints 120 (Implementation of External Declarations)
	 */
	def private boolean holdsExternalImplementation(ExportableElement element) {
		if (element instanceof AnnotableElement) {
			if (AnnotationDefinition.IGNORE_IMPLEMENTATION.hasAnnotation(element)) {
				return true;
			}

			if (AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation(element)) {
				return holdsDefinedInRuntime(element);
			}
		}

		return true;

	//
	// val message =getMessageForCLF_EXT_NO_MATCH
	// val eObjectToNameFeature = element.findNameFeature
	// addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value,CLF_EXT_NO_MATCH)
	// return false;
	}

	/**
	 * Constraints 70. 1
	 */
	def private holdsDefinedInRuntime(ExportableElement element) {
		val projectType = n4jsCore.findProject(element?.eResource?.URI).orNull?.projectType;
		if (projectType === null || projectType === ProjectType.RUNTIME_ENVIRONMENT ||
			projectType === ProjectType.RUNTIME_LIBRARY) {
			return true;
		}

		val message = messageForCLF_EXT_PROVIDED_BY_RUNTIME_IN_RUNTIME_TYPE
		val eObjectToNameFeature = element.findNameFeature
		addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value,
			CLF_EXT_PROVIDED_BY_RUNTIME_IN_RUNTIME_TYPE)
		return false;

	}

	def private validateNoObservableAtClassifier(ExportDeclaration ed, N4ClassifierDeclaration declaration,
		String classesOrRolesOrInterface) {
		if (AnnotationDefinition.OBSERVABLE.hasAnnotation(ed)) {
			val message = getMessageForCLF_EXT_NO_OBSERV_ANNO(classesOrRolesOrInterface)
			val eObjectToNameFeature = declaration.findNameFeature
			addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value, CLF_EXT_NO_OBSERV_ANNO)
		}
	}

	def private validateMembers(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		validateNoBody(declaration, classesOrRolesOrInterface)
		validateNoFieldExpression(declaration, classesOrRolesOrInterface)
		validateNoObservableAtMember(declaration, classesOrRolesOrInterface)
		validateNoNfonAtMember(declaration, classesOrRolesOrInterface)
	}

	private def validateNoObservableAtMember(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		for (member : declaration.ownedMembers.filter[AnnotationDefinition.OBSERVABLE.hasAnnotation(it)]) {
			val message = getMessageForCLF_EXT_METHOD_NO_ANNO(member.keyword.toFirstUpper + "s",
				classesOrRolesOrInterface, "Observable")
			addIssue(message, member, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_EXT_METHOD_NO_ANNO)
		}
	}

	private def validateNoNfonAtMember(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		for (member : declaration.ownedMembers.filter[AnnotationDefinition.NFON.hasAnnotation(it)]) {
			val message = getMessageForCLF_EXT_METHOD_NO_ANNO(member.keyword.toFirstUpper + "s",
				classesOrRolesOrInterface, "Nfon")
			addIssue(message, member, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_EXT_METHOD_NO_ANNO)
		}
	}

	private def validateNoFieldExpression(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		for (member : declaration.ownedFields.filter[expression !== null]) {
			val message = getMessageForCLF_EXT_NO_FIELD_EXPR(classesOrRolesOrInterface)
			addIssue(message, member, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_EXT_NO_FIELD_EXPR)
		}
	}

	private def validateNoBody(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		for (member : declaration.ownedMembers.filter[!(it instanceof N4FieldDeclaration)].filter [
			body !== null
		]) {
			val message = getMessageForCLF_EXT_NO_METHOD_BODY(member.keyword.toFirstUpper + "s",
				classesOrRolesOrInterface)
			addIssue(message, member, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_EXT_NO_METHOD_BODY)
		}
	}

	def private validateNonAnnotatedClassDoesntExtendN4Object(N4ClassDeclaration exported, TClass superType,
		ExportDeclaration eo) {
		if (superType !== null && ! superType.isExternal) {
			val message = messageForCLF_EXT_NOT_ANNOTATED_EXTEND_N4OBJECT
			val eObjectToNameFeature = eo.findNameFeature
			addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value,
				CLF_EXT_NOT_ANNOTATED_EXTEND_N4OBJECT)
		}
	}

	def private validateClassifierIsExternal(boolean external, String classifiers, ExportDeclaration eo) {
		if (!external) {
			val message = getMessageForCLF_EXT_EXTERNAL(classifiers)
			val eObjectToNameFeature = eo.findNameFeature
			addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value, CLF_EXT_EXTERNAL)
		}
	}

	def private validateExtensionOfNotAnnotatedClass(N4ClassDeclaration exportedWithN4JSAnnotation,
		ExportDeclaration eo) {
		val TClass superClass = exportedWithN4JSAnnotation.superClassType
		if (superClass !== null) {
			if (superClass.isExternal) {
				validateSuperClassAnnotatedWithN4JS(superClass, eo)
			}
		} else {
			val superType = exportedWithN4JSAnnotation.superClassRef?.declaredType
			if (superType !== null && !"N4Object".equals(superType.name) && !isSubtypeOfError(superType)) {
				handleSuperClassNotAnnotatedWithN4JS(eo)
			}
		}
	}

	/**
	 * Returns with {@code true} if the type argument is a subtype of Error. Could be direct or implicit subtype as well.
	 * 13.1 ExternalDeclarations, Constraints 144/c (External allowed occurrences)
	 *
	 * @see IDEBUG-512
	 */
	def private isSubtypeOfError(Type type) {
		var toPrototype = [Type t|if (t instanceof TObjectPrototype) t else null];
		var TObjectPrototype prototype = toPrototype.apply(type);
		while (null !== prototype) {
			val G = RuleEnvironmentExtensions.newRuleEnvironment(prototype);
			if (RuleEnvironmentExtensions.errorType(G) === prototype) {
				return true;
			}
			prototype = toPrototype.apply(prototype?.superType?.declaredType);
		}
		return false;
	}

	def private validateSuperClassAnnotatedWithN4JS(TClass classifier, ExportDeclaration eo) {
		if (!AnnotationDefinition.N4JS.hasAnnotation(classifier)) {
			handleSuperClassNotAnnotatedWithN4JS(eo)
		}
	}

	private def handleSuperClassNotAnnotatedWithN4JS(ExportDeclaration eo) {
		val message = messageForCLF_EXT_ANNOTATED_EXTEND
		val eObjectToNameFeature = eo.findNameFeature
		addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value, CLF_EXT_ANNOTATED_EXTEND)
	}

	def private getSuperClassType(N4ClassDeclaration exported) {
		exported.superClassRef?.hasExpectedTypes(TClass)
	}

	def private validateConsumptionOfNonAnnotatedInterfaces(Iterable<ParameterizedTypeRef> superInterfaces,
		ExportDeclaration eo, String classifiers) {
		for (TInterface tinterface : superInterfaces.map[hasExpectedTypes(TInterface)].filter[it !== null]) {
			validateConsumptionOfNonExternalInterface(tinterface, classifiers, eo)
			validateConsumptionOfNonAnnotatedRole(tinterface, classifiers, eo)
		}
	}

	private def validateConsumptionOfNonAnnotatedRole(TInterface consumedRole, String classifiers,
		ExportDeclaration eo) {
		if (!AnnotationDefinition.N4JS.hasAnnotation(consumedRole) &&
			consumedRole.typingStrategy != TypingStrategy.STRUCTURAL) {
			val message = getMessageForCLF_EXT_ANNOTATED_CONSUME(classifiers)
			val eObjectToNameFeature = eo.findNameFeature
			addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value, CLF_EXT_ANNOTATED_CONSUME)
		}
	}

	def private validateConsumptionOfNonExternalInterfaces(Iterable<ParameterizedTypeRef> superInterfaces,
		ExportDeclaration eo, String classifiers) {
		for (tinterface : superInterfaces.map[hasExpectedTypes(TInterface)].filter[it !== null]) {
			validateConsumptionOfNonExternalInterface(tinterface, classifiers, eo)
		}
	}

	private def validateConsumptionOfNonExternalInterface(TInterface tinterface, String classifiers,
		ExportDeclaration eo) {
		if (!tinterface.external && tinterface.typingStrategy !== TypingStrategy.STRUCTURAL) {
			val message = getMessageForCLF_EXT_CONSUME_NON_EXT(classifiers)
			val eObjectToNameFeature = eo.findNameFeature
			addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value, CLF_EXT_CONSUME_NON_EXT)
		}
	}

	def private handleUnallowedElement(EObject eo) {
		val message = messageForCLF_EXT_UNALLOWED_N4JSD
		val eObjectToNameFeature = eo.findNameFeature
		if (eObjectToNameFeature === null) {
			val offsetAndLength = eo.findOffsetAndLength
			addIssue(message, eo, offsetAndLength.key, offsetAndLength.value, CLF_EXT_UNALLOWED_N4JSD)
		} else {
			addIssue(message, eObjectToNameFeature.key, eObjectToNameFeature.value, CLF_EXT_UNALLOWED_N4JSD)
		}
	}

	/**
	 * Returns converted declared type of type reference if it matches the expected type. Otherwise, null is returned.
	 */
	def private <T extends Type> T hasExpectedTypes(ParameterizedTypeRef typeRef, Class<T> typeClazz) {
		val type = typeRef?.declaredType
		if (type !== null && typeClazz.isAssignableFrom(type.class)) {
			return type as T;
		}
		return null
	}

	def private boolean findUnallowedElement(EObject eo) {
		if (eo instanceof EmptyStatement) {
			return false;
		}
		if (eo instanceof ImportDeclaration) {
			return false;
		}
		if (eo instanceof Annotation) {
			return false; // concrete annotations are handled in N4JSAnnotationValidation
		}
		if (eo instanceof ExportDeclaration) {
			return findUnallowedElement(eo.exportedElement);
		}
		if (eo instanceof N4ClassDeclaration) {
			if (eo.external) {
				return false;
			}
		}
		if (eo instanceof N4InterfaceDeclaration) {
			if (eo.external || eo.typingStrategy == TypingStrategy.STRUCTURAL) {
				return false;
			}
		}
		if (eo instanceof N4EnumDeclaration) {
			if (eo.external) {
				return false;
			}
		}
		if (eo instanceof FunctionDeclaration) {
			if (eo.external) {
				return false;
			}
		}
		if (eo instanceof VariableStatement) {
			return false;
		}
		return true
	}

	def private boolean isStringBased(N4EnumDeclaration enumDecl) {
		return AnnotationDefinition.STRING_BASED.hasAnnotation(enumDecl);
	}
}
