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
import java.util.List
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker
import org.eclipse.n4js.scoping.builtin.N4Scheme
import org.eclipse.n4js.scoping.members.TypingStrategyFilter
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueItem
import org.eclipse.n4js.validation.IssueUserDataKeys
import org.eclipse.n4js.validation.N4JSElementKeywordProvider
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static com.google.common.collect.Iterables.getOnlyElement
import static org.eclipse.n4js.AnnotationDefinition.*
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.*
import static org.eclipse.n4js.ts.types.TypingStrategy.*
import static org.eclipse.n4js.validation.IssueCodes.*
import static org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.*

/**
 * superfluous properties in {@code @Spec} constructor.
 */
class N4JSClassValidator extends AbstractN4JSDeclarativeValidator implements PolyfillValidatorHost {

	@Inject private N4JSTypeSystem ts;

	@Inject private PolyfillValidatorFragment polyfillValidatorFragment;

	@Inject private extension ContainerTypesHelper containerTypesHelper;

	@Inject private MemberVisibilityChecker memberVisibilityChecker;

	@Inject private N4JSElementKeywordProvider n4jsElementKeywordProvider;

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
	 * Non abstract test classes have to exported: IDEBUG-572.
	 */
	@Check
	def void checkTestClassExported(N4ClassDeclaration classDecl) {
		if (classDecl?.definedType instanceof TClass) {
			var clazz = classDecl.definedType as TClass;
			if (!clazz.abstract && !clazz.directlyExported && clazz.hasTestMethods) {
				addIssue(classDecl, N4_TYPE_DECLARATION__NAME, CLF_TEST_CLASS_NOT_EXPORTED.toIssueItem());
			}
		}
		if (classDecl.typingStrategy === TypingStrategy.STRUCTURAL) {
			addIssue(classDecl, N4JSPackage.eINSTANCE.n4ClassifierDeclaration_TypingStrategy, CLF_MUST_BE_NOMINAL.toIssueItem());
		}
	}

	// Get all transitive methods and checks if any of them has @Test annotation
	private def hasTestMethods(TClass clazz) {
		if (null !== clazz?.eResource) {
			val module = N4JSResource.getModule(clazz.eResource);
			if (null !== module) {
				return containerTypesHelper.fromContext(module).allMembers(clazz, false, false)
					.filter(TMethod).exists[TEST_METHOD.hasAnnotation(it)];
			}
		}
		return false;
	}

	/**
	 * Checks new expression.
	 */
	@Check
	def void checkSpecConstructorArguments(NewExpression newExpression) {
		if (!(newExpression.callee instanceof IdentifierRef)) {
			return;
		}
		val identifierRef = newExpression.callee as IdentifierRef;

		if (!(identifierRef.id instanceof TClass)) {
			return;
		}
		val tclass = identifierRef.id as TClass;
		val ctor = newExpression.fromContext.findConstructor(tclass);

		// ctor might be null if class has a super class that cannot be resolved
		if (null === ctor || ctor.fpars.isNullOrEmpty) {
			return;
		}

		// Collect constructor parameters with @Spec annotation.
		// Probably finding the first parameter with @Spec annotation would be
		// fine enough since using more than one is not allowed anyway.
		val fparsWithSpecAnnotation = ctor.fpars.filter[null !== SPEC.getAnnotation(it)].toList;

		if (fparsWithSpecAnnotation.isNullOrEmpty) {
			return;
		}

		if (fparsWithSpecAnnotation.size > 1) {
			return;
		}

		val fparWithSpecAnnotation = getOnlyElement(fparsWithSpecAnnotation);
		val typeRef = fparWithSpecAnnotation.typeRef;

		// Not a field structural 'this' reference, nothing to validate.
		if (!(typeRef instanceof ThisTypeRefStructural) || STRUCTURAL_FIELD_INITIALIZER !== typeRef.typingStrategy) {
			return;
		}

		val indexOfTheSpecFpar = ctor.fpars.indexOf(fparWithSpecAnnotation);

		// Possible broken AST or optional formal parameters. Abort validation process.
		if (0 > indexOfTheSpecFpar || newExpression.arguments.size <= indexOfTheSpecFpar) {
			return;
		}

		if (!(newExpression.arguments.get(indexOfTheSpecFpar)?.expression instanceof ObjectLiteral)) {
			return;
		}
		val objectLiteral = newExpression.arguments.get(indexOfTheSpecFpar)?.expression as ObjectLiteral;
		if (!(objectLiteral.definedType instanceof ContainerType<?>)) {
			return;
		}

		val strategy = new TypingStrategyFilter(STRUCTURAL);

		val publicWritableFieldsAndSetters = newExpression.fromContext.allMembers(tclass)
				.filter[member | member instanceof TField || member instanceof TSetter]
				.filter[strategy.apply(it)]
				.toMap[name];

		// These are available via the 'with' keyword add them to the accepted ones
		(typeRef as ThisTypeRefStructural).structuralMembers.filter(TField).forEach[
			publicWritableFieldsAndSetters.put(it.name, it);
		];

		// superfluous properties in initializer are checked in org.eclipse.n4js.validation.validators.N4JSTypeValidator.internalCheckSuperfluousPropertiesInObjectLiteral(..)
		checkFieldInitializationOfImplementedInterface(publicWritableFieldsAndSetters, objectLiteral);
	}



