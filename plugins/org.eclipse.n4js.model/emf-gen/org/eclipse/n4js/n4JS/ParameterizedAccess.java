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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Access</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ParameterizedAccess#getTypeArgs <em>Type Args</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getParameterizedAccess()
 * @model abstract="true"
 * @generated
 */
public interface ParameterizedAccess extends EObject {
	/**
	 * Returns the value of the '<em><b>Type Args</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.TypeReferenceInAST}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Args</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getParameterizedAccess_TypeArgs()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeReferenceInAST> getTypeArgs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns <code>true<code> if the expression has type arguments.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isParameterized();

} // ParameterizedAccess
