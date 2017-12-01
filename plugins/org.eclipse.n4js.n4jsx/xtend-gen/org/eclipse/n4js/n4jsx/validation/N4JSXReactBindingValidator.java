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
package org.eclipse.n4js.n4jsx.validation;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4jsx.helpers.ReactHelper;
import org.eclipse.n4js.n4jsx.n4JSX.JSXAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElementName;
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage;
import org.eclipse.n4js.n4jsx.validation.IssueCodes;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.TypeSystemHelper;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.xsemantics.runtime.Result;
import org.eclipse.xsemantics.runtime.RuleEnvironment;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Validation of React bindings including naming convention (components in upper case and HTML tags in lower case)
 */
@SuppressWarnings("all")
public class N4JSXReactBindingValidator extends AbstractN4JSDeclarativeValidator {
  @Inject
  private N4JSTypeSystem ts;
  
  @Inject
  private TypeSystemHelper tsh;
  
  @Inject
  @Extension
  private ReactHelper reactHelper;
  
  private final static List<String> htmlTags = Arrays.<String>asList(
    "a", "abbr", "address", "area", "article", "aside", "audio", 
    "b", "base", "bdi", "bdo", "blockquote", "body", "br", "button", 
    "canvas", "caption", "cite", "code", "col", "colgroup", 
    "datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt", 
    "em", "embed", 
    "fieldset", "figcaption", "figure", "footer", "form", 
    "h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hr", "html", 
    "i", "iframe", "img", "input", "ins", 
    "kbd", "keygen", 
    "label", "legend", "li", "link", 
    "main", "map", "mark", "menu", "menuitem", "meta", "meter", 
    "nav", "noscript", 
    "object", "ol", "optgroup", "option", 
    "p", "param", "pre", "progress", "q", "rp", "rt", "ruby", 
    "s", "samp", "script", "section", "select", "small", "source", "span", 
    "strong", "style", "sub", "summary", "sup", "svg", 
    "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track", 
    "u", "ul", "use", "var", "video", "wbr");
  
  /**
   * NEEEDED
   * 
   * when removed check methods will be called twice once by N4JSValidator, and once by
   * AbstractDeclarativeN4JSValidator
   */
  @Override
  public void register(final EValidatorRegistrar registrar) {
  }
  
  /**
   * This method checks that JSXElement bind to a valid React component function or class React component declaration
   * See Req. 241103
   */
  @Check
  public void checkOpeningClosingElementMismatch(final JSXElement jsxElem) {
    JSXElementName _jsxElementName = null;
    if (jsxElem!=null) {
      _jsxElementName=jsxElem.getJsxElementName();
    }
    Expression _expression = null;
    if (_jsxElementName!=null) {
      _expression=_jsxElementName.getExpression();
    }
    String _refName = null;
    if (_expression!=null) {
      _refName=this.getRefName(_expression);
    }
    final String openingName = _refName;
    JSXElementName _jsxClosingName = null;
    if (jsxElem!=null) {
      _jsxClosingName=jsxElem.getJsxClosingName();
    }
    Expression _expression_1 = null;
    if (_jsxClosingName!=null) {
      _expression_1=_jsxClosingName.getExpression();
    }
    String _refName_1 = null;
    if (_expression_1!=null) {
      _refName_1=this.getRefName(_expression_1);
    }
    final String closingName = _refName_1;
    if (((jsxElem.getJsxClosingName() != null) && (!Objects.equal(openingName, closingName)))) {
      final String message = IssueCodes.getMessageForJSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH(openingName, closingName);
      this.addIssue(message, jsxElem, 
        N4JSXPackage.Literals.JSX_ELEMENT__JSX_CLOSING_NAME, 
        IssueCodes.JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH);
    }
  }
  
