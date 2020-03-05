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
package org.eclipse.n4js.ts.types;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TModule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * *
 * The TModule is the representation of the script on the type level.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getSimpleName <em>Simple Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getVendorID <em>Vendor ID</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#isN4jsdModule <em>N4jsd Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#isStaticPolyfillModule <em>Static Polyfill Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#isStaticPolyfillAware <em>Static Polyfill Aware</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#isMainModule <em>Main Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#isPreLinkingPhase <em>Pre Linking Phase</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#isReconciled <em>Reconciled</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getDependenciesRuntime <em>Dependencies Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getCyclicModulesRuntime <em>Cyclic Modules Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getCyclicModulesLoadtimeForInheritance <em>Cyclic Modules Loadtime For Inheritance</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getRuntimeCyclicLoadtimeDependents <em>Runtime Cyclic Loadtime Dependents</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getTopLevelTypes <em>Top Level Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getVariables <em>Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getInternalTypes <em>Internal Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getExposedInternalTypes <em>Exposed Internal Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getAstMD5 <em>Ast MD5</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getComposedMemberCaches <em>Composed Member Caches</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getTemporaryTypes <em>Temporary Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.TModule#getModuleSpecifier <em>Module Specifier</em>}</li>
 * </ul>
 *
 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule()
 * @model
 * @generated
 */
