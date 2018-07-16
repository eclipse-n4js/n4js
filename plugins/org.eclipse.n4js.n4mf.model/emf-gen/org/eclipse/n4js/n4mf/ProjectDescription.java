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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectId <em>Project Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getVendorId <em>Vendor Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getVendorName <em>Vendor Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectVersion <em>Project Version</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectType <em>Project Type</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getMainModule <em>Main Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getRequiredRuntimeLibraries <em>Required Runtime Libraries</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectDependencies <em>Project Dependencies</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getImplementationId <em>Implementation Id</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getImplementedProjects <em>Implemented Projects</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getInitModules <em>Init Modules</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getExecModule <em>Exec Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getOutputPathRaw <em>Output Path Raw</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getLibraryPathsRaw <em>Library Paths Raw</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getResourcePathsRaw <em>Resource Paths Raw</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getSourceContainers <em>Source Containers</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getModuleFilters <em>Module Filters</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getTestedProjects <em>Tested Projects</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getModuleLoader <em>Module Loader</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#isHasNestedNodeModulesFolder <em>Has Nested Node Modules Folder</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#isHasN4JSNature <em>Has N4JS Nature</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription()
 * @model
 * @generated
 */
public interface ProjectDescription extends EObject {
	/**
	 * Returns the value of the '<em><b>Project Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Id</em>' attribute.
	 * @see #setProjectId(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ProjectId()
	 * @model unique="false"
	 * @generated
	 */
	String getProjectId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectId <em>Project Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Id</em>' attribute.
	 * @see #getProjectId()
	 * @generated
	 */
	void setProjectId(String value);

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
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_VendorId()
	 * @model unique="false"
	 * @generated
	 */
	String getVendorId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getVendorId <em>Vendor Id</em>}' attribute.
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
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_VendorName()
	 * @model unique="false"
	 * @generated
	 */
	String getVendorName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getVendorName <em>Vendor Name</em>}' attribute.
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
	 * @see #setProjectVersion(DeclaredVersion)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ProjectVersion()
	 * @model containment="true"
	 * @generated
	 */
	DeclaredVersion getProjectVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectVersion <em>Project Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Version</em>' containment reference.
	 * @see #getProjectVersion()
	 * @generated
	 */
	void setProjectVersion(DeclaredVersion value);

	/**
	 * Returns the value of the '<em><b>Project Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4mf.ProjectType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Type</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.ProjectType
	 * @see #setProjectType(ProjectType)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ProjectType()
	 * @model unique="false"
	 * @generated
	 */
	ProjectType getProjectType();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectType <em>Project Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Type</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.ProjectType
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
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_MainModule()
	 * @model unique="false"
	 * @generated
	 */
	String getMainModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getMainModule <em>Main Module</em>}' attribute.
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
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ExtendedRuntimeEnvironment()
	 * @model containment="true"
	 * @generated
	 */
	ProjectReference getExtendedRuntimeEnvironment();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Runtime Environment</em>' containment reference.
	 * @see #getExtendedRuntimeEnvironment()
	 * @generated
	 */
	void setExtendedRuntimeEnvironment(ProjectReference value);

	/**
	 * Returns the value of the '<em><b>Provided Runtime Libraries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ProjectReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Runtime Libraries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Runtime Libraries</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ProvidedRuntimeLibraries()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectReference> getProvidedRuntimeLibraries();

	/**
	 * Returns the value of the '<em><b>Required Runtime Libraries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ProjectReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Runtime Libraries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Runtime Libraries</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_RequiredRuntimeLibraries()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectReference> getRequiredRuntimeLibraries();

	/**
	 * Returns the value of the '<em><b>Project Dependencies</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ProjectDependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Dependencies</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Dependencies</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ProjectDependencies()
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
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ImplementationId()
	 * @model unique="false"
	 * @generated
	 */
	String getImplementationId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getImplementationId <em>Implementation Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementation Id</em>' attribute.
	 * @see #getImplementationId()
	 * @generated
	 */
	void setImplementationId(String value);

	/**
	 * Returns the value of the '<em><b>Implemented Projects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ProjectReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implemented Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implemented Projects</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ImplementedProjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectReference> getImplementedProjects();

	/**
	 * Returns the value of the '<em><b>Init Modules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.BootstrapModule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Modules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Modules</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_InitModules()
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
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ExecModule()
	 * @model containment="true"
	 * @generated
	 */
	BootstrapModule getExecModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getExecModule <em>Exec Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exec Module</em>' containment reference.
	 * @see #getExecModule()
	 * @generated
	 */
	void setExecModule(BootstrapModule value);

	/**
	 * Returns the value of the '<em><b>Output Path Raw</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Path Raw</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Path Raw</em>' attribute.
	 * @see #setOutputPathRaw(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_OutputPathRaw()
	 * @model unique="false"
	 * @generated
	 */
	String getOutputPathRaw();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getOutputPathRaw <em>Output Path Raw</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Path Raw</em>' attribute.
	 * @see #getOutputPathRaw()
	 * @generated
	 */
	void setOutputPathRaw(String value);

	/**
	 * Returns the value of the '<em><b>Library Paths Raw</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library Paths Raw</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library Paths Raw</em>' attribute list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_LibraryPathsRaw()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getLibraryPathsRaw();

	/**
	 * Returns the value of the '<em><b>Resource Paths Raw</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Paths Raw</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Paths Raw</em>' attribute list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ResourcePathsRaw()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getResourcePathsRaw();

	/**
	 * Returns the value of the '<em><b>Source Containers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.SourceContainerDescription}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Containers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Containers</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_SourceContainers()
	 * @model containment="true"
	 * @generated
	 */
	EList<SourceContainerDescription> getSourceContainers();

	/**
	 * Returns the value of the '<em><b>Module Filters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ModuleFilter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Filters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Filters</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ModuleFilters()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModuleFilter> getModuleFilters();

	/**
	 * Returns the value of the '<em><b>Tested Projects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ProjectDependency}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tested Projects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tested Projects</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_TestedProjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProjectDependency> getTestedProjects();

	/**
	 * Returns the value of the '<em><b>Module Loader</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.n4js.n4mf.ModuleLoader}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Loader</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Loader</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.ModuleLoader
	 * @see #setModuleLoader(ModuleLoader)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ModuleLoader()
	 * @model unique="false"
	 * @generated
	 */
	ModuleLoader getModuleLoader();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getModuleLoader <em>Module Loader</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Loader</em>' attribute.
	 * @see org.eclipse.n4js.n4mf.ModuleLoader
	 * @see #getModuleLoader()
	 * @generated
	 */
	void setModuleLoader(ModuleLoader value);

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
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_HasNestedNodeModulesFolder()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasNestedNodeModulesFolder();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#isHasNestedNodeModulesFolder <em>Has Nested Node Modules Folder</em>}' attribute.
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
	 * *
	 * Indicates whether the underlying project description explicitly configured
	 * the project to be an N4JS project (e.g. includes n4js section).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Has N4JS Nature</em>' attribute.
	 * @see #setHasN4JSNature(boolean)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_HasN4JSNature()
	 * @model unique="false"
	 * @generated
	 */
	boolean isHasN4JSNature();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#isHasN4JSNature <em>Has N4JS Nature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has N4JS Nature</em>' attribute.
	 * @see #isHasN4JSNature()
	 * @generated
	 */
	void setHasN4JSNature(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return &lt;%org.eclipse.n4js.utils.io.FileUtils%&gt;.normalizeDotWhenEmpty(this.getOutputPathRaw());'"
	 * @generated
	 */
	String getOutputPath();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model newOutputPathUnique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='this.setOutputPathRaw(newOutputPath);'"
	 * @generated
	 */
	void setOutputPath(String newOutputPath);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='int _length = ((&lt;%java.lang.Object%&gt;[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(this.getLibraryPathsRaw(), &lt;%java.lang.Object%&gt;.class)).length;\nfinal &lt;%org.eclipse.emf.common.util.BasicEList%&gt;&lt;&lt;%java.lang.String%&gt;&gt; paths = new &lt;%org.eclipse.emf.common.util.BasicEList%&gt;&lt;&lt;%java.lang.String%&gt;&gt;(_length);\n&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%java.lang.String%&gt;&gt; _libraryPathsRaw = this.getLibraryPathsRaw();\nfor (final &lt;%java.lang.String%&gt; pathRaw : _libraryPathsRaw)\n{\n\t{\n\t\tfinal &lt;%java.lang.String%&gt; normalizedPath = &lt;%org.eclipse.n4js.utils.io.FileUtils%&gt;.normalizeDotWhenEmpty(pathRaw);\n\t\tpaths.add(normalizedPath);\n\t}\n}\nreturn paths;'"
	 * @generated
	 */
	EList<String> getLibraryPaths();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='int _length = ((&lt;%java.lang.Object%&gt;[])org.eclipse.xtext.xbase.lib.Conversions.unwrapArray(this.getResourcePathsRaw(), &lt;%java.lang.Object%&gt;.class)).length;\nfinal &lt;%org.eclipse.emf.common.util.BasicEList%&gt;&lt;&lt;%java.lang.String%&gt;&gt; paths = new &lt;%org.eclipse.emf.common.util.BasicEList%&gt;&lt;&lt;%java.lang.String%&gt;&gt;(_length);\n&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%java.lang.String%&gt;&gt; _resourcePathsRaw = this.getResourcePathsRaw();\nfor (final &lt;%java.lang.String%&gt; pathRaw : _resourcePathsRaw)\n{\n\t{\n\t\tfinal &lt;%java.lang.String%&gt; normalizedPath = &lt;%org.eclipse.n4js.utils.io.FileUtils%&gt;.normalizeDotWhenEmpty(pathRaw);\n\t\tpaths.add(normalizedPath);\n\t}\n}\nreturn paths;'"
	 * @generated
	 */
	EList<String> getResourcePaths();

} // ProjectDescription