	/**
	 * Check if an object literal in {@code @Spec} constructor provide a property that comes from a built-in/provided by runtime interface.
	 * IDE-2747.
	 */
	def private void checkFieldInitializationOfImplementedInterface(Map<String, TMember> publicWritableFieldsMap, ObjectLiteral objectLiteral) {
		// For each property of object literal, check if it comes from a built-in/provided by runtime interface.
		val properties = (objectLiteral.definedType as ContainerType<?>).ownedMembers;
		properties.forEach[ property |
			// Search the corresponding field of the property
			val field = publicWritableFieldsMap.get(property.name)
			if (field !== null) {
				val containingClassifier = field.containingType;
				if (containingClassifier instanceof TInterface) {
					if (N4JSLanguageUtils.builtInOrProvidedByRuntime(containingClassifier)) {
						val IssueItem issueItem = CLF_SPEC_BUILT_IN_OR_PROVIDED_BY_RUNTIME_OR_EXTENAL_WITHOUT_N4JS_ANNOTATION.toIssueItem(field.name, containingClassifier.name);
						val feature = if ((property.astElement as PropertyNameValuePair).property === null) PROPERTY_NAME_OWNER__DECLARED_NAME
										else N4JSPackage.eINSTANCE.propertyNameValuePair_Property;
						addIssue(property.astElement, feature, issueItem);
					}
				}
			}
		];
	}

	@Check
	def void checkN4ClassDeclaration(N4ClassDeclaration n4Class) {

		// wrong parsed
		if (!(n4Class.definedType instanceof TClass)) {
			return;
		}

		if (polyfillValidatorFragment.holdsPolyfill(this, n4Class, getCancelIndicator())) {
			val tClass = n4Class.definedType as TClass;
			internalCheckAbstractFinal(tClass);

			if (holdsSuperClass(n4Class)) { // avoid consequential errors
				holdsNoCyclicInheritance(n4Class);
			}

			internalCheckPolyFilledClassWithAdditionalInterface(n4Class,this);
			internalCheckImplementedInterfaces(n4Class);
			internalCheckSpecAnnotation(n4Class);
		}
	}

