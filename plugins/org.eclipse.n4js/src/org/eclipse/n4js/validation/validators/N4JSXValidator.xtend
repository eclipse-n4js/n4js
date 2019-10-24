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

import com.google.common.collect.Lists
import com.google.inject.Inject
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.JSXAbstractElement
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.JSXPropertyAttribute
import org.eclipse.n4js.n4JS.JSXSpreadAttribute
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4jsx.ReactHelper
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.ResourceType
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.JSX_ELEMENT__JSX_CLOSING_NAME
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.JSX_ELEMENT__JSX_ELEMENT_NAME
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.JSX_PROPERTY_ATTRIBUTE__PROPERTY
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.JSX_SPREAD_ATTRIBUTE__EXPRESSION
import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.organize.imports.ImportSpecifiersUtil.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Validation of React bindings including naming convention (components in upper case and HTML tags in lower case)
 */
class N4JSXValidator extends AbstractN4JSDeclarativeValidator {
	@Inject private N4JSTypeSystem ts;
	@Inject private TypeSystemHelper tsh
	@Inject private extension ReactHelper reactHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * This method checks that JSXElement is not placed in JSX like resource.
	 */
	@Check
	def public void checkJSXinN4JS(JSXElement jsxElem) {
		val resType = ResourceType.getResourceType(jsxElem);
		if (resType == ResourceType.N4JSX || resType == ResourceType.JSX) {
			return;
		}
		val message = getMessageForJSX_JSXELEMENT_IN_NON_JSX_RESOURCE(resType.name)
		addIssue(message,jsxElem, JSX_JSXELEMENT_IN_NON_JSX_RESOURCE);
	}


	/**
	 * We need jsx resources to depend on jsx backend. We are patching imports in the transpiler (to add the import to jsx backend if it is missing),
	 * but transpiler will crash if that import will be invalid, i.e. project has no dependency on jsx backend. It would be ideal to add validation 
	 * on manifest and not transpile, at least jsx files. Unfortunately changes to the manifest are a bit disconnected to changes of the individual 
	 * files, e.g. adding jsx file does not trigger manifest validation. Also errors in the manifest do not prevent single file compilation. 
	 * @see https://github.com/eclipse/n4js/issues/346
	 */
	@Check
	def checkProjectDependsOnReact(Script script) {
		val resourceType = ResourceType.getResourceType(script);
		if (!(ResourceType.N4JSX === resourceType || ResourceType.JSX === resourceType))
			return;

		val firstJSXAbstractElement = script.eAllContents.findFirst[it instanceof JSXAbstractElement]
		if (firstJSXAbstractElement !== null && reactHelper.getJsxBackendModule(script.eResource) === null)
			addIssue(getMessageForJSX_REACT_NOT_RESOLVED(), firstJSXAbstractElement, JSX_REACT_NOT_RESOLVED);
	}

	/** Make sure the namespace to react module is React. */
	@Check
	def checkReactImport(NamespaceImportSpecifier importSpecifier) {
		val resource = importSpecifier.eResource;
		val resourceType = ResourceType.getResourceType(resource);
		if (!(ResourceType.N4JSX === resourceType || ResourceType.JSX === resourceType))
			return;

		val reactModule = reactHelper.getJsxBackendModule(resource);
		val importedModule = importSpecifier.importedModule;
		if (reactModule !== null && importedModule === reactModule) {
			if (importSpecifier.alias != ReactHelper.REACT_NAMESPACE_NAME) {
						addIssue(
							getMessageForJSX_REACT_NAMESPACE_NOT_ALLOWED(),
							importSpecifier, JSX_REACT_NAMESPACE_NOT_ALLOWED);
			}
		}
	}


	/**
	 * This method checks that JSXElement bind to a valid React component function or class React component declaration
	 * See Req. 241103
	 */
	@Check
	def public void checkOpeningClosingElementMismatch(JSXElement jsxElem) {
		val openingName = jsxElem?.jsxElementName?.expression?.refName;
		val closingName = jsxElem?.jsxClosingName?.expression?.refName;

		if ((jsxElem.jsxClosingName !==null) && !(openingName == closingName)) {
			//Only check if the closing element exists, e.g. not null
			val message = getMessageForJSX_JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH(openingName, closingName);
			addIssue(
				message,
				jsxElem,
				JSX_ELEMENT__JSX_CLOSING_NAME,
				JSX_JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH
			);
		}
	}

