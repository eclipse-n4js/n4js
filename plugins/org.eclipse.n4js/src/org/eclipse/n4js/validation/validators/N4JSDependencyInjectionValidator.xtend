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
import org.eclipse.n4js.n4JS.Annotation
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.types.BuiltInType
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TAnnotation
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TN4Classifier
import org.eclipse.n4js.ts.types.TObjectPrototype
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ts.types.VirtualBaseType
import org.eclipse.n4js.ts.types.util.AllSuperTypesCollector
import org.eclipse.n4js.ts.types.util.SuperInterfacesIterable
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.TypeSystemHelper
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.concurrent.atomic.AtomicBoolean
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.AnnotationDefinition.*
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.*
import static org.eclipse.n4js.ts.types.TypingStrategy.*
import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Validations related to dependency injection (covering annotations and instantiations).
 * <p>
 * For other DI-related validations (covering invocations of N4Injector methods) see {@link N4JSInjectorCallsitesValidator}
 */
class N4JSDependencyInjectionValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;

	@Inject
	private extension ContainerTypesHelper;

	@Inject
	private extension IScopeProvider;

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
	 * In case the argument refers to a class declaration, return its TClass. Otherwise null.
	 */
	public static def TClass tClassOf(TypeRef ref) {
		if (null !== ref) {
			if (ref.declaredType instanceof TClass) {
				return ref.declaredType as TClass;
			}
		}
		return null
	}

	/**
	 * "new C" is invalid if:
	 * <ul>
	 * <li>C declares or inherits some member(s) annotated (at)Inject.</li>
	 * <li>C or a super-type is marked (at)Injected, ie an API project marks a type so that implementing projects may subclass it adding injected members.</li>
	 * </ul>
	 */
	@Check
	def checkNewExpression(NewExpression newExpression) {
		if (newExpression?.callee === null)
			return; // invalid AST

		val typeRef = ts.tau(newExpression.callee)
		if (typeRef === null)
			return; // invalid AST
		if (typeRef instanceof UnknownTypeRef)
			return; // suppress error message in case of UnknownTypeRef

		val G = newExpression.newRuleEnvironment;
		val staticType = if (typeRef instanceof TypeTypeRef) tsh.getStaticType(G, typeRef) else null;
		if (staticType === null || staticType.eIsProxy)
			return;
		if (!(staticType instanceof TClass)) {
			// in "new C" ignore C being TObjectPrototype or TInterface
			return;
		}

		val tClazz = staticType as TClass;
		if (requiresInjection(tClazz)) {
			addIssue(getMessageForDI_MUST_BE_INJECTED(tClazz.typeAsString),
				newExpression, N4JSPackage.eINSTANCE.newExpression_Callee, DI_MUST_BE_INJECTED);
		}
		if (isMarkedInjected(tClazz)) {
			addIssue(getMessageForDI_API_INJECTED(),
				newExpression, N4JSPackage.eINSTANCE.newExpression_Callee, DI_API_INJECTED);
		}
	}


	@Check
	def checkDependencyInjectionAnnotation(Annotation ann) {
		switch(ann.name) {
		case GENERATE_INJECTOR.name:
			internalCheckAnnotationInjector(ann)
		case BINDER.name:
			internalCheckAnnotationBinder(ann)
		case BIND.name:
			internalCheckAnnotationBind(ann)
		case WITH_PARENT_INJECTOR.name:
			internalCheckAnnotationWithParentInjector(ann)
		case USE_BINDER.name:
			internalCheckAnnotationUseBinder(ann)
		case PROVIDES.name:
			internalCheckAnnotationProvides(ann)
		case INJECT.name:
			internalCheckAnnotationInject(ann)
		case INJECTED.name:
			internalCheckAnnotationInjected(ann)
		}
	}

	/**
	 * In addition to the validations invoked directly from this method,
	 * further validations (applicable to the formal params of an injected ctor)
	 * are checked in {@link #internalCheckAnnotationInject}.
	 */
	@Check
	def checkCtor(N4MethodDeclaration it) {
		if (null !== it && constructor
			&& internalCheckCtorReferencesInjectedFields
			&& internalCheckCtorInjectedWhenParentInjected
		) {
			// Validation is done at condition level.
		}
	}

	/**
	 * A constructor that's not annotated (at)Inject (in our case, the argument)
	 * breaks the injection-chain if some inherited constructors exist
	 * that are marked (at)Inject.
	 *
	 * TODO clarification: is it "inherited constructors exist" or "other constructors --- inherited or not ---"
	 *
	 * @return true iff no issues were found.
	 */
	private def boolean internalCheckCtorInjectedWhenParentInjected(N4MethodDeclaration ctor) {
		if (!INJECT.hasAnnotation(ctor)) {
			val currentType = ctor?.owner?.definedType;
			if (currentType instanceof ContainerType<?>) {
				// Get all super constructors (if any) which is annotated with @Inject.
				val injectedParentInjectors = ctor.fromContext.allMembers(currentType).filter(TMethod)
					.filter[constructor && it !== ctor.definedType && INJECT.hasAnnotation(it)];
				if (!injectedParentInjectors.empty) {
					val currentName = currentType.name;
					val superName = injectedParentInjectors.get(0)?.containingType?.name;
					addIssue(getMessageForDI_CTOR_BREAKS_INJECTION_CHAIN(superName, currentName),
						ctor, PROPERTY_NAME_OWNER__DECLARED_NAME, DI_CTOR_BREAKS_INJECTION_CHAIN
					)
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Scan the statements of ctor, focusing on field-reads of the form "this.f" where f is injected. For each of them:
	 * <ul>
	 * <il>the ctor itself must be marked (at)Inject,</il>
	 * <il>and a formal param must exist whose type is (a subtype of) that of the field "f" being read.</il>
	 * </ul>
	 *
	 * @return true iff no issues were found.
	 */
	private def boolean internalCheckCtorReferencesInjectedFields(N4MethodDeclaration ctor) {
		if (null === ctor.body) {
			return true
		}
		val valid = new AtomicBoolean(true);
		// scan each field-read of the form "this.f" where f is injected
		ctor.body.allStatements.forEach[stmt|
			EcoreUtil.getAllContents(stmt, false).forEach[propAcc|
				if (isPropAccessOfInterest(propAcc)) {
					if (!holdsCtorReferencesInjectedField(ctor, propAcc as ParameterizedPropertyAccessExpression)) {
						valid.set(false);
					}
				}
			]
		];
		return valid.get
	}

	/**
	 * For a given field-read of the form "this.f" where f is injected. Check whether:
	 * <ul>
	 * <il>the ctor itself is marked (at)Inject,</il>
	 * <il>a formal param must exist whose type is (a subtype of) that of the field "f" being read.</il>
	 * </ul>
	 *
	 * @return true iff no issues were found.
	 */
	private def boolean holdsCtorReferencesInjectedField(N4MethodDeclaration ctor, ParameterizedPropertyAccessExpression propAccess) {
		var isValid = true;
		val accessedField = propAccess.property as TField;
		val isInjectedCtor = INJECT.hasAnnotation(ctor)
		if (!isInjectedCtor) {
			// read-access "this.f" where f is injected is valid only in a constructor that is itself marked (at)Inject
			addIssue(getMessageForDI_FIELD_IS_NOT_INJECTED_YET(accessedField.name), propAccess, DI_FIELD_IS_NOT_INJECTED_YET);
			isValid = false;
		} else {
			// some param must exist whose type conforms to that of the injected field being read.
			val G = newRuleEnvironment(ctor);
			val someParamSubtypesFieldType = ctor.fpars.map[declaredTypeRef].exists[
				declaredType === accessedField?.typeRef?.declaredType
				|| ts.subtypeSucceeded(G, it, accessedField?.typeRef)
			];
			if (!someParamSubtypesFieldType) {
				addIssue(getMessageForDI_FIELD_IS_NOT_INJECTED_YET(accessedField.name), propAccess, DI_FIELD_IS_NOT_INJECTED_YET);
				isValid = false;
			}
		}
		return isValid
	}

	/**
	 * Is the argument a field-read of the form "this.f" where f is injected?
	 */
	private def boolean isPropAccessOfInterest(EObject eo) {
		if (!(eo instanceof ParameterizedPropertyAccessExpression)) {
			return false
		}
		val propAcc = eo as ParameterizedPropertyAccessExpression;
		if (propAcc.property instanceof TField) {
			if (!isLhsInFieldAssignment(propAcc)) {
				if (propAcc.target instanceof ThisLiteral) {
					val accessedField = propAcc.property as TField;
					return (INJECT.hasAnnotation(accessedField) && accessedField.isVisibleAt(propAcc))
				}
			}
		}
		return false
	}

	/**
	 * Does the argument occur as LHS in a field assignment?
	 */
	// XXX field write access is ignored for now.
	private def boolean isLhsInFieldAssignment(ParameterizedPropertyAccessExpression it) {
		if (eContainer instanceof AssignmentExpression) {
			val assignExp = eContainer as AssignmentExpression;
			if (assignExp.lhs === it && it.property instanceof TField) {
				return true
			}
		}
		false
	}

	private def boolean isVisibleAt(TField field, ParameterizedPropertyAccessExpression expr) {
		// Check if field is visible
		val scope = expr.getScope(N4JSPackage.eINSTANCE.parameterizedPropertyAccessExpression_Property);
		// We already have some validation issue for the field (for instance not visible), do not produce a new one.
		return !IEObjectDescriptionWithError.isErrorDescription(scope.getSingleElement(field));
	}

	/**
	 * A class annotated (at)GenerateInjector:
	 * <ul>
	 * <li>may not extend another class</li>
	 * <li>may not use binders that, taken together, contain duplicates</li>
	 * <li>the constructor of DIC is either arg-less or injected</li>
	 * </ul>
	 *
	 * As per Sec. 11.2.1 "DI Components and Injectors"
	 *
	 * IDE-1563 is about relaxing the first constraint above:
	 * once a class is marked as injector, all its subclasses must be injectors too.
	 */
	private def void internalCheckAnnotationInjector(Annotation ann) {
		val injtorClassDecl = getAnnotatedClass(ann)
		if (null === injtorClassDecl) {
			return // invalid AST
		}
		if(injtorClassDecl.superClassRef !== null){
			addIssue(getMessageForDI_ANN_INJECTOR_EXTENDS(), ann, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_INJECTOR_EXTENDS);
		}
		// collect binding across all binders used by the injector of interest
		val usedBindersAnns = USE_BINDER.getAllAnnotations(injtorClassDecl)
		val collectedBindings = new ArrayList<Binding_UseBinder>
		usedBindersAnns.forEach[usedBinderAnn|
			val binderTypeRef = usedBinderAnn.getArgAsTypeRef(0);
			val binderTClass = tClassOf(binderTypeRef);
			if (null !== binderTClass) {
				val tbindings = BIND.getAllAnnotations(binderTClass)
				tbindings.forEach[tbinding|
					collectedBindings.add(new Binding_UseBinder(tbinding, usedBinderAnn))
				]
			}
		]
		internalCheckNoDupBindingsAcrossBinders(collectedBindings, newRuleEnvironment(injtorClassDecl))
		// the constructor of DIC is either arg-less or injected
		val injtorCtor = injtorClassDecl.ownedCtor
		if ((null !== injtorCtor) && !(injtorCtor.fpars.isEmpty)) {
			if (!INJECT.hasAnnotation(injtorCtor)) {
				addIssue(getMessageForDI_ANN_INJECTOR_CTOR_MUST_BE_INJECT(), injtorCtor, DI_ANN_INJECTOR_CTOR_MUST_BE_INJECT);
			}
		}
	}

	/**
	 * In source code, dicTClass stands for a class marked (at)GenerateInjector
	 * with one or more (at)UseBinder(binderClass_i) annotations.
	 * This method returns those binder classes.
	 *
	 * TODO reuse this method in N4JSDependencyInjectionValidator.internalCheckAnnotationInjector
	 */
	public static def Iterable<TClass> usedBindersOf(TClass dicTClass) {
		val usedBindersAnns = USE_BINDER.getAllAnnotations(dicTClass)
		usedBindersAnns.map[usedBinderAnn|
			val binderTypeRef = getArgAsTypeRef(usedBinderAnn, 0);
			tClassOf(binderTypeRef)
		]
	}

	/**
	 * Record-like class to track the association between a binding and the UseBinder annotation
	 * that brings it into the configuration of an injector of interest (not shown).
	 */
	static class Binding_UseBinder {
		final TAnnotation bindingTAnn
		final Annotation useBinderAnn
		new(TAnnotation bindingTAnn, Annotation useBinderAnn) {
			this.bindingTAnn = bindingTAnn;
			this.useBinderAnn = useBinderAnn;
		}
	}

	/**
	 * All of the given bindings are part of the configuration of a single injector.
	 * Duplicate keys are detected, and errors are issued at the (a)UseBinder annotations
	 * that brought the duplicate bindings into the configuration.
	 */
	private def void internalCheckNoDupBindingsAcrossBinders(
		List<Binding_UseBinder> collectedBindings,
		RuleEnvironment G
	) {
		// maps the key of a binding to the UseBinder annotation that brought it into the injector
		val seen = new HashMap<TypeRef, Annotation>
		collectedBindings.forEach[bindingTAnnAndItsUseAnn|
			val bindingTAnn = bindingTAnnAndItsUseAnn.bindingTAnn;
			val useBinderAnn = bindingTAnnAndItsUseAnn.useBinderAnn;
			val extra = bindingTAnn.getArgAsTypeRef(0);
			if (!hasStructuralFlavor(extra)) {
				// TODO for now we simply ignore those keys with STRUCTURAL and STRUCTURAL_FIELD typing strategy
				// comparing structurally leads to flagging as duplicates, say, two different classes lacking members
				val dupBinding = getDuplicate(extra, seen, G)
				if (null !== dupBinding) {
					addIssue(getMessageForDI_ANN_DUPLICATE_BINDING(), useBinderAnn, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_DUPLICATE_BINDING);
					addIssue(getMessageForDI_ANN_DUPLICATE_BINDING(), dupBinding, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_DUPLICATE_BINDING);
				} else {
					seen.put(extra, useBinderAnn)
				}
			}
		]
	}

	/**
	 * The N4ClassDefinition annotated by (at)Binder:
	 * <ul>
	 * <li>must not be abstract</li>
	 * <li>must not extend another class</li>
	 * <li>must not be annotated with (at)GenerateInjector</li>
	 * </ul>
	 * Additionally, duplicate bindings aren't allowed (ie, different (at)Bind annotations with the same key).
	 * This method looks for duplicate bindings within the binder of interest.
	 * Method {@link #internalCheckAnnotationInjector} considers all binders used by the given injector and detects duplicate bindings across all of them.
	 *
	 * As per Sec. 11.2.6.4 "N4JS DI (at)Binder".
	 */
	private def void internalCheckAnnotationBinder(Annotation binderAnn) {
		// TODO this assumes an ExportDeclaration may be annotated @Binder. Fix javadoc if so. Fix error message if not.
		val binderClassDecl = getAnnotatedClass(binderAnn);
		if(null === binderClassDecl || binderClassDecl.isAbstract){
			addIssue(getMessageForDI_ANN_BINDER_NOT_APPLICABLE(), binderAnn, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_BINDER_NOT_APPLICABLE);
			return
		}
		if(binderClassDecl.superClassRef !== null){
			addIssue(getMessageForDI_ANN_BINDER_EXTENDS(), binderAnn, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_BINDER_EXTENDS);
		}
		val binderTClazz = binderClassDecl.definedTypeAsClass;
		if (GENERATE_INJECTOR.hasAnnotation(binderTClazz)) {
			addIssue(getMessageForDI_ANN_BINDER_AND_INJECTOR_DONT_GO_TOGETHER(), binderAnn, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_BINDER_AND_INJECTOR_DONT_GO_TOGETHER);
		}
		internalCheckNoDupBindings(BIND.getAllAnnotations(binderClassDecl), newRuleEnvironment(binderClassDecl))
	}

	/**
	 * The arguments are (at)Bind annotations. In case two different bindings share the same key an issue is reported.
	 */
	private def void internalCheckNoDupBindings(Iterable<Annotation> bindings, RuleEnvironment G) {
		// maps the key of a binding to the binding in question (useful for error messages)
		val seen = new HashMap<TypeRef, Annotation>
		bindings.forEach[binding|
			val extra = binding.getArgAsTypeRef(0);
			if (!hasStructuralFlavor(extra)) {
				// TODO for now we simply ignore those keys with STRUCTURAL and STRUCTURAL_FIELD typing strategy
				// comparing structurally leads to flagging as duplicates, say, two different classes lacking members
				val dupBinding = getDuplicate(extra, seen, G)
				if (null !== dupBinding) {
					addIssue(getMessageForDI_ANN_DUPLICATE_BINDING(), binding, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_DUPLICATE_BINDING);
					addIssue(getMessageForDI_ANN_DUPLICATE_BINDING(), dupBinding, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_DUPLICATE_BINDING);
				} else {
					seen.put(extra, binding)
				}
			}
		]
	}

	/**
	 * Is the just-encountered key ("extra") among those already seen? If so, return the annotation for its duplicate
	 * (ie, the binding or useBinder annotation that brought that duplicate into the configuration).
	 * Otherwise null.
	 */
	private def Annotation getDuplicate(TypeRef extra, HashMap<TypeRef, Annotation> seen, RuleEnvironment G) {
		val iter = seen.entrySet.iterator
		while (iter.hasNext) {
			val oldEntry = iter.next
			val TypeRef old = oldEntry.key
			if (ts.equaltypeSucceeded(G, old, extra)) {
				return oldEntry.value
			}
		}
		return null
	}

	private static def boolean hasStructuralFlavor(TypeRef typeRef) {
		val ts = typeRef.typingStrategy
		return (STRUCTURAL == ts) || (STRUCTURAL_FIELDS == ts);
	}

	/**
	 * Bind annotation:
	 * <ul>
	 * <li>occurs in tandem with (at)Binder on the ExportDeclaration or N4ClassDeclaration it annotates</li>
	 * <li>its first argument (key) denotes an injectable type</li>
	 * <li>its second argument (target) subtypes the first one, is itself injectable, and moreover is non-abstract</li>
	 * </ul>
	 */
	private def void internalCheckAnnotationBind(Annotation ann) {
		ann.holdsAnnotatedTClassIsAnnotatedWith(BINDER)
		&& ann.holdsIsInjectableType(ann.getArgAsTypeRef(0))
		&& ann.holdsSecondArgIsSubtypeOfFirstArg
		// note: must also ensure that 2nd arg is injectable, because it might well be
		// a subtype of first but may still not be injectable (e.g. use site structural)
		&& holdsHasValidTargetType(ann)
	}

	/**
	 * WithParentInjector annotation:
	 * <ul>
	 * <li>occurs in tandem with (at)GenerateInjector on the ExportDeclaration or N4ClassDeclaration it annotates</li>
	 * <li>its first argument refers to an injector</li>
	 * <li>holdsNoCycleBetweenUsedInjectors</li>
	 * </ul>
	 */
	private def void internalCheckAnnotationWithParentInjector(Annotation ann) {
		ann.holdsAnnotatedTClassIsAnnotatedWith(GENERATE_INJECTOR)
		&& ann.holdsArgumentIsTypeRefToTClassAnnotatedWith(GENERATE_INJECTOR)
		&& ann.holdsNoCycleBetweenUsedInjectors;
	}

	private def boolean holdsNoCycleBetweenUsedInjectors(Annotation it) {
		var type = typeOfUseInjector;
		if (null !== type) {
			val visitedTypes = newArrayList(type.name);
			var annotation = type.annotations.findFirst[WITH_PARENT_INJECTOR.name == name];
			while (null !== annotation) {
				type = annotation.typeOfUseInjector;
				if (null === type) {
					annotation = null;
				} else {
					val indexOf = visitedTypes.indexOf(type.name);
					if (indexOf > -1) {
						addIssue(
							getMessageForDI_ANN_USE_INJECTOR_CYCLE('''«visitedTypes.join(' > ')» > «type.name»'''),
							it,
							N4JSPackage.eINSTANCE.annotation_Name,
							DI_ANN_USE_INJECTOR_CYCLE
						);
						return false
					}
					visitedTypes.add(type.name)
					annotation = type.annotations.findFirst[WITH_PARENT_INJECTOR.name == name];
				}
			}
		}
		return true
	}

	private def Type typeOfUseInjector(Annotation it) {
		if (WITH_PARENT_INJECTOR.name == name && !args.nullOrEmpty && args.head instanceof TypeRefAnnotationArgument) {
			val arg = args.head as TypeRefAnnotationArgument;
			if (arg.typeRef instanceof ParameterizedTypeRef) {
				return (arg.typeRef as ParameterizedTypeRef).declaredType;
			}
		}
		return null;
	}

	private def Type typeOfUseInjector(TAnnotation it) {
		if (WITH_PARENT_INJECTOR.name == name && !args.nullOrEmpty && args.head instanceof TAnnotationTypeRefArgument) {
			val arg = args.head as TAnnotationTypeRefArgument;
			if (arg.typeRef instanceof ParameterizedTypeRef) {
				return (arg.typeRef as ParameterizedTypeRef).declaredType;
			}
		}
		return null;
	}

	/**
	 * UseBinder annotation:
	 * <ul>
	 * <li>occurs in tandem with (at)GenerateInjector on the class it annotates</li>
	 * <li>its first argument refers to a binder</li>
	 * </ul>
	 */
	private def void internalCheckAnnotationUseBinder(Annotation ann) {
		ann.holdsAnnotatedTClassIsAnnotatedWith(GENERATE_INJECTOR)
		&& ann.holdsArgumentIsTypeRefToTClassAnnotatedWith(BINDER);
	}

	/**
	 * Provides annotates a method that:
	 * <ul>
	 * <li>belongs to a binder</li>
	 * <li>has zero or more params whose types are all injectable</li>
	 * <li>has a non-void, injectable return type</li>
	 * </ul>
	 */
	private def void internalCheckAnnotationProvides(Annotation ann) {
		ann.holdsAnnotatedTMethodIsContainedInTClassAnnotatedWith(BINDER)
		&& ann.holdsAnnotatedTMethodHasCorrectSignature()
	}

	/**
	 * Inject annotation marks a field or constructor that may not belong to an interface and that:
	 * <ul>
	 * <li>for a field: has injectable type and neither such type nor any of its super-classes is an injector</li>
	 * <li>for a method or constructor:
	 * <ul>
	 * <li>has zero or more params whose types are all injectable (the return type doesn't matter)</li>
	 * <li>no param is variadic</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * Method injection is not supported yet (an error is issued upon attempting to inject a method).
	 *
	 * TODO injection point bindings need to be resolvable
	 * (that check requires determining at compile time the injector in effect).
	 */
	private def void internalCheckAnnotationInject(Annotation ann) {
		val annElem = ann.annotatedElement;

		//check the container (classifier decl) of the annotated element (ie, field or constructor)
		val annElemCont = annElem.eContainer;
		if(annElemCont instanceof N4InterfaceDeclaration){
			addIssue(getMessageForDI_ANN_INTERFACE_INJECTION_NOT_SUPPORTED(), ann, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_INTERFACE_INJECTION_NOT_SUPPORTED);
			//no return, do other checks
		}

		if (annElem instanceof N4FieldDeclaration) {
			if (annElem.definedTypeElement instanceof TField) {
				val field = annElem.definedTypeElement as TField
				val ref = field.typeRef;
				var TClass clazz = tClassOf(ref);
				if (null !== clazz) {
					var valid = true;
					while (null !== clazz) {
						if (!valid) {
							clazz = null;
						} else {
							valid = !AnnotationDefinition.GENERATE_INJECTOR.hasAnnotation(clazz)
							if (!valid) {
								addIssue(
									getMessageForDI_ANN_INJECTOR_CANNOT_BE_INJECTED_INTO_INJECTOR(),
									ann,
									N4JSPackage.eINSTANCE.annotation_Name,
									DI_ANN_INJECTOR_CANNOT_BE_INJECTED_INTO_INJECTOR
								);
							}
							val type = clazz?.superClassRef?.declaredType;
							clazz = if (type instanceof TClass) type else null;
						}
					}
					if (!valid) {
						return;
					}
				}
			}
		}

		//check annotated element (field or constructor)
		val defMember = if (annElem instanceof N4MemberDeclaration) annElem.definedTypeElement else null;
		switch (defMember) {
			TField: ann.holdsIsInjectableType(defMember.typeRef, defMember.name)
			TMethod: {
				if(defMember.constructor) {
					defMember.fpars.forEach[ann.holdsIsInjectableType(it)]
				} else {
					// method injection not supported yet
					addIssue(getMessageForDI_ANN_INJECT_METHOD_NOT_SUPPORTED_YET(),
							 ann, N4JSPackage.eINSTANCE.annotation_Name,
							 DI_ANN_INJECT_METHOD_NOT_SUPPORTED_YET)
				}
			}
		};
	}

	/**
	 * The class marked by the given annotation should also carry another (required) annotation.
	 * For example, an (at)Bind annotation may only mark a class that's also annotated (at)Binder.
	 *
	 * @return true iff no issues were found
	 */
	private def boolean holdsAnnotatedTClassIsAnnotatedWith(Annotation ann, AnnotationDefinition requiredDef) {
		val classDecl = getAnnotatedClass(ann);
		val tClass = classDecl?.definedType;
		if(tClass!==null && !requiredDef.hasAnnotation(tClass)) {
			addIssue(getMessageForDI_ANN_ONLY_ON_CLASS_ANNOTATED_WITH(ann.name,requiredDef.name),
				ann, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_ONLY_ON_CLASS_ANNOTATED_WITH);
			return false;
		}
		return true;
	}

	/**
	 * The method marked by the given annotation belong to a class that in turn should carry another (required) annotation.
	 * For example, an (at)Provides annotation may only mark a method of an (at)Binder class.
	 *
	 * @return true iff no issues were found
	 */
	private def boolean holdsAnnotatedTMethodIsContainedInTClassAnnotatedWith(Annotation ann, AnnotationDefinition requiredDef) {
		val methodDecl = getAnnotatedMethod(ann);
		val tClass = methodDecl?.definedType?.eContainer;
		if(tClass instanceof TClass && !requiredDef.hasAnnotation(tClass as TClass)) {
			addIssue(getMessageForDI_ANN_ONLY_ON_METHOD_IN_CLASS_ANNOTATED_WITH(ann.name,requiredDef.name),
				ann, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_ONLY_ON_METHOD_IN_CLASS_ANNOTATED_WITH);
			return false;
		}
		return true;
	}

	/**
	 * The method annotated by the argument:
	 * <ul>
	 * <li>has zero or more params whose types are all injectable</li>
	 * <li>has a non-void, injectable return type</li>
	 * </ul>
	 *
	 * @return true iff no issues were found
	 */
	private def boolean holdsAnnotatedTMethodHasCorrectSignature(Annotation ann) {
		val methodDecl = getAnnotatedMethod(ann);
		val tMethod = methodDecl?.definedType;
		if(tMethod instanceof TMethod) {
			val nonInjectableParams = tMethod.fpars.filter[fpar| !(fpar.typeRef.isInjectableType)]
			nonInjectableParams.forEach[fpar|
				addIssue(
					getMessageForDI_NOT_INJECTABLE(fpar.typeRef.typeRefAsString, '''at «fpar.name»'''),
					fpar.astElement,
					TypesPackage.eINSTANCE.identifiableElement_Name,
					DI_NOT_INJECTABLE
				);
			];

			if (!(nonInjectableParams.isEmpty)) {
				return false;
			}

			val retTR = tMethod.returnTypeRef
			val isVoidOrOptional = TypeUtils.isVoid(retTR) || tMethod.isReturnValueOptional;
			if(isVoidOrOptional) {
				addIssue(messageForDI_ANN_PROVIDES_METHOD_MUST_RETURN_VALUE,
					methodDecl, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, DI_ANN_PROVIDES_METHOD_MUST_RETURN_VALUE);
				return false;
			}
			return ann.holdsIsInjectableType(tMethod.returnTypeRef)
		}
		return true;
	}

	/**
	 * The annotation's first argument is expected to refer to a class marked as requested.
	 * If not, issue an error.
	 */
	private def holdsArgumentIsTypeRefToTClassAnnotatedWith(Annotation ann, AnnotationDefinition requiredDef) {
		val arg = if(!ann.args.empty) ann.args.get(0) else null;
		if (null === arg) {
			return true // invalid AST
		}
		val argTypeRef = if(arg instanceof TypeRefAnnotationArgument) arg.typeRef else null;
		if(!isTypeRefToTClassAnnotatedWith(argTypeRef, requiredDef)) {
			addIssue(getMessageForDI_ANN_ARG_MUST_BE_ANNOTATED_WITH(ann.name,requiredDef.name),
				arg, DI_ANN_ARG_MUST_BE_ANNOTATED_WITH);
			return false;
		}
		return true;
	}

	/**
	 * Is the type-declaration annotated as requested?
	 */
	private static def boolean isTypeRefToTClassAnnotatedWith(TypeRef typeRef, AnnotationDefinition requiredAnn) {
		val Type tclazz = tClassOf(typeRef);
		return (null !== tclazz) && requiredAnn.hasAnnotation(tclazz);
	}

	/**
	 * The (binding) annotation's second arg (target) is expected to subtype the first arg (key).
	 * If not, issue an error.
	 */
	private def holdsSecondArgIsSubtypeOfFirstArg(Annotation ann) {
		if(ann?.eResource?.resourceSet===null)
			return true;
		val RuleEnvironment G = newRuleEnvironment(ann);
		val arg0TypeRef = ann.getArgAsTypeRef(0);
		val arg1TypeRef = ann.getArgAsTypeRef(1);
		if(arg0TypeRef!==null && arg1TypeRef!==null && !ts.subtypeSucceeded(G, arg1TypeRef,arg0TypeRef)) {
			addIssue(getMessageForDI_ANN_BIND_SECOND_MUST_BE_SUBTYPE_FIRST(ann.name), ann.args.get(1), DI_ANN_BIND_SECOND_MUST_BE_SUBTYPE_FIRST)
			return false;
		}
		return true;
	}

	/**
	 * The given formal param belongs to an injected constructor. Such param is expected to be non-variadic, non-optional,
	 * and have an injectable type. If not, issue an error.
	 */
	private def holdsIsInjectableType(Annotation ann, TFormalParameter it) {
		if (null === it) {
			return true;
		}
		if (isVariadicOrOptional) {
			addIssue(getMessageForDI_VARARGS_NOT_INJECTABLE(), astElement,
				TypesPackage.eINSTANCE.identifiableElement_Name, DI_VARARGS_NOT_INJECTABLE
			);
			return false;
		}
		return ann.holdsIsInjectableType(typeRef, name);
	}

	private def boolean holdsIsInjectableType(Annotation ann, TypeRef typeRef) {
		ann.holdsIsInjectableType(typeRef, null as String)
	}

	/**
	 * A Bind annotation must have an injectable and moreover non-abstract target.
	 *
	 * @return true iff no issues were found
	 */
	private def boolean holdsHasValidTargetType(Annotation bindAnn) {
		val targetTypeRef = bindAnn.getArgAsTypeRef(1)
		if (targetTypeRef === null) {
			return false
		}
		if (!bindAnn.holdsIsInjectableType(targetTypeRef, null as String)) {
			return false
		}
		if (!isConcrete(targetTypeRef)) {
			addIssue(getMessageForDI_ANN_BIND_ABSTRACT_TARGET(), bindAnn, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_BIND_ABSTRACT_TARGET);
			return false
		}
		return true
	}

	/**
	 * Does the argument denote a concrete class? (ie, non-abstract class, non-interface)
	 */
	private static def boolean isConcrete(TypeRef typeRef) {
		return isConcrete(typeRef.declaredType)
	}

	/**
	 * Is the argument a concrete class? (ie, non-abstract class, non-interface)
	 */
	private static def boolean isConcrete(Type declType) {
		return switch(declType) {
			TClass: !declType.abstract
			default: false
		}
	}

	/**
	 * The typeRef argument is expected to denote an injectable type.
	 * If not, issue an error at the position of the annotation.
	 *
	 * @return true iff no issues were found
	 */
	private def boolean holdsIsInjectableType(Annotation ann, TypeRef typeRef, String name) {
		if (typeRef === null) {
			return true;
		}
		if (typeRef.declaredType !== null ) {
			if (typeRef.declaredType.eIsProxy) {
				return true; // avoid duplicate errors in case of "Couldn't resolve reference to Type 'A'." errors!
			}
		}
		if (!typeRef.isInjectableType) {
			val description = if (null === name) '''''' else '''at «name»''';
			addIssue(getMessageForDI_NOT_INJECTABLE(typeRef.typeRefAsString, description), ann, DI_NOT_INJECTABLE);
			return false;
		}
		return true;
	}


	/**
	 * See N4JS specification, Section 11.1.5 "N4JS DI Injectable Values".
	 *
	 * Summing up: for a value to injectable it must:
	 * <ul>
	 * <li>either conform to a user-defined class or interface (a non-parameterized one, that is),</li>
	 * <li>or conform to Provider-of-T where T is injectable itself</li>
	 * </ul>
	 */
	public static def boolean isInjectableType(TypeArgument typeArg) {
		// must be non-null, a ParameterizedTypeRef and have a declared type
		// (sorts out things like ComposedTypeRefs, ClassifierTypeRefs, etc.)
		//can be structural type (DefSite and UseSite)
		if (!(typeArg instanceof ParameterizedTypeRef)) {
			return false;
		}
		val typeRef = typeArg as ParameterizedTypeRef;
		val declType = typeRef.declaredType;
		if (declType===null) {
			return false;
		}
		// declared type must not be a primitive
		if (declType instanceof PrimitiveType) {
			return false;
		}
		// declared type must not be a built-in type
		if (declType instanceof BuiltInType || declType instanceof TObjectPrototype || declType instanceof VirtualBaseType) {
			return false;
		}
		// declared type must be a class or interface
		// (sorts out primitives, built-in types, enums, object prototype types, virtual base types.)
		if (!(declType instanceof TN4Classifier)) {
			return false;
		}
		// classifiers with generic types cannot be injected but providers can be
		if (declType instanceof TN4Classifier && !declType.providerType) {
			if (!declType.typeVars.nullOrEmpty) {
				return false;
			}
		}
		if (declType.providerType) {
			// a (subtype of) Provider without type-args is itself injectable
			// a Provider-of-T is injectable only if T is injectable
			var targs = typeRef.typeArgs.filter(TypeRef)
			if (targs.size === 1) {
				if (targs.head.injectableType) {
					return true;
				}
			}
			return targs.isEmpty
		}
		// must not be field structural typed
		if (STRUCTURAL_FIELDS === typeRef.typingStrategy) {
			return false;
		}
		// must not be a type variable
		if (declType instanceof TypeVariable) {
			return false;
		}
		return true;
	}

	/**
	 * Is the argument (a subtype of) the built-in N4Provider interface?
	 */
	private static def isProviderType(Type type) {
		if (type instanceof TN4Classifier) {
			val G = newRuleEnvironment(type);
			if (G.n4ProviderType === type) {
				return true;
			}
			SuperInterfacesIterable.of(type).exists[iface| iface === G.n4ProviderType]
		} else {
			false;
		}
	}

	/**
	 * The given annotation marks an (exported) class declaration, return it of so, otherwise null.
	 */
	private static def N4ClassDeclaration getAnnotatedClass(Annotation ann) {
		var elem = ann.annotatedElement;
		if(elem instanceof ExportDeclaration)
			elem = elem.exportedElement;
		if(elem instanceof N4ClassDeclaration)
			return elem;
		return null;
	}

	/**
	 * The given annotation marks a method declaration, return it of so, otherwise null.
	 */
	private static def N4MethodDeclaration getAnnotatedMethod(Annotation ann) {
		var elem = ann.annotatedElement;
		if(elem instanceof N4MethodDeclaration)
			return elem;
		return null;
	}

	/**
	 * If the annotation argument at the given index is a TypeRef, then returns
	 * that type reference; otherwise <code>null</code>.
	 */
	private static def TypeRef getArgAsTypeRef(Annotation ann, int index) {
		val arg = if(index < ann.args.size) ann.args.get(index) else null;
		if(arg instanceof TypeRefAnnotationArgument)
			return arg.typeRef;
		return null;
	}

	/**
	 * If the annotation argument at the given index is a TypeRef, then returns
	 * that type reference; otherwise <code>null</code>.
	 */
	private static def TypeRef getArgAsTypeRef(TAnnotation ann, int index) {
		val arg = if(index < ann.args.size) ann.args.get(index) else null;
		if(arg instanceof TAnnotationTypeRefArgument)
			return arg.typeRef;
		return null;
	}

	/**
	 * Does the given type inherit or declare some members annotated with (at)Inject?
	 */
	public static def boolean requiresInjection(TClass type) {
		// note: AllSuperTypesCollector ignores implicit super types and polyfills, but that is ok, because
		// we assume that members of implicit super types and polyfilled members won't have an @Inject
		// TODO use an 'all super types' iterator instead of collector
		AllSuperTypesCollector.collect(type)
		.exists[t|
			t.ownedMembers.exists[m|
				INJECT.hasAnnotation(m)
			]
		]
	}

	/**
	 * Is the given type (or some super-type) marked (at)Injected?
	 */
	public static def boolean isMarkedInjected(TClass type) {
		// TODO use an 'all super types' iterator instead of collector
		AllSuperTypesCollector.collect(type).exists[t| INJECTED.hasAnnotation(t)]
	}

	/**
	 * Injected annotates a class (abstract or not) defined in an API project.
	 */
	private def internalCheckAnnotationInjected(Annotation ann) {
		val classDecl = getAnnotatedClass(ann);
		if(!isInjectedApplicable(classDecl)){
			addIssue(getMessageForDI_ANN_INJECTED_NOT_APPLICABLE(), ann, N4JSPackage.eINSTANCE.annotation_Name, DI_ANN_INJECTED_NOT_APPLICABLE);
			return
		}
		// val tClass = classDecl?.definedTypeAsClass;
		// FIXME IDE-1686 how to check if classDecl belongs to an API project
	}

	/**
	 * Is it allowed to mark the given class declaration (at)Injected?
	 */
	private static def boolean isInjectedApplicable(N4ClassDeclaration classDecl) {
		return (null !== classDecl) && (null !== classDecl.definedTypeAsClass)
	}

}
