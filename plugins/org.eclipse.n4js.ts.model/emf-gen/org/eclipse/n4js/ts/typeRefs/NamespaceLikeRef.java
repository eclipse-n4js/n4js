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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.types.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Namespace Like Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef#getDeclaredTypeAsText <em>Declared Type As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef#getDeclaredType <em>Declared Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getNamespaceLikeRef()
 * @model
 * @generated
 */
public interface NamespaceLikeRef extends EObject {
	/**
	 * Returns the value of the '<em><b>Declared Type As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Type As Text</em>' attribute.
	 * @see #setDeclaredTypeAsText(String)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getNamespaceLikeRef_DeclaredTypeAsText()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getDeclaredTypeAsText();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef#getDeclaredTypeAsText <em>Declared Type As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Type As Text</em>' attribute.
	 * @see #getDeclaredTypeAsText()
	 * @generated
	 */
	void setDeclaredTypeAsText(String value);

	/**
	 * Returns the value of the '<em><b>Declared Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Type</em>' reference.
	 * @see #setDeclaredType(Type)
	 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getNamespaceLikeRef_DeclaredType()
	 * @model
	 * @generated
	 */
	Type getDeclaredType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.typeRefs.NamespaceLikeRef#getDeclaredType <em>Declared Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Type</em>' reference.
	 * @see #getDeclaredType()
	 * @generated
	 */
	void setDeclaredType(Type value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	NamespaceLikeRef getPreviousSibling();

} // NamespaceLikeRef
