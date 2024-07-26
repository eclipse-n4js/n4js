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

import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.JSX_ELEMENT__JSX_CLOSING_NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.JSX_ELEMENT__JSX_ELEMENT_NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.JSX_PROPERTY_ATTRIBUTE__PROPERTY;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.JSX_SPREAD_ATTRIBUTE__EXPRESSION;
import static org.eclipse.n4js.tooling.organizeImports.ImportSpecifiersUtil.importedModule;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isAnyDynamic;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.validation.IssueCodes.JSX_JSXELEMENT_IN_NON_JSX_RESOURCE;
import static org.eclipse.n4js.validation.IssueCodes.JSX_JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH;
import static org.eclipse.n4js.validation.IssueCodes.JSX_JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED;
import static org.eclipse.n4js.validation.IssueCodes.JSX_JSXSPREADATTRIBUTE_WRONG_SUBTYPE;
import static org.eclipse.n4js.validation.IssueCodes.JSX_JSXSPROPERTYATTRIBUTE_CHILDREN;
import static org.eclipse.n4js.validation.IssueCodes.JSX_JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS;
import static org.eclipse.n4js.validation.IssueCodes.JSX_NAME_CANNOT_BE_REACT;
import static org.eclipse.n4js.validation.IssueCodes.JSX_REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE;
import static org.eclipse.n4js.validation.IssueCodes.JSX_REACT_ELEMENT_CLASS_MUST_NOT_BE_ABSTRACT;
import static org.eclipse.n4js.validation.IssueCodes.JSX_REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR;
import static org.eclipse.n4js.validation.IssueCodes.JSX_REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR;
import static org.eclipse.n4js.validation.IssueCodes.JSX_REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR;
import static org.eclipse.n4js.validation.IssueCodes.JSX_REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE;
import static org.eclipse.n4js.validation.IssueCodes.JSX_REACT_NAMESPACE_NOT_ALLOWED;
import static org.eclipse.n4js.validation.IssueCodes.JSX_REACT_NOT_RESOLVED;
import static org.eclipse.n4js.validation.IssueCodes.JSX_TAG_UNKNOWN;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.findFirst;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.JSXAbstractElement;
import org.eclipse.n4js.n4JS.JSXAttribute;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.JSXSpreadAttribute;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tooling.react.ReactHelper;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 * Validation of React bindings including naming convention (components in upper case and HTML tags in lower case)
 */
@SuppressWarnings("javadoc")
public class N4JSXValidator extends AbstractN4JSDeclarativeValidator {
	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private ReactHelper reactHelper;

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
	 * This method checks that JSXElement is not placed in JSX like resource.
	 */
	@Check
	public void checkJSXinN4JS(JSXElement jsxElem) {
		ResourceType resType = ResourceType.getResourceType(jsxElem);
		if (resType == ResourceType.N4JSX || resType == ResourceType.JSX) {
			return;
		}
		addIssue(jsxElem, JSX_JSXELEMENT_IN_NON_JSX_RESOURCE.toIssueItem(resType.name()));
	}

	/**
	 * We need jsx resources to depend on jsx backend. We are patching imports in the transpiler (to add the import to
	 * jsx backend if it is missing), but transpiler will crash if that import will be invalid, i.e. project has no
	 * dependency on jsx backend. It would be ideal to add validation on manifest and not transpile, at least jsx files.
	 * Unfortunately changes to the manifest are a bit disconnected to changes of the individual files, e.g. adding jsx
	 * file does not trigger manifest validation. Also errors in the manifest do not prevent single file compilation.
	 *
	 * @apiNote https://github.com/eclipse/n4js/issues/346
	 */
	@Check
	public void checkProjectDependsOnReact(Script script) {
		ResourceType resourceType = ResourceType.getResourceType(script);
		if (!(ResourceType.N4JSX == resourceType || ResourceType.JSX == resourceType)) {
			return;
		}

		JSXAbstractElement firstJSXAbstractElement = (JSXAbstractElement) findFirst(script.eAllContents(),
				it -> it instanceof JSXAbstractElement);
		if (firstJSXAbstractElement != null && reactHelper.getJsxBackendModule(script.eResource()) == null) {
			addIssue(firstJSXAbstractElement, JSX_REACT_NOT_RESOLVED);
		}
	}

