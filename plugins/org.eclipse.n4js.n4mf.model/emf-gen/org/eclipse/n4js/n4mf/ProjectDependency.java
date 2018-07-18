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
package org.eclipse.n4js.n4mf;

import org.eclipse.n4js.semver.SEMVER.VersionRangeSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Defines a unique project dependency by identifying a project by
 * projectId. The allowed version can be defined either in a range by
 * stating lower and upper version bound or by stating only one version.
 * So lowerVersionBound and upperVersionBound should be null if exactVersion
 * is and vice versa.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDependency#getVersionRequirement <em>Version Requirement</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDependency#getVersionRequirementString <em>Version Requirement String</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDependency()
 * @model
 * @generated
 */
public interface ProjectDependency extends ProjectReference {
	/**
	 * Returns the value of the '<em><b>Version Requirement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Requirement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Requirement</em>' containment reference.
	 * @see #setVersionRequirement(VersionRangeSet)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDependency_VersionRequirement()
	 * @model containment="true"
	 * @generated
	 */
	VersionRangeSet getVersionRequirement();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDependency#getVersionRequirement <em>Version Requirement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version Requirement</em>' containment reference.
	 * @see #getVersionRequirement()
	 * @generated
	 */
	void setVersionRequirement(VersionRangeSet value);

	/**
	 * Returns the value of the '<em><b>Version Requirement String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Requirement String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Requirement String</em>' attribute.
	 * @see #setVersionRequirementString(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDependency_VersionRequirementString()
	 * @model unique="false"
	 * @generated
	 */
	String getVersionRequirementString();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDependency#getVersionRequirementString <em>Version Requirement String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version Requirement String</em>' attribute.
	 * @see #getVersionRequirementString()
	 * @generated
	 */
	void setVersionRequirementString(String value);

} // ProjectDependency
