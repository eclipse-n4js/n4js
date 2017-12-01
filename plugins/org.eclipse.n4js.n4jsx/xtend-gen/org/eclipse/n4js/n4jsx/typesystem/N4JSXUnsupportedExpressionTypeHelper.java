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
package org.eclipse.n4js.n4jsx.typesystem;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4jsx.helpers.ReactHelper;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement;
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.typesystem.DefaultUnsupportedExpressionTypeHelper;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.xsemantics.runtime.RuleEnvironment;
import org.eclipse.xtext.xbase.lib.Extension;

/**
 * This class adds typing rules for JSX elements. Consider to define these typing rules in an Xsemantics for N4JSX
 */
@SuppressWarnings("all")
public class N4JSXUnsupportedExpressionTypeHelper extends DefaultUnsupportedExpressionTypeHelper {
  @Inject
  @Extension
  private ReactHelper reactLookupHelper;
  
  @Inject
  private N4JSTypeSystem ts;
  
  /**
   * Return the type of JSX element as React.Element
   * 
   * @param expression the JSX element
   * @param G the rule environment
   * 
   * @return the type ref to React.Element
   */
  @Override
  public TypeRef typeExpression(final Expression expression, final RuleEnvironment G) {
    if ((expression instanceof JSXElement)) {
      final TClassifier classifierReactElement = this.reactLookupHelper.lookUpReactElement(expression);
      if ((classifierReactElement != null)) {
        final ParameterizedTypeRef typeRef = TypeUtils.createTypeRef(classifierReactElement);
        return typeRef;
      }
    }
    return super.typeExpression(expression, G);
  }
  
  /**
   * Return the expected type of an expression declared within a JSX property attribute. This is needed for checking
   * expression type
   * 
   * @param container JSX property attribute AST node
   * @param expression expression declared within the JSX property attribute
   * @param G rule environment
   * 
   * @return the expected of the expression if the container is a JSX property attribute node and null otherwise
   */
  @Override
  public TypeRef expectedExpressionTypeInEObject(final EObject container, final Expression expression, final RuleEnvironment G) {
    if ((container instanceof JSXPropertyAttribute)) {
      EObject _eContainer = ((JSXPropertyAttribute)container).eContainer();
      final JSXElement jsxElem = ((JSXElement) _eContainer);
      final TypeRef propsType = this.reactLookupHelper.getPropsType(jsxElem);
      if ((propsType != null)) {
        return this.ts.tau(((JSXPropertyAttribute)container).getProperty(), propsType);
      }
    }
    return super.expectedExpressionTypeInEObject(container, expression, G);
  }
}
