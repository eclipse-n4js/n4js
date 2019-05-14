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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage
 * @generated
 */
public interface ProjectDescriptionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProjectDescriptionFactory eINSTANCE = org.eclipse.n4js.projectDescription.impl.ProjectDescriptionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Project Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Description</em>'.
	 * @generated
	 */
	ProjectDescription createProjectDescription();

	/**
	 * Returns a new object of class '<em>Source Container Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Source Container Description</em>'.
	 * @generated
	 */
	SourceContainerDescription createSourceContainerDescription();

	/**
	 * Returns a new object of class '<em>Project Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Reference</em>'.
	 * @generated
	 */
	ProjectReference createProjectReference();

	/**
	 * Returns a new object of class '<em>Project Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Dependency</em>'.
	 * @generated
	 */
	ProjectDependency createProjectDependency();

	/**
	 * Returns a new object of class '<em>Module Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Module Filter</em>'.
	 * @generated
	 */
	ModuleFilter createModuleFilter();

	/**
	 * Returns a new object of class '<em>Module Filter Specifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Module Filter Specifier</em>'.
	 * @generated
	 */
	ModuleFilterSpecifier createModuleFilterSpecifier();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ProjectDescriptionPackage getProjectDescriptionPackage();

} //ProjectDescriptionFactory
