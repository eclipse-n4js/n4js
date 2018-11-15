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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TAnonymous Formal Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTAnonymousFormalParameter()
 * @model
 * @generated
 */
public interface TAnonymousFormalParameter extends TFormalParameter {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the name of the anonymous parameter, maybe an artificial name
	 * (_par_n with n is the index of the parameter in the parameter list, or just _par_ in case of setter).
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns the declared name, may be null since parameter is anonymous and name is optional
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	String getDefinedName();

} // TAnonymousFormalParameter
