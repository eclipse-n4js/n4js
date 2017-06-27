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
 * A representation of the model object '<em><b>Bootstrap Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.BootstrapModule#getModuleSpecifierWithWildcard <em>Module Specifier With Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.BootstrapModule#getSourcePath <em>Source Path</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getBootstrapModule()
 * @model
 * @generated
 */
public interface BootstrapModule extends EObject {
	/**
	 * Returns the value of the '<em><b>Module Specifier With Wildcard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Specifier With Wildcard</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Specifier With Wildcard</em>' attribute.
	 * @see #setModuleSpecifierWithWildcard(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getBootstrapModule_ModuleSpecifierWithWildcard()
	 * @model unique="false"
	 * @generated
	 */
	String getModuleSpecifierWithWildcard();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.BootstrapModule#getModuleSpecifierWithWildcard <em>Module Specifier With Wildcard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Specifier With Wildcard</em>' attribute.
	 * @see #getModuleSpecifierWithWildcard()
	 * @generated
	 */
	void setModuleSpecifierWithWildcard(String value);

	/**
	 * Returns the value of the '<em><b>Source Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Path</em>' attribute.
	 * @see #setSourcePath(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getBootstrapModule_SourcePath()
	 * @model unique="false"
	 * @generated
	 */
	String getSourcePath();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.BootstrapModule#getSourcePath <em>Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Path</em>' attribute.
	 * @see #getSourcePath()
	 * @generated
	 */
	void setSourcePath(String value);

} // BootstrapModule
