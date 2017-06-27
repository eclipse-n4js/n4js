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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.common.util.EList;

import org.eclipse.n4js.ts.types.TModule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Import Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ImportDeclaration#getImportSpecifiers <em>Import Specifiers</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ImportDeclaration#isImportFrom <em>Import From</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ImportDeclaration#getModule <em>Module</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportDeclaration()
 * @model
 * @generated
 */
public interface ImportDeclaration extends AnnotableScriptElement {
	/**
	 * Returns the value of the '<em><b>Import Specifiers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4JS.ImportSpecifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import Specifiers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import Specifiers</em>' containment reference list.
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportDeclaration_ImportSpecifiers()
	 * @model containment="true"
	 * @generated
	 */
	EList<ImportSpecifier> getImportSpecifiers();

	/**
	 * Returns the value of the '<em><b>Import From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import From</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import From</em>' attribute.
	 * @see #setImportFrom(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportDeclaration_ImportFrom()
	 * @model unique="false"
	 * @generated
	 */
	boolean isImportFrom();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ImportDeclaration#isImportFrom <em>Import From</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import From</em>' attribute.
	 * @see #isImportFrom()
	 * @generated
	 */
	void setImportFrom(boolean value);

	/**
	 * Returns the value of the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module</em>' reference.
	 * @see #setModule(TModule)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportDeclaration_Module()
	 * @model
	 * @generated
	 */
	TModule getModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ImportDeclaration#getModule <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module</em>' reference.
	 * @see #getModule()
	 * @generated
	 */
	void setModule(TModule value);

} // ImportDeclaration
