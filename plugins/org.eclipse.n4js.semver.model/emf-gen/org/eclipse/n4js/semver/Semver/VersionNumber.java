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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Number</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMajor <em>Major</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMinor <em>Minor</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getPatch <em>Patch</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getExtended <em>Extended</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionNumber#getQualifier <em>Qualifier</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber()
 * @model
 * @generated
 */
public interface VersionNumber extends SemverToStringable {
	/**
	 * Returns the value of the '<em><b>Major</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Major</em>' containment reference.
	 * @see #setMajor(VersionPart)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Major()
	 * @model containment="true"
	 * @generated
	 */
	VersionPart getMajor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMajor <em>Major</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Major</em>' containment reference.
	 * @see #getMajor()
	 * @generated
	 */
	void setMajor(VersionPart value);

	/**
	 * Returns the value of the '<em><b>Minor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minor</em>' containment reference.
	 * @see #setMinor(VersionPart)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Minor()
	 * @model containment="true"
	 * @generated
	 */
	VersionPart getMinor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getMinor <em>Minor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minor</em>' containment reference.
	 * @see #getMinor()
	 * @generated
	 */
	void setMinor(VersionPart value);

	/**
	 * Returns the value of the '<em><b>Patch</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patch</em>' containment reference.
	 * @see #setPatch(VersionPart)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Patch()
	 * @model containment="true"
	 * @generated
	 */
	VersionPart getPatch();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getPatch <em>Patch</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Patch</em>' containment reference.
	 * @see #getPatch()
	 * @generated
	 */
	void setPatch(VersionPart value);

	/**
	 * Returns the value of the '<em><b>Extended</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.semver.Semver.VersionPart}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended</em>' containment reference list.
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Extended()
	 * @model containment="true"
	 * @generated
	 */
	EList<VersionPart> getExtended();

	/**
	 * Returns the value of the '<em><b>Qualifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualifier</em>' containment reference.
	 * @see #setQualifier(Qualifier)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionNumber_Qualifier()
	 * @model containment="true"
	 * @generated
	 */
	Qualifier getQualifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.VersionNumber#getQualifier <em>Qualifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualifier</em>' containment reference.
	 * @see #getQualifier()
	 * @generated
	 */
	void setQualifier(Qualifier value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * @return true if the major part is a wildcard
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isWildcard();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	EList<String> getPreReleaseTag();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	boolean hasPreReleaseTag();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false"
	 * @generated
	 */
	int length();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" idxUnique="false"
	 * @generated
	 */
	VersionPart getPart(int idx);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" objUnique="false"
	 * @generated
	 */
	boolean equals(Object obj);

} // VersionNumber