	/**
	 * Overridden to be accessible from PolyfillValidatorFragment
	 */
	override void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
		String... issueData) {
		super.addIssue(message, source, feature, issueCode, issueData);
	}

	def private internalCheckAbstractFinal(TClass tClass) {
		if (tClass.abstract && tClass.final) {
			addIssue(tClass.astElement, N4_TYPE_DECLARATION__NAME, CLF_ABSTRACT_FINAL.toIssueItem("class"));
		}
	}

	def private boolean holdsSuperClass(N4ClassDeclaration n4Class) {
		val superTypeRef = n4Class.superClassRef?.typeRef;
		val superType = superTypeRef?.declaredType;
		if (superType !== null && superType.name !== null) { // note: in case superType.name===null, the type reference is completely invalid and other, more appropriate error messages have been created elsewhere

			if (superType instanceof PrimitiveType) {
				if (!N4Scheme.isFromResourceWithN4Scheme(n4Class)) { // primitive types may be extended in built-in types
					val IssueItem issueItem = CLF_EXTENDS_PRIMITIVE_GENERIC_TYPE.toIssueItem(superType.name);
					addIssue(n4Class.superClassRef, null, issueItem);
				}
			} else if (!(superType instanceof TClass)) {
				if (superType instanceof TInterface) {
					val IssueItem issueItem = SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP.toIssueItem(n4Class.description, "extend",
						superType.description, "implements");
					addIssue(n4Class.superClassRef, null, issueItem);
				} else {
					val IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(n4Class.description, "extend", superType.description);
					addIssue(n4Class.superClassRef, null, issueItem);
					return false;
				}
			} else if(superType instanceof TClass) {
				// (got a super class; now validate it ...)

				// super class must not be final (except in case of polyfills)
				if (superType.final && !(N4JSLanguageUtils.isNonStaticPolyfill(n4Class) || N4JSLanguageUtils.isStaticPolyfill(n4Class))) {

					val superTypeAstElement = superType.eGet(TypesPackage.eINSTANCE.syntaxRelatedTElement_AstElement,
						false) as EObject;
					val superClassUri = if (superTypeAstElement!==null) EcoreUtil.getURI(superTypeAstElement).toString;

					if (superClassUri !== null) {
						val IssueItem issueItem = CLF_EXTEND_FINAL.toIssueItemWithData(
							List.of(IssueUserDataKeys.CLF_EXTEND_FINAL.SUPER_TYPE_DECLARATION_URI, superClassUri),
							superType.name
						);
						addIssue(n4Class.superClassRef, null, issueItem);
					} else {
						val IssueItem issueItem = CLF_EXTEND_FINAL.toIssueItemWithData(
							List.of(IssueUserDataKeys.CLF_EXTEND_FINAL.SUPER_TYPE_DECLARATION_URI),
							superType.name);
						addIssue(n4Class.superClassRef, null, issueItem);
					}
					return false;
				}

				// ctor of super class must be accessible
				if (!holdsCtorOfSuperTypeIsAccessible(n4Class, superType)) {
					return false;
				}

				// if super class is observable, then this class must be observable as well
				if (superType.observable && !(n4Class.definedType as TClass).observable) {
					val IssueItem issueItem = CLF_OBSERVABLE_MISSING.toIssueItem(n4Class.name, superType.name);
					addIssue(n4Class, N4_TYPE_DECLARATION__NAME, issueItem);
					return false;
				}
			}
		} else if (superTypeRef !== null && superTypeRef.isAliasResolved) {
			// not all aliases are illegal after "extends", but if we get to this point we have an illegal case:
			val IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(n4Class.description, "extend", superTypeRef.typeRefAsStringWithAliasResolution);
			addIssue(n4Class.superClassRef, null, issueItem);
			return false;
		}
		return true;
	}

	def private holdsCtorOfSuperTypeIsAccessible(N4ClassDeclaration n4Class, TClassifier superType) {
		val receiverTypeRef = TypeUtils.createTypeRef(n4Class.definedType);
		val superCtor = containerTypesHelper.fromContext(n4Class).findConstructor(superType);
		if(superCtor!==null && !memberVisibilityChecker.isVisible(n4Class, receiverTypeRef, superCtor).visibility) {
			val IssueItem issueItem = CLF_EXTEND_NON_ACCESSIBLE_CTOR.toIssueItem(
				n4jsElementKeywordProvider.keyword(superType),
				superType.name);
			addIssue(n4Class, N4_CLASS_DEFINITION__SUPER_CLASS_REF, issueItem);
			return false;
		}
		return true;
	}

	def private internalCheckImplementedInterfaces(N4ClassDeclaration n4Class) {
		n4Class.implementedInterfaceRefs.forEach [
			val consumedTypeRef = it.typeRef;
			val consumedType = consumedTypeRef?.declaredType;
			if (consumedType !== null && consumedType.name !== null) { // note: in case consumedType.name===null, the type reference is completely invalid and other, more appropriate error messages have been created elsewhere

				// consumed type must be an interface
				if (!(consumedType instanceof TInterface)) {
					if (consumedType instanceof TClass && n4Class.superClassRef === null) {
						val IssueItem issueItem = SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP.toIssueItem(n4Class.description, "implement",
							consumedType.description, "extends");
						addIssue(it, null, issueItem);
					} else {
						val IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(n4Class.description, "implement",
							consumedType.description);
						addIssue(it, null, issueItem);
					}
				} else {
					val tIfc = consumedType as TInterface;
					val cth = containerTypesHelper.fromContext(n4Class);
					val hasCallConstructSig = cth.findCallSignature(tIfc) !== null || cth.findConstructSignature(tIfc) !== null;
					if (hasCallConstructSig) {
						addIssue(it, null, CLF_CALL_CONSTRUCT_SIG_CANNOT_IMPLEMENT.toIssueItem());
					}
				}
			} else if (consumedTypeRef !== null && consumedTypeRef.isAliasResolved) {
				// not all aliases are illegal after "implements", but if we get to this point we have an illegal case:
				val IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(n4Class.description, "implement", consumedTypeRef.typeRefAsStringWithAliasResolution);
				addIssue(it, null, issueItem);
			}
		]
	}

	def private internalCheckSpecAnnotation(N4ClassDeclaration n4ClassDeclaration) {
		val N4MethodDeclaration ctor = n4ClassDeclaration.ownedCtor;
		if (ctor !== null) {
			var specAnnotations = newArrayList;
			var int i = 0;
			for (FormalParameter currFPar : ctor.fpars) {
				val annSpec = SPEC.getAnnotation(currFPar);
				if (annSpec !== null) {
					specAnnotations.add(annSpec);
					val correctType = currFPar.declaredTypeRef instanceof ThisTypeRef
						&& STRUCTURAL_FIELD_INITIALIZER === currFPar.declaredTypeRef.typingStrategy;
					if (!correctType) {
						addIssue(annSpec, null, CLF_SPEC_WRONG_TYPE.toIssueItem());
					} else { // prevent consequential errors
						holdsAdditionalSpecFieldMatchesOwnedFields(
							n4ClassDeclaration,
							ctor,
							i
						);
					}
				}
				i = i + 1;
			}
			if (specAnnotations.size >= 2) {
				for (currAnnSpec : specAnnotations) {
					addIssue(currAnnSpec, null, CLF_SPEC_MULTIPLE.toIssueItem());
				}
			}
		}
	}

	def holdsAdditionalSpecFieldMatchesOwnedFields(
		N4ClassDeclaration n4ClassDeclaration,
		N4MethodDeclaration ctor,
		int parIndex
	) {
		val TClass tclass = n4ClassDeclaration.definedType as TClass;
		val fpars = (ctor.definedType as TFunction).fpars;
		if (parIndex >= fpars.size) return; //broken AST
		val TFormalParameter fpar = fpars.get(parIndex);
		val fparType = fpar.typeRef;
		val G = RuleEnvironmentExtensions.newRuleEnvironment(n4ClassDeclaration);

		var int memberIndex = 0;
		for (smember : fparType.structuralMembers) {
			val tfield = tclass.ownedMembers.findFirst[name == smember.name];
			if (tfield !== null && (tfield.isField || tfield.isSetter)) {
				val fieldType = ts.tau(tfield, TypeUtils.createTypeRef(tclass));
				val smemberType = ts.tau(smember, TypeUtils.createTypeRef(tclass));
				val subtypeRes = ts.subtype(G, smemberType, fieldType);
				if (subtypeRes.failure) {
					val IssueItem issueItem = CLF_SPEC_WRONG_ADD_MEMBERTYPE.toIssueItem(smember.name, description(tfield),
						trimTypesystemMessage(subtypeRes));
					val errMember = (ctor.fpars.get(parIndex).declaredTypeRefInAST as StructuralTypeRef).structuralMembers.
						get(memberIndex);
					val sourceObject = (if (errMember.astElement !== null) errMember.astElement else errMember);
					addIssue(sourceObject, issueItem);
				}

			}
			memberIndex = memberIndex + 1;

		}

	}

	def private boolean holdsNoCyclicInheritance(N4ClassDeclaration n4ClassDeclaration) {
		val cls = n4ClassDeclaration.definedType as TClassifier;
		val cycle = findCyclicInheritance(cls);
		if (cycle!==null) {
			val IssueItem issueItem = CLF_INHERITANCE_CYCLE.toIssueItem(cycle.map[name].join(", "));
			addIssue(n4ClassDeclaration, N4JSPackage.Literals.N4_CLASS_DEFINITION__SUPER_CLASS_REF, issueItem);
			return false;
		}
		return true;
	}
}
