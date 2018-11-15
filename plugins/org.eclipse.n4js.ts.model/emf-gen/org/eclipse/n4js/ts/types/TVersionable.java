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
 * A representation of the model object '<em><b>TVersionable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Base class for all versionable types.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TVersionable#getDeclaredVersion <em>Declared Version</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTVersionable()
 * @model abstract="true"
 * @generated
 */
public interface TVersionable extends Type {
	/**
	 * Returns the value of the '<em><b>Declared Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The declared version of the type. This is not used by N4JS at the moment but only
	 * in derived languages such as N4IDL. It needs to be stored in the type model though as
	 * it is required to be cached (i.e. stored in the Xtext index) for scoping etc.
	 * 	 * This is set by the N4IDL types builder.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Version</em>' attribute.
	 * @see #setDeclaredVersion(int)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTVersionable_DeclaredVersion()
	 * @model unique="false"
	 * @generated
	 */
	int getDeclaredVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TVersionable#getDeclaredVersion <em>Declared Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Version</em>' attribute.
	 * @see #getDeclaredVersion()
	 * @generated
	 */
	void setDeclaredVersion(int value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Overrides default implementation of type, returns the actually declared version (which
	 * is 0 in N4JS as it cannot be set there). Only used for derived languages such as N4IDL.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	int getVersion();

} // TVersionable
