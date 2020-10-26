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
package org.eclipse.n4js.ts.types;

import java.math.BigDecimal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TEnum Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Literal of a TEnum
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TEnumLiteral#getValueString <em>Value String</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TEnumLiteral#getValueNumber <em>Value Number</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTEnumLiteral()
 * @model
 * @generated
 */
public interface TEnumLiteral extends SyntaxRelatedTElement, IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * For normal and <code>@StringBased</code> enums this is the literal's value as given in the
	 * source code or the default value set by the types builder; <code>null</code> iff this literal
	 * belongs to a <code>@NumberBased</code> enum.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value String</em>' attribute.
	 * @see #setValueString(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTEnumLiteral_ValueString()
	 * @model unique="false"
	 * @generated
	 */
	String getValueString();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TEnumLiteral#getValueString <em>Value String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value String</em>' attribute.
	 * @see #getValueString()
	 * @generated
	 */
	void setValueString(String value);

	/**
	 * Returns the value of the '<em><b>Value Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * For <code>@NumberBased</code> enums this is the literal's value as given in the source code or
	 * the default value computed by the types builder; <code>null</code> iff this literal belongs to
	 * a normal or <code>@StringBased</code> enum.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Value Number</em>' attribute.
	 * @see #setValueNumber(BigDecimal)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTEnumLiteral_ValueNumber()
	 * @model unique="false"
	 * @generated
	 */
	BigDecimal getValueNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TEnumLiteral#getValueNumber <em>Value Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Number</em>' attribute.
	 * @see #getValueNumber()
	 * @generated
	 */
	void setValueNumber(BigDecimal value);

} // TEnumLiteral
