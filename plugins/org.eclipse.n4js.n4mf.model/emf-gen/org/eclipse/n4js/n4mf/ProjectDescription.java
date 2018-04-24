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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Project Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * Describes a project by making it identifiable by projectId, vendorId and
 * declared version.
 *  * The project type defines how a project should be bundled.
 *  * Project paths decide about which resources should be bundled and be visible
 * to other projects that list this project as dependency. So e.g. the files
 * contained in a declared source folder will be in scope for a project that
 * has this project as a test scoped dependency.
 *  * The listed dependencies lists all projects this project depends on. A dependency
 * is categorized to be only while testing or at runtime. This plays together with
 * projects paths (source or test).
 *  * To be later validated
 * - validate if manifest is in project
 * - check if file is in root folder
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
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
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getOutputPath <em>Output Path</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getLibraryPaths <em>Library Paths</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getResourcePaths <em>Resource Paths</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getSourceFragment <em>Source Fragment</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getModuleFilters <em>Module Filters</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getTestedProjects <em>Tested Projects</em>}</li>
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getModuleLoader <em>Module Loader</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription()
 * @model
 * @generated
 */
public interface ProjectDescription extends SimpleProjectDescription {
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
	 * @see #setExtendedRuntimeEnvironment(ExtendedRuntimeEnvironment)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ExtendedRuntimeEnvironment()
	 * @model containment="true"
	 * @generated
	 */
	ExtendedRuntimeEnvironment getExtendedRuntimeEnvironment();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getExtendedRuntimeEnvironment <em>Extended Runtime Environment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Runtime Environment</em>' containment reference.
	 * @see #getExtendedRuntimeEnvironment()
	 * @generated
	 */
	void setExtendedRuntimeEnvironment(ExtendedRuntimeEnvironment value);

	/**
	 * Returns the value of the '<em><b>Provided Runtime Libraries</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provided Runtime Libraries</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provided Runtime Libraries</em>' containment reference.
	 * @see #setProvidedRuntimeLibraries(ProvidedRuntimeLibraries)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ProvidedRuntimeLibraries()
	 * @model containment="true"
	 * @generated
	 */
	ProvidedRuntimeLibraries getProvidedRuntimeLibraries();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProvidedRuntimeLibraries <em>Provided Runtime Libraries</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provided Runtime Libraries</em>' containment reference.
	 * @see #getProvidedRuntimeLibraries()
	 * @generated
	 */
	void setProvidedRuntimeLibraries(ProvidedRuntimeLibraries value);

	/**
	 * Returns the value of the '<em><b>Required Runtime Libraries</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Runtime Libraries</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Runtime Libraries</em>' containment reference.
	 * @see #setRequiredRuntimeLibraries(RequiredRuntimeLibraries)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_RequiredRuntimeLibraries()
	 * @model containment="true"
	 * @generated
	 */
	RequiredRuntimeLibraries getRequiredRuntimeLibraries();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getRequiredRuntimeLibraries <em>Required Runtime Libraries</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Runtime Libraries</em>' containment reference.
	 * @see #getRequiredRuntimeLibraries()
	 * @generated
	 */
	void setRequiredRuntimeLibraries(RequiredRuntimeLibraries value);

	/**
	 * Returns the value of the '<em><b>Project Dependencies</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Dependencies</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Dependencies</em>' containment reference.
	 * @see #setProjectDependencies(ProjectDependencies)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ProjectDependencies()
	 * @model containment="true"
	 * @generated
	 */
	ProjectDependencies getProjectDependencies();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getProjectDependencies <em>Project Dependencies</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Dependencies</em>' containment reference.
	 * @see #getProjectDependencies()
	 * @generated
	 */
	void setProjectDependencies(ProjectDependencies value);

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
	 * Returns the value of the '<em><b>Implemented Projects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implemented Projects</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implemented Projects</em>' containment reference.
	 * @see #setImplementedProjects(ImplementedProjects)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ImplementedProjects()
	 * @model containment="true"
	 * @generated
	 */
	ImplementedProjects getImplementedProjects();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getImplementedProjects <em>Implemented Projects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implemented Projects</em>' containment reference.
	 * @see #getImplementedProjects()
	 * @generated
	 */
	void setImplementedProjects(ImplementedProjects value);

	/**
	 * Returns the value of the '<em><b>Init Modules</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Modules</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Modules</em>' containment reference.
	 * @see #setInitModules(InitModules)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_InitModules()
	 * @model containment="true"
	 * @generated
	 */
	InitModules getInitModules();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getInitModules <em>Init Modules</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init Modules</em>' containment reference.
	 * @see #getInitModules()
	 * @generated
	 */
	void setInitModules(InitModules value);

	/**
	 * Returns the value of the '<em><b>Exec Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exec Module</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exec Module</em>' containment reference.
	 * @see #setExecModule(ExecModule)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ExecModule()
	 * @model containment="true"
	 * @generated
	 */
	ExecModule getExecModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getExecModule <em>Exec Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exec Module</em>' containment reference.
	 * @see #getExecModule()
	 * @generated
	 */
	void setExecModule(ExecModule value);

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
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_OutputPath()
	 * @model unique="false"
	 * @generated
	 */
	String getOutputPath();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getOutputPath <em>Output Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Path</em>' attribute.
	 * @see #getOutputPath()
	 * @generated
	 */
	void setOutputPath(String value);

