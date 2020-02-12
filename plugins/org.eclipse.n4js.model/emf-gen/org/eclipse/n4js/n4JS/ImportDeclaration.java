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
 *   <li>{@link org.eclipse.n4js.n4JS.ImportDeclaration#getModuleSpecifierAsText <em>Module Specifier As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ImportDeclaration#getModuleSpecifierForm <em>Module Specifier Form</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ImportDeclaration#isRetainedAtRunTime <em>Retained At Run Time</em>}</li>
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

	/**
	 * Returns the value of the '<em><b>Module Specifier As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Specifier As Text</em>' attribute.
	 * @see #setModuleSpecifierAsText(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportDeclaration_ModuleSpecifierAsText()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getModuleSpecifierAsText();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ImportDeclaration#getModuleSpecifierAsText <em>Module Specifier As Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Specifier As Text</em>' attribute.
	 * @see #getModuleSpecifierAsText()
	 * @generated
	 */
	void setModuleSpecifierAsText(String value);

	/**
	 * Returns the value of the '<em><b>Module Specifier Form</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4JS.ModuleSpecifierForm}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Specifier Form</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.ModuleSpecifierForm
	 * @see #setModuleSpecifierForm(ModuleSpecifierForm)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportDeclaration_ModuleSpecifierForm()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	ModuleSpecifierForm getModuleSpecifierForm();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ImportDeclaration#getModuleSpecifierForm <em>Module Specifier Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Specifier Form</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.ModuleSpecifierForm
	 * @see #getModuleSpecifierForm()
	 * @generated
	 */
	void setModuleSpecifierForm(ModuleSpecifierForm value);

	/**
	 * Returns the value of the '<em><b>Retained At Run Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Tells whether this import declaration will be present in the transpiler output code. Set during AST traversal.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Retained At Run Time</em>' attribute.
	 * @see #setRetainedAtRunTime(boolean)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getImportDeclaration_RetainedAtRunTime()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	boolean isRetainedAtRunTime();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ImportDeclaration#isRetainedAtRunTime <em>Retained At Run Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retained At Run Time</em>' attribute.
	 * @see #isRetainedAtRunTime()
	 * @generated
	 */
	void setRetainedAtRunTime(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells if this import is a so-called "bare import" of the form:
	 * <pre>
	 * import "path/to/SomeModule"
	 * </pre>
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isBare();

} // ImportDeclaration
