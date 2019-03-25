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
package org.eclipse.n4js.projectDescription;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bootstrap Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.projectDescription.BootstrapModule#getModuleSpecifier <em>Module Specifier</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getBootstrapModule()
 * @model
 * @generated
 */
public interface BootstrapModule extends EObject {
	/**
	 * Returns the value of the '<em><b>Module Specifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Specifier</em>' attribute.
	 * @see #setModuleSpecifier(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getBootstrapModule_ModuleSpecifier()
	 * @model unique="false"
	 * @generated
	 */
	String getModuleSpecifier();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.BootstrapModule#getModuleSpecifier <em>Module Specifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Specifier</em>' attribute.
	 * @see #getModuleSpecifier()
	 * @generated
	 */
	void setModuleSpecifier(String value);

} // BootstrapModule
