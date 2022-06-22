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
 * A representation of the model object '<em><b>Named Export Specifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.NamedExportSpecifier#getExportedElement <em>Exported Element</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.NamedExportSpecifier#getAlias <em>Alias</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getNamedExportSpecifier()
 * @model
 * @generated
 */
public interface NamedExportSpecifier extends MemberAccess {
	/**
	 * Returns the value of the '<em><b>Exported Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exported Element</em>' containment reference.
	 * @see #setExportedElement(IdentifierRef)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getNamedExportSpecifier_ExportedElement()
	 * @model containment="true"
	 * @generated
	 */
	IdentifierRef getExportedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.NamedExportSpecifier#getExportedElement <em>Exported Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exported Element</em>' containment reference.
	 * @see #getExportedElement()
	 * @generated
	 */
	void setExportedElement(IdentifierRef value);

	/**
	 * Returns the value of the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alias</em>' attribute.
	 * @see #setAlias(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getNamedExportSpecifier_Alias()
	 * @model unique="false"
	 * @generated
	 */
	String getAlias();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.NamedExportSpecifier#getAlias <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias</em>' attribute.
	 * @see #getAlias()
	 * @generated
	 */
	void setAlias(String value);

} // NamedExportSpecifier
