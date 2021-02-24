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
 * A representation of the model object '<em><b>URL Commit ISH</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.URLCommitISH#getCommitISH <em>Commit ISH</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getURLCommitISH()
 * @model
 * @generated
 */
public interface URLCommitISH extends URLVersionSpecifier {
	/**
	 * Returns the value of the '<em><b>Commit ISH</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commit ISH</em>' attribute.
	 * @see #setCommitISH(String)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getURLCommitISH_CommitISH()
	 * @model unique="false"
	 * @generated
	 */
	String getCommitISH();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.URLCommitISH#getCommitISH <em>Commit ISH</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commit ISH</em>' attribute.
	 * @see #getCommitISH()
	 * @generated
	 */
	void setCommitISH(String value);

} // URLCommitISH
