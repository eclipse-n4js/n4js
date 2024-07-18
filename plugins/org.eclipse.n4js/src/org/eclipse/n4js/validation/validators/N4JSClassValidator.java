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

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.eclipse.n4js.AnnotationDefinition.SPEC;
import static org.eclipse.n4js.AnnotationDefinition.TEST_METHOD;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_CLASS_DEFINITION__SUPER_CLASS_REF;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL;
import static org.eclipse.n4js.ts.types.TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
import static org.eclipse.n4js.validation.IssueCodes.CLF_ABSTRACT_FINAL;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CALL_CONSTRUCT_SIG_CANNOT_IMPLEMENT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXTENDS_PRIMITIVE_GENERIC_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXTEND_FINAL;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXTEND_NON_ACCESSIBLE_CTOR;
import static org.eclipse.n4js.validation.IssueCodes.CLF_INHERITANCE_CYCLE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_MUST_BE_NOMINAL;
import static org.eclipse.n4js.validation.IssueCodes.CLF_OBSERVABLE_MISSING;
import static org.eclipse.n4js.validation.IssueCodes.CLF_SPEC_BUILT_IN_OR_PROVIDED_BY_RUNTIME_OR_EXTENAL_WITHOUT_N4JS_ANNOTATION;
import static org.eclipse.n4js.validation.IssueCodes.CLF_SPEC_MULTIPLE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_SPEC_WRONG_ADD_MEMBERTYPE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_SPEC_WRONG_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_TEST_CLASS_NOT_EXPORTED;
import static org.eclipse.n4js.validation.IssueCodes.CLF_WRONG_META_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP;
import static org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.internalCheckPolyFilledClassWithAdditionalInterface;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isNullOrEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.scoping.members.TypingStrategyFilter;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.IssueUserDataKeys;
import org.eclipse.n4js.validation.N4JSElementKeywordProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * superfluous properties in {@code @Spec} constructor.
 */
@SuppressWarnings("javadoc")
public class N4JSClassValidator extends AbstractN4JSDeclarativeValidator implements PolyfillValidatorHost {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private PolyfillValidatorFragment polyfillValidatorFragment;

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Inject
	private MemberVisibilityChecker memberVisibilityChecker;

	@Inject
	private N4JSElementKeywordProvider n4jsElementKeywordProvider;

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
	 * Non abstract test classes have to exported: IDEBUG-572.
	 */
	@Check
	public void checkTestClassExported(N4ClassDeclaration classDecl) {
		if (classDecl.getDefinedType() instanceof TClass) {
			TClass clazz = (TClass) classDecl.getDefinedType();
			if (!clazz.isAbstract() && !clazz.isDirectlyExported() && hasTestMethods(clazz)) {
				addIssue(classDecl, N4_TYPE_DECLARATION__NAME, CLF_TEST_CLASS_NOT_EXPORTED.toIssueItem());
			}
		}
		if (classDecl.getTypingStrategy() == TypingStrategy.STRUCTURAL) {
			addIssue(classDecl, N4JSPackage.eINSTANCE.getN4ClassifierDeclaration_TypingStrategy(),
					CLF_MUST_BE_NOMINAL.toIssueItem());
		}
	}

	// Get all transitive methods and checks if any of them has @Test annotation
	private boolean hasTestMethods(TClass clazz) {
		if (clazz != null && clazz.eResource() != null) {
			TModule module = N4JSResource.getModule(clazz.eResource());
			if (null != module) {
				MemberList<TMember> allMembers = containerTypesHelper.fromContext(module).allMembers(clazz, false,
						false);
				return exists(filter(allMembers, TMethod.class), m -> TEST_METHOD.hasAnnotation(m));
			}
		}
		return false;
	}

