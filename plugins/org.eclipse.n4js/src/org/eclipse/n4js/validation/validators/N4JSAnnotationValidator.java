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

import static org.eclipse.n4js.AnnotationDefinition.AFTERALL_TEARDOWN;
import static org.eclipse.n4js.AnnotationDefinition.AFTER_TEARDOWN;
import static org.eclipse.n4js.AnnotationDefinition.BEFOREALL_SETUP;
import static org.eclipse.n4js.AnnotationDefinition.BEFORE_SETUP;
import static org.eclipse.n4js.AnnotationDefinition.DESCRIPTION;
import static org.eclipse.n4js.AnnotationDefinition.ECMASCRIPT;
import static org.eclipse.n4js.AnnotationDefinition.EXCLUDE_FROM_TEST_CATALOG;
import static org.eclipse.n4js.AnnotationDefinition.IGNORE_IMPLEMENTATION;
import static org.eclipse.n4js.AnnotationDefinition.N4JS;
import static org.eclipse.n4js.AnnotationDefinition.PARAMETER;
import static org.eclipse.n4js.AnnotationDefinition.PARAMETERS;
import static org.eclipse.n4js.AnnotationDefinition.PROMISIFIABLE;
import static org.eclipse.n4js.AnnotationDefinition.PROVIDED_BY_RUNTIME;
import static org.eclipse.n4js.AnnotationDefinition.SPEC;
import static org.eclipse.n4js.AnnotationDefinition.STATIC_POLYFILL;
import static org.eclipse.n4js.AnnotationDefinition.STATIC_POLYFILL_MODULE;
import static org.eclipse.n4js.AnnotationDefinition.TEST_FIXME;
import static org.eclipse.n4js.AnnotationDefinition.TEST_GROUP;
import static org.eclipse.n4js.AnnotationDefinition.TEST_IGNORE;
import static org.eclipse.n4js.AnnotationDefinition.TEST_METHOD;
import static org.eclipse.n4js.AnnotationDefinition.TEST_TIMEOUT;
import static org.eclipse.n4js.AnnotationDefinition.THIS;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.ANNOTABLE_EXPRESSION__ANNOTATION_LIST;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.ANNOTABLE_PROPERTY_ASSIGNMENT__ANNOTATION_LIST;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.ANNOTATION__ARGS;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.ANNOTATION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FORMAL_PARAMETER__ANNOTATIONS;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.SCRIPT__ANNOTATIONS;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.VARIABLE_DECLARATION__ANNOTATIONS;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isContainedInStaticPolyfillAware;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isContainedInStaticPolyfillModule;
import static org.eclipse.n4js.validation.IssueCodes.ANN_DISALLOWED_AT_LOCATION;
import static org.eclipse.n4js.validation.IssueCodes.ANN_DISALLOWED_IN_NONDEFINTION_FILE;
import static org.eclipse.n4js.validation.IssueCodes.ANN_DISALLOWED_IN_NON_RUNTIME_COMPONENT;
import static org.eclipse.n4js.validation.IssueCodes.ANN_DISALLOWED_ON_SHAPES;
import static org.eclipse.n4js.validation.IssueCodes.ANN_NON_REPEATABLE;
import static org.eclipse.n4js.validation.IssueCodes.ANN_NOT_DEFINED;
import static org.eclipse.n4js.validation.IssueCodes.ANN_ONLY_ALLOWED_LOCATION_CONSTRUCTORS;
import static org.eclipse.n4js.validation.IssueCodes.ANN_ONL_ALLOWED_AT_CLASSES_IN_N4JSD;
import static org.eclipse.n4js.validation.IssueCodes.ANN_ONL_ALLOWED_IN_VARIANTS;
import static org.eclipse.n4js.validation.IssueCodes.ANN_POLY_AWARE_AND_MODULE_MUTUAL_EXCLUSIVE;
import static org.eclipse.n4js.validation.IssueCodes.ANN_POLY_STATIC_POLY_ONLY_IN_POLYFILL_MODULE;
import static org.eclipse.n4js.validation.IssueCodes.ANN_PROMISIFIABLE_BAD_CALLBACK_ERROR_NOT_FIRST_ARG;
import static org.eclipse.n4js.validation.IssueCodes.ANN_PROMISIFIABLE_BAD_CALLBACK_MORE_THAN_ONE_ERROR;
import static org.eclipse.n4js.validation.IssueCodes.ANN_PROMISIFIABLE_MISSING_CALLBACK;
import static org.eclipse.n4js.validation.IssueCodes.ANN_THIS_DISALLOWED_ON_STATIC_MEMBER_OF_INTERFACE;
import static org.eclipse.n4js.validation.IssueCodes.ANN_THIS_NOT_SUBTYPE_OF_CONTAINING_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.ANN_UNNECESSARY;
import static org.eclipse.n4js.validation.IssueCodes.ANN_WRONG_ARGUMENT_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.ANN_WRONG_NUMBER_OF_ARGUMENTS;
import static org.eclipse.n4js.validation.IssueCodes.ANN__ONLY_IN_N4JS;
import static org.eclipse.n4js.validation.IssueCodes.ANN__TEST_ONLY_IN_TEST_SOURCES;
import static org.eclipse.n4js.validation.IssueCodes.NO_PROJECT_FOUND;
import static org.eclipse.n4js.validation.IssueCodes.POLY_CLASH_IN_STATIC_POLYFILL_MODULE;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forall;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AbstractAnnotationList;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.AnnotableExpression;
import org.eclipse.n4js.n4JS.AnnotableN4MemberDeclaration;
import org.eclipse.n4js.n4JS.AnnotablePropertyAssignment;
import org.eclipse.n4js.n4JS.AnnotableScriptElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.resource.XpectAwareFileExtensionCalculator;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.scoping.utils.PolyfillUtils;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.PromisifyHelper;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.base.Joiner;
import com.google.inject.Inject;

