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
 * A representation of the model object '<em><b>Type Alias</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A type alias.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TypeAlias#getActualTypeRef <em>Actual Type Ref</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeAlias()
 * @model
 * @generated
 */
public interface TypeAlias extends GenericType, AccessibleTypeElement, SyntaxRelatedTElement {
	/**
	 * Returns the value of the '<em><b>Actual Type Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actual Type Ref</em>' containment reference.
	 * @see #setActualTypeRef(TypeRef)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTypeAlias_ActualTypeRef()
	 * @model containment="true"
	 * @generated
	 */
	TypeRef getActualTypeRef();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TypeAlias#getActualTypeRef <em>Actual Type Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Actual Type Ref</em>' containment reference.
	 * @see #getActualTypeRef()
	 * @generated
	 */
	void setActualTypeRef(TypeRef value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAlias();

} // TypeAlias
