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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.AnnotationDefinition.BIND;
import static org.eclipse.n4js.AnnotationDefinition.BINDER;
import static org.eclipse.n4js.AnnotationDefinition.GENERATE_INJECTOR;
import static org.eclipse.n4js.AnnotationDefinition.INJECT;
import static org.eclipse.n4js.AnnotationDefinition.INJECTED;
import static org.eclipse.n4js.AnnotationDefinition.PROVIDES;
import static org.eclipse.n4js.AnnotationDefinition.USE_BINDER;
import static org.eclipse.n4js.AnnotationDefinition.WITH_PARENT_INJECTOR;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_FIELDS;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4ProviderType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_ARG_MUST_BE_ANNOTATED_WITH;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_BINDER_AND_INJECTOR_DONT_GO_TOGETHER;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_BINDER_EXTENDS;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_BINDER_NOT_APPLICABLE;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_BIND_ABSTRACT_TARGET;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_BIND_SECOND_MUST_BE_SUBTYPE_FIRST;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_DUPLICATE_BINDING;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_INJECTED_NOT_APPLICABLE;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_INJECTOR_CANNOT_BE_INJECTED_INTO_INJECTOR;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_INJECTOR_CTOR_MUST_BE_INJECT;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_INJECTOR_EXTENDS;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_INJECT_METHOD_NOT_SUPPORTED_YET;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_INTERFACE_INJECTION_NOT_SUPPORTED;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_ONLY_ON_CLASS_ANNOTATED_WITH;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_ONLY_ON_METHOD_IN_CLASS_ANNOTATED_WITH;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_PROVIDES_METHOD_MUST_RETURN_VALUE;
import static org.eclipse.n4js.validation.IssueCodes.DI_ANN_USE_INJECTOR_CYCLE;
import static org.eclipse.n4js.validation.IssueCodes.DI_API_INJECTED;
import static org.eclipse.n4js.validation.IssueCodes.DI_CTOR_BREAKS_INJECTION_CHAIN;
import static org.eclipse.n4js.validation.IssueCodes.DI_FIELD_IS_NOT_INJECTED_YET;
import static org.eclipse.n4js.validation.IssueCodes.DI_MUST_BE_INJECTED;
import static org.eclipse.n4js.validation.IssueCodes.DI_NOT_INJECTABLE;
import static org.eclipse.n4js.validation.IssueCodes.DI_VARARGS_NOT_INJECTABLE;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isNullOrEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toIterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AnnotationArgument;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Statement;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.types.BuiltInType;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TAnnotationArgument;
import org.eclipse.n4js.ts.types.TAnnotationTypeRefArgument;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.ts.types.util.SuperInterfacesIterable;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.AllSuperTypesCollector;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * Validations related to dependency injection (covering annotations and instantiations).
 * <p>
 * For other DI-related validations (covering invocations of N4Injector methods) see
 * {@link N4JSInjectorCallsitesValidator}
 */
