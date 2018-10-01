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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template Segment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A template segment is a string literal that occurs in
 * template literals between interpolated expressions.
 * It has a dedicated type to allow to distinguish between
 * string literals that are interpolated expressions and the
 * raw value in a template literal.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.TemplateSegment#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.TemplateSegment#getRawValue <em>Raw Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTemplateSegment()
 * @model
 * @generated
 */
public interface TemplateSegment extends Literal {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTemplateSegment_Value()
	 * @model unique="false"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TemplateSegment#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Raw Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The raw value of the template segment
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Raw Value</em>' attribute.
	 * @see #setRawValue(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getTemplateSegment_RawValue()
	 * @model unique="false"
	 * @generated
	 */
	String getRawValue();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.TemplateSegment#getRawValue <em>Raw Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Raw Value</em>' attribute.
	 * @see #getRawValue()
	 * @generated
	 */
	void setRawValue(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the "template value (TV)" of the template segment as defined in
	 * the ECMA spec 11.8.6.1 Static Semantics: TV’s and TRV’s
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getValue();'"
	 * @generated
	 */
	String getValueAsString();

} // TemplateSegment