	/**
	 * This method checks that JSXElement bind to a valid React component function or class React component declaration
	 */
	@Check
	def public void checkReactElementBinding(JSXElement jsxElem) {
		val expr = jsxElem.jsxElementName.expression;
		val TypeRef exprTypeRef = reactHelper.getJsxElementBindingType(jsxElem);
		var isFunction = exprTypeRef instanceof FunctionTypeExprOrRef;
		var isClass = exprTypeRef instanceof TypeTypeRef && (exprTypeRef as TypeTypeRef).constructorRef;

		if (!isFunction && !isClass) {
			val String refName = expr.refName
			if ((refName !== null) && Character::isLowerCase(refName.charAt(0))) {
				// See Req. IDE-241118
				// If the JSX element name starts with lower case, warning if it is unknown HTML tag
				if (!N4JSGlobals.HTML_TAGS.contains(refName)
					&& !N4JSGlobals.SVG_TAGS.contains(refName)) {
					val message = getMessageForJSX_TAG_UNKNOWN(refName);
					addIssue(
						message,
						jsxElem,
						JSX_ELEMENT__JSX_ELEMENT_NAME,
						JSX_TAG_UNKNOWN
					);
				}
			} else {
				// JSX element name starts with an upper case, error because it does not bind to a class or function
				// See Req. IDE-241115
				val message = getMessageForJSX_REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR(exprTypeRef.typeRefAsString);
				addIssue(message, expr, JSX_REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR);
			}
			return;
		}

		if (isFunction) {
			checkFunctionTypeExprOrRef(jsxElem, exprTypeRef as FunctionTypeExprOrRef);
			checkReactComponentShouldStartWithUppercase(jsxElem, true);
		}

		if (isClass) {
			checkTypeTypeRefConstructor(jsxElem, exprTypeRef as TypeTypeRef);
			checkReactComponentShouldStartWithUppercase(jsxElem, false);
		}

		// Furthermore, check that all non-optional fields of the properties type are used
		checkAllNonOptionalFieldsAreSpecified(jsxElem, exprTypeRef);
	}

	/**
	 * Check that a React function/class component should start with an upper case
	 * See Req. 241101
	 */
	def private void checkReactComponentShouldStartWithUppercase(JSXElement jsxElem, boolean isFunctionalComponent) {
		val expr = jsxElem.jsxElementName.expression;
		val String refName = expr.refName
		if ((refName !== null) && (!refName.isEmpty) && Character::isLowerCase(refName.charAt(0))) {
			if (isFunctionalComponent) {
				val message = getMessageForJSX_REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE(refName);
				addIssue(
					message,
					jsxElem,
					JSX_ELEMENT__JSX_ELEMENT_NAME,
					JSX_REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE
				);
			} else {
				val message = IssueCodes.getMessageForJSX_REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE(refName);
				addIssue(
					message,
					jsxElem,
					JSX_ELEMENT__JSX_ELEMENT_NAME,
					JSX_REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE
				);
			}
		}
	}

	/**
	 * The JSX element binds to a function or function expression, check that the return type is a subtype of React.Element
	 * See Req. IDE-241116
	 */
	def private void checkFunctionTypeExprOrRef(JSXElement jsxElem, FunctionTypeExprOrRef exprTypeRef) {
		val elementClassTypeRef = reactHelper.lookUpReactElement(jsxElem);
		if (elementClassTypeRef === null)
			return;

		val expr = jsxElem.jsxElementName.expression;
		val G = expr.newRuleEnvironment;
		val result = ts.subtype(G, exprTypeRef.returnTypeRef, TypeUtils.createTypeRef(elementClassTypeRef));
		if (result.failure) {
			val message = IssueCodes.
				getMessageForJSX_REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR(exprTypeRef.returnTypeRef.typeRefAsString);
			addIssue(
				message,
				expr,
				JSX_REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR
			);
		}
	}

