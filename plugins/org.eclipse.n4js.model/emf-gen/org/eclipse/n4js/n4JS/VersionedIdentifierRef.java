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

import org.eclipse.n4js.ts.typeRefs.VersionedReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Versioned Identifier Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getVersionedIdentifierRef()
 * @model
 * @generated
 */
public interface VersionedIdentifierRef extends IdentifierRef, VersionedReference {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Override VersionedElement#getVersion() to return the declared version.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	int getVersion();

} // VersionedIdentifierRef
