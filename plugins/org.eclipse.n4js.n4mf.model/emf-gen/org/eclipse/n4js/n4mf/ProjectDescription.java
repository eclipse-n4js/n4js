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
 *   <li>{@link org.eclipse.n4js.n4mf.ProjectDescription#getDeclaredVendorId <em>Declared Vendor Id</em>}</li>
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
public interface ProjectDescription extends EObject {
	/**
	 * Returns the value of the '<em><b>Project Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * the project ID
	 * <!-- end-model-doc -->
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
	 * Returns the value of the '<em><b>Declared Vendor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * the vendor ID
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Declared Vendor Id</em>' attribute.
	 * @see #setDeclaredVendorId(String)
	 * @see org.eclipse.n4js.n4mf.N4mfPackage#getProjectDescription_DeclaredVendorId()
	 * @model unique="false"
	 * @generated
	 */
	String getDeclaredVendorId();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.n4mf.ProjectDescription#getDeclaredVendorId <em>Declared Vendor Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Vendor Id</em>' attribute.
	 * @see #getDeclaredVendorId()
	 * @generated
	 */
	void setDeclaredVendorId(String value);

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
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.ProvidedRuntimeLibraryDependency}.
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
	EList<ProvidedRuntimeLibraryDependency> getProvidedRuntimeLibraries();

	/**
	 * Returns the value of the '<em><b>Required Runtime Libraries</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.RequiredRuntimeLibraryDependency}.
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
	EList<RequiredRuntimeLibraryDependency> getRequiredRuntimeLibraries();

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
	 * Returns the value of the '<em><b>Tested Projects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.n4mf.TestedProject}.
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
	EList<TestedProject> getTestedProjects();

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
	 * @model kind="operation" unique="false"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='&lt;%java.lang.String%&gt; _xifexpression = null;\n&lt;%java.lang.String%&gt; _declaredVendorId = this.getDeclaredVendorId();\nboolean _tripleNotEquals = (_declaredVendorId != null);\nif (_tripleNotEquals)\n{\n\t_xifexpression = this.getDeclaredVendorId();\n}\nelse\n{\n\t&lt;%org.eclipse.emf.ecore.EObject%&gt; _eContainer = this.eContainer();\n\t&lt;%org.eclipse.emf.ecore.EObject%&gt; _eContainer_1 = null;\n\tif (_eContainer!=null)\n\t{\n\t\t_eContainer_1=_eContainer.eContainer();\n\t}\n\t&lt;%org.eclipse.emf.ecore.EObject%&gt; _eContainer_2 = null;\n\tif (_eContainer_1!=null)\n\t{\n\t\t_eContainer_2=_eContainer_1.eContainer();\n\t}\n\t&lt;%java.lang.String%&gt; _declaredVendorId_1 = null;\n\tif (((&lt;%org.eclipse.n4js.n4mf.ProjectDescription%&gt;) _eContainer_2)!=null)\n\t{\n\t\t_declaredVendorId_1=((&lt;%org.eclipse.n4js.n4mf.ProjectDescription%&gt;) _eContainer_2).getDeclaredVendorId();\n\t}\n\t_xifexpression = _declaredVendorId_1;\n}\nreturn _xifexpression;'"
	 * @generated
	 */
	String getVendorId();

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