/**
 * Annotation validation rules for N4JS.
 */
@SuppressWarnings("javadoc")
public class N4JSAnnotationValidator extends AbstractN4JSDeclarativeValidator {
	private final static Logger LOGGER = Logger.getLogger(N4JSAnnotationValidator.class);

	@Inject
	WorkspaceAccess workspaceAccess;

	@Inject
	IQualifiedNameProvider qnProvider;

	@Inject
	ResourceDescriptionsProvider indexAccess;

	@Inject
	private PromisifyHelper promisifyHelper;

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	@Inject
	protected XpectAwareFileExtensionCalculator fileExtensionCalculator;

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
	 * check all annotable elements and do various checks for special types.
	 */
	@Check
	public void checkAnnotations(AnnotableElement annotableElement) {
		if (!jsVariantHelper.allowAnnotation(annotableElement)) {
			// Annotation not allowed in other then N4JS modes:
			if (annotableElement.getAnnotations().size() > 0) {
				addIssue(annotableElement, annoFeature(annotableElement), ANN__ONLY_IN_N4JS.toIssueItem());
			}
			return;
		}

		// prevent double-checking left-factored AnnotationLists.
		if (annotableElement instanceof AbstractAnnotationList) {
			return;
		}

		Set<String> foundNames = new HashSet<>();
		for (Annotation a : annotableElement.getAnnotations()) {
			if (a.getName() != null) {
				AnnotationDefinition def = AnnotationDefinition.find(a.getName());
				if (def == null) {
					addIssue(a, ANNOTATION__NAME, ANN_NOT_DEFINED.toIssueItem(a.getName()));
				} else {
					if (def.repeatable) {
						if (foundNames.add(a.getName())) {
							internalCheckRepeatableAnnotations(def, filter(annotableElement.getAnnotations(),
									e -> Objects.equals(e.getName(), a.getName())));
						}
					} else {
						if (!foundNames.add(a.getName())) {
							addIssue(a, ANNOTATION__NAME, ANN_NON_REPEATABLE.toIssueItem(a.getName()));
						} else {
							internalCheckAnnotation(def, a);
						}
					}
				}
			}
		}
	}