  /**
   * This method checks that JSXElement bind to a valid React component function or class React component declaration
   */
  @Check
  public void checkReactElementBinding(final JSXElement jsxElem) {
    final Expression expr = jsxElem.getJsxElementName().getExpression();
    final TypeRef exprTypeRef = this.reactHelper.getJSXElementBindingType(jsxElem);
    boolean isFunction = (exprTypeRef instanceof FunctionTypeExprOrRef);
    boolean isClass = ((exprTypeRef instanceof TypeTypeRef) && ((TypeTypeRef) exprTypeRef).isConstructorRef());
    if (((!isFunction) && (!isClass))) {
      final String refName = this.getRefName(expr);
      if (((refName != null) && Character.isLowerCase(refName.charAt(0)))) {
        boolean _contains = N4JSXReactBindingValidator.htmlTags.contains(refName);
        boolean _not = (!_contains);
        if (_not) {
          final String message = IssueCodes.getMessageForHTMLTAG_UNKNOWN(refName);
          this.addIssue(message, jsxElem, 
            N4JSXPackage.Literals.JSX_ELEMENT__JSX_ELEMENT_NAME, 
            IssueCodes.HTMLTAG_UNKNOWN);
        }
      } else {
        final String message_1 = IssueCodes.getMessageForREACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR(exprTypeRef.getTypeRefAsString());
        this.addIssue(message_1, expr, IssueCodes.REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR);
      }
      return;
    }
    if (isFunction) {
      this.checkFunctionTypeExprOrRef(jsxElem, ((FunctionTypeExprOrRef) exprTypeRef));
      this.checkReactComponentShouldStartWithUppercase(jsxElem, true);
    }
    if (isClass) {
      this.checkTypeTypeRefConstructor(jsxElem, ((TypeTypeRef) exprTypeRef));
      this.checkReactComponentShouldStartWithUppercase(jsxElem, false);
    }
    this.checkAllNonOptionalFieldsAreSpecified(jsxElem, exprTypeRef);
  }
  
  /**
   * Check that a React function/class component should start with an upper case
   * See Req. 241101
   */
  private void checkReactComponentShouldStartWithUppercase(final JSXElement jsxElem, final boolean isFunctionalComponent) {
    final Expression expr = jsxElem.getJsxElementName().getExpression();
    final String refName = this.getRefName(expr);
    if ((((refName != null) && (!refName.isEmpty())) && Character.isLowerCase(refName.charAt(0)))) {
      if (isFunctionalComponent) {
        final String message = IssueCodes.getMessageForREACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE(refName);
        this.addIssue(message, jsxElem, 
          N4JSXPackage.Literals.JSX_ELEMENT__JSX_ELEMENT_NAME, 
          IssueCodes.REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE);
      } else {
        final String message_1 = IssueCodes.getMessageForREACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE(refName);
        this.addIssue(message_1, jsxElem, 
          N4JSXPackage.Literals.JSX_ELEMENT__JSX_ELEMENT_NAME, 
          IssueCodes.REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE);
      }
    }
  }
  
  /**
   * The JSX element binds to a function or function expression, check that the return type is a subtype of React.Element
   * See Req. IDE-241116
   */
  private void checkFunctionTypeExprOrRef(final JSXElement jsxElem, final FunctionTypeExprOrRef exprTypeRef) {
    final TClassifier elementClassTypeRef = this.reactHelper.lookUpReactElement(jsxElem);
    if ((elementClassTypeRef == null)) {
      return;
    }
    final Expression expr = jsxElem.getJsxElementName().getExpression();
    final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(expr);
    final Result<Boolean> result = this.ts.subtype(G, exprTypeRef.getReturnTypeRef(), TypeUtils.createTypeRef(elementClassTypeRef));
    boolean _failed = result.failed();
    if (_failed) {
      final String message = IssueCodes.getMessageForREACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR(exprTypeRef.getReturnTypeRef().getTypeRefAsString());
      this.addIssue(message, expr, 
        IssueCodes.REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR);
    }
  }
  
  /**
   * The JSX element binds to a class, check that the class type is a subtype of React.Component
   * See Req. IDE-241116
   */
  private void checkTypeTypeRefConstructor(final JSXElement jsxElem, final TypeTypeRef exprTypeRef) {
    final TClassifier componentClassTypeRef = this.reactHelper.lookUpReactComponent(jsxElem);
    if ((componentClassTypeRef == null)) {
      return;
    }
    final Expression expr = jsxElem.getJsxElementName().getExpression();
    final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(expr);
    final Type tclass = this.tsh.getStaticType(G, exprTypeRef);
    final ParameterizedTypeRef tclassTypeRef = TypeUtils.createTypeRef(tclass);
    final Result<Boolean> resultSubType = this.ts.subtype(G, tclassTypeRef, TypeUtils.createTypeRef(componentClassTypeRef));
    boolean _failed = resultSubType.failed();
    if (_failed) {
      final String message = IssueCodes.getMessageForREACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR();
      this.addIssue(message, expr, IssueCodes.REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR);
    }
  }
  