	/** Make sure the namespace to react module is React. */
	@Check
	public void checkReactImport(NamespaceImportSpecifier importSpecifier) {
		Resource resource = importSpecifier.eResource();
		ResourceType resourceType = ResourceType.getResourceType(resource);
		if (!(ResourceType.N4JSX == resourceType || ResourceType.JSX == resourceType)) {
			return;
		}

		TModule reactModule = reactHelper.getJsxBackendModule(resource);
		TModule importedModule = importedModule(importSpecifier);
		if (reactModule != null && importedModule == reactModule) {
			if (!ReactHelper.REACT_NAMESPACE_NAME.equals(importSpecifier.getAlias())) {
				addIssue(importSpecifier, JSX_REACT_NAMESPACE_NOT_ALLOWED);
			}
		}
	}

	/**
	 * This method checks that JSXElement bind to a valid React component function or class React component declaration
	 * See Req. 241103
	 */
	@Check
	public void checkOpeningClosingElementMismatch(JSXElement jsxElem) {
		if (jsxElem == null || jsxElem.getJsxElementName() == null || jsxElem.getJsxClosingName() == null) {
			return;
		}
		String openingName = getRefName(jsxElem.getJsxElementName().getExpression());
		String closingName = getRefName(jsxElem.getJsxClosingName().getExpression());

		if (!Objects.equals(openingName, closingName)) {
			// Only check if the closing element exists, e.g. not null
			addIssue(
					jsxElem,
					JSX_ELEMENT__JSX_CLOSING_NAME,
					JSX_JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH.toIssueItem(openingName, closingName));
		}
	}

	/**
	 * This method checks that JSXElement bind to a valid React component function or class React component declaration
	 */
	@Check
	public void checkReactElementBinding(JSXElement jsxElem) {
		RuleEnvironment G = newRuleEnvironment(jsxElem);
		Expression expr = jsxElem.getJsxElementName().getExpression();
		TypeRef exprTypeRef = reactHelper.getJsxElementBindingType(jsxElem);
		FunctionTypeExprOrRef callable = tsh.getFunctionTypeExprOrRef(G, exprTypeRef);
		boolean isFunction = callable != null;
		boolean isClass = exprTypeRef instanceof TypeTypeRef && ((TypeTypeRef) exprTypeRef).isConstructorRef();

		if (!isFunction && !isClass) {
			String refName = getRefName(expr);
			if ((refName != null) && Character.isLowerCase(refName.charAt(0))) {
				// See Req. IDE-241118
				// If the JSX element name starts with lower case, warning if it is unknown HTML tag
				if (!N4JSGlobals.HTML_TAGS.contains(refName)
						&& !N4JSGlobals.SVG_TAGS.contains(refName)) {
					addIssue(
							jsxElem,
							JSX_ELEMENT__JSX_ELEMENT_NAME,
							JSX_TAG_UNKNOWN.toIssueItem(refName));
				}
			} else if (isAnyDynamic(G, exprTypeRef)) {
				return;
			} else if (exprTypeRef instanceof TypeTypeRef
					&& ((TypeTypeRef) exprTypeRef).getTypeArg() != null
					&& ((TypeTypeRef) exprTypeRef).getTypeArg().getDeclaredType() instanceof TClass
					&& ((TClass) ((TypeTypeRef) exprTypeRef).getTypeArg().getDeclaredType()).isAbstract()) {
				// JSX element name starts with an upper case, error because it does not bind to a class or function
				// See Req. IDE-241115
				addIssue(expr, JSX_REACT_ELEMENT_CLASS_MUST_NOT_BE_ABSTRACT.toIssueItem());
			} else {
				// JSX element name starts with an upper case, error because it does not bind to a class or function
				// See Req. IDE-241115
				addIssue(expr,
						JSX_REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR.toIssueItem(exprTypeRef.getTypeRefAsString()));
			}
			return;
		}

		if (isFunction) {
			checkFunctionTypeExprOrRef(jsxElem, callable);
			checkReactComponentShouldStartWithUppercase(jsxElem, true);
		}

		if (isClass) {
			checkTypeTypeRefConstructor(jsxElem, (TypeTypeRef) exprTypeRef);
			checkReactComponentShouldStartWithUppercase(jsxElem, false);
		}

		// Furthermore, check that all non-optional fields of the properties type are used
		checkAllNonOptionalFieldsAreSpecified(jsxElem);
	}

