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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.n4js.n4mf.BootstrapModule;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier;
import org.eclipse.n4js.n4mf.ModuleFilterType;
import org.eclipse.n4js.n4mf.ModuleLoader;
import org.eclipse.n4js.n4mf.N4mfFactory;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDependencyScope;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.SourceContainerDescription;
import org.eclipse.n4js.n4mf.SourceContainerType;
import org.eclipse.n4js.n4mf.VersionConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class N4mfPackageImpl extends EPackageImpl implements N4mfPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass declaredVersionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sourceContainerDescriptionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass moduleFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bootstrapModuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass moduleFilterSpecifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass projectDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass versionConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum projectTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum sourceContainerTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum moduleFilterTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum projectDependencyScopeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum moduleLoaderEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private N4mfPackageImpl() {
		super(eNS_URI, N4mfFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link N4mfPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static N4mfPackage init() {
		if (isInited) return (N4mfPackage)EPackage.Registry.INSTANCE.getEPackage(N4mfPackage.eNS_URI);

		// Obtain or create and register package
		N4mfPackageImpl theN4mfPackage = (N4mfPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof N4mfPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new N4mfPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theN4mfPackage.createPackageContents();

		// Initialize created meta-data
		theN4mfPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theN4mfPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(N4mfPackage.eNS_URI, theN4mfPackage);
		return theN4mfPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProjectDescription() {
		return projectDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_ProjectId() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_VendorId() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_VendorName() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_ProjectVersion() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_ProjectType() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_MainModule() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_ExtendedRuntimeEnvironment() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_ProvidedRuntimeLibraries() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_RequiredRuntimeLibraries() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_ProjectDependencies() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_ImplementationId() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_ImplementedProjects() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_InitModules() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_ExecModule() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_OutputPathRaw() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_LibraryPathsRaw() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_ResourcePathsRaw() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_SourceContainers() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_ModuleFilters() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDescription_TestedProjects() {
		return (EReference)projectDescriptionEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_ModuleLoader() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_HasNestedNodeModulesFolder() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDescription_HasN4JSNature() {
		return (EAttribute)projectDescriptionEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProjectDescription__GetOutputPath() {
		return projectDescriptionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProjectDescription__SetOutputPath__String() {
		return projectDescriptionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProjectDescription__GetLibraryPaths() {
		return projectDescriptionEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProjectDescription__GetResourcePaths() {
		return projectDescriptionEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeclaredVersion() {
		return declaredVersionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeclaredVersion_Major() {
		return (EAttribute)declaredVersionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeclaredVersion_Minor() {
		return (EAttribute)declaredVersionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeclaredVersion_Micro() {
		return (EAttribute)declaredVersionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeclaredVersion_Qualifier() {
		return (EAttribute)declaredVersionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDeclaredVersion_BuildMetaData() {
		return (EAttribute)declaredVersionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSourceContainerDescription() {
		return sourceContainerDescriptionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSourceContainerDescription_SourceContainerType() {
		return (EAttribute)sourceContainerDescriptionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSourceContainerDescription_PathsRaw() {
		return (EAttribute)sourceContainerDescriptionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSourceContainerDescription__CompareByFragmentType__SourceContainerDescription() {
		return sourceContainerDescriptionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getSourceContainerDescription__GetPaths() {
		return sourceContainerDescriptionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModuleFilter() {
		return moduleFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModuleFilter_ModuleFilterType() {
		return (EAttribute)moduleFilterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModuleFilter_ModuleSpecifiers() {
		return (EReference)moduleFilterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBootstrapModule() {
		return bootstrapModuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBootstrapModule_ModuleSpecifierWithWildcard() {
		return (EAttribute)bootstrapModuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBootstrapModule_SourcePath() {
		return (EAttribute)bootstrapModuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProjectReference() {
		return projectReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectReference_ProjectId() {
		return (EAttribute)projectReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectReference_DeclaredVendorId() {
		return (EAttribute)projectReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProjectReference__GetVendorId() {
		return projectReferenceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProjectReference__GetScope() {
		return projectReferenceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModuleFilterSpecifier() {
		return moduleFilterSpecifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModuleFilterSpecifier_ModuleSpecifierWithWildcard() {
		return (EAttribute)moduleFilterSpecifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModuleFilterSpecifier_SourcePath() {
		return (EAttribute)moduleFilterSpecifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProjectDependency() {
		return projectDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProjectDependency_VersionConstraint() {
		return (EReference)projectDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProjectDependency_DeclaredScope() {
		return (EAttribute)projectDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getProjectDependency__GetScope() {
		return projectDependencyEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVersionConstraint() {
		return versionConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVersionConstraint_ExclLowerBound() {
		return (EAttribute)versionConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionConstraint_LowerVersion() {
		return (EReference)versionConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVersionConstraint_ExclUpperBound() {
		return (EAttribute)versionConstraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVersionConstraint_UpperVersion() {
		return (EReference)versionConstraintEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getProjectType() {
		return projectTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSourceContainerType() {
		return sourceContainerTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getModuleFilterType() {
		return moduleFilterTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getProjectDependencyScope() {
		return projectDependencyScopeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getModuleLoader() {
		return moduleLoaderEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public N4mfFactory getN4mfFactory() {
		return (N4mfFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		projectDescriptionEClass = createEClass(PROJECT_DESCRIPTION);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__PROJECT_ID);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__VENDOR_ID);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__VENDOR_NAME);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__PROJECT_VERSION);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__PROJECT_TYPE);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__MAIN_MODULE);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__IMPLEMENTATION_ID);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__INIT_MODULES);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__EXEC_MODULE);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__OUTPUT_PATH_RAW);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__LIBRARY_PATHS_RAW);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__RESOURCE_PATHS_RAW);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__SOURCE_CONTAINERS);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__MODULE_FILTERS);
		createEReference(projectDescriptionEClass, PROJECT_DESCRIPTION__TESTED_PROJECTS);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__MODULE_LOADER);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__HAS_NESTED_NODE_MODULES_FOLDER);
		createEAttribute(projectDescriptionEClass, PROJECT_DESCRIPTION__HAS_N4JS_NATURE);
		createEOperation(projectDescriptionEClass, PROJECT_DESCRIPTION___GET_OUTPUT_PATH);
		createEOperation(projectDescriptionEClass, PROJECT_DESCRIPTION___SET_OUTPUT_PATH__STRING);
		createEOperation(projectDescriptionEClass, PROJECT_DESCRIPTION___GET_LIBRARY_PATHS);
		createEOperation(projectDescriptionEClass, PROJECT_DESCRIPTION___GET_RESOURCE_PATHS);

		declaredVersionEClass = createEClass(DECLARED_VERSION);
		createEAttribute(declaredVersionEClass, DECLARED_VERSION__MAJOR);
		createEAttribute(declaredVersionEClass, DECLARED_VERSION__MINOR);
		createEAttribute(declaredVersionEClass, DECLARED_VERSION__MICRO);
		createEAttribute(declaredVersionEClass, DECLARED_VERSION__QUALIFIER);
		createEAttribute(declaredVersionEClass, DECLARED_VERSION__BUILD_META_DATA);

		sourceContainerDescriptionEClass = createEClass(SOURCE_CONTAINER_DESCRIPTION);
		createEAttribute(sourceContainerDescriptionEClass, SOURCE_CONTAINER_DESCRIPTION__SOURCE_CONTAINER_TYPE);
		createEAttribute(sourceContainerDescriptionEClass, SOURCE_CONTAINER_DESCRIPTION__PATHS_RAW);
		createEOperation(sourceContainerDescriptionEClass, SOURCE_CONTAINER_DESCRIPTION___COMPARE_BY_FRAGMENT_TYPE__SOURCECONTAINERDESCRIPTION);
		createEOperation(sourceContainerDescriptionEClass, SOURCE_CONTAINER_DESCRIPTION___GET_PATHS);

		moduleFilterEClass = createEClass(MODULE_FILTER);
		createEAttribute(moduleFilterEClass, MODULE_FILTER__MODULE_FILTER_TYPE);
		createEReference(moduleFilterEClass, MODULE_FILTER__MODULE_SPECIFIERS);

		bootstrapModuleEClass = createEClass(BOOTSTRAP_MODULE);
		createEAttribute(bootstrapModuleEClass, BOOTSTRAP_MODULE__MODULE_SPECIFIER_WITH_WILDCARD);
		createEAttribute(bootstrapModuleEClass, BOOTSTRAP_MODULE__SOURCE_PATH);

		projectReferenceEClass = createEClass(PROJECT_REFERENCE);
		createEAttribute(projectReferenceEClass, PROJECT_REFERENCE__PROJECT_ID);
		createEAttribute(projectReferenceEClass, PROJECT_REFERENCE__DECLARED_VENDOR_ID);
		createEOperation(projectReferenceEClass, PROJECT_REFERENCE___GET_VENDOR_ID);
		createEOperation(projectReferenceEClass, PROJECT_REFERENCE___GET_SCOPE);

		moduleFilterSpecifierEClass = createEClass(MODULE_FILTER_SPECIFIER);
		createEAttribute(moduleFilterSpecifierEClass, MODULE_FILTER_SPECIFIER__MODULE_SPECIFIER_WITH_WILDCARD);
		createEAttribute(moduleFilterSpecifierEClass, MODULE_FILTER_SPECIFIER__SOURCE_PATH);

		projectDependencyEClass = createEClass(PROJECT_DEPENDENCY);
		createEReference(projectDependencyEClass, PROJECT_DEPENDENCY__VERSION_CONSTRAINT);
		createEAttribute(projectDependencyEClass, PROJECT_DEPENDENCY__DECLARED_SCOPE);
		createEOperation(projectDependencyEClass, PROJECT_DEPENDENCY___GET_SCOPE);

		versionConstraintEClass = createEClass(VERSION_CONSTRAINT);
		createEAttribute(versionConstraintEClass, VERSION_CONSTRAINT__EXCL_LOWER_BOUND);
		createEReference(versionConstraintEClass, VERSION_CONSTRAINT__LOWER_VERSION);
		createEAttribute(versionConstraintEClass, VERSION_CONSTRAINT__EXCL_UPPER_BOUND);
		createEReference(versionConstraintEClass, VERSION_CONSTRAINT__UPPER_VERSION);

		// Create enums
		projectTypeEEnum = createEEnum(PROJECT_TYPE);
		sourceContainerTypeEEnum = createEEnum(SOURCE_CONTAINER_TYPE);
		moduleFilterTypeEEnum = createEEnum(MODULE_FILTER_TYPE);
		projectDependencyScopeEEnum = createEEnum(PROJECT_DEPENDENCY_SCOPE);
		moduleLoaderEEnum = createEEnum(MODULE_LOADER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		projectDependencyEClass.getESuperTypes().add(this.getProjectReference());

		// Initialize classes, features, and operations; add parameters
		initEClass(projectDescriptionEClass, ProjectDescription.class, "ProjectDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProjectDescription_ProjectId(), theEcorePackage.getEString(), "projectId", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_VendorId(), theEcorePackage.getEString(), "vendorId", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_VendorName(), theEcorePackage.getEString(), "vendorName", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_ProjectVersion(), this.getDeclaredVersion(), null, "projectVersion", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_ProjectType(), this.getProjectType(), "projectType", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_MainModule(), theEcorePackage.getEString(), "mainModule", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_ExtendedRuntimeEnvironment(), this.getProjectReference(), null, "extendedRuntimeEnvironment", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_ProvidedRuntimeLibraries(), this.getProjectReference(), null, "providedRuntimeLibraries", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_RequiredRuntimeLibraries(), this.getProjectReference(), null, "requiredRuntimeLibraries", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_ProjectDependencies(), this.getProjectDependency(), null, "projectDependencies", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_ImplementationId(), theEcorePackage.getEString(), "implementationId", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_ImplementedProjects(), this.getProjectReference(), null, "implementedProjects", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_InitModules(), this.getBootstrapModule(), null, "initModules", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_ExecModule(), this.getBootstrapModule(), null, "execModule", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_OutputPathRaw(), theEcorePackage.getEString(), "outputPathRaw", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_LibraryPathsRaw(), theEcorePackage.getEString(), "libraryPathsRaw", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_ResourcePathsRaw(), theEcorePackage.getEString(), "resourcePathsRaw", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_SourceContainers(), this.getSourceContainerDescription(), null, "sourceContainers", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_ModuleFilters(), this.getModuleFilter(), null, "moduleFilters", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProjectDescription_TestedProjects(), this.getProjectDependency(), null, "testedProjects", null, 0, -1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_ModuleLoader(), this.getModuleLoader(), "moduleLoader", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_HasNestedNodeModulesFolder(), theEcorePackage.getEBoolean(), "hasNestedNodeModulesFolder", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDescription_HasN4JSNature(), theEcorePackage.getEBoolean(), "hasN4JSNature", null, 0, 1, ProjectDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getProjectDescription__GetOutputPath(), theEcorePackage.getEString(), "getOutputPath", 0, 1, !IS_UNIQUE, IS_ORDERED);

		EOperation op = initEOperation(getProjectDescription__SetOutputPath__String(), null, "setOutputPath", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theEcorePackage.getEString(), "newOutputPath", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getProjectDescription__GetLibraryPaths(), theEcorePackage.getEString(), "getLibraryPaths", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getProjectDescription__GetResourcePaths(), theEcorePackage.getEString(), "getResourcePaths", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(declaredVersionEClass, DeclaredVersion.class, "DeclaredVersion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeclaredVersion_Major(), theEcorePackage.getEInt(), "major", null, 0, 1, DeclaredVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeclaredVersion_Minor(), theEcorePackage.getEInt(), "minor", null, 0, 1, DeclaredVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeclaredVersion_Micro(), theEcorePackage.getEInt(), "micro", null, 0, 1, DeclaredVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeclaredVersion_Qualifier(), theEcorePackage.getEString(), "qualifier", null, 0, 1, DeclaredVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeclaredVersion_BuildMetaData(), theEcorePackage.getEString(), "buildMetaData", null, 0, 1, DeclaredVersion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sourceContainerDescriptionEClass, SourceContainerDescription.class, "SourceContainerDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSourceContainerDescription_SourceContainerType(), this.getSourceContainerType(), "sourceContainerType", null, 0, 1, SourceContainerDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSourceContainerDescription_PathsRaw(), theEcorePackage.getEString(), "pathsRaw", null, 0, -1, SourceContainerDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getSourceContainerDescription__CompareByFragmentType__SourceContainerDescription(), theEcorePackage.getEInt(), "compareByFragmentType", 0, 1, !IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getSourceContainerDescription(), "other", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getSourceContainerDescription__GetPaths(), theEcorePackage.getEString(), "getPaths", 0, -1, !IS_UNIQUE, IS_ORDERED);

		initEClass(moduleFilterEClass, ModuleFilter.class, "ModuleFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModuleFilter_ModuleFilterType(), this.getModuleFilterType(), "moduleFilterType", null, 0, 1, ModuleFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModuleFilter_ModuleSpecifiers(), this.getModuleFilterSpecifier(), null, "moduleSpecifiers", null, 0, -1, ModuleFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bootstrapModuleEClass, BootstrapModule.class, "BootstrapModule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBootstrapModule_ModuleSpecifierWithWildcard(), theEcorePackage.getEString(), "moduleSpecifierWithWildcard", null, 0, 1, BootstrapModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBootstrapModule_SourcePath(), theEcorePackage.getEString(), "sourcePath", null, 0, 1, BootstrapModule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(projectReferenceEClass, ProjectReference.class, "ProjectReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProjectReference_ProjectId(), theEcorePackage.getEString(), "projectId", null, 0, 1, ProjectReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectReference_DeclaredVendorId(), theEcorePackage.getEString(), "declaredVendorId", null, 0, 1, ProjectReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getProjectReference__GetVendorId(), theEcorePackage.getEString(), "getVendorId", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEOperation(getProjectReference__GetScope(), this.getProjectDependencyScope(), "getScope", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(moduleFilterSpecifierEClass, ModuleFilterSpecifier.class, "ModuleFilterSpecifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModuleFilterSpecifier_ModuleSpecifierWithWildcard(), theEcorePackage.getEString(), "moduleSpecifierWithWildcard", null, 0, 1, ModuleFilterSpecifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModuleFilterSpecifier_SourcePath(), theEcorePackage.getEString(), "sourcePath", null, 0, 1, ModuleFilterSpecifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(projectDependencyEClass, ProjectDependency.class, "ProjectDependency", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProjectDependency_VersionConstraint(), this.getVersionConstraint(), null, "versionConstraint", null, 0, 1, ProjectDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProjectDependency_DeclaredScope(), this.getProjectDependencyScope(), "declaredScope", null, 0, 1, ProjectDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEOperation(getProjectDependency__GetScope(), this.getProjectDependencyScope(), "getScope", 0, 1, !IS_UNIQUE, IS_ORDERED);

		initEClass(versionConstraintEClass, VersionConstraint.class, "VersionConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersionConstraint_ExclLowerBound(), theEcorePackage.getEBoolean(), "exclLowerBound", null, 0, 1, VersionConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersionConstraint_LowerVersion(), this.getDeclaredVersion(), null, "lowerVersion", null, 0, 1, VersionConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionConstraint_ExclUpperBound(), theEcorePackage.getEBoolean(), "exclUpperBound", null, 0, 1, VersionConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersionConstraint_UpperVersion(), this.getDeclaredVersion(), null, "upperVersion", null, 0, 1, VersionConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(projectTypeEEnum, ProjectType.class, "ProjectType");
		addEEnumLiteral(projectTypeEEnum, ProjectType.VALIDATION);
		addEEnumLiteral(projectTypeEEnum, ProjectType.APPLICATION);
		addEEnumLiteral(projectTypeEEnum, ProjectType.PROCESSOR);
		addEEnumLiteral(projectTypeEEnum, ProjectType.LIBRARY);
		addEEnumLiteral(projectTypeEEnum, ProjectType.API);
		addEEnumLiteral(projectTypeEEnum, ProjectType.RUNTIME_ENVIRONMENT);
		addEEnumLiteral(projectTypeEEnum, ProjectType.RUNTIME_LIBRARY);
		addEEnumLiteral(projectTypeEEnum, ProjectType.TEST);

		initEEnum(sourceContainerTypeEEnum, SourceContainerType.class, "SourceContainerType");
		addEEnumLiteral(sourceContainerTypeEEnum, SourceContainerType.SOURCE);
		addEEnumLiteral(sourceContainerTypeEEnum, SourceContainerType.TEST);
		addEEnumLiteral(sourceContainerTypeEEnum, SourceContainerType.EXTERNAL);

		initEEnum(moduleFilterTypeEEnum, ModuleFilterType.class, "ModuleFilterType");
		addEEnumLiteral(moduleFilterTypeEEnum, ModuleFilterType.NO_VALIDATE);
		addEEnumLiteral(moduleFilterTypeEEnum, ModuleFilterType.NO_MODULE_WRAPPING);

		initEEnum(projectDependencyScopeEEnum, ProjectDependencyScope.class, "ProjectDependencyScope");
		addEEnumLiteral(projectDependencyScopeEEnum, ProjectDependencyScope.COMPILE);
		addEEnumLiteral(projectDependencyScopeEEnum, ProjectDependencyScope.TEST);

		initEEnum(moduleLoaderEEnum, ModuleLoader.class, "ModuleLoader");
		addEEnumLiteral(moduleLoaderEEnum, ModuleLoader.N4JS);
		addEEnumLiteral(moduleLoaderEEnum, ModuleLoader.COMMONJS);
		addEEnumLiteral(moduleLoaderEEnum, ModuleLoader.NODE_BUILTIN);

		// Create resource
		createResource(eNS_URI);
	}

} //N4mfPackageImpl