  @Check
  public void checkUnknownJSXPropertyAttribute(final JSXPropertyAttribute propertyAttribute) {
    EObject _eContainer = propertyAttribute.eContainer();
    final JSXElement jsxElem = ((JSXElement) _eContainer);
    final TypeRef exprTypeRef = this.reactHelper.getJSXElementBindingType(jsxElem);
    boolean isFunction = (exprTypeRef instanceof FunctionTypeExprOrRef);
    boolean isClass = ((exprTypeRef instanceof TypeTypeRef) && ((TypeTypeRef) exprTypeRef).isConstructorRef());
    if (((!isFunction) && (!isClass))) {
      return;
    }
    final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(propertyAttribute);
    final Result<TypeRef> result = this.ts.type(G, propertyAttribute.getProperty());
    TypeRef _value = result.getValue();
    if ((_value instanceof UnknownTypeRef)) {
      String _propertyAsText = propertyAttribute.getPropertyAsText();
      JSXElementName _jsxElementName = null;
      if (jsxElem!=null) {
        _jsxElementName=jsxElem.getJsxElementName();
      }
      Expression _expression = null;
      if (_jsxElementName!=null) {
        _expression=_jsxElementName.getExpression();
      }
      String _refName = null;
      if (_expression!=null) {
        _refName=this.getRefName(_expression);
      }
      final String message = IssueCodes.getMessageForJSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS(_propertyAsText, _refName);
      this.addIssue(message, propertyAttribute, 
        N4JSXPackage.Literals.JSX_PROPERTY_ATTRIBUTE__PROPERTY, 
        IssueCodes.JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS);
    }
  }
  
  /**
   * Check the type conformity of types of spread operator's attributes against "props" types
   * See Req. IDE-241119
   */
  @Check
  public void checkAttributeAndTypeConformityInJSXSpreadAttribute(final JSXSpreadAttribute spreadAttribute) {
    Expression _expression = null;
    if (spreadAttribute!=null) {
      _expression=spreadAttribute.getExpression();
    }
    final Expression expr = _expression;
    EObject _eContainer = null;
    if (spreadAttribute!=null) {
      _eContainer=spreadAttribute.eContainer();
    }
    final JSXElement jsxElem = ((JSXElement) _eContainer);
    TypeRef _propsType = null;
    if (jsxElem!=null) {
      _propsType=this.reactHelper.getPropsType(jsxElem);
    }
    final TypeRef propsType = _propsType;
    if ((propsType == null)) {
      return;
    }
    final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(spreadAttribute);
    final Function1<TMember, Boolean> _function = (TMember m) -> {
      return Boolean.valueOf(((m instanceof TField) || (m instanceof TGetter)));
    };
    final Iterable<TMember> fieldsOrGettersInProps = IterableExtensions.<TMember>filter(this.tsh.getStructuralTypesHelper().collectStructuralMembers(G, propsType, 
      TypingStrategy.STRUCTURAL), _function);
    final Result<TypeRef> exprTypeResult = this.ts.type(G, expr);
    boolean _failed = exprTypeResult.failed();
    if (_failed) {
      return;
    }
    final Function1<TMember, Boolean> _function_1 = (TMember m) -> {
      return Boolean.valueOf(((m instanceof TField) || (m instanceof TGetter)));
    };
    final Iterable<TMember> attributesInSpreadOperatorType = IterableExtensions.<TMember>filter(this.tsh.getStructuralTypesHelper().collectStructuralMembers(G, exprTypeResult.getValue(), 
      TypingStrategy.STRUCTURAL), _function_1);
    final Consumer<TMember> _function_2 = (TMember attributeInSpreadOperator) -> {
      final TypeRef attributeInSpreadOperatorTypeRef = this.reactHelper.typeRefOfFieldOrGetter(attributeInSpreadOperator, exprTypeResult.getValue());
      final Function1<TMember, Boolean> _function_3 = (TMember fieldOrGetter) -> {
        String _name = attributeInSpreadOperator.getName();
        String _name_1 = fieldOrGetter.getName();
        return Boolean.valueOf(Objects.equal(_name, _name_1));
      };
      final TMember fieldOrGetterInProps = IterableExtensions.<TMember>findFirst(fieldsOrGettersInProps, _function_3);
      if ((fieldOrGetterInProps != null)) {
        final TypeRef fieldOrGetterInPropsTypeRef = this.ts.tau(fieldOrGetterInProps, propsType);
        final Result<Boolean> result = this.ts.subtype(G, attributeInSpreadOperatorTypeRef, fieldOrGetterInPropsTypeRef);
        boolean _failed_1 = result.failed();
        if (_failed_1) {
          final String message = IssueCodes.getMessageForJSXSPREADATTRIBUTE_WRONG_SUBTYPE(attributeInSpreadOperator.getName(), 
            attributeInSpreadOperatorTypeRef.getTypeRefAsString(), fieldOrGetterInPropsTypeRef.getTypeRefAsString());
          this.addIssue(message, spreadAttribute, 
            N4JSXPackage.Literals.JSX_SPREAD_ATTRIBUTE__EXPRESSION, 
            IssueCodes.JSXSPREADATTRIBUTE_WRONG_SUBTYPE);
        }
      }
    };
    attributesInSpreadOperatorType.forEach(_function_2);
  }
  
