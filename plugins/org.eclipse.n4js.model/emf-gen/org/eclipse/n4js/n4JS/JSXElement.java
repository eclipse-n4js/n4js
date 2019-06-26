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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JSX Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.JSXElement#getJsxElementName <em>Jsx Element Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.JSXElement#getJsxAttributes <em>Jsx Attributes</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.JSXElement#getJsxClosingName <em>Jsx Closing Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXElement()
 * @model
 * @generated
 */
public interface JSXElement extends Expression, JSXChild, JSXAbstractElement {
	/**
	 * Returns the value of the '<em><b>Jsx Element Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jsx Element Name</em>' containment reference.
	 * @see #setJsxElementName(JSXElementName)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXElement_JsxElementName()
	 * @model containment="true"
	 * @generated
	 */
	JSXElementName getJsxElementName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.JSXElement#getJsxElementName <em>Jsx Element Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jsx Element Name</em>' containment reference.
	 * @see #getJsxElementName()
	 * @generated
	 */
	void setJsxElementName(JSXElementName value);

	/**
	 * Returns the value of the '<em><b>Jsx Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.JSXAttribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jsx Attributes</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXElement_JsxAttributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<JSXAttribute> getJsxAttributes();

	/**
	 * Returns the value of the '<em><b>Jsx Closing Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jsx Closing Name</em>' containment reference.
	 * @see #setJsxClosingName(JSXElementName)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXElement_JsxClosingName()
	 * @model containment="true"
	 * @generated
	 */
	JSXElementName getJsxClosingName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.JSXElement#getJsxClosingName <em>Jsx Closing Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jsx Closing Name</em>' containment reference.
	 * @see #getJsxClosingName()
	 * @generated
	 */
	void setJsxClosingName(JSXElementName value);

} // JSXElement
