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
 * A representation of the model object '<em><b>Workspace Version Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.WorkspaceVersionRequirement#getVersion <em>Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.WorkspaceVersionRequirement#getOtherVersion <em>Other Version</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getWorkspaceVersionRequirement()
 * @model
 * @generated
 */
public interface WorkspaceVersionRequirement extends NPMVersionRequirement {
	/**
	 * Returns the value of the '<em><b>Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' containment reference.
	 * @see #setVersion(SimpleVersion)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getWorkspaceVersionRequirement_Version()
	 * @model containment="true"
	 * @generated
	 */
	SimpleVersion getVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.WorkspaceVersionRequirement#getVersion <em>Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' containment reference.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(SimpleVersion value);

	/**
	 * Returns the value of the '<em><b>Other Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Other Version</em>' attribute.
	 * @see #setOtherVersion(String)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getWorkspaceVersionRequirement_OtherVersion()
	 * @model unique="false"
	 * @generated
	 */
	String getOtherVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.WorkspaceVersionRequirement#getOtherVersion <em>Other Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Other Version</em>' attribute.
	 * @see #getOtherVersion()
	 * @generated
	 */
	void setOtherVersion(String value);

} // WorkspaceVersionRequirement