  /**
   * Check that non-optional fields of "props" should be specified in JSX element
   * See Req. IDE-241117
   */
  private void checkAllNonOptionalFieldsAreSpecified(final JSXElement jsxElem, final TypeRef exprTypeRef) {
    final EList<JSXAttribute> jsxPropertyAttributes = jsxElem.getJsxAttributes();
    final Function1<JSXPropertyAttribute, IdentifiableElement> _function = (JSXPropertyAttribute a) -> {
      return a.getProperty();
    };
    final ArrayList<IdentifiableElement> allAttributesInJSXElement = Lists.<IdentifiableElement>newArrayList(IterableExtensions.<JSXPropertyAttribute, IdentifiableElement>map(Iterables.<JSXPropertyAttribute>filter(jsxPropertyAttributes, JSXPropertyAttribute.class), _function));
    final TypeRef propsType = this.reactHelper.getPropsType(jsxElem);
    if ((propsType == null)) {
      return;
    }
    final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(jsxElem);
    final Function1<JSXSpreadAttribute, Iterable<TMember>> _function_1 = (JSXSpreadAttribute spreadAttribute) -> {
      ArrayList<TMember> _xblockexpression = null;
      {
        final Result<TypeRef> exprTypeRefResult = this.ts.type(G, spreadAttribute.getExpression());
        ArrayList<TMember> _xifexpression = null;
        boolean _failed = exprTypeRefResult.failed();
        boolean _not = (!_failed);
        if (_not) {
          final Function1<TMember, Boolean> _function_2 = (TMember m) -> {
            return Boolean.valueOf(((m instanceof TField) || (m instanceof TGetter)));
          };
          return IterableExtensions.<TMember>filter(this.tsh.getStructuralTypesHelper().collectStructuralMembers(G, exprTypeRefResult.getValue(), TypingStrategy.STRUCTURAL), _function_2);
        } else {
          _xifexpression = Lists.<TMember>newArrayList();
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    };
    final Iterable<TMember> attributesInSpreadOperator = Iterables.<TMember>concat(Lists.<Iterable<TMember>>newArrayList(IterableExtensions.<JSXSpreadAttribute, Iterable<TMember>>map(Iterables.<JSXSpreadAttribute>filter(jsxPropertyAttributes, JSXSpreadAttribute.class), _function_1)));
    Iterables.<IdentifiableElement>addAll(allAttributesInJSXElement, attributesInSpreadOperator);
    final Function1<TMember, Boolean> _function_2 = (TMember m) -> {
      return Boolean.valueOf((((m instanceof TField) || (m instanceof TGetter)) && (!m.isOptional())));
    };
    final Iterable<TMember> nonOptionalFieldsOrGettersInProps = IterableExtensions.<TMember>filter(this.tsh.getStructuralTypesHelper().collectStructuralMembers(G, propsType, TypingStrategy.STRUCTURAL), _function_2);
    final Function1<TMember, Boolean> _function_3 = (TMember fieldOrGetter) -> {
      final Function1<IdentifiableElement, Boolean> _function_4 = (IdentifiableElement attribute) -> {
        String _name = attribute.getName();
        String _name_1 = fieldOrGetter.getName();
        return Boolean.valueOf(Objects.equal(_name, _name_1));
      };
      boolean _exists = IterableExtensions.<IdentifiableElement>exists(allAttributesInJSXElement, _function_4);
      return Boolean.valueOf((!_exists));
    };
    final Function1<TMember, String> _function_4 = (TMember fieldOrGetter) -> {
      return fieldOrGetter.getName();
    };
    final String missingFieldsStringRep = IterableExtensions.join(IterableExtensions.<TMember, String>map(IterableExtensions.<TMember>filter(nonOptionalFieldsOrGettersInProps, _function_3), _function_4), ",");
    boolean _isEmpty = missingFieldsStringRep.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      final String message = IssueCodes.getMessageForJSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED(missingFieldsStringRep);
      this.addIssue(message, jsxElem, 
        N4JSXPackage.Literals.JSX_ELEMENT__JSX_ELEMENT_NAME, 
        IssueCodes.JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED);
    }
  }
  
  /**
   * Calculate the reference name of an expression, should be used only for expressions within JSX element!
   */
  private String getRefName(final Expression expr) {
    String refName = null;
    if ((expr instanceof IdentifierRef)) {
      refName = ((IdentifierRef)expr).getIdAsText();
    } else {
      if ((expr instanceof ParameterizedPropertyAccessExpression)) {
        refName = ((ParameterizedPropertyAccessExpression)expr).getPropertyAsText();
      }
    }
    return refName;
  }
}