@SuppressWarnings("javadoc")
public class N4JSDependencyInjectionValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private DeclMergingHelper declMergingHelper;

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Inject
	private IScopeProvider scopeProvider;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * In case the argument refers to a class declaration, return its TClass. Otherwise null.
	 */
	public static TClass tClassOf(TypeRef ref) {
		if (null != ref) {
			if (ref.getDeclaredType() instanceof TClass) {
				return (TClass) ref.getDeclaredType();
			}
		}
		return null;
	}

	/**
	 * "new C" is invalid if:
	 * <ul>
	 * <li>C declares or inherits some member(s) annotated (at)Inject.</li>
	 * <li>C or a super-type is marked (at)Injected, ie an API project marks a type so that implementing projects may
	 * subclass it adding injected members.</li>
	 * </ul>
	 */
	@Check
	public void checkNewExpression(NewExpression newExpression) {
		if (newExpression == null || newExpression.getCallee() == null)
			return; // invalid AST

		TypeRef typeRef = ts.tau(newExpression.getCallee());
		if (typeRef == null)
			return; // invalid AST
		if (typeRef instanceof UnknownTypeRef)
			return; // suppress error message in case of UnknownTypeRef

		RuleEnvironment G = newRuleEnvironment(newExpression);
		Type staticType = (typeRef instanceof TypeTypeRef) ? tsh.getStaticType(G, (TypeTypeRef) typeRef) : null;
		if (staticType == null || staticType.eIsProxy()) {
			return;
		}
		if (!(staticType instanceof TClass)) {
			// in "new C" ignore C being TInterface, primitive type, etc.
			return;
		}

		TClass tClazz = (TClass) staticType;
		if (requiresInjection(tClazz, declMergingHelper)) {
			addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(),
					DI_MUST_BE_INJECTED.toIssueItem(tClazz.getTypeAsString()));
		}
		if (isMarkedInjected(tClazz, declMergingHelper)) {
			addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), DI_API_INJECTED.toIssueItem());
		}
	}

	@Check
	public void checkDependencyInjectionAnnotation(Annotation ann) {
		if (GENERATE_INJECTOR.name.equals(ann.getName())) {
			internalCheckAnnotationInjector(ann);
		} else if (BINDER.name.equals(ann.getName())) {
			internalCheckAnnotationBinder(ann);
		} else if (BIND.name.equals(ann.getName())) {
			internalCheckAnnotationBind(ann);
		} else if (WITH_PARENT_INJECTOR.name.equals(ann.getName())) {
			internalCheckAnnotationWithParentInjector(ann);
		} else if (USE_BINDER.name.equals(ann.getName())) {
			internalCheckAnnotationUseBinder(ann);
		} else if (PROVIDES.name.equals(ann.getName())) {
			internalCheckAnnotationProvides(ann);
		} else if (INJECT.name.equals(ann.getName())) {
			internalCheckAnnotationInject(ann);
		} else if (INJECTED.name.equals(ann.getName())) {
			internalCheckAnnotationInjected(ann);
		}
	}

	/**
	 * In addition to the validations invoked directly from this method, further validations (applicable to the formal
	 * params of an injected ctor) are checked in {@link #internalCheckAnnotationInject}.
	 */
	@Check
	public void checkCtor(N4MethodDeclaration it) {
		if (null != it && it.isConstructor()
				&& internalCheckCtorReferencesInjectedFields(it)
				&& internalCheckCtorInjectedWhenParentInjected(it)) {
			// Validation is done at condition level.
		}
	}

	/**
	 * A constructor that's not annotated (at)Inject (in our case, the argument) breaks the injection-chain if some
	 * inherited constructors exist that are marked (at)Inject.
	 *
	 * TODO clarification: is it "inherited constructors exist" or "other constructors --- inherited or not ---"
	 *
	 * @return true iff no issues were found.
	 */
	private boolean internalCheckCtorInjectedWhenParentInjected(N4MethodDeclaration ctor) {
		if (!INJECT.hasAnnotation(ctor)) {
			Type currentType = ctor == null || ctor.getOwner() == null ? null : ctor.getOwner().getDefinedType();
			if (currentType instanceof ContainerType<?> && ctor != null) {
				// Get all super constructors (if any) which is annotated with @Inject.
				MemberList<TMember> allMembers = containerTypesHelper.fromContext(ctor)
						.allMembers((ContainerType<?>) currentType);
				List<TMethod> injectedParentInjectors = toList(filter(filter(allMembers, TMethod.class),
						(it) -> it.isConstructor() && it != ctor.getDefinedType() && INJECT.hasAnnotation(it)));

				if (!injectedParentInjectors.isEmpty()) {
					String currentName = currentType.getName();
					TMethod firstM = injectedParentInjectors.get(0);
					String superName = firstM == null || firstM.getContainingType() == null ? null
							: firstM.getContainingType().getName();
					addIssue(ctor, PROPERTY_NAME_OWNER__DECLARED_NAME,
							DI_CTOR_BREAKS_INJECTION_CHAIN.toIssueItem(superName, currentName));
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Scan the statements of ctor, focusing on field-reads of the form "this.f" where f is injected. For each of them:
	 * <ul>
	 * <il>the ctor itself must be marked (at)Inject,</il> <il>and a formal param must exist whose type is (a subtype
	 * of) that of the field "f" being read.</il>
	 * </ul>
	 *
	 * @return true iff no issues were found.
	 */
	private boolean internalCheckCtorReferencesInjectedFields(N4MethodDeclaration ctor) {
		if (null == ctor.getBody()) {
			return true;
		}
		AtomicBoolean valid = new AtomicBoolean(true);
		// scan each field-read of the form "this.f" where f is injected
		for (Statement stmt : toIterable(ctor.getBody().getAllStatements())) {
			for (EObject propAcc : toIterable(EcoreUtil.<EObject> getAllContents(stmt, false))) {
				if (isPropAccessOfInterest(propAcc)) {
					if (!holdsCtorReferencesInjectedField(ctor, (ParameterizedPropertyAccessExpression) propAcc)) {
						valid.set(false);
					}
				}
			}
		}
		return valid.get();
	}

	/**
	 * For a given field-read of the form "this.f" where f is injected. Check whether:
	 * <ul>
	 * <il>the ctor itself is marked (at)Inject,</il> <il>a formal param must exist whose type is (a subtype of) that of
	 * the field "f" being read.</il>
	 * </ul>
	 *
	 * @return true iff no issues were found.
	 */
	private boolean holdsCtorReferencesInjectedField(N4MethodDeclaration ctor,
			ParameterizedPropertyAccessExpression propAccess) {
		boolean isValid = true;
		TField accessedField = (TField) propAccess.getProperty();
		boolean isInjectedCtor = INJECT.hasAnnotation(ctor);
		if (!isInjectedCtor) {
			// read-access "this.f" where f is injected is valid only in a constructor that is itself marked (at)Inject
			addIssue(propAccess, DI_FIELD_IS_NOT_INJECTED_YET.toIssueItem(accessedField.getName()));
			isValid = false;
		} else {
			// some param must exist whose type conforms to that of the injected field being read.
			RuleEnvironment G = newRuleEnvironment(ctor);
			boolean someParamSubtypesFieldType = exists(map(ctor.getFpars(), fp -> fp.getDeclaredTypeRef()), fp -> {
				TypeRef tRef = accessedField == null ? null : accessedField.getTypeRef();
				Type type = tRef == null ? null : tRef.getDeclaredType();
				return fp.getDeclaredType() == type || ts.subtypeSucceeded(G, fp, tRef);
			});
			if (!someParamSubtypesFieldType) {
				addIssue(propAccess, DI_FIELD_IS_NOT_INJECTED_YET.toIssueItem(accessedField.getName()));
				isValid = false;
			}
		}
		return isValid;
	}

	/**
	 * Is the argument a field-read of the form "this.f" where f is injected?
	 */
	private boolean isPropAccessOfInterest(EObject eo) {
		if (!(eo instanceof ParameterizedPropertyAccessExpression)) {
			return false;
		}
		ParameterizedPropertyAccessExpression propAcc = (ParameterizedPropertyAccessExpression) eo;
		if (propAcc.getProperty() instanceof TField) {
			if (!isLhsInFieldAssignment(propAcc)) {
				if (propAcc.getTarget() instanceof ThisLiteral) {
					TField accessedField = (TField) propAcc.getProperty();
					return (INJECT.hasAnnotation(accessedField) && isVisibleAt(accessedField, propAcc));
				}
			}
		}
		return false;
	}

	/**
	 * Does the argument occur as LHS in a field assignment?
	 */
	// XXX field write access is ignored for now.
	private boolean isLhsInFieldAssignment(ParameterizedPropertyAccessExpression it) {
		if (it.eContainer() instanceof AssignmentExpression) {
			AssignmentExpression assignExp = (AssignmentExpression) it.eContainer();
			if (assignExp.getLhs() == it && it.getProperty() instanceof TField) {
				return true;
			}
		}
		return false;
	}

	private boolean isVisibleAt(TField field, ParameterizedPropertyAccessExpression expr) {
		// Check if field is visible
		IScope scope = scopeProvider.getScope(expr,
				N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property());
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
	 * IDE-1563 is about relaxing the first constraint above: once a class is marked as injector, all its subclasses
	 * must be injectors too.
	 */
	private void internalCheckAnnotationInjector(Annotation ann) {
		N4ClassDeclaration injtorClassDecl = getAnnotatedClass(ann);
		if (null == injtorClassDecl) {
			return; // invalid AST
		}
		if (injtorClassDecl.getSuperClassRef() != null) {
			addIssue(ann, N4JSPackage.eINSTANCE.getAnnotation_Name(), DI_ANN_INJECTOR_EXTENDS.toIssueItem());
		}
		// collect binding across all binders used by the injector of interest
		Iterable<Annotation> usedBindersAnns = USE_BINDER.getAllAnnotations(injtorClassDecl);
		List<Binding_UseBinder> collectedBindings = new ArrayList<>();
		for (Annotation usedBinderAnn : usedBindersAnns) {
			TypeRef binderTypeRef = getArgAsTypeRef(usedBinderAnn, 0);
			TClass binderTClass = tClassOf(binderTypeRef);
			if (null != binderTClass) {
				Iterable<TAnnotation> tbindings = BIND.getAllAnnotations(binderTClass);
				for (TAnnotation tbinding : tbindings) {
					collectedBindings.add(new Binding_UseBinder(tbinding, usedBinderAnn));
				}
			}
		}
		internalCheckNoDupBindingsAcrossBinders(collectedBindings, newRuleEnvironment(injtorClassDecl));
		// the constructor of DIC is either arg-less or injected
		N4MethodDeclaration injtorCtor = injtorClassDecl.getOwnedCtor();
		if ((null != injtorCtor) && !(injtorCtor.getFpars().isEmpty())) {
			if (!INJECT.hasAnnotation(injtorCtor)) {
				addIssue(injtorCtor, DI_ANN_INJECTOR_CTOR_MUST_BE_INJECT.toIssueItem());
			}
		}
	}

	/**
	 * In source code, dicTClass stands for a class marked (at)GenerateInjector with one or more
	 * (at)UseBinder(binderClass_i) annotations. This method returns those binder classes.
	 *
	 * TODO reuse this method in N4JSDependencyInjectionValidator.internalCheckAnnotationInjector
	 */
	public static Iterable<TClass> usedBindersOf(TClass dicTClass) {
		Iterable<TAnnotation> usedBindersAnns = USE_BINDER.getAllAnnotations(dicTClass);
		return map(usedBindersAnns, usedBinderAnn -> tClassOf(getArgAsTypeRef(usedBinderAnn, 0)));
	}

	/**
	 * Record-like class to track the association between a binding and the UseBinder annotation that brings it into the
	 * configuration of an injector of interest (not shown).
	 */
	static class Binding_UseBinder {
		final TAnnotation bindingTAnn;
		final Annotation useBinderAnn;

		Binding_UseBinder(TAnnotation bindingTAnn, Annotation useBinderAnn) {
			this.bindingTAnn = bindingTAnn;
			this.useBinderAnn = useBinderAnn;
		}
	}

	/**
	 * All of the given bindings are part of the configuration of a single injector. Duplicate keys are detected, and
	 * errors are issued at the (a)UseBinder annotations that brought the duplicate bindings into the configuration.
	 */
	private void internalCheckNoDupBindingsAcrossBinders(
			List<Binding_UseBinder> collectedBindings,
			RuleEnvironment G) {
		// maps the key of a binding to the UseBinder annotation that brought it into the injector
		Map<TypeRef, Annotation> seen = new HashMap<>();
		for (Binding_UseBinder bindingTAnnAndItsUseAnn : collectedBindings) {
			TAnnotation bindingTAnn = bindingTAnnAndItsUseAnn.bindingTAnn;
			Annotation useBinderAnn = bindingTAnnAndItsUseAnn.useBinderAnn;
			TypeRef extra = getArgAsTypeRef(bindingTAnn, 0);
			if (!hasStructuralFlavor(extra)) {
				// TODO for now we simply ignore those keys with STRUCTURAL and STRUCTURAL_FIELD typing strategy
				// comparing structurally leads to flagging as duplicates, say, two different classes lacking members
				Annotation dupBinding = getDuplicate(extra, seen, G);
				if (null != dupBinding) {
					addIssue(useBinderAnn, N4JSPackage.eINSTANCE.getAnnotation_Name(),
							DI_ANN_DUPLICATE_BINDING.toIssueItem());
					addIssue(dupBinding, N4JSPackage.eINSTANCE.getAnnotation_Name(),
							DI_ANN_DUPLICATE_BINDING.toIssueItem());
				} else {
					seen.put(extra, useBinderAnn);
				}
			}
		}
	}

	/**
	 * The N4ClassDefinition annotated by (at)Binder:
	 * <ul>
	 * <li>must not be abstract</li>
	 * <li>must not extend another class</li>
	 * <li>must not be annotated with (at)GenerateInjector</li>
	 * </ul>
	 * Additionally, duplicate bindings aren't allowed (ie, different (at)Bind annotations with the same key). This
	 * method looks for duplicate bindings within the binder of interest. Method
	 * {@link #internalCheckAnnotationInjector} considers all binders used by the given injector and detects duplicate
	 * bindings across all of them.
	 *
	 * As per Sec. 11.2.6.4 "N4JS DI (at)Binder".
	 */
	private void internalCheckAnnotationBinder(Annotation binderAnn) {
		// TODO this assumes an ExportDeclaration may be annotated @Binder. Fix javadoc if so. Fix error message if not.
		N4ClassDeclaration binderClassDecl = getAnnotatedClass(binderAnn);
		if (null == binderClassDecl || binderClassDecl.isAbstract()) {
			addIssue(binderAnn, N4JSPackage.eINSTANCE.getAnnotation_Name(), DI_ANN_BINDER_NOT_APPLICABLE.toIssueItem());
			return;
		}
		if (binderClassDecl.getSuperClassRef() != null) {
			addIssue(binderAnn, N4JSPackage.eINSTANCE.getAnnotation_Name(), DI_ANN_BINDER_EXTENDS.toIssueItem());
		}
		TClass binderTClazz = binderClassDecl.getDefinedTypeAsClass();
		if (GENERATE_INJECTOR.hasAnnotation(binderTClazz)) {
			addIssue(binderAnn, N4JSPackage.eINSTANCE.getAnnotation_Name(),
					DI_ANN_BINDER_AND_INJECTOR_DONT_GO_TOGETHER.toIssueItem());
		}
		internalCheckNoDupBindings(BIND.getAllAnnotations(binderClassDecl), newRuleEnvironment(binderClassDecl));
	}

	/**
	 * The arguments are (at)Bind annotations. In case two different bindings share the same key an issue is reported.
	 */
	private void internalCheckNoDupBindings(Iterable<Annotation> bindings, RuleEnvironment G) {
		// maps the key of a binding to the binding in question (useful for error messages)
		Map<TypeRef, Annotation> seen = new HashMap<>();
		for (Annotation binding : bindings) {
			TypeRef extra = getArgAsTypeRef(binding, 0);
			if (!hasStructuralFlavor(extra)) {
				// TODO for now we simply ignore those keys with STRUCTURAL and STRUCTURAL_FIELD typing strategy
				// comparing structurally leads to flagging as duplicates, say, two different classes lacking members
				Annotation dupBinding = getDuplicate(extra, seen, G);
				if (null != dupBinding) {
					addIssue(binding, N4JSPackage.eINSTANCE.getAnnotation_Name(),
							DI_ANN_DUPLICATE_BINDING.toIssueItem());
					addIssue(dupBinding, N4JSPackage.eINSTANCE.getAnnotation_Name(),
							DI_ANN_DUPLICATE_BINDING.toIssueItem());
				} else {
					seen.put(extra, binding);
				}
			}
		}
	}

	/**
	 * Is the just-encountered key ("extra") among those already seen? If so, return the annotation for its duplicate
	 * (ie, the binding or useBinder annotation that brought that duplicate into the configuration). Otherwise null.
	 */
	private Annotation getDuplicate(TypeRef extra, Map<TypeRef, Annotation> seen, RuleEnvironment G) {
		Iterator<Entry<TypeRef, Annotation>> iter = seen.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<TypeRef, Annotation> oldEntry = iter.next();
			TypeRef old = oldEntry.getKey();
			if (ts.equaltypeSucceeded(G, old, extra)) {
				return oldEntry.getValue();
			}
		}
		return null;
	}

	private static boolean hasStructuralFlavor(TypeRef typeRef) {
		TypingStrategy ts = typeRef.getTypingStrategy();
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
	private boolean internalCheckAnnotationBind(Annotation ann) {
		return holdsAnnotatedTClassIsAnnotatedWith(ann, BINDER)
				&& holdsIsInjectableType(ann, getArgAsTypeRef(ann, 0))
				&& holdsSecondArgIsSubtypeOfFirstArg(ann)
				// note: must also ensure that 2nd arg is injectable, because it might well be
				// a subtype of first but may still not be injectable (e.g. use site structural)
				&& holdsHasValidTargetType(ann);
	}

	/**
	 * WithParentInjector annotation:
	 * <ul>
	 * <li>occurs in tandem with (at)GenerateInjector on the ExportDeclaration or N4ClassDeclaration it annotates</li>
	 * <li>its first argument refers to an injector</li>
	 * <li>holdsNoCycleBetweenUsedInjectors</li>
	 * </ul>
	 */
	private boolean internalCheckAnnotationWithParentInjector(Annotation ann) {
		return holdsAnnotatedTClassIsAnnotatedWith(ann, GENERATE_INJECTOR)
				&& holdsArgumentIsTypeRefToTClassAnnotatedWith(ann, GENERATE_INJECTOR)
				&& holdsNoCycleBetweenUsedInjectors(ann);
	}

	private boolean holdsNoCycleBetweenUsedInjectors(Annotation it) {
		Type type = typeOfUseInjector(it);
		if (null != type) {
			List<String> visitedTypes = new ArrayList<>();
			visitedTypes.add(type.getName());
			TAnnotation annotation = findFirst(type.getAnnotations(),
					an -> WITH_PARENT_INJECTOR.name.equals(an.getName()));
			while (null != annotation) {
				type = typeOfUseInjector(annotation);
				if (null == type) {
					annotation = null;
				} else {
					int indexOf = visitedTypes.indexOf(type.getName());
					if (indexOf > -1) {
						IssueItem issueItem = DI_ANN_USE_INJECTOR_CYCLE
								.toIssueItem("%s > %s".formatted(Strings.join(" > ", visitedTypes), type.getName()));
						addIssue(
								it,
								N4JSPackage.eINSTANCE.getAnnotation_Name(),
								issueItem);
						return false;
					}
					visitedTypes.add(type.getName());
					annotation = findFirst(type.getAnnotations(), an -> WITH_PARENT_INJECTOR.name.equals(an.getName()));
				}
			}
		}
		return true;
	}

	private Type typeOfUseInjector(Annotation it) {
		if (WITH_PARENT_INJECTOR.name.equals(it.getName()) && !isNullOrEmpty(it.getArgs())
				&& it.getArgs().get(0) instanceof TypeRefAnnotationArgument) {
			TypeRefAnnotationArgument arg = (TypeRefAnnotationArgument) it.getArgs().get(0);
			if (arg.getTypeRef() instanceof ParameterizedTypeRef) {
				return ((ParameterizedTypeRef) arg.getTypeRef()).getDeclaredType();
			}
		}
		return null;
	}

	private Type typeOfUseInjector(TAnnotation it) {
		if (WITH_PARENT_INJECTOR.name.equals(it.getName()) && !isNullOrEmpty(it.getArgs())
				&& it.getArgs().get(0) instanceof TAnnotationTypeRefArgument) {
			TAnnotationTypeRefArgument arg = (TAnnotationTypeRefArgument) it.getArgs().get(0);
			if (arg.getTypeRef() instanceof ParameterizedTypeRef) {
				return ((ParameterizedTypeRef) arg.getTypeRef()).getDeclaredType();
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
	private boolean internalCheckAnnotationUseBinder(Annotation ann) {
		return holdsAnnotatedTClassIsAnnotatedWith(ann, GENERATE_INJECTOR)
				&& holdsArgumentIsTypeRefToTClassAnnotatedWith(ann, BINDER);
	}

	/**
	 * Provides annotates a method that:
	 * <ul>
	 * <li>belongs to a binder</li>
	 * <li>has zero or more params whose types are all injectable</li>
	 * <li>has a non-void, injectable return type</li>
	 * </ul>
	 */
	private boolean internalCheckAnnotationProvides(Annotation ann) {
		return holdsAnnotatedTMethodIsContainedInTClassAnnotatedWith(ann, BINDER)
				&& holdsAnnotatedTMethodHasCorrectSignature(ann);
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
	 * TODO injection point bindings need to be resolvable (that check requires determining at compile time the injector
	 * in effect).
	 */
	private void internalCheckAnnotationInject(Annotation ann) {
		EObject annElem = ann.getAnnotatedElement();

		// check the container (classifier decl) of the annotated element (ie, field or constructor)
		EObject annElemCont = annElem.eContainer();
		if (annElemCont instanceof N4InterfaceDeclaration) {
			addIssue(ann, N4JSPackage.eINSTANCE.getAnnotation_Name(),
					DI_ANN_INTERFACE_INJECTION_NOT_SUPPORTED.toIssueItem());
			// no return, do other checks
		}

		if (annElem instanceof N4FieldDeclaration) {
			N4FieldDeclaration fd = (N4FieldDeclaration) annElem;
			if (fd.getDefinedTypeElement() instanceof TField) {
				TField field = (TField) fd.getDefinedTypeElement();
				TypeRef ref = field.getTypeRef();
				TClass clazz = tClassOf(ref);
				if (null != clazz) {
					boolean valid = true;
					while (null != clazz) {
						if (!valid) {
							clazz = null;
						} else {
							valid = !AnnotationDefinition.GENERATE_INJECTOR.hasAnnotation(clazz);
							if (!valid) {
								addIssue(
										ann,
										N4JSPackage.eINSTANCE.getAnnotation_Name(),
										DI_ANN_INJECTOR_CANNOT_BE_INJECTED_INTO_INJECTOR.toIssueItem());
							}
							Type type = clazz.getSuperClassRef() == null ? null
									: clazz.getSuperClassRef().getDeclaredType();
							clazz = (type instanceof TClass) ? (TClass) type : null;
						}
					}
					if (!valid) {
						return;
					}
				}
			}
		}

		// check annotated element (field or constructor)
		TMember defMember = (annElem instanceof N4MemberDeclaration)
				? ((N4MemberDeclaration) annElem).getDefinedTypeElement()
				: null;
		if (defMember instanceof TField) {
			TField tf = (TField) defMember;
			holdsIsInjectableType(ann, tf.getTypeRef(), tf.getName());
		} else if (defMember instanceof TMethod) {
			TMethod tm = (TMethod) defMember;
			if (defMember.isConstructor()) {
				for (TFormalParameter fpar : tm.getFpars()) {
					holdsIsInjectableType(ann, fpar);
				}
			} else {
				// method injection not supported yet
				addIssue(ann, N4JSPackage.eINSTANCE.getAnnotation_Name(),
						DI_ANN_INJECT_METHOD_NOT_SUPPORTED_YET.toIssueItem());
			}
		}
	}

	/**
	 * The class marked by the given annotation should also carry another (required) annotation. For example, an
	 * (at)Bind annotation may only mark a class that's also annotated (at)Binder.
	 *
	 * @return true iff no issues were found
	 */
	private boolean holdsAnnotatedTClassIsAnnotatedWith(Annotation ann, AnnotationDefinition requiredDef) {
		N4ClassDeclaration classDecl = getAnnotatedClass(ann);
		Type tClass = classDecl == null ? null : classDecl.getDefinedType();
		if (tClass != null && !requiredDef.hasAnnotation(tClass)) {
			addIssue(ann, N4JSPackage.eINSTANCE.getAnnotation_Name(),
					DI_ANN_ONLY_ON_CLASS_ANNOTATED_WITH.toIssueItem(ann.getName(), requiredDef.name));
			return false;
		}
		return true;
	}

	/**
	 * The method marked by the given annotation belong to a class that in turn should carry another (required)
	 * annotation. For example, an (at)Provides annotation may only mark a method of an (at)Binder class.
	 *
	 * @return true iff no issues were found
	 */
	private boolean holdsAnnotatedTMethodIsContainedInTClassAnnotatedWith(Annotation ann,
			AnnotationDefinition requiredDef) {
		N4MethodDeclaration methodDecl = getAnnotatedMethod(ann);
		EObject tClass = methodDecl == null || methodDecl.getDefinedType() == null ? null
				: methodDecl.getDefinedType().eContainer();
		if (tClass instanceof TClass && !requiredDef.hasAnnotation((TClass) tClass)) {
			addIssue(ann, N4JSPackage.eINSTANCE.getAnnotation_Name(),
					DI_ANN_ONLY_ON_METHOD_IN_CLASS_ANNOTATED_WITH.toIssueItem(ann.getName(), requiredDef.name));
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
	private boolean holdsAnnotatedTMethodHasCorrectSignature(Annotation ann) {
		N4MethodDeclaration methodDecl = getAnnotatedMethod(ann);
		Type tMethod = methodDecl == null ? null : methodDecl.getDefinedType();
		if (tMethod instanceof TMethod) {
			TMethod tm = (TMethod) tMethod;
			Iterable<TFormalParameter> nonInjectableParams = filter(tm.getFpars(),
					fpar -> !(isInjectableType(fpar.getTypeRef())));
			for (TFormalParameter fpar : nonInjectableParams) {
				addIssue(
						fpar.getAstElement(),
						N4JSPackage.eINSTANCE.getAbstractVariable_Name(),
						DI_NOT_INJECTABLE.toIssueItem(fpar.getTypeRef().getTypeRefAsString(),
								" at %s".formatted(fpar.getName())));
			}

			if (!isEmpty(nonInjectableParams)) {
				return false;
			}

			TypeRef retTR = tm.getReturnTypeRef();
			boolean isVoidOrOptional = TypeUtils.isVoid(retTR) || tm.isReturnValueOptional();
			if (isVoidOrOptional) {
				addIssue(methodDecl, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME,
						DI_ANN_PROVIDES_METHOD_MUST_RETURN_VALUE.toIssueItem());
				return false;
			}
			return holdsIsInjectableType(ann, tm.getReturnTypeRef());
		}
		return true;
	}

	/**
	 * The annotation's first argument is expected to refer to a class marked as requested. If not, issue an error.
	 */
	private boolean holdsArgumentIsTypeRefToTClassAnnotatedWith(Annotation ann, AnnotationDefinition requiredDef) {
		AnnotationArgument arg = (!ann.getArgs().isEmpty()) ? ann.getArgs().get(0) : null;
		if (null == arg) {
			return true;// invalid AST
		}
		TypeRef argTypeRef = (arg instanceof TypeRefAnnotationArgument) ? ((TypeRefAnnotationArgument) arg).getTypeRef()
				: null;
		if (!isTypeRefToTClassAnnotatedWith(argTypeRef, requiredDef)) {
			addIssue(arg, DI_ANN_ARG_MUST_BE_ANNOTATED_WITH.toIssueItem(ann.getName(), requiredDef.name));
			return false;
		}
		return true;
	}

	/**
	 * Is the type-declaration annotated as requested?
	 */
	private static boolean isTypeRefToTClassAnnotatedWith(TypeRef typeRef, AnnotationDefinition requiredAnn) {
		Type tclazz = tClassOf(typeRef);
		return (null != tclazz) && requiredAnn.hasAnnotation(tclazz);
	}

	/**
	 * The (binding) annotation's second arg (target) is expected to subtype the first arg (key). If not, issue an
	 * error.
	 */
	private boolean holdsSecondArgIsSubtypeOfFirstArg(Annotation ann) {
		if (ann == null || ann.eResource() == null || ann.eResource().getResourceSet() == null) {
			return true;
		}
		RuleEnvironment G = newRuleEnvironment(ann);
		TypeRef arg0TypeRef = getArgAsTypeRef(ann, 0);
		TypeRef arg1TypeRef = getArgAsTypeRef(ann, 1);
		if (arg0TypeRef != null && arg1TypeRef != null && !ts.subtypeSucceeded(G, arg1TypeRef, arg0TypeRef)) {
			addIssue(ann.getArgs().get(1), DI_ANN_BIND_SECOND_MUST_BE_SUBTYPE_FIRST.toIssueItem(ann.getName()));
			return false;
		}
		return true;
	}

	/**
	 * The given formal param belongs to an injected constructor. Such param is expected to be non-variadic,
	 * non-optional, and have an injectable type. If not, issue an error.
	 */
	private boolean holdsIsInjectableType(Annotation ann, TFormalParameter it) {
		if (null == it) {
			return true;
		}
		if (it.isVariadicOrOptional()) {
			addIssue(it.getAstElement(), N4JSPackage.eINSTANCE.getAbstractVariable_Name(),
					DI_VARARGS_NOT_INJECTABLE.toIssueItem());
			return false;
		}
		return holdsIsInjectableType(ann, it.getTypeRef(), it.getName());
	}

	private boolean holdsIsInjectableType(Annotation ann, TypeRef typeRef) {
		return holdsIsInjectableType(ann, typeRef, (String) null);
	}

	/**
	 * A Bind annotation must have an injectable and moreover non-abstract target.
	 *
	 * @return true iff no issues were found
	 */
	private boolean holdsHasValidTargetType(Annotation bindAnn) {
		TypeRef targetTypeRef = getArgAsTypeRef(bindAnn, 1);
		if (targetTypeRef == null) {
			return false;
		}
		if (!holdsIsInjectableType(bindAnn, targetTypeRef, (String) null)) {
			return false;
		}
		if (!isConcrete(targetTypeRef)) {
			addIssue(bindAnn, N4JSPackage.eINSTANCE.getAnnotation_Name(), DI_ANN_BIND_ABSTRACT_TARGET.toIssueItem());
			return false;
		}
		return true;
	}

	/**
	 * Does the argument denote a concrete class? (ie, non-abstract class, non-interface)
	 */
	private static boolean isConcrete(TypeRef typeRef) {
		return isConcrete(typeRef.getDeclaredType());
	}

	/**
	 * Is the argument a concrete class? (ie, non-abstract class, non-interface)
	 */
	private static boolean isConcrete(Type declType) {
		if (declType instanceof TClass) {
			return !((TClass) declType).isAbstract();
		}
		return false;
	}

	/**
	 * The typeRef argument is expected to denote an injectable type. If not, issue an error at the position of the
	 * annotation.
	 *
	 * @return true iff no issues were found
	 */
	private boolean holdsIsInjectableType(Annotation ann, TypeRef typeRef, String name) {
		if (typeRef == null) {
			return true;
		}
		if (typeRef.getDeclaredType() != null) {
			if (typeRef.getDeclaredType().eIsProxy()) {
				return true; // avoid duplicate errors in case of "Couldn't resolve reference to Type 'A'." errors!
			}
		}
		if (!isInjectableType(typeRef)) {
			String description = (null == name) ? "" : " at %s".formatted(name);
			addIssue(ann, DI_NOT_INJECTABLE.toIssueItem(typeRef.getTypeRefAsString(), description));
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
	public static boolean isInjectableType(TypeArgument typeArg) {
		// must be non-null, a ParameterizedTypeRef and have a declared type
		// (sorts out things like ComposedTypeRefs, ClassifierTypeRefs, etc.)
		// can be structural type (DefSite and UseSite)
		if (!(typeArg instanceof ParameterizedTypeRef)) {
			return false;
		}
		ParameterizedTypeRef typeRef = (ParameterizedTypeRef) typeArg;
		Type declType = typeRef.getDeclaredType();
		if (declType == null) {
			return false;
		}
		// declared type must not be a primitive
		if (declType instanceof PrimitiveType) {
			return false;
		}
		// declared type must not be a built-in type (except N4Provider)
		if (declType instanceof BuiltInType
				|| (N4Scheme.isFromResourceWithN4Scheme(declType)
						&& declType.getName() != BuiltInTypeScope.QN_N4PROVIDER.toString())) {
			return false;
		}
		// declared type must be a class or interface
		// (sorts out primitives, built-in types, enums, object prototype types, virtual base types.)
		if (!(declType instanceof TN4Classifier)) {
			return false;
		}
		// classifiers with generic types cannot be injected but providers can be
		if (declType instanceof TN4Classifier && !isProviderType(declType)) {
			if (!isNullOrEmpty(declType.getTypeVars())) {
				return false;
			}
		}
		if (isProviderType(declType)) {
			// a (subtype of) Provider without type-args is itself injectable
			// a Provider-of-T is injectable only if T is injectable
			List<TypeRef> targs = toList(filter(typeRef.getDeclaredTypeArgs(), TypeRef.class));
			if (targs.size() == 1) {
				if (isInjectableType(targs.get(0))) {
					return true;
				}
			}
			return targs.isEmpty();
		}
		// must not be field structural typed
		if (STRUCTURAL_FIELDS == typeRef.getTypingStrategy()) {
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
	private static boolean isProviderType(Type type) {
		if (type instanceof TN4Classifier) {
			RuleEnvironment G = newRuleEnvironment(type);
			if (n4ProviderType(G) == type) {
				return true;
			}
			return exists(SuperInterfacesIterable.of((TN4Classifier) type), iface -> iface == n4ProviderType(G));
		} else {
			return false;
		}
	}

	/**
	 * The given annotation marks an (exported) class declaration, return it of so, otherwise null.
	 */
	private static N4ClassDeclaration getAnnotatedClass(Annotation ann) {
		EObject elem = ann.getAnnotatedElement();
		if (elem instanceof ExportDeclaration) {
			elem = ((ExportDeclaration) elem).getExportedElement();
		}
		if (elem instanceof N4ClassDeclaration) {
			return (N4ClassDeclaration) elem;
		}
		return null;
	}

	/**
	 * The given annotation marks a method declaration, return it of so, otherwise null.
	 */
	private static N4MethodDeclaration getAnnotatedMethod(Annotation ann) {
		EObject elem = ann.getAnnotatedElement();
		if (elem instanceof N4MethodDeclaration) {
			return (N4MethodDeclaration) elem;
		}
		return null;
	}

	/**
	 * If the annotation argument at the given index is a TypeRef, then returns that type reference; otherwise
	 * <code>null</code>.
	 */
	private static TypeRef getArgAsTypeRef(Annotation ann, int index) {
		AnnotationArgument arg = (index < ann.getArgs().size()) ? ann.getArgs().get(index) : null;
		if (arg instanceof TypeRefAnnotationArgument) {
			return ((TypeRefAnnotationArgument) arg).getTypeRef();
		}
		return null;
	}

	/**
	 * If the annotation argument at the given index is a TypeRef, then returns that type reference; otherwise
	 * <code>null</code>.
	 */
	private static TypeRef getArgAsTypeRef(TAnnotation ann, int index) {
		TAnnotationArgument arg = (index < ann.getArgs().size()) ? ann.getArgs().get(index) : null;
		if (arg instanceof TAnnotationTypeRefArgument) {
			return ((TAnnotationTypeRefArgument) arg).getTypeRef();
		}
		return null;
	}

	/**
	 * Does the given type inherit or declare some members annotated with (at)Inject?
	 */
	public static boolean requiresInjection(TClass type, DeclMergingHelper declMergingHelper) {
		// note: AllSuperTypesCollector ignores implicit super types and polyfills, but that is ok, because
		// we assume that members of implicit super types and polyfilled members won't have an @Inject
		// TODO use an 'all super types' iterator instead of collector
		return exists(AllSuperTypesCollector.collect(type, declMergingHelper),
				t -> exists(t.getOwnedMembers(), m -> INJECT.hasAnnotation(m)));
	}

	/**
	 * Is the given type (or some super-type) marked (at)Injected?
	 */
	public static boolean isMarkedInjected(TClass type, DeclMergingHelper declMergingHelper) {
		// TODO use an 'all super types' iterator instead of collector
		return exists(AllSuperTypesCollector.collect(type, declMergingHelper), t -> INJECTED.hasAnnotation(t));
	}

	/**
	 * Injected annotates a class (abstract or not) defined in an API project.
	 */
	private void internalCheckAnnotationInjected(Annotation ann) {
		N4ClassDeclaration classDecl = getAnnotatedClass(ann);
		if (!isInjectedApplicable(classDecl)) {
			addIssue(ann, N4JSPackage.eINSTANCE.getAnnotation_Name(), DI_ANN_INJECTED_NOT_APPLICABLE.toIssueItem());
			return;
		}
		// val tClass = classDecl?.definedTypeAsClass;
		// FIXME IDE-1686 how to check if classDecl belongs to an API project
	}

	/**
	 * Is it allowed to mark the given class declaration (at)Injected?
	 */
	private static boolean isInjectedApplicable(N4ClassDeclaration classDecl) {
		return (null != classDecl) && (null != classDecl.getDefinedTypeAsClass());
	}

}
