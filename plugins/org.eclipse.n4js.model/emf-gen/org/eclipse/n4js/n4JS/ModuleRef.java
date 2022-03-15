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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.ts.types.AbstractModule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Abstract base for elements that may refer to another module, i.e. {@link ImportDeclaration}s (always)
 * and {@link ExportDeclaration}s (in case of re-exports).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4JS.ModuleRef#getModule <em>Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ModuleRef#getModuleSpecifierAsText <em>Module Specifier As Text</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4JS.ModuleRef#getModuleSpecifierForm <em>Module Specifier Form</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4JS.N4JSPackage#getModuleRef()
 * @model abstract="true"
 * @generated
 */
public interface ModuleRef extends EObject {
	/**
	 * Returns the value of the '<em><b>Module</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module</em>' reference.
	 * @see #setModule(AbstractModule)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getModuleRef_Module()
	 * @model
	 * @generated
	 */
	AbstractModule getModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ModuleRef#getModule <em>Module</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module</em>' reference.
	 * @see #getModule()
	 * @generated
	 */
	void setModule(AbstractModule value);

	/**
	 * Returns the value of the '<em><b>Module Specifier As Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Specifier As Text</em>' attribute.
	 * @see #setModuleSpecifierAsText(String)
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getModuleRef_ModuleSpecifierAsText()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getModuleSpecifierAsText();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ModuleRef#getModuleSpecifierAsText <em>Module Specifier As Text</em>}' attribute.
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
	 * @see org.eclipse.n4js.n4JS.N4JSPackage#getModuleRef_ModuleSpecifierForm()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	ModuleSpecifierForm getModuleSpecifierForm();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4JS.ModuleRef#getModuleSpecifierForm <em>Module Specifier Form</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Specifier Form</em>' attribute.
	 * @see org.eclipse.n4js.n4JS.ModuleSpecifierForm
	 * @see #getModuleSpecifierForm()
	 * @generated
	 */
	void setModuleSpecifierForm(ModuleSpecifierForm value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 *  Tells whether this {@link ModuleRef} is actually referring to another model, without triggering proxy resolution.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 * @generated
	 */
	boolean isReferringToOtherModule();

} // ModuleRef
