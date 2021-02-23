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
package org.eclipse.n4js.semver.Semver;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Part</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionPart#isWildcard <em>Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionPart#getNumberRaw <em>Number Raw</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionPart()
 * @model
 * @generated
 */
public interface VersionPart extends SemverToStringable {
	/**
	 * Returns the value of the '<em><b>Wildcard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wildcard</em>' attribute.
	 * @see #setWildcard(boolean)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionPart_Wildcard()
	 * @model unique="false"
	 * @generated
	 */
	boolean isWildcard();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionPart#isWildcard <em>Wildcard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wildcard</em>' attribute.
	 * @see #isWildcard()
	 * @generated
	 */
	void setWildcard(boolean value);

	/**
	 * Returns the value of the '<em><b>Number Raw</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Raw</em>' attribute.
	 * @see #setNumberRaw(Integer)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionPart_NumberRaw()
	 * @model unique="false"
	 * @generated
	 */
	Integer getNumberRaw();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionPart#getNumberRaw <em>Number Raw</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Raw</em>' attribute.
	 * @see #getNumberRaw()
	 * @generated
	 */
	void setNumberRaw(Integer value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	Integer getNumber();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" objUnique="false"
	 * @generated
	 */
	boolean equals(Object obj);

} // VersionPart
