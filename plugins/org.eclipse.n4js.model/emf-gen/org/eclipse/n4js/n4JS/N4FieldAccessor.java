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
 * A representation of the model object '<em><b>N4 Field Accessor</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4FieldAccessor()
 * @model abstract="true"
 * @generated
 */
public interface N4FieldAccessor extends AnnotableN4MemberDeclaration, FieldAccessor {
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
	 * <!-- begin-model-doc -->
	 * *
	 * Field accessors in classes may not be called 'prototype' or 'constructor' (except for computed names).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isValidName();

} // N4FieldAccessor