	/**
	 * Checks all repeatable annotations with same name.
	 */
	private void internalCheckRepeatableAnnotations(AnnotationDefinition definition,
			Iterable<Annotation> annotations) {

		if (forall(annotations, an -> holdsTargets(definition, an) && holdsArgumentTypes(definition, an))) {

			if (definition.transitive && !definition.repeatable) {
				for (Annotation an : annotations) {
					checkUnnecessaryAnnotation(definition, an);
				}
			}

			// special validations:
			if (Objects.equals(definition.name, TEST_GROUP.name)) {
				for (Annotation an : annotations) {
					internalCheckTestAnnotation(an);
				}
			}
		}

	}

	/**
	 * Checks a single non-repeatable annotation
	 */
	private void internalCheckAnnotation(AnnotationDefinition definition, Annotation annotation) {

		if (holdsJavaScriptVariants(definition, annotation) && holdsTargets(definition, annotation)
				&& holdsArgumentTypes(definition, annotation)) {

			checkUnnecessaryAnnotation(definition, annotation);

			// special validations:
			if (THIS.name.equals(annotation.getName())) {
				internalCheckThis(annotation);
			} else if (PROVIDED_BY_RUNTIME.name.equals(annotation.getName())) {
				internalCheckProvidedByRuntime(annotation);
			} else if (IGNORE_IMPLEMENTATION.name.equals(annotation.getName())) {
				internalCheckIgnoreImplementation(annotation);
			} else if (STATIC_POLYFILL_MODULE.name.equals(annotation.getName())) {
				internalCheckStaticPolyfillModule(annotation);
			} else if (STATIC_POLYFILL.name.equals(annotation.getName())) {
				internalCheckStaticPolyfill(annotation);
			} else if (N4JS.name.equals(annotation.getName())) {
				internalCheckN4JS(annotation);
			} else if (ECMASCRIPT.name.equals(annotation.getName())) {
				internalCheckECMASCRIPT(annotation);

				/* case TEST_GROUP.name, */ // checked in internalCheckRepeatableAnnotations()

			} else if (Set.of(
					TEST_METHOD.name,
					PARAMETERS.name,
					PARAMETER.name,
					BEFOREALL_SETUP.name,
					BEFORE_SETUP.name,
					AFTERALL_TEARDOWN.name,
					AFTER_TEARDOWN.name,
					TEST_IGNORE.name,
					TEST_FIXME.name,
					TEST_TIMEOUT.name,
					DESCRIPTION.name,
					EXCLUDE_FROM_TEST_CATALOG.name).contains(annotation.getName())) {

				internalCheckTestAnnotation(annotation);
			}
		}
	}

	private void checkUnnecessaryAnnotation(AnnotationDefinition definition, Annotation annotation) {
		if (definition.transitive && !definition.repeatable) {
			if (definition.hasAnnotation(
					EcoreUtil2.getContainerOfType(annotation.getAnnotatedElement().eContainer(),
							AnnotableElement.class))) {
				addIssue(annotation, ANNOTATION__NAME, ANN_UNNECESSARY.toIssueItem(annotation.getName()));
			}
		}
	}