	/**
	 * Check that a React function/class component should start with an upper case See Req. 241101
	 */
	private void checkReactComponentShouldStartWithUppercase(JSXElement jsxElem, boolean isFunctionalComponent) {
		Expression expr = jsxElem.getJsxElementName().getExpression();
		String refName = getRefName(expr);
		if (refName != null && !refName.isEmpty() && Character.isLowerCase(refName.charAt(0))) {
			if (isFunctionalComponent) {
				addIssue(
						jsxElem,
						JSX_ELEMENT__JSX_ELEMENT_NAME,
						JSX_REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE.toIssueItem(refName));
			} else {
				addIssue(
						jsxElem,
						JSX_ELEMENT__JSX_ELEMENT_NAME,
						JSX_REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE.toIssueItem(refName));
			}
		}
	}

	/**
	 * The JSX element binds to a function or function expression, check that the return type is a subtype of
	 * React.Element See Req. IDE-241116
	 */
	private void checkFunctionTypeExprOrRef(JSXElement jsxElem, FunctionTypeExprOrRef exprTypeRef) {
		TClassifier tReactNode = reactHelper.lookUpReactNode(jsxElem);
		if (tReactNode == null) {
			return;
		}

		TypeRef expectedReturnTypeRef = TypeUtils.createTypeRef(tReactNode, TypingStrategy.DEFAULT, true);

		Expression expr = jsxElem.getJsxElementName().getExpression();
		RuleEnvironment G = newRuleEnvironment(expr);
		TypeRef actualReturnTypeRef = exprTypeRef.getReturnTypeRef();
		Result result = ts.subtype(G, actualReturnTypeRef, expectedReturnTypeRef);
		if (result.isFailure()) {
			IssueItem issueItem = JSX_REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR.toIssueItem(
					expectedReturnTypeRef.getTypeRefAsString(),
					actualReturnTypeRef.getTypeRefAsString());
			addIssue(expr, issueItem);
		}
	}

	/**
	 * The JSX element binds to a class, check that the class type is a subtype of React.Component See Req. IDE-241116
	 */
	private void checkTypeTypeRefConstructor(JSXElement jsxElem, TypeTypeRef exprTypeRef) {
		TClass tReactComponent = reactHelper.lookUpReactComponent(jsxElem);
		if (tReactComponent == null) {
			return;
		}

		TypeRef expectedTypeRef = TypeUtils.createTypeRef(tReactComponent, TypingStrategy.DEFAULT, true);

		Expression expr = jsxElem.getJsxElementName().getExpression();
		RuleEnvironment G = newRuleEnvironment(expr);
		Type tclass = tsh.getStaticType(G, exprTypeRef);
		TypeRef actualTypeRef = TypeUtils.createTypeRef(tclass, TypingStrategy.DEFAULT, true);
		Result resultSubType = ts.subtype(G, actualTypeRef, expectedTypeRef);
		if (resultSubType.isFailure()) {
			addIssue(expr, JSX_REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR);
		}
	}

