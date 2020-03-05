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
package org.eclipse.n4js.ts.types.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.n4js.ts.types.ComposedMemberCache;
import org.eclipse.n4js.ts.types.RuntimeDependency;
import org.eclipse.n4js.ts.types.TAnnotableElement;
import org.eclipse.n4js.ts.types.TAnnotation;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TModule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getSimpleName <em>Simple Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getQualifiedName <em>Qualified Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getVendorID <em>Vendor ID</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#isN4jsdModule <em>N4jsd Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#isStaticPolyfillModule <em>Static Polyfill Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#isStaticPolyfillAware <em>Static Polyfill Aware</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#isMainModule <em>Main Module</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#isPreLinkingPhase <em>Pre Linking Phase</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#isReconciled <em>Reconciled</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getDependenciesRuntime <em>Dependencies Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getCyclicModulesRuntime <em>Cyclic Modules Runtime</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getCyclicModulesLoadtimeForInheritance <em>Cyclic Modules Loadtime For Inheritance</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getRuntimeCyclicLoadtimeDependents <em>Runtime Cyclic Loadtime Dependents</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getTopLevelTypes <em>Top Level Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getInternalTypes <em>Internal Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getExposedInternalTypes <em>Exposed Internal Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getAstMD5 <em>Ast MD5</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getComposedMemberCaches <em>Composed Member Caches</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getTemporaryTypes <em>Temporary Types</em>}</li>
 *   <li>{@link org.eclipse.n4js.ts.types.impl.TModuleImpl#getModuleSpecifier <em>Module Specifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TModuleImpl extends SyntaxRelatedTElementImpl implements TModule {
	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<TAnnotation> annotations;

	/**
	 * The default value of the '{@link #getSimpleName() <em>Simple Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleName()
	 * @generated
	 * @ordered
	 */
	protected static final String SIMPLE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSimpleName() <em>Simple Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleName()
	 * @generated
	 * @ordered
	 */
	protected String simpleName = SIMPLE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedName() <em>Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String qualifiedName = QUALIFIED_NAME_EDEFAULT;

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
	 * The default value of the '{@link #getVendorID() <em>Vendor ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendorID()
	 * @generated
	 * @ordered
	 */
	protected static final String VENDOR_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVendorID() <em>Vendor ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendorID()
	 * @generated
	 * @ordered
	 */
	protected String vendorID = VENDOR_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isN4jsdModule() <em>N4jsd Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isN4jsdModule()
	 * @generated
	 * @ordered
	 */
	protected static final boolean N4JSD_MODULE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isN4jsdModule() <em>N4jsd Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isN4jsdModule()
	 * @generated
	 * @ordered
	 */
	protected boolean n4jsdModule = N4JSD_MODULE_EDEFAULT;

	/**
	 * The default value of the '{@link #isStaticPolyfillModule() <em>Static Polyfill Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticPolyfillModule()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STATIC_POLYFILL_MODULE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStaticPolyfillModule() <em>Static Polyfill Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticPolyfillModule()
	 * @generated
	 * @ordered
	 */
	protected boolean staticPolyfillModule = STATIC_POLYFILL_MODULE_EDEFAULT;

	/**
	 * The default value of the '{@link #isStaticPolyfillAware() <em>Static Polyfill Aware</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticPolyfillAware()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STATIC_POLYFILL_AWARE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStaticPolyfillAware() <em>Static Polyfill Aware</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStaticPolyfillAware()
	 * @generated
	 * @ordered
	 */
	protected boolean staticPolyfillAware = STATIC_POLYFILL_AWARE_EDEFAULT;

	/**
	 * The default value of the '{@link #isMainModule() <em>Main Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMainModule()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MAIN_MODULE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMainModule() <em>Main Module</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMainModule()
	 * @generated
	 * @ordered
	 */
	protected boolean mainModule = MAIN_MODULE_EDEFAULT;

	/**
	 * The default value of the '{@link #isPreLinkingPhase() <em>Pre Linking Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreLinkingPhase()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PRE_LINKING_PHASE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPreLinkingPhase() <em>Pre Linking Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPreLinkingPhase()
	 * @generated
	 * @ordered
	 */
	protected boolean preLinkingPhase = PRE_LINKING_PHASE_EDEFAULT;

	/**
	 * The default value of the '{@link #isReconciled() <em>Reconciled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReconciled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RECONCILED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReconciled() <em>Reconciled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReconciled()
	 * @generated
	 * @ordered
	 */
	protected boolean reconciled = RECONCILED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDependenciesRuntime() <em>Dependencies Runtime</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependenciesRuntime()
	 * @generated
	 * @ordered
	 */
	protected EList<RuntimeDependency> dependenciesRuntime;

	/**
	 * The cached value of the '{@link #getCyclicModulesRuntime() <em>Cyclic Modules Runtime</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCyclicModulesRuntime()
	 * @generated
	 * @ordered
	 */
	protected EList<TModule> cyclicModulesRuntime;

	/**
	 * The cached value of the '{@link #getCyclicModulesLoadtimeForInheritance() <em>Cyclic Modules Loadtime For Inheritance</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCyclicModulesLoadtimeForInheritance()
	 * @generated
	 * @ordered
	 */
	protected EList<TModule> cyclicModulesLoadtimeForInheritance;

	/**
	 * The cached value of the '{@link #getRuntimeCyclicLoadtimeDependents() <em>Runtime Cyclic Loadtime Dependents</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuntimeCyclicLoadtimeDependents()
	 * @generated
	 * @ordered
	 */
	protected EList<TModule> runtimeCyclicLoadtimeDependents;

	/**
	 * The cached value of the '{@link #getTopLevelTypes() <em>Top Level Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopLevelTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> topLevelTypes;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList<TVariable> variables;

	/**
	 * The cached value of the '{@link #getInternalTypes() <em>Internal Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInternalTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> internalTypes;

	/**
	 * The cached value of the '{@link #getExposedInternalTypes() <em>Exposed Internal Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExposedInternalTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> exposedInternalTypes;

	/**
	 * The default value of the '{@link #getAstMD5() <em>Ast MD5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstMD5()
	 * @generated
	 * @ordered
	 */
	protected static final String AST_MD5_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAstMD5() <em>Ast MD5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAstMD5()
	 * @generated
	 * @ordered
	 */
	protected String astMD5 = AST_MD5_EDEFAULT;

	/**
	 * The cached value of the '{@link #getComposedMemberCaches() <em>Composed Member Caches</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComposedMemberCaches()
	 * @generated
	 * @ordered
	 */
	protected EList<ComposedMemberCache> composedMemberCaches;

	/**
	 * The cached value of the '{@link #getTemporaryTypes() <em>Temporary Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemporaryTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> temporaryTypes;

	/**
	 * The default value of the '{@link #getModuleSpecifier() <em>Module Specifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleSpecifier()
	 * @generated
	 * @ordered
	 */
	protected static final String MODULE_SPECIFIER_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypesPackage.Literals.TMODULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TAnnotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<TAnnotation>(TAnnotation.class, this, TypesPackage.TMODULE__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSimpleName(String newSimpleName) {
		String oldSimpleName = simpleName;
		simpleName = newSimpleName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__SIMPLE_NAME, oldSimpleName, simpleName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedName() {
		return qualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setQualifiedName(String newQualifiedName) {
		String oldQualifiedName = qualifiedName;
		qualifiedName = newQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__QUALIFIED_NAME, oldQualifiedName, qualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getProjectName() {
		return projectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProjectName(String newProjectName) {
		String oldProjectName = projectName;
		projectName = newProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__PROJECT_NAME, oldProjectName, projectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getVendorID() {
		return vendorID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVendorID(String newVendorID) {
		String oldVendorID = vendorID;
		vendorID = newVendorID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__VENDOR_ID, oldVendorID, vendorID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isN4jsdModule() {
		return n4jsdModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setN4jsdModule(boolean newN4jsdModule) {
		boolean oldN4jsdModule = n4jsdModule;
		n4jsdModule = newN4jsdModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__N4JSD_MODULE, oldN4jsdModule, n4jsdModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStaticPolyfillModule() {
		return staticPolyfillModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStaticPolyfillModule(boolean newStaticPolyfillModule) {
		boolean oldStaticPolyfillModule = staticPolyfillModule;
		staticPolyfillModule = newStaticPolyfillModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__STATIC_POLYFILL_MODULE, oldStaticPolyfillModule, staticPolyfillModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStaticPolyfillAware() {
		return staticPolyfillAware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStaticPolyfillAware(boolean newStaticPolyfillAware) {
		boolean oldStaticPolyfillAware = staticPolyfillAware;
		staticPolyfillAware = newStaticPolyfillAware;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__STATIC_POLYFILL_AWARE, oldStaticPolyfillAware, staticPolyfillAware));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isMainModule() {
		return mainModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMainModule(boolean newMainModule) {
		boolean oldMainModule = mainModule;
		mainModule = newMainModule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__MAIN_MODULE, oldMainModule, mainModule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPreLinkingPhase() {
		return preLinkingPhase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPreLinkingPhase(boolean newPreLinkingPhase) {
		boolean oldPreLinkingPhase = preLinkingPhase;
		preLinkingPhase = newPreLinkingPhase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__PRE_LINKING_PHASE, oldPreLinkingPhase, preLinkingPhase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReconciled() {
		return reconciled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReconciled(boolean newReconciled) {
		boolean oldReconciled = reconciled;
		reconciled = newReconciled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__RECONCILED, oldReconciled, reconciled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RuntimeDependency> getDependenciesRuntime() {
		if (dependenciesRuntime == null) {
			dependenciesRuntime = new EObjectContainmentEList<RuntimeDependency>(RuntimeDependency.class, this, TypesPackage.TMODULE__DEPENDENCIES_RUNTIME);
		}
		return dependenciesRuntime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TModule> getCyclicModulesRuntime() {
		if (cyclicModulesRuntime == null) {
			cyclicModulesRuntime = new EObjectResolvingEList<TModule>(TModule.class, this, TypesPackage.TMODULE__CYCLIC_MODULES_RUNTIME);
		}
		return cyclicModulesRuntime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TModule> getCyclicModulesLoadtimeForInheritance() {
		if (cyclicModulesLoadtimeForInheritance == null) {
			cyclicModulesLoadtimeForInheritance = new EObjectResolvingEList<TModule>(TModule.class, this, TypesPackage.TMODULE__CYCLIC_MODULES_LOADTIME_FOR_INHERITANCE);
		}
		return cyclicModulesLoadtimeForInheritance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TModule> getRuntimeCyclicLoadtimeDependents() {
		if (runtimeCyclicLoadtimeDependents == null) {
			runtimeCyclicLoadtimeDependents = new EObjectResolvingEList<TModule>(TModule.class, this, TypesPackage.TMODULE__RUNTIME_CYCLIC_LOADTIME_DEPENDENTS);
		}
		return runtimeCyclicLoadtimeDependents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Type> getTopLevelTypes() {
		if (topLevelTypes == null) {
			topLevelTypes = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.TMODULE__TOP_LEVEL_TYPES);
		}
		return topLevelTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TVariable> getVariables() {
		if (variables == null) {
			variables = new EObjectContainmentEList<TVariable>(TVariable.class, this, TypesPackage.TMODULE__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Type> getInternalTypes() {
		if (internalTypes == null) {
			internalTypes = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.TMODULE__INTERNAL_TYPES);
		}
		return internalTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Type> getExposedInternalTypes() {
		if (exposedInternalTypes == null) {
			exposedInternalTypes = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES);
		}
		return exposedInternalTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getAstMD5() {
		return astMD5;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAstMD5(String newAstMD5) {
		String oldAstMD5 = astMD5;
		astMD5 = newAstMD5;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TypesPackage.TMODULE__AST_MD5, oldAstMD5, astMD5));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ComposedMemberCache> getComposedMemberCaches() {
		if (composedMemberCaches == null) {
			composedMemberCaches = new EObjectContainmentEList<ComposedMemberCache>(ComposedMemberCache.class, this, TypesPackage.TMODULE__COMPOSED_MEMBER_CACHES);
		}
		return composedMemberCaches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Type> getTemporaryTypes() {
		if (temporaryTypes == null) {
			temporaryTypes = new EObjectContainmentEList<Type>(Type.class, this, TypesPackage.TMODULE__TEMPORARY_TYPES);
		}
		return temporaryTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModuleSpecifier() {
		return this.getQualifiedName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TypesPackage.TMODULE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__DEPENDENCIES_RUNTIME:
				return ((InternalEList<?>)getDependenciesRuntime()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				return ((InternalEList<?>)getTopLevelTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__VARIABLES:
				return ((InternalEList<?>)getVariables()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				return ((InternalEList<?>)getInternalTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				return ((InternalEList<?>)getExposedInternalTypes()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__COMPOSED_MEMBER_CACHES:
				return ((InternalEList<?>)getComposedMemberCaches()).basicRemove(otherEnd, msgs);
			case TypesPackage.TMODULE__TEMPORARY_TYPES:
				return ((InternalEList<?>)getTemporaryTypes()).basicRemove(otherEnd, msgs);
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
			case TypesPackage.TMODULE__ANNOTATIONS:
				return getAnnotations();
			case TypesPackage.TMODULE__SIMPLE_NAME:
				return getSimpleName();
			case TypesPackage.TMODULE__QUALIFIED_NAME:
				return getQualifiedName();
			case TypesPackage.TMODULE__PROJECT_NAME:
				return getProjectName();
			case TypesPackage.TMODULE__VENDOR_ID:
				return getVendorID();
			case TypesPackage.TMODULE__N4JSD_MODULE:
				return isN4jsdModule();
			case TypesPackage.TMODULE__STATIC_POLYFILL_MODULE:
				return isStaticPolyfillModule();
			case TypesPackage.TMODULE__STATIC_POLYFILL_AWARE:
				return isStaticPolyfillAware();
			case TypesPackage.TMODULE__MAIN_MODULE:
				return isMainModule();
			case TypesPackage.TMODULE__PRE_LINKING_PHASE:
				return isPreLinkingPhase();
			case TypesPackage.TMODULE__RECONCILED:
				return isReconciled();
			case TypesPackage.TMODULE__DEPENDENCIES_RUNTIME:
				return getDependenciesRuntime();
			case TypesPackage.TMODULE__CYCLIC_MODULES_RUNTIME:
				return getCyclicModulesRuntime();
			case TypesPackage.TMODULE__CYCLIC_MODULES_LOADTIME_FOR_INHERITANCE:
				return getCyclicModulesLoadtimeForInheritance();
			case TypesPackage.TMODULE__RUNTIME_CYCLIC_LOADTIME_DEPENDENTS:
				return getRuntimeCyclicLoadtimeDependents();
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				return getTopLevelTypes();
			case TypesPackage.TMODULE__VARIABLES:
				return getVariables();
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				return getInternalTypes();
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				return getExposedInternalTypes();
			case TypesPackage.TMODULE__AST_MD5:
				return getAstMD5();
			case TypesPackage.TMODULE__COMPOSED_MEMBER_CACHES:
				return getComposedMemberCaches();
			case TypesPackage.TMODULE__TEMPORARY_TYPES:
				return getTemporaryTypes();
			case TypesPackage.TMODULE__MODULE_SPECIFIER:
				return getModuleSpecifier();
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
			case TypesPackage.TMODULE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends TAnnotation>)newValue);
				return;
			case TypesPackage.TMODULE__SIMPLE_NAME:
				setSimpleName((String)newValue);
				return;
			case TypesPackage.TMODULE__QUALIFIED_NAME:
				setQualifiedName((String)newValue);
				return;
			case TypesPackage.TMODULE__PROJECT_NAME:
				setProjectName((String)newValue);
				return;
			case TypesPackage.TMODULE__VENDOR_ID:
				setVendorID((String)newValue);
				return;
			case TypesPackage.TMODULE__N4JSD_MODULE:
				setN4jsdModule((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__STATIC_POLYFILL_MODULE:
				setStaticPolyfillModule((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__STATIC_POLYFILL_AWARE:
				setStaticPolyfillAware((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__MAIN_MODULE:
				setMainModule((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__PRE_LINKING_PHASE:
				setPreLinkingPhase((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__RECONCILED:
				setReconciled((Boolean)newValue);
				return;
			case TypesPackage.TMODULE__DEPENDENCIES_RUNTIME:
				getDependenciesRuntime().clear();
				getDependenciesRuntime().addAll((Collection<? extends RuntimeDependency>)newValue);
				return;
			case TypesPackage.TMODULE__CYCLIC_MODULES_RUNTIME:
				getCyclicModulesRuntime().clear();
				getCyclicModulesRuntime().addAll((Collection<? extends TModule>)newValue);
				return;
			case TypesPackage.TMODULE__CYCLIC_MODULES_LOADTIME_FOR_INHERITANCE:
				getCyclicModulesLoadtimeForInheritance().clear();
				getCyclicModulesLoadtimeForInheritance().addAll((Collection<? extends TModule>)newValue);
				return;
			case TypesPackage.TMODULE__RUNTIME_CYCLIC_LOADTIME_DEPENDENTS:
				getRuntimeCyclicLoadtimeDependents().clear();
				getRuntimeCyclicLoadtimeDependents().addAll((Collection<? extends TModule>)newValue);
				return;
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				getTopLevelTypes().clear();
				getTopLevelTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case TypesPackage.TMODULE__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection<? extends TVariable>)newValue);
				return;
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				getInternalTypes().clear();
				getInternalTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				getExposedInternalTypes().clear();
				getExposedInternalTypes().addAll((Collection<? extends Type>)newValue);
				return;
			case TypesPackage.TMODULE__AST_MD5:
				setAstMD5((String)newValue);
				return;
			case TypesPackage.TMODULE__COMPOSED_MEMBER_CACHES:
				getComposedMemberCaches().clear();
				getComposedMemberCaches().addAll((Collection<? extends ComposedMemberCache>)newValue);
				return;
			case TypesPackage.TMODULE__TEMPORARY_TYPES:
				getTemporaryTypes().clear();
				getTemporaryTypes().addAll((Collection<? extends Type>)newValue);
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
			case TypesPackage.TMODULE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case TypesPackage.TMODULE__SIMPLE_NAME:
				setSimpleName(SIMPLE_NAME_EDEFAULT);
				return;
			case TypesPackage.TMODULE__QUALIFIED_NAME:
				setQualifiedName(QUALIFIED_NAME_EDEFAULT);
				return;
			case TypesPackage.TMODULE__PROJECT_NAME:
				setProjectName(PROJECT_NAME_EDEFAULT);
				return;
			case TypesPackage.TMODULE__VENDOR_ID:
				setVendorID(VENDOR_ID_EDEFAULT);
				return;
			case TypesPackage.TMODULE__N4JSD_MODULE:
				setN4jsdModule(N4JSD_MODULE_EDEFAULT);
				return;
			case TypesPackage.TMODULE__STATIC_POLYFILL_MODULE:
				setStaticPolyfillModule(STATIC_POLYFILL_MODULE_EDEFAULT);
				return;
			case TypesPackage.TMODULE__STATIC_POLYFILL_AWARE:
				setStaticPolyfillAware(STATIC_POLYFILL_AWARE_EDEFAULT);
				return;
			case TypesPackage.TMODULE__MAIN_MODULE:
				setMainModule(MAIN_MODULE_EDEFAULT);
				return;
			case TypesPackage.TMODULE__PRE_LINKING_PHASE:
				setPreLinkingPhase(PRE_LINKING_PHASE_EDEFAULT);
				return;
			case TypesPackage.TMODULE__RECONCILED:
				setReconciled(RECONCILED_EDEFAULT);
				return;
			case TypesPackage.TMODULE__DEPENDENCIES_RUNTIME:
				getDependenciesRuntime().clear();
				return;
			case TypesPackage.TMODULE__CYCLIC_MODULES_RUNTIME:
				getCyclicModulesRuntime().clear();
				return;
			case TypesPackage.TMODULE__CYCLIC_MODULES_LOADTIME_FOR_INHERITANCE:
				getCyclicModulesLoadtimeForInheritance().clear();
				return;
			case TypesPackage.TMODULE__RUNTIME_CYCLIC_LOADTIME_DEPENDENTS:
				getRuntimeCyclicLoadtimeDependents().clear();
				return;
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				getTopLevelTypes().clear();
				return;
			case TypesPackage.TMODULE__VARIABLES:
				getVariables().clear();
				return;
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				getInternalTypes().clear();
				return;
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				getExposedInternalTypes().clear();
				return;
			case TypesPackage.TMODULE__AST_MD5:
				setAstMD5(AST_MD5_EDEFAULT);
				return;
			case TypesPackage.TMODULE__COMPOSED_MEMBER_CACHES:
				getComposedMemberCaches().clear();
				return;
			case TypesPackage.TMODULE__TEMPORARY_TYPES:
				getTemporaryTypes().clear();
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
			case TypesPackage.TMODULE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case TypesPackage.TMODULE__SIMPLE_NAME:
				return SIMPLE_NAME_EDEFAULT == null ? simpleName != null : !SIMPLE_NAME_EDEFAULT.equals(simpleName);
			case TypesPackage.TMODULE__QUALIFIED_NAME:
				return QUALIFIED_NAME_EDEFAULT == null ? qualifiedName != null : !QUALIFIED_NAME_EDEFAULT.equals(qualifiedName);
			case TypesPackage.TMODULE__PROJECT_NAME:
				return PROJECT_NAME_EDEFAULT == null ? projectName != null : !PROJECT_NAME_EDEFAULT.equals(projectName);
			case TypesPackage.TMODULE__VENDOR_ID:
				return VENDOR_ID_EDEFAULT == null ? vendorID != null : !VENDOR_ID_EDEFAULT.equals(vendorID);
			case TypesPackage.TMODULE__N4JSD_MODULE:
				return n4jsdModule != N4JSD_MODULE_EDEFAULT;
			case TypesPackage.TMODULE__STATIC_POLYFILL_MODULE:
				return staticPolyfillModule != STATIC_POLYFILL_MODULE_EDEFAULT;
			case TypesPackage.TMODULE__STATIC_POLYFILL_AWARE:
				return staticPolyfillAware != STATIC_POLYFILL_AWARE_EDEFAULT;
			case TypesPackage.TMODULE__MAIN_MODULE:
				return mainModule != MAIN_MODULE_EDEFAULT;
			case TypesPackage.TMODULE__PRE_LINKING_PHASE:
				return preLinkingPhase != PRE_LINKING_PHASE_EDEFAULT;
			case TypesPackage.TMODULE__RECONCILED:
				return reconciled != RECONCILED_EDEFAULT;
			case TypesPackage.TMODULE__DEPENDENCIES_RUNTIME:
				return dependenciesRuntime != null && !dependenciesRuntime.isEmpty();
			case TypesPackage.TMODULE__CYCLIC_MODULES_RUNTIME:
				return cyclicModulesRuntime != null && !cyclicModulesRuntime.isEmpty();
			case TypesPackage.TMODULE__CYCLIC_MODULES_LOADTIME_FOR_INHERITANCE:
				return cyclicModulesLoadtimeForInheritance != null && !cyclicModulesLoadtimeForInheritance.isEmpty();
			case TypesPackage.TMODULE__RUNTIME_CYCLIC_LOADTIME_DEPENDENTS:
				return runtimeCyclicLoadtimeDependents != null && !runtimeCyclicLoadtimeDependents.isEmpty();
			case TypesPackage.TMODULE__TOP_LEVEL_TYPES:
				return topLevelTypes != null && !topLevelTypes.isEmpty();
			case TypesPackage.TMODULE__VARIABLES:
				return variables != null && !variables.isEmpty();
			case TypesPackage.TMODULE__INTERNAL_TYPES:
				return internalTypes != null && !internalTypes.isEmpty();
			case TypesPackage.TMODULE__EXPOSED_INTERNAL_TYPES:
				return exposedInternalTypes != null && !exposedInternalTypes.isEmpty();
			case TypesPackage.TMODULE__AST_MD5:
				return AST_MD5_EDEFAULT == null ? astMD5 != null : !AST_MD5_EDEFAULT.equals(astMD5);
			case TypesPackage.TMODULE__COMPOSED_MEMBER_CACHES:
				return composedMemberCaches != null && !composedMemberCaches.isEmpty();
			case TypesPackage.TMODULE__TEMPORARY_TYPES:
				return temporaryTypes != null && !temporaryTypes.isEmpty();
			case TypesPackage.TMODULE__MODULE_SPECIFIER:
				return MODULE_SPECIFIER_EDEFAULT == null ? getModuleSpecifier() != null : !MODULE_SPECIFIER_EDEFAULT.equals(getModuleSpecifier());
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TAnnotableElement.class) {
			switch (derivedFeatureID) {
				case TypesPackage.TMODULE__ANNOTATIONS: return TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TAnnotableElement.class) {
			switch (baseFeatureID) {
				case TypesPackage.TANNOTABLE_ELEMENT__ANNOTATIONS: return TypesPackage.TMODULE__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (simpleName: ");
		result.append(simpleName);
		result.append(", qualifiedName: ");
		result.append(qualifiedName);
		result.append(", projectName: ");
		result.append(projectName);
		result.append(", vendorID: ");
		result.append(vendorID);
		result.append(", n4jsdModule: ");
		result.append(n4jsdModule);
		result.append(", staticPolyfillModule: ");
		result.append(staticPolyfillModule);
		result.append(", staticPolyfillAware: ");
		result.append(staticPolyfillAware);
		result.append(", mainModule: ");
		result.append(mainModule);
		result.append(", preLinkingPhase: ");
		result.append(preLinkingPhase);
		result.append(", reconciled: ");
		result.append(reconciled);
		result.append(", astMD5: ");
		result.append(astMD5);
		result.append(')');
		return result.toString();
	}

} //TModuleImpl
