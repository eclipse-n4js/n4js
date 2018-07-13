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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Git Hub Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.GitHubVersion#getGithubUrl <em>Github Url</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.GitHubVersion#getCommitISH <em>Commit ISH</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getGitHubVersion()
 * @model
 * @generated
 */
public interface GitHubVersion extends NPMVersion {
	/**
	 * Returns the value of the '<em><b>Github Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Github Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Github Url</em>' attribute.
	 * @see #setGithubUrl(String)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getGitHubVersion_GithubUrl()
	 * @model unique="false"
	 * @generated
	 */
	String getGithubUrl();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.GitHubVersion#getGithubUrl <em>Github Url</em>}' attribute.
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
	 * <p>
	 * If the meaning of the '<em>Commit ISH</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit ISH</em>' attribute.
	 * @see #setCommitISH(String)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getGitHubVersion_CommitISH()
	 * @model unique="false"
	 * @generated
	 */
	String getCommitISH();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.GitHubVersion#getCommitISH <em>Commit ISH</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit ISH</em>' attribute.
	 * @see #getCommitISH()
	 * @generated
	 */
	void setCommitISH(String value);

} // GitHubVersion
