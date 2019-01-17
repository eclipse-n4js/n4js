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
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.N4ClassDefinition
import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberAnnotationList
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.PropertyNameOwner
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.types.VoidType
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.util.Tuples
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.AnnotationDefinition.*
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.*
import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Validation of rules that apply to individual members of a classifier.<p>
 * 
 * Validation of rules about members:
 * <ul>
 * <li>if the rules require to take into account the other owned or inherited members of the
 *     containing classifier, then the validation is contained in {@link N4JSClassifierValidator},
 * <li>if they can be checked by only looking at each member individually, then the validation
 *     is contained here.
 * </ul>
 */
class N4JSMemberValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * NEEDED
	 * 
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	@Check
	def void checkN4MemberDeclaration(N4MemberDeclaration n4Member) {
		if (n4Member instanceof N4MemberAnnotationList) {

			// parent already checked
			return
		}
		val it = n4Member.definedTypeElement
		if (it === null) {
			return
		}
		internalCheckNameStartsWithDollar
		internalCheckAbstractAndFinal
		internalCheckAbstractAndPrivate
		internalCheckPrivateOrProjectWithInternalAnnotation(n4Member, it)
	}

	/*
	 * This check was previously covered by ASTStructureValidator#validateName, but since computed property names are
	 * only set during post processing as of IDE-2468, we now have to perform validations for computed property names
	 * here.
	 */
	@Check
	def void checkComputedPropertyName(PropertyNameOwner owner) {
		if (owner.hasComputedPropertyName) {
			val name = owner.name
			if (name !== null) {
				if (!owner.isValidName) {
					val message = IssueCodes.getMessageForAST_RESERVED_IDENTIFIER(name);
					addIssue(message, owner, PROPERTY_NAME_OWNER__DECLARED_NAME, IssueCodes.AST_RESERVED_IDENTIFIER);
				}
			}
		}
	}

	@Check
	def void checkN4FieldDeclaration(N4FieldDeclaration n4Field) {
		if (n4Field instanceof N4MemberAnnotationList) {

			// parent already checked
			return
		}
		val TMember member = n4Field.definedTypeElement
		if (member === null) {
			return
		}

		holdsMinimalMemberAccessModifier(member);
	}

	@Check
	def void checkN4MethodDeclaration(N4MethodDeclaration n4Method) {
		if (n4Method instanceof N4MemberAnnotationList) {

			// parent already checked
			return
		}

		holdsCallableConstructorConstraints(n4Method)

		// wrong parsed
		if (n4Method.definedTypeElement === null) {
			return
		}

		val tmethod = n4Method.definedTypeElement as TMethod

		holdsAbstractAndBodyPropertiesOfMethod(tmethod)
		holdsConstructorConstraints(tmethod)
	}

	@Check
	def void checkN4GetterDeclaration(N4GetterDeclaration n4Getter) {
		if (n4Getter instanceof N4MemberAnnotationList) {

			// parent already checked
			return
		}

		// wrong parsed
		if (n4Getter.definedGetter === null) {
			return
		}
		val it = n4Getter.definedGetter

		holdsAbstractAndBodyPropertiesOfMethod(it)

		internalCheckGetterType(n4Getter);
	}

	@Check
	def void checkN4SetterDeclaration(N4SetterDeclaration n4Setter) {
		if (n4Setter instanceof N4MemberAnnotationList) {

			// parent already checked
			return
		}
		if (n4Setter.definedSetter === null) {
			return
		}
		val it = n4Setter.definedSetter

		holdsAbstractAndBodyPropertiesOfMethod(it)
	}

	@Check
	def void checkN4StructuralWithOnTypeVariables(ParameterizedTypeRefStructural ptrs) {
		if (!(ptrs.declaredType instanceof TypeVariable))
			return;

		for (sm : ptrs.astStructuralMembers) {
			val message = IssueCodes.getMessageForTYS_ADDITIONAL_STRUCTURAL_MEMBERS_ON_TYPE_VARS()
			addIssue(message, sm, TYS_ADDITIONAL_STRUCTURAL_MEMBERS_ON_TYPE_VARS)
		}
	}

	def boolean holdsAbstractAndBodyPropertiesOfMethod(TMember accessorOrMethod) {
		return //
		holdsAbstractOrHasBody(accessorOrMethod) //
		&& holdsAbstractMethodMustHaveNoBody(accessorOrMethod) //
		&& holdsAbstractMethodMustNotBeStatic(accessorOrMethod) //
		&& holdsAbstractMemberContainedInAbstractClassifier(accessorOrMethod) //
		&& holdsMinimalMemberAccessModifier(accessorOrMethod) //
	}

	def private internalCheckNameStartsWithDollar(TMember member) {

		// don't validate this in external (i.e. n4jd) files
		if (!jsVariantHelper.requireCheckNameStartsWithDollar(member)) {
			return
		}

		val name = member?.name;

		// name may be null (invalid file), we do not need an NPE here
		if (name !== null && name.startsWith('$')) {
			val message = IssueCodes.getMessageForCLF_NAME_DOLLAR()
			addIssue(message, member.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_NAME_DOLLAR)
		}
	}

	def private internalCheckAbstractAndFinal(TMember member) {
		if (member.final) {
			if (member.abstract) {
				val message = IssueCodes.getMessageForCLF_ABSTRACT_FINAL(member.keyword)
				addIssue(message, member.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME, IssueCodes.CLF_ABSTRACT_FINAL)
			} else if (member.containingType instanceof TInterface && !(member instanceof TMethod)) {
				val message = IssueCodes.getMessageForCLF_NO_FINAL_INTERFACE_MEMBER()
				addIssue(message, member.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME,
					IssueCodes.CLF_NO_FINAL_INTERFACE_MEMBER)
			}
		}
	}
	
	def private boolean internalCheckAbstractAndPrivate(TMember member) {
		if (member.abstract && member.memberAccessModifier == MemberAccessModifier.PRIVATE) {
			val message = IssueCodes.getMessageForCLF_ABSTRACT_PRIVATE(member.keyword)
			addIssue(message, member.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME, IssueCodes.CLF_ABSTRACT_PRIVATE)
			return false;
		}
		return true;
	}


	def private boolean holdsConstructorConstraints(TMethod method) {
		if (method.constructor) {
			if (!holdsConstructorInInterfaceDoesNotHaveBody(method)) {
				return false;
			}
			if (!holdsConstructorInInterfaceRequiresCovarianceAnnotation(method)) {
				return false;
			}
			if (!holdsConstructorNoReturnType(method)) {
				return false;
			}
			if (!holdsConstructorNoTypeParameters(method)) {
				return false;
			}
			var result = holdsConstructorModifiers(method);
			return holdsRequiredExplicitSuperCallIsFound(method) && result;
		}
		return true;
	}

	/**
	 * N4JS spec constraints 51.1
	 */
	private def holdsConstructorModifiers(TMethod constructor) {
		if ((constructor.abstract && !(constructor.containingType instanceof TInterface)) // ctor in interface may be abstract (actually it *must* be abstract)
		|| constructor.static || constructor.final || constructor.hasIllegalOverride) {
			val message = getMessageForCLF_CTOR_ILLEGAL_MODIFIER
			addIssue(message, constructor.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_CTOR_ILLEGAL_MODIFIER)
			return false;
		}
		return true;
	}

	/** @return true if not a static polyfill but has a {@code @Override} annotation. */
	private def boolean getHasIllegalOverride(TMethod constructor) {
		(! constructor.containingType.isStaticPolyfill) &&
			AnnotationDefinition.OVERRIDE.hasAnnotation(constructor.astElement as N4MethodDeclaration)
	}

	/**
	 * Requirement 56 (Defining and Calling Constructors), #5.a
	 */
	private def boolean holdsConstructorInInterfaceDoesNotHaveBody(TMethod constructor) {
		if (constructor.containingType instanceof TInterface && !constructor.hasNoBody) {
			addIssue(getMessageForITF_CONSTRUCTOR_BODY, constructor.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME,
				ITF_CONSTRUCTOR_BODY);
			return false;
		}
		return true;
	}

	/**
	 * Requirement 56 (Defining and Calling Constructors), #5.b
	 */
	private def boolean holdsConstructorInInterfaceRequiresCovarianceAnnotation(TMethod constructor) {
		val container = constructor.containingType;
		if (container instanceof TInterface && !N4JSLanguageUtils.hasCovariantConstructor(container as TInterface)) {
			addIssue(getMessageForITF_CONSTRUCTOR_COVARIANCE, constructor.astElement,
				PROPERTY_NAME_OWNER__DECLARED_NAME, ITF_CONSTRUCTOR_COVARIANCE);
			return false;
		}
		return true;
	}

	/**
	 * Requirement 56 (Defining and Calling Constructors), #6
	 */
	private def boolean holdsConstructorNoReturnType(TMethod constructor) {
		val constructorDecl = constructor.astElement as N4MethodDeclaration;
		if (constructorDecl.returnTypeRef !== null) {
			addIssue(getMessageForCLF_CTOR_RETURN_TYPE, constructorDecl, FUNCTION_DEFINITION__RETURN_TYPE_REF,
				CLF_CTOR_RETURN_TYPE);
			return false;
		}
		return true;
	}

	/**
	 * Requirement 56 (Defining and Calling Constructors), #8
	 */
	private def holdsConstructorNoTypeParameters(TMethod method) {
		if (method.typeVars.isEmpty) {
			return true;
		}
		
		val constructorDecl = method.astElement as N4MethodDeclaration;
		val offsetLength = findTypeVariablesOffset(constructorDecl);

		addIssue(IssueCodes.messageForCLF_CTOR_NO_TYPE_PARAMETERS, constructorDecl, offsetLength.key,
			offsetLength.value, IssueCodes.CLF_CTOR_NO_TYPE_PARAMETERS);

		return false;
	}

	/** 
	 * Determines the offset and length of the full list of type variable of the given {@link GenericDeclaration}.
	 * 
	 * This does not include the delimiting characters '<' and '>'.
	 * 
	 * @throws IllegalArgumentException if the GenericDeclaration does not have any type variables. 
	 */
	private def Pair<Integer, Integer> findTypeVariablesOffset(GenericDeclaration genericDeclaration) {
		if (genericDeclaration.typeVars.empty) {
			throw new IllegalArgumentException(
				"Cannot determine offset of type variables for a GenericDeclaration without any type variables.")
		}

		val typeVariableNodes = NodeModelUtils.findNodesForFeature(genericDeclaration, GENERIC_DECLARATION__TYPE_VARS);
		val firstTypeVariable = typeVariableNodes.get(0);
		val lastTypeVariable = typeVariableNodes.last;

		return Pair.of(firstTypeVariable.offset, lastTypeVariable.offset + lastTypeVariable.length - firstTypeVariable.offset);
	}

	/**
	 * N4JS spec constraints 44.3
	 */
	private def boolean holdsRequiredExplicitSuperCallIsFound(TMethod constructor) {
		if ((constructor.astElement as N4MemberDeclaration).body !== null) { // otherwise another validation will complain
			val type = constructor.eContainer;
			if (type instanceof TClass) { // otherwise another validation will complain
				val G = constructor.newRuleEnvironment;
				val superClass = G.getDeclaredOrImplicitSuperType(type);
				val ctor = containerTypesHelper.fromContext(constructor).findConstructor(superClass);
				if (ctor !== null && ctor !== G.objectType.ownedCtor) {
					if (ctor.fpars.size > 0) {
						val existsSuperCall = (constructor.astElement as N4MethodDeclaration).existsExplicitSuperCall();
						if (! existsSuperCall) {
							val className = (ctor.eContainer as IdentifiableElement).name;
							addIssue(
								getMessageForKEY_SUP_REQUIRE_EXPLICIT_SUPERCTOR_CALL(className),
								constructor.astElement,
								PROPERTY_NAME_OWNER__DECLARED_NAME,
								KEY_SUP_REQUIRE_EXPLICIT_SUPERCTOR_CALL
							)
							return false;
						} // else: ok so far, more checks on super call are performed in N4JSExpressionValidator
					}
				}
			}
		}
		return true;
	}

	def private boolean holdsCallableConstructorConstraints(N4MethodDeclaration method) {
		if (method.isCallableConstructor) {
			// constraint: only in classes
			if (!(method.eContainer instanceof N4ClassDefinition)) {
				addIssue(getMessageForCLF_CTOR_CALLABLE_ONLY_IN_CLASS, method, CLF_CTOR_CALLABLE_ONLY_IN_CLASS);
				return false;
			}
			// constraint: only in .n4jsd files
			if (!jsVariantHelper.isExternalMode(method)) {
				addIssue(getMessageForCLF_CTOR_CALLABLE_ONLY_IN_N4JSD, method, CLF_CTOR_CALLABLE_ONLY_IN_N4JSD);
				return false;
			}
			// constraint: not more than one callable constructor per class
			if ((method.eContainer as N4ClassifierDefinition).ownedMembersRaw.filter[isCallableConstructor].size >= 2) {
				addIssue(getMessageForCLF_CTOR_CALLABLE_DUPLICATE, method, CLF_CTOR_CALLABLE_DUPLICATE);
			}
		}
		return true;
	}

	def private boolean holdsAbstractOrHasBody(TMember member) {
		val requireCheckForMissingBody = jsVariantHelper.requireCheckForMissingBody(member);
		val memberIsAbstract = switch (member) {
			TMethod: member.isAbstract()
			FieldAccessor: member.isAbstract()
			default: false
		};
		if (requireCheckForMissingBody && !memberIsAbstract &&
			(member.astElement as N4MemberDeclaration).body === null) {
			if (member.isConstructor) {
				addIssue(messageForCLF_MISSING_CTOR_BODY, member.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME,
					IssueCodes.CLF_MISSING_CTOR_BODY)
			} else {
				val message = IssueCodes.getMessageForCLF_MISSING_BODY(member.keyword, member.name)
				addIssue(message, member.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME, IssueCodes.CLF_MISSING_BODY)
			}
			return false;
		}
		return true;
	}

	/**
	 * Constraints 49: abstract methods/getters/setters must not be static and vice versa.
	 */
	def private boolean holdsAbstractMethodMustNotBeStatic(TMember member) {
		if (member.abstract && member.static) {
			addIssue(getMessageForCLF_STATIC_ABSTRACT(member.keyword, member.name), member.astElement,
				PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_STATIC_ABSTRACT)
			return false;
		}
		return true;
	}

	def private boolean holdsAbstractMethodMustHaveNoBody(TMember member) {
		if (member.abstract && (member.astElement as N4MemberDeclaration).body !== null) {
			val message = IssueCodes.getMessageForCLF_ABSTRACT_BODY
			addIssue(message, member.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME, IssueCodes.CLF_ABSTRACT_BODY)
			return false;
		}
		return true;
	}

	def private boolean holdsAbstractMemberContainedInAbstractClassifier(TMember member) {
		if (member.abstract) {
			val classifier = EcoreUtil2.getContainerOfType(member, TClassifier)
			if (classifier !== null && !classifier.abstract) {
				val message = IssueCodes.getMessageForCLF_ABSTRACT_MISSING(member.keyword, member.name, classifier.name)
				addIssue(message, member.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME,
					IssueCodes.CLF_ABSTRACT_MISSING)
				return false;
			}
		}
		return true;
	}

	/**
	 * Internally, internal project and internal private do not exist. (IDEBUG-658)
	 */
	def private void internalCheckPrivateOrProjectWithInternalAnnotation(N4MemberDeclaration n4Member,
		TMember tmember) {
		if (AnnotationDefinition.INTERNAL.hasAnnotation(n4Member)) {
			val memberAccessModifier = tmember.memberAccessModifier
			val hasPrivateModifier = (memberAccessModifier === MemberAccessModifier.PRIVATE)
			val hasProjectModifier = (memberAccessModifier === MemberAccessModifier.PROJECT)
			if (hasPrivateModifier || hasProjectModifier) {
				val message = IssueCodes.getMessageForCLF_INTERNAL_BAD_WITH_PRIVATE_OR_PROJECT();
				addIssue(message, tmember.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME,
					IssueCodes.CLF_INTERNAL_BAD_WITH_PRIVATE_OR_PROJECT);
			}
		}
	}

	/**
	 * Constraint 44.3: Members of an interface must not be declared private or project
	 */
	private def boolean holdsMinimalMemberAccessModifier(TMember member) {
		if (member.containingType instanceof TInterface) {
			val memberAccessModifier = member.memberAccessModifier
			if (memberAccessModifier === MemberAccessModifier.PRIVATE) {
				val message = IssueCodes.getMessageForCLF_MINIMAL_ACCESSIBILITY_IN_INTERFACES();
				addIssue(message, member.astElement, PROPERTY_NAME_OWNER__DECLARED_NAME,
					IssueCodes.CLF_MINIMAL_ACCESSIBILITY_IN_INTERFACES);
				return false;
			}
		}
		return true;
	}

	def private internalCheckGetterType(N4GetterDeclaration n4GetterDeclaration) {
		val getterType = n4GetterDeclaration.declaredTypeRef?.declaredType;
		if (getterType !== null && getterType instanceof VoidType) {
			val message = IssueCodes.messageForCLF_VOID_ACCESSOR;
			addIssue(message, n4GetterDeclaration, PROPERTY_NAME_OWNER__DECLARED_NAME, IssueCodes.CLF_VOID_ACCESSOR);
		}
	}

	@Check
	def checkDuplicateFieldsIn(ThisTypeRefStructural thisTypeRefStructural) {
		val n4ClassifierDefinition = EcoreUtil2.getContainerOfType(thisTypeRefStructural, N4ClassifierDefinition)
		if (n4ClassifierDefinition !== null) {
			val tClass = n4ClassifierDefinition.definedType
			if (tClass instanceof TClass) {
				internalCheckDuplicateFieldsIn(tClass, thisTypeRefStructural)
			}
		}
	}

	private def internalCheckDuplicateFieldsIn(TClass tclass, ThisTypeRefStructural thisTypeRefStructural) {
		val structFieldInitMode = thisTypeRefStructural.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
		val members = LazyOverrideAwareMemberCollector.collectAllMembers(tclass)
		val membersByNameAndStatic = members.groupBy[Tuples.pair(name, static)];
		val structuralMembersByNameAndStatic = thisTypeRefStructural.structuralMembers.groupBy [
			Tuples.pair(name, static)
		];
		structuralMembersByNameAndStatic.keySet.forEach [
			if (membersByNameAndStatic.containsKey(it)) {
				val structuralFieldDuplicate = structuralMembersByNameAndStatic.get(it).head
				val existingClassifierMember = membersByNameAndStatic.get(it).headExceptNonExistantGetters(structFieldInitMode)
				if (existingClassifierMember?.memberAccessModifier == MemberAccessModifier.PUBLIC) {
					val message = getMessageForCLF_DUP_MEMBER(structuralFieldDuplicate.descriptionWithLine,
						existingClassifierMember.descriptionWithLine);
					val index = thisTypeRefStructural.structuralMembers.indexOf(structuralFieldDuplicate)
					addIssue(message, thisTypeRefStructural,
						TypeRefsPackage.Literals.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS, index, CLF_DUP_MEMBER)
				}
			}
		]
	}

	private def TMember headExceptNonExistantGetters(Iterable<? extends TMember> members, boolean structFieldInitMode) {
		if (structFieldInitMode) {
			return members.filter[!(it instanceof TGetter)].head;
		}
		return members.head;
	}

	/**
	 * A list of annotations that requires {@code @Test} annotation as well.
	 * Currently used by {@link #checkFixmeUsedWithTestAnnotation(N4MethodDeclaration)} validation method.
	 */
	val static ANNOTATIONS_REQUIRE_TEST = #[TEST_FIXME, TEST_IGNORE];

	/**
	 * Raises an issue if a method has any arbitrary annotation that requires {@Test} annotation as well but it does not have it.
	 * Currently:
	 * <ul>
	 * <li>{@code @Ignore} and</li>
	 * <li>{@code @Fixme}</li>
	 * </ul>
	 * requires {@code @Test} annotation.
	 */
	@Check
	def checkFixmeUsedWithTestAnnotation(N4MethodDeclaration methodDecl) {
		ANNOTATIONS_REQUIRE_TEST.forEach [ annotation |
			if (annotation.hasAnnotation(methodDecl) && !TEST_METHOD.hasAnnotation(methodDecl)) {
				addIssue(getMessageForANN_REQUIRES_TEST('''@«annotation.name»'''), methodDecl.annotations.findFirst [
					name == annotation.name
				], N4JSPackage.eINSTANCE.annotation_Name, ANN_REQUIRES_TEST);
			}
		];
	}

}