	private boolean holdsArgumentTypes(AnnotationDefinition definition, Annotation annotation) {
		int actualSize = annotation.getArgs().size();
		int expectedSize = definition.argtypes.length;
		boolean variadic = definition.argsVariadic;

		if (variadic) {
			// less actual arguments than specified in the definition
			if (expectedSize - actualSize >= 2) {
				IssueItem issueItem = ANN_WRONG_NUMBER_OF_ARGUMENTS.toIssueItem(definition.name, expectedSize - 1,
						actualSize);
				addIssue(annotation, ANNOTATION__NAME, issueItem);
				return false;
			}

			// missing last argument due to variadic arguments or equal size
			if (1 == expectedSize - actualSize || expectedSize == actualSize) {
				return validateArgumentTypes(definition, annotation);
			}

			boolean valid = true;
			for (int i = 0; i < actualSize; i++) {
				EObject arg = annotation.getArgs().get(i).value();
				EClass argType = definition.argtypes[Math.max(i, definition.argtypes.length - 1)];

				if (!argType.isInstance(arg)) {
					IssueItem issueItem = ANN_WRONG_ARGUMENT_TYPE.toIssueItem(definition.name, argType.getName());
					addIssue(annotation, ANNOTATION__ARGS, i, issueItem);
					valid = false;
				}
			}
			return valid;

		} else {
			if (actualSize > expectedSize || (!definition.argsOptional && actualSize != expectedSize)) {
				IssueItem issueItem = ANN_WRONG_NUMBER_OF_ARGUMENTS.toIssueItem(definition.name, expectedSize,
						actualSize);
				addIssue(annotation, ANNOTATION__NAME, issueItem);
				return false;
			}
		}

		return validateArgumentTypes(definition, annotation);
	}

	private boolean validateArgumentTypes(AnnotationDefinition definition, Annotation annotation) {
		int actualSize = annotation.getArgs().size();
		int expectedSize = definition.argtypes.length;
		boolean valid = true;
		int min = Math.min(expectedSize, actualSize);
		for (int i = 0; i < min; i++) {
			EObject arg = annotation.getArgs().get(i).value();
			EClass argType = definition.argtypes[i];
			if (!argType.isInstance(arg)) {
				IssueItem issueItem = ANN_WRONG_ARGUMENT_TYPE.toIssueItem(definition.name, argType.getName());
				addIssue(annotation, ANNOTATION__ARGS, i, issueItem);
				valid = false;
			}
		}

		return valid;
	}

	/**
	 * Checks whether the given annotation conforms with the {@link AnnotationDefinition#javaScriptVariants} of the
	 * given AnnotationDefinition.
	 */
	private boolean holdsJavaScriptVariants(AnnotationDefinition definition, Annotation annotation) {
		if (definition.javaScriptVariants == null || definition.javaScriptVariants.length == 0) {
			return true; // nothing to validate against
		}
		EObject element = annotation.getAnnotatedElement();
		List<String> jsvList = Arrays.asList(definition.javaScriptVariants);
		if (!jsvList.contains(jsVariantHelper.variantMode(element))) {
			IssueItem issueItem = ANN_ONL_ALLOWED_IN_VARIANTS.toIssueItem(definition.name,
					validatorMessageHelper.orList(toList(map(jsvList, v -> jsVariantHelper.getVariantName(v)))));
			addIssue(annotation, issueItem);
			return false;
		}
		return true;
	}

	private boolean holdsTargets(AnnotationDefinition definition, Annotation annotation) {
		if (definition.targets == null || definition.targets.length == 0) {
			return true; // undefined targets, will probably be tested in special validation
		}
		List<EClass> tgtList = Arrays.asList(definition.targets);
		List<EClass> tgtWCEList = Arrays.asList(definition.targetsWithCustomError);

		EObject element = annotation.getAnnotatedElement();
		if (element == null) {
			return true; // robustness
		}
		boolean isElementAmongValidTargets = exists(tgtList, t -> t.isInstance(element)) ||
				exists(tgtWCEList, t -> t.isInstance(element));

		if (!isElementAmongValidTargets) {
			// second chance:
			// if we have an ExportDeclaration, it is sufficient if the exported element is among the valid targets
			ExportableElement exportedElement = (element instanceof ExportDeclaration)
					? ((ExportDeclaration) element).getExportedElement()
					: null;
			boolean isExportedElementAmongValidTargets = exportedElement != null
					&& (exists(tgtList, t -> t.isInstance(exportedElement)) ||
							exists(tgtWCEList, t -> t.isInstance(exportedElement)));

			if (!isExportedElementAmongValidTargets) {
				addWrongLocationIssue(annotation);
				return false;
			}
		}

		// special location checks for specific annotations
		if (Objects.equals(annotation.getName(), SPEC.name)) {
			return internalCheckLocationSpec(annotation);
		}

		return true;
	}

