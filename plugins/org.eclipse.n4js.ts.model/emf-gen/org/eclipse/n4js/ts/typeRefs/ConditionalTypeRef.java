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
 * A representation of the model object '<em><b>Conditional Type Ref</b></em>'.
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
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef#getTypeRef <em>Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef#getUpperBound <em>Upper Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef#getTrueTypeRef <em>True Type Ref</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef#getFalseTypeRef <em>False Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getConditionalTypeRef()
 * @model
 * @generated
 */
public interface ConditionalTypeRef extends TypeRef {
	/**
	 * Returns the value of the '<em><b>Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Ref</em>' containment reference.
	 * @see #setTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getConditionalTypeRef_TypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef#getTypeRef <em>Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Ref</em>' containment reference.
	 * @see #getTypeRef()
	 * @generated
	 */
	void setTypeRef(TypeRef value);

	/**
	 * Returns the value of the '<em><b>Upper Bound</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Bound</em>' containment reference.
	 * @see #setUpperBound(TypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getConditionalTypeRef_UpperBound()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getUpperBound();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef#getUpperBound <em>Upper Bound</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Bound</em>' containment reference.
	 * @see #getUpperBound()
	 * @generated
	 */
	void setUpperBound(TypeRef value);

	/**
	 * Returns the value of the '<em><b>True Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>True Type Ref</em>' containment reference.
	 * @see #setTrueTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getConditionalTypeRef_TrueTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getTrueTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef#getTrueTypeRef <em>True Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>True Type Ref</em>' containment reference.
	 * @see #getTrueTypeRef()
	 * @generated
	 */
	void setTrueTypeRef(TypeRef value);

	/**
	 * Returns the value of the '<em><b>False Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>False Type Ref</em>' containment reference.
	 * @see #setFalseTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getConditionalTypeRef_FalseTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getFalseTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.ConditionalTypeRef#getFalseTypeRef <em>False Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>False Type Ref</em>' containment reference.
	 * @see #getFalseTypeRef()
	 * @generated
	 */
	void setFalseTypeRef(TypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	String internalGetTypeRefAsString();

} // ConditionalTypeRef
