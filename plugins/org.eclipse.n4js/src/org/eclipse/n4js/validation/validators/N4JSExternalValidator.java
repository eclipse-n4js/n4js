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

import static org.eclipse.n4js.validation.IssueCodes.ANN__N4JS_NO_EFFECT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_CONSUME_NON_EXT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_EXTERNAL;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_EXTERNAL_N4JSD;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_FUN_NO_BODY;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_LITERAL_NO_VALUE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_METHOD_NO_ANNO;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_NOT_ANNOTATED_EXTEND_N4OBJECT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_NO_FIELD_EXPR;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_NO_METHOD_BODY;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_NO_OBSERV_ANNO;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_PROVIDED_BY_RUNTIME_IN_RUNTIME_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_PROVIDES_IMPL_ONLY_IN_DEFFILES;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_PROVIDES_IMPL_ONLY_IN_INTERFACE_MEMBERS;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_PROVIDES_IMPL_ONLY_IN_N4JS_INTERFACES;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_UNALLOWED_N4JSD;
import static org.eclipse.n4js.validation.IssueCodes.CLF_EXT_VAR_NO_VAL;
import static org.eclipse.n4js.validation.IssueCodes.CLF_IN_DEFINITION_PRJ_NON_N4JS;
import static org.eclipse.n4js.validation.IssueCodes.ITF_IN_DEFINITION_PRJ_NON_N4JS;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.StringExtensions.toFirstUpper;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.EmptyStatement;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4NamespaceDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeAliasDeclaration;
import org.eclipse.n4js.n4JS.N4TypeDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.util.Tuples;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

/**
 */
public class N4JSExternalValidator extends AbstractN4JSDeclarativeValidator {

	/**
	 * Helper structure for 13.1. constraints validation
	 */
	private final List<? extends Triple<AnnotationDefinition, String, ? extends Class<? extends N4ClassifierDeclaration>>> PROVIDES_ANNOTATION_INFO = List
			.of(
					Tuples.create(AnnotationDefinition.PROVIDES_DEFAULT_IMPLEMENTATION, "interfaces",
							N4InterfaceDeclaration.class),
					Tuples.create(AnnotationDefinition.PROVIDES_INITIALZER, "classifiers",
							N4ClassifierDeclaration.class));

	@Inject
	private WorkspaceAccess workspaceAccess;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

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
	 * Temporary validation
	 */
	@Check
	public void checkAnnotationsInN4JSDFile(Annotation annotation) {
		if (AnnotationDefinition.N4JS.isAnnotation(annotation)) {
			addIssue(annotation, ANN__N4JS_NO_EFFECT.toIssueItem());
		}
	}

	/**
	 * @apiNote N4Spec, 4.20. External Classes
	 */
	@Check
	public void checkExternalClassesDefinedInN4JSDFile(N4ClassDeclaration clazz) {
		if (!holdsExternalOnlyInDefinitionFile(clazz, "Classes")) {
			return;
		}
		if (clazz.isExternal() && jsVariantHelper.isExternalMode(clazz)) {
			N4JSProjectConfigSnapshot prj = workspaceAccess.findProjectContaining(clazz);
			ProjectType projectType = prj == null ? null : prj.getType();
			if (projectType == ProjectType.DEFINITION && !AnnotationDefinition.ECMASCRIPT.hasAnnotation(clazz)) {
				addIssue(clazz, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
						CLF_IN_DEFINITION_PRJ_NON_N4JS.toIssueItem());
				return;
			}
		}
	}

	/**
	 * @apiNote N4Spec, 4.20. External Interfaces
	 */
	@Check
	public void checkExternalInterfacesDefinedInN4JSDFile(N4InterfaceDeclaration interfaceDecl) {
		if (!holdsExternalOnlyInDefinitionFile(interfaceDecl, "Interfaces")) {
			return;
		}
		if (interfaceDecl.isExternal() && jsVariantHelper.isExternalMode(interfaceDecl)) {
			boolean isStructural = TypeUtils.isStructural(interfaceDecl.getTypingStrategy());
			N4JSProjectConfigSnapshot prj = workspaceAccess.findProjectContaining(interfaceDecl);
			ProjectType projectType = prj == null ? null : prj.getType();
			if (!isStructural && projectType == ProjectType.DEFINITION) {
				addIssue(interfaceDecl, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
						ITF_IN_DEFINITION_PRJ_NON_N4JS.toIssueItem());
				return;
			}
		}
	}

