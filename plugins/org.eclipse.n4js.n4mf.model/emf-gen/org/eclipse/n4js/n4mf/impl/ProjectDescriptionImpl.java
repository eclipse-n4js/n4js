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

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals;

import org.eclipse.n4js.n4mf.BootstrapModule;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ExecModule;
import org.eclipse.n4js.n4mf.ExtendedRuntimeEnvironment;
import org.eclipse.n4js.n4mf.ImplementedProjects;
import org.eclipse.n4js.n4mf.InitModules;
import org.eclipse.n4js.n4mf.ModuleFilter;
import org.eclipse.n4js.n4mf.ModuleLoader;
import org.eclipse.n4js.n4mf.N4mfPackage;
import org.eclipse.n4js.n4mf.ProjectDependencies;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.ProjectReference;
import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries;
import org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency;
import org.eclipse.n4js.n4mf.RequiredRuntimeLibraries;
import org.eclipse.n4js.n4mf.RequiredRuntimeLibraryDependency;
import org.eclipse.n4js.n4mf.SourceFragment;
import org.eclipse.n4js.n4mf.TestedProject;
import org.eclipse.n4js.n4mf.TestedProjects;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Project Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getVendorName <em>Vendor Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getProjectVersion <em>Project Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getProjectType <em>Project Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getMainModule <em>Main Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getRequiredRuntimeLibraries <em>Required Runtime Libraries</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getProjectDependencies <em>Project Dependencies</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getImplementationId <em>Implementation Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getImplementedProjects <em>Implemented Projects</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getInitModules <em>Init Modules</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getExecModule <em>Exec Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getOutputPath <em>Output Path</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getLibraryPaths <em>Library Paths</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getResourcePaths <em>Resource Paths</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getSourceFragment <em>Source Fragment</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getModuleFilters <em>Module Filters</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getTestedProjects <em>Tested Projects</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.impl.ProjectDescriptionImpl#getModuleLoader <em>Module Loader</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProjectDescriptionImpl extends SimpleProjectDescriptionImpl implements ProjectDescription {
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
	protected DeclaredVersion projectVersion;

	/**
	 * The default value of the '{@link #getProjectType() <em>Project Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectType()
	 * @generated
	 * @ordered
	 */
	protected static final ProjectType PROJECT_TYPE_EDEFAULT = ProjectType.APPLICATION;

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
	protected ExtendedRuntimeEnvironment extendedRuntimeEnvironment;

	/**
	 * The cached value of the '{@link #getProvidedRuntimeLibraries() <em>Provided Runtime Libraries</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProvidedRuntimeLibraries()
	 * @generated
	 * @ordered
	 */
	protected ProvidedRuntimeLibraries providedRuntimeLibraries;

	/**
	 * The cached value of the '{@link #getRequiredRuntimeLibraries() <em>Required Runtime Libraries</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredRuntimeLibraries()
	 * @generated
	 * @ordered
	 */
	protected RequiredRuntimeLibraries requiredRuntimeLibraries;

	/**
	 * The cached value of the '{@link #getProjectDependencies() <em>Project Dependencies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectDependencies()
	 * @generated
	 * @ordered
	 */
	protected ProjectDependencies projectDependencies;

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
	 * The cached value of the '{@link #getImplementedProjects() <em>Implemented Projects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementedProjects()
	 * @generated
	 * @ordered
	 */
	protected ImplementedProjects implementedProjects;

	/**
	 * The cached value of the '{@link #getInitModules() <em>Init Modules</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitModules()
	 * @generated
	 * @ordered
	 */
	protected InitModules initModules;

	/**
	 * The cached value of the '{@link #getExecModule() <em>Exec Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExecModule()
	 * @generated
	 * @ordered
	 */
	protected ExecModule execModule;

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
	 * The cached value of the '{@link #getLibraryPaths() <em>Library Paths</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLibraryPaths()
	 * @generated
	 * @ordered
	 */
	protected EList<String> libraryPaths;

	/**
	 * The cached value of the '{@link #getResourcePaths() <em>Resource Paths</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourcePaths()
	 * @generated
	 * @ordered
	 */
	protected EList<String> resourcePaths;

	/**
	 * The cached value of the '{@link #getSourceFragment() <em>Source Fragment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFragment()
	 * @generated
	 * @ordered
	 */
	protected EList<SourceFragment> sourceFragment;

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
	 * The cached value of the '{@link #getTestedProjects() <em>Tested Projects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestedProjects()
	 * @generated
	 * @ordered
	 */
	protected TestedProjects testedProjects;

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
		return N4mfPackage.Literals.PROJECT_DESCRIPTION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__VENDOR_NAME, oldVendorName, vendorName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeclaredVersion getProjectVersion() {
		return projectVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProjectVersion(DeclaredVersion newProjectVersion, NotificationChain msgs) {
		DeclaredVersion oldProjectVersion = projectVersion;
		projectVersion = newProjectVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__PROJECT_VERSION, oldProjectVersion, newProjectVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectVersion(DeclaredVersion newProjectVersion) {
		if (newProjectVersion != projectVersion) {
			NotificationChain msgs = null;
			if (projectVersion != null)
				msgs = ((InternalEObject)projectVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__PROJECT_VERSION, null, msgs);
			if (newProjectVersion != null)
				msgs = ((InternalEObject)newProjectVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__PROJECT_VERSION, null, msgs);
			msgs = basicSetProjectVersion(newProjectVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__PROJECT_VERSION, newProjectVersion, newProjectVersion));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__PROJECT_TYPE, oldProjectType, projectType));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__MAIN_MODULE, oldMainModule, mainModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtendedRuntimeEnvironment getExtendedRuntimeEnvironment() {
		return extendedRuntimeEnvironment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtendedRuntimeEnvironment(ExtendedRuntimeEnvironment newExtendedRuntimeEnvironment, NotificationChain msgs) {
		ExtendedRuntimeEnvironment oldExtendedRuntimeEnvironment = extendedRuntimeEnvironment;
		extendedRuntimeEnvironment = newExtendedRuntimeEnvironment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT, oldExtendedRuntimeEnvironment, newExtendedRuntimeEnvironment);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendedRuntimeEnvironment(ExtendedRuntimeEnvironment newExtendedRuntimeEnvironment) {
		if (newExtendedRuntimeEnvironment != extendedRuntimeEnvironment) {
			NotificationChain msgs = null;
			if (extendedRuntimeEnvironment != null)
				msgs = ((InternalEObject)extendedRuntimeEnvironment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT, null, msgs);
			if (newExtendedRuntimeEnvironment != null)
				msgs = ((InternalEObject)newExtendedRuntimeEnvironment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT, null, msgs);
			msgs = basicSetExtendedRuntimeEnvironment(newExtendedRuntimeEnvironment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT, newExtendedRuntimeEnvironment, newExtendedRuntimeEnvironment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProvidedRuntimeLibraries getProvidedRuntimeLibraries() {
		return providedRuntimeLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProvidedRuntimeLibraries(ProvidedRuntimeLibraries newProvidedRuntimeLibraries, NotificationChain msgs) {
		ProvidedRuntimeLibraries oldProvidedRuntimeLibraries = providedRuntimeLibraries;
		providedRuntimeLibraries = newProvidedRuntimeLibraries;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES, oldProvidedRuntimeLibraries, newProvidedRuntimeLibraries);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvidedRuntimeLibraries(ProvidedRuntimeLibraries newProvidedRuntimeLibraries) {
		if (newProvidedRuntimeLibraries != providedRuntimeLibraries) {
			NotificationChain msgs = null;
			if (providedRuntimeLibraries != null)
				msgs = ((InternalEObject)providedRuntimeLibraries).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES, null, msgs);
			if (newProvidedRuntimeLibraries != null)
				msgs = ((InternalEObject)newProvidedRuntimeLibraries).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES, null, msgs);
			msgs = basicSetProvidedRuntimeLibraries(newProvidedRuntimeLibraries, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES, newProvidedRuntimeLibraries, newProvidedRuntimeLibraries));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredRuntimeLibraries getRequiredRuntimeLibraries() {
		return requiredRuntimeLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequiredRuntimeLibraries(RequiredRuntimeLibraries newRequiredRuntimeLibraries, NotificationChain msgs) {
		RequiredRuntimeLibraries oldRequiredRuntimeLibraries = requiredRuntimeLibraries;
		requiredRuntimeLibraries = newRequiredRuntimeLibraries;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES, oldRequiredRuntimeLibraries, newRequiredRuntimeLibraries);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredRuntimeLibraries(RequiredRuntimeLibraries newRequiredRuntimeLibraries) {
		if (newRequiredRuntimeLibraries != requiredRuntimeLibraries) {
			NotificationChain msgs = null;
			if (requiredRuntimeLibraries != null)
				msgs = ((InternalEObject)requiredRuntimeLibraries).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES, null, msgs);
			if (newRequiredRuntimeLibraries != null)
				msgs = ((InternalEObject)newRequiredRuntimeLibraries).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES, null, msgs);
			msgs = basicSetRequiredRuntimeLibraries(newRequiredRuntimeLibraries, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES, newRequiredRuntimeLibraries, newRequiredRuntimeLibraries));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectDependencies getProjectDependencies() {
		return projectDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProjectDependencies(ProjectDependencies newProjectDependencies, NotificationChain msgs) {
		ProjectDependencies oldProjectDependencies = projectDependencies;
		projectDependencies = newProjectDependencies;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES, oldProjectDependencies, newProjectDependencies);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectDependencies(ProjectDependencies newProjectDependencies) {
		if (newProjectDependencies != projectDependencies) {
			NotificationChain msgs = null;
			if (projectDependencies != null)
				msgs = ((InternalEObject)projectDependencies).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES, null, msgs);
			if (newProjectDependencies != null)
				msgs = ((InternalEObject)newProjectDependencies).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES, null, msgs);
			msgs = basicSetProjectDependencies(newProjectDependencies, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES, newProjectDependencies, newProjectDependencies));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID, oldImplementationId, implementationId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImplementedProjects getImplementedProjects() {
		return implementedProjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImplementedProjects(ImplementedProjects newImplementedProjects, NotificationChain msgs) {
		ImplementedProjects oldImplementedProjects = implementedProjects;
		implementedProjects = newImplementedProjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS, oldImplementedProjects, newImplementedProjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementedProjects(ImplementedProjects newImplementedProjects) {
		if (newImplementedProjects != implementedProjects) {
			NotificationChain msgs = null;
			if (implementedProjects != null)
				msgs = ((InternalEObject)implementedProjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS, null, msgs);
			if (newImplementedProjects != null)
				msgs = ((InternalEObject)newImplementedProjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS, null, msgs);
			msgs = basicSetImplementedProjects(newImplementedProjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS, newImplementedProjects, newImplementedProjects));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InitModules getInitModules() {
		return initModules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitModules(InitModules newInitModules, NotificationChain msgs) {
		InitModules oldInitModules = initModules;
		initModules = newInitModules;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__INIT_MODULES, oldInitModules, newInitModules);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitModules(InitModules newInitModules) {
		if (newInitModules != initModules) {
			NotificationChain msgs = null;
			if (initModules != null)
				msgs = ((InternalEObject)initModules).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__INIT_MODULES, null, msgs);
			if (newInitModules != null)
				msgs = ((InternalEObject)newInitModules).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__INIT_MODULES, null, msgs);
			msgs = basicSetInitModules(newInitModules, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__INIT_MODULES, newInitModules, newInitModules));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecModule getExecModule() {
		return execModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExecModule(ExecModule newExecModule, NotificationChain msgs) {
		ExecModule oldExecModule = execModule;
		execModule = newExecModule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__EXEC_MODULE, oldExecModule, newExecModule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecModule(ExecModule newExecModule) {
		if (newExecModule != execModule) {
			NotificationChain msgs = null;
			if (execModule != null)
				msgs = ((InternalEObject)execModule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__EXEC_MODULE, null, msgs);
			if (newExecModule != null)
				msgs = ((InternalEObject)newExecModule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__EXEC_MODULE, null, msgs);
			msgs = basicSetExecModule(newExecModule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__EXEC_MODULE, newExecModule, newExecModule));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__OUTPUT_PATH, oldOutputPath, outputPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getLibraryPaths() {
		if (libraryPaths == null) {
			libraryPaths = new EDataTypeEList<String>(String.class, this, N4mfPackage.PROJECT_DESCRIPTION__LIBRARY_PATHS);
		}
		return libraryPaths;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getResourcePaths() {
		if (resourcePaths == null) {
			resourcePaths = new EDataTypeEList<String>(String.class, this, N4mfPackage.PROJECT_DESCRIPTION__RESOURCE_PATHS);
		}
		return resourcePaths;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SourceFragment> getSourceFragment() {
		if (sourceFragment == null) {
			sourceFragment = new EObjectContainmentEList<SourceFragment>(SourceFragment.class, this, N4mfPackage.PROJECT_DESCRIPTION__SOURCE_FRAGMENT);
		}
		return sourceFragment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModuleFilter> getModuleFilters() {
		if (moduleFilters == null) {
			moduleFilters = new EObjectContainmentEList<ModuleFilter>(ModuleFilter.class, this, N4mfPackage.PROJECT_DESCRIPTION__MODULE_FILTERS);
		}
		return moduleFilters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestedProjects getTestedProjects() {
		return testedProjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTestedProjects(TestedProjects newTestedProjects, NotificationChain msgs) {
		TestedProjects oldTestedProjects = testedProjects;
		testedProjects = newTestedProjects;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS, oldTestedProjects, newTestedProjects);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTestedProjects(TestedProjects newTestedProjects) {
		if (newTestedProjects != testedProjects) {
			NotificationChain msgs = null;
			if (testedProjects != null)
				msgs = ((InternalEObject)testedProjects).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS, null, msgs);
			if (newTestedProjects != null)
				msgs = ((InternalEObject)newTestedProjects).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - N4mfPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS, null, msgs);
			msgs = basicSetTestedProjects(newTestedProjects, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS, newTestedProjects, newTestedProjects));
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
			eNotify(new ENotificationImpl(this, Notification.SET, N4mfPackage.PROJECT_DESCRIPTION__MODULE_LOADER, oldModuleLoader, moduleLoader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TestedProject> getAllTestedProjects() {
		EList<TestedProject> _xifexpression = null;
		TestedProjects _testedProjects = this.getTestedProjects();
		boolean _tripleEquals = (null == _testedProjects);
		if (_tripleEquals) {
			_xifexpression = XcoreCollectionLiterals.<TestedProject>emptyEList();
		}
		else {
			_xifexpression = this.getTestedProjects().getTestedProjects();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BootstrapModule> getAllInitModules() {
		EList<BootstrapModule> _xifexpression = null;
		InitModules _initModules = this.getInitModules();
		boolean _tripleEquals = (null == _initModules);
		if (_tripleEquals) {
			_xifexpression = XcoreCollectionLiterals.<BootstrapModule>emptyEList();
		}
		else {
			_xifexpression = this.getInitModules().getInitModules();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectReference> getAllImplementedProjects() {
		EList<ProjectReference> _xifexpression = null;
		ImplementedProjects _implementedProjects = this.getImplementedProjects();
		boolean _tripleEquals = (null == _implementedProjects);
		if (_tripleEquals) {
			_xifexpression = XcoreCollectionLiterals.<ProjectReference>emptyEList();
		}
		else {
			_xifexpression = this.getImplementedProjects().getImplementedProjects();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProjectDependency> getAllProjectDependencies() {
		EList<ProjectDependency> _xifexpression = null;
		ProjectDependencies _projectDependencies = this.getProjectDependencies();
		boolean _tripleEquals = (null == _projectDependencies);
		if (_tripleEquals) {
			_xifexpression = XcoreCollectionLiterals.<ProjectDependency>emptyEList();
		}
		else {
			_xifexpression = this.getProjectDependencies().getProjectDependencies();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProvidedRuntimeLibraryDependency> getAllProvidedRuntimeLibraries() {
		EList<ProvidedRuntimeLibraryDependency> _xifexpression = null;
		ProvidedRuntimeLibraries _providedRuntimeLibraries = this.getProvidedRuntimeLibraries();
		boolean _tripleEquals = (null == _providedRuntimeLibraries);
		if (_tripleEquals) {
			_xifexpression = XcoreCollectionLiterals.<ProvidedRuntimeLibraryDependency>emptyEList();
		}
		else {
			_xifexpression = this.getProvidedRuntimeLibraries().getProvidedRuntimeLibraries();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RequiredRuntimeLibraryDependency> getAllRequiredRuntimeLibraries() {
		EList<RequiredRuntimeLibraryDependency> _xifexpression = null;
		RequiredRuntimeLibraries _requiredRuntimeLibraries = this.getRequiredRuntimeLibraries();
		boolean _tripleEquals = (null == _requiredRuntimeLibraries);
		if (_tripleEquals) {
			_xifexpression = XcoreCollectionLiterals.<RequiredRuntimeLibraryDependency>emptyEList();
		}
		else {
			_xifexpression = this.getRequiredRuntimeLibraries().getRequiredRuntimeLibraries();
		}
		return _xifexpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				return basicSetProjectVersion(null, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				return basicSetExtendedRuntimeEnvironment(null, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				return basicSetProvidedRuntimeLibraries(null, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				return basicSetRequiredRuntimeLibraries(null, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				return basicSetProjectDependencies(null, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				return basicSetImplementedProjects(null, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				return basicSetInitModules(null, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				return basicSetExecModule(null, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__SOURCE_FRAGMENT:
				return ((InternalEList<?>)getSourceFragment()).basicRemove(otherEnd, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				return ((InternalEList<?>)getModuleFilters()).basicRemove(otherEnd, msgs);
			case N4mfPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				return basicSetTestedProjects(null, msgs);
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
			case N4mfPackage.PROJECT_DESCRIPTION__VENDOR_NAME:
				return getVendorName();
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				return getProjectVersion();
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_TYPE:
				return getProjectType();
			case N4mfPackage.PROJECT_DESCRIPTION__MAIN_MODULE:
				return getMainModule();
			case N4mfPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				return getExtendedRuntimeEnvironment();
			case N4mfPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				return getProvidedRuntimeLibraries();
			case N4mfPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				return getRequiredRuntimeLibraries();
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				return getProjectDependencies();
			case N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID:
				return getImplementationId();
			case N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				return getImplementedProjects();
			case N4mfPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				return getInitModules();
			case N4mfPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				return getExecModule();
			case N4mfPackage.PROJECT_DESCRIPTION__OUTPUT_PATH:
				return getOutputPath();
			case N4mfPackage.PROJECT_DESCRIPTION__LIBRARY_PATHS:
				return getLibraryPaths();
			case N4mfPackage.PROJECT_DESCRIPTION__RESOURCE_PATHS:
				return getResourcePaths();
			case N4mfPackage.PROJECT_DESCRIPTION__SOURCE_FRAGMENT:
				return getSourceFragment();
			case N4mfPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				return getModuleFilters();
			case N4mfPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				return getTestedProjects();
			case N4mfPackage.PROJECT_DESCRIPTION__MODULE_LOADER:
				return getModuleLoader();
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
			case N4mfPackage.PROJECT_DESCRIPTION__VENDOR_NAME:
				setVendorName((String)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				setProjectVersion((DeclaredVersion)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_TYPE:
				setProjectType((ProjectType)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__MAIN_MODULE:
				setMainModule((String)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				setExtendedRuntimeEnvironment((ExtendedRuntimeEnvironment)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				setProvidedRuntimeLibraries((ProvidedRuntimeLibraries)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				setRequiredRuntimeLibraries((RequiredRuntimeLibraries)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				setProjectDependencies((ProjectDependencies)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID:
				setImplementationId((String)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				setImplementedProjects((ImplementedProjects)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				setInitModules((InitModules)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				setExecModule((ExecModule)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__OUTPUT_PATH:
				setOutputPath((String)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__LIBRARY_PATHS:
				getLibraryPaths().clear();
				getLibraryPaths().addAll((Collection<? extends String>)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__RESOURCE_PATHS:
				getResourcePaths().clear();
				getResourcePaths().addAll((Collection<? extends String>)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__SOURCE_FRAGMENT:
				getSourceFragment().clear();
				getSourceFragment().addAll((Collection<? extends SourceFragment>)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				getModuleFilters().clear();
				getModuleFilters().addAll((Collection<? extends ModuleFilter>)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				setTestedProjects((TestedProjects)newValue);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__MODULE_LOADER:
				setModuleLoader((ModuleLoader)newValue);
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
			case N4mfPackage.PROJECT_DESCRIPTION__VENDOR_NAME:
				setVendorName(VENDOR_NAME_EDEFAULT);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				setProjectVersion((DeclaredVersion)null);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_TYPE:
				setProjectType(PROJECT_TYPE_EDEFAULT);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__MAIN_MODULE:
				setMainModule(MAIN_MODULE_EDEFAULT);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				setExtendedRuntimeEnvironment((ExtendedRuntimeEnvironment)null);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				setProvidedRuntimeLibraries((ProvidedRuntimeLibraries)null);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				setRequiredRuntimeLibraries((RequiredRuntimeLibraries)null);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				setProjectDependencies((ProjectDependencies)null);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID:
				setImplementationId(IMPLEMENTATION_ID_EDEFAULT);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				setImplementedProjects((ImplementedProjects)null);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				setInitModules((InitModules)null);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				setExecModule((ExecModule)null);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__OUTPUT_PATH:
				setOutputPath(OUTPUT_PATH_EDEFAULT);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__LIBRARY_PATHS:
				getLibraryPaths().clear();
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__RESOURCE_PATHS:
				getResourcePaths().clear();
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__SOURCE_FRAGMENT:
				getSourceFragment().clear();
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				getModuleFilters().clear();
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				setTestedProjects((TestedProjects)null);
				return;
			case N4mfPackage.PROJECT_DESCRIPTION__MODULE_LOADER:
				setModuleLoader(MODULE_LOADER_EDEFAULT);
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
			case N4mfPackage.PROJECT_DESCRIPTION__VENDOR_NAME:
				return VENDOR_NAME_EDEFAULT == null ? vendorName != null : !VENDOR_NAME_EDEFAULT.equals(vendorName);
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_VERSION:
				return projectVersion != null;
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_TYPE:
				return projectType != PROJECT_TYPE_EDEFAULT;
			case N4mfPackage.PROJECT_DESCRIPTION__MAIN_MODULE:
				return MAIN_MODULE_EDEFAULT == null ? mainModule != null : !MAIN_MODULE_EDEFAULT.equals(mainModule);
			case N4mfPackage.PROJECT_DESCRIPTION__EXTENDED_RUNTIME_ENVIRONMENT:
				return extendedRuntimeEnvironment != null;
			case N4mfPackage.PROJECT_DESCRIPTION__PROVIDED_RUNTIME_LIBRARIES:
				return providedRuntimeLibraries != null;
			case N4mfPackage.PROJECT_DESCRIPTION__REQUIRED_RUNTIME_LIBRARIES:
				return requiredRuntimeLibraries != null;
			case N4mfPackage.PROJECT_DESCRIPTION__PROJECT_DEPENDENCIES:
				return projectDependencies != null;
			case N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTATION_ID:
				return IMPLEMENTATION_ID_EDEFAULT == null ? implementationId != null : !IMPLEMENTATION_ID_EDEFAULT.equals(implementationId);
			case N4mfPackage.PROJECT_DESCRIPTION__IMPLEMENTED_PROJECTS:
				return implementedProjects != null;
			case N4mfPackage.PROJECT_DESCRIPTION__INIT_MODULES:
				return initModules != null;
			case N4mfPackage.PROJECT_DESCRIPTION__EXEC_MODULE:
				return execModule != null;
			case N4mfPackage.PROJECT_DESCRIPTION__OUTPUT_PATH:
				return OUTPUT_PATH_EDEFAULT == null ? outputPath != null : !OUTPUT_PATH_EDEFAULT.equals(outputPath);
			case N4mfPackage.PROJECT_DESCRIPTION__LIBRARY_PATHS:
				return libraryPaths != null && !libraryPaths.isEmpty();
			case N4mfPackage.PROJECT_DESCRIPTION__RESOURCE_PATHS:
				return resourcePaths != null && !resourcePaths.isEmpty();
			case N4mfPackage.PROJECT_DESCRIPTION__SOURCE_FRAGMENT:
				return sourceFragment != null && !sourceFragment.isEmpty();
			case N4mfPackage.PROJECT_DESCRIPTION__MODULE_FILTERS:
				return moduleFilters != null && !moduleFilters.isEmpty();
			case N4mfPackage.PROJECT_DESCRIPTION__TESTED_PROJECTS:
				return testedProjects != null;
			case N4mfPackage.PROJECT_DESCRIPTION__MODULE_LOADER:
				return moduleLoader != MODULE_LOADER_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case N4mfPackage.PROJECT_DESCRIPTION___GET_ALL_TESTED_PROJECTS:
				return getAllTestedProjects();
			case N4mfPackage.PROJECT_DESCRIPTION___GET_ALL_INIT_MODULES:
				return getAllInitModules();
			case N4mfPackage.PROJECT_DESCRIPTION___GET_ALL_IMPLEMENTED_PROJECTS:
				return getAllImplementedProjects();
			case N4mfPackage.PROJECT_DESCRIPTION___GET_ALL_PROJECT_DEPENDENCIES:
				return getAllProjectDependencies();
			case N4mfPackage.PROJECT_DESCRIPTION___GET_ALL_PROVIDED_RUNTIME_LIBRARIES:
				return getAllProvidedRuntimeLibraries();
			case N4mfPackage.PROJECT_DESCRIPTION___GET_ALL_REQUIRED_RUNTIME_LIBRARIES:
				return getAllRequiredRuntimeLibraries();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (vendorName: ");
		result.append(vendorName);
		result.append(", projectType: ");
		result.append(projectType);
		result.append(", mainModule: ");
		result.append(mainModule);
		result.append(", implementationId: ");
		result.append(implementationId);
		result.append(", outputPath: ");
		result.append(outputPath);
		result.append(", libraryPaths: ");
		result.append(libraryPaths);
		result.append(", resourcePaths: ");
		result.append(resourcePaths);
		result.append(", moduleLoader: ");
		result.append(moduleLoader);
		result.append(')');
		return result.toString();
	}

} //ProjectDescriptionImpl
