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
 * A representation of the model object '<em><b>Versioned Parameterized Type Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.ts.typeRefs.TypeRefsPackage#getVersionedParameterizedTypeRef()
 * @model
 * @generated
 */
public interface VersionedParameterizedTypeRef extends ParameterizedTypeRef, VersionedReference {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	int getVersion();

} // VersionedParameterizedTypeRef
