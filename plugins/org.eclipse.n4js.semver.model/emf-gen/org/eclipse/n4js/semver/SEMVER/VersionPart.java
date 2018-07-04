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
package org.eclipse.n4js.semver.SEMVER;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Part</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.VersionPart#isWildcard <em>Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.VersionPart#getNumber <em>Number</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionPart()
 * @model
 * @generated
 */
public interface VersionPart extends EObject {
	/**
	 * Returns the value of the '<em><b>Wildcard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wildcard</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wildcard</em>' attribute.
	 * @see #setWildcard(boolean)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionPart_Wildcard()
	 * @model unique="false"
	 * @generated
	 */
	boolean isWildcard();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.VersionPart#isWildcard <em>Wildcard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Wildcard</em>' attribute.
	 * @see #isWildcard()
	 * @generated
	 */
	void setWildcard(boolean value);

	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(int)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionPart_Number()
	 * @model unique="false"
	 * @generated
	 */
	int getNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.VersionPart#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(int value);

} // VersionPart