	/**
	 * The JSX element binds to a class, check that the class type is a subtype of React.Component
	 * See Req. IDE-241116
	 */
	def private void checkTypeTypeRefConstructor(JSXElement jsxElem, TypeTypeRef exprTypeRef) {
		val componentClassTypeRef = reactHelper.lookUpReactComponent(jsxElem);
		if (componentClassTypeRef === null)
			return;

		val expr = jsxElem.jsxElementName.expression;
		val G = expr.newRuleEnvironment;
		val tclass = tsh.getStaticType(G, exprTypeRef);
		val tclassTypeRef = TypeUtils.createTypeRef(tclass);
		val resultSubType = ts.subtype(G, tclassTypeRef, TypeUtils.createTypeRef(componentClassTypeRef))
		if (resultSubType.failure) {
			val message = getMessageForJSX_REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR();
			addIssue(message, expr, JSX_REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR);
		}
	}

	@Check
	def public void checkUnknownJSXPropertyAttribute(JSXPropertyAttribute propertyAttribute) {
		val jsxElem = propertyAttribute.eContainer as JSXElement;
		val TypeRef exprTypeRef = reactHelper.getJsxElementBindingType(jsxElem);
		var isFunction = exprTypeRef instanceof FunctionTypeExprOrRef;
		var isClass = exprTypeRef instanceof TypeTypeRef && (exprTypeRef as TypeTypeRef).constructorRef;
		if (!isFunction && !isClass) {
			return;
		}

		val G = propertyAttribute.newRuleEnvironment;
		val TypeRef result = ts.type(G, propertyAttribute.property);
		//TODO: it's not nice that we get an UnknownTypeRef, here;
		//they are mainly intended for error cases, not valid code. Probably it should be any+ instead.
		//This requires refactoring else where
		if (result instanceof UnknownTypeRef) {
			val message = IssueCodes.getMessageForJSX_JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS(propertyAttribute.propertyAsText,
				jsxElem?.jsxElementName?.expression?.refName);
					addIssue(
						message,
						propertyAttribute,
						JSX_PROPERTY_ATTRIBUTE__PROPERTY,
						JSX_JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS
					);
		}
	}

	/**
	 * Check the type conformity of types of spread operator's attributes against "props" types
	 * See Req. IDE-241119
	 */
	@Check
	def public void checkAttributeAndTypeConformityInJSXSpreadAttribute(JSXSpreadAttribute spreadAttribute) {
		val expr = spreadAttribute?.expression;
		val jsxElem =  spreadAttribute?.eContainer as JSXElement;
		val propsType = jsxElem?.propsType
		if (propsType === null)
			return;

		val G = spreadAttribute.newRuleEnvironment
		// Retrieve fields or getters in props type
		val fieldsOrGettersInProps = tsh.structuralTypesHelper.collectStructuralMembers(G, propsType,
			TypingStrategy.STRUCTURAL).filter[m | (m instanceof TField) || (m instanceof TGetter)];

		val exprTypeResult = ts.type(G, expr);
		// Retrieve attributes (either field or getter) in spread operator type
		val attributesInSpreadOperatorType = tsh.structuralTypesHelper.collectStructuralMembers(G, exprTypeResult,
				TypingStrategy.STRUCTURAL).filter[m | (m instanceof TField) || (m instanceof TGetter)];

		//commented out but not deleted for now since still it is not clear if this check makes sense
		//spreadAttribute.checkUnknownAttributeInSpreadOperator(jsxElem, attributesInSpreadOperatorType, fieldsOrGettersInProps);

		// Type check each attribute in spreader operator against the corresponding props type's field/getter
		attributesInSpreadOperatorType.forEach [ attributeInSpreadOperator |
			val attributeInSpreadOperatorTypeRef = reactHelper.typeRefOfFieldOrGetter(attributeInSpreadOperator, exprTypeResult);
			val fieldOrGetterInProps = fieldsOrGettersInProps.findFirst[fieldOrGetter | attributeInSpreadOperator.name == fieldOrGetter.name];

			if (fieldOrGetterInProps !== null) {
				//Reason for using tau: Consider type arguments by calculating the property of within the context of "props" type
				val fieldOrGetterInPropsTypeRef = ts.tau(fieldOrGetterInProps, propsType);
				val result = ts.subtype(G, attributeInSpreadOperatorTypeRef, fieldOrGetterInPropsTypeRef);
				if (result.failure) {
					val message = IssueCodes.getMessageForJSX_JSXSPREADATTRIBUTE_WRONG_SUBTYPE(attributeInSpreadOperator.name,
						attributeInSpreadOperatorTypeRef.typeRefAsString, fieldOrGetterInPropsTypeRef.typeRefAsString);
					addIssue(
						message,
						spreadAttribute,
						JSX_SPREAD_ATTRIBUTE__EXPRESSION,
						IssueCodes.JSX_JSXSPREADATTRIBUTE_WRONG_SUBTYPE
					);
				}
			}
		];
	}

