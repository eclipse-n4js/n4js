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
 * A representation of the model object '<em><b>JSX Property Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute#getProperty <em>Property</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute#getPropertyAsText <em>Property As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute#getJsxAttributeValue <em>Jsx Attribute Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXPropertyAttribute()
 * @model
 * @generated
 */
public interface JSXPropertyAttribute extends JSXAttribute, MemberAccess {
	/**
	 * Returns the value of the '<em><b>Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property</em>' reference.
	 * @see #setProperty(IdentifiableElement)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXPropertyAttribute_Property()
	 * @model
	 * @generated
	 */
	IdentifiableElement getProperty();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute#getProperty <em>Property</em>}' reference.
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
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXPropertyAttribute_PropertyAsText()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getPropertyAsText();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute#getPropertyAsText <em>Property As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property As Text</em>' attribute.
	 * @see #getPropertyAsText()
	 * @generated
	 */
	void setPropertyAsText(String value);

	/**
	 * Returns the value of the '<em><b>Jsx Attribute Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jsx Attribute Value</em>' containment reference.
	 * @see #setJsxAttributeValue(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getJSXPropertyAttribute_JsxAttributeValue()
	 * @model containment="true"
	 * @generated
	 */
	Expression getJsxAttributeValue();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.JSXPropertyAttribute#getJsxAttributeValue <em>Jsx Attribute Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jsx Attribute Value</em>' containment reference.
	 * @see #getJsxAttributeValue()
	 * @generated
	 */
	void setJsxAttributeValue(Expression value);

} // JSXPropertyAttribute
