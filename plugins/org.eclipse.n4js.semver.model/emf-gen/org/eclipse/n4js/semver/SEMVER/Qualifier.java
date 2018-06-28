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
 * A representation of the model object '<em><b>Qualifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getPreRelease <em>Pre Release</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getBuildMetadata <em>Build Metadata</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getQualifier()
 * @model
 * @generated
 */
public interface Qualifier extends EObject {
	/**
	 * Returns the value of the '<em><b>Pre Release</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pre Release</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pre Release</em>' attribute.
	 * @see #setPreRelease(String)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getQualifier_PreRelease()
	 * @model unique="false"
	 * @generated
	 */
	String getPreRelease();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getPreRelease <em>Pre Release</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Release</em>' attribute.
	 * @see #getPreRelease()
	 * @generated
	 */
	void setPreRelease(String value);

	/**
	 * Returns the value of the '<em><b>Build Metadata</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build Metadata</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build Metadata</em>' attribute.
	 * @see #setBuildMetadata(String)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getQualifier_BuildMetadata()
	 * @model unique="false"
	 * @generated
	 */
	String getBuildMetadata();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.Qualifier#getBuildMetadata <em>Build Metadata</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Metadata</em>' attribute.
	 * @see #getBuildMetadata()
	 * @generated
	 */
	void setBuildMetadata(String value);

} // Qualifier
