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
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._CallExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._NULL;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ObjLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyNameValuePair;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._StringLiteral;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._TRUE;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.JSXAbstractElement;
import org.eclipse.n4js.n4JS.JSXAttribute;
import org.eclipse.n4js.n4JS.JSXChild;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXExpression;
import org.eclipse.n4js.n4JS.JSXFragment;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.JSXSpreadAttribute;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.tooling.react.ReactHelper;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.Script_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.utils.ResourceType;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Inject;

/**
 * Transforms JSX tags to output code according to JSX/React conventions.
 * <p>
 * For example:
 *
 * <pre>
 * &lt;div attr="value">&lt;/div>
 * </pre>
 *
 * will be transformed to
 *
 * <pre>
 * React.createElement('div', Object.assign({attr: "value"}));
 * </pre>
 */
public class JSXTransformation extends Transformation {

	private SymbolTableEntryOriginal steForJsxBackendNamespace;
	private SymbolTableEntryOriginal steForJsxBackendElementFactoryFunction;
	private SymbolTableEntryOriginal steForJsxBackendFragmentComponent;

	@Inject
	private ReactHelper reactHelper;

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// ignore
	}

	/**
	 * IMPORTANT: our strategy for handling nested JSXElements is as follows: 1) direct child JSXElements will be
	 * handled together with their parent JSXElement 2) indirect children that are contained in nested expressions will
	 * be handled via a separate invocation to method #transformJSXElement(JSXElement)
	 *
	 * Example for case 1:
	 *
	 * <pre>
	 * let elem1 = &lt;div>&lt;a>&lt;/a>&lt;/div>; // &lt;a> is a direct child
	 * </pre>
	 *
	 * Example for case 2:
	 *
	 * <pre>
	 * let elem2 = &lt;div>{function() {return &lt;a>&lt;/a>;}}&lt;/div>; // &lt;a> is the child of a nested expression!
	 * </pre>
	 *
	 * More examples for case 2:
	 *
	 * <pre>
	 * let elem3 = &lt;div>{&lt;a>&lt;/a>}&lt;/div>;
	 * let elem4 = &lt;div prop={&lt;a>&lt;/a>}>&lt;/div>;
	 * </pre>
	 */
	@Override
	public void transform() {
		ResourceType resourceType = ResourceType.getResourceType(getState().resource);
		boolean inJSX = resourceType == ResourceType.JSX || resourceType == ResourceType.N4JSX;
		if (!inJSX) {
			return; // this transformation is not applicable
		}

		// Transform JSXFragments and JSXElements
		List<JSXAbstractElement> jsxAbstractElements = collectNodes(getState().im, JSXAbstractElement.class, true);
		if (jsxAbstractElements.isEmpty()) {
			// Nothing to transform
			return;
		}

		steForJsxBackendNamespace = prepareImportOfJsxBackend();
		steForJsxBackendElementFactoryFunction = prepareElementFactoryFunction();
		steForJsxBackendFragmentComponent = prepareFragmentComponent();

		// note: we are passing 'true' to #collectNodes(), i.e. we are searching for nested elements
		for (JSXAbstractElement jsxElem : jsxAbstractElements) {
			transformJSXAbstractElement(jsxElem);
		}
	}

	private SymbolTableEntryOriginal prepareImportOfJsxBackend() {
		TModule jsxBackendModule = reactHelper.getJsxBackendModule(getState().resource);
		if (jsxBackendModule == null) {
			throw new RuntimeException("cannot locate JSX backend for N4JSX resource " + getState().resource.getURI());
		}
		NamespaceImportSpecifier existingNamespaceImportOfReactIM = null;
		for (ImportDeclaration id : filter(getState().im.getScriptElements(), ImportDeclaration.class)) {
			if (getState().info.getImportedModule(id) == jsxBackendModule) {
				List<NamespaceImportSpecifier> niss = toList(
						filter(id.getImportSpecifiers(), NamespaceImportSpecifier.class));
				if (!niss.isEmpty()) {
					existingNamespaceImportOfReactIM = niss.get(0);
					break;
				}
			}
		}

		if (existingNamespaceImportOfReactIM != null) {
			// we already have a namespace import of the JSX backend, no need to create a new one:
			existingNamespaceImportOfReactIM.setFlaggedUsedInCode(true);
			return findSymbolTableEntryForNamespaceImport(existingNamespaceImportOfReactIM);
		}
		// create namespace import for the JSX backend
		// (note: we do not have to care for name clashes regarding name of the namespace, because validations ensure
		// that "React" is never used as a name in N4JSX files, except as the namespace name of a react import)
		return addNamespaceImport(jsxBackendModule, reactHelper.getJsxBackendNamespaceName());
	}

	private SymbolTableEntryOriginal prepareElementFactoryFunction() {
		TFunction elementFactoryFunction = reactHelper.getJsxBackendElementFactoryFunction(getState().resource);
		if (elementFactoryFunction == null) {
			throw new RuntimeException("cannot locate element factory function of JSX backend for N4JSX resource "
					+ getState().resource.getURI());
		}
		return getSymbolTableEntryOriginal(elementFactoryFunction, true);
	}

	private SymbolTableEntryOriginal prepareFragmentComponent() {
		IdentifiableElement fragmentComponent = reactHelper.getJsxBackendFragmentComponent(getState().resource);
		if (fragmentComponent == null) {
			throw new RuntimeException("cannot locate fragment component of JSX backend for N4JSX resource "
					+ getState().resource.getURI());
		}
		return getSymbolTableEntryOriginal(fragmentComponent, true);
	}

	private void transformJSXAbstractElement(JSXAbstractElement elem) {
		// IMPORTANT: 'elem' might be a direct or indirect child, but if it is a direct child, it was already
		// transformed when this method was invoked with its ancestor JSXElement as argument
		if (EcoreUtil2.getContainerOfType(elem, Script_IM.class) == null) {
			// 'elem' was already processed -> simply ignore it
			return;
		}
		replace(elem, convertJSXAbstractElement(elem));
	}

	private ParameterizedCallExpression convertJSXAbstractElement(JSXAbstractElement elem) {
		List<Expression> args = new ArrayList<>();
		if (elem instanceof JSXElement) {
			JSXElement jsxElem = (JSXElement) elem;
			args.add(getTagNameFromElement(jsxElem));
			args.add(convertJSXAttributes(jsxElem.getJsxAttributes()));
		} else {
			args.add(_PropertyAccessExpr(steForJsxBackendNamespace, steForJsxBackendFragmentComponent));
			args.add(_NULL());
		}
		args.addAll(toList(map(elem.getJsxChildren(), child -> convertJSXChild(child))));

		return _CallExpr(
				_PropertyAccessExpr(steForJsxBackendNamespace, steForJsxBackendElementFactoryFunction),
				args.toArray(new Expression[0]));
	}

	private Expression convertJSXChild(JSXChild child) {
		if (child instanceof JSXElement) {
			return convertJSXAbstractElement((JSXElement) child);
		}
		if (child instanceof JSXFragment) {
			return convertJSXAbstractElement((JSXFragment) child);
		}
		if (child instanceof JSXExpression) {
			return ((JSXExpression) child).getExpression();
		}
		return null;
	}

	// Generate Object.assign({}, {foo, bar: "Hi"}, spr)
	private Expression convertJSXAttributes(List<JSXAttribute> attrs) {
		if (attrs.isEmpty()) {
			return _NULL();
		} else if (attrs.size() == 1 && attrs.get(0) instanceof JSXSpreadAttribute) {
			// Special case: if only a single spread operator is passed, we pass it directly, e.g. spr instead of
			// cloning with Object.assign.
			return ((JSXSpreadAttribute) attrs.get(0)).getExpression();
		} else {

			List<Integer> spreadIndices = new ArrayList<>();
			for (int idx = 0; idx < attrs.size(); idx++) {
				if (attrs.get(idx) instanceof JSXSpreadAttribute) {
					spreadIndices.add(idx);
				}
			}
			// GHOLD-413: We have to make sure that the only properties locating next to each other are combined.
			// Moreover, the order of properties as well as spread operators must be preserved!
			List<PropertyNameValuePair> props = new ArrayList<>();
			if (attrs.get(0) instanceof JSXSpreadAttribute) {
				// The first attribute is a spread object, the target must be {}.
			} else {
				// Otherwise, the target is of the form {foo: true, bar: "Hi"}
				int firstSpreadIndex = (!spreadIndices.isEmpty()) ? spreadIndices.get(0) : attrs.size();
				for (int i = 0; i < firstSpreadIndex; i++) {
					props.add(convertJSXAttribute((JSXPropertyAttribute) attrs.get(i)));
				}
			}
			ObjectLiteral target = _ObjLit(props.toArray(new PropertyNameValuePair[0]));

			List<Expression> parameters = new ArrayList<>();
			parameters.add(target);

			for (int i = 0; i < spreadIndices.size(); i++) {
				int curSpreadIdx = spreadIndices.get(i);
				// Spread expression passed is used directly
				parameters.add(((JSXSpreadAttribute) attrs.get(curSpreadIdx)).getExpression());
				// Combine properties between spread intervals
				int nextSpreadIdx = (i < spreadIndices.size() - 1) ? spreadIndices.get(i + 1)
						: attrs.size();
				List<JSXAttribute> propsBetweenTwoSpreads = attrs.subList(curSpreadIdx + 1, nextSpreadIdx);
				if (!propsBetweenTwoSpreads.isEmpty()) {
					List<PropertyAssignment> props2 = new ArrayList<>();
					for (JSXAttribute attr : propsBetweenTwoSpreads) {
						props2.add(convertJSXAttribute((JSXPropertyAttribute) attr));
					}
					parameters.add(_ObjLit(props2.toArray(new PropertyAssignment[0])));
				}
			}

			return _CallExpr(_PropertyAccessExpr(steFor_Object(), steFor_Object_assign()),
					parameters.toArray(new Expression[0]));
		}
	}

	private PropertyNameValuePair convertJSXAttribute(JSXPropertyAttribute attr) {
		return _PropertyNameValuePair(
				getNameFromPropertyAttribute(attr),
				getValueExpressionFromPropertyAttribute(attr));
	}

	private Expression getTagNameFromElement(JSXElement elem) {
		Expression nameExpr = elem.getJsxElementName().getExpression();
		if (nameExpr instanceof IdentifierRef_IM) {
			IdentifierRef_IM idRef = (IdentifierRef_IM) nameExpr;
			SymbolTableEntry id = idRef.getId_IM();
			if (id == null) {
				return _StringLiteral(idRef.getIdAsText());
			}
		}
		return nameExpr;
	}

	private String getNameFromPropertyAttribute(JSXPropertyAttribute attr) {
		IdentifiableElement prop = attr.getProperty();
		if (prop != null && !prop.eIsProxy()) {
			return prop.getName();
		}
		return attr.getPropertyAsText();
	}

	private Expression getValueExpressionFromPropertyAttribute(JSXPropertyAttribute attr) {
		return attr.getJsxAttributeValue() != null ? attr.getJsxAttributeValue() : _TRUE();
	}
}