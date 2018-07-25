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
package org.eclipse.n4js.projectDescription;

import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  Reference to another project, including a version requirement.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDependency#getVersionRequirementString <em>Version Requirement String</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDependency#getVersionRequirement <em>Version Requirement</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDependency()
 * @model
 * @generated
 */
public interface ProjectDependency extends ProjectReference {
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
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDependency_VersionRequirementString()
	 * @model unique="false"
	 * @generated
	 */
	String getVersionRequirementString();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDependency#getVersionRequirementString <em>Version Requirement String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version Requirement String</em>' attribute.
	 * @see #getVersionRequirementString()
	 * @generated
	 */
	void setVersionRequirementString(String value);

	/**
	 * Returns the value of the '<em><b>Version Requirement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Requirement</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Requirement</em>' containment reference.
	 * @see #setVersionRequirement(NPMVersionRequirement)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDependency_VersionRequirement()
	 * @model containment="true"
	 * @generated
	 */
	NPMVersionRequirement getVersionRequirement();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDependency#getVersionRequirement <em>Version Requirement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version Requirement</em>' containment reference.
	 * @see #getVersionRequirement()
	 * @generated
	 */
	void setVersionRequirement(NPMVersionRequirement value);

} // ProjectDependency
