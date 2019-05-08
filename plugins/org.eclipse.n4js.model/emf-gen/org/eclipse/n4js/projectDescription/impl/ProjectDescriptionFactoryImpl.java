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
package org.eclipse.n4js.projectDescription.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.n4js.projectDescription.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProjectDescriptionFactoryImpl extends EFactoryImpl implements ProjectDescriptionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ProjectDescriptionFactory init() {
		try {
			ProjectDescriptionFactory theProjectDescriptionFactory = (ProjectDescriptionFactory)EPackage.Registry.INSTANCE.getEFactory(ProjectDescriptionPackage.eNS_URI);
			if (theProjectDescriptionFactory != null) {
				return theProjectDescriptionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ProjectDescriptionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectDescriptionFactoryImpl() {
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
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION: return createProjectDescription();
			case ProjectDescriptionPackage.SOURCE_CONTAINER_DESCRIPTION: return createSourceContainerDescription();
			case ProjectDescriptionPackage.PROJECT_REFERENCE: return createProjectReference();
			case ProjectDescriptionPackage.PROJECT_DEPENDENCY: return createProjectDependency();
			case ProjectDescriptionPackage.MODULE_FILTER: return createModuleFilter();
			case ProjectDescriptionPackage.MODULE_FILTER_SPECIFIER: return createModuleFilterSpecifier();
			case ProjectDescriptionPackage.BOOTSTRAP_MODULE: return createBootstrapModule();
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
			case ProjectDescriptionPackage.PROJECT_TYPE:
				return createProjectTypeFromString(eDataType, initialValue);
			case ProjectDescriptionPackage.SOURCE_CONTAINER_TYPE:
				return createSourceContainerTypeFromString(eDataType, initialValue);
			case ProjectDescriptionPackage.MODULE_FILTER_TYPE:
				return createModuleFilterTypeFromString(eDataType, initialValue);
			case ProjectDescriptionPackage.DEPENDENCY_TYPE:
				return createDependencyTypeFromString(eDataType, initialValue);
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
			case ProjectDescriptionPackage.PROJECT_TYPE:
				return convertProjectTypeToString(eDataType, instanceValue);
			case ProjectDescriptionPackage.SOURCE_CONTAINER_TYPE:
				return convertSourceContainerTypeToString(eDataType, instanceValue);
			case ProjectDescriptionPackage.MODULE_FILTER_TYPE:
				return convertModuleFilterTypeToString(eDataType, instanceValue);
			case ProjectDescriptionPackage.DEPENDENCY_TYPE:
				return convertDependencyTypeToString(eDataType, instanceValue);
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
	public ProjectDescription createProjectDescription() {
		ProjectDescriptionImpl projectDescription = new ProjectDescriptionImpl();
		return projectDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SourceContainerDescription createSourceContainerDescription() {
		SourceContainerDescriptionImpl sourceContainerDescription = new SourceContainerDescriptionImpl();
		return sourceContainerDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProjectReference createProjectReference() {
		ProjectReferenceImpl projectReference = new ProjectReferenceImpl();
		return projectReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProjectDependency createProjectDependency() {
		ProjectDependencyImpl projectDependency = new ProjectDependencyImpl();
		return projectDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModuleFilter createModuleFilter() {
		ModuleFilterImpl moduleFilter = new ModuleFilterImpl();
		return moduleFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ModuleFilterSpecifier createModuleFilterSpecifier() {
		ModuleFilterSpecifierImpl moduleFilterSpecifier = new ModuleFilterSpecifierImpl();
		return moduleFilterSpecifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BootstrapModule createBootstrapModule() {
		BootstrapModuleImpl bootstrapModule = new BootstrapModuleImpl();
		return bootstrapModule;
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
	public SourceContainerType createSourceContainerTypeFromString(EDataType eDataType, String initialValue) {
		SourceContainerType result = SourceContainerType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSourceContainerTypeToString(EDataType eDataType, Object instanceValue) {
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
	public DependencyType createDependencyTypeFromString(EDataType eDataType, String initialValue) {
		DependencyType result = DependencyType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDependencyTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProjectDescriptionPackage getProjectDescriptionPackage() {
		return (ProjectDescriptionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ProjectDescriptionPackage getPackage() {
		return ProjectDescriptionPackage.eINSTANCE;
	}

} //ProjectDescriptionFactoryImpl