	/**
	 * @apiNote N4Spec, 4.20. External Enums
	 */
	@Check
	public void checkExternalEnumsDefinedInN4JSDFile(N4EnumDeclaration enumDecl) {
		if (!holdsExternalOnlyInDefinitionFile(enumDecl, "Enumerations")) {
			return;
		}
	}

	private boolean holdsExternalOnlyInDefinitionFile(N4TypeDeclaration typeDecl, String typesName) {
		if (typeDecl.isExternal() && !jsVariantHelper.isExternalMode(typeDecl)) {
			addIssue(typeDecl, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
					CLF_EXT_EXTERNAL_N4JSD.toIssueItem(typesName));
			return false;
		}
		return true;
	}

	/**
	 * 13.1 ExternalDeclarations, Constraints 138.4: External class/interface members
	 * {@code @ProvidesDefaultImplementation}.
	 */
	@Check
	public void checkProvidesDefaultImplementationAnnotation(N4MemberDeclaration memberDecl) {
		if (memberDecl.getName() == null) { // ignore invalid/incomplete situations which would lead to duplicate errors
			return;
		}

		for (Triple<AnnotationDefinition, String, ? extends Class<? extends N4ClassifierDeclaration>> valInfo : PROVIDES_ANNOTATION_INFO) {

			// @ProvidesInitializer is supported for setters in n4js files as well. (IDE-1996)
			if (AnnotationDefinition.PROVIDES_INITIALZER == valInfo.getFirst() &&
					memberDecl instanceof N4SetterDeclaration) {
				return;
			}

			AnnotationDefinition annodef = valInfo.getFirst();
			String typeName = valInfo.getSecond();
			Class<?> metaType = valInfo.getThird();

			if (annodef.hasAnnotation(memberDecl)) {
				if (!jsVariantHelper.isExternalMode(memberDecl)) {
					IssueItem issueItem = CLF_EXT_PROVIDES_IMPL_ONLY_IN_DEFFILES.toIssueItem(annodef.name, typeName);
					addIssue(memberDecl, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, issueItem);
					return;
				}

				N4ClassifierDefinition owner = memberDecl.getOwner();

				if (!(metaType.isInstance(owner))) {
					IssueItem issueItem = CLF_EXT_PROVIDES_IMPL_ONLY_IN_INTERFACE_MEMBERS.toIssueItem(annodef.name,
							typeName);
					addIssue(memberDecl, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, issueItem);
					return;
				}

				if (N4JSLanguageUtils.isShapeOrEcmaScript(owner)) {
					IssueItem issueItem = CLF_EXT_PROVIDES_IMPL_ONLY_IN_N4JS_INTERFACES.toIssueItem(annodef.name);
					addIssue(memberDecl, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, issueItem);
					return;

				}
			}
		}
	}

	/**
	 * @apiNote N4Spec, 4.20. External Classes and Roles
	 */
	@Check
	public void checkExternalFunctionsDefinedInN4JSDFile(FunctionDeclaration funDecl) {
		if (funDecl.isExternal() && !jsVariantHelper.isExternalMode(funDecl)) {
			addIssue(funDecl, N4JSPackage.Literals.FUNCTION_DECLARATION__NAME,
					CLF_EXT_EXTERNAL_N4JSD.toIssueItem("Functions"));
		}
	}

	/**
	 * No assignment in n4jsd.
	 */
	@Check
	public void checkExternalVariableStatementAssigments(VariableStatement variableStatement) {
		if (!variableStatement.isDirectlyExported()) {
			return;
		}

		if (variableStatement.isExternal() &&
				!jsVariantHelper.isExternalMode(variableStatement)) {
			addIssue(variableStatement, CLF_EXT_EXTERNAL_N4JSD.toIssueItem("Variables"));
		}

		for (VariableDeclaration evd : variableStatement.getVarDecl()) {
			if (jsVariantHelper.isExternalMode(evd) && evd.getExpression() != null) {
				String mod = (evd.isConst()) ? "constant" : "variable";
				addIssue(evd, CLF_EXT_VAR_NO_VAL.toIssueItem(mod));
			}
		}
	}

