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
package org.eclipse.n4js.typesystem;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4jsx.helpers.ReactHelper;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.xsemantics.runtime.RuleEnvironment;

import com.google.inject.Inject;

/**
 * Default implementation of {@link UnsupportedExpressionTypeHelper} that always throws an
 * {@link UnsupportedOperationException}. In N4JS, the method {@link #typeExpression(Expression, RuleEnvironment)} must
 * never be called as all types of expressions should be handled in Xsemantics.
 */
public class DefaultUnsupportedExpressionTypeHelper implements UnsupportedExpressionTypeHelper {
	@Inject
	ReactHelper reactLookupHelper;
	@Inject
	N4JSTypeSystem ts;

	/**
	 * In default case throws {@link UnsupportedOperationException}.
	 * <p>
	 * When provided @ {@code expression} is link JSXElement} tries to find proper {@link TypeRef}, that is to return
	 * the type of JSX element as React.Element
	 *
	 * @param expression
	 *            the JSX element
	 * @param G
	 *            the rule environment
	 *
	 * @return the type ref to React.Element
	 * @throws UnsupportedOperationException
	 *             when expression can not be typed
	 */
	@Override
	public TypeRef typeExpression(Expression expression, RuleEnvironment G) {
		if (expression instanceof JSXElement) {
			TClassifier classifierReactElement = reactLookupHelper.lookUpReactElement(expression);
			if (classifierReactElement != null) {
				ParameterizedTypeRef typeRef = TypeUtils.createTypeRef(classifierReactElement);
				return typeRef;
			}
		}
		// Otherwise, standard behavior
		throw new UnsupportedOperationException("Cannot determine the type of expression: " + expression);
	}

	/**
	 * This is the equivalence of the axiom expectedTypeNone that has been commented out in n4js.xsemantics
	 * <p>
	 * Return the expected type of an expression declared within a JSX property attribute. This is needed for checking
	 * expression type
	 *
	 * @param container
	 *            JSX property attribute AST node
	 * @param expression
	 *            expression declared within the JSX property attribute
	 * @param G
	 *            rule environment
	 *
	 * @return the expected of the expression if the container is a JSX property attribute node and null otherwise
	 */
	@Override
	public TypeRef expectedExpressionTypeInEObject(EObject container, Expression expression, RuleEnvironment G) {
		if (container instanceof JSXPropertyAttribute) {
			JSXElement jsxElem = (JSXElement) container.eContainer();
			TypeRef propsType = reactLookupHelper.getPropsType(jsxElem);
			if (propsType != null) {
				// Reason for using tau:
				// Consider type arguments by calculating the property
				// of within the context of "props" type
				return ts.tau(((JSXPropertyAttribute) container).getProperty(), propsType);
			}
		}
		return null;
	}
}