	/**
	 * Checks new expression.
	 */
	@Check
	public void checkSpecConstructorArguments(NewExpression newExpression) {
		if (!(newExpression.getCallee() instanceof IdentifierRef)) {
			return;
		}
		IdentifierRef identifierRef = (IdentifierRef) newExpression.getCallee();

		if (!(identifierRef.getId() instanceof TClass)) {
			return;
		}
		TClass tclass = (TClass) identifierRef.getId();
		TMethod ctor = containerTypesHelper.fromContext(newExpression).findConstructor(tclass);

		// ctor might be null if class has a super class that cannot be resolved
		if (null == ctor || isNullOrEmpty(ctor.getFpars())) {
			return;
		}

		// Collect constructor parameters with @Spec annotation.
		// Probably finding the first parameter with @Spec annotation would be
		// fine enough since using more than one is not allowed anyway.
		List<TFormalParameter> fparsWithSpecAnnotation = toList(
				filter(ctor.getFpars(), fp -> null != SPEC.getAnnotation(fp)));

		if (fparsWithSpecAnnotation.isEmpty()) {
			return;
		}

		if (fparsWithSpecAnnotation.size() > 1) {
			return;
		}

		TFormalParameter fparWithSpecAnnotation = getOnlyElement(fparsWithSpecAnnotation);
		TypeRef typeRef = fparWithSpecAnnotation.getTypeRef();

		// Not a field structural 'this' reference, nothing to validate.
		if (!(typeRef instanceof ThisTypeRefStructural)
				|| STRUCTURAL_FIELD_INITIALIZER != typeRef.getTypingStrategy()) {
			return;
		}

		int indexOfTheSpecFpar = ctor.getFpars().indexOf(fparWithSpecAnnotation);

		// Possible broken AST or optional formal parameters. Abort validation process.
		if (0 > indexOfTheSpecFpar || newExpression.getArguments().size() <= indexOfTheSpecFpar) {
			return;
		}

		Argument argAtIdx = newExpression.getArguments().get(indexOfTheSpecFpar);
		if (argAtIdx == null || !(argAtIdx.getExpression() instanceof ObjectLiteral)) {
			return;
		}
		ObjectLiteral objectLiteral = (ObjectLiteral) argAtIdx.getExpression();
		if (!(objectLiteral.getDefinedType() instanceof ContainerType<?>)) {
			return;
		}

		TypingStrategyFilter strategy = new TypingStrategyFilter(STRUCTURAL);

		Map<String, TMember> publicWritableFieldsAndSetters = new LinkedHashMap<>();
		for (TMember m : containerTypesHelper.fromContext(newExpression).allMembers(tclass)) {
			if (m instanceof TField || m instanceof TSetter) {
				if (strategy.apply(m)) {
					publicWritableFieldsAndSetters.put(m.getName(), m);
				}
			}
		}

		// These are available via the 'with' keyword add them to the accepted ones
		for (TField f : filter(((ThisTypeRefStructural) typeRef).getStructuralMembers(), TField.class)) {
			publicWritableFieldsAndSetters.put(f.getName(), f);
		}

		// superfluous properties in initializer are checked in
		// org.eclipse.n4js.validation.validators.N4JSTypeValidator.internalCheckSuperfluousPropertiesInObjectLiteral(..)
		checkFieldInitializationOfImplementedInterface(publicWritableFieldsAndSetters, objectLiteral);
	}

	/**
	 * Check if an object literal in {@code @Spec} constructor provide a property that comes from a built-in/provided by
	 * runtime interface. IDE-2747.
	 */
	private void checkFieldInitializationOfImplementedInterface(Map<String, TMember> publicWritableFieldsMap,
			ObjectLiteral objectLiteral) {
		// For each property of object literal, check if it comes from a built-in/provided by runtime interface.
		EList<? extends TMember> properties = ((ContainerType<?>) objectLiteral.getDefinedType()).getOwnedMembers();
		for (TMember property : properties) {
			// Search the corresponding field of the property
			TMember field = publicWritableFieldsMap.get(property.getName());
			if (field != null) {
				ContainerType<?> containingClassifier = field.getContainingType();
				if (containingClassifier instanceof TInterface) {
					if (N4JSLanguageUtils.builtInOrProvidedByRuntime((TInterface) containingClassifier)) {
						IssueItem issueItem = CLF_SPEC_BUILT_IN_OR_PROVIDED_BY_RUNTIME_OR_EXTENAL_WITHOUT_N4JS_ANNOTATION
								.toIssueItem(field.getName(), containingClassifier.getName());
						EReference feature = (((PropertyNameValuePair) property.getAstElement()).getProperty() == null)
								? PROPERTY_NAME_OWNER__DECLARED_NAME
								: N4JSPackage.eINSTANCE.getPropertyNameValuePair_Property();
						addIssue(property.getAstElement(), feature, issueItem);
					}
				}
			}
		}
	}

