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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4 Method Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.N4MethodDeclaration#isDtsDeclaredOptional <em>Dts Declared Optional</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4MethodDeclaration()
 * @model
 * @generated
 */
public interface N4MethodDeclaration extends AnnotableN4MemberDeclaration, MethodDeclaration {
	/**
	 * Returns the value of the '<em><b>Dts Declared Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Only allowed in DTS.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dts Declared Optional</em>' attribute.
	 * @see #setDtsDeclaredOptional(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4MethodDeclaration_DtsDeclaredOptional()
	 * @model unique="false"
	 * @generated
	 */
	boolean isDtsDeclaredOptional();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.N4MethodDeclaration#isDtsDeclaredOptional <em>Dts Declared Optional</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dts Declared Optional</em>' attribute.
	 * @see #isDtsDeclaredOptional()
	 * @generated
	 */
	void setDtsDeclaredOptional(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isConstructor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isCallSignature();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isConstructSignature();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isStatic();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Methods in classes may not be called 'prototype'.
	 * Generators may not be called 'constructor' either (except for computed names).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValidName();

} // N4MethodDeclaration
