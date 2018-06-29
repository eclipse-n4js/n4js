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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getNumber <em>Number</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getComparators <em>Comparators</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getSimpleVersion()
 * @model
 * @generated
 */
public interface SimpleVersion extends VersionRange {
	/**
	 * Returns the value of the '<em><b>Number</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' containment reference.
	 * @see #setNumber(VersionNumber)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getSimpleVersion_Number()
	 * @model containment="true"
	 * @generated
	 */
	VersionNumber getNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getNumber <em>Number</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' containment reference.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(VersionNumber value);

	/**
	 * Returns the value of the '<em><b>Comparators</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.n4js.semver.SEMVER.VersionComparator}.
	 * The literals are from the enumeration {@link org.eclipse.n4js.semver.SEMVER.VersionComparator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comparators</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comparators</em>' attribute list.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionComparator
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getSimpleVersion_Comparators()
	 * @model unique="false"
	 * @generated
	 */
	EList<VersionComparator> getComparators();

} // SimpleVersion
