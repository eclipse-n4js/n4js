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
 * A representation of the model object '<em><b>Module Filter Specifier</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.projectDescription.ModuleFilterSpecifier#getModuleSpecifierWithWildcard <em>Module Specifier With Wildcard</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ModuleFilterSpecifier#getSourcePath <em>Source Path</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getModuleFilterSpecifier()
 * @model
 * @generated
 */
public interface ModuleFilterSpecifier extends EObject {
	/**
	 * Returns the value of the '<em><b>Module Specifier With Wildcard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Specifier With Wildcard</em>' attribute.
	 * @see #setModuleSpecifierWithWildcard(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getModuleFilterSpecifier_ModuleSpecifierWithWildcard()
	 * @model unique="false"
	 * @generated
	 */
	String getModuleSpecifierWithWildcard();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ModuleFilterSpecifier#getModuleSpecifierWithWildcard <em>Module Specifier With Wildcard</em>}' attribute.
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
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Path</em>' attribute.
	 * @see #setSourcePath(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getModuleFilterSpecifier_SourcePath()
	 * @model unique="false"
	 * @generated
	 */
	String getSourcePath();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ModuleFilterSpecifier#getSourcePath <em>Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Path</em>' attribute.
	 * @see #getSourcePath()
	 * @generated
	 */
	void setSourcePath(String value);

} // ModuleFilterSpecifier
