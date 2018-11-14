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
 * A representation of the model object '<em><b>N4 Class Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getN4ClassDeclaration()
 * @model
 * @generated
 */
public interface N4ClassDeclaration extends N4ClassDefinition, N4ClassifierDeclaration, Versionable, VersionedElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns true if the class is declared as abstract.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isAbstract();

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

} // N4ClassDeclaration
