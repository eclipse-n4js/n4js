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
 * A representation of the model object '<em><b>TExportable Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TExportableElement#getExportedName <em>Exported Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTExportableElement()
 * @model
 * @generated
 */
public interface TExportableElement extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Exported Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exported Name</em>' attribute.
	 * @see #setExportedName(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTExportableElement_ExportedName()
	 * @model unique="false"
	 * @generated
	 */
	String getExportedName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TExportableElement#getExportedName <em>Exported Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exported Name</em>' attribute.
	 * @see #getExportedName()
	 * @generated
	 */
	void setExportedName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isExported();

} // TExportableElement