public interface TModule extends SyntaxRelatedTElement, TAnnotableElement {
	/**
	 * Returns the value of the '<em><b>Simple Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The module's simple name, i.e. the last segment of its qualified name.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Simple Name</em>' attribute.
	 * @see #setSimpleName(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_SimpleName()
	 * @model unique="false"
	 * @generated
	 */
	String getSimpleName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#getSimpleName <em>Simple Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Simple Name</em>' attribute.
	 * @see #getSimpleName()
	 * @generated
	 */
	void setSimpleName(String value);

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The qualified name of the module, which is derived from the file path. Includes the module's
	 * file name (without extension) and the names of all ancestor folders up to, excluding the
	 * containing project's source folder. The containing project's name is also not included,
	 * but can be retrieved via {@link #getProjectName()}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_QualifiedName()
	 * @model unique="false"
	 * @generated
	 */
	String getQualifiedName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#getQualifiedName <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
	void setQualifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The <em>N4JS project name</em> of the project containing this module, as defined at
	 * {@link org.eclipse.n4js.utils.ProjectDescriptionUtils#isProjectNameWithScope(String)}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Project Name</em>' attribute.
	 * @see #setProjectName(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_ProjectName()
	 * @model unique="false"
	 * @generated
	 */
	String getProjectName();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#getProjectName <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Name</em>' attribute.
	 * @see #getProjectName()
	 * @generated
	 */
	void setProjectName(String value);

	/**
	 * Returns the value of the '<em><b>Vendor ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * The vendorId of the project containing this module.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Vendor ID</em>' attribute.
	 * @see #setVendorID(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_VendorID()
	 * @model unique="false"
	 * @generated
	 */
	String getVendorID();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#getVendorID <em>Vendor ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vendor ID</em>' attribute.
	 * @see #getVendorID()
	 * @generated
	 */
	void setVendorID(String value);

	/**
	 * Returns the value of the '<em><b>N4jsd Module</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Flag indicating a module defined in an <code>.n4jsd</code> file.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>N4jsd Module</em>' attribute.
	 * @see #setN4jsdModule(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_N4jsdModule()
	 * @model unique="false"
	 * @generated
	 */
	boolean isN4jsdModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#isN4jsdModule <em>N4jsd Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>N4jsd Module</em>' attribute.
	 * @see #isN4jsdModule()
	 * @generated
	 */
	void setN4jsdModule(boolean value);

	/**
	 * Returns the value of the '<em><b>Static Polyfill Module</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Flag indicating a static-polyfilling (not a standalone) module.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Static Polyfill Module</em>' attribute.
	 * @see #setStaticPolyfillModule(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_StaticPolyfillModule()
	 * @model unique="false"
	 * @generated
	 */
	boolean isStaticPolyfillModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#isStaticPolyfillModule <em>Static Polyfill Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Static Polyfill Module</em>' attribute.
	 * @see #isStaticPolyfillModule()
	 * @generated
	 */
	void setStaticPolyfillModule(boolean value);

	/**
	 * Returns the value of the '<em><b>Static Polyfill Aware</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Flag indicating a target of static-polyfilling (possibly a generated) module.
	 * Only one of {@code #staticPolyfillModule} of {@code staticPolyfillAware} can be {@code true}
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Static Polyfill Aware</em>' attribute.
	 * @see #setStaticPolyfillAware(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_StaticPolyfillAware()
	 * @model unique="false"
	 * @generated
	 */
	boolean isStaticPolyfillAware();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#isStaticPolyfillAware <em>Static Polyfill Aware</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Static Polyfill Aware</em>' attribute.
	 * @see #isStaticPolyfillAware()
	 * @generated
	 */
	void setStaticPolyfillAware(boolean value);

	/**
	 * Returns the value of the '<em><b>Main Module</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Flag indicating a MainModule (see ProjectDescription#mainModule)
	 * Used in scoping to adjust shadowing rules for the project imports (see org.eclipse.n4js.scoping.utils.ProjectImportEnablingScope).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Main Module</em>' attribute.
	 * @see #setMainModule(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_MainModule()
	 * @model unique="false"
	 * @generated
	 */
	boolean isMainModule();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#isMainModule <em>Main Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Main Module</em>' attribute.
	 * @see #isMainModule()
	 * @generated
	 */
	void setMainModule(boolean value);

	/**
	 * Returns the value of the '<em><b>Pre Linking Phase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * True iff this TModule was created during pre-linking phase.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Pre Linking Phase</em>' attribute.
	 * @see #setPreLinkingPhase(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_PreLinkingPhase()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	boolean isPreLinkingPhase();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#isPreLinkingPhase <em>Pre Linking Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pre Linking Phase</em>' attribute.
	 * @see #isPreLinkingPhase()
	 * @generated
	 */
	void setPreLinkingPhase(boolean value);

	/**
	 * Returns the value of the '<em><b>Reconciled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * True iff this TModule was reconciled, i.e. was re-linked to an AST after its creation.
	 * For details see {@code N4JSResource#isReconciled()}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Reconciled</em>' attribute.
	 * @see #setReconciled(boolean)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_Reconciled()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	boolean isReconciled();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#isReconciled <em>Reconciled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reconciled</em>' attribute.
	 * @see #isReconciled()
	 * @generated
	 */
	void setReconciled(boolean value);

	/**
	 * Returns the value of the '<em><b>Dependencies Runtime</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.RuntimeDependency}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Direct runtime dependencies of this module within same project.
	 * <p>
	 * Set at end of AST traversal (i.e. during main post-processing).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Dependencies Runtime</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_DependenciesRuntime()
	 * @model containment="true"
	 * @generated
	 */
	EList<RuntimeDependency> getDependenciesRuntime();

	/**
	 * Returns the value of the '<em><b>Cyclic Modules Runtime</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TModule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Modules within same project with a direct or indirect cyclic runtime dependency to this module.
	 * Never includes this module.
	 * <p>
	 * Set during finalization of post-processing.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cyclic Modules Runtime</em>' reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_CyclicModulesRuntime()
	 * @model
	 * @generated
	 */
	EList<TModule> getCyclicModulesRuntime();

	/**
	 * Returns the value of the '<em><b>Cyclic Modules Loadtime For Inheritance</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TModule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Modules within same project with a direct or indirect cyclic loadtime dependency to this module,
	 * taking into account only such loadtime dependencies that are caused by extends/implements clauses.
	 * Never includes this module.
	 * <p>
	 * Set during finalization of post-processing.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cyclic Modules Loadtime For Inheritance</em>' reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_CyclicModulesLoadtimeForInheritance()
	 * @model
	 * @generated
	 */
	EList<TModule> getCyclicModulesLoadtimeForInheritance();

	/**
	 * Returns the value of the '<em><b>Runtime Cyclic Loadtime Dependents</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TModule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Modules within same project that are runtime cyclic to this module AND have direct loadtime dependency
	 * to this module, taking into account only such loadtime dependencies that are caused by extends/implements
	 * clauses.
	 * <p>
	 * Set during finalization of post-processing.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Runtime Cyclic Loadtime Dependents</em>' reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_RuntimeCyclicLoadtimeDependents()
	 * @model
	 * @generated
	 */
	EList<TModule> getRuntimeCyclicLoadtimeDependents();

	/**
	 * Returns the value of the '<em><b>Top Level Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.Type}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of all types declarations in the script on the top level.
	 * These include the exported classes, interfaces, function as well
	 * as the types inferred from type defining elements that are not marked as exported.
	 * This allows for better validation messages and diagnostics in later stages
	 * of the processing.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Top Level Types</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_TopLevelTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Type> getTopLevelTypes();

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.TVariable}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A list of all top level variables in the script.
	 * These include the exported variables as well as the internal variables.
	 * Similar to #topLevelTypes, this allows for better validation messages and diagnostics.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<TVariable> getVariables();

	/**
	 * Returns the value of the '<em><b>Internal Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.Type}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Internal types may be populated incrementally by the type checker. Typically these
	 * are types derived from nested structures of the AST.
	 * <p>
	 * An anonymous, internal function may be assigned to a variable and we have
	 * to compute the type of that variable. Therefore the type has to be contained somewhere in
	 * the resource. Even worse, a named internal function can be called by name, thus it would
	 * have to become an IdentifiableElement which opens another can of worms. To avoid that, the
	 * internal function defines a type which in turn is the identifiable element. EMF constrains
	 * us to provide a container for that type, which is this containment reference.
	 * It is not persisted in the index.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Internal Types</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_InternalTypes()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<Type> getInternalTypes();

	/**
	 * Returns the value of the '<em><b>Exposed Internal Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.Type}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Sometimes, internal types are referenced by exported types, e.g. as the type of an exported
	 * variable or a field of a class. The fact that internal types are not serialized would
	 * lead to unresolved reference exceptions upon deserialization. Therefore, these internal
	 * types are serialized by moving them from 'internalTypes' to this containment reference.
	 * <p>
	 * These are not directly referable from the outside but may specify the types of exported
	 * variables, etc. Anonymous types of a script 'A' do not become visible to a script 'B' that
	 * imports 'A'. To access those, the referring {@link #variables variable} has to be used.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Exposed Internal Types</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_ExposedInternalTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Type> getExposedInternalTypes();

	/**
	 * Returns the value of the '<em><b>Ast MD5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * MD5 hash (hex) of the AST from which this type model was created. This can be used to quickly
	 * compare two type models created from -- maybe -- different versions of the same AST.
	 * <p>
	 * The MD5 is used since this is the fastest and easiest solution to compare the AST. More elegant
	 * methods may would use the AST (in order to ignore comments and whitespaces), however this
	 * is rather complicated to implement: No proxies must be resolved and the hash must be stable between
	 * different runs (and between different machines to enable stable tests). This is rather hard to
	 * achieve with traversing the AST.
	 * 
	 * @see org.eclipse.n4js.typesbuilder.N4JSTypesBuilder.md5Hex(String)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ast MD5</em>' attribute.
	 * @see #setAstMD5(String)
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_AstMD5()
	 * @model unique="false" transient="true"
	 * @generated
	 */
	String getAstMD5();

	/**
	 * Sets the value of the '{@link org.eclipse.n4js.ts.types.TModule#getAstMD5 <em>Ast MD5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ast MD5</em>' attribute.
	 * @see #getAstMD5()
	 * @generated
	 */
	void setAstMD5(String value);

	/**
	 * Returns the value of the '<em><b>Composed Member Caches</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.ComposedMemberCache}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Caches for composed members. Not serialized to the Xtext index.
	 * See {@link ComposedTypeRef#composedMemberCache()} for details.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Composed Member Caches</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_ComposedMemberCaches()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<ComposedMemberCache> getComposedMemberCaches();

	/**
	 * Returns the value of the '<em><b>Temporary Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.n4js.ts.types.Type}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * As a rule, types must always be contained in a resource and resource set (as opposed to type references which
	 * need not be contained). Therefore, when types are required to be created for a temporary purpose, they need to be
	 * added somewhere, temporarily. This transient containment reference is used for that purpose.
	 * <p>
	 * IMPORTANT: code using this reference should
	 * <ol>
	 * <li>turn off notifications whenever adding/removing a type to this reference or when changing properties of an
	 * already added type (to avoid unnecessary cache clear),
	 * <li>remove the temporary type when it is no longer required,<br>
	 * For convenience, the transpiler infrastructure will <b>clear this reference after transpilation</b> has
	 * completed, so transpiler transformations do not have to handle removal of temporary types themselves.
	 * <li>make sure no ordinary types ever refer to a temporary type contained here (references in the other direction
	 * are ok, though),
	 * <li>use the convenience methods for dealing with this reference provided in {@code N4JSResource}:
	 * {@code addTemporaryType} and {@code clearTemporaryTypes}.
	 * </ol>
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Temporary Types</em>' containment reference list.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_TemporaryTypes()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<Type> getTemporaryTypes();

	/**
	 * Returns the value of the '<em><b>Module Specifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * *
	 * Returns this module's module specifier as it would appear, for example, in the string literal at the end of an
	 * import statement.
	 * <p>
	 * Since we changed the delimiter for our internal qualified names from '.' to '/', this simply returns the same
	 * value as {@link #getQualifiedName()}. However, this getter is retained for the time being to let client code
	 * differentiate between internal use (qualified name, e.g. in the Xtext index) and Javascript context (module
	 * specifier, e.g. in import statements).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Module Specifier</em>' attribute.
	 * @see org.eclipse.n4js.ts.types.TypesPackage#getTModule_ModuleSpecifier()
	 * @model unique="false" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getModuleSpecifier();

} // TModule
