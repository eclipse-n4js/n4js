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

import org.eclipse.n4js.ts.typeRefs.Versionable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>N4IDL Interface Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * IDL interfaces are like N4JS interfaces, except that they support declaring a version.
 * <!-- end-model-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4IDLInterfaceDeclaration()
 * @model
 * @generated
 */
public interface N4IDLInterfaceDeclaration extends N4InterfaceDeclaration, Versionable, VersionedElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Override VersionedElement#getVersion() to return the declared version.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return this.getDeclaredVersionOrZero();'"
	 * @generated
	 */
	int getVersion();

} // N4IDLInterfaceDeclaration
