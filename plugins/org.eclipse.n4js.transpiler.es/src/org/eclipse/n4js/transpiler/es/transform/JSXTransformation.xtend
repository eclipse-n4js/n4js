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
package org.eclipse.n4js.transpiler.es.transform

import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.n4jsx.n4JSX.JSXAttribute
import org.eclipse.n4js.n4jsx.n4JSX.JSXChild
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement
import org.eclipse.n4js.n4jsx.n4JSX.JSXExpression
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute
import org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute
import java.util.List

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.n4js.transpiler.im.Script_IM

/**
 *
 */
class JSXTransformation extends Transformation {


	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	/**
	 * IMPORTANT: our strategy for handling nested JSXElements is as follows:
	 * 1) direct child JSXElements will be handled together with their parent JSXElement
	 * 2) indirect children that are contained in nested expressions will be handled via a separate invocation to
	 * method #transformJSXElement(JSXElement)
	 *
	 * Example for case 1:
	 * <pre>
	 * let elem1 = &lt;div>&lt;a>&lt;/a>&lt;/div>; // &lt;a> is a direct child
	 * </pre>
	 * Example for case 2:
	 * <pre>
	 * let elem2 = &lt;div>{function() {return &lt;a>&lt;/a>;}}&lt;/div>; // &lt;a> is the child of a nested expression!
	 * </pre>
	 * More examples for case 2:
	 * <pre>
	 * let elem3 = &lt;div>{&lt;a>&lt;/a>}&lt;/div>;
	 * let elem4 = &lt;div prop={&lt;a>&lt;/a>}>&lt;/div>;
	 * </pre>
	 *
	 */
	override transform() {
		// note: we are passing 'true' to #collectNodes(), i.e. we are searching for nested elements
		collectNodes(state.im, JSXElement, true).forEach[transformJSXElement];
	}

	def private void transformJSXElement(JSXElement elem) {
		// IMPORTANT: 'elem' might be a direct or indirect child, but if it is a direct child, it was already
		// transformed when this method was invoked with its ancestor JSXElement as argument
		if(EcoreUtil2.getContainerOfType(elem, Script_IM)===null) {
			// 'elem' was already processed -> simply ignore it
			return;
		}
		replace(elem, convertJSXElement(elem));
	}
	def private ParameterizedCallExpression convertJSXElement(JSXElement elem) {
		return _CallExpr(
			_PropertyAccessExpr(steFor_React, steFor_createElement),
			(
				#[
					elem.tagNameFromElement,
					convertJSXAttributes(elem.jsxAttributes)
				]
				+ elem.jsxChildren.map[convertJSXChild]
			)
		);
	}

	def private Expression convertJSXChild(JSXChild child) {
		switch(child) {
			JSXElement:
				convertJSXElement(child)
			JSXExpression:
				child.expression
		}
	}

	def private Expression convertJSXAttributes(List<JSXAttribute> attrs) {
		if(attrs.isEmpty) {
			_NULL
		} else {
			val propsSimple = _ObjLit(attrs.filter(JSXPropertyAttribute).map[convertJSXAttribute]);
			val propsSpread = attrs.filter(JSXSpreadAttribute).map[expression].toList;
			if(propsSpread.isEmpty) {
				propsSimple
			} else {
				_CallExpr(_PropertyAccessExpr(steFor_Object,steFor_assign), #[_ObjLit] + propsSpread + #[ propsSimple ])
			}
		}
	}

	def private PropertyNameValuePair convertJSXAttribute(JSXPropertyAttribute attr) {
		_PropertyNameValuePair(
			attr.nameFromPropertyAttribute,
			attr.valueExpressionFromPropertyAttribute)
	}

	def private Expression getTagNameFromElement(JSXElement elem) {
		val nameExpr = elem.jsxElementName.expression;
		if(nameExpr instanceof IdentifierRef_IM) {
			val id = nameExpr.id_IM;
			if(id===null) {
				return _StringLiteral(nameExpr.idAsText);
			}
		}
		return nameExpr;
	}

	def private String getNameFromPropertyAttribute(JSXPropertyAttribute attr) {
		val prop = attr.property;
		if(prop!==null && !prop.eIsProxy) {
			return prop.name;
		}
		return attr.propertyAsText;
	}
	def private Expression getValueExpressionFromPropertyAttribute(JSXPropertyAttribute attr) {
		return attr.jsxAttributeValue ?: _TRUE;
	}
}
