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
package org.eclipse.n4js.n4jsx.n4JSX;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage
 * @generated
 */
public interface N4JSXFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	N4JSXFactory eINSTANCE = org.eclipse.n4js.n4jsx.n4JSX.impl.N4JSXFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>JSX Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Element</em>'.
	 * @generated
	 */
	JSXElement createJSXElement();

	/**
	 * Returns a new object of class '<em>JSX Text</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Text</em>'.
	 * @generated
	 */
	JSXText createJSXText();

	/**
	 * Returns a new object of class '<em>JSX Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Expression</em>'.
	 * @generated
	 */
	JSXExpression createJSXExpression();

	/**
	 * Returns a new object of class '<em>JSX Element Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Element Name</em>'.
	 * @generated
	 */
	JSXElementName createJSXElementName();

	/**
	 * Returns a new object of class '<em>JSX Property Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Property Attribute</em>'.
	 * @generated
	 */
	JSXPropertyAttribute createJSXPropertyAttribute();

	/**
	 * Returns a new object of class '<em>JSX Spread Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>JSX Spread Attribute</em>'.
	 * @generated
	 */
	JSXSpreadAttribute createJSXSpreadAttribute();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	N4JSXPackage getN4JSXPackage();

} //N4JSXFactory
