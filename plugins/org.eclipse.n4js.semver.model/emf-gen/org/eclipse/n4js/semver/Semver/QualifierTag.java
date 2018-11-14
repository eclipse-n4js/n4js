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
 * A representation of the model object '<em><b>Qualifier Tag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.Semver.QualifierTag#getParts <em>Parts</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getQualifierTag()
 * @model
 * @generated
 */
public interface QualifierTag extends SemverToStringable {
	/**
	 * Returns the value of the '<em><b>Parts</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parts</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parts</em>' attribute list.
	 * @see org.eclipse.n4js.semver.Semver.SemverPackage#getQualifierTag_Parts()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getParts();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model unique="false" objUnique="false"
	 * @generated
	 */
	boolean equals(Object obj);

} // QualifierTag