	@Check
	public void checkUnknownJSXPropertyAttribute(JSXPropertyAttribute propertyAttribute) {
		JSXElement jsxElem = (JSXElement) propertyAttribute.eContainer();
		TypeRef exprTypeRef = reactHelper.getJsxElementBindingType(jsxElem);
		boolean isFunction = exprTypeRef instanceof FunctionTypeExprOrRef;
		boolean isClass = exprTypeRef instanceof TypeTypeRef && ((TypeTypeRef) exprTypeRef).isConstructorRef();
		if (!isFunction && !isClass) {
			return;
		}

		RuleEnvironment G = newRuleEnvironment(propertyAttribute);
		TypeRef result = ts.type(G, propertyAttribute.getProperty());
		// TODO: it's not nice that we get an UnknownTypeRef, here;
		// they are mainly intended for error cases, not valid code. Probably it should be any+ instead.
		// This requires refactoring else where
		if (result instanceof UnknownTypeRef) {
			String name = jsxElem == null || jsxElem.getJsxElementName() == null
					|| jsxElem.getJsxElementName().getExpression() == null ? null
							: getRefName(jsxElem.getJsxElementName().getExpression());
			IssueItem issueItem = JSX_JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS
					.toIssueItem(propertyAttribute.getPropertyAsText(), name);
			addIssue(
					propertyAttribute,
					JSX_PROPERTY_ATTRIBUTE__PROPERTY,
					issueItem);
		}
	}

	@Check
	public void checkChildrenJSXPropertyAttribute(JSXPropertyAttribute propertyAttribute) {
		if (!ReactHelper.REACT_ELEMENT_PROPERTY_CHILDREN_NAME.equals(propertyAttribute.getPropertyAsText())) {
			return;
		}
		JSXElement jsxElem = (JSXElement) propertyAttribute.eContainer();
		if (jsxElem.getJsxChildren().isEmpty()) {
			return;
		}

		addIssue(
				propertyAttribute,
				JSX_PROPERTY_ATTRIBUTE__PROPERTY,
				JSX_JSXSPROPERTYATTRIBUTE_CHILDREN.toIssueItem());
	}

	/**
	 * Check the type conformity of types of spread operator's attributes against "props" types See Req. IDE-241119
	 */
	@Check
	public void checkAttributeAndTypeConformityInJSXSpreadAttribute(JSXSpreadAttribute spreadAttribute) {
		Expression expr = spreadAttribute == null ? null : spreadAttribute.getExpression();
		JSXElement jsxElem = spreadAttribute == null ? null : (JSXElement) spreadAttribute.eContainer();
		TypeRef propsType = jsxElem == null ? null : reactHelper.getPropsType(jsxElem);
		if (propsType == null) {
			return;
		}

		RuleEnvironment G = newRuleEnvironment(spreadAttribute);
		// Retrieve fields or getters in props type
		Iterable<TMember> fieldsOrGettersInProps = filter(
				tsh.getStructuralTypesHelper().collectStructuralMembers(G, propsType,
						TypingStrategy.STRUCTURAL),
				m -> (m instanceof TField) || (m instanceof TGetter));

		TypeRef exprTypeResult = ts.type(G, expr);
		// Retrieve attributes (either field or getter) in spread operator type
		Iterable<TMember> attributesInSpreadOperatorType = filter(
				tsh.getStructuralTypesHelper().collectStructuralMembers(G, exprTypeResult,
						TypingStrategy.STRUCTURAL),
				m -> (m instanceof TField) || (m instanceof TGetter));

		// commented out but not deleted for now since still it is not clear if this check makes sense
		// spreadAttribute.checkUnknownAttributeInSpreadOperator(jsxElem, attributesInSpreadOperatorType,
		// fieldsOrGettersInProps);

		// Type check each attribute in spreader operator against the corresponding props type's field/getter
		for (TMember attributeInSpreadOperator : attributesInSpreadOperatorType) {
			TypeRef attributeInSpreadOperatorTypeRef = reactHelper.typeRefOfFieldOrGetter(attributeInSpreadOperator,
					exprTypeResult);
			TMember fieldOrGetterInProps = findFirst(fieldsOrGettersInProps,
					fieldOrGetter -> Objects.equals(attributeInSpreadOperator.getName(), fieldOrGetter.getName()));

			if (fieldOrGetterInProps != null) {
				// Reason for using tau: Consider type arguments by calculating the property of within the context of
				// "props" type
				TypeRef fieldOrGetterInPropsTypeRef = ts.tau(fieldOrGetterInProps, propsType);
				Result result = ts.subtype(G, attributeInSpreadOperatorTypeRef, fieldOrGetterInPropsTypeRef);
				if (result.isFailure()) {
					IssueItem issueItem = JSX_JSXSPREADATTRIBUTE_WRONG_SUBTYPE.toIssueItem(
							attributeInSpreadOperator.getName(),
							attributeInSpreadOperatorTypeRef.getTypeRefAsString(),
							fieldOrGetterInPropsTypeRef.getTypeRefAsString());
					addIssue(
							spreadAttribute,
							JSX_SPREAD_ATTRIBUTE__EXPRESSION,
							issueItem);
				}
			}
		}
	}