	/**
	 * Returns the value of the '<em><b>Library Paths</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library Paths</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library Paths</em>' attribute list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_LibraryPaths()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getLibraryPaths();

	/**
	 * Returns the value of the '<em><b>Resource Paths</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Paths</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Paths</em>' attribute list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_ResourcePaths()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getResourcePaths();

	/**
	 * Returns the value of the '<em><b>Source Fragment</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.SourceFragment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Fragment</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Fragment</em>' containment reference list.
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_SourceFragment()
	 * @model containment="true"
	 * @generated
	 */
	EList<SourceFragment> getSourceFragment();

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
	 * Returns the value of the '<em><b>Tested Projects</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tested Projects</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tested Projects</em>' containment reference.
	 * @see #setTestedProjects(TestedProjects)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_TestedProjects()
	 * @model containment="true"
	 * @generated
	 */
	TestedProjects getTestedProjects();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getTestedProjects <em>Tested Projects</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tested Projects</em>' containment reference.
	 * @see #getTestedProjects()
	 * @generated
	 */
	void setTestedProjects(TestedProjects value);

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenient method for getting all tested projects defined for the current project description.
	 * Returns with an empty list if no test projects are set for the current project, otherwise returns with them.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%org.eclipse.n4js.n4mf.TestedProject%&gt;&gt; _xifexpression = null;\n&lt;%org.eclipse.n4js.n4mf.TestedProjects%&gt; _testedProjects = this.getTestedProjects();\nboolean _tripleEquals = (null == _testedProjects);\nif (_tripleEquals)\n{\n\t_xifexpression = &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.n4mf.TestedProject%&gt;&gt;emptyEList();\n}\nelse\n{\n\t_xifexpression = this.getTestedProjects().getTestedProjects();\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	EList<TestedProject> getAllTestedProjects();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenient method for getting all initializer bootstrap modules defined for the current project description.
	 * Returns with an empty list if no initializer modules are set for the current project, otherwise returns with them.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%org.eclipse.n4js.n4mf.BootstrapModule%&gt;&gt; _xifexpression = null;\n&lt;%org.eclipse.n4js.n4mf.InitModules%&gt; _initModules = this.getInitModules();\nboolean _tripleEquals = (null == _initModules);\nif (_tripleEquals)\n{\n\t_xifexpression = &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.n4mf.BootstrapModule%&gt;&gt;emptyEList();\n}\nelse\n{\n\t_xifexpression = this.getInitModules().getInitModules();\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	EList<BootstrapModule> getAllInitModules();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenient method for getting all implemented modules defined for the current project description.
	 * Returns with an empty list if no modules are implemented by the current project, otherwise returns with them.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%org.eclipse.n4js.n4mf.ProjectReference%&gt;&gt; _xifexpression = null;\n&lt;%org.eclipse.n4js.n4mf.ImplementedProjects%&gt; _implementedProjects = this.getImplementedProjects();\nboolean _tripleEquals = (null == _implementedProjects);\nif (_tripleEquals)\n{\n\t_xifexpression = &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.n4mf.ProjectReference%&gt;&gt;emptyEList();\n}\nelse\n{\n\t_xifexpression = this.getImplementedProjects().getImplementedProjects();\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	EList<ProjectReference> getAllImplementedProjects();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenient method for getting all project dependencies for the current project description.
	 * Returns with an empty list if the current project does not depend on any other projects, otherwise returns with them.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%org.eclipse.n4js.n4mf.ProjectDependency%&gt;&gt; _xifexpression = null;\n&lt;%org.eclipse.n4js.n4mf.ProjectDependencies%&gt; _projectDependencies = this.getProjectDependencies();\nboolean _tripleEquals = (null == _projectDependencies);\nif (_tripleEquals)\n{\n\t_xifexpression = &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.n4mf.ProjectDependency%&gt;&gt;emptyEList();\n}\nelse\n{\n\t_xifexpression = this.getProjectDependencies().getProjectDependencies();\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	EList<ProjectDependency> getAllProjectDependencies();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenient method for getting all provided runtime libraries defined for the current project description.
	 * Returns with an empty list if no runtime libraries are provided by the current project, otherwise returns with them.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency%&gt;&gt; _xifexpression = null;\n&lt;%org.eclipse.n4js.n4mf.ProvidedRuntimeLibraries%&gt; _providedRuntimeLibraries = this.getProvidedRuntimeLibraries();\nboolean _tripleEquals = (null == _providedRuntimeLibraries);\nif (_tripleEquals)\n{\n\t_xifexpression = &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency%&gt;&gt;emptyEList();\n}\nelse\n{\n\t_xifexpression = this.getProvidedRuntimeLibraries().getProvidedRuntimeLibraries();\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	EList<ProvidedRuntimeLibraryDependency> getAllProvidedRuntimeLibraries();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Convenient method for getting all required runtime libraries for the current project description.
	 * Returns with an empty list if no required runtime libraries are specified by the current project, otherwise returns with them.
	 * <!-- end-model-doc -->
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%org.eclipse.emf.common.util.EList%&gt;&lt;&lt;%org.eclipse.n4js.n4mf.RequiredRuntimeLibraryDependency%&gt;&gt; _xifexpression = null;\n&lt;%org.eclipse.n4js.n4mf.RequiredRuntimeLibraries%&gt; _requiredRuntimeLibraries = this.getRequiredRuntimeLibraries();\nboolean _tripleEquals = (null == _requiredRuntimeLibraries);\nif (_tripleEquals)\n{\n\t_xifexpression = &lt;%org.eclipse.emf.ecore.xcore.lib.XcoreCollectionLiterals%&gt;.&lt;&lt;%org.eclipse.n4js.n4mf.RequiredRuntimeLibraryDependency%&gt;&gt;emptyEList();\n}\nelse\n{\n\t_xifexpression = this.getRequiredRuntimeLibraries().getRequiredRuntimeLibraries();\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	EList<RequiredRuntimeLibraryDependency> getAllRequiredRuntimeLibraries();

} // ProjectDescription
