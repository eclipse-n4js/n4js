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
package org.eclipse.n4js.n4mf.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.n4mf.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class N4mfFactoryImpl extends EFactoryImpl implements N4mfFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static N4mfFactory init() {
		try {
			N4mfFactory theN4mfFactory = (N4mfFactory)EPackage.Registry.INSTANCE.getEFactory(N4mfPackage.eNS_URI);
			if (theN4mfFactory != null) {
				return theN4mfFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new N4mfFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4mfFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case N4mfPackage.PROJECT_DESCRIPTION: return createProjectDescription();
			case N4mfPackage.SIMPLE_PROJECT_DESCRIPTION: return createSimpleProjectDescription();
			case N4mfPackage.TESTED_PROJECT: return createTestedProject();
			case N4mfPackage.DECLARED_VERSION: return createDeclaredVersion();
			case N4mfPackage.SOURCE_FRAGMENT: return createSourceFragment();
			case N4mfPackage.MODULE_FILTER: return createModuleFilter();
			case N4mfPackage.BOOTSTRAP_MODULE: return createBootstrapModule();
			case N4mfPackage.PROJECT_REFERENCE: return createProjectReference();
			case N4mfPackage.SIMPLE_PROJECT_DEPENDENCY: return createSimpleProjectDependency();
			case N4mfPackage.MODULE_FILTER_SPECIFIER: return createModuleFilterSpecifier();
			case N4mfPackage.REQUIRED_RUNTIME_LIBRARY_DEPENDENCY: return createRequiredRuntimeLibraryDependency();
			case N4mfPackage.PROVIDED_RUNTIME_LIBRARY_DEPENDENCY: return createProvidedRuntimeLibraryDependency();
			case N4mfPackage.PROJECT_DEPENDENCY: return createProjectDependency();
			case N4mfPackage.VERSION_CONSTRAINT: return createVersionConstraint();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case N4mfPackage.PROJECT_TYPE:
				return createProjectTypeFromString(eDataType, initialValue);
			case N4mfPackage.SOURCE_FRAGMENT_TYPE:
				return createSourceFragmentTypeFromString(eDataType, initialValue);
			case N4mfPackage.MODULE_FILTER_TYPE:
				return createModuleFilterTypeFromString(eDataType, initialValue);
			case N4mfPackage.PROJECT_DEPENDENCY_SCOPE:
				return createProjectDependencyScopeFromString(eDataType, initialValue);
			case N4mfPackage.MODULE_LOADER:
				return createModuleLoaderFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case N4mfPackage.PROJECT_TYPE:
				return convertProjectTypeToString(eDataType, instanceValue);
			case N4mfPackage.SOURCE_FRAGMENT_TYPE:
				return convertSourceFragmentTypeToString(eDataType, instanceValue);
			case N4mfPackage.MODULE_FILTER_TYPE:
				return convertModuleFilterTypeToString(eDataType, instanceValue);
			case N4mfPackage.PROJECT_DEPENDENCY_SCOPE:
				return convertProjectDependencyScopeToString(eDataType, instanceValue);
			case N4mfPackage.MODULE_LOADER:
				return convertModuleLoaderToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectDescription createProjectDescription() {
		ProjectDescriptionImpl projectDescription = new ProjectDescriptionImpl();
		return projectDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleProjectDescription createSimpleProjectDescription() {
		SimpleProjectDescriptionImpl simpleProjectDescription = new SimpleProjectDescriptionImpl();
		return simpleProjectDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestedProject createTestedProject() {
		TestedProjectImpl testedProject = new TestedProjectImpl();
		return testedProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeclaredVersion createDeclaredVersion() {
		DeclaredVersionImpl declaredVersion = new DeclaredVersionImpl();
		return declaredVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceFragment createSourceFragment() {
		SourceFragmentImpl sourceFragment = new SourceFragmentImpl();
		return sourceFragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleFilter createModuleFilter() {
		ModuleFilterImpl moduleFilter = new ModuleFilterImpl();
		return moduleFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BootstrapModule createBootstrapModule() {
		BootstrapModuleImpl bootstrapModule = new BootstrapModuleImpl();
		return bootstrapModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectReference createProjectReference() {
		ProjectReferenceImpl projectReference = new ProjectReferenceImpl();
		return projectReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleProjectDependency createSimpleProjectDependency() {
		SimpleProjectDependencyImpl simpleProjectDependency = new SimpleProjectDependencyImpl();
		return simpleProjectDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleFilterSpecifier createModuleFilterSpecifier() {
		ModuleFilterSpecifierImpl moduleFilterSpecifier = new ModuleFilterSpecifierImpl();
		return moduleFilterSpecifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredRuntimeLibraryDependency createRequiredRuntimeLibraryDependency() {
		RequiredRuntimeLibraryDependencyImpl requiredRuntimeLibraryDependency = new RequiredRuntimeLibraryDependencyImpl();
		return requiredRuntimeLibraryDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProvidedRuntimeLibraryDependency createProvidedRuntimeLibraryDependency() {
		ProvidedRuntimeLibraryDependencyImpl providedRuntimeLibraryDependency = new ProvidedRuntimeLibraryDependencyImpl();
		return providedRuntimeLibraryDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectDependency createProjectDependency() {
		ProjectDependencyImpl projectDependency = new ProjectDependencyImpl();
		return projectDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionConstraint createVersionConstraint() {
		VersionConstraintImpl versionConstraint = new VersionConstraintImpl();
		return versionConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectType createProjectTypeFromString(EDataType eDataType, String initialValue) {
		ProjectType result = ProjectType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProjectTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourceFragmentType createSourceFragmentTypeFromString(EDataType eDataType, String initialValue) {
		SourceFragmentType result = SourceFragmentType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSourceFragmentTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleFilterType createModuleFilterTypeFromString(EDataType eDataType, String initialValue) {
		ModuleFilterType result = ModuleFilterType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertModuleFilterTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectDependencyScope createProjectDependencyScopeFromString(EDataType eDataType, String initialValue) {
		ProjectDependencyScope result = ProjectDependencyScope.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProjectDependencyScopeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleLoader createModuleLoaderFromString(EDataType eDataType, String initialValue) {
		ModuleLoader result = ModuleLoader.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertModuleLoaderToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4mfPackage getN4mfPackage() {
		return (N4mfPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static N4mfPackage getPackage() {
		return N4mfPackage.eINSTANCE;
	}

} //N4mfFactoryImpl
