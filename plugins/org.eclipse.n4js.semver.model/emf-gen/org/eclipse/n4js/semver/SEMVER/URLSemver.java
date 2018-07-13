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
 * A representation of the model object '<em><b>URL Semver</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.URLSemver#getSimpleVersion <em>Simple Version</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getURLSemver()
 * @model
 * @generated
 */
public interface URLSemver extends URLVersionSpecifier {
	/**
	 * Returns the value of the '<em><b>Simple Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Simple Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple Version</em>' containment reference.
	 * @see #setSimpleVersion(SimpleVersion)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getURLSemver_SimpleVersion()
	 * @model containment="true"
	 * @generated
	 */
	SimpleVersion getSimpleVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.URLSemver#getSimpleVersion <em>Simple Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Simple Version</em>' containment reference.
	 * @see #getSimpleVersion()
	 * @generated
	 */
	void setSimpleVersion(SimpleVersion value);

} // URLSemver