	private void addWrongLocationIssue(Annotation annotation) {
		IssueItem issueItem = ANN_DISALLOWED_AT_LOCATION.toIssueItem(annotation.getName());
		addIssue(annotation, ANNOTATION__NAME, issueItem);
	}

	/**
	 * Mapping from EObject to the Feature containing the Annotations. Used for the correct location of error-markings.
	 */
	private EStructuralFeature annoFeature(AnnotableElement element) {
		if (element instanceof AnnotableExpression) {
			return ANNOTABLE_EXPRESSION__ANNOTATION_LIST;
		}
		if (element instanceof AnnotableScriptElement) {
			return ANNOTABLE_SCRIPT_ELEMENT__ANNOTATION_LIST;
		}
		if (element instanceof AnnotableN4MemberDeclaration) {
			return ANNOTABLE_N4_MEMBER_DECLARATION__ANNOTATION_LIST;
		}
		if (element instanceof AnnotablePropertyAssignment) {
			return ANNOTABLE_PROPERTY_ASSIGNMENT__ANNOTATION_LIST;
		}
		if (element instanceof FormalParameter) {
			return FORMAL_PARAMETER__ANNOTATIONS;
		}
		if (element instanceof Script) {
			return SCRIPT__ANNOTATIONS;
		}
		if (element instanceof VariableDeclaration) {
			return VARIABLE_DECLARATION__ANNOTATIONS;
		}
		return null;
	}

	/**
	 * Constraints 91 (Valid Target and Argument for &#64;This Annotation)
	 */
	private void internalCheckThis(Annotation annotation) {
		EObject element = annotation.getAnnotatedElement();
		if (element == null) {
			return;
		}
		if (element instanceof N4MemberDeclaration) {
			N4MemberDeclaration md = (N4MemberDeclaration) element;
			TMember tMember = md.getDefinedTypeElement();
			ContainerType<?> containingType = tMember == null ? null : tMember.getContainingType();
			if (tMember == null || containingType == null) {
				return; // invalid types model
			}
			// constraint #1: @This not allowed on static members of interfaces
			if (tMember.isStatic() && containingType instanceof TInterface) {
				addIssue(annotation, ANNOTATION__NAME, ANN_THIS_DISALLOWED_ON_STATIC_MEMBER_OF_INTERFACE.toIssueItem());
				return;
			}
			// constraint #2: declared this type must be subtype of the containing type
			TypeRef declThisTypeRef = null;
			if (tMember instanceof TMethod) {
				declThisTypeRef = ((TMethod) tMember).getDeclaredThisType();
			}
			if (tMember instanceof FieldAccessor) {
				declThisTypeRef = ((FieldAccessor) tMember).getDeclaredThisType();
			}

			TypeRef containingTypeRef = null;
			if (tMember.isStatic()) {
				containingTypeRef = TypeUtils.createTypeTypeRef(TypeUtils.createTypeRef(containingType), false);
			} else {
				containingTypeRef = TypeUtils.createTypeRef(containingType, TypingStrategy.DEFAULT, true);
			}

			RuleEnvironment G = newRuleEnvironment(element);
			if (!ts.subtypeSucceeded(G, declThisTypeRef, containingTypeRef)) {
				IssueItem issueItem = ANN_THIS_NOT_SUBTYPE_OF_CONTAINING_TYPE.toIssueItem(
						validatorMessageHelper.description(tMember),
						validatorMessageHelper.description(containingType), containingTypeRef.getTypeRefAsString());
				addIssue(annotation, ANNOTATION__ARGS, issueItem);
			}
		}
	}

