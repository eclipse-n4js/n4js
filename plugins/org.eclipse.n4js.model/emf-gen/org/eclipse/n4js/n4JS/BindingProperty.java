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

import org.eclipse.n4js.ts.types.IdentifiableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binding Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.BindingProperty#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.BindingProperty#getPropertyAsText <em>Property As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.BindingProperty#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingProperty()
 * @model
 * @generated
 */
public interface BindingProperty extends PropertyNameOwner, MemberAccess {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' reference.
	 * @see #setProperty(IdentifiableElement)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingProperty_Property()
	 * @model
	 * @generated
	 */
	IdentifiableElement getProperty();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BindingProperty#getProperty <em>Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property</em>' reference.
	 * @see #getProperty()
	 * @generated
	 */
	void setProperty(IdentifiableElement value);

	/**
	 * Returns the value of the '<em><b>Property As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property As Text</em>' attribute.
	 * @see #setPropertyAsText(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingProperty_PropertyAsText()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getPropertyAsText();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BindingProperty#getPropertyAsText <em>Property As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property As Text</em>' attribute.
	 * @see #getPropertyAsText()
	 * @generated
	 */
	void setPropertyAsText(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(BindingElement)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getBindingProperty_Value()
	 * @model containment="true"
	 * @generated
	 */
	BindingElement getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.BindingProperty#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(BindingElement value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValidName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isSingleNameBinding();

} // BindingProperty