	/**
	 * @apiNote N4Spec, 4.20. External Classes and Roles
	 */
	@Check
	public void checkAllowedElementsInN4JSDFile(EObject eo) {
		if (jsVariantHelper.isExternalMode(eo)
				&& (eo.eContainer() instanceof Script || eo.eContainer() instanceof N4NamespaceDeclaration)) {
			boolean found = isUnallowedElement(eo);
			if (found) {
				handleUnallowedElement(eo);
			} else if (eo instanceof ExportDeclaration) {
				ExportableElement exported = ((ExportDeclaration) eo).getExportedElement();
				handleExportDeclaration((ExportDeclaration) eo, exported);
			} else if (eo instanceof ExportableElement) {
				handleExportDeclaration(null, (ExportableElement) eo);
			} else if (!(eo instanceof AnnotationList || eo instanceof Annotation || eo instanceof EmptyStatement ||
					eo instanceof ImportDeclaration)) {
				// relaxed by IDEBUG-561: handleNotExported(eo)
			}
		}
	}

	private void handleExportDeclaration(ExportDeclaration eo, ExportableElement exported) {
		holdsExternalImplementation(exported);
		if (exported instanceof N4ClassDeclaration) {
			handleN4ClassDeclaration(eo, (N4ClassDeclaration) exported);
		} else if (exported instanceof N4InterfaceDeclaration) {
			handleN4InterfaceDeclaration(eo, (N4InterfaceDeclaration) exported);
		} else if (exported instanceof N4EnumDeclaration) {
			handleN4EnumDeclaration((N4EnumDeclaration) exported);
		} else if (exported instanceof FunctionDeclaration) {
			handleFunctionDeclaration((FunctionDeclaration) exported);
		}
	}

	private void handleN4ClassDeclaration(ExportDeclaration eo, N4ClassDeclaration exported) {
		validateClassifierIsExternal(exported, "classes");
		// relaxed by IDEBUG-561: exported.validateClassifierIsPublicApi("classes", eo)
		if (AnnotationDefinition.ECMASCRIPT.hasAnnotation(exported)) {
			TypeReferenceNode<ParameterizedTypeRef> superClassRef = exported.getSuperClassRef();
			TClass superClass = superClassRef == null || superClassRef.getTypeRef() == null ? null
					: hasExpectedTypes(superClassRef.getTypeRef(), TClass.class);
			validateEcmaScriptClassDoesntExtendN4Object(exported, superClass);
			validateConsumptionOfNonExternalInterfaces(exported, exported.getImplementedInterfaceRefs(), "classes");
		}
		validateNoObservableAtClassifier(eo, exported, "classes");
		// relaxed by IDEBUG-561: validatePublicConstructor(exported)
		validateMembers(exported, "classes");
	}

	private void handleN4InterfaceDeclaration(ExportDeclaration eo, N4InterfaceDeclaration exported) {
		if (exported.getTypingStrategy() == TypingStrategy.NOMINAL
				|| exported.getTypingStrategy() == TypingStrategy.DEFAULT) {
			validateClassifierIsExternal(exported, "interfaces");
		}

		validateNoObservableAtClassifier(eo, exported, "interfaces");
		validateMembers(exported, "interfaces");
	}

	private void handleN4EnumDeclaration(N4EnumDeclaration exported) {
		// relaxed by IDEBUG-561: exported.validateEnumIsPublicApi(eo)
		EnumKind enumKind = N4JSLanguageUtils.getEnumKind(exported);
		if (enumKind == EnumKind.Normal) { // note: literal in a number-/string-based enum may have value even in .n4jsd
											// files!
			for (N4EnumLiteral literal : filter(exported.getLiterals(), it -> it.getValueExpression() != null)) {
				addIssue(literal, N4JSPackage.Literals.N4_ENUM_LITERAL__VALUE_EXPRESSION,
						CLF_EXT_LITERAL_NO_VALUE.toIssueItem());
			}
		}
	}

	private void handleFunctionDeclaration(FunctionDeclaration exported) {
		// relaxed by IDEBUG-561: exported.validateFunctionIsPublicApi(eo)
		if (exported.getBody() != null) {
			Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = findNameFeature(exported);
			addIssue(eObjectToNameFeature.getKey(), eObjectToNameFeature.getValue(), CLF_EXT_FUN_NO_BODY.toIssueItem());
		}
	}