	/**
	 * Check N4JS annotation to be in definition file.
	 */
	private void internalCheckN4JS(Annotation annotation) {
		EObject element = annotation.getAnnotatedElement();
		if (element == null) {
			return;
		}
		if (!jsVariantHelper.isExternalMode(element)) {
			IssueItem issueItem = ANN_DISALLOWED_IN_NONDEFINTION_FILE.toIssueItem(annotation.getName());
			addIssue(annotation, ANNOTATION__NAME, issueItem);
			return;
		}
		if (element instanceof ExportDeclaration
				&& ((ExportDeclaration) element).getExportedElement() instanceof N4InterfaceDeclaration
				&& ((N4InterfaceDeclaration) ((ExportDeclaration) element).getExportedElement())
						.getTypingStrategy() == TypingStrategy.STRUCTURAL) {
			IssueItem issueItem = ANN_DISALLOWED_ON_SHAPES.toIssueItem(annotation.getName());
			addIssue(annotation, ANNOTATION__NAME, issueItem);
		}
	}

	/**
	 * Check ECMASCRIPT annotation to be in definition file.
	 */
	private void internalCheckECMASCRIPT(Annotation annotation) {
		EObject element = annotation.getAnnotatedElement();
		if (element == null) {
			return;
		}
		if (!jsVariantHelper.isExternalMode(element)) {
			IssueItem issueItem = ANN_ONL_ALLOWED_AT_CLASSES_IN_N4JSD.toIssueItem(annotation.getName());
			addIssue(annotation, ANNOTATION__NAME, issueItem);
			return;
		}
		if (element instanceof ExportDeclaration
				&& !(((ExportDeclaration) element).getExportedElement() instanceof N4ClassDeclaration)) {

			IssueItem issueItem = ANN_ONL_ALLOWED_AT_CLASSES_IN_N4JSD.toIssueItem(annotation.getName());
			addIssue(annotation, ANNOTATION__NAME, issueItem);
			return;
		}
	}

	/**
	 * Check that test related annotations are located in test containers (defined in package.json).
	 */
	private void internalCheckTestAnnotation(Annotation annotation) {
		EObject element = annotation.getAnnotatedElement();
		if (element == null) {
			return;
		}
		URI uri = fileExtensionCalculator.getUriWithoutXpectExtension(element);
		N4JSSourceFolderSnapshot srcContainer = workspaceAccess.findSourceFolderContaining(annotation, uri);
		if (srcContainer == null) {
			return;
		}
		if (!srcContainer.isTest()) {
			IssueItem issueItem = ANN__TEST_ONLY_IN_TEST_SOURCES.toIssueItem(annotation.getName());
			addIssue(annotation, ANNOTATION__NAME, issueItem);
			return;
		}
	}

	/**
	 * Check SPEC annotation to be at a formal parameter in a constructor.
	 */
	private boolean internalCheckLocationSpec(Annotation annotation) {
		EObject element = annotation.getAnnotatedElement();
		if (element != null) {
			EObject parent = element.eContainer();
			if (parent instanceof N4MethodDeclaration) {
				if (((N4MethodDeclaration) parent).isConstructor()) {
					return true; // annotation located at the right place
				}
			}
		}
		IssueItem issueItem = ANN_ONLY_ALLOWED_LOCATION_CONSTRUCTORS.toIssueItem(annotation.getName());
		addIssue(annotation, ANNOTATION__NAME, issueItem);
		return false;
	}

