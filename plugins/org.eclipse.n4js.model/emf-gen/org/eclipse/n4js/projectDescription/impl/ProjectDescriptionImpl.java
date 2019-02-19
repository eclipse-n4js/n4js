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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.projectDescription.BootstrapModule;
import org.eclipse.n4js.projectDescription.ModuleFilter;
import org.eclipse.n4js.projectDescription.ModuleLoader;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectDescriptionPackage;
import org.eclipse.n4js.projectDescription.ProjectReference;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;

import org.eclipse.n4js.semver.Semver.VersionNumber;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getVendorId <em>Vendor Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getVendorName <em>Vendor Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getProjectVersion <em>Project Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getProjectType <em>Project Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getMainModule <em>Main Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getRequiredRuntimeLibraries <em>Required Runtime Libraries</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getProjectDependencies <em>Project Dependencies</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getImplementationId <em>Implementation Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getImplementedProjects <em>Implemented Projects</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getInitModules <em>Init Modules</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getExecModule <em>Exec Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getOutputPath <em>Output Path</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getSourceContainers <em>Source Containers</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getModuleFilters <em>Module Filters</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getTestedProjects <em>Tested Projects</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getModuleLoader <em>Module Loader</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getDefinesPackage <em>Defines Package</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#isHasNestedNodeModulesFolder <em>Has Nested Node Modules Folder</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#isHasN4JSNature <em>Has N4JS Nature</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#isYarnWorkspaceRoot <em>Yarn Workspace Root</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.impl.ProjectDescriptionImpl#getWorkspaces <em>Workspaces</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectDescriptionImpl extends MinimalEObjectImpl.Container implements ProjectDescription {
	/**
	 * The default value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectName() <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectName()
	 * @generated
	 * @ordered
	 */
	protected String projectName = PROJECT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getVendorId() <em>Vendor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendorId()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDOR_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVendorId() <em>Vendor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendorId()
	 * @generated
	 * @ordered
	 */
	protected String vendorId = VENDOR_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getVendorName() <em>Vendor Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendorName()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDOR_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVendorName() <em>Vendor Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendorName()
	 * @generated
	 * @ordered
	 */
	protected String vendorName = VENDOR_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProjectVersion() <em>Project Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectVersion()
	 * @generated
	 * @ordered
	 */
	protected VersionNumber projectVersion;

	/**
	 * The default value of the '{@link #getProjectType() <em>Project Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectType()
	 * @generated
	 * @ordered
	 */
	protected static final ProjectType PROJECT_TYPE_EDEFAULT = ProjectType.PLAINJS;

	/**
	 * The cached value of the '{@link #getProjectType() <em>Project Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectType()
	 * @generated
	 * @ordered
	 */
	protected ProjectType projectType = PROJECT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMainModule() <em>Main Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainModule()
	 * @generated
	 * @ordered
	 */
	protected static final String MAIN_MODULE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMainModule() <em>Main Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainModule()
	 * @generated
	 * @ordered
	 */
	protected String mainModule = MAIN_MODULE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExtendedRuntimeEnvironment() <em>Extended Runtime Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedRuntimeEnvironment()
	 * @generated
	 * @ordered
	 */
	protected ProjectReference extendedRuntimeEnvironment;

	/**
	 * The cached value of the '{@link #getProvidedRuntimeLibraries() <em>Provided Runtime Libraries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvidedRuntimeLibraries()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectReference> providedRuntimeLibraries;

	/**
	 * The cached value of the '{@link #getRequiredRuntimeLibraries() <em>Required Runtime Libraries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredRuntimeLibraries()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectReference> requiredRuntimeLibraries;

	/**
	 * The cached value of the '{@link #getProjectDependencies() <em>Project Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectDependency> projectDependencies;

	/**
	 * The default value of the '{@link #getImplementationId() <em>Implementation Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationId()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationId() <em>Implementation Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationId()
	 * @generated
	 * @ordered
	 */
	protected String implementationId = IMPLEMENTATION_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getImplementedProjects() <em>Implemented Projects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementedProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectReference> implementedProjects;

	/**
	 * The cached value of the '{@link #getInitModules() <em>Init Modules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitModules()
	 * @generated
	 * @ordered
	 */
	protected EList<BootstrapModule> initModules;

	/**
	 * The cached value of the '{@link #getExecModule() <em>Exec Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecModule()
	 * @generated
	 * @ordered
	 */
	protected BootstrapModule execModule;

	/**
	 * The default value of the '{@link #getOutputPath() <em>Output Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputPath()
	 * @generated
	 * @ordered
	 */
	protected static final String OUTPUT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOutputPath() <em>Output Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputPath()
	 * @generated
	 * @ordered
	 */
	protected String outputPath = OUTPUT_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSourceContainers() <em>Source Containers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceContainers()
	 * @generated
	 * @ordered
	 */
	protected EList<SourceContainerDescription> sourceContainers;

	/**
	 * The cached value of the '{@link #getModuleFilters() <em>Module Filters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleFilters()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleFilter> moduleFilters;

	/**
	 * The cached value of the '{@link #getTestedProjects() <em>Tested Projects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestedProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<ProjectReference> testedProjects;

	/**
	 * The default value of the '{@link #getModuleLoader() <em>Module Loader</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleLoader()
	 * @generated
	 * @ordered
	 */
	protected static final ModuleLoader MODULE_LOADER_EDEFAULT = ModuleLoader.N4JS;

	/**
	 * The cached value of the '{@link #getModuleLoader() <em>Module Loader</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleLoader()
	 * @generated
	 * @ordered
	 */
	protected ModuleLoader moduleLoader = MODULE_LOADER_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefinesPackage() <em>Defines Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinesPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFINES_PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefinesPackage() <em>Defines Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinesPackage()
	 * @generated
	 * @ordered
	 */
	protected String definesPackage = DEFINES_PACKAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isHasNestedNodeModulesFolder() <em>Has Nested Node Modules Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasNestedNodeModulesFolder()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_NESTED_NODE_MODULES_FOLDER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasNestedNodeModulesFolder() <em>Has Nested Node Modules Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasNestedNodeModulesFolder()
	 * @generated
	 * @ordered
	 */
	protected boolean hasNestedNodeModulesFolder = HAS_NESTED_NODE_MODULES_FOLDER_EDEFAULT;

	/**
	 * The default value of the '{@link #isHasN4JSNature() <em>Has N4JS Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasN4JSNature()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_N4JS_NATURE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasN4JSNature() <em>Has N4JS Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasN4JSNature()
	 * @generated
	 * @ordered
	 */
	protected boolean hasN4JSNature = HAS_N4JS_NATURE_EDEFAULT;

	/**
	 * The default value of the '{@link #isYarnWorkspaceRoot() <em>Yarn Workspace Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isYarnWorkspaceRoot()
	 * @generated
	 * @ordered
	 */
	protected static final boolean YARN_WORKSPACE_ROOT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isYarnWorkspaceRoot() <em>Yarn Workspace Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isYarnWorkspaceRoot()
	 * @generated
	 * @ordered
	 */
	protected boolean yarnWorkspaceRoot = YARN_WORKSPACE_ROOT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getWorkspaces() <em>Workspaces</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkspaces()
	 * @generated
	 * @ordered
	 */
	protected EList<String> workspaces;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProjectDescriptionPackage.Literals.PROJECT_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectName(String newProjectName) {
		String oldProjectName = projectName;
		projectName = newProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_NAME, oldProjectName, projectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVendorId(String newVendorId) {
		String oldVendorId = vendorId;
		vendorId = newVendorId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_ID, oldVendorId, vendorId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVendorName(String newVendorName) {
		String oldVendorName = vendorName;
		vendorName = newVendorName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_NAME, oldVendorName, vendorName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VersionNumber getProjectVersion() {
		return projectVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProjectVersion(VersionNumber newProjectVersion, NotificationChain msgs) {
		VersionNumber oldProjectVersion = projectVersion;
		projectVersion = newProjectVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION, oldProjectVersion, newProjectVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectVersion(VersionNumber newProjectVersion) {
		if (newProjectVersion != projectVersion) {
			NotificationChain msgs = null;
			if (projectVersion != null)
				msgs = ((InternalEObject)projectVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION, null, msgs);
			if (newProjectVersion != null)
				msgs = ((InternalEObject)newProjectVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION, null, msgs);
			msgs = basicSetProjectVersion(newProjectVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION, newProjectVersion, newProjectVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectType getProjectType() {
		return projectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectType(ProjectType newProjectType) {
		ProjectType oldProjectType = projectType;
		projectType = newProjectType == null ? PROJECT_TYPE_EDEFAULT : newProjectType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_TYPE, oldProjectType, projectType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMainModule() {
		return mainModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMainModule(String newMainModule) {
		String oldMainModule = mainModule;
		mainModule = newMainModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__MAIN_MODULE, oldMainModule, mainModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectReference getExtendedRuntimeEnvironment() {
		return extendedRuntimeEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtendedRuntimeEnvironment(ProjectReference newExtendedRuntimeEnvironment, NotificationChain msgs) {
		ProjectReference oldExtendedRuntimeEnvironment = extendedRuntimeEnvironment;
		extendedRuntimeEnvironment = newExtendedRuntimeEnvironment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT, oldExtendedRuntimeEnvironment, newExtendedRuntimeEnvironment);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendedRuntimeEnvironment(ProjectReference newExtendedRuntimeEnvironment) {
		if (newExtendedRuntimeEnvironment != extendedRuntimeEnvironment) {
			NotificationChain msgs = null;
			if (extendedRuntimeEnvironment != null)
				msgs = ((InternalEObject)extendedRuntimeEnvironment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT, null, msgs);
			if (newExtendedRuntimeEnvironment != null)
				msgs = ((InternalEObject)newExtendedRuntimeEnvironment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT, null, msgs);
			msgs = basicSetExtendedRuntimeEnvironment(newExtendedRuntimeEnvironment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT, newExtendedRuntimeEnvironment, newExtendedRuntimeEnvironment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectReference> getProvidedRuntimeLibraries() {
		if (providedRuntimeLibraries == null) {
			providedRuntimeLibraries = new EObjectContainmentEList<ProjectReference>(ProjectReference.class, this, ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES);
		}
		return providedRuntimeLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectReference> getRequiredRuntimeLibraries() {
		if (requiredRuntimeLibraries == null) {
			requiredRuntimeLibraries = new EObjectContainmentEList<ProjectReference>(ProjectReference.class, this, ProjectDescriptionPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES);
		}
		return requiredRuntimeLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectDependency> getProjectDependencies() {
		if (projectDependencies == null) {
			projectDependencies = new EObjectContainmentEList<ProjectDependency>(ProjectDependency.class, this, ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES);
		}
		return projectDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementationId() {
		return implementationId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementationId(String newImplementationId) {
		String oldImplementationId = implementationId;
		implementationId = newImplementationId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID, oldImplementationId, implementationId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectReference> getImplementedProjects() {
		if (implementedProjects == null) {
			implementedProjects = new EObjectContainmentEList<ProjectReference>(ProjectReference.class, this, ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS);
		}
		return implementedProjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BootstrapModule> getInitModules() {
		if (initModules == null) {
			initModules = new EObjectContainmentEList<BootstrapModule>(BootstrapModule.class, this, ProjectDescriptionPackage.PROJECT_DESCRIPTION__INIT_MODULES);
		}
		return initModules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BootstrapModule getExecModule() {
		return execModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExecModule(BootstrapModule newExecModule, NotificationChain msgs) {
		BootstrapModule oldExecModule = execModule;
		execModule = newExecModule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXEC_MODULE, oldExecModule, newExecModule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecModule(BootstrapModule newExecModule) {
		if (newExecModule != execModule) {
			NotificationChain msgs = null;
			if (execModule != null)
				msgs = ((InternalEObject)execModule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXEC_MODULE, null, msgs);
			if (newExecModule != null)
				msgs = ((InternalEObject)newExecModule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXEC_MODULE, null, msgs);
			msgs = basicSetExecModule(newExecModule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXEC_MODULE, newExecModule, newExecModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputPath(String newOutputPath) {
		String oldOutputPath = outputPath;
		outputPath = newOutputPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__OUTPUT_PATH, oldOutputPath, outputPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SourceContainerDescription> getSourceContainers() {
		if (sourceContainers == null) {
			sourceContainers = new EObjectContainmentEList<SourceContainerDescription>(SourceContainerDescription.class, this, ProjectDescriptionPackage.PROJECT_DESCRIPTION__SOURCE_CONTAINERS);
		}
		return sourceContainers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModuleFilter> getModuleFilters() {
		if (moduleFilters == null) {
			moduleFilters = new EObjectContainmentEList<ModuleFilter>(ModuleFilter.class, this, ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_FILTERS);
		}
		return moduleFilters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectReference> getTestedProjects() {
		if (testedProjects == null) {
			testedProjects = new EObjectContainmentEList<ProjectReference>(ProjectReference.class, this, ProjectDescriptionPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS);
		}
		return testedProjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleLoader getModuleLoader() {
		return moduleLoader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModuleLoader(ModuleLoader newModuleLoader) {
		ModuleLoader oldModuleLoader = moduleLoader;
		moduleLoader = newModuleLoader == null ? MODULE_LOADER_EDEFAULT : newModuleLoader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_LOADER, oldModuleLoader, moduleLoader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefinesPackage() {
		return definesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefinesPackage(String newDefinesPackage) {
		String oldDefinesPackage = definesPackage;
		definesPackage = newDefinesPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__DEFINES_PACKAGE, oldDefinesPackage, definesPackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasNestedNodeModulesFolder() {
		return hasNestedNodeModulesFolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasNestedNodeModulesFolder(boolean newHasNestedNodeModulesFolder) {
		boolean oldHasNestedNodeModulesFolder = hasNestedNodeModulesFolder;
		hasNestedNodeModulesFolder = newHasNestedNodeModulesFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_NESTED_NODE_MODULES_FOLDER, oldHasNestedNodeModulesFolder, hasNestedNodeModulesFolder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasN4JSNature() {
		return hasN4JSNature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasN4JSNature(boolean newHasN4JSNature) {
		boolean oldHasN4JSNature = hasN4JSNature;
		hasN4JSNature = newHasN4JSNature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_N4JS_NATURE, oldHasN4JSNature, hasN4JSNature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isYarnWorkspaceRoot() {
		return yarnWorkspaceRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setYarnWorkspaceRoot(boolean newYarnWorkspaceRoot) {
		boolean oldYarnWorkspaceRoot = yarnWorkspaceRoot;
		yarnWorkspaceRoot = newYarnWorkspaceRoot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProjectDescriptionPackage.PROJECT_DESCRIPTION__YARN_WORKSPACE_ROOT, oldYarnWorkspaceRoot, yarnWorkspaceRoot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getWorkspaces() {
		if (workspaces == null) {
			workspaces = new EDataTypeEList<String>(String.class, this, ProjectDescriptionPackage.PROJECT_DESCRIPTION__WORKSPACES);
		}
		return workspaces;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				return basicSetProjectVersion(null, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				return basicSetExtendedRuntimeEnvironment(null, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				return ((InternalEList<?>)getProvidedRuntimeLibraries()).basicRemove(otherEnd, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				return ((InternalEList<?>)getRequiredRuntimeLibraries()).basicRemove(otherEnd, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				return ((InternalEList<?>)getProjectDependencies()).basicRemove(otherEnd, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				return ((InternalEList<?>)getImplementedProjects()).basicRemove(otherEnd, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				return ((InternalEList<?>)getInitModules()).basicRemove(otherEnd, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				return basicSetExecModule(null, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__SOURCE_CONTAINERS:
				return ((InternalEList<?>)getSourceContainers()).basicRemove(otherEnd, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				return ((InternalEList<?>)getModuleFilters()).basicRemove(otherEnd, msgs);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				return ((InternalEList<?>)getTestedProjects()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_NAME:
				return getProjectName();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_ID:
				return getVendorId();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_NAME:
				return getVendorName();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				return getProjectVersion();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_TYPE:
				return getProjectType();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MAIN_MODULE:
				return getMainModule();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				return getExtendedRuntimeEnvironment();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				return getProvidedRuntimeLibraries();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				return getRequiredRuntimeLibraries();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				return getProjectDependencies();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID:
				return getImplementationId();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				return getImplementedProjects();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				return getInitModules();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				return getExecModule();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__OUTPUT_PATH:
				return getOutputPath();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__SOURCE_CONTAINERS:
				return getSourceContainers();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				return getModuleFilters();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				return getTestedProjects();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_LOADER:
				return getModuleLoader();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__DEFINES_PACKAGE:
				return getDefinesPackage();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_NESTED_NODE_MODULES_FOLDER:
				return isHasNestedNodeModulesFolder();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_N4JS_NATURE:
				return isHasN4JSNature();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__YARN_WORKSPACE_ROOT:
				return isYarnWorkspaceRoot();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__WORKSPACES:
				return getWorkspaces();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_NAME:
				setProjectName((String)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_ID:
				setVendorId((String)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_NAME:
				setVendorName((String)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				setProjectVersion((VersionNumber)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_TYPE:
				setProjectType((ProjectType)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MAIN_MODULE:
				setMainModule((String)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				setExtendedRuntimeEnvironment((ProjectReference)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				getProvidedRuntimeLibraries().clear();
				getProvidedRuntimeLibraries().addAll((Collection<? extends ProjectReference>)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				getRequiredRuntimeLibraries().clear();
				getRequiredRuntimeLibraries().addAll((Collection<? extends ProjectReference>)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				getProjectDependencies().clear();
				getProjectDependencies().addAll((Collection<? extends ProjectDependency>)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID:
				setImplementationId((String)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				getImplementedProjects().clear();
				getImplementedProjects().addAll((Collection<? extends ProjectReference>)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				getInitModules().clear();
				getInitModules().addAll((Collection<? extends BootstrapModule>)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				setExecModule((BootstrapModule)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__OUTPUT_PATH:
				setOutputPath((String)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__SOURCE_CONTAINERS:
				getSourceContainers().clear();
				getSourceContainers().addAll((Collection<? extends SourceContainerDescription>)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				getModuleFilters().clear();
				getModuleFilters().addAll((Collection<? extends ModuleFilter>)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				getTestedProjects().clear();
				getTestedProjects().addAll((Collection<? extends ProjectReference>)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_LOADER:
				setModuleLoader((ModuleLoader)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__DEFINES_PACKAGE:
				setDefinesPackage((String)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_NESTED_NODE_MODULES_FOLDER:
				setHasNestedNodeModulesFolder((Boolean)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_N4JS_NATURE:
				setHasN4JSNature((Boolean)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__YARN_WORKSPACE_ROOT:
				setYarnWorkspaceRoot((Boolean)newValue);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__WORKSPACES:
				getWorkspaces().clear();
				getWorkspaces().addAll((Collection<? extends String>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_NAME:
				setProjectName(PROJECT_NAME_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_ID:
				setVendorId(VENDOR_ID_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_NAME:
				setVendorName(VENDOR_NAME_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				setProjectVersion((VersionNumber)null);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_TYPE:
				setProjectType(PROJECT_TYPE_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MAIN_MODULE:
				setMainModule(MAIN_MODULE_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				setExtendedRuntimeEnvironment((ProjectReference)null);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				getProvidedRuntimeLibraries().clear();
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				getRequiredRuntimeLibraries().clear();
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				getProjectDependencies().clear();
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID:
				setImplementationId(IMPLEMENTATION_ID_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				getImplementedProjects().clear();
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				getInitModules().clear();
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				setExecModule((BootstrapModule)null);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__OUTPUT_PATH:
				setOutputPath(OUTPUT_PATH_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__SOURCE_CONTAINERS:
				getSourceContainers().clear();
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				getModuleFilters().clear();
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				getTestedProjects().clear();
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_LOADER:
				setModuleLoader(MODULE_LOADER_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__DEFINES_PACKAGE:
				setDefinesPackage(DEFINES_PACKAGE_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_NESTED_NODE_MODULES_FOLDER:
				setHasNestedNodeModulesFolder(HAS_NESTED_NODE_MODULES_FOLDER_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_N4JS_NATURE:
				setHasN4JSNature(HAS_N4JS_NATURE_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__YARN_WORKSPACE_ROOT:
				setYarnWorkspaceRoot(YARN_WORKSPACE_ROOT_EDEFAULT);
				return;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__WORKSPACES:
				getWorkspaces().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_NAME:
				return PROJECT_NAME_EDEFAULT == null ? projectName != null : !PROJECT_NAME_EDEFAULT.equals(projectName);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_ID:
				return VENDOR_ID_EDEFAULT == null ? vendorId != null : !VENDOR_ID_EDEFAULT.equals(vendorId);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__VENDOR_NAME:
				return VENDOR_NAME_EDEFAULT == null ? vendorName != null : !VENDOR_NAME_EDEFAULT.equals(vendorName);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				return projectVersion != null;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_TYPE:
				return projectType != PROJECT_TYPE_EDEFAULT;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MAIN_MODULE:
				return MAIN_MODULE_EDEFAULT == null ? mainModule != null : !MAIN_MODULE_EDEFAULT.equals(mainModule);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				return extendedRuntimeEnvironment != null;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				return providedRuntimeLibraries != null && !providedRuntimeLibraries.isEmpty();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				return requiredRuntimeLibraries != null && !requiredRuntimeLibraries.isEmpty();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				return projectDependencies != null && !projectDependencies.isEmpty();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID:
				return IMPLEMENTATION_ID_EDEFAULT == null ? implementationId != null : !IMPLEMENTATION_ID_EDEFAULT.equals(implementationId);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				return implementedProjects != null && !implementedProjects.isEmpty();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				return initModules != null && !initModules.isEmpty();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				return execModule != null;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__OUTPUT_PATH:
				return OUTPUT_PATH_EDEFAULT == null ? outputPath != null : !OUTPUT_PATH_EDEFAULT.equals(outputPath);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__SOURCE_CONTAINERS:
				return sourceContainers != null && !sourceContainers.isEmpty();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				return moduleFilters != null && !moduleFilters.isEmpty();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				return testedProjects != null && !testedProjects.isEmpty();
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__MODULE_LOADER:
				return moduleLoader != MODULE_LOADER_EDEFAULT;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__DEFINES_PACKAGE:
				return DEFINES_PACKAGE_EDEFAULT == null ? definesPackage != null : !DEFINES_PACKAGE_EDEFAULT.equals(definesPackage);
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_NESTED_NODE_MODULES_FOLDER:
				return hasNestedNodeModulesFolder != HAS_NESTED_NODE_MODULES_FOLDER_EDEFAULT;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__HAS_N4JS_NATURE:
				return hasN4JSNature != HAS_N4JS_NATURE_EDEFAULT;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__YARN_WORKSPACE_ROOT:
				return yarnWorkspaceRoot != YARN_WORKSPACE_ROOT_EDEFAULT;
			case ProjectDescriptionPackage.PROJECT_DESCRIPTION__WORKSPACES:
				return workspaces != null && !workspaces.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (projectName: ");
		result.append(projectName);
		result.append(", vendorId: ");
		result.append(vendorId);
		result.append(", vendorName: ");
		result.append(vendorName);
		result.append(", projectType: ");
		result.append(projectType);
		result.append(", mainModule: ");
		result.append(mainModule);
		result.append(", implementationId: ");
		result.append(implementationId);
		result.append(", outputPath: ");
		result.append(outputPath);
		result.append(", moduleLoader: ");
		result.append(moduleLoader);
		result.append(", definesPackage: ");
		result.append(definesPackage);
		result.append(", hasNestedNodeModulesFolder: ");
		result.append(hasNestedNodeModulesFolder);
		result.append(", hasN4JSNature: ");
		result.append(hasN4JSNature);
		result.append(", yarnWorkspaceRoot: ");
		result.append(yarnWorkspaceRoot);
		result.append(", workspaces: ");
		result.append(workspaces);
		result.append(')');
		return result.toString();
	}

} //ProjectDescriptionImpl
