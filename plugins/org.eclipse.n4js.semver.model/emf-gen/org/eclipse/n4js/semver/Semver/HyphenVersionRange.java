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
 * A representation of the model object '<em><b>Hyphen Version Range</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.HyphenVersionRange#getFrom <em>From</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.HyphenVersionRange#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getHyphenVersionRange()
 * @model
 * @generated
 */
public interface HyphenVersionRange extends VersionRange {
	/**
	 * Returns the value of the '<em><b>From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' containment reference.
	 * @see #setFrom(VersionNumber)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getHyphenVersionRange_From()
	 * @model containment="true"
	 * @generated
	 */
	VersionNumber getFrom();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.HyphenVersionRange#getFrom <em>From</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' containment reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(VersionNumber value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' containment reference.
	 * @see #setTo(VersionNumber)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getHyphenVersionRange_To()
	 * @model containment="true"
	 * @generated
	 */
	VersionNumber getTo();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.HyphenVersionRange#getTo <em>To</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' containment reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(VersionNumber value);

} // HyphenVersionRange