	/**
	 * Check that a named element is not named React in N4JSX file to avoid naming clash.
	 */
	@Check
	def void checkNamedElementNotNamedReact(NamedElement elem) {
		val resourceType = ResourceType.getResourceType(elem)
		// This check is only applicable to N4JSX/JSX file
		if (!(ResourceType.N4JSX === resourceType || ResourceType.JSX === resourceType))
			return;

 		if (elem.name == ReactHelper.REACT_NAMESPACE_NAME) {
			val message = IssueCodes.getMessageForJSX_NAME_CANNOT_BE_REACT();
					addIssue(
						message,
						elem,
						findNameFeature(elem).value,
						IssueCodes.JSX_NAME_CANNOT_BE_REACT
					);
		}
	}

	/**
	 * Check that non-optional fields of "props" should be specified in JSX element
	 * See Req. IDE-241117
	 */
	def private void checkAllNonOptionalFieldsAreSpecified(JSXElement jsxElem, TypeRef exprTypeRef) {
		val jsxPropertyAttributes = jsxElem.jsxAttributes;
		// First, collect all normal properties in JSX element
		val allAttributesInJSXElement = Lists.newArrayList(jsxPropertyAttributes.filter(typeof(JSXPropertyAttribute)).map[a | a.property]);
		val propsType = jsxElem.propsType;
		if (propsType === null)
			return;

		val G = jsxElem.newRuleEnvironment;
		// Then collect attributes in spread operators
		val attributesInSpreadOperator = Lists.newArrayList(jsxPropertyAttributes.filter(typeof(JSXSpreadAttribute)).map [ spreadAttribute |
			val exprTypeRefResult = ts.type(G, spreadAttribute.expression);
			return tsh.structuralTypesHelper.collectStructuralMembers(G, exprTypeRefResult, TypingStrategy.STRUCTURAL).filter [ m |
				(m instanceof TField) || (m instanceof TGetter)
			]
		]).flatten;
		allAttributesInJSXElement.addAll(attributesInSpreadOperator)

		// Retrieve all non-optional fields or getters in "props" type
		val nonOptionalFieldsOrGettersInProps =
				tsh.structuralTypesHelper.collectStructuralMembers(G, propsType, TypingStrategy.STRUCTURAL).filter[m |
					(m instanceof TField || m instanceof TGetter) && !m.isOptional
				];
		//Calculate the set of unspecified non-optional properties
		val String missingFieldsStringRep = nonOptionalFieldsOrGettersInProps.filter [ fieldOrGetter |
			!(allAttributesInJSXElement.exists[attribute | attribute.name == fieldOrGetter.name])
		].map [ fieldOrGetter |	fieldOrGetter.name ].join(",");

		if (!missingFieldsStringRep.isEmpty) {
			val message = IssueCodes.
				getMessageForJSX_JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED(missingFieldsStringRep);
			addIssue(
				message,
				jsxElem,
				JSX_ELEMENT__JSX_ELEMENT_NAME,
				JSX_JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED
			);
		}
	}

	/**
	 * Calculate the reference name of an expression, should be used only for expressions within JSX element!
	 */
	def private String getRefName(Expression expr) {
		var String refName = null;
		if (expr instanceof IdentifierRef) {
			refName = expr.idAsText;
		} else if (expr instanceof ParameterizedPropertyAccessExpression) {
			refName = expr.propertyAsText
		}
		return refName;
	}
}
