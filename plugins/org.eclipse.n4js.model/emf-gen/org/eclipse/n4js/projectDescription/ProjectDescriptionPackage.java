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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * *
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  * Contributors:
 *   NumberFour AG - Initial API and implementation
 * <!-- end-model-doc -->
 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel fileExtensions='n4jsProjectDescription' modelDirectory='/org.eclipse.n4js.model/emf-gen' forceOverwrite='true' updateClasspath='false' complianceLevel='8.0' copyrightFields='false' copyrightText='Copyright (c) 2016 NumberFour AG.\nAll rights reserved. This program and the accompanying materials\nare made available under the terms of the Eclipse Public License v1.0\nwhich accompanies this distribution, and is available at\nhttp://www.eclipse.org/legal/epl-v10.html\n\nContributors:\n  NumberFour AG - Initial API and implementation' language='' basePackage='org.eclipse.n4js'"
 * @generated
 */
public interface ProjectDescriptionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "projectDescription";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/projectDescription/projectDescription";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "projectDescription";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProjectDescriptionPackage eINSTANCE = org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl <em>Project Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getProjectDescription()
	 * @generated
	 */
	int PROJECT_DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__PROJECT_NAME = 0;

	/**
	 * The feature id for the '<em><b>Vendor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__VENDOR_ID = 1;

	/**
	 * The feature id for the '<em><b>Vendor Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__VENDOR_NAME = 2;

	/**
	 * The feature id for the '<em><b>Project Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__PROJECT_VERSION = 3;

	/**
	 * The feature id for the '<em><b>Project Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__PROJECT_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Main Module</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__MAIN_MODULE = 5;

	/**
	 * The feature id for the '<em><b>Extended Runtime Environment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT = 6;

	/**
	 * The feature id for the '<em><b>Provided Runtime Libraries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES = 7;

	/**
	 * The feature id for the '<em><b>Required Runtime Libraries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES = 8;

	/**
	 * The feature id for the '<em><b>Project Dependencies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES = 9;

	/**
	 * The feature id for the '<em><b>Implementation Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__IMPLEMENTATION_ID = 10;

	/**
	 * The feature id for the '<em><b>Implemented Projects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS = 11;

	/**
	 * The feature id for the '<em><b>Init Modules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__INIT_MODULES = 12;

	/**
	 * The feature id for the '<em><b>Exec Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__EXEC_MODULE = 13;

	/**
	 * The feature id for the '<em><b>Output Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__OUTPUT_PATH = 14;

	/**
	 * The feature id for the '<em><b>Source Containers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__SOURCE_CONTAINERS = 15;

	/**
	 * The feature id for the '<em><b>Module Filters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__MODULE_FILTERS = 16;

	/**
	 * The feature id for the '<em><b>Tested Projects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__TESTED_PROJECTS = 17;

	/**
	 * The feature id for the '<em><b>Module Loader</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__MODULE_LOADER = 18;

	/**
	 * The feature id for the '<em><b>Defines Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__DEFINES_PACKAGE = 19;

	/**
	 * The feature id for the '<em><b>Has Nested Node Modules Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__HAS_NESTED_NODE_MODULES_FOLDER = 20;

	/**
	 * The feature id for the '<em><b>Has N4JS Nature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__HAS_N4JS_NATURE = 21;

	/**
	 * The feature id for the '<em><b>Yarn Workspace Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__YARN_WORKSPACE_ROOT = 22;

	/**
	 * The feature id for the '<em><b>Workspaces</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__WORKSPACES = 23;

	/**
	 * The number of structural features of the '<em>Project Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION_FEATURE_COUNT = 24;

	/**
	 * The number of operations of the '<em>Project Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.impl.SourceContainerDescriptionImpl <em>Source Container Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.impl.SourceContainerDescriptionImpl
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getSourceContainerDescription()
	 * @generated
	 */
	int SOURCE_CONTAINER_DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Source Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION__PATHS = 1;

	/**
	 * The number of structural features of the '<em>Source Container Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Source Container Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.impl.ProjectReferenceImpl <em>Project Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectReferenceImpl
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getProjectReference()
	 * @generated
	 */
	int PROJECT_REFERENCE = 2;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_REFERENCE__PROJECT_NAME = 0;

	/**
	 * The number of structural features of the '<em>Project Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_REFERENCE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Project Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_REFERENCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.impl.ProjectDependencyImpl <em>Project Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDependencyImpl
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getProjectDependency()
	 * @generated
	 */
	int PROJECT_DEPENDENCY = 3;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY__PROJECT_NAME = PROJECT_REFERENCE__PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY__TYPE = PROJECT_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version Requirement String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY__VERSION_REQUIREMENT_STRING = PROJECT_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Version Requirement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY__VERSION_REQUIREMENT = PROJECT_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Project Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY_FEATURE_COUNT = PROJECT_REFERENCE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Project Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY_OPERATION_COUNT = PROJECT_REFERENCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.impl.ModuleFilterImpl <em>Module Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.impl.ModuleFilterImpl
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getModuleFilter()
	 * @generated
	 */
	int MODULE_FILTER = 4;

	/**
	 * The feature id for the '<em><b>Module Filter Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILTER__MODULE_FILTER_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Module Specifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILTER__MODULE_SPECIFIERS = 1;

	/**
	 * The number of structural features of the '<em>Module Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILTER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Module Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILTER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.impl.ModuleFilterSpecifierImpl <em>Module Filter Specifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.impl.ModuleFilterSpecifierImpl
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getModuleFilterSpecifier()
	 * @generated
	 */
	int MODULE_FILTER_SPECIFIER = 5;

	/**
	 * The feature id for the '<em><b>Module Specifier With Wildcard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD = 0;

	/**
	 * The feature id for the '<em><b>Source Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILTER_SPECIFIER__SOURCE_PATH = 1;

	/**
	 * The number of structural features of the '<em>Module Filter Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILTER_SPECIFIER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Module Filter Specifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODULE_FILTER_SPECIFIER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.impl.BootstrapModuleImpl <em>Bootstrap Module</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.impl.BootstrapModuleImpl
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getBootstrapModule()
	 * @generated
	 */
	int BOOTSTRAP_MODULE = 6;

	/**
	 * The feature id for the '<em><b>Module Specifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOTSTRAP_MODULE__MODULE_SPECIFIER = 0;

	/**
	 * The number of structural features of the '<em>Bootstrap Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOTSTRAP_MODULE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Bootstrap Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOTSTRAP_MODULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.ProjectType <em>Project Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.ProjectType
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getProjectType()
	 * @generated
	 */
	int PROJECT_TYPE = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.SourceContainerType <em>Source Container Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.SourceContainerType
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getSourceContainerType()
	 * @generated
	 */
	int SOURCE_CONTAINER_TYPE = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.ModuleFilterType <em>Module Filter Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.ModuleFilterType
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getModuleFilterType()
	 * @generated
	 */
	int MODULE_FILTER_TYPE = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.ModuleLoader <em>Module Loader</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.ModuleLoader
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getModuleLoader()
	 * @generated
	 */
	int MODULE_LOADER = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.projectDescription.DependencyType <em>Dependency Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.projectDescription.DependencyType
	 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getDependencyType()
	 * @generated
	 */
	int DEPENDENCY_TYPE = 11;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.projectDescription.ProjectDescription <em>Project Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Description</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription
	 * @generated
	 */
	EClass getProjectDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectName <em>Project Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getProjectName()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_ProjectName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getVendorId <em>Vendor Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor Id</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getVendorId()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_VendorId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getVendorName <em>Vendor Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor Name</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getVendorName()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_VendorName();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectVersion <em>Project Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Project Version</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getProjectVersion()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ProjectVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectType <em>Project Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Type</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getProjectType()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_ProjectType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getMainModule <em>Main Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Main Module</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getMainModule()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_MainModule();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extended Runtime Environment</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getExtendedRuntimeEnvironment()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ExtendedRuntimeEnvironment();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Provided Runtime Libraries</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getProvidedRuntimeLibraries()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ProvidedRuntimeLibraries();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getRequiredRuntimeLibraries <em>Required Runtime Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Required Runtime Libraries</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getRequiredRuntimeLibraries()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_RequiredRuntimeLibraries();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectDependencies <em>Project Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Project Dependencies</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getProjectDependencies()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ProjectDependencies();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getImplementationId <em>Implementation Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation Id</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getImplementationId()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_ImplementationId();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getImplementedProjects <em>Implemented Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implemented Projects</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getImplementedProjects()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ImplementedProjects();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getInitModules <em>Init Modules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Init Modules</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getInitModules()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_InitModules();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getExecModule <em>Exec Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exec Module</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getExecModule()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ExecModule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getOutputPath <em>Output Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Output Path</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getOutputPath()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_OutputPath();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getSourceContainers <em>Source Containers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source Containers</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getSourceContainers()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_SourceContainers();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getModuleFilters <em>Module Filters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Module Filters</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getModuleFilters()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ModuleFilters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getTestedProjects <em>Tested Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tested Projects</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getTestedProjects()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_TestedProjects();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getModuleLoader <em>Module Loader</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Loader</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getModuleLoader()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_ModuleLoader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getDefinesPackage <em>Defines Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Defines Package</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getDefinesPackage()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_DefinesPackage();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#isHasNestedNodeModulesFolder <em>Has Nested Node Modules Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Nested Node Modules Folder</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#isHasNestedNodeModulesFolder()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_HasNestedNodeModulesFolder();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#isHasN4JSNature <em>Has N4JS Nature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has N4JS Nature</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#isHasN4JSNature()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_HasN4JSNature();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDescription#isYarnWorkspaceRoot <em>Yarn Workspace Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Yarn Workspace Root</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#isYarnWorkspaceRoot()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_YarnWorkspaceRoot();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getWorkspaces <em>Workspaces</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Workspaces</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescription#getWorkspaces()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_Workspaces();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.projectDescription.SourceContainerDescription <em>Source Container Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Container Description</em>'.
	 * @see org.eclipse.n4js.projectDescription.SourceContainerDescription
	 * @generated
	 */
	EClass getSourceContainerDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.SourceContainerDescription#getSourceContainerType <em>Source Container Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Container Type</em>'.
	 * @see org.eclipse.n4js.projectDescription.SourceContainerDescription#getSourceContainerType()
	 * @see #getSourceContainerDescription()
	 * @generated
	 */
	EAttribute getSourceContainerDescription_SourceContainerType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.projectDescription.SourceContainerDescription#getPaths <em>Paths</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Paths</em>'.
	 * @see org.eclipse.n4js.projectDescription.SourceContainerDescription#getPaths()
	 * @see #getSourceContainerDescription()
	 * @generated
	 */
	EAttribute getSourceContainerDescription_Paths();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.projectDescription.ProjectReference <em>Project Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Reference</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectReference
	 * @generated
	 */
	EClass getProjectReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectReference#getProjectName <em>Project Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectReference#getProjectName()
	 * @see #getProjectReference()
	 * @generated
	 */
	EAttribute getProjectReference_ProjectName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.projectDescription.ProjectDependency <em>Project Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Dependency</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDependency
	 * @generated
	 */
	EClass getProjectDependency();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDependency#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDependency#getType()
	 * @see #getProjectDependency()
	 * @generated
	 */
	EAttribute getProjectDependency_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ProjectDependency#getVersionRequirementString <em>Version Requirement String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version Requirement String</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDependency#getVersionRequirementString()
	 * @see #getProjectDependency()
	 * @generated
	 */
	EAttribute getProjectDependency_VersionRequirementString();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.projectDescription.ProjectDependency#getVersionRequirement <em>Version Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Version Requirement</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectDependency#getVersionRequirement()
	 * @see #getProjectDependency()
	 * @generated
	 */
	EReference getProjectDependency_VersionRequirement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.projectDescription.ModuleFilter <em>Module Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Module Filter</em>'.
	 * @see org.eclipse.n4js.projectDescription.ModuleFilter
	 * @generated
	 */
	EClass getModuleFilter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ModuleFilter#getModuleFilterType <em>Module Filter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Filter Type</em>'.
	 * @see org.eclipse.n4js.projectDescription.ModuleFilter#getModuleFilterType()
	 * @see #getModuleFilter()
	 * @generated
	 */
	EAttribute getModuleFilter_ModuleFilterType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.projectDescription.ModuleFilter#getModuleSpecifiers <em>Module Specifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Module Specifiers</em>'.
	 * @see org.eclipse.n4js.projectDescription.ModuleFilter#getModuleSpecifiers()
	 * @see #getModuleFilter()
	 * @generated
	 */
	EReference getModuleFilter_ModuleSpecifiers();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.projectDescription.ModuleFilterSpecifier <em>Module Filter Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Module Filter Specifier</em>'.
	 * @see org.eclipse.n4js.projectDescription.ModuleFilterSpecifier
	 * @generated
	 */
	EClass getModuleFilterSpecifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ModuleFilterSpecifier#getModuleSpecifierWithWildcard <em>Module Specifier With Wildcard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Specifier With Wildcard</em>'.
	 * @see org.eclipse.n4js.projectDescription.ModuleFilterSpecifier#getModuleSpecifierWithWildcard()
	 * @see #getModuleFilterSpecifier()
	 * @generated
	 */
	EAttribute getModuleFilterSpecifier_ModuleSpecifierWithWildcard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.ModuleFilterSpecifier#getSourcePath <em>Source Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Path</em>'.
	 * @see org.eclipse.n4js.projectDescription.ModuleFilterSpecifier#getSourcePath()
	 * @see #getModuleFilterSpecifier()
	 * @generated
	 */
	EAttribute getModuleFilterSpecifier_SourcePath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.projectDescription.BootstrapModule <em>Bootstrap Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bootstrap Module</em>'.
	 * @see org.eclipse.n4js.projectDescription.BootstrapModule
	 * @generated
	 */
	EClass getBootstrapModule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.projectDescription.BootstrapModule#getModuleSpecifier <em>Module Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Specifier</em>'.
	 * @see org.eclipse.n4js.projectDescription.BootstrapModule#getModuleSpecifier()
	 * @see #getBootstrapModule()
	 * @generated
	 */
	EAttribute getBootstrapModule_ModuleSpecifier();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.projectDescription.ProjectType <em>Project Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Project Type</em>'.
	 * @see org.eclipse.n4js.projectDescription.ProjectType
	 * @generated
	 */
	EEnum getProjectType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.projectDescription.SourceContainerType <em>Source Container Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Source Container Type</em>'.
	 * @see org.eclipse.n4js.projectDescription.SourceContainerType
	 * @generated
	 */
	EEnum getSourceContainerType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.projectDescription.ModuleFilterType <em>Module Filter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Module Filter Type</em>'.
	 * @see org.eclipse.n4js.projectDescription.ModuleFilterType
	 * @generated
	 */
	EEnum getModuleFilterType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.projectDescription.ModuleLoader <em>Module Loader</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Module Loader</em>'.
	 * @see org.eclipse.n4js.projectDescription.ModuleLoader
	 * @generated
	 */
	EEnum getModuleLoader();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.projectDescription.DependencyType <em>Dependency Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Dependency Type</em>'.
	 * @see org.eclipse.n4js.projectDescription.DependencyType
	 * @generated
	 */
	EEnum getDependencyType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProjectDescriptionFactory getProjectDescriptionFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl <em>Project Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getProjectDescription()
		 * @generated
		 */
		EClass PROJECT_DESCRIPTION = eINSTANCE.getProjectDescription();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__PROJECT_NAME = eINSTANCE.getProjectDescription_ProjectName();

		/**
		 * The meta object literal for the '<em><b>Vendor Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__VENDOR_ID = eINSTANCE.getProjectDescription_VendorId();

		/**
		 * The meta object literal for the '<em><b>Vendor Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__VENDOR_NAME = eINSTANCE.getProjectDescription_VendorName();

		/**
		 * The meta object literal for the '<em><b>Project Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__PROJECT_VERSION = eINSTANCE.getProjectDescription_ProjectVersion();

		/**
		 * The meta object literal for the '<em><b>Project Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__PROJECT_TYPE = eINSTANCE.getProjectDescription_ProjectType();

		/**
		 * The meta object literal for the '<em><b>Main Module</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__MAIN_MODULE = eINSTANCE.getProjectDescription_MainModule();

		/**
		 * The meta object literal for the '<em><b>Extended Runtime Environment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT = eINSTANCE.getProjectDescription_ExtendedRuntimeEnvironment();

		/**
		 * The meta object literal for the '<em><b>Provided Runtime Libraries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES = eINSTANCE.getProjectDescription_ProvidedRuntimeLibraries();

		/**
		 * The meta object literal for the '<em><b>Required Runtime Libraries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES = eINSTANCE.getProjectDescription_RequiredRuntimeLibraries();

		/**
		 * The meta object literal for the '<em><b>Project Dependencies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES = eINSTANCE.getProjectDescription_ProjectDependencies();

		/**
		 * The meta object literal for the '<em><b>Implementation Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__IMPLEMENTATION_ID = eINSTANCE.getProjectDescription_ImplementationId();

		/**
		 * The meta object literal for the '<em><b>Implemented Projects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS = eINSTANCE.getProjectDescription_ImplementedProjects();

		/**
		 * The meta object literal for the '<em><b>Init Modules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__INIT_MODULES = eINSTANCE.getProjectDescription_InitModules();

		/**
		 * The meta object literal for the '<em><b>Exec Module</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__EXEC_MODULE = eINSTANCE.getProjectDescription_ExecModule();

		/**
		 * The meta object literal for the '<em><b>Output Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__OUTPUT_PATH = eINSTANCE.getProjectDescription_OutputPath();

		/**
		 * The meta object literal for the '<em><b>Source Containers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__SOURCE_CONTAINERS = eINSTANCE.getProjectDescription_SourceContainers();

		/**
		 * The meta object literal for the '<em><b>Module Filters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__MODULE_FILTERS = eINSTANCE.getProjectDescription_ModuleFilters();

		/**
		 * The meta object literal for the '<em><b>Tested Projects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DESCRIPTION__TESTED_PROJECTS = eINSTANCE.getProjectDescription_TestedProjects();

		/**
		 * The meta object literal for the '<em><b>Module Loader</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__MODULE_LOADER = eINSTANCE.getProjectDescription_ModuleLoader();

		/**
		 * The meta object literal for the '<em><b>Defines Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__DEFINES_PACKAGE = eINSTANCE.getProjectDescription_DefinesPackage();

		/**
		 * The meta object literal for the '<em><b>Has Nested Node Modules Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__HAS_NESTED_NODE_MODULES_FOLDER = eINSTANCE.getProjectDescription_HasNestedNodeModulesFolder();

		/**
		 * The meta object literal for the '<em><b>Has N4JS Nature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__HAS_N4JS_NATURE = eINSTANCE.getProjectDescription_HasN4JSNature();

		/**
		 * The meta object literal for the '<em><b>Yarn Workspace Root</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__YARN_WORKSPACE_ROOT = eINSTANCE.getProjectDescription_YarnWorkspaceRoot();

		/**
		 * The meta object literal for the '<em><b>Workspaces</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__WORKSPACES = eINSTANCE.getProjectDescription_Workspaces();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.impl.SourceContainerDescriptionImpl <em>Source Container Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.impl.SourceContainerDescriptionImpl
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getSourceContainerDescription()
		 * @generated
		 */
		EClass SOURCE_CONTAINER_DESCRIPTION = eINSTANCE.getSourceContainerDescription();

		/**
		 * The meta object literal for the '<em><b>Source Container Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE = eINSTANCE.getSourceContainerDescription_SourceContainerType();

		/**
		 * The meta object literal for the '<em><b>Paths</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_CONTAINER_DESCRIPTION__PATHS = eINSTANCE.getSourceContainerDescription_Paths();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.impl.ProjectReferenceImpl <em>Project Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectReferenceImpl
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getProjectReference()
		 * @generated
		 */
		EClass PROJECT_REFERENCE = eINSTANCE.getProjectReference();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_REFERENCE__PROJECT_NAME = eINSTANCE.getProjectReference_ProjectName();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.impl.ProjectDependencyImpl <em>Project Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDependencyImpl
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getProjectDependency()
		 * @generated
		 */
		EClass PROJECT_DEPENDENCY = eINSTANCE.getProjectDependency();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DEPENDENCY__TYPE = eINSTANCE.getProjectDependency_Type();

		/**
		 * The meta object literal for the '<em><b>Version Requirement String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DEPENDENCY__VERSION_REQUIREMENT_STRING = eINSTANCE.getProjectDependency_VersionRequirementString();

		/**
		 * The meta object literal for the '<em><b>Version Requirement</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DEPENDENCY__VERSION_REQUIREMENT = eINSTANCE.getProjectDependency_VersionRequirement();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.impl.ModuleFilterImpl <em>Module Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.impl.ModuleFilterImpl
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getModuleFilter()
		 * @generated
		 */
		EClass MODULE_FILTER = eINSTANCE.getModuleFilter();

		/**
		 * The meta object literal for the '<em><b>Module Filter Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODULE_FILTER__MODULE_FILTER_TYPE = eINSTANCE.getModuleFilter_ModuleFilterType();

		/**
		 * The meta object literal for the '<em><b>Module Specifiers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODULE_FILTER__MODULE_SPECIFIERS = eINSTANCE.getModuleFilter_ModuleSpecifiers();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.impl.ModuleFilterSpecifierImpl <em>Module Filter Specifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.impl.ModuleFilterSpecifierImpl
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getModuleFilterSpecifier()
		 * @generated
		 */
		EClass MODULE_FILTER_SPECIFIER = eINSTANCE.getModuleFilterSpecifier();

		/**
		 * The meta object literal for the '<em><b>Module Specifier With Wildcard</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD = eINSTANCE.getModuleFilterSpecifier_ModuleSpecifierWithWildcard();

		/**
		 * The meta object literal for the '<em><b>Source Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODULE_FILTER_SPECIFIER__SOURCE_PATH = eINSTANCE.getModuleFilterSpecifier_SourcePath();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.impl.BootstrapModuleImpl <em>Bootstrap Module</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.impl.BootstrapModuleImpl
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getBootstrapModule()
		 * @generated
		 */
		EClass BOOTSTRAP_MODULE = eINSTANCE.getBootstrapModule();

		/**
		 * The meta object literal for the '<em><b>Module Specifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOTSTRAP_MODULE__MODULE_SPECIFIER = eINSTANCE.getBootstrapModule_ModuleSpecifier();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.ProjectType <em>Project Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.ProjectType
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getProjectType()
		 * @generated
		 */
		EEnum PROJECT_TYPE = eINSTANCE.getProjectType();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.SourceContainerType <em>Source Container Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.SourceContainerType
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getSourceContainerType()
		 * @generated
		 */
		EEnum SOURCE_CONTAINER_TYPE = eINSTANCE.getSourceContainerType();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.ModuleFilterType <em>Module Filter Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.ModuleFilterType
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getModuleFilterType()
		 * @generated
		 */
		EEnum MODULE_FILTER_TYPE = eINSTANCE.getModuleFilterType();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.ModuleLoader <em>Module Loader</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.ModuleLoader
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getModuleLoader()
		 * @generated
		 */
		EEnum MODULE_LOADER = eINSTANCE.getModuleLoader();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.projectDescription.DependencyType <em>Dependency Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.projectDescription.DependencyType
		 * @see org.eclipse.n4js.projectDescription.impl.ProjectDescriptionPackageImpl#getDependencyType()
		 * @generated
		 */
		EEnum DEPENDENCY_TYPE = eINSTANCE.getDependencyType();

	}

} //ProjectDescriptionPackage
