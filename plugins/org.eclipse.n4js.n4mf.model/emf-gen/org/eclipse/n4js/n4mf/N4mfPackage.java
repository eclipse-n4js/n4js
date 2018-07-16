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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
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
 * @see org.eclipse.n4js.n4mf.N4mfFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel fileExtensions='n4mf' modelDirectory='/org.eclipse.n4js.n4mf.model/emf-gen' forceOverwrite='true' updateClasspath='false' complianceLevel='8.0' copyrightFields='false' copyrightText='Copyright (c) 2016 NumberFour AG.\nAll rights reserved. This program and the accompanying materials\nare made available under the terms of the Eclipse Public License v1.0\nwhich accompanies this distribution, and is available at\nhttp://www.eclipse.org/legal/epl-v10.html\n\nContributors:\n  NumberFour AG - Initial API and implementation' language='' basePackage='org.eclipse.n4js'"
 * @generated
 */
public interface N4mfPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "n4mf";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/n4js/n4mf/N4MF";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "n4mf";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	N4mfPackage eINSTANCE = org.eclipse.n4js.n4mf.impl.N4mfPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl <em>Project Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectDescription()
	 * @generated
	 */
	int PROJECT_DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Project Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__PROJECT_ID = 0;

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
	 * The feature id for the '<em><b>Output Path Raw</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__OUTPUT_PATH_RAW = 14;

	/**
	 * The feature id for the '<em><b>Library Paths Raw</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__LIBRARY_PATHS_RAW = 15;

	/**
	 * The feature id for the '<em><b>Resource Paths Raw</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__RESOURCE_PATHS_RAW = 16;

	/**
	 * The feature id for the '<em><b>Source Containers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__SOURCE_CONTAINERS = 17;

	/**
	 * The feature id for the '<em><b>Module Filters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__MODULE_FILTERS = 18;

	/**
	 * The feature id for the '<em><b>Tested Projects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__TESTED_PROJECTS = 19;

	/**
	 * The feature id for the '<em><b>Module Loader</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__MODULE_LOADER = 20;

	/**
	 * The feature id for the '<em><b>Has Nested Node Modules Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__HAS_NESTED_NODE_MODULES_FOLDER = 21;

	/**
	 * The feature id for the '<em><b>Has N4JS Nature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION__HAS_N4JS_NATURE = 22;

	/**
	 * The number of structural features of the '<em>Project Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION_FEATURE_COUNT = 23;

	/**
	 * The operation id for the '<em>Get Output Path</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION___GET_OUTPUT_PATH = 0;

	/**
	 * The operation id for the '<em>Set Output Path</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION___SET_OUTPUT_PATH__STRING = 1;

	/**
	 * The operation id for the '<em>Get Library Paths</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION___GET_LIBRARY_PATHS = 2;

	/**
	 * The operation id for the '<em>Get Resource Paths</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION___GET_RESOURCE_PATHS = 3;

	/**
	 * The number of operations of the '<em>Project Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DESCRIPTION_OPERATION_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.impl.DeclaredVersionImpl <em>Declared Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.impl.DeclaredVersionImpl
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getDeclaredVersion()
	 * @generated
	 */
	int DECLARED_VERSION = 1;

	/**
	 * The feature id for the '<em><b>Major</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECLARED_VERSION__MAJOR = 0;

	/**
	 * The feature id for the '<em><b>Minor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECLARED_VERSION__MINOR = 1;

	/**
	 * The feature id for the '<em><b>Micro</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECLARED_VERSION__MICRO = 2;

	/**
	 * The feature id for the '<em><b>Qualifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECLARED_VERSION__QUALIFIER = 3;

	/**
	 * The feature id for the '<em><b>Build Meta Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECLARED_VERSION__BUILD_META_DATA = 4;

	/**
	 * The number of structural features of the '<em>Declared Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECLARED_VERSION_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Declared Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECLARED_VERSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.impl.SourceContainerDescriptionImpl <em>Source Container Description</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.impl.SourceContainerDescriptionImpl
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getSourceContainerDescription()
	 * @generated
	 */
	int SOURCE_CONTAINER_DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Source Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Paths Raw</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW = 1;

	/**
	 * The number of structural features of the '<em>Source Container Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Compare By Fragment Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION___COMPARE_BY_FRAGMENT_TYPE__SOURCECONTAINERDESCRIPTION = 0;

	/**
	 * The operation id for the '<em>Get Paths</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION___GET_PATHS = 1;

	/**
	 * The number of operations of the '<em>Source Container Description</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_CONTAINER_DESCRIPTION_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.impl.ModuleFilterImpl <em>Module Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.impl.ModuleFilterImpl
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getModuleFilter()
	 * @generated
	 */
	int MODULE_FILTER = 3;

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
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.impl.BootstrapModuleImpl <em>Bootstrap Module</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.impl.BootstrapModuleImpl
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getBootstrapModule()
	 * @generated
	 */
	int BOOTSTRAP_MODULE = 4;

	/**
	 * The feature id for the '<em><b>Module Specifier With Wildcard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOTSTRAP_MODULE__MODULE_SPECIFIER_WITH_WILDCARD = 0;

	/**
	 * The feature id for the '<em><b>Source Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOTSTRAP_MODULE__SOURCE_PATH = 1;

	/**
	 * The number of structural features of the '<em>Bootstrap Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOTSTRAP_MODULE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Bootstrap Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOTSTRAP_MODULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.impl.ProjectReferenceImpl <em>Project Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.impl.ProjectReferenceImpl
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectReference()
	 * @generated
	 */
	int PROJECT_REFERENCE = 5;

	/**
	 * The feature id for the '<em><b>Project Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_REFERENCE__PROJECT_ID = 0;

	/**
	 * The feature id for the '<em><b>Declared Vendor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_REFERENCE__DECLARED_VENDOR_ID = 1;

	/**
	 * The number of structural features of the '<em>Project Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_REFERENCE_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Get Vendor Id</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_REFERENCE___GET_VENDOR_ID = 0;

	/**
	 * The operation id for the '<em>Get Scope</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_REFERENCE___GET_SCOPE = 1;

	/**
	 * The number of operations of the '<em>Project Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_REFERENCE_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.impl.ModuleFilterSpecifierImpl <em>Module Filter Specifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.impl.ModuleFilterSpecifierImpl
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getModuleFilterSpecifier()
	 * @generated
	 */
	int MODULE_FILTER_SPECIFIER = 6;

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
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.impl.ProjectDependencyImpl <em>Project Dependency</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.impl.ProjectDependencyImpl
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectDependency()
	 * @generated
	 */
	int PROJECT_DEPENDENCY = 7;

	/**
	 * The feature id for the '<em><b>Project Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY__PROJECT_ID = PROJECT_REFERENCE__PROJECT_ID;

	/**
	 * The feature id for the '<em><b>Declared Vendor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY__DECLARED_VENDOR_ID = PROJECT_REFERENCE__DECLARED_VENDOR_ID;

	/**
	 * The feature id for the '<em><b>Version Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY__VERSION_CONSTRAINT = PROJECT_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Declared Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY__DECLARED_SCOPE = PROJECT_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Project Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY_FEATURE_COUNT = PROJECT_REFERENCE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Get Vendor Id</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY___GET_VENDOR_ID = PROJECT_REFERENCE___GET_VENDOR_ID;

	/**
	 * The operation id for the '<em>Get Scope</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY___GET_SCOPE = PROJECT_REFERENCE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Project Dependency</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROJECT_DEPENDENCY_OPERATION_COUNT = PROJECT_REFERENCE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.impl.VersionConstraintImpl <em>Version Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.impl.VersionConstraintImpl
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getVersionConstraint()
	 * @generated
	 */
	int VERSION_CONSTRAINT = 8;

	/**
	 * The feature id for the '<em><b>Excl Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONSTRAINT__EXCL_LOWER_BOUND = 0;

	/**
	 * The feature id for the '<em><b>Lower Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONSTRAINT__LOWER_VERSION = 1;

	/**
	 * The feature id for the '<em><b>Excl Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONSTRAINT__EXCL_UPPER_BOUND = 2;

	/**
	 * The feature id for the '<em><b>Upper Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONSTRAINT__UPPER_VERSION = 3;

	/**
	 * The number of structural features of the '<em>Version Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONSTRAINT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Version Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.ProjectType <em>Project Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.ProjectType
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectType()
	 * @generated
	 */
	int PROJECT_TYPE = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.SourceContainerType <em>Source Container Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.SourceContainerType
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getSourceContainerType()
	 * @generated
	 */
	int SOURCE_CONTAINER_TYPE = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.ModuleFilterType <em>Module Filter Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.ModuleFilterType
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getModuleFilterType()
	 * @generated
	 */
	int MODULE_FILTER_TYPE = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.ProjectDependencyScope <em>Project Dependency Scope</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.ProjectDependencyScope
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectDependencyScope()
	 * @generated
	 */
	int PROJECT_DEPENDENCY_SCOPE = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.n4js.n4mf.ModuleLoader <em>Module Loader</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.n4js.n4mf.ModuleLoader
	 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getModuleLoader()
	 * @generated
	 */
	int MODULE_LOADER = 13;


	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4mf.ProjectDescription <em>Project Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Description</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription
	 * @generated
	 */
	EClass getProjectDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectId <em>Project Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Id</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getProjectId()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_ProjectId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#getVendorId <em>Vendor Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor Id</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getVendorId()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_VendorId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#getVendorName <em>Vendor Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor Name</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getVendorName()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_VendorName();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectVersion <em>Project Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Project Version</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getProjectVersion()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ProjectVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectType <em>Project Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Type</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getProjectType()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_ProjectType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#getMainModule <em>Main Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Main Module</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getMainModule()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_MainModule();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4mf.ProjectDescription#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extended Runtime Environment</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getExtendedRuntimeEnvironment()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ExtendedRuntimeEnvironment();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Provided Runtime Libraries</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getProvidedRuntimeLibraries()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ProvidedRuntimeLibraries();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getRequiredRuntimeLibraries <em>Required Runtime Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Required Runtime Libraries</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getRequiredRuntimeLibraries()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_RequiredRuntimeLibraries();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectDependencies <em>Project Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Project Dependencies</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getProjectDependencies()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ProjectDependencies();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#getImplementationId <em>Implementation Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation Id</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getImplementationId()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_ImplementationId();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getImplementedProjects <em>Implemented Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Implemented Projects</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getImplementedProjects()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ImplementedProjects();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getInitModules <em>Init Modules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Init Modules</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getInitModules()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_InitModules();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4mf.ProjectDescription#getExecModule <em>Exec Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exec Module</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getExecModule()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ExecModule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#getOutputPathRaw <em>Output Path Raw</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Output Path Raw</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getOutputPathRaw()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_OutputPathRaw();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getLibraryPathsRaw <em>Library Paths Raw</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Library Paths Raw</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getLibraryPathsRaw()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_LibraryPathsRaw();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getResourcePathsRaw <em>Resource Paths Raw</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Resource Paths Raw</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getResourcePathsRaw()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_ResourcePathsRaw();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getSourceContainers <em>Source Containers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Source Containers</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getSourceContainers()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_SourceContainers();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getModuleFilters <em>Module Filters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Module Filters</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getModuleFilters()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_ModuleFilters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4mf.ProjectDescription#getTestedProjects <em>Tested Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tested Projects</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getTestedProjects()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EReference getProjectDescription_TestedProjects();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#getModuleLoader <em>Module Loader</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Loader</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getModuleLoader()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_ModuleLoader();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#isHasNestedNodeModulesFolder <em>Has Nested Node Modules Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has Nested Node Modules Folder</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#isHasNestedNodeModulesFolder()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_HasNestedNodeModulesFolder();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDescription#isHasN4JSNature <em>Has N4JS Nature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Has N4JS Nature</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#isHasN4JSNature()
	 * @see #getProjectDescription()
	 * @generated
	 */
	EAttribute getProjectDescription_HasN4JSNature();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getOutputPath() <em>Get Output Path</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Output Path</em>' operation.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getOutputPath()
	 * @generated
	 */
	EOperation getProjectDescription__GetOutputPath();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4mf.ProjectDescription#setOutputPath(java.lang.String) <em>Set Output Path</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Set Output Path</em>' operation.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#setOutputPath(java.lang.String)
	 * @generated
	 */
	EOperation getProjectDescription__SetOutputPath__String();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getLibraryPaths() <em>Get Library Paths</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Library Paths</em>' operation.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getLibraryPaths()
	 * @generated
	 */
	EOperation getProjectDescription__GetLibraryPaths();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getResourcePaths() <em>Get Resource Paths</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Resource Paths</em>' operation.
	 * @see org.eclipse.n4js.n4mf.ProjectDescription#getResourcePaths()
	 * @generated
	 */
	EOperation getProjectDescription__GetResourcePaths();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4mf.DeclaredVersion <em>Declared Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Declared Version</em>'.
	 * @see org.eclipse.n4js.n4mf.DeclaredVersion
	 * @generated
	 */
	EClass getDeclaredVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getMajor <em>Major</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Major</em>'.
	 * @see org.eclipse.n4js.n4mf.DeclaredVersion#getMajor()
	 * @see #getDeclaredVersion()
	 * @generated
	 */
	EAttribute getDeclaredVersion_Major();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getMinor <em>Minor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Minor</em>'.
	 * @see org.eclipse.n4js.n4mf.DeclaredVersion#getMinor()
	 * @see #getDeclaredVersion()
	 * @generated
	 */
	EAttribute getDeclaredVersion_Minor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getMicro <em>Micro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Micro</em>'.
	 * @see org.eclipse.n4js.n4mf.DeclaredVersion#getMicro()
	 * @see #getDeclaredVersion()
	 * @generated
	 */
	EAttribute getDeclaredVersion_Micro();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getQualifier <em>Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualifier</em>'.
	 * @see org.eclipse.n4js.n4mf.DeclaredVersion#getQualifier()
	 * @see #getDeclaredVersion()
	 * @generated
	 */
	EAttribute getDeclaredVersion_Qualifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.DeclaredVersion#getBuildMetaData <em>Build Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Build Meta Data</em>'.
	 * @see org.eclipse.n4js.n4mf.DeclaredVersion#getBuildMetaData()
	 * @see #getDeclaredVersion()
	 * @generated
	 */
	EAttribute getDeclaredVersion_BuildMetaData();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4mf.SourceContainerDescription <em>Source Container Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source Container Description</em>'.
	 * @see org.eclipse.n4js.n4mf.SourceContainerDescription
	 * @generated
	 */
	EClass getSourceContainerDescription();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.SourceContainerDescription#getSourceContainerType <em>Source Container Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Container Type</em>'.
	 * @see org.eclipse.n4js.n4mf.SourceContainerDescription#getSourceContainerType()
	 * @see #getSourceContainerDescription()
	 * @generated
	 */
	EAttribute getSourceContainerDescription_SourceContainerType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.n4js.n4mf.SourceContainerDescription#getPathsRaw <em>Paths Raw</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Paths Raw</em>'.
	 * @see org.eclipse.n4js.n4mf.SourceContainerDescription#getPathsRaw()
	 * @see #getSourceContainerDescription()
	 * @generated
	 */
	EAttribute getSourceContainerDescription_PathsRaw();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4mf.SourceContainerDescription#compareByFragmentType(org.eclipse.n4js.n4mf.SourceContainerDescription) <em>Compare By Fragment Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Compare By Fragment Type</em>' operation.
	 * @see org.eclipse.n4js.n4mf.SourceContainerDescription#compareByFragmentType(org.eclipse.n4js.n4mf.SourceContainerDescription)
	 * @generated
	 */
	EOperation getSourceContainerDescription__CompareByFragmentType__SourceContainerDescription();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4mf.SourceContainerDescription#getPaths() <em>Get Paths</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Paths</em>' operation.
	 * @see org.eclipse.n4js.n4mf.SourceContainerDescription#getPaths()
	 * @generated
	 */
	EOperation getSourceContainerDescription__GetPaths();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4mf.ModuleFilter <em>Module Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Module Filter</em>'.
	 * @see org.eclipse.n4js.n4mf.ModuleFilter
	 * @generated
	 */
	EClass getModuleFilter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ModuleFilter#getModuleFilterType <em>Module Filter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Filter Type</em>'.
	 * @see org.eclipse.n4js.n4mf.ModuleFilter#getModuleFilterType()
	 * @see #getModuleFilter()
	 * @generated
	 */
	EAttribute getModuleFilter_ModuleFilterType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.n4js.n4mf.ModuleFilter#getModuleSpecifiers <em>Module Specifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Module Specifiers</em>'.
	 * @see org.eclipse.n4js.n4mf.ModuleFilter#getModuleSpecifiers()
	 * @see #getModuleFilter()
	 * @generated
	 */
	EReference getModuleFilter_ModuleSpecifiers();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4mf.BootstrapModule <em>Bootstrap Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bootstrap Module</em>'.
	 * @see org.eclipse.n4js.n4mf.BootstrapModule
	 * @generated
	 */
	EClass getBootstrapModule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.BootstrapModule#getModuleSpecifierWithWildcard <em>Module Specifier With Wildcard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Specifier With Wildcard</em>'.
	 * @see org.eclipse.n4js.n4mf.BootstrapModule#getModuleSpecifierWithWildcard()
	 * @see #getBootstrapModule()
	 * @generated
	 */
	EAttribute getBootstrapModule_ModuleSpecifierWithWildcard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.BootstrapModule#getSourcePath <em>Source Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Path</em>'.
	 * @see org.eclipse.n4js.n4mf.BootstrapModule#getSourcePath()
	 * @see #getBootstrapModule()
	 * @generated
	 */
	EAttribute getBootstrapModule_SourcePath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4mf.ProjectReference <em>Project Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Reference</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectReference
	 * @generated
	 */
	EClass getProjectReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectReference#getProjectId <em>Project Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Id</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectReference#getProjectId()
	 * @see #getProjectReference()
	 * @generated
	 */
	EAttribute getProjectReference_ProjectId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectReference#getDeclaredVendorId <em>Declared Vendor Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared Vendor Id</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectReference#getDeclaredVendorId()
	 * @see #getProjectReference()
	 * @generated
	 */
	EAttribute getProjectReference_DeclaredVendorId();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4mf.ProjectReference#getVendorId() <em>Get Vendor Id</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Vendor Id</em>' operation.
	 * @see org.eclipse.n4js.n4mf.ProjectReference#getVendorId()
	 * @generated
	 */
	EOperation getProjectReference__GetVendorId();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4mf.ProjectReference#getScope() <em>Get Scope</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Scope</em>' operation.
	 * @see org.eclipse.n4js.n4mf.ProjectReference#getScope()
	 * @generated
	 */
	EOperation getProjectReference__GetScope();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4mf.ModuleFilterSpecifier <em>Module Filter Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Module Filter Specifier</em>'.
	 * @see org.eclipse.n4js.n4mf.ModuleFilterSpecifier
	 * @generated
	 */
	EClass getModuleFilterSpecifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ModuleFilterSpecifier#getModuleSpecifierWithWildcard <em>Module Specifier With Wildcard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Module Specifier With Wildcard</em>'.
	 * @see org.eclipse.n4js.n4mf.ModuleFilterSpecifier#getModuleSpecifierWithWildcard()
	 * @see #getModuleFilterSpecifier()
	 * @generated
	 */
	EAttribute getModuleFilterSpecifier_ModuleSpecifierWithWildcard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ModuleFilterSpecifier#getSourcePath <em>Source Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Path</em>'.
	 * @see org.eclipse.n4js.n4mf.ModuleFilterSpecifier#getSourcePath()
	 * @see #getModuleFilterSpecifier()
	 * @generated
	 */
	EAttribute getModuleFilterSpecifier_SourcePath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4mf.ProjectDependency <em>Project Dependency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Project Dependency</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDependency
	 * @generated
	 */
	EClass getProjectDependency();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4mf.ProjectDependency#getVersionConstraint <em>Version Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Version Constraint</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDependency#getVersionConstraint()
	 * @see #getProjectDependency()
	 * @generated
	 */
	EReference getProjectDependency_VersionConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.ProjectDependency#getDeclaredScope <em>Declared Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Declared Scope</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDependency#getDeclaredScope()
	 * @see #getProjectDependency()
	 * @generated
	 */
	EAttribute getProjectDependency_DeclaredScope();

	/**
	 * Returns the meta object for the '{@link org.eclipse.n4js.n4mf.ProjectDependency#getScope() <em>Get Scope</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Scope</em>' operation.
	 * @see org.eclipse.n4js.n4mf.ProjectDependency#getScope()
	 * @generated
	 */
	EOperation getProjectDependency__GetScope();

	/**
	 * Returns the meta object for class '{@link org.eclipse.n4js.n4mf.VersionConstraint <em>Version Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Constraint</em>'.
	 * @see org.eclipse.n4js.n4mf.VersionConstraint
	 * @generated
	 */
	EClass getVersionConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.VersionConstraint#isExclLowerBound <em>Excl Lower Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Excl Lower Bound</em>'.
	 * @see org.eclipse.n4js.n4mf.VersionConstraint#isExclLowerBound()
	 * @see #getVersionConstraint()
	 * @generated
	 */
	EAttribute getVersionConstraint_ExclLowerBound();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4mf.VersionConstraint#getLowerVersion <em>Lower Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lower Version</em>'.
	 * @see org.eclipse.n4js.n4mf.VersionConstraint#getLowerVersion()
	 * @see #getVersionConstraint()
	 * @generated
	 */
	EReference getVersionConstraint_LowerVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.n4js.n4mf.VersionConstraint#isExclUpperBound <em>Excl Upper Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Excl Upper Bound</em>'.
	 * @see org.eclipse.n4js.n4mf.VersionConstraint#isExclUpperBound()
	 * @see #getVersionConstraint()
	 * @generated
	 */
	EAttribute getVersionConstraint_ExclUpperBound();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.n4js.n4mf.VersionConstraint#getUpperVersion <em>Upper Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Upper Version</em>'.
	 * @see org.eclipse.n4js.n4mf.VersionConstraint#getUpperVersion()
	 * @see #getVersionConstraint()
	 * @generated
	 */
	EReference getVersionConstraint_UpperVersion();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4mf.ProjectType <em>Project Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Project Type</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectType
	 * @generated
	 */
	EEnum getProjectType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4mf.SourceContainerType <em>Source Container Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Source Container Type</em>'.
	 * @see org.eclipse.n4js.n4mf.SourceContainerType
	 * @generated
	 */
	EEnum getSourceContainerType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4mf.ModuleFilterType <em>Module Filter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Module Filter Type</em>'.
	 * @see org.eclipse.n4js.n4mf.ModuleFilterType
	 * @generated
	 */
	EEnum getModuleFilterType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4mf.ProjectDependencyScope <em>Project Dependency Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Project Dependency Scope</em>'.
	 * @see org.eclipse.n4js.n4mf.ProjectDependencyScope
	 * @generated
	 */
	EEnum getProjectDependencyScope();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.n4js.n4mf.ModuleLoader <em>Module Loader</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Module Loader</em>'.
	 * @see org.eclipse.n4js.n4mf.ModuleLoader
	 * @generated
	 */
	EEnum getModuleLoader();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	N4mfFactory getN4mfFactory();

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
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl <em>Project Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectDescription()
		 * @generated
		 */
		EClass PROJECT_DESCRIPTION = eINSTANCE.getProjectDescription();

		/**
		 * The meta object literal for the '<em><b>Project Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__PROJECT_ID = eINSTANCE.getProjectDescription_ProjectId();

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
		 * The meta object literal for the '<em><b>Output Path Raw</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__OUTPUT_PATH_RAW = eINSTANCE.getProjectDescription_OutputPathRaw();

		/**
		 * The meta object literal for the '<em><b>Library Paths Raw</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__LIBRARY_PATHS_RAW = eINSTANCE.getProjectDescription_LibraryPathsRaw();

		/**
		 * The meta object literal for the '<em><b>Resource Paths Raw</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DESCRIPTION__RESOURCE_PATHS_RAW = eINSTANCE.getProjectDescription_ResourcePathsRaw();

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
		 * The meta object literal for the '<em><b>Get Output Path</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROJECT_DESCRIPTION___GET_OUTPUT_PATH = eINSTANCE.getProjectDescription__GetOutputPath();

		/**
		 * The meta object literal for the '<em><b>Set Output Path</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROJECT_DESCRIPTION___SET_OUTPUT_PATH__STRING = eINSTANCE.getProjectDescription__SetOutputPath__String();

		/**
		 * The meta object literal for the '<em><b>Get Library Paths</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROJECT_DESCRIPTION___GET_LIBRARY_PATHS = eINSTANCE.getProjectDescription__GetLibraryPaths();

		/**
		 * The meta object literal for the '<em><b>Get Resource Paths</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROJECT_DESCRIPTION___GET_RESOURCE_PATHS = eINSTANCE.getProjectDescription__GetResourcePaths();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.impl.DeclaredVersionImpl <em>Declared Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.impl.DeclaredVersionImpl
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getDeclaredVersion()
		 * @generated
		 */
		EClass DECLARED_VERSION = eINSTANCE.getDeclaredVersion();

		/**
		 * The meta object literal for the '<em><b>Major</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECLARED_VERSION__MAJOR = eINSTANCE.getDeclaredVersion_Major();

		/**
		 * The meta object literal for the '<em><b>Minor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECLARED_VERSION__MINOR = eINSTANCE.getDeclaredVersion_Minor();

		/**
		 * The meta object literal for the '<em><b>Micro</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECLARED_VERSION__MICRO = eINSTANCE.getDeclaredVersion_Micro();

		/**
		 * The meta object literal for the '<em><b>Qualifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECLARED_VERSION__QUALIFIER = eINSTANCE.getDeclaredVersion_Qualifier();

		/**
		 * The meta object literal for the '<em><b>Build Meta Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECLARED_VERSION__BUILD_META_DATA = eINSTANCE.getDeclaredVersion_BuildMetaData();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.impl.SourceContainerDescriptionImpl <em>Source Container Description</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.impl.SourceContainerDescriptionImpl
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getSourceContainerDescription()
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
		 * The meta object literal for the '<em><b>Paths Raw</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW = eINSTANCE.getSourceContainerDescription_PathsRaw();

		/**
		 * The meta object literal for the '<em><b>Compare By Fragment Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SOURCE_CONTAINER_DESCRIPTION___COMPARE_BY_FRAGMENT_TYPE__SOURCECONTAINERDESCRIPTION = eINSTANCE.getSourceContainerDescription__CompareByFragmentType__SourceContainerDescription();

		/**
		 * The meta object literal for the '<em><b>Get Paths</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SOURCE_CONTAINER_DESCRIPTION___GET_PATHS = eINSTANCE.getSourceContainerDescription__GetPaths();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.impl.ModuleFilterImpl <em>Module Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.impl.ModuleFilterImpl
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getModuleFilter()
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
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.impl.BootstrapModuleImpl <em>Bootstrap Module</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.impl.BootstrapModuleImpl
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getBootstrapModule()
		 * @generated
		 */
		EClass BOOTSTRAP_MODULE = eINSTANCE.getBootstrapModule();

		/**
		 * The meta object literal for the '<em><b>Module Specifier With Wildcard</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOTSTRAP_MODULE__MODULE_SPECIFIER_WITH_WILDCARD = eINSTANCE.getBootstrapModule_ModuleSpecifierWithWildcard();

		/**
		 * The meta object literal for the '<em><b>Source Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOTSTRAP_MODULE__SOURCE_PATH = eINSTANCE.getBootstrapModule_SourcePath();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.impl.ProjectReferenceImpl <em>Project Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.impl.ProjectReferenceImpl
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectReference()
		 * @generated
		 */
		EClass PROJECT_REFERENCE = eINSTANCE.getProjectReference();

		/**
		 * The meta object literal for the '<em><b>Project Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_REFERENCE__PROJECT_ID = eINSTANCE.getProjectReference_ProjectId();

		/**
		 * The meta object literal for the '<em><b>Declared Vendor Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_REFERENCE__DECLARED_VENDOR_ID = eINSTANCE.getProjectReference_DeclaredVendorId();

		/**
		 * The meta object literal for the '<em><b>Get Vendor Id</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROJECT_REFERENCE___GET_VENDOR_ID = eINSTANCE.getProjectReference__GetVendorId();

		/**
		 * The meta object literal for the '<em><b>Get Scope</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROJECT_REFERENCE___GET_SCOPE = eINSTANCE.getProjectReference__GetScope();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.impl.ModuleFilterSpecifierImpl <em>Module Filter Specifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.impl.ModuleFilterSpecifierImpl
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getModuleFilterSpecifier()
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
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.impl.ProjectDependencyImpl <em>Project Dependency</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.impl.ProjectDependencyImpl
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectDependency()
		 * @generated
		 */
		EClass PROJECT_DEPENDENCY = eINSTANCE.getProjectDependency();

		/**
		 * The meta object literal for the '<em><b>Version Constraint</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROJECT_DEPENDENCY__VERSION_CONSTRAINT = eINSTANCE.getProjectDependency_VersionConstraint();

		/**
		 * The meta object literal for the '<em><b>Declared Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROJECT_DEPENDENCY__DECLARED_SCOPE = eINSTANCE.getProjectDependency_DeclaredScope();

		/**
		 * The meta object literal for the '<em><b>Get Scope</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PROJECT_DEPENDENCY___GET_SCOPE = eINSTANCE.getProjectDependency__GetScope();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.impl.VersionConstraintImpl <em>Version Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.impl.VersionConstraintImpl
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getVersionConstraint()
		 * @generated
		 */
		EClass VERSION_CONSTRAINT = eINSTANCE.getVersionConstraint();

		/**
		 * The meta object literal for the '<em><b>Excl Lower Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_CONSTRAINT__EXCL_LOWER_BOUND = eINSTANCE.getVersionConstraint_ExclLowerBound();

		/**
		 * The meta object literal for the '<em><b>Lower Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_CONSTRAINT__LOWER_VERSION = eINSTANCE.getVersionConstraint_LowerVersion();

		/**
		 * The meta object literal for the '<em><b>Excl Upper Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_CONSTRAINT__EXCL_UPPER_BOUND = eINSTANCE.getVersionConstraint_ExclUpperBound();

		/**
		 * The meta object literal for the '<em><b>Upper Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VERSION_CONSTRAINT__UPPER_VERSION = eINSTANCE.getVersionConstraint_UpperVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.ProjectType <em>Project Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.ProjectType
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectType()
		 * @generated
		 */
		EEnum PROJECT_TYPE = eINSTANCE.getProjectType();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.SourceContainerType <em>Source Container Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.SourceContainerType
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getSourceContainerType()
		 * @generated
		 */
		EEnum SOURCE_CONTAINER_TYPE = eINSTANCE.getSourceContainerType();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.ModuleFilterType <em>Module Filter Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.ModuleFilterType
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getModuleFilterType()
		 * @generated
		 */
		EEnum MODULE_FILTER_TYPE = eINSTANCE.getModuleFilterType();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.ProjectDependencyScope <em>Project Dependency Scope</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.ProjectDependencyScope
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getProjectDependencyScope()
		 * @generated
		 */
		EEnum PROJECT_DEPENDENCY_SCOPE = eINSTANCE.getProjectDependencyScope();

		/**
		 * The meta object literal for the '{@link org.eclipse.n4js.n4mf.ModuleLoader <em>Module Loader</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.n4js.n4mf.ModuleLoader
		 * @see org.eclipse.n4js.n4mf.impl.N4mfPackageImpl#getModuleLoader()
		 * @generated
		 */
		EEnum MODULE_LOADER = eINSTANCE.getModuleLoader();

	}

} //N4mfPackage
