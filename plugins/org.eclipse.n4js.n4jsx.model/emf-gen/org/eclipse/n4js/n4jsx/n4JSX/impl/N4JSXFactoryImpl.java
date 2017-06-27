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
package org.eclipse.n4js.n4jsx.n4JSX.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.n4jsx.n4JSX.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class N4JSXFactoryImpl extends EFactoryImpl implements N4JSXFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static N4JSXFactory init() {
		try {
			N4JSXFactory theN4JSXFactory = (N4JSXFactory)EPackage.Registry.INSTANCE.getEFactory(N4JSXPackage.eNS_URI);
			if (theN4JSXFactory != null) {
				return theN4JSXFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new N4JSXFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4JSXFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case N4JSXPackage.JSX_ELEMENT: return createJSXElement();
			case N4JSXPackage.JSX_TEXT: return createJSXText();
			case N4JSXPackage.JSX_EXPRESSION: return createJSXExpression();
			case N4JSXPackage.JSX_ELEMENT_NAME: return createJSXElementName();
			case N4JSXPackage.JSX_PROPERTY_ATTRIBUTE: return createJSXPropertyAttribute();
			case N4JSXPackage.JSX_SPREAD_ATTRIBUTE: return createJSXSpreadAttribute();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JSXElement createJSXElement() {
		JSXElementImpl jsxElement = new JSXElementImpl();
		return jsxElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JSXText createJSXText() {
		JSXTextImpl jsxText = new JSXTextImpl();
		return jsxText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JSXExpression createJSXExpression() {
		JSXExpressionImpl jsxExpression = new JSXExpressionImpl();
		return jsxExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JSXElementName createJSXElementName() {
		JSXElementNameImpl jsxElementName = new JSXElementNameImpl();
		return jsxElementName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JSXPropertyAttribute createJSXPropertyAttribute() {
		JSXPropertyAttributeImpl jsxPropertyAttribute = new JSXPropertyAttributeImpl();
		return jsxPropertyAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JSXSpreadAttribute createJSXSpreadAttribute() {
		JSXSpreadAttributeImpl jsxSpreadAttribute = new JSXSpreadAttributeImpl();
		return jsxSpreadAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4JSXPackage getN4JSXPackage() {
		return (N4JSXPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static N4JSXPackage getPackage() {
		return N4JSXPackage.eINSTANCE;
	}

} //N4JSXFactoryImpl