	@Check
	public void checkN4ClassDeclaration(N4ClassDeclaration n4Class) {
		// wrong parsed
		if (!(n4Class.getDefinedType() instanceof TClass)) {
			return;
		}

		if (polyfillValidatorFragment.holdsPolyfill(this, n4Class, getCancelIndicator())) {
			TClass tClass = (TClass) n4Class.getDefinedType();
			internalCheckAbstractFinal(tClass);

			if (holdsSuperClass(n4Class)) { // avoid consequential errors
				holdsNoCyclicInheritance(n4Class);
			}

			internalCheckPolyFilledClassWithAdditionalInterface(n4Class, this);
			internalCheckImplementedInterfaces(n4Class);
			internalCheckSpecAnnotation(n4Class);
		}
	}

	/**
	 * Overridden to be accessible from PolyfillValidatorFragment
	 */
	@Override
	public void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
		super.addIssue(message, source, feature, issueCode, issueData);
	}

	private void internalCheckAbstractFinal(TClass tClass) {
		if (tClass.isAbstract() && tClass.isFinal()) {
			addIssue(tClass.getAstElement(), N4_TYPE_DECLARATION__NAME, CLF_ABSTRACT_FINAL.toIssueItem("class"));
		}
	}

	private boolean holdsSuperClass(N4ClassDeclaration n4Class) {
		TypeRef superTypeRef = n4Class.getSuperClassRef() == null ? null : n4Class.getSuperClassRef().getTypeRef();
		Type superType = superTypeRef == null ? null : superTypeRef.getDeclaredType();
		if (superType != null && superType.getName() != null) { // note: in case superType.name==null, the type
																// reference is completely invalid and other, more
																// appropriate error messages have been created
																// elsewhere

			if (superType instanceof PrimitiveType) {
				if (!N4Scheme.isFromResourceWithN4Scheme(n4Class)) { // primitive types may be extended in built-in
																		// types
					IssueItem issueItem = CLF_EXTENDS_PRIMITIVE_GENERIC_TYPE.toIssueItem(superType.getName());
					addIssue(n4Class.getSuperClassRef(), null, issueItem);
				}
			} else if (!(superType instanceof TClass)) {
				if (superType instanceof TInterface) {
					IssueItem issueItem = SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP.toIssueItem(
							validatorMessageHelper.description(n4Class), "extend",
							validatorMessageHelper.description(superType), "implements");
					addIssue(n4Class.getSuperClassRef(), null, issueItem);
				} else {
					IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(validatorMessageHelper.description(n4Class),
							"extend", validatorMessageHelper.description(superType));
					addIssue(n4Class.getSuperClassRef(), null, issueItem);
					return false;
				}
			} else if (superType instanceof TClass) {
				// (got a super class; now validate it ...)

				TClass stc = (TClass) superType;

				// super class must not be final (except in case of polyfills)
				if (stc.isFinal() && !(N4JSLanguageUtils.isNonStaticPolyfill(n4Class)
						|| N4JSLanguageUtils.isStaticPolyfill(n4Class))) {

					EObject superTypeAstElement = (EObject) stc.eGet(
							TypesPackage.eINSTANCE.getSyntaxRelatedTElement_AstElement(),
							false);
					String superClassUri = (superTypeAstElement == null) ? null
							: EcoreUtil.getURI(superTypeAstElement).toString();

					if (superClassUri != null) {
						IssueItem issueItem = CLF_EXTEND_FINAL.toIssueItemWithData(
								List.of(IssueUserDataKeys.CLF_EXTEND_FINAL.SUPER_TYPE_DECLARATION_URI, superClassUri),
								stc.getName());
						addIssue(n4Class.getSuperClassRef(), null, issueItem);
					} else {
						IssueItem issueItem = CLF_EXTEND_FINAL.toIssueItemWithData(
								List.of(IssueUserDataKeys.CLF_EXTEND_FINAL.SUPER_TYPE_DECLARATION_URI),
								stc.getName());
						addIssue(n4Class.getSuperClassRef(), null, issueItem);
					}
					return false;
				}

				// ctor of super class must be accessible
				if (!holdsCtorOfSuperTypeIsAccessible(n4Class, stc)) {
					return false;
				}

				// if super class is observable, then this class must be observable as well
				if (stc.isObservable() && !((TClass) n4Class.getDefinedType()).isObservable()) {
					IssueItem issueItem = CLF_OBSERVABLE_MISSING.toIssueItem(n4Class.getName(), stc.getName());
					addIssue(n4Class, N4_TYPE_DECLARATION__NAME, issueItem);
					return false;
				}
			}
		} else if (superTypeRef != null && superTypeRef.isAliasResolved()) {
			// not all aliases are illegal after "extends", but if we get to this point we have an illegal case:
			IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(validatorMessageHelper.description(n4Class), "extend",
					superTypeRef.getTypeRefAsStringWithAliasResolution());
			addIssue(n4Class.getSuperClassRef(), null, issueItem);
			return false;
		}
		return true;
	}

	private boolean holdsCtorOfSuperTypeIsAccessible(N4ClassDeclaration n4Class, TClassifier superType) {
		TypeRef receiverTypeRef = TypeUtils.createTypeRef(n4Class.getDefinedType());
		TMethod superCtor = containerTypesHelper.fromContext(n4Class).findConstructor(superType);
		if (superCtor != null && !memberVisibilityChecker.isVisible(n4Class, receiverTypeRef, superCtor).visibility) {
			IssueItem issueItem = CLF_EXTEND_NON_ACCESSIBLE_CTOR.toIssueItem(
					n4jsElementKeywordProvider.keyword(superType),
					superType.getName());
			addIssue(n4Class, N4_CLASS_DEFINITION__SUPER_CLASS_REF, issueItem);
			return false;
		}
		return true;
	}

	private void internalCheckImplementedInterfaces(N4ClassDeclaration n4Class) {
		for (TypeReferenceNode<ParameterizedTypeRef> it : n4Class.getImplementedInterfaceRefs()) {
			TypeRef consumedTypeRef = it.getTypeRef();
			Type consumedType = consumedTypeRef == null ? null : consumedTypeRef.getDeclaredType();
			if (consumedType != null && consumedType.getName() != null) { // note: in case consumedType.name==null, the
																			// type reference is completely invalid and
																			// other, more appropriate error messages
																			// have been created elsewhere

				// consumed type must be an interface
				if (!(consumedType instanceof TInterface)) {
					if (consumedType instanceof TClass && n4Class.getSuperClassRef() == null) {
						IssueItem issueItem = SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP.toIssueItem(
								validatorMessageHelper.description(n4Class), "implement",
								validatorMessageHelper.description(consumedType), "extends");
						addIssue(it, null, issueItem);
					} else {
						IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(
								validatorMessageHelper.description(n4Class), "implement",
								validatorMessageHelper.description(consumedType));
						addIssue(it, null, issueItem);
					}
				} else {
					TInterface tIfc = (TInterface) consumedType;
					MemberCollector cth = containerTypesHelper.fromContext(n4Class);
					boolean hasCallConstructSig = cth.findCallSignature(tIfc) != null
							|| cth.findConstructSignature(tIfc) != null;
					if (hasCallConstructSig) {
						addIssue(it, null, CLF_CALL_CONSTRUCT_SIG_CANNOT_IMPLEMENT.toIssueItem());
					}
				}
			} else if (consumedTypeRef != null && consumedTypeRef.isAliasResolved()) {
				// not all aliases are illegal after "implements", but if we get to this point we have an illegal case:
				IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(validatorMessageHelper.description(n4Class),
						"implement", consumedTypeRef.getTypeRefAsStringWithAliasResolution());
				addIssue(it, null, issueItem);
			}
		}
	}

	private void internalCheckSpecAnnotation(N4ClassDeclaration n4ClassDeclaration) {
		N4MethodDeclaration ctor = n4ClassDeclaration.getOwnedCtor();
		if (ctor != null) {
			List<Annotation> specAnnotations = new ArrayList<>();
			int i = 0;
			for (FormalParameter currFPar : ctor.getFpars()) {
				Annotation annSpec = SPEC.getAnnotation(currFPar);
				if (annSpec != null) {
					specAnnotations.add(annSpec);
					boolean correctType = currFPar.getDeclaredTypeRef() instanceof ThisTypeRef
							&& STRUCTURAL_FIELD_INITIALIZER == currFPar.getDeclaredTypeRef().getTypingStrategy();
					if (!correctType) {
						addIssue(annSpec, null, CLF_SPEC_WRONG_TYPE.toIssueItem());
					} else { // prevent consequential errors
						holdsAdditionalSpecFieldMatchesOwnedFields(
								n4ClassDeclaration,
								ctor,
								i);
					}
				}
				i = i + 1;
			}
			if (specAnnotations.size() >= 2) {
				for (Annotation currAnnSpec : specAnnotations) {
					addIssue(currAnnSpec, null, CLF_SPEC_MULTIPLE.toIssueItem());
				}
			}
		}
	}

	public void holdsAdditionalSpecFieldMatchesOwnedFields(
			N4ClassDeclaration n4ClassDeclaration,
			N4MethodDeclaration ctor,
			int parIndex) {

		TClass tclass = (TClass) n4ClassDeclaration.getDefinedType();
		EList<TFormalParameter> fpars = ((TFunction) ctor.getDefinedType()).getFpars();
		if (parIndex >= fpars.size()) {
			return; // broken AST
		}
		TFormalParameter fpar = fpars.get(parIndex);
		TypeRef fparType = fpar.getTypeRef();
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(n4ClassDeclaration);

		int memberIndex = 0;
		for (TStructMember smember : fparType.getStructuralMembers()) {
			TMember tfield = findFirst(tclass.getOwnedMembers(), m -> Objects.equals(m.getName(), smember.getName()));
			if (tfield != null && (tfield.isField() || tfield.isSetter())) {
				TypeRef fieldType = ts.tau(tfield, TypeUtils.createTypeRef(tclass));
				TypeRef smemberType = ts.tau(smember, TypeUtils.createTypeRef(tclass));
				Result subtypeRes = ts.subtype(G, smemberType, fieldType);
				if (subtypeRes.isFailure()) {
					IssueItem issueItem = CLF_SPEC_WRONG_ADD_MEMBERTYPE.toIssueItem(smember.getName(),
							validatorMessageHelper.description(tfield),
							validatorMessageHelper.trimTypesystemMessage(subtypeRes));
					TStructMember errMember = ((StructuralTypeRef) ctor.getFpars().get(parIndex)
							.getDeclaredTypeRefInAST()).getStructuralMembers().get(memberIndex);
					EObject sourceObject = (errMember.getAstElement() != null) ? errMember.getAstElement() : errMember;
					addIssue(sourceObject, issueItem);
				}

			}
			memberIndex = memberIndex + 1;

		}
	}

	private boolean holdsNoCyclicInheritance(N4ClassDeclaration n4ClassDeclaration) {
		TClassifier cls = (TClassifier) n4ClassDeclaration.getDefinedType();
		List<TClassifier> cycle = findCyclicInheritance(cls);
		if (cycle != null) {
			IssueItem issueItem = CLF_INHERITANCE_CYCLE
					.toIssueItem(Strings.join(", ", map(cycle, tcf -> tcf.getName())));
			addIssue(n4ClassDeclaration, N4JSPackage.Literals.N4_CLASS_DEFINITION__SUPER_CLASS_REF, issueItem);
			return false;
		}
		return true;
	}
}