	/**
	 * Constraints 120 (Implementation of External Declarations)
	 */
	private boolean holdsExternalImplementation(ExportableElement element) {
		if (element instanceof AnnotableElement) {
			if (AnnotationDefinition.IGNORE_IMPLEMENTATION.hasAnnotation((AnnotableElement) element)) {
				return true;
			}

			if (AnnotationDefinition.PROVIDED_BY_RUNTIME.hasAnnotation((AnnotableElement) element)) {
				return holdsDefinedInRuntime(element);
			}
		}

		return true;
	}

	/**
	 * Constraints 70. 1
	 */
	private boolean holdsDefinedInRuntime(ExportableElement element) {
		N4JSProjectConfigSnapshot prj = workspaceAccess.findProjectContaining(element);
		ProjectType projectType = prj == null ? null : prj.getType();
		if (projectType == null || projectType == ProjectType.RUNTIME_ENVIRONMENT ||
				projectType == ProjectType.RUNTIME_LIBRARY) {
			return true;
		}

		Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = findNameFeature(element);
		addIssue(eObjectToNameFeature.getKey(), eObjectToNameFeature.getValue(),
				CLF_EXT_PROVIDED_BY_RUNTIME_IN_RUNTIME_TYPE.toIssueItem());
		return false;
	}

	private void validateNoObservableAtClassifier(ExportDeclaration ed, N4ClassifierDeclaration declaration,
			String classesOrRolesOrInterface) {
		if (AnnotationDefinition.OBSERVABLE.hasAnnotation(ed)) {
			Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = findNameFeature(declaration);
			addIssue(eObjectToNameFeature.getKey(), eObjectToNameFeature.getValue(),
					CLF_EXT_NO_OBSERV_ANNO.toIssueItem(classesOrRolesOrInterface));
		}
	}

	private void validateMembers(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		validateNoBody(declaration, classesOrRolesOrInterface);
		validateNoFieldExpression(declaration, classesOrRolesOrInterface);
		validateNoObservableAtMember(declaration, classesOrRolesOrInterface);
		validateNoNfonAtMember(declaration, classesOrRolesOrInterface);
	}

	private void validateNoObservableAtMember(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		for (N4MemberDeclaration member : filter(declaration.getOwnedMembers(),
				m -> AnnotationDefinition.OBSERVABLE.hasAnnotation(m))) {
			IssueItem issueItem = CLF_EXT_METHOD_NO_ANNO.toIssueItem(
					toFirstUpper(keywordProvider.keyword(member)) + "s",
					classesOrRolesOrInterface, "Observable");
			addIssue(member, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, issueItem);
		}
	}

	private void validateNoNfonAtMember(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		for (N4MemberDeclaration member : filter(declaration.getOwnedMembers(),
				m -> AnnotationDefinition.NFON.hasAnnotation(m))) {
			IssueItem issueItem = CLF_EXT_METHOD_NO_ANNO.toIssueItem(
					toFirstUpper(keywordProvider.keyword(member)) + "s",
					classesOrRolesOrInterface, "Nfon");
			addIssue(member, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, issueItem);
		}
	}

	private void validateNoFieldExpression(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		for (N4FieldDeclaration member : filter(declaration.getOwnedFields(), f -> f.getExpression() != null)) {
			IssueItem issueItem = CLF_EXT_NO_FIELD_EXPR.toIssueItem(classesOrRolesOrInterface);
			addIssue(member, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, issueItem);
		}
	}

	private void validateNoBody(N4ClassifierDeclaration declaration, String classesOrRolesOrInterface) {
		Iterable<N4MemberDeclaration> members = filter(declaration.getOwnedMembers(),
				m -> !(m instanceof N4FieldDeclaration) && getBody(m) != null);
		for (N4MemberDeclaration member : members) {
			IssueItem issueItem = CLF_EXT_NO_METHOD_BODY.toIssueItem(
					toFirstUpper(keywordProvider.keyword(member)) + "s",
					classesOrRolesOrInterface);
			addIssue(member, N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME, issueItem);
		}
	}