	/**
	 * Check that a named element is not named React in N4JSX file to avoid naming clash.
	 */
	@Check
	public void checkNamedElementNotNamedReact(NamedElement elem) {
		ResourceType resourceType = ResourceType.getResourceType(elem);
		// This check is only applicable to N4JSX/JSX file
		if (!(ResourceType.N4JSX == resourceType || ResourceType.JSX == resourceType)) {
			return;
		}

		if (ReactHelper.REACT_NAMESPACE_NAME.equals(elem.getName())) {
			addIssue(
					elem,
					findNameFeature(elem).getValue(),
					JSX_NAME_CANNOT_BE_REACT.toIssueItem());
		}
	}

	/**
	 * Check that non-optional fields of "props" should be specified in JSX element See Req. IDE-241117
	 */
	private void checkAllNonOptionalFieldsAreSpecified(JSXElement jsxElem) {
		List<JSXAttribute> jsxPropertyAttributes = jsxElem.getJsxAttributes();
		// First, collect all normal properties in JSX element
		List<IdentifiableElement> allAttributesInJSXElement = new ArrayList<>(
				toList(map(filter(jsxPropertyAttributes, JSXPropertyAttribute.class), a -> a.getProperty())));
		TypeRef propsType = reactHelper.getPropsType(jsxElem);
		if (propsType == null) {
			return;
		}

		RuleEnvironment G = newRuleEnvironment(jsxElem);
		// Then collect attributes in spread operators
		List<TMember> attributesInSpreadOperator = toList(
				flatten(map(filter(jsxPropertyAttributes, JSXSpreadAttribute.class), spreadAttribute -> {
					TypeRef exprTypeRefResult = ts.type(G, spreadAttribute.getExpression());
					Iterable<TMember> members = tsh.getStructuralTypesHelper().collectStructuralMembers(G,
							exprTypeRefResult, TypingStrategy.STRUCTURAL);
					return filter(members, m -> (m instanceof TField) || (m instanceof TGetter));

				})));
		allAttributesInJSXElement.addAll(attributesInSpreadOperator);

		// Retrieve all non-optional fields or getters in "props" type
		Iterable<TMember> nonOptionalFieldsOrGettersInProps = filter(
				tsh.getStructuralTypesHelper().collectStructuralMembers(G, propsType, TypingStrategy.STRUCTURAL),
				m -> (m instanceof TField || m instanceof TGetter) && !m.isOptional());
		// Calculate the set of unspecified non-optional properties
		String missingFieldsStringRep = Strings.join(",", map(
				filter(nonOptionalFieldsOrGettersInProps,
						fieldOrGetter -> !(exists(allAttributesInJSXElement,
								attribute -> Objects.equals(attribute.getName(), fieldOrGetter.getName())))),
				fieldOrGetter -> fieldOrGetter.getName()));

		if (!missingFieldsStringRep.isEmpty()) {
			addIssue(
					jsxElem,
					JSX_ELEMENT__JSX_ELEMENT_NAME,
					JSX_JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED.toIssueItem(missingFieldsStringRep));
		}
	}

	/**
	 * Calculate the reference name of an expression, should be used only for expressions within JSX element!
	 */
	private String getRefName(Expression expr) {
		String refName = null;
		if (expr instanceof IdentifierRef) {
			refName = ((IdentifierRef) expr).getIdAsText();
		} else if (expr instanceof ParameterizedPropertyAccessExpression) {
			refName = ((ParameterizedPropertyAccessExpression) expr).getPropertyAsText();
		}
		return refName;
	}
}
