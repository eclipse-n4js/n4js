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

import org.eclipse.n4js.ts.types.ExpressionInTypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Query Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  Only allowed in DTS.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.QueryTypeRef#getExpr <em>Expr</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getQueryTypeRef()
 * @model
 * @generated
 */
public interface QueryTypeRef extends TypeRef {
	/**
	 * Returns the value of the '<em><b>Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expr</em>' containment reference.
	 * @see #setExpr(ExpressionInTypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getQueryTypeRef_Expr()
	 * @model containment="true"
	 * @generated
	 */
	ExpressionInTypeRef getExpr();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.QueryTypeRef#getExpr <em>Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expr</em>' containment reference.
	 * @see #getExpr()
	 * @generated
	 */
	void setExpr(ExpressionInTypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

} // QueryTypeRef