	/**
	 * Constraints 120 (Provided By Runtime)
	 */
	private void internalCheckProvidedByRuntime(Annotation annotation) {
		EObject element = annotation.getAnnotatedElement();
		if (element == null) {
			return;
		}
		if (!jsVariantHelper.isExternalMode(element)) {
			IssueItem issueItem = ANN_DISALLOWED_IN_NONDEFINTION_FILE.toIssueItem(annotation.getName());
			addIssue(annotation, ANNOTATION__NAME, issueItem);
			return;
		}
		Resource resource = element.eResource();
		URI projURI = resource.getURI();
		N4JSProjectConfigSnapshot project = workspaceAccess.findProjectByNestedLocation(annotation, projURI);
		if (project == null) {
			if (!N4Scheme.isResourceWithN4Scheme(resource)) { // built-in type definition files are not contained in a
																// project
				IssueItem issueItem = NO_PROJECT_FOUND.toIssueItem(projURI);
				Script script = EcoreUtil2.getContainerOfType(element, Script.class);
				addIssue(script, issueItem);
			}
		} else {
			ProjectType projectType = project.getType();
			if (projectType != ProjectType.RUNTIME_ENVIRONMENT && projectType != ProjectType.RUNTIME_LIBRARY) {
				IssueItem issueItem = ANN_DISALLOWED_IN_NON_RUNTIME_COMPONENT.toIssueItem(annotation.getName());
				addIssue(annotation, ANNOTATION__NAME, issueItem);
				return;
			}
		}
	}

	/**
	 * 11.1.3. Implementation of External Declarations
	 */
	private void internalCheckIgnoreImplementation(Annotation annotation) {
		EObject element = annotation.getAnnotatedElement();
		if (element == null) {
			return;
		}
		if (!jsVariantHelper.isExternalMode(element)) {
			IssueItem issueItem = ANN_DISALLOWED_IN_NONDEFINTION_FILE.toIssueItem(annotation.getName());
			addIssue(annotation, ANNOTATION__NAME, issueItem);
			return;
		}
	}

	/**
	 * Constraints 140 (Restrictions on static-polyfilling) §140.3
	 */
	private void internalCheckStaticPolyfillModule(Annotation annotation) {
		EObject element = annotation.getAnnotatedElement();
		if (element == null) {
			return;
		}

		if (!(element instanceof Script)) {
			return;
		}
		Script script = (Script) element;

		// mutual exclusive with poly-fill aware.
		if (isContainedInStaticPolyfillAware(script)) {
			addIssue(annotation, ANNOTATION__NAME, ANN_POLY_AWARE_AND_MODULE_MUTUAL_EXCLUSIVE.toIssueItem());
			return;
		}

		QualifiedName moduleQN = qnProvider.getFullyQualifiedName(script.getModule());
		// check
		if (!PolyfillUtils.isModulePolyfill(moduleQN)) {
			URI uri = script.getModule() == null || script.getModule().eResource() == null
					? null
					: script.getModule().eResource().getURI();

			LOGGER.warn("### strange should start with '!MPOLY' for " + uri);
			return;
		}

		if (moduleQN == null)
			return;

		Resource resource = script.eResource();
		if (resource == null) {
			return;
		}
		N4JSWorkspaceConfigSnapshot ws = workspaceAccess.getWorkspaceConfig(resource);
		N4JSProjectConfigSnapshot currentProject = ws.findProjectByNestedLocation(resource.getURI());
		if (currentProject == null) {
			return;
		}

		IResourceDescriptions index = indexAccess.getResourceDescriptions(resource.getResourceSet());

		IResourceDescription currentResDesc = index.getResourceDescription(resource.getURI());

		List<IResourceDescription> sameResdesc = new ArrayList<>();
		for (IEObjectDescription desc : index.getExportedObjectsByType(TypesPackage.Literals.TMODULE)) {
			if (desc.getQualifiedName() != null && desc.getQualifiedName().startsWith(moduleQN)) {
				URI uri = desc.getEObjectURI();
				URI uriFragment = uri.trimFragment();
				IResourceDescription frmgtRD = index.getResourceDescription(uriFragment);
				if (currentResDesc != frmgtRD) { // not me
					N4JSProjectConfigSnapshot prj = ws.findProjectByNestedLocation(frmgtRD.getURI());
					if (currentProject == prj) {
						sameResdesc.add(frmgtRD);
					}
				}
			}
		}

		if (!sameResdesc.isEmpty()) {
			// multiple Resources with Static Polyfill Module.
			String msg_prefix = "module" + ((sameResdesc.size() > 1) ? "s" : "") + " in ";
			String clashes = Joiner.on(", ").join(map(sameResdesc, rd -> {
				N4JSSourceFolderSnapshot srcCon = ws.findSourceFolderContaining(rd.getURI());
				if (srcCon != null) {
					return srcCon.getRelativeLocation();
				} else {
					return rd.getURI().toString();
				}
			}));
			IssueItem issueItem = POLY_CLASH_IN_STATIC_POLYFILL_MODULE.toIssueItem(msg_prefix + clashes);
			addIssue(annotation, ANNOTATION__NAME, issueItem);
		}
	}