	private void validateEcmaScriptClassDoesntExtendN4Object(N4ClassDeclaration exported, TClass superType) {
		if (superType != null
				&& (!superType.isExternal() || !AnnotationDefinition.ECMASCRIPT.hasAnnotation(superType))) {
			Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = findNameFeature(exported);
			addIssue(eObjectToNameFeature.getKey(), eObjectToNameFeature.getValue(),
					CLF_EXT_NOT_ANNOTATED_EXTEND_N4OBJECT.toIssueItem());
		}
	}

	private void validateClassifierIsExternal(N4ClassifierDefinition exported, String classifiers) {
		if (!exported.isExternal()) {
			Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = findNameFeature(exported);
			addIssue(eObjectToNameFeature.getKey(), eObjectToNameFeature.getValue(),
					CLF_EXT_EXTERNAL.toIssueItem(classifiers));
		}
	}

	private void validateConsumptionOfNonExternalInterfaces(N4ClassDeclaration exported,
			Iterable<TypeReferenceNode<ParameterizedTypeRef>> superInterfaces, String classifiers) {

		Iterable<TInterface> intfs = filterNull(
				map(superInterfaces, intf -> hasExpectedTypes(intf.getTypeRef(), TInterface.class)));
		for (TInterface tinterface : intfs) {
			validateConsumptionOfNonExternalInterface(exported, tinterface, classifiers);
		}
	}

	private void validateConsumptionOfNonExternalInterface(N4ClassDeclaration exported, TInterface tinterface,
			String classifiers) {
		if (!tinterface.isExternal() && tinterface.getTypingStrategy() != TypingStrategy.STRUCTURAL) {
			Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = findNameFeature(exported);
			addIssue(eObjectToNameFeature.getKey(), eObjectToNameFeature.getValue(),
					CLF_EXT_CONSUME_NON_EXT.toIssueItem(classifiers));
		}
	}

	private void handleUnallowedElement(EObject eo) {
		Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = findNameFeature(eo);
		if (eObjectToNameFeature == null) {
			Pair<Integer, Integer> offsetAndLength = findOffsetAndLength(eo);
			addIssue(eo, offsetAndLength.getKey(), offsetAndLength.getValue(), CLF_EXT_UNALLOWED_N4JSD.toIssueItem());
		} else {
			addIssue(eObjectToNameFeature.getKey(), eObjectToNameFeature.getValue(),
					CLF_EXT_UNALLOWED_N4JSD.toIssueItem());
		}
	}

	/**
	 * Returns converted declared type of type reference if it matches the expected type. Otherwise, null is returned.
	 */
	@SuppressWarnings("unchecked")
	private <T extends Type> T hasExpectedTypes(TypeRef typeRef, Class<T> typeClazz) {
		Type type = typeRef == null ? null : typeRef.getDeclaredType();
		if (type != null && typeClazz.isAssignableFrom(type.getClass())) {
			return (T) type;
		}
		return null;
	}

	private boolean isUnallowedElement(EObject eo) {
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
			ExportableElement expElem = ((ExportDeclaration) eo).getExportedElement();
			if (expElem != null) {
				return isUnallowedElement(expElem);
			}
			return false;
		}
		if (eo instanceof N4ClassDeclaration) {
			if (((N4ClassDeclaration) eo).isExternal()) {
				return false;
			}
		}
		if (eo instanceof N4NamespaceDeclaration) {
			if (((N4NamespaceDeclaration) eo).isExternal()) {
				return false;
			}
		}
		if (eo instanceof N4InterfaceDeclaration) {
			N4InterfaceDeclaration id = (N4InterfaceDeclaration) eo;
			if (id.isExternal() || id.getTypingStrategy() == TypingStrategy.STRUCTURAL) {
				return false;
			}
		}
		if (eo instanceof N4EnumDeclaration) {
			if (((N4EnumDeclaration) eo).isExternal()) {
				return false;
			}
		}
		if (eo instanceof N4TypeAliasDeclaration) {
			if (((N4TypeAliasDeclaration) eo).isExternal()) {
				return false;
			}
		}
		if (eo instanceof FunctionDeclaration) {
			if (((FunctionDeclaration) eo).isExternal()) {
				return false;
			}
		}
		if (eo instanceof VariableStatement) {
			return false;
		}
		return true;
	}
}
