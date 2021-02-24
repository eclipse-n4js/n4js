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
 * A representation of the model object '<em><b>Git Hub Version Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.GitHubVersionRequirement#getGithubUrl <em>Github Url</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.Semver.GitHubVersionRequirement#getCommitISH <em>Commit ISH</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getGitHubVersionRequirement()
 * @model
 * @generated
 */
public interface GitHubVersionRequirement extends NPMVersionRequirement {
	/**
	 * Returns the value of the '<em><b>Github Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Github Url</em>' attribute.
	 * @see #setGithubUrl(String)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getGitHubVersionRequirement_GithubUrl()
	 * @model unique="false"
	 * @generated
	 */
	String getGithubUrl();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.GitHubVersionRequirement#getGithubUrl <em>Github Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Github Url</em>' attribute.
	 * @see #getGithubUrl()
	 * @generated
	 */
	void setGithubUrl(String value);

	/**
	 * Returns the value of the '<em><b>Commit ISH</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit ISH</em>' attribute.
	 * @see #setCommitISH(String)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getGitHubVersionRequirement_CommitISH()
	 * @model unique="false"
	 * @generated
	 */
	String getCommitISH();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.GitHubVersionRequirement#getCommitISH <em>Commit ISH</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit ISH</em>' attribute.
	 * @see #getCommitISH()
	 * @generated
	 */
	void setCommitISH(String value);

} // GitHubVersionRequirement
