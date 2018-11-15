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
 * A representation of the model object '<em><b>Simple Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * The name 'SimpleVersion' is taken from the Semver documentation.
 * However, this name can be misleading since a SimpleVersion can represent
 * a range of versions, e.g. >1.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.SimpleVersion#getNumber <em>Number</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isWithLetterV <em>With Letter V</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.SimpleVersion#getComparators <em>Comparators</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getSimpleVersion()
 * @model
 * @generated
 */
public interface SimpleVersion extends SemverToStringable {
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
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getSimpleVersion_Number()
	 * @model containment="true"
	 * @generated
	 */
	VersionNumber getNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#getNumber <em>Number</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' containment reference.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(VersionNumber value);

	/**
	 * Returns the value of the '<em><b>With Letter V</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>With Letter V</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>With Letter V</em>' attribute.
	 * @see #setWithLetterV(boolean)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getSimpleVersion_WithLetterV()
	 * @model unique="false"
	 * @generated
	 */
	boolean isWithLetterV();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.SimpleVersion#isWithLetterV <em>With Letter V</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>With Letter V</em>' attribute.
	 * @see #isWithLetterV()
	 * @generated
	 */
	void setWithLetterV(boolean value);

	/**
	 * Returns the value of the '<em><b>Comparators</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.n4js.semver.Semver.VersionComparator}.
	 * The literals are from the enumeration {@link org.eclipse.n4js.semver.Semver.VersionComparator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comparators</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comparators</em>' attribute list.
	 * @see org.eclipse.n4js.semver.Semver.VersionComparator
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getSimpleVersion_Comparators()
	 * @model unique="false"
	 * @generated
	 */
	EList<VersionComparator> getComparators();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * @return true if the version number is a wildcard
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isWildcard();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * * @return true iff the comparators are either empty, or contain 'v' or '='
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isSpecific();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isCaret();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isTilde();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isGreater();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isGreaterEquals();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isSmaller();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isSmallerEquals();

} // SimpleVersion
