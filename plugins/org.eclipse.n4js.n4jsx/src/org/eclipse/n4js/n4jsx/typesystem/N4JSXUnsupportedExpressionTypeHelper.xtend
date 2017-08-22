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
package org.eclipse.n4js.n4jsx.typesystem

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.DefaultUnsupportedExpressionTypeHelper
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.n4jsx.helpers.ReactHelper
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute
import it.xsemantics.runtime.RuleEnvironment
import org.eclipse.emf.ecore.EObject

/**
 * This class adds typing rules for JSX elements. Consider to define these typing rules in an Xsemantics for N4JSX
 */
class N4JSXUnsupportedExpressionTypeHelper extends DefaultUnsupportedExpressionTypeHelper {
	@Inject extension ReactHelper reactLookupHelper;
	@Inject N4JSTypeSystem ts;

	/**
	 * Return the type of JSX element as React.Element
	 *
	 * @param expression the JSX element
	 * @param G the rule environment
	 *
	 * @return the type ref to React.Element
	 */
	override public typeExpression(Expression expression, RuleEnvironment G) {
		if (expression instanceof JSXElement) {
			val classifierReactElement = reactLookupHelper.lookUpReactElement(expression);

			if (classifierReactElement === null) {
				throw new IllegalStateException(ReactHelper.REACT_MODULE + "." + ReactHelper.REACT_ELEMENT +  " not found");
			}
			val typeRef = TypeUtils.createTypeRef(classifierReactElement)
			return typeRef
		} else {
			return super.typeExpression(expression, G)
		}
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
	override public TypeRef expectedExpressionTypeInEObject(EObject container, Expression expression,
		RuleEnvironment G) {
		if (container instanceof JSXPropertyAttribute) {
			val jsxElem = (container.eContainer) as JSXElement;
			val propsType = jsxElem.propsType;
			if (propsType !== null) {
				//Reason for using tau: Consider type arguments by calculating the property of within the context of "props" type
				return ts.tau(container.property, propsType);	
			}
		}
		return super.expectedExpressionTypeInEObject(container, expression, G);

	}
}
