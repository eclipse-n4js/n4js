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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4mf.N4mfPackage
 * @generated
 */
public interface N4mfFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	N4mfFactory eINSTANCE = org.eclipse.n4js.n4mf.impl.N4mfFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Project Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Description</em>'.
	 * @generated
	 */
	ProjectDescription createProjectDescription();

	/**
	 * Returns a new object of class '<em>Exec Module</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exec Module</em>'.
	 * @generated
	 */
	ExecModule createExecModule();

	/**
	 * Returns a new object of class '<em>Tested Projects</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tested Projects</em>'.
	 * @generated
	 */
	TestedProjects createTestedProjects();

	/**
	 * Returns a new object of class '<em>Init Modules</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Init Modules</em>'.
	 * @generated
	 */
	InitModules createInitModules();

	/**
	 * Returns a new object of class '<em>Implemented Projects</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Implemented Projects</em>'.
	 * @generated
	 */
	ImplementedProjects createImplementedProjects();

	/**
	 * Returns a new object of class '<em>Project Dependencies</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Dependencies</em>'.
	 * @generated
	 */
	ProjectDependencies createProjectDependencies();

	/**
	 * Returns a new object of class '<em>Provided Runtime Libraries</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provided Runtime Libraries</em>'.
	 * @generated
	 */
	ProvidedRuntimeLibraries createProvidedRuntimeLibraries();

	/**
	 * Returns a new object of class '<em>Required Runtime Libraries</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Runtime Libraries</em>'.
	 * @generated
	 */
	RequiredRuntimeLibraries createRequiredRuntimeLibraries();

	/**
	 * Returns a new object of class '<em>Simple Project Description</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Project Description</em>'.
	 * @generated
	 */
	SimpleProjectDescription createSimpleProjectDescription();

	/**
	 * Returns a new object of class '<em>Tested Project</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tested Project</em>'.
	 * @generated
	 */
	TestedProject createTestedProject();

	/**
	 * Returns a new object of class '<em>Declared Version</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Declared Version</em>'.
	 * @generated
	 */
	DeclaredVersion createDeclaredVersion();

	/**
	 * Returns a new object of class '<em>Source Fragment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Source Fragment</em>'.
	 * @generated
	 */
	SourceFragment createSourceFragment();

	/**
	 * Returns a new object of class '<em>Module Filter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Module Filter</em>'.
	 * @generated
	 */
	ModuleFilter createModuleFilter();

	/**
	 * Returns a new object of class '<em>Bootstrap Module</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bootstrap Module</em>'.
	 * @generated
	 */
	BootstrapModule createBootstrapModule();

	/**
	 * Returns a new object of class '<em>Extended Runtime Environment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Extended Runtime Environment</em>'.
	 * @generated
	 */
	ExtendedRuntimeEnvironment createExtendedRuntimeEnvironment();

	/**
	 * Returns a new object of class '<em>Project Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Reference</em>'.
	 * @generated
	 */
	ProjectReference createProjectReference();

	/**
	 * Returns a new object of class '<em>Simple Project Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Project Dependency</em>'.
	 * @generated
	 */
	SimpleProjectDependency createSimpleProjectDependency();

	/**
	 * Returns a new object of class '<em>Module Filter Specifier</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Module Filter Specifier</em>'.
	 * @generated
	 */
	ModuleFilterSpecifier createModuleFilterSpecifier();

	/**
	 * Returns a new object of class '<em>Required Runtime Library Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Runtime Library Dependency</em>'.
	 * @generated
	 */
	RequiredRuntimeLibraryDependency createRequiredRuntimeLibraryDependency();

	/**
	 * Returns a new object of class '<em>Provided Runtime Library Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provided Runtime Library Dependency</em>'.
	 * @generated
	 */
	ProvidedRuntimeLibraryDependency createProvidedRuntimeLibraryDependency();

	/**
	 * Returns a new object of class '<em>Project Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Project Dependency</em>'.
	 * @generated
	 */
	ProjectDependency createProjectDependency();

	/**
	 * Returns a new object of class '<em>Version Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Constraint</em>'.
	 * @generated
	 */
	VersionConstraint createVersionConstraint();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	N4mfPackage getN4mfPackage();

} //N4mfFactory
