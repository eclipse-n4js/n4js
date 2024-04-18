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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.semver.Semver.SemverPackage
 * @generated
 */
public interface SemverFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SemverFactory eINSTANCE = org.eclipse.n4js.semver.Semver.impl.SemverFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>URL Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>URL Version Requirement</em>'.
	 * @generated
	 */
	URLVersionRequirement createURLVersionRequirement();

	/**
	 * Returns a new object of class '<em>URL Semver</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>URL Semver</em>'.
	 * @generated
	 */
	URLSemver createURLSemver();

	/**
	 * Returns a new object of class '<em>URL Commit ISH</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>URL Commit ISH</em>'.
	 * @generated
	 */
	URLCommitISH createURLCommitISH();

	/**
	 * Returns a new object of class '<em>Workspace Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Workspace Version Requirement</em>'.
	 * @generated
	 */
	WorkspaceVersionRequirement createWorkspaceVersionRequirement();

	/**
	 * Returns a new object of class '<em>Git Hub Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Git Hub Version Requirement</em>'.
	 * @generated
	 */
	GitHubVersionRequirement createGitHubVersionRequirement();

	/**
	 * Returns a new object of class '<em>Local Path Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Path Version Requirement</em>'.
	 * @generated
	 */
	LocalPathVersionRequirement createLocalPathVersionRequirement();

	/**
	 * Returns a new object of class '<em>Tag Version Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tag Version Requirement</em>'.
	 * @generated
	 */
	TagVersionRequirement createTagVersionRequirement();

	/**
	 * Returns a new object of class '<em>Version Range Set Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Range Set Requirement</em>'.
	 * @generated
	 */
	VersionRangeSetRequirement createVersionRangeSetRequirement();

	/**
	 * Returns a new object of class '<em>Hyphen Version Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hyphen Version Range</em>'.
	 * @generated
	 */
	HyphenVersionRange createHyphenVersionRange();

	/**
	 * Returns a new object of class '<em>Version Range Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Range Constraint</em>'.
	 * @generated
	 */
	VersionRangeConstraint createVersionRangeConstraint();

	/**
	 * Returns a new object of class '<em>Simple Version</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Version</em>'.
	 * @generated
	 */
	SimpleVersion createSimpleVersion();

	/**
	 * Returns a new object of class '<em>Version Number</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Number</em>'.
	 * @generated
	 */
	VersionNumber createVersionNumber();

	/**
	 * Returns a new object of class '<em>Version Part</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Part</em>'.
	 * @generated
	 */
	VersionPart createVersionPart();

	/**
	 * Returns a new object of class '<em>Qualifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Qualifier</em>'.
	 * @generated
	 */
	Qualifier createQualifier();

	/**
	 * Returns a new object of class '<em>Qualifier Tag</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Qualifier Tag</em>'.
	 * @generated
	 */
	QualifierTag createQualifierTag();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SemverPackage getSemverPackage();

} //SemverFactory
