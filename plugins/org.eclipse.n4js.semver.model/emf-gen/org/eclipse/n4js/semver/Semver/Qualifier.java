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
 * A representation of the model object '<em><b>Qualifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.Qualifier#getPreRelease <em>Pre Release</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.Qualifier#getBuildMetadata <em>Build Metadata</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getQualifier()
 * @model
 * @generated
 */
public interface Qualifier extends SemverToStringable {
	/**
	 * Returns the value of the '<em><b>Pre Release</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Release</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Release</em>' containment reference.
	 * @see #setPreRelease(QualifierTag)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getQualifier_PreRelease()
	 * @model containment="true"
	 * @generated
	 */
	QualifierTag getPreRelease();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.Qualifier#getPreRelease <em>Pre Release</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Release</em>' containment reference.
	 * @see #getPreRelease()
	 * @generated
	 */
	void setPreRelease(QualifierTag value);

	/**
	 * Returns the value of the '<em><b>Build Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build Metadata</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build Metadata</em>' containment reference.
	 * @see #setBuildMetadata(QualifierTag)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getQualifier_BuildMetadata()
	 * @model containment="true"
	 * @generated
	 */
	QualifierTag getBuildMetadata();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.Qualifier#getBuildMetadata <em>Build Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Metadata</em>' containment reference.
	 * @see #getBuildMetadata()
	 * @generated
	 */
	void setBuildMetadata(QualifierTag value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" objUnique="false"
	 * @generated
	 */
	boolean equals(Object obj);

} // Qualifier
