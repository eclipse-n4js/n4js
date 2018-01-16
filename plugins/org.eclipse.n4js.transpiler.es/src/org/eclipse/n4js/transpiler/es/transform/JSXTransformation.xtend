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

import com.google.inject.Inject
import java.util.ArrayList
import java.util.List
import java.util.stream.IntStream
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.JSXAttribute
import org.eclipse.n4js.n4JS.JSXChild
import org.eclipse.n4js.n4JS.JSXElement
import org.eclipse.n4js.n4JS.JSXExpression
import org.eclipse.n4js.n4JS.JSXPropertyAttribute
import org.eclipse.n4js.n4JS.JSXSpreadAttribute
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4jsx.transpiler.utils.JSXBackendHelper
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.Script_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.xtext.EcoreUtil2

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 *
 */
class JSXTransformation extends Transformation {

	private SymbolTableEntry ste_jsxBackendNamespace;
	private SymbolTableEntry ste_jsxCreateElementMethod;

	@Inject
	private JSXBackendHelper jsxBackendHelper;


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
	 */
	override transform() {
		ste_jsxBackendNamespace = null;
		ste_jsxCreateElementMethod = null;

		// note: we are passing 'true' to #collectNodes(), i.e. we are searching for nested elements
		val jsxElements = collectNodes(state.im, JSXElement, true);
		if(jsxElements.empty) {
			return;
		}
		// we have at least one JSXElement

		prepareImportOfJSXBackend();
		jsxElements.forEach[transformJSXElement];
	}


	/**
	 * Adds namespace import for JSX backend (e.g. React), if it does not exist already.
	 */
	def private void prepareImportOfJSXBackend() {
		val existingJSXBackendNamespaceImportSpecifier = state.info.browseOriginalImports_internal // FIXME why go via original AST here?????
			.filter[jsxBackendHelper.isJsxBackendModule(it.value)]
			.map[it.key.importSpecifiers]
			.flatten
			.filter(NamespaceImportSpecifier)
			.head;

		if(existingJSXBackendNamespaceImportSpecifier!==null) {
			if(!existingJSXBackendNamespaceImportSpecifier.flaggedUsedInCode) {
				existingJSXBackendNamespaceImportSpecifier.flaggedUsedInCode = true;
			}
			ste_jsxBackendNamespace = findSymbolTableEntryForNamespaceImport(existingJSXBackendNamespaceImportSpecifier);
			ste_jsxCreateElementMethod = steFor_createElement();
			return;
		}

		// create new namespace import for JSX backend
		val jsxBackendModule = jsxBackendHelper.getJsxBackendModule(state.resource); // FIXME consider moving this to transpiler state operations
		if(jsxBackendModule === null) {
			throw new RuntimeException("cannot locate JSX backend module for resource " + state.resource.URI);
		}
		val jsxBackendNamespaceName = "$jsxBackend";

		val impSpec = _NamespaceImportSpecifier(jsxBackendNamespaceName, true);
		val impDecl = _ImportDecl(null, impSpec);
		insertBefore(state.im.scriptElements.get(0), impDecl);
		state.info.setImportedModule_internal(impDecl, jsxBackendModule);

		ste_jsxBackendNamespace = createSymbolTableEntryInternal(jsxBackendNamespaceName) => [
			it.importSpecifier = impSpec;
		];
		ste_jsxCreateElementMethod = steFor_createElement();
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
			_PropertyAccessExpr(ste_jsxBackendNamespace, ste_jsxCreateElementMethod),
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

	// Generate Object.assign({}, {foo, bar: "Hi"}, spr)
	def private Expression convertJSXAttributes(List<JSXAttribute> attrs) {
		if(attrs.isEmpty) {
			_NULL
		} else if (attrs.size == 1 && attrs.get(0) instanceof JSXSpreadAttribute) {
			// Special case: if only a single spread operator is passed, we pass it directly, e.g. spr instead of cloning with Object.assign.
			(attrs.get(0) as JSXSpreadAttribute).expression
		}
		else {
			val spreadIndices = IntStream.range(0, attrs.size)
							.filter[i | attrs.get(i) instanceof JSXSpreadAttribute].toArray;
			// GHOLD-413: We have to make sure that the only properties locating next to each other are combined.
			// Moreover, the order of properties as well as spread operators must be preserved!
			val target = if (attrs.get(0) instanceof JSXSpreadAttribute) {
				// The first attribute is a spread object, the target must be {}.
				_ObjLit
			} else {
				// Otherwise, the target is of the form {foo: true, bar: "Hi"}
				val firstSpreadIndex = if (!spreadIndices.empty) {
					spreadIndices.get(0)
				} else {
					attrs.size
				}
				var firstProps = attrs.subList(0, firstSpreadIndex).map[it as JSXPropertyAttribute];
				_ObjLit(firstProps.map[convertJSXAttribute])
			}

			var parameters = new ArrayList<Expression>();
			parameters.add(target);

			for (var i = 0; i < spreadIndices.length; i++) {
				val curSpreadIdx = spreadIndices.get(i)
				// Spread expression passed is used directly
				parameters.add((attrs.get(curSpreadIdx) as JSXSpreadAttribute).expression);
				// Combine properties between spread intervals
				val nextSpreadIdx = if (i < spreadIndices.length-1) {
					spreadIndices.get(i + 1);
				} else {
					attrs.length
				}
				val propsBetweenTwoSpreads = attrs.subList(curSpreadIdx + 1, nextSpreadIdx)
				if (!propsBetweenTwoSpreads.empty) {
					parameters.add(_ObjLit(propsBetweenTwoSpreads.map[(it as JSXPropertyAttribute).convertJSXAttribute]));
				}
			}

			_CallExpr(_PropertyAccessExpr(steFor_Object,steFor_assign), parameters)
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