	/**
	 * Constraints 139 (static polyfill layout) §139
	 *
	 */
	private void internalCheckStaticPolyfill(Annotation annotation) {
		AnnotableElement element = (AnnotableElement) annotation.getAnnotatedElement();
		if (element == null) {
			return;
		}

		// inside a static-polyfill module (IDE-1735)
		if (!isContainedInStaticPolyfillModule(element)) { // transitively inherited
			// not in a polyfill-module
			addIssue(annotation, ANNOTATION__NAME, ANN_POLY_STATIC_POLY_ONLY_IN_POLYFILL_MODULE.toIssueItem());
			return;
		}

		EObject annoTarget = element;
		if (element instanceof ExportDeclaration) {
			annoTarget = ((ExportDeclaration) element).getExportedElement();
		}

		// only classes can be polyfilled (class expressions not supported)
		if (!(annoTarget instanceof N4ClassDeclaration)) {
			// n.t.d.
			// annotation-definition only allowes StaticPolyFill on N4ClassDeclarations.
		}

		// val N4ClassDeclaration classDecl = annoTarget as N4ClassDeclaration
		// §139.4 class must be top-level:
		// - is ensured due to Annotation-definition restricted to N4ClassDeclarations.
	}

	@Check
	public void checkPromisifiableMethod(FunctionDeclaration methodDecl) {
		if (PROMISIFIABLE.hasAnnotation(methodDecl)) {
			holdsPromisifiablePreconditions(methodDecl);
		}
	}

	@Check
	public void checkPromisifiableMethod(N4MethodDeclaration methodDecl) {
		if (PROMISIFIABLE.hasAnnotation(methodDecl)) {
			holdsPromisifiablePreconditions(methodDecl);
		}
	}

	private boolean holdsPromisifiablePreconditions(FunctionDefinition funDef) {
		switch (promisifyHelper.checkPromisifiablePreconditions(funDef)) {
		case MISSING_CALLBACK: {
			addIssue(PROMISIFIABLE.getAnnotation(funDef), ANN_PROMISIFIABLE_MISSING_CALLBACK.toIssueItem());
			return false;
		}
		case BAD_CALLBACK__MORE_THAN_ONE_ERROR: {
			addIssue(PROMISIFIABLE.getAnnotation(funDef),
					ANN_PROMISIFIABLE_BAD_CALLBACK_MORE_THAN_ONE_ERROR.toIssueItem());
			return false;
		}
		case BAD_CALLBACK__ERROR_NOT_FIRST_ARG: {
			addIssue(PROMISIFIABLE.getAnnotation(funDef),
					ANN_PROMISIFIABLE_BAD_CALLBACK_ERROR_NOT_FIRST_ARG.toIssueItem());
			return false;
		}
		case OK: {
			return true;
		}
		}
		return false;
	}
}
