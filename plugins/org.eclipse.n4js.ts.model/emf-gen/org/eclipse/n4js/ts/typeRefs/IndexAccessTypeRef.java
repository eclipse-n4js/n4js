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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Index Access Type Ref</b></em>'.
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
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.IndexAccessTypeRef#getTargetTypeRef <em>Target Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.IndexAccessTypeRef#getIndexTypeRef <em>Index Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getIndexAccessTypeRef()
 * @model
 * @generated
 */
public interface IndexAccessTypeRef extends TypeRef {
	/**
	 * Returns the value of the '<em><b>Target Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Type Ref</em>' containment reference.
	 * @see #setTargetTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getIndexAccessTypeRef_TargetTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getTargetTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.IndexAccessTypeRef#getTargetTypeRef <em>Target Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Type Ref</em>' containment reference.
	 * @see #getTargetTypeRef()
	 * @generated
	 */
	void setTargetTypeRef(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Index Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index Type Ref</em>' containment reference.
	 * @see #setIndexTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getIndexAccessTypeRef_IndexTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getIndexTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.IndexAccessTypeRef#getIndexTypeRef <em>Index Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index Type Ref</em>' containment reference.
	 * @see #getIndexTypeRef()
	 * @generated
	 */
	void setIndexTypeRef(TypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

} // IndexAccessTypeRef
