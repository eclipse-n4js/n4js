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
package org.eclipse.n4js.semver.Semver.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.n4js.semver.Semver.GitHubVersionRequirement;
import org.eclipse.n4js.semver.Semver.SemverPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Git Hub Version Requirement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.GitHubVersionRequirementImpl#getGithubUrl <em>Github Url</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.impl.GitHubVersionRequirementImpl#getCommitISH <em>Commit ISH</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GitHubVersionRequirementImpl extends NPMVersionRequirementImpl implements GitHubVersionRequirement {
	/**
	 * The default value of the '{@link #getGithubUrl() <em>Github Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGithubUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String GITHUB_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGithubUrl() <em>Github Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGithubUrl()
	 * @generated
	 * @ordered
	 */
	protected String githubUrl = GITHUB_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getCommitISH() <em>Commit ISH</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitISH()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMIT_ISH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommitISH() <em>Commit ISH</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommitISH()
	 * @generated
	 * @ordered
	 */
	protected String commitISH = COMMIT_ISH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GitHubVersionRequirementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SemverPackage.Literals.GIT_HUB_VERSION_REQUIREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGithubUrl() {
		return githubUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGithubUrl(String newGithubUrl) {
		String oldGithubUrl = githubUrl;
		githubUrl = newGithubUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.GIT_HUB_VERSION_REQUIREMENT__GITHUB_URL, oldGithubUrl, githubUrl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCommitISH() {
		return commitISH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommitISH(String newCommitISH) {
		String oldCommitISH = commitISH;
		commitISH = newCommitISH;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SemverPackage.GIT_HUB_VERSION_REQUIREMENT__COMMIT_ISH, oldCommitISH, commitISH));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT__GITHUB_URL:
				return getGithubUrl();
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT__COMMIT_ISH:
				return getCommitISH();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT__GITHUB_URL:
				setGithubUrl((String)newValue);
				return;
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT__COMMIT_ISH:
				setCommitISH((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT__GITHUB_URL:
				setGithubUrl(GITHUB_URL_EDEFAULT);
				return;
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT__COMMIT_ISH:
				setCommitISH(COMMIT_ISH_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT__GITHUB_URL:
				return GITHUB_URL_EDEFAULT == null ? githubUrl != null : !GITHUB_URL_EDEFAULT.equals(githubUrl);
			case SemverPackage.GIT_HUB_VERSION_REQUIREMENT__COMMIT_ISH:
				return COMMIT_ISH_EDEFAULT == null ? commitISH != null : !COMMIT_ISH_EDEFAULT.equals(commitISH);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (githubUrl: ");
		result.append(githubUrl);
		result.append(", commitISH: ");
		result.append(commitISH);
		result.append(')');
		return result.toString();
	}

} //GitHubVersionRequirementImpl
