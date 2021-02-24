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
 * A representation of the model object '<em><b>Version Range Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.VersionRangeConstraint#getVersionConstraints <em>Version Constraints</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionRangeConstraint()
 * @model
 * @generated
 */
public interface VersionRangeConstraint extends VersionRange {
	/**
	 * Returns the value of the '<em><b>Version Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.semver.Semver.SimpleVersion}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version Constraints</em>' containment reference list.
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getVersionRangeConstraint_VersionConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<SimpleVersion> getVersionConstraints();

} // VersionRangeConstraint
