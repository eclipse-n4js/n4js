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
package org.eclipse.n4js.ts.typeRefs;

import java.math.BigDecimal;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Numeric Literal Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef#isAstNegated <em>Ast Negated</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getNumericLiteralTypeRef()
 * @model
 * @generated
 */
public interface NumericLiteralTypeRef extends LiteralTypeRef {
	/**
	 * Returns the value of the '<em><b>Ast Negated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  True iff this is used as an AST node <em>and</em> the {@link LiteralTypeRef#getAstValue() astValue} needs to be negated to obtain the actual value.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ast Negated</em>' attribute.
	 * @see #setAstNegated(boolean)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getNumericLiteralTypeRef_AstNegated()
	 * @model unique="false"
	 * @generated
	 */
	boolean isAstNegated();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef#isAstNegated <em>Ast Negated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ast Negated</em>' attribute.
	 * @see #isAstNegated()
	 * @generated
	 */
	void setAstNegated(boolean value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(BigDecimal)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getNumericLiteralTypeRef_Value()
	 * @model unique="false"
	 * @generated
	 */
	BigDecimal getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(BigDecimal value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

} // NumericLiteralTypeRef
