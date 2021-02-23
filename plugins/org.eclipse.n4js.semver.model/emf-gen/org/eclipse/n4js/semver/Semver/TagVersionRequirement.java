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
 * A representation of the model object '<em><b>Tag Version Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.TagVersionRequirement#getTagName <em>Tag Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getTagVersionRequirement()
 * @model
 * @generated
 */
public interface TagVersionRequirement extends NPMVersionRequirement {
	/**
	 * Returns the value of the '<em><b>Tag Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tag Name</em>' attribute.
	 * @see #setTagName(String)
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getTagVersionRequirement_TagName()
	 * @model unique="false"
	 * @generated
	 */
	String getTagName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.Semver.TagVersionRequirement#getTagName <em>Tag Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tag Name</em>' attribute.
	 * @see #getTagName()
	 * @generated
	 */
	void setTagName(String value);

} // TagVersionRequirement
