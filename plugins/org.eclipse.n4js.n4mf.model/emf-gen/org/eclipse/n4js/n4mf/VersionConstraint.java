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
package org.eclipse.n4js.n4mf;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Version Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * A version bound describes a side of an version range.
 * A version bound can be inclusive or exclusive,
 * so e.g. in (1.0.0, 2.0.0] 1.0.0 is excluded,
 * but 2.0.0 is included, so all versions x with
 * 1.0.0 < x <= 2.0.0 are allowed. If only a lowerVersion
 * is assigned this means [version, infinity).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.VersionConstraint#isExclLowerBound <em>Excl Lower Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.VersionConstraint#getLowerVersion <em>Lower Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.VersionConstraint#isExclUpperBound <em>Excl Upper Bound</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.VersionConstraint#getUpperVersion <em>Upper Version</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getVersionConstraint()
 * @model
 * @generated
 */
public interface VersionConstraint extends EObject {
	/**
	 * Returns the value of the '<em><b>Excl Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Excl Lower Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Excl Lower Bound</em>' attribute.
	 * @see #setExclLowerBound(boolean)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getVersionConstraint_ExclLowerBound()
	 * @model unique="false"
	 * @generated
	 */
	boolean isExclLowerBound();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.VersionConstraint#isExclLowerBound <em>Excl Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Excl Lower Bound</em>' attribute.
	 * @see #isExclLowerBound()
	 * @generated
	 */
	void setExclLowerBound(boolean value);

	/**
	 * Returns the value of the '<em><b>Lower Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Version</em>' containment reference.
	 * @see #setLowerVersion(DeclaredVersion)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getVersionConstraint_LowerVersion()
	 * @model containment="true"
	 * @generated
	 */
	DeclaredVersion getLowerVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.VersionConstraint#getLowerVersion <em>Lower Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Version</em>' containment reference.
	 * @see #getLowerVersion()
	 * @generated
	 */
	void setLowerVersion(DeclaredVersion value);

	/**
	 * Returns the value of the '<em><b>Excl Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Excl Upper Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Excl Upper Bound</em>' attribute.
	 * @see #setExclUpperBound(boolean)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getVersionConstraint_ExclUpperBound()
	 * @model unique="false"
	 * @generated
	 */
	boolean isExclUpperBound();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.VersionConstraint#isExclUpperBound <em>Excl Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Excl Upper Bound</em>' attribute.
	 * @see #isExclUpperBound()
	 * @generated
	 */
	void setExclUpperBound(boolean value);

	/**
	 * Returns the value of the '<em><b>Upper Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Version</em>' containment reference.
	 * @see #setUpperVersion(DeclaredVersion)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getVersionConstraint_UpperVersion()
	 * @model containment="true"
	 * @generated
	 */
	DeclaredVersion getUpperVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.VersionConstraint#getUpperVersion <em>Upper Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Version</em>' containment reference.
	 * @see #getUpperVersion()
	 * @generated
	 */
	void setUpperVersion(DeclaredVersion value);

} // VersionConstraint
