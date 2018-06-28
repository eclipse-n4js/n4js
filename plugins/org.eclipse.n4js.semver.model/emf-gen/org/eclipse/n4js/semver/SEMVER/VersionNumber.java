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
 * A representation of the model object '<em><b>Version Number</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getQualifier <em>Qualifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getMajor <em>Major</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getMinor <em>Minor</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getPath <em>Path</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionNumber()
 * @model
 * @generated
 */
public interface VersionNumber extends EObject {
	/**
	 * Returns the value of the '<em><b>Qualifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualifier</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualifier</em>' containment reference.
	 * @see #setQualifier(Qualifier)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionNumber_Qualifier()
	 * @model containment="true"
	 * @generated
	 */
	Qualifier getQualifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getQualifier <em>Qualifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualifier</em>' containment reference.
	 * @see #getQualifier()
	 * @generated
	 */
	void setQualifier(Qualifier value);

	/**
	 * Returns the value of the '<em><b>Major</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Major</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Major</em>' attribute.
	 * @see #setMajor(String)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionNumber_Major()
	 * @model unique="false"
	 * @generated
	 */
	String getMajor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getMajor <em>Major</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Major</em>' attribute.
	 * @see #getMajor()
	 * @generated
	 */
	void setMajor(String value);

	/**
	 * Returns the value of the '<em><b>Minor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minor</em>' attribute.
	 * @see #setMinor(String)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionNumber_Minor()
	 * @model unique="false"
	 * @generated
	 */
	String getMinor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getMinor <em>Minor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minor</em>' attribute.
	 * @see #getMinor()
	 * @generated
	 */
	void setMinor(String value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getVersionNumber_Path()
	 * @model unique="false"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.VersionNumber#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

} // VersionNumber
