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

import org.eclipse.n4js.ts.typeRefs.TypeRef;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TIndex Signature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TIndexSignature#isReadonly <em>Readonly</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TIndexSignature#getKeyName <em>Key Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TIndexSignature#getKeyTypeRef <em>Key Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TIndexSignature#getValueTypeRef <em>Value Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTIndexSignature()
 * @model
 * @generated
 */
public interface TIndexSignature extends TMember {
	/**
	 * Returns the value of the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Readonly</em>' attribute.
	 * @see #setReadonly(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTIndexSignature_Readonly()
	 * @model unique="false"
	 * @generated
	 */
	boolean isReadonly();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TIndexSignature#isReadonly <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Readonly</em>' attribute.
	 * @see #isReadonly()
	 * @generated
	 */
	void setReadonly(boolean value);

	/**
	 * Returns the value of the '<em><b>Key Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key Name</em>' attribute.
	 * @see #setKeyName(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTIndexSignature_KeyName()
	 * @model unique="false"
	 * @generated
	 */
	String getKeyName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TIndexSignature#getKeyName <em>Key Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Name</em>' attribute.
	 * @see #getKeyName()
	 * @generated
	 */
	void setKeyName(String value);

	/**
	 * Returns the value of the '<em><b>Key Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key Type Ref</em>' containment reference.
	 * @see #setKeyTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTIndexSignature_KeyTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getKeyTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TIndexSignature#getKeyTypeRef <em>Key Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key Type Ref</em>' containment reference.
	 * @see #getKeyTypeRef()
	 * @generated
	 */
	void setKeyTypeRef(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Value Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Type Ref</em>' containment reference.
	 * @see #setValueTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTIndexSignature_ValueTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getValueTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TIndexSignature#getValueTypeRef <em>Value Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Type Ref</em>' containment reference.
	 * @see #getValueTypeRef()
	 * @generated
	 */
	void setValueTypeRef(TypeRef value);

} // TIndexSignature
