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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Range Set Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionRangeSetRequirement#getRanges <em>Ranges</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionRangeSetRequirement()
 * @model
 * @generated
 */
public interface VersionRangeSetRequirement extends NPMVersionRequirement {
	/**
	 * Returns the value of the '<em><b>Ranges</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.semver.Semver.VersionRange}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ranges</em>' containment reference list.
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionRangeSetRequirement_Ranges()
	 * @model containment="true"
	 * @generated
	 */
	EList<VersionRange> getRanges();

} // VersionRangeSetRequirement
