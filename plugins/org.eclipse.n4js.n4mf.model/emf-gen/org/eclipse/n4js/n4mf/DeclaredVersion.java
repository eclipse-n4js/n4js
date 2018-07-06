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
 * A representation of the model object '<em><b>Declared Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * All elements of the declared version not set manually will be set automatically to 0.
 *  * To be later validated: The length of the major, minor and micro numbers is
 * additionally limited to four digits.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.DeclaredVersion#getMajor <em>Major</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.DeclaredVersion#getMinor <em>Minor</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.DeclaredVersion#getMicro <em>Micro</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.DeclaredVersion#getQualifier <em>Qualifier</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.DeclaredVersion#getBuildMetaData <em>Build Meta Data</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getDeclaredVersion()
 * @model
 * @generated
 */
public interface DeclaredVersion extends EObject {
	/**
	 * Returns the value of the '<em><b>Major</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Major</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Major</em>' attribute.
	 * @see #setMajor(int)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getDeclaredVersion_Major()
	 * @model unique="false"
	 * @generated
	 */
	int getMajor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getMajor <em>Major</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Major</em>' attribute.
	 * @see #getMajor()
	 * @generated
	 */
	void setMajor(int value);

	/**
	 * Returns the value of the '<em><b>Minor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Minor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Minor</em>' attribute.
	 * @see #setMinor(int)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getDeclaredVersion_Minor()
	 * @model unique="false"
	 * @generated
	 */
	int getMinor();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getMinor <em>Minor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Minor</em>' attribute.
	 * @see #getMinor()
	 * @generated
	 */
	void setMinor(int value);

	/**
	 * Returns the value of the '<em><b>Micro</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Micro</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Micro</em>' attribute.
	 * @see #setMicro(int)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getDeclaredVersion_Micro()
	 * @model unique="false"
	 * @generated
	 */
	int getMicro();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getMicro <em>Micro</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Micro</em>' attribute.
	 * @see #getMicro()
	 * @generated
	 */
	void setMicro(int value);

	/**
	 * Returns the value of the '<em><b>Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualifier</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualifier</em>' attribute.
	 * @see #setQualifier(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getDeclaredVersion_Qualifier()
	 * @model unique="false"
	 * @generated
	 */
	String getQualifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getQualifier <em>Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualifier</em>' attribute.
	 * @see #getQualifier()
	 * @generated
	 */
	void setQualifier(String value);

	/**
	 * Returns the value of the '<em><b>Build Meta Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Build Meta Data</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Build Meta Data</em>' attribute.
	 * @see #setBuildMetaData(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getDeclaredVersion_BuildMetaData()
	 * @model unique="false"
	 * @generated
	 */
	String getBuildMetaData();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getBuildMetaData <em>Build Meta Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Build Meta Data</em>' attribute.
	 * @see #getBuildMetaData()
	 * @generated
	 */
	void setBuildMetaData(String value);

} // DeclaredVersion
