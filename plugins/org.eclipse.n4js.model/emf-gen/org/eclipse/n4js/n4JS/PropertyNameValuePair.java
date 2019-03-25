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

import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TypableElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Name Value Pair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#getDefinedField <em>Defined Field</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#isDeclaredOptional <em>Declared Optional</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameValuePair()
 * @model
 * @generated
 */
public interface PropertyNameValuePair extends AnnotablePropertyAssignment, TypedElement, TypableElement {
	/**
	 * Returns the value of the '<em><b>Defined Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Defined Field</em>' reference.
	 * @see #setDefinedField(TStructField)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameValuePair_DefinedField()
	 * @model transient="true"
	 * @generated
	 */
	TStructField getDefinedField();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#getDefinedField <em>Defined Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defined Field</em>' reference.
	 * @see #getDefinedField()
	 * @generated
	 */
	void setDefinedField(TStructField value);

	/**
	 * Returns the value of the '<em><b>Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Optional</em>' attribute.
	 * @see #setDeclaredOptional(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameValuePair_DeclaredOptional()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDeclaredOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#isDeclaredOptional <em>Declared Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Optional</em>' attribute.
	 * @see #isDeclaredOptional()
	 * @generated
	 */
	void setDeclaredOptional(boolean value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The (initial) value of the property.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(Expression)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getPropertyNameValuePair_Expression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.PropertyNameValuePair#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(Expression value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	TStructField getDefinedMember();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Methods in object literals may not be called 'prototype'.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValidName();

} // PropertyNameValuePair
