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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression In Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.ExpressionInTypeRef#getNameTypeRef <em>Name Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.ExpressionInTypeRef#getIdentifierNames <em>Identifier Names</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getExpressionInTypeRef()
 * @model
 * @generated
 */
public interface ExpressionInTypeRef extends EObject {
	/**
	 * Returns the value of the '<em><b>Name Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name Type Ref</em>' containment reference.
	 * @see #setNameTypeRef(LiteralTypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getExpressionInTypeRef_NameTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	LiteralTypeRef getNameTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.ExpressionInTypeRef#getNameTypeRef <em>Name Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name Type Ref</em>' containment reference.
	 * @see #getNameTypeRef()
	 * @generated
	 */
	void setNameTypeRef(LiteralTypeRef value);

	/**
	 * Returns the value of the '<em><b>Identifier Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identifier Names</em>' attribute list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getExpressionInTypeRef_IdentifierNames()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getIdentifierNames();

} // ExpressionInTypeRef
