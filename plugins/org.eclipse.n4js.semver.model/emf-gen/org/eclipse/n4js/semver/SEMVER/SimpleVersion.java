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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getNumber <em>Number</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getComparator <em>Comparator</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isHasTilde <em>Has Tilde</em>}</li>
 *   <li>{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isHasCaret <em>Has Caret</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getSimpleVersion()
 * @model
 * @generated
 */
public interface SimpleVersion extends EObject {
	/**
	 * Returns the value of the '<em><b>Number</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' containment reference.
	 * @see #setNumber(VersionNumber)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getSimpleVersion_Number()
	 * @model containment="true"
	 * @generated
	 */
	VersionNumber getNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getNumber <em>Number</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' containment reference.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(VersionNumber value);

	/**
	 * Returns the value of the '<em><b>Comparator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.semver.SEMVER.VersionComparator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comparator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comparator</em>' attribute.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionComparator
	 * @see #setComparator(VersionComparator)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getSimpleVersion_Comparator()
	 * @model unique="false"
	 * @generated
	 */
	VersionComparator getComparator();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#getComparator <em>Comparator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comparator</em>' attribute.
	 * @see org.eclipse.n4js.semver.SEMVER.VersionComparator
	 * @see #getComparator()
	 * @generated
	 */
	void setComparator(VersionComparator value);

	/**
	 * Returns the value of the '<em><b>Has Tilde</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Tilde</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Tilde</em>' attribute.
	 * @see #setHasTilde(boolean)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getSimpleVersion_HasTilde()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasTilde();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isHasTilde <em>Has Tilde</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Tilde</em>' attribute.
	 * @see #isHasTilde()
	 * @generated
	 */
	void setHasTilde(boolean value);

	/**
	 * Returns the value of the '<em><b>Has Caret</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Caret</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Caret</em>' attribute.
	 * @see #setHasCaret(boolean)
	 * @see org.eclipse.n4js.semver.SEMVER.SEMVERPackage#getSimpleVersion_HasCaret()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasCaret();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.semver.SEMVER.SimpleVersion#isHasCaret <em>Has Caret</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Caret</em>' attribute.
	 * @see #isHasCaret()
	 * @generated
	 */
	void setHasCaret(boolean value);

} // SimpleVersion
