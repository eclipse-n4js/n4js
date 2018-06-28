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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enumerated Version Range</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.EnumeratedVersionRange#getSimpleVersions <em>Simple Versions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getEnumeratedVersionRange()
 * @model
 * @generated
 */
public interface EnumeratedVersionRange extends VersionRange {
	/**
	 * Returns the value of the '<em><b>Simple Versions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.semver.SEMVER.SimpleVersion}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Simple Versions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple Versions</em>' containment reference list.
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getEnumeratedVersionRange_SimpleVersions()
	 * @model containment="true"
	 * @generated
	 */
	EList<SimpleVersion> getSimpleVersions();

} // EnumeratedVersionRange
