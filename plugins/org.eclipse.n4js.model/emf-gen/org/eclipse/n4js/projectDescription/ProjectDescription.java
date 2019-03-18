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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.n4js.semver.Semver.VersionNumber;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Basic information about a project, as read from the {@code package.json}
 * file in the project's root folder.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getVendorId <em>Vendor Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getVendorName <em>Vendor Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectVersion <em>Project Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectType <em>Project Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getMainModule <em>Main Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getRequiredRuntimeLibraries <em>Required Runtime Libraries</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectDependencies <em>Project Dependencies</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getImplementationId <em>Implementation Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getImplementedProjects <em>Implemented Projects</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getInitModules <em>Init Modules</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getExecModule <em>Exec Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getOutputPath <em>Output Path</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getSourceContainers <em>Source Containers</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getModuleFilters <em>Module Filters</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getTestedProjects <em>Tested Projects</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getModuleLoader <em>Module Loader</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getDefinesPackage <em>Defines Package</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#isHasNestedNodeModulesFolder <em>Has Nested Node Modules Folder</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#isHasN4JSNature <em>Has N4JS Nature</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#isYarnWorkspaceRoot <em>Yarn Workspace Root</em>}</li>
 *   <li>{@link org.eclipse.n4js.projectDescription.ProjectDescription#getWorkspaces <em>Workspaces</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription()
 * @model
 * @generated
 */
public interface ProjectDescription extends EObject {
	/**
	 * Returns the value of the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The <em>N4JS project name</em>, as defined at
	 * {@link org.eclipse.n4js.utils.ProjectDescriptionUtils#isProjectNameWithScope(String)}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Project Name</em>' attribute.
	 * @see #setProjectName(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ProjectName()
	 * @model unique="false"
	 * @generated
	 */
	String getProjectName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectName <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Name</em>' attribute.
	 * @see #getProjectName()
	 * @generated
	 */
	void setProjectName(String value);

	/**
	 * Returns the value of the '<em><b>Vendor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vendor Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vendor Id</em>' attribute.
	 * @see #setVendorId(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_VendorId()
	 * @model unique="false"
	 * @generated
	 */
	String getVendorId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getVendorId <em>Vendor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vendor Id</em>' attribute.
	 * @see #getVendorId()
	 * @generated
	 */
	void setVendorId(String value);

	/**
	 * Returns the value of the '<em><b>Vendor Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vendor Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vendor Name</em>' attribute.
	 * @see #setVendorName(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_VendorName()
	 * @model unique="false"
	 * @generated
	 */
	String getVendorName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getVendorName <em>Vendor Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vendor Name</em>' attribute.
	 * @see #getVendorName()
	 * @generated
	 */
	void setVendorName(String value);

	/**
	 * Returns the value of the '<em><b>Project Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Version</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Version</em>' containment reference.
	 * @see #setProjectVersion(VersionNumber)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ProjectVersion()
	 * @model containment="true"
	 * @generated
	 */
	VersionNumber getProjectVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectVersion <em>Project Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Version</em>' containment reference.
	 * @see #getProjectVersion()
	 * @generated
	 */
	void setProjectVersion(VersionNumber value);

	/**
	 * Returns the value of the '<em><b>Project Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.projectDescription.ProjectType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Type</em>' attribute.
	 * @see org.eclipse.n4js.projectDescription.ProjectType
	 * @see #setProjectType(ProjectType)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ProjectType()
	 * @model unique="false"
	 * @generated
	 */
	ProjectType getProjectType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getProjectType <em>Project Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Type</em>' attribute.
	 * @see org.eclipse.n4js.projectDescription.ProjectType
	 * @see #getProjectType()
	 * @generated
	 */
	void setProjectType(ProjectType value);

	/**
	 * Returns the value of the '<em><b>Main Module</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Main Module</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Main Module</em>' attribute.
	 * @see #setMainModule(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_MainModule()
	 * @model unique="false"
	 * @generated
	 */
	String getMainModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getMainModule <em>Main Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Main Module</em>' attribute.
	 * @see #getMainModule()
	 * @generated
	 */
	void setMainModule(String value);

	/**
	 * Returns the value of the '<em><b>Extended Runtime Environment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Runtime Environment</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Runtime Environment</em>' containment reference.
	 * @see #setExtendedRuntimeEnvironment(ProjectReference)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ExtendedRuntimeEnvironment()
	 * @model containment="true"
	 * @generated
	 */
	ProjectReference getExtendedRuntimeEnvironment();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Runtime Environment</em>' containment reference.
	 * @see #getExtendedRuntimeEnvironment()
	 * @generated
	 */
	void setExtendedRuntimeEnvironment(ProjectReference value);

	/**
	 * Returns the value of the '<em><b>Provided Runtime Libraries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.projectDescription.ProjectReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Runtime Libraries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Runtime Libraries</em>' containment reference list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ProvidedRuntimeLibraries()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectReference> getProvidedRuntimeLibraries();

	/**
	 * Returns the value of the '<em><b>Required Runtime Libraries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.projectDescription.ProjectReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Runtime Libraries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Runtime Libraries</em>' containment reference list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_RequiredRuntimeLibraries()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectReference> getRequiredRuntimeLibraries();

	/**
	 * Returns the value of the '<em><b>Project Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.projectDescription.ProjectDependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Dependencies</em>' containment reference list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ProjectDependencies()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectDependency> getProjectDependencies();

	/**
	 * Returns the value of the '<em><b>Implementation Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementation Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementation Id</em>' attribute.
	 * @see #setImplementationId(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ImplementationId()
	 * @model unique="false"
	 * @generated
	 */
	String getImplementationId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getImplementationId <em>Implementation Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation Id</em>' attribute.
	 * @see #getImplementationId()
	 * @generated
	 */
	void setImplementationId(String value);

	/**
	 * Returns the value of the '<em><b>Implemented Projects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.projectDescription.ProjectReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implemented Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implemented Projects</em>' containment reference list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ImplementedProjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectReference> getImplementedProjects();

	/**
	 * Returns the value of the '<em><b>Init Modules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.projectDescription.BootstrapModule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Modules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Modules</em>' containment reference list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_InitModules()
	 * @model containment="true"
	 * @generated
	 */
	EList<BootstrapModule> getInitModules();

	/**
	 * Returns the value of the '<em><b>Exec Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exec Module</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exec Module</em>' containment reference.
	 * @see #setExecModule(BootstrapModule)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ExecModule()
	 * @model containment="true"
	 * @generated
	 */
	BootstrapModule getExecModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getExecModule <em>Exec Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exec Module</em>' containment reference.
	 * @see #getExecModule()
	 * @generated
	 */
	void setExecModule(BootstrapModule value);

	/**
	 * Returns the value of the '<em><b>Output Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Path</em>' attribute.
	 * @see #setOutputPath(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_OutputPath()
	 * @model unique="false"
	 * @generated
	 */
	String getOutputPath();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getOutputPath <em>Output Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Path</em>' attribute.
	 * @see #getOutputPath()
	 * @generated
	 */
	void setOutputPath(String value);

	/**
	 * Returns the value of the '<em><b>Source Containers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.projectDescription.SourceContainerDescription}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Containers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Containers</em>' containment reference list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_SourceContainers()
	 * @model containment="true"
	 * @generated
	 */
	EList<SourceContainerDescription> getSourceContainers();

	/**
	 * Returns the value of the '<em><b>Module Filters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.projectDescription.ModuleFilter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Filters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Filters</em>' containment reference list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ModuleFilters()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModuleFilter> getModuleFilters();

	/**
	 * Returns the value of the '<em><b>Tested Projects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.projectDescription.ProjectReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tested Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tested Projects</em>' containment reference list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_TestedProjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectReference> getTestedProjects();

	/**
	 * Returns the value of the '<em><b>Module Loader</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.projectDescription.ModuleLoader}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Loader</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Loader</em>' attribute.
	 * @see org.eclipse.n4js.projectDescription.ModuleLoader
	 * @see #setModuleLoader(ModuleLoader)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_ModuleLoader()
	 * @model unique="false"
	 * @generated
	 */
	ModuleLoader getModuleLoader();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getModuleLoader <em>Module Loader</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Loader</em>' attribute.
	 * @see org.eclipse.n4js.projectDescription.ModuleLoader
	 * @see #getModuleLoader()
	 * @generated
	 */
	void setModuleLoader(ModuleLoader value);

	/**
	 * Returns the value of the '<em><b>Defines Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 *  Returns the name of the package the project provides type definitions for.
	 * 	 * {@code null} if this project does not specify the property (i.e. not a
	 * type definitions project (cf. {@link ProjectType#DEFINITION}).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Defines Package</em>' attribute.
	 * @see #setDefinesPackage(String)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_DefinesPackage()
	 * @model unique="false"
	 * @generated
	 */
	String getDefinesPackage();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#getDefinesPackage <em>Defines Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Defines Package</em>' attribute.
	 * @see #getDefinesPackage()
	 * @generated
	 */
	void setDefinesPackage(String value);

	/**
	 * Returns the value of the '<em><b>Has Nested Node Modules Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells if the project represented by this project description has a nested
	 * "node_modules" folder, i.e. a folder named "node_modules" located right next
	 * to the package.json file.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Has Nested Node Modules Folder</em>' attribute.
	 * @see #setHasNestedNodeModulesFolder(boolean)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_HasNestedNodeModulesFolder()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasNestedNodeModulesFolder();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#isHasNestedNodeModulesFolder <em>Has Nested Node Modules Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Nested Node Modules Folder</em>' attribute.
	 * @see #isHasNestedNodeModulesFolder()
	 * @generated
	 */
	void setHasNestedNodeModulesFolder(boolean value);

	/**
	 * Returns the value of the '<em><b>Has N4JS Nature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates whether the underlying project description explicitly configured
	 * the project to be an N4JS project (e.g. includes n4js section).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Has N4JS Nature</em>' attribute.
	 * @see #setHasN4JSNature(boolean)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_HasN4JSNature()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasN4JSNature();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#isHasN4JSNature <em>Has N4JS Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has N4JS Nature</em>' attribute.
	 * @see #isHasN4JSNature()
	 * @generated
	 */
	void setHasN4JSNature(boolean value);

	/**
	 * Returns the value of the '<em><b>Yarn Workspace Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Tells whether the project represented by this project description is the root of a yarn workspace.
	 * This flag will be {@code true} iff the package.json contains yarn's top-level property "workspaces",
	 * no matter the value (i.e. will be {@code true} even if the value is the empty array).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Yarn Workspace Root</em>' attribute.
	 * @see #setYarnWorkspaceRoot(boolean)
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_YarnWorkspaceRoot()
	 * @model unique="false"
	 * @generated
	 */
	boolean isYarnWorkspaceRoot();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.projectDescription.ProjectDescription#isYarnWorkspaceRoot <em>Yarn Workspace Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Yarn Workspace Root</em>' attribute.
	 * @see #isYarnWorkspaceRoot()
	 * @generated
	 */
	void setYarnWorkspaceRoot(boolean value);

	/**
	 * Returns the value of the '<em><b>Workspaces</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Value of top-level property "workspaces" in package.json, used by yarn to denote the contained projects.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Workspaces</em>' attribute list.
	 * @see org.eclipse.n4js.projectDescription.ProjectDescriptionPackage#getProjectDescription_Workspaces()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getWorkspaces();

} // ProjectDescription
