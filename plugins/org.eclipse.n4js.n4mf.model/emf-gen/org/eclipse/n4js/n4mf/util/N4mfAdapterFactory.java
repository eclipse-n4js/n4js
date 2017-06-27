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
package org.eclipse.n4js.n4mf.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.n4mf.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.n4js.n4mf.N4mfPackage
 * @generated
 */
public class N4mfAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static N4mfPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4mfAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = N4mfPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected N4mfSwitch<Adapter> modelSwitch =
		new N4mfSwitch<Adapter>() {
			@Override
			public Adapter caseProjectDescription(ProjectDescription object) {
				return createProjectDescriptionAdapter();
			}
			@Override
			public Adapter caseExecModule(ExecModule object) {
				return createExecModuleAdapter();
			}
			@Override
			public Adapter caseTestedProjects(TestedProjects object) {
				return createTestedProjectsAdapter();
			}
			@Override
			public Adapter caseInitModules(InitModules object) {
				return createInitModulesAdapter();
			}
			@Override
			public Adapter caseImplementedProjects(ImplementedProjects object) {
				return createImplementedProjectsAdapter();
			}
			@Override
			public Adapter caseProjectDependencies(ProjectDependencies object) {
				return createProjectDependenciesAdapter();
			}
			@Override
			public Adapter caseProvidedRuntimeLibraries(ProvidedRuntimeLibraries object) {
				return createProvidedRuntimeLibrariesAdapter();
			}
			@Override
			public Adapter caseRequiredRuntimeLibraries(RequiredRuntimeLibraries object) {
				return createRequiredRuntimeLibrariesAdapter();
			}
			@Override
			public Adapter caseSimpleProjectDescription(SimpleProjectDescription object) {
				return createSimpleProjectDescriptionAdapter();
			}
			@Override
			public Adapter caseTestedProject(TestedProject object) {
				return createTestedProjectAdapter();
			}
			@Override
			public Adapter caseDeclaredVersion(DeclaredVersion object) {
				return createDeclaredVersionAdapter();
			}
			@Override
			public Adapter caseSourceFragment(SourceFragment object) {
				return createSourceFragmentAdapter();
			}
			@Override
			public Adapter caseModuleFilter(ModuleFilter object) {
				return createModuleFilterAdapter();
			}
			@Override
			public Adapter caseBootstrapModule(BootstrapModule object) {
				return createBootstrapModuleAdapter();
			}
			@Override
			public Adapter caseExtendedRuntimeEnvironment(ExtendedRuntimeEnvironment object) {
				return createExtendedRuntimeEnvironmentAdapter();
			}
			@Override
			public Adapter caseProjectReference(ProjectReference object) {
				return createProjectReferenceAdapter();
			}
			@Override
			public Adapter caseSimpleProjectDependency(SimpleProjectDependency object) {
				return createSimpleProjectDependencyAdapter();
			}
			@Override
			public Adapter caseModuleFilterSpecifier(ModuleFilterSpecifier object) {
				return createModuleFilterSpecifierAdapter();
			}
			@Override
			public Adapter caseRuntimeProjectDependency(RuntimeProjectDependency object) {
				return createRuntimeProjectDependencyAdapter();
			}
			@Override
			public Adapter caseRequiredRuntimeLibraryDependency(RequiredRuntimeLibraryDependency object) {
				return createRequiredRuntimeLibraryDependencyAdapter();
			}
			@Override
			public Adapter caseProvidedRuntimeLibraryDependency(ProvidedRuntimeLibraryDependency object) {
				return createProvidedRuntimeLibraryDependencyAdapter();
			}
			@Override
			public Adapter caseProjectDependency(ProjectDependency object) {
				return createProjectDependencyAdapter();
			}
			@Override
			public Adapter caseVersionConstraint(VersionConstraint object) {
				return createVersionConstraintAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ProjectDescription <em>Project Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription
	 * @generated
	 */
	public Adapter createProjectDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ExecModule <em>Exec Module</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ExecModule
	 * @generated
	 */
	public Adapter createExecModuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.TestedProjects <em>Tested Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.TestedProjects
	 * @generated
	 */
	public Adapter createTestedProjectsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.InitModules <em>Init Modules</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.InitModules
	 * @generated
	 */
	public Adapter createInitModulesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ImplementedProjects <em>Implemented Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ImplementedProjects
	 * @generated
	 */
	public Adapter createImplementedProjectsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ProjectDependencies <em>Project Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ProjectDependencies
	 * @generated
	 */
	public Adapter createProjectDependenciesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries
	 * @generated
	 */
	public Adapter createProvidedRuntimeLibrariesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.RequiredRuntimeLibraries <em>Required Runtime Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.RequiredRuntimeLibraries
	 * @generated
	 */
	public Adapter createRequiredRuntimeLibrariesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.SimpleProjectDescription <em>Simple Project Description</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.SimpleProjectDescription
	 * @generated
	 */
	public Adapter createSimpleProjectDescriptionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.TestedProject <em>Tested Project</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.TestedProject
	 * @generated
	 */
	public Adapter createTestedProjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.DeclaredVersion <em>Declared Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.DeclaredVersion
	 * @generated
	 */
	public Adapter createDeclaredVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.SourceFragment <em>Source Fragment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.SourceFragment
	 * @generated
	 */
	public Adapter createSourceFragmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ModuleFilter <em>Module Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ModuleFilter
	 * @generated
	 */
	public Adapter createModuleFilterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.BootstrapModule <em>Bootstrap Module</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.BootstrapModule
	 * @generated
	 */
	public Adapter createBootstrapModuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment
	 * @generated
	 */
	public Adapter createExtendedRuntimeEnvironmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ProjectReference <em>Project Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ProjectReference
	 * @generated
	 */
	public Adapter createProjectReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.SimpleProjectDependency <em>Simple Project Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.SimpleProjectDependency
	 * @generated
	 */
	public Adapter createSimpleProjectDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ModuleFilterSpecifier <em>Module Filter Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ModuleFilterSpecifier
	 * @generated
	 */
	public Adapter createModuleFilterSpecifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.RuntimeProjectDependency <em>Runtime Project Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.RuntimeProjectDependency
	 * @generated
	 */
	public Adapter createRuntimeProjectDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.RequiredRuntimeLibraryDependency <em>Required Runtime Library Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.RequiredRuntimeLibraryDependency
	 * @generated
	 */
	public Adapter createRequiredRuntimeLibraryDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency <em>Provided Runtime Library Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency
	 * @generated
	 */
	public Adapter createProvidedRuntimeLibraryDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.ProjectDependency <em>Project Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.ProjectDependency
	 * @generated
	 */
	public Adapter createProjectDependencyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.n4js.n4mf.VersionConstraint <em>Version Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.n4js.n4mf.VersionConstraint
	 * @generated
	 */
	public Adapter createVersionConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //N4mfAdapterFactory
