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

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.n4JS.Expression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JSX Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxElementName <em>Jsx Element Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxClosingName <em>Jsx Closing Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxAttributes <em>Jsx Attributes</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxChildren <em>Jsx Children</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage#getJSXElement()
 * @model
 * @generated
 */
public interface JSXElement extends Expression, JSXChild {
	/**
	 * Returns the value of the '<em><b>Jsx Element Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jsx Element Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jsx Element Name</em>' containment reference.
	 * @see #setJsxElementName(JSXElementName)
	 * @see org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage#getJSXElement_JsxElementName()
	 * @model containment="true"
	 * @generated
	 */
	JSXElementName getJsxElementName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxElementName <em>Jsx Element Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jsx Element Name</em>' containment reference.
	 * @see #getJsxElementName()
	 * @generated
	 */
	void setJsxElementName(JSXElementName value);

	/**
	 * Returns the value of the '<em><b>Jsx Closing Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jsx Closing Name</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jsx Closing Name</em>' containment reference.
	 * @see #setJsxClosingName(JSXElementName)
	 * @see org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage#getJSXElement_JsxClosingName()
	 * @model containment="true"
	 * @generated
	 */
	JSXElementName getJsxClosingName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4jsx.n4JSX.JSXElement#getJsxClosingName <em>Jsx Closing Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jsx Closing Name</em>' containment reference.
	 * @see #getJsxClosingName()
	 * @generated
	 */
	void setJsxClosingName(JSXElementName value);

	/**
	 * Returns the value of the '<em><b>Jsx Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4jsx.n4JSX.JSXAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jsx Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jsx Attributes</em>' containment reference list.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage#getJSXElement_JsxAttributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<JSXAttribute> getJsxAttributes();

	/**
	 * Returns the value of the '<em><b>Jsx Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4jsx.n4JSX.JSXChild}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jsx Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jsx Children</em>' containment reference list.
	 * @see org.eclipse.n4js.n4jsx.n4JSX.N4JSXPackage#getJSXElement_JsxChildren()
	 * @model containment="true"
	 * @generated
	 */
	EList<JSXChild> getJsxChildren();

} // JSXElement
